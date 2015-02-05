package common.webservices;

import ieci.tecdoc.core.base64.Base64Util;

import java.util.Iterator;
import java.util.Properties;

import javax.security.auth.login.LoginException;
import javax.xml.rpc.handler.soap.SOAPMessageContext;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;

import org.apache.axis.MessageContext;
import org.apache.axis.client.Stub;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.log4j.Logger;
import org.w3c.dom.DOMException;

import se.autenticacion.AuthenticationConnector;
import se.autenticacion.AuthenticationConnectorFactory;
import se.usuarios.AppUser;
import se.usuarios.AppUserRIFactory;
import se.usuarios.ServiceClient;
import se.usuarios.TipoUsuario;
import xml.config.ConfiguracionGeneral;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.ConfigConstants;
import common.Constants;
import common.Messages;
import common.MultiEntityConstants;
import common.bi.ServiceRepository;
import common.exceptions.RequiredEntityException;
import common.util.StringUtils;

import es.archigest.framework.facilities.security.exceptions.RequiredPasswordException;

/**
 * Clase base para la autenticación de los servicios Web
 */
public class WSBase {

	/** Logger de la clase. */
	protected static final Logger logger = Logger.getLogger(WSBase.class);

	/** Nombre del usuario. */
	private String userName = null;

	/** Clave del usuario. */
	private String password = null;

	/** Dirección IP del cliente. */
	private String remoteIpAddress = ".";

	/** Entidad del usuario **/
	private String entity = null;

	/** Cliente del servicio. */
	private ServiceClient serviceClient = null;

	/** Repositorio de servicios. */
	private ServiceRepository serviceRepository = null;

	private static String entityProperty = "ENTITY_PROPERTY";

	/**
	 * Datos del usuario conectado
	 */
	private AppUser appUser = null;

	/**
	 * Constructor.
	 */
	public WSBase() {
		// Usuario anónimo
		ConfiguracionGeneral cfg = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionGeneral();
		if (StringUtils.isNotBlank(cfg.getUsuarioAnonimoWS())) {
			setUserName(cfg.getUsuarioAnonimoWS());
			setPassword(cfg.getClaveUsuarioAnonimoWS());
		}
	}

	/**
	 * Método para procesar las cabeceras del servicio Web
	 */
	public void processHeaders() {

		MessageContext msgContext = MessageContext.getCurrentContext();
		if (msgContext instanceof SOAPMessageContext) {
			SOAPMessageContext context = (SOAPMessageContext) msgContext;

			try {
				// Obtener la cabecera SOAP
				SOAPHeader header = context.getMessage().getSOAPPart()
						.getEnvelope().getHeader();

				// Obtener todos los elementos de la cabecera
				Iterator headers = header
						.extractHeaderElements("http://schemas.xmlsoap.org/soap/actor/next");
				while (headers.hasNext()) {
					SOAPHeaderElement he = (SOAPHeaderElement) headers.next();

					// Comprobar si las cabeceras incluyen usuario y password
					if (he.getName().equals(Stub.USERNAME_PROPERTY))
						setUserName(he.getFirstChild().getNodeValue());
					else if (he.getName().equals(Stub.PASSWORD_PROPERTY))
						setPassword(he.getFirstChild().getNodeValue());
					else if (he.getName().equals(entityProperty))
						setEntity(he.getFirstChild().getNodeValue());

				}
			} catch (DOMException e) {
				logger.error("Error al procesar las cabeceras", e);
			} catch (SOAPException e) {
				logger.error("Error al procesar las cabeceras", e);
			} catch (NullPointerException e) {
				logger.error("Error al procesar las cabeceras", e);
			}
		}
	}

	/**
	 * Comprueba la autenticación del usuario.
	 */
	public void authenticate() throws Exception {

		// Procesar las cabeceras
		processHeaders();

		if (StringUtils.isBlank(userName) || StringUtils.isBlank(password))
			throw new RequiredPasswordException();

		if (ConfigConstants.getInstance().getEntidadRequerida()
				&& StringUtils.isBlank(entity))
			throw new RequiredEntityException(
					Messages.getString("archigest.archivo.webservice.RequiredEntityException"));

		// Obtener la entidad para el usuario conectado
		Properties params = new Properties();

		if (entity != null) {
			params.put(MultiEntityConstants.ENTITY_PARAM, entity);
		}

		// Instancia del conector adecuado para el tipo de usuario
		AuthenticationConnector authClient = AuthenticationConnectorFactory
				.getConnector(TipoUsuario.INTERNO, params);

		// Autenticación del usuario
		try {
			String identifier = authClient.authenticate(userName, password,
					remoteIpAddress);
			// Información del usuario
			setAppUser(AppUserRIFactory.createAppUserRI().getAppUser(
					identifier, TipoUsuario.INTERNO, Constants.STRING_EMPTY,
					remoteIpAddress, entity, null));

			// Información reducida del usuario
			serviceClient = ServiceClient.create(getAppUser());

		} catch (LoginException e) {
			logger.error("Error al autenticar el usuario");
			throw e;
		}

	}

	/**
	 * Obtiene la información del usuario.
	 *
	 * @return Información del usuario.
	 */
	public ServiceClient getServiceClient() {
		return serviceClient;
	}

	/**
	 * Obtiene el repositorio de servicios.
	 *
	 * @return Repositorio de servicios.
	 */
	public ServiceRepository getServiceRepository() {
		if (serviceRepository == null)
			serviceRepository = ServiceRepository.getInstance(serviceClient);

		return serviceRepository;
	}

	/**
	 * @param password
	 *            The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	/**
	 * @param appUser
	 *            el objeto appUser a fijar
	 */
	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	/**
	 * @return el objeto appUser
	 */
	public AppUser getAppUser() {
		return appUser;
	}

	protected String getPasswordByBase64(String password){
		try {
			return Base64Util.decodeToString(password);
		} catch (Exception e) {
			logger.error(e);
		}
		return password;
	}


	protected void finalizarSesion() {
		// TODO Plantilla de método auto-generado

	}
}
