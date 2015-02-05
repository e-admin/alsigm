package ieci.tdw.ispac.ispaccatalog.action.procedure;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.TreeView;
import ieci.tdw.ispac.ispaclib.context.ClientContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AddStageTpDocAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception {
    	
		ClientContext cct = session.getClientContext();
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, cct, new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });
		
		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		String sPcdStageId=request.getParameter("pcdStageId");
		String stpdocId=request.getParameter("tpdocId");
		if (sPcdStageId==null || stpdocId==null)
		    return mapping.findForward("success");

		int pcdStageId=Integer.parseInt(sPcdStageId);
		int tpdocId=Integer.parseInt(stpdocId);

		request.setAttribute("Refresh","true");
		


		//Si ya existe no se crea y se debe notificar al usuario.
		String sqlquery="WHERE ID_TPDOC = "+tpdocId+" AND ID_FASE ="+pcdStageId;
		int ncount=catalogAPI.countCTEntities(ICatalogAPI.ENTITY_P_FSTD,sqlquery);
		if (ncount>0)
		{
		    //TODO El trámite ya está asociado.Mostrar mensaje de aviso.
		    return mapping.findForward("success");
		}

		IItem item = catalogAPI.createCTEntity(ICatalogAPI.ENTITY_P_FSTD);
		item.set("ID_TPDOC", tpdocId);
		item.set("ID_FASE", pcdStageId);
		item.store(cct);
		
		TreeView tree = (TreeView)request.getSession().getAttribute(ManageVistaCuadroProcedimientoAction.CUADRO_PROCEDIMIENTO);
		if (tree!=null)
			tree.getSelectedNode().refresh();
		
		return mapping.findForward("success");
		
//
//		IItem item=catalogAPI.createCTEntity(ICatalogAPI.ENTITY_P_FSTD);
//		item.set("ID_FASE", stageId);
//		item.set("ID_TPDOC", documentId);
//		item.store(cct);
//
//		ActionForward forward = mapping.findForward("success");
//		String redirected = forward.getPath() + "?StageId=" + stageId;
//		return new ActionForward( forward.getName(), redirected, true);
    }
}
