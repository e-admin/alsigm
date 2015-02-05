package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispacmgr.action.BaseAction;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;
import ieci.tdw.ispac.ispacweb.util.DocumentUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AttachTemplateAction extends BaseAction {
	
	public ActionForward executeAction(ActionMapping mapping,
									   ActionForm form,
									   HttpServletRequest request,
									   HttpServletResponse response,
									   SessionAPI session) throws Exception {
		
	    ClientContext cct = session.getClientContext();

		///////////////////////////////////////////////
		// Se obtiene el estado de tramitación
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
		IState currentState = managerAPI.currentState(getStateticket(request));
		
  	    IInvesflowAPI invesFlowAPI = session.getAPI();
		IGenDocAPI genDocAPI = invesFlowAPI.getGenDocAPI();

		int keyId = currentState.getEntityRegId();
		int stageId = currentState.getStageId();
		int taskId = currentState.getTaskId();
		int state = currentState.getState();

		int templateId = Integer.parseInt(request.getParameter("templateId"));

		IItem entity = null;

		
		Object connectorSession = null;
		try {
			connectorSession = genDocAPI.createConnectorSession();
			switch (state) {
			
				case ManagerState.EXPEDIENT :
					entity = genDocAPI.attachStageTemplate(connectorSession, stageId, keyId, templateId);
					break;
					
				case ManagerState.TASK :
					entity = genDocAPI.attachTaskTemplate(connectorSession, taskId, keyId, templateId);
					break;
					
				default :
					return null;
			}
			
			// Referencia al fichero en el gestor documental
			String docref = entity.getString("INFOPAG");
			String sMimetype = genDocAPI.getMimeType(connectorSession, docref);
	
			boolean viewDocument = DocumentUtil.viewDocument(getServlet().getServletContext(),
										   					 request,
										   					 response,
										   					 "storage",
										   					 session,
										   					 String.valueOf(keyId),
										   					 docref,
										   					 sMimetype,
										   					 null,
										   					 false, true);
			if (!viewDocument) {
				
				throw new ISPACInfo("No se puede visualizar el documento");
			}
		}finally {
			if (connectorSession != null) {
				genDocAPI.closeConnectorSession(connectorSession);
			}
    	}	
			    
		return null;
	}
}
