package ieci.tdw.ispac.ispaccatalog.action.publisher;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IPublisherAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ReactivateMilestoneAction extends BaseAction {

 	public ActionForward executeAction(ActionMapping mapping,
 									   ActionForm form,
 									   HttpServletRequest request,
 									   HttpServletResponse response,
 									   SessionAPI session) throws Exception	{
 		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_PUB_MILESTONES_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IPublisherAPI publisherAPI = invesFlowAPI.getPublisherAPI();
		
		int milestoneId = Integer.parseInt(request.getParameter("milestoneId"));
		int applicationId = Integer.parseInt(request.getParameter("applicationId"));
		String systemId = request.getParameter("systemId");
		//String objectId = request.getParameter("objectId");
		
		//publisherAPI.reactivateMilestone(milestoneId, applicationId, systemId, objectId);
		publisherAPI.reactivateMilestone(milestoneId, applicationId, systemId);
		
		return mapping.findForward("success");
	}
 	
}