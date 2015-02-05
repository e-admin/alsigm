package ieci.tdw.ispac.ispaccatalog.action.pcdflow;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.procedure.ManageVistaCuadroProcedimientoAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.TreeNode;
import ieci.tdw.ispac.ispaclib.bean.TreeView;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFlujoDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para añadir un flujo en un procedimiento.
 *
 */
public class AddPcdFlowAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IProcedureAPI procedureAPI= invesFlowAPI.getProcedureAPI();
		
		int pcdId = Integer.parseInt(request.getParameter("pcdId"));
		int nodeId = Integer.parseInt(request.getParameter("nodeId"));
		int selectedNodeId = Integer.parseInt(request.getParameter("selectedNodeId"));
		String flowType = request.getParameter("flowType");

		
		boolean inserted = false;
		// Crear el flujo 
		if ("IN".equals(flowType)) {
			synchronized (this) {
				if (!PFlujoDAO.checkFlowInstance(session.getClientContext().getConnection(), pcdId, selectedNodeId, nodeId)){
					procedureAPI.createFlow(pcdId, selectedNodeId, nodeId);
					inserted = true;
				}
			}
		} else if ("OUT".equals(flowType)) {
			synchronized (this) {
				if (!PFlujoDAO.checkFlowInstance(session.getClientContext().getConnection(), pcdId, nodeId, selectedNodeId)){
					procedureAPI.createFlow(pcdId, nodeId, selectedNodeId);
					inserted = true;
				}
			}
		}

		if (inserted){
			// Actualizar el árbol del procedimiento
			TreeView tree = (TreeView)request.getSession().getAttribute(
					ManageVistaCuadroProcedimientoAction.CUADRO_PROCEDIMIENTO);
			if (tree!=null) {
				
				// Actualizar el nodo seleccionado
				TreeNode selectedNode = tree.getSelectedNode();
				selectedNode.refresh();
	
				// Actualizar el flujo en la otra fase
				TreeNode pcdNode = selectedNode.getParent().getParent();
				if ("IN".equals(flowType)) {
					tree.refreshNode(pcdNode.getNodePath() + "/item" + selectedNodeId + "/item-2");
				} else if ("OUT".equals(flowType)) {
					tree.refreshNode(pcdNode.getNodePath() + "/item" + selectedNodeId + "/item-1");
				}
			}
		}
		
		// Refrescar la pantalla principal
		request.setAttribute("Refresh", "true");
		
		return mapping.findForward("success");
    }
}
