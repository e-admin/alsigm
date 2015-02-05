package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.context.NextActivity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class OpenStageAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, 
									   ActionForm form,
									   HttpServletRequest request, 
									   HttpServletResponse response,
									   SessionAPI session) throws Exception {
		
		ClientContext cct = session.getClientContext();
		
		IInvesflowAPI invesflowAPI = session.getAPI();
		ITXTransaction tx = invesflowAPI.getTransactionAPI();
		
		// Estado del contexto de tramitación
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
		IState currentstate = managerAPI.currentState(getStateticket(request));
		
		String idPcdStage = request.getParameter("idPStage");
		String idProcess = request.getParameter("idProcess");

		int nIdPcdStage = Integer.parseInt(idPcdStage);
		int nIdProcess = Integer.parseInt(idProcess);

		// Abrir las siguientes fases del flujo
		tx.openNextStages(nIdProcess, nIdPcdStage);

		// En el caso de cerrar una sola fase de un expediente se intentará
		// seguir trabajando con el expediente
		if (nIdProcess > 0) {
			
			return NextActivity.afterCloseStage(request, nIdProcess, invesflowAPI, mapping);
		}
		
		return NextActivity.refresh(request, mapping, currentstate);
	}
	
}