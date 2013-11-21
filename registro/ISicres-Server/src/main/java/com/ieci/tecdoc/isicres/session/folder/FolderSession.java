/**
 *
 */
package com.ieci.tecdoc.isicres.session.folder;

import gnu.trove.THashMap;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.conf.BookConf;
import com.ieci.tecdoc.common.conf.BookTypeConf;
import com.ieci.tecdoc.common.entity.AxSfEntity;
import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.invesdoc.Iuserusertype;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrRegasoc;
import com.ieci.tecdoc.common.invesicres.ScrRegstate;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfOut;
import com.ieci.tecdoc.common.isicres.AxSfQuery;
import com.ieci.tecdoc.common.isicres.AxSfQueryField;
import com.ieci.tecdoc.common.isicres.AxSfQueryResults;
import com.ieci.tecdoc.common.isicres.Keys;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.keys.ISicresKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.BBDDUtils;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.common.utils.ISUnitsValidator;
import com.ieci.tecdoc.common.utils.ISicresAPerms;
import com.ieci.tecdoc.common.utils.ISicresQueries;
import com.ieci.tecdoc.isicres.audit.helper.ISicresAuditHelper;
import com.ieci.tecdoc.isicres.events.exception.EventException;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.Validator;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

/**
 * @author 66575267
 *
 */
