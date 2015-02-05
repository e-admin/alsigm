package ieci.tdw.ispac.ispaccatalog.action.frmtasks;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.context.IClientContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author alberto
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DelEntityFrmTaskAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception {
        
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

        IClientContext cct = session.getClientContext();
		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		int itemId = Integer.parseInt(request.getParameter("TaskId"));
		int frmtaskId = Integer.parseInt(request.getParameter("FrmTaskDel"));

		IItem item=catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_FRMTASK, frmtaskId);
		item.delete(cct);

		ActionForward forward = mapping.findForward("success");
		String redirected = forward.getPath()
						  + "?TaskId=" + itemId;
		return new ActionForward( forward.getName(), redirected, true);
    }

}
