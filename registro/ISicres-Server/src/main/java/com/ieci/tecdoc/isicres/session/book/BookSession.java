package com.ieci.tecdoc.isicres.session.book;

import gnu.trove.THashMap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import org.dom4j.Document;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.conf.FieldConf;
import com.ieci.tecdoc.common.conf.InvesicresConf;
import com.ieci.tecdoc.common.conf.UserConf;
import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.invesdoc.Idocarchhdr;
import com.ieci.tecdoc.common.invesdoc.Iuserusertype;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrOrg;
import com.ieci.tecdoc.common.invesicres.ScrRegstate;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.Keys;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.keys.ISicresKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.BBDDUtils;
import com.ieci.tecdoc.common.utils.EntityByLanguage;
import com.ieci.tecdoc.common.utils.ISUnitsValidator;
import com.ieci.tecdoc.common.utils.ISicresAPerms;
import com.ieci.tecdoc.common.utils.ISicresGenPerms;
import com.ieci.tecdoc.common.utils.ISicresQueries;
import com.ieci.tecdoc.common.utils.ISicresSaveQueries;
import com.ieci.tecdoc.common.utils.Repository;
import com.ieci.tecdoc.common.utils.ScrRegStateByLanguage;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FieldFormat;
import com.ieci.tecdoc.idoc.utils.ConfiguratorInvesicres;
import com.ieci.tecdoc.idoc.utils.ConfiguratorUser;
import com.ieci.tecdoc.isicres.session.folder.FolderSessionUtil;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.Validator;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

/**
 * @author lmvicente
 * @version
 * @since
 * @creationDate 24-mar-2004
 */

