/*
 * Created on Oct 29, 2004 by IECISA
 *
 */
package ieci.tdw.ispac.ispaccatalog.action.supervision;

import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.frmsupervision.DeleteSupervisedsForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DeleteFollowedModeSupervisedsAction extends BaseAction {
	
	public ActionForward executeAction(ActionMapping mapping, 
									   ActionForm form,
									   HttpServletRequest request, 
									   HttpServletResponse response, 
									   SessionAPI session) throws Exception	{
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_PERM_EDIT });

		DeleteSupervisedsForm deleteSupervisedsForm = (DeleteSupervisedsForm)form;
		String[] uids = deleteSupervisedsForm.getUids();
		String uid = request.getParameter("uid");

		ISecurityAPI api = session.getAPI().getSecurityAPI();
		api.deleteSuperviseds(uid, uids, ISecurityAPI.SUPERV_FOLLOWEDMODE);

	    ActionForward forward = mapping.findForward("showInfoEntry");
	    return new ActionForward (forward.getName(), forward.getPath() + "?" + request.getQueryString(), true);
	}
	
}