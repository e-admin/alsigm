package ieci.tecdoc.sgm.core.services.gestion_backoffice;

import ieci.tecdoc.sgm.core.config.impl.spring.Config;

import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class GestionURLsBackOffice {
	private static final Logger logger = Logger.getLogger(GestionURLsBackOffice.class);

	private static final String KEY_URL = "Url";
	private static final String KEY_LOGIN = "login";
	private static final String KEY_LOGOUT = "logout";
	private static final String SEPARADOR = ".";

	private static ResourceBundle propiedades;

	private static Config configuracion;

	private static HashMap config = new HashMap();

	static{
		try {
			propiedades = ResourceBundle.getBundle("ieci.tecdoc.sgm.core.services.gestion_backoffice.URLs");
		} catch (Exception e) {
			logger.error("Error inicializando configuración de URLs de back office.", e);
		}
		if (propiedades != null) {
			String aux = propiedades.getString(ConstantesGestionUsuariosBackOffice.APLICACION_ARCHIVO);
			config.put(KEY_URL + ConstantesGestionUsuariosBackOffice.APLICACION_ARCHIVO, aux);
			aux = propiedades.getString(ConstantesGestionUsuariosBackOffice.APLICACION_REGISTRO_PRESENCIAL);
			config.put(KEY_URL + ConstantesGestionUsuariosBackOffice.APLICACION_REGISTRO_PRESENCIAL, aux);
			aux = propiedades.getString(ConstantesGestionUsuariosBackOffice.APLICACION_TRAMITADOR_MANAGER);
			config.put(KEY_URL + ConstantesGestionUsuariosBackOffice.APLICACION_TRAMITADOR_MANAGER, aux);
			aux = propiedades.getString(ConstantesGestionUsuariosBackOffice.APLICACION_BUSCADOR_DOCUMENTOS);
			config.put(KEY_URL + ConstantesGestionUsuariosBackOffice.APLICACION_BUSCADOR_DOCUMENTOS, aux);
			aux = propiedades.getString(ConstantesGestionUsuariosBackOffice.APLICACION_CONSULTA_EXPEDIENTES);
			config.put(KEY_URL + ConstantesGestionUsuariosBackOffice.APLICACION_CONSULTA_EXPEDIENTES, aux);
			aux = propiedades.getString(ConstantesGestionUsuariosBackOffice.APLICACION_CONSULTA_REGISTROS_TELEMATICOS);
			config.put(KEY_URL + ConstantesGestionUsuariosBackOffice.APLICACION_CONSULTA_REGISTROS_TELEMATICOS, aux);
			aux = propiedades.getString(KEY_LOGIN);
			config.put(KEY_LOGIN, aux);
			aux = propiedades.getString(KEY_LOGOUT);
			config.put(KEY_LOGOUT, aux);
		}
	}

    public static Config getConfiguracion() {
		return configuracion;
	}

    public static String getUrlAplicacion(String idAplicacion) {
    	return (String)config.get(KEY_URL + idAplicacion);
	}

    public static String getUrlLogin() {
    	return (String)config.get(KEY_LOGIN);
	}

    public static String getUrlLogout() {
    	return (String)config.get(KEY_LOGOUT);
	}

}
