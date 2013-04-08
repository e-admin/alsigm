package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.ISPACVariable;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CreateDocumentStageAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

	    ClientContext cct = session.getClientContext();
	    request.getSession().removeAttribute("docEntidad");
	    // Estado del contexto de tramitación
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
		IState currentState = managerAPI.currentState(getStateticket(request));
		
		if ((currentState.getState() == ManagerState.SUBPROCESSESLIST) || 
			(currentState.getState() == ManagerState.PROCESSESLIST)) {
			
			String idsStage = request.getParameter("idsStage");
			cct.setSsVariable(ISPACVariable.STAGES_ACTIVITES_DOCUMENTS_GENERATION, idsStage);
		}
		
		return mapping.findForward("success");
	}

}
