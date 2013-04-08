package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaccatalog.action.procedure.ManageVistaCuadroProcedimientoAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.TreeView;
import ieci.tdw.ispac.ispaclib.context.ClientContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AddTemplateToProc extends BaseAction {

	 	public ActionForward executeAction(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		SessionAPI session)
		throws Exception
		{

			ClientContext cct = session.getClientContext();

	 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
			FunctionHelper.checkFunctions(request, cct, new int[] {
					ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

			IInvesflowAPI invesFlowAPI = session.getAPI();
			ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

			String sPlantId=request.getParameter("id_planttpdoc");
			String stProcId=request.getParameter("idProc");
			if (sPlantId==null || stProcId==null)
			    return mapping.findForward("success");

			int plantId=Integer.parseInt(sPlantId);
			int procId=Integer.parseInt(stProcId);


			synchronized (this) {
				if (catalogAPI.countCTEntities(ICatalogAPI.ENTITY_P_PLANTPROC, "WHERE ID_P_PLANTDOC =  "+ plantId +" AND ID_PCD = "+procId) == 0 ){
					IItem item=catalogAPI.createCTEntity(ICatalogAPI.ENTITY_P_PLANTPROC);
					//item.set("ID_TPDOC",tpdocId);
					//item.set("ID_PROC",procId);
					item.set("ID_PCD",procId);
					//item.set("ID_PLANTPDOC",plantId);
					item.set("ID_P_PLANTDOC",plantId);
					item.store(cct);
	
					TreeView tree = (TreeView)request.getSession().getAttribute(ManageVistaCuadroProcedimientoAction.CUADRO_PROCEDIMIENTO);
					if (tree!=null)
						tree.getSelectedNode().refresh();
				}
			}
			
			request.setAttribute("Refresh","true");
			


//			//Si ya existe no se crea y se debe notificar al usuario.
//			String sqlquery="WHERE ID_TPDOC = "+tpdocId+" AND ID_TRAMITE ="+taskId;
//			int ncount=catalogAPI.countCTEntities(ICatalogAPI.ENTITY_CT_TASKTYPEDOC,sqlquery);
//			if (ncount>0)
//			{
//			    //TODO El trámite ya está asociado.Mostrar mensaje de aviso.
//			    return mapping.findForward("success");
//			}


			return mapping.findForward("success");
		}

}
