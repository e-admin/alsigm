package com.ieci.tecdoc.common.isicres;

import java.io.File;

import es.ieci.tecdoc.fwktd.core.spring.configuration.ConfigFilePathResolver;
import es.ieci.tecdoc.fwktd.core.spring.configuration.ConfigFilePathResolverImpl;

public class ConfigFilePathResolverIsicres implements ConfigFilePathResolver {

	protected static String SPRING_BEANS_FILENAME_DEFAULT = "isicres-config-beans.xml";
	protected static String SPRING_CONFIG_BEAN_DEFAULT = "isicresConfigurationResourceLoader";
	protected static String REGISTRO_BASE_CONFIG_SUBDIR_KEY = "CONFIG_SUBDIR";
	protected static String CONFIGURATION_BEAN_DEFAULT = "registroConfigurationBean";

	protected ConfigFilePathResolver configFilePathResolver;

	public static String ISICRES_CONFIGURATION_NAME = "ISicres-Configuration.xml";
	public static String ISICRES_EVENTES_CONFIGURATION_NAME = "ISicres-Events-Configuration.xml";
	public static String ISICRES_EXTENSION_FILES_CONFIGURATION = "ISicres-Extension-Files-Configuration.xml";

	public static String ISICRES_RESOURCES_NAME = "resources/ISicres-resources.properties";
	public static String HIBERNATE_CFG_FILENAME_DEFAULT = "hibernate.cfg.xml";
	public static String QUARTZ_FILENAME_DEFAULT = "quartz.properties";
	public static String LOG4J_FILENAME_DEFAULT = "log4j.xml";

	public static String INTERCAMBIO_REGISTRAL_FILENAME_DEFAULT = "intercambioRegistral.properties";

	static ConfigFilePathResolverIsicres instance;

	public String configSubdir;

	public static synchronized ConfigFilePathResolverIsicres getInstance() {
		if (instance == null)
			instance = new ConfigFilePathResolverIsicres();
		return instance;
	}

	private ConfigFilePathResolverIsicres() {

		String springBeansFileName = SPRING_BEANS_FILENAME_DEFAULT;
		String springConfigBean = SPRING_CONFIG_BEAN_DEFAULT;
		String configSubDirKey = REGISTRO_BASE_CONFIG_SUBDIR_KEY;
		String configurationBean = CONFIGURATION_BEAN_DEFAULT;
		ConfigFilePathResolverImpl configFilePathResolverImpl=new ConfigFilePathResolverImpl(
				springBeansFileName, springConfigBean, configSubDirKey,
				configurationBean);

		this.configSubdir = configFilePathResolverImpl.getConfigSubdirFromConfigurationBean();
		configFilePathResolver =configFilePathResolverImpl;



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

	public String getISicresConfigurationPath() {
		return resolveFullPath(ISICRES_CONFIGURATION_NAME);
	}

	public String getISicresConfigurationEventsPath() {
		return resolveFullPath(ISICRES_EVENTES_CONFIGURATION_NAME);
	}

	public String getISicresExtensionFilesConfigurationPath() {
		return resolveFullPath(ISICRES_EXTENSION_FILES_CONFIGURATION);
	}

	public String getISicresHibernateCfgPath() {
		return resolveFullPath(HIBERNATE_CFG_FILENAME_DEFAULT);
	}

	public String getISicresLog4jPath() {
		return resolveFullPath(LOG4J_FILENAME_DEFAULT);
	}

	public String getISicresLog4jPath(String subDir) {
		return resolveFullPath(LOG4J_FILENAME_DEFAULT,subDir);
	}

	public String getISicresResourcesPath() {
		return resolveFullPath(ISICRES_RESOURCES_NAME);
	}

	public String getISicresQuartzPath() {
		return resolveFullPath(QUARTZ_FILENAME_DEFAULT);
	}

	public String getIntercambioRegistralConfigPath(){
		return resolveFullPath(INTERCAMBIO_REGISTRAL_FILENAME_DEFAULT);
	}
	public String resolveFullPath(String fileName, String subDir) {

		String newFileName=subDir+File.separator+fileName;
		String result= this.resolveFullPath(newFileName);

		return result;
	}

	public String getConfigSubdir() {
		return configSubdir;
	}

	public void setConfigSubdir(String configSubdir) {
		this.configSubdir = configSubdir;
	}

}
