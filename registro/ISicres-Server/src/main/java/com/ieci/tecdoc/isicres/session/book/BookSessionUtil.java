/**
 *
 */
package com.ieci.tecdoc.isicres.session.book;

import gnu.trove.THashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.type.Type;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.invesdoc.Idocarchhdr;
import com.ieci.tecdoc.common.invesdoc.Idocfmt;
import com.ieci.tecdoc.common.invesdoc.Idocprefwfmt;
import com.ieci.tecdoc.common.invesdoc.Iusergroupuser;
import com.ieci.tecdoc.common.invesdoc.Iuserobjhdr;
import com.ieci.tecdoc.common.invesdoc.Iuserusertype;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrRegstate;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfIn;
import com.ieci.tecdoc.common.isicres.Keys;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.keys.ISicresKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.EntityByLanguage;
import com.ieci.tecdoc.common.utils.ISicresQueries;
import com.ieci.tecdoc.common.utils.Repository;
import com.ieci.tecdoc.common.utils.ScrRegStateByLanguage;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FFldDef;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FieldFormat;
import com.ieci.tecdoc.isicres.session.utils.UtilsSession;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.Validator;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

/**
 * @author 66575267
 *
 * @date 30/06/2009
 *
 *
 * Esta clase contiene metodos complementarios de la clase BookSession
 */
