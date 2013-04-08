/**
 *
 */
package com.ieci.tecdoc.isicres.session.attributes;

import gnu.trove.THashMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.invesdoc.Idocbtblctlg;
import com.ieci.tecdoc.common.invesdoc.Idocvtblctlg;
import com.ieci.tecdoc.common.invesicres.ScrCa;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrOrg;
import com.ieci.tecdoc.common.isicres.Keys;
import com.ieci.tecdoc.common.isicres.ValidationResultCode;
import com.ieci.tecdoc.common.isicres.ValidationResultScrOrg;
import com.ieci.tecdoc.common.isicres.ValidationResults;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.utils.BBDDUtils;
import com.ieci.tecdoc.common.utils.EntityByLanguage;
import com.ieci.tecdoc.common.utils.ISOfficesValidator;
import com.ieci.tecdoc.common.utils.ISSubjectsValidator;
import com.ieci.tecdoc.common.utils.ISUnitsValidator;
import com.ieci.tecdoc.common.utils.Repository;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.VFldVldDef;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.ValidationFormat;
import com.ieci.tecdoc.idoc.decoder.validation.idocvtblctlg.IdocvtblctlgTV;
import com.ieci.tecdoc.idoc.decoder.validation.idocvtblctlg.IdocvtblctlgTVS;
import com.ieci.tecdoc.isicres.session.validation.ValidationSessionUtil;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.Validator;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

/**
 * @author 66575267
 *
 */
public class AttributesSessionUtil implements HibernateKeys {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private static final Logger log = Logger
			.getLogger(AttributesSessionUtil.class);

	protected static final String FIELD_VALUE_WITH_WHERE = "SELECT {0} FROM {1} WHERE {2} AND {3}=?";

	protected static final String FIELD_VALUE_WITHOUT_WHERE = "SELECT {0} FROM {1} WHERE {2}=?";

	protected static final String FIELD_VALUES_WITH_ONE_WHERE_TVS = "SELECT {0} FROM {1} WHERE {2}";

	protected static final String FIELD_VALUES_WITH_TWO_WHERE_TVS = "SELECT {0} FROM {1} WHERE {2} AND {3}";

	protected static final String FIELD_VALUES_WITHOUT_WHERE_TVS = "SELECT  {0} FROM {1}";

	protected static final String FIELD_VALUES_WITH_ONE_WHERE_TV = "SELECT {0} FROM {1} WHERE {2}";

	protected static final String FIELD_VALUES_WITH_TWO_WHERE_TV = "SELECT {0} FROM {1} WHERE {2} AND {3}";

	protected static final String FIELD_VALUES_WITHOUT_WHERE_TV = "SELECT  {0} FROM {1}";

	/***************************************************************************
	 * Protected methods
	 **************************************************************************/

	protected static boolean isFldidValid(Integer bookID, int fldid,
			String entidad) {
		int firstValidationField = Keys.EREG_FDR_MATTER + 1;
		if (Repository.getInstance(entidad).isOutBook(bookID.toString())
				.booleanValue()) {
			firstValidationField = Keys.SREG_FDR_MATTER + 1;
		}

		// Validamos que el atributo sea mayor que 17 o 13
		if (fldid >= firstValidationField) {
			return true;
		}

		return false;
	}

	protected static String getExtendedValidationFieldValue(String sessionID,
			Integer bookID, int fldid, String value, boolean isTVNull,
			String language, String entidad) throws AttributesException,
			BookException, SessionException, ValidationException {

		String result = null;
		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			result = getExtendedValidationFieldValue(session, sessionID, bookID, fldid,
					value, isTVNull, language, entidad);

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
	
	protected static String getExtendedValidationFieldValue(Session session, String sessionID,
			Integer bookID, int fldid, String value, boolean isTVNull,
			String language, String entidad) throws AttributesException,
			BookException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);
		Validator.validate_NotNull(value,
				ValidationException.ATTRIBUTE_VALIDATION_FIELD_VALUE);

