package ieci.tdw.ispac.ispaccatalog.action.frmstages;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.context.IClientContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AddAppFrmStageAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

        IClientContext cct = session.getClientContext();
		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		int itemId = Integer.parseInt(request.getParameter("FrmStageId"));

		IItem item=catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_FRMSTAGE, itemId);

		String stageId = item.get("ID_FASE").toString();


		String appId=request.getParameter("AppId");
		if (appId==null || appId.length()==0)
		    item.set("FRM_EDIT", (Object) null);
		else
		    item.set("FRM_EDIT", Integer.parseInt(appId));

		item.store(cct);

		ActionForward forward = mapping.findForward("success");
		String redirected = forward.getPath()
						  + "?StageId=" + stageId;
		return new ActionForward( forward.getName(), redirected, true);
    }

}
