/**
 *
 */
package ieci.tecdoc.sgm.registropresencial.register;

import ieci.tecdoc.sgm.registropresencial.autenticacion.User;
import ieci.tecdoc.sgm.registropresencial.utils.Keys;
import ieci.tecdoc.sgm.registropresencial.utils.RBUtil;

import java.math.BigDecimal;
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

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfIn;
import com.ieci.tecdoc.common.isicres.AxSfOut;
import com.ieci.tecdoc.common.isicres.AxSfQueryField;
import com.ieci.tecdoc.common.utils.ScrRegisterInter;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FFldDef;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FieldFormat;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrDocument;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrField;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrPage;
import com.ieci.tecdoc.isicres.session.attributes.AttributesSession;
import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.session.utils.UtilsSessionEx;
import com.ieci.tecdoc.isicres.usecase.book.BookUseCase;

/**
 * @author 66575267
 *
 */
public class RegisterServicesUtilPrivate {

	private static final Logger log = Logger.getLogger(RegisterServicesUtilPrivate.class);

	private static final String DEFAULT_FLD = ieci.tecdoc.sgm.registropresencial.utils.Keys.FLD;

	/**
	 * Añadimos al registro los atributos que son sustitutos
	 *
	 * @param translatedIds
	 * @param newAxSF
	 * @return
	 */
	protected static AxSf addTranslateAttributes(Map translatedIds, AxSf newAxSF) {
		Integer id = null;
		for (Iterator it = translatedIds.keySet().iterator(); it.hasNext();) {
			id = (Integer) it.next();
			newAxSF.addAttributeName("fld" + id.toString());
			newAxSF.addAttributeValue("fld" + id.toString(), translatedIds
					.get(id));
		}

		return newAxSF;
	}

	/**
	 * Añadimos los atributos que son de tanto de registro de entrada como de
	 * salida
	 *
	 * @param flushFdrField
	 * @param newAxSF
	 * @param longFormatter
	 * @param shortFormatter
	 * @return
	 * @throws ParseException
	 */
	protected static AxSf addField2AxSf(FlushFdrField flushFdrField,
			AxSf newAxSF, SimpleDateFormat longFormatter,
			SimpleDateFormat shortFormatter, boolean consolidacion)
			throws ParseException {
		int fldId = flushFdrField.getFldid();
		if ((fldId == 1) && consolidacion) {
			newAxSF.addAttributeName("fld" + flushFdrField.getFldid());
			newAxSF.addAttributeValue("fld" + flushFdrField.getFldid(),
					flushFdrField.getValue());
		}
		if (fldId == 2) {
			newAxSF.addAttributeName("fld" + flushFdrField.getFldid());
			if (flushFdrField.getValue() != null) {
				newAxSF.addAttributeValue("fld" + flushFdrField.getFldid(),
						longFormatter.parse(flushFdrField.getValue()));
			} else {
				newAxSF.addAttributeValue("fld" + flushFdrField.getFldid(),
						flushFdrField.getValue());
			}
		}
		if (fldId == 4) {
			newAxSF.addAttributeName("fld" + flushFdrField.getFldid());
			if (flushFdrField.getValue() != null) {
				newAxSF.addAttributeValue("fld" + flushFdrField.getFldid(),
						shortFormatter.parse(flushFdrField.getValue()));
			} else {
				newAxSF.addAttributeValue("fld" + flushFdrField.getFldid(),
						flushFdrField.getValue());
			}
		}

		return newAxSF;
	}

