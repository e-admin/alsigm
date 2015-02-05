package ieci.tdw.ispac.ispacmgr.intermediacion.services;

public class Constantes {

	/**
	 * 
	 * Nombre de variable de sistema en SPAC_VARS que contiene la relacion entre procedimientos administrativos y procedimientos del Cliente ligero
	 */
	public static final String RELACION_PCDS_SVD = "RELACION_PCDS_SVD";
	/**
	 * Nombre de variable de sistema en SPAC_VARS que contiene los servicios de intermediacion que solo necesitan datos genericos
	 */ 
	public static final String SERVICIOS_SVD_DATOS_GENERICOS = "SERVICIOS_SVD_DATOS_GENERICOS";	
	
    /**
     * Nombre del atributo a introducir en la sesion con el valor del nif del certificado de usuario seleccionado 
     * 
     */
	public static final String NIF_CERTIFICADO_SELECCIONADO = "NIF_CERTIFICADO_SELECCIONADO";
    
    /**
     * Nombre del atributo a introducir en la sesion con el valor del nombre del certificado de usuario seleccionado 
     */
    public static final String NOMBRE_CERTIFICADO_SELECCIONADO = "NOMBRE_CERTIFICADO_SELECCIONADO";

	
    /**
     * Nombre de la propiedad en ispac.propertes que contiene la url de servicio web Recubrimiento de la Plataforma de Intermediacion 
     */
    public static final String RECUBRIMIENTOWS_SERVICE_WS_URL = "RECUBRIMIENTOWS_SERVICE_WS_URL";
    

    /**
     * Nombre de la propiedad en ispac.propertes que contiene la url de servicio web del Cliente Ligero de la Plataforma de Intermediacion
     */
    public static final String CLIENTE_LIGERO_SERVICE_WS_URL = "CLIENTE_LIGERO_SERVICE_WS_URL";

    /**
     * Nombre de la propiedad en ispac.propertes que contiene la url de la aplicacion web Cliente Ligero de la Plataforma de Intermediacion 
     */
    public static final String CLIENTE_LIGERO_WEB_URL = "CLIENTE_LIGERO_WEB_URL";
    
    
    /**
     * Nombre de la propiedad en ispac.propertes que contiene el identificador del solicitante (maximo 10 caracteres) para la invocacion a los servicios de la Plataforma de Intermediacion 
     */
    public static final String INTERMEDIACION_ID_SOLICITANTE = "INTERMEDIACION_ID_SOLICITANTE";

    /**
     * Nombre de la propiedad en ispac.propertes que contiene el nombre del solicitante para la invocacion a los servicios de la Plataforma de Intermediacion 
     */
    public static final String INTERMEDIACION_NOMBRE_SOLICITANTE = "INTERMEDIACION_NOMBRE_SOLICITANTE";
    
    
    /**
     * Parametro en el que incluira la url de la aplicacion web Cliente Ligero de la Plataforma de Intermediacion
     */
    public static final String CLIENTE_LIGERO_WEB_URL_PARAM = "CLIENTE_LIGERO_WEB_URL_PARAM";
    
}
