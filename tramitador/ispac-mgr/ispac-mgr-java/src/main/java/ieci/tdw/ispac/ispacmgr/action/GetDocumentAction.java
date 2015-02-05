package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IWorklistAPI;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.api.item.ITask;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.impl.states.ExpedientState;
import ieci.tdw.ispac.ispacweb.api.impl.states.SubProcessState;
import ieci.tdw.ispac.ispacweb.api.impl.states.TaskState;
import ieci.tdw.ispac.ispacweb.util.DocumentUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GetDocumentAction extends BaseAction {
	
	public ActionForward executeAction(ActionMapping mapping,
									   ActionForm form,
									   HttpServletRequest request,
									   HttpServletResponse response,
									   SessionAPI session) throws Exception {

	    ClientContext cct = session.getClientContext();
	    
		IInvesflowAPI invesFlowAPI = session.getAPI();
		IEntitiesAPI entityAPI = invesFlowAPI.getEntitiesAPI();
		IWorklistAPI workListAPI = invesFlowAPI.getWorkListAPI();
		IGenDocAPI genDocAPI = invesFlowAPI.getGenDocAPI();

		///////////////////////////////////////////////
		// Se obtiene el estado de tramitación
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
		IState currentState = managerAPI.currentState(getStateticket(request));

		String document = request.getParameter("document");
		if (StringUtils.isNotEmpty(document)) {
	
			IItem entity = entityAPI.getDocument(Integer.parseInt(document));
			if (entity != null) {
				
				String readonly = request.getParameter("readonly");
				if (StringUtils.isEmpty(readonly)) {
					
					readonly = "false";
				}
				
				boolean isResponsible = false;
				
				if (currentState instanceof SubProcessState) {
					
					int activityId = currentState.getActivityId();
					IStage activity = invesFlowAPI.getStage(activityId);
					if (activity != null) {
						
						String sUID = activity.getString("ID_RESP");
						isResponsible = workListAPI.isInResponsibleList(sUID , activity);
					}
				}
				else if (currentState instanceof TaskState) {
					
					int taskId = currentState.getTaskId();
					ITask task = invesFlowAPI.getTask(taskId);
					if (task != null) {
						
						String sUID = task.getString("ID_RESP");
						isResponsible = workListAPI.isInResponsibleList(sUID , task);
					}
				}
				else if (currentState instanceof ExpedientState) {
				
					int stageId = currentState.getStageId();
					IStage stage = invesFlowAPI.getStage(stageId);
					if (stage != null) {
						
						String sUID = stage.getString("ID_RESP");
						isResponsible = workListAPI.isInResponsibleList(sUID, stage);
					}
				}
				
			  	if (!isResponsible) {
			  		
			  		readonly = "true";
			  	}
	
				String docref = entity.getString("INFOPAG");
				if (docref == null) {
					
					throw new ISPACInfo("exception.documents.notExists");
				}
				
				Object connectorSession = null;
				
				try {
					connectorSession = genDocAPI.createConnectorSession();
					if (!genDocAPI.existsDocument(connectorSession, docref)){
						logger.error("No se ha encontrado el documento físico con identificador: '"+docref+"' en el repositorio de documentos");
						throw new ISPACInfo("exception.documents.notExists", false);
					}
					// Procesar el tipo del fichero anexado al documento
					String sMimetype = genDocAPI.getMimeType(connectorSession, docref);
					
					DocumentUtil.viewDocument(getServlet().getServletContext(),
											  request,
											  response,
											  "storage",
											  session,
											  document,
											  docref,
											  sMimetype,
											  readonly,
											  true, false);
				}
				finally {
					
					if (connectorSession != null) {
						genDocAPI.closeConnectorSession(connectorSession);
					}
		    	}
			}
		}

		return null;
	}
	
}