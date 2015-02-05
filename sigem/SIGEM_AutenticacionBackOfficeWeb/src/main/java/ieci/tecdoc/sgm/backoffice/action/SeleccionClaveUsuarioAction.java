package ieci.tecdoc.sgm.backoffice.action;

import ieci.tecdoc.sgm.backoffice.form.CambioClaveForm;
import ieci.tecdoc.sgm.backoffice.utils.Utilidades;
import ieci.tecdoc.sgm.core.admin.web.AdministracionHelper;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.ConstantesGestionUsuariosBackOffice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SeleccionClaveUsuarioAction extends Action{
		
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
				HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException {

		HttpSession session = request.getSession();
		CambioClaveForm formCambioClave = (CambioClaveForm)form;
		
		// URL de retorno por defecto
		if (Utilidades.isNuloOVacio(formCambioClave.getUrl())) {
			formCambioClave.setUrl("seleccionEntidad.do");
		}

		// Identificador de la entidad por defecto
		String idEntidad = (String) session.getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD);
		if (Utilidades.isNuloOVacio(idEntidad)) {
			request.setAttribute("entidades", AdministracionHelper.obtenerListaEntidades());
		} else {
			formCambioClave.setIdEntidad(idEntidad);
		}

		return mapping.findForward("success");
	}

}
