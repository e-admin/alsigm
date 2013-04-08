package ieci.tecdoc.sgm.catalogo_tramites.action;
/*
 * $Id: CatalogoTramitesWebAction.java,v 1.1.2.3 2008/10/13 08:53:23 jnogales Exp $
 */

import ieci.tecdoc.sgm.catalogo_tramites.utils.Defs;
import ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion;
import ieci.tecdoc.sgm.core.admin.web.SesionAdminHelper;
import ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion;
import ieci.tecdoc.sgm.sesiones.administrador.ws.client.Sesion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class CatalogoTramitesWebAction extends Action {

	private static final Logger logger = Logger.getLogger(CatalogoTramitesWebAction.class);
	
	public static final String PARAM_WEB_AUTH_URL = "WEB_AUTH_URL";
	public static final String GLOBAL_FORWARD_LOGIN = "errorAutenticacion";	

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = null;
		try{
			if (!AutenticacionAdministracion.autenticarEntidad(request, ConstantesGestionUsuariosAdministracion.APLICACION_CATALOGO_TRAMITES)) {
				String url = AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_CATALOGO_TRAMITES);
				//response.sendRedirect(url);
				//return null;
				request.setAttribute(Defs.PARAMETRO_URL_REDIRECCION, url);
				return mapping.findForward(GLOBAL_FORWARD_LOGIN);
			} 
			
			forward = executeAction(mapping, form, request, response);	
		}catch(Throwable e){
			logger.error("Error inesperado ejecutando acción.", e);
			return null;
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
