package ieci.tecdoc.sgm.core.admin.web;

import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.admsesion.backoffice.ServicioAdministracionSesionesBackOffice;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.ConstantesGestionUsuariosBackOffice;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.ServicioGestionUsuariosBackOffice;
import ieci.tecdoc.sgm.sesiones.backoffice.ws.client.Sesion;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


public class AutenticacionBackOffice {

	private static final Logger logger = Logger.getLogger(AutenticacionBackOffice.class);
	private static ServicioAdministracionSesionesBackOffice oServicio;
	
	static {
		try{
			oServicio = LocalizadorServicios.getServicioAdministracionSesionesBackOffice();
		} catch (SigemException e) {
		}
	}
	
	public static boolean autenticar(HttpServletRequest request) {
		
    	String key = request.getParameter(ConstantesGestionUsuariosBackOffice.PARAMETRO_KEY_SESION_USUARIO);
    	if(isNuloOVacio(key)) {
    		key = (String) request.getSession().getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_KEY_SESION_USUARIO);
    	}
    	
    	if(isNuloOVacio(key)) {
    		return false;
    	} else if (oServicio.validarSesion(key)) {
    		request.getSession().setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_KEY_SESION_USUARIO, key);
    		return true;
    	} else {
    		return false;
    	}
	}
	
	public static Sesion obtenerDatos(HttpServletRequest request) {
		
    	String key = request.getParameter(ConstantesGestionUsuariosBackOffice.PARAMETRO_KEY_SESION_USUARIO);
    	if(isNuloOVacio(key)) {
    		key = (String) request.getSession().getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_KEY_SESION_USUARIO);
    	}
    	
    	if(isNuloOVacio(key)) {
    		return null;
    	} else  {
    		return getSesion(oServicio.obtenerSesion(key));
    	}
	}
	
	public static String obtenerUrlLogin(HttpServletRequest request, String aplicacion){
		ServicioGestionUsuariosBackOffice oServicio;
		StringBuffer sbUrl;
		try {
			oServicio = LocalizadorServicios.getServicioAutenticacionUsuariosBackOffice();
			
			sbUrl = new StringBuffer(oServicio.obtenerDireccionLogado()).append("?");
			sbUrl.append(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_APLICACION).append("=").append(aplicacion);
			
			String entidadId = (String)request.getParameter(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD);
			if(isNuloOVacio(entidadId)){
				entidadId = (String)request.getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD);
			}
			if(!isNuloOVacio(entidadId)) {
				sbUrl.append('&').append(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD).append("=").append(entidadId);
			}
		} catch (SigemException e) {
			logger.error("No se ha podido obtener la dirección de logado", e);
			return null;
		}
		
		return comprobarURL(request, sbUrl.toString());
	}
	
	public static String obtenerUrlLogout(HttpServletRequest request){
		ServicioGestionUsuariosBackOffice oServicio;
		StringBuffer sbUrl;
		
		try {
			oServicio = LocalizadorServicios.getServicioAutenticacionUsuariosBackOffice();
			sbUrl = new StringBuffer(oServicio.obtenerDireccionDeslogado());
		} catch (SigemException e) {
			logger.error("No se ha podido obtener la dirección de deslogado", e);
			return null;
		}

		return comprobarURL(request, sbUrl.toString());
	}
	
	public static String comprobarURL(HttpServletRequest request, String url) {
		if (url == null || "".equals(url)) 
			return null;
		
		if (url.indexOf("localhost") != -1) {
			int index = url.indexOf("localhost");
			return url.substring(0, index) + request.getServerName() + url.substring(index + "localhost".length());
		} else {
			return url;
		}
	}
	
	public static String obtenerUrlAplicacion(HttpServletRequest request, String idAplicacion, String key){
		ServicioGestionUsuariosBackOffice oServicio;
		StringBuffer sbUrl;
		
		try {
			oServicio = LocalizadorServicios.getServicioAutenticacionUsuariosBackOffice();
			sbUrl = new StringBuffer(oServicio.obtenerDireccionAplicacion(idAplicacion)).append("?");
			sbUrl.append(ConstantesGestionUsuariosBackOffice.PARAMETRO_KEY_SESION_USUARIO).append("=").append(key);
		} catch (SigemException e) {
			logger.error("No se ha podido obtener la dirección de deslogado", e);
			return null;
		}

		return comprobarURL(request, sbUrl.toString());
	}
	
	public static boolean modificarDatosSesion(String key, String datosEspecificos){
		boolean retorno = false;
		
		try {
			retorno = oServicio.modificarDatosSesion(key, datosEspecificos);
		} catch (Exception e) {
			logger.error("No se ha podido modificar los datos específicos de la sesion", e);
			return retorno;
		}

		return retorno;
	}
	
	
	public static boolean isNuloOVacio(Object cadena)
	{
		if((cadena == null) || ("".equals(cadena)) || ("null".equals(cadena))) {
			return true;
		}
		return false;
	}
	
	private static Sesion  getSesion (ieci.tecdoc.sgm.core.services.admsesion.backoffice.Sesion sesion){
		if (sesion!=null){ 
			Sesion newSesion = new Sesion();
			newSesion.setDatosEspecificos(sesion.getDatosEspecificos());
			newSesion.setIdEntidad(sesion.getIdEntidad());
			newSesion.setIdSesion(sesion.getIdSesion());
			newSesion.setUsuario(sesion.getUsuario());
			return newSesion;
		}
		return null;
	}
}
