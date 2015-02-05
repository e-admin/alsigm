package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.context.NextActivity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SendTrashAction extends BaseAction {

	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {


		ClientContext cct = session.getClientContext();
		// API de invesFlow
		IInvesflowAPI invesflowAPI =cct.getAPI();
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
		IState state = managerAPI.currentState(getStateticket(request));
		//Si tenemos el parámetro idsStage quiere decir que estamos en el listado de expedientes de un procedimiento
		//en una misma fase
		String idsStage = request.getParameter("idsStage");
		
		if (StringUtils.isNotBlank(idsStage)){
			int nidstage = 0;
			int nIdProcess = 0;
			String[] stageids = idsStage.split("-");
			for (int i = 0; i < stageids.length; i++) {
				IStage stage = null;
				nidstage = Integer.parseInt(stageids[i]);
				stage = invesflowAPI.getStage(nidstage);				
				nIdProcess = stage.getInt("ID_EXP");
				invesflowAPI.getTransactionAPI().sendProcessToTrash(nIdProcess);
			}
			return NextActivity.refresh(request, mapping, state);
		}
	
		else{
			
				invesflowAPI.getTransactionAPI().sendProcessToTrash(state.getProcessId());
		}
	
			if (logger.isInfoEnabled()) {
				logger.info("Expediente [" 
						+ cct.getStateContext().getNumexp() + "] enviado a la papelera");
			}

			return mapping.findForward("success");
		}
		

}
