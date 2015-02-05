package es.ieci.tecdoc.fwktd.sir.ws.servlet;


import java.io.File;
import es.ieci.tecdoc.fwktd.core.spring.configuration.ConfigFilePathResolver;
import es.ieci.tecdoc.fwktd.core.spring.configuration.ConfigFilePathResolverImpl;

public class ServicioIntercambioRegistralConfigFilePathResolver implements ConfigFilePathResolver {

	protected static String SPRING_BEANS_FILENAME_DEFAULT = "beans/fwktd-sir-ws/fwktd-sir-ws-config-beans.xml";
	protected static String SPRING_CONFIG_BEAN_DEFAULT = "fwktd_sir_ws_configurationResourceLoader";
	protected static String INVESFORM_BASE_CONFIG_SUBDIR_KEY = "CONFIG_SUBDIR";
	protected static String CONFIGURATION_BEAN_DEFAULT = "fwktd_sir_ws_configurationBean";

	protected ConfigFilePathResolver configFilePathResolver;

	static ServicioIntercambioRegistralConfigFilePathResolver instance;

	public static synchronized ServicioIntercambioRegistralConfigFilePathResolver getInstance() {
		if (instance == null)
			instance = new ServicioIntercambioRegistralConfigFilePathResolver();
		return instance;
	}

	protected ServicioIntercambioRegistralConfigFilePathResolver(){
		String springBeansFileName = SPRING_BEANS_FILENAME_DEFAULT;
		String springConfigBean = SPRING_CONFIG_BEAN_DEFAULT;
		String configSubDirKey = INVESFORM_BASE_CONFIG_SUBDIR_KEY;
		String configurationBean = CONFIGURATION_BEAN_DEFAULT;
		configFilePathResolver = new ConfigFilePathResolverImpl(
				springBeansFileName, springConfigBean, configSubDirKey,
				configurationBean);
	}

	public ConfigFilePathResolver getConfigFilePathResolver() {
		return configFilePathResolver;
	}

	public void setConfigFilePathResolver(
			ConfigFilePathResolver configFilePathResolver) {
		this.configFilePathResolver = configFilePathResolver;
	}


	public String resolveFullPath(String fileName) {
		String result = configFilePathResolver.resolveFullPath(fileName);
		return result;
	}



	public String resolveFullPath(String fileName, String subDir) {
		String newFileName=subDir+File.separator+fileName;
		String result= this.resolveFullPath(newFileName);

		return result;
	}

}
