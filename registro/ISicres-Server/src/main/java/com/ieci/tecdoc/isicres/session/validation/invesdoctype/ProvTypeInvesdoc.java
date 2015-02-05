package com.ieci.tecdoc.isicres.session.validation.invesdoctype;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.expression.Expression;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesicres.ScrCity;
import com.ieci.tecdoc.common.invesicres.ScrProv;
import com.ieci.tecdoc.common.isicres.ValidationResults;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.isicres.session.validation.ValidationSessionUtil;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.Validator;
import com.ieci.tecdoc.utils.cache.CacheFactory;

public class ProvTypeInvesdoc extends ValidationSessionUtil implements
		ServerKeys, HibernateKeys {

	private static final Logger log = Logger.getLogger(ProvTypeInvesdoc.class);

	public static ValidationResults getInitialValidationTypeInvesdocProvResults(
			String sessionID, int firstRow, int idCrl, int maxResults,
			String where, String additionalFieldName, String docColName,
			String entidad) throws AttributesException, SessionException,
			ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		if (where != null && where.length() > 0) {
			return getCitiesFromProv(sessionID, firstRow, idCrl, maxResults,
					where, additionalFieldName, docColName, entidad);
		} else {
			return getProvForCities(sessionID, firstRow, maxResults, where,
					additionalFieldName, docColName, entidad);
		}
	}

	public static ValidationResults getCitiesFromProv(String sessionID,
			int firstRow, int idCrl, int maxResults, String where,
			String additionalFieldName, String docColName, String entidad)
			throws AttributesException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		ValidationResults result = new ValidationResults();
		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();
			// Recuperamos la sesión
			CacheFactory.getCacheInterface().getCacheEntry(sessionID);

			// Calculamos el tamaño de los resultados
			Criteria criteriaResults = session.createCriteria(ScrCity.class);

			StringBuffer querySize = new StringBuffer();
			querySize.append("select count(*) from ");
			querySize.append(HIBERNATE_ScrCity);
			if (where != null && where.length() > 0) {
				querySize.append(" as scr where ");
			} else {
				querySize.append(" as scr where scr.idProv = ");
				querySize.append(idCrl);
				criteriaResults
						.add(Expression.eq("idProv", new Integer(idCrl)));
			}

			result = getValidationResults(session, criteriaResults, querySize
					.toString(), where, 1, firstRow, maxResults, "name");

			result.setAdditionalFieldName(additionalFieldName);
			result.setDocColName(docColName);
			// result.setResults(cities);

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (Exception e) {
			log.error(
					"Impossible to load cities for getProvForCities for session ["
							+ sessionID + "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_PROV);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static ValidationResults getProvForCities(String sessionID,
			int firstRow, int maxResults, String where,
			String additionalFieldName, String docColName, String entidad)
			throws AttributesException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		ValidationResults result = new ValidationResults();
		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();
			// Recuperamos la sesión
			CacheFactory.getCacheInterface().getCacheEntry(sessionID);

			// Calculamos el tamaño de los resultados
			StringBuffer querySize = new StringBuffer();
			querySize.append("select count(*) from ");
			querySize.append(HIBERNATE_ScrProv);
			querySize.append(" as scr");
			if (where != null && where.length() > 0) {
				querySize.append(" as scr where ");
			}

			Criteria criteriaResults = session.createCriteria(ScrProv.class);
			result = getValidationResults(session, criteriaResults, querySize
					.toString(), where, 1, firstRow, maxResults, "name");

			result.setAdditionalFieldName(additionalFieldName);
			result.setDocColName(docColName);
			// result.setResults(provincies);

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (Exception e) {
			log.error(
					"Impossible to load provinces for getProvForCities for session ["
							+ sessionID + "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_PROV);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static ScrProv getScrProvById(String sessionID, int id,
			String entidad) throws AttributesException, SessionException,
			ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		ScrProv result = null;
		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheFactory.getCacheInterface().getCacheEntry(sessionID);

			result = (ScrProv) session.load(ScrProv.class, new Integer(id));

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to load ScrProv for session [" + sessionID
					+ "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_PROV);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static ScrCity getScrCityById(String sessionID, int id,
			String entidad) throws AttributesException, SessionException,
			ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		ScrCity result = null;
		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheFactory.getCacheInterface().getCacheEntry(sessionID);

			result = (ScrCity) session.load(ScrCity.class, new Integer(id));

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to load ScrCity for session [" + sessionID
					+ "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_CITIES);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

}
