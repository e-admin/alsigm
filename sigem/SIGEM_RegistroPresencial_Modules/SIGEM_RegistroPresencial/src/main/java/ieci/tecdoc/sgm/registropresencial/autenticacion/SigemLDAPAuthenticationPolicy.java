package ieci.tecdoc.sgm.registropresencial.autenticacion;

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
import com.ieci.tecdoc.common.invesdoc.Iuserldapgrphdr;
import com.ieci.tecdoc.common.invesdoc.Iuserldapuserhdr;
import com.ieci.tecdoc.common.invesdoc.Iuserusertype;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.keys.ISicresKeys;
import com.ieci.tecdoc.common.utils.ISicresQueries;
import com.ieci.tecdoc.idoc.authentication.LDAPAuthenticationPolicy;
import com.ieci.tecdoc.idoc.authentication.ldap.LdapBasicFns;
import com.ieci.tecdoc.idoc.authentication.ldap.LdapConnection;
import com.ieci.tecdoc.idoc.authentication.ldap.LdapSearch;
import com.ieci.tecdoc.idoc.decoder.dbinfo.LDAPDef;
import com.ieci.tecdoc.idoc.repository.RepositoryLDAP;
import com.ieci.tecdoc.idoc.utils.CryptoUtils;
import com.ieci.tecdoc.idoc.utils.LDAPRBUtil;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.Validator;

/**
 *
 * @author 66575267
 *
 */
