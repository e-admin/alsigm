package ieci.tecdoc.isicres.rpadmin.struts.util;




import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import es.ieci.tecdoc.isicres.admin.core.services.ConstantesGestionUsuariosAdministracion;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;
import es.ieci.tecdoc.isicres.admin.service.admsesion.Sesion;

public class AutenticacionAdministracion {

	private static final Logger logger = Logger.getLogger(AutenticacionAdministracion.class);
	private static ISicresServicioRPAdmin oServicio;



	public static boolean autenticar(HttpServletRequest request) {

    	String key = request.getParameter(ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM);
    	if(isNuloOVacio(key)) {
    		key = (String) request.getSession().getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM);
    	}

    	if(isNuloOVacio(key)) {
    		return false;
    	} else {
   			request.getSession().setAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM, key);
   			return true;
    	}
	}


	public static Sesion obtenerDatosEntidad(HttpServletRequest request) {

		String key_entidad = request.getParameter(ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD);
    	if(isNuloOVacio(key_entidad)) {
    		key_entidad = (String) request.getSession().getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD);
    	}

    	if(isNuloOVacio(key_entidad)) {
    		return null;
    	} else  {
    		//return getSesion(oServicio.obtenerSesionEntidad(key_entidad));
    		return null;
    	}
	}

	public static Sesion obtenerDatos(HttpServletRequest request) {

    	String key = request.getParameter(ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM);
    	if(isNuloOVacio(key)) {
    		key = (String) request.getSession().getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM);
    	}

    	if(isNuloOVacio(key)) {
    		return null;
    	} else  {
    		//return getSesion(oServicio.obtenerSesion(key));
    		  return null;
    	}
	}

	public static String obtenerUrlLogin(HttpServletRequest request, String aplicacion){

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
		StringBuffer sbUrl;
		try {

			sbUrl = new StringBuffer(oServicio.obtenerDireccionLogado()).append("?");
			sbUrl.append(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_APLICACION).append("=").append(aplicacion);

			String entidadId = (String)request.getParameter(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD);
			if(isNuloOVacio(entidadId)){
				entidadId = (String)request.getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD);
			}
			if(!isNuloOVacio(entidadId)) {
				sbUrl.append('&').append(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD).append("=").append(entidadId);
			}
		} catch (Exception e) {
			logger.error("No se ha podido obtener la dirección de logado", e);
			return null;
		}

		return comprobarURL(request, sbUrl.toString());
	}

	public static String obtenerUrlLogin(HttpServletRequest request){
		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
		StringBuffer sbUrl;

		try {
			//oServicio = LocalizadorServicios.getServicioAutenticacionUsuariosAdministracion();
			  sbUrl = new StringBuffer(oServicio.obtenerDireccionLogado());
		} catch (Exception e) {
			logger.error("No se ha podido obtener la dirección de logado", e);
			return null;
		}

		return comprobarURL(request, sbUrl.toString());
	}

	public static String obtenerUrlLogout(HttpServletRequest request){
		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
		StringBuffer sbUrl;

		try {
			//oServicio = LocalizadorServicios.getServicioAutenticacionUsuariosAdministracion();
			  sbUrl = new StringBuffer(oServicio.obtenerDireccionDeslogado());
		} catch (Exception e) {
			logger.error("No se ha podido obtener la dirección de deslogado", e);
			return null;
		}

		return comprobarURL(request, sbUrl.toString());
	}


	private static String comprobarURL(HttpServletRequest request, String url) {
		if (url == null || "".equals(url))
			return null;

		if (url.indexOf("localhost") != -1) {
			int index = url.indexOf("localhost");
			return url.substring(0, index) + request.getServerName() + url.substring(index + "localhost".length());
		} else {
			return url;
		}
	}


	public static boolean modificarDatosSesion(String key, String datosEspecificos){
		boolean retorno = false;

		try {
			//retorno = oServicio.modificarDatosSesion(key, datosEspecificos);
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

	private static Sesion getSesion (es.ieci.tecdoc.isicres.admin.service.admsesion.Sesion sesion){
		if (sesion!=null){
			Sesion newSesion = new Sesion();
			newSesion.setDatosEspecificos(sesion.getDatosEspecificos());
			newSesion.setIdEntidad(sesion.getIdEntidad());
			newSesion.setIdSesion(sesion.getIdSesion());
			newSesion.setUsuario(sesion.getUsuario());
			//newSesion.setTipoUsuario(sesion.getTipoUsuario());
			return newSesion;
		}
		return null;
	}

}