public class BookSessionUtil extends UtilsSession implements ServerKeys, Keys,
		HibernateKeys {

	private static final Logger log = Logger.getLogger(BookSessionUtil.class);

	/***************************************************************************
	 * PUBLIC METHOD
	 **************************************************************************/

	/***************************************************************************
	 * PROTECTED METHOD
	 **************************************************************************/

	protected static List getBooks(String sessionID, int bookType,
			boolean oficAsoc, Locale locale, String entidad)
			throws BookException, SessionException, ValidationException {
		return getBooks(sessionID, bookType, oficAsoc, null, locale, entidad);
	}

	protected static List getBooks(String sessionID, int bookType,
			boolean oficAsoc, Integer perm, Locale locale, String entidad)
			throws BookException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		List books = new ArrayList();
		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			// Recuperamos la informacion y perfil del usuario
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			Iuserusertype userusertype = (Iuserusertype) cacheBag
					.get(HIBERNATE_Iuserusertype);
			ScrOfic scrofic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

			// Recuperamos los libros de entrada o de salida
			StringBuffer query = new StringBuffer();
			query.append("FROM ");
			query.append(HIBERNATE_ScrRegstate);
			query.append(" scr WHERE scr.idocarchhdr.type=?");

			Iterator results = session.iterate(query.toString(), new Integer(
					bookType), Hibernate.INTEGER);
			while (results.hasNext()) {
				// Para cada libro analizamos el perfil del usuario
				ScrRegstate scrregstate = (ScrRegstate) results.next();
				Idocarchhdr idoccarchhdr = scrregstate.getIdocarchhdr();

				// Guardamos si el libro es de entrada o se salida
				Repository.getInstance(entidad).setProperty(
						String.valueOf(idoccarchhdr.getId()),
						new Integer(idoccarchhdr.getType()));

				int numeration = getNumeration(session, idoccarchhdr.getId(),
						scrofic, scrregstate);
				// Si es local, ya no se chequean los permisos. El libro ya no
				// es devuelto.
				boolean includeBook = (numeration == ISicresKeys.SCR_NUMERATION_LOCAL);

				if (!includeBook) {
					includeBook = isUserPermBook(session, user, userusertype,
							idoccarchhdr, perm, oficAsoc);
				}

				if (includeBook) {
					String bookNameByLanguage = null;
					if (!locale.getLanguage().equals("es")) {
						bookNameByLanguage = DBEntityDAOFactory.getCurrentDBEntityDAO()
							.getDescriptionByLocale(idoccarchhdr.getId(), false, false, locale.getLanguage(),
									EntityByLanguage.getTableName(22), entidad);
					} else {
						bookNameByLanguage = idoccarchhdr.getName();
					}

					ScrRegStateByLanguage scrRegStateByLanguage = new ScrRegStateByLanguage();
					scrRegStateByLanguage.setScrregstateId(scrregstate.getId());
					scrRegStateByLanguage
							.setIdocarchhdrId(idoccarchhdr.getId());
					scrRegStateByLanguage
							.setIdocarchhdrName(bookNameByLanguage);
					scrRegStateByLanguage.setScrregstateState(scrregstate
							.getState());
					scrRegStateByLanguage.setType(idoccarchhdr.getType());
					scrRegStateByLanguage.setIdocarchhdrRemarks(idoccarchhdr
							.getRemarks());
					//books.add(scrregstate);
					books.add(scrRegStateByLanguage);
				}
			}

			HibernateUtil.commitTransaction(tran);
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			// Por aqui pasa la HibernateException tambien.
			HibernateUtil.rollbackTransaction(tran);
			log.error("Imposible recuperar los libros de entrada", e);
			throw new BookException(BookException.ERROR_CANNOT_OPEN_BOOK);
		} finally {
			HibernateUtil.closeSession(entidad);
		}

		return books;
	}

	protected static void getIdocprefwfmt1(Session session, Integer bookId,
			Integer userId, Integer deptId, List iUserGroupUser,
			Map bookInformation) throws BookException, HibernateException {

		Integer format[] = new Integer[] {
				new Integer(IDocKeys.IDOCPREFWFMT_TYPE_QRY),
				new Integer(IDocKeys.IDOCPREFWFMT_TYPE_TBL),
				new Integer(IDocKeys.IDOCPREFWFMT_TYPE_FRM) };

		List result = new ArrayList();
		for (int i = 0; i < format.length; i++) {
			List listIdocprefwfmt = ISicresQueries.getIdocprefwfmt(session,
					bookId, userId, format[i]);
			if (listIdocprefwfmt == null || listIdocprefwfmt.isEmpty()) {
				List listIdocdeffmt1 = ISicresQueries.getIdocdeffmt1(session,
						bookId, format[i], 1);
				if (listIdocdeffmt1 == null || listIdocdeffmt1.isEmpty()) {
					List listIdocdeffmt1Aux = ISicresQueries.getIdocdeffmt1(
							session, bookId, format[i], 0);
					result
							.addAll(getListIdocprefwfmt(session, bookId,
									iUserGroupUser, userId, deptId,
									listIdocdeffmt1Aux));
				} else {
					for (Iterator it = listIdocdeffmt1.iterator(); it.hasNext();) {
						Idocfmt fmt = (Idocfmt) it.next();
						Idocprefwfmt idoc = newIdocprefwfmt(fmt, userId);
						result.add(idoc);
					}
				}
			} else {
				Idocprefwfmt idoc = null;
				for (Iterator it = listIdocprefwfmt.iterator(); it.hasNext();) {
					idoc = (Idocprefwfmt) it.next();
					result.add(idoc);
				}
			}

		}

		putIdocToBookInformation(result, bookId, bookInformation);
	}

	protected static AxSf returnFormat(String sessionID, Integer bookID,
			String formatType, String entidad) throws BookException,
			SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			// Es necesario tener el libro abierto para ver su formato de
			// consulta.
			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}

			THashMap bookInformation = (THashMap) cacheBag.get(bookID);
			Idocprefwfmt idocprefwfmt = (Idocprefwfmt) bookInformation
					.get(formatType);
			Idocarchdet idoc = (Idocarchdet) bookInformation
					.get(IDocKeys.IDOCARCHDET_FLD_DEF_ASOBJECT);

			// Recuperamos la metainformacion solicitada
			AxSf axsf = (AxSf) bookInformation.get(AXSF);

			// Recuperamos el formato solicitado.
			Idocfmt format = ISicresQueries.getIdocfmt(session, new Integer(
					idocprefwfmt.getFmtid()));
			axsf.setFormat(format);
			axsf.setLenFields(getLenFields(idoc));

			List extendedFields = null;
			if (axsf instanceof AxSfIn) {
				extendedFields = getExtendedFields(idoc, Keys.EREG_FDR_MATTER);
			} else {
				extendedFields = getExtendedFields(idoc, Keys.SREG_FDR_MATTER);
			}
			for (Iterator it = extendedFields.iterator(); it.hasNext();) {
				Integer id = (Integer) it.next();
				if (!axsf.getProposedExtendedFields().contains(id)) {
					axsf.addProposedExtendedFiels(id);
				}
			}

			HibernateUtil.commitTransaction(tran);
			return axsf;
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to create book query for [" + bookID
					+ "] for the session [" + sessionID + "]", e);
			throw new BookException(
					BookException.ERROR_CANNOT_CREATE_BOOK_FORMAT);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	protected static Map returnCompareBooks(String sessionID, List bookids,
			Integer bookID, String formatType, String entidad)
			throws BookException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			// Es necesario tener el libro abierto para ver su formato de
			// consulta.
			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}

			THashMap bookInformation = (THashMap) cacheBag.get(bookID);

			Idocarchdet idoc = (Idocarchdet) bookInformation
					.get(IDocKeys.IDOCARCHDET_FLD_DEF_ASOBJECT);
			FieldFormat fieldFormat = new FieldFormat(idoc.getDetval());

			Map fieldsNotEqual = getDistintFields(session, bookids, bookID,
					fieldFormat);

			HibernateUtil.commitTransaction(tran);

			return fieldsNotEqual;
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to create book query for [" + bookID
					+ "] for the session [" + sessionID + "]", e);
			throw new BookException(
					BookException.ERROR_CANNOT_CREATE_BOOK_FORMAT);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}


	/***************************************************************************
	 * PRIVATE METHOD
	 **************************************************************************/

	private static boolean isUserPermBook(Session session,
			AuthenticationUser user, Iuserusertype userusertype,
			Idocarchhdr idoccarchhdr, Integer perm, boolean oficAsoc)
			throws HibernateException {
		boolean includeBook = false;
		if (userusertype.getType() == IDocKeys.IUSERUSERTYPE_USER_TYPE_ADMIN) {
			// Si es superadministrador, el libro se devuelve
			includeBook = true;
		} else if (userusertype.getType() == IDocKeys.IUSERUSERTYPE_USER_TYPE_BOOK_ADMIN) {
			// Si es administrador de libro, tenemos que ver si es
			// administrador de este libro en concreto, si lo es se
			// devuelve, sino hay que ver los permisos de este
			// usuario como si fuese un operador
			includeBook = idoccarchhdr.getCrtrid() == user.getId().intValue()
					|| checkScrBookAdmin(session, user.getId(), idoccarchhdr
							.getId());
			if (!includeBook) {
				includeBook = checkDeptPerm(session, idoccarchhdr.getId(), user
						.getDeptid(), perm);
				if (!includeBook) {
					includeBook = checkUserPerm(session, idoccarchhdr.getId(),
							user.getId(), perm);
				}
			}
		} else if (userusertype.getType() == IDocKeys.IUSERUSERTYPE_USER_TYPE_OPERATOR) {
			// Si el usuario no es administrador de libro o
			// superusuario, hay que ver los permisos que tiene,
			// primero como usuario y despues en el departamento
			includeBook = checkUserPerm(session, idoccarchhdr.getId(), user
					.getId(), perm);
			if (!includeBook) {
				includeBook = checkDeptPerm(session, idoccarchhdr.getId(), user
						.getDeptid(), perm);
				if (!includeBook && oficAsoc) {
					includeBook = checkDeptListPerm(session, idoccarchhdr
							.getId(), user.getDeptList(), perm);
				}
			}
			// else {
			// // Si es un usuario de invesdoc, igual que si fuese
			// // operador
			// includeBook = checkDeptPerm(session, idoccarchhdr
			// .getId(), user.getDeptid());
			// if (!includeBook) {
			// includeBook = checkUserPerm(session, idoccarchhdr
			// .getId(), user.getId());
			// }
		}

		return includeBook;
	}

	private static boolean checkScrBookAdmin(Session session, Integer userId,
			Integer bookId) throws HibernateException {
		StringBuffer query = new StringBuffer();
		query.append("FROM ");
		query.append(HIBERNATE_ScrBookadmin);
		query.append(" scr WHERE scr.iduser=? AND scr.idbook=?");
		List list = session.find(query.toString(), new Object[] { userId,
				bookId }, new Type[] { Hibernate.INTEGER, Hibernate.INTEGER });

		return list != null && !list.isEmpty();
	}

	private static boolean checkDeptListPerm(Session session, Integer bookId,
			List deptList) throws HibernateException {
		if (deptList != null && !deptList.isEmpty()) {
			for (Iterator iterator = deptList.iterator(); iterator.hasNext();) {
				Integer deptId = (Integer) iterator.next();

				if (checkDeptPerm(session, bookId, deptId)) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean checkDeptListPerm(Session session, Integer bookId,
			List deptList, Integer perm) throws HibernateException {
		if (perm == null) {
			return checkDeptListPerm(session, bookId, deptList);
		} else {
			if (deptList != null && !deptList.isEmpty()) {
				for (Iterator iterator = deptList.iterator(); iterator
						.hasNext();) {
					Integer deptId = (Integer) iterator.next();

					if (checkDeptPerm(session, bookId, deptId, perm)) {
						return true;
					}
				}
			}
			return false;
		}
	}

	private static boolean checkDeptPerm(Session session, Integer bookId,
			Integer deptId) throws HibernateException {
		StringBuffer query = new StringBuffer();
		query.append("FROM ");
		query.append(HIBERNATE_Iuserobjperm);
		query
				.append(" iuser WHERE iuser.dstid=? AND iuser.objid=? AND iuser.dsttype=");
		query.append(IDocKeys.IUSEROBJPERM_DEPT_TYPE);
		query.append(" AND iuser.aperm=");
		query.append(IDocKeys.IUSEROBJPERM_QUERY_PERM);
		// Consultamos la tabla iuserobjperm con deptId y bookId
		List list = session.find(query.toString(), new Object[] { deptId,
				bookId }, new Type[] { Hibernate.INTEGER, Hibernate.INTEGER });

		// Examinamos si el usuario tiene permisos desde el departamento
		return list != null && !list.isEmpty();
	}

	private static boolean checkDeptPerm(Session session, Integer bookId,
			Integer deptId, Integer perm) throws HibernateException {
		if (perm == null) {
			return checkDeptPerm(session, bookId, deptId);
		} else {
			StringBuffer query = new StringBuffer();
			query.append("FROM ");
			query.append(HIBERNATE_Iuserobjperm);
			query
					.append(" iuser WHERE iuser.dstid=? AND iuser.objid=? AND iuser.dsttype=");
			query.append(IDocKeys.IUSEROBJPERM_DEPT_TYPE);
			query.append(" AND iuser.aperm=");
			query.append(perm.intValue());
			// Consultamos la tabla iuserobjperm con deptId y bookId
			List list = session.find(query.toString(), new Object[] { deptId,
					bookId },
					new Type[] { Hibernate.INTEGER, Hibernate.INTEGER });

			// Examinamos si el usuario tiene permisos desde el departamento
			return list != null && !list.isEmpty();
		}
	}

	private static boolean checkUserPerm(Session session, Integer bookId,
			Integer userId) throws HibernateException {
		StringBuffer query = new StringBuffer();
		query.append("FROM ");
		query.append(HIBERNATE_Iuserobjperm);
		query
				.append(" iuser WHERE iuser.dstid=? AND iuser.objid=? AND iuser.dsttype=");
		query.append(IDocKeys.IUSEROBJPERM_USER_TYPE);
		query.append(" AND iuser.aperm=");
		query.append(IDocKeys.IUSEROBJPERM_QUERY_PERM);
		// Consultamos la tabla iuserobjperm con userId y bookId
		List list = session.find(query.toString(), new Object[] { userId,
				bookId }, new Type[] { Hibernate.INTEGER, Hibernate.INTEGER });

		// Examinamos si el usuario tiene permisos
		return list != null && !list.isEmpty();
	}

	private static boolean checkUserPerm(Session session, Integer bookId,
			Integer userId, Integer perm) throws HibernateException {
		if (perm == null) {
			return checkUserPerm(session, bookId, userId);
		} else {
			StringBuffer query = new StringBuffer();
			query.append("FROM ");
			query.append(HIBERNATE_Iuserobjperm);
			query
					.append(" iuser WHERE iuser.dstid=? AND iuser.objid=? AND iuser.dsttype=");
			query.append(IDocKeys.IUSEROBJPERM_USER_TYPE);
			query.append(" AND iuser.aperm=");
			query.append(perm);
			// Consultamos la tabla iuserobjperm con userId y bookId
			List list = session.find(query.toString(), new Object[] { userId,
					bookId },
					new Type[] { Hibernate.INTEGER, Hibernate.INTEGER });

			// Examinamos si el usuario tiene permisos
			return list != null && !list.isEmpty();
		}
	}

	private static Map getLenFields(Idocarchdet idoc) {
		Map result = new HashMap();
		FieldFormat fieldFormat = new FieldFormat(idoc.getDetval());

		for (Iterator it = fieldFormat.getFlddefs().values().iterator(); it
				.hasNext();) {
			FFldDef fldDef = (FFldDef) it.next();
			int fldid = Integer.parseInt(fldDef.getColname().substring(
					FLD.length(), fldDef.getColname().length()));
			int len = fldDef.getLen();
			result.put(new Integer(fldid), new Integer(len));
		}

		return result;
	}

	private static Map getDistintFields(Session session, List bookids,
			Integer bookID, FieldFormat fieldFormat) throws HibernateException {

		Map fieldsNotEqual = new HashMap();

		for (Iterator iter = bookids.iterator(); iter.hasNext();) {
			Integer idBook = (Integer) iter.next();
			if (idBook.intValue() != bookID.intValue()) {
				List list = ISicresQueries.getIdocarchdet(session, idBook);
				if (list != null && !list.isEmpty()) {
					for (Iterator it = list.iterator(); it.hasNext();) {
						Idocarchdet idocBookIds = (Idocarchdet) it.next();
						if (idocBookIds.getDettype() == IDocKeys.IDOCARCHDET_FLD_DEF) {
							FieldFormat fieldFormatCompare = new FieldFormat(
									idocBookIds.getDetval());
							Map fldDefInitial = fieldFormat.getFlddefs();
							for (Iterator it1 = fldDefInitial.keySet()
									.iterator(); it1.hasNext();) {
								Integer key = (Integer) it1.next();
								FFldDef flddef = (FFldDef) fldDefInitial
										.get(key);
								Map fldDefCompare = fieldFormatCompare
										.getFlddefs();
								if (!fldDefCompare.containsKey(key)) {
									fieldsNotEqual.put("FLD" + flddef.getId(),
											new Integer(flddef.getId()));
								}
							}
						}
					}
				}
			}
		}

		return fieldsNotEqual;
	}

	private static List getListIdocprefwfmt(Session session, Integer bookId,
			List iUserGroupUser, Integer userId, Integer deptId,
			List listIdocdeffmt1) throws HibernateException {
		List result = new ArrayList();
		for (Iterator it = listIdocdeffmt1.iterator(); it.hasNext();) {
			boolean isFromDept = false;
			boolean isFromUser = false;
			boolean isFromGroup = false;
			Idocfmt fmt = (Idocfmt) it.next();
			List listIuserObjHdr = ISicresQueries.getIuserObjHdr(session,
					bookId, fmt.getId());
			if (listIuserObjHdr != null && !listIuserObjHdr.isEmpty()) {
				Iuserobjhdr iobjhdr = (Iuserobjhdr) listIuserObjHdr.get(0);
				List listIuserobjpermForDeptFormat = ISicresQueries
						.getIuserobjpermForDeptFormat(session, new Integer(
								iobjhdr.getId()), deptId);
				if (listIuserobjpermForDeptFormat == null
						|| listIuserobjpermForDeptFormat.isEmpty()) {
					List listIuserobjpermForUserFormat = ISicresQueries
							.getIuserobjpermForUserFormat(session, new Integer(
									iobjhdr.getId()), userId);
					if (listIuserobjpermForUserFormat == null
							|| listIuserobjpermForUserFormat.isEmpty()) {
						isFromGroup = isFromGroup(session, iUserGroupUser,
								new Integer(iobjhdr.getId()));
					} else {
						isFromUser = true;
					}
				} else {
					isFromDept = true;
				}
				if (isFromDept || isFromUser || isFromGroup) {
					Idocprefwfmt idoc = newIdocprefwfmt(fmt, userId);
					result.add(idoc);
				}
			}
		}

		return result;
	}

	private static boolean isFromGroup(Session session, List iUserGroupUser,
			Integer iObjHdr) throws HibernateException {
		for (int i1 = 0; i1 < iUserGroupUser.size(); i1++) {
			Integer idGroup = new Integer(((Iusergroupuser) iUserGroupUser
					.get(i1)).getGroupid());
			List listIuserobjpermForGroupFormat = ISicresQueries
					.getIuserobjpermForGroupFormat(session, iObjHdr, idGroup);
			if (listIuserobjpermForGroupFormat != null
					&& !listIuserobjpermForGroupFormat.isEmpty()) {
				return true;
			}
		}

		return false;
	}

	private static Idocprefwfmt newIdocprefwfmt(Idocfmt fmt, Integer userId) {
		Idocprefwfmt idoc = new Idocprefwfmt();
		idoc.setArchid(fmt.getArchid());
		idoc.setFmtid(fmt.getId().intValue());
		idoc.setFmttype(fmt.getType());
		idoc.setUserid(userId.intValue());

		return idoc;
	}

	private static void putIdocToBookInformation(List result, Integer bookId,
			Map bookInformation) throws BookException {
		if (result != null) {
			int size = 0;
			int sizeQry = 0;
			int sizeTbl = 0;
			int sizeFrm = 0;
			for (Iterator it = result.iterator(); it.hasNext();) {
				Idocprefwfmt idoc = (Idocprefwfmt) it.next();
				if (idoc.getFmttype() == IDocKeys.IDOCPREFWFMT_TYPE_QRY) {
					if (sizeQry == 0) {
						bookInformation.put(
								IDocKeys.IDOCPREFWFMT_TYPE_QRY_ASOBJECT, idoc);
						sizeQry++;
					}
					size++;
				} else if (idoc.getFmttype() == IDocKeys.IDOCPREFWFMT_TYPE_TBL) {
					if (sizeTbl == 0) {
						bookInformation.put(
								IDocKeys.IDOCPREFWFMT_TYPE_TBL_ASOBJECT, idoc);
						sizeTbl++;
					}
					size++;
				} else if (idoc.getFmttype() == IDocKeys.IDOCPREFWFMT_TYPE_FRM) {
					if (sizeFrm == 0) {
						bookInformation.put(
								IDocKeys.IDOCPREFWFMT_TYPE_FRM_ASOBJECT, idoc);
						sizeFrm++;
					}
					size++;
				}
			}
			if (size < 3) {
				log.info("Se han recuperado [" + size
						+ "] formatos para  el libro [" + bookId + "]");
				throw new BookException(
						BookException.ERROR_BOOK_HAS_NOT_FORMATS);
			}
		} else {
			int size = 0;
			if (result != null) {
				size = result.size();
			}
			log.info("Se han recuperado [" + size
					+ "] formatos para el libro [" + bookId + "]");
			throw new BookException(BookException.ERROR_BOOK_HAS_NOT_FORMATS);
		}

	}

}
