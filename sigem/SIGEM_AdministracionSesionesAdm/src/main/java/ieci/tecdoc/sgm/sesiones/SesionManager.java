package ieci.tecdoc.sgm.sesiones;

import java.util.StringTokenizer;

import ieci.tecdoc.sgm.base.guid.Guid;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.administracion.Perfil;
import ieci.tecdoc.sgm.core.services.administracion.ServicioAdministracion;
import ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion;
import ieci.tecdoc.sgm.sesiones.datatype.KeySesion;
import ieci.tecdoc.sgm.sesiones.datatype.Sesion;
import ieci.tecdoc.sgm.sesiones.datatype.Sesiones;
import ieci.tecdoc.sgm.sesiones.timer.SesionesCaducadas;


public class SesionManager {
	
	public static String nuevaSesion(String usuario, int tipoUsuario) {
		String idSesion = new Guid().toString();
		Sesion sesion = new Sesion(usuario, idSesion, tipoUsuario);
		Sesiones.getTimerCaducadas().schedule(sesion.getSesionCaducada(), Sesiones.getDuracionSesion());
		String key = usuario + "_" + idSesion;
		Sesiones.getSesiones().put(key, sesion);
		
		return key;
	}
	
	public static boolean validarSesion(String key) {
		Sesion sesion = (Sesion)Sesiones.getSesiones().get(key);
		if (sesion != null) {
			if (sesion.getTipoUsuario() == Sesion.TIPO_USUARIO_ADMINISTRADOR) {
				if (permisosAdministracion(sesion.getUsuario())) {
					sesion.getSesionCaducada().cancel();
					sesion.setSesionCaducada(new SesionesCaducadas(key));
					Sesiones.getTimerCaducadas().schedule(sesion.getSesionCaducada(), Sesiones.getDuracionSesion());
					return true;
				}
			} else {
				return true;
			}
		}
		return false;
	}
	
	public static boolean validarSesionEntidad(String key_entidad, String idAplicacion) {
		Sesion sesion = null;
		KeySesion key_sesion = obtenerValores(key_entidad);
		if (key_sesion != null) {
			sesion = (Sesion)Sesiones.getSesiones().get(key_sesion.getKey());
			if (sesion != null) {
				if (sesion.getTipoUsuario() == Sesion.TIPO_USUARIO_ADMINISTRADOR) {
					if (permisosAplicacion(sesion.getUsuario(), key_sesion.getEntidad(), idAplicacion)) {
						sesion.getSesionCaducada().cancel();
						sesion.setSesionCaducada(new SesionesCaducadas(key_sesion.getKey()));
						Sesiones.getTimerCaducadas().schedule(sesion.getSesionCaducada(), Sesiones.getDuracionSesion());
						return true;
					}
				} else {
					return true;
				}
			} 
		} 
		return false;
	}
	
	public static void caducarSesion(String key) {
		Sesion sesion = (Sesion)Sesiones.getSesiones().get(key);
		if (sesion != null) 
			sesion.getSesionCaducada().cancel();
		Sesiones.getSesiones().remove(key);
	}
	
	public static void caducarSesionEntidad(String key_entidad) {
		KeySesion key_sesion = obtenerValores(key_entidad);
		if (key_sesion != null)
			caducarSesion(key_sesion.getKey());
	}
	
	public static Sesion obtenerSesion(String key) {
		return (Sesion)Sesiones.getSesiones().get(key);
	}
	
	public static Sesion obtenerSesionEntidad(String key_entidad) {
		Sesion sesion = null;
		
		KeySesion key_sesion = obtenerValores(key_entidad);
		if (key_sesion != null) {
			sesion = obtenerSesion(key_sesion.getKey());
			if (sesion != null) {
				sesion.setIdEntidad(key_sesion.getEntidad());
			}
		}
		return sesion;
	}
	
	public static boolean modificarDatosSesion(String key, String datosEspecificos) {
		Sesion sesion = (Sesion)Sesiones.getSesiones().get(key);
		if (sesion != null)
			sesion.setDatosEspecificos(datosEspecificos);
		else return false;
		return true;
	}
	
	private static boolean permisosAplicacion(String usuario, String entidad, String aplicacion) {
		try {
			ServicioAdministracion oServicioAdm = LocalizadorServicios.getServicioAdministracion();
			Perfil[] perfiles = oServicioAdm.getPerfiles(usuario, entidad);
			for(int i=0; i<perfiles.length; i++) {
				if (aplicacion.equals(perfiles[i].getIdAplicacion()))
					return true;
			}
		} catch(Exception e) { }
		return false;
	}
	
	private static boolean permisosAdministracion(String usuario) {
		try {
			ServicioAdministracion oServicioAdm = LocalizadorServicios.getServicioAdministracion();
			Perfil[] perfiles = oServicioAdm.getPerfiles(usuario);
			for(int i=0; i<perfiles.length; i++) {
				if (ConstantesGestionUsuariosAdministracion.APLICACION_ADMINISTRACION.equalsIgnoreCase(perfiles[i].getIdAplicacion()))
					return true;
			}
			return false;
		} catch(Exception e) {
			return false;
		}
	}
	
	private static KeySesion obtenerValores(String key_entidad) {
		try {
			
			StringTokenizer st = new StringTokenizer(key_entidad, "_");
			String key = null;
			String entidad = null;
			
			while (st.countTokens() > 1) {
				if (key == null) {
					key = st.nextToken();
				} else {
					key += "_" + st.nextToken();
				}
			}
			entidad = st.nextToken();
			
			return new KeySesion(key, entidad);
			
		} catch(Exception e) {
			return null;
		}
	}
}
