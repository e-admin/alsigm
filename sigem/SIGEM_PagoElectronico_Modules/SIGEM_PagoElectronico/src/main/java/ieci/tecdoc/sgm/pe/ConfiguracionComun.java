package ieci.tecdoc.sgm.pe;
/*
 * $Id: Configuracion.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
import ieci.tecdoc.sgm.core.config.impl.spring.Config;
import ieci.tecdoc.sgm.core.config.impl.spring.SigemConfigFilePathResolver;

import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;


public class ConfiguracionComun {
	
	private static final Logger logger = Logger.getLogger(ConfiguracionComun.class);
	
	/**
	 * Entrada de las propiedades dentro del archivo de configuración del contenedor de Spring
	 */
	private static final String PAGO_PROPERTIES_FILE	= "PagoElectronico.properties";
	private static final String CONFIG_DIR="SIGEM_PagoElectronico";
	
	/**
	 * Constantes
	 */
	private static final String MULTIPLE_VALUES_SEPARATOR = ",";
//	private static final String KEY_DATABASE = 				"database";
//	private static final String KEY_USER = 					"user";
//	private static final String KEY_PASS = 					"password";
//	private static final String KEY_DRIVER = 				"driver";
	private static final String KEY_DEBUG = 				"debug";
	private static final String KEY_TIMEOUT = 				"timeout";
	private static final String KEY_PAGOS_PERMITIDOS =		"ieci.tec.doc.sgm.pe.PagoElectronicoManager.pagosPermitidos";
