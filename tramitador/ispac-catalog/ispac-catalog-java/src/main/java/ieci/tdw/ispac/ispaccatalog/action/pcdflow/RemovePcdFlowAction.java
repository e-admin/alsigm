package ieci.tdw.ispac.ispaccatalog.action.pcdflow;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.procedure.ManageVistaCuadroProcedimientoAction;
import ieci.tdw.ispac.ispaccatalog.action.procedure.tree.ElementoCuadro;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.TreeNode;
import ieci.tdw.ispac.ispaclib.bean.TreeView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para eliminar un flujo.
 */
public class RemovePcdFlowAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();

		// Identificador del procedimiento
		int pcdId = Integer.parseInt(request.getParameter("pcdId"));

		// Identificador del flujo
		int flowId = Integer.parseInt(request.getParameter("flowId"));

		// Eliminar el flujo
		procedureAPI.removeFlow(pcdId, flowId);
		
		// Visualizar el árbol del procedimiento
		TreeView tree = (TreeView)request.getSession().getAttribute(
				ManageVistaCuadroProcedimientoAction.CUADRO_PROCEDIMIENTO);
		if (tree != null) {
			
			// Actualizar el nodo padre (Flujos de Entrada/Salida)
			TreeNode flowNode = tree.getSelectedNode();
			TreeNode flowsNode = flowNode.getParent();
			tree.setSelectedNode(flowsNode);
			flowsNode.refresh();

			// Información del flujo
			ElementoCuadro flow = (ElementoCuadro) flowNode.getModelItem();
			String origId = flow.getProperty("FLOW:ID_ORIGEN");
			String destId = flow.getProperty("FLOW:ID_DESTINO");

			// Actualizar el nodo en la otra fase
			TreeNode stageNode = flowsNode.getParent();
			TreeNode pcdNode = stageNode.getParent();

			if (origId.equals(stageNode.getNodeId())) {
				tree.refreshNode(pcdNode.getNodePath() + "/item" + destId + "/item-1");
			} else if (destId.equals(stageNode.getNodeId())) {
				tree.refreshNode(pcdNode.getNodePath() + "/item" + origId + "/item-2");
			}
		}

		request.setAttribute("Refresh", "true");
		
		return mapping.findForward("showTreeProc");
	}
}
