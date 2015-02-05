package ieci.tecdoc.isicres.rpadmin.struts.util;



import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.core.services.ConstantesGestionUsuariosAdministracion;
import es.ieci.tecdoc.isicres.admin.service.admsesion.Sesion;

public class SesionHelper {



	private static final Logger logger = Logger.getLogger(SesionHelper.class);
	public static boolean authenticate(HttpServletRequest request) {
    //	if(AutenticacionAdministracion.autenticarEntidad(request, ConstantesGestionUsuariosAdministracion.APLICACION_REGISTRO)) {
  //  		if(obtenerEntidad(request)==null) {
//    			Sesion sesion = AutenticacionAdministracion.obtenerDatosEntidad(request);
    			Sesion sesion = null;
    			sesion = new Sesion();
    			guardarEntidad(request, sesion.getIdEntidad());
    			guardarSesion(request, sesion);
    		//}
    		//return true;
    	//} else {
    		return true;
    	//}
	}

	public static String getWebAuthURL(HttpServletRequest request){
//		return AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REGISTRO);
		return AutenticacionAdministracion.obtenerUrlLogin(request);
	}

	public static String getWebAuthDesconectURL(HttpServletRequest request){
		return AutenticacionAdministracion.obtenerUrlLogout(request);
	}

	public static String getEntidad(HttpServletRequest request){
		//Sesion sesion = AutenticacionAdministracion.obtenerDatosEntidad(request);
		//return sesion.getIdEntidad();
		return null;
	}

	public static Entidad obtenerEntidad(HttpServletRequest request) {
		Object oEntidad = request.getSession().getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD);
		if(oEntidad==null) {
			return null;
		}
		Entidad entidad = new Entidad();
		entidad.setIdentificador(oEntidad.toString());
		return entidad;
	}

	public static void guardarEntidad(HttpServletRequest request, String idEntidad) {
		request.getSession().setAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD, idEntidad);
		return;
	}

	public static void guardarSesion(HttpServletRequest request, Sesion sesion) {
		request.getSession().setAttribute("datosSesion", sesion);
		return;
	}

	public static void guardarCaseSensitive (HttpServletRequest request, String caseSensitive){
		request.getSession().setAttribute("caseSensitive", caseSensitive);
		return;
	}

	public static String obtenerCaseSensitive (HttpServletRequest request){
		Object oCaseSensitive = request.getSession().getAttribute("caseSensitive");

		if ((oCaseSensitive == null) || !(oCaseSensitive instanceof String)){
			return "CI";
		}

		return (String)oCaseSensitive;
	}
}
