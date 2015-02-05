package com.ieci.tecdoc.isicres.session.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.expression.Expression;
import net.sf.hibernate.expression.Order;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Iuserusertype;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrOrg;
import com.ieci.tecdoc.common.invesicres.ScrTypeproc;
import com.ieci.tecdoc.common.isicres.ValidationResults;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.EntityByLanguage;
import com.ieci.tecdoc.common.utils.Repository;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.Validator;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

public class ValidationSession extends ValidationSessionUtil implements
		ServerKeys, HibernateKeys {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private static final Logger log = Logger.getLogger(ValidationSession.class);

	/***************************************************************************
	 * Public methods
	 **************************************************************************/

	public static ValidationResults getScrTypeproc(String sessionID,
			int firstRow, int maxResults, String where, int enabled,
			String entidad) throws AttributesException, SessionException,
			ValidationException {
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
			querySize.append(HIBERNATE_ScrTypeproc);
			querySize.append(" as scr ");

			Criteria criteriaResults = session
					.createCriteria(ScrTypeproc.class);
			if (enabled == 1) {
				querySize.append("where enabled=1");
				criteriaResults.add(Expression.eq("enabled", new Integer(1)));
			}

			result = getValidationResults(session, criteriaResults, querySize
					.toString(), where, enabled, firstRow, maxResults, "name");

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to load ScrTypeProc for session ["
					+ sessionID + "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_VALIDATIONLISTFIELDS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static ValidationResults getScrOfic(String sessionID, int firstRow,
			int maxResults, String where, int enabled, Locale locale,
			String entidad) throws AttributesException, SessionException,
			ValidationException {
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
			querySize.append(HIBERNATE_ScrOfic);
			querySize.append(" as scr ");

			// Criteria criteriaResults = session.createCriteria(ScrOfic.class);
			Criteria criteriaResults = session.createCriteria(EntityByLanguage
					.getScrOficLanguage(locale.getLanguage()));
			if (enabled == 1) {
				querySize.append("where scr.disableDate is null");
				criteriaResults.add(Expression.isNull("disableDate"));
			}

			result = getValidationResults(session, criteriaResults, querySize
					.toString(), where, enabled, firstRow, maxResults, "name");

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to load ScrOfic for session [" + sessionID
					+ "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_VALIDATIONLISTFIELDS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static ValidationResults getScrtt(String sessionID, int firstRow,
			int maxResults, String where, Locale locale, String entidad)
			throws AttributesException, SessionException, ValidationException {
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
			querySize.append(HIBERNATE_ScrTt);
			querySize.append(" as scr ");

			// Criteria criteriaResults = session.createCriteria(ScrTt.class);
			Criteria criteriaResults = session.createCriteria(EntityByLanguage
					.getScrTtLanguage(locale.getLanguage()));

			result = getValidationResults(session, criteriaResults, querySize
					.toString(), where, 0, firstRow, maxResults, "transport");

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to load ScrTt for session [" + sessionID
					+ "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_VALIDATIONLISTFIELDS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static ValidationResults getScrOrgs(String sessionID, int firstRow,
			int maxResults, String where, int enabled, Locale locale,
			String entidad) throws AttributesException, SessionException,
			ValidationException {
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
			querySize.append(HIBERNATE_ScrOrg);
			querySize.append(" as scr where scr.scrTypeadm.id=0 ");

			// Criteria criteriaResults = session.createCriteria(ScrOrg.class);
			Criteria criteriaResults = session.createCriteria(EntityByLanguage
					.getScrOrgLanguage(locale.getLanguage()));
			criteriaResults.add(Expression.eq("scrTypeadm.id", new Integer(0)));
			if (enabled == 1) {
				querySize.append(" and scr.enabled=1 ");
				criteriaResults.add(Expression.eq("enabled", new Integer(1)));
			}

			result = getValidationResults(session, criteriaResults, querySize
					.toString(), where, 1, firstRow, maxResults, "name");

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to load ScrOrg for session [" + sessionID
					+ "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_VALIDATIONLISTFIELDS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static ValidationResults getScrOrgsForAdminUnits(String sessionID,
			int firstRow, int maxResults, String where, int enabled,
			String language, String entidad) throws AttributesException,
			SessionException, ValidationException {
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
			querySize.append(HIBERNATE_ScrOrg);
			querySize.append(" as scr ");

			// Criteria criteriaResults = session.createCriteria(ScrOrg.class);
			Criteria criteriaResults = session.createCriteria(EntityByLanguage
					.getScrOrgLanguage(language));
			if (enabled == 1) {
				querySize.append(" where scr.enabled=1 ");
				criteriaResults.add(Expression.eq("enabled", new Integer(1)));
			}

			result = getValidationResults(session, criteriaResults, querySize
					.toString(), where, enabled, firstRow, maxResults, "name");

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error(
					"Impossible to load ScrOrgs for getScrOrgsForAdminUnits for session ["
							+ sessionID + "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_VALIDATIONLISTFIELDS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static ValidationResults getScrOrgsForDistritution(String sessionID,
			int firstRow, int maxResults, String where, int enabled,
			Locale locale, String entidad) throws AttributesException,
			SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		ValidationResults result = new ValidationResults();
		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheFactory.getCacheInterface().getCacheEntry(sessionID);

			result = getValidationResultsScrOrgForAdminUnits(session, enabled,
					where, firstRow, maxResults, true, false, 0, null, 0,
					locale.getLanguage(), entidad);

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error(
					"Impossible to load ScrOrgs for Distribution for session ["
							+ sessionID + "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_VALIDATIONLISTFIELDS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static ValidationResults getScrOrgsForAdminUnitsWithType(
			String sessionID, int firstRow, int maxResults, String where,
			int enabled, int type, Locale locale, String entidad)
			throws AttributesException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		ValidationResults result = new ValidationResults();
		Transaction tran = null;

		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			Iuserusertype userusertype = (Iuserusertype) cacheBag
					.get(HIBERNATE_Iuserusertype);
			ScrOfic scrOfic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

			result = getValidationResultsScrOrgForAdminUnits(session, enabled,
					where, firstRow, maxResults, false, false, type, scrOfic
							.getId(), userusertype.getType(), locale
							.getLanguage(), entidad);

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error(
					"Impossible to load ScrOrgs for getScrOrgsForAdminUnitsWithType for session ["
							+ sessionID + "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_VALIDATIONLISTFIELDS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static ValidationResults getScrOrgsForAdminUnitsWithFather(
			String sessionID, int firstRow, int maxResults, String where,
			int enabled, int father, Locale locale, String entidad)
			throws AttributesException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		ValidationResults result = new ValidationResults();
		Transaction tran = null;

		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			Iuserusertype userusertype = (Iuserusertype) cacheBag
					.get(HIBERNATE_Iuserusertype);
			ScrOfic scrOfic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

			result = getValidationResultsScrOrgForAdminUnits(session, enabled,
					where, firstRow, maxResults, false, true, father, scrOfic
							.getId(), userusertype.getType(), locale
							.getLanguage(), entidad);

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log
					.error(
							"Impossible to load ScrOrgs for getScrOrgsForAdminUnitsWithFather for session ["
									+ sessionID + "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_VALIDATIONLISTFIELDS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static ValidationResults getScrTypeAdm(String sessionID,
			int firstRow, int maxResults, String where, Locale locale,
			String entidad) throws AttributesException, SessionException,
			ValidationException {
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
			querySize.append(HIBERNATE_ScrTypeadm);
			querySize.append(" as scr ");
			if (where != null && where.length() > 0) {
				querySize.append(" where scr.id != 0 and description ");
				if (where.startsWith("LIKE")) {
					querySize.append(" like '");
					querySize.append(getWhereLike(where));
					querySize.append("'");
				} else if (where.startsWith("=")) {
					querySize.append(where);
				}
			} else if (where == null || (where != null && where.length() == 0)) {
				querySize.append(" where scr.id != 0 ");
			}
			result.setTotalSize(((Integer) session
					.iterate(querySize.toString()).next()).intValue());

			// Recuperamos los resultados
			// Criteria criteriaResults =
			// session.createCriteria(ScrTypeadm.class);
			Criteria criteriaResults = session.createCriteria(EntityByLanguage
					.getScrTypeAdmLanguage(locale.getLanguage()));
			criteriaResults.addOrder(Order.asc("description"));
			criteriaResults.setFirstResult(firstRow);
			criteriaResults.setMaxResults(maxResults);
			criteriaResults.add(Expression.not(Expression.eq("id", new Integer(
					0))));
			if (where != null && where.length() > 0) {
				if (where.startsWith("LIKE")) {
					criteriaResults.add(Expression.like("description",
							getWhereLike(where)));
				} else if (where.startsWith("=")) {
					criteriaResults.add(Expression.eq("description",
							getWhereAnd(where)));
				}
			}
			result.setResults(criteriaResults.list());

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to load ScrTypeadm for session [" + sessionID
					+ "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_VALIDATIONLISTFIELDS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	/**
	 * Metodo que obtiene los tipos de asunto
	 * @param sessionID
	 * @param bookID
	 * @param firstRow
	 * @param maxResults
	 * @param where
	 * @param enabled
	 * @param locale
	 * @param entidad
	 * @return {@link ValidationResults}
	 * @throws BookException
	 * @throws AttributesException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static ValidationResults getScrCa(String sessionID, Integer bookID,
			int firstRow, int maxResults, String where, int enabled,
			Locale locale, String entidad) throws BookException,
			AttributesException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		ValidationResults result = new ValidationResults();
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
			ScrOfic scrOfic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

			//obtenemos el listado de tipos de asunto
			List scrCaList = getScrCaCriteriaList(session, bookID, false,
					firstRow, where, enabled, locale, entidad, true);

			//obtenemos el listado de los tipos de asuntos filtrados por oficina
			List listScrCaByOfic = getScrCaOficList(session, scrCaList,
					scrOfic.getId());

			List listResult = new ArrayList();
			//comprobamos que la lista tenga valores
			if (listScrCaByOfic.size()>0){
				//obtenemos los indices para obtener una sublist
				int lastRow = firstRow + maxResults;
				if(lastRow > listScrCaByOfic.size()){
					lastRow = listScrCaByOfic.size();
				}
				//obtenemos una lista segun los indices indicados
				listResult = listScrCaByOfic.subList(firstRow, lastRow);
			}

			HibernateUtil.commitTransaction(tran);

			result.setResults(listResult);
			result.setTotalSize(listScrCaByOfic.size());

			return result;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to load ScrCa for session [" + sessionID
					+ "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_VALIDATIONLISTFIELDS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static ValidationResults getInitialValidationTreeResults(
			String sessionID, Integer bookID, int firstRow, int maxResults,
			String where, int enabled, int fldid, Locale locale, String entidad)
			throws AttributesException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		if (Repository.getInstance(entidad).isInBook(bookID).booleanValue()
				&& fldid == 7) {
			if (where != null && !where.equals("")) {
				return getScrOrgsForAdminUnits(sessionID, firstRow, maxResults,
						where, enabled, locale.getLanguage(), entidad);
			} else {
				return getScrTypeAdm(sessionID, firstRow, maxResults, where,
						locale, entidad);
			}
		} else if (Repository.getInstance(entidad).isOutBook(bookID)
				.booleanValue()
				&& fldid == 8) {
			if (where != null && !where.equals("")) {
				return getScrOrgsForAdminUnits(sessionID, firstRow, maxResults,
						where, enabled, locale.getLanguage(), entidad);
			} else {
				return getScrTypeAdm(sessionID, firstRow, maxResults, where,
						locale, entidad);
			}
		} else {
			if (where != null && !where.equals("")) {
				return getScrOrgsForAdminUnits(sessionID, firstRow, maxResults,
						where, enabled, locale.getLanguage(), entidad);
			} else {
				return getScrOrgsForAdminUnitsWithType(sessionID, firstRow,
						maxResults, where, enabled, 1, locale, entidad);
			}
		}
	}


	public static ValidationResults getInitialValidationTreeResults(
			String sessionID, int firstRow, int maxResults,
			String where, int enabled, Locale locale, String entidad)
			throws AttributesException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);


			if (where != null && !where.equals("")) {
				return getScrOrgsForAdminUnits(sessionID, firstRow, maxResults,
						where, enabled, locale.getLanguage(), entidad);
			} else {
				return getScrOrgsForAdminUnitsWithType(sessionID, firstRow,
						maxResults, where, enabled, 1, locale, entidad);
			}

	}

	public static ValidationResults validateAndReturnUA(String sessionID,
			int firstRow, int maxResults, int maximumIncludedAllowed,
			String value, Locale locale, String entidad)
			throws AttributesException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_String_NotNull_LengthMayorZero(value,
				ValidationException.ATTRIBUTE_SESSION);

		ValidationResults result = new ValidationResults();
		Transaction tran = null;
		List privOrgs = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			Iuserusertype userusertype = (Iuserusertype) cacheBag
					.get(HIBERNATE_Iuserusertype);
			ScrOfic scrOfic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);
			if (userusertype.getType() != IDocKeys.IUSERUSERTYPE_USER_TYPE_ADMIN) {
				privOrgs = DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getPrivOrgs(scrOfic.getId().intValue(), entidad);
			}

			List listaOption0 = getScrOrgCriteriaList(session, value, firstRow,
					maxResults, null, 0);

			if (!listaOption0.isEmpty()) {
				ScrOrg scrOrg = (ScrOrg) listaOption0.get(0);
				if (getScrOrgFromList(session, scrOrg, privOrgs)) {
					result.setTotalSize(1);
					result.addResult(getValidationResultScrOrg(session, scrOrg,
							locale.getLanguage(), entidad));
				}
			} else {
				result.setTotalSize(getScrOrgSize(session, value, true));

				if (result.getTotalSize() > 0
						&& result.getTotalSize() <= maximumIncludedAllowed) {
					List scrOrgToRemove = getScrOrgsToRemove(session, value,
							firstRow, maxResults, privOrgs, 1);
					List listaScrOrgs = getScrOrgCriteriaList(session, value,
							firstRow, maxResults, scrOrgToRemove, 2);

					result = getValidadationResultsFromListScrOrgs(session,
							result, listaScrOrgs, scrOrgToRemove,
							scrOrgToRemove.size(), locale.getLanguage(),
							entidad);
				} else {
					result.setTotalSize(getScrOrgSize(session, value, false));

					if (result.getTotalSize() > 0
							&& result.getTotalSize() <= maximumIncludedAllowed) {
						List scrOrgToRemove = getScrOrgsToRemove(session,
								value, firstRow, maxResults, privOrgs, 3);
						List listaScrOrgs = getScrOrgCriteriaList(session,
								value, firstRow, maxResults, scrOrgToRemove, 4);

						result = getValidadationResultsFromListScrOrgs(session,
								result, listaScrOrgs, scrOrgToRemove, privOrgs
										.size(), locale.getLanguage(), entidad);
					}
				}
			}

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error(
					"Impossible to load ScrOrgs for validateAndReturnUA for session ["
							+ sessionID + "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_VALIDATIONLISTFIELDS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

}