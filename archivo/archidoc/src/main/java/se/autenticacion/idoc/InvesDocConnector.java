package se.autenticacion.idoc;

import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.sbo.uas.base.UasAuthToken;
import ieci.tecdoc.sbo.uas.base.UasBaseError;
import ieci.tecdoc.sbo.uas.std.UasBnoAuthEx;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.security.auth.login.LoginException;

import org.apache.log4j.Logger;

import se.autenticacion.AuthenticationConnector;
import se.autenticacion.AuthenticationConnectorException;
import se.autenticacion.UserInfoImpl;
import se.autenticacion.idoc.api.UasBnoUser;
import se.autenticacion.idoc.api.UasDaoUserRecO;
import util.MultiEntityUtil;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.MultiEntityConstants;
import common.exceptions.UserLockedLoginException;
import common.model.UserInfo;
import common.util.StringUtils;
import common.util.TypeConverter;

import es.archigest.framework.facilities.security.SecurityConstants;
import es.archigest.framework.facilities.security.exceptions.InsufficientDataException;
import es.archigest.framework.facilities.security.exceptions.UnknownUserException;
import es.archigest.framework.facilities.security.exceptions.UnmanagedLoginException;
import es.archigest.framework.facilities.security.exceptions.WrongPasswordException;

/**
 * Implementación del interfaz AuthenticationConnector para la autenticación de
 * usuarios contra InvesDoc.
 */
public class InvesDocConnector implements AuthenticationConnector {
	private final String EC_USER_LOCKED = "IECI_TECDOC_UAS_USER_LOCKED";
	/** Logger de la clase. */
	protected static final Logger logger = Logger
			.getLogger(InvesDocConnector.class);

	/** Configuración de la conexión con InvesDoc. */
	DbConnectionConfig dbConnectionConfig = null;

	/**
	 * Constructor.
	 */
	public InvesDocConnector() {
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

			String entity = null;

			// Buscar la propiedad entidad
			if ((params != null)
					&& (params.containsKey(MultiEntityConstants.ENTITY_PARAM))) {
				entity = (String) params.get(MultiEntityConstants.ENTITY_PARAM);
			}

			// Obtener el dataSource de autenticación
			String dataSource = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo().getConfiguracionGeneral()
					.getDataSourceNameAuth();
			if (entity != null)
				dataSource = MultiEntityUtil.composeDsName(dataSource, entity);
			dbConnectionConfig = new DbConnectionConfig(dataSource, null, null);

		} catch (Exception e) {
			logger.error("Error leyendo el nombre del dataSource", e);
			throw new AuthenticationConnectorException(e, this.getClass()
					.getName(), "Error leyendo el nombre del dataSource");
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

		String identifier = null;

		try {
			// Autenticar el usuario
			UasAuthToken token = UasBnoAuthEx.authenticateUser(
					dbConnectionConfig, username, password, 1);
			if (logger.isDebugEnabled())
				logger.debug("token => " + token.toString());

			// Devolver el identificador del usuario autenticado
			identifier = new Integer(token.getUser().getId()).toString();

			if (logger.isInfoEnabled())
				logger.info("Usuario [" + username + "] autenticado: "
						+ identifier);
		} catch (IeciTdException e) {
			logger.warn("Error en la autenticaci\u00F3n del usuario "
					+ username, e);

			if (UasBaseError.EC_INVALID_USER_NAME.equals(e.getErrorCode())) {
				UnknownUserException ex = new UnknownUserException(
						SecurityConstants.USERNAME);
				ex.setContextValue(SecurityConstants.USERNAME, username);
				throw ex;
			} else if (UasBaseError.EC_INVALID_AUTH_SPEC.equals(e
					.getErrorCode()))
				throw new WrongPasswordException(username, password);
			else if (EC_USER_LOCKED.equals(e.getErrorCode())) {
				throw new UserLockedLoginException(e.getLocalizedMessage());
			} else
				throw new UnmanagedLoginException(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.warn("Error en la autenticaci\u00F3n del usuario "
					+ username, e);
			throw new UnmanagedLoginException(e.getLocalizedMessage());
		}

		return identifier;

		// return "1160";
	}

	/**
	 * Obtiene la informacion acerca del usuario.
	 * 
	 * @param idUser
	 *            Identificador del usuario.
	 * @return Información del usuario.
	 */
	public UserInfo getUserInfo(String idUser) {
		UserInfo user = null;

		try {
			UasDaoUserRecO idocUser = UasBnoUser.findUserById(
					dbConnectionConfig, TypeConverter.toInt(idUser, -1));
			if (idocUser != null) {
				user = new UserInfoImpl(
						new Integer(idocUser.getId()).toString(), new Integer(
								idocUser.getId()).toString(), null, // "address"
						null, // "email"
						idocUser.getName(), null, idocUser.getRemarks());
			}
		} catch (Exception e) {
			logger.error("Error al buscar el usuario con id: " + idUser, e);
			throw new AuthenticationConnectorException(e, this.getClass()
					.getName(), "Error al buscar el usuario con id: " + idUser);
		}

		return user;
	}

	/**
	 * Busqueda del usuario por nombre. Funcionara a modo de LIKE.
	 * 
	 * @param username
	 *            Nombre del usuario.
	 * @return Lista de usuarios.
	 */
	public List findUserByName(String username) {
		List users = new ArrayList();
		UasDaoUserRecO idocUser = null;

		try {
			List idocUsers = UasBnoUser.findUsersByName(dbConnectionConfig,
					username);
			for (int i = 0; i < idocUsers.size(); i++) {
				idocUser = (UasDaoUserRecO) idocUsers.get(i);
				if (logger.isDebugEnabled())
					logger.debug("Usuario encontrado en Invesdoc: " + idocUser);

				users.add(new UserInfoImpl(new Integer(idocUser.getId())
						.toString(), new Integer(idocUser.getId()).toString(),
						null, // "address"
						null, // "email"
						idocUser.getName(), null, idocUser.getRemarks()));
			}
		} catch (Exception e) {
			logger.error("Error al buscar el usuario: " + username, e);
			throw new AuthenticationConnectorException(e, this.getClass()
					.getName(), "Error al buscar el usuario: " + username);
		}
		return users;
	}
}
