package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DeleteSubProcessAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping, 
    								   ActionForm form, 
    								   HttpServletRequest request,
    								   HttpServletResponse response, 
    								   SessionAPI session) throws Exception {
    	
    	ClientContext cct = session.getClientContext();
    	
		IInvesflowAPI invesflowAPI = session.getAPI();
		ITXTransaction tx = invesflowAPI.getTransactionAPI();
    	IEntitiesAPI entitiesAPI = invesflowAPI.getEntitiesAPI();
    	IGenDocAPI genDocAPI= invesflowAPI.getGenDocAPI();
    	
    	// Estado del contexto de tramitación
    	IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
    	IState currentState = managerAPI.currentState(getStateticket(request));
    	
    	// Identificador del trámite del subproceso
    	String taskid = request.getParameter("idsTask");
    	int ntaskid = Integer.parseInt(taskid);
    	
        // Obtiene la lista de documentos generados en el subproceso
        String query = "WHERE ID_TRAMITE = " + ntaskid;
        IItemCollection itemcol = entitiesAPI.queryEntities(SpacEntities.SPAC_DT_DOCUMENTOS, query);
    	if (itemcol.next()) {
    		
			// Se eliminan los documentos
			Object connectorSession = null;
			try {
				connectorSession = genDocAPI.createConnectorSession();
	
				while(itemcol.next()) {
					
				    IItem document = itemcol.value();
				    String docref = document.getString("INFOPAG");
				    
				    if (StringUtils.isNotBlank(docref)) {
				    	
				        // Se borra el documento físico del repositorio
				        genDocAPI.deleteDocument(connectorSession, docref);
				    }
				    
				    // Se borra el registro que almacena los datos del documento
				    document.delete(session.getClientContext());
				}            	
			}
			finally {
				if (connectorSession != null) {
					genDocAPI.closeConnectorSession(connectorSession);
				}
	    	}
    	}
		
    	int nstageid = currentState.getStageId();
    	int nsubprocid = currentState.getSubProcessId();
    	
//    	String numexp = currentState.getNumexp();
    	
    	// Eliminar el subproceso
    	tx.deleteSubProcess(nsubprocid, ntaskid);
    	
    	// Eliminar los registros de las entidades específicas del subproceso
//    	int npcdid = currentState.getPcdId();
//    	IItemCollection itemcolPcd = entitiesAPI.getProcedureEntities(npcdid);
//    	Map mapPcd = itemcolPcd.toMapStringKey("ID_ENT");
//    	
//    	int nsubpcdid = currentState.getSubPcdId();
//    	IItemCollection itemcolSubPcd = entitiesAPI.getProcedureEntities(nsubpcdid);
//    	Map mapSubPcd = itemcolSubPcd.toMapStringKey("ID_ENT");
//    	
//    	Iterator it = mapSubPcd.entrySet().iterator();
//    	while (it.hasNext()) {
//    		
//    		Entry entry = (Entry) it.next();
//    		String entityId = (String) entry.getKey();
//    		if (!mapPcd.containsKey(entityId)) {
//    			
//    			// Eliminar registros de la entidad
//    			IItemCollection entityRegs = entitiesAPI.getEntities(Integer.parseInt(entityId), numexp);
//    			while (entityRegs.next()) {
//    				
//    				IItem entity = entityRegs.value();
//    				entity.delete(cct);
//    			}
//    		}
//    	}

    	// Nos situamos en la fase del expediente
    	ActionForward showexp =mapping.findForward("showexp");		
		return new ActionForward(showexp.getName(), showexp.getPath() + "?stageId=" + nstageid, true);
	}
    
}