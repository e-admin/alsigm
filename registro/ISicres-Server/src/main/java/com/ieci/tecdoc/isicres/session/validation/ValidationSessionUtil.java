package com.ieci.tecdoc.isicres.session.validation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Session;
import net.sf.hibernate.expression.Expression;
import net.sf.hibernate.expression.Order;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesicres.ScrCa;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrOrg;
import com.ieci.tecdoc.common.invesicres.ScrTmzofic;
import com.ieci.tecdoc.common.isicres.ValidationResultScrOrg;
import com.ieci.tecdoc.common.isicres.ValidationResults;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.EntityByLanguage;
import com.ieci.tecdoc.common.utils.Repository;
import com.ieci.tecdoc.common.utils.adapter.XMLPersons;
import com.ieci.tecdoc.isicres.person.PersonValidationFactory;

public class ValidationSessionUtil implements ServerKeys, HibernateKeys {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private static final Logger log = Logger
			.getLogger(ValidationSessionUtil.class);

	/***************************************************************************
	 * Public methods
	 **************************************************************************/

	public static ValidationResultScrOrg getValidationResultScrOrg(
			Session session, ScrOrg scrOrg, String language, String entidad)
			throws AttributesException, HibernateException {
		ValidationResultScrOrg result = new ValidationResultScrOrg();

		try {
			if (!language.equals("es")) {
				result.setScrOrgName(DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getDescriptionByLocale(scrOrg.getId(), false, false,
								language, EntityByLanguage.getTableName(7),
								entidad));
			} else {
				result.setScrOrgName(scrOrg.getName());
			}
		} catch (Exception e) {
			log.error("Impossible to load the unit description  id ["
					+ scrOrg.getId() + "] for the language [" + language + "]",
					e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_VALIDATIONFIELDS);
		}
		// result.setScrOrgName(scrOrg.getName());
		result.setScrOrgCode(scrOrg.getCode());
		result.setScrOrgId(scrOrg.getId());

		if (scrOrg.getIdFather() != null) {
			ScrOrg father = null;
			try {
				father = (ScrOrg) session.load(ScrOrg.class, scrOrg
						.getIdFather());
				// result.setScrOrgFatherName(father.getName());
				if (!language.equals("es")) {
					result.setScrOrgFatherName(DBEntityDAOFactory
							.getCurrentDBEntityDAO().getDescriptionByLocale(
									father.getId(), false, false, language,
									EntityByLanguage.getTableName(7), entidad));
				} else {
					result.setScrOrgFatherName(father.getName());
				}
			} catch (Exception e) {
			}
			if (father != null && father.getIdFather() != null) {
				Integer scrID = father.getIdFather();
				ScrOrg parent = null;
				try {
					do {
						parent = (ScrOrg) session.load(ScrOrg.class, scrID);
						if (parent.getIdFather() != null) {
							scrID = parent.getIdFather();
						}
					} while (parent.getIdFather() != null);
					result.setScrOrgParentAcron(parent.getAcron());
				} catch (Exception e) {
				}
			}
		}

		return result;
	}

	/***************************************************************************
	 * Protected methods
	 **************************************************************************/

	protected static String getWhereLike(String where) throws Exception {
		String sub = where.substring(where.indexOf("'") + 1, where
				.lastIndexOf("'"));
		sub = sub.substring(0, sub.length() - 1)
				+ DBEntityDAOFactory.getCurrentDBEntityDAO().getLikeCharacter();
		if (sub.startsWith("*")) {
			sub = DBEntityDAOFactory.getCurrentDBEntityDAO().getLikeCharacter()
					+ sub.substring(1, sub.length());
		}
		return sub;
	}

	protected static String getWhereAnd(String where) {
		String sub = where.substring(where.indexOf("'") + 1, where
				.lastIndexOf("'"));
		return sub;
	}

