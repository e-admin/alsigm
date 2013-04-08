package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInboxAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaclib.sicres.vo.Intray;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * Action para rechazar un registro distribuido.
 *
 */
public class RejectIntrayAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

		IInvesflowAPI invesflowAPI = session.getAPI();
    	IInboxAPI inbox = invesflowAPI.getInboxAPI();

		// Identificador del registro distribuido
		String register = request.getParameter("register");
		
		// Motivo del rechazo
		String motivo = request.getParameter("motivo");
		if (StringUtils.isBlank(motivo)) {
			ActionMessages errors = new ActionMessages();
			errors.add("", new ActionMessage("intray.reject.error.motivo"));
			saveErrors(request, errors);
			return mapping.findForward("error");
		}

        // Registro de entrada
		Intray intray = inbox.getIntray(register);
		request.setAttribute("INTRAY", intray);

		// Rechazar el registro distribuido
		inbox.rejectIntray(register, motivo);

		return mapping.findForward("success");
	}
}
