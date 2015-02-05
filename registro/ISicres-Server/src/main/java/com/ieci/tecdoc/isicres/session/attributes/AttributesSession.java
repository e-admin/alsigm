package com.ieci.tecdoc.isicres.session.attributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Idocbtblctlg;
import com.ieci.tecdoc.common.invesdoc.Idocvtblctlg;
import com.ieci.tecdoc.common.invesdoc.Iuserusertype;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrOrg;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.ISFieldsValidator;
import com.ieci.tecdoc.common.isicres.Keys;
import com.ieci.tecdoc.common.isicres.ValidationResultCode;
import com.ieci.tecdoc.common.isicres.ValidationResults;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.utils.ISOfficesValidator;
import com.ieci.tecdoc.common.utils.ISSubjectsValidator;
import com.ieci.tecdoc.common.utils.ISUnitsValidator;
import com.ieci.tecdoc.common.utils.Repository;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.VFldVldDef;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.ValidationFormat;
import com.ieci.tecdoc.idoc.decoder.validation.idocvtblctlg.IdocvtblctlgTV;
import com.ieci.tecdoc.idoc.decoder.validation.idocvtblctlg.IdocvtblctlgTVS;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.Validator;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

/**
 * @author 79426599
 *
 */

public class AttributesSession extends AttributesSessionUtil implements
		HibernateKeys {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private static final Logger log = Logger.getLogger(AttributesSession.class);

	/***************************************************************************
	 * Public methods
	 **************************************************************************/

	public static List getExtendedValidationFieldsForBook(String sessionID,
			Integer bookID, String entidad) throws AttributesException,
			BookException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		List fields = new ArrayList();
		try {
			ValidationFormat validationFormat = getValidationFormat(sessionID,
					bookID);

			int firstValidationField = Keys.EREG_FDR_MATTER + 1;
			if (Repository.getInstance(entidad).isOutBook(bookID.toString())
					.booleanValue()) {
				firstValidationField = Keys.SREG_FDR_MATTER + 1;
			}

			for (Iterator it = validationFormat.getFldvlddefs().values()
					.iterator(); it.hasNext();) {
				VFldVldDef vldDef = (VFldVldDef) it.next();
				if (vldDef.getFldid() >= firstValidationField
						&& vldDef.getVldtype() == IDocKeys.IDOCARCHDET_IDOC_VLD_TYPE_TVS) {
					fields.add(new Integer(vldDef.getFldid()));
				} else if (vldDef.getFldid() >= firstValidationField
						&& vldDef.getVldtype() == IDocKeys.IDOCARCHDET_IDOC_VLD_TYPE_TV) {
					fields.add(new Integer(vldDef.getFldid()));
				}
			}

			return fields;
		} catch (BookException sE) {
			throw sE;
		} catch (SessionException sE) {
			throw sE;
		} catch (Exception e) {
			log.error("Impossible to load the attributes for [" + bookID
					+ "] session [" + sessionID + "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_VALIDATIONFIELDS);
		}
	}

	public static String getExtendedValidationFieldValue(String sessionID,
			Integer bookID, int fldid, String value, String language,
			String entidad) throws AttributesException, BookException,
			SessionException, ValidationException {
		return getExtendedValidationFieldValue(sessionID, bookID, fldid, value,
				false, language, entidad);
	}
	
	public static String getExtendedValidationFieldValue(Session session, String sessionID,
			Integer bookID, int fldid, String value, String language,
			String entidad) throws AttributesException, BookException,
			SessionException, ValidationException {
		return getExtendedValidationFieldValue(session, sessionID, bookID, fldid, value,
				false, language, entidad);
	}

	public static String getExtendedValidationFieldValueWithTVNull(
			String sessionID, Integer bookID, int fldid, String value,
			Locale locale, String entidad) throws AttributesException,
			BookException, SessionException, ValidationException {
		return getExtendedValidationFieldValue(sessionID, bookID, fldid, value,
				true, locale.getLanguage(), entidad);
	}

	public static boolean getExtendedValidationFieldValueForQuery(
			String sessionID, Integer bookID, int fldid, String operator,
			String entidad) throws AttributesException, BookException,
			SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		boolean result = false;
		// Transaction tran = null;
		try {
			// Session session = HibernateUtil.currentSession(entidad);
			// tran = session.beginTransaction();

			ValidationFormat validationFormat = getValidationFormat(sessionID,
					bookID);

			// Validamos que el atributo sea mayor que 17 o 13
			if (isFldidValid(bookID, fldid, entidad)) {

				VFldVldDef vldDef = getVldDef(validationFormat, fldid);

				// Si es de validación simple, el valor devuelto es el pasado
				// como parámetro
				if ((vldDef.getVldtype() == IDocKeys.IDOCARCHDET_IDOC_VLD_TYPE_TVS)
						&& (operator.equals(Keys.QUERY_LIKE_TEXT_VALUE))) {
					result = true;
				} else if ((vldDef.getVldtype() == IDocKeys.IDOCARCHDET_IDOC_VLD_TYPE_TV)
						&& (operator.equals(Keys.QUERY_LIKE_TEXT_VALUE))) {
					result = true;
				}
			} else {
				log.info("Campo no soportado por la validacion fldid [" + fldid
						+ "]");
				throw new AttributesException(
						AttributesException.ERROR_NOT_VALIDATION_FIELD);
			}

			// HibernateUtil.commitTransaction(tran);

			return result;
		} catch (AttributesException sE) {
			// HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (SessionException sE) {
			// HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (BookException sE) {
			// HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			// HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to load the validation value field  bookID ["
					+ bookID + "] fldid [" + fldid + "] for the session ["
					+ sessionID + "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_VALIDATIONFIELDS);
		}
	}

	public static Map getExtendedValidationFieldValues(String sessionID,
			Integer bookID, int fldid, List values, Locale locale,
			String entidad) throws AttributesException, SessionException,
			BookException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);
		Validator.validate_NotNull(values,
				ValidationException.ATTRIBUTE_VALIDATION_FIELD_VALUE);

		Map result = new HashMap();
		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			ValidationFormat validationFormat = getValidationFormat(sessionID,
					bookID);

			// Validamos que el atributo sea mayor que 17 o 13
			if (isFldidValid(bookID, fldid, entidad)) {
				result = getExtendedValidationFieldValues(session,
						validationFormat, fldid, values, locale.getLanguage(),
						entidad);
			} else {
				throw new AttributesException(
						AttributesException.ERROR_NOT_VALIDATION_FIELD);
			}

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (AttributesException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (BookException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to load the validation value field  bookID ["
					+ bookID + "] fldid [" + fldid + "] for the session ["
					+ sessionID + "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_VALIDATIONFIELDS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static Map getExtendedValidationFieldValues(String sessionID,
			Integer bookID, Map fldids, Locale locale, String entidad)
			throws AttributesException, BookException, SessionException,
			ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);
		Validator.validate_NotNull(fldids,
				ValidationException.ATTRIBUTE_VALIDATION_FIELD_VALUE);

		Map result = new HashMap(fldids.size());
		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			ValidationFormat validationFormat = getValidationFormat(sessionID,
					bookID);

			int firstValidationField = Keys.EREG_FDR_MATTER + 1;
			if (Repository.getInstance(entidad).isOutBook(bookID.toString())
					.booleanValue()) {
				firstValidationField = Keys.SREG_FDR_MATTER + 1;
			}

			for (Iterator it = fldids.keySet().iterator(); it.hasNext();) {
				int fldid = ((Integer) it.next()).intValue();
				List values = (List) fldids.get(new Integer(fldid));

				// Validamos que el atributo sea mayor que 17 o 13
				if (fldid >= firstValidationField) {
					Map auxResult = getExtendedValidationFieldValues(session,
							validationFormat, fldid, values, locale
									.getLanguage(), entidad);
					result.put(new Integer(fldid), auxResult);
				}
			}

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (AttributesException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (BookException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to load the validation value field  bookID ["
					+ bookID + "] for the session [" + sessionID + "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_VALIDATIONFIELDS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static ValidationResults getExtendedValidationFieldValues(
			String sessionID, Integer bookID, int fldid, int firstRow,
			int maxResults, String where, Locale locale, String entidad,
			boolean closeSession) throws AttributesException, BookException,
			SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		ValidationResults result = new ValidationResults();
		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			ValidationFormat validationFormat = getValidationFormat(sessionID,
					bookID);

			// Validamos que el atributo sea mayor que 17 o 13
			if (isFldidValid(bookID, fldid, entidad)) {
				VFldVldDef vldDef = getVldDef(validationFormat, fldid);

				// Si es de validación simple, el valor devuelto es el pasado
				// como parámetro
				if (vldDef.getVldtype() == IDocKeys.IDOCARCHDET_IDOC_VLD_TYPE_TVS) {
					Idocvtblctlg idocvtblctlg = (Idocvtblctlg) session.load(
							Idocvtblctlg.class, new Integer(vldDef.getId1()));
					Idocbtblctlg idocbtblctlg = (Idocbtblctlg) session.load(
							Idocbtblctlg.class, new Integer(idocvtblctlg
									.getBtblid()));
					IdocvtblctlgTVS tvs = new IdocvtblctlgTVS(idocvtblctlg
							.getInfo());
					result = (getFieldValues(tvs, idocbtblctlg, firstRow,
							maxResults, where, vldDef.getVldtype(), locale
									.getLanguage(), entidad));
				} else if (vldDef.getVldtype() == IDocKeys.IDOCARCHDET_IDOC_VLD_TYPE_TV) {
					Idocvtblctlg idocvtblctlg = (Idocvtblctlg) session.load(
							Idocvtblctlg.class, new Integer(vldDef.getId1()));
					Idocbtblctlg idocbtblctlg = (Idocbtblctlg) session.load(
							Idocbtblctlg.class, new Integer(idocvtblctlg
									.getBtblid()));
					IdocvtblctlgTV tv = new IdocvtblctlgTV(idocvtblctlg
							.getInfo());
					result = (getFieldValues(tv, idocbtblctlg, firstRow,
							maxResults, where, vldDef.getVldtype(), locale
									.getLanguage(), entidad));
				}
			} else {
				throw new AttributesException(
						AttributesException.ERROR_NOT_VALIDATION_FIELD);
			}

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (AttributesException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (BookException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to load the validation value field  bookID ["
					+ bookID + "] fldid [" + fldid + "] for the session ["
					+ sessionID + "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_VALIDATIONFIELDS);
		} finally {
			if (closeSession) {
				HibernateUtil.closeSession(entidad);
			}
		}
	}

	public static List validateFixedValues(String sessionID, Integer bookID,
			Map values, boolean OnlyEnabled, String entidad)
			throws AttributesException, BookException, SessionException,
			ValidationException {

		return ISFieldsValidator.validateValues(sessionID, bookID, values,
				OnlyEnabled, entidad);
	}

	public static String validateAndReturnDescriptionValue(String sessionID,
			Integer bookID, int fldid, String code, Locale locale,
			String entidad) throws AttributesException, BookException,
			SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		String result = null;
		Transaction tran = null;
		List privOrgs = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			// Es necesario tener el libro abierto para consultar su contenido.
			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}
			ScrOfic scrofic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);
			Iuserusertype userusertype = (Iuserusertype) cacheBag
					.get(HIBERNATE_Iuserusertype);
			if (userusertype.getType() != IDocKeys.IUSERUSERTYPE_USER_TYPE_ADMIN) {
				privOrgs = DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getPrivOrgs(scrofic.getId().intValue(), entidad);
			}

			try {
				if (fldid == 5) {
					result = ISOfficesValidator.getOffice(session, code)
							.getName();
				}
				if (fldid == 7 || fldid == 8) {
					ScrOrg scrOrg = ISUnitsValidator.getUnit(session, code,
							true, privOrgs);
					result = scrOrg.getName();
				}
				if (Repository.getInstance(entidad).isInBook(bookID)
						.booleanValue()) {
					if (fldid == 13) {
						result = ISUnitsValidator
								.getEntReg(session, code, true).getName();
					}
					if (fldid == 16) {
						result = ISSubjectsValidator.getSubjectForOfic(session,
								code, true, true, scrofic.getId()).getMatter();
					}
					if (fldid > Keys.EREG_FDR_MATTER) {
						result = getExtendedValidationFieldValue(sessionID,
								bookID, fldid, code, locale.getLanguage(),
								entidad);
					}
				} else {
					if (fldid == 12) {
						result = ISSubjectsValidator.getSubjectForOfic(session,
								code, true, false, scrofic.getId()).getMatter();
					}
					if (fldid > Keys.SREG_FDR_MATTER) {
						result = getExtendedValidationFieldValue(sessionID,
								bookID, fldid, code, locale.getLanguage(),
								entidad);
					}
				}
			} catch (ValidationException e1) {
			} catch (Exception e) {
			}

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (AttributesException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (BookException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to load the validation value field  bookID ["
					+ bookID + "] for the session [" + sessionID + "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_VALIDATIONFIELDS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static ValidationResultCode validateAndReturnValidationCode(
			String sessionID, Integer bookID, int fldid, String code,
			Locale locale, String entidad) throws AttributesException,
			BookException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		ValidationResultCode result = new ValidationResultCode();
		Transaction tran = null;

		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			// Es necesario tener el libro abierto para consultar su contenido.
			if (bookID != null) {
				if (!cacheBag.containsKey(bookID)) {
					throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
				}
			}
			ScrOfic scrofic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);
			Iuserusertype userusertype = (Iuserusertype) cacheBag
					.get(HIBERNATE_Iuserusertype);
			try {
				if (fldid == 5) {
					result.setDescription(ISOfficesValidator.getOffice(session,
							code).getName());
				}
				if (fldid == 7 || fldid == 8) {
					List privOrgs = null;
					if (userusertype.getType() != IDocKeys.IUSERUSERTYPE_USER_TYPE_ADMIN) {
						privOrgs = DBEntityDAOFactory.getCurrentDBEntityDAO()
								.getPrivOrgs(scrofic.getId().intValue(),
										entidad);
					}
					ScrOrg scrOrg = ISUnitsValidator.getUnit(session, code,
							true, privOrgs);
					String desc = getScrOrgDescription(session, scrOrg, locale
							.getLanguage(), entidad);
					if (desc != null) {
						result.setDescription(desc);
					} else {
						result.setDescription(scrOrg.getName());
					}
				}
				if ((bookID != null)
						&& (Repository.getInstance(entidad).isInBook(bookID)
								.booleanValue())) {
					if (fldid == 13) {
						result.setDescription(ISUnitsValidator.getEntReg(
								session, code, true).getName());
					}
					if (fldid == 16) {
						result = setValidationResult(session, code, scrofic
								.getId(), true, 8, result,
								locale.getLanguage(), entidad);
					}
					// Validación del campo tipo de transporte
					if (fldid > Keys.EREG_FDR_MATTER) {
						result.setDescription(getExtendedValidationFieldValue(
								session, sessionID, bookID, fldid, code, locale
										.getLanguage(), entidad));
					}
				} else if (bookID != null) {
					if (fldid == 12) {
						result = setValidationResult(session, code, scrofic
								.getId(), false, 7, result, locale
								.getLanguage(), entidad);
					}
					if (fldid > Keys.SREG_FDR_MATTER) {
						result.setDescription(getExtendedValidationFieldValue(
								session, sessionID, bookID, fldid, code, locale
										.getLanguage(), entidad));
					}
				}
			} catch (ValidationException e1) {
			} catch (Exception e) {
			}

			result.setFldid(new Integer(fldid));
			result.setCode(code);

			HibernateUtil.commitTransaction(tran);
			return result;
		} catch (AttributesException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (BookException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_VALIDATIONFIELDS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static Map translateFixedValues(String sessionID, Integer bookID,
			Map values, String entidad) throws AttributesException,
			BookException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);
		Validator.validate_NotNull(values,
				ValidationException.ATTRIBUTE_VALIDATION_FIELD_VALUE);

		Map result = new HashMap();
		Transaction tran = null;
		List privOrgs = new ArrayList();
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

			Iuserusertype userusertype = (Iuserusertype) cacheBag
					.get(HIBERNATE_Iuserusertype);
			ScrOfic scrOfic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);
			/*
			 * Si el usuario no pertenece a ninguna oficina. 
			 * La variable privOrgs está vacía 
			 */
			if (scrOfic != null) {
				if (userusertype.getType() != IDocKeys.IUSERUSERTYPE_USER_TYPE_ADMIN) {
					privOrgs = DBEntityDAOFactory.getCurrentDBEntityDAO()
							.getPrivOrgs(scrOfic.getId().intValue(), entidad);
				}
			}

			result.putAll(getResultScrOfic(session, values, new Integer(
					AxSf.FLD5_FIELD_ID), true));
			result.putAll(getResultScrOrg(session, values, new Integer(
					AxSf.FLD7_FIELD_ID), privOrgs, true, true));
			result.putAll(getResultScrOrg(session, values, new Integer(
					AxSf.FLD8_FIELD_ID), privOrgs, true, true));

			if (Repository.getInstance(entidad).isInBook(bookID).booleanValue()) {
				result.putAll(getResultScrOrg(session, values, new Integer(
						AxSf.FLD13_FIELD_ID), privOrgs, true, false));
				result.putAll(getResultScrCa(session, values, new Integer(
						AxSf.FLD16_FIELD_ID), true, true));
			} else {
				result.putAll(getResultScrCa(session, values, new Integer(
						AxSf.FLD12_FIELD_ID), true, false));
			}

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (AttributesException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (BookException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error(
					"Impossible to translate the validation fixed value field  bookID ["
							+ bookID + "] for the session [" + sessionID + "]",
					e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_VALIDATIONFIELDS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static Map translateFixedValuesForSaveOrUpdate(String sessionID,
			Integer bookID, Map values, String entidad)
			throws AttributesException, BookException, SessionException,
			ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);
		Validator.validate_NotNull(values,
				ValidationException.ATTRIBUTE_VALIDATION_FIELD_VALUE);

		Map result = new HashMap();
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

			Iuserusertype userusertype = (Iuserusertype) cacheBag
					.get(HIBERNATE_Iuserusertype);
			ScrOfic scrOfic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);
			if (userusertype.getType() != IDocKeys.IUSERUSERTYPE_USER_TYPE_ADMIN) {
				privOrgs = DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getPrivOrgs(scrOfic.getId().intValue(), entidad);
			}

			result.putAll(getResultScrOfic(session, values, new Integer(
					AxSf.FLD5_FIELD_ID), false));
			result.putAll(getResultScrOrg(session, values, new Integer(
					AxSf.FLD7_FIELD_ID), privOrgs, false, true));
			result.putAll(getResultScrOrg(session, values, new Integer(
					AxSf.FLD8_FIELD_ID), privOrgs, false, true));

			if (Repository.getInstance(entidad).isInBook(bookID).booleanValue()) {
				result.putAll(getResultScrOrg(session, values, new Integer(
						AxSf.FLD13_FIELD_ID), privOrgs, false, false));
				result.putAll(getResultScrCa(session, values, new Integer(
						AxSf.FLD16_FIELD_ID), false, true));
			} else {
				result.putAll(getResultScrCa(session, values, new Integer(
						AxSf.FLD12_FIELD_ID), false, false));
			}

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (AttributesException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (BookException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (ValidationException vE) {
			HibernateUtil.rollbackTransaction(tran);
			throw vE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error(
					"Impossible to translate the validation fixed value field  bookID ["
							+ bookID + "] for the session [" + sessionID + "]",
					e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_VALIDATIONFIELDS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

}