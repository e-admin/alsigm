package ieci.tdw.ispac.ispaccatalog.action.procedure;

import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.procedure.tree.CuadroEntidad;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.TreeNode;
import ieci.tdw.ispac.ispaclib.bean.TreeView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para mostrar/ocultar la información extendida del procedimiento.
 */
public class ShowProcedureExtendedInfoAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_READ,
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

    	// Flag que indica si hay que mostrar la información extendida
    	String show = request.getParameter("show");
    	
		// Visualizar el árbol del procedimiento
		TreeView tree = (TreeView)request.getSession().getAttribute(
				ManageVistaCuadroProcedimientoAction.CUADRO_PROCEDIMIENTO);
		if (tree != null) {
			
			// Información del cuadro
			CuadroEntidad cuadro = (CuadroEntidad) tree.getTreeModel();
			cuadro.setShowExtendedInfo("true".equalsIgnoreCase(show));
			
			// Seleccionar el nodo raíz del árbol
			TreeNode pcd = (TreeNode)tree.getRootNodes().iterator().next();
			tree.setSelectedNode(pcd);
			pcd.refresh();
			
			return mapping.findForward("success");
		}

		request.setAttribute("Refresh", "true");
		
		return mapping.findForward("success");
	}
}
