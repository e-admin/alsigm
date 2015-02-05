package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInboxAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AcceptIntrayAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
		IInvesflowAPI invesflowAPI = session.getAPI();
    	IInboxAPI inbox = invesflowAPI.getInboxAPI();

        // Registro de entrada
		String register = request.getParameter("register");

		// Aceptar del registro de entrada
		inbox.acceptIntray(register);

		// Retornar a la página del registro distribuido
		ActionForward forward = mapping.findForward("success");
		String path = new StringBuffer(forward.getPath())
			.append(forward.getPath().indexOf("?") > 0 ? "&" : "?")
			.append("id=").append(register)
			.toString();
		
		return new ActionForward(forward.getName(), path, forward.getRedirect());
	}
}
