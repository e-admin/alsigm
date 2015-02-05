package ieci.tecdoc.sgm.catalogo_tramites;

import ieci.tecdoc.sgm.core.config.impl.spring.Config;
import ieci.tecdoc.sgm.catalogo_tramites.Configuracion;

import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Clase que implementa métodos relacionados con la configuración
 *
 */
public class Configuracion {
	
	private static final Logger logger = Logger.getLogger(Configuracion.class);
	
	/**
	 * Entrada de las propiedades dentro del archivo de configuración del contenedor de Spring
	 */
	private static final String CATALOGO_TRAMITES_PROPERTIES_ENTRY	= "CatalogoTramites.propiedades";
	
	/**
	 * Constantes
	 */
	private static final String KEY_DATABASE = 				"database";
	private static final String KEY_USER = 					"user";
	private static final String KEY_PASS = 					"password";
	private static final String KEY_DRIVER = 				"driver";
	private static final String KEY_DEBUG = 				"debug";
	private static final String KEY_TIMEOUT = 				"timeout";
    
	private static Properties propiedades;
	
	private static HashMap config = new HashMap();
	
	private static Config configuracion;
	

	static{
		try {
			configuracion = new Config(new String[]{"SIGEM_spring.xml","CatalogoTramites_spring.xml"});
			propiedades = (Properties)configuracion.getBean(CATALOGO_TRAMITES_PROPERTIES_ENTRY);
		} catch (Exception e) {
			logger.error("Error inicializando configuración.", e);
		}

		// Parámetros de configuración de base de datos
		config.put(KEY_DATABASE, (String)propiedades.get(KEY_DATABASE));
		config.put(KEY_USER, (String)propiedades.get(KEY_USER));
		config.put(KEY_PASS, (String)propiedades.get(KEY_PASS));
		config.put(KEY_DRIVER, (String)propiedades.get(KEY_DRIVER));		
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
