package ieci.tecdoc.isicres.rpadmin.struts.acciones.asuntos;

import java.util.ArrayList;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.AsuntoForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class NuevoAsuntoAction extends RPAdminWebAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		setInSession(request, KEY_LISTA_OFICINAS_ASUNTOS, new ArrayList());
		setInSession(request, KEY_LISTA_DOCUMENTOS_ASUNTOS, new ArrayList());
		
		return mapping.findForward("success");
	}
}
