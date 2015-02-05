package ieci.tdw.ispac.ispaccatalog.action.procedure;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.TreeNode;
import ieci.tdw.ispac.ispaclib.bean.TreeView;
import ieci.tdw.ispac.ispaclib.context.ClientContext;

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
public class DeleteStageTpDocAction extends BaseAction {
	
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception {
        
        ClientContext cct = session.getClientContext();
        
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, cct, new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });
        
		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		int itemId = Integer.parseInt(request.getParameter("idfstd"));

		IItem item = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_FSTD, itemId);
		item.delete(cct);
		
		TreeView tree = (TreeView)request.getSession().getAttribute(ManageVistaCuadroProcedimientoAction.CUADRO_PROCEDIMIENTO);
		if (tree!=null){
			TreeNode actualnode = tree.getSelectedNode().getParent();
			tree.setSelectedNode(actualnode);
			actualnode.refresh();
		}
		
		request.setAttribute("Refresh","true");
		
		return mapping.findForward("success");
    }
}
