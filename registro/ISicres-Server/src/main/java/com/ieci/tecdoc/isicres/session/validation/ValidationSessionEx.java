/**
 *
 */
package com.ieci.tecdoc.isicres.session.validation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.expression.Expression;
import net.sf.hibernate.type.Type;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Iuserdepthdr;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrOrg;
import com.ieci.tecdoc.common.invesicres.ScrOrgct;
import com.ieci.tecdoc.common.invesicres.ScrOrgeu;
import com.ieci.tecdoc.common.invesicres.ScrOrggl;
import com.ieci.tecdoc.common.invesicres.ScrTmzofic;
import com.ieci.tecdoc.common.invesicres.ScrTt;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.EntityByLanguage;
import com.ieci.tecdoc.common.utils.ISicresQueries;
import com.ieci.tecdoc.common.utils.adapter.XMLPersons;
import com.ieci.tecdoc.isicres.person.PersonValidationFactory;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.Validator;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

/**
 * @author 66575267
 *
 */
public class ValidationSessionEx extends ValidationSessionUtil implements
		ServerKeys, HibernateKeys {

	private static final Logger log = Logger
			.getLogger(ValidationSessionEx.class);

	/***************************************************************************
	 * Public methods
	 **************************************************************************/

	public static Object getTopLevelParentScrOrg(String sessionID, int scrID,
			String language, String entidad) throws AttributesException, SessionException,
			ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		Object result = null;
		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesion
			CacheFactory.getCacheInterface().getCacheEntry(sessionID);

			ScrOrg scrOrg = null;
			ScrOrgeu scrOrgEu = null;
			ScrOrggl scrOrgGl = null;
			ScrOrgct scrOrgCt = null;

			do {
				result = session.load(EntityByLanguage
						.getScrOrgLanguage(language), new Integer(scrID));
				if (result instanceof ScrOrg) {
					scrOrg = (ScrOrg) result;
					if (scrOrg.getIdFather() != null) {
						scrID = scrOrg.getIdFather().intValue();
					}
				} else if (result instanceof ScrOrgeu) {
					scrOrgEu = (ScrOrgeu) result;
					if (scrOrgEu.getIdFather() != null) {
						scrID = scrOrgEu.getIdFather().intValue();
					}
				} else if (result instanceof ScrOrggl) {
					scrOrgGl = (ScrOrggl) result;
					if (scrOrgGl.getIdFather() != null) {
						scrID = scrOrgGl.getIdFather().intValue();
					}
				} else if (result instanceof ScrOrgct) {
					scrOrgCt = (ScrOrgct) result;
					if (scrOrgCt.getIdFather() != null) {
						scrID = scrOrgCt.getIdFather().intValue();
					}
				}
			} while ((scrOrg != null && scrOrg.getIdFather() != null)
					|| (scrOrgEu != null && scrOrgEu.getIdFather() != null)
					|| (scrOrgGl != null && scrOrgGl.getIdFather() != null)
					|| (scrOrgCt != null && scrOrgCt.getIdFather() != null));

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error(
					"Impossible to load getTopLevelParentScrOrg for session ["
							+ sessionID + "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_VALIDATIONLISTFIELDS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static Object getScrOrg(String sessionID, int id, String language,
			String entidad) throws AttributesException, SessionException,
			ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		Object result = null;
		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesion
			CacheFactory.getCacheInterface().getCacheEntry(sessionID);

			result = session.load(EntityByLanguage.getScrOrgLanguage(language),
					new Integer(id));

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

	public static Object getScrOrgValidate(String sessionID, int id,
			String language, String entidad) throws SessionException,
			ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		Object result = null;
		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesion
			CacheFactory.getCacheInterface().getCacheEntry(sessionID);

			result = session.load(EntityByLanguage.getScrOrgLanguage(language),
					new Integer(id));

			HibernateUtil.commitTransaction(tran);

		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			result = new ScrOrg();
			((ScrOrg) result).setName(" ");
		}
		return result;
	}

	public static Object getScrTypeAdm(String sessionID, int id,
			String language, String entidad) throws AttributesException,
			SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		Object result = null;
		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesion
			CacheFactory.getCacheInterface().getCacheEntry(sessionID);

			result = session.load(EntityByLanguage
					.getScrTypeAdmLanguage(language), new Integer(id));

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to load ScrTypeAdm for session [" + sessionID
					+ "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_VALIDATIONLISTFIELDS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static String getOneScrtt(String sessionID, String valueTransport,
			String entidad) throws AttributesException, SessionException,
			ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		String result = null;

		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesion
			CacheFactory.getCacheInterface().getCacheEntry(sessionID);

			try {
				ScrTt scr = ISicresQueries.getScrTt(session, valueTransport);
				result = scr.getTransport();
			} catch (HibernateException h) {
			}

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

	public static String getScrCities(String sessionID, Integer provId,
			String entidad) throws AttributesException, SessionException,
			ValidationException {
		String result = null;
		try {
			String xmlParamId = XMLPersons.createXMLParamIdInfo(provId
					.toString(), sessionID, entidad);

			result = PersonValidationFactory.getCurrentPersonValidation()
					.getCities(xmlParamId);

			return result;
		} catch (SessionException sE) {
			throw sE;
		} catch (Exception e) {
			log.error("Impossible to find getScrProv", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_PROV);
		}
	}

	public static String getDir(String sessionID, Integer personID,
			int maxResults, String entidad, int typeAddress)
			throws AttributesException, SessionException, ValidationException {
		return getDirTemp(sessionID, personID, maxResults, false, entidad,
				typeAddress);
	}

	public static String getScrProv(String sessionID, String entidad)
			throws AttributesException, SessionException, ValidationException {
		String result = null;
		try {
			String xmlParamId = XMLPersons.createXMLParamIdInfo(null,
					sessionID, entidad);

			result = PersonValidationFactory.getCurrentPersonValidation()
					.getProvicies(xmlParamId);

			return result;
		} catch (SessionException sE) {
			throw sE;
		} catch (Exception e) {
			log.error("Impossible to find getScrProv", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_PROV);
		}
	}

	public static String getTypeDocs(String sessionID, Integer personType,
			String entidad) throws SessionException, AttributesException {
		String result = null;
		try {
			String personTypeStr = null;
			if (personType != null) {
				personTypeStr = personType.toString();
			}

			String xmlParamId = XMLPersons.createXMLParamIdInfo(personTypeStr,
					sessionID, entidad);

			result = PersonValidationFactory.getCurrentPersonValidation()
					.getDocsType(xmlParamId);

			return result;
		} catch (SessionException sE) {
			throw sE;
		} catch (Exception e) {
			log.error("Impossible to find ScrTypeDoc", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_TYPE_DOCS);
		}
	}

	public static String getTypeAddresses(String sessionID, String entidad)
			throws SessionException, AttributesException {
		String result = null;
		try {
			String xmlParamId = XMLPersons.createXMLParamIdInfo(null,
					sessionID, entidad);

			result = PersonValidationFactory.getCurrentPersonValidation()
					.getAddressesType(xmlParamId);

			return result;
		} catch (SessionException sE) {
			throw sE;
		} catch (Exception e) {
			log.error("Impossible to find ScrTypeAddress", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_TYPE_ADDRESSES);
		}
	}

	/**
	 * Metodo que se usa para hacer cambio de oficina al usuario actual conectado.
	 * Desbloquea todos los registros bloqueados por el usuario actual
	 * Actualiza el usuario
	 * Actualiza la oficina
	 * Actualiza la zona horaria de la oficina
	 *
	 * @param sessionID
	 * @param code
	 * @param entidad
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static void validateOfficeCode(String sessionID, String code,
			String entidad) throws SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesion
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);

			ScrOfic scrOfic = ISicresQueries.getScrOficByCode(session, code);
			if (scrOfic == null) {
				throw new ValidationException(
						ValidationException.ERROR_ATTRIBUTE_NOT_FOUND);
			}

			//desbloquea registros del usuario actual, para ello obtenemos todos los libros que tiene abiertos y borramos los bloqueos de
			//los registros que pertenecen a dicho usuario y a esos libros
			List openBooks = new ArrayList();
			for (Iterator it = cacheBag.keySet().iterator(); it.hasNext();) {
				Object o = it.next();
				if (o instanceof Integer) {
					Integer bookID = (Integer) o;
					StringBuffer query = new StringBuffer();
					query.append("FROM ");
					query.append(HIBERNATE_Idocfdrstat);
					query
							.append(" scr WHERE scr.archid=? AND scr.userid=? and scr.stat=1");
					int result = session.delete(query.toString(), new Object[] {
							bookID, user.getId() }, new Type[] {
							Hibernate.INTEGER, Hibernate.INTEGER });

					if (log.isDebugEnabled()) {
						log.debug("Desbloqueos  [" + result + "]");
					}
					openBooks.add(bookID);
				}
			}

			//elimina de la cachebah los libros abiertos por el usuario y oficina anterior
			for (Iterator it = openBooks.iterator(); it.hasNext();) {
				Integer bookIdForDelete = (Integer) it.next();
				if (cacheBag.containsKey(bookIdForDelete)) {
					cacheBag.remove(bookIdForDelete);
				}
			}

			DBEntityDAOFactory.getCurrentDBEntityDAO()
					.deleteIdsGenerationTable(user.getId(), entidad);
			//setea al usuario la nueva oficina
			user.setDeptid(new Integer(scrOfic.getDeptid()));
			ScrTmzofic scrtmzofic = getScrTmzofic(session, scrOfic.getId());
			if (cacheBag.containsKey(HIBERNATE_ScrOfic)) {
				cacheBag.remove(HIBERNATE_ScrOfic);
			}
			//actualiza la oficina
			cacheBag.put(HIBERNATE_ScrOfic, scrOfic);

			//actualiza el usuario
			cacheBag.remove(HIBERNATE_Iuseruserhdr);
			cacheBag.put(HIBERNATE_Iuseruserhdr, user);

			//actualiza la zona horaria de la oficina
			if (cacheBag.containsKey(HIBERNATE_ScrTmzofic)) {
				cacheBag.remove(HIBERNATE_ScrTmzofic);
			}
			if (scrtmzofic != null) {
				cacheBag.put(HIBERNATE_ScrTmzofic, scrtmzofic);
			}

			HibernateUtil.commitTransaction(tran);

		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (ValidationException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to validate office code: " + code
					+ " for session [" + sessionID + "]", e);
			throw new ValidationException(
					ValidationException.ERROR_ATTRIBUTE_NOT_FOUND);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	/**
	 *
	 * Metodo que obtiene el listado de objetos segun el userTypeId que pasemos como parametro
	 * El array de objetos puede ser: usuario, departamentos o grupos
	 *
	 * @param sessionID
	 * @param userTypeId
	 * @param entidad
	 * @return
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static List getDeptsGroupsUsers(String sessionID,
			Integer userTypeId, String entidad) throws SessionException,
			ValidationException {
		Transaction tran = null;
		List deptsGroupsUserList = null;
		try {
			Validator.validate_String_NotNull_LengthMayorZero(sessionID,
					ValidationException.ATTRIBUTE_SESSION);

			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesion
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);

			ScrOfic scrOfic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);
			List iUserGroupUser = ISicresQueries.getIUserGroupUser(session,
					user.getId());

			if (userTypeId.intValue() == 1) {
				deptsGroupsUserList = ISicresQueries.getUsers(session, user
						.getId());
			} else if (userTypeId.intValue() == 2) {
				if (scrOfic != null) {
					deptsGroupsUserList = ISicresQueries.getDepts(session,
							new Integer(scrOfic.getDeptid()));
				} else {
					deptsGroupsUserList = ISicresQueries
							.getDepts(session, null);
				}
			} else {
				//obtenemos los grupos a excepcion de los que pertenece el usuario
				deptsGroupsUserList = ISicresQueries.getGroups(session,
						iUserGroupUser);
			}

			HibernateUtil.commitTransaction(tran);

			return deptsGroupsUserList;
		} catch (SessionException sE) {
			throw sE;
		} catch (Exception e) {
			log.error("Impossible to find deptsGroupsUserList", e);
			throw new ValidationException(
					ValidationException.ERROR_USERS_LIST_NOT_FOUND);
		}
	}

	public static List getGroupsUsersLDAP(String sessionID, Integer userTypeId,
			String entidad) throws SessionException, ValidationException {
		Transaction tran = null;
		List ldapGroupsUsersList = null;
		try {
			Validator.validate_String_NotNull_LengthMayorZero(sessionID,
					ValidationException.ATTRIBUTE_SESSION);

			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);

			if (userTypeId.intValue() == 1) {
				// Este metodo nos devuelve lo que queremos, que es la lista de
				// usuarios ldap excepto el usuario conectado
				ldapGroupsUsersList = ISicresQueries.getLdapUsers(session, user
						.getId());
			} else {
				// ldapGroupsUsersList = getLDAPGroups(sessionID, entidad);
				List userDepts = user.getDeptList();
				List userLdapGroups = new ArrayList();

				if (userDepts != null && userDepts.size() > 0) {
					for (Iterator it = userDepts.listIterator(); it.hasNext();) {
						Iuserdepthdr deptHdr = ISicresQueries
								.getUserDeptHdrByDeptId(session, new Integer(it
										.next().toString()));
						userLdapGroups.add(new Integer(deptHdr.getCrtrid()));
					}
				}
				ldapGroupsUsersList = ISicresQueries.getLdapGroups(session,
						userLdapGroups);
			}

			HibernateUtil.commitTransaction(tran);

			return ldapGroupsUsersList;
		} catch (SessionException sE) {
			throw sE;
		} catch (Exception e) {
			log.error("Impossible to find ldapGroupsUsers", e);
			throw new ValidationException(
					ValidationException.ERROR_USERS_LIST_NOT_FOUND);
		}finally{
			HibernateUtil.closeSession(entidad);
		}
	}

	public static List getOtherOffices(String sessionID, Locale locale,
			String entidad) throws AttributesException, SessionException,
			ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		Transaction tran = null;
		String sqlUsrOfic = "id in (select idofic from scr_usrofic where iduser = ";
		String sqlIuseruserhdr = "deptid in (select deptid from iuseruserhdr where id = ";
		StringBuffer queryUsrOfic = new StringBuffer();
		StringBuffer queryIuseruserhdr = new StringBuffer();
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			ScrOfic scrOfic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

			queryUsrOfic.append(sqlUsrOfic);
			queryUsrOfic.append(user.getId());
			queryUsrOfic.append(")");

			queryIuseruserhdr.append(sqlIuseruserhdr);
			queryIuseruserhdr.append(user.getId());
			queryIuseruserhdr.append(")");

			// Criteria criteriaResults = session.createCriteria(ScrOfic.class);
			Criteria criteriaResults = session.createCriteria(EntityByLanguage
					.getScrOficLanguage(locale.getLanguage()));
			criteriaResults.add(Expression.sql(queryUsrOfic.toString()));
			List list = criteriaResults.list();

			// Criteria criteriaResults1 =
			// session.createCriteria(ScrOfic.class);
			Criteria criteriaResults1 = session.createCriteria(EntityByLanguage
					.getScrOficLanguage(locale.getLanguage()));
			criteriaResults1.add(Expression.sql(queryIuseruserhdr.toString()));
			List list1 = criteriaResults1.list();

			EntityByLanguage.removeOfic(scrOfic, list, list1);

			HibernateUtil.commitTransaction(tran);

			return list;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to load other offices for user for session ["
					+ sessionID + "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_OTHER_OFFICE_USER);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static List getOtherOfficesLDAP(String sessionID, String entidad)
			throws ValidationException, SessionException, AttributesException {

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		try {
			// Recuperamos la sesión
			Session session = HibernateUtil.currentSession(entidad);

			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);

			List allOfficesUser = new ArrayList();
			List officesUser = new ArrayList();

			if (user.getDeptList() != null) {
				allOfficesUser.addAll(user.getDeptList());
			}
			allOfficesUser.remove(user.getDeptid());

			for (Iterator it = allOfficesUser.listIterator(); it.hasNext();) {
				ScrOfic tmpOfic = ISicresQueries.getScrOficByDeptId(session,
						new Integer(it.next().toString()));

				officesUser.add(tmpOfic);
			}

			return officesUser;

		} catch (SessionException sE) {
			throw sE;
		} catch (Exception e) {
			log.error("Impossible to load other offices for user for session ["
					+ sessionID + "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_OTHER_OFFICE_USER);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

}
