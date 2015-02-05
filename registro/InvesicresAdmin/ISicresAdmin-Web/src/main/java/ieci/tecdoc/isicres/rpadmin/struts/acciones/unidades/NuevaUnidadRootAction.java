package ieci.tecdoc.isicres.rpadmin.struts.acciones.unidades;


import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.UnidadForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class NuevaUnidadRootAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(NuevaUnidadRootAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String idPadre = request.getParameter("idPadre");
		
		request.setAttribute("idPadre", idPadre);
		request.setAttribute("idTipo", (String)request.getSession(false).getAttribute("idTipo"));
		
		((UnidadForm)form).setTipo((String)request.getSession(false).getAttribute("idTipo"));
		
		return mapping.findForward("success");
		
	}

}
