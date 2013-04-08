package ieci.tecdoc.sgm.core.services.gestion_administracion;

import ieci.tecdoc.sgm.core.config.impl.spring.Config;

import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class GestionURLsAdministracion {
	private static final Logger logger = Logger.getLogger(GestionURLsAdministracion.class);

	private static final String KEY_LOGIN = "login"; 
	private static final String KEY_LOGOUT = "logout"; 
	
	private static ResourceBundle propiedades;
	
	private static Config configuracion;

	private static HashMap config = new HashMap();
	
	static{
		try {
			propiedades = ResourceBundle.getBundle("ieci.tecdoc.sgm.core.services.gestion_administracion.URLs");
		} catch (Exception e) {
			logger.error("Error inicializando configuración de URLs de administracion.", e);
		}
		if (propiedades != null) {
			String aux = propiedades.getString(KEY_LOGIN);
			config.put(KEY_LOGIN, aux);
			aux = propiedades.getString(KEY_LOGOUT);
			config.put(KEY_LOGOUT, aux);
		}
	}
		
    public static Config getConfiguracion() {
		return configuracion;
	}
    
    public static String getUrlLogin() {
    	return (String)config.get(KEY_LOGIN);
	}
    
    public static String getUrlLogout() {
    	return (String)config.get(KEY_LOGOUT);
	}
    
}
