package ieci.tecdoc.sgm.scheduler.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.fwktd.core.spring.configuration.ConfigFilePathResolver;
import es.ieci.tecdoc.fwktd.core.spring.configuration.ConfigFilePathResolverImpl;


/**
 * Clase para obtener la ruta absoluta de los ficheros de configuración.
 *
 */
public class SchedulerConfigFilePathResolver implements ConfigFilePathResolver {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(SchedulerConfigFilePathResolver.class);
	
	protected static String SPRING_BEANS_FILENAME_DEFAULT	= "scheduler-config-beans.xml";
	protected static String SPRING_CONFIG_BEAN_DEFAULT		= "schedulerConfigurationResourceLoader";
	protected static String ISPAC_BASE_CONFIG_SUBDIR_KEY	= "CONFIG_SUBDIR";
	protected static String CONFIGURATION_BEAN_DEFAULT		= "schedulerConfigurationBean";

	protected ConfigFilePathResolver configFilePathResolver = null;

	
	/**
	 * Constructor.
	 */
	public SchedulerConfigFilePathResolver() {
		this.configFilePathResolver = new ConfigFilePathResolverImpl(
				SPRING_BEANS_FILENAME_DEFAULT,
				SPRING_CONFIG_BEAN_DEFAULT,
				ISPAC_BASE_CONFIG_SUBDIR_KEY,
				CONFIGURATION_BEAN_DEFAULT);
	}

	/**
	 * Constructor.
	 * @param springBeansFileName Nombre del fichero de beans de SPRING.
	 * @param springConfigBean Nombre del bean de configuración de SPRING.
	 * @param configSubDirKey Clave del directorio raíz. 
	 * @param configurationBean Nombre del bean de configuración.
	 */
	public SchedulerConfigFilePathResolver(String springBeansFileName, String springConfigBean, 
			String configSubDirKey, String configurationBean) {
		this.configFilePathResolver = new ConfigFilePathResolverImpl(
				springBeansFileName,
				springConfigBean,
				configSubDirKey,
				configurationBean);
	}

	public String resolveFullPath(String fileName) {
		String result = configFilePathResolver.resolveFullPath(fileName);
		return result;
	}

	public String resolveFullPath(String fileName, String subdir) {
		String result = configFilePathResolver.resolveFullPath(fileName, subdir);
		return result;
	}

	public ConfigFilePathResolver getConfigFilePathResolver() {
		return configFilePathResolver;
	}
	
	public void setConfigFilePathResolver(ConfigFilePathResolver configFilePathResolver) {
		this.configFilePathResolver = configFilePathResolver;
	}
	
	/**
	 * Obtiene la ruta completa del fichero de configuración.
	 * @param configFileName Nombre del fichero de configuración.
	 * @return Ruta completa del fichero de configuración.
	 */
	public static String getConfigFilePath(String configFileName) {
		
		SchedulerConfigFilePathResolver configFilePathResolver = new SchedulerConfigFilePathResolver();
		String fullPath = configFilePathResolver.resolveFullPath(configFileName);
		
		if (logger.isInfoEnabled()) {
			logger.info("Fichero de configuración: " + configFileName + " => " + fullPath);
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
	 * Obtiene el InputStream del fichero de configuración.
	 * @param configFileName Nombre del fichero de configuración.
	 * @return InputStream del fichero de configuración.
	 * @throws FileNotFoundException si el fichero no existe.
	 */
	public static InputStream getConfigFileInputStream(String configFileName) 
			throws FileNotFoundException {
		return new FileInputStream(getConfigFile(configFileName));
	}

}
