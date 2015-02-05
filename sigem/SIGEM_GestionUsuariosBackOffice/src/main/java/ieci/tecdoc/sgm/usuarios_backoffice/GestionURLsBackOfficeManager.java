package ieci.tecdoc.sgm.usuarios_backoffice;

import ieci.tecdoc.sgm.core.services.gestion_backoffice.GestionURLsBackOffice;
import ieci.tecdoc.sgm.usuarios_backoffice.exception.CodigosErrorGestionUsuariosBackOfficeException;
import ieci.tecdoc.sgm.usuarios_backoffice.exception.GestionUsuariosBackOfficeException;

import org.apache.log4j.Logger;

public class GestionURLsBackOfficeManager {
	
	private static final Logger logger = Logger.getLogger(GestionURLsBackOfficeManager.class);
	
	public static String obtenerUrlAplicacion(String idAplicacion) throws GestionUsuariosBackOfficeException {
		try {
			return GestionURLsBackOffice.getUrlAplicacion(idAplicacion);
		} catch(Exception e) {
			logger.error("Error al obtener URL de aplicacion: " + idAplicacion, e);
    		throw new GestionUsuariosBackOfficeException(CodigosErrorGestionUsuariosBackOfficeException.EC_OBTENER_URL_APLICACION, e);
		}
	}
	
	public static String obtenerUrlLogin() throws GestionUsuariosBackOfficeException {
		try {
			return GestionURLsBackOffice.getUrlLogin();
		} catch(Exception e) {
			logger.error("Error al obtener URL de login", e);
    		throw new GestionUsuariosBackOfficeException(CodigosErrorGestionUsuariosBackOfficeException.EC_OBTENER_URL_LOGIN, e);
		}
	}
	
	public static String obtenerUrlLogout() throws GestionUsuariosBackOfficeException {
		try {
			return GestionURLsBackOffice.getUrlLogout();
		} catch(Exception e) {
			logger.error("Error al obtener URL de logout", e);
    		throw new GestionUsuariosBackOfficeException(CodigosErrorGestionUsuariosBackOfficeException.EC_OBTENER_URL_LOGOUT, e);
		}
	}
}
