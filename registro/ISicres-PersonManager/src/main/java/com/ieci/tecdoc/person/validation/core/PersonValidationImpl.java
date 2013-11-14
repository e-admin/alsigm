package com.ieci.tecdoc.person.validation.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.JDBCException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.expression.Expression;
import net.sf.hibernate.expression.Order;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.adapter.PersonValidation;
import com.ieci.tecdoc.common.conf.InvesicresConf;
import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesicres.ScrAddress;
import com.ieci.tecdoc.common.invesicres.ScrAddrtel;
import com.ieci.tecdoc.common.invesicres.ScrCity;
import com.ieci.tecdoc.common.invesicres.ScrDom;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrPfi;
import com.ieci.tecdoc.common.invesicres.ScrPinfo;
import com.ieci.tecdoc.common.invesicres.ScrPjur;
import com.ieci.tecdoc.common.invesicres.ScrProv;
import com.ieci.tecdoc.common.invesicres.ScrTypeaddress;
import com.ieci.tecdoc.common.invesicres.ScrTypedoc;
import com.ieci.tecdoc.common.isicres.BuscInterResults;
import com.ieci.tecdoc.common.isicres.DirInterResults;
import com.ieci.tecdoc.common.isicres.PersonAddress;
import com.ieci.tecdoc.common.isicres.PersonAddressTel;
import com.ieci.tecdoc.common.isicres.PersonInfo;
import com.ieci.tecdoc.common.isicres.ProvCity;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.BBDDUtils;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.common.utils.ISicresQueries;
import com.ieci.tecdoc.common.utils.adapter.CriteriaAttributes;
import com.ieci.tecdoc.common.utils.adapter.PersonCriteria;
import com.ieci.tecdoc.common.utils.adapter.XMLPersons;
import com.ieci.tecdoc.idoc.utils.ConfiguratorInvesicres;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.Validator;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

/**
 *
 *
 */
public class PersonValidationImpl implements PersonValidation {
	private static final String BLANCO = " ";

	private static final Logger log = Logger
			.getLogger(PersonValidationImpl.class);