public class SigemLDAPAuthenticationPolicy implements AuthenticationPolicy,
		IDocKeys, ISicresKeys {

	private static final Logger log = Logger
			.getLogger(LDAPAuthenticationPolicy.class);

	public AuthenticationUser validate(String login, String password,
			String entidad) throws SecurityException, ValidationException {
		AuthenticationUser user = null;
		validateParameters(login, password);

		try {
			user = getAuthenticationUser(login, entidad, false);
		} catch (NamingException e) {
			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		} catch (SecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		}

		return user;
	}

	public AuthenticationUser validate(String userDn, String entidad)
			throws SecurityException, ValidationException {
		AuthenticationUser user = null;

		try {
			// String decodedDn = CryptoUtils.getDecodeDn(userDn);
			String decodedDn = userDn;
			user = getAuthenticationUser(decodedDn, entidad, true);
		} catch (NamingException e) {
			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		} catch (SecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		}

		return user;
	}

	public void changePassword(String login, String newPassword,
			String oldPassword, String entidad) throws SecurityException,
			ValidationException {

	}

	private LdapSearch getSearch(String login, LdapConnection conn,
			LDAPDef ldapDef, String[] attributes) throws SecurityException {
		LdapSearch search = null;
		try {
			// busqueda por dn
			String filter = MessageFormat.format(LDAPRBUtil.getInstance(null)
					.getProperty(LDAP_SCOPE_BASE + ldapDef.getLdapEngine()),
					new String[] { login });

			search = getLdapSearch(conn, SearchControls.OBJECT_SCOPE, ldapDef,
					attributes, filter);
			if (!search.next()) {
				// Busqueda por UniqueName
				filter = MessageFormat.format(LDAPRBUtil.getInstance(null)
						.getProperty(
								LDAP_SCOPE_SUBTREE1 + ldapDef.getLdapEngine()),
						new String[] { login });
				search = getLdapSearch(conn, SearchControls.SUBTREE_SCOPE,
						ldapDef, attributes, filter);
				if (!search.next()) {
					// Busqueda por número de cuenta sAMAccountName
					filter = MessageFormat.format(LDAPRBUtil.getInstance(null)
							.getProperty(
									LDAP_SCOPE_SUBTREE2
											+ ldapDef.getLdapEngine()),
							new String[] { login });
					if (!filter.equals("")) {
						search = getLdapSearch(conn,
								SearchControls.SUBTREE_SCOPE, ldapDef,
								attributes, filter);
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

	private LdapSearch getSearchSSO(String dn, LdapConnection conn,
			LDAPDef ldapDef, String[] attributes) throws SecurityException {
		LdapSearch search = null;
		try {
			// busqueda por dn
			String filter = LDAP_SCOPE_BASE_SSO;
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

	private LDAPAuthenticationUser getUserAttributes(LdapSearch search,
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
				log.error("Error getUserAttributes: " + e.getMessage(), e);
				throw new SecurityException(
						SecurityException.ERROR_CAN_NOT_FIND_USER_ATTRIBUTES_LDAP);
			} catch (Exception e) {
				log.error("Error getUserAttributes: " + e.getMessage(), e);
				throw new SecurityException(
						SecurityException.ERROR_CAN_NOT_FIND_USER_ATTRIBUTES_LDAP);
			}
		}

		return ldapAUser;
	}

	private Integer userVerification(LDAPAuthenticationUser attributesUser,
			String entidad) throws SecurityException {

		Integer userId = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			// tran = session.beginTransaction();

			List list = ISicresQueries.getUserLdapUser(session, attributesUser
					.getGuidStringFormat());
			if (list == null || list.isEmpty()) {
				log.error("not exist userguid ["
						+ attributesUser.getGuidStringFormat()
						+ "] Iuserldapuserhdr ");
				throw new SecurityException(
						SecurityException.ERROR_IUSERUSERTYPE_NOT_FOUND);
			} else {
				Iuserldapuserhdr ldapUser = (Iuserldapuserhdr) list.get(0);
				if (log.isDebugEnabled()) {
					log.debug("exist userId Iuserldapuserhdr [" + ldapUser
							+ "] con el log [" + log + "]");
				}
				userId = new Integer(ldapUser.getId());
				list = ISicresQueries.getUserUserType(session,
						ldapUser.getId(), 5);

				// Si no existen valores en iuserusertype, se lanzará una
				// excepción de seguridad
				if (list == null || list.size() == 0) {
					log.error("Not exist userId [" + ldapUser.getId()
							+ "] Iuserusertype ");
					throw new SecurityException(
							SecurityException.ERROR_IUSERUSERTYPE_NOT_FOUND);
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
		}

		return userId;

	}

	private List connectionVerification(LdapConnection conn,
			LDAPAuthenticationUser attributesUser, LDAPDef ldapDef,
			String attributes[], String entidad, List groupList) throws SecurityException {

		List deptList = new ArrayList();

		try {
			int scope = new Integer(LDAPRBUtil.getInstance(null).getProperty(
					LDAP_SCOPEGROUP + ldapDef.getLdapEngine())).intValue();
			List groups = attributesUser.getGroupList();
			if (log.isDebugEnabled()) {
				log.debug("groups [" + groups + "] con el log [" + log + "]");
			}
			String attr[] = { attributes[0] };
			for (int i = 0; i < groups.size(); i++) {
				String group = (String) groups.get(i);
				String filter = LDAPRBUtil.getInstance(null).getProperty(
						LDAP_SCOPE_BASESUBTREE_GROUP + ldapDef.getLdapEngine());
				LdapSearch search = new LdapSearch();
				search.initialize(conn, group, scope, filter, attr);
				search.execute();
				if (search.next()) {
					LDAPAuthenticationUser attributesGroup = getUserAttributes(
							search, attributes);
					attributesGroup.setGuidStringFormat(LdapBasicFns
							.formatGuid(conn, attributesGroup.getGuid()));

					//obtiene los grupos
					Integer idGroup = getGroupUser(attributesGroup.getGuidStringFormat(), entidad);

					if(idGroup != null){
						groupList.add(idGroup);
					}

					//obtiene los departamentos
					Integer deptId = getRegisterDeptOfic(attributesGroup
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

	/**
	 * Metodo que obtiene un array de ids a los que usuario pertenece
	 * @param conn
	 * @param attributesUser
	 * @param ldapDef
	 * @param attributes
	 * @param entidad
	 * @return
	 * @throws SecurityException
	 */
	private List getListGroupOfUser(LdapConnection conn,
			LDAPAuthenticationUser attributesUser, LDAPDef ldapDef,
			String attributes[], String entidad) throws SecurityException {

		List result = new ArrayList();

		try {
			int scope = new Integer(LDAPRBUtil.getInstance(null).getProperty(
					LDAP_SCOPEGROUP + ldapDef.getLdapEngine())).intValue();
			List groups = attributesUser.getGroupList();
			if (log.isDebugEnabled()) {
				log.debug("groups [" + groups + "] con el log [" + log + "]");
			}
			String attr[] = { attributes[0] };
			for (int i = 0; i < groups.size(); i++) {
				String group = (String) groups.get(i);
				String filter = LDAPRBUtil.getInstance(null).getProperty(
						LDAP_SCOPE_BASESUBTREE_GROUP + ldapDef.getLdapEngine());
				LdapSearch search = new LdapSearch();
				search.initialize(conn, group, scope, filter, attr);
				search.execute();
				if (search.next()) {
					LDAPAuthenticationUser attributesGroup = getUserAttributes(
							search, attributes);
					attributesGroup.setGuidStringFormat(LdapBasicFns
							.formatGuid(conn, attributesGroup.getGuid()));

					Integer idGroup = getGroupUser(attributesGroup.getGuidStringFormat(), entidad);

					if(idGroup != null){
						result.add(idGroup);
					}
				}
			}
		} catch (SecurityException e) {
			log.error("Error getListGroupOfUser: " + e.getMessage(), e);
			//throw new SecurityException(
			//		SecurityException.ERROR_PASSWORD_INCORRECT);
			throw e;
		} catch (Exception e) {
			log.error("Error getListGroupOfUser: " + e.getMessage(), e);
			//throw new SecurityException(
			//		SecurityException.ERROR_PASSWORD_INCORRECT);
			throw new SecurityException(
					SecurityException.ERROR_CAN_NOT_FIND_USER_ATTRIBUTES_LDAP);
		}

		return result;
	}

	/**
	 * Metodo que obtiene un array de ids a los que usuario pertenece
	 * @param attributesUser
	 * @param password
	 * @param ldapDef
	 * @param attributes
	 * @param entidad
	 * @return
	 * @throws SecurityException
	 */
	private List getListGroupOfUser(LDAPAuthenticationUser attributesUser,
			String password, LDAPDef ldapDef, String attributes[],
			String entidad) throws SecurityException {

		List result = new ArrayList();
		LdapConnection conn = null;
		try {
			if (log.isDebugEnabled()) {
				log.debug("dn [" + attributesUser.getDn() + "] con el log ["
						+ log + "]");
			}
			conn = new LdapConnection();
			conn.open(ldapDef, ldapDef.getLdapUser(), password, 1);

			int scope = new Integer(LDAPRBUtil.getInstance(null).getProperty(
					LDAP_SCOPEGROUP + ldapDef.getLdapEngine())).intValue();
			String attr[] = { attributes[0] };
			if (ldapDef.getLdapEngine() == 1) {
				List groups = attributesUser.getGroupList();
				if (log.isDebugEnabled()) {
					log.debug(" groups [" + groups + "] con el log [" + log
							+ "]");
				}

				String filter = LDAPRBUtil.getInstance(null).getProperty(
						LDAP_SCOPE_BASESUBTREE_GROUP + ldapDef.getLdapEngine());
				if (log.isDebugEnabled()) {
					log.debug(" filter [" + filter + "] con el log [" + log
							+ "]");
				}

				for (int i = 0; i < groups.size(); i++) {

					String group = (String) groups.get(i);

					LdapSearch search = new LdapSearch();
					search.initialize(conn, group, scope, filter, attr);
					search.execute();
					if (search.next()) {
						LDAPAuthenticationUser attributesGroup = getUserAttributes(
								search, attributes);
						attributesGroup.setGuidStringFormat(LdapBasicFns
								.formatGuid(conn, attributesGroup.getGuid()));

						Integer idGroup = getGroupUser(attributesGroup.getGuidStringFormat(), entidad);

						if(idGroup != null){
							result.add(idGroup);
						}
					}
				}
			} else {
				String filter = MessageFormat.format(
						LDAPRBUtil.getInstance(null).getProperty(
								LDAP_SCOPE_BASESUBTREE_GROUP
										+ ldapDef.getLdapEngine()),
						new String[] { attributesUser.getDn() });
				if (log.isDebugEnabled()) {
					log.debug(" filter [" + filter + "] con el log [" + log
							+ "]");
				}
				List list = getUserGroupGuidsIp(conn, ldapDef.getLdapRoot(),
						scope, filter, attr);
				if (log.isDebugEnabled()) {
					log
							.debug(" groups [" + list + "] con el log [" + log
									+ "]");
				}

				for (int i = 0; i < list.size(); i++) {
					String groupGuid = (String) list.get(i);

					Integer idGroup = getGroupUser(groupGuid, entidad);

					if(idGroup != null){
						result.add(idGroup);
					}
				}
			}
		} catch (SecurityException e) {
			log.error("Error getListGroupOfUser: " + e.getMessage(), e);
			//throw new SecurityException(
			//		SecurityException.ERROR_PASSWORD_INCORRECT);
			throw e;
		} catch (Exception e) {
			log.error("Error getListGroupOfUser: " + e.getMessage(), e);
			//throw new SecurityException(
			//		SecurityException.ERROR_PASSWORD_INCORRECT);
			throw new SecurityException(
					SecurityException.ERROR_CAN_NOT_FIND_USER_ATTRIBUTES_LDAP);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}

		}
		return result;
	}

	/**
	 * Metodo que obtiene el id del grupo mediante su guid
	 * @param guid
	 * @param entidad
	 * @return id
	 * @throws SecurityException
	 */
	public Integer getGroupUser(String guid, String entidad)
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

				result = new Integer(udeptGrpHdr.getId());

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

	private List connectionVerification(LDAPAuthenticationUser attributesUser,
			String password, LDAPDef ldapDef, String attributes[],
			String entidad) throws SecurityException {

		List deptList = new ArrayList();
		LdapConnection conn = null;
		try {
			if (log.isDebugEnabled()) {
				log.debug("dn [" + attributesUser.getDn() + "] con el log ["
						+ log + "]");
			}
			conn = new LdapConnection();
			conn.open(ldapDef, ldapDef.getLdapUser(), password, 1);

			int scope = new Integer(LDAPRBUtil.getInstance(null).getProperty(
					LDAP_SCOPEGROUP + ldapDef.getLdapEngine())).intValue();
			String attr[] = { attributes[0] };
			if (ldapDef.getLdapEngine() == 1) {
				List groups = attributesUser.getGroupList();
				if (log.isDebugEnabled()) {
					log.debug(" groups [" + groups + "] con el log [" + log
							+ "]");
				}

				String filter = LDAPRBUtil.getInstance(null).getProperty(
						LDAP_SCOPE_BASESUBTREE_GROUP + ldapDef.getLdapEngine());
				if (log.isDebugEnabled()) {
					log.debug(" filter [" + filter + "] con el log [" + log
							+ "]");
				}

				for (int i = 0; i < groups.size(); i++) {

					String group = (String) groups.get(i);

					LdapSearch search = new LdapSearch();
					search.initialize(conn, group, scope, filter, attr);
					search.execute();
					if (search.next()) {
						LDAPAuthenticationUser attributesGroup = getUserAttributes(
								search, attributes);
						attributesGroup.setGuidStringFormat(LdapBasicFns
								.formatGuid(conn, attributesGroup.getGuid()));
						Integer deptId = getRegisterDeptOfic(attributesGroup
								.getGuidStringFormat(), entidad);

						if (deptId != null) {
							deptList.add(deptId);
							if (log.isDebugEnabled()) {
								log.debug(" deptId [" + deptId
										+ "] con el log [" + log + "]");
							}
						}
					}
				}
			} else {
				String filter = MessageFormat.format(
						LDAPRBUtil.getInstance(null).getProperty(
								LDAP_SCOPE_BASESUBTREE_GROUP
										+ ldapDef.getLdapEngine()),
						new String[] { attributesUser.getDn() });
				if (log.isDebugEnabled()) {
					log.debug(" filter [" + filter + "] con el log [" + log
							+ "]");
				}
				List list = getUserGroupGuidsIp(conn, ldapDef.getLdapRoot(),
						scope, filter, attr);
				if (log.isDebugEnabled()) {
					log
							.debug(" groups [" + list + "] con el log [" + log
									+ "]");
				}

				for (int i = 0; i < list.size(); i++) {
					String groupGuid = (String) list.get(i);
					Integer deptId = getRegisterDeptOfic(groupGuid, entidad);

					if (deptId != null) {
						if (log.isDebugEnabled()) {
							log.debug(" deptId [" + deptId + "] con el log ["
									+ log + "]");
						}
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
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}

		}
		return deptList;
	}

	private Integer getRegisterDeptOfic(String guid, String entidad)
			throws SecurityException {
		Integer deptId = null;
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

				// Obtener el departamento invesdoc asociado al grupo ldap
				list = ISicresQueries.getUserDeptHdr(session, udeptGrpHdr
						.getId());
				if (list != null && !list.isEmpty()) {
					Iuserdepthdr udeptHdr = (Iuserdepthdr) list.get(0);
					if (log.isDebugEnabled()) {
						log.debug(" Iuserdepthdr [" + udeptHdr
								+ "] con el log [" + log + "]");
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

	private String[] parseAttributes(String attrs) {
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

	private List getUserGroupGuidsIp(LdapConnection conn, String start,
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

	private void validateParameters(String login, String password)
			throws ValidationException {
		try {
			Validator.validate_String_NotNull_WithLength(login, "",
					IUSERUSERHDR_MAX_LOGIN_LENGTH);
		} catch (ValidationException vE) {
			throw new ValidationException(
					ValidationException.ERROR_LOGIN_LENGTH);
		}
		try {
			Validator.validate_String_NotNull_WithLength(password, "",
					IUSERUSERHDR_MAX_PASSWORD_LENGTH);
		} catch (ValidationException vE) {
			throw new ValidationException(
					ValidationException.ERROR_PASSWORD_LENGTH);
		}
	}

	private List getUserDeptListVerification(Integer userId,
			LDAPAuthenticationUser attributesUser, String password,
			LDAPDef ldapDef, String attributes[], String entidad)
			throws SecurityException {

		List deptListLDAP = connectionVerification(attributesUser, password,
				ldapDef, attributes, entidad);

		List deptList = getUserDeptList(userId, entidad, deptListLDAP);

		return deptList;

	}

	private List getUserDeptList(Integer userId, String entidad,
			List deptListLDAP) throws SecurityException {

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
			log.error("Impossible to load other offices for user [" + userId
					+ "]", e);

			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		}
	}

	private AuthenticationUser getAuthenticationUser(String loginOrDecodedDn,
			String entidad, boolean isSSO) throws SecurityException, Exception {
		LDAPDef ldapDef = RepositoryLDAP.getInstance(entidad).getLDAPInfo();
		if (log.isDebugEnabled()) {
			log.debug("LDAPDef [" + ldapDef + "] con el log [" + log + "]");
		}
		if (ldapDef.getLdapEngine() == 0) {
			throw new SecurityException(
					SecurityException.ERROR_AUTHENTICATION_POLICY_NOTFOUND);
		}

		String dn = ldapDef.getLdapUser();
		String attrID = LDAPRBUtil.getInstance(null).getProperty(
				LDAP_ATTRIBUTES + ldapDef.getLdapEngine());
		String attributes[] = parseAttributes(attrID);
		String passwordDecrypt = CryptoUtils.decryptPasswordLDAP(ldapDef
				.getLdapPassword());

		LdapConnection conn = new LdapConnection();
		try{
			conn.open(ldapDef, dn, passwordDecrypt, 1);

			LdapSearch search = null;
			if (isSSO) {
				search = getSearchSSO(loginOrDecodedDn, conn, ldapDef, attributes);
			} else {
				search = getSearch(loginOrDecodedDn, conn, ldapDef, attributes);
			}

			LDAPAuthenticationUser attributesUser = getUserAttributes(search,
					attributes);
			attributesUser.setGuidStringFormat(LdapBasicFns.formatGuid(conn,
					attributesUser.getGuid()));
			if (log.isDebugEnabled()) {
				log.debug("attributesUser [" + attributesUser + "] con el log ["
						+ log + "]");
			}

			Integer userId = userVerification(attributesUser, entidad);

			List deptList = null;
			List groupList = new ArrayList();
			if (isSSO) {
				//obtenemos los grupos a los que pertenece el usuario
				deptList = connectionVerification(conn, attributesUser, ldapDef,
						attributes, entidad, groupList);

				deptList = getUserDeptList(userId, entidad, deptList);
			} else {
				deptList = getUserDeptListVerification(userId, attributesUser,
						passwordDecrypt, ldapDef, attributes, entidad);
				//obtenemos los grupos a los que pertenece el usuario
				groupList = getListGroupOfUser(attributesUser, passwordDecrypt,
						ldapDef, attributes, entidad);
			}


			AuthenticationUser user = new AuthenticationUser();

			Integer deptId = null;
			if (deptList != null && deptList.size() > 0) {
				deptId = (Integer) deptList.get(0);
				user.setDeptList(deptList);
			}

			user.setId(userId);
			user.setName(attributesUser.getFullName());
			user.setDeptid(deptId);
			user.setDeptIdOriginal(deptId);
			user.setGroupList(groupList);

			return user;

		}finally{
			try{
				conn.close();
			}catch(Exception e){
				if(log.isDebugEnabled()){
					log.debug("Error al cerrar conexión LDAP", e);
				}
			}
		}

	}

	private LdapSearch getLdapSearch(LdapConnection conn, int scope,
			LDAPDef ldapDef, String[] attributes, String filter)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("filter [" + filter + "] con el log [" + log + "]");
		}
		LdapSearch search = new LdapSearch();
		search.initialize(conn, ldapDef.getLdapRoot(), scope, filter,
				attributes);
		search.execute();

		return search;
	}

}
