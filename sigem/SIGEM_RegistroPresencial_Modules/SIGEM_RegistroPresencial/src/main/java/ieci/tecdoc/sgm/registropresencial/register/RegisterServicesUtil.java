package ieci.tecdoc.sgm.registropresencial.register;

import gnu.trove.THashMap;
import ieci.tecdoc.sgm.core.services.registro.Interested;
import ieci.tecdoc.sgm.registropresencial.autenticacion.User;
import ieci.tecdoc.sgm.registropresencial.utils.Keys;
import ieci.tecdoc.sgm.registropresencial.utils.RBUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.conf.InvesicresConf;
import com.ieci.tecdoc.common.entity.AxSfEntity;
import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.invesdoc.Iuserusertype;
import com.ieci.tecdoc.common.invesicres.ScrAddress;
import com.ieci.tecdoc.common.invesicres.ScrAddrtel;
import com.ieci.tecdoc.common.invesicres.ScrCa;
import com.ieci.tecdoc.common.invesicres.ScrDom;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrRegstate;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfIn;
import com.ieci.tecdoc.common.isicres.AxSfOut;
import com.ieci.tecdoc.common.isicres.AxSfQuery;
import com.ieci.tecdoc.common.isicres.AxSfQueryField;
import com.ieci.tecdoc.common.isicres.AxSfQueryResults;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.BBDDUtils;
import com.ieci.tecdoc.common.utils.ISicresAPerms;
import com.ieci.tecdoc.common.utils.ISicresQueries;
import com.ieci.tecdoc.common.utils.ScrRegStateByLanguage;
import com.ieci.tecdoc.common.utils.ScrRegisterInter;
import com.ieci.tecdoc.idoc.decoder.form.FormFormat;
import com.ieci.tecdoc.idoc.decoder.query.QCtrlDef;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FieldFormat;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrField;
import com.ieci.tecdoc.isicres.session.attributes.AttributesSession;
import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.session.folder.FolderFileSession;
import com.ieci.tecdoc.isicres.session.security.SecuritySession;
import com.ieci.tecdoc.isicres.session.utils.UtilsSession;
import com.ieci.tecdoc.isicres.session.utils.UtilsSessionEx;
import com.ieci.tecdoc.isicres.usecase.book.BookUseCase;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.Validator;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

public class RegisterServicesUtil extends RegisterServicesUtilPrivate {

	private static final Logger log = Logger.getLogger(RegisterServicesUtil.class);

	/**
	 * Obtenemos las acciones que se realizaran con el registro
	 *
	 * @param entidad
	 * @return
	 */
	public static Integer getInvesConfActions(String entidad) {
		InvesicresConf invesicresConf = BookSession.invesicresConf(entidad);
		Integer launchDistOutRegister = null;
		if (invesicresConf != null) {

			launchDistOutRegister = new Integer(BookSession.invesicresConf(
					entidad).getDistSRegister());
		} else {
			launchDistOutRegister = new Integer(0);
		}

		return launchDistOutRegister;
	}

