package ieci.tecdoc.sgm.administracion.plugin;

import ieci.tecdoc.sgm.administracion.utils.Defs;
import ieci.tecdoc.sgm.core.config.impl.spring.SigemConfigFilePathResolver;

import java.util.Properties;

public class ConfigLoader {

	public static String CONFIG_SUBDIR="SIGEM_AutenticacionAdministracionWeb";
	public static String CONFIG_FILE="autenticacionAdministracionWebConfig.properties";

	protected Properties properties;


	public ConfigLoader(){
		properties= loadConfiguration();
	}

	public String getSingleSignOnValue(){
		String key=Defs.PLUGIN_SINGLE_SIGN_ON;
		String result=getValue(key);
		return result;
	}

	protected String getValue(String key){
		String result="";
		result= properties.getProperty(key);
		return result;
	}

	protected Properties loadConfiguration(){

		Properties result=null;
		SigemConfigFilePathResolver pathResolver = SigemConfigFilePathResolver.getInstance();
		result= pathResolver.loadProperties(CONFIG_FILE, CONFIG_SUBDIR);

		return result;
	}

}
