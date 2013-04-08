package ieci.tecdoc.isicres.rpadmin.struts.acciones.transportes;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class NuevoTransporteAction extends RPAdminWebAction {
	
//	private static final Logger logger = Logger.getLogger(NuevoUsuarioAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		return mapping.findForward("success");
	}

}
