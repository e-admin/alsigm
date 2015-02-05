package ieci.tecdoc.sgm.registro;

import ieci.tecdoc.sgm.core.config.impl.spring.Config;

import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Clase que implementa métodos relacionados con la configuración
 *
 */
public class Configuracion {
    //static ResourceBundle application = ResourceBundle.getBundle("application");
    
	/**
	 * Obtiene los datos necesarios para conectar con la bse de datos
	 * @return Objeto de conexión a la base de datos
	 */
 /*   public static DbConnectionConfig getDatabaseConnection(){
      ResourceBundle application = ResourceBundle.getBundle("ieci.tecdoc.sgm.registro.resources.application");
        String base = application.getString("database");
        String user = application.getString("user");
        String password = application.getString("password");
        String driver = application.getString("driver");
        
        DbConnectionConfig dbcc = new DbConnectionConfig(driver, base, user, password);
        return dbcc;
    }*/
    
    /**
     * Devuelve si se debe o no almacenar las operaciones realizadas en un log
     * @return true si se debe almacenar en el log, false en caso contrario
     */
    /*public static boolean getIsDebugeable(){
      ResourceBundle application = ResourceBundle.getBundle("ieci.tecdoc.sgm.catalogo_tramites.resources.application");
      String sDebug = application.getString("debug");
      if (sDebug!=null && sDebug.equals("true"))
        return true;
      else return false;
  }*/
    
    /**
     * Devuelve el tipo máximo de duración de las sesiones de usuario
     * @return Tiempo en minutos de duración de las sesiones
     */
   /* public static int getTimeout(){
      ResourceBundle application = ResourceBundle.getBundle("ieci.tecdoc.sgm.catalogo_tramites.resources.application");
      String sDebug = application.getString("timeout");
      return new Integer(sDebug).intValue();
    }*/
	
	
	private static final Logger logger = Logger.getLogger(Configuracion.class);
	
	/**
	 * Entrada de las propiedades dentro del archivo de configuración del contenedor de Spring
	 */
	private static final String REGISTRO_PROPERTIES_ENTRY	= "registro.propiedades";
	
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
			configuracion = new Config(new String[]{"SIGEM_spring.xml","Registro_spring.xml"});
			propiedades = (Properties)configuracion.getBean(REGISTRO_PROPERTIES_ENTRY);
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
