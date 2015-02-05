package ieci.tdw.ispac.ispaccatalog.action.ctstage;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.form.SelectObjForm;
import ieci.tdw.ispac.ispaccatalog.action.procedure.ManageVistaCuadroProcedimientoAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.TreeView;
import ieci.tdw.ispac.ispaclib.context.ClientContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class AddCTStageTaskAction extends BaseAction
{

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
				ISecurityAPI.FUNC_INV_STAGES_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();
		String[] tasksIds= ((SelectObjForm)form).getMultibox();
		if (tasksIds==null){
			tasksIds = new String[1];
			tasksIds[0]= request.getParameter("taskId");
		}
		
		String sctstageid=request.getParameter("ctstageId");
		if (tasksIds==null || sctstageid==null)
		    return mapping.findForward("success");

		int ctstageId=Integer.parseInt(sctstageid);

		request.setAttribute("Refresh","true");
		
		//para cada task
		IItem item;
		int taskId;
		String sqlquery;
		int ncount;
		for (int i = 0; i < tasksIds.length; i++) {
			taskId=Integer.parseInt(tasksIds[i]);

			//Si ya existe no se crea y se debe notificar al usuario.
			sqlquery = new StringBuffer("WHERE ID_FASE=").append(ctstageId)
				.append(" AND ID_TRAMITE=").append(taskId).toString();
			ncount=catalogAPI.countCTEntities(ICatalogAPI.ENTITY_CT_STAGETASK,sqlquery);
			if (ncount==0) {
				item=catalogAPI.createCTEntity(ICatalogAPI.ENTITY_CT_STAGETASK);
				item.set("ID_FASE",ctstageId);
				item.set("ID_TRAMITE",taskId);
				item.store(cct);
			}
		}
		
		TreeView tree = (TreeView)request.getSession().getAttribute(ManageVistaCuadroProcedimientoAction.CUADRO_PROCEDIMIENTO);
		if (tree!=null)
			tree.getSelectedNode().refresh();
		
		return mapping.findForward("success");
	}
}
