package ieci.tecdoc.sgm.certificacion.config.impl;

public class DefaultConfiguration {

	private static Config configuracion;
	
	static{
		configuracion = new Config();
	}
	
	public static Config getConfiguration(){
		return configuracion;
	}	
	
}
