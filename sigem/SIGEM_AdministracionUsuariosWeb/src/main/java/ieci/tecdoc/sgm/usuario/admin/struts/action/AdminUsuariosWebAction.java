package ieci.tecdoc.sgm.usuario.admin.struts.action;

import ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion;
import ieci.tecdoc.sgm.core.admin.web.SesionAdminHelper;
import ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion;
import ieci.tecdoc.sgm.usuario.admin.struts.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class AdminUsuariosWebAction extends Action {

	private static final Logger logger = Logger.getLogger(AdminUsuariosWebAction.class);
	
	public static final String GLOBAL_FORWARD_ERROR = "error";
	public static final String GLOBAL_FORWARD_LOGIN = "errorAutenticacion";	
	public static final String ERROR_KEY 	=  "mensajeError";
	public static final String PARAM_WEB_AUTH_URL = "WEB_AUTH_URL";
	

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ActionForward forward = null;
		try{

			// Comprobamos si el usuario está autenticado con 
			// certificado.
//			if(!AuthenticationHelper.isUserAuthenticated(request)){
//				return mapping.findForward(GLOBAL_FORWARD_LOGIN);
//			}
			
			//if(!SesionAdminHelper.autenticarUsuario(request)){
			//	return mapping.findForward(GLOBAL_FORWARD_LOGIN);
			//}
			
			if (!AutenticacionAdministracion.autenticarEntidad(request, ConstantesGestionUsuariosAdministracion.APLICACION_USUARIOS_PORTAL)) {
				String Url = AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_USUARIOS_PORTAL);
				request.setAttribute(Constants.URL_REDIRECCION, Url);
				return mapping.findForward(GLOBAL_FORWARD_LOGIN);
			}
			
			forward = executeAction(mapping, form, request, response);	
		
		}catch(Throwable e){
			logger.error("Error inesperado ejecutando acción.", e);
			return mapping.findForward(GLOBAL_FORWARD_ERROR);
		}
		
		return forward;
	}

	/**
	 * Método específico que se ejecuta en cada acción.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public abstract ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response);
}
