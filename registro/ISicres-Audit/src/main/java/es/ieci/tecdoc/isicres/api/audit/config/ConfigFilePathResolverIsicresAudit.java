package es.ieci.tecdoc.isicres.api.audit.config;

import java.io.File;

import es.ieci.tecdoc.fwktd.core.spring.configuration.ConfigFilePathResolver;
import es.ieci.tecdoc.fwktd.core.spring.configuration.ConfigFilePathResolverImpl;
/**
 * Clase para resolver el path de los ficheros de ISicres-Audit
 *
 * @author Blimea
 *
 */
public class ConfigFilePathResolverIsicresAudit implements ConfigFilePathResolver {

	protected static String SPRING_BEANS_FILENAME_DEFAULT = "isicres-config-beans.xml";
	protected static String SPRING_CONFIG_BEAN_DEFAULT = "isicresConfigurationResourceLoader";
	protected static String REGISTRO_BASE_CONFIG_SUBDIR_KEY = "CONFIG_SUBDIR";
	protected static String CONFIGURATION_BEAN_DEFAULT = "registroConfigurationBean";

	protected ConfigFilePathResolver configFilePathResolver;

	static ConfigFilePathResolverIsicresAudit instance;


	private ConfigFilePathResolverIsicresAudit() {

		String springBeansFileName = SPRING_BEANS_FILENAME_DEFAULT;
		String springConfigBean = SPRING_CONFIG_BEAN_DEFAULT;
		String configSubDirKey = REGISTRO_BASE_CONFIG_SUBDIR_KEY;
		String configurationBean = CONFIGURATION_BEAN_DEFAULT;
		configFilePathResolver = new ConfigFilePathResolverImpl(
				springBeansFileName, springConfigBean, configSubDirKey,
				configurationBean);
	}


	public static synchronized ConfigFilePathResolverIsicresAudit getInstance() {
		if (instance == null)
			instance = new ConfigFilePathResolverIsicresAudit();
		return instance;
	}

	public String resolveFullPath(String fileName) {
		String result = configFilePathResolver.resolveFullPath(fileName);
		if(result == null){
			result = fileName;
		}
		return result;
	}

	public ConfigFilePathResolver getConfigFilePathResolver() {
		return configFilePathResolver;
	}

	public void setConfigFilePathResolver(
			ConfigFilePathResolver configFilePathResolver) {
		this.configFilePathResolver = configFilePathResolver;
	}

	public String resolveFullPath(String fileName, String subDir) {

		String newFileName=subDir+File.separator+fileName;
		String result= this.resolveFullPath(newFileName);

		return result;
	}

}