	/**
	 * Metodo que comprueba que los campos introducidos son correctos
	 *
	 * @param sessionID
	 * @param user
	 * @param bookId
	 * @param fdrid
	 * @param atts - Listados de campos
	 * @param documents
	 * @param entidad
	 * @param isConsolidacion
	 *
	 * @return List de campos erroneos
	 *
	 * @throws ValidationException
	 * @throws SecurityException
	 * @throws AttributesException
	 * @throws BookException
	 * @throws SessionException
	 */
	protected static List validateFolder(String sessionID, User user,
			Integer bookId, int fdrid, List atts, Map documents,
			String entidad, boolean isConsolidacion)
			throws ValidationException, SecurityException, AttributesException,
			BookException, SessionException {

		//Validamos los documentos
		validateDocuments(documents);

		Map ctrlIds = new HashMap();
		if ((atts != null) && (!atts.isEmpty())) {
			for (Iterator it = atts.iterator(); it.hasNext();) {
				FlushFdrField flushFdrField = (FlushFdrField) it.next();
				ctrlIds.put(new Integer(flushFdrField.getFldid()), new Integer(
						flushFdrField.getCtrlid()));
			}
		}

		AxSf axsfQ = BookSession.getFormFormat(sessionID, bookId, entidad);
		Idocarchdet idocarchdet = BookSession.getIdocarchdetFld(sessionID,
				bookId);
		FieldFormat fieldFormat = new FieldFormat(idocarchdet.getDetval());

		Map idsToValidate = new HashMap();
		List preResult = new ArrayList();
		SimpleDateFormat longFormatter = new SimpleDateFormat(
				RBUtil
						.getInstance(user.getLocale())
						.getProperty(
								ieci.tecdoc.sgm.registropresencial.utils.Keys.I18N_DATE_LONGFORMAT));
		longFormatter.setLenient(false);
		SimpleDateFormat shortFormatter = new SimpleDateFormat(
				RBUtil
						.getInstance(user.getLocale())
						.getProperty(
								ieci.tecdoc.sgm.registropresencial.utils.Keys.I18N_DATE_SHORTFORMAT));
		shortFormatter.setLenient(false);

		boolean dateError = fdrid != -1;
		validateFolder(sessionID, bookId, atts, axsfQ, fdrid, shortFormatter,
				longFormatter, preResult, idsToValidate, user.getLocale(),
				fieldFormat, entidad, dateError, isConsolidacion);

		if (!idsToValidate.isEmpty()) {
			preResult.addAll(AttributesSession.validateFixedValues(sessionID,
					bookId, idsToValidate, true, entidad));
		}

		return getResultValidateFolder(preResult, fieldFormat);
	}

	/**
	 * Metodo que valida los documentos
	 *
	 * @param documents - Collection de Documentos ({@link FlushFdrDocument})
	 * @throws ValidationException
	 */
	protected static void validateDocuments(Map documents) throws ValidationException{
		//comprobamos si vienen documentos
		if (documents != null) {
			for (Iterator itDoc = documents.keySet().iterator(); itDoc.hasNext();) {
				String key = (String) itDoc.next();
				FlushFdrDocument document = (FlushFdrDocument) documents.get(key);

					//validamos el nombre del documento no sea mayor a 32 caracteres
				if(document.getDocumentName().length() > 32){
					if(log.isDebugEnabled()){
						log.debug("Error en la longitud del nombre del documento ["+ document.getDocumentName() +"]");
					}
					throw new ValidationException(ValidationException.ERROR_DOCUMENT_NAME_LENGTH);
				}

				//validamos las paginas del documento
				validatePagesDocument(document.getPages());
			}
		}
	}

	/**
	 * Metodo que valida las paginas de un documento
	 *
	 * @param pages - Listado de objetos tipo {@link FlushFdrPage}
	 * @throws ValidationException
	 */
	private static void validatePagesDocument(List pages)
			throws ValidationException {
		//validamos si vienen paginas
		if(pages != null){
			//si es asi comprobamos cada pagina
			for(Iterator itPages = pages.iterator(); itPages.hasNext();){
				FlushFdrPage flushFdrPage = (FlushFdrPage) itPages.next();

				//validamos el nombre de las paginas del documento para que no sean mayor a 32 caracteres
				if(flushFdrPage.getPageName().length() > 64){
					if(log.isDebugEnabled()){
						log.debug("Error en la longitud del nombre de la pagina ["+ flushFdrPage.getPageName() +"]");
					}
					throw new ValidationException(ValidationException.ERROR_PAGE_NAME_LENGTH);
				}
			}
		}
	}


