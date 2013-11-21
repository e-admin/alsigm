package com.ieci.tecdoc.idoc.authentication;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.SearchControls;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.expression.Expression;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.AuthenticationPolicy;
import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.LDAPAuthenticationUser;
import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Iuserdepthdr;
import com.ieci.tecdoc.common.invesdoc.Iusergenperm;
import com.ieci.tecdoc.common.invesdoc.Iuserldapgrphdr;
import com.ieci.tecdoc.common.invesdoc.Iuserldapuserhdr;
import com.ieci.tecdoc.common.invesdoc.Iuserusertype;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.keys.ISicresKeys;
import com.ieci.tecdoc.common.utils.ISicresQueries;
import com.ieci.tecdoc.idoc.authentication.ldap.LdapBasicFns;
import com.ieci.tecdoc.idoc.authentication.ldap.LdapConnCfg;
import com.ieci.tecdoc.idoc.authentication.ldap.LdapConnection;
import com.ieci.tecdoc.idoc.authentication.ldap.LdapSearch;
import com.ieci.tecdoc.idoc.decoder.dbinfo.LDAPDef;
import com.ieci.tecdoc.idoc.repository.RepositoryLDAP;
import com.ieci.tecdoc.idoc.utils.CryptoUtils;
import com.ieci.tecdoc.idoc.utils.LDAPRBUtil;
import com.ieci.tecdoc.idoc.utils.LdapConfigUtils;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.Validator;

