package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IBPMAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CaptureExpedientAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
		ClientContext cct = session.getClientContext();
		IInvesflowAPI invesFlowAPI = cct.getAPI();

    	//----
    	//BPM
    	//----
    	//Se adquiere el expediente	
    	IBPMAPI bpmAPI = null;
    	boolean commit = true;
    	int stageId = Integer.parseInt(request.getParameter(ManagerState.PARAM_STAGEID));
    	try{
    		bpmAPI = invesFlowAPI.getBPMAPI();
			//Iniciamos la sesion con el BPM
			bpmAPI.initBPMSession();

			IStage stage = invesFlowAPI.getStage(stageId);
			bpmAPI.captureStage(stage.getString("ID_FASE_BPM"), cct.getRespId());

		}catch(ISPACException e){
			commit = false;
			throw e;
		}finally{
			if (bpmAPI != null)
				bpmAPI.closeBPMSession(commit);
		}		

		ActionForward forward = mapping.findForward("success");
		String path = forward.getPath() + "?"+ ManagerState.PARAM_STAGEID + "=" + stageId;
	    forward = new ActionForward( forward.getName(), path, true);
		return forward;
	}

}
