package ieci.tecdoc.sgm.administracion.action;

import ieci.tecdoc.sgm.administracion.form.EntidadAccesoForm;
import ieci.tecdoc.sgm.administracion.utils.Comprobador;
import ieci.tecdoc.sgm.administracion.utils.Defs;
import ieci.tecdoc.sgm.administracion.utils.Utilidades;
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

public class SeleccionEntidadAction extends Action {
	
	private static final Logger logger = Logger.getLogger(LogoutAction.class);

	public ActionForward execute(ActionMapping mapping , ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		try{
			HttpSession session = request.getSession();
			EntidadAccesoForm entidadForm = (EntidadAccesoForm)form;
		
			String key = (String)session.getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM);
			if(Utilidades.isNuloOVacio(key)) {
				key = "";
			}
		
			String idAplicacion = (String)session.getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_APLICACION);
			if(Utilidades.isNuloOVacio(idAplicacion)) {
				idAplicacion = "";
			}
			
			String usuario = (String)session.getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_USUARIO);
			if(Utilidades.isNuloOVacio(usuario)) {
				usuario = "";
			}
			
			String password = (String)session.getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_PASSWORD);
			if(Utilidades.isNuloOVacio(password)) {
				password = "";
			}
			
			Boolean validado = (Boolean)session.getAttribute(Defs.PARAMETRO_USUARIO_VALIDADO);
			if(validado == null) {
				validado = new Boolean(false);
			}
			
			String idEntidad = entidadForm.getEntidadId();
			if (Utilidades.isNuloOVacio(idEntidad))
				idEntidad = "";
			
			session.setAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD, idEntidad);
			
			return mapping.findForward(Comprobador.comprobarInformacion(request, key, idEntidad, idAplicacion, usuario, password, validado.booleanValue()));
			
		}catch(Exception e){
			logger.error("Se ha producido un error al seleccionar la entidad", e.fillInStackTrace());
			return mapping.findForward("success");
	   	}		

	}
}
