package es.ieci.tecdoc.fwktd.core.config.business.manager.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.core.config.business.manager.ConfigurationPathLoader;
import es.ieci.tecdoc.fwktd.core.config.exception.ConfigurationObjectException;
import es.ieci.tecdoc.fwktd.core.messages.ApplicationMessages;
import es.ieci.tecdoc.fwktd.core.messages.ConfiguracionObjectErrorCodes;
import es.ieci.tecdoc.fwktd.core.messages.ConfiguracionObjectMessageCodes;

/**
 * Clase que permite obtener propiedades de configuracion de un fichero
 * properties
 */
public class ConfigurationPathLoaderWebImpl implements ConfigurationPathLoader {

	/**
	 * Atributo para logging
	 */
	private static Logger logger = LoggerFactory
			.getLogger(ConfigurationPathLoaderWebImpl.class);

	/**
	 * Nombre del fichero en el que se almacenan las variables de configuración
	 */
	private final String COMMON_PROPERTIES_FILE_NAME = "common.properties";

	/**
	 * Nombre del fichero en el que se almacena la ruta al fichero de
	 * configuración
	 */
	private final String LOCATION_PROPERTIES_FILE_NAME = "configLocation.properties";

	/**
	 * Nombre de la variable de entorno en la que se almacena la ruta al fichero
	 * de configuración
	 */
	private final String COMMON_PROPERTIES_FILE_NAME_PROPERTY = "COMMON_PROPERTIES_FILE_NAME_PROPERTY";

	/**
	 * Variable para almacenar la petición actual
	 */
	protected ServletRequest request;

	/**
	 * Parámetros pasados al loader
	 */
	private Map params = new HashMap();

	/**
	 * Constructor a partir de la petición actual
	 * 
	 * @param request
	 *            Petición actual
	 */
	public ConfigurationPathLoaderWebImpl(ServletRequest request, Map params) {
		this.request = request;
		this.params = params;
	}

	/**
	 * Comprueba si el path es correcto
	 * 
	 * @param path
	 *            Ruta a comprobar
	 * @return Ruta a comprobar
	 */
	private boolean checkPropertyFileNamePath(String path) {
		if ((path != null) && (!"".equals(path)))
			return true;
		return false;
	}

	/**
	 * Método para buscar la ruta de configuración en una variable del sistema
	 * 
	 * @return ruta de configuración del fichero de configuración
	 */
	private String getPropertyFileNameInFilterParameter() {
		return (String) params.get(COMMON_PROPERTIES_FILE_NAME_PROPERTY);
	}

	/**
	 * Método para buscar la ruta de configuración en una variable del sistema
	 * 
	 * @return ruta de configuración del fichero de configuración
	 */
	private String getPropertyFileNameInEnvironmentVariable() {
		return System.getProperty(COMMON_PROPERTIES_FILE_NAME_PROPERTY);
	}

	/**
	 * Método para buscar la ruta de configuración en un fichero del classloader
	 * 
	 * @return ruta de configuración del fichero de configuración
	 */
	private String getPropertyFileNameInClassloaderFile() {

		String propertyFileNamePath = null;
		InputStream is = null;
		try {
			is = ConfigurationPathLoaderWebImpl.class.getClassLoader()
					.getResourceAsStream(LOCATION_PROPERTIES_FILE_NAME);

			if (is != null) {
				Properties properties = new Properties();
				properties.load(is);
				propertyFileNamePath = properties
						.getProperty(COMMON_PROPERTIES_FILE_NAME_PROPERTY);
			}
		} catch (IOException e) {
			logger.debug(e.getMessage(), e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.debug(e.getMessage(), e);
				}
			}
		}

		return propertyFileNamePath;
	}

	/**
	 * Método para buscar la ruta de configuración en el classloader
	 * 
	 * @return ruta de configuración del fichero de configuración
	 */
	private String getPropertyFileNameInClassloader() {

		URL propertyFileNameUrl = ConfigurationPathLoaderWebImpl.class
				.getClassLoader().getResource(COMMON_PROPERTIES_FILE_NAME);
		if (propertyFileNameUrl != null) {
			return propertyFileNameUrl.getPath();
		}

		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.finder.ConfigurationPathLoader#getPathConfiguracionFile()
	 */
	public String getPathConfiguracionFile() {

		// Buscar en la variable de entorno
		String propertyFileNamePath = getPropertyFileNameInEnvironmentVariable();
		logger.debug(ApplicationMessages.getString(
				ApplicationMessages.BUNDLE_MESSAGES,
				ConfiguracionObjectMessageCodes.MSG_FIND_PATH_IN_ENVIRONMENT,
				request.getLocale()));
		if (checkPropertyFileNamePath(propertyFileNamePath)) {
			logger
					.debug(ApplicationMessages
							.getString(
									ApplicationMessages.BUNDLE_MESSAGES,
									ConfiguracionObjectMessageCodes.MSG_FIND_PATH_IN_ENVIRONMENT_FOUND,
									request.getLocale()));
			return propertyFileNamePath;
		}

		// Buscar en las variables del loader
		propertyFileNamePath = getPropertyFileNameInFilterParameter();
		logger.debug(ApplicationMessages.getString(
				ApplicationMessages.BUNDLE_MESSAGES,
				ConfiguracionObjectMessageCodes.MSG_FIND_PATH_IN_FILTER,
				request.getLocale()));
		if (checkPropertyFileNamePath(propertyFileNamePath)) {
			logger
					.debug(ApplicationMessages
							.getString(
									ApplicationMessages.BUNDLE_MESSAGES,
									ConfiguracionObjectMessageCodes.MSG_FIND_PATH_IN_FILTER_FOUND,
									request.getLocale()));
			return propertyFileNamePath;
		}

		// Buscar en el classloader el fichero location.properties
		propertyFileNamePath = getPropertyFileNameInClassloaderFile();
		logger
				.debug(ApplicationMessages
						.getString(
								ApplicationMessages.BUNDLE_MESSAGES,
								ConfiguracionObjectMessageCodes.MSG_FIND_PATH_IN_CLASSLOADER_FILE,
								request.getLocale()));
		if (checkPropertyFileNamePath(propertyFileNamePath)) {
			logger
					.debug(ApplicationMessages
							.getString(
									ApplicationMessages.BUNDLE_MESSAGES,
									ConfiguracionObjectMessageCodes.MSG_FIND_PATH_IN_CLASSLOADER_FILE_FOUND,
									request.getLocale()));
			return propertyFileNamePath;
		}

		// Buscar en el classloader el fichero de configuración
		propertyFileNamePath = getPropertyFileNameInClassloader();
		logger.debug(ApplicationMessages.getString(
				ApplicationMessages.BUNDLE_MESSAGES,
				ConfiguracionObjectMessageCodes.MSG_FIND_PATH_IN_CLASSLOADER,
				request.getLocale()));
		if (checkPropertyFileNamePath(propertyFileNamePath)) {
			logger
					.debug(ApplicationMessages
							.getString(
									ApplicationMessages.BUNDLE_MESSAGES,
									ConfiguracionObjectMessageCodes.MSG_FIND_PATH_IN_CLASSLOADER_FOUND,
									request.getLocale()));
			return propertyFileNamePath;
		}

		if (!checkPropertyFileNamePath(propertyFileNamePath)) {
			throw new ConfigurationObjectException(
					ConfiguracionObjectErrorCodes.ERR_1002_PATH_CONFIG_FILE_NOT_FOUND);
		}

		return null;
	}

}
