package ieci.tdw.ispac.ispaclib.configuration;

import es.ieci.tecdoc.fwktd.core.spring.configuration.ConfigFilePathResolver;
import es.ieci.tecdoc.fwktd.core.spring.configuration.ConfigFilePathResolverImpl;


/**
 * Clase para obtener la ruta absoluta de los ficheros de configuración.
 *
 */
public class ISPACConfigFilePathResolver implements ConfigFilePathResolver {
	
	protected static String SPRING_BEANS_FILENAME_DEFAULT	= "ispac-config-beans.xml";
	protected static String SPRING_CONFIG_BEAN_DEFAULT		= "ispacConfigurationResourceLoader";
	protected static String ISPAC_BASE_CONFIG_SUBDIR_KEY	= "CONFIG_SUBDIR";
	protected static String CONFIGURATION_BEAN_DEFAULT		= "ispacConfigurationBean";

	protected ConfigFilePathResolver configFilePathResolver = null;

	protected static ISPACConfigFilePathResolver instance = null; 
	
	
	/**
	 * Obtiene una instancia de la clase.
	 * @return Instancia de la clase.
	 */
	public static synchronized ISPACConfigFilePathResolver getInstance() {
		if (instance == null) {
			instance = new ISPACConfigFilePathResolver();
		}
		
		return instance;
	}
	
	/**
	 * Constructor.
	 */
	protected ISPACConfigFilePathResolver() {
		this.configFilePathResolver = new ConfigFilePathResolverImpl(
				SPRING_BEANS_FILENAME_DEFAULT,
				SPRING_CONFIG_BEAN_DEFAULT,
				ISPAC_BASE_CONFIG_SUBDIR_KEY,
				CONFIGURATION_BEAN_DEFAULT);
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
	
}
