package ieci.tecdoc.mvc.util;

import javax.servlet.http.HttpServletRequest;

import ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion;
import ieci.tecdoc.sgm.sesiones.administrador.ws.client.Sesion;

public class SessionHelper {

	public static String getEntidad(HttpServletRequest request){
		Sesion sesion = AutenticacionAdministracion.obtenerDatosEntidad(request);
		return sesion.getIdEntidad();
	}
	
}
