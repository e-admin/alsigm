package se.autenticacion;

import gcontrol.vos.UsuarioVO;
import ieci.core.base64.Base64Util;
import ieci.tecdoc.sbo.util.idoccrypto.IdocCryptoUtil;

import java.util.List;
import java.util.Properties;

import javax.security.auth.login.LoginException;

import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import se.usuarios.ServiceClient;
import xml.config.ConfiguracionControlAcceso;
import xml.config.ConfiguracionSistemaArchivoFactory;
import xml.config.Superusuario;

import common.Constants;
import common.bi.GestionControlUsuariosBI;
import common.bi.ServiceRepository;
import common.model.UserInfo;
import common.util.ListUtils;
import common.util.StringUtils;

import es.archigest.framework.facilities.security.SecurityConstants;
import es.archigest.framework.facilities.security.exceptions.UnknownUserException;
import es.archigest.framework.facilities.security.exceptions.UnmanagedLoginException;
import es.archigest.framework.facilities.security.exceptions.WrongPasswordException;

/**
 * Implementación del interfaz AuthenticationConnector para autenticar
 * superusuarios.
 */
public class SuperuserAuthenticationConnectorImpl implements
		AuthenticationConnector {

	/** Clave para encriptar la password */
	private String PASSWORDTOGENKEY = "ARCHIVO";

	/** Logger de la clase. */
	protected static final Logger logger = Logger
			.getLogger(SuperuserAuthenticationConnectorImpl.class);

	/**
	 * Constructor.
	 */
	public SuperuserAuthenticationConnectorImpl() {
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
		ConfiguracionControlAcceso cca = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo()
				.getConfiguracionControlAcceso();
		String ret = null;

		if (!ListUtils.isEmpty(cca.getSuperusuarios())) {
			Superusuario superusuario = cca.findSuperusuario(username);

			// Si el superusuario no existe lanzar una excepción para indicarlo
			if (superusuario == null) {
				UnknownUserException ex = new UnknownUserException(
						SecurityConstants.USERNAME);
				ex.setContextValue(SecurityConstants.USERNAME, username);
				throw ex;
			} else {
				String encPwd = Constants.STRING_EMPTY;
				try {
					// Encriptar la contraseña
					encPwd = IdocCryptoUtil.encryptPassword(password,
							PASSWORDTOGENKEY);
				} catch (Exception e) {
					throw new UnmanagedLoginException(e.getLocalizedMessage());
				}

				// Codificar en base 64 el resultado
				String base64EncPwd = Base64Util.encodeString(encPwd);

				if (StringUtils.isEmpty(base64EncPwd)) {
					throw new WrongPasswordException(username, password);
				} else {
					if (!base64EncPwd.equals(superusuario.getClave())) {
						throw new WrongPasswordException(username, password);
					} else {
						ret = getSuperusuarioArchivo(username);
					}
				}
			}
		}
		return ret;
	}

	public static String getSuperusuarioArchivo(String username) {
		// Leer el usuario de base de datos y si no existe crearlo
		try {
			// Obtener el repositorio de servicios
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.createWithEntity(null));

			// Obtenemos el servicio de prestamos para el usuario conectado
			GestionControlUsuariosBI gu = services
					.lookupGestionControlUsuariosBI();

			UsuarioVO usuario = gu.getSuperusuario(username);
			if (usuario == null) {
				usuario = gu.crearUsuarioAdministracion(username);
				return usuario.getId();
			} else {
				return usuario.getId();
			}
		} catch (Exception e1) {
			throw new UnmanagedLoginException(e1.getMessage());
		}
	}

	public List findUserByName(String name) throws NotImplementedException {
		return null;
	}

	public UserInfo getUserInfo(String idUser) throws NotImplementedException {
		return null;
	}
}
