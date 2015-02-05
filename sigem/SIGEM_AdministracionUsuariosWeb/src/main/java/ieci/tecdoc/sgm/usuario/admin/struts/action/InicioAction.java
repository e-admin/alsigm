package ieci.tecdoc.sgm.usuario.admin.struts.action;

import ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion;
import ieci.tecdoc.sgm.core.admin.web.ConstantesSesionAdmin;
import ieci.tecdoc.sgm.core.services.sesionadmin.SesionAdministracion;
import ieci.tecdoc.sgm.sesiones.administrador.ws.client.Sesion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Acción de entrada a la aplicación de pago
 */
public class InicioAction extends AdminUsuariosWebAction {
	
	private static final Logger logger = Logger.getLogger(LoginAction.class);	
	//public static final String GLOBAL_FORWARD_LOGIN = "errorAutenticacion";
	private static final String SUCCESS_FORWARD = "success";
	
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
		
		return mapping.findForward(SUCCESS_FORWARD);
	}
}