	/**
	 * Comprobamos que los atributos de busqueda son correctos
	 *
	 * @param field
	 * @param id
	 * @param fldid
	 * @param locale
	 * @param sessionID
	 * @param axsfQ
	 * @param bookId
	 * @param entidad
	 * @return
	 * @throws AttributesException
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	protected static List validateQueryFields(AxSfQueryField field, int id,
			int fldid, Locale locale, String sessionID, AxSf axsfQ,
			Integer bookId, SimpleDateFormat shortFormatter, String entidad)
			throws AttributesException, BookException, SessionException,
			ValidationException {
		List result = new ArrayList();
		Map idsToValidate = new HashMap();
		Map controlsMemo = new HashMap();
		try {
			if (field.getValue().getClass().equals(ArrayList.class)) {
				if (((List) field.getValue()).size() != 2
						&& !field.getOperator().equals(Keys.BARRA)) {
					result.add(new Integer(id));
				}
			}

			if ((fldid == 2 || fldid == 4)
					&& field.getValue().getClass().equals(Date.class)) {
				String auxDate = field.getValue().toString();

				try {
					if (auxDate.length() > 10) {
						result.add(new Integer(id));
					}
					shortFormatter.parse(auxDate);
					if (shortFormatter.getCalendar().get(Calendar.YEAR) < 1970) {
						result.add(new Integer(id));
					}
				} catch (Exception e) {
					result.add(new Integer(id));
				}
			}
			if ((fldid == 5 || fldid == 7 || fldid == 8)) {
				if (!field.getValue().getClass().equals(List.class)) {
					idsToValidate.put(new Integer(fldid), field.getValue());
					controlsMemo.put(new Integer(fldid), new Integer(id));
				}
			}

			if (axsfQ instanceof AxSfIn) {
				if ((fldid == 13 || fldid == 16)) {
					if (!field.getValue().getClass().equals(List.class)) {
						idsToValidate.put(new Integer(fldid), field.getValue());
						controlsMemo.put(new Integer(fldid), new Integer(id));
					}
				}
				if (fldid > com.ieci.tecdoc.common.isicres.Keys.EREG_FDR_MATTER) {
					if (AttributesSession
							.getExtendedValidationFieldValueWithTVNull(
									sessionID, bookId, fldid, field.getValue()
											.toString(), locale, entidad) == null) {
						result.add(new Integer(id));
					}

				}
			} else {
				if ((fldid == 12)) {
					if (!field.getValue().getClass().equals(List.class)) {
						idsToValidate.put(new Integer(fldid), field.getValue());
						controlsMemo.put(new Integer(fldid), new Integer(id));
					}
				}
				if (fldid > com.ieci.tecdoc.common.isicres.Keys.SREG_FDR_MATTER) {
					if (AttributesSession
							.getExtendedValidationFieldValueWithTVNull(
									sessionID, bookId, fldid, field.getValue()
											.toString(), locale, entidad) == null) {
						result.add(new Integer(id));
					}

				}
			}
		} catch (Exception e) {
			result.add(new Integer(id));
		}

		if (!idsToValidate.isEmpty()) {
			List aux = AttributesSession.validateFixedValues(sessionID, bookId,
					idsToValidate, false, entidad);
			Integer auxFldid = null;

			for (Iterator it = aux.iterator(); it.hasNext();) {
				auxFldid = (Integer) it.next();
				result.add(controlsMemo.get(auxFldid));
			}
		}

		return result;
	}

	/**
	 * Sustituimos los atributos con sistituto
	 *
	 * @param field
	 * @param id
	 * @param fldid
	 * @param locale
	 * @param sessionID
	 * @param axsfQ
	 * @param bookId
	 * @param entidad
	 * @return
	 */
	protected static Map translateQueryFields(AxSfQueryField field, int fldid,
			AxSf axsfQ) {
		Map idsToValidate = new HashMap();
		try {
			if (fldid == 5) {
				idsToValidate.put(new Integer(fldid), field.getValue());
			}
			if ((fldid == 7 || fldid == 8)
					&& !field
							.getOperator()
							.equals(
									com.ieci.tecdoc.common.isicres.Keys.QUERY_DEPEND_OF_TEXT_VALUE)) {
				idsToValidate.put(new Integer(fldid), field.getValue());
			}
			if (axsfQ instanceof AxSfIn) {
				if ((fldid == 13 || fldid == 16)) {
					idsToValidate.put(new Integer(fldid), field.getValue());
				}
			} else {
				if ((fldid == 12))
					idsToValidate.put(new Integer(fldid), field.getValue());
			}
		} catch (Exception e) {
		}

		return idsToValidate;
	}

