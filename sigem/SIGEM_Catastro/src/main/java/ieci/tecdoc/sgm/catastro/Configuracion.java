package ieci.tecdoc.sgm.catastro;

import ieci.tecdoc.sgm.core.config.impl.spring.SigemConfigFilePathResolver;

import java.util.Properties;

import org.apache.log4j.Logger;

public class Configuracion {

	private static final Logger logger = Logger.getLogger(Configuracion.class);

	/**
	 * Entrada de las propiedades dentro del archivo de configuración del
	 * contenedor de Spring
	 */
	private static final String CATASTRO_PROPERTIES_ENTRY = "Catastro.properties";

	private static String SUBDIR_PARAM_NAME = "SIGEM_Catastro";

	/**
	 * Constantes
	 */
	public static final String KEY_SECURITY_MODE = "security.mode";
	public static final String KEY_SECURITY_USERTOKEN_USER = "security.usertoken.user";
	public static final String KEY_SECURITY_USERTOKEN_PASS = "security.usertoken.password";
	public static final String KEY_SECURITY_USERTOKEN_PASSTYPE = "security.usertoken.passwordType";
	public static final String KEY_PROXY_HOST = "http.proxyHost";
	public static final String KEY_PROXY_PORT = "http.proxyPort";
	public static final String KEY_PROXY_USER = "http.proxyUser";
	public static final String KEY_PROXY_PASS = "http.proxyPassword";
	public static final String KEY_NON_PROXY_HOSTS = "http.nonProxyHosts";
	public static final String KEY_WS_URL = "service.url";

	private static final String KEY_DEBUG = "debug";
	private static final String KEY_TIMEOUT = "timeout";

	private static Properties config = new Properties();

	static {
		try {
			config.putAll(getConfigFileAutenticacion());
		} catch (Exception e) {
			logger.error("Error inicializando configuración.", e);
		}
	}

	/**
	 * Método que indica si la aplicación tiene habilitado el debug.
	 * 
	 * @return boolean
	 */
	public static boolean getIsDebugeable() {
		String sDebug = (String) config.get(KEY_DEBUG);
		if (sDebug != null && sDebug.equals("true"))
			return true;
		else
			return false;
	}

	/**
	 * Método que devuelve el timeout
	 * 
	 * @return int
	 */
	public static int getTimeout() {
		String sTimeout = (String) config.get(KEY_TIMEOUT);
		return new Integer(sTimeout).intValue();
	}

	/**
	 * Método que devuelve el valor de una propiedad de configuración.
	 * 
	 * @param pcClave
	 *            Nombre de la propiedad
	 * @return String Valor de la propiedad.
	 */
	public static String obtenerPropiedad(String pcClave) {
		String cRetorno = null;
		if ((pcClave != null) && (!"".equals(pcClave))) {
			cRetorno = (String) config.get(pcClave);
		}
		return cRetorno;
	}

	/**
	 * Metodo que obtiene el path al archivo de configuración de autenticacion
	 * 
	 * @return
	 */
	public static Properties getConfigFileAutenticacion() {
		return SigemConfigFilePathResolver.getInstance().loadProperties(
				CATASTRO_PROPERTIES_ENTRY, SUBDIR_PARAM_NAME);
	}
}
