package ieci.tecdoc.sgm.catalogo_tramites.action;
/*
 * $Id: InicioAction.java,v 1.1.2.2 2008/04/29 11:04:00 jnogales Exp $
 */
import ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion;
import ieci.tecdoc.sgm.core.admin.web.ConstantesSesionAdmin;
import ieci.tecdoc.sgm.core.services.sesionadmin.SesionAdministracion;
import ieci.tecdoc.sgm.sesiones.administrador.ws.client.Sesion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Acción de entrada a la aplicación de pago
 */
public class InicioAction extends CatalogoTramitesWebAction {
	
	private static final Logger logger = Logger.getLogger(InicioAction.class);	
	public static final String FORWARD_SUCCESS = "success";
	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {

		Sesion oSesion = AutenticacionAdministracion.obtenerDatosEntidad(request);
		SesionAdministracion poSesion = new SesionAdministracion();
		poSesion.setIdEntidad(oSesion.getIdEntidad());
		poSesion.setIdSesion(oSesion.getIdSesion());
		poSesion.setIdUsuario(oSesion.getUsuario());
		request.getSession().setAttribute(ConstantesSesionAdmin.ID_SESION_ADMINISTRACION, poSesion);
		
		return mapping.findForward(FORWARD_SUCCESS);
	}
	
}