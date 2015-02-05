package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInboxAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.audit.business.vo.AuditContext;
import ieci.tdw.ispac.audit.context.AuditContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ArchiveIntrayAction extends BaseAction {
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
		IInvesflowAPI invesflowAPI = session.getAPI();
		IInboxAPI inbox = invesflowAPI.getInboxAPI();
		
		//TODO: Auditoría: Añadir en el ThreadLocal el objeto AuditContext.
		setAuditContext(request);

		// Identificador del registro distribuido
		String register = request.getParameter("register");

		// Archivar registro distribuido
		inbox.archiveIntray(register);

		return mapping.findForward("success");
	}

	/**
	 * @param request
	 */
	private void setAuditContext(HttpServletRequest request) {
		AuditContext auditContext = new AuditContext();
		auditContext.setUserHost(request.getRemoteHost());
		auditContext.setUserIP(request.getRemoteAddr());		
		AuditContextHolder.setAuditContext(auditContext);
	}
}