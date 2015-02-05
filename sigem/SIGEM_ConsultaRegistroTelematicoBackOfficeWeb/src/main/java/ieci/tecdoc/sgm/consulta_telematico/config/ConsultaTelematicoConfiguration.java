package ieci.tecdoc.sgm.consulta_telematico.config;

import ieci.tecdoc.sgm.core.config.impl.spring.Config;

public class ConsultaTelematicoConfiguration {

	private static Config configuracion;
	
	private static final String CONSULTA_REGISTRO_CONFIG_FILE = "consultaRegistroTelematico-beans.xml";

	static{
		configuracion = new Config(CONSULTA_REGISTRO_CONFIG_FILE);
	}
	
	public static Config getConfiguration(){
		return configuracion;
	}	
	
	
	
}
