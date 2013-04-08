package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.ITemplateAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.procedure.ManageVistaCuadroProcedimientoAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.TreeNode;
import ieci.tdw.ispac.ispaclib.bean.TreeView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RemoveTemplateFromProc extends BaseAction {
 	
	public ActionForward executeAction(
 			ActionMapping mapping,
 			ActionForm form,
 			HttpServletRequest request,
 			HttpServletResponse response,
 			SessionAPI session)
 			throws Exception
 			{

		 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
				FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
						ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });
		
				IInvesflowAPI invesFlowAPI = session.getAPI();
 				ITemplateAPI templateAPI= invesFlowAPI.getTemplateAPI();

 				String sPlantId=request.getParameter("idTemplate");
 				String stProcId=request.getParameter("pcdId");
 				if (sPlantId==null || stProcId==null)
 				    return mapping.findForward("showTreeProc");

 				int plantId=Integer.parseInt(sPlantId);
 				int procId=Integer.parseInt(stProcId);
 				templateAPI.deleteTemplateProc(procId, plantId);
 				
 				TreeView tree = (TreeView)request.getSession().getAttribute(ManageVistaCuadroProcedimientoAction.CUADRO_PROCEDIMIENTO);
 				if (tree!=null){
 					TreeNode parent = tree.getSelectedNode().getParent();
 					tree.setSelectedNode(parent);
 					parent.refresh();
 					return mapping.findForward("showTreeProc");
 				}
 				
 				request.setAttribute("Refresh","true");
 				
 				return mapping.findForward("showTreeProc");
 			}

}
