package ieci.tecdoc.sgm.admsistema.action;

import ieci.tecdoc.sgm.admsistema.util.Defs;
import ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion;
import ieci.tecdoc.sgm.sesiones.administrador.ws.client.Sesion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InicioAction extends AdministracionWebAction {
	private static final Logger logger = Logger.getLogger(InicioAction.class);
	
	public static final String FORWARD_SUCCESS = "success";
	public static final String FORWARD_ERROR = "failure";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {

			try {
				Sesion sesion = AutenticacionAdministracion.obtenerDatos(request);
				if (sesion != null) {
					request.getSession().setAttribute(Defs.PARAMETRO_NOMBRE_ADMINISTRADOR, sesion.getDatosEspecificos());
					request.getSession().setAttribute(Defs.PARAMETRO_NOMBRE_USUARIO_TRABAJO, sesion.getUsuario());
				} else { 
					request.getSession().setAttribute(Defs.PARAMETRO_NOMBRE_ADMINISTRADOR, "");
				}
				
				return mapping.findForward(FORWARD_SUCCESS);
			} catch(Exception e) {
				logger.error("Se ha producido un error inesperado", e);
				return mapping.findForward(FORWARD_ERROR);
			}
	}
}