	/**
	 *
	 * @param field
	 * @param axsfQ
	 * @param translations
	 * @param locale
	 * @return
	 * @throws ParseException
	 */
	protected static Object getQueryFieldValue(AxSfQueryField field,
			AxSf axsfQ, Map translations, Locale locale,
			boolean isDataBaseCaseSentitive) throws ParseException {
		if (translations != null
				&& translations.containsKey(new Integer(field.getFldId()))) {
			return translations.get(new Integer(field.getFldId()));
		} else {
			if (field.getOperator().equalsIgnoreCase(
					com.ieci.tecdoc.common.isicres.Keys.QUERY_OR_TEXT_VALUE)
					|| field
							.getOperator()
							.equalsIgnoreCase(
									com.ieci.tecdoc.common.isicres.Keys.QUERY_BETWEEN_TEXT_VALUE)) {
				StringTokenizer tokenizer = new StringTokenizer(field
						.getValue().toString(), ";");
				List list = new ArrayList(tokenizer.countTokens());
				while (tokenizer.hasMoreTokens()) {

					list.add(getQueryFieldValue(field, axsfQ, locale, tokenizer
							.nextToken(), isDataBaseCaseSentitive));
				}
				return list;
			} else if (field.getOperator().equalsIgnoreCase(
					com.ieci.tecdoc.common.isicres.Keys.QUERY_LIKE_TEXT_VALUE)) {
				String aux = field.getValue().toString().replaceAll("%", "");
				aux = "%" + aux + "%";
				return getQueryFieldValue(field, axsfQ, locale, aux,
						isDataBaseCaseSentitive);
			} else {
				return getQueryFieldValue(field, axsfQ, locale, field
						.getValue().toString(), isDataBaseCaseSentitive);
			}
		}
	}

	protected static String getDest(String sessionID, Integer bookId,
			int fldid, String entidad) throws ValidationException,
			SecurityException, AttributesException, BookException,
			SessionException {
		StringBuffer buffer = new StringBuffer();
		List scrRegIntList = UtilsSessionEx.getScrRegisterInter(bookId, fldid,
				true, entidad);
		if (scrRegIntList != null && !scrRegIntList.isEmpty()) {
			int size = scrRegIntList.size();
			int i = 0;
			for (Iterator it = scrRegIntList.iterator(); it.hasNext();) {
				ScrRegisterInter scrRegInt = (ScrRegisterInter) it.next();
				if (i < size - 1) {
					buffer.append(scrRegInt.getName());
					buffer.append(", ");
				} else {
					buffer.append(scrRegInt.getName());
				}
				i++;
			}
		}
		return buffer.toString();
	}

