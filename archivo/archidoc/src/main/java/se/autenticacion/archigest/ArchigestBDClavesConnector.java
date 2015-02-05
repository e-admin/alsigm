package se.autenticacion.archigest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.security.auth.login.LoginException;

import org.apache.log4j.Logger;

import se.autenticacion.AuthenticationConnector;
import se.autenticacion.AuthenticationConnectorException;
import se.autenticacion.UserInfoImpl;
import se.terceros.GestorTerceros;
import se.terceros.GestorTercerosFactory;
import se.terceros.InfoTercero;

import common.MultiEntityConstants;
import common.model.UserInfo;
import common.util.NombresUtils;
import common.util.StringUtils;

import es.archigest.framework.core.configuration.ConfigurationParametersProperties;
import es.archigest.framework.facilities.security.exceptions.InsufficientDataException;
import es.archigest.framework.facilities.security.exceptions.UnmanagedLoginException;
import es.archigest.framework.modules.bdClaves.BDClavesRemoteInterface;
import es.archigest.framework.modules.bdClaves.BDClavesRemoteInterfaceFactory;
import es.archigest.framework.modules.bdClaves.exceptions.BDClavesException;
import es.archigest.framework.modules.bdClaves.vo.BDClavesPersonVO;

/**
 * Implementación del interfaz AuthenticationConnector para la autenticación de
 * usuarios contra el módulo común de autenticación
 */
public class ArchigestBDClavesConnector implements AuthenticationConnector {

	/** Logger de la clase. */
	protected static final Logger logger = Logger
			.getLogger(ArchigestBDClavesConnector.class);

	/** Parámetros de configuración del conector. */
	protected Properties parametros = new Properties();

	/**
	 * Constructor.
	 */
	public ArchigestBDClavesConnector() {
	}

	/**
	 * Inicializa con los parámetros de configuración.
	 * 
	 * @param params
	 *            Parámetros de configuración.
	 * @throws AuthenticationConnectorException
	 *             si ocurre algún error.
	 */
	public void initialize(Properties params) {
		this.parametros.putAll(new HashMap(params));
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
		String id = null;

		if (logger.isInfoEnabled())
			logger.info("Autenticando al usuario [" + username + "@" + ip + "]");

		if (StringUtils.isBlank(username)) {
			logger.info("El usuario est\u00E1 vac\u00EDo");
			throw new InsufficientDataException("USUARIO");
		}

		try {
			BDClavesRemoteInterface cri = BDClavesRemoteInterfaceFactory
					.getBDClavesRemoteInterface(new ConfigurationParametersProperties(
							parametros));

			// Autenticar al usuario
			BDClavesPersonVO person = cri.validatePhysicalPerson(username,
					password, ip);

			if (logger.isDebugEnabled())
				logger.debug("BDClavesPersonVO: " + person.toString());

			id = person.getCnIdTerce();
		} catch (BDClavesException e) {
			logger.warn("Error en la autenticaci\u00F3n del usuario "
					+ username, e);
			// throw new WrongPasswordException(username, password);
			throw new UnmanagedLoginException(e.getDescription());
		} catch (Exception e) {
			logger.warn("Error en la autenticaci\u00F3n del usuario "
					+ username, e);
			throw new UnmanagedLoginException(e.getLocalizedMessage());
		}

		if (logger.isInfoEnabled())
			logger.info("Usuario [" + username + "] autenticado: " + id);

		return id;
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

		if (StringUtils.isNotBlank(idUser)) {
			try {
				String entity = null;
				// Buscar la propiedad entidad
				if ((parametros != null)
						&& (parametros
								.containsKey(MultiEntityConstants.ENTITY_PARAM))) {
					entity = (String) parametros
							.get(MultiEntityConstants.ENTITY_PARAM);
				}

				// Obtener la entidad para el usuario conectado
				Properties params = null;

				if (StringUtils.isNotEmpty(entity)) {
					params = new Properties();
					params.put(MultiEntityConstants.ENTITY_PARAM, entity);
				}

				GestorTerceros terceros = GestorTercerosFactory
						.getConnector(params);
				InfoTercero infoTercero = terceros.recuperarTercero(idUser);
				if (infoTercero != null)
					user = new UserInfoImpl(idUser, idUser,
							infoTercero.getDireccion(), infoTercero.getEmail(),
							infoTercero.getNombre(), NombresUtils.getApellidos(
									infoTercero.getPrimerApellido(),
									infoTercero.getSegundoApellido()), null);
			} catch (Exception e) {
				logger.error("Error al buscar el usuario con id: " + idUser, e);
				throw new AuthenticationConnectorException(e, this.getClass()
						.getName(), "Error al buscar el usuario con id: "
						+ idUser);
			}
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
		return new ArrayList();
	}

}
