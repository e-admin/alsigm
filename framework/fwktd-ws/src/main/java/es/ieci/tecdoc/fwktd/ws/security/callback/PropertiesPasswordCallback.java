package es.ieci.tecdoc.fwktd.ws.security.callback;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.commons.lang.StringUtils;
import org.apache.ws.security.WSPasswordCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.core.spring.configuration.ConfigurationResourceLoader;

/**
 * Callback para obtener la clave del usuario.
 *
 * Esta clase contiene un objeto Properties con la lista de usuarios y sus
 * contraseñas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class PropertiesPasswordCallback implements CallbackHandler {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(PropertiesPasswordCallback.class);

	/**
	 * Relación de usuarios y claves.
	 */
	private Properties users = null;

	/**
	 * Cargador de recursos de configuración.
	 */
	private ConfigurationResourceLoader configurationResourceLoader = null;

	/**
	 * Fichero de propiedades a cargar.
	 */
	private String propertiesFile = null;

	/**
	 * Constructor.
	 */
	public PropertiesPasswordCallback() {
		super();
	}

	public Properties getUsers() {
		return users;
	}

	public void setUsers(Properties users) {
		this.users = users;
	}

	public ConfigurationResourceLoader getConfigurationResourceLoader() {
		return configurationResourceLoader;
	}

	public void setConfigurationResourceLoader(
			ConfigurationResourceLoader configurationResourceLoader) {
		this.configurationResourceLoader = configurationResourceLoader;
	}

	public String getPropertiesFile() {
		return propertiesFile;
	}

	public void setPropertiesFile(String propertiesFile) {
		this.propertiesFile = propertiesFile;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback[])
	 */
	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {

		logger.info("Entrada en ServerPasswordCallback.handle");

		if ((callbacks != null) && (callbacks.length > 0)) {

			WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
			if (pc != null) {

				logger.info("Obteniendo la clave para el usuario [{}]",
						pc.getIdentifier());

				if (getUsers() != null) {
					// set the password on the callback. This will be compared
					// to the password which was sent from the client.
					pc.setPassword(getUsers().getProperty(pc.getIdentifier()));
				}
			}
		}
	}

	/**
	 * Carga las propiedades de los usuarios.
	 */
	public void loadProperties() {

		logger.info("Cargando propiedades de usuarios");

		if (getConfigurationResourceLoader() != null) {

			if (StringUtils.isNotBlank(getPropertiesFile())) {

				logger.info("Cargando el fichero [{}] con el cargador [{}]", getPropertiesFile(), getConfigurationResourceLoader());

				// Obtener la ruta del fichero de propiedades
				String path = getConfigurationResourceLoader().pathResolver(getPropertiesFile(), null);
				if (StringUtils.isNotBlank(path)) {

					logger.info("Ruta del fichero de propiedades: {}", path);

					// Cargar las propiedades de usuarios
					Properties props = new Properties();
					try {
						props.load(new FileInputStream(path));
					} catch (IOException e) {
						logger.warn("Error en la carga del fichero de propiedades: " + path, e);
					}

					setUsers(props);

				} else {
					logger.warn("No se ha encontrado el fichero de propiedades: {}", getPropertiesFile());
				}

			} else {
				logger.info("No se ha configurado el fichero de propiedades de usuarios");
			}

		} else {
			logger.info("No se ha configurado el cargador de recursos de configuración [configurationResourceLoader]");
		}
	}
}
