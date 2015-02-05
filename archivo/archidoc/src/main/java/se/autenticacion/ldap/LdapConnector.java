package se.autenticacion.ldap;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.core.ldap.LdapAttribute;
import ieci.tecdoc.core.ldap.LdapBasicFns;
import ieci.tecdoc.core.ldap.LdapConnection;
import ieci.tecdoc.core.ldap.LdapError;
import ieci.tecdoc.core.ldap.LdapFilter;
import ieci.tecdoc.core.ldap.LdapScope;
import ieci.tecdoc.core.ldap.LdapSearch;
import ieci.tecdoc.core.ldap.LdapUasFns;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.security.auth.login.LoginException;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

import se.LdapConstants;
import se.autenticacion.AuthenticationConnector;
import se.autenticacion.AuthenticationConnectorException;
import se.autenticacion.UserInfoImpl;
import xml.config.ConfiguracionArchivoManager;

import common.Constants;
import common.model.UserInfo;
import common.util.StringUtils;
import common.util.XmlFacade;

import es.archigest.framework.facilities.security.SecurityConstants;
import es.archigest.framework.facilities.security.exceptions.UnknownUserException;
import es.archigest.framework.facilities.security.exceptions.UnmanagedLoginException;
import es.archigest.framework.facilities.security.exceptions.WrongPasswordException;

public class LdapConnector implements AuthenticationConnector {

	private int engine;
	private int provider;
	private String url;
	private boolean pooling;
	private int poolTimeout;
	private String userStart;
	private int userScope;
	private String userAttribute;
	private String userDefault;
	private String passwordDefault;

	/** Logger de la clase. */
	protected static final Logger logger = Logger
			.getLogger(LdapConnector.class);

	/**
	 * Inicializa con los parámetros de configuración.
	 * 
	 * @param params
	 *            Parámetros de configuración.
	 * @throws AuthenticationConnectorException
	 *             si ocurre algún error.
	 */
	public void initialize(Properties params)
			throws AuthenticationConnectorException {

		this.engine = Integer.parseInt(params.get(LdapConstants.ENGINE)
				.toString());
		this.provider = Integer.parseInt(params.get(LdapConstants.PROVIDER)
				.toString());
		this.url = params.get(LdapConstants.URL).toString();
		this.pooling = Constants.TRUE_STRING.equalsIgnoreCase(params.get(
				LdapConstants.POOLING).toString());
		this.poolTimeout = Integer.parseInt(params.get(
				LdapConstants.POOLING_TIMEOUT).toString());
		this.userStart = params.get(LdapConstants.USER_START).toString();
		this.userScope = Integer.parseInt(params.get(LdapConstants.USER_SCOPE)
				.toString());
		this.userAttribute = params.get(LdapConstants.USER_ATTRIBUTE)
				.toString();
		this.userDefault = params.get(LdapConstants.USER_DEFAULT).toString();
		this.passwordDefault = params.get(LdapConstants.PASSWORD_DEFAULT)
				.toString();

	}

	private String authenticateUserConnect(String userName)
			throws LoginException {

		String userDn = null;
		LdapConnection ldapConnection = new LdapConnection();

		try {

			// Abrir una conexión con el usuario de conexión
			ldapConnection.open(engine, provider, url, userDefault,
					passwordDefault, pooling, poolTimeout);

			try {
				userDn = LdapUasFns.findUserDn(ldapConnection, this.userStart,
						this.userScope, this.userAttribute, userName);
			} catch (IeciTdException ieciEx) {
				if (LdapError.EC_NOT_FOUND.equals(ieciEx.getErrorCode())) {
					UnknownUserException ex = new UnknownUserException(
							SecurityConstants.USERNAME);
					ex.setContextValue(SecurityConstants.USERNAME, userName);
					throw ex;
				} else
					throw new UnmanagedLoginException(
							ieciEx.getLocalizedMessage());
			}
		} catch (IeciTdException e) {
			if (LdapError.EC_NOT_FOUND.equals(e.getErrorCode())) {
				UnknownUserException ex = new UnknownUserException(
						SecurityConstants.USERNAME);
				ex.setContextValue(SecurityConstants.USERNAME, userDefault);
				throw ex;
			} else if (LdapError.EC_INVALID_AUTH_SPEC.equals(e.getErrorCode()))
				throw new WrongPasswordException(userDefault, passwordDefault);
			else
				throw new UnmanagedLoginException(e.getLocalizedMessage());
		} catch (Exception e) {
			throw new UnmanagedLoginException(e.getLocalizedMessage());
		} finally {
			try {
				ldapConnection.close();
			} catch (Exception e) {
			}
		}

		return userDn;
	}

