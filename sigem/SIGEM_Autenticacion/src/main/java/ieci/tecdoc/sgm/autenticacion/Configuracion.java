package ieci.tecdoc.sgm.autenticacion;

import ieci.tecdoc.sgm.base.dbex.DbConnectionConfig;
import ieci.tecdoc.sgm.core.config.impl.spring.Config;
import ieci.tecdoc.sgm.core.config.impl.spring.SigemConfigFilePathResolver;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.ResourceBundle;

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
    /*public static DbConnectionConfig getDatabaseConnection(){
      ResourceBundle application = ResourceBundle.getBundle("ieci.tecdoc.sgm.autenticacion.resources.application");
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
    /*public static int getTimeout(){
      ResourceBundle application = ResourceBundle.getBundle("ieci.tecdoc.sgm.catalogo_tramites.resources.application");
      String sDebug = application.getString("timeout");
      return new Integer(sDebug).intValue();
    }*/
	
	private static final Logger logger = Logger.getLogger(Configuracion.class);
	
	
	private static final String AUTENTICACION_PROPERTIES_ENTRY	= "Autenticacion.properties";
	
	/**
	 * Constantes
	 */
	private static final String MULTIPLE_KEYS_SEPARATOR =	",";
	private static final String KEY_DATABASE = 				"database";
	private static final String KEY_USER = 					"user";
	private static final String KEY_PASS = 					"password";
	private static final String KEY_DRIVER = 				"driver";
	private static final String KEY_DEBUG = 				"debug";
	private static final String KEY_TIMEOUT = 				"timeout";
	public static final String KEY_PASSWORD = 				"ieci.tecdoc.sgm.autenticacion.password";
	public static final String KEY_ALIAS = 				"ieci.tecdoc.sgm.autenticacion.alias";
	public static final String KEY_CERT_CLASS  = "ieci.tecdoc.sgm.autenticacion.certificateClass";
	public static final String KEY_JUSTIFICANTE_CLASS = "ieci.tecdoc.sgm.autenticacion.justificanteClass";
	
	public  static String SUBDIR_PARAM_NAME="SIGEM_Autenticacion";
	
	private static Properties propiedades;
	
	private static HashMap config = new HashMap();
	
	private static Config configuracion;
	

	static{
		try {
			configuracion = new Config(new String[]{"SIGEM_spring.xml"});
			
			propiedades =Configuracion.getConfigFileAutenticacion();
			
			
		} catch (Exception e) {
			logger.error("Error inicializando configuración.", e);
		}

		// Parámetros de configuración de base de datos
		config.put(KEY_DATABASE, (String)propiedades.get(KEY_DATABASE));
		config.put(KEY_USER, (String)propiedades.get(KEY_USER));
		config.put(KEY_PASS, (String)propiedades.get(KEY_PASS));
		config.put(KEY_DRIVER, (String)propiedades.get(KEY_DRIVER));		
		
		config.put(KEY_PASSWORD, (String)propiedades.get(KEY_PASSWORD));
		config.put(KEY_ALIAS, (String)propiedades.get(KEY_ALIAS));
		config.put(KEY_CERT_CLASS, (String)propiedades.get(KEY_CERT_CLASS));
		config.put(KEY_JUSTIFICANTE_CLASS, (String)propiedades.get(KEY_JUSTIFICANTE_CLASS));
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
    
    
    /**
     * Metodo que obtiene el path al archivo de configuración de autenticacion
     * @return
     */
    public static Properties getConfigFileAutenticacion(){
		Properties result=null;
    	SigemConfigFilePathResolver pathResolver = SigemConfigFilePathResolver.getInstance();
    	result=pathResolver.loadProperties(AUTENTICACION_PROPERTIES_ENTRY,SUBDIR_PARAM_NAME);
		return result;
	}
    
}
