package ieci.tecdoc.sgm.admsistema.action;

import ieci.tecdoc.sgm.admsistema.util.Defs;
import ieci.tecdoc.sgm.admsistema.util.Utilidades;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.administracion.ServicioAdministracion;
import ieci.tecdoc.sgm.core.services.administracion.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ComprobarUsuarioAction extends AdministracionWebAction {
	private static final Logger logger = Logger.getLogger(ComprobarUsuarioAction.class);
	
	public static final String FORWARD_SUCCESS = "success";
	public static final String FORWARD_ERROR = "failure";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {

			boolean libre = false;
			try {
				String usuario = (String)request.getParameter(Defs.PARAMETRO_ADMINISTRADOR_USERNAME);
				if (!Utilidades.esNuloOVacio(usuario)) {
					ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
					Usuario user = oServicio.obtenerUsuario(usuario);
					if (user == null)
						libre = true;
				} 
				request.setAttribute(Defs.PARAMETRO_USUARIO_LIBRE, new Boolean(libre));
				return mapping.findForward(FORWARD_SUCCESS);
			} catch(Exception e) {
				logger.error("Se ha producido un error inesperado", e);
				request.setAttribute(Defs.PARAMETRO_USUARIO_LIBRE, new Boolean(libre));
				return mapping.findForward(FORWARD_ERROR);
			}
	}
}
