package ieci.tecdoc.sgm.consulta_telematico.action;
/*
 *  $Id: RegistroWebAction.java,v 1.1.2.2 2008/03/14 11:36:02 jnogales Exp $
 */

import ieci.tecdoc.sgm.consulta_telematico.utils.Defs;
import ieci.tecdoc.sgm.core.user.web.SesionUserHelper;
import ieci.tecdoc.sgm.core.user.web.WebAuthenticationHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class ConsultaRegistroTelematicoWebAction extends Action {

	private static final Logger logger = Logger.getLogger(ConsultaRegistroTelematicoWebAction.class);

	public static final String GLOBAL_FORWARD_ERROR = "";

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = null;
		try{

			if(!SesionUserHelper.authenticateUser(request)){
				response.sendRedirect(WebAuthenticationHelper.getWebAuthURL(request, Defs.TIPO_APLICACION));
				return null;
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
	public abstract ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
