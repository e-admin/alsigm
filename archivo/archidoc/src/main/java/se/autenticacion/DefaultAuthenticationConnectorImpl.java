package se.autenticacion;

import ieci.core.base64.Base64Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.security.auth.login.LoginException;

import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Node;

import xml.config.ConfiguracionArchivoManager;

import common.Constants;
import common.model.UserInfo;
import common.util.StringUtils;

/**
 * Implementación del interfaz AuthenticationConnector para que obtenga los
 * datos del xml de usuarios.
 */
public class DefaultAuthenticationConnectorImpl implements
		AuthenticationConnector {
	/** Logger de la clase. */
	protected static final Logger logger = Logger
			.getLogger(DefaultAuthenticationConnectorImpl.class);

	/** Mapa de usuarios. */
	private static HashMap usuarios = null;

	/**
	 * Constructor.
	 */
	public DefaultAuthenticationConnectorImpl() {
	}

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
		//if (usuarios == null) {
			// SAXReader saxReader = new SAXReader();
			// Document sisUsuariosDOM = saxReader.read(getClass()
			// .getClassLoader().getResource("test/sistema_gestor_usuarios.xml"));

			Document sisUsuariosDOM;
			sisUsuariosDOM = ConfiguracionArchivoManager.getInstance()
					.getDefaultSistemaGestorAutenticacion();

			usuarios = new HashMap();
			List listaUsuarios = sisUsuariosDOM
					.selectNodes("/sistema_gestor_usuarios/usuario");
			for (Iterator i = listaUsuarios.iterator(); i.hasNext();) {
				Node unUsuarioDOM = (Node) i.next();
				String externalUserId = unUsuarioDOM.valueOf("externalUserId");
				String organizationUserId = unUsuarioDOM
						.valueOf("organizationUserId");
				String address = unUsuarioDOM.valueOf("address");
				String email = unUsuarioDOM.valueOf("email");
				String name = unUsuarioDOM.valueOf("name");
				String surname = unUsuarioDOM.valueOf("surname");

				// Nuevos valores
				String username = unUsuarioDOM.valueOf("username");
				String password = unUsuarioDOM.valueOf("password");

				/*
				 * usuarios.put(externalUserId, new UserInfoImpl(externalUserId,
				 * organizationUserId, address,email,name,surname,null));
				 */

				usuarios.put(externalUserId, new UserInfoImpl(externalUserId,
						organizationUserId, address, email, name, surname,
						username + Constants.ARROBA + password));

			//}
		}
	}

	/**
	 * Realiza la autenticación del usuario.
	 *
	 * @param username
	 *            Nombre del usuario.
	 * @param password
	 *            Clave del usuario.
	 * @param ip
	 *            Dirección IP del usuario.
	 * @return Identificador único del usuario.
	 * @throws LoginException
	 *             si se produce algún error.
	 */
	public String authenticate(String username, String password, String ip)
			throws LoginException {
		logger.warn("autenticaci\u00F3n del usuario ["
				+ username + "] !!!");

		String ret = null;
		List usuarios = findUserByUsername(username);

		if (usuarios != null && usuarios.size() > 0) {
			Iterator it = usuarios.iterator();
			while (it.hasNext()) {
				// Ahora autenticamos x nombre de usuario y contraseña
				// codificada
				UserInfo usuario = (UserInfo) it.next();
				String usuarioDesc = usuario.getDescription();
				if (StringUtils.isNotEmpty(usuarioDesc)) {
					String[] usernamePassword = usuarioDesc
							.split(Constants.ARROBA);
					if (usernamePassword != null
							&& usernamePassword.length >= 1) {
						String pwd = Constants.STRING_EMPTY;
						if (usernamePassword.length > 1) {
							pwd = usernamePassword[1];
						}

						// Codificamos la password introducida por el usuario
						// para compararla con la del xml
						String encryptedPwd = Base64Util.encodeString(password);
						if (encryptedPwd.equals(pwd))
							ret = usuario.getOrganizationUserId();
					}
				}
			}
		}
		return ret;
		// return username;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * se.autenticacion.AuthenticationConnector#getUserInfo(java.lang.String)
	 */
	public UserInfo getUserInfo(String idUser) throws NotImplementedException {
		return (UserInfo) usuarios.get(idUser);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * se.autenticacion.AuthenticationConnector#findUserByName(java.lang.String)
	 */
	public List findUserByName(String name) throws NotImplementedException {
		List ret = new ArrayList();
		for (Iterator itUsuarios = usuarios.keySet().iterator(); itUsuarios
				.hasNext();) {
			String usuarioKey = (String) itUsuarios.next();
			UserInfo usuario = (UserInfo) usuarios.get(usuarioKey);
			if (usuario.getName().toLowerCase().indexOf(name.toLowerCase()) >= 0
					|| usuario.getSurname().toLowerCase()
							.indexOf(name.toLowerCase()) >= 0)
				ret.add(usuario);
		}
		return ret.size() > 0 ? ret : null;
	}

	public List findUserByUsername(String username)
			throws NotImplementedException {
		List ret = new ArrayList();
		for (Iterator itUsuarios = usuarios.keySet().iterator(); itUsuarios
				.hasNext();) {
			String usuarioKey = (String) itUsuarios.next();
			UserInfo usuario = (UserInfo) usuarios.get(usuarioKey);
			// if
			// (usuario.getName().toLowerCase().indexOf(username.toLowerCase())
			// >= 0)
			// Nuevo para autenticación username - password
			String usuarioDesc = usuario.getDescription();
			if (StringUtils.isNotEmpty(usuarioDesc)) {
				String[] usernamePassword = usuarioDesc.split(Constants.ARROBA);
				if (usernamePassword != null && usernamePassword.length >= 1)
					if (usernamePassword[0].equals(username))
						ret.add(usuario);
			}
		}
		return ret.size() > 0 ? ret : null;
	}
}
