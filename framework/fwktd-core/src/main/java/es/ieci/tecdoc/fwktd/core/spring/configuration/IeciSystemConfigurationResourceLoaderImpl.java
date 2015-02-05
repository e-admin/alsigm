package es.ieci.tecdoc.fwktd.core.spring.configuration;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 
 * ConfigurationResourceLoader que cargara los properties a partir de un path
 * base que se puede encontrar (siguiendo este orden de prioriodad) en: 1.-El
 * indicado por el path que se encuentra en un fichero de properties el cual se
 * cargará siguiendo la politica indicada en 2,3,4 2.-Una variable de sistema de
 * la que se obtendra el path del sistema de ficheros a partir del cual se
 * encuentran los recursos 3.-Un path del sistema de ficheros 4.-En el classpath
 * 
 */
public class IeciSystemConfigurationResourceLoaderImpl implements
		ConfigurationResourceLoader {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(IeciSystemConfigurationResourceLoaderImpl.class);

	/**
	 * nombre por defecto de la variable de sistema la cual contendra el path al
	 * directorio base de configuracion
	 */
	public static String DEFAULT_BASE_CONFIGURATION_PATH_DIR_SYSTEM_VAR_NAME = "IECI_SYSTEM_CONFIG_DIR";

	/**
	 * nombre del fichero de properties que contiene el path del directorio base
	 * donde puede encontrarse la configuracion
	 */
	public static String DEFAULT_BASE_CONFIGURATION_LOCATION_FILE_NAME = "configLocation.properties";

	/**
	 * nombre por defecto de la property que contiene el path del directorio de
	 * configuración indicado en el <code>configLocationFilename</code>
	 */
	protected static String CONFIG_LOCATION_PROPERTY_NAME = "path";

	/**
	 * contendra el nombre de la property que contiene el path del directorio de
	 * configuración indicado en el <code>configLocationFilename</code>
	 */
	protected String configLocationPropertyName;

	/**
	 * contiene el path completo del fichero de properties que contiene el path
	 * del directorio base donde puede encontrarse la configuracion
	 */
	protected String configLocationFilename;

	/**
	 * propiedad que contrendra el nombre de la variable de sistema la cual
	 * contendra el path al directorio base de configuracion
	 */
	protected String baseConfigurationPathDirSystemVarName;

	/**
	 * propiedad que path al directorio base de configuracion del sistema de
	 * ficheros
	 */
	protected String baseConfigurationPathDir;

	public IeciSystemConfigurationResourceLoaderImpl() {
		super();
		configLocationPropertyName = new String(CONFIG_LOCATION_PROPERTY_NAME);
		configLocationFilename = new String(
				DEFAULT_BASE_CONFIGURATION_LOCATION_FILE_NAME);

		baseConfigurationPathDirSystemVarName = new String(
				DEFAULT_BASE_CONFIGURATION_PATH_DIR_SYSTEM_VAR_NAME);
		baseConfigurationPathDir = "";
	}

	public String pathResolver(String resourceName, Map params) {
		String result = null;

		Resource resource = this.loadResource(resourceName, null);
		if (resource != null) {
			try {
				result = resource.getURL().getPath();
				result = URLDecoder.decode(result, System
						.getProperty("file.encoding"));
			} catch (IOException e) {
				String message = e.getLocalizedMessage();
				logger.warn(message, e);

			}
		} else {
			result = null;
			String message = "Imposible encontrar path del recurso:"
					+ resourceName;
			if (logger.isDebugEnabled()) {
				logger.debug(message);
			}
		}

		return result;
	}

	public Resource loadResource(String resourceName, Map params) {
		if (logger.isDebugEnabled()) {
			logger.debug("loadResource(String, Map) - start");
		}

		Resource result = null;

		// intentamos cargar la configuracion a partir de un path indicado por
		// el nombre de
		result = loadResourceFromConfigFile(resourceName, params);

		if (result == null || !result.exists()) {
			// intentamos buscar la localizacion base de una variable de sistema
			result = loadResourceFromSystemVar(resourceName, params);
		}

		// intentamos buscar la localizacion del sistema de ficheros
		if (result == null || !result.exists()) {
			result = loadResourceFromFileSystem(resourceName, params);
		}

		// intentamos buscar la localizacion en el classpath
		if (result == null || !result.exists()) {
			result = loadResourceFromClassPath(resourceName, params);
		}

		if (result == null || !result.exists()) {
			String message = "loadResource(String, Map) - No se ha podido cargar el recurso:"
					+ resourceName;
			logger.warn(message);
			result = null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("loadResource(String, Map) - end");
		}

		return result;
	}

	public Resource loadResourceFromClassPath(String resourceName, Map params) {

		Resource result = null;
		try {
			result = new ClassPathResource(resourceName);
		} catch (RuntimeException e) {
			logger.warn("loadResourceFromClassPath(String, Map)", e);

		}

		if (logger.isDebugEnabled() && result != null && result.exists()) {
			String fullPath;
			try {
				fullPath = result.getURL().toString();
			} catch (IOException e) {
				fullPath = "";
			}
			String message = "cargado recurso " + resourceName
					+ "desde loadResourceFromClassPath:" + fullPath;
			logger.debug(message);

		}

		return result;
	}

	public Resource loadResourceFromFileSystem(String resourceName, Map params) {

		Resource result = null;

		String path = getFullPath(resourceName);

		if (logger.isDebugEnabled()) {
			logger
					.debug("loadResourceFromFileSystem(String, Map) - String path="
							+ path);
		}

		try {
			result = new FileSystemResource(path);
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("loadResourceFromFileSystem(String, Map)", e);
			}

		}

		if (logger.isDebugEnabled() && result != null && result.exists()) {
			String fullPath;
			try {
				fullPath = result.getURL().toString();
			} catch (IOException e) {
				fullPath = "";
			}
			String message = "cargado recurso " + resourceName
					+ "desde loadResourceFromFileSystem:" + fullPath;
			logger.debug(message);

		}

		return result;
	}

	public Resource loadResourceFromSystemVar(String resourceName, Map params) {
		Resource result = null;
		String path = getFullPathFromSystemVar(resourceName);

		if (logger.isDebugEnabled()) {
			logger
					.debug("loadResourceFromSystemVar(String, Map) - String path="
							+ path);
		}

		try {
			result = new FileSystemResource(path);
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("loadResourceFromSystemVar(String, Map)", e);
			}

		}

		if (logger.isDebugEnabled() && result != null && result.exists()) {
			String fullPath;
			try {
				fullPath = result.getURL().toString();
			} catch (IOException e) {
				fullPath = "";
			}
			String message = "cargado recurso " + resourceName
					+ "desde loadResourceFromSystemVar :" + fullPath;
			logger.debug(message);

		}

		return result;
	}

	protected Resource loadResourceFromConfigFile(String resourceName,
			Map params) {

		Resource result = null;

		String path = getFullPathFromConfigFile(resourceName);

		if (logger.isDebugEnabled()) {
			logger
					.debug("loadResourceFromConfigFile(String, Map) - String path="
							+ path);
		}

		try {
			result = new FileSystemResource(path);
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("loadResourceFromConfigFile(String, Map)", e);
			}

		}

		if (logger.isDebugEnabled() && result != null && result.exists()) {
			String fullPath;
			try {
				fullPath = result.getURL().toString();
			} catch (IOException e) {
				fullPath = "";
			}
			String message = "cargado recurso " + resourceName
					+ "desde loadResourceFromConfigFile:" + fullPath;
			logger.debug(message);

		}

		return result;

	}

	protected String getFullPath(String resourceName) {
		String result = "";

		result = normalizePath(baseConfigurationPathDir, resourceName);

		return result;
	}

	protected String getFullPathFromSystemVar(String resourceName) {
		String result = "";
		String basePath = System
				.getProperty(getBaseConfigurationPathDirSystemVarName());
		result = normalizePath(basePath, resourceName);

		return result;
	}

	protected String getFullPathFromConfigFile(String resourceName) {
		String result = null;
		String basePath = getPathFromConfigFile();
		result = normalizePath(basePath, resourceName);
		return result;
	}

	protected String getPathFromConfigFile() {
		String result = null;

		// intentamos buscar el fichero a partir del path ofrecido por la
		// variable de sistema
		Resource resource = loadResourceFromSystemVar(configLocationFilename,
				null);

		if (resource == null || !resource.exists()) {
			// intentamos buscar el fichero con la ruta del sistema de ficheros
			resource = loadResourceFromFileSystem(configLocationFilename, null);
		}

		if (resource == null || !resource.exists()) {
			// intentamos buscar el fichero a partir del classpath
			resource = loadResourceFromClassPath(configLocationFilename, null);
		}
		if (resource != null && resource.exists()) {
			Properties props = null;
			try {
				props = PropertiesLoaderUtils.loadProperties(resource);
			} catch (IOException e) {
				String message = "Imposible cargar el fichero de localización: "
						+ resource.getFilename();
				logger.debug(message, e);

			}

			if (props != null) {
				result = props.getProperty(configLocationPropertyName);
			}

		}

		return result;

	}

	protected String normalizePath(String basePath, String resourceName) {

		String result = basePath + File.separator + resourceName;
		result = FilenameUtils.normalize(result);
		result = FilenameUtils.separatorsToSystem(result);

		return result;

	}

	public String getBaseConfigurationPathDir() {
		return baseConfigurationPathDir;
	}

	public void setBaseConfigurationPathDir(String baseConfigurationPathDir) {
		this.baseConfigurationPathDir = baseConfigurationPathDir;
	}

	public String getBaseConfigurationPathDirSystemVarName() {
		return baseConfigurationPathDirSystemVarName;
	}

	public void setBaseConfigurationPathDirSystemVarName(
			String baseConfigurationPathDirSystemVarName) {
		this.baseConfigurationPathDirSystemVarName = baseConfigurationPathDirSystemVarName;
	}

	public String getConfigLocationFilename() {
		return configLocationFilename;
	}

	public void setConfigLocationFilename(String configLocationFilenamePath) {
		this.configLocationFilename = configLocationFilenamePath;
	}

	public String getConfigLocationPropertyName() {
		return configLocationPropertyName;
	}

	public void setConfigLocationPropertyName(String configLocationPropertyName) {
		this.configLocationPropertyName = configLocationPropertyName;
	}

}
