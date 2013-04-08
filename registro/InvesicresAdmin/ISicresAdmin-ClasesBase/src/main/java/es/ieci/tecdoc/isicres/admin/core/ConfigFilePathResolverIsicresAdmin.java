package es.ieci.tecdoc.isicres.admin.core;

import java.io.File;

import es.ieci.tecdoc.fwktd.core.spring.configuration.ConfigFilePathResolver;
import es.ieci.tecdoc.fwktd.core.spring.configuration.ConfigFilePathResolverImpl;

public class ConfigFilePathResolverIsicresAdmin implements ConfigFilePathResolver {

	protected static String SPRING_BEANS_FILENAME_DEFAULT = "isicresadmin-config-beans.xml";
	protected static String SPRING_CONFIG_BEAN_DEFAULT = "isicresadminConfigurationResourceLoader";
	protected static String REGISTRO_BASE_CONFIG_SUBDIR_KEY = "CONFIG_SUBDIR";
	protected static String CONFIGURATION_BEAN_DEFAULT = "registroAdminConfigurationBean";

	protected ConfigFilePathResolver configFilePathResolver;

	static ConfigFilePathResolverIsicresAdmin instance;

	public static synchronized ConfigFilePathResolverIsicresAdmin getInstance() {
		if (instance == null)
			instance = new ConfigFilePathResolverIsicresAdmin();
		return instance;
	}

	private ConfigFilePathResolverIsicresAdmin() {

		String springBeansFileName = SPRING_BEANS_FILENAME_DEFAULT;
		String springConfigBean = SPRING_CONFIG_BEAN_DEFAULT;
		String configSubDirKey = REGISTRO_BASE_CONFIG_SUBDIR_KEY;
		String configurationBean = CONFIGURATION_BEAN_DEFAULT;
		configFilePathResolver = new ConfigFilePathResolverImpl(
				springBeansFileName, springConfigBean, configSubDirKey,
				configurationBean);
	}

	public String resolveFullPath(String fileName) {
		String result = configFilePathResolver.resolveFullPath(fileName);
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