	public String search(String xmlSearchParameters)
			throws AttributesException, SessionException, ValidationException {

		Transaction tran = null;
		String xmlResult = null;
		BuscInterResults result = new BuscInterResults();
		StringBuffer wherePCriteriaPFis = new StringBuffer();
		StringBuffer wherePCriteriaPJur = new StringBuffer();
		String sessionId = null;
		String entidad = null;
		Integer personType = null;
		Integer inicio = null;
		Integer rango = null;
		String sqlPinfo = "id in (select id from scr_pinfo where officeid = ";

		try {
			CriteriaAttributes criteriaAttrs = XMLPersons
					.getAttributesCriteria(xmlSearchParameters);
			sessionId = criteriaAttrs.getSessionId();
			entidad = criteriaAttrs.getEntidadId();
			personType = Integer.valueOf(criteriaAttrs.getPersonType());
			inicio = Integer.valueOf(criteriaAttrs.getInicio());
			rango = Integer.valueOf(criteriaAttrs.getRango());

			List criteria = XMLPersons
					.getCriteriaFromXMLText(xmlSearchParameters);

			Validator.validate_String_NotNull_LengthMayorZero(sessionId,
					ValidationException.ATTRIBUTE_SESSION);

			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionId);
			ScrOfic scrOfic = (ScrOfic) cacheBag
					.get(HibernateKeys.HIBERNATE_ScrOfic);

			InvesicresConf invesicresConf = ConfiguratorInvesicres.getInstance(
					entidad).getInvesicresConf();

			List listPCriteriaPFis = new ArrayList();
			List listPCriteriaPJur = new ArrayList();
			PersonCriteria pCriteria = null;
			for (int i = 0; i < criteria.size(); i++) {
				pCriteria = (PersonCriteria) criteria.get(i);
				if (personType.intValue() == 1) {
					listPCriteriaPFis.add(pCriteria);
				} else {
					listPCriteriaPJur.add(pCriteria);
				}
			}
			Criteria criteriaResults = null;
			List aux = null;
			criteriaResults = session.createCriteria(ScrPfi.class);
			criteriaResults.addOrder(Order.asc("firstName"));
			criteriaResults.addOrder(Order.asc("secondName"));
			criteriaResults.addOrder(Order.asc("surname"));

			if (personType.intValue() == 1 && !listPCriteriaPFis.isEmpty()) {
				if (rango.intValue() > 0) {
					criteriaResults.setFirstResult(inicio.intValue());
					criteriaResults.setMaxResults(rango.intValue());
				}
				wherePCriteriaPFis.append(createWhereCriteriaPFisPJur(
						listPCriteriaPFis, false, entidad));
				if (invesicresConf.getPopulationPartition() == 1) {
					wherePCriteriaPFis.append(" and " + sqlPinfo);
					wherePCriteriaPFis.append(scrOfic.getId() + ")");
				}
				criteriaResults.add(Expression.sql(wherePCriteriaPFis
						.toString()));
				aux = criteriaResults.list();
				result.setSize(aux.size());
				for (Iterator it = aux.iterator(); it.hasNext();) {
					result.add((ScrPfi) it.next());
				}
			} else if (personType.intValue() == 1
					&& listPCriteriaPFis.isEmpty()) {
				if (rango.intValue() > 0) {
					criteriaResults.setFirstResult(inicio.intValue());
					criteriaResults.setMaxResults(rango.intValue());
				}
				if (invesicresConf.getPopulationPartition() == 1) {
					wherePCriteriaPFis.append(" where " + sqlPinfo);
					wherePCriteriaPFis.append(scrOfic.getId() + ")");
					criteriaResults.add(Expression.sql(wherePCriteriaPFis
							.toString()));
				}
				aux = criteriaResults.list();
				result.setSize(aux.size());
				for (Iterator it = aux.iterator(); it.hasNext();) {
					result.add((ScrPfi) it.next());
				}

			}
			criteriaResults = session.createCriteria(ScrPjur.class);
			criteriaResults.addOrder(Order.asc("name"));
			if (personType.intValue() == 2 && !listPCriteriaPJur.isEmpty()) {
				if (rango.intValue() > 0) {
					criteriaResults.setFirstResult(inicio.intValue());
					criteriaResults.setMaxResults(rango.intValue());
				}
				wherePCriteriaPJur.append(createWhereCriteriaPFisPJur(
						listPCriteriaPJur, false, entidad));
				if (invesicresConf.getPopulationPartition() == 1) {
					wherePCriteriaPJur.append(" and " + sqlPinfo);
					wherePCriteriaPJur.append(scrOfic.getId() + ")");
				}
				criteriaResults.add(Expression.sql(wherePCriteriaPJur
						.toString()));
				aux = criteriaResults.list();
				result.setSize(aux.size());
				for (Iterator it = aux.iterator(); it.hasNext();) {
					result.add((ScrPjur) it.next());
				}
			} else if (personType.intValue() == 2
					&& listPCriteriaPJur.isEmpty()) {
				if (rango.intValue() > 0) {
					criteriaResults.setFirstResult(inicio.intValue());
					criteriaResults.setMaxResults(rango.intValue());
				}
				if (invesicresConf.getPopulationPartition() == 1) {
					wherePCriteriaPJur.append(" and " + sqlPinfo);
					wherePCriteriaPJur.append(scrOfic.getId() + ")");
					criteriaResults.add(Expression.sql(wherePCriteriaPJur
							.toString()));
				}
				aux = criteriaResults.list();
				result.setSize(aux.size());
				for (Iterator it = aux.iterator(); it.hasNext();) {
					result.add((ScrPjur) it.next());
				}

			}
			xmlResult = createXMLPersonsInfo(result.getContent(),
					result.getSize(), criteriaAttrs);

			HibernateUtil.commitTransaction(tran);

			return xmlResult;
		} catch (ValidationException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to find inter", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_INTER);
		} finally {
			HibernateUtil.closeSession(entidad);
		}

	}

	// El parámetro de entrada xmlPersonFisSearchParameters
	// es un xml que contiene criterios de búsqueda de una persona física o
	// jurídica.
	// El resultado será el número de personas encontradas que cumplan el
	// criterio de busqueda.
	public Integer count(String xmlSearchParameters)
			throws AttributesException, SessionException, ValidationException {

		// Transaction tran = null;
		int auxResult = 0;
		String countPfis = "select count(*) from scr_pfis";
		String countPjur = "select count(*) from scr_pjur";
		StringBuffer wherePCriteriaPFis = new StringBuffer();
		StringBuffer wherePCriteriaPJur = new StringBuffer();
		String sessionId = null;
		String entidad = null;
		Integer personType = null;
		String sqlPinfo = "id in (select id from scr_pinfo where officeid = ";

		try {
			CriteriaAttributes criteriaAttrs = XMLPersons
					.getAttributesCriteria(xmlSearchParameters);
			sessionId = criteriaAttrs.getSessionId();
			entidad = criteriaAttrs.getEntidadId();
			personType = Integer.valueOf(criteriaAttrs.getPersonType());

			List criteria = XMLPersons
					.getCriteriaFromXMLText(xmlSearchParameters);

			Validator.validate_String_NotNull_LengthMayorZero(sessionId,
					ValidationException.ATTRIBUTE_SESSION);

			// Session session = HibernateUtil.currentSession(entidad);
			// tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionId);
			ScrOfic scrOfic = (ScrOfic) cacheBag
					.get(HibernateKeys.HIBERNATE_ScrOfic);

			InvesicresConf invesicresConf = ConfiguratorInvesicres.getInstance(
					entidad).getInvesicresConf();

			List listPCriteriaPFis = new ArrayList();
			List listPCriteriaPJur = new ArrayList();
			PersonCriteria pCriteria = null;
			for (int i = 0; i < criteria.size(); i++) {
				pCriteria = (PersonCriteria) criteria.get(i);
				if (personType.intValue() == 1) {
					listPCriteriaPFis.add(pCriteria);
				} else {
					listPCriteriaPJur.add(pCriteria);
				}
			}

			if (personType.intValue() == 1 && !listPCriteriaPFis.isEmpty()) {
				wherePCriteriaPFis.append(createWhereCriteriaPFisPJur(
						listPCriteriaPFis, true, entidad));
				if (invesicresConf.getPopulationPartition() == 1) {
					wherePCriteriaPFis.append(" and " + sqlPinfo);
					wherePCriteriaPFis.append(scrOfic.getId() + ")");
				}
				auxResult = DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getPersonListSize(countPfis + wherePCriteriaPFis,
								entidad);
			} else if (personType.intValue() == 1
					&& listPCriteriaPFis.isEmpty()) {
				if (invesicresConf.getPopulationPartition() == 1) {
					wherePCriteriaPFis.append(" where " + sqlPinfo);
					wherePCriteriaPFis.append(scrOfic.getId() + ")");
				}
				auxResult = DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getPersonListSize(countPfis + wherePCriteriaPFis,
								entidad);

			}
			if (personType.intValue() == 2 && !listPCriteriaPJur.isEmpty()) {
				wherePCriteriaPJur.append(createWhereCriteriaPFisPJur(
						listPCriteriaPJur, true, entidad));
				if (invesicresConf.getPopulationPartition() == 1) {
					wherePCriteriaPJur.append(" and " + sqlPinfo);
					wherePCriteriaPJur.append(scrOfic.getId() + ")");
				}
				auxResult = DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getPersonListSize(countPjur + wherePCriteriaPJur,
								entidad);
			} else if (personType.intValue() == 2
					&& listPCriteriaPJur.isEmpty()) {
				if (invesicresConf.getPopulationPartition() == 1) {
					wherePCriteriaPJur.append(" where " + sqlPinfo);
					wherePCriteriaPJur.append(scrOfic.getId() + ")");
				}
				auxResult = DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getPersonListSize(countPjur + wherePCriteriaPJur,
								entidad);

			}
			// HibernateUtil.commitTransaction(tran);

			return new Integer(auxResult);
		} catch (ValidationException sE) {
			// HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (SessionException sE) {
			// HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			// HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to find inter", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_INTER);
			// } finally {
			// HibernateUtil.closeSession(entidad);
		}

	}

	// El parámetro de entrada es el identificador de la persona como un String.
	// El resultado será un xml con las direcciones
	// de la persona física o jurídica.

	public String getAddresses(String xmlParamId) throws Exception {

		return getAddresses(xmlParamId, 0);
	}

	public String getAddresses(String xmlParamId, int typeAddress)
			throws Exception {

		Transaction tran = null;
		Map result = new HashMap();
		String personID = null;
		String entidad = null;
		int maxResults = 0;
		int size = 0;
		try {
			personID = (String) XMLPersons.getParamId(xmlParamId).get(0);
			entidad = (String) XMLPersons.getParamId(xmlParamId).get(2);
			Validator.validate_Integer(new Integer(personID),
					ValidationException.ATTRIBUTE_PERSON);

			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			maxResults = new Integer(Configurator.getInstance().getProperty(
					ConfigurationKeys.KEY_SERVER_MAXROWSFORVALIDATIONRULES))
					.intValue();

			StringBuffer querySize = new StringBuffer();
			querySize.append("select count(*) from ");
			querySize.append(HibernateKeys.HIBERNATE_ScrAddress);
			querySize.append(" as scr where scr.type=");
			querySize.append(typeAddress);
			querySize.append(" and scr.idPerson=");
			querySize.append(new Integer(personID));
			size = ((Integer) session.iterate(querySize.toString()).next())
					.intValue();
			if (size > 0 && size <= maxResults) {
				Criteria criteriaResults = session
						.createCriteria(ScrAddress.class);
				if (typeAddress == 0) {
					criteriaResults.add(Expression.eq("type", new Integer(0)));
				} else {
					criteriaResults.add(Expression.not(Expression.eq("type",
							new Integer(0))));
				}
				criteriaResults.add(Expression.eq("idPerson", new Integer(
						personID)));
				List aux = criteriaResults.list();
				ScrAddress address = null;
				if (typeAddress == 0) {
					for (Iterator it = aux.iterator(); it.hasNext();) {
						address = (ScrAddress) it.next();
						result.put(
								address,
								ISicresQueries.getScrDom(session,
										address.getId()));
					}
				} else {
					for (Iterator it = aux.iterator(); it.hasNext();) {
						address = (ScrAddress) it.next();
						result.put(address, ISicresQueries.getScrAddrtel(
								session, address.getId().intValue()));
					}
				}
			}

			HibernateUtil.commitTransaction(tran);

			return createXMLPersonsAddresses(result);
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to find getDirInter [" + personID
					+ "] with type [" + typeAddress + "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_PERSON_ADDRESS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	// El resultado será un xml con las provincias
	public String getProvicies(String xmlParamId) throws AttributesException,
			SessionException, ValidationException {

		Transaction tran = null;
		String result = null;
		String sessionID = null;
		String entidad = null;
		try {
			sessionID = (String) XMLPersons.getParamId(xmlParamId).get(1);
			entidad = (String) XMLPersons.getParamId(xmlParamId).get(2);
			Validator.validate_String_NotNull_LengthMayorZero(sessionID,
					ValidationException.ATTRIBUTE_SESSION);

			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheFactory.getCacheInterface().getCacheEntry(sessionID);

			result = createXMLProvCities(ISicresQueries.getScrProv(session),
					false);

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to find getScrProv", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_PROV);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	// El parámetro de entrada es el identificador de la provincia como un
	// String.
	// El resultado será un xml con las ciudades.
	public String getCities(String xmlParamId) throws AttributesException,
			ValidationException, SessionException {

		Transaction tran = null;
		String result = null;
		String provId = null;
		String sessionID = null;
		String entidad = null;
		try {
			provId = (String) XMLPersons.getParamId(xmlParamId).get(0);
			sessionID = (String) XMLPersons.getParamId(xmlParamId).get(1);
			entidad = (String) XMLPersons.getParamId(xmlParamId).get(2);
			Validator.validate_String_NotNull_LengthMayorZero(sessionID,
					ValidationException.ATTRIBUTE_SESSION);
			Validator.validate_Integer(new Integer(provId),
					ValidationException.ATTRIBUTE_PROV);

			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheFactory.getCacheInterface().getCacheEntry(sessionID);

			result = createXMLProvCities(
					ISicresQueries.getScrCities(session, new Integer(provId)),
					true);

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to find getScrCities [" + provId + "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_CITIES);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	// La creación de personas físicas y jurídicas se llevará a cabo
	// con un método cuyo parámetro de entrada serán un xml que contendrá
	// la información de las personas que se han de crear.
	// Se devolverá un String con el identificador de la persona creada.

	public String create(String xmlPersonInfo) throws BookException,
			SessionException, ValidationException {

		Date currentDate = null;
		Transaction tran = null;
		PersonInfo pInfo = null;
		String sessionId = null;
		String entidad = null;
		ScrPfi pfi = null;
		ScrPjur pjur = null;
		List doms = null;
		List dirTels = null;
		try {
			sessionId = XMLPersons.getSessionId(xmlPersonInfo);
			entidad = XMLPersons.getEntidadId(xmlPersonInfo);
			Validator.validate_String_NotNull_LengthMayorZero(sessionId,
					ValidationException.ATTRIBUTE_SESSION);

			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionId);

			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HibernateKeys.HIBERNATE_Iuseruserhdr);
			ScrOfic scrOfic = (ScrOfic) cacheBag
					.get(HibernateKeys.HIBERNATE_ScrOfic);

			currentDate = BBDDUtils.getDateFromTimestamp(DBEntityDAOFactory
					.getCurrentDBEntityDAO().getDBServerDate(entidad));

			int personId = DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getContador4PERSONS(user.getId(), entidad);
			pInfo = XMLPersons.getPersonInfoFromXMLText(xmlPersonInfo);
			doms = pInfo.getAddresses();
			dirTels = pInfo.getAddressesTel();
			if (new Integer(pInfo.getType()).intValue() == 1) {
				pfi = new ScrPfi();
				pfi.setId(new Integer(personId));
				ScrTypedoc typeDocfi = new ScrTypedoc();
				typeDocfi.setId(new Integer(pInfo.getTypeDoc()));

				ScrTypedoc typeDoc = (ScrTypedoc) session.load(
						ScrTypedoc.class, typeDocfi.getId());
				pfi.setScrTypedoc(typeDoc);
				pfi.setNif(pInfo.getNif());
				pfi.setFirstName(pInfo.getFirstName());
				pfi.setSecondName(pInfo.getSecondName());
				pfi.setSurname(pInfo.getName());

				session.save(pfi);
			} else {
				pjur = new ScrPjur();
				pjur.setId(new Integer(personId));
				ScrTypedoc typeDocjur = new ScrTypedoc();
				typeDocjur.setId(new Integer(pInfo.getTypeDoc()));
				pjur.setScrTypedoc(typeDocjur);
				pjur.setCif(pInfo.getNif());
				pjur.setName(pInfo.getName());
				session.save(pjur);

			}
			ScrPinfo scrPinfo = new ScrPinfo();
			scrPinfo.setId(new Integer(personId));
			scrPinfo.setOptype(0);
			scrPinfo.setOfficeid(scrOfic.getId().intValue());
			scrPinfo.setUsername(user.getName());
			scrPinfo.setOpdate(currentDate);
			session.save(scrPinfo);

			ScrAddress scrAddress = null;
			ScrDom scrDom = null;
			ScrAddrtel scrAddrtel = null;
			PersonAddress pAddress = null;
			PersonAddressTel pAddressTel = null;
			for (Iterator it = doms.iterator(); it.hasNext();) {
				pAddress = (PersonAddress) it.next();
				scrDom = new ScrDom();
				scrDom.setZip(pAddress.getZip());
				scrDom.setPreference(Integer.valueOf(pAddress.getPreference())
						.intValue());
				scrDom.setId(Integer.valueOf(pAddress.getId()));
				scrDom.setCountry(pAddress.getProvince());
				scrDom.setCity(pAddress.getCity());
				scrDom.setAddress(pAddress.getDom());
				if (scrDom.getId().intValue() == 0) {
					if (pAddress.getToDelete().equals("0")) {
						scrAddress = new ScrAddress();
						scrAddress
								.setId(new Integer(DBEntityDAOFactory
										.getCurrentDBEntityDAO()
										.getContador4SCRADDRESS(user.getId(),
												entidad)));
						scrAddress.setIdPerson(personId);
						scrAddress.setType(0);
						session.save(scrAddress);

						scrDom.setId(scrAddress.getId());
						session.save(scrDom);
					}
				} else if (scrDom.getId().equals("1")) {
					session.delete(scrDom);
				}
			}
			for (Iterator it = dirTels.iterator(); it.hasNext();) {
				pAddressTel = (PersonAddressTel) it.next();
				scrAddrtel = new ScrAddrtel();
				scrAddrtel.setPreference(Integer.valueOf(
						pAddressTel.getPreference()).intValue());
				scrAddrtel.setId(Integer.valueOf(pAddressTel.getId())
						.intValue());

				ScrTypeaddress scrTypeaddress = new ScrTypeaddress();
				scrTypeaddress.setId(new Integer(pAddressTel.getType()));
				ScrTypeaddress typeAddress = (ScrTypeaddress) session.load(
						ScrTypeaddress.class, scrTypeaddress.getId());
				scrAddrtel.setScrTypeaddress(typeAddress);

				scrAddrtel.setAddress(pAddressTel.getDirTel());
				if (scrAddrtel.getId() == 0) {
					if (pAddressTel.getToDelete().equals("0")) {
						scrAddress = new ScrAddress();
						scrAddress
								.setId(new Integer(DBEntityDAOFactory
										.getCurrentDBEntityDAO()
										.getContador4SCRADDRESS(user.getId(),
												entidad)));
						scrAddress.setIdPerson(personId);
						scrAddress.setType(1);
						session.save(scrAddress);

						scrAddrtel.setId(scrAddress.getId().intValue());
						session.save(scrAddrtel);
					}
				} else if (pAddressTel.getToDelete().equals("1")) {
					session.delete(scrAddrtel);
				}
			}
			HibernateUtil.commitTransaction(tran);

			return Integer.toString(personId);
		} catch (ValidationException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to add a person for the session ["
					+ sessionId + "]", e);
			if (new Integer(pInfo.getType()).intValue() == 1) {
				throw new BookException(BookException.ERROR_CANNOT_ADD_PFIS);
			} else {
				throw new BookException(BookException.ERROR_CANNOT_ADD_PJUR);
			}
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	// La modificación de personas físicas y jurídicas se llevará a cabo
	// con un método cuyos parámetro de entrada serán un xml
	// que contendrá la información de las personas que se han de modificar.
	// Se devolverá un String con el identificador de la persona modificada.

	public String update(String xmlPersonInfo) throws Exception {

		Transaction tran = null;
		PersonInfo pInfo = null;
		String sessionId = null;
		String entidad = null;
		ScrPfi pfi = null;
		ScrPjur pjur = null;
		List doms = null;
		List dirTels = null;
		try {
			sessionId = XMLPersons.getSessionId(xmlPersonInfo);
			entidad = XMLPersons.getEntidadId(xmlPersonInfo);
			Validator.validate_String_NotNull_LengthMayorZero(sessionId,
					ValidationException.ATTRIBUTE_SESSION);

			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionId);

			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HibernateKeys.HIBERNATE_Iuseruserhdr);

			pInfo = XMLPersons.getPersonInfoFromXMLText(xmlPersonInfo);
			doms = pInfo.getAddresses();
			dirTels = pInfo.getAddressesTel();
			String personId = pInfo.getId();
			if (new Integer(pInfo.getType()).intValue() == 1) {
				pfi = new ScrPfi();
				pfi.setId(new Integer(personId));
				ScrTypedoc typeDocfi = new ScrTypedoc();
				typeDocfi.setId(new Integer(pInfo.getTypeDoc()));

				ScrTypedoc typeDoc = (ScrTypedoc) session.load(
						ScrTypedoc.class, typeDocfi.getId());
				pfi.setScrTypedoc(typeDoc);
				pfi.setNif(pInfo.getNif());
				pfi.setFirstName(pInfo.getFirstName());
				pfi.setSecondName(pInfo.getSecondName());
				pfi.setSurname(pInfo.getName());
				session.update(pfi);
			} else {
				pjur = new ScrPjur();
				pjur.setId(new Integer(personId));
				ScrTypedoc typeDocjur = new ScrTypedoc();
				typeDocjur.setId(new Integer(pInfo.getTypeDoc()));
				pjur.setScrTypedoc(typeDocjur);
				pjur.setCif(pInfo.getNif());
				pjur.setName(pInfo.getName());
				session.update(pjur);

			}

			ScrAddress scrAddress = null;
			ScrDom scrDom = null;
			ScrAddrtel scrAddrtel = null;
			PersonAddress pAddress = null;
			PersonAddressTel pAddressTel = null;
			for (Iterator it = dirTels.iterator(); it.hasNext();) {
				pAddressTel = (PersonAddressTel) it.next();
				scrAddrtel = new ScrAddrtel();
				scrAddrtel.setPreference(Integer.valueOf(
						pAddressTel.getPreference()).intValue());
				scrAddrtel.setId(Integer.valueOf(pAddressTel.getId())
						.intValue());

				ScrTypeaddress scrTypeaddress = new ScrTypeaddress();
				scrTypeaddress.setId(new Integer(pAddressTel.getType()));
				ScrTypeaddress typeAddress = (ScrTypeaddress) session.load(
						ScrTypeaddress.class, scrTypeaddress.getId());
				scrAddrtel.setScrTypeaddress(typeAddress);

				scrAddrtel.setAddress(pAddressTel.getDirTel());
				if (scrAddrtel.getId() == 0) {
					if (pAddressTel.getToDelete().equals("0")) {
						scrAddress = new ScrAddress();
						scrAddress
								.setId(new Integer(DBEntityDAOFactory
										.getCurrentDBEntityDAO()
										.getContador4SCRADDRESS(user.getId(),
												entidad)));
						scrAddress.setIdPerson(Integer.valueOf(personId)
								.intValue());
						scrAddress.setType(1);
						session.save(scrAddress);

						scrAddrtel.setId(scrAddress.getId().intValue());
						session.save(scrAddrtel);
					}
				} else {
					if (pAddressTel.getToDelete().equals("0")) {
						session.update(scrAddrtel);
					} else {
						scrAddress = new ScrAddress();
						scrAddress.setId(new Integer(scrAddrtel.getId()));
						scrAddress.setIdPerson(Integer.valueOf(personId)
								.intValue());
						scrAddress.setType(1);
						session.delete(scrAddress);
						session.delete(scrAddrtel);
					}
				}
			}

			for (Iterator it = doms.iterator(); it.hasNext();) {
				pAddress = (PersonAddress) it.next();
				scrDom = new ScrDom();
				scrDom.setZip(pAddress.getZip());
				scrDom.setPreference(Integer.valueOf(pAddress.getPreference())
						.intValue());
				scrDom.setId(Integer.valueOf(pAddress.getId()));
				scrDom.setCountry(pAddress.getProvince());
				scrDom.setCity(pAddress.getCity());
				scrDom.setAddress(pAddress.getDom());
				if (scrDom.getId().intValue() == 0) {
					if (pAddress.getToDelete().equals("0")) {
						scrAddress = new ScrAddress();
						scrAddress
								.setId(new Integer(DBEntityDAOFactory
										.getCurrentDBEntityDAO()
										.getContador4SCRADDRESS(user.getId(),
												entidad)));
						scrAddress.setIdPerson(Integer.valueOf(personId)
								.intValue());
						scrAddress.setType(0);
						session.save(scrAddress);

						scrDom.setId(scrAddress.getId());
						session.save(scrDom);
					}
				} else {
					if (pAddress.getToDelete().equals("0")) {
						session.update(scrDom);
					} else {
						scrAddress = new ScrAddress();
						scrAddress.setId(scrDom.getId());
						scrAddress.setIdPerson(Integer.valueOf(personId)
								.intValue());
						scrAddress.setType(0);
						session.delete(scrAddress);
						session.delete(scrDom);
					}
				}
			}

			HibernateUtil.commitTransaction(tran);

			return personId;
		} catch (ValidationException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (JDBCException e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to update a person for the session ["
					+ sessionId + "]", e);
			throw new BookException(BookException.ERROR_CANNOT_DELETE_DIRECTION);
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to update a person for the session ["
					+ sessionId + "]", e);
			if (new Integer(pInfo.getType()).intValue() == 1) {
				throw new BookException(BookException.ERROR_CANNOT_UPDATE_PFIS);
			} else {
				throw new BookException(BookException.ERROR_CANNOT_UPDATE_PJUR);
			}
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	// Con esta operación se pretende recuperar la información completa
	// correspondiente a una sola persona física o jurídica.
	// Se llevará a cabo con un método cuyo parámetro de entrada serán
	// el identificador de la persona como un.
	// Se devolverá un xml con información de la persona ( nombre , apellidos
	// Nif, Cif, direcciones, tipo, etc.).

	public String getInfo(String xmlParamId) throws Exception {

		Transaction tran = null;
		boolean equal = true;
		DirInterResults result = new DirInterResults();
		String personId = null;
		String sessionID = null;
		String entidad = null;
		int maxResults = 0;
		try {
			personId = (String) XMLPersons.getParamId(xmlParamId).get(0);
			sessionID = (String) XMLPersons.getParamId(xmlParamId).get(1);
			entidad = (String) XMLPersons.getParamId(xmlParamId).get(2);
			Validator.validate_String_NotNull_LengthMayorZero(sessionID,
					ValidationException.ATTRIBUTE_SESSION);
			Validator.validate_Integer(new Integer(personId),
					ValidationException.ATTRIBUTE_PERSON);

			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			ScrOfic scrOfic = (ScrOfic) cacheBag
					.get(HibernateKeys.HIBERNATE_ScrOfic);

			InvesicresConf invesicresConf = ConfiguratorInvesicres.getInstance(
					entidad).getInvesicresConf();
			maxResults = new Integer(Configurator.getInstance().getProperty(
					ConfigurationKeys.KEY_SERVER_MAXROWSFORVALIDATIONRULES))
					.intValue();

			// //HA DE QUITARSE DESPUES DE PRUEBAS////
			// invesicresConf.setPopulationPartition(1);
			// ////////////////////////////////////////
			if (invesicresConf.getPopulationPartition() == 1) {
				try {
					ScrPinfo scrPinfo = (ScrPinfo) session.load(ScrPinfo.class,
							new Integer(personId));
					if (scrPinfo.getOfficeid() != scrOfic.getId().intValue()) {
						result.setPermModifPersonInfo(false);
					}
				} catch (HibernateException e) {
				}
			}
			if (result.isPermModifPersonInfo()) {
				try {
					ScrPfi scr = (ScrPfi) session.load(ScrPfi.class,
							new Integer(personId));
					result.setPfi(scr);
				} catch (HibernateException e) {
					try {
						ScrPjur scr = (ScrPjur) session.load(ScrPjur.class,
								new Integer(personId));
						result.setPjur(scr);
					} catch (HibernateException e1) {
					}
				}

				if (result.getPfi() != null || result.getPjur() != null) {
					StringBuffer querySize = new StringBuffer();
					querySize.append("select count(*) from ");
					querySize.append(HibernateKeys.HIBERNATE_ScrAddress);
					querySize.append(" as scr where scr.idPerson=");
					querySize.append(new Integer(personId));
					result.setAddressSize(((Integer) session.iterate(
							querySize.toString()).next()).intValue());

					if (result.getAddressSize() > 0
							&& result.getAddressSize() <= maxResults) {
						Criteria criteriaResults = session
								.createCriteria(ScrAddress.class);

						criteriaResults.add(Expression.eq("idPerson",
								new Integer(personId)));
						List aux = criteriaResults.list();
						ScrAddress address = null;
						for (Iterator it = aux.iterator(); it.hasNext();) {
							address = (ScrAddress) it.next();
							if (address.getType() == 0) {
								result.addScrAdress(address, ISicresQueries
										.getScrDom(session, address.getId()));
							} else {
								result.addScrAdress(address, ISicresQueries
										.getScrAddrtel(session, address.getId()
												.intValue()));
							}
						}
					}
				}
			}

			HibernateUtil.commitTransaction(tran);

			return createXMLRecoverPersonInfo(result);
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to find getDirInter [" + personId
					+ "] with equal [" + equal + "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_PERSON_ADDRESS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	/*
	 * El parámetro de entrada es el tipo de persona de la que se quieren
	 * obtener los tipos de documentos. Si el tipo de documento es 0 o vacio
	 * recuperamos la lista entera de documentos
	 *
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ieci.tecdoc.common.adapter.PersonValidation#getDocsType(java.lang
	 * .String)
	 */
	public String getDocsType(String xmlParamId) throws Exception {
		Transaction tran = null;
		String result = null;
		String personTypeString = null;
		String sessionID = null;
		String entidad = null;

		Integer personType = null;
		try {
			personTypeString = (String) XMLPersons.getParamId(xmlParamId)
					.get(0);
			sessionID = (String) XMLPersons.getParamId(xmlParamId).get(1);
			entidad = (String) XMLPersons.getParamId(xmlParamId).get(2);

			Validator.validate_String_NotNull_LengthMayorZero(sessionID,
					ValidationException.ATTRIBUTE_SESSION);

			if ((personTypeString != null) && (personTypeString.length() > 0)) {
				Validator.validate_Integer(new Integer(personTypeString),
						ValidationException.ATTRIBUTE_PROV);

				personType = new Integer(personTypeString);
			} else {
				personType = new Integer(0);
			}

			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheFactory.getCacheInterface().getCacheEntry(sessionID);

			List listaDocsType = new ArrayList();
			if (personType.intValue() == 0) {
				listaDocsType = ISicresQueries.getTypeDocs(session);
			} else {
				listaDocsType = ISicresQueries.getTypeDocs(session, personType);
			}

			result = XMLPersons.createXMLTypeDocs(listaDocsType);

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to find getTypeDocs [" + personTypeString
					+ "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_TYPE_DOCS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public String getAddressesType(String xmlParamId) throws Exception {
		Transaction tran = null;
		String result = null;
		String sessionID = null;
		String entidad = null;

		try {
			sessionID = (String) XMLPersons.getParamId(xmlParamId).get(1);
			entidad = (String) XMLPersons.getParamId(xmlParamId).get(2);

			Validator.validate_String_NotNull_LengthMayorZero(sessionID,
					ValidationException.ATTRIBUTE_SESSION);

			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheFactory.getCacheInterface().getCacheEntry(sessionID);

			List listaAddressesType = new ArrayList();
			listaAddressesType = ISicresQueries.getTypeAddresses(session);

			result = XMLPersons.createXMLTypeAddresses(listaAddressesType);

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to find getTypeAddresses", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_TYPE_ADDRESSES);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	private static String createXMLProvCities(List provinces, boolean type)
			throws Exception {

		ScrCity city = null;
		ScrProv prov = null;
		Object object = null;
		ProvCity pC = null;
		List provCity = new ArrayList();
		for (Iterator it = provinces.iterator(); it.hasNext();) {
			object = it.next();
			pC = new ProvCity();
			if (object instanceof ScrCity) {
				city = (ScrCity) object;
				pC.setId(city.getId().toString());
				pC.setCode(city.getCode());
				pC.setName(city.getName());
				pC.setProvId(new Integer(city.getIdProv()).toString());
			} else {
				prov = (ScrProv) object;
				pC.setId(prov.getId().toString());
				pC.setCode(prov.getCode());
				pC.setName(prov.getName());
			}
			provCity.add(pC);
		}
		return XMLPersons.createXMLProvCities(provCity, type);

	}

	/**
	 * Se procesan las personas Fisicas/Juridicas encontradas y retorna un XML
	 * con los datos.
	 *
	 * @param personData
	 *            Lista de Personas encontradas
	 * @param total
	 *            Total de personas encontradas
	 * @param criteriaAttrs
	 * @return Un XML con los datos de las Personas encontradas
	 * @throws Exception
	 */
	private static String createXMLPersonsInfo(List personData, int total,
			CriteriaAttributes criteriaAttrs) throws Exception {
		Object object = null;
		ScrPfi pfi = null;
		ScrPjur pjur = null;
		String id = null;
		String firstName = null;
		String secondName = null;
		String personName = null;
		String personType = null;
		String numDoc = null;
		String typeDoc = null;
		PersonInfo pInfo = null;
		List pData = new ArrayList();
		for (Iterator it = personData.iterator(); it.hasNext();) {
			object = it.next();

			firstName = "";
			secondName = " ";
			personName = " ";
			typeDoc = " ";
			numDoc = " ";

			pInfo = new PersonInfo();
			if (object instanceof ScrPfi) {
				personType = "1"; // Persona Fisica
				pfi = ((ScrPfi) object);
				id = pfi.getId().toString();
				if (pfi.getNif() != null) {
					numDoc = pfi.getNif();
				}
				personName = parseValues(pfi.getSurname()); // Nombre
				firstName = parseValues(pfi.getFirstName()); // Primer Apellido
				secondName = parseValues(pfi.getSecondName()); // Segundo
																// Apellido
				if (pfi.getScrTypedoc() != null) {
					typeDoc = pfi.getScrTypedoc().getId().toString();
				}
			} else {
				personType = "2"; // Persona Juridica
				pjur = ((ScrPjur) object);
				id = pjur.getId().toString();
				if (pjur.getCif() != null) {
					numDoc = pjur.getCif();
				}
				personName = parseValues(pjur.getName());
				if (pjur.getScrTypedoc() != null) {
					typeDoc = pjur.getScrTypedoc().getId().toString();
				}
			}

			pInfo.setId(id);
			pInfo.setType(personType);
			pInfo.setNif(numDoc);
			pInfo.setName(personName);
			pInfo.setFirstName(firstName);
			pInfo.setSecondName(secondName);
			pInfo.setTypeDoc(typeDoc);
			pData.add(pInfo);
		}

		return XMLPersons.createXMLPersonsInfo(pData, total, criteriaAttrs);

	}

	private static String createXMLRecoverPersonInfo(DirInterResults result)
			throws Exception {
		String perInfo = null;
		ScrPfi pfi = result.getPfi();
		ScrPjur pjur = result.getPjur();
		String personType = null;
		PersonInfo pInfo = new PersonInfo();
		if (result.isPermModifPersonInfo()) {
			if (pfi != null) {
				personType = "1";
				pInfo.setId(pfi.getId().toString());
				pInfo.setType(personType);
				if (null != pfi.getScrTypedoc()) {
					pInfo.setTypeDoc(pfi.getScrTypedoc().getId().toString());
				}
				pInfo.setNif(pfi.getNif());
				pInfo.setFirstName(parseValues(pfi.getFirstName()));
				pInfo.setSecondName(parseValues(pfi.getSecondName()));
				pInfo.setName(parseValues(pfi.getSurname()));
			} else {
				personType = "2";
				pInfo.setId(pjur.getId().toString());
				pInfo.setType(personType);
				if (null != pInfo.getTypeDoc()) {
					pInfo.setTypeDoc(pjur.getScrTypedoc().getId().toString());
				}
				pInfo.setNif(pjur.getCif());
				pInfo.setFirstName("");
				pInfo.setSecondName("");
				pInfo.setName(parseValues(pjur.getName()));

			}

			if (!result.getDom().isEmpty()) {
				List aux = null;
				ScrAddress address = null;
				ScrDom dom = null;
				ScrAddrtel dirtel = null;
				for (Iterator it = result.getAddresses().iterator(); it
						.hasNext();) {
					address = (ScrAddress) it.next();
					aux = (List) result.getDom().get(address.getId());
					if (!aux.isEmpty()) {
						if (address.getType() == 0) {
							for (Iterator it2 = aux.iterator(); it2.hasNext();) {
								dom = (ScrDom) it2.next();
								pInfo.addAddress(getDom(dom));
							}
						} else {
							for (Iterator it3 = aux.iterator(); it3.hasNext();) {
								dirtel = (ScrAddrtel) it3.next();
								pInfo.addAddressTel(getDirtel(dirtel));
							}
						}
					}
				}
			}

			perInfo = XMLPersons.createXMLRecoverPersonInfo(pInfo);
		}
		return perInfo;

	}

	private static String createXMLPersonsAddresses(Map addresses)
			throws Exception {
		List perAddresses = new ArrayList();
		boolean type = true;
		if (!addresses.isEmpty()) {
			List aux = null;
			ScrAddress address = null;
			ScrDom dom = null;
			ScrAddrtel dirtel = null;

			for (Iterator it = addresses.keySet().iterator(); it.hasNext();) {
				address = (ScrAddress) it.next();
				if (address != null) {
					aux = (List) (addresses.get(address));
					if (address.getType() == 0) {
						type = true;
						for (Iterator it2 = aux.iterator(); it2.hasNext();) {
							dom = (ScrDom) it2.next();
							perAddresses.add(getDom(dom));
						}
					} else {
						type = false;
						for (Iterator it3 = aux.iterator(); it3.hasNext();) {
							dirtel = (ScrAddrtel) it3.next();
							perAddresses.add(getDirtel(dirtel));
						}
					}
				}
			}
		}
		if (type) {
			return XMLPersons.createXMLPersonsAddresses(perAddresses);
		} else {
			return XMLPersons.createXMLPersonsAddressesTel(perAddresses);
		}
	}

	private static PersonAddress getDom(ScrDom dom) {
		PersonAddress pAddress = new PersonAddress();
		if (dom.getAddress() != null) {
			pAddress.setDom(dom.getAddress());
		} else {
			pAddress.setDom(BLANCO);
		}
		if (dom.getZip() != null) {
			pAddress.setZip(dom.getZip());
		} else {
			pAddress.setZip(BLANCO);
		}
		if (dom.getCity() != null) {
			pAddress.setCity(dom.getCity());
		} else {
			pAddress.setCity(BLANCO);
		}
		if (dom.getCountry() != null) {
			pAddress.setProvince(dom.getCountry());
		} else {
			pAddress.setProvince(BLANCO);
		}
		pAddress.setId(dom.getId().toString());
		pAddress.setPreference(Integer.toString(dom.getPreference()));
		return pAddress;
	}

	private static PersonAddressTel getDirtel(ScrAddrtel dirtel) {
		PersonAddressTel pAddressTel = new PersonAddressTel();
		if (dirtel.getAddress() != null) {
			pAddressTel.setDirTel(dirtel.getAddress());
		} else {
			pAddressTel.setDirTel(BLANCO);
		}

		pAddressTel.setType((dirtel.getScrTypeaddress()).getId().toString());
		pAddressTel.setId(new Integer(dirtel.getId()).toString());
		pAddressTel.setPreference(Integer.toString(dirtel.getPreference()));
		return pAddressTel;
	}

	private static String getCriteriaBuscInter(String name, String operator,
			String valor, String entidad) throws Exception {
		StringBuffer buffer = new StringBuffer();

		String dataBaseType = getDataBaseType("");
		String oraVersion = null;

		if (dataBaseType.equals("ORACLE")) {

			oraVersion = getOracleVersion(entidad);

		}

		if (dataBaseType.equals("DB2") || dataBaseType.equals("POSTGRESQL")
				|| (dataBaseType.equals("ORACLE") && oraVersion.equals("1"))) {
			buffer.append(name.toUpperCase());
		} else {
			buffer.append(name);
		}

		if (operator.equals(ServerKeys.EQUAL)) {
			buffer.append("=");
			buffer.append("'");
			buffer.append(StringEscapeUtils.escapeSql(valor));
			buffer.append("'");
		}
		if (operator.equals(ServerKeys.BEGIN_BY)) {
			buffer.append(" like ");
			buffer.append("'");
			buffer.append(StringEscapeUtils.escapeSql(valor));
			buffer.append(DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getLikeCharacter());
			buffer.append("'");
		}
		if (operator.equals(ServerKeys.CONTAIN)) {
			buffer.append(" like ");
			buffer.append("'");
			buffer.append(DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getLikeCharacter());
			buffer.append(StringEscapeUtils.escapeSql(valor));
			buffer.append(DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getLikeCharacter());
			buffer.append("'");
		}

		return buffer.toString();
	}

	private static String createWhereCriteriaPFisPJur(List listPFisPJur,
			boolean counSearch, String entidad) throws Exception {
		StringBuffer wherePfis = new StringBuffer();
		if (counSearch) {
			wherePfis.append(" where ");
		}
		PersonCriteria pCriteria = null;
		for (int i = 0; i < listPFisPJur.size(); i++) {
			pCriteria = (PersonCriteria) listPFisPJur.get(i);
			if (i > 0 && i < listPFisPJur.size()) {
				wherePfis.append(" and ");
			}
			wherePfis.append(getCriteriaBuscInter(pCriteria.getField(),
					pCriteria.getOperator(), pCriteria.getValue(), entidad));
		}

		return wherePfis.toString();
	}

	private static String parseValues(String value) {
		String result = value;
		if (value == null)
			return "";

		result = result.replaceAll("\"", "'");
		result = result.replaceAll("\\n", "");
		result = result.replaceAll("\\r", "");
		return result;
	}

	private static String getDataBaseType(String sessionID) throws Exception {
		String dataBase = null;
		try {
			dataBase = DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getDataBaseType();
		} catch (Exception e) {
			throw e;
		}
		return dataBase;
	}

	private static String getOracleVersion(String entidad) throws Exception {
		String oVersion = null;
		try {
			oVersion = DBEntityDAOFactory.getCurrentDBEntityDAO().getVersion(
					entidad);
			String aux1 = oVersion.substring(0, oVersion.indexOf("."));
			String aux = oVersion.substring(oVersion.indexOf(".") + 1,
					oVersion.length());
			if (new Integer(aux1).intValue() < 10) {
				oVersion = "1";
			} else {
				oVersion = aux.substring(0, aux.indexOf("."));
			}
		} catch (Exception e) {
			throw e;
		}
		return oVersion;
	}

}