	/**
	 * Añadimos los atributos que son de registro de entrada
	 *
	 * @param bookUseCase
	 * @param flushFdrField
	 * @param axsfQ
	 * @param newAxSF
	 * @param locale
	 * @param longFormatter
	 * @param shortFormatter
	 * @param fieldFormat
	 * @return
	 * @throws ParseException
	 */
	protected static AxSf addField2AxSfIn(BookUseCase bookUseCase, FlushFdrField flushFdrField,
			AxSf axsfQ, AxSf newAxSF, Locale locale,
			SimpleDateFormat longFormatter, SimpleDateFormat shortFormatter,
			FieldFormat fieldFormat) throws ParseException {
		int fldId = flushFdrField.getFldid();
		if (fldId == 17 || fldId == 14 || fldId == 15 || fldId == 10) {
			newAxSF.addAttributeName("fld" + flushFdrField.getFldid());
			newAxSF.addAttributeValue("fld" + flushFdrField.getFldid(),
					flushFdrField.getValue());
		}
		if (fldId == 11) {
			newAxSF.addAttributeName("fld" + flushFdrField.getFldid());
			if (flushFdrField.getValue() != null) {
				newAxSF.addAttributeValue("fld" + flushFdrField.getFldid(),
						new BigDecimal(RBUtil.getInstance(locale).getProperty(
								"book.fld11." + flushFdrField.getValue())));
			} else {
				newAxSF.addAttributeValue("fld" + flushFdrField.getFldid(),
						flushFdrField.getValue());
			}
		}
		if (fldId == 12) {
			newAxSF.addAttributeName("fld" + flushFdrField.getFldid());
			if (flushFdrField.getValue() != null) {
				newAxSF.addAttributeValue("fld" + flushFdrField.getFldid(),
						shortFormatter.parse(flushFdrField.getValue()));
			} else {
				newAxSF.addAttributeValue("fld" + flushFdrField.getFldid(),
						flushFdrField.getValue());
			}
		}

		//Campos Extendidos
		if ((axsfQ instanceof AxSfIn
				&& flushFdrField.getFldid() > com.ieci.tecdoc.common.isicres.Keys.EREG_FDR_MATTER)){
			bookUseCase.getExtendsFields(axsfQ, fieldFormat,
					flushFdrField, newAxSF, longFormatter, shortFormatter);
		}

		return newAxSF;
	}

	/**
	 * Añadimos los atributos que son de registro de salida
	 *
	 * @param bookUseCase
	 * @param flushFdrField
	 * @param axsfQ
	 * @param newAxSF
	 * @param locale
	 * @param longFormatter
	 * @param shortFormatter
	 * @param fieldFormat
	 * @return
	 * @throws ParseException
	 */
	protected static AxSf addField2AxSfOut(BookUseCase bookUseCase, FlushFdrField flushFdrField,
			AxSf axsfQ, AxSf newAxSF, Locale locale,
			SimpleDateFormat longFormatter, SimpleDateFormat shortFormatter,
			FieldFormat fieldFormat) throws ParseException {
		int fldId = flushFdrField.getFldid();
		if (fldId == 10 || fldId == 11 || fldId == 13) {
			newAxSF.addAttributeName("fld" + flushFdrField.getFldid());
			newAxSF.addAttributeValue("fld" + flushFdrField.getFldid(),
					flushFdrField.getValue());
		}

		//Campos Extendidos
		if (axsfQ instanceof AxSfOut
				&& flushFdrField.getFldid() > com.ieci.tecdoc.common.isicres.Keys.SREG_FDR_MATTER) {
			bookUseCase.getExtendsFields(axsfQ, fieldFormat, flushFdrField,
					newAxSF, longFormatter, shortFormatter);
		}

		return newAxSF;
	}

