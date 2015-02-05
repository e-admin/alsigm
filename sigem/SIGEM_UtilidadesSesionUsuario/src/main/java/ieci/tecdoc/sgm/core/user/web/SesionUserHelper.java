package ieci.tecdoc.sgm.core.user.web;

import java.util.Locale;

import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.sesion.ServicioSesionUsuario;
import ieci.tecdoc.sgm.core.services.sesion.SesionUsuario;
import ieci.tecdoc.sgm.core.services.sesion.SesionUsuarioException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class SesionUserHelper {

	private static final Logger logger = Logger.getLogger(SesionUserHelper.class);

	public static boolean authenticateUser(HttpServletRequest request){
		if( (!isNuloOVacio(request.getSession().getAttribute(ConstantesSesionUser.ID_SESION)))
		){
			// Ya existe una sesión de usuario iniciada.
			if(isNuloOVacio(obtenerIdentificadorEntidad(request))){
				return false;
			}
			return true;
		}else if( !isNuloOVacio(request.getParameter(ConstantesSesionUser.ID_SESION))
					|| !isNuloOVacio(request.getAttribute(ConstantesSesionUser.ID_SESION))
			){
				// Nos llega el identificador de sesión como parámetro en la petición.
				String sesionId = request.getParameter(ConstantesSesionUser.ID_SESION);
				if(isNuloOVacio(sesionId)){
					sesionId = (String)request.getAttribute(ConstantesSesionUser.ID_SESION);
				}

				String entidadId = request.getParameter(ConstantesSesionUser.ID_ENTIDAD);
				if(isNuloOVacio(entidadId)){
					entidadId = (String)request.getAttribute(ConstantesSesionUser.ID_ENTIDAD);
				}
				Entidad entidad = new Entidad();
				entidad.setIdentificador(entidadId);

				String lang = request.getParameter(ConstantesSesionUser.LANG);
				if(isNuloOVacio(lang)){
					lang = (String)request.getAttribute(ConstantesSesionUser.LANG);
				}

				String country = request.getParameter(ConstantesSesionUser.COUNTRY);
				if(isNuloOVacio(lang)){
					country = (String)request.getAttribute(ConstantesSesionUser.COUNTRY);
				}

				String idioma = (String)request.getParameter(ConstantesSesionUser.IDIOMA);
				if(SesionUserHelper.isNuloOVacio(idioma)){
					idioma = (String)request.getAttribute(ConstantesSesionUser.IDIOMA);
				}
				if(!isNuloOVacio(idioma)) {
					int index = idioma.indexOf("_");
					lang = idioma.substring(0, index);
					country = idioma.substring(index+1, idioma.length());
				}

				if(!isNuloOVacio(lang) && !isNuloOVacio(country)) {
					request.getSession().setAttribute("org.apache.struts.action.LOCALE", new Locale(lang, country));
				}
				ServicioSesionUsuario oServicio = null;
				try {
					oServicio = LocalizadorServicios.getServicioSesionUsuario();
				} catch (SigemException e) {
					logger.fatal("Error obteniendo el servicio de sesión de usuario.");
					return false;
				}
				SesionUsuario oSesion = null;

				try {
					oSesion = oServicio.obtenerSesion(sesionId, entidad);

				} catch (SesionUsuarioException e) {
					StringBuffer sbError = new StringBuffer("Error obteniendo sesión de usuario. ID:");
					sbError.append(sesionId);
					logger.error(sbError.toString());
					return false;
				}

				if(isNuloOVacio(oSesion)){//|| (oSesion.getIdEntidad() == null) || ("".equals(oSesion.getIdEntidad()))){
					return false;
				} else {
					request.getSession().setAttribute(ConstantesSesionUser.ID_SESION, sesionId);
					request.getSession().setAttribute(ConstantesSesionUser.ID_ENTIDAD, entidadId);
				}
				return true;
		}
		return false;
	}

	public static String obtenerIdentificadorEntidad(HttpServletRequest request){
		String idEntidad = (String)request.getSession().getAttribute(ConstantesSesionUser.ID_ENTIDAD);
		return idEntidad;
	}

	public static boolean isNuloOVacio(Object cadena)
	{
		if((cadena == null) || ("".equals(cadena)) || ("null".equals(cadena))) {
			return true;
		}
		return false;
	}
}
