package ieci.tecdoc.sgm.ct;

import ieci.tecdoc.sgm.core.config.impl.spring.Config;

import java.util.HashMap;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;


public class Configuracion {
	
	private static final Logger logger = Logger.getLogger(Configuracion.class);
	
	/**
	 * Entrada de las propiedades dentro del archivo de configuración del contenedor de Spring
	 */
	private static final String CONSULTA_PROPERTIES_ENTRY	= "consulta.propiedades";
	
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
    
	private static final String CT_RESOURCES = 				"ieci.tecdoc.sgm.ct.resources.application";
	private static final String KEY_URL_DOCUMENTOS =		"URLDocumentos";
	private static final String KEY_PATH_DOCUMENTOS =		"pathDocumentos";
	private static final String KEY_URL_APORTACION =		"aportacionURL";
	private static final String KEY_URL_NOTIFICACION =		"notificacionURL";
	private static final String KEY_URL_PAGO =		"pagoURL";
	
	private static Properties propiedades;
	
	private static HashMap config = new HashMap();
	
	private static HashMap pagosPermitidos = new HashMap(); 
	
	private static Config configuracion;
	

	static{
		try {
			configuracion = new Config(new String[]{"SIGEM_spring.xml","Consulta_spring.xml"});
			propiedades = (Properties)configuracion.getBean(CONSULTA_PROPERTIES_ENTRY);
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
    
    
    /**
	 * Devuelve la propiedad 'URLDocumentos', del fichero properties de configuración
	 * de la aplicación. Esta propiedad identifica 
	 * una URL para llamar a documentos temporales adjuntos a un hito. 
	 *
	 * @return URL base donde residen los documentos
	 */
	public static String getURLDocumentos(){
		ResourceBundle application = ResourceBundle.getBundle(CT_RESOURCES);
		String URLDocumentos = application.getString(KEY_URL_DOCUMENTOS);
		return URLDocumentos;
	}

	/**
	 * Devuelve la propiedad 'pathDocumentos'. Esta propiedad identifica 
	 * la localizacion en el sistema de ficheros del servidor
	 * donde residen los documentos temporales adjuntos a un hito. 
	 *
	 * @return Path donde residen los documentos
	 */
	public static String getPathDocumentos(){
		ResourceBundle application = ResourceBundle.getBundle(CT_RESOURCES);
		String pathDocumentos = application.getString(KEY_PATH_DOCUMENTOS);
		return pathDocumentos;
	}

	/**
	 * Devuelve la propiedad 'aportacionURL'. Esta propiedad identifica 
	 * una URL variable a la que se llama para proceder a la subsanación de un expediente. 
	 *
	 * @return URL de subsanación.
	 */
	public static String getURLAportacion(){
		ResourceBundle application = ResourceBundle.getBundle(CT_RESOURCES);
		String aportacionURL = application.getString(KEY_URL_APORTACION);
		return aportacionURL;
	}

	/**
	 * Devuelve la propiedad 'notificacionURL'. Esta propiedad identifica 
	 * una URL variable a la que se llama para acceder a las notificaciones de los Expedientes
	 * de un Interesado. 
	 *
	 * @return URL de subsanación.
	 */

	public static String getURLNotificacion(){
		ResourceBundle application = ResourceBundle.getBundle(CT_RESOURCES);
		String notificacionURL = application.getString(KEY_URL_NOTIFICACION);
		return notificacionURL;
	}
	
	
	/**
	 * Devuelve la propiedad 'pagoURL'. Esta propiedad identifica 
	 * una URL variable a la que se llama para proceder al pago de tasas.. 
	 *
	 * @return URL de pago..
	 */
	public static String getURLPago(){
		ResourceBundle application = ResourceBundle.getBundle(CT_RESOURCES);
		String pagoURL = application.getString(KEY_URL_PAGO);
		return pagoURL;
	}
}
