package ieci.tecdoc.sgm.consulta_telematico.plugin;

import ieci.tecdoc.sgm.core.config.impl.spring.SigemConfigFilePathResolver;

import java.util.Properties;

public class ConfigLoader {

	public static String CONFIG_SUBDIR="SIGEM_ConsultaRegistroTelematicoBackOfficeWeb";
	public static String CONFIG_FILE="consultaRegistroTelematicoBackOfficeWebConfig.properties";

	protected Properties properties;

	public ConfigLoader(){
		properties= loadConfiguration();
	}

	public String getAplicacionTelematicoValue(){
		String result="";
		result=getValue("aplicacionTelematico");
		return result;
	}

	public String getCarpetaTelematicoValue(){
		String result="";
		result=getValue("carpetaTelematico");
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
