package ieci.tecdoc.sgm.administracion.action;

import ieci.tecdoc.sgm.administracion.utils.Utilidades;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.admsesion.administracion.ServicioAdministracionSesionesAdministrador;
import ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LogoutAction extends Action {
	
	private static final Logger logger = Logger.getLogger(LogoutAction.class);
	
	public ActionForward execute(ActionMapping mapping , ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		try{
			String desconectar = (String)request.getParameter(ConstantesGestionUsuariosAdministracion.PARAMETRO_DESCONECTAR);
			if (!Utilidades.isNuloOVacio(desconectar)) {
				HttpSession session = request.getSession();
				session.removeAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_APLICACION);
				session.removeAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD);
				session.removeAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_PASSWORD);
				session.removeAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_USUARIO);
				
				String key = (String)session.getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM);
				if (!Utilidades.isNuloOVacio(key)) {
					ServicioAdministracionSesionesAdministrador oClient = LocalizadorServicios.getServicioAdministracionSesionesAdministrador();
					oClient.caducarSesion(key);
				}
				
				session.removeAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM);
			}
		}catch(Exception e){
			logger.error("Se ha producido un error en logout.do: ", e.fillInStackTrace());
			request.setAttribute("error_logout", e.getCause());
			return mapping.findForward("success");
	   	}		
		return mapping.findForward("success");
	}
	
}
