/**
 *
 */
package com.ieci.tecdoc.isicres.session.folder;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.invesicres.ScrCa;
import com.ieci.tecdoc.common.invesicres.ScrModifreg;
import com.ieci.tecdoc.common.invesicres.ScrValdate;
import com.ieci.tecdoc.common.invesicres.ScrValnum;
import com.ieci.tecdoc.common.invesicres.ScrValstr;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfIn;
import com.ieci.tecdoc.common.isicres.AxSfOut;
import com.ieci.tecdoc.common.isicres.Keys;
import com.ieci.tecdoc.common.isicres.UpdHisFdrResults;
import com.ieci.tecdoc.common.isicres.ValidationResults;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.common.utils.EntityByLanguage;
import com.ieci.tecdoc.common.utils.ISicresQueries;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FieldFormat;
import com.ieci.tecdoc.isicres.session.attributes.AttributesSession;
import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.session.validation.ValidationSessionEx;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.Validator;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

/**
 * @author 66575267
 *
 */
public class FolderHistSession extends FolderSessionUtil implements ServerKeys,
		Keys, HibernateKeys {

	private static final String SPACE = " ";
	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private static final Logger log = Logger.getLogger(FolderHistSession.class);

	/***************************************************************************
	 * Public methods
	 **************************************************************************/

	public static List getUpdHisFdrResults(String sessionID, Locale locale,
			Integer bookID, int folderId, AxSf axsf, String num_reg,
			String entidad) throws BookException, SessionException,
			ValidationException {

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		Transaction tran = null;
		List result = new ArrayList();

		try {
			Session session = HibernateUtil.currentSession(entidad);

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			// Es necesario tener el libro abierto para consultar su contenido.
			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}

			Idocarchdet idocarchdet = BookSession.getIdocarchdetFld(sessionID,
					bookID);
			FieldFormat fieldFormat = new FieldFormat(idocarchdet.getDetval());

			List list = ISicresQueries.getUpdHistReg(session, bookID, num_reg);

			if (list != null && !list.isEmpty()) {
				for (Iterator it = list.iterator(); it.hasNext();) {
					ScrModifreg scr = (ScrModifreg) it.next();
					UpdHisFdrResults updHisFdrResults = new UpdHisFdrResults();
					updHisFdrResults.setScrModifReg(scr);

					String nameCampo = fieldFormat.getFFldDef(scr.getIdFld())
							.getName();
					updHisFdrResults.setNameFld(axsf
							.getLocaleAttributeNameForm(locale, nameCampo));

					Object fldtype = axsf.getAttributeClass("fld"
							+ String.valueOf(scr.getIdFld()));
					updHisFdrResults = getUpdHisFdrResultsByFldType(session,
							updHisFdrResults, fldtype, bookID, scr.getId());

					int validation = getModifRegValidationByFld(axsf, scr
							.getIdFld());

					updHisFdrResults = getUpdHisFdrResultsByValidation(
							sessionID, session, updHisFdrResults, validation,
							bookID, fldtype, scr.getIdFld(), locale, entidad);

					result.add(updHisFdrResults);
				}
			}

			return result;
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to close the book [" + bookID
					+ "] and fdrid [" + folderId + "] for the session ["
					+ sessionID + "]", e);
			throw new BookException(
					BookException.ERROR_CANNOT_FIND_MODIFICATION_HISTORY);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	/***************************************************************************
	 * Private methods
	 **************************************************************************/

	private static UpdHisFdrResults getUpdHisFdrResultsByFldType(
			Session session, UpdHisFdrResults updHisFdrResults, Object fldtype,
			Integer bookID, Integer modifRegId) throws HibernateException {
		if (fldtype != null) {
			if ((fldtype.equals(Date.class))
					|| (fldtype.equals(Timestamp.class))) {
				ScrValdate scrValdate = ISicresQueries.getScrValdatebyId(
						session, bookID, modifRegId);

				if(scrValdate != null){
					updHisFdrResults
							.setValue(getUpdHisFdrDateToString(scrValdate
									.getValue()));

					updHisFdrResults
							.setOldvalue(getUpdHisFdrDateToString(scrValdate
									.getOldvalue()));
				}
			} else if ((fldtype.equals(Integer.class))
					|| (fldtype.equals(Long.class))
					|| (fldtype.equals(BigDecimal.class))) {
				ScrValnum scrValnum = ISicresQueries.getScrValnumbyId(session,
						bookID, modifRegId);

				if(scrValnum != null){
					updHisFdrResults.setValue(getUpdHisFdrNumToString(scrValnum
							.getValue()));

					updHisFdrResults
							.setOldvalue(getUpdHisFdrNumToString(scrValnum
									.getOldvalue()));
				}
			} else {
				ScrValstr scrValstr = ISicresQueries.getScrValstrbyId(session,
						bookID, modifRegId);

				if(scrValstr != null){

					updHisFdrResults.setValue(getUpdHistFdrString(scrValstr
							.getValue()));

					updHisFdrResults.setOldvalue(getUpdHistFdrString(scrValstr
							.getOldvalue()));
				}
			}
		}

		return updHisFdrResults;
	}

	/**
	 * Metodo que parsea un valor de tipo Date a String, si el valor del Date es
	 * nulo se retorna una cadena inicializada a espacio
	 *
	 * @param date
	 *            - Date (Fecha)
	 * @return String - Cadena con la fecha o cadena inicializada
	 */
	private static String getUpdHisFdrDateToString(Date date) {
		String result = SPACE;

		if (date != null) {
			result = String.valueOf(date);
		}
		return result;
	}

	/**
	 * Método que parsea un Integer a String, si el valor del Integer es nulo se
	 * retorna una cadena inicializada a espacio.
	 *
	 * @param numero
	 *            - Integer
	 * @return String - Cadena con el valor del entero o cadena inicializada
	 */
	private static String getUpdHisFdrNumToString(Integer numero) {
		String result = SPACE;

		if (numero != null) {
			result = String.valueOf(numero);
		}
		return result;
	}

	/**
	 * Metodo que retorna la cadena pasada como parametro o si esta es vacia se
	 * retorna una cadena inicializada a espacio
	 *
	 * @param cadena
	 *            - Cadena
	 * @return String - Cadena con el string pasado como parametro o cadena
	 *         inicializada a espacio
	 */
	private static String getUpdHistFdrString(String cadena){
		String result = SPACE;

		if (!StringUtils.isEmpty(cadena)) {
			result = String.valueOf(cadena);
		}
		return result;
	}

	private static UpdHisFdrResults getUpdHisFdrResultsByValidation(
			String sessionID, Session session,
			UpdHisFdrResults updHisFdrResults, int validation, Integer bookID,
			Object fldtype, int fldId, Locale locale, String entidad)
			throws AttributesException, BookException, HibernateException,
			SessionException, ValidationException {
		switch (validation) {
		case VALIDATE_TYPE_UA: {
			// valida el origen, destino y registro original
			updHisFdrResults.setValue(getValueUAUpdHisFdr(sessionID,
					updHisFdrResults.getValue(), locale, entidad));

			updHisFdrResults.setOldvalue(getValueUAUpdHisFdr(sessionID,
					updHisFdrResults.getOldvalue(), locale,
					entidad));

			break;
		}
		case VALIDATE_ESTADO_REGISTRO: {
			// valida el estado del registro
			updHisFdrResults.setSustituto(validation);
			break;
		}
		case VALIDATE_TYPE_SUBJECT: {
			// valida el asunto
			ScrCa scrCa = null;
			try {
				scrCa = ((ScrCa) session.load(ScrCa.class, new Integer(
						updHisFdrResults.getValue())));
				updHisFdrResults.setValue(scrCa.getMatter());
			} catch (Exception e) {
				updHisFdrResults.setValue(SPACE);
			}
			try {
				scrCa = ((ScrCa) session.load(ScrCa.class, new Integer(
						updHisFdrResults.getOldvalue())));
				updHisFdrResults.setOldvalue(scrCa.getMatter());
			} catch (Exception e) {
				updHisFdrResults.setOldvalue(SPACE);
			}
			break;
		}
		case VALIDATE_TYPE_REGISTER_TYPE: {
			// valida el tipo de reg original
			updHisFdrResults.setSustituto(validation);
			break;
		}
		case VALIDATE_INVESDOC: {
			// validacion para campos nuevos
			int defaultPageSize = Configurator.getInstance()
					.getDefaultPageValidationListSize();

			updHisFdrResults.setValue(getValueUpdHisFdr(fldtype,
					updHisFdrResults.getValue(), sessionID, bookID, fldId, 0,
					defaultPageSize, locale, entidad, false));

			updHisFdrResults.setOldvalue(getValueUpdHisFdr(fldtype,
					updHisFdrResults.getOldvalue(), sessionID, bookID, fldId,
					0, defaultPageSize, locale, entidad, false));
			break;
		}
		default: {
			break;
		}
		}

		return updHisFdrResults;
	}

	private static int getModifRegValidationByFld(AxSf axsf, int fldId) {
		int validation = 0;
		if (axsf instanceof AxSfIn) {
			if (fldId == 5) {
				validation = 4;
			} else if (fldId == 6) {
				validation = 6;
			} else if (fldId == 7 || (fldId == 8) || (fldId == 13)) {
				validation = 1;
			} else if (fldId == 11) {
				validation = 7;
			} else if (fldId == 14) {
				validation = 2;
			} else if (fldId == 16) {
				validation = 3;
			} else if (fldId > EREG_FDR_MATTER) {
				validation = 1000;
			}
		} else if (axsf instanceof AxSfOut) {
			if (fldId == 5) {
				validation = 4;
			} else if (fldId == 6) {
				validation = 6;
			} else if (fldId == 7 || fldId == 8) {
				validation = 1;
			} else if (fldId == 10) {
				validation = 2;
			} else if (fldId == 12) {
				validation = 3;
			} else if (fldId > SREG_FDR_MATTER) {
				validation = 1000;
			}
		}

		return validation;
	}

	/**
	 * Método que obtiene el código de la unid. Administrativa (SCR_ORGS) a
	 * partir del Identificador pasado como parámetro
	 *
	 * @param sessionID - ID de session
	 * @param idOrg
	 *            - ID de la unid. Administrativa
	 * @param locale
	 *            - Idioma
	 * @param entidad
	 * @return String - Código de la unidad administrativa
	 * @throws SessionException
	 * @throws ValidationException
	 */
	private static String getValueUAUpdHisFdr(String sessionID, String idOrg,
			Locale locale, String entidad) throws SessionException,
			ValidationException {

		String result = null;
		//Comprobamos el valor del ID de la unidad
		if(!StringUtils.isBlank(idOrg)){
			//Obtenemos la información de la Unid. Admin del valor pasado como parametro
			Object scrOrgCurrent = ValidationSessionEx.getScrOrgValidate(
					sessionID, Integer.parseInt(idOrg), locale.getLanguage(),
					entidad);
			//Recuperamos el codigo de la unidad
			result = EntityByLanguage.getOrgCode(scrOrgCurrent);
		}

		return result;
	}

	private static String getValueUpdHisFdr(Object fldtype,
			String updHisFdrResultsValue, String sessionID, Integer bookID,
			int fldid, int firstRow, int maxResults, Locale locale,
			String entidad, boolean closeSession) throws AttributesException,
			BookException, SessionException, ValidationException {

		String where = null;
		ValidationResults results = null;
		Object datosVal[] = null;

		if (!StringUtils.isEmpty(updHisFdrResultsValue)) {
			if ((fldtype.equals(Integer.class)) || (fldtype.equals(Long.class))
					|| (fldtype.equals(BigDecimal.class))) {
				where = " idocval = " + updHisFdrResultsValue;

			} else {
				where = " idocval = '" + updHisFdrResultsValue + "'";
			}

			results = AttributesSession.getExtendedValidationFieldValues(
					sessionID, bookID, fldid, 0, Configurator.getInstance()
							.getDefaultPageValidationListSize(), where, locale,
					entidad, false);

			if (!results.getResults().isEmpty()) {
				datosVal = (Object[]) results.getResults().iterator().next();

				updHisFdrResultsValue = datosVal[1].toString();
			}
		}
		return updHisFdrResultsValue;
	}

}