	public String authenticate(String userName, String password, String ip)
			throws LoginException {

		String userGuid = null;
		LdapConnection ldapConnection = new LdapConnection();
		try {

			if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password))
				throw new IeciTdException("IECI_TECDOC_UAS_INVALID_AUTH_SPEC",
						"Invalid authorization specification (user / password)");

			// Autenticar al usuario de conexión
			String userDn = authenticateUserConnect(userName);
			if (userDn != null) {

				// Autenticar al usuario
				ldapConnection.open(engine, provider, url, userDn, password,
						pooling, poolTimeout);

				// Obtener su guid
				userGuid = LdapUasFns.findUserGuidByDn(ldapConnection, userDn);
				ldapConnection.close();
			}
		} catch (IeciTdException e) {
			if (LdapError.EC_NOT_FOUND.equals(e.getErrorCode())) {
				UnknownUserException ex = new UnknownUserException(
						SecurityConstants.USERNAME);
				ex.setContextValue(SecurityConstants.USERNAME, userName);
				throw ex;
			} else if (LdapError.EC_INVALID_AUTH_SPEC.equals(e.getErrorCode()))
				throw new WrongPasswordException(userName, password);
			else
				throw new UnmanagedLoginException(e.getLocalizedMessage());
		} catch (Exception e) {
			throw new UnmanagedLoginException(e.getLocalizedMessage());
		}
		return userGuid;
	}

	private List findUsers(LdapConnection conn, String start, int scope,
			String attrName, String attrSurName, String attrMail,
			String attrValue) throws Exception {

		String filter = "";
		filter = addOrFilter(generateFilter(attrName, attrValue),
				generateFilter(attrSurName, attrValue));
		filter = addOrFilter(generateFilter(attrMail, attrValue), filter);
		filter = addAndFilter(LdapFilter.getUserFilter(conn), filter);

		List users = new ArrayList();
		LdapSearch search = null;

		Node node = getLdapFileAttributes();

		String descriptionAttributeName = getDescriptionAttribute(node, conn);
		String guidAttributeName = LdapAttribute.getGuidAttributeName(conn);
		String nameAttributeName = getNameAttribute(node, conn);
		String surnameAttributeName = getSurnameAttribute(node, conn);
		String mailAttributeName = getMailAttribute(node, conn);
		String addressAttributeName = getAddressAttribute(node, conn);
		String retAttrs[] = { descriptionAttributeName, nameAttributeName,
				surnameAttributeName, guidAttributeName, mailAttributeName,
				addressAttributeName };

		int maxCount = 0;
		try {
			search = new LdapSearch();
			search.initialize(conn, start, scope, filter, retAttrs, maxCount);
			search.execute();

			Object objDescription;
			Object objName;
			Object objSurname;
			Object objUid;
			Object objMail;
			Object objAddress;

			while (search.next()) {
				String uid = null;
				String description = null;
				String name = null;
				String surname = null;
				String mail = null;
				String address = null;

				objUid = search.getAttributeValue(guidAttributeName);
				uid = LdapBasicFns.formatGuid(conn, objUid);

				if (search.getAttributeValueCount(descriptionAttributeName) > 0) {
					objDescription = search
							.getAttributeValue(descriptionAttributeName);
					description = objDescription.toString();
				}

				if (search.getAttributeValueCount(nameAttributeName) > 0) {
					objName = search.getAttributeValue(nameAttributeName);
					name = objName.toString();
				}

				if (search.getAttributeValueCount(surnameAttributeName) > 0) {
					objSurname = search.getAttributeValue(surnameAttributeName);
					surname = objSurname.toString();
				}

				if (search.getAttributeValueCount(mailAttributeName) > 0) {
					objMail = search.getAttributeValue(mailAttributeName);
					mail = objMail.toString();
				}

				if (search.getAttributeValueCount(addressAttributeName) > 0) {
					objAddress = search.getAttributeValue(addressAttributeName);
					address = objAddress.toString();
				}

				users.add(new UserInfoImpl(uid, uid, address, // "address"
						mail, // "email"
						name, surname, description));
			}
			search.release();
		} catch (Exception e) {
			/*
			 * File fichero = new File ("c:\\errorldapfindusers.txt");
			 * BufferedWriter out = new BufferedWriter(new FileWriter(fichero));
			 * out.write("ex.message:" + e.getMessage()); out.write("ex.cause:"
			 * + e.getCause()); out.write("ex.stacktrace:" + e.getStackTrace());
			 * out.close();
			 */
			LdapSearch.ensureRelease(search, e);
		}
		return users;
	}

	private List findUsers(LdapConnection conn, String start, int scope,
			String guidValue) throws Exception {
		String guidAttributeName = LdapAttribute.getGuidAttributeName(conn);
		// Formatear el guid en binario si es necesario (Active Directory)
		guidValue = LdapBasicFns.formatGuid(conn, guidValue);

		String filter = "";
		filter = generateFilter(guidAttributeName, guidValue);
		filter = addAndFilter(LdapFilter.getUserFilter(conn), filter);

		List users = new ArrayList();
		LdapSearch search = null;

		Node node = getLdapFileAttributes();

		String descriptionAttributeName = getDescriptionAttribute(node, conn);
		String nameAttributeName = getNameAttribute(node, conn);
		String surnameAttributeName = getSurnameAttribute(node, conn);
		String mailAttributeName = getMailAttribute(node, conn);
		String addressAttributeName = getAddressAttribute(node, conn);
		String retAttrs[] = { descriptionAttributeName, nameAttributeName,
				surnameAttributeName, guidAttributeName, mailAttributeName,
				addressAttributeName };

		int maxCount = 0;
		try {
			search = new LdapSearch();
			search.initialize(conn, start, scope, filter, retAttrs, maxCount);
			search.execute();

			Object objDescription;
			Object objName;
			Object objSurname;
			Object objUid;
			Object objMail;
			Object objAddress;

			while (search.next()) {
				String uid = null;
				String description = null;
				String name = null;
				String surname = null;
				String mail = null;
				String address = null;

				objUid = search.getAttributeValue(guidAttributeName);
				uid = LdapBasicFns.formatGuid(conn, objUid);

				if (search.getAttributeValueCount(descriptionAttributeName) > 0) {
					objDescription = search
							.getAttributeValue(descriptionAttributeName);
					description = objDescription.toString();
				}

				if (search.getAttributeValueCount(nameAttributeName) > 0) {
					objName = search.getAttributeValue(nameAttributeName);
					name = objName.toString();
				}

				if (search.getAttributeValueCount(surnameAttributeName) > 0) {
					objSurname = search.getAttributeValue(surnameAttributeName);
					surname = objSurname.toString();
				}

				if (search.getAttributeValueCount(mailAttributeName) > 0) {
					objMail = search.getAttributeValue(mailAttributeName);
					mail = objMail.toString();
				}

				if (search.getAttributeValueCount(addressAttributeName) > 0) {
					objAddress = search.getAttributeValue(addressAttributeName);
					address = objAddress.toString();
				}

				users.add(new UserInfoImpl(uid, uid, address, // "address"
						mail, // "email"
						name, surname, description));
			}
			search.release();
		} catch (Exception e) {
			/*
			 * File fichero = new File ("c:\\errorldapfindusers.txt");
			 * BufferedWriter out = new BufferedWriter(new FileWriter(fichero));
			 * out.write("ex.message:" + e.getMessage()); out.write("ex.cause:"
			 * + e.getCause()); out.write("ex.stacktrace:" + e.getStackTrace());
			 * out.close();
			 */
			LdapSearch.ensureRelease(search, e);
		}
		return users;
	}

	public List findUserByName(String userName) {

		LdapConnection ldapConnection = new LdapConnection();
		List list = null;
		try {

			if (StringUtils.isEmpty(userName))
				throw new IeciTdException("IECI_TECDOC_UAS_INVALID_AUTH_SPEC",
						"Invalid authorization specification (user / password)");
			ldapConnection.open(engine, provider, url, userDefault,
					passwordDefault, pooling, poolTimeout);

			Node node = getLdapFileAttributes();

			String nameAttributeName = getNameAttribute(node, ldapConnection);
			String surnameAttributeName = getSurnameAttribute(node,
					ldapConnection);
			String mailAttributeName = getMailAttribute(node, ldapConnection);

			userName = "*" + userName + "*";
			list = findUsers(ldapConnection, userStart, userScope,
					nameAttributeName, surnameAttributeName, mailAttributeName,
					userName);

		} catch (Exception ex) {
			/*
			 * try { File fichero = new File ("c:\\errorldapfind.txt");
			 * BufferedWriter out = new BufferedWriter(new FileWriter(fichero));
			 * out.write("ex.message:" + ex.getMessage()); out.write("ex.cause:"
			 * + ex.getCause()); out.write("ex.stacktrace:" +
			 * ex.getStackTrace()); out.close(); } catch (IOException e) {
			 * 
			 * }
			 */
			logger.error("Error al buscar el usuario: " + userName, ex);
			throw new AuthenticationConnectorException(ex, this.getClass()
					.getName(), "Error al buscar el usuario: " + userName);
		}
		return list;

	}

	public UserInfo getUserInfo(String idUser) {
		LdapConnection ldapConnection = new LdapConnection();
		List list = null;
		try {

			if (StringUtils.isEmpty(idUser))
				throw new IeciTdException("IECI_TECDOC_UAS_INVALID_AUTH_SPEC",
						"Invalid authorization specification (user / password)");
			ldapConnection.open(engine, provider, url, userDefault,
					passwordDefault, pooling, poolTimeout);

			list = findUsers(ldapConnection, userStart, LdapScope.SUBTREE,
					idUser);
		} catch (Exception ex) {
			/*
			 * try { File fichero = new File ("c:\\errorldapfind.txt");
			 * BufferedWriter out = new BufferedWriter(new FileWriter(fichero));
			 * out.write("ex.message:" + ex.getMessage()); out.write("ex.cause:"
			 * + ex.getCause()); out.write("ex.stacktrace:" +
			 * ex.getStackTrace()); out.close(); } catch (IOException e) {
			 * 
			 * }
			 */
			logger.error("Error al buscar el usuario con guid: " + idUser, ex);
			throw new AuthenticationConnectorException(ex, this.getClass()
					.getName(), "Error al buscar el usuario con guid: "
					+ idUser);
		}
		if ((list != null) && (!list.isEmpty()))
			return (UserInfo) list.get(0);
		else
			return null;
	}

	private String getNameAttribute(Node node, LdapConnection conn) {
		String nameAttribute = null;
		if (node != null) {
			Node nameNode = XmlFacade.getNode(node, LdapConstants.NAME);

			if (nameNode != null
					&& nameNode.getFirstChild() != null
					&& !StringUtils.isEmpty(nameNode.getFirstChild()
							.getNodeValue()))
				nameAttribute = nameNode.getFirstChild().getNodeValue();
			else
				nameAttribute = LdapAttributes.getNameAttributeName(conn);
		} else
			nameAttribute = LdapAttributes.getNameAttributeName(conn);

		return nameAttribute;
	}

	private String getSurnameAttribute(Node node, LdapConnection conn) {
		String surnameAttribute = null;
		if (node != null) {
			Node surnameNode = XmlFacade.getNode(node, LdapConstants.SURNAME);

			if (surnameNode != null
					&& surnameNode.getFirstChild() != null
					&& !StringUtils.isEmpty(surnameNode.getFirstChild()
							.getNodeValue()))
				surnameAttribute = surnameNode.getFirstChild().getNodeValue();
			else
				surnameAttribute = LdapAttributes.getSurnameAttributeName(conn);
		} else
			surnameAttribute = LdapAttributes.getSurnameAttributeName(conn);

		return surnameAttribute;
	}

	private String getMailAttribute(Node node, LdapConnection conn) {
		String mailAttribute = null;
		if (node != null) {
			Node mailNode = XmlFacade.getNode(node, LdapConstants.MAIL);

			if (mailNode != null
					&& mailNode.getFirstChild() != null
					&& !StringUtils.isEmpty(mailNode.getFirstChild()
							.getNodeValue()))
				mailAttribute = mailNode.getFirstChild().getNodeValue();

			else
				mailAttribute = LdapAttributes.getMailAttributeName(conn);
		} else
			mailAttribute = LdapAttributes.getMailAttributeName(conn);

		return mailAttribute;
	}

	private String getAddressAttribute(Node node, LdapConnection conn) {
		String addressAttribute = null;
		if (node != null) {
			Node addressNode = XmlFacade.getNode(node, LdapConstants.ADDRESS);

			if (addressNode != null
					&& addressNode.getFirstChild() != null
					&& !StringUtils.isEmpty(addressNode.getFirstChild()
							.getNodeValue()))
				addressAttribute = addressNode.getFirstChild().getNodeValue();

			else
				addressAttribute = LdapAttributes.getAddressAttributeName(conn);
		} else
			addressAttribute = LdapAttributes.getAddressAttributeName(conn);

		return addressAttribute;
	}

	private String getDescriptionAttribute(Node node, LdapConnection conn) {
		String descriptionAttribute = null;

		if (node != null) {
			Node descriptionNode = XmlFacade.getNode(node,
					LdapConstants.DESCRIPTION);

			if (descriptionNode != null
					&& descriptionNode.getFirstChild() != null
					&& !StringUtils.isEmpty(descriptionNode.getFirstChild()
							.getNodeValue()))
				descriptionAttribute = descriptionNode.getFirstChild()
						.getNodeValue();

			else
				descriptionAttribute = LdapAttributes
						.getDescriptionAttributeName(conn);
		} else
			descriptionAttribute = LdapAttributes
					.getDescriptionAttributeName(conn);

		return descriptionAttribute;
	}

	private Node getLdapFileAttributes() {

		Node node = null;
		try {

			// String
			// xmlAttributes=FileHelper.getTextFileContent(fileUserAttributes);
			// XmlFacade xmlFacade = new XmlFacade(xmlAttributes);
			XmlFacade xmlFacade = ConfiguracionArchivoManager.getInstance()
					.getLdapUserAttibutes();

			String path = Constants.SLASH + LdapConstants.ATTRIBUTES;

			NodeIterator attributes = XmlFacade.getNodeIterator(
					xmlFacade.getDocument(), path);
			node = attributes.nextNode();
		} catch (Exception ex) {
		}
		return node;
	}

	private String generateFilter(String attrName, String attrValue) {

		String newFilter = "";

		newFilter += "(";
		newFilter += attrName + "=" + attrValue;
		newFilter += ")";

		return newFilter;

	}

	private String addAndFilter(String filter, String filterToAdd) {
		String newFilter = filter;
		if (newFilter == null)
			newFilter = "";

		newFilter = "(&" + newFilter;
		newFilter += filterToAdd;
		newFilter += ")";

		return newFilter;

	}

	private String addOrFilter(String filter, String filterToAdd) {
		String newFilter = filter;
		if (newFilter == null)
			newFilter = "";

		newFilter = "(|" + newFilter;
		newFilter += filterToAdd;
		newFilter += ")";

		return newFilter;

	}
}
