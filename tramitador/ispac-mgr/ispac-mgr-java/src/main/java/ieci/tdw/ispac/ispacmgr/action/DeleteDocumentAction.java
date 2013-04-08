package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.sicres.RegisterHelper;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DeleteDocumentAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping,
    								   ActionForm form,
    								   HttpServletRequest request,
    								   HttpServletResponse response,
    								   SessionAPI session) throws Exception {

	    ClientContext cct = session.getClientContext();
	    
	    IEntitiesAPI entitiesAPI = session.getAPI().getEntitiesAPI();

	    // Estado del contexto de tramitación
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
		IState currentState = managerAPI.currentState(getStateticket(request));

		// Identificador del documento a borrar
        String documentId = request.getParameter("documentId");
        
        //Se desvincula el apunte de registro vinculado si procede
        RegisterHelper.onDeleteDocument(cct, entitiesAPI.getDocument(Integer.parseInt(documentId)));

        // Eliminar el documento
        entitiesAPI.deleteDocument(Integer.parseInt(documentId));
	
        
		//El destino dependera si eliminamos un documento que esta asociado a la actividad, al trámite o a la fase 
    	if (currentState.getState() == ManagerState.SUBPROCESS) {
    		
	    	int entityId = currentState.getEntityId();
			String param = "&taskId=" + currentState.getTaskId() + "&activityId=" + currentState.getActivityId();
	    	
			ActionForward action =  mapping.findForward("showsubprocess");
			return new ActionForward(action.getName(), action.getPath() + "?entity=" + entityId + param, true);
    	}
    	else if (currentState.getTaskId() != 0) {
    	
	    	int entityId = SpacEntities.SPAC_DT_TRAMITES;//currentState.getEntityId();
			String keyId = request.getParameter("keyId");//currentState.getEntityRegId();
			String param = "&taskId=" + currentState.getTaskId();
	    	
			ActionForward action =  mapping.findForward("showtask");
			return new ActionForward(action.getName(), action.getPath() + "?entity=" + entityId + "&key=" + keyId + param, true);
    	}
    	else {
	    	int entityId = currentState.getEntityId();
			//int keyId = currentState.getEntityRegId();
	    	int stageId = currentState.getStageId();
	    	
			ActionForward action =  mapping.findForward("showexp");
			return new ActionForward(action.getName(), action.getPath() + "?stageId=" + stageId + "&entity=" + entityId, true);
    	}
    }
    
}