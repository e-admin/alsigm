package com.ieci.tecdoc.isicres.session.folder;

import es.ieci.tecdoc.isicres.terceros.business.manager.InteresadoManager;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseDireccionVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.RepresentanteInteresadoVO;
import gnu.trove.THashMap;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.expression.Expression;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.conf.BookConf;
import com.ieci.tecdoc.common.conf.BookTypeConf;
import com.ieci.tecdoc.common.conf.InvesicresConf;
import com.ieci.tecdoc.common.entity.AxDochEntity;
import com.ieci.tecdoc.common.entity.AxFdrhEntity;
import com.ieci.tecdoc.common.entity.AxSfEntity;
import com.ieci.tecdoc.common.entity.AxXfEntity;
import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.invesicres.ScrBookasoc;
import com.ieci.tecdoc.common.invesicres.ScrCa;
import com.ieci.tecdoc.common.invesicres.ScrCadoc;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrOrg;
import com.ieci.tecdoc.common.invesicres.ScrRegasoc;
import com.ieci.tecdoc.common.invesicres.ScrRegasocex;
import com.ieci.tecdoc.common.invesicres.ScrRegstate;
import com.ieci.tecdoc.common.invesicres.ScrTmzofic;
import com.ieci.tecdoc.common.isicres.AxFdrh;
import com.ieci.tecdoc.common.isicres.AxPK;
import com.ieci.tecdoc.common.isicres.AxPKById;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfOut;
import com.ieci.tecdoc.common.isicres.AxXf;
import com.ieci.tecdoc.common.isicres.AxXfPK;
import com.ieci.tecdoc.common.isicres.Keys;
import com.ieci.tecdoc.common.isicres.PersonAddress;
import com.ieci.tecdoc.common.isicres.PersonAddressTel;
import com.ieci.tecdoc.common.isicres.PersonInfo;
import com.ieci.tecdoc.common.keys.ConfigurationEventsKeys;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.keys.ISicresKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.BBDDUtils;
import com.ieci.tecdoc.common.utils.ConfiguratorEvents;
import com.ieci.tecdoc.common.utils.ISDateUtils;
import com.ieci.tecdoc.common.utils.ISDistribution;
import com.ieci.tecdoc.common.utils.ISicresAPerms;
import com.ieci.tecdoc.common.utils.ISicresQueries;
import com.ieci.tecdoc.common.utils.ISicresSaveQueries;
import com.ieci.tecdoc.common.utils.Repository;
import com.ieci.tecdoc.common.utils.adapter.XMLPersons;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FFldDef;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FieldFormat;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrDocument;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrField;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrInter;
import com.ieci.tecdoc.idoc.utils.ConfiguratorInvesicres;
import com.ieci.tecdoc.isicres.context.ISicresBeansProvider;
import com.ieci.tecdoc.isicres.events.exception.EventException;
import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.session.distribution.DistributionSession;
import com.ieci.tecdoc.isicres.session.events.EventsSession;
import com.ieci.tecdoc.isicres.session.utils.UtilsSession;
import com.ieci.tecdoc.isicres.session.validation.ValidationSessionEx;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

