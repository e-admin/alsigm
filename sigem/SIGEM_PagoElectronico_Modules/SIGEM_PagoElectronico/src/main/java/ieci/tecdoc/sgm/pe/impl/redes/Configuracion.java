package ieci.tecdoc.sgm.pe.impl.redes;
/*
 * $Id: Configuracion.java,v 1.2.2.1 2008/01/25 12:30:16 jconca Exp $
 */
import ieci.tecdoc.sgm.core.config.impl.spring.SigemConfigFilePathResolver;

import java.io.File;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class Configuracion {
	
	/**
	 * Instancia del logger
	 */
	private static final Logger logger = Logger.getLogger(Configuracion.class);
	
	/**
	 * Entrada de las propiedades dentro del archivo de configuración del contenedor de Spring
	 */
	private static final String PAGO_REDES_PROPERTIES_ENTRY	= "SPTRedes.properties";
	private static final String CRYPTO_REDES_PROPERTIES_ENTRY	= "crypto.properties";
	private static final String CONFIG_DIR="SIGEM_PagoElectronico"+File.separator+"red.es";
	
	/**
	 * Constantes
	 */
	private static final String ERRORS_RESOURCES_FILE = "ieci.tecdoc.sgm.pe.impl.redes.resources.SPTRedesCodigosError";
	
	public static final String KEY_PREFIX = "ieci.tecdoc.sgm.pe.impl.redes.RedesSistemaPagoElectronico.";
	public static final String KEY_REDES_ERRORS_PREFIX = "ieci.tecdoc.sgm.pe.impl.redes.RedesSistemaPagoElectronico.redes.error.code.";
	public static final String KEY_ERRORS_PREFIX = "ieci.tecdoc.sgm.pe.impl.redes.RedesSistemaPagoElectronico.error.code."; 
	
	public static final String KEY_TIPO_PETICION_ALTA = "param.tipoPeticionAlta";
	public static final String KEY_TIPO_PETICION_CONSULTA = "param.tipoPeticionConsulta";
	public static final String KEY_PASARELA = "param.pasarela";
	public static final String KEY_FIRMA_ALIAS = "param.firma.alias";
	public static final String KEY_FIRMA_PASS = "param.firma.pass";
	public static final String KEY_FIRMA_SOAP_PAGO6012_HANDLER_CLASS = "param.pago60_1_2.firma.handler.class";
	public static final String KEY_SISTEMA_PAGO_EXCEPTION_PREFIX = "param.exception.codes.prefix";
	public static final String KEY_SISTEMA_PAGO_EXCEPTION_MASK = "param.exception.codes.mask";
	public static final String KEY_SISTEMA_PAGO_DEFAULT_EXCEPTION_CODE = "param.exception.codes.default";
	public static final String KEY_CONSULTA_COD_ORGANISMO = "param.consulta.codOrganismo";
	
	public static final String KEY_SISTEMA_PAGO_EXCEPTION_MAPPING_SUFFIX = ".mapping";
	public static final String KEY_RESERVADO = "param.reservado";
	public static final String KEY_URL_RETORNO = "param.urlRetorno";
	public static final String KEY_ENDPOINT_URL = "endpoint.url";
	

    
	private static Properties propiedades;
	private static Properties crypto;	
	
	/**
	 * Códigos de error
	 */
	private static ResourceBundle errors;
			
	static{
		try {
			SigemConfigFilePathResolver pathResolver = SigemConfigFilePathResolver.getInstance();
			propiedades= pathResolver.loadProperties(PAGO_REDES_PROPERTIES_ENTRY, CONFIG_DIR);
			crypto = pathResolver.loadProperties(CRYPTO_REDES_PROPERTIES_ENTRY, CONFIG_DIR);
		} catch (Exception e) {
			logger.error("Error inicializando configuración.", e);
		}
//		config =  ResourceBundle.getBundle(RESOURCES_FILE);
		errors = ResourceBundle.getBundle(ERRORS_RESOURCES_FILE);
		
		
	}
	
//	/**
//	 * Método que devuelve la única instancia de la configuración del módulo.
//	 * @return Configuracion Objeto con la configuración del módulo.
//	 */
//	public static Configuracion getInstance(){
//		if(oInstance == null){
//			synchronized (Configuracion.class) {
//				if(oInstance == null){
//					oInstance = new Configuracion();
//				}
//			}
//		}
//		return oInstance;
//	}
	
    /**
     * Método que devuelve el valor de una propiedad de configuración.
     * @param pcClave Nombre de la propiedad
     * @return String Valor de la propiedad.
     */
    public static String obtenerPropiedad(String pcClave){
    	String cRetorno = null;
    	if((pcClave != null) && (!"".equals(pcClave))){
    		try{
    			cRetorno = (String)propiedades.getProperty(KEY_PREFIX + pcClave);
    		}catch(MissingResourceException e){
    			return null;
    		}
    	}    	
    	return cRetorno;
    }
    
    /**
     * Método que devuelve el mensaje de error asociado a un código de error de Red.es
     * @param pcClave clave del mensaje
     * @return String Mensaje de error.
     */
    public static String obtenerMensajeErrorRedes(String pcClave){
    	String cRetorno = null;
    	if((pcClave != null) && (!"".equals(pcClave))){
    		try{
    			cRetorno = (String)errors.getString(KEY_REDES_ERRORS_PREFIX + pcClave);
    		}catch(MissingResourceException e){
    			return null;
    		}
    	}    	
    	return cRetorno;
    }
    
    /**
     * Método que devuelve el mensaje de error asociado a un código de error
     * @param pcClave clave del mensaje
     * @return String Mensaje de error.
     */
    public static String obtenerMensajeError(String pcClave){
    	String cRetorno = null;
    	if((pcClave != null) && (!"".equals(pcClave))){
    		try{
    			cRetorno = (String)errors.getString(KEY_ERRORS_PREFIX + pcClave);
    		}catch(MissingResourceException e){
    			return null;
    		}
    	}    	
    	return cRetorno;
    }
    
    /**
     * Método que traduce el código de error recibido de redes por un código de error 
     * del sistema de pago electrónico.
     * @param pcClave Código de error de Red.es
     * @return long identificador del error
     */
    public static long obtenerCodigoExcepcionSistemaPago(String pcClave){
    	long cRetorno = -1;
    	if((pcClave != null) && (!"".equals(pcClave))){
    		String cClave = pcClave;
    		try{
    			Long.valueOf(cClave);
    		}catch(NumberFormatException e){
    			// no es un numero hay que utilizar el mapeo
    			StringBuffer sbClaveMapeo = new StringBuffer(KEY_REDES_ERRORS_PREFIX);
    			sbClaveMapeo.append(cClave).append(KEY_SISTEMA_PAGO_EXCEPTION_MAPPING_SUFFIX);
    			cClave = errors.getString(sbClaveMapeo.toString());
    		}
    		try{
    			StringBuffer sbCodigo = new StringBuffer(propiedades.getProperty(KEY_PREFIX + KEY_SISTEMA_PAGO_EXCEPTION_MASK));
    			sbCodigo.append(cClave);
    			String cCodigo = null; 
    			try{
    				cCodigo = sbCodigo.substring(cClave.length(), sbCodigo.length());
    			}catch(StringIndexOutOfBoundsException e){
    				StringBuffer sbError = new StringBuffer("Error obteniendo código de excepción del sistema de pago electrónico. Codigo:");
    				sbError.append(cClave);
    				logger.error(sbError.toString());
    				logger.error("Devolviendo código de error por defecto.");
    				return Long.valueOf(propiedades.getProperty(KEY_SISTEMA_PAGO_DEFAULT_EXCEPTION_CODE)).longValue();
    			}
    				
    			StringBuffer sbPrefix = new StringBuffer(propiedades.getProperty(KEY_PREFIX + KEY_SISTEMA_PAGO_EXCEPTION_PREFIX));
    			cRetorno = Long.valueOf(sbPrefix.append(cCodigo).toString()).longValue();
    		}catch(MissingResourceException e){
    			logger.error("Error obteniendo recursos para el código de excepción.");
    			logger.error("Devolviendo código de error por defecto");
    			return Long.valueOf(propiedades.getProperty(KEY_SISTEMA_PAGO_DEFAULT_EXCEPTION_CODE)).longValue();
    		}
    	}    	
    	return cRetorno;    	
    }

	public static Properties getCrypto() {
		return crypto;
	}
}