	private static void validateFolder(String sessionID, Integer bookId,
			List atts, AxSf axsfQ, int fdrid, SimpleDateFormat shortFormatter,
			SimpleDateFormat longFormatter, List preResult, Map idsToValidate,
			Locale locale, FieldFormat fieldFormat, String entidad,
			boolean dateError, boolean isConsolidacion) throws AttributesException, BookException,
			SessionException, ValidationException {
		if ((atts == null) || (atts.isEmpty())) {
			return;
		}

		for (Iterator it = atts.iterator(); it.hasNext();) {
			boolean validate = false;
			FlushFdrField flushFdrField = (FlushFdrField) it.next();

			if (flushFdrField.getValue() != null
					&& !flushFdrField.getValue().equals("")) {
				validate = validateFolderCommon(flushFdrField, fdrid,
						shortFormatter, longFormatter, preResult,
						idsToValidate, dateError, isConsolidacion);
				if (!validate) {
					validate = validateFolderIn(sessionID, bookId, axsfQ,
							flushFdrField, locale, preResult, idsToValidate,
							shortFormatter, longFormatter, fieldFormat, entidad);
				}
				if (!validate) {
					validate = validateFolderOut(sessionID, bookId, axsfQ,
							flushFdrField, locale, preResult, idsToValidate,
							shortFormatter, longFormatter, fieldFormat, entidad);
				}
				if (!validate) {
					checkValue(flushFdrField, shortFormatter, longFormatter,
							fieldFormat, preResult);
				}
			}
		}
	}

	private static boolean validateFolderCommon(FlushFdrField flushFdrField,
			int fdrid, SimpleDateFormat shortFormatter,
			SimpleDateFormat longFormatter, List preResult, Map idsToValidate,
			boolean dateError, boolean isConsolidacion) {
		boolean validate = false;
		if (flushFdrField.getFldid() == 2) {
			try {
				Date date = null;
				if (flushFdrField.getValue().length() > 19) {
					dateError = true;
				} else {
					date = longFormatter.parse(flushFdrField.getValue());
				}

				if (isConsolidacion){
					if (longFormatter.getCalendar().get(Calendar.YEAR) < 1970
							|| shortFormatter.getCalendar().get(Calendar.YEAR) > 2040) {
						dateError = true;
					} else {
						dateError = false;
					}
				} else if (date.after(new Date())
						|| longFormatter.getCalendar().get(Calendar.YEAR) < 1970
						|| shortFormatter.getCalendar().get(Calendar.YEAR) > 2040) {
					dateError = true;
				} else {
					dateError = false;
				}

			} catch (Exception e) {
				dateError = true;
			}
			if (dateError) {
				preResult.add(new Integer(2));
			}
			validate = true;
		} else if (flushFdrField.getFldid() == 4) {
			try {
				if (flushFdrField.getValue().length() > 10) {
					new Exception("Fecha mal formada");
				}
				shortFormatter.parse(flushFdrField.getValue());
			} catch (Exception e) {
				preResult.add(new Integer(flushFdrField.getFldid()));
			}
			validate = true;
		} else if (flushFdrField.getFldid() == 5
				|| flushFdrField.getFldid() == 7
				|| flushFdrField.getFldid() == 8) {
			idsToValidate.put(new Integer(flushFdrField.getFldid()),
					flushFdrField.getValue());
			validate = true;
		}

		return validate;
	}