public class BookSession extends BookSessionUtil implements ServerKeys, Keys,
		HibernateKeys {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private static final Logger log = Logger.getLogger(BookSession.class);

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/***************************************************************************
	 * Public methods
	 **************************************************************************/

	public static List getInBooks(String sessionID, Locale locale,
			String entidad) throws BookException, SessionException,
			ValidationException {
		return getBooks(sessionID, ISicresKeys.SCRREGSTATE_IN_BOOK, false,
				locale, entidad);
	}

	public static List getInBooks(String sessionID, boolean oficAsoc,
			Locale locale, String entidad) throws BookException,
			SessionException, ValidationException {
		return getBooks(sessionID, ISicresKeys.SCRREGSTATE_IN_BOOK, oficAsoc,
				locale, entidad);
	}

	public static List getInBooks(String sessionID, boolean oficAsoc, int perm,
			Locale locale, String entidad) throws BookException,
			SessionException, ValidationException {
		return getBooks(sessionID, ISicresKeys.SCRREGSTATE_IN_BOOK, oficAsoc,
				new Integer(perm), locale, entidad);
	}

	public static List getOutBooks(String sessionID, Locale locale,
			String entidad) throws BookException, SessionException,
			ValidationException {
		return getBooks(sessionID, ISicresKeys.SCRREGSTATE_OUT_BOOK, false,
				locale, entidad);
	}

	public static List getOutBooks(String sessionID, boolean oficAsoc,
			Locale locale, String entidad) throws BookException,
			SessionException, ValidationException {
		return getBooks(sessionID, ISicresKeys.SCRREGSTATE_OUT_BOOK, oficAsoc,
				locale, entidad);
	}

	public static List getOutBooks(String sessionID, boolean oficAsoc,
			int perm, Locale locale, String entidad) throws BookException,
			SessionException, ValidationException {
		return getBooks(sessionID, ISicresKeys.SCRREGSTATE_OUT_BOOK, oficAsoc,
				new Integer(perm), locale, entidad);
	}

	/**
	 * Obtenemos la lista de libros que estan abiertos en función del tipo de
	 * libro
	 *
	 * @param sessionID
	 * @param bookType:
	 *            0: Libros de entrada y salida; 1: Libros de entrada; 2: Libros
	 *            de salida
	 * @param entidad
	 * @return
	 * @throws SessionException
	 * @throws BookException
	 * @throws ValidationException
	 */
	public static List getBooksOpenByType(String sessionID, int bookType,
			String entidad) throws SessionException, BookException,
			ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		List books = new ArrayList();
		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos los libros de entrada o de salida
			StringBuffer query = new StringBuffer();
			query.append("FROM ");
			query.append(HIBERNATE_ScrRegstate);
			query.append(" scr WHERE scr.state=0");
			if (bookType == 0) {
				query.append(" and scr.idocarchhdr.type!=?");
			} else {
				query.append(" and scr.idocarchhdr.type=?");
			}

			Iterator results = session.iterate(query.toString(), new Integer(
					bookType), Hibernate.INTEGER);
			while (results.hasNext()) {
				ScrRegstate scrregstate = (ScrRegstate) results.next();
				Idocarchhdr idoccarchhdr = scrregstate.getIdocarchhdr();

				// Guardamos si el libro es de entrada o se salida
				Repository.getInstance(entidad).setProperty(
						String.valueOf(idoccarchhdr.getId()),
						new Integer(idoccarchhdr.getType()));

				books.add(scrregstate);
			}

			HibernateUtil.commitTransaction(tran);
		} catch (Exception e) {
			// Por aqui pasa la HibernateException tambien.
			HibernateUtil.rollbackTransaction(tran);
			log.error("Imposible recuperar los libros ", e);
			throw new BookException(BookException.ERROR_CANNOT_OPEN_BOOK);
		} finally {
			HibernateUtil.closeSession(entidad);
		}

		return books;
	}

	public static List getInOutBooksOpen(String sessionID, Locale locale,
			String entidad) throws BookException, SessionException,
			ValidationException {
		List bookList = new ArrayList();

		List bookInList = BookSession.getInBooks(sessionID, locale, entidad);
		List bookOutList = BookSession.getOutBooks(sessionID, locale, entidad);

		if (bookInList != null && !bookInList.isEmpty()) {
			for (Iterator iterator = bookInList.iterator(); iterator.hasNext();) {
				// ScrRegstate book = (ScrRegstate) iterator.next();
				// if (book.getState() == 0) {
				// bookList.add(book);
				// }
				ScrRegStateByLanguage book = (ScrRegStateByLanguage) iterator
						.next();

				if (book.getScrregstateState() == 0) {
					bookList.add(book);
				}
			}
		}

		if (bookOutList != null && !bookOutList.isEmpty()) {
			for (Iterator iterator = bookOutList.iterator(); iterator.hasNext();) {
				// ScrRegstate book = (ScrRegstate) iterator.next();
				// if (book.getState() == 0) {
				// bookList.add(book);
				// }
				ScrRegStateByLanguage book = (ScrRegStateByLanguage) iterator
						.next();

				if (book.getScrregstateState() == 0) {
					bookList.add(book);
				}
			}
		}

		return bookList;
	}

	public static List getInBooksOpen(String sessionID, Locale locale,
			String entidad) throws BookException, SessionException,
			ValidationException {
		List bookList = new ArrayList();

		List bookInList = BookSession.getInBooks(sessionID, locale, entidad);

		if (bookInList != null && !bookInList.isEmpty()) {
			for (Iterator iterator = bookInList.iterator(); iterator.hasNext();) {
				ScrRegStateByLanguage book = (ScrRegStateByLanguage) iterator
						.next();

				if (book.getScrregstateState() == 0) {
					bookList.add(book);
				}
			}
		}

		return bookList;
	}

	public static List getOutBooksOpen(String sessionID, Locale locale,
			String entidad) throws BookException, SessionException,
			ValidationException {
		List bookList = new ArrayList();

		List bookOutList = BookSession.getOutBooks(sessionID, locale, entidad);

		if (bookOutList != null && !bookOutList.isEmpty()) {
			for (Iterator iterator = bookOutList.iterator(); iterator.hasNext();) {
				ScrRegStateByLanguage book = (ScrRegStateByLanguage) iterator
						.next();

				if (book.getScrregstateState() == 0) {
					bookList.add(book);
				}
			}
		}

		return bookList;
	}

	public static AxSf getQueryFormat(String sessionID, Integer bookID,
			String entidad) throws BookException, SessionException,
			ValidationException {
		return returnFormat(sessionID, bookID,
				IDocKeys.IDOCPREFWFMT_TYPE_QRY_ASOBJECT, entidad);
	}

	public static AxSf getTableFormat(String sessionID, Integer bookID,
			String entidad) throws BookException, SessionException,
			ValidationException {
		return returnFormat(sessionID, bookID,
				IDocKeys.IDOCPREFWFMT_TYPE_TBL_ASOBJECT, entidad);
	}

	public static AxSf getFormFormat(String sessionID, Integer bookID,
			String entidad) throws BookException, SessionException,
			ValidationException {
		return returnFormat(sessionID, bookID,
				IDocKeys.IDOCPREFWFMT_TYPE_FRM_ASOBJECT, entidad);
	}

	public static Map compareBooks(String sessionID, List bookids,
			Integer bookID, String entidad) throws BookException,
			SessionException, ValidationException {
		return returnCompareBooks(sessionID, bookids, bookID,
				IDocKeys.IDOCPREFWFMT_TYPE_QRY_ASOBJECT, entidad);
	}

	public static boolean isInBook(String sessionID, Integer bookID,
			String entidad) throws BookException, SessionException,
			ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		try {
			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}

			return Repository.getInstance(entidad).isInBook(bookID)
					.booleanValue();
		} catch (BookException sE) {
			throw sE;
		} catch (SessionException sE) {
			throw sE;
		} catch (Exception e) {
			log.error("Impossible to know if is in book [" + bookID
					+ "] for the session [" + sessionID + "]", e);
			throw new BookException(BookException.ERROR_CANNOT_OPEN_BOOK);
		}
	}

	public static void openBook(String sessionID, Integer bookID, String entidad)
			throws BookException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		Transaction tran = null;

		try {
			Session session = HibernateUtil.currentSession(entidad);
			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			tran = session.beginTransaction();

			// Vamos a introducir la informacion de un libro en la sesion,
			// siempre que ese libro no haya sido abierto con anterioridad.
			if (!cacheBag.containsKey(bookID)) {
				AuthenticationUser user = (AuthenticationUser) cacheBag
						.get(HIBERNATE_Iuseruserhdr);
				Iuserusertype userusertype = (Iuserusertype) cacheBag
						.get(HIBERNATE_Iuserusertype);
				ScrOfic scrofic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);
				ScrRegstate scrRegstate = ISicresQueries.getScrRegstate(
						session, bookID);
				ISicresAPerms aPerms = new ISicresAPerms();
				List iUserGroupUser = ISicresQueries.getIUserGroupUser(session,
						user.getId());

				// Creamos un map para meter la informacion del libro dentro de
				// la sesion bajo el identificador de libro
				THashMap bookInformation = new THashMap(10);

				// Recuperamos informacion de permisos y la introducimos en la
				// informacion de libro
				bookInformation.put(HIBERNATE_ScrRegstate, scrRegstate);

				// Guardamos si el libro es de entrada o se salida
				Repository.getInstance(entidad).setProperty(
						String.valueOf(scrRegstate.getIdocarchhdr().getId()),
						new Integer(scrRegstate.getIdocarchhdr().getType()));

				getAPerms(session, bookID, user.getId(),
						userusertype.getType(), user.getDeptid(), aPerms,
						scrRegstate, scrofic);

				bookInformation.put(APERMS_USER, aPerms);

				if (!aPerms.isBookAdmin()) {
					getQueryFilter(session, bookInformation, bookID, user
							.getId(), scrofic);
				}

				// Introducimos en la informacion del libro el detalle del libro
				getIdocarchdet(session, bookID, bookInformation);

				// Cargamos los formatos de libro
				getIdocprefwfmt1(session, bookID, user.getId(), user
						.getDeptid(), iUserGroupUser, bookInformation);

				// Cargamos la información sobre la estructura
				AxSf axsf = BBDDUtils.getTableSchemaFromDatabase(bookID
						.toString(), entidad);
				bookInformation.put(AXSF, axsf);

				// Introducimos la informacion de libro en la sesion del usuario
				cacheBag.put(bookID, bookInformation);
			}

			HibernateUtil.commitTransaction(tran);
		} catch (BookException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to open the book [" + bookID
					+ "] for the session [" + sessionID + "]", e);
			throw new BookException(BookException.ERROR_CANNOT_OPEN_BOOK);
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to open the book [" + bookID
					+ "] for the session [" + sessionID + "]", e);
			throw new BookException(BookException.ERROR_CANNOT_OPEN_BOOK);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static void closeBook(String sessionID, Integer bookID)
			throws BookException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		try {
			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			// Borramos de la sesion la informacion de libro
			cacheBag.remove(sessionID);
		} catch (SessionException sE) {
			throw sE;
		} catch (Exception e) {
			log.error("Impossible to close the book [" + bookID
					+ "] for the session [" + sessionID + "]", e);
			throw new BookException(BookException.ERROR_CANNOT_CLOSE_BOOK);
		}
	}

	public static ScrRegstate getBook(String sessionID, Integer bookID)
			throws BookException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		ScrRegstate scrregstate = null;
		try {
			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			// Es necesario tener el libro abierto para consultar su contenido.
			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}

			THashMap bookInformation = (THashMap) cacheBag.get(bookID);
			scrregstate = (ScrRegstate) bookInformation
					.get(HIBERNATE_ScrRegstate);

			return scrregstate;
		} catch (BookException bE) {
			throw bE;
		} catch (SessionException sE) {
			throw sE;
		} catch (Exception e) {
			log.error("Impossible to load the book  for [" + bookID
					+ "] for the session [" + sessionID + "]", e);
			throw new BookException(BookException.ERROR_CANNOT_FIND_REGISTERS);
		}
	}

	public static String getBookName(String sessionID, Integer bookID,
			Locale locale, String entidad) throws BookException,
			SessionException, ValidationException {

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);
		String bookName = "";

		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}

			THashMap bookInformation = (THashMap) cacheBag.get(bookID);
			ScrRegstate scrregstate = (ScrRegstate) bookInformation
					.get(HIBERNATE_ScrRegstate);
			// bookName = scrregstate.getIdocarchhdr().getName();
			if (!locale.getLanguage().equals("es")) {
				bookName = DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getDescriptionByLocale(
								scrregstate.getIdocarchhdr().getId(), false,
								false, locale.getLanguage(),
								EntityByLanguage.getTableName(22), entidad);
			} else {
				bookName = scrregstate.getIdocarchhdr().getName();
			}

			HibernateUtil.commitTransaction(tran);
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Imposible cargar el libro [" + bookID
					+ "] para la sesión [" + sessionID + "]", e);
			throw new BookException(BookException.ERROR_CANNOT_FIND_REGISTERS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}

		return bookName;
	}

	public static Idocarchdet getIdocarchdetMisc(String sessionID,
			Integer bookID) throws BookException, SessionException,
			ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		Idocarchdet idocarchdet = null;
		try {
			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			// Es necesario tener el libro abierto para consultar su contenido.
			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}

			THashMap bookInformation = (THashMap) cacheBag.get(bookID);
			idocarchdet = (Idocarchdet) bookInformation
					.get(IDocKeys.IDOCARCHDET_MISC_ASOBJECT);

			return idocarchdet;
		} catch (BookException bE) {
			throw bE;
		} catch (SessionException sE) {
			throw sE;
		} catch (Exception e) {
			log.error("Impossible to load the book  for [" + bookID
					+ "] for the session [" + sessionID + "]", e);
			throw new BookException(BookException.ERROR_CANNOT_FIND_IDOCARCHDET);
		}
	}

	public static Idocarchdet getIdocarchdetFld(String sessionID, Integer bookID)
			throws BookException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		Idocarchdet idocarchdet = null;
		try {
			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			// Es necesario tener el libro abierto para consultar su contenido.
			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}

			THashMap bookInformation = (THashMap) cacheBag.get(bookID);
			idocarchdet = (Idocarchdet) bookInformation
					.get(IDocKeys.IDOCARCHDET_FLD_DEF_ASOBJECT);

			return idocarchdet;
		} catch (BookException bE) {
			throw bE;
		} catch (SessionException sE) {
			throw sE;
		} catch (Exception e) {
			log.error("Impossible to load the book  for [" + bookID
					+ "] for the session [" + sessionID + "]", e);
			throw new BookException(BookException.ERROR_CANNOT_FIND_IDOCARCHDET);
		}
	}

	public static String getPersistFields(String sessionID, Integer bookId,
			AxSf axsf, Locale locale, String entidad) throws BookException,
			SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookId, ValidationException.ATTRIBUTE_BOOK);

		// Transaction tran = null;
		String fields = "";
		try {
			UserConf usrConf = getUserConfig(sessionID, bookId, axsf, true,
					locale, entidad);
			List list = null;
			if (usrConf != null) {
				list = usrConf.getFieldConf();
			}
			if (list != null && !list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					FieldConf fieldConf = (FieldConf) list.get(i);
					if (fieldConf.getFieldCheck() == 1) {
						fields = fields + fieldConf.getFieldId() + ";";
					}
				}
			} else {
				fields = "";
			}
		} catch (BookException bE) {
			// HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			// HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			// HibernateUtil.rollbackTransaction(tran);
			log.error(
					"Impossible to save the persisted fields for the session ["
							+ sessionID + "]", e);
			throw new BookException(
					BookException.ERROR_CANNOT_SAVE_PERSIST_FIELDS);
		}
		return fields;
	}

	public static void savePersistFields(String sessionID, String cadena,
			String entidad) throws BookException, SessionException,
			ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		Transaction tran = null;
		Integer userPersistFields = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			userPersistFields = user.getId();

			StringBuffer query = new StringBuffer();
			query.append("FROM ");
			query.append(HIBERNATE_ScrUserconfig);
			query.append(" scr WHERE scr.userid=?");
			List userConfigList = session.find(query.toString(),
					new Object[] { user.getId() },
					new Type[] { Hibernate.INTEGER });

			if (userConfigList != null && !userConfigList.isEmpty()) {
				DBEntityDAOFactory.getCurrentDBEntityDAO().deleteUserConfig(
						user.getId().intValue(), entidad);
			}
			ISicresSaveQueries.saveScrUserconfig(session, userPersistFields,
					cadena);

			HibernateUtil.commitTransaction(tran);
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to save the persisted fields for the user ["
					+ userPersistFields.intValue() + "] for the session ["
					+ sessionID + "]", e);
			throw new BookException(
					BookException.ERROR_CANNOT_SAVE_PERSIST_FIELDS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static ScrOrg preUpdateFieldOrg(String sessionID, Integer bookID,
			String newCode, List registers, String entidad)
			throws BookException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		Transaction tran = null;
		List privOrgs = null;
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
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			ScrOfic scrOfic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);
			Iuserusertype userusertype = (Iuserusertype) cacheBag
					.get(HIBERNATE_Iuserusertype);
			if (userusertype.getType() != IDocKeys.IUSERUSERTYPE_USER_TYPE_ADMIN) {
				privOrgs = DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getPrivOrgs(scrOfic.getId().intValue(), entidad);
			}

			ScrOrg scrOrg = null;
			try {
				scrOrg = ISUnitsValidator.getUnit(session, newCode, true,
						privOrgs);
			} catch (ValidationException e) {
				log.error("There is no ScrOrg for the code [" + newCode + "]",
						e);
				throw new BookException(
						BookException.ERROR_UDPATEFIELDS_NOT_VALID_CODE);
			}

			for (Iterator it = registers.iterator(); it.hasNext();) {
				Integer folderID = (Integer) it.next();
				if (!lock(session, bookID, folderID.intValue(), user, scrOfic,
						entidad)) {
					throw new BookException(
							BookException.ERROR_CANNOT_LOCK_FOLDER);
				}
			}

			HibernateUtil.commitTransaction(tran);

			return scrOrg;
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to update a folder for the session ["
					+ sessionID + "] and bookID [" + bookID + "]", e);
			throw new BookException(BookException.ERROR_UPDATE_FOLDER);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static List postUpdateFieldOrg(String sessionID, Integer bookID,
			List registers, Locale locale, String entidad) throws BookException,
			SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		List result = new ArrayList();
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
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);

			for (Iterator it = registers.iterator(); it.hasNext();) {
				Integer folderID = (Integer) it.next();
				unlock(session, bookID, folderID.intValue(), user);
			}

			for (Iterator it = registers.iterator(); it.hasNext();) {
				Integer folderID = (Integer) it.next();
				AxSf axsf = FolderSessionUtil.getAxSf(session, bookID,
						folderID, null, locale.getLanguage(), entidad, false);
				Object object = axsf.getAttributeValue("fld6");
				if (object != null && object instanceof BigDecimal) {
					if (((BigDecimal) object).intValue() == 0) {
						result.add(folderID);
					}
				} else if (object != null && object instanceof Integer) {
					if (((Integer) object).intValue() == 0) {
						result.add(folderID);
					}
				}
			}

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to update a folder for the session ["
					+ sessionID + "] and bookID [" + bookID + "]", e);
			throw new BookException(BookException.ERROR_UPDATE_FOLDER);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static InvesicresConf invesicresConf(String entidad) {
		// Obtiene la información de configuración de invesicres
		InvesicresConf result = null;
		try {
			result = ConfiguratorInvesicres.getInstance(entidad)
					.getInvesicresConf();
		} catch (Exception e) {
			log.error("Impossible to load invesicres configuration", e);
		}
		return result;
	}

	/**
	 *
	 * Metodo que obtiene la configuracion de un usuario
	 *
	 * Se incluye un parametro para saber si estamos creando o editando un
	 * registro
	 *
	 * @param sessionID
	 * @param bookId
	 * @param axsf
	 * @param query
	 * @param locale
	 * @param entidad
	 * @param nuevoRegistro
	 *            - parametro para saber si estamos creando o editando un
	 *            registro
	 * @return
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static UserConf getUserConfig(String sessionID, Integer bookId,
			AxSf axsf, boolean query, Locale locale, String entidad,
			boolean nuevoRegistro) throws BookException, SessionException,
			ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookId, ValidationException.ATTRIBUTE_BOOK);

		Transaction tran = null;
		UserConf usrConf = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			THashMap bookInformation = (THashMap) cacheBag.get(bookId);
			Idocarchdet idoc = (Idocarchdet) bookInformation
					.get(IDocKeys.IDOCARCHDET_FLD_DEF_ASOBJECT);
			FieldFormat fieldFormat = new FieldFormat(idoc.getDetval());
			ISicresAPerms aPerms = (ISicresAPerms) bookInformation
					.get(APERMS_USER);

			boolean updateRegDate = false;
			boolean introRegDate = false;
			if (aPerms.isBookAdmin()) {
				updateRegDate = true;
				introRegDate = true;
			} else {
				ISicresGenPerms genPerms = (ISicresGenPerms) cacheBag
						.get(GENPERMS_USER);
				updateRegDate = genPerms.canUpdateRegDate();
				introRegDate = genPerms.canIntroRegDate();
			}

			ConfiguratorUser configuratorUser = new ConfiguratorUser();
			usrConf = configuratorUser.getUsrConf(user, entidad);
			if (nuevoRegistro) {
				usrConf = configuratorUser.getUserConfig(usrConf, bookId, axsf,
						introRegDate, fieldFormat.getFlddefs(), false, query,
						locale);
			} else {
				usrConf = configuratorUser.getUserConfig(usrConf, bookId, axsf,
						updateRegDate, fieldFormat.getFlddefs(), false, query,
						locale);
			}
			HibernateUtil.commitTransaction(tran);
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			log.error(
					"Impossible load the user configuration for the session ["
							+ sessionID + "]", e);
			throw new BookException(
					BookException.ERROR_CANNOT_LOAD_PERSIST_FIELDS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
		return usrConf;
	}

	/**
	 * Metodo que obtiene la configuracion de un usuario
	 * @param sessionID
	 * @param bookId
	 * @param axsf
	 * @param query
	 * @param locale
	 * @param entidad
	 * @return
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static UserConf getUserConfig(String sessionID, Integer bookId,
			AxSf axsf, boolean query, Locale locale, String entidad)
			throws BookException, SessionException, ValidationException {
		return getUserConfig(sessionID, bookId, axsf, query, locale, entidad,
				false);
	}

	/**
	 * Método que nos devuelve las unidades administrativas que recuera el
	 * usuario en funcion del libro al que se está accediendo
	 *
	 * @param sessionID
	 * @param bookId
	 * @param locale
	 * @param entidad
	 * @return
	 * @throws ValidationException
	 * @throws BookException
	 * @throws SessionException
	 */
	public static Map getUserRememberUnitSelected(String sessionID,
			Integer bookId, Locale locale, String entidad)
			throws ValidationException, BookException, SessionException {
		AxSf axsfQ = BookSession.getFormFormat(sessionID, bookId, entidad);

		UserConf userConf = BookSession.getUserConfig(sessionID, bookId, axsfQ,
				false, locale, entidad);

		if (userConf.getRememberLastSelectedUnit() == 0){
			return null;
		}

		if (userConf == null || userConf.getSelectUnitInBook() == null
				|| userConf.getSelectUnitInBook().isEmpty()) {
			return null;
		}

		return userConf.getSelectUnitInBook();
	}

	/**
	 * Guardamos la configuracion del usuario que vemos en el cuadro de
	 * configuracion de la pantalla de registro
	 *
	 * @param sessionID
	 * @param bookId
	 * @param fields
	 * @param params
	 * @param axsf
	 * @param locale
	 * @param entidad
	 * @return
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static Document saveUserConfig(String sessionID, Integer bookId,
			String fields, String params, AxSf axsf, Locale locale,
			String entidad) throws BookException, SessionException,
			ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookId, ValidationException.ATTRIBUTE_BOOK);

		Transaction tran = null;
		Document result = null;
		try {
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			THashMap bookInformation = (THashMap) cacheBag.get(bookId);
			Idocarchdet idoc = (Idocarchdet) bookInformation
					.get(IDocKeys.IDOCARCHDET_FLD_DEF_ASOBJECT);
			FieldFormat fieldFormat = new FieldFormat(idoc.getDetval());

			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			UserConf usrConf = new UserConf();
			ConfiguratorUser configuratorUser = new ConfiguratorUser();
			usrConf = configuratorUser.getUsrConf(user, entidad);
			usrConf = configuratorUser.getUserConfig(usrConf, bookId, axsf,
					true, fieldFormat.getFlddefs(), true, false, locale);
			configuratorUser.getUsrConfFieldsChanged(usrConf, fields);
			configuratorUser.getUsrConfParamsChanged(usrConf, params);

			Document document = usrConf.getDoc();
			result = configuratorUser
					.createOrUpdateXMLDocument(usrConf, bookId, false);
			if (document == null) {
				DBEntityDAOFactory.getCurrentDBEntityDAO()
						.saveOrUpdateUserConfig(result.asXML(), user.getId(),
								0, entidad);
			} else {
				DBEntityDAOFactory.getCurrentDBEntityDAO()
						.saveOrUpdateUserConfig(result.asXML(), user.getId(),
								1, entidad);
			}
			HibernateUtil.commitTransaction(tran);

		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error(
					"Impossible to save the user configuration for the session ["
							+ sessionID + "]", e);
			throw new BookException(
					BookException.ERROR_CANNOT_SAVE_PERSIST_FIELDS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
		return result;
	}

	public static Document saveUserConfig(String sessionID, Integer bookId,
			String unitCode, Integer unitType, AxSf axsf, Locale locale,
			String entidad) throws BookException, SessionException,
			ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookId, ValidationException.ATTRIBUTE_BOOK);

		Transaction tran = null;
		Document result = null;
		try {
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			THashMap bookInformation = (THashMap) cacheBag.get(bookId);
			Idocarchdet idoc = (Idocarchdet) bookInformation
					.get(IDocKeys.IDOCARCHDET_FLD_DEF_ASOBJECT);
			FieldFormat fieldFormat = new FieldFormat(idoc.getDetval());

			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			UserConf usrConf = new UserConf();
			ConfiguratorUser configuratorUser = new ConfiguratorUser();
			usrConf = configuratorUser.getUsrConf(user, entidad);
			usrConf = configuratorUser.getUserConfig(usrConf, bookId, axsf,
					true, fieldFormat.getFlddefs(), true, false, locale);
			configuratorUser.getUsrConfselectUnitInBookChanged(usrConf,
					unitCode, unitType);

			if (usrConf.getRememberLastSelectedUnit() == 1) {
				Document document = usrConf.getDoc();
				result = configuratorUser.createOrUpdateXMLDocument(usrConf,
						bookId, true);
				if (document == null) {
					DBEntityDAOFactory.getCurrentDBEntityDAO()
							.saveOrUpdateUserConfig(result.asXML(),
									user.getId(), 0, entidad);
				} else {
					DBEntityDAOFactory.getCurrentDBEntityDAO()
							.saveOrUpdateUserConfig(result.asXML(),
									user.getId(), 1, entidad);
				}
			}
			HibernateUtil.commitTransaction(tran);

		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error(
					"Impossible to save the user configuration for the session ["
							+ sessionID + "]", e);
			throw new BookException(
					BookException.ERROR_CANNOT_SAVE_PERSIST_FIELDS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
		return result;
	}

	// Acciones y comprobaciones previas a realizar antes de poder actualizar
	// varios registros
	public static void preUpdateFields(String sessionID, Integer bookID,
			List registers, String entidad) throws BookException,
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
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			ScrOfic scrOfic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

			Integer folderID = null;
			for (Iterator it = registers.iterator(); it.hasNext();) {
				folderID = (Integer) it.next();
				if (!lock(session, bookID, folderID.intValue(), user, scrOfic,
						entidad)) {
					throw new BookException(
							BookException.ERROR_CANNOT_LOCK_FOLDER);
				}
			}

			HibernateUtil.commitTransaction(tran);

		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to update a folder for the session ["
					+ sessionID + "] and bookID [" + bookID + "]", e);
			throw new BookException(BookException.ERROR_UPDATE_FOLDER);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	// Acciones y comprobaciones previas a realizar antes de poder actualizar
	// varios registros a estado cerrado
	public static void preUpdateRegisterToClose(String sessionID, Integer bookID,
			List registers, String entidad) throws BookException,
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
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			ScrOfic scrOfic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

			Integer folderID = null;
			for (Iterator it = registers.iterator(); it.hasNext();) {
				folderID = (Integer) it.next();
				if (!validateLockForCloseReg(session, bookID, folderID.intValue(), user, scrOfic,
						entidad)) {
					throw new BookException(
							BookException.ERROR_CANNOT_LOCK_FOLDER);
				}
			}

			HibernateUtil.commitTransaction(tran);

		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to update a folder for the session ["
					+ sessionID + "] and bookID [" + bookID + "]", e);
			throw new BookException(BookException.ERROR_UPDATE_FOLDER);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	// Acciones y comprobaciones a realizar después de actualizar varios
	// registros
	public static void postUpdateFields(String sessionID, Integer bookID,
			List registers, String entidad) throws BookException,
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
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);

			for (Iterator it = registers.iterator(); it.hasNext();) {
				Integer folderID = (Integer) it.next();
				unlock(session, bookID, folderID.intValue(), user);
			}

			HibernateUtil.commitTransaction(tran);

		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to update a folder for the session ["
					+ sessionID + "] and bookID [" + bookID + "]", e);
			throw new BookException(BookException.ERROR_UPDATE_FOLDER);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static void saveUserConfigIdOficPref(Integer idUser,
			Integer idOficPref, String entidad) throws Exception {

		try {
			DBEntityDAOFactory.getCurrentDBEntityDAO()
					.updateUserConfigIdOficPref(idUser, idOficPref, entidad);
		} catch (Exception e) {
			log.error(
					"Impossible to save the user configuration for the user ["
							+ idUser + "]", e);
			throw new BookException(
					BookException.ERROR_CANNOT_SAVE_PERSIST_FIELDS);
		}

	}

	/**
	 * Método que nos devuelve la fecha maxima de cierre de registros cerrados
	 *
	 * @param sessionID
	 * @param entidad
	 * @param bookId
	 * @return
	 * @throws BookException
	 */
	public static Date getMaxDateRegClose(String sessionID, String entidad,
			Integer bookId) throws BookException{

		Date date;
		try {

			//obtenemos la oficina de registro
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			ScrOfic scrofic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

			date = BBDDUtils
			.getDateFromTimestamp(DBEntityDAOFactory
					.getCurrentDBEntityDAO().getMaxDateClose(bookId, entidad, scrofic.getId()));

		} catch (Exception e) {
			log.error("Imposible cargar el libro [" + bookId
					+ "] para la sesión [" + sessionID + "]", e);
			throw new BookException(BookException.ERROR_CANNOT_FIND_REGISTERS);
		}

		return date;
	}


}