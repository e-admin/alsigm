package ieci.tecdoc.sgm.sesiones.administrador.ws.server;

import org.apache.log4j.Logger;

import ieci.tecdoc.sgm.sesiones.SesionManager;

public class AdministracionSesionesAdministradorWebService {
	
	private static final Logger logger = Logger.getLogger(AdministracionSesionesAdministradorWebService.class);
			
	public String nuevaSesion (String usuario, int tipoUsuario) {
		try {
			return SesionManager.nuevaSesion(usuario, tipoUsuario);
		} catch(Exception e) {
			logger.error("Error al crear una nueva sesión.", e.getCause());
			return null;
		} catch(Throwable e) {
			logger.error("Error inesperado al crear una nueva sesión.", e.getCause());
			return null;
		}
	}
	
	public String nuevaSesionEntidad (String key, String idEntidad) {
		try {
			if (key != null && !"".equals(key) && idEntidad != null && !"".equals(idEntidad))
				return key + "_" + idEntidad;
			else return null;
		} catch(Exception e) {
			logger.error("Error al crear una nueva sesión de entidad.", e.getCause());
			return null;
		} catch(Throwable e) {
			logger.error("Error inesperado al crear una nueva sesión entidad.", e.getCause());
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
	
	public boolean validarSesionEntidad(String key_entidad, String idAplicacion) {
		try {
			return SesionManager.validarSesionEntidad(key_entidad, idAplicacion);
		} catch(Exception e) {
			logger.error("Error al validar una sesión de entidad.", e.getCause());
			return false;
		} catch(Throwable e) {
			logger.error("Error inesperado al validar una sesión de entidad.", e.getCause());
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
	
	public void caducarSesionEntidad(String key_entidad) {
		try {
			SesionManager.caducarSesionEntidad(key_entidad);
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
	
	public Sesion obtenerSesionEntidad(String key_entidad) {
		try {
			return getSesionWS(SesionManager.obtenerSesionEntidad(key_entidad));
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
		poSesion.setTipoUsuario(oSesion.getTipoUsuario());
		
		return poSesion;
	}
}
