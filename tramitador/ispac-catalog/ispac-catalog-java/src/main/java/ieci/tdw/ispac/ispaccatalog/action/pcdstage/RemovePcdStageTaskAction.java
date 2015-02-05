/*
 * Created on 11-nov-2005
 *
 */
package ieci.tdw.ispac.ispaccatalog.action.pcdstage;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.procedure.ManageVistaCuadroProcedimientoAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.TreeNode;
import ieci.tdw.ispac.ispaclib.bean.TreeView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 */
public class RemovePcdStageTaskAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IProcedureAPI pcdapi= invesFlowAPI.getProcedureAPI();

		int ptaskId = Integer.parseInt(request.getParameter("ptaskId"));
		int pstageId = Integer.parseInt(request.getParameter("pstageId"));
		
		try {
			pcdapi.removeTask(ptaskId);
		} catch(Exception e) {
			
			String message = null;
			if (e instanceof ISPACException) {
				message = ((ISPACException)e).getExtendedMessage(request.getLocale());
			} else {
				message = e.getLocalizedMessage();
			}
			request.setAttribute("MESSAGE", message);
			
			return mapping.findForward("showTreeProc");
		}
		
		TreeView tree = (TreeView)request.getSession().getAttribute(ManageVistaCuadroProcedimientoAction.CUADRO_PROCEDIMIENTO);
		if (tree!=null){
			request.setAttribute("Refresh","true");
			TreeNode parent = tree.getSelectedNode().getParent();
			tree.setSelectedNode(parent);
			parent.refresh();
			return mapping.findForward("showTreeProc");
		}else{
			ActionForward forward = mapping.findForward("success");
			String path = forward.getPath() + "&regId=" + pstageId;
			return new ActionForward( forward.getName(), path, true);
		}
	}
}