//	public static final String KEY_SPE_IMPLEMENTACION =	"ieci.tec.doc.sgm.pe.SistemaPagoElectronicoFactory.implementacion";

	private static final String KEY_USAR_PASARELA_PAGO ="ieci.tecdoc.sgm.pe.pasarelaPago";
	public static final String KEY_CUADERNO60_M1_IDENT1 =  "ieci.tec.doc.sgm.pe.PagoElectronicoManager.Cuaderno60.Modalidad1.ident1";
    
	public static final String KEY_PROXY_HOST = "ieci.tecdoc.sgm.pe.proxyHost";
	public static final String KEY_PROXY_PORT = "ieci.tecdoc.sgm.pe.proxyPort";
	public static final String KEY_PROXY_USER = "ieci.tecdoc.sgm.pe.proxyUser";
	public static final String KEY_PROXY_PASS = "ieci.tecdoc.sgm.pe.proxyPassword";
	public static final String KEY_NON_PROXY_HOSTS = "ieci.tecdoc.sgm.pe.nonProxyHosts";
	public static final String KEY_JAVA_NET_DEBUG = 	"ieci.tecdoc.sgm.pe.javax.net.debug";
	public static final String KEY_JAVA_NET_SSL_TRUSTSTORE = "ieci.tecdoc.sgm.pe.javax.net.ssl.trustStore";
	public static final String KEY_JAVA_NET_SSL_TRUSTSTORE_PASS = "ieci.tecdoc.sgm.pe.javax.net.ssl.trustStorePassword";
	
	public static final String KEY_TYPES_MAPPING_CPR = "ieci.tecdoc.sgm.pe.param.mapping.types.cpr";
	
	public static final String KEY_CONECTOR_BEAN="ieci.tec.doc.sgm.pe.SistemaPagoElectronicoConnector.bean";
	
	private static Properties propiedades;
	private static HashMap pagosPermitidos = new HashMap(); 
	private static Config configuracion;
	private static Map mapeoTiposCpr=new HashMap();

	static{
		try {
			configuracion = new Config(new String[]{"SIGEM_spring.xml","Pago_spring.xml"});
			SigemConfigFilePathResolver pathResolver = SigemConfigFilePathResolver.getInstance();
			propiedades= pathResolver.loadProperties(PAGO_PROPERTIES_FILE, CONFIG_DIR);
		} catch (Exception e) {
			logger.error("Error inicializando configuración.", e);
		}

		// Parámetros de configuración generales
		String cPagosPermitidos = (String)propiedades.get(KEY_PAGOS_PERMITIDOS);
		StringTokenizer stPagosPermitidos = new StringTokenizer(cPagosPermitidos, MULTIPLE_VALUES_SEPARATOR);
		String cToken = null;
		while(stPagosPermitidos.hasMoreTokens()){
			cToken = stPagosPermitidos.nextToken();
			pagosPermitidos.put(cToken, cToken);
		}
		rellenarMapeosTiposCpr();
	}
	
	private static void rellenarMapeosTiposCpr(){
		String cTiposCpr = null;
		String cTiposGenericos = null;
		try{
			cTiposCpr = (String)propiedades.getProperty(KEY_TYPES_MAPPING_CPR);
			cTiposGenericos = (String)propiedades.getProperty(KEY_PAGOS_PERMITIDOS);
		}catch(MissingResourceException e){
			logger.fatal("Error cargando parámetros necesarios de configuración.");
		}
		
		StringTokenizer stTiposCpr = new StringTokenizer(cTiposCpr, MULTIPLE_VALUES_SEPARATOR);
		StringTokenizer stTiposGenericos = new StringTokenizer(cTiposGenericos, MULTIPLE_VALUES_SEPARATOR);
		if(stTiposGenericos.countTokens() != stTiposCpr.countTokens()){
			logger.fatal("Error en la configuración de los mapeos de tipos de liquidaciones.");
		}else{
			String cTipoCpr = null;
			String cTipoGenerico = null;
			while(stTiposGenericos.hasMoreTokens()){
				cTipoCpr = stTiposCpr.nextToken();
				cTipoGenerico = stTiposGenericos.nextToken();
				mapeoTiposCpr.put(cTipoCpr, cTipoGenerico);
				mapeoTiposCpr.put(cTipoGenerico, cTipoCpr);
			}
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
        String sDebug = (String)propiedades.get(KEY_DEBUG);
        if (sDebug!=null && sDebug.equals("true"))
          return true;
        else return false;
    }
    
    /**
     * Método que devuelve el timeout
     * @return int
     */
    public static int getTimeout(){
      String sTimeout = (String)propiedades.get(KEY_TIMEOUT);
      return new Integer(sTimeout).intValue();
    }
    
    /**
     * Método que comprueba si un tipo de tasa está soportada por el sistema de pago telemático
     * @param pcTipoTasa Nombre del tipo de tasa.
     * @return True si el tipo de tasa está permitodo.
     */
    public static boolean pagoPermitido(String pcTipoTasa){
    	if((pcTipoTasa != null) && (!"".equals(pcTipoTasa))){
        	String cClave = (String)pagosPermitidos.get(pcTipoTasa);    
        	if((cClave != null)){
        		return true;
        	}
    	}
    	return false;
    }
    
    /**
     * Método que devuelve el valor de una propiedad de configuración.
     * @param pcClave Nombre de la propiedad
     * @return String Valor de la propiedad.
     */
    public static String obtenerPropiedad(String pcClave){
    	String cRetorno = null;
    	if((pcClave != null) && (!"".equals(pcClave))){
        	cRetorno = (String)propiedades.get(pcClave);
    	}    	
    	return cRetorno;
    }
    
    public static boolean usarPasarelaPagoExternaConRedireccion(){
    	String usarPasarela = (String)propiedades.get(KEY_USAR_PASARELA_PAGO);
    	if(StringUtils.isEmpty(usarPasarela)) return false;
        return usarPasarela.toLowerCase().equals(""+Boolean.TRUE.booleanValue());
    }
    
    /**
     * Método que obtiene el código interno CPR para cada tipo de liquidación
     * @param pcClave Código de liquidación.
     * @return String código interno de redes
     */
    public static String obtenerCPRModalidad(String pcTipoLiquidacion){
    	return (String)mapeoTiposCpr.get(pcTipoLiquidacion);
    }
}
