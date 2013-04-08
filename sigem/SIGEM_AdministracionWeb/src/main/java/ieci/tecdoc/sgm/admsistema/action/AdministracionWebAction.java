package ieci.tecdoc.sgm.admsistema.action;

import ieci.tecdoc.sgm.admsistema.util.Defs;
import ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion;
import ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class AdministracionWebAction extends Action {

	private static final Logger logger = Logger.getLogger(AdministracionWebAction.class);
	
	public static final String GLOBAL_FORWARD_ERROR = "errorGlobal";
	public static final String GLOBAL_FORWARD_LOGIN = "errorAutenticacion";	

	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ActionForward forward = null;
		try{
			
			if (!AutenticacionAdministracion.autenticar(request)) {
				String url = AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_ADMINISTRACION);
				request.setAttribute(Defs.PARAMETRO_URL_REDIRECCION, url);
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
