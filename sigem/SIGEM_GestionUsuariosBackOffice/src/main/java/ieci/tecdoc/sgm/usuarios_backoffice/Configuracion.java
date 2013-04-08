package ieci.tecdoc.sgm.usuarios_backoffice;

import ieci.tecdoc.sgm.core.config.impl.spring.Config;

import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * Clase que implementa ciertos aspectos de configuración
 *
 */
public class Configuracion {
	
	private static final Logger logger = Logger.getLogger(Configuracion.class);
	
	/**
	 * Constantes
	 */
	private static final String KEY_DEBUG = 				"debug";
	private static final String KEY_TIMEOUT = 				"timeout";
	
	private static HashMap config = new HashMap();
	
	private static Config configuracion;
	

	static{
		try {
			configuracion = new Config(new String[]{"SIGEM_spring.xml"});
		} catch (Exception e) {
			logger.error("Error inicializando configuración.", e);
		}
	}
		
    public static Config getConfiguracion() {
		return configuracion;
	}

	/**
     * Método que indica si la aplicación tiene habilitado el debug.
     * @return boolean
     */
    public static boolean getIsDebugeable(){
        String sDebug = (String)config.get(KEY_DEBUG);
        if (sDebug!=null && sDebug.equals("true"))
          return true;
        else return false;
    }
    
    /**
     * Método que devuelve el timeout
     * @return int
     */
    public static int getTimeout(){
      String sTimeout = (String)config.get(KEY_TIMEOUT);
      return new Integer(sTimeout).intValue();
    }
  
    
    /**
     * Método que devuelve el valor de una propiedad de configuración.
     * @param pcClave Nombre de la propiedad
     * @return String Valor de la propiedad.
     */
    public static String obtenerPropiedad(String pcClave){
    	String cRetorno = null;
    	if((pcClave != null) && (!"".equals(pcClave))){
        	cRetorno = (String)config.get(pcClave);
    	}    	
    	return cRetorno;
    }

}
