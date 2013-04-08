package ieci.tdw.ispac.ispaclib.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.log4j.Logger;

/**
 * Clase para acceder a los ficheros de configuración.
 *
 */
public class ConfigurationHelper {

	/**
	 * Logger de la clase.
	 */
	protected static final Logger logger = Logger.getLogger(ConfigurationHelper.class);
	
	
	/**
	 * Obtiene la ruta completa del fichero de configuración.
	 * @param configFileName Nombre del fichero de configuración.
	 * @return Ruta completa del fichero de configuración.
	 */
	public static String getConfigFilePath(String configFileName) {
		
		String fullPath = ISPACConfigFilePathResolver.getInstance().resolveFullPath(configFileName);
		
		if (logger.isInfoEnabled()) {
			logger.info("Fichero de configuración: " + configFileName + " => " + fullPath);
		}

		return fullPath;
	}

	/**
	 * Obtiene la ruta completa del fichero de configuración.
	 * @param subdir Subdirectorio del fichero de configuración.
	 * @param configFileName Nombre del fichero de configuración.
	 * @return Ruta completa del fichero de configuración.
	 */
	public static String getConfigFilePath(String subdir, String configFileName) {
		
		String fullPath = ISPACConfigFilePathResolver.getInstance().resolveFullPath(configFileName, subdir);
		
		if (logger.isInfoEnabled()) {
			logger.info("Fichero de configuración: " + subdir + "/" + configFileName + " => " + fullPath);
		}

		return fullPath;
	}
	
	/**
	 * Obtiene el fichero de configuración.
	 * @param configFileName Nombre del fichero de configuración.
	 * @return Fichero de configuración.
	 */
	public static File getConfigFile(String configFileName) {
		return new File(getConfigFilePath(configFileName));
	}

	/**
	 * Obtiene el fichero de configuración.
	 * @param subdir Subdirectorio del fichero de configuración.
	 * @param configFileName Nombre del fichero de configuración.
	 * @return Fichero de configuración.
	 */
	public static File getConfigFile(String subdir, String configFileName) {
		return new File(getConfigFilePath(subdir, configFileName));
	}
	
	/**
	 * Obtiene el InputStream del fichero de configuración.
	 * @param configFileName Nombre del fichero de configuración.
	 * @return InputStream del fichero de configuración.
	 * @throws FileNotFoundException si el fichero no existe.
	 */
	public static InputStream getConfigFileInputStream(String configFileName) 
			throws FileNotFoundException {
		return new FileInputStream(getConfigFile(configFileName));
	}

	/**
	 * Obtiene el InputStream del fichero de configuración.
	 * @param subdir Subdirectorio del fichero de configuración.
	 * @param configFileName Nombre del fichero de configuración.
	 * @return InputStream del fichero de configuración.
	 * @throws FileNotFoundException si el fichero no existe.
	 */
	public static InputStream getConfigFileInputStream(String subdir, String configFileName)
			throws FileNotFoundException {
		return new FileInputStream(getConfigFile(subdir, configFileName));
	}

}