public class LDAPAuthenticationPolicy implements AuthenticationPolicy,
		IDocKeys, ISicresKeys {

	private static final Logger log = Logger
			.getLogger(LDAPAuthenticationPolicy.class);

	public AuthenticationUser validate(String login, String password,
			String entidad) throws SecurityException, ValidationException {
		AuthenticationUser user = null;
		validateParameters(login, password);
		LDAPAuthenticationUser attributesUser = null;

		try {
			LDAPDef ldapDef = RepositoryLDAP.getInstance(entidad).getLDAPInfo();
			if (log.isDebugEnabled()) {
				log.debug("LDAPDef [" + ldapDef + "] con el log [" + log + "]");
			}
			if (ldapDef.getLdapEngine() == 0) {
				throw new SecurityException(
						SecurityException.ERROR_AUTHENTICATION_POLICY_NOTFOUND);
			}

			//String dn = ldapDef.getLdapUser();
			String attrID = LDAPRBUtil.getInstance(null).getProperty(
					LDAP_ATTRIBUTES + ldapDef.getLdapEngine());
			String attributes[] = parseAttributes(attrID);
			String passwordDecrypt = CryptoUtils.decryptPasswordLDAP(ldapDef
					.getLdapPassword());
			LdapConnection conn = new LdapConnection();
			LdapConnCfg ldapConfig=LdapConfigUtils.createLdapConnConfig(ldapDef);
			ldapConfig.setProvider(1);
			String dn=ldapConfig.getUser();
			conn.open(ldapConfig, dn, passwordDecrypt);

			LdapSearch search = getSearch(login, conn, ldapDef, attributes);
			attributesUser = getUserAttributes(search, attributes);
			attributesUser.setGuidStringFormat(LdapBasicFns.formatGuid(conn,
					attributesUser.getGuid()));
			if (log.isDebugEnabled()) {
				log.debug("attributesUser [" + attributesUser
						+ "] con el log [" + log + "]");
			}

			conn.close();


			//se valida al usuario logueado y su contraseña
			validateUserPassword(ldapDef, attributesUser.getDn(), password);

			Integer userId = userVerification(attributesUser, entidad);
//			Integer deptId = connectionVerification(attributesUser, passwordDecrypt,
//					ldapDef, attributes, entidad);

			Integer deptId = null;

			// List deptList = connectionVerification(attributesUser, passwordDecrypt, ldapDef, attributes, entidad);
			//obtiene los departamentos del usuario
			List deptListLDAP = connectionVerification(attributesUser, passwordDecrypt, ldapDef, attributes, entidad);
			//obtenemos las oficinas del usuario
			List deptList = getUserDeptList (userId, entidad, deptListLDAP);
			//obtenemos los ids de los grupos a los que pertenece el usuario
			List groupList = getListGroupOfUser(attributesUser, passwordDecrypt, ldapDef, attributes, entidad);

			user = new AuthenticationUser();

			if (deptList != null && deptList.size() > 0) {
				deptId = (Integer)deptList.get(0);
				user.setDeptList(deptList);
			}

			user.setId(userId);
			user.setName(attributesUser.getFullName());
			user.setDeptid(deptId);
			user.setDeptIdOriginal(deptId);
			user.setGroupList(groupList);

		} catch (NamingException e) {
			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		} catch (SecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		}

		return user;
	}

	private List getUserDeptList (Integer userId, String entidad, List deptListLDAP) throws SecurityException {

		String sqlUsrOfic = "id in (select idofic from scr_usrofic where iduser = ";
		StringBuffer query = new StringBuffer();
		List deptList = new ArrayList();

		try {
			Session session = HibernateUtil.currentSession(entidad);

			query.append(sqlUsrOfic);
			query.append(userId);
			query.append(")");

			Criteria criteriaResults = session.createCriteria(ScrOfic.class);
			criteriaResults.add(Expression.sql(query.toString()));
			List list = criteriaResults.list();

			if (deptListLDAP != null) {
				deptList.addAll(deptListLDAP);
			}
			if (list != null && list.size() > 0) {
				Iterator it = list.iterator();
				while (it.hasNext()) {
					ScrOfic ofic = (ScrOfic) it.next();
					if (!deptList.contains(new Integer(ofic.getDeptid())))
						deptList.add(new Integer(ofic.getDeptid()));
				}
			}

			return deptList;

		} catch (Exception e) {
			log.error("Impossible to load other offices for user ["
					+ userId + "]", e);

			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		}
	}


	public AuthenticationUser validate(String userDn, String entidad)
			throws SecurityException, ValidationException {
		AuthenticationUser user = null;
		LDAPAuthenticationUser attributesUser = null;

		LdapConnection conn = new LdapConnection();
		try {
			// String decodedDn = CryptoUtils.getDecodeDn(userDn);
			String decodedDn = userDn;
			LDAPDef ldapDef = RepositoryLDAP.getInstance(entidad).getLDAPInfo();
			if (log.isDebugEnabled()) {
				log.debug("LDAPDef [" + ldapDef + "] con el log [" + log + "]");
			}
			if (ldapDef.getLdapEngine() == 0) {
				throw new SecurityException(
						SecurityException.ERROR_AUTHENTICATION_POLICY_NOTFOUND);
			}

			//String dn = ldapDef.getLdapUser();
			String attrID = LDAPRBUtil.getInstance(null).getProperty(
					LDAP_ATTRIBUTES + ldapDef.getLdapEngine());
			String attributes[] = parseAttributes(attrID);
			String passwordDecrypt = CryptoUtils.decryptPasswordLDAP(ldapDef
					.getLdapPassword());
			LdapConnCfg ldapConfig=LdapConfigUtils.createLdapConnConfig(ldapDef);
			String dn = ldapConfig.getUser();
			ldapConfig.setProvider(1);
			conn.open(ldapConfig, dn, passwordDecrypt);

			LdapSearch search = getSearchSSO(decodedDn, conn, ldapDef,
					attributes);
			attributesUser = getUserAttributes(search, attributes);
			attributesUser.setGuidStringFormat(LdapBasicFns.formatGuid(conn,
					attributesUser.getGuid()));
			if (log.isDebugEnabled()) {
				log.debug("attributesUser [" + attributesUser
						+ "] con el log [" + log + "]");
			}

			Integer userId = userVerification(attributesUser, entidad);
//			Integer deptId = connectionVerification(conn, attributesUser, ldapDef, attributes, entidad);

			Integer deptId = null;

			List groupList = new ArrayList();
			List deptList = connectionVerification(conn, attributesUser, ldapDef, attributes, entidad, groupList);
			//se deja comentado este punto porque es nuevo y soluciona el error detectado con el boton cambiar oficina por SSO

			deptList = getUserDeptList (userId, entidad, deptList);

			user = new AuthenticationUser();

			if (deptList != null && deptList.size() > 0) {
				deptId = (Integer)deptList.get(0);
				user.setDeptList(deptList);
			}

			user.setId(userId);
			user.setName(attributesUser.getFullName());
			user.setDeptid(deptId);
			user.setGroupList(groupList);

			return user;

		} catch (NamingException e) {
			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		} catch (SecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		} finally{
			try{
				conn.close();
			}catch(Exception e){
				if(log.isDebugEnabled()){
					log.debug("Error al cerrar conexión LDAP", e);
				}
			}
		}
	}

	public LdapSearch getSearch(String login, LdapConnection conn,
			LDAPDef ldapDef, String[] attributes) throws SecurityException {
		String filter = null;
		LdapSearch search = null;
		try {
			// busqueda por dn
			filter = MessageFormat.format(LDAPRBUtil.getInstance(null)
					.getProperty(LDAP_SCOPE_BASE + ldapDef.getLdapEngine()),
					new String[] { login });
			if (log.isDebugEnabled()) {
				log.debug("filter [" + filter + "] con el log [" + log + "]");
				log.debug("dn [" + login + "] con el log [" + log + "]");
			}
			search = new LdapSearch();
			search.initialize(conn, ldapDef.getLdapRoot(),
					SearchControls.OBJECT_SCOPE, filter, attributes);
			search.execute();
			if (!search.next()) {
				// Busqueda por UniqueName
				filter = MessageFormat.format(LDAPRBUtil.getInstance(null)
						.getProperty(
								LDAP_SCOPE_SUBTREE1 + ldapDef.getLdapEngine()),
						new String[] { login });
				if (log.isDebugEnabled()) {
					log.debug("filter [" + filter + "] con el log [" + log
							+ "]");
					log.debug("UniqueName [" + login + "] con el log [" + log
							+ "]");
				}
				search = new LdapSearch();
				search.initialize(conn, ldapDef.getLdapRoot(),
						SearchControls.SUBTREE_SCOPE, filter, attributes);
				search.execute();
				if (!search.next()) {
					// Busqueda por número de cuenta sAMAccountName
					filter = MessageFormat.format(LDAPRBUtil.getInstance(null)
							.getProperty(
									LDAP_SCOPE_SUBTREE2
											+ ldapDef.getLdapEngine()),
							new String[] { login });
					if (log.isDebugEnabled()) {
						log.debug("filter [" + filter + "] con el log [" + log
								+ "]");
						log.debug("sAMAccountName [" + login + "] con el log ["
								+ log + "]");
					}
					if (!filter.equals("")) {
						search = new LdapSearch();
						search.initialize(conn, ldapDef.getLdapRoot(),
								SearchControls.SUBTREE_SCOPE, filter,
								attributes);
						search.execute();
						if (!search.next()) {
							throw new SecurityException(
									SecurityException.ERROR_NAME_INCORRECT);
						}
					}
				}
			}
			if (search.getM_srAttrs() == null) {
				throw new SecurityException(
						SecurityException.ERROR_NAME_INCORRECT);
			}
		} catch (SecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new SecurityException(
					SecurityException.ERROR_CAN_NOT_FIND_USER_ATTRIBUTES_LDAP);
		}

		return search;

	}

	public LdapSearch getSearchSSO(String dn, LdapConnection conn,
			LDAPDef ldapDef, String[] attributes) throws SecurityException {
		String filter = null;
		LdapSearch search = null;
		try {
			// busqueda por dn
			filter = LDAP_SCOPE_BASE_SSO;
			search = new LdapSearch();
			search.initialize(conn, dn, SearchControls.OBJECT_SCOPE, filter,
					attributes);
			search.execute();
			if (!search.next()) {
				throw new SecurityException(
						SecurityException.ERROR_NAME_INCORRECT);
			}
		} catch (SecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new SecurityException(
					SecurityException.ERROR_CAN_NOT_FIND_USER_ATTRIBUTES_LDAP);
		}

		return search;

	}

	public LDAPAuthenticationUser getUserAttributes(LdapSearch search,
			String[] attributes) throws SecurityException {
		LDAPAuthenticationUser ldapAUser = new LDAPAuthenticationUser();
		if (search.getM_srAttrs() == null) {
			throw new SecurityException(
					SecurityException.ERROR_CAN_NOT_FIND_USER_ATTRIBUTES_LDAP);
		} else {
			try {
				Object object = null;
				for (NamingEnumeration enum1 = search.getM_srAttrs().getAll(); enum1
						.hasMore();) {
					Attribute attrib = (Attribute) enum1.next();

					for (NamingEnumeration e = attrib.getAll(); e.hasMore();) {
						object = e.next();
						try {
							if (attrib.getID().equals(attributes[0])) {
								ldapAUser.setGuid(object);
							}
							if (attrib.getID().equals(attributes[1])) {
								ldapAUser.setDn((String) object);
							}

							if (attrib.getID().equals(attributes[2])) {
								String obj = (String) object;
								if (attributes[2].equals("CN")) {
									if (obj != null && !obj.equals("")) {
										ldapAUser.setFullName((String) object);
									}
								} else {
									if (ldapAUser.getFullName() == null
											&& obj != null && !obj.equals("")) {
										ldapAUser.setFullName((String) object);
									}
								}
							}

							if (attrib.getID().equals(attributes[3])) {
								String obj = (String) object;
								if (attributes[3].equals("cn")) {
									if (obj != null && !obj.equals("")) {
										ldapAUser.setFullName((String) object);
									}
								} else {
									if (ldapAUser.getFullName() == null
											&& obj != null && !obj.equals("")) {
										ldapAUser.setFullName((String) object);
									}
								}
							}

							if (attrib.getID().equals(attributes[4])) {
								String obj = (String) object;
								if (ldapAUser.getFullName() == null
										&& obj != null && !obj.equals("")) {
									ldapAUser.setFullName((String) object);
								}
							}
							if (attrib.getID().equals(attributes[5])) {
								ldapAUser.setGroups((String) object);
							}
						} catch (Exception ex) {
						}
					}
				}

			} catch (NamingException e) {
				throw new SecurityException(
						SecurityException.ERROR_CAN_NOT_FIND_USER_ATTRIBUTES_LDAP);
			} catch (Exception e) {
				throw new SecurityException(
						SecurityException.ERROR_CAN_NOT_FIND_USER_ATTRIBUTES_LDAP);
			}
		}

		return ldapAUser;
	}

	/**
	 * Metodo que obtiene los ids de los grupos a los que pertenece el usuario
	 * @param conn
	 * @param attributesUser
	 * @param ldapDef
	 * @param attributes
	 * @param entidad
	 * @return
	 * @throws SecurityException
	 */
	public List getListGroupOfUser(LdapConnection conn,
			LDAPAuthenticationUser attributesUser, LDAPDef ldapDef,
			String attributes[], String entidad) throws SecurityException {
		List result = new ArrayList();
		String attr[] = { attributes[0] };
		try {
			String group = null;
			String filter = null;
			LdapSearch search = null;
			LDAPAuthenticationUser attributesGroup = null;
			List groups = null;
			int scope = new Integer(LDAPRBUtil.getInstance(null).getProperty(
					LDAP_SCOPEGROUP + ldapDef.getLdapEngine())).intValue();
			groups = attributesUser.getGroupList();
			if (log.isDebugEnabled()) {
				log.debug("groups [" + groups + "] con el log [" + log + "]");
			}
			for (int i = 0; i < groups.size(); i++) {
				group = (String) groups.get(i);
				filter = LDAPRBUtil.getInstance(null).getProperty(
						LDAP_SCOPE_BASESUBTREE_GROUP + ldapDef.getLdapEngine());
				search = new LdapSearch();
				search.initialize(conn, group, scope, filter, attr);
				search.execute();
				if (search.next()) {
					attributesGroup = getUserAttributes(search, attributes);
					attributesGroup.setGuidStringFormat(LdapBasicFns
							.formatGuid(conn, attributesGroup.getGuid()));

					Integer idGroup = getIdGroup(attributesGroup.getGuidStringFormat(), entidad);

					if(idGroup != null){
						result.add(idGroup);
					}
				}
			}
		} catch (SecurityException e) {
			throw new SecurityException(
					SecurityException.ERROR_PASSWORD_INCORRECT);
		} catch (Exception e) {
			throw new SecurityException(
					SecurityException.ERROR_PASSWORD_INCORRECT);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}

		}
		return result;
	}

	public List getListGroupOfUser(
			LDAPAuthenticationUser attributesUser, String password,
			LDAPDef ldapDef, String attributes[], String entidad)
			throws SecurityException {

		List result = new ArrayList();
		String attr[] = { attributes[0] };
		LdapConnection conn = null;
		try {
			String group = null;
			String filter = null;
			LdapSearch search = null;
			LDAPAuthenticationUser attributesGroup = null;
			if (log.isDebugEnabled()) {
				log.debug("dn [" + attributesUser.getDn() + "] con el log [" + log + "]");
			}
			conn = new LdapConnection();
			LdapConnCfg ldapConfig=LdapConfigUtils.createLdapConnConfig(ldapDef);
			ldapConfig.setProvider(1);
			conn.open(ldapConfig, ldapConfig.getUser(), password);

			List groups = null;
			int scope = new Integer(LDAPRBUtil.getInstance(null).getProperty(
					LDAP_SCOPEGROUP + ldapDef.getLdapEngine())).intValue();
			if (ldapDef.getLdapEngine() == 1) {
				groups = attributesUser.getGroupList();
				if (log.isDebugEnabled()) {
					log.debug(" groups [" + groups + "] con el log [" + log	+ "]");
				}

				filter = LDAPRBUtil.getInstance(null).getProperty(LDAP_SCOPE_BASESUBTREE_GROUP + ldapDef.getLdapEngine());
				if (log.isDebugEnabled()) {
					log.debug(" filter [" + filter + "] con el log [" + log	+ "]");
				}

				for (int i = 0; i < groups.size(); i++) {

					group = (String) groups.get(i);

					search = new LdapSearch();
					search.initialize(conn, group, scope, filter, attr);
					search.execute();
					if (search.next()) {
						attributesGroup = getUserAttributes(search, attributes);
						attributesGroup.setGuidStringFormat(LdapBasicFns.formatGuid(conn, attributesGroup.getGuid()));

						Integer idGroup = getIdGroup(attributesGroup.getGuidStringFormat(), entidad);

						if(idGroup != null){
							result.add(idGroup);
						}
					}
				}
			} else {
				filter = MessageFormat.format(LDAPRBUtil.getInstance(null)
						.getProperty(
								LDAP_SCOPE_BASESUBTREE_GROUP
										+ ldapDef.getLdapEngine()),
						new String[] { attributesUser.getDn() });
				if (log.isDebugEnabled()) {
					log.debug(" filter [" + filter + "] con el log [" + log
							+ "]");
				}
				List list = new ArrayList();
				list = getUserGroupGuidsIp(conn, ldapDef.getLdapRoot(), scope, filter, attr);
				if (log.isDebugEnabled()) {
					log.debug(" groups [" + list + "] con el log [" + log + "]");
				}

				String groupGuid = null;
				for (int i = 0; i < list.size(); i++) {
					groupGuid = (String) list.get(i);

					Integer idGroup = getIdGroup(groupGuid, entidad);

					if(idGroup != null){
						result.add(idGroup);
					}
				}

			}
		} catch (SecurityException e) {
			throw new SecurityException(
					SecurityException.ERROR_PASSWORD_INCORRECT);
		} catch (Exception e) {
			throw new SecurityException(
					SecurityException.ERROR_PASSWORD_INCORRECT);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}

		}
		return result;
	}

	public Integer userVerification(LDAPAuthenticationUser attributesUser,
			String entidad) throws SecurityException {

		Integer userId = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			// tran = session.beginTransaction();

			//consulta si existe el usuario en ldap
			List list = ISicresQueries.getUserLdapUser(session, attributesUser
					.getGuidStringFormat());
			// si no existe el usuario lo inserta en el modelo de isicres
			if (list == null || list.isEmpty()) {
				userId = new Integer(DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getNextIdForUser(attributesUser.getGuidStringFormat(),
								attributesUser.getFullName(), entidad));
				if (log.isDebugEnabled()) {
					log.debug("new userId [" + userId + "] con el log [" + log
							+ "]");
				}
				list = ISicresQueries.getUserUserType(session, userId
						.intValue(), 5);
				Iuserusertype userType = null;
				if (list == null || list.isEmpty()) {
					userType = new Iuserusertype();
					userType.setUserid(userId.intValue());

					//producto sicres valor 5
					// seteamos usuario estandar (operador de registro)
					//IDocKeys
					//IUSER_USER_TYPE_NULL       (0)
					//IUSERUSERTYPE_USER_TYPE_OPERATOR = 1;
					//IUSERUSERTYPE_USER_TYPE_BOOK_ADMIN = 2;
					//IUSERUSERTYPE_USER_TYPE_ADMIN = 3;
					userType.setProdid(5);
					userType.setType(1);
					session.save(userType);

					//IUSER_PROD_ID_IUSER    (2) (q tipo de producto es este? para q es?)
					userType = new Iuserusertype();
					userType.setUserid(userId.intValue());
					userType.setProdid(2);
					userType.setType(0);
					session.save(userType);

					session.flush();
				}
				//metodo que asigna los permisos al usuario IUSERGENPERMS
				asignarPermisosUsuario(userId, session);
			} else {
				Iuserldapuserhdr ldapUser = (Iuserldapuserhdr) list.get(0);
				if (log.isDebugEnabled()) {
					log.debug("exist userId Iuserldapuserhdr [" + ldapUser
							+ "] con el log [" + log + "]");
				}
				//obtenemos el perfil del usuario
				userId = new Integer(ldapUser.getId());
				list = ISicresQueries.getUserUserType(session,
						ldapUser.getId(), 5);

				// Si no existen valores en iuserusertype, se lanzará una excepción de seguridad
				if (list == null || list.size() == 0) {
					throw new SecurityException(SecurityException.ERROR_IUSERUSERTYPE_NOT_FOUND);
				}

				Iuserusertype userType = (Iuserusertype) list.get(0);
				if (log.isDebugEnabled()) {
					log.debug("exist userId Iuserusertype [" + userType
							+ "] con el log [" + log + "]");
				}
				// DBEntityDAOFactory.getCurrentDBEntityDAO().updateRole(userType.getUserid(),
				// userType.getProdid(), 1, entidad);
				DBEntityDAOFactory.getCurrentDBEntityDAO().updateRole(
						userType.getUserid(), userType.getProdid(),
						userType.getType(), entidad);
				DBEntityDAOFactory.getCurrentDBEntityDAO()
						.updateLdapFullName(ldapUser.getId(),
								attributesUser.getFullName(), entidad);
			}

			// HibernateUtil.commitTransaction(tran);
		} catch (HibernateException e) {
			// HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to verify user [" + attributesUser
					+ "] on LDAP", e);
			throw new SecurityException(SecurityException.ERROR_SQL);
		} catch (SecurityException e) {
			throw e;
		} catch (Exception e) {
			// HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to verify user [" + attributesUser
					+ "] on LDAP", e);
			throw new SecurityException(SecurityException.ERROR_SQL);
		}/*
			 * finally { HibernateUtil.closeSession(); }
			 */
		return userId;

	}

	/**
	 * Método que asigna los permisos al usuario (IUSERGENPERMS) al igual que si
	 * se da de alta al usuario desde la herramienta ISicresAdminWeb
	 *
	 * @param userId
	 *            - Identificador del usuario
	 * @param session
	 *            - Sesion de hibernate
	 *
	 * @throws HibernateException
	 */
	private void asignarPermisosUsuario(Integer userId, Session session)
			throws HibernateException {
		//obtenemos los permisos que tiene el usuario
		List listadoPermisosUsuario = ISicresQueries.getUserGenPerms(session,
				1, userId.intValue());
		Iusergenperm iuserGenPerm = null;
		// comprobamos si el usuario tiene permisos
		if (listadoPermisosUsuario == null || listadoPermisosUsuario.isEmpty()) {
			// si el usuario no tiene permisos creamos los permisos para cada
			// una de las aplicaciones: IUSER_PROD_ID_IDOC, IUSER_PROD_ID_IFLOW
			// y IUSER_PROD_ID_ISICRES

			iuserGenPerm = new Iusergenperm();
			iuserGenPerm.setDsttype(1);
			iuserGenPerm.setDstid(userId);
			// Asignamos el permiso de consulta
			iuserGenPerm.setPerms(1);
			// prodid: IUSER_PROD_ID_IDOC (3)
			iuserGenPerm.setProdid(3);
			session.save(iuserGenPerm);

			iuserGenPerm = new Iusergenperm();
			iuserGenPerm.setDsttype(1);
			iuserGenPerm.setDstid(userId);
			iuserGenPerm.setPerms(0);
			// prodid: IUSER_PROD_ID_IFLOW (4)
			iuserGenPerm.setProdid(4);
			session.save(iuserGenPerm);

			iuserGenPerm = new Iusergenperm();
			iuserGenPerm.setDsttype(1);
			iuserGenPerm.setDstid(userId);
			iuserGenPerm.setPerms(0);
			// prodid: IUSER_PROD_ID_ISICRES (5)
			iuserGenPerm.setProdid(5);
			session.save(iuserGenPerm);
			session.flush();
		}
	}

	public List connectionVerification(LdapConnection conn,
			LDAPAuthenticationUser attributesUser, LDAPDef ldapDef,
			String attributes[], String entidad, List groupList) throws SecurityException {
		Integer deptId = null;
		List deptList = new ArrayList();
		String attr[] = { attributes[0] };
		try {
			String group = null;
			String filter = null;
			LdapSearch search = null;
			LDAPAuthenticationUser attributesGroup = null;
			List groups = null;
			int scope = new Integer(LDAPRBUtil.getInstance(null).getProperty(
					LDAP_SCOPEGROUP + ldapDef.getLdapEngine())).intValue();
			groups = attributesUser.getGroupList();
			if (log.isDebugEnabled()) {
				log.debug("groups [" + groups + "] con el log [" + log + "]");
			}
			for (int i = 0; i < groups.size(); i++) {
				group = (String) groups.get(i);
				filter = LDAPRBUtil.getInstance(null).getProperty(
						LDAP_SCOPE_BASESUBTREE_GROUP + ldapDef.getLdapEngine());
				search = new LdapSearch();
				search.initialize(conn, group, scope, filter, attr);
				search.execute();
				if (search.next()) {
					attributesGroup = getUserAttributes(search, attributes);
					attributesGroup.setGuidStringFormat(LdapBasicFns
							.formatGuid(conn, attributesGroup.getGuid()));

					//obtiene los grupos
					Integer idGroup = getIdGroup(attributesGroup.getGuidStringFormat(), entidad);

					if(idGroup != null){
						groupList.add(idGroup);
					}

					deptId = getRegisterDeptOfic(attributesGroup
							.getGuidStringFormat(), entidad);

					if (deptId != null) {
						deptList.add(deptId);
					}
				}
			}
		} catch (SecurityException e) {
			log.error("Error connectionVerification: " + e.getMessage(), e);
			throw new SecurityException(
					SecurityException.ERROR_PASSWORD_INCORRECT);
		} catch (Exception e) {
			log.error("Error connectionVerification: " + e.getMessage(), e);
			throw new SecurityException(
					SecurityException.ERROR_PASSWORD_INCORRECT);
		}
		return deptList;
	}

	// método que abre una conexión con el usuario y password que le llega, para comprobar si con ese usuario
	// y password se puede crear una conexión
	public void validateUserPassword(LDAPDef ldapDef,String user,String password) throws SecurityException {
		LdapConnection conn = new LdapConnection();
		try{
			conn.open(ldapDef, user, password, 1);
		} catch (SecurityException e) {
			log.error("Impossible to verify user [" + user
					+ "] on LDAP", e);
			throw new SecurityException(
					SecurityException.ERROR_PASSWORD_INCORRECT);
		} catch (Exception e) {
			log.error("Impossible to verify user [" + user
					+ "] on LDAP", e);
			throw new SecurityException(
					SecurityException.ERROR_CAN_NOT_CONNECT_LDAP);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	public List connectionVerification(
			LDAPAuthenticationUser attributesUser, String password,
			LDAPDef ldapDef, String attributes[], String entidad)
			throws SecurityException {

		Integer deptId = null;
		List deptList = new ArrayList();
		String attr[] = { attributes[0] };
		LdapConnection conn = null;
		try {
			String group = null;
			String filter = null;
			LdapSearch search = null;
			LDAPAuthenticationUser attributesGroup = null;
			if (log.isDebugEnabled()) {
				log.debug("dn [" + attributesUser.getDn() + "] con el log [" + log + "]");
			}
			conn = new LdapConnection();
			LdapConnCfg ldapConfig=LdapConfigUtils.createLdapConnConfig(ldapDef);
			ldapConfig.setProvider(1);
			//conn.open(ldapConfig, ldapDef.getLdapUser(), password, 1);
			conn.open(ldapConfig, ldapConfig.getUser(), password);

			List groups = null;
			int scope = new Integer(LDAPRBUtil.getInstance(null).getProperty(
					LDAP_SCOPEGROUP + ldapDef.getLdapEngine())).intValue();
			if (ldapDef.getLdapEngine() == 1) {
				groups = attributesUser.getGroupList();
				if (log.isDebugEnabled()) {
					log.debug(" groups [" + groups + "] con el log [" + log	+ "]");
				}

				filter = LDAPRBUtil.getInstance(null).getProperty(LDAP_SCOPE_BASESUBTREE_GROUP + ldapDef.getLdapEngine());
				if (log.isDebugEnabled()) {
					log.debug(" filter [" + filter + "] con el log [" + log	+ "]");
				}

				for (int i = 0; i < groups.size(); i++) {

					group = (String) groups.get(i);

					search = new LdapSearch();
					search.initialize(conn, group, scope, filter, attr);
					search.execute();
					if (search.next()) {
						attributesGroup = getUserAttributes(search, attributes);
						attributesGroup.setGuidStringFormat(LdapBasicFns.formatGuid(conn, attributesGroup.getGuid()));
						deptId = getRegisterDeptOfic(attributesGroup.getGuidStringFormat(), entidad);

						if (deptId != null) {
							deptList.add(deptId);
							if (log.isDebugEnabled()) {
								log.debug(" deptId [" + deptId + "] con el log ["+ log + "]");
							}
							// break;
						}
					}
				}
			} else {
				filter = MessageFormat.format(LDAPRBUtil.getInstance(null)
						.getProperty(
								LDAP_SCOPE_BASESUBTREE_GROUP
										+ ldapDef.getLdapEngine()),
						new String[] { attributesUser.getDn() });
				if (log.isDebugEnabled()) {
					log.debug(" filter [" + filter + "] con el log [" + log
							+ "]");
				}
				List list = new ArrayList();
				list = getUserGroupGuidsIp(conn, ldapDef.getLdapRoot(), scope, filter, attr);
				if (log.isDebugEnabled()) {
					log.debug(" groups [" + list + "] con el log [" + log + "]");
				}

				String groupGuid = null;
				for (int i = 0; i < list.size(); i++) {
					groupGuid = (String) list.get(i);
					deptId = getRegisterDeptOfic(groupGuid, entidad);

					if (deptId != null) {
						if (log.isDebugEnabled()) {
							log.debug(" deptId [" + deptId + "] con el log [" + log	+ "]");
						}
						deptList.add(deptId);
						//break;
					}
				}

			}
		} catch (SecurityException e) {
			throw new SecurityException(
					SecurityException.ERROR_PASSWORD_INCORRECT);
		} catch (Exception e) {
			throw new SecurityException(
					SecurityException.ERROR_PASSWORD_INCORRECT);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}

		}
		return deptList;
	}

	public Integer getRegisterDeptOfic(String guid, String entidad)
			throws SecurityException {
		Transaction tran = null;
		Integer deptId = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			// tran = session.beginTransaction();

			// Obtener el grupo ldap de la tabla de grupos
			List list = ISicresQueries.getUserLdapPgrp(session, guid);
			if (list != null && !list.isEmpty()) {
				Iuserldapgrphdr udeptGrpHdr = (Iuserldapgrphdr) list.get(0);
				if (log.isDebugEnabled()) {
					log.debug(" Iuserldapgrphdr [" + udeptGrpHdr + "] con el log [" + log + "]");
				}

				// Obtener el departamento invesdoc asociado al grupo ldap
				list = ISicresQueries.getUserDeptHdr(session, udeptGrpHdr.getId());
				if (list != null && !list.isEmpty()) {
					Iuserdepthdr udeptHdr = (Iuserdepthdr) list.get(0);
					if (log.isDebugEnabled()) {
						log.debug(" Iuserdepthdr [" + udeptHdr + "] con el log [" + log + "]");
					}
					deptId = udeptHdr.getId();
				}
			}
			// HibernateUtil.commitTransaction(tran);
		} catch (Exception e) {
			// HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to verify user with guid[" + guid + "]", e);
			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		}/*
			 * finally { HibernateUtil.closeSession(); }
			 */
		return deptId;

	}


	/**
	 * Metodo que obtiene el id del grupo mediante su guid
	 * @param guid
	 * @param entidad
	 * @return id
	 * @throws SecurityException
	 */
	public Integer getIdGroup(String guid, String entidad)
			throws SecurityException {
		Transaction tran = null;
		Integer result = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			// tran = session.beginTransaction();

			// Obtener el grupo ldap de la tabla de grupos
			List list = ISicresQueries.getUserLdapPgrp(session, guid);
			if (list != null && !list.isEmpty()) {
				Iuserldapgrphdr udeptGrpHdr = (Iuserldapgrphdr) list.get(0);
				if (log.isDebugEnabled()) {
					log.debug(" Iuserldapgrphdr [" + udeptGrpHdr
							+ "] con el log [" + log + "]");
				}

				result = Integer.valueOf(udeptGrpHdr.getId());

			}
			// HibernateUtil.commitTransaction(tran);
		} catch (Exception e) {
			// HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to verify user with guid[" + guid + "]", e);
			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		}/*
		 * finally { HibernateUtil.closeSession(); }
		 */
		return result;

	}

	public String[] parseAttributes(String attrs) {
		String attributes[] = null;
		StringTokenizer tokenizer = new StringTokenizer(attrs, ",");
		int i = 0;
		while (tokenizer.hasMoreTokens()) {
			tokenizer.nextToken();
			i++;
		}
		tokenizer = new StringTokenizer(attrs, ",");
		attributes = new String[i];
		i = 0;
		String token = "";
		while (tokenizer.hasMoreTokens()) {
			token = tokenizer.nextToken();
			attributes[i] = token;
			i++;
		}

		return attributes;
	}

	public List getUserGroupGuidsIp(LdapConnection conn, String start,
			int scope, String filter, String attr[]) throws SecurityException {
		List guids = null;
		String guid;
		LdapSearch search = null;
		String[] retAttrs = null;
		Object guidAv;

		retAttrs = attr;
		try {

			guids = new ArrayList();
			search = new LdapSearch();
			search.initialize(conn, start, scope, filter, retAttrs);
			search.execute();

			while (search.next()) {
				guidAv = search.getAttributeValue(retAttrs[0]);
				guid = LdapBasicFns.formatGuid(conn, guidAv);

				guids.add(guid);
			}

			search.release();

		} catch (Exception e) {
			throw new SecurityException(
					SecurityException.ERROR_CAN_NOT_FIND_USER_ATTRIBUTES_LDAP);
		}

		return guids;

	}

	public void changePassword(String login, String newPassword,
			String oldPassword, String entidad) throws SecurityException,
			ValidationException {

	}

	private void validateParameters(String login, String password)
			throws ValidationException {
		try {
			Validator.validate_String_NotNull_WithLength(login, "",	IUSERUSERHDR_MAX_LOGIN_LENGTH);
		} catch (ValidationException vE) {
			throw new ValidationException(
					ValidationException.ERROR_LOGIN_LENGTH);
		}
		try {
			Validator.validate_String_NotNull_WithLength(password, "", IUSERUSERHDR_MAX_PASSWORD_LENGTH);
		} catch (ValidationException vE) {
			throw new ValidationException(
					ValidationException.ERROR_PASSWORD_LENGTH);
		}
	}

	private void validateParameters(String login, String password,
			String password2) throws ValidationException {
		try {
			Validator.validate_String_NotNull_WithLength(login, "",	IUSERUSERHDR_MAX_LOGIN_LENGTH);
		} catch (ValidationException vE) {
			throw new ValidationException(
					ValidationException.ERROR_LOGIN_LENGTH);
		}
		try {
			Validator.validate_String_NotNull_WithLength(password, "", IUSERUSERHDR_MAX_PASSWORD_LENGTH);
		} catch (ValidationException vE) {
			throw new ValidationException(
					ValidationException.ERROR_PASSWORD_LENGTH);
		}
		try {
			Validator.validate_String_NotNull_WithLength(password2, "", IUSERUSERHDR_MAX_PASSWORD_LENGTH);
		} catch (ValidationException vE) {
			throw new ValidationException(
					ValidationException.ERROR_PASSWORD_LENGTH);
		}
	}

	public static boolean isLdap(String entidad) {
		LDAPDef ldapDef = RepositoryLDAP.getInstance(entidad).getLDAPInfo();
		boolean isLdap = false;
		if (ldapDef.getLdapEngine() != 0)
			isLdap = true;
		return isLdap;
	}

	/***************************************************************************
	 * Inner classes
	 **************************************************************************/

	/***************************************************************************
	 * Test brench
	 **************************************************************************/

}