	private static boolean validateFolderIn(String sessionID, Integer bookId,
			AxSf axsfQ, FlushFdrField flushFdrField, Locale locale,
			List preResult, Map idsToValidate, SimpleDateFormat shortFormatter,
			SimpleDateFormat longFormatter, FieldFormat fieldFormat,
			String entidad) throws AttributesException, BookException,
			SessionException, ValidationException {
		boolean validate = false;
		if (axsfQ instanceof AxSfIn) {
			if (flushFdrField.getFldid() == 11) {
				try {
					Integer.parseInt(RBUtil.getInstance(locale).getProperty(
							"book.fld11." + flushFdrField.getValue()));
				} catch (Exception e) {
					preResult.add(new Integer(flushFdrField.getFldid()));
				}
				validate = true;
			} else if (flushFdrField.getFldid() == 12) {
				try {
					if (flushFdrField.getValue().length() > 10) {
						new Exception("Fecha mal formada");
					}
					shortFormatter.parse(flushFdrField.getValue());
					if (shortFormatter.getCalendar().get(Calendar.YEAR) < 1970
							|| shortFormatter.getCalendar().get(Calendar.YEAR) > 2040) {
						preResult.add(new Integer(flushFdrField.getFldid()));
					}
				} catch (Exception e) {
					preResult.add(new Integer(flushFdrField.getFldid()));
				}
				validate = true;
			} else if (flushFdrField.getFldid() == 13
					|| flushFdrField.getFldid() == 16) {
				idsToValidate.put(new Integer(flushFdrField.getFldid()),
						flushFdrField.getValue());
				validate = true;
			} else if (flushFdrField.getFldid() > com.ieci.tecdoc.common.isicres.Keys.EREG_FDR_MATTER) {
				if (!axsfQ.getProposedExtendedFields().contains(
						new Integer(flushFdrField.getFldid()))) {
					if (AttributesSession
							.getExtendedValidationFieldValueWithTVNull(
									sessionID, bookId,
									flushFdrField.getFldid(), flushFdrField
											.getValue(), locale, entidad) != null) {
						checkValue(flushFdrField, shortFormatter,
								longFormatter, fieldFormat, preResult);
					} else {
						preResult.add(new Integer(flushFdrField.getFldid()));
					}
				}
				validate = true;
			}

		}

		return validate;
	}

	private static boolean validateFolderOut(String sessionID, Integer bookId,
			AxSf axsfQ, FlushFdrField flushFdrField, Locale locale,
			List preResult, Map idsToValidate, SimpleDateFormat shortFormatter,
			SimpleDateFormat longFormatter, FieldFormat fieldFormat,
			String entidad) throws AttributesException, BookException,
			SessionException, ValidationException {
		boolean validate = false;
		if (axsfQ instanceof AxSfOut) {
			if (flushFdrField.getFldid() == 12) {
				idsToValidate.put(new Integer(flushFdrField.getFldid()),
						flushFdrField.getValue());
				validate = true;
			} else if (flushFdrField.getFldid() > com.ieci.tecdoc.common.isicres.Keys.SREG_FDR_MATTER) {
				if (!axsfQ.getProposedExtendedFields().contains(
						new Integer(flushFdrField.getFldid()))) {
					if (AttributesSession
							.getExtendedValidationFieldValueWithTVNull(
									sessionID, bookId,
									flushFdrField.getFldid(), flushFdrField
											.getValue(), locale, entidad) != null) {
						checkValue(flushFdrField, shortFormatter,
								longFormatter, fieldFormat, preResult);
					} else {
						preResult.add(new Integer(flushFdrField.getFldid()));
					}
				}
				validate = true;
			}
		}

		return validate;
	}

	/**
	 * Metodo que retorna un listado con la informacion de los campos erroneos
	 *
	 * @param preResult
	 * @param fieldFormat {@link FieldFormat}
	 *
	 * @return Listado de campos erroneos
	 */
	private static List getResultValidateFolder(List preResult,
			FieldFormat fieldFormat) {
		List result = new ArrayList();
		String name = null;
		for (Iterator it = preResult.iterator(); it.hasNext();) {
			Integer f = (Integer) it.next();
			for (int i = 1; i < fieldFormat.getNumflds(); i++) {
				String aux = fieldFormat.getFFldDef(i).getColname();
				if (aux.equals(DEFAULT_FLD + f.toString())) {
					name = DEFAULT_FLD + f.toString() + " - "
							+ fieldFormat.getFFldDef(i).getName();
					break;
				}
			}

			log.error("Campo erróneo: " + name);
			result.add(name);
		}
		return result;
	}