		String result = null;
		try {

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
					result = getFieldValue(tvs, idocbtblctlg, value, language,
							entidad);
				} else if (vldDef.getVldtype() == IDocKeys.IDOCARCHDET_IDOC_VLD_TYPE_TV) {
					Idocvtblctlg idocvtblctlg = (Idocvtblctlg) session.load(
							Idocvtblctlg.class, new Integer(vldDef.getId1()));
					Idocbtblctlg idocbtblctlg = (Idocbtblctlg) session.load(
							Idocbtblctlg.class, new Integer(idocvtblctlg
									.getBtblid()));
					IdocvtblctlgTV tv = new IdocvtblctlgTV(idocvtblctlg
							.getInfo());
					result = getFieldValue(tv, idocbtblctlg, value, language,
							entidad, isTVNull);
				} else {
					result = value;
				}
			} else {
				log.info("Campo no soportado por la validacion fldid [" + fldid
						+ "]");
				throw new AttributesException(
						AttributesException.ERROR_NOT_VALIDATION_FIELD);
			}

			return result;
		} catch (AttributesException sE) {
			throw sE;
		} catch (SessionException sE) {
			throw sE;
		} catch (BookException sE) {
			throw sE;
		} catch (Exception e) {
			log.error("Impossible to load the validation value field  bookID ["
					+ bookID + "] fldid [" + fldid + "] for the session ["
					+ sessionID + "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_VALIDATIONFIELDS);
		} 
	}

	protected static Map getExtendedValidationFieldValues(Session session,
			ValidationFormat validationFormat, int fldid, List values,
			String language, String entidad) throws HibernateException,
			NamingException, SQLException {
		Map result = new HashMap(values.size());
		VFldVldDef vldDef = getVldDef(validationFormat, fldid);

		// Si es de validación simple, el valor devuelto es el
		// pasado como parámetro
		if (vldDef.getVldtype() == IDocKeys.IDOCARCHDET_IDOC_VLD_TYPE_TVS) {
			Idocvtblctlg idocvtblctlg = (Idocvtblctlg) session.load(
					Idocvtblctlg.class, new Integer(vldDef.getId1()));
			Idocbtblctlg idocbtblctlg = (Idocbtblctlg) session.load(
					Idocbtblctlg.class, new Integer(idocvtblctlg.getBtblid()));
			IdocvtblctlgTVS tvs = new IdocvtblctlgTVS(idocvtblctlg.getInfo());

			for (Iterator it1 = values.iterator(); it1.hasNext();) {
				String value = (String) it1.next();
				String aux = getFieldValue(tvs, idocbtblctlg, value, language,
						entidad);
				result.put(value, aux);
			}
		} else if (vldDef.getVldtype() == IDocKeys.IDOCARCHDET_IDOC_VLD_TYPE_TV) {
			for (Iterator it1 = values.iterator(); it1.hasNext();) {
				String value = (String) it1.next();
				result.put(value, value);
			}
		}

		return result;
	}

	protected static ValidationFormat getValidationFormat(String sessionID,
			Integer bookID) throws BookException, SessionException,
			TecDocException {
		// Recuperamos la sesión
		CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
				sessionID);

		// Es necesario tener el libro abierto para consultar su contenido.
		if (!cacheBag.containsKey(bookID)) {
			throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
		}

		THashMap bookInformation = (THashMap) cacheBag.get(bookID);
		Idocarchdet idocarchdet = (Idocarchdet) bookInformation
				.get(IDocKeys.IDOCARCHDET_VLD_DEF_ASOBJECT);

		ValidationFormat validationFormat = new ValidationFormat(idocarchdet
				.getDetval());

		return validationFormat;
	}

	protected static VFldVldDef getVldDef(ValidationFormat validationFormat,
			int fldid) {
		VFldVldDef vldDef = null;
		int id1 = Integer.MIN_VALUE;
		for (Iterator it = validationFormat.getFldvlddefs().values().iterator(); it
				.hasNext()
				&& id1 == Integer.MIN_VALUE;) {
			vldDef = (VFldVldDef) it.next();
			if (vldDef.getFldid() == fldid) {
				id1 = vldDef.getId1();
			}
		}
		return vldDef;
	}

	protected static String getFieldValue(IdocvtblctlgTVS tvs,
			Idocbtblctlg idocbtblctlg, String value, String language,
			String entidad) throws NamingException, SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String result = null;
		try {
			String query = null;
			String[] parameters = null;
			String tableName = EntityByLanguage.getTableNameExtendedFields(
					idocbtblctlg.getName(), language);

			if (tvs.getWhere() != null && tvs.getWhere().length() > 0) {
				query = FIELD_VALUE_WITH_WHERE;
				// parameters = new String[] { tvs.getSustColName(),
				// idocbtblctlg.getName(), tvs.getWhere(),
				// tvs.getDocColName() };
				parameters = new String[] { tvs.getSustColName(), tableName,
						tvs.getWhere(), tvs.getDocColName() };
			} else {
				query = FIELD_VALUE_WITHOUT_WHERE;
				// parameters = new String[] { tvs.getSustColName(),
				// idocbtblctlg.getName(), tvs.getDocColName() };
				parameters = new String[] { tvs.getSustColName(), tableName,
						tvs.getDocColName() };
			}
			query = MessageFormat.format(query, parameters);
			con = BBDDUtils.getConnection(entidad);
			ps = con.prepareStatement(query);
			ps.setString(1, value);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getString(tvs.getSustColName());
			}
		} finally {
			BBDDUtils.close(rs);
			BBDDUtils.close(ps);
			BBDDUtils.close(con);
		}
		return result;
	}

	protected static String getFieldValue(IdocvtblctlgTV tv,
			Idocbtblctlg idocbtblctlg, String value, String language,
			String entidad) throws NamingException, SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String result = null;
		try {
			String query = null;
			String[] parameters = null;
			String tableName = EntityByLanguage.getTableNameExtendedFields(
					idocbtblctlg.getName(), language);

			if (tv.getWhere() != null && tv.getWhere().length() > 0) {
				query = FIELD_VALUE_WITH_WHERE;
				// parameters = new String[] { tv.getDocColName(),
				// idocbtblctlg.getName(), tv.getWhere(),
				// " AND " + tv.getDocColName() };
				parameters = new String[] { tv.getDocColName(), tableName,
						tv.getWhere(), " AND " + tv.getDocColName() };
			} else {
				query = FIELD_VALUE_WITHOUT_WHERE;
				// parameters = new String[] { tv.getDocColName(),
				// idocbtblctlg.getName(), tv.getDocColName() };
				parameters = new String[] { tv.getDocColName(), tableName,
						tv.getDocColName() };
			}
			query = MessageFormat.format(query, parameters);
			con = BBDDUtils.getConnection(entidad);
			ps = con.prepareStatement(query);
			ps.setString(1, value);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getString(tv.getDocColName());
			}
		} finally {
			BBDDUtils.close(rs);
			BBDDUtils.close(ps);
			BBDDUtils.close(con);
		}
		return result;
	}

	protected static String getFieldValue(IdocvtblctlgTV tv,
			Idocbtblctlg idocbtblctlg, String value, String language,
			String entidad, boolean isTVNull) throws NamingException,
			SQLException, Exception {
		if (!isTVNull) {
			return getFieldValue(tv, idocbtblctlg, value, language, entidad);
		} else {
			ValidationResults vr = null;
			if (tv.getLogDocColName().equals("POBLACION")) {
				vr = getFieldValues(tv, idocbtblctlg, 0, 1000, tv
						.getDocColName()
						+ "='" + value + "'",
						IDocKeys.IDOCARCHDET_IDOC_VLD_TYPE_TV, language,
						entidad);
			} else {
				vr = getFieldValues(tv, idocbtblctlg, 0, 1000, null,
						IDocKeys.IDOCARCHDET_IDOC_VLD_TYPE_TV, language,
						entidad);
			}
			if (vr.getTotalSize() == 0) {
				return null;
			} else {
				Collection col = vr.getResults();
				Object[] obj = null;
				boolean found = false;
				for (Iterator it = col.iterator(); it.hasNext() && !found;) {
					obj = (Object[]) it.next();
					found = obj[1].toString().toUpperCase().equals(
							value.toUpperCase());
				}
				if (!found) {
					return null;
				} else {
					return obj[1].toString();
				}
			}
		}
	}

	protected static ValidationResults getFieldValues(IdocvtblctlgTV tv,
			Idocbtblctlg idocbtblctlg, int firstRow, int maxResults,
			String where, int type, String language, String entidad)
			throws NamingException, SQLException, Exception {
		ValidationResults result = new ValidationResults();

		String transformedWhere = getWhereLike(where);

		result.setDocColName(tv.getDocColName());
		result.setAdditionalFieldName(tv.getLogDocColName());

		// Calculamos el tamaño de la query
		executeCountQuery(getQueryCount(tv.getWhere(), transformedWhere,
				idocbtblctlg, type), result, entidad);

		if (result.getTotalSize() > 0) {
			// Obtenemos los resultados
			executeQuery(getQuery(tv.getWhere(), transformedWhere,
					idocbtblctlg, type, language, tv.getDocColName(), null),
					firstRow, maxResults, result, type, entidad);
		}

		return result;
	}

	protected static ValidationResults getFieldValues(IdocvtblctlgTVS tvs,
			Idocbtblctlg idocbtblctlg, int firstRow, int maxResults,
			String where, int type, String language, String entidad)
			throws NamingException, SQLException, Exception {
		ValidationResults result = new ValidationResults();

		String transformedWhere = getWhereLike(where);

		result.setDocColName(tvs.getDocColName());
		result.setSusColName(tvs.getSustColName());
		result.setAdditionalFieldName(tvs.getLogDocColName());

		// Calculamos el tamaño de la query
		executeCountQuery(getQueryCount(tvs.getWhere(), transformedWhere,
				idocbtblctlg, type), result, entidad);

		if (result.getTotalSize() > 0) {
			// Obtenemos los resultados
			executeQuery(getQuery(tvs.getWhere(), transformedWhere,
					idocbtblctlg, type, language, tvs.getDocColName(), tvs
							.getSustColName()), firstRow, maxResults, result,
					type, entidad);
		}

		return result;
	}

	protected static String getScrOrgDescription(Session session,
			ScrOrg scrOrg, String language, String entidad)
			throws HibernateException, AttributesException {
		ValidationResultScrOrg validationResultScrOrg = ValidationSessionUtil
				.getValidationResultScrOrg(session, scrOrg, language, entidad);
		String des = validationResultScrOrg.getScrOrgName();
		if (validationResultScrOrg.getScrOrgFatherName() != null
				|| validationResultScrOrg.getScrOrgParentAcron() != null) {
			des = des + " (";
			boolean haveOrgFather = false;

			if (validationResultScrOrg.getScrOrgFatherName() != null) {
				des = des + validationResultScrOrg.getScrOrgFatherName();
				haveOrgFather = true;
			}

			if (haveOrgFather) {
				des = des + " - ";
			}

			if (validationResultScrOrg.getScrOrgParentAcron() != null) {
				des = des + validationResultScrOrg.getScrOrgParentAcron();
			}

			des = des + ")";
		}

		return des;
	}

	protected static ValidationResultCode setValidationResult(Session session,
			String code, Integer scrOficId, boolean forEreg, int fldAdd,
			ValidationResultCode result, String language, String entidad)
			throws HibernateException, ValidationException, AttributesException {
		ScrCa scrCa = ISSubjectsValidator.getSubjectForOfic(session, code,
				true, forEreg, scrOficId);
		String matter = "";
		if (scrCa != null) {
			if (!language.equals("es")) {
				try {
					matter = DBEntityDAOFactory.getCurrentDBEntityDAO()
							.getDescriptionByLocale(scrCa.getId(), false,
									true, language,
									EntityByLanguage.getTableName(16),
									entidad);
				}  catch (Exception e) {
					matter = scrCa.getMatter();
				}
			} else {
				matter = scrCa.getMatter();
			}
			result.setDescription(matter);
			if (scrCa.getIdOrg() != 0) {
				ScrOrg scrOrg = (ScrOrg) session.load(ScrOrg.class,
						new Integer(scrCa.getIdOrg()));
				if (scrOrg != null) {
					result.setFldIdAdd(new Integer(fldAdd));
					result.setCodeAdd(scrOrg.getCode());
					String desc = getScrOrgDescription(session, scrOrg,
							language, entidad);
					if (desc != null) {
						result.setDescriptionAdd(desc);
					} else {
						result.setDescriptionAdd(scrOrg.getName());
					}
				}
			}
		}

		return result;
	}

	protected static Map getResultScrOrg(Session session, Map values,
			Integer fieldId, List privOrgs, boolean isList, boolean isUnit)
			throws HibernateException {
		Map result = new HashMap();
		if (values.containsKey(fieldId)) {
			if (values.get(fieldId) != null) {

				try {
					if (isList
							&& values.get(fieldId).getClass().equals(
									ArrayList.class)) {
						List list = new ArrayList();
						for (Iterator it = ((List) values.get(fieldId))
								.iterator(); it.hasNext();) {
							String code = (String) it.next();
							ScrOrg org = null;
							if (isUnit) {
								org = ISUnitsValidator.getUnit(session, code,
										false, privOrgs);
							} else {
								org = ISUnitsValidator.getEntReg(session, code,
										false);
							}
							list.add(org.getId());
						}
						result.put(fieldId, list);
					} else {
						ScrOrg org = null;
						if (isUnit) {
							org = ISUnitsValidator.getUnit(session, values.get(
									fieldId).toString(), false, privOrgs);
						} else {
							org = ISUnitsValidator.getEntReg(session, values
									.get(fieldId).toString(), false);
						}

						result.put(fieldId, org.getId());
					}
				} catch (ValidationException e) {
				}
			} else if (!isList) {
				result.put(fieldId, "");
			}
		}

		return result;
	}

	protected static Map getResultScrCa(Session session, Map values,
			Integer fieldId, boolean isList, boolean forEreg)
			throws HibernateException, ValidationException {
		Map result = new HashMap();
		if (values.containsKey(fieldId)) {
			if (values.get(fieldId) != null) {

				try {
					if (isList
							&& values.get(fieldId).getClass().equals(
									ArrayList.class)) {
						List list = new ArrayList();
						for (Iterator it = ((List) values.get(fieldId))
								.iterator(); it.hasNext();) {
							String code = (String) it.next();
							ScrCa ca = ISSubjectsValidator.getSubject(session,
									code, false, forEreg);
							list.add(ca.getId());
						}
						result.put(fieldId, list);
					} else {
						ScrCa ca = ISSubjectsValidator.getSubject(session,
								values.get(fieldId).toString(), false, forEreg);
						result.put(fieldId, ca.getId());
					}
				} catch (ValidationException e) {
				}
			} else if (!isList) {
				result.put(fieldId, "");
			}
		}
		return result;
	}

	protected static Map getResultScrOfic(Session session, Map values,
			Integer fieldId, boolean isList) throws HibernateException,
			ValidationException {
		Map result = new HashMap();
		if (values.containsKey(fieldId)) {
			if (values.get(fieldId) != null) {

				try {
					if (isList
							&& values.get(fieldId).getClass().equals(
									ArrayList.class)) {
						List list = new ArrayList();
						for (Iterator it = ((List) values.get(fieldId))
								.iterator(); it.hasNext();) {
							String code = (String) it.next();
							ScrOfic ofic = ISOfficesValidator.getOffice(
									session, code);
							list.add(ofic.getId());
						}
						result.put(fieldId, list);
					} else {
						ScrOfic ofic = ISOfficesValidator.getOffice(session,
								values.get(fieldId).toString());
						result.put(fieldId, ofic.getId());
					}
				} catch (ValidationException e) {
				}
			} else if (!isList) {
				result.put(fieldId, "");
			}
		}
		return result;
	}

	/***************************************************************************
	 * Private methods
	 **************************************************************************/

	private static String getWhereLike(String where) throws Exception {
		if (where == null) {
			return null;
		}
		if (where.length() == 0) {
			return "";
		}

		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < where.length(); i++) {
			if (where.charAt(i) == '*') {
				buffer.append(DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getLikeCharacter());
			} else {
				buffer.append(where.charAt(i));
			}
		}

		return buffer.toString();
	}

	private static String getQueryCount(String tWhere, String where,
			Idocbtblctlg idocbtblctlg, int type) {
		String query = null;
		String[] parameters = null;
		String columns = " count(*) ";

		if (tWhere != null && tWhere.length() > 0 && where != null
				&& where.length() > 0) {
			if (type == IDocKeys.IDOCARCHDET_IDOC_VLD_TYPE_TV) {
				query = FIELD_VALUES_WITH_TWO_WHERE_TV;
			} else {
				query = FIELD_VALUES_WITH_TWO_WHERE_TVS;
			}
			parameters = new String[] { columns, idocbtblctlg.getName(),
					tWhere, " AND " + where };
		} else if (tWhere != null && tWhere.length() > 0) {
			if (type == IDocKeys.IDOCARCHDET_IDOC_VLD_TYPE_TV) {
				query = FIELD_VALUES_WITH_ONE_WHERE_TV;
			} else {
				query = FIELD_VALUES_WITH_ONE_WHERE_TVS;
			}
			parameters = new String[] { columns, idocbtblctlg.getName(), tWhere };
		} else if (where != null && where.length() > 0) {
			if (type == IDocKeys.IDOCARCHDET_IDOC_VLD_TYPE_TV) {
				query = FIELD_VALUES_WITH_ONE_WHERE_TV;
			} else {
				query = FIELD_VALUES_WITH_ONE_WHERE_TVS;
			}
			parameters = new String[] { columns, idocbtblctlg.getName(), where };
		} else {
			if (type == IDocKeys.IDOCARCHDET_IDOC_VLD_TYPE_TV) {
				query = FIELD_VALUES_WITHOUT_WHERE_TV;
			} else {
				query = FIELD_VALUES_WITHOUT_WHERE_TVS;
			}
			parameters = new String[] { columns, idocbtblctlg.getName() };
		}

		return MessageFormat.format(query, parameters);
	}

	private static void executeQuery(String query, int firstRow,
			int maxResults, ValidationResults result, int type, String entidad)
			throws NamingException, SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		if (log.isDebugEnabled()) {
			log.debug(query);
		}

		try {
			con = BBDDUtils.getConnection(entidad);
			ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			rs = ps.executeQuery();
			rs.setFetchSize(maxResults);
			if (firstRow == 0) {
				rs.first();
				rs.previous();
			} else {
				rs.absolute(firstRow);
			}
			List aux = new ArrayList();
			int index = 0;

			while (rs.next() && index < maxResults) {
				if (type == IDocKeys.IDOCARCHDET_IDOC_VLD_TYPE_TVS) {
					aux.add(index++, new Object[] { rs.getString(1),
							rs.getString(2) });
				} else if (type == IDocKeys.IDOCARCHDET_IDOC_VLD_TYPE_TV) {
					aux.add(index++, new Object[] { rs.getString(1),
							rs.getString(1) });
				}
			}
			result.setResults(aux);
		} finally {
			BBDDUtils.close(rs);
			BBDDUtils.close(ps);
			BBDDUtils.close(con);
		}
	}

	private static void executeCountQuery(String query,
			ValidationResults result, String entidad) throws NamingException,
			SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		if (log.isDebugEnabled()) {
			log.debug(query);
		}

		try {
			con = BBDDUtils.getConnection(entidad);
			ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			rs = ps.executeQuery();
			if (rs.next()) {
				result.setTotalSize(rs.getInt(1));
			}
		} finally {
			BBDDUtils.close(rs);
			BBDDUtils.close(ps);
			BBDDUtils.close(con);
		}
	}

	private static String getQuery(String tWhere, String where,
			Idocbtblctlg idocbtblctlg, int type, String language,
			String docColName, String sustColName) {
		String query = null;
		String[] parameters = null;
		String result = null;
		String columns = null;
		String tableName = EntityByLanguage.getTableNameExtendedFields(
				idocbtblctlg.getName(), language);

		if (type == IDocKeys.IDOCARCHDET_IDOC_VLD_TYPE_TV) {
			columns = docColName;
		} else {
			columns = docColName + ", " + sustColName;
		}

		if (tWhere != null && tWhere.length() > 0 && where != null
				&& where.length() > 0) {
			if (type == IDocKeys.IDOCARCHDET_IDOC_VLD_TYPE_TV) {
				query = FIELD_VALUES_WITH_TWO_WHERE_TV;
			} else {
				query = FIELD_VALUES_WITH_TWO_WHERE_TVS;
			}
			// parameters = new String[] { columns, idocbtblctlg.getName(),
			// tWhere, " AND " + where };
			parameters = new String[] { columns, tableName, tWhere,
					" AND " + where };
		} else if (tWhere != null && tWhere.length() > 0) {
			if (type == IDocKeys.IDOCARCHDET_IDOC_VLD_TYPE_TV) {
				query = FIELD_VALUES_WITH_ONE_WHERE_TV;
			} else {
				query = FIELD_VALUES_WITH_ONE_WHERE_TVS;
			}
			// parameters = new String[] { columns, idocbtblctlg.getName(),
			// tWhere };
			parameters = new String[] { columns, tableName, tWhere };
		} else if (where != null && where.length() > 0) {
			if (type == IDocKeys.IDOCARCHDET_IDOC_VLD_TYPE_TV) {
				query = FIELD_VALUES_WITH_ONE_WHERE_TV;
			} else {
				query = FIELD_VALUES_WITH_ONE_WHERE_TVS;
			}
			// parameters = new String[] { columns, idocbtblctlg.getName(),
			// where };
			parameters = new String[] { columns, tableName, where };
		} else {
			if (type == IDocKeys.IDOCARCHDET_IDOC_VLD_TYPE_TV) {
				query = FIELD_VALUES_WITHOUT_WHERE_TV;
			} else {
				query = FIELD_VALUES_WITHOUT_WHERE_TVS;
			}
			// parameters = new String[] { columns, idocbtblctlg.getName() };
			parameters = new String[] { columns, tableName };
		}
		result = MessageFormat.format(query, parameters) + " ORDER BY "
				+ columns;
		return result;
	}

}