public class FolderSession extends FolderSessionUtil implements ServerKeys,
		Keys, HibernateKeys {

	private static final Logger log = Logger.getLogger(FolderSession.class);

	/***************************************************************************
	 * PUBLIC METHOD
	 **************************************************************************/

	public static void closeRegistersQuery(String sessionID, Integer bookID)
			throws BookException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		try {
			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			// Es necesario tener el libro abierto para consultar su contenido.
			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}
			THashMap bookInformation = (THashMap) cacheBag.get(bookID);
			AxSfQueryResults queryResults = (AxSfQueryResults) bookInformation
					.get(AXSF_QUERY_RESULTS);

			if (queryResults == null) {
				throw new BookException(BookException.ERROR_QUERY_NOT_OPEN);
			} else {
				bookInformation.remove(AXSF_QUERY_RESULTS);
			}
		} catch (SessionException sE) {
			throw sE;
		} catch (BookException bE) {
			throw bE;
		} catch (Exception e) {
			log.error("Impossible to close the book [" + bookID
					+ "] for the session [" + sessionID + "]", e);
			throw new BookException(BookException.ERROR_CANNOT_CLOSE_BOOK);
		}
	}

	public static int getIncompletRegisterSize(String sessionID,
			Integer bookID, String entidad) throws BookException,
			SessionException, ValidationException {

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		Transaction tran = null;
		int size = 0;

		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			ScrOfic scrofic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);
			ScrRegstate scrRegstate = ISicresQueries.getScrRegstate(session,
					bookID);
			if (scrRegstate.getState() == 0 && scrofic != null) {
				size = DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getSizeIncompletRegister(bookID, scrofic.getId(),
								entidad);
			}

			HibernateUtil.commitTransaction(tran);

			return size;
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

	public static AxSfQueryResults getLastRegisterForUser(String sessionID,
			Integer bookID, Locale locale, String entidad)
			throws BookException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

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

			THashMap bookInformation = (THashMap) cacheBag.get(bookID);
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			AxSf axsf = (AxSf) bookInformation.get(AXSF);
			String filter = (String) bookInformation.get(QUERY_FILTER);
			Idocarchdet idoc = (Idocarchdet) bookInformation
					.get(IDocKeys.IDOCARCHDET_FLD_DEF_ASOBJECT);
			AxSfQuery axsfQuery = (AxSfQuery) bookInformation.get(AXSF_QUERY);
			if (axsfQuery != null) {
				bookInformation.remove(AXSF_QUERY);
			}

			StringBuffer buffer = new StringBuffer();
			buffer.append("fld3" + IGUAL + COMILLA);
			if (isDataBaseCaseSensitive(entidad)) {
				buffer.append(user.getName().toUpperCase());
			} else {
				buffer.append(user.getName());
			}
			buffer.append(COMILLA);

			if (filter != null) {
				filter = filter + " AND " + buffer.toString();
			} else {
				filter = buffer.toString();
			}

			Map result = new TreeMap();

			AxSfEntity axSfEntity = new AxSfEntity();
			Collection col = axSfEntity.findLastRegisterForUser(axsf, bookID,
					user.getId(), filter, entidad);

			AxSfQueryResults queryResults = new AxSfQueryResults();
			if (col.size() > 0) {
				extractRegistersFromQuery(col, result, session, idoc,
						axSfEntity, locale.getLanguage(), entidad);

				HibernateUtil.commitTransaction(tran);

				queryResults.setBookId(bookID);
				queryResults.setTotalQuerySize(1);
				queryResults.setPageSize(1);
				queryResults.setCurrentFirstRow(1);
				queryResults.setCurrentLastRow(1);
				queryResults.setCurrentResultsSize(1);
			} else {
				queryResults.setBookId(bookID);
				queryResults.setTotalQuerySize(0);
				queryResults.setPageSize(0);
				queryResults.setCurrentFirstRow(0);
				queryResults.setCurrentLastRow(0);
				queryResults.setCurrentResultsSize(0);
			}

			return queryResults.clone(result.values());
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to execute the book query for [" + bookID
					+ "] for the session [" + sessionID + "]", e);
			throw new BookException(BookException.ERROR_CANNOT_FIND_REGISTERS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static AxSfQueryResults navigateToRowRegistersQuery(
			String sessionID, Integer bookID, int row, Locale locale,
			String entidad, String orderByTable) throws BookException,
			SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		return navigateOrNavigateToRowRegisterQuery(sessionID, bookID, locale,
				entidad, true, row, null, orderByTable);
	}

	public static AxSfQueryResults navigateRegistersQuery(String sessionID,
			Integer bookID, String queryNavigationType, Locale locale,
			String entidad, String orderByTable) throws BookException,
			SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_NotNull(queryNavigationType,
				ValidationException.ATTRIBUTE_NAVIGATIONTYPE);
		validateNavigationType(queryNavigationType);

		return navigateOrNavigateToRowRegisterQuery(sessionID, bookID, locale,
				entidad, false, 0, queryNavigationType, orderByTable);
	}

	public static int openRegistersQuery(String sessionID, AxSfQuery axsfQuery,
			List bookIds, Integer reportOption, String entidad)
			throws BookException, SessionException, ValidationException {

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_NotNull(axsfQuery,
				ValidationException.ATTRIBUTE_AXSFQUERY);
		Validator.validate_Integer(axsfQuery.getBookId(),
				ValidationException.ATTRIBUTE_BOOK);

		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			// Es necesario tener el libro abierto para consultar su contenido.
			if (!cacheBag.containsKey(axsfQuery.getBookId())) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}

			THashMap bookInformation = (THashMap) cacheBag.get(axsfQuery
					.getBookId());
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			Iuserusertype userusertype = (Iuserusertype) cacheBag
					.get(HIBERNATE_Iuserusertype);
			ScrOfic scrofic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

			int totalSize = getOpenRegisterTotalSize(session, bookInformation,
					user, axsfQuery, reportOption, bookIds, userusertype,
					scrofic, entidad);

			if (totalSize > 0) {
				AxSfQueryResults queryResults = new AxSfQueryResults(axsfQuery
						.getBookId(), totalSize, axsfQuery.getPageSize());

				bookInformation.put(AXSF_QUERY_RESULTS, queryResults);
				bookInformation.put(AXSF_QUERY, axsfQuery);
			}

			HibernateUtil.commitTransaction(tran);

			return totalSize;
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to execute the book query for ["
					+ axsfQuery.getBookId() + "] for the session [" + sessionID
					+ "]", e);
			throw new BookException(BookException.ERROR_CANNOT_FIND_REGISTERS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	/**
	 * Metodo que obtiene el numero de registros encontrados que cumplen el criterio pasado como parametro
	 *
	 * @param sessionID
	 * @param axsfQuery - {@link AxSfQuery}
	 * @param bookIds - Array de Libros donde se realiza la busqueda
	 * @param reportOption
	 * @param entidad
	 * @return int - Numero de elementos encontrados que cumplen el criterio de busqueda
	 *
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static int getCountRegistersQuery(String sessionID, AxSfQuery axsfQuery,
			List bookIds, Integer reportOption, String entidad)
			throws BookException, SessionException, ValidationException {

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_NotNull(axsfQuery,
				ValidationException.ATTRIBUTE_AXSFQUERY);
		Validator.validate_Integer(axsfQuery.getBookId(),
				ValidationException.ATTRIBUTE_BOOK);

		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			// Es necesario tener el libro abierto para consultar su contenido.
			if (!cacheBag.containsKey(axsfQuery.getBookId())) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}

			THashMap bookInformation = (THashMap) cacheBag.get(axsfQuery
					.getBookId());
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			Iuserusertype userusertype = (Iuserusertype) cacheBag
					.get(HIBERNATE_Iuserusertype);
			ScrOfic scrofic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

			int totalSize = getOpenRegisterTotalSize(session, bookInformation,
					user, axsfQuery, reportOption, bookIds, userusertype,
					scrofic, entidad);

			HibernateUtil.commitTransaction(tran);

			return totalSize;
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to execute the book query for ["
					+ axsfQuery.getBookId() + "] for the session [" + sessionID
					+ "]", e);
			throw new BookException(BookException.ERROR_CANNOT_FIND_REGISTERS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static boolean lockFolder(String sessionID, Integer bookID,
			int fdrid, String entidad) throws BookException, SessionException,
			ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		boolean result = false;
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

			result = lock(session, bookID, fdrid, user, scrOfic, entidad);

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
			log.error("Impossible to lock the book [" + bookID
					+ "] and fdrid [" + fdrid + "] for the session ["
					+ sessionID + "]", e);
			throw new BookException(BookException.ERROR_CANNOT_LOCK_FOLDER);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	/***************************************************************************
	 * * Obtener los ids de los registros que cumplan las condiciones indicadas
	 * desde la pantalla de relaciones para poder pasarlos al estado CERRADO Los
	 * Registros además sólo podrán ser cerrados si están en estado COMPLETO
	 **************************************************************************/
	public static List getRegistersToClose(String sessionID, Integer bookID,
			Date beginDate, Date endDate, String unit, boolean isTarget,
			String entidad) throws BookException, SessionException,
			ValidationException, SQLException {

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		ArrayList idsRegisters = new ArrayList();
		Statement statement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			// Es necesario tener el libro abierto para consultar su contenido.
			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}

			ScrOfic scrofic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);
			Iuserusertype userusertype = (Iuserusertype) cacheBag
					.get(HIBERNATE_Iuserusertype);



			String query = getRegisterToCloseQuery(session, bookID,
					userusertype, scrofic, beginDate, endDate, unit, isTarget,
					entidad);

			connection = BBDDUtils.getConnection(entidad);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				int fdrid = resultSet.getInt(1); // FDRID_FIELD
				idsRegisters.add(new Integer(fdrid));
			}

		} catch (SQLException e) {
			log.warn("Error ejecutando consulta", e);
			throw e;
		} catch (BookException bE) {
			throw bE;
		} catch (SessionException sE) {
			throw sE;
		} catch (Exception e) {
			log.error("Impossible to getRegistersToClose for the session ["
					+ sessionID + "]", e);
			throw new BookException(
					BookException.ERROR_CANNOT_GET_REGISTERS_TO_CLOSE);
		} finally {
			BBDDUtils.close(statement);
			BBDDUtils.close(resultSet);
			BBDDUtils.close(connection);
			HibernateUtil.closeSession(entidad);
		}

		return idsRegisters;
	}

	/**
	 * Obtenemos un registro
	 *
	 * @param sessionID
	 * @param bookID
	 * @param fdrid
	 * @param entidad
	 * @return
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static AxSf getBookFolder(String sessionID, Integer bookID,
			int fdrid, Locale locale, String entidad) throws BookException,
			SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		if (log.isDebugEnabled()) {
			log.debug("///////// getBookFolder =========================> ["
					+ bookID + "] fdrid [" + fdrid + "]");
		}

		AxSf axsf = null;
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

			THashMap bookInformation = (THashMap) cacheBag.get(bookID);
			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}
			// AxSf axsfP = (AxSf) bookInformation.get(AXSF);
			Idocarchdet idoc = (Idocarchdet) bookInformation
					.get(IDocKeys.IDOCARCHDET_FLD_DEF_ASOBJECT);

			axsf = getAxSf(session, bookID, new Integer(fdrid), idoc, locale
					.getLanguage(), entidad, true);

			HibernateUtil.commitTransaction(tran);

			if (log.isDebugEnabled()) {
				log
						.debug("///////// getBookFolder =======recovered==========> ["
								+ bookID + "] fdrid [" + fdrid + "] " + axsf);
			}

			return axsf;
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to load the book  for [" + bookID
					+ "] and fdrid [" + fdrid + "] for the session ["
					+ sessionID + "]", e);
			throw new BookException(BookException.ERROR_CANNOT_FIND_REGISTERS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	/**
	 *
	 * @param session
	 * @param bookInformation
	 * @param sessionID
	 * @param bookID
	 * @param fdrid
	 * @param locale
	 * @param entidad
	 * @param idoc
	 * @param axSfEntity
	 * @param vldAttribMap
	 * @return
	 * @throws Exception
	 */
	public static AxSf getBookFolder(Session session, THashMap bookInformation,
			String sessionID, Integer bookID, int fdrid, Locale locale,
			String entidad, Idocarchdet idoc, AxSfEntity axSfEntity,
			Map vldAttribMap) throws Exception {

		AxSf axsf = getAxSf(session, bookID, new Integer(fdrid), idoc, locale
				.getLanguage(), entidad, true, axSfEntity, vldAttribMap);

		if (log.isDebugEnabled()) {
			log
					.debug("///////// getBookFolder vldAttribMap =======recovered==========> ["
							+ bookID + "] fdrid [" + fdrid + "] " + axsf);
		}

		return axsf;
	}

	public static int createNewFolder(String sessionID, Integer bookID,
			AxSf axsfNew, List inter, Integer launchDistOutRegister,
			Locale locale, String entidad) throws BookException,
			SessionException, ValidationException {

		FolderDataSession data = crearRegistro(sessionID, bookID, axsfNew,
				inter, -1, launchDistOutRegister, null, locale, entidad, false,
				false, true, null);

		int newRegisterID = 0;
		if (data != null) {
			newRegisterID = data.getNewRegisterID();
		}

		return newRegisterID;
	}

	/**
	 * Creamos un nuevo registro desde la distribucion
	 *
	 * @param sessionID
	 * @param bookID
	 * @param axsfNew
	 * @param assignedRegisterID
	 * @param launchDistOutRegister
	 * @param bookTypeConf
	 * @param entidad
	 * @return
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static int createNewFolderFromDistribution(String sessionID,
			Integer bookID, AxSf axsfNew, List inter, int assignedRegisterID,
			Integer launchDistOutRegister, BookTypeConf bookTypeConf,
			Locale locale, String entidad) throws BookException,
			SessionException, ValidationException {

		boolean distributionRegIn = Configurator
				.getInstance()
				.getPropertyBoolean(
						ConfigurationKeys.KEY_SERVER_DISTRIBUTION_REGISTER_IN_FROM_DISTRIBUTION);

		FolderDataSession data = crearRegistro(sessionID, bookID, axsfNew,
				inter, assignedRegisterID, launchDistOutRegister, bookTypeConf,
				locale, entidad, false, false, distributionRegIn, null);

		int newRegisterID = 0;
		if (data != null) {
			newRegisterID = data.getNewRegisterID();
		}

		return newRegisterID;
	}

	public static FolderDataSession createNewFolderEx(String sessionID,
			Integer bookID, AxSf axsfNew, List inter, int assignedRegisterID,
			Integer launchDistOutRegister, BookTypeConf bookTypeConf,
			Locale locale, String entidad, boolean isConsolidacion,
			boolean isImport, boolean distributeRegInAccepted)
			throws BookException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		FolderDataSession data = new FolderDataSession(true, isImport, axsfNew,
				inter, bookTypeConf, distributeRegInAccepted);

		// Preparación del registro
		data = preparationOfFolder(sessionID, bookID, 0, false,
				launchDistOutRegister, locale.getLanguage(), entidad, data);

		data = getNewIDs(bookID, assignedRegisterID, data, entidad);

		data = createRegister(sessionID, bookID, inter,
				data.getNewRegisterID(), data.getNewAssociatedBookID(), data
						.getNewAssociatedRegisterID(), entidad, data,
				isConsolidacion);

		data = createNewOutputRegister(sessionID, data.getAxsfNew(), data
				.isAutomaticRegisterCreation(), launchDistOutRegister, data,
				locale, entidad);

		return data;
	}

	public static FolderDataSession createFolderWithDocuments(String sessionID,
			Integer bookID, AxSf axsfNew, List inter, Map documents,
			Integer launchDistOutRegister, Locale locale, String entidad,
			boolean consolidacion, boolean isImport) throws BookException,
			SessionException, ValidationException {

//		Transaction tran = null;
		try {
//			Session session = HibernateUtil.currentSession(entidad);
//			tran = session.beginTransaction();

			boolean distributeRegInAccepted = false;
			if(!isImport){
				distributeRegInAccepted = true;
			} else {
				distributeRegInAccepted = Configurator
						.getInstance()
						.getPropertyBoolean(
								ConfigurationKeys.KEY_WS_DISTRIBUTION_AUTODISTRIBUTION_REGISTER_IMPORT);
			}

			FolderDataSession data = crearRegistro(sessionID, bookID,
					axsfNew, inter, -1, launchDistOutRegister, null, locale,
					entidad, consolidacion, isImport, distributeRegInAccepted, documents);

			if (data != null) {
				//Añadimos los documentos al registro
				data.setDocumentsUpdate(updateDocumentsImages(sessionID,
						bookID, data.getUser(), data.getNewRegisterID(),
						documents, data.getAxsfNew(), data.getAxsfNew(), data
								.getScrofic(), locale, entidad));

			}

//			HibernateUtil.commitTransaction(tran);

			return data;
		} catch (BookException bE) {
//			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
//			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (EventException eE){
			throw eE;
		} catch (Exception e) {
//			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to update a folder for the session ["
					+ sessionID + "] and bookID [" + bookID + "]", e);
			throw new BookException(BookException.ERROR_UPDATE_FOLDER);
		} finally {
//			HibernateUtil.closeSession(entidad);
		}
	}

	public static FolderDataSession updateFolder(String sessionID,
			Integer bookID, int fdrid, AxSf axsfNew, List inter, Map documents,
			boolean updateDate, Integer launchDistOutRegister, Locale locale,
			String entidad) throws BookException, SessionException,
			ValidationException {

		return actualizarRegistro(sessionID, bookID, fdrid, axsfNew, inter,
				documents, updateDate, launchDistOutRegister, locale, entidad, null);
	}

	public static FolderDataSession updateFolderEx(String sessionID,
			Integer bookID, int fdrid, AxSf axsfNew, List inter, Map documents,
			boolean updateDate, Integer launchDistOutRegister, Locale locale,
			String entidad, Integer idDistFather) throws BookException, SessionException,
			ValidationException {

		return actualizarRegistro(sessionID, bookID, fdrid, axsfNew, inter,
				documents, updateDate, launchDistOutRegister, locale, entidad, idDistFather);
	}


	private static FolderDataSession actualizarRegistro(String sessionID,
			Integer bookID, int fdrid, AxSf axsfNew, List inter, Map documents,
			boolean updateDate, Integer launchDistOutRegister, Locale locale,
			String entidad, Integer idDistFather) throws ValidationException, BookException,
			SessionException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		if (log.isDebugEnabled()) {
			log.debug("updateFolderPrivate ============> " + bookID + ".."
					+ fdrid);
		}

		//Datos del registro a modificar (obtenemos este dato para la auditoria de cambios)
		AxSf axsfOld = FolderSession.getBookFolder(sessionID,
				bookID, fdrid, locale, entidad);


		FolderDataSession data = new FolderDataSession(false, false, axsfNew,
				inter, documents, true);

		data = preparationOfFolder(sessionID, bookID, fdrid, updateDate,
				launchDistOutRegister, locale.getLanguage(), entidad, data);

		data = getNewIDs(bookID, 0, data, entidad);

		if (log.isDebugEnabled()) {
			log.debug("ASOCIACION AUTOMATICA DE REGISTROS LANZAR ["
					+ data.isAutomaticRegisterCreation() + "] TIPO ["
					+ data.getAutomaticRegisterCreactionType() + "] ID ["
					+ data.getNewAssociatedRegisterID() + "]");
		}

		data = updateRegister(sessionID, bookID, fdrid, inter, data
				.getNewAssociatedBookID(), data.getNewAssociatedRegisterID(),
				entidad, data, idDistFather);

		// Modificación del registro
		updateNewOutputRegister(sessionID, updateDate, launchDistOutRegister,
				data.getNewAssociatedBookID(), inter, data
						.getNewAssociatedRegisterID(), locale, entidad, data);

		// Documentos e imagenes
		data.setDocumentsUpdate(updateDocumentsImages(sessionID, bookID, data
				.getUser(), fdrid, data.getDocumentsUpdate(),
				data.getAxsfNew(), data.getAxsfOld(), data.getScrofic(),
				locale, entidad));

		//Auditoría de cambios del registro
		ISicresAuditHelper.auditarModificacionRegistro(inter, axsfOld, data);

		return data;
	}

	/**
	 * Método para añadir documentos
	 *
	 * @param sessionID
	 * @param bookID
	 * @param folderId
	 * @param documents
	 * @param axsfNew
	 * @param locale
	 * @param entidad
	 * @return
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static FolderDataSession addDocument(String sessionID,
			Integer bookID, Integer folderId, Map documents, AxSf axsfNew,
			Locale locale, String entidad) throws BookException,
			SessionException, ValidationException {

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			FolderDataSession data = new FolderDataSession(false, false,
					axsfNew, null, documents, false);

			data = FolderSession.preparationOfFolder(sessionID, bookID,
					folderId.intValue(), false, new Integer(0), locale
							.getLanguage(), entidad, data);

			data.setDocumentsUpdate(updateDocumentsImages(sessionID, bookID,
					data.getUser(), folderId.intValue(), data
							.getDocumentsUpdate(), data.getAxsfNew(), data
							.getAxsfNew(), data.getScrofic(), locale, entidad));

			HibernateUtil.commitTransaction(tran);

			return data;
		} catch (BookException e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to update a folder for bookID [" + bookID
					+ "]", e);
			throw new BookException(BookException.ERROR_CANNOT_SAVE_FILE);
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to update a folder for bookID [" + bookID
					+ "]", e);
			throw new BookException(BookException.ERROR_UPDATE_FOLDER);
		} finally {
			HibernateUtil.closeSession(entidad);
		}

	}

	/**
	 *
	 * @param sessionID
	 * @param bookID
	 * @param user
	 * @param fdrid
	 * @param documentsUpdate
	 * @param axsfNew
	 * @param axsfOld
	 * @param scrOfic
	 * @param entidad
	 * @return
	 * @throws BookException
	 */
	public static Map updateDocumentsImages(String sessionID, Integer bookID,
			AuthenticationUser user, int fdrid, Map documentsUpdate,
			AxSf axsfNew, AxSf axsfOld, ScrOfic scrOfic, Locale locale,
			String entidad) throws BookException {

		BookConf bookConf = FolderSessionUtil.getBookConf(bookID, entidad);

		BookTypeConf bookTypeConf = getBookTypeConf(bookID, entidad, axsfNew,
				axsfOld, user.getName(), scrOfic.getCode());

		// Documentos e imagenes
		if (documentsUpdate != null && !documentsUpdate.isEmpty()) {
			documentsUpdate = FolderFileSession.createDocuments(bookID, fdrid,
					documentsUpdate, user.getId(), entidad);

			documentsUpdate = FolderFileSession.saveDocuments(sessionID,
					bookID, fdrid, documentsUpdate, bookTypeConf, bookConf, user.getId(),
					locale, entidad);
		}

		return documentsUpdate;
	}

	/**
	 * Cerramos un registro
	 *
	 * @param sessionID
	 * @param bookID
	 * @param folderID
	 * @param entidad
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static void closeFolder(String sessionID, Integer bookID,
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

			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);

			unlock(session, bookID, folderID, user);

			HibernateUtil.commitTransaction(tran);

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
			throw new BookException(BookException.ERROR_CANNOT_CLOSE_FOLDER);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static void closeFolderFromDistribution(String sessionID,
			Integer bookID, int folderID, String entidad) throws BookException,
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

			unlock(session, bookID, folderID, user);

			HibernateUtil.commitTransaction(tran);

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
			throw new BookException(BookException.ERROR_CANNOT_CLOSE_FOLDER);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	/***************************************************************************
	 * PRIVATE METHOD
	 **************************************************************************/

	private static AxSfQueryResults navigateOrNavigateToRowRegisterQuery(
			String sessionID, Integer bookID, Locale locale, String entidad,
			boolean isToRow, int row, String queryNavigationType,
			String orderByTable) throws BookException, SessionException,
			ValidationException {
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

			THashMap bookInformation = (THashMap) cacheBag.get(bookID);
			AxSfQueryResults queryResults = (AxSfQueryResults) bookInformation
					.get(AXSF_QUERY_RESULTS);

			if (queryResults == null) {
				throw new BookException(BookException.ERROR_QUERY_NOT_OPEN);
			}
			if (isToRow) {
				if (row < 0 || row > queryResults.getTotalQuerySize()) {
					throw new BookException(BookException.ERROR_ROW_OUTSIDE);
				}
			}

			AxSf axsf = (AxSf) bookInformation.get(AXSF);
			AxSfQuery axsfQuery = (AxSfQuery) bookInformation.get(AXSF_QUERY);

			// si viene distinto de null o vacio se modifica el orden de la
			// consulta
			// por la ordenacion que llega como parametro
			if ((orderByTable != null) && (!orderByTable.equals(""))) {
				axsfQuery.setOrderBy(orderByTable);
			}

			String filter = (String) bookInformation.get(QUERY_FILTER);
			Idocarchdet idoc = (Idocarchdet) bookInformation
					.get(IDocKeys.IDOCARCHDET_FLD_DEF_ASOBJECT);

			Map result = new TreeMap();
			AxSfEntity axSfEntity = new AxSfEntity();
			Collection col = null;
			if (isToRow) {
				col = axSfEntity.findByRowAxSfQuery(axsfQuery, axsf,
						queryResults, row, filter, entidad);
			} else {
				col = axSfEntity.findByAxSfQuery(axsfQuery, axsf, queryResults,
						queryNavigationType, filter, entidad);
			}
			extractRegistersFromQuery(col, result, session, idoc, axSfEntity,
					locale.getLanguage(), entidad);

			HibernateUtil.commitTransaction(tran);

			return queryResults.clone(result.values());
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			throw new BookException(BookException.ERROR_CANNOT_FIND_REGISTERS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	private static void validateNavigationType(String queryNavigationType)
			throws ValidationException {
		if (!queryNavigationType.equals(Keys.QUERY_FIRST_PAGE)
				&& !queryNavigationType.equals(Keys.QUERY_LAST_PAGE)
				&& !queryNavigationType.equals(Keys.QUERY_NEXT_PAGE)
				&& !queryNavigationType.equals(Keys.QUERY_PREVIOUS_PAGE)
				&& !queryNavigationType.equals(Keys.QUERY_ALL)) {
			throw new ValidationException(
					ValidationException.ERROR_ATTRIBUTE_VALIDATION,
					new String[] { ValidationException.ATTRIBUTE_NAVIGATIONTYPE });
		}
	}

	private static FolderDataSession createNewOutputRegister(String sessionID,
			AxSf axsfNew, boolean automaticRegisterCreation,
			Integer launchDistOutRegister, FolderDataSession data,
			Locale locale, String entidad) throws BookException,
			SessionException, ValidationException {
		if (automaticRegisterCreation) {
			AxSf newOutputRegister = getNewOutputRegister(axsfNew);

			return crearRegistro(sessionID, data.getNewAssociatedBookID(),
					newOutputRegister, data.getInter(),
					data.getNewAssociatedRegisterID(), launchDistOutRegister,
					null, locale, entidad, false, false, true, null);

		}
		return data;
	}

	/**
	 * Método que genera el registro, ademas de auditar la creación del mismo
	 *
	 * @param sessionID - ID de session
	 * @param bookID - ID del libro
	 * @param axsfNew - Nuevo registro
	 * @param inter - Interesados
	 * @param assignedRegisterID - ID del registro
	 * @param launchDistOutRegister - Indica si se ha de distribuir
	 * @param bookTypeConf - Configuracion del libro
	 * @param locale - Idioma
	 * @param entidad - Entidad
	 * @param isConsolidacion - Indica si es consolidacion
	 * @param isImport - Indica si es una importación
	 * @param distributeRegInAccepted
	 * @param documentos - Documentos adjuntados al registro
	 *
	 * @return FolderDataSession
	 *
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	private static FolderDataSession crearRegistro(String sessionID,
			Integer bookID, AxSf axsfNew, List inter, int assignedRegisterID,
			Integer launchDistOutRegister, BookTypeConf bookTypeConf,
			Locale locale, String entidad, boolean isConsolidacion,
			boolean isImport, boolean distributeRegInAccepted, Map documentos)
			throws BookException, SessionException, ValidationException {

		FolderDataSession result = createNewFolderEx(sessionID, bookID, axsfNew, inter,
				assignedRegisterID, launchDistOutRegister, bookTypeConf,
				locale, entidad, isConsolidacion, isImport,
				distributeRegInAccepted);

		// Auditar nuevo registro
		ISicresAuditHelper.auditoriaCrearRegistro(inter, documentos, result);

		return result;
	}

	private static void updateNewOutputRegister(String sessionID,
			boolean updateDate, Integer launchDistOutRegister,
			Integer newAssociatedBookID, List inter,
			int newAssociatedRegisterID, Locale locale, String entidad,
			FolderDataSession data) throws BookException, SessionException,
			ValidationException {
		if (data.isAutomaticRegisterCreation()) {
			switch (data.getAutomaticRegisterCreactionType()) {
			case 1: {

				for (Iterator it2 = data.getScrRegAsocToDelete().iterator(); it2
						.hasNext();) {
					ScrRegasoc scrRegAsoc = (ScrRegasoc) it2.next();
					AxSf newOutputRegister = new AxSfOut();
					newOutputRegister.setAttributeValue("fld6", new Integer(4));

					if (log.isDebugEnabled()) {
						log.debug("Poniendo anulado a................... "
								+ scrRegAsoc.getIdArchsec() + " .. "
								+ scrRegAsoc.getIdFdrsec());
					}

					updateFolderEx(sessionID, new Integer(scrRegAsoc
							.getIdArchsec()), scrRegAsoc.getIdFdrsec(),
							newOutputRegister, null, null, updateDate,
							launchDistOutRegister, locale, entidad,0);
				}

				break;
			}
			case 2: {
				AxSf newOutputRegister = getNewOutputRegister(data.getAxsfOld());

				for (Iterator it2 = data.getScrRegAsocToDelete().iterator(); it2
						.hasNext();) {
					ScrRegasoc scrRegAsoc = (ScrRegasoc) it2.next();
					updateFolderEx(sessionID, new Integer(scrRegAsoc
							.getIdArchsec()), scrRegAsoc.getIdFdrsec(),
							newOutputRegister, inter, null, updateDate,
							launchDistOutRegister, locale, entidad,0);
				}
				break;
			}
			case 3: {
				createNewOutputRegister(sessionID, data.getAxsfNew(), data
						.isAutomaticRegisterCreation(), launchDistOutRegister,
						data, locale, entidad);

				break;
			}
			}
		}
	}

	private static AxSf getNewOutputRegister(AxSf axsf) {
		AxSf newOutputRegister = new AxSfOut();
		newOutputRegister.setAttributeValue("fld8", axsf
				.getAttributeValue("fld8"));
		newOutputRegister.setAttributeValue("fld7", axsf
				.getAttributeValue("fld7"));
		newOutputRegister.setAttributeValue("fld10", axsf
				.getAttributeValue("fld14"));
		newOutputRegister.setAttributeValue("fld11", axsf
				.getAttributeValue("fld15"));
		if (axsf.getAttributeValue("fld16") != null) {
			newOutputRegister.setAttributeValue("fld12", new Integer(axsf
					.getAttributeValueAsString("fld16")));
		} else {
			newOutputRegister.setAttributeValue("fld12", axsf
					.getAttributeValue("fld16"));
		}
		newOutputRegister.setAttributeValue("fld13", axsf
				.getAttributeValue("fld17"));

		return newOutputRegister;
	}

	private static int getOpenRegisterTotalSize(Session session,
			THashMap bookInformation, AuthenticationUser user,
			AxSfQuery axsfQuery, Integer reportOption, List bookIds,
			Iuserusertype userusertype, ScrOfic scrofic, String entidad)
			throws HibernateException, Exception {
		AxSf axsf = (AxSf) bookInformation.get(AXSF);
		String filter = (String) bookInformation.get(QUERY_FILTER);

		int totalSize = 0;
		AxSfEntity axSfEntity = new AxSfEntity();
		if (reportOption.intValue() == 0) {
			totalSize = axSfEntity.calculateQuerySize(axsfQuery, axsf, filter,
					entidad);
		} else {
			ISicresAPerms aPerms = new ISicresAPerms();
			Integer initialBookId = axsfQuery.getBookId();

			for (Iterator it = bookIds.iterator(); it.hasNext();) {
				Integer auxBookId = (Integer) it.next();
				ScrRegstate scrRegstate = ISicresQueries.getScrRegstate(
						session, auxBookId);
				axsfQuery.setBookId(auxBookId);
				getAPerms(session, auxBookId, user.getId(), userusertype
						.getType(), user.getDeptid(), aPerms, scrRegstate,
						scrofic);
				if (!aPerms.isBookAdmin()) {
					getQueryFilter(session, bookInformation, auxBookId, user
							.getId(), scrofic);
				}
				String filterBookIds = (String) bookInformation
						.get(QUERY_FILTER);
				totalSize = totalSize
						+ axSfEntity.calculateQuerySize(axsfQuery, axsf,
								filterBookIds, entidad);
			}
			if (filter == null) {
				bookInformation.remove(QUERY_FILTER);
			} else {
				bookInformation.put(QUERY_FILTER, "(" + filter + ")");
			}
			axsfQuery.setBookId(initialBookId);
		}

		return totalSize;
	}

	private static String getRegisterToCloseQuery(Session session,
			Integer bookID, Iuserusertype userusertype, ScrOfic scrofic,
			Date beginDate, Date endDate, String unit, boolean isTarget,
			String entidad) throws BookException, HibernateException,
			SQLException, Exception {
		List privOrgs = null;
		if (userusertype.getType() != IDocKeys.IUSERUSERTYPE_USER_TYPE_ADMIN) {
			privOrgs = DBEntityDAOFactory.getCurrentDBEntityDAO().getPrivOrgs(
					scrofic.getId().intValue(), entidad);
		}

		int unitId = -1;
		if (unit != null) {
			try {
				unitId = ISUnitsValidator
						.getUnit(session, unit, true, privOrgs).getId()
						.intValue();
			} catch (ValidationException e) {
				throw new BookException(
						BookException.ERROR_CANNOT_GET_REGISTERS_TO_CLOSE_INVALID_CODE);
			}
		}

		StringBuffer where = new StringBuffer();
		if (scrofic != null) {
			where.append(" FLD5=");
			where.append(scrofic.getId());
			where.append(" AND FLD6="
					+ ISicresKeys.SCR_ESTADO_REGISTRO_COMPLETO);
		} else {
			where.append(" FLD6=" + ISicresKeys.SCR_ESTADO_REGISTRO_COMPLETO);
		}
		where.append(" AND FLD2>="
				+ DBEntityDAOFactory.getCurrentDBEntityDAO().getTimeStampField(
						beginDate, 0));
		where.append(" AND FLD2<="
				+ DBEntityDAOFactory.getCurrentDBEntityDAO().getTimeStampField(
						endDate, 1));

		if (unit != null) {
			if (isTarget) {
				where.append(" AND FLD8=" + unitId);
			} else {
				where.append(" AND FLD7=" + unitId);
			}
		}

		String tableName = "A" + bookID + "SF";
		String query = DBEntityDAOFactory.getCurrentDBEntityDAO()
				.findAxSFToCloseSentence(tableName, where.toString());

		return query;
	}

	/**
	 * Metodo que obtiene el numero de registros que cumplen con idBook e idFolder que se pasan como parametro
	 *
	 * @param sessionID - ID de la session
	 * @param idEntidad - ID de la entidad
	 * @param bookID - ID del libro
	 * @param idFolder - ID del registro
	 *
	 * @return int - numero de registros que cumplen el criterio de idBook e idFolder
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static int getCountRegisterByIdReg(String sessionID, String idEntidad, Integer bookID,
			Integer idFolder)
			throws BookException, SessionException, ValidationException {
		AxSfQuery axsfQuery = new AxSfQuery();

		axsfQuery.setBookId(bookID);

		AxSfQueryField field = new AxSfQueryField();

		// generamos los criterios para el campo
		field.setFldId(AxSf.FDRID_FIELD);
		field.setOperator(IGUAL);
		field.setValue(idFolder);

		// asignamos el campo al objeto de consulta
		axsfQuery.addField(field);

		// obtenemos el numero de registros que cumplen con los valores
		// indicados en la url y a los que el usuario tiene permisos
		int size = getCountRegistersQuery(sessionID, axsfQuery, null,
				new Integer(0), idEntidad);

		return size;
	}
}
