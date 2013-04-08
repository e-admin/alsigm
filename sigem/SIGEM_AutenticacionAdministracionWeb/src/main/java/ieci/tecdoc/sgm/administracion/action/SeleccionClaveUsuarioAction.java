package ieci.tecdoc.sgm.administracion.action;

import ieci.tecdoc.sgm.administracion.form.CambioClaveForm;
import ieci.tecdoc.sgm.administracion.utils.Defs;
import ieci.tecdoc.sgm.administracion.utils.Utilidades;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.entidades.ServicioEntidades;
import ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SeleccionClaveUsuarioAction extends Action{
		
	private static final Logger logger = Logger.getLogger(SeleccionClaveUsuarioAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, 
				HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException {

		HttpSession session = request.getSession();
		CambioClaveForm formCambioClave = (CambioClaveForm)form;

		// URL de retorno por defecto
		if (Utilidades.isNuloOVacio(formCambioClave.getUrl())) {

			String idEntidad = (String) session.getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD);
			if(Utilidades.isNuloOVacio(idEntidad)) {
				idEntidad = "";
			}
	
			String idAplicacion = (String) session.getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_APLICACION);
			if(Utilidades.isNuloOVacio(idAplicacion)) {
				idAplicacion = "";
			}

			formCambioClave.setUrl("inicio.do?" 
					+ ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD + "=" + idEntidad
					+ "&" + ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_APLICACION + "=" + idAplicacion);
		}

		// Obtener lista de entidades
		try {
			ServicioEntidades oServicio = LocalizadorServicios.getServicioEntidades();
			List entidades = oServicio.obtenerEntidades();
			request.setAttribute(Defs.PARAMETRO_ENTIDADES, entidades);
		} catch(Exception e) {
			logger.error("Se ha producido un error en seleccionClaveUsuario.do: ", e);
			return new ActionForward(formCambioClave.getUrl(), true);
		}

		return mapping.findForward("success");
	}

}
