package ieci.tdw.ispac.ispaccatalog.action.pcdstage;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
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
 * Action para subir/bajar un trámite dentro de una fase
 * de un procedimiento.
 */
public class MovePcdStageTaskAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		int ptaskId = Integer.parseInt(request.getParameter("ptaskId"));
		int pstageId = Integer.parseInt(request.getParameter("pstageId"));
		String direction = request.getParameter("dir");
		
		IInvesflowAPI invesFlowAPI = session.getAPI();
		IProcedureAPI pcdapi= invesFlowAPI.getProcedureAPI();
		
		if ("down".equalsIgnoreCase(direction)) {
			pcdapi.moveTaskDown(ptaskId);
		} else if ("up".equalsIgnoreCase(direction)) {
			pcdapi.moveTaskUp(ptaskId);
		}
		
		TreeView tree = (TreeView)request.getSession().getAttribute(ManageVistaCuadroProcedimientoAction.CUADRO_PROCEDIMIENTO);
		if (tree != null) {
			request.setAttribute("Refresh","true");
			TreeNode parent = tree.getSelectedNode().getParent();
			tree.setSelectedNode(parent);
			parent.refresh();
			return mapping.findForward("showTreeProc");
		} else {
			ActionForward forward = mapping.findForward("success");
			String path = forward.getPath() + "&regId=" + pstageId;
			return new ActionForward( forward.getName(), path, true);
		}
	}
}