	/**
	 *
	 * @param flushFdrField
	 * @param shortFormatter
	 * @param longFormatter
	 * @param fieldFormat
	 * @param preResult
	 */
	private static void checkValue(FlushFdrField flushFdrField,
			SimpleDateFormat shortFormatter, SimpleDateFormat longFormatter,
			FieldFormat fieldFormat, List preResult) {
		FFldDef fldDef = fieldFormat.getFFldDef(flushFdrField.getFldid());

		switch (fldDef.getType()) {
		case 1:
		case 2: {
			// if (flushFdrField.getValue().length() > fldDef.getLen()) {
			// preResult.add(new Integer(flushFdrField.getFldid()));
			// }
			break;
		}
		case 3:
		case 4: {
			try {
				Long.parseLong(flushFdrField.getValue());
			} catch (Exception e) {
				preResult.add(new Integer(flushFdrField.getFldid()));
			}
			break;
		}
		case 5:
		case 6: {
			try {
				new BigDecimal(flushFdrField.getValue());
			} catch (Exception e) {
				preResult.add(new Integer(flushFdrField.getFldid()));
			}
			break;
		}
		case 7: {
			try {
				if (flushFdrField.getValue().length() > 10) {
					new Exception("Fecha mal formada");
				}
				shortFormatter.parse(flushFdrField.getValue());
				if (shortFormatter.getCalendar().get(Calendar.YEAR) < 1970
						|| shortFormatter.getCalendar().get(Calendar.YEAR) > 2040) {
					preResult.add(new Integer(flushFdrField.getFldid()));
				}
			} catch (Exception e) {
				try {
					if (flushFdrField.getValue().length() > 10) {
						new Exception("Fecha mal formada");
					}
					shortFormatter.parse(flushFdrField.getValue());
					if (shortFormatter.getCalendar().get(Calendar.YEAR) < 1970
							|| shortFormatter.getCalendar().get(Calendar.YEAR) > 2040) {
						preResult.add(new Integer(flushFdrField.getFldid()));
					}
				} catch (Exception e1) {
					preResult.add(new Integer(flushFdrField.getFldid()));
				}
			}
			break;
		}
		case 8:
		case 9: {
			try {
				longFormatter.parse(flushFdrField.getValue());
				if (longFormatter.getCalendar().get(Calendar.YEAR) < 1970
						|| shortFormatter.getCalendar().get(Calendar.YEAR) > 2040) {
					preResult.add(new Integer(flushFdrField.getFldid()));
				}
			} catch (Exception e) {
				try {
					shortFormatter.parse(flushFdrField.getValue());
					if (shortFormatter.getCalendar().get(Calendar.YEAR) < 1970
							|| shortFormatter.getCalendar().get(Calendar.YEAR) > 2040) {
						preResult.add(new Integer(flushFdrField.getFldid()));
					}
				} catch (Exception e1) {
					preResult.add(new Integer(flushFdrField.getFldid()));
				}
			}
			break;
		}
		}
	}

	/**
	 *
	 * @param field
	 * @param axsfQ
	 * @param locale
	 * @param value
	 * @return
	 * @throws ParseException
	 */
	private static Object getQueryFieldValue(AxSfQueryField field, AxSf axsfQ,
			Locale locale, String value, boolean isDataBaseCaseSentitive)
			throws ParseException {
		if (axsfQ.getAttributeClass("fld" + field.getFldId())
				.equals(Date.class)) {
			SimpleDateFormat SDF = new SimpleDateFormat(RBUtil.getInstance(
					locale).getProperty(Keys.I18N_DATE_SHORTFORMAT));
			SDF.setLenient(false);
			return SDF.parse(value);
		} else if (axsfQ.getAttributeClass("fld" + field.getFldId()).equals(
				BigDecimal.class)) {
			return new Integer(value);

		} else if (axsfQ.getAttributeClass("fld" + field.getFldId()).equals(
				String.class)
				&& isDataBaseCaseSentitive) {
			return value.toUpperCase(locale);
		} else {
			return value;
		}
	}

}
