package ieci.tecdoc.sgm.core.config.impl.spring;

public class DefaultConfiguration {

	private static Config configuracion;
	
	static{
		configuracion = new Config();
	}
	
	public static Config getConfiguration(){
		return configuracion;
	}	
	
}
