package ieci.tecdoc.isicres.rpadmin.struts.acciones;

import ieci.tecdoc.isicres.rpadmin.struts.util.SesionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ChequearSessionAction extends RPAdminWebAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		if(!SesionHelper.authenticate(request)) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
		
		return null;
	}

}