	protected static String getDirTemp(String sessionID, Integer personID,
			int maxResults, boolean equal, String entidad, int typeAddress)
			throws AttributesException, SessionException, ValidationException {
		String result = null;
		try {
			String xmlParamId = XMLPersons.createXMLParamIdInfo(personID
					.toString(), sessionID, entidad);
			if (equal) {
				if (typeAddress == 0) {
					result = PersonValidationFactory
							.getCurrentPersonValidation().getAddresses(
									xmlParamId);
				} else {
					result = PersonValidationFactory
							.getCurrentPersonValidation().getAddresses(
									xmlParamId, 1);
				}
			} else {
				result = PersonValidationFactory.getCurrentPersonValidation()
						.getInfo(xmlParamId);
			}
		} catch (SessionException sE) {
			throw sE;
		} catch (Exception e) {
			log.error("Impossible to find getDirInter [" + personID
					+ "] with equal [" + equal + "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_PERSON_ADDRESS);
		}
		return result;
	}

	protected static ValidationResults getValidationResults(Session session,
			Criteria criteriaResults, String queryByClass, String where,
			int enabled, int firstRow, int maxResults, String order)
			throws HibernateException, Exception {
		ValidationResults result = new ValidationResults();

		StringBuffer querySize = new StringBuffer();
		querySize.append(queryByClass);

		String field = null;
		String fieldWhere = null;

		if (where != null && where.length() > 0) {
			int pos = where.indexOf("LIKE");
			if (pos == -1) {
				pos = where.indexOf("=");
			}
			field = where.substring(0, pos).trim().toLowerCase();
			fieldWhere = where.substring(pos, where.length());
			if (enabled == 1) {
				querySize.append(" and scr." + field);
			} else {
				querySize.append(" where scr." + field);
			}
			if (fieldWhere.startsWith("LIKE")) {
				querySize.append(" like '");
				querySize.append(getWhereLike(fieldWhere));
				querySize.append("'");
			} else if (fieldWhere.startsWith("=")) {
				querySize.append(fieldWhere);
			}
		}
		result.setTotalSize(((Integer) session.iterate(querySize.toString())
				.next()).intValue());

		// Recuperamos los resultados
		criteriaResults.addOrder(Order.asc(order));
		criteriaResults.setFirstResult(firstRow);
		criteriaResults.setMaxResults(maxResults);

		if (where != null && where.length() > 0) {
			if (fieldWhere.startsWith("LIKE")) {
				criteriaResults.add(Expression.like(field,
						getWhereLike(fieldWhere)));
			} else if (fieldWhere.startsWith("=")) {
				criteriaResults.add(Expression.eq(field,
						getWhereAnd(fieldWhere)));
			}
		}
		result.setResults(criteriaResults.list());

		return result;
	}

	protected static ValidationResults getValidationResultsScrOrgForAdminUnits(
			Session session, int enabled, String where, int firstRow,
			int maxResults, boolean isForDistribution, boolean isFather,
			int typeOrFather, Integer scrOficId, int userType, String language,
			String entidad) throws HibernateException, SQLException, Exception {
		ValidationResults result = new ValidationResults();

		String[] params = getQueryParamsScrOgsForAdminUnits(enabled, where,
				isForDistribution, isFather, typeOrFather, userType, scrOficId,
				entidad);

		if (params != null && params.length == 3) {
			String query = params[0];
			String field = params[1];
			String fieldWhere = params[2];

			result.setTotalSize(((Integer) session.iterate(query.toString())
					.next()).intValue());

			// Recuperamos los resultados
			result.setResults(getScrOrgsForAdminUnits(session, firstRow,
					maxResults, enabled, where, field, fieldWhere,
					typeOrFather, isForDistribution, isFather, userType,
					scrOficId, language, entidad));
		}

		return result;
	}

	protected static ValidationResults getValidadationResultsFromListScrOrgs(
			Session session, ValidationResults result, List listaScrOrgs,
			List scrOrgToRemove, int size, String language, String entidad)
			throws HibernateException, AttributesException {
		if (scrOrgToRemove != null && !scrOrgToRemove.isEmpty()) {
			result.setTotalSize(result.getTotalSize() - size);
		}

		for (Iterator it = listaScrOrgs.iterator(); it.hasNext();) {
			result.addResult(getValidationResultScrOrg(session, (ScrOrg) it
					.next(), language, entidad));
		}

		return result;
	}

	/**
	 * Metodo que obtiene los tipos de asunto
	 * @param session
	 * @param bookID
	 * @param isResult
	 * @param firstRow
	 * @param where
	 * @param enabled
	 * @param locale
	 * @param entidad
	 * @param isEntityByLanguage
	 * @return Listado de tipos de asunto
	 * @throws HibernateException
	 * @throws Exception
	 */
	protected static List getScrCaCriteriaList(Session session, Integer bookID,
			boolean isResult, int firstRow, String where, int enabled,
			Locale locale, String entidad, boolean isEntityByLanguage)
			throws HibernateException, Exception {
		Criteria criteria = null;
		if (isEntityByLanguage) {
			criteria = session.createCriteria(EntityByLanguage
					.getScrCaLanguage(locale.getLanguage()));
		} else {
			criteria = session.createCriteria(ScrCa.class);
		}
		
		criteria.addOrder(Order.asc("matter"));

		if (bookID != null) {
			if (Repository.getInstance(entidad).isInBook(bookID).booleanValue()) {
				criteria.add(Expression.eq("forEreg", new Integer(1)));
			} else {
				criteria.add(Expression.eq("forSreg", new Integer(1)));
			}
		} else {
			criteria.add(Expression.eq("forEreg", new Integer(1)));
		}
		if (enabled == 1) {
			criteria.add(Expression.eq("enabled", new Integer(1)));
		}
		if (where != null && where.length() > 0) {
			int pos = where.indexOf("LIKE");
			if (pos == -1) {
				pos = where.indexOf("=");
			}
			String field = where.substring(0, pos).trim().toLowerCase();
			String fieldWhere = where.substring(pos, where.length());
			if (fieldWhere.startsWith("LIKE")) {
				criteria.add(Expression.like(field, getWhereLike(fieldWhere)));
			} else if (fieldWhere.startsWith("=")) {
				criteria.add(Expression.eq(field, getWhereAnd(fieldWhere)));
			}
		}

		return criteria.list();
	}

	protected static List getScrCaOficList(Session session,
			List scrCaListResult, ScrOfic scrOfic, int maxResults)
			throws HibernateException {
		// List list = new ArrayList();
		// int index = 0;
		// for (Iterator it = scrCaListResult.iterator(); it.hasNext()
		// && index < maxResults;) {
		// ScrCa scr = (ScrCa) it.next();
		// if (scr.getAllOfics().equals(new Integer(1))) {
		// list.add(index++, scr);
		// } else if (scr.getAllOfics().equals(new Integer(0))) {
		// StringBuffer subquery = new StringBuffer();
		// subquery.append("select count(*) from ");
		// subquery.append(HIBERNATE_ScrCaofic);
		// subquery.append(" as scr where scr.scrOfic.id=");
		// subquery.append(scrOficId);
		// subquery.append(" and scr.idMatter=");
		// subquery.append(scr.getId());
		// if (((Integer) session.iterate(subquery.toString()).next())
		// .intValue() > 0) {
		// list.add(index++, scr);
		// }
		// }
		// }
		List list = EntityByLanguage.getAllOficsScrCaList(session,
				HIBERNATE_ScrCaofic, scrOfic, scrCaListResult, maxResults);
		return list;
	}

	protected static List getScrOrgCriteriaList(Session session, String value,
			int firstRow, int maxResults, List scrOrgToRemove, int option)
			throws HibernateException, Exception {
		Criteria criteriaResults = session.createCriteria(ScrOrg.class);
		criteriaResults.add(Expression.eq("enabled", new Integer(1)));
		criteriaResults.add(Expression.not(Expression.eq("scrTypeadm.id",
				new Integer(0))));

		switch (option) {
		case 0:
			criteriaResults.add(Expression.or(Expression.eq("code", value),
					Expression.eq("acron", value)));
			break;

		case 1:
			criteriaResults.addOrder(Order.asc("name"));
			criteriaResults.add(Expression.like("code", value
					+ DBEntityDAOFactory.getCurrentDBEntityDAO()
							.getLikeCharacter()));
			break;

		case 2:
			criteriaResults.addOrder(Order.asc("name"));
			criteriaResults.setFirstResult(firstRow);
			criteriaResults.setMaxResults(maxResults);
			criteriaResults.add(Expression.like("code", value
					+ DBEntityDAOFactory.getCurrentDBEntityDAO()
							.getLikeCharacter()));
			if (scrOrgToRemove != null && !scrOrgToRemove.isEmpty()) {
				criteriaResults.add(Expression.not(Expression.in("id",
						scrOrgToRemove.toArray())));
			}
			break;

		case 3:
			criteriaResults.addOrder(Order.asc("name"));
			criteriaResults.add(Expression.like("acron", value
					+ DBEntityDAOFactory.getCurrentDBEntityDAO()
							.getLikeCharacter()));
			break;

		case 4:
			criteriaResults.addOrder(Order.asc("name"));
			criteriaResults.setFirstResult(firstRow);
			criteriaResults.setMaxResults(maxResults);
			criteriaResults.add(Expression.like("acron", value
					+ DBEntityDAOFactory.getCurrentDBEntityDAO()
							.getLikeCharacter()));
			if (scrOrgToRemove != null && !scrOrgToRemove.isEmpty()) {
				criteriaResults.add(Expression.not(Expression.in("id",
						scrOrgToRemove.toArray())));
			}

			break;
		default:
			break;
		}

		return criteriaResults.list();
	}

	protected static List getScrOrgsToRemove(Session session, String value,
			int firstRow, int maxResults, List privOrgs, int option)
			throws HibernateException, Exception {
		List listaScrOrgs = getScrOrgCriteriaList(session, value, firstRow,
				maxResults, null, option);

		List scrOrgToRemove = new ArrayList();
		if (privOrgs != null && !privOrgs.isEmpty()) {
			for (Iterator it = listaScrOrgs.iterator(); it.hasNext();) {
				ScrOrg scrOrg = (ScrOrg) it.next();
				if (!getScrOrgFromList(session, scrOrg, privOrgs)) {
					scrOrgToRemove.add(scrOrg.getId());
				}
			}
		}

		return scrOrgToRemove;
	}

	protected static int getScrOrgSize(Session session, String value,
			boolean isCode) throws HibernateException, Exception {
		StringBuffer querySize = new StringBuffer();

		querySize.append("select count(*) from ");
		querySize.append(HIBERNATE_ScrOrg);
		querySize
				.append(" as scr where scr.scrTypeadm.id != 0 and scr.enabled=1 and ");
		if (isCode) {
			querySize.append("code ");
		} else {
			querySize.append("acron ");
		}
		querySize.append("like '");
		querySize.append(value);
		querySize.append(DBEntityDAOFactory.getCurrentDBEntityDAO()
				.getLikeCharacter());
		querySize.append("'");

		return ((Integer) session.iterate(querySize.toString()).next())
				.intValue();
	}

	/**
	 * Metodo que obtiene los tipos de asunto para una oficina
	 * @param session
	 * @param scrCaList - Listado de tipos de asunto
	 * @param scrOficId - id oficina
	 * @return Listado de objetos tipo {@link ScrCa}
	 * @throws HibernateException
	 */
	protected static List getScrCaOficList(Session session, List scrCaList,
			Integer scrOficId) throws HibernateException {
		List result = new ArrayList();
		ScrCa scrCa = null;
		for (Iterator it = scrCaList.iterator(); it.hasNext();) {
			scrCa = new ScrCa();
			//copiamos las propiedades que son igual del objeto iterado
			//a un objeto tipo scrCa
			BeanUtils.copyProperties(it.next(), scrCa);
			
			if (scrCa.getAllOfics().equals(new Integer(1))) {
				result.add(scrCa);
			} else if (scrCa.getAllOfics().equals(new Integer(0))) {
				StringBuffer subquery = new StringBuffer();
				// subquery.append("select count(*) from ");
				// subquery.append(HIBERNATE_ScrCaofic);
				// subquery.append(" as scr where scr.scrOfic.id=");
				// subquery.append(scrOficId);
				// subquery.append(" and scr.idMatter=");
				// subquery.append(scr.getId());
				subquery.append(EntityByLanguage.getScrCaOfic(
						HIBERNATE_ScrCaofic, scrOficId, scrCa.getId()));
				if (((Integer) session.iterate(subquery.toString()).next())
						.intValue() > 0) {
					result.add(scrCa);
				}
			}
		}

		return result;
	}

	protected static boolean getScrOrgFromList(Session session, ScrOrg scrOrg,
			List privOrgs) {
		boolean addScrOrg = true;
		if (privOrgs != null && !privOrgs.isEmpty()) {
			if (privOrgs.contains(scrOrg.getId())) {
				addScrOrg = false;
			} else {
				Integer father = scrOrg.getIdFather();
				while (father != null) {
					if (privOrgs.contains(father)) {
						addScrOrg = false;
						break;
					} else {
						try {
							father = ((ScrOrg) session.load(ScrOrg.class,
									new Integer(father.intValue())))
									.getIdFather();
						} catch (HibernateException e) {
							father = null;
						}
					}
				}
			}
		}

		return addScrOrg;
	}

	protected static ScrTmzofic getScrTmzofic(Session session, Integer oficId)
			throws HibernateException {
		ScrTmzofic scrTmzofic = null;
		try {
			scrTmzofic = (ScrTmzofic) session.load(ScrTmzofic.class, oficId);
		} catch (ObjectNotFoundException onF) {
			// Puede ser que una oficina no tenga cambios horarios
		}
		return scrTmzofic;
	}

	/***************************************************************************
	 * Private methods
	 **************************************************************************/

	private static String[] getQueryParamsScrOgsForAdminUnits(int enabled,
			String where, boolean isForDistribution, boolean isFather,
			int typeOrFather, int userType, Integer scrOficId, String entidad)
			throws SQLException, Exception {
		String field = null;
		String fieldWhere = null;

		// Calculamos el tamaño de los resultados
		StringBuffer querySize = new StringBuffer();
		querySize.append("select count(*) from ");
		querySize.append(HIBERNATE_ScrOrg);
		querySize.append(" as scr ");
		if (!isForDistribution) {
			if (isFather) {
				querySize.append(" where scr.idFather = ");
				querySize.append(typeOrFather);
			} else {
				querySize.append(" where scr.scrTypeadm.id = ");
				querySize.append(typeOrFather);
				querySize.append(" and scr.idFather is null ");
			}
			if (enabled == 1) {
				querySize.append(" and scr.enabled=1 ");
			}
		} 
		
		if (where != null && where.length() > 0) {
			int pos = where.indexOf("LIKE");
			if (pos == -1) {
				pos = where.indexOf("=");
			}

			field = where.substring(0, pos).trim().toLowerCase();
			fieldWhere = where.substring(pos, where.length());
			if (isForDistribution) {
				querySize.append(" where scr.scrTypeadm.id != 0 and scr."
						+ field);
			} else {
				querySize.append(" and scr." + field);
			}
			if (fieldWhere.startsWith("LIKE")) {
				querySize.append(" like '");
				querySize.append(getWhereLike(fieldWhere));
				querySize.append("'");
			} else if (fieldWhere.startsWith("=")) {
				querySize.append(fieldWhere);
			}
		} else if (isForDistribution) {
			querySize
					.append(" where scr.scrTypeadm.id=1 and scr.idFather is null");
			if (enabled == 1) {
				querySize.append(" and scr.enabled=1 ");
			}
		}

		if (!isForDistribution) {
			if (userType != IDocKeys.IUSERUSERTYPE_USER_TYPE_ADMIN) {
				List privOrgs = DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getPrivOrgs(scrOficId.intValue(), entidad);
				if (privOrgs != null && !privOrgs.isEmpty()) {
					for (Iterator it = privOrgs.iterator(); it.hasNext();) {
						Integer scrOrgsId = (Integer) it.next();
						querySize.append(" and scr.id !="
								+ scrOrgsId.intValue());
					}
				}
			}
		}

		String[] params = new String[] { querySize.toString(), field,
				fieldWhere };

		return params;

	}

	private static List getScrOrgsForAdminUnits(Session session, int firstRow,
			int maxResults, int enabled, String where, String field,
			String fieldWhere, int typeOrFather, boolean isForDistribution,
			boolean isFather, int userType, Integer scrOficId, String language,
			String entidad) throws HibernateException, SQLException, Exception {

		// Recuperamos los resultados
		// Criteria criteriaResults = session.createCriteria(ScrOrg.class);
		Criteria criteriaResults = session.createCriteria(EntityByLanguage
				.getScrOrgLanguage(language));
		criteriaResults.addOrder(Order.asc("name"));
		criteriaResults.setFirstResult(firstRow);
		criteriaResults.setMaxResults(maxResults);
		if (!isForDistribution) {
			if (isFather) {
				criteriaResults.add(Expression.eq("idFather", new Integer(
						typeOrFather)));
			} else {
				criteriaResults.add(Expression.eq("scrTypeadm.id", new Integer(
						typeOrFather)));
				criteriaResults.add(Expression.isNull("idFather"));
			}
		}
		if (enabled == 1) {
			criteriaResults.add(Expression.eq("enabled", new Integer(1)));
		}
		if (where != null && where.length() > 0) {
			if (isForDistribution) {
				criteriaResults.add(Expression.not(Expression.eq(
						"scrTypeadm.id", new Integer(0))));
			}
			if (fieldWhere.startsWith("LIKE")) {
				criteriaResults.add(Expression.like(field,
						getWhereLike(fieldWhere)));
			} else if (fieldWhere.startsWith("=")) {
				criteriaResults.add(Expression.eq(field,
						getWhereAnd(fieldWhere)));
			}
		} else if (isForDistribution) {
			criteriaResults.add(Expression.eq("scrTypeadm.id", new Integer(1)));
			criteriaResults.add(Expression.isNull("idFather"));
		}

		if (!isForDistribution) {
			if (userType != IDocKeys.IUSERUSERTYPE_USER_TYPE_ADMIN) {
				List privOrgs = DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getPrivOrgs(scrOficId.intValue(), entidad);
				if (privOrgs != null && !privOrgs.isEmpty()) {
					criteriaResults.add(Expression.not(Expression.in("id",
							privOrgs.toArray())));
				}
			}
		}
		return criteriaResults.list();
	}

}
