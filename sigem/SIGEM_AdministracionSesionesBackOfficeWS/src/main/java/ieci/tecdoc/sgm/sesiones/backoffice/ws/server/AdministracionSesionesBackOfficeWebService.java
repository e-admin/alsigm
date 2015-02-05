package ieci.tecdoc.sgm.sesiones.backoffice.ws.server;

import org.apache.log4j.Logger;

import ieci.tecdoc.sgm.sesiones.SesionManager;

public class AdministracionSesionesBackOfficeWebService {
	
	private static final Logger logger = Logger.getLogger(AdministracionSesionesBackOfficeWebService.class);
			
	public String nuevaSesion (String usuario, String idEntidad) {
		try {
			return SesionManager.nuevaSesion(usuario, idEntidad);
		} catch(Exception e) {
			logger.error("Error al crear una nueva sesión.", e.getCause());
			return null;
		} catch(Throwable e) {
			logger.error("Error inesperado al crear una nueva sesión.", e.getCause());
			return null;
		}
	}
	
	public boolean validarSesion(String key) {
		try {
			return SesionManager.validarSesion(key);
		} catch(Exception e) {
			logger.error("Error al validar una sesión.", e.getCause());
			return false;
		} catch(Throwable e) {
			logger.error("Error inesperado al validar una sesión.", e.getCause());
			return false;
		}
	}
	
	public void caducarSesion(String key) {
		try {
			SesionManager.caducarSesion(key);
		} catch(Exception e) {
			logger.error("Error al eliminar una sesión.", e.getCause());
			return;
		} catch(Throwable e) {
			logger.error("Error inesperado al eliminar una sesión.", e.getCause());
			return;
		}
	}
	
	public Sesion obtenerSesion(String key) {
		try {
			return getSesionWS(SesionManager.obtenerSesion(key));
		} catch(Exception e) {
			logger.error("Error al obtener datos de una sesión.", e.getCause());
			return null;
		} catch(Throwable e) {
			logger.error("Error inesperado al obtener datos de validar una sesión.", e.getCause());
			return null;
		}
	}
	
	public boolean modificarDatosSesion(String key, String datosEspecificos) {
		try {
			return SesionManager.modificarDatosSesion(key, datosEspecificos);
		} catch(Exception e) {
			logger.error("Error al modificar datos de una sesión.", e.getCause());
			return false;
		} catch(Throwable e) {
			logger.error("Error inesperado al modificar datos de una sesión.", e.getCause());
			return false;
		}
	}
	
	private Sesion getSesionWS(ieci.tecdoc.sgm.sesiones.datatype.Sesion oSesion) {
		if (oSesion == null)
			return null;
		
		Sesion poSesion = new Sesion();
		
		poSesion.setIdEntidad(oSesion.getIdEntidad());
		poSesion.setIdSesion(oSesion.getIdSesion());
		poSesion.setUsuario(oSesion.getUsuario());
		poSesion.setDatosEspecificos(oSesion.getDatosEspecificos());
		
		return poSesion;
	}
}