public class FolderSessionUtil extends UtilsSession implements ServerKeys,
		Keys, HibernateKeys {

	protected static final SimpleDateFormat FORMATTER = new SimpleDateFormat(
			"yyyy");

	private static final Logger log = Logger.getLogger(FolderSessionUtil.class);

	/***************************************************************************
	 * PUBLIC METHOD
	 **************************************************************************/

	public static AxSf generateRegisterNumber(Session session, Integer bookID,
			AxSf axsfNew, ScrRegstate scrregstate, ScrOfic scrofic,
			String entidad) throws BookException, HibernateException,
			SQLException, Exception {
		InvesicresConf invesicresConf = ConfiguratorInvesicres.getInstance(
				entidad).getInvesicresConf();

		int numeration = getNumeration(session, scrregstate.getIdocarchhdr()
				.getId(), scrofic, scrregstate);
		if (numeration == ISicresKeys.SCR_NUMERATION_CENTRAL) {
			createCentralNumeration(bookID, axsfNew, invesicresConf
					.getFormatType0(), entidad);
		} else if (numeration == ISicresKeys.SCR_NUMERATION_OFIC) {
			createOficNumeration(bookID, scrofic, axsfNew, invesicresConf
					.getFormatType2(), entidad);
		} else {
			throw new BookException(
					BookException.ERROR_NUMERATION_NOT_SUPPORTED);
		}

		return axsfNew;
	}

	public static AxSf getAxSf(Session session, Integer bookId,
			Integer folderId, Idocarchdet idoc, String language,
			String entidad, boolean load) throws Exception {
		AxPK pk = new AxPK(bookId.toString(), folderId.intValue());
		AxSfEntity axSfEntity = new AxSfEntity();
		pk = axSfEntity.findByPrimaryKey(pk, entidad);
		axSfEntity.load(pk, entidad);
		AxSf axsf = axSfEntity.getAxSf(entidad);
		if (load) {
			loadAxSf(session, pk, axsf, new HashMap(), idoc, language, entidad);
		}
		return axsf;
	}

	/**
	 *
	 * @param session
	 * @param bookId
	 * @param folderId
	 * @param idoc
	 * @param language
	 * @param entidad
	 * @param load
	 * @param axSfEntity
	 * @param vldAttribMap
	 * @return
	 * @throws Exception
	 */
	public static AxSf getAxSf(Session session, Integer bookId,
			Integer folderId, Idocarchdet idoc, String language,
			String entidad, boolean load, AxSfEntity axSfEntity,
			Map vldAttribMap) throws Exception {
		AxPK pk = new AxPK(bookId.toString(), folderId.intValue());
		pk = axSfEntity.findByPrimaryKey(pk, entidad);
		axSfEntity.load(pk, entidad);
		AxSf axsf = axSfEntity.getAxSf(entidad);
		if (load) {
			loadAxSf(session, pk, axsf, vldAttribMap, idoc, language, entidad);
		}
		return axsf;
	}

	/**
	 * Método para saber si se tienen permisos para crear o actualizar un
	 * registro
	 *
	 * @param sessionID
	 * @param bookId
	 * @param documents
	 * @throws BookException
	 * @throws SessionException
	 * @throws TecDocException
	 */
	public static void canCreateOrUpdateFolder(String sessionID,
			Integer bookId, List documents) throws BookException,
			SessionException, TecDocException {
		CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
				sessionID);
		THashMap bookInformation = (THashMap) cacheBag.get(bookId);
		ISicresAPerms aPerms = (ISicresAPerms) bookInformation
				.get(ServerKeys.APERMS_USER);
		if (!aPerms.canCreate()) {
			throw new BookException(
					BookException.ERROR_CANNOT_CREATE_NEW_FOLDER);
		} else if (documents != null && !documents.isEmpty()
				&& !aPerms.canModify()) {
			throw new BookException(
					BookException.ERROR_CANNOT_CREATE_NEW_FOLDER);
		}
	}

	/**
	 * Método que transforma los valores de un atributo en el que realmente es
	 *
	 * @param flushFdrField
	 * @param shortFormatter
	 * @param longFormatter
	 * @param fieldFormat
	 * @return
	 */
	public static Object convertValue(FlushFdrField flushFdrField,
			SimpleDateFormat shortFormatter, SimpleDateFormat longFormatter,
			FieldFormat fieldFormat) {
		FFldDef fldDef = fieldFormat.getFFldDef(flushFdrField.getFldid());
		switch (fldDef.getType()) {
		case 1:
		case 2: {
			return flushFdrField.getValue();
		}
		case 3:
		case 4: {
			if (flushFdrField.getValue() != null) {
				return new Integer(flushFdrField.getValue());
			} else {
				return flushFdrField.getValue();
			}
		}
		case 5:
		case 6: {
			if (flushFdrField.getValue() != null) {
				return Integer.valueOf(flushFdrField.getValue());
			} else {
				return flushFdrField.getValue();
			}
		}
		case 7: {
			try {
				if (flushFdrField.getValue() != null) {
					return shortFormatter.parse(flushFdrField.getValue());
				} else {
					return flushFdrField.getValue();
				}
			} catch (ParseException e) {
			}
		}
		case 8:
		case 9: {
			try {
				if (flushFdrField.getValue() != null) {
					return longFormatter.parse(flushFdrField.getValue());
				} else {
					return flushFdrField.getValue();
				}
			} catch (ParseException e) {
			}
		}
		}
		return null;
	}

	/**
	 * Método que comprueba la validez de un valor de un atributo
	 *
	 * @param flushFdrField
	 * @param shortFormatter
	 * @param longFormatter
	 * @param fieldFormat
	 * @return
	 */
	public static boolean checkValue(FlushFdrField flushFdrField,
			SimpleDateFormat shortFormatter, SimpleDateFormat longFormatter,
			FieldFormat fieldFormat) {
		FFldDef fldDef = fieldFormat.getFFldDef(flushFdrField.getFldid());

		if (fldDef == null) {
			return false;
		}

		switch (fldDef.getType()) {
		case 1:
		case 2: {
			break;
		}
		case 3:
		case 4: {
			try {
				Long.parseLong(flushFdrField.getValue());
			} catch (Exception e) {
				return false;
			}
			break;
		}
		case 5:
		case 6: {
			try {
				Integer.parseInt(flushFdrField.getValue());
			} catch (Exception e) {
				return false;
			}
			break;
		}
		case 7: {
			try {
				shortFormatter.parse(flushFdrField.getValue());
				if (shortFormatter.getCalendar().get(Calendar.YEAR) < 1970
						|| shortFormatter.getCalendar().get(Calendar.YEAR) > 2040) {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
			break;
		}
		case 8:
		case 9: {
			try {
				longFormatter.parse(flushFdrField.getValue());
				if (longFormatter.getCalendar().get(Calendar.YEAR) < 1970
						|| shortFormatter.getCalendar().get(Calendar.YEAR) > 2040) {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
			break;
		}
		}

		return true;
	}

	/***************************************************************************
	 * PROTECTED METHOD
	 **************************************************************************/

	protected static FolderDataSession preparationOfFolder(String sessionID,
			Integer bookID, int fdrid, boolean updateDate,
			Integer launchDistOutRegister, String language, String entidad,
			FolderDataSession data) throws BookException, SessionException {

		// Preparación operación del registro
		Transaction tran = null;
		boolean asocEqualFld8 = true;
		try {

			data = getInformation(sessionID, bookID, entidad, data);

			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			if (data.isCreate()) {
				data = setUser(sessionID, bookID, entidad, data);
				data = setScrOficForImportFolder(session, data);
			} else {
				data = loadAxSfOld(session, bookID, fdrid, language, entidad,
						data);
				data = setChangedFields(data);

				if (data.changedContainsKey(new Integer(8))) {
					asocEqualFld8 = false;
				}

				data.resetAxSfOld();
			}

			// Punto a)
			data = setDate(updateDate, entidad, data);

			// Punto g)
			Integer scrCaId = getTipoAsuntoId(bookID, true, entidad, data);
			if (log.isDebugEnabled()) {
				log.debug("scrCaId => " + scrCaId);
			}

			data = setTipoAsunto(session, bookID, scrCaId, entidad, data);
			data = isStateCompleted(bookID, entidad, data);

			// Punto h)
			scrCaId = getTipoAsuntoId(bookID, false, entidad, data);
			data = setDocumentsTipoAsunto(session, scrCaId, data, data
					.isCreate());

			// Punto i)
			data = isLaunchDistribution(session, bookID, launchDistOutRegister,
					entidad, data);

			// Punto j)
			data = isAutomaticRegisterCreation(session, bookID, fdrid,
					asocEqualFld8, entidad, data);

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

		return data;
	}

	protected static FolderDataSession getNewIDs(Integer bookID,
			int assignedRegisterID, FolderDataSession data, String entidad)
			throws BookException {
		Integer newAssociatedBookID = getNewAssociatedBookID(data
				.getAssociatedBook());
		int newRegisterID = -1;
		if (data.isCreate()) {
			newRegisterID = getNewRegisterID(bookID, data.getUserId(),
					assignedRegisterID, entidad);
		}
		int newAssociatedRegisterID = getNewAssociatedRegisterID(bookID, data,
				entidad);

		data.setNewAssociatedBookID(newAssociatedBookID);
		data.setNewRegisterID(newRegisterID);
		data.setNewAssociatedRegisterID(newAssociatedRegisterID);

		return data;
	}

	protected static FolderDataSession createRegister(String sessionID,
			Integer bookID, List inter, int newRegisterID,
			Integer newAssociatedBookID, int newAssociatedRegisterID,
			String entidad, FolderDataSession data, boolean isConsolidacion)
			throws BookException {

		Transaction tran = null;
		try {

			// Punto a)
			data = completeInterList(sessionID, bookID, newRegisterID, inter,
					entidad, data);

			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Generación del número de registro
			if (!isConsolidacion && !data.isImportFolder()) {
				data.setAxsfNew(generateRegisterNumber(session, bookID, data
						.getAxsfNew(), data.getScrregstate(),
						data.getScrofic(), entidad));
			}

			if (data.getBookTypeConf() != null) {
				data.getBookTypeConf().setRegisterNumber(
						data.getNewAttributeValueAsString("fld1"));
			}

			// Auditoria de modificación
			if (!data.isImportFolder()) {
				createFolderAudit(sessionID, entidad, data.getUser(), data
						.getAxsfNew(), bookID, data.getScrofic(), session, data
						.getCurrentDate(), data.isUpdateAudit());
			}

			// Creación de registro

			// Punto b)
			createGenericInformationFolder(bookID, newRegisterID, data
					.getUserId(), data.getCurrentDate(), entidad);

			// Punto c)
			data = saveExtendedFields(sessionID, bookID, newRegisterID,
					entidad, data);



			// Distribución
			if (data.isLaunchDistribution()) {
				DistributionSession.distribute(false, session, bookID,
						newRegisterID, data.getLaunchDistributionType(), data
								.getNewAttributeValueAsInteger("fld8"), data
								.getUserId(), data.getDeptId(), data
								.getUserName(), entidad, data.getLocale(),new Integer(0));
			}

			createGenericInformationDocumentsTipoAsunto(bookID, data
					.getDocumentsScrCa(), newRegisterID, data.getCurrentDate(),
					data.getUserId(), entidad);

			// Punto d)
			lock(session, bookID, newRegisterID, data.getUser(), data
					.getScrofic(), entidad);

			// Creacion automática de registros
			createScrRegAsoc(session, bookID, newRegisterID,
					newAssociatedBookID, newAssociatedRegisterID, entidad, data);

			unlock(session, bookID, newRegisterID, data.getUser());

			HibernateUtil.commitTransaction(tran);

		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (EventException eE){
			HibernateUtil.rollbackTransaction(tran);
			throw eE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to create a new folder for the session ["
					+ sessionID + "] and bookID [" + bookID + "]", e);
			throw new BookException(
					BookException.ERROR_CANNOT_CREATE_NEW_FOLDER);
		} finally {
			HibernateUtil.closeSession(entidad);
		}

		return data;
	}

	protected static FolderDataSession updateRegister(String sessionID,
			Integer bookID, int fdrid, List inter, Integer newAssociatedBookID,
			int newAssociatedRegisterID, String entidad, FolderDataSession data, Integer idDistFather)
			throws BookException {
		Transaction tran = null;
		try {
			// Punto a)
			data = completeInterList(sessionID, bookID, fdrid, inter, entidad,
					data);

			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Auditoria de modificaciones
			updateFolderAudit(sessionID, entidad, data.getChangedFields(), data
					.getUser(), data.getAxsfOld(), bookID, data.getScrofic(),
					session);



			// Punto b)
			AxFdrhEntity axFdrhEntity = new AxFdrhEntity();
			axFdrhEntity.updateAccessControl(bookID, data.getUserId()
					.intValue(), DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getDBServerDate(entidad), fdrid, entidad);

			// Punto c)
			data = saveExtendedFields(sessionID, bookID, fdrid, entidad, data);

			// Distribución
			if (data.isLaunchDistribution()) {
				DistributionSession.distribute(true, session, bookID, fdrid,
						data.getLaunchDistributionType(), new Integer(data
								.getOldAttributeValueAsString("fld8")), data
								.getUserId(), data.getDeptId(), data
								.getUserName(), entidad, data.getLocale(), idDistFather);
			} else if (data.changedContainsKey(new Integer(8))) {
				DBEntityDAOFactory.getCurrentDBEntityDAO()
						.deleteDistributeForUpdate(bookID.intValue(), fdrid,
								data.getDeptId().intValue(), entidad,
								ISDistribution.STATE_PENDIENTE);
			}

			// Creación automática de registros
			createScrRegAsoc(session, bookID, fdrid, newAssociatedBookID,
					newAssociatedRegisterID, entidad, data);

			HibernateUtil.commitTransaction(tran);
		} catch (EventException eE){
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to update a folder for the session ["
					+ sessionID + "] and bookID [" + bookID + "]", eE);
			throw eE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to update a folder for the session ["
					+ sessionID + "] and bookID [" + bookID + "]", e);
			throw new BookException(BookException.ERROR_UPDATE_FOLDER);
		} finally {
			HibernateUtil.closeSession(entidad);
		}

		return data;
	}


	protected static BookConf getBookConf(Integer bookId, String entidad){
		return bookConf(bookId, entidad);
	}
	protected static BookTypeConf getBookTypeConf(Integer bookID,
			String entidad, AxSf axsfNew, AxSf axsfOld, String userName,
			String scrOficCode) {

		BookTypeConf bookTypeConf = null;

		if (Repository.getInstance(entidad).isInBook(bookID).booleanValue()) {
			bookTypeConf = bookTypeConf(1, entidad);
		} else {
			bookTypeConf = bookTypeConf(2, entidad);
		}
		if (bookTypeConf != null) {
			Locale locale = new Locale("ES");
			bookTypeConf.setStringBookType(axsfNew
					.getLocaleAttributeLiteralTypeBook(locale,
							Integer.valueOf(bookTypeConf.getBookType())));

			//bookTypeConf.setStringBookType(axsfNew.getLiteralBookType());
			bookTypeConf.setRegisterNumber(axsfOld
					.getAttributeValueAsString("fld1"));
			Date date = null;
			if (axsfOld.getAttributeValue("fld2") != null) {

				date = (Date) axsfOld.getAttributeValue("fld2");
			} else {
				try {
					date = BBDDUtils.getDateFromTimestamp(DBEntityDAOFactory
							.getCurrentDBEntityDAO().getDBServerDate(entidad));
				} catch (Exception e) {
					date = Calendar.getInstance().getTime();
				}
			}
			SimpleDateFormat shortformatter = new SimpleDateFormat(
					"dd/MM/yyyy HH:mm:ss");
			String dateString = shortformatter.format(date);

			bookTypeConf.setRegisterDate(dateString);
			bookTypeConf.setUser(userName);
			bookTypeConf.setCodeOfic(scrOficCode);
		}

		return bookTypeConf;

	}

	/***************************************************************************
	 * PRIVATE METHOD
	 **************************************************************************/

	private static void createCentralNumeration(Integer bookID, AxSf axsf,
			String format, String entidad) throws SQLException, Exception {
		Date registerDate = (Date) axsf.getAttributeValue("fld2");
		int year = Integer.parseInt(FORMATTER.format(registerDate));
		int bookType = 1;
		if (Repository.getInstance(entidad).isOutBook(bookID).booleanValue()) {
			bookType = 2;
		}

		DBEntityDAOFactory.getCurrentDBEntityDAO().updateScrCntCentral(year,
				bookType, entidad);
		int newCont = DBEntityDAOFactory.getCurrentDBEntityDAO()
				.getNumRegFromScrCntCentral(year, bookType, entidad);
		if (newCont == -1) {
			DBEntityDAOFactory.getCurrentDBEntityDAO().lockScrCentral(year,
					entidad);
			newCont = DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getNumRegFromScrCntCentral(year, bookType, entidad);
			if (newCont == -1) {
				newCont = DBEntityDAOFactory.getCurrentDBEntityDAO()
						.createScrCentralRow(year, bookType, entidad);
			}
		}
		String fld1 = centralNumerationFormat(format, year, newCont);

		axsf.setAttributeValue("fld1", fld1);
	}

	private static void createOficNumeration(Integer bookID, ScrOfic scrofic,
			AxSf axsf, String format, String entidad) throws SQLException,
			Exception {
		Date registerDate = (Date) axsf.getAttributeValue("fld2");
		int year = Integer.parseInt(FORMATTER.format(registerDate));
		int bookType = 1;
		if (Repository.getInstance(entidad).isOutBook(bookID).booleanValue()) {
			bookType = 2;
		}

		DBEntityDAOFactory.getCurrentDBEntityDAO().updateScrCntOficina(year,
				bookType, scrofic.getId().intValue(), entidad);

		if (log.isDebugEnabled()) {
			log.debug("year => " + year);
		}

		int newCont = DBEntityDAOFactory.getCurrentDBEntityDAO()
				.getNumRegFromScrCntOficina(year, bookType,
						scrofic.getId().intValue(), entidad);

		if (log.isDebugEnabled()) {
			log.debug("newCont => " + newCont);
		}

		if (newCont == -1) {
			DBEntityDAOFactory.getCurrentDBEntityDAO().lockScrOficina(year,
					entidad);
			newCont = DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getNumRegFromScrCntOficina(year, bookType,
							scrofic.getId().intValue(), entidad);
			if (log.isDebugEnabled()) {
				log.debug("newCont => " + newCont);
			}
			if (newCont == -1) {
				newCont = DBEntityDAOFactory.getCurrentDBEntityDAO()
						.createScrOficinaRow(year, bookType,
								scrofic.getId().intValue(), entidad);
				if (log.isDebugEnabled()) {
					log.debug("newCont => " + newCont);
				}

			}
		}
		String fld1 = oficNumerationFormat(format, year, newCont, scrofic
				.getCode());

		axsf.setAttributeValue("fld1", fld1);
	}

	private static String centralNumerationFormat(String format, int year,
			int cont) throws Exception {
		String regNumber = "";
		String formatYear = null;
		String formatCont = null;
		String yearString = String.valueOf(year);

		if (!format.equals("")) {
			StringTokenizer tokenizer = new StringTokenizer(format, "}");

			int numTokens = tokenizer.countTokens();
			String token = null;
			for (int i = 0; i < numTokens; i++) {
				token = tokenizer.nextToken();
				formatYear = getFormatYear(token, yearString);
				formatCont = getFormatCont(token, cont);
			}
			if (formatYear != null) {
				regNumber = regNumber + formatYear;
			}
			if (formatCont != null) {
				regNumber = regNumber + formatCont;
			}
		} else {
			regNumber = year + "00";
			String newCont = String.valueOf(cont);
			for (int i = 6; i > newCont.length(); i--) {
				regNumber = regNumber + "0";
			}
			regNumber = regNumber + cont;
		}

		return regNumber;
	}

	/**
	 * Metodo que mediante la numeracion por oficina de registros obtiene el
	 * numero de registro mediante el formato a aplicar
	 *
	 * @param format
	 * @param year
	 * @param cont
	 * @param code
	 * @return
	 * @throws Exception
	 */
	private static String oficNumerationFormat(String format, int year,
			int cont, String code) throws Exception {
		String regNumber = "";
		String parameterValueNumReg = null;

		if (!format.equals("")) {
			StringTokenizer tokenizer = new StringTokenizer(format, "}");
			int numTokens = tokenizer.countTokens();
			String token = null;

			for (int i = 0; i < numTokens; i++) {
				token = tokenizer.nextToken();
				// se llama a la funcion que devuelve cada parametro segun el
				// formato
				parameterValueNumReg = getParameterNumRegValue(token, year,
						cont, code);
				// comprobamos que el string no sea nulo
				if (parameterValueNumReg != null) {
					regNumber = regNumber + parameterValueNumReg;
				}
			}

		} else {
			regNumber = String.valueOf(year);
			for (int i = 3; i > code.length(); i--) {
				regNumber = regNumber + "0";
			}

			regNumber = regNumber + code;
			String newCont = String.valueOf(cont);

			for (int i = 8; i > newCont.length(); i--) {
				regNumber = regNumber + "0";
			}

			regNumber = regNumber + cont;

		}
		return regNumber;
	}

	/**
	 * Metodo que segun el token que le llegue y el formato va retornando el
	 * string correspondiente a la posicion en la que se trabaja, para componer
	 * el numero de registro
	 *
	 * @param token
	 * @param year
	 * @param cont
	 * @param code
	 * @return {@link String}
	 */
	private static String getParameterNumRegValue(String token, int year,
			int cont, String code) {
		String result = null;
		String yearString = String.valueOf(year);

		// comprueba si el token corresponde al anyo
		String formatYear = getFormatYear(token, yearString);

		// comprueba si el token corresponde al codigo oficina
		String formatCode = getFormatCode(token, code);

		// comprueba si el token corresponde al contador de registros
		String formatCont = getFormatCont(token, cont);

		if (formatYear != null) {
			result = formatYear;
		} else if (formatCode != null) {
			result = formatCode;
		} else if (formatCont != null) {
			result = formatCont;
		} else {
			result = token;
		}

		return result;
	}

	private static String getFormatYear(String token, String yearString) {
		String result = null;
		if (token.indexOf("Y") != -1) {
			String longYear = token.substring(token.indexOf(",") + 1, token
					.length());
			String fixPart = token.substring(0, token.indexOf("{"));
			result = fixPart;
			if (longYear.equals("2")) {
				result = result
						+ yearString.substring(yearString.length() - 2,
								yearString.length());
			} else {
				result = result + yearString;
			}
		}

		return result;
	}

	private static String getFormatCode(String token, String code) {
		if (token.indexOf("C") != -1) {
			String fixPart = token.substring(0, token.indexOf("{"));
			String formatCode = fixPart;
			token = token.substring(token.indexOf("{"), token.length());
			int pos = token.indexOf(",");
			if (pos != -1) {
				String longCode = token.substring(pos + 1, token.length());
				if (new Integer(longCode).intValue() < code.length()) {
					formatCode = formatCode + code;
				} else {
					for (int j = new Integer(longCode).intValue(); j > code
							.length(); j--) {
						formatCode = formatCode + "0";
					}
					formatCode = formatCode + code;
				}

			} else {
				formatCode = formatCode + code;
			}

			return formatCode;
		}

		return null;
	}

	private static String getFormatCont(String token, int cont) {
		if (token.indexOf("N") != -1) {
			String fixPart = token.substring(0, token.indexOf("{"));
			String formatCont = fixPart;
			token = token.substring(token.indexOf("{"), token.length());
			int pos = token.indexOf(",");
			if (pos != -1) {
				String longCont = token.substring(pos + 1, token.length());
				if (new Integer(longCont).intValue() < String.valueOf(cont)
						.length()) {
					formatCont = formatCont + String.valueOf(cont);
				} else {
					String newCont = String.valueOf(cont);
					for (int j = new Integer(longCont).intValue(); j > newCont
							.length(); j--) {
						formatCont = formatCont + "0";
					}
					formatCont = formatCont + cont;
				}

			} else {
				formatCont = formatCont + cont;
			}

			return formatCont;
		}

		return null;
	}

	private static FolderDataSession getInformation(String sessionID,
			Integer bookID, String entidad, FolderDataSession data)
			throws BookException, SessionException, TecDocException,
			ValidationException, Exception {
		CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
				sessionID);

		AuthenticationUser user = (AuthenticationUser) cacheBag
				.get(HIBERNATE_Iuseruserhdr);

		// Es necesario tener el libro abierto para consultar su contenido.
		if (data.isCreate() && !cacheBag.containsKey(bookID)) {
			BookSession.openBook(sessionID, bookID, entidad);
		}
		if (!cacheBag.containsKey(bookID)) {
			throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
		}
		if (data.isImportFolder()) {
			AxSfEntity axSfEntity = new AxSfEntity();
			boolean registerExist = axSfEntity.checkAxSF(bookID, data
					.getNewAttributeValueAsString("fld1"), entidad);
			if (registerExist) {
				throw new BookException(
						BookException.ERROR_CANNOT_CREATE_NEW_FOLDER);
			}
		}

		THashMap bookInformation = (THashMap) cacheBag.get(bookID);

		Idocarchdet idoc = (Idocarchdet) bookInformation
				.get(IDocKeys.IDOCARCHDET_FLD_DEF_ASOBJECT);
		ScrOfic scrofic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

		ScrTmzofic scrTmzofic = (ScrTmzofic) cacheBag.get(HIBERNATE_ScrTmzofic);

		//Formato y estado del libro
		ScrRegstate scrregstate = (ScrRegstate) bookInformation
				.get(HIBERNATE_ScrRegstate);
		AxSf axsfFormat = (AxSf) bookInformation.get(AXSF);
		/*
		if (data.isCreate()) {
			scrregstate = (ScrRegstate) bookInformation
					.get(HIBERNATE_ScrRegstate);
			axsfFormat = (AxSf) bookInformation.get(AXSF);
		}*/
		Idocarchdet idocMisc = (Idocarchdet) bookInformation
				.get(IDocKeys.IDOCARCHDET_MISC_ASOBJECT);

		data.setUser(user);
		data.setIdoc(idoc);
		data.setScrofic(scrofic);

		data.setScrTmzofic(scrTmzofic);

		data.setScrregstate(scrregstate);
		data.setAxsfFormat(axsfFormat);
		data.setIdocMisc(idocMisc);

		return data;

	}

	private static FolderDataSession setUser(String sessionID, Integer bookID,
			String entidad, FolderDataSession data) throws ValidationException,
			Exception {
		Integer fld3Length = null;

		// Si el objeto que representa al libro actual no tiene relleno su
		// formato, intentar recuperarlo
		if (data.getAxsfFormat() == null
				|| data.getAxsfFormat().getLenFields() == null) {
			data.setAxsfFormat(BookSession.getTableFormat(sessionID, bookID,
					entidad));
		}
		// Si en este punto tenemos el formato del libro, intentamos
		// recuperar la longitud del campo que necesitamos (usuario - fld3)
		if (data.getAxsfFormat() != null
				&& data.getAxsfFormat().getLenFields() != null) {
			fld3Length = (Integer) data.getAxsfFormat().getLenFields().get(
					new Integer(3));
		}

		String aux = data.getUserName();
		if (fld3Length != null) {
			if (aux.length() > fld3Length.intValue()) {
				aux = aux.substring(0, fld3Length.intValue());
				data.getUser().setName(aux);
			}
		}

		if (isDataBaseCaseSensitive(entidad)) {
			data.setNewAttributeValue("fld3", data.getUserName().toUpperCase());
		} else {
			data.setNewAttributeValue("fld3", data.getUserName());
		}

		return data;
	}

	private static FolderDataSession setScrOficForImportFolder(Session session,
			FolderDataSession data) throws HibernateException {
		if (data.isImportFolder()) {
			Integer scrOficId = (Integer) data.getNewAttributeValue("fld5");
			ScrOfic scrOfic = (ScrOfic) session.load(ScrOfic.class, scrOficId);
			data.setScrofic(scrOfic);
		}
		return data;
	}

	private static FolderDataSession loadAxSfOld(Session session,
			Integer bookID, int fdrid, String language, String entidad,
			FolderDataSession data) throws Exception {
		AxPK pk = new AxPK(bookID.toString(), fdrid);
		pk = data.getAxSfEntity().findByPrimaryKey(pk, entidad);
		data.getAxSfEntity().load(pk, entidad);
		AxSf axsfOld = data.getAxSfEntity().getAxSf(entidad);

		loadAxSf(session, pk, axsfOld, new HashMap(), data.getIdoc(), language,
				entidad);

		if (log.isDebugEnabled()) {
			log.debug("axsfOld to antes ===============> " + axsfOld);
		}

		data.setAxsfOld(axsfOld);

		return data;
	}

	private static FolderDataSession setChangedFields(FolderDataSession data) {
		Map changedFields = new HashMap();
		for (Iterator it = data.getNewAttributesNames().iterator(); it
				.hasNext();) {
			String name = (String) it.next();
			changedFields.put(new Integer(name.substring(FLD.length(), name
					.length())), new Object[] {
					data.getOldAttributeValue(name),
					data.getNewAttributeValue(name) });

			data.getAxsfOld().removeAttribute(name);
			data.getAxsfOld().addAttributeName(name);
			data.getAxsfOld().addAttributeValue(name,
					data.getNewAttributeValue(name));

		}

		data.getChangedFields().putAll(changedFields);

		return data;

	}

	private static FolderDataSession setDate(boolean updateDate,
			String entidad, FolderDataSession data) throws ParseException,
			Exception {
		Locale locale = data.getLocale();

		Date currentDate = BBDDUtils.getDateFromTimestamp(DBEntityDAOFactory
				.getCurrentDBEntityDAO().getDBServerDate(entidad));

		Date currentDate1 = getAxSfDateFormatted(data.getAxsfNew(), locale,
				DEFAULTSIMPLEDATEFORMAT, currentDate);

		if (data.isCreate() && !data.isImportFolder()) {
			ScrTmzofic scrTmzofic = data.getScrTmzofic();
			Date dateReg = ISDateUtils
					.calculateTmzOfic(currentDate, scrTmzofic);

			Date currentDateFormated = getAxSfDateFormatted(data.getAxsfNew(),
					locale, LONGSIMPLEDATEFORMAT, dateReg);

			if (data.getNewAttributeValue("fld2") == null) {
				data.setNewAttributeValue("fld2", currentDateFormated);
			}

			data.setNewAttributeValue("fld4", currentDate1);
			data.setNewAttributeValue("fld5", data.getScrofic().getId());
		} else if (updateDate) {
			data.setOldAttributeValue("fld4", currentDate1);
		}

		if (!data.isCreate() && !data.isImportFolder()
				&& data.getAxsfOld() != null) {
			data.setOldAttributeValue("timestamp", currentDate);
		}

		data.setCurrentDate(currentDate);

		return data;

	}

	private static Integer getTipoAsuntoId(Integer bookID, boolean validaFld,
			String entidad, FolderDataSession data) {
		Integer scrCaId = null;
		if (Repository.getInstance(entidad).isInBook(bookID).booleanValue()) {
			if (validaFld) {
				if (data.isCreate()) {
					if (data.getNewAttributeValue("fld8") == null
							&& data.getNewAttributeValue("fld16") != null) {
						scrCaId = data.getNewAttributeValueAsInteger("fld16");
					}
				} else {
					if (data.getOldAttributeValue("fld8") == null
							&& data.getOldAttributeValue("fld16") != null
							&& data.getNewAttributeValue("fld16") != null) {
						scrCaId = data.getNewAttributeValueAsInteger("fld16");
					}
				}
			} else {
				if (data.isCreate()) {
					if (data.getNewAttributeValue("fld16") != null) {
						scrCaId = data.getNewAttributeValueAsInteger("fld16");
					}
				} else {
					if (data.getOldAttributeValue("fld16") != null
							&& data.getNewAttributeValue("fld16") != null) {
						scrCaId = data.getNewAttributeValueAsInteger("fld16");
					}
				}
			}
		} else {
			if (validaFld) {
				if (data.isCreate()) {
					if (data.getNewAttributeValue("fld7") == null
							&& data.getNewAttributeValue("fld12") != null) {
						scrCaId = data.getNewAttributeValueAsInteger("fld12");
					}
				} else {
					if (data.getOldAttributeValue("fld7") == null
							&& data.getOldAttributeValue("fld12") != null
							&& data.getNewAttributeValue("fld12") != null) {
						scrCaId = data.getNewAttributeValueAsInteger("fld12");
					}
				}
			} else {
				if (data.isCreate()) {
					if (data.getNewAttributeValue("fld12") != null) {
						scrCaId = data.getNewAttributeValueAsInteger("fld12");
					}
				} else {
					if (data.getOldAttributeValue("fld12") != null
							&& data.getNewAttributeValue("fld12") != null) {
						scrCaId = data.getNewAttributeValueAsInteger("fld12");
					}
				}
			}
		}

		return scrCaId;
	}

	private static FolderDataSession setTipoAsunto(Session session,
			Integer bookID, Integer scrCaId, String entidad,
			FolderDataSession data) {
		if (scrCaId != null) {
			try {
				ScrCa scrCa = (ScrCa) session.load(ScrCa.class, scrCaId);
				if (scrCa.getIdOrg() != 0) {
					if (data.isCreate()) {
						if (Repository.getInstance(entidad).isInBook(bookID)
								.booleanValue()) {
							data.setNewAttributeValue("fld8", new Integer(scrCa
									.getIdOrg()));
						} else {
							data.setNewAttributeValue("fld7", new Integer(scrCa
									.getIdOrg()));
						}
					} else {
						Object oldFld8 = data.getOldAttributeValue("fld8");
						Object oldFld7 = data.getOldAttributeValue("fld7");
						if (Repository.getInstance(entidad).isInBook(bookID)
								.booleanValue()) {
							data.setOldAttributeValue("fld8", new Integer(scrCa
									.getIdOrg()));
							data
									.changedPut(new Integer(8), new Object[] {
											oldFld8,
											data.getOldAttributeValue("fld8") });
						} else {
							data.setOldAttributeValue("fld7", new Integer(scrCa
									.getIdOrg()));
							data
									.changedPut(new Integer(7), new Object[] {
											oldFld7,
											data.getOldAttributeValue("fld7") });
						}
					}
				}
			} catch (HibernateException e) {
			}
		}

		return data;
	}

	private static FolderDataSession isStateCompleted(Integer bookID,
			String entidad, FolderDataSession data) {
		if (data.isCreate()) {
			return isStateCompletedCreate(bookID, entidad, data);
		} else {
			return isStateCompletedUpdate(bookID, entidad, data);
		}
	}

	private static FolderDataSession setDocumentsTipoAsunto(Session session,
			Integer scrCaId, FolderDataSession data, boolean isCreate) {
		List docs = null;
		try {
			docs = ISicresQueries.getScrCaDocIDMatter(session, scrCaId);

			if (!isCreate) {
				List docsToAdd = new ArrayList();
				for (Iterator it = docs.iterator(); it.hasNext();) {
					ScrCadoc scrcadoc = (ScrCadoc) it.next();
					boolean found = false;
					for (Iterator it2 = data.getDocumentsUpdate().values()
							.iterator(); it2.hasNext() && !found;) {
						FlushFdrDocument document = (FlushFdrDocument) it2
								.next();
						found = scrcadoc.getDescription().equals(
								document.getDocumentName());
					}
					if (!found) {
						FlushFdrDocument document = new FlushFdrDocument();
						document.setDocumentName(scrcadoc.getDescription());
						docsToAdd.add(document);
					}
				}
				if (!docsToAdd.isEmpty()) {
					for (Iterator it = docsToAdd.iterator(); it.hasNext();) {
						FlushFdrDocument document = (FlushFdrDocument) it
								.next();
						data.getDocumentsUpdate().put(
								document.getDocumentName(), document);
					}
				}
			}
		} catch (HibernateException e) {
		}

		data.setDocumentsScrCa(docs);

		return data;
	}

	/**
	 *
	 * Metodo que obtiene si un registro se ha de ser distribuido de forma automatica
	 *
	 * @param session
	 * @param bookID
	 * @param launchDistOutRegister
	 * @param entidad
	 * @param data
	 * @return
	 * @throws NumberFormatException
	 * @throws HibernateException
	 */
	private static FolderDataSession isLaunchDistribution(Session session,
			Integer bookID, Integer launchDistOutRegister, String entidad,
			FolderDataSession data) throws NumberFormatException,
			HibernateException {

		boolean launchDistribution = false;
		if ((Repository.getInstance(entidad).isInBook(bookID).booleanValue() || (Repository
				.getInstance(entidad).isOutBook(bookID).booleanValue() && launchDistOutRegister
				.intValue() == 1))
				&& data.isCompletedState()
				&& data.isDistributeRegInAccepted()) {
			ScrOrg scrorg = null;
			// fld8 es el identificador de la Unidad Administrativa destino del
			// asiento. ID existente en SCR_ORGS.
			if ((data.getAxsfOld() != null)
					&& (data.getOldAttributeValue("fld8") != null)) {
				String destinoOldAttributeValue = data
						.getOldAttributeValueAsString("fld8");
				scrorg = (ScrOrg) session.load(ScrOrg.class, new Integer(
						destinoOldAttributeValue));
			} else {

				String destinoNewAttributeValue = data
						.getNewAttributeValueAsString("fld8");
				// Si es modificacion
				if (!data.isCreate() && data.getAxsfOld() != null) {
					String destinoOldAttributeValue = data
							.getOldAttributeValueAsString("fld8");
					// Si el valor del destino no es nulo
					if (StringUtils.isNotEmpty(destinoOldAttributeValue)) {
						scrorg = (ScrOrg) session.load(ScrOrg.class,
								new Integer(destinoOldAttributeValue));
						// Si el valor del destino es nulo intentamos cogerlo
						// del newAttribute
					} else {

						if (StringUtils.isNotEmpty(destinoNewAttributeValue)) {
							scrorg = (ScrOrg) session.load(ScrOrg.class,
									new Integer(destinoNewAttributeValue));
						}
					}
				} else {
					// Si es creacion y si el destino no es nulo
					if (StringUtils.isNotEmpty(destinoNewAttributeValue)) {
						scrorg = (ScrOrg) session.load(ScrOrg.class,
								new Integer(destinoNewAttributeValue));
					}
				}
			}

			launchDistribution = scrorg != null
					&& scrorg.getScrTypeadm() != null
					&& scrorg.getScrTypeadm().getId().equals(new Integer(1));

			// Si es una modificacion de un registro y el estado del registro no
			// ha cambiado (anteriormente se valida que el estado sea completo),
			// se pasará a comprobar si el destino (FLD8) realmente ha sido
			// modificado para generar la distribución, en el caso que haya sido
			// realmente
			// modificado
			if (!data.isCreate()
					&& (data.getChangedFields().get(new Integer(6)) == null)) {
				// Si el campo destino (FLD8) no ha cambiado. (Hay que
				// contemplar que si el usuario no modifica el campo, el valor
				// "New" sera
				// igual a "null", pero tambien puede ser que el usuario haya
				// modificado el campo dejando el mismo valor de Destino.)
				if (data.changedContainsKey(new Integer(8))) {
					Object[] value = (Object[]) data.changedGet(new Integer(8));
					Object oldValue = value[0];
					Object newValue = value[1];
					// comprobamos si el valor es el mismo o ha cambiado
					if (!isAuditFieldModif(oldValue, newValue)
							|| newValue == null) {
						// si el destino (FLD8) no ha cambiado o el valor es
						// igual a
						// null, indicamos que no se ejecute la distribucion
						// automatica.
						launchDistribution = false;
						// Indicamos que no ha cambiado el campo "FLD8" ya que
						// se comprueba posteriormete este dato para borrar
						// la distribucion. Lo borramos de la lista de campos
						// que han cambiado.
						data.getChangedFields().remove(new Integer(8));
					}
				} else {
					// Comprobamos si el campo FLD8 ha sido modificado si ha
					// cambiado se debe ejecutar la distribución por tanto no
					// entrará en el if.
					if (!isChangeFieldFld8(data)) {
						// si el destino (FLD8) no ha cambiado, indicamos que no
						// se ejecute la distribucion automatica.
						launchDistribution = false;
					}
				}
			}

			if (launchDistribution) {
				data.setLaunchDistributionType(1);
			}

			if (log.isDebugEnabled()) {
				log.debug("************** ANALIZANDO LAUNCHDISTRIBUTION ["
						+ launchDistribution + "]");
			}
		}

		data.setLaunchDistribution(launchDistribution);
		return data;
	}

	/**
	 * Método que nos indica si el valor del campo FLD8 ha cambiado durante la
	 * modificación del registro
	 *
	 * @param data - {@link FolderDataSession}Datos del registro
	 * @return boolean: TRUE - valor ha cambiado / FALSE - el valor no ha cambiado
	 */
	private static boolean isChangeFieldFld8(FolderDataSession data){
		boolean result = true;

		// comprobamos si el valor del campo FLD8 no ha
		// cambiado(para ello en el objeto AxsfOld existira valor y
		// en el
		// AxSfNew no)y no se encuentra entre los campos modificados
		if (data.getOldAttributeValue("fld8") != null
				&& data.getNewAttributeValue("fld8") == null
				&& !data.changedContainsKey(new Integer(8))) {
			//el campo FLD8 no ha cambiado su valor
			result = false;
		}

		return result;
	}

	private static FolderDataSession isAutomaticRegisterCreation(
			Session session, Integer bookID, int fdrid, boolean asocEqualFld8,
			String entidad, FolderDataSession data) throws BookException {
		if (Repository.getInstance(entidad).isInBook(bookID).booleanValue()) {
			if (data.isCreate()) {
				boolean automaticRegisterCreation = false;
				if (data.getNewAttributeValue("fld8") != null) {
					try {
						ScrOrg scrorg = (ScrOrg) session.load(ScrOrg.class,
								new Integer(data
										.getNewAttributeValueAsString("fld8")));
						automaticRegisterCreation = scrorg != null
								&& scrorg.getScrTypeadm() != null
								&& !scrorg.getScrTypeadm().getId().equals(
										new Integer(1));

						if (automaticRegisterCreation) {
							data = isAutomaticRegisterCreationType(session,
									bookID, 0, entidad, data);
						}
					} catch (HibernateException e) {
						automaticRegisterCreation = false;
						data.setAssociatedBook(null);
					}
				}

				data.setAutomaticRegisterCreation(automaticRegisterCreation);
			} else {
				if (!asocEqualFld8) {
					try {
						data = isAutomaticRegisterCreationType(session, bookID,
								fdrid, entidad, data);
					} catch (HibernateException e) {
						data.setAutomaticRegisterCreation(false);
						data.setAssociatedBook(null);
					}
				}
			}
		}

		return data;
	}

	private static Integer getNewAssociatedBookID(ScrBookasoc associatedBook) {
		Integer newAssociatedBookID = null;
		if (associatedBook != null) {
			newAssociatedBookID = new Integer(associatedBook.getIdBooksec());
		}

		return newAssociatedBookID;
	}

	private static int getNewRegisterID(Integer bookID, Integer userId,
			int assignedRegisterID, String entidad) throws BookException {
		Transaction tran = null;
		int newRegisterID = assignedRegisterID;
		// Generación ID de registro
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			if (newRegisterID == -1) {
				newRegisterID = DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getNextIdForRegister(bookID, entidad);
				DBEntityDAOFactory.getCurrentDBEntityDAO().lastRegister(
						new Integer(newRegisterID), userId, bookID, entidad);
			}

			HibernateUtil.commitTransaction(tran);
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to create a new folder for bookID [" + bookID
					+ "]", e);
			throw new BookException(
					BookException.ERROR_CANNOT_CREATE_NEW_FOLDER);
		} finally {
			HibernateUtil.closeSession(entidad);
		}

		return newRegisterID;
	}

	private static int getNewAssociatedRegisterID(Integer bookID,
			FolderDataSession data, String entidad) throws BookException {

		int newAssociatedRegisterID = 0;
		if (data.isAutomaticRegisterCreation()
				&& (data.isCreate() || data.getAutomaticRegisterCreactionType() == 3)) {
			// Generación ID de registro
			Transaction tran = null;
			try {
				Session session = HibernateUtil.currentSession(entidad);
				tran = session.beginTransaction();

				newAssociatedRegisterID = DBEntityDAOFactory
						.getCurrentDBEntityDAO().getNextIdForRegister(
								new Integer(data.getAssociatedBook()
										.getIdBooksec()), entidad);

				HibernateUtil.commitTransaction(tran);
			} catch (Exception e) {
				HibernateUtil.rollbackTransaction(tran);
				log.error("Impossible to create a new folder for bookID ["
						+ bookID + "]", e);
				throw new BookException(
						BookException.ERROR_CANNOT_CREATE_NEW_FOLDER);
			} finally {
				HibernateUtil.closeSession(entidad);
			}
		}

		return newAssociatedRegisterID;
	}

	/**
	 * Metodo que registra la auditoria cuando creamos un registro
	 *
	 * @param sessionID
	 * @param entidad
	 * @param user
	 * @param axsfNew
	 * @param bookID
	 * @param scrofic
	 * @param session
	 * @param currentDate
	 * @param updateAudit
	 * @throws SQLException
	 * @throws Exception
	 */
	private static void createFolderAudit(String sessionID, String entidad,
			AuthenticationUser user, AxSf axsfNew, Integer bookID,
			ScrOfic scrofic, Session session, Date currentDate,
			boolean updateAudit) throws SQLException, Exception {
		if (updateAudit) {
			Integer updateAuditId = new Integer(DBEntityDAOFactory
					.getCurrentDBEntityDAO().getNextIdForScrModifreg(
							user.getId(), entidad));

			String userName = null;
			if (isDataBaseCaseSensitive(entidad)) {
				userName = user.getName().toUpperCase();
			} else {
				userName = user.getName();
			}
			String numReg = axsfNew.getAttributeValueAsString("fld1");

			ISicresSaveQueries.saveScrModifreg(session, updateAuditId,
					userName, currentDate, numReg, 2, bookID.intValue(),
					scrofic.getId().intValue(), 1);

			DBEntityDAOFactory.getCurrentDBEntityDAO().insertAudit(
					updateAuditId, axsfNew.getAttributeValue("fld2"), null,
					entidad);
		}
	}

	/**
	 * Metodo que registra la auditoria cuando modificamos un registro
	 *
	 * @param sessionID
	 * @param entidad
	 * @param changedFields
	 * @param user
	 * @param axsfOld
	 * @param bookID
	 * @param scrofic
	 * @param session
	 * @throws Exception
	 */
	private static void updateFolderAudit(String sessionID, String entidad,
			Map changedFields, AuthenticationUser user, AxSf axsfOld,
			Integer bookID, ScrOfic scrofic, Session session) throws Exception {

		Date currentDate = new Date(DBEntityDAOFactory.getCurrentDBEntityDAO()
				.getDBServerDate(entidad).getTime());
		if (!changedFields.isEmpty()) {
			Integer size = new Integer(BBDDUtils.SCR_MODIFREG_USR_FIELD_LENGTH);

			String aux = user.getName();
			if (aux.length() > size.intValue()) {
				aux = aux.substring(0, size.intValue());
				user.setName(aux);
			}

			for (Iterator it = changedFields.keySet().iterator(); it.hasNext();) {
				Integer fldidToChange = (Integer) it.next();
				Object[] value = (Object[]) changedFields.get(fldidToChange);
				Object oldValue = value[0];
				Object newValue = value[1];

				if (isAuditFieldModif(oldValue, newValue)) {
					Integer updateAuditId = new Integer(DBEntityDAOFactory
							.getCurrentDBEntityDAO().getNextIdForScrModifreg(
									user.getId(), entidad));
					String userName = null;
					if (isDataBaseCaseSensitive(entidad)) {
						userName = user.getName().toUpperCase();
					} else {
						userName = user.getName();
					}
					String numReg = axsfOld.getAttributeValueAsString("fld1");

					ISicresSaveQueries.saveScrModifreg(session, updateAuditId,
							userName, currentDate, numReg, fldidToChange
									.intValue(), bookID.intValue(), scrofic
									.getId().intValue(), 0);

					try {
						DBEntityDAOFactory.getCurrentDBEntityDAO().insertAudit(
								updateAuditId, oldValue, newValue, entidad);
					} catch (SQLException e) {
						log.error(
								"Impossible to audit the update for the session ["
										+ sessionID + "] and bookID [" + bookID
										+ "]", e);
						throw new BookException(
								BookException.ERROR_AUDIT_CHANGES);
					}
				}
			}
		}
	}

	/**
	 * Metodo que comprueba si dos valores son iguales
	 * @param oldValue
	 * @param newValue
	 * @return boolean - TRUE - son diferentes, FALSE - son iguales
	 */
	private static boolean isAuditFieldModif(Object oldValue, Object newValue) {
		BigDecimal bigDecimalValue = null;
		boolean result = true;

		if ((oldValue instanceof BigDecimal) && (newValue instanceof Integer)){
			bigDecimalValue = new BigDecimal(((Integer) newValue).toString());
			result = !(new EqualsBuilder().append(oldValue, bigDecimalValue).isEquals());
		}else{
			if ((oldValue instanceof Integer) && (newValue instanceof BigDecimal)){
				bigDecimalValue = new BigDecimal(((Integer) oldValue).toString());
				result = !(new EqualsBuilder().append(bigDecimalValue, newValue).isEquals());
			}else{
				result = !(new EqualsBuilder().append(oldValue, newValue).isEquals());
			}
		}

		return result;
	}

	private static FolderDataSession completeInterList(String sessionID,
			Integer bookID, int registerID, List inter, String entidad,
			FolderDataSession data) throws HibernateException, SQLException,
			Exception {
		if (inter != null && !inter.isEmpty()) {

			InteresadoManager interesadoManager = ISicresBeansProvider.getInstance().getInteresadoManager();

//			InteresadoManager interesadoManager = (InteresadoManager) SpringApplicationContext
//					.getBean("interesadoManager");
			interesadoManager.deleteAll(bookID.toString(),
					String.valueOf(registerID));

			boolean existValidado = addInteresadosValidados(entidad, sessionID,
					inter, bookID, registerID, data);

			if (!existValidado) {
				addInteresadosNoValidados(entidad, sessionID, inter, bookID,
						registerID, data);

			}
		}

		return data;
	}

	private static void createGenericInformationFolder(Integer bookID,
			int newRegisterID, Integer userId, Date currentDate, String entidad)
			throws Exception {
		AxFdrh axfdrh = new AxFdrh();
		axfdrh.setId(newRegisterID);
		axfdrh.setVerStat(1);
		axfdrh.setRefCount(0);
		axfdrh.setAccessType(1);
		axfdrh.setAcsId(Integer.MAX_VALUE - 1);
		axfdrh.setCrtrId(userId.intValue());
		axfdrh.setCrtnDate(currentDate);
		axfdrh.setAccrId(userId.intValue());
		axfdrh.setAccDate(currentDate);
		axfdrh.setAcccount(1);
		AxFdrhEntity axFdrhEntity = new AxFdrhEntity();
		axFdrhEntity.create(bookID.toString(), axfdrh, entidad);
	}

	private static FolderDataSession saveExtendedFields(String sessionID,
			Integer bookID, int registerID, String entidad,
			FolderDataSession data) throws BookException, SessionException,
			ValidationException, SQLException, Exception {
		AxXfEntity axXfEntity = new AxXfEntity();
		List extendedFields = getExtendedFields(data);

		String dataBaseType = DBEntityDAOFactory.getCurrentDBEntityDAO()
				.getDataBaseType();

		for (Iterator it = extendedFields.iterator(); it.hasNext();) {
			Integer extendedFieldId = (Integer) it.next();

			String value = null;
			boolean isOk = false;
			boolean createExtField = true;

			AxXf newValueFieldExtend = (AxXf) data.getAxsfNew().getExtendedFields().get(extendedFieldId);

			if(data.isCreate()){
				//si se crea el registro guardamos los datos de nuevo registro
				if(newValueFieldExtend != null){
					value = newValueFieldExtend.getText();
					isOk = true;
				}
			}else{
				//si se actualiza registro
				if (newValueFieldExtend != null){
					AxXf oldValueFieldExtend = (AxXf) data.getAxsfOld().getExtendedFields().get(extendedFieldId);

					//comprobamos que el valor nuevo y el valor viejo no sean iguales
					if ((oldValueFieldExtend != null)
							&& !(oldValueFieldExtend.getText()
									.equalsIgnoreCase(newValueFieldExtend
											.getText()))) {
						value = newValueFieldExtend.getText();
						isOk = true;
					}else{
						//si el valor viejo no tiene ese campo relleno se guardan los cambios del nuevo
						if(oldValueFieldExtend == null){
							value = newValueFieldExtend.getText();
							isOk = true;
						}
					}
				}
			}

			if (isOk) {
				AxXfPK axxpk = new AxXfPK();
				axxpk.setType(bookID.toString());
				axxpk.setFdrId(registerID);
				axxpk.setFldId(extendedFieldId.intValue());

				if (!data.isCreate()) {
					try {
						axXfEntity.findByPrimaryKey(axxpk, entidad);
						axXfEntity.remove(axxpk, entidad);
					} catch (Exception e) {
					}
				}

				if (createExtField) {
					int nextId = DBEntityDAOFactory.getCurrentDBEntityDAO()
							.getNextIdForExtendedField(bookID, entidad);

					axXfEntity.create(axxpk, nextId, value, DBEntityDAOFactory
							.getCurrentDBEntityDAO().getDBServerDate(entidad),
							entidad, dataBaseType);
				}
			}
		}

		if (data.isCreate()) {
			data.setNewAttributeValue("fdrid", new Integer(registerID));
			data.setNewAttributeValue("timestamp", data.getCurrentDate());
			String eventsManagerClassName = ConfiguratorEvents.getInstance(
					REGISTER_CREATE_EVENT).getProperty(
					ConfigurationEventsKeys.KEY_EVENTS_IMPLEMENTATION);
			if (eventsManagerClassName != null
					&& !eventsManagerClassName.equals("")) {
				EventsSession.registerCreateEvent(sessionID,
						REGISTER_CREATE_EVENT, bookID, data.getAxsfNew(),
						entidad);
			}

			AxSfEntity axSfEntity = new AxSfEntity();
			axSfEntity.create(bookID.toString(), data.getAxsfNew(), entidad,
					dataBaseType);
		} else {
			String eventsManagerClassName = ConfiguratorEvents.getInstance(
					REGISTER_MODIF_EVENT).getProperty(
					ConfigurationEventsKeys.KEY_EVENTS_IMPLEMENTATION);
			if (eventsManagerClassName != null
					&& !eventsManagerClassName.equals("")) {
				EventsSession.registerModifEvent(sessionID,
						REGISTER_MODIF_EVENT, bookID, new Integer(registerID),
						entidad);
			}
			data.getAxSfEntity().setAxSf(data.getAxsfOld());
			data.getAxSfEntity().store(entidad, dataBaseType);
		}

		return data;
	}

	private static void createGenericInformationDocumentsTipoAsunto(
			Integer bookID, List documents, int newRegisterID,
			Date currentDate, Integer userId, String entidad)
			throws SQLException, Exception {
		if (documents != null && !documents.isEmpty()) {
			for (Iterator it = documents.iterator(); it.hasNext();) {
				ScrCadoc scrCaDoc = (ScrCadoc) it.next();
				int docID = DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getNextDocID(bookID, entidad);
				AxPKById docPk = new AxPKById(bookID.toString(), newRegisterID,
						docID);

				AxDochEntity axDochEntity = new AxDochEntity();
				axDochEntity.create(docPk, userId.intValue(), scrCaDoc
						.getDescription(),
						new Timestamp(currentDate.getTime()), entidad);
			}
		}
	}

	private static void createScrRegAsoc(Session session, Integer bookID,
			int registerID, Integer newAssociatedBookID,
			int newAssociatedRegisterID, String entidad, FolderDataSession data)
			throws HibernateException, SQLException, Exception {
		if (data.isAutomaticRegisterCreation()) {
			boolean createScrRegAsoc = false;
			if (data.isCreate()) {
				createScrRegAsoc = true;
			} else {
				switch (data.getAutomaticRegisterCreactionType()) {
				case 1: {
					List auxList = ISicresQueries.getScrRegAsocEx(session);
					List toDelete = new ArrayList();

					for (Iterator it = auxList.iterator(); it.hasNext();) {
						ScrRegasocex scrRegAsocEx = (ScrRegasocex) it.next();
						for (Iterator it2 = data.getScrRegAsocToDelete()
								.iterator(); it2.hasNext();) {
							ScrRegasoc scrRegAsoc = (ScrRegasoc) it2.next();
							if (scrRegAsoc.getId().equals(
									new Integer(scrRegAsocEx.getIdAsoc()))) {
								toDelete.add(scrRegAsocEx);
							}
						}
					}

					Object[] obj = toDelete.toArray();
					for (int i = 0; i < obj.length; i++) {
						ScrRegasocex scrRegAsocEx = (ScrRegasocex) obj[i];
						session.delete(scrRegAsocEx);
					}

					break;
				}
				case 2: {

					break;
				}
				case 3: {
					createScrRegAsoc = true;
					break;
				}
				}
			}

			if (createScrRegAsoc) {
				int assocId = DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getNextIdForScrRegAsoc(data.getUserId(), entidad);

				ScrRegasoc scrRegAsoc = new ScrRegasoc();
				scrRegAsoc.setId(new Integer(assocId));
				scrRegAsoc.setIdArchprim(bookID.intValue());
				scrRegAsoc.setIdFdrprim(registerID);
				scrRegAsoc.setIdArchsec(newAssociatedBookID.intValue());
				scrRegAsoc.setIdFdrsec(newAssociatedRegisterID);
				session.save(scrRegAsoc);

				ScrRegasocex scrRegasocex = new ScrRegasocex();
				scrRegasocex.setIdAsoc(assocId);
				scrRegasocex.setType(0);
				session.save(scrRegasocex);
			}
		}
	}

	private static FolderDataSession isStateCompletedCreate(Integer bookID,
			String entidad, FolderDataSession data) {
		boolean completedState = false;
		List inter = data.getInter();
		boolean fullInter = inter != null && !inter.isEmpty();
		if (fullInter) {
			FlushFdrInter flushFdrInter = (FlushFdrInter) inter.get(0);
			fullInter = flushFdrInter.getInterName() != null;
		}

		if (Repository.getInstance(entidad).isInBook(bookID).booleanValue()) {
			if ((data.getNewAttributeValue("fld7") != null || fullInter)
					&& (data.getNewAttributeValue("fld8") != null)
					&& (data.getNewAttributeValue("fld16") != null || data
							.getNewAttributeValue("fld17") != null)) {
				data.setNewAttributeValue("fld6", new Integer(0));
				completedState = true;
			} else {
				data.setNewAttributeValue("fld6", new Integer(1));
			}
		} else {
			if ((data.getNewAttributeValue("fld8") != null || fullInter)
					&& (data.getNewAttributeValue("fld7") != null)
					&& (data.getNewAttributeValue("fld12") != null || data
							.getNewAttributeValue("fld13") != null)) {
				data.setNewAttributeValue("fld6", new Integer(0));
				completedState = true;
			} else {
				data.setNewAttributeValue("fld6", new Integer(1));
			}
		}

		data.setCompletedState(completedState);
		return data;
	}

	/**
	 * Hallar completo o incompleto despues de lo anterior siempre y cuando no
	 * sea el estado el campo que se está modificando por ejemplo desde las
	 * opciones de Abrir/Cerrar porque en este caso ya tenemos cuál es el valor
	 * original y el final
	 *
	 * @param bookID
	 * @param inter
	 * @param entidad
	 * @param data
	 * @return
	 */
	private static FolderDataSession isStateCompletedUpdate(Integer bookID,
			String entidad, FolderDataSession data) {

		boolean completedState = false;
		if (data.getChangedFields().size() == 0
				|| !data.changedContainsKey(new Integer(6))) {
			Object oldFld6 = data.getOldAttributeValue("fld6");
			boolean changeState = data.getNewAttributeValue("fld6") == null;
			if (!changeState) {
				data.setOldAttributeValue("fld6", data
						.getNewAttributeValue("fld6"));
				data.changedPut(new Integer(6), new Object[] { oldFld6,
						data.getNewAttributeValue("fld6") });
			}

			List inter = data.getInter();
			boolean fullInter = inter != null && !inter.isEmpty();
			boolean deleteInter = false;
			if (fullInter) {
				FlushFdrInter flushFdrInter = (FlushFdrInter) inter.get(0);
				fullInter = flushFdrInter.getInterName() != null;
				deleteInter = flushFdrInter.getInterName() == null;
			}
			if (!fullInter && !deleteInter) {
				fullInter = data.getOldAttributeValue("fld9") != null;
			}
			boolean finalChangeState = false;
			if (changeState) {
				// Obtenemos el valor del FLD6 old en entero (ya que en Oracle es un BigDecimal)
				int old = parseFLDToInt(oldFld6);

				if (Repository.getInstance(entidad).isInBook(bookID)
						.booleanValue()) {
					if ((data.getOldAttributeValue("fld7") != null || fullInter)
							&& (data.getOldAttributeValue("fld8") != null)
							&& (data.getOldAttributeValue("fld16") != null || data
									.getOldAttributeValue("fld17") != null)) {
						data.setOldAttributeValue("fld6", new Integer(0));
						if (old != 0) {
							finalChangeState = true;
						}
						completedState = true;
					} else {
						data.setOldAttributeValue("fld6", new Integer(1));
						if (old != 1) {
							finalChangeState = true;
						}
					}
				} else {
					if (((data.getOldAttributeValue("fld8") != null || fullInter)
							&& (data.getOldAttributeValue("fld7") != null) && (data
							.getOldAttributeValue("fld12") != null || data
							.getOldAttributeValue("fld13") != null))) {
						data.setOldAttributeValue("fld6", new Integer(0));
						if (old != 0) {
							finalChangeState = true;
						}
						completedState = true;
					} else {
						data.setOldAttributeValue("fld6", new Integer(1));
						if (old != 1) {
							finalChangeState = true;
						}
					}
				}
				if (finalChangeState) {
					data.changedPut(new Integer(6), new Object[] { oldFld6,
							data.getOldAttributeValue("fld6") });
				}
			}
		}

		data.setCompletedState(completedState);
		return data;
	}

	/**
	 * Método que traduce el campo pasado como objeto en un entero
	 * @param fld - Campo a convertir
	 * @return valor entero del campo
	 */
	private static int parseFLDToInt(Object fld) {
		int result = 0;
		if(fld != null){
			try {
				result = Integer.parseInt(fld.toString());
			} catch (NumberFormatException rFE) {
				StringBuffer sb = new StringBuffer();
				sb.append(
						"Se ha producido error al intentar parsear FLD [")
						.append(fld)
						.append("] a un valor númerico");
				log.warn(sb.toString());
			}
		}
		return result;
	}

	private static FolderDataSession isAutomaticRegisterCreationType(
			Session session, Integer bookID, int fdrid, String entidad,
			FolderDataSession data) throws BookException, HibernateException {
		ScrBookasoc associatedBook = ISicresQueries.getScrBookasoc(session,
				bookID);
		data.setAssociatedBook(associatedBook);
		if (associatedBook != null) {
			if (log.isDebugEnabled()) {
				log.debug("%%%%%%%%%%%%%%%%%%%%% associatedBook "
						+ associatedBook.getIdBooksec());
			}
			ScrRegstate regState = ISicresQueries.getScrRegstate(session,
					new Integer(associatedBook.getIdBooksec()));

			if (regState.getState() == ISicresKeys.BOOK_STATE_CLOSED) {
				throw new BookException(
						BookException.ERROR_ASSOCIATEDBOOK_NOT_OPEN);
			}

			if (Repository.getInstance(entidad).isInBook(
					new Integer(associatedBook.getIdBooksec())).booleanValue()) {
				throw new BookException(
						BookException.ERROR_ASSOCIATEDBOOK_NOT_IN_BOOK);
			}

			if (!data.isCreate()) {
				String whereScrRegasoc = getWhereScrRegasoc(bookID, fdrid,
						associatedBook);
				Criteria criteria = session.createCriteria(ScrRegasoc.class);
				criteria.add(Expression.sql(whereScrRegasoc));

				boolean existsAssociatedRegister = !criteria.list().isEmpty();

				if (existsAssociatedRegister) {
					List scrRegAsocToDelete = criteria.list();
					data.setScrRegAsocToDelete(scrRegAsocToDelete);
				}
				ScrOrg scrorg = null;
				if (((Object[]) data.changedGet(new Integer(8)))[1] != null) {
					scrorg = (ScrOrg) session.load(ScrOrg.class, new Integer(
							data.getOldAttributeValueAsString("fld8")));
				}

				boolean isOwnType = (scrorg != null
						&& scrorg.getScrTypeadm() != null && scrorg
						.getScrTypeadm().getId().equals(new Integer(1)))
						|| ((Object[]) data.changedGet(new Integer(8)))[1] == null;

				if (log.isDebugEnabled()) {
					log.debug("%%%%%%%%%%%%%%%%%%%%% existsAssociatedRegister "
							+ existsAssociatedRegister + "  isOwnType "
							+ isOwnType);
				}

				data.setAutomaticRegisterCreation(true);
				if (isOwnType && existsAssociatedRegister) {
					data.setAutomaticRegisterCreactionType(1);
				} else if (!isOwnType && existsAssociatedRegister) {
					data.setAutomaticRegisterCreactionType(2);
				} else if (!isOwnType && !existsAssociatedRegister) {
					data.setAutomaticRegisterCreactionType(3);
				}
			}
		}

		return data;
	}

	private static List getExtendedFields(FolderDataSession data) {
		List extendedFields = null;
		boolean isAxSfIn = true;
		if (data.isCreate()) {
			if (data.getAxsfNew() instanceof AxSfOut) {
				isAxSfIn = false;
			}
		} else {
			if (data.getAxsfOld() instanceof AxSfOut) {
				isAxSfIn = false;
			}
		}
		if (isAxSfIn) {
			extendedFields = getExtendedFields(data.getIdoc(),
					Keys.EREG_FDR_MATTER);
		} else {
			extendedFields = getExtendedFields(data.getIdoc(),
					Keys.SREG_FDR_MATTER);
		}

		return extendedFields;
	}

	private static Date getAxSfDateFormatted(AxSf axsf, Locale locale,
			String format, Date date) throws ParseException {
		SimpleDateFormat longformatter = new SimpleDateFormat(axsf
				.getLocaleAttributeDateFormat(locale, format));

		String dateString = longformatter.format(date);
		Date dateFormated = longformatter.parse(dateString);

		return dateFormated;

	}

	private static String getWhereScrRegasoc(Integer bookID, int fdrid,
			ScrBookasoc associatedBook) {
		String sqlScrRegAsocEx = "id in (select id_asoc from scr_regasocex where type = 0)";
		StringBuffer whereScrRegasoc = new StringBuffer();
		whereScrRegasoc.append(" id_Archprim = ");
		whereScrRegasoc.append(bookID);
		whereScrRegasoc.append(" and id_Fdrprim = ");
		whereScrRegasoc.append(fdrid);
		whereScrRegasoc.append(" and id_Archsec = ");
		whereScrRegasoc.append(associatedBook.getIdBooksec());
		whereScrRegasoc.append(" and ");
		whereScrRegasoc.append(sqlScrRegAsocEx);

		return whereScrRegasoc.toString();
	}

	/**
	 * Inserta los interesados validados en la tabla scr_regint y en el campo de
	 * interesados del Registro
	 *
	 * @param entidad
	 * @param sessionID
	 * @param inter
	 * @param bookID
	 * @param registerID
	 * @param data
	 * @return
	 * @throws HibernateException
	 */
	private static boolean addInteresadosValidados(String entidad,
			String sessionID, List inter, Integer bookID, int registerID,
			FolderDataSession data) throws HibernateException {

		FlushFdrInter flushFdrInter = null;
		boolean interesadosValidados = false;
		int order = 1;

		for (Iterator it = inter.iterator(); it.hasNext();) {
			flushFdrInter = (FlushFdrInter) it.next();
			int interId = flushFdrInter.getInterId();
			String interName = flushFdrInter.getInterName();
			int domId = flushFdrInter.getDomId();

			if ((interId > 0)
					&& StringUtils.isNotBlank(interName)
					&& (getValidateInteresadoInfo(sessionID, interId, null,
							entidad))) {
				try {

					if (interId != 0
							&& flushFdrInter.getDomId() != 0
							&& !getValidateInteresadoInfo(sessionID, interId,
									new Integer(domId), entidad)) {
						domId = 0;
					}

					if (data.isCreate()) {
						if (order == 1) {
							data.setNewAttributeValue("fld9", flushFdrInter
									.getInterName());
						}
					} else {
						if (order == 1) {
							Object oldFld9 = data.getOldAttributeValue("fld9");
							data.setOldAttributeValue("fld9", flushFdrInter
									.getInterName());
							data
									.changedPut(new Integer(9), new Object[] {
											oldFld9,
											data.getOldAttributeValue("fld9") });
						}
					}

					InteresadoManager interesadoManager = (InteresadoManager)ISicresBeansProvider.getInstance().getInteresadoManager();

//					InteresadoManager interesadoManager = (InteresadoManager) SpringApplicationContext
//							.getBean("interesadoManager");

					InteresadoVO interesado = populateInteresado(
							null, bookID.toString(),
							String.valueOf(registerID), flushFdrInter, order++);

					interesadoManager.save(interesado);

					interesadosValidados = true;

				} catch (Exception e) {
					log
							.debug(
									"Error al insertar un tercero en AddInteresadosValidados ",
									e);
				}
			} else if (interId == -1 && !data.isCreate()) {
				data.setOldAttributeValue("fld9", null);
			}
		}
		return interesadosValidados;
	}

	/**
	 * Devuelve una instancia de <code>InteresadoVO</code> con la información
	 * que recibe por parámetro.
	 *
	 * @see InteresadoVO
	 *
	 * @return una instancia de <code>InteresadoVO</code>
	 */
	private static InteresadoVO populateInteresado(String id, String idLibro,
			String idRegistro, FlushFdrInter flushFdrInter, int orden) {
		InteresadoVO interesado = new InteresadoVO();

		interesado.setId(id);
		interesado.setIdLibro(idLibro);
		interesado.setIdRegistro(idRegistro);
		interesado.setNombre(flushFdrInter.getInterName());
		interesado.setOrden(orden);

		// Tercero que actua como interesado
		BaseTerceroVO tercero = new BaseTerceroVO();
		tercero.setId(String.valueOf(flushFdrInter.getInterId()));
		tercero.setNombre(flushFdrInter.getInterName());
		interesado.setTercero(tercero);

		// Direccion de notificacion del interesado
		BaseDireccionVO direccionNotificacion = new BaseDireccionVO();
		direccionNotificacion.setId(String.valueOf(flushFdrInter.getDomId()));
		direccionNotificacion.setDireccion(flushFdrInter.getDirection());
		interesado.setDireccionNotificacion(direccionNotificacion);

		// Representante del interesado
		if (null != flushFdrInter.getRepresentante()) {
			RepresentanteInteresadoVO representanteInteresado = new RepresentanteInteresadoVO();
			representanteInteresado.setInteresado(interesado);

			// Tercero que actua como representante
			BaseTerceroVO representante = new BaseTerceroVO();
			representante.setId(String.valueOf(flushFdrInter.getRepresentante()
					.getInterId()));
			representante.setNombre(flushFdrInter.getInterName());
			representanteInteresado.setRepresentante(representante);
			representanteInteresado.setNombre(flushFdrInter.getRepresentante()
					.getInterName());

			// Direccion de notificacion del representante
			BaseDireccionVO direccionNotificacionRepresentante = new BaseDireccionVO();
			direccionNotificacionRepresentante.setId(String
					.valueOf(flushFdrInter.getRepresentante().getDomId()));
			direccionNotificacionRepresentante.setDireccion(flushFdrInter
					.getRepresentante().getDirection());
			representanteInteresado
					.setDireccionNotificacion(direccionNotificacionRepresentante);

			interesado.setRepresentante(representanteInteresado);
		}

		return interesado;
	}

	/**
	 * Inserta los interesados no validados en la tabla scr_regint y en el campo
	 * de interesados del Registro
	 *
	 * @param entidad
	 * @param sessionID
	 * @param inter
	 * @param bookID
	 * @param registerID
	 * @param data
	 * @throws HibernateException
	 */
	private static void addInteresadosNoValidados(String entidad,
			String sessionID, List inter, Integer bookID, int registerID,
			FolderDataSession data) throws HibernateException {

		int order = 1;
		FlushFdrInter flushFdrInter = null;

		for (Iterator it = inter.iterator(); it.hasNext();) {
			flushFdrInter = (FlushFdrInter) it.next();
			int interId = flushFdrInter.getInterId();
			String interName = flushFdrInter.getInterName();

			int domId = 0;
			if (StringUtils.isNotBlank(interName)) {
				try {
					// Obtener id
					int id = DBEntityDAOFactory.getCurrentDBEntityDAO()
							.getNextIdForInter(new Integer(interId), entidad);

					if (data.isCreate()) {
						if (order == 1) {
							data.setNewAttributeValue("fld9", flushFdrInter
									.getInterName());
						}
					} else {
						if (order == 1) {
							Object oldFld9 = data.getOldAttributeValue("fld9");
							data.setOldAttributeValue("fld9", flushFdrInter
									.getInterName());
							data
									.changedPut(new Integer(9), new Object[] {
											oldFld9,
											data.getOldAttributeValue("fld9") });
						}
					}

					DBEntityDAOFactory.getCurrentDBEntityDAO().insertScrRegInt(
							id, bookID.intValue(), registerID, interName, 0,
							domId, order++, entidad);

				} catch (Exception e) {
					log
							.debug(
									"Error al insertar un tercero en AddInteresadosNoValidados ",
									e);
				}
			} else if (interId == -1 && !data.isCreate()) {
				data.setOldAttributeValue("fld9", null);
			}
		}

	}

	/**
	 * Comprueba si los interesados y sus direcciones son validados o no
	 *
	 * @param sessionID
	 * @param idPerson
	 * @param domID
	 * @param entidad
	 * @return
	 */
	protected static boolean getValidateInteresadoInfo(String sessionID,
			int idPerson, Integer domID, String entidad) {

		String result = null;
		try {
			result = ValidationSessionEx.getDir(sessionID,
					new Integer(idPerson), 0, entidad, 0);
		} catch (Exception e) {
			return false;
		}

		if (StringUtils.isBlank(result)) {
			return false;
		}

		try {
			PersonInfo personInfo = XMLPersons.getPersonInfoFromXMLText(result);

			if (personInfo != null) {
				if (domID == null) {
					return true;
				}

				List doms = personInfo.getAddresses();
				List dirTels = personInfo.getAddressesTel();

				for (Iterator it = doms.iterator(); it.hasNext();) {
					PersonAddress pAddress = (PersonAddress) it.next();
					if (pAddress.getId().equals(domID.toString())) {
						return true;
					}
				}

				for (Iterator it = dirTels.iterator(); it.hasNext();) {
					PersonAddressTel pAddressTel = (PersonAddressTel) it.next();
					if (pAddressTel.getId().equals(domID.toString())) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			return false;
		}

		return false;
	}
}