	/**
	 * Este metodo hace las comprobaciones necesarias por las que no se podría
	 * hacer el create.
	 *
	 * Si lanza una excepcion es que no se puede realizar.
	 *
	 * @param sessionID
	 * @param bookId
	 * @param documents
	 * @param user
	 * @param atts
	 * @param entidad
	 * @throws SessionException
	 * @throws TecDocException
	 * @throws BookException
	 * @throws ValidationException
	 */
	public static void canCreateFolder(String sessionID, Integer bookId,
			Map documents, User user, List atts, String entidad,
			boolean isConsolidacion) throws SessionException, TecDocException,
			BookException, ValidationException {
		CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
				sessionID);
		THashMap bookInformation = (THashMap) cacheBag.get(bookId);
		ISicresAPerms aPerms = (ISicresAPerms) bookInformation
				.get(ServerKeys.APERMS_USER);
		if (!aPerms.canCreate()) {
			log.error("El usuario no tiene permisos para crear registros");
			throw new BookException(
					BookException.ERROR_CANNOT_CREATE_NEW_FOLDER);
		} else if (documents != null && !documents.isEmpty()
				&& !aPerms.canModify()) {
			log.error("El usuario no tiene permisos para crear registros");
			throw new BookException(
					BookException.ERROR_CANNOT_CREATE_NEW_FOLDER);
		} else {
			List badCtrls = validateFolder(sessionID, user, bookId, -1, atts,
					documents, entidad, isConsolidacion);
			if (!badCtrls.isEmpty()) {
				log.error("Error en la validación de los datos del registro");
				throw new ValidationException(
						BookException.ERROR_CANNOT_CREATE_NEW_FOLDER);
			}
		}
	}

	/**
	 * Este metodo hace las comprobaciones necesarias por las que no se podría
	 * hacer el update.
	 *
	 * Si lanza una excepcion es que no se puede realizar el update
	 *
	 * @param sessionID
	 * @param bookId
	 * @param folderIdInt
	 * @param user
	 * @param changeFields
	 * @param documents
	 * @param entidad
	 * @param isConsolidacion
	 * @throws SessionException
	 * @throws TecDocException
	 * @throws BookException
	 * @throws ValidationException
	 */
	public static void canUpdateFolder(String sessionID, Integer bookId,
			int folderIdInt, User user, List changeFields, Map documents,
			String entidad) throws SessionException, TecDocException,
			BookException, ValidationException {
		CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
				sessionID);

		THashMap bookInformation = (THashMap) cacheBag.get(bookId);
		ISicresAPerms aPerms = (ISicresAPerms) bookInformation
				.get(ServerKeys.APERMS_USER);
		if (!aPerms.canModify()) {
			log.error("El usuario no tiene permisos para modificar registros");
			throw new BookException(BookException.ERROR_UPDATE_FOLDER);
		}
		if (folderIdInt == -1) {
			log.error("El identificador del registro no es válido");
			throw new BookException(BookException.ERROR_UPDATE_FOLDER);
		}

		List badCtrls = validateFolder(sessionID, user, bookId, folderIdInt,
				changeFields, documents, entidad, false);
		if (!badCtrls.isEmpty()) {
			log.error("Error en la validación de los datos del registro");
			throw new ValidationException(
					ValidationException.ERROR_ATTRIBUTE_NOT_FOUND);
		}

	}

	/**
	 * Este metodo devuelve el formato de los atributos del libro con el que
	 * estamos trabajando
	 *
	 * @param sessionID
	 * @param bookId
	 * @return
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static FieldFormat getFieldFormat(String sessionID, Integer bookId)
			throws BookException, SessionException, ValidationException {
		Idocarchdet idocarchdet = BookSession.getIdocarchdetFld(sessionID,
				bookId);
		FieldFormat fieldFormat = new FieldFormat(idocarchdet.getDetval());

		return fieldFormat;
	}

	/**
	 * Obtenemos los atributos que tienen sustituto
	 *
	 * @param changeFields
	 * @param axsfQ
	 * @param sessionID
	 * @param bookId
	 * @param entidad
	 * @return
	 * @throws AttributesException
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static Map getFieldsWithSustitute(List fields, AxSf axsfQ,
			String sessionID, Integer bookId, String entidad)
			throws AttributesException, BookException, SessionException,
			ValidationException {
		Map idsToTranslate = new HashMap();
		if (fields != null) {
			for (Iterator it = fields.iterator(); it.hasNext();) {
				FlushFdrField flushFdrField = (FlushFdrField) it.next();
				if (flushFdrField.getFldid() == 5
						|| flushFdrField.getFldid() == 7
						|| flushFdrField.getFldid() == 8) {
					idsToTranslate.put(new Integer(flushFdrField.getFldid()),
							flushFdrField.getValue());
				}
				if (axsfQ instanceof AxSfIn
						&& (flushFdrField.getFldid() == 13 || flushFdrField
								.getFldid() == 16)) {
					idsToTranslate.put(new Integer(flushFdrField.getFldid()),
							flushFdrField.getValue());
				}
				if (axsfQ instanceof AxSfOut
						&& (flushFdrField.getFldid() == 12)) {
					idsToTranslate.put(new Integer(flushFdrField.getFldid()),
							flushFdrField.getValue());
				}
			}
		}

		Map translatedIds = AttributesSession
				.translateFixedValuesForSaveOrUpdate(sessionID, bookId,
						idsToTranslate, entidad);

		return translatedIds;
	}

	/**
	 * Inicializamos el registro del tipo correspondiente, registro de entrada o
	 * de salida
	 *
	 * @param user
	 * @param axsfQ
	 * @return
	 */
	public static AxSf initInOrOutFolder(User user, AxSf axsfQ) {
		AxSf newAxSF = null;
		if (axsfQ instanceof AxSfIn) {
			newAxSF = new AxSfIn();
			newAxSF.setLiteralBookType(RBUtil.getInstance(user.getLocale())
					.getProperty(Keys.I18N_BOOKUSECASE_NODE_INBOOK_NAME));
		} else {
			newAxSF = new AxSfOut();
			newAxSF.setLiteralBookType(RBUtil.getInstance(user.getLocale())
					.getProperty(Keys.I18N_BOOKUSECASE_NODE_OUTBOOK_NAME));
		}

		return newAxSF;
	}

	/**
	 * Completamos el registro que se va a crear/actualizar con los atributos
	 * que le correspondes
	 *
	 * @param translatedIds
	 * @param axsfQ
	 * @param newAxSF
	 * @param locale
	 * @param changeFields
	 * @param fieldFormat
	 * @return
	 * @throws ParseException
	 */
	public static AxSf completeFolder(Map translatedIds, AxSf axsfQ,
			AxSf newAxSF, Locale locale, List changeFields,
			FieldFormat fieldFormat, boolean consolidacion)
			throws ParseException {

		newAxSF = addTranslateAttributes(translatedIds, newAxSF);

		SimpleDateFormat longFormatter = new SimpleDateFormat(RBUtil
				.getInstance(locale).getProperty(Keys.I18N_DATE_LONGFORMAT));
		SimpleDateFormat shortFormatter = new SimpleDateFormat(RBUtil
				.getInstance(locale).getProperty(Keys.I18N_DATE_SHORTFORMAT));

		if (changeFields != null) {
			BookUseCase bookUseCase = new BookUseCase();
			for (Iterator it = changeFields.iterator(); it.hasNext();) {
				FlushFdrField flushFdrField = (FlushFdrField) it.next();
				if (axsfQ instanceof AxSfIn) {
					newAxSF = addField2AxSfIn(bookUseCase, flushFdrField, axsfQ, newAxSF,
							locale, longFormatter, shortFormatter, fieldFormat);
				}
				if (axsfQ instanceof AxSfOut) {
					newAxSF = addField2AxSfOut(bookUseCase, flushFdrField, axsfQ, newAxSF,
							locale, longFormatter, shortFormatter, fieldFormat);
				}

				newAxSF = addField2AxSf(flushFdrField, newAxSF, longFormatter,
						shortFormatter, consolidacion);

			}
		}
		newAxSF.setLocale(locale);

		return newAxSF;
	}

	public static AxSf completeFolderImport(Map translatedIds, AxSf axsfQ,
			AxSf newAxSF, Locale locale, List changeFields,
			FieldFormat fieldFormat) throws BookException, ParseException {
		newAxSF = addTranslateAttributes(translatedIds, newAxSF);

		SimpleDateFormat longFormatter = new SimpleDateFormat(RBUtil
				.getInstance(locale).getProperty(Keys.I18N_DATE_LONGFORMAT));
		SimpleDateFormat shortFormatter = new SimpleDateFormat(RBUtil
				.getInstance(locale).getProperty(Keys.I18N_DATE_SHORTFORMAT));

		if (changeFields != null) {
			BookUseCase bookUseCase = new BookUseCase();
			for (Iterator it = changeFields.iterator(); it.hasNext();) {
				FlushFdrField flushFdrField = (FlushFdrField) it.next();
				if (flushFdrField.getFldid() == 1
						&& (flushFdrField.getValue() == null || flushFdrField
								.getValue().equals(""))) {
					throw new BookException(
							BookException.ERROR_CANNOT_CREATE_NEW_FOLDER);
				} else if (flushFdrField.getFldid() == 1
						&& flushFdrField.getValue() != null
						&& !flushFdrField.getValue().equals("")) {
					newAxSF.addAttributeName("fld" + flushFdrField.getFldid());
					newAxSF.addAttributeValue("fld" + flushFdrField.getFldid(),
							flushFdrField.getValue());
				}
				if (flushFdrField.getFldid() == 3
						|| flushFdrField.getFldid() == 6) {
					newAxSF.addAttributeName("fld" + flushFdrField.getFldid());
					newAxSF.addAttributeValue("fld" + flushFdrField.getFldid(),
							flushFdrField.getValue());
				}

				if (axsfQ instanceof AxSfIn) {
					newAxSF = addField2AxSfIn(bookUseCase, flushFdrField, axsfQ, newAxSF,
							locale, longFormatter, shortFormatter, fieldFormat);
				}
				if (axsfQ instanceof AxSfOut) {
					newAxSF = addField2AxSfOut(bookUseCase, flushFdrField, axsfQ, newAxSF,
							locale, longFormatter, shortFormatter, fieldFormat);
				}

				newAxSF = addField2AxSf(flushFdrField, newAxSF, longFormatter,
						shortFormatter, false);
			}
		}
		newAxSF.setLocale(locale);

		return newAxSF;
	}

	/**
	 *
	 * @param sessionID
	 * @param axsfQuery
	 * @param bookIds
	 * @param entidad
	 * @return
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static AxSfQueryResults openRegistersQuery(String sessionID,
			AxSfQuery axsfQuery, List bookIds, String entidad)
			throws BookException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_NotNull(axsfQuery,
				ValidationException.ATTRIBUTE_AXSFQUERY);

		Transaction tran = null;
		AxSfQueryResults queryResults = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HibernateKeys.HIBERNATE_Iuseruserhdr);
			Iuserusertype userusertype = (Iuserusertype) cacheBag
					.get(HibernateKeys.HIBERNATE_Iuserusertype);
			ScrOfic scrofic = (ScrOfic) cacheBag
					.get(HibernateKeys.HIBERNATE_ScrOfic);

			int auxTotalSize = 0;
			AxSfEntity axSfEntity = new AxSfEntity();
			Integer finalBookId = null;
			ISicresAPerms aPerms = new ISicresAPerms();

			for (Iterator it = bookIds.iterator(); it.hasNext();) {
				Integer auxBookId = (Integer) it.next();
				ScrRegstate scrRegstate = ISicresQueries.getScrRegstate(
						session, auxBookId);
				axsfQuery.setBookId(auxBookId);
				AxSf axsfBookIds = BBDDUtils.getTableSchemaFromDatabase(
						auxBookId.toString(), entidad);
				UtilsSession.getAPerms(session, auxBookId, user.getId(),
						userusertype.getType(), user.getDeptid(), aPerms,
						scrRegstate, scrofic);
				int totalSize = axSfEntity.calculateQuerySize(axsfQuery,
						axsfBookIds, null, entidad);
				if (totalSize > 0) {
					finalBookId = auxBookId;
				}
				auxTotalSize = auxTotalSize + totalSize;
			}
			axsfQuery.setBookId(finalBookId);
			axsfQuery.setPageSize(1);
			if (auxTotalSize > 0) {
				queryResults = new AxSfQueryResults(axsfQuery.getBookId(),
						auxTotalSize, axsfQuery.getPageSize());
			}

			HibernateUtil.commitTransaction(tran);

			return queryResults;
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

	/**
	 * Obtenemos el formato de busqueda del registro
	 *
	 * @param sessionID
	 * @param bookId
	 * @param entidad
	 * @return
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static AxSf getQueryFormat(String sessionID, Integer bookId,
			String entidad) throws BookException, SessionException,
			ValidationException {
		AxSf axsfQ = BookSession.getQueryFormat(sessionID, bookId, entidad);

		// Cambiamos los tipos de datos a String porque nos vienen codes y no id
		axsfQ.addAttributeClass(Keys.XML_FLD_TEXT + Integer.toString(5),
				String.class.getName());
		axsfQ.addAttributeClass(Keys.XML_FLD_TEXT + Integer.toString(7),
				String.class.getName());
		axsfQ.addAttributeClass(Keys.XML_FLD_TEXT + Integer.toString(8),
				String.class.getName());

		if (axsfQ instanceof AxSfIn) {
			axsfQ.addAttributeClass(Keys.XML_FLD_TEXT + Integer.toString(13),
					String.class.getName());
			axsfQ.addAttributeClass(Keys.XML_FLD_TEXT + Integer.toString(16),
					String.class.getName());
		} else {
			axsfQ.addAttributeClass(Keys.XML_FLD_TEXT + Integer.toString(12),
					String.class.getName());
		}

		return axsfQ;

	}

	/**
	 *
	 * @param sessionID
	 * @param bookId
	 * @param atts
	 * @param entidad
	 * @param locale
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 * @throws AttributesException
	 */
	public static void validateQuery(String sessionID, Integer bookId,
			AxSf axsfQ, List atts, String entidad, Locale locale,
			Collection formatterFields) throws BookException, SessionException,
			ValidationException, AttributesException {

		if ((atts != null) && (!atts.isEmpty())) {
			List result = new ArrayList();

			SimpleDateFormat shortFormatter = new SimpleDateFormat(RBUtil
					.getInstance(locale)
					.getProperty(Keys.I18N_DATE_SHORTFORMAT));
			shortFormatter.setLenient(false);

			for (Iterator iterator = atts.iterator(); iterator.hasNext();) {
				AxSfQueryField field = (AxSfQueryField) iterator.next();

				for (Iterator it = formatterFields.iterator(); it.hasNext();) {
					QCtrlDef ctrlDef = (QCtrlDef) it.next();
					int fldid = ctrlDef.getFldId();
					int id = ctrlDef.getId();

					if (ctrlDef.getName().startsWith(Keys.IDOC_EDIT)) {
						if (field.getFldId().equals(String.valueOf(fldid))) {
							result.addAll(validateQueryFields(field, id, fldid,
									locale, sessionID, axsfQ, bookId,
									shortFormatter, entidad));
							break;
						}
					}
				}
			}

			if ((result != null) && (!result.isEmpty())) {
				throw new BookException(
						BookException.ERROR_CANNOT_FIND_REGISTERS);
			}
		} else {
			throw new BookException(BookException.ERROR_CANNOT_FIND_REGISTERS);
		}
	}

	/**
	 *
	 * Devuelve un mapa con los campos y los valores sustituyendo  
	 *
	 * @param sessionID
	 * @param bookId
	 * @param axsfQ
	 * @param atts
	 * @param entidad
	 * @param locale
	 * @param formatterFields
	 * @return
	 * @throws AttributesException
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static Map getQueryFieldsWithSustitute(String sessionID,
			Integer bookId, AxSf axsfQ, List atts, String entidad,
			Locale locale, Collection formatterFields)
			throws AttributesException, BookException, SessionException,
			ValidationException {

		Map idsToValidate = new HashMap();
		Map result = new HashMap();

		for (Iterator iterator = atts.iterator(); iterator.hasNext();) {
			AxSfQueryField field = (AxSfQueryField) iterator.next();

			for (Iterator it = formatterFields.iterator(); it.hasNext();) {
				QCtrlDef ctrlDef = (QCtrlDef) it.next();
				int fldid = ctrlDef.getFldId();
				if (ctrlDef.getName().startsWith(Keys.IDOC_EDIT)) {
					if (field.getFldId().equals(String.valueOf(fldid))) {
						idsToValidate.putAll(translateQueryFields(field, fldid,
								axsfQ));
						
						break;
					}
				}
			}
		}
		if (!idsToValidate.isEmpty()) {
			result = AttributesSession.translateFixedValues(sessionID, bookId,
					idsToValidate, entidad);
		}
		return result;

	}

	/**
	 *
	 * Metodo que termina de componer la busqueda a realizar: indica la
	 * ordenacion, traduce los caracteres especiales, aplica CS en los casos que
	 * corresponda...
	 *
	 * @param sessionID
	 * @param bookId
	 * @param atts - Listado de campos
	 * @param axsfQ
	 * @param translations - Coleccion con los campos con sustituto
	 * @param locale
	 *
	 * @return {@link AxSfQuery}
	 */
	public static AxSfQuery getQueryFolder(String sessionID, Integer bookId,
			List atts, AxSf axsfQ, Map translations, Locale locale,
			String entidad) {
		AxSfQuery axsfQuery = new AxSfQuery();
		axsfQuery.setOrderBy("fld1");
		axsfQuery.setBookId(bookId);

		boolean isDataBaseCaseSentitive = true;

		try {
			isDataBaseCaseSentitive = UtilsSession
					.isDataBaseCaseSensitive(entidad);
		} catch (Exception e1) {
		}

		for (Iterator iterator = atts.iterator(); iterator.hasNext();) {
			try {
				AxSfQueryField field = (AxSfQueryField) iterator.next();
				if (field.getOperator().equalsIgnoreCase("OR")
						|| field
								.getOperator()
								.equalsIgnoreCase(
										com.ieci.tecdoc.common.isicres.Keys.QUERY_OR_TEXT_VALUE)) {
					field
							.setOperator(com.ieci.tecdoc.common.isicres.Keys.QUERY_OR_TEXT_VALUE);
				} else if (field.getOperator().equalsIgnoreCase("BEETWEN")
						|| field
								.getOperator()
								.equalsIgnoreCase(
										com.ieci.tecdoc.common.isicres.Keys.QUERY_BETWEEN_TEXT_VALUE)) {
					field
							.setOperator(com.ieci.tecdoc.common.isicres.Keys.QUERY_BETWEEN_TEXT_VALUE);
				} else if (field.getOperator().equalsIgnoreCase("LIKE")
						|| field
								.getOperator()
								.equalsIgnoreCase(
										com.ieci.tecdoc.common.isicres.Keys.QUERY_LIKE_TEXT_VALUE)) {
					field
							.setOperator(com.ieci.tecdoc.common.isicres.Keys.QUERY_LIKE_TEXT_VALUE);
				}
				field.setValue(getQueryFieldValue(field, axsfQ, translations,
						locale, isDataBaseCaseSentitive));
				field.setBookId(bookId);
				axsfQuery.addField(field);
			} catch (ParseException e) {
				if(log.isDebugEnabled()){
					log.debug("Error ParseException en getQueryFolder" , e);
				}
			}
		}

		return axsfQuery;
	}

	/**
	 *
	 * @param folderNumber
	 * @return
	 */
	public static AxSfQuery getAxSfQueryFolderNumber(String folderNumber) {
		AxSfQueryField field = new AxSfQueryField();
		field.setFldId(Keys.XML_FLD_TEXT + new Integer(1).toString());
		field.setValue(folderNumber);
		field
				.setOperator(com.ieci.tecdoc.common.isicres.Keys.QUERY_EQUAL_TEXT_VALUE);

		AxSfQuery axsfQuery = new AxSfQuery();
		axsfQuery.addField(field);

		return axsfQuery;
	}

	/**
	 *
	 * @param sessionID
	 * @param type
	 * @param oficAsoc
	 * @param entidad
	 * @return
	 * @throws ValidationException
	 * @throws SessionException
	 * @throws BookException
	 */
	public static List getBookIdList(String sessionID, int type,
			boolean oficAsoc, Locale locale, String entidad)
			throws BookException, SessionException, ValidationException {
		List bookIds = new ArrayList();
		if (type == 1) {
			List inList = BookSession.getInBooks(sessionID, oficAsoc, locale,
					entidad);
			for (Iterator it = inList.iterator(); it.hasNext();) {
				// ScrRegstate scrregstate = (ScrRegstate) it.next();
				// if (scrregstate.getState() == 0) {
				// bookIds.add(scrregstate.getIdocarchhdr().getId());
				// }
				ScrRegStateByLanguage book = (ScrRegStateByLanguage) it.next();
				if (book.getScrregstateState() == 0) {
					bookIds.add(book.getIdocarchhdrId());
				}
			}
		} else {
			List outList = BookSession.getOutBooks(sessionID, oficAsoc, locale,
					entidad);
			for (Iterator it = outList.iterator(); it.hasNext();) {
				// ScrRegstate scrregstate = (ScrRegstate) it.next();
				// if (scrregstate.getState() == 0) {
				// bookIds.add(scrregstate.getIdocarchhdr().getId());
				// }
				ScrRegStateByLanguage book = (ScrRegStateByLanguage) it.next();
				if (book.getScrregstateState() == 0) {
					bookIds.add(book.getIdocarchhdrId());
				}
			}
		}

		return bookIds;
	}

	public static List getBookList(String sessionID, int type,
			boolean oficAsoc, boolean onlyOpenBooks, int perm, Locale locale,
			String entidad) throws BookException, SessionException,
			ValidationException {
		List books = new ArrayList();
		if (type == 1) {
			List inList = BookSession.getInBooks(sessionID, oficAsoc, perm,
					locale, entidad);
			for (Iterator it = inList.iterator(); it.hasNext();) {
				// ScrRegstate scrregstate = (ScrRegstate) it.next();
				// if (onlyOpenBooks) {
				// if (scrregstate.getState() == 0) {
				// books.add(scrregstate);
				// }
				// } else {
				// books.add(scrregstate);
				// }
				ScrRegStateByLanguage book = (ScrRegStateByLanguage) it.next();
				if (onlyOpenBooks) {
					if (book.getScrregstateState() == 0) {
						books.add(book);
					}
				} else {
					books.add(book);
				}
			}
		} else {
			List outList = BookSession.getOutBooks(sessionID, oficAsoc, perm,
					locale, entidad);
			for (Iterator it = outList.iterator(); it.hasNext();) {
				// ScrRegstate scrregstate = (ScrRegstate) it.next();
				// if (onlyOpenBooks) {
				// if (scrregstate.getState() == 0) {
				// books.add(scrregstate);
				// }
				// } else {
				// books.add(scrregstate);
				// }
				ScrRegStateByLanguage book = (ScrRegStateByLanguage) it.next();
				if (onlyOpenBooks) {
					if (book.getScrregstateState() == 0) {
						books.add(book);
					}
				} else {
					books.add(book);
				}
			}
		}

		return books;
	}

	/**
	 *
	 * @param sessionID
	 * @param queryResults
	 * @param axsfQuery
	 * @param locale
	 * @param entidad
	 * @return
	 * @throws ValidationException
	 * @throws SecurityException
	 * @throws BookException
	 * @throws SessionException
	 * @throws AttributesException
	 * @throws TecDocException
	 */
	public static Map getFolderInfoByFolderNumber(String sessionID,
			AxSfQueryResults queryResults, AxSfQuery axsfQuery, Locale locale,
			String entidad) throws ValidationException, SecurityException,
			BookException, SessionException, AttributesException,
			TecDocException {

		BookSession.openBook(sessionID, queryResults.getBookId(), entidad);
		CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
				sessionID);
		THashMap bookInformation = (THashMap) cacheBag.get(queryResults
				.getBookId());
		Idocarchdet idoc = (Idocarchdet) bookInformation
				.get(IDocKeys.IDOCARCHDET_FLD_DEF_ASOBJECT);
		ScrRegstate scrRegstate = (ScrRegstate) bookInformation
				.get(HibernateKeys.HIBERNATE_ScrRegstate);
		FieldFormat fieldFormat = new FieldFormat(idoc.getDetval());
		Map fldDefs = fieldFormat.getFlddefs();

		queryResults = Register.navigateRegistersQuery(sessionID, queryResults,
				axsfQuery,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_FIRST_PAGE, locale,
				entidad);

		AxSf axsfQ = BookSession.getFormFormat(sessionID, queryResults
				.getBookId(), entidad);
		Integer bookID = queryResults.getBookId();
		String bookId = queryResults.getBookId().intValue() + " - "
				+ scrRegstate.getIdocarchhdr().getName();

		Map folderInfo = new HashMap();

		for (Iterator it = queryResults.getResults().iterator(); it.hasNext();) {
			AxSf axsf = (AxSf) it.next();
			axsf.setFormat(axsfQ.getFormat());
			axsf.setLenFields(axsfQ.getLenFields());

			String data = axsf.getFormat().getData();
			FormFormat formFormat = new FormFormat(data);
			TreeMap pages = formFormat.getDlgDef().getPagedefs();

			int numPages = pages.values().size();
			int fdrid = Integer.parseInt(axsf.getAttributeValue("fdrid")
					.toString());

			folderInfo.put("bookId", bookId);
			folderInfo.put("fdrid", new Integer(fdrid).toString());

			for (int i = 0; i < numPages; i++) {
				Map folderInfoAux = new HashMap();
				Map extendedValues = new HashMap();
				extendedValues = Register.getValidationFields(axsf, sessionID,
						queryResults.getBookId(), i, locale, entidad);
				String origen = axsf.getFld7Name();
				String destino = axsf.getFld8Name();
				axsf.setAttributeValue("fld9", getDest(sessionID, queryResults
						.getBookId(), fdrid, entidad));

				folderInfoAux = ConsultRegister.consultFolderInfo(axsf,
						queryResults.getBookId(), i, locale, extendedValues,
						origen, destino, fldDefs);
				folderInfo.putAll(folderInfoAux);
			}
			// List documents = FolderFileSession.getBookFolderDocsWithPages(
			// sessionID, bookID, fdrid, entidad);
			boolean permShowDocuments = SecuritySession.permisionShowDocuments(
					sessionID, axsf);
			List documents = null;
			if (permShowDocuments) {
				documents = FolderFileSession.getBookFolderDocsWithPages(
						sessionID, bookID, fdrid, entidad);
			}
			if (documents != null) {
				folderInfo.put("documents", documents);
			}
			break;
		}

		return folderInfo;
	}

	public static List getOfficeCanCreateRegister(String sessionID,
			Integer bookID, String entidad) throws BookException,
			SecurityException, SessionException, TecDocException,
			ValidationException {

		BookSession.openBook(sessionID, bookID, entidad);

		List oficList = null;
		if (SecuritySession.isSuperuser(sessionID)) {
			oficList = UtilsSessionEx.getAllScrOficByUser(sessionID, entidad);
		} else if (SecuritySession.isBookAdmin(sessionID, bookID)) {
			oficList = UtilsSessionEx.getAllScrOficByUser(sessionID, entidad);
		} else if (SecuritySession.canCreate(sessionID, bookID)) {
			AuthenticationUser user = SecuritySession.getUserLogin(sessionID);

			if (user != null) {
				if (SecuritySession.canCreate(sessionID, bookID, true, user
						.getId(), entidad)) {
					oficList = UtilsSessionEx.getAllScrOficByUser(sessionID,
							entidad);
				} else {
					List deptList = user.getDeptList();
					if (deptList != null && !deptList.isEmpty()) {
						oficList = new ArrayList();
						for (Iterator iterator = deptList.iterator(); iterator
								.hasNext();) {
							Integer deptId = (Integer) iterator.next();

							if (SecuritySession.canCreate(sessionID, bookID,
									false, deptId, entidad)) {
								ScrOfic scrOfic = UtilsSessionEx
										.getScrOficByDeptId(sessionID, deptId,
												entidad);

								if (scrOfic != null) {
									oficList.add(scrOfic);
								}
							}
						}
					}
				}
			}
		}

		return oficList;

	}

	public static Interested[] getInterestedForFolder(String sessionID,
			Integer bookId, Integer fdrId, String entidad) throws Exception {
		Interested[] participants = null;

		// Transaction tran = null;
		try {
			// Session session = HibernateUtil.currentSession(entidad);
			// tran = session.beginTransaction();

			// List scrRegIntList = ISicresQueries.getScrRegInt(session, bookId,
			// fdrId.intValue());
			List scrRegIntList = DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getScrRegisterInter(bookId, fdrId.intValue(), true,
							entidad);
			if (scrRegIntList == null || scrRegIntList.isEmpty()) {
				return null;
			}

			participants = new Interested[scrRegIntList.size()];
			for (int i = 0; i < scrRegIntList.size(); i++) {
				// ScrRegint scrRegInt = (ScrRegint) scrRegIntList.get(i);
				ScrRegisterInter scrRegInt = (ScrRegisterInter) scrRegIntList
						.get(i);
				participants[i] = new Interested(String.valueOf(scrRegInt
						.getPersonId()), scrRegInt.getName(), scrRegInt
						.getAddressId().toString());

				if (scrRegInt.getAddressId() != null
						&& scrRegInt.getAddressId().intValue() != 0) {
					ScrAddress scrAddress = UtilsSessionEx.getInterAddress(
							sessionID, scrRegInt.getPersonId(), scrRegInt
									.getAddressId(), entidad);
					if ((scrAddress != null) && (scrAddress.getId() != null)) {
						participants[i].setAddressId(String.valueOf(scrAddress
								.getId()));

						// Obtenemos la direccion por defecto
						if (scrAddress.getType() == 0) {
							ScrDom scrDom = UtilsSessionEx
									.getInterDom(
											sessionID,
											scrRegInt.getPersonId().intValue(),
											scrRegInt.getAddressId().intValue(),
											entidad);
							participants[i].setAddress(scrDom.getAddress());
						} else if (scrAddress.getType() != 0) {
							ScrAddrtel scrAddrtel = UtilsSessionEx
									.getInterAddrtel(
											sessionID,
											scrRegInt.getPersonId().intValue(),
											scrRegInt.getAddressId().intValue(),
											entidad);
							participants[i].setAddress(scrAddrtel.getAddress());
						}
					}
				} else {
					participants[i].setAddress(" ");
				}
			}

			// HibernateUtil.commitTransaction(tran);
			return participants;
		} catch (Exception e) {
			// HibernateUtil.rollbackTransaction(tran);
			throw e;
			// } finally {
			// HibernateUtil.closeSession(entidad);
		}
	}

	/**
	 * Comprueba si un tipo de asunto está diponible para una oficina
	 * @param sessionID Cadena que contiene el identificador de la sesión
	 * @param matterTypeCode Cadena que contiene el tipo de asunto
	 * @param officeCode Cadena que contiene el código de oficina
	 * @param entidad Cadena que contiene el identificador de la entidad
	 * @return Booleano
	 * 					- true Si el tipo de asunto está disponible para una oficina
	 * 					- false Si el tipo de asunto no está diponible para una oficina
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static boolean existMatterTypeInOffice(String sessionID,
			String matterTypeCode, String officeCode, String entidad)
			throws  SessionException, ValidationException {

		ScrCa scrCa = UtilsSessionEx.getScrCaByOfic(sessionID, matterTypeCode,officeCode, entidad);

		if(scrCa != null){
			return true;
		}
		return false;
	}

}
