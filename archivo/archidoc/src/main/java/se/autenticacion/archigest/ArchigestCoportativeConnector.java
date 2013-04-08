package se.autenticacion.archigest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.security.auth.login.LoginException;

import org.apache.log4j.Logger;

import se.autenticacion.AuthenticationConnector;
import se.autenticacion.AuthenticationConnectorException;

import common.model.UserInfo;
import common.util.StringUtils;

import es.archigest.framework.facilities.security.SecurityConstants;
import es.archigest.framework.facilities.security.SecurityLevel;
import es.archigest.framework.facilities.security.exceptions.ArchigestLoginException;
import es.archigest.framework.facilities.security.exceptions.InsufficientDataException;
import es.archigest.framework.facilities.security.exceptions.UnmanagedLoginException;
import es.archigest.framework.modules.security.corp.client.ArchigestCorporativeLoginModule;
import es.archigest.framework.modules.security.corp.ws.LoginExceptionMarshaller;
import es.archigest.framework.modules.security.corp.ws.LoginRequest;
import es.archigest.framework.modules.security.corp.ws.LoginResponse;
import es.archigest.framework.modules.security.corp.ws.SecurityServerRemoteInterfaceProxy;
import es.archigest.framework.modules.security.corp.ws.WSAdapter;
import es.archigest.framework.modules.security.corp.ws.WSException;

/**
 * Implementación del interfaz AuthenticationConnector para la autenticación de
 * usuarios contra el módulo común de autenticación
 */
public class ArchigestCoportativeConnector implements AuthenticationConnector {

	/** Logger de la clase. */
	protected static final Logger logger = Logger
			.getLogger(ArchigestCoportativeConnector.class);

	/** Proxy de acceso al servicio web de autenticación. */
	protected SecurityServerRemoteInterfaceProxy securityServerProxy = null;

	/** Opciones del módulo de autenticación. */
	protected Map options = new HashMap();

	/**
	 * Constructor.
	 */
	public ArchigestCoportativeConnector() {
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
		try {
			// Establece las opciones del módulo de autenticación
			options.putAll(params);

			// URL del servicio web de autenticación
			String wsUrl = params
					.getProperty(ArchigestCorporativeLoginModule.WS_URL);
			if (logger.isDebugEnabled())
				logger.debug("URL del Servicio Web de Autenticaci\u00F3n: "
						+ wsUrl);

			// Creación del proxy
			securityServerProxy = new SecurityServerRemoteInterfaceProxy(wsUrl);
		} catch (Exception e) {
			logger.error("Error inicializando el conector.", e);
			throw new AuthenticationConnectorException(e, this.getClass()
					.getName(), "Error inicializando el conector.");
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
		logger.info("Autenticando al usuario [" + username + "@" + ip + "]");

		if (StringUtils.isBlank(username)) {
			logger.info("El usuario est\u00E1 vac\u00EDo");
			throw new InsufficientDataException("USUARIO");
		}

		LoginResponse response = null;

		try {
			options.put(SecurityConstants.ACTOR_TYPE,
					SecurityConstants.CITIZEN_ACTOR);
			options.put(SecurityConstants.LOGIN_LEVEL,
					SecurityLevel.level1.getName());
			options.put(SecurityConstants.CHANNEL,
					SecurityConstants.PORTAL_CHANNEL);

			options.put(SecurityConstants.IP, ip);
			options.put(SecurityConstants.USERNAME, username);
			options.put(SecurityConstants.NIFNIE, username);
			options.put(SecurityConstants.PASSWORD, password);
			// options.put("javax.servlet.request.X509Certificate",
			// certCb.getEncodedCertificate());

			LoginRequest loginRequest = WSAdapter.getInstance()
					.hashMap2LoginRequest(options);
			response = securityServerProxy.login(loginRequest);
		} catch (Exception e) {
			logger.warn("Error en la autenticaci\u00F3n del usuario "
					+ username, e);
			throw new UnmanagedLoginException(e.getLocalizedMessage());
		}

		// Comprobar si hay error
		WSException ex = response.getException();
		if (ex != null) {
			ArchigestLoginException ple = LoginExceptionMarshaller
					.unmarshall(ex);
			if (ple != null)
				throw ple;
			else
				throw new UnmanagedLoginException(
						getClass(),
						"procesando excepciones recibidas de modulo de autentificacion",
						"excepcion desconocida");
		}

		if (logger.isInfoEnabled())
			logger.info("Usuario [" + username + "] autenticado: "
					+ response.getIdentifier().getValue());

		return response.getIdentifier().getValue();
	}

	/**
	 * Obtiene la informacion acerca del usuario.
	 * 
	 * @param idUser
	 *            Identificador del usuario.
	 * @return Información del usuario.
	 */
	public UserInfo getUserInfo(String idUser) {
		return null;
	}

	/**
	 * Busqueda del usuario por nombre. Funcionara a modo de LIKE.
	 * 
	 * @param username
	 *            Nombre del usuario.
	 * @return Lista de usuarios.
	 */
	public List findUserByName(String username) {
		return new ArrayList();
	}

}
