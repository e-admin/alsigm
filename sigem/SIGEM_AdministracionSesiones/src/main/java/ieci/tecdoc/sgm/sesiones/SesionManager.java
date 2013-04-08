package ieci.tecdoc.sgm.sesiones;

import ieci.tecdoc.sgm.base.guid.Guid;
import ieci.tecdoc.sgm.sesiones.datatype.Sesion;
import ieci.tecdoc.sgm.sesiones.datatype.Sesiones;
import ieci.tecdoc.sgm.sesiones.timer.SesionesCaducadas;


public class SesionManager {
	
	public static String nuevaSesion(String usuario, String idEntidad) {
		String idSesion = new Guid().toString();
		Sesion sesion = new Sesion(usuario, idSesion, idEntidad);
		Sesiones.getTimerCaducadas().schedule(sesion.getSesionCaducada(), Sesiones.getDuracionSesion());
		String key = usuario + "_" + idSesion;
		Sesiones.getSesiones().put(key, sesion);
		
		return key;
	}
	
	public static boolean validarSesion(String key) {
		Sesion sesion = (Sesion)Sesiones.getSesiones().get(key);
		if (sesion != null) {
			sesion.getSesionCaducada().cancel();
			sesion.setSesionCaducada(new SesionesCaducadas(key));
			Sesiones.getTimerCaducadas().schedule(sesion.getSesionCaducada(), Sesiones.getDuracionSesion());
		}
		return (sesion != null);
	}
	
	public static void caducarSesion(String key) {
		Sesion sesion = (Sesion)Sesiones.getSesiones().get(key);
		if (sesion != null) 
			sesion.getSesionCaducada().cancel();
		Sesiones.getSesiones().remove(key);
	}
	
	public static Sesion obtenerSesion(String key) {
		return (Sesion)Sesiones.getSesiones().get(key);
	}
	
	public static boolean modificarDatosSesion(String key, String datosEspecificos) {
		Sesion sesion = (Sesion)Sesiones.getSesiones().get(key);
		if (sesion != null)
			sesion.setDatosEspecificos(datosEspecificos);
		else return false;
		return true;
	}
}
