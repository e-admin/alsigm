package ieci.tecdoc.sgm.nt.struts.acciones;
/*
 *  $Id: NotificacionWebAction.java,v 1.1.2.1 2008/02/11 12:24:13 jconca Exp $
 */
import ieci.tecdoc.sgm.core.user.web.SesionUserHelper;
import ieci.tecdoc.sgm.core.user.web.WebAuthenticationHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class NotificacionWebAction extends Action {

	private static final Logger logger = Logger.getLogger(NotificacionWebAction.class);
	
	public static final String GLOBAL_FORWARD_ERROR = "errorGlobal";
	public static final String GLOBAL_FORWARD_LOGIN = "errorAutenticacion";	
	public static final String ERROR_KEY 	=  "mensajeError";
	public static final String PARAM_WEB_AUTH_URL = "WEB_AUTH_URL";
	private static final String TIPO_APLICACION = "Notificacion";

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ActionForward forward = null;
		try{

			// Comprobamos si el usuario está autenticado con 
			// certificado.
			if(!SesionUserHelper.authenticateUser(request)){
				response.sendRedirect(WebAuthenticationHelper.getWebAuthURL(request,TIPO_APLICACION));
				return null;
			}
		
			//AuthenticationHelper.saveUser(request);
			
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
