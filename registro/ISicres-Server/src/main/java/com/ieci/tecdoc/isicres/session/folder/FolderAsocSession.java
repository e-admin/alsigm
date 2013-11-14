/**
 *
 */
package com.ieci.tecdoc.isicres.session.folder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.entity.AxSfEntity;
import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.DistributionException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.invesdoc.Idocarchhdr;
import com.ieci.tecdoc.common.invesdoc.Iuserusertype;
import com.ieci.tecdoc.common.invesicres.ScrCa;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrOrg;
import com.ieci.tecdoc.common.invesicres.ScrRegasoc;
import com.ieci.tecdoc.common.isicres.AxPK;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.Keys;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.ISOfficesValidator;
import com.ieci.tecdoc.common.utils.ISSubjectsValidator;
import com.ieci.tecdoc.common.utils.ISUnitsValidator;
import com.ieci.tecdoc.common.utils.ISicresQueries;
import com.ieci.tecdoc.common.utils.ISicresSaveQueries;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.Validator;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

/**
 * @author 66575267
 *
 */
public class FolderAsocSession extends FolderSessionUtil implements ServerKeys,
		Keys, HibernateKeys {

	private static final Logger log = Logger.getLogger(FolderAsocSession.class);

	public static Object[] getAsocRegsFdr(String sessionID, Integer bookID,
			int folderID, Locale locale, String entidad) throws BookException,
			SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		Transaction tran = null;
		Object[] result = new Object[3];
		Map axsfPrim, axsfs, idocs;
		result[0] = idocs = new HashMap();
		result[1] = axsfs = new HashMap();
		result[2] = axsfPrim = new HashMap();
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			// Es necesario tener el libro abierto para consultar su contenido.
			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}

			Integer idArchPrim = null;
			Integer idFdrPrim = null;
			List aux = ISicresQueries.getAsocRegsFdrSec(session, bookID,
					folderID);

			if (aux != null && !aux.isEmpty()) {
				ScrRegasoc scr = (ScrRegasoc) aux.get(0);
				idArchPrim = new Integer(scr.getIdArchprim());
				idFdrPrim = new Integer(scr.getIdFdrprim());

				Idocarchhdr idoc = (Idocarchhdr) session.load(
						Idocarchhdr.class, new Integer(scr.getIdArchprim()));
				idocs.put(new Integer(scr.getIdArchprim()), idoc);

				AxPK pk = new AxPK(Integer.toString(scr.getIdArchprim()),
						new Integer(scr.getIdFdrprim()));
				AxSfEntity axSfEntity = new AxSfEntity();
				pk = axSfEntity.findByPrimaryKey(pk, entidad);
				axSfEntity.load(pk, entidad);
				AxSf axsf = axSfEntity.getAxSf(entidad);

				Idocarchdet det = ISicresQueries.getIdocarchdet(
						session, new Integer(scr.getIdArchprim()), new Integer(
								IDocKeys.IDOCARCHDET_FLD_DEF));

				loadAxSf(session, pk, axsf, new HashMap(), det, locale
						.getLanguage(), entidad);
				axsfPrim.put(pk, axsf);
			} else {
				aux = ISicresQueries.getAsocRegsFdrPrim(session, bookID,
						folderID);
				if (aux != null && !aux.isEmpty()) {
					idArchPrim = bookID;
					idFdrPrim = new Integer(folderID);
				}
			}

			if (idArchPrim != null && idFdrPrim != null) {
				aux = ISicresQueries.getAsocRegsFdrPrim(session, idArchPrim,
						idFdrPrim.intValue());
				idocs.putAll(getScrRegsAsocIdocs(session, aux));
				axsfs.putAll(getScrRegsAsocAxSfs(session, aux, locale
						.getLanguage(), entidad));
			}

			HibernateUtil.commitTransaction(tran);

			return result;

		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to close the book [" + bookID
					+ "] and fdrid [" + folderID + "] for the session ["
					+ sessionID + "]", e);
			throw new BookException(BookException.ERROR_CANNOT_FIND_ASOC_FDR);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static List getAsocRegFdr(String sessionID, Integer bookID,
			int folderID, String entidad) throws ValidationException,
			BookException, SessionException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			// Es necesario tener el libro abierto para consultar su contenido.
			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}

			List aux = ISicresQueries.getAsocRegsFdrSec(session, bookID,
					folderID);

			if (aux == null || aux.isEmpty()) {
				aux = ISicresQueries.getAsocRegsFdrPrim(session, bookID,
						folderID);
			}

			HibernateUtil.commitTransaction(tran);

			return aux;
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to close the book [" + bookID
					+ "] and fdrid [" + folderID + "] for the session ["
					+ sessionID + "]", e);
			throw new BookException(BookException.ERROR_CANNOT_FIND_ASOC_FDR);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static boolean isAsocRegsFdr(String sessionID, Integer bookID,
			int folderID, String entidad) throws BookException,
			SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			// Es necesario tener el libro abierto para consultar su contenido.
			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}

			List aux = ISicresQueries.getAsocRegsFdrSec(session, bookID,
					folderID);

			if (aux != null && !aux.isEmpty()) {
				return true;
			} else {
				aux = ISicresQueries.getAsocRegsFdrPrim(session, bookID,
						folderID);
				if (aux != null && !aux.isEmpty()) {
					return true;
				}
			}

			HibernateUtil.commitTransaction(tran);

			return false;
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to close the book [" + bookID
					+ "] and fdrid [" + folderID + "] for the session ["
					+ sessionID + "]", e);
			throw new BookException(BookException.ERROR_CANNOT_FIND_ASOC_FDR);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static Integer validateQueryAsocRegs(String sessionID, String code,
			Integer validation, String entidad) throws DistributionException,
			SessionException, ValidationException {

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Transaction tran = null;
		Integer id = null;
		List privOrgs = null;

		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			Iuserusertype userusertype = (Iuserusertype) cacheBag
					.get(HIBERNATE_Iuserusertype);
			ScrOfic scrofic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);
			if (userusertype.getType() != IDocKeys.IUSERUSERTYPE_USER_TYPE_ADMIN) {
				privOrgs = DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getPrivOrgs(scrofic.getId().intValue(), entidad);
			}
			if (validation.intValue() == 9 || validation.intValue() == 10) {
				try {
					ScrOfic scrOfic = ISOfficesValidator.getOffice(session,
							code);
					id = new Integer(scrOfic.getDeptid());
				} catch (ValidationException e) {
				}
			}
			if (validation.intValue() == 1) {
				try {
					ScrOrg scrOrg = ISUnitsValidator.getUnit(session, code,
							false, privOrgs);
					id = scrOrg.getId();
				} catch (ValidationException e) {
				}
			}
			if (validation.intValue() == 6) {
				try {
					ScrCa scrCa = ISSubjectsValidator.getSubject(session, code,
							false);
					id = scrCa.getId();
				} catch (ValidationException e) {
				}
			}

			HibernateUtil.commitTransaction(tran);
			return id;
		} catch (DistributionException e) {
			HibernateUtil.rollbackTransaction(tran);
			throw e;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to validate distribution query [" + sessionID
					+ "]", e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_ACCEPT_DISTRIBUTION);
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to validate distribution query [" + sessionID
					+ "]", e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_ACCEPT_DISTRIBUTION);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	/**
	 * Insertamos una nueva entrada en la tabla SCR_REGASOC.
	 *
	 * @param sessionID
	 * @param entidad
	 * @param idArchPrim
	 * @param idFdrPrim
	 * @param idArchSec
	 * @param idFdrSec
	 * @throws Exception
	 */
	public static void saveAsocRegFdr(String sessionID, String entidad,
			int idArchPrim, int idFdrPrim, int idArchSec, int idFdrSec)
			throws Exception {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Transaction tran = null;

		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			int assocId = DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getNextIdForScrRegAsoc(user.getId(), entidad);

			ISicresSaveQueries.saveScrRegAsoc(session, assocId, idArchPrim,
					idArchSec, idFdrPrim, idFdrSec);

			HibernateUtil.commitTransaction(tran);
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction(tran);
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static void deleteAsocRegFdr(String sessionID, String entidad,
			ScrRegasoc scrRegasoc) throws Exception {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Transaction tran = null;

		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			session.delete(scrRegasoc);

			HibernateUtil.commitTransaction(tran);
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction(tran);
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	private static Map getScrRegsAsocIdocs(Session session, List scrRegsAsoc)
			throws HibernateException {
		Map idocs = new HashMap();
		for (Iterator it = scrRegsAsoc.iterator(); it.hasNext();) {
			ScrRegasoc scr = (ScrRegasoc) it.next();

			Idocarchhdr idoc = (Idocarchhdr) session.load(Idocarchhdr.class,
					new Integer(scr.getIdArchsec()));
			idocs.put(new Integer(scr.getIdArchsec()), idoc);
		}

		return idocs;
	}

	private static Map getScrRegsAsocAxSfs(Session session, List scrRegsAsoc,
			String language, String entidad) throws Exception {
		Map axsfs = new HashMap();
		for (Iterator it = scrRegsAsoc.iterator(); it.hasNext();) {
			ScrRegasoc scr = (ScrRegasoc) it.next();

			AxPK pk = new AxPK(Integer.toString(scr.getIdArchsec()),
					new Integer(scr.getIdFdrsec()));

			AxSfEntity axSfEntity = new AxSfEntity();
			pk = axSfEntity.findByPrimaryKey(pk, entidad);
			axSfEntity.load(pk, entidad);
			AxSf axsf = axSfEntity.getAxSf(entidad);

			Idocarchdet det = ISicresQueries.getIdocarchdet(
					session, new Integer(scr.getIdArchprim()), new Integer(
							IDocKeys.IDOCARCHDET_FLD_DEF));

			loadAxSf(session, pk, axsf, new HashMap(), det, language, entidad);
			axsfs.put(pk, axsf);
		}

		return axsfs;
	}
}
