package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DeleteExpedientRelationAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, 
									   ActionForm form,
									   HttpServletRequest request, 
									   HttpServletResponse response,
									   SessionAPI session) throws Exception {
		
		/*
		IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(session.getClientContext());
		IState state = managerAPI.currentState(getStateticket(request));
		*/
		
		String keyId = request.getParameter(ActionsConstants.PARAM_KEY);
		IEntitiesAPI entitiesAPI = session.getAPI().getEntitiesAPI();
		IItem item = entitiesAPI.getEntity(SpacEntities.SPAC_EXP_RELACIONADOS,Integer.parseInt(keyId));
		if (item != null)
			item.delete(session.getClientContext());
		
		return mapping.findForward("success");
		
		/*
		ActionForward forward = mapping.findForward("success");
		return new ActionForward(forward.getName(), forward.getPath()+"?stageId="+state.getStageId(), forward.getRedirect());
		*/
	}

}