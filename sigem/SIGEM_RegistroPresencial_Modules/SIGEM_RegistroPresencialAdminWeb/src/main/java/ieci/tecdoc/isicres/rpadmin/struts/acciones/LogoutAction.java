package ieci.tecdoc.isicres.rpadmin.struts.acciones;

import ieci.tecdoc.isicres.rpadmin.struts.util.SesionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LogoutAction extends RPAdminWebAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		request.getSession().invalidate();
		response.sendRedirect(SesionHelper.getWebAuthDesconectURL(request));
		return null;
	}

}
