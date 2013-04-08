package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ITemplateAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SelectTemplateDocumentEntityAction extends BaseAction {

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
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		IGenDocAPI gendocAPI = invesFlowAPI.getGenDocAPI();

		List listTiposDoc = new ArrayList(); 
		List listTask= new ArrayList();

   	    // Obtener el tipo de documento
        String field = request.getParameter("field");
        if(field==null){
        	field="";
        }
        String documentTypeId = request.getParameter(field);
        String idTask = request.getParameter("idTask");
        
        // Si se recibe el tipo de documento es que se está sustituyendo la plantilla de un documento
        // y sólo se cargarán las plantillas para ese tipo de documento
        if (documentTypeId != null) {
        	
        	IItem item = entitiesAPI.getEntity(SpacEntities.SPAC_CT_TPDOC, Integer.parseInt(documentTypeId));	
        	listTiposDoc.add(new ItemBean(item));
        	
        	if (StringUtils.isEmpty(request.getParameter("new"))){
	        	// Establecer el id del documento
	        	int documentId = currentState.getEntityRegId();
	        	request.setAttribute("documentId", String.valueOf(documentId));
        	}
        }
        else {
    		IItemCollection collection = null;
    		IItemCollection collectionTask=null;
    		
    		
    		if(StringUtils.isNotBlank(idTask) )
    		{
    			int idTarea=Integer.parseInt(idTask);
    			collection = gendocAPI.getDocTypesFromTaskCTL(idTarea);
    			
    		}
    		// Obtener los tipos de documentos asociados al trámite
    		else if (currentState.getTaskCtlId() != 0){
    			collection = gendocAPI.getDocTypesFromTaskCTL(currentState.getTaskCtlId());
    		}
    		else if (currentState.getTaskPcdId() != 0){
    			collection = gendocAPI.getDocTypesFromTaskPCD(currentState.getTaskPcdId());
    		}
    		else {
    			//Obtener los tramites asociados a la fase actual
        		IProcedure procedure = invesFlowAPI.getProcedure(currentState.getPcdId());
        		collectionTask = procedure.getTasks(currentState.getStagePcdId());
        		//Si estamos en una fase entonces sacaremos los tipos documentales asociados a esa fase y los tramites asociados esa fase
        		if(currentState.getStagePcdId()!=0){
        			collection=gendocAPI.getDocTypesFromStage(currentState.getStagePcdId());
        		}
    		}
    		
    		 
    		
    		
    		
    		if (collection != null) {
    			listTiposDoc = CollectionBean.getBeanList(collection);
    		}
    		if(collectionTask !=null){
    			
    			listTask=CollectionBean.getBeanList(collectionTask);
    		}
        }
        
		if (!listTiposDoc.isEmpty()) {
			
			// Obtener las plantillas para los tipos de documentos cargados
			Iterator it = listTiposDoc.iterator();
			while (it.hasNext()) {
				ItemBean itemBean = (ItemBean) it.next();

				IItemCollection collection = null;
				if (currentState.getState() == ManagerState.SUBPROCESSESLIST || currentState.getState() == ManagerState.PROCESSESLIST)
					collection = gendocAPI.getTemplatesInStage(Integer.parseInt(itemBean.getString("ID")), 0);
				else if (currentState.getTaskId() != 0)
					collection = gendocAPI.getTemplatesInTask(Integer.parseInt(itemBean.getString("ID")), currentState.getTaskId());
				else
					collection = gendocAPI.getTemplatesInStage(Integer.parseInt(itemBean.getString("ID")), currentState.getStageId());
				
				if (collection != null) {
					
					List ltTemplates = CollectionBean.getBeanList(collection);
					itemBean.setProperty("TEMPLATES", ltTemplates);
					
					//Para calcular si son plantillas especificas o genéricas
			        ITemplateAPI templateAPI = invesFlowAPI.getTemplateAPI();		        
			        for (Iterator iter = ltTemplates.iterator(); iter.hasNext();) {
						 ItemBean element = (ItemBean) iter.next();
						 int id = element.getItem().getInt("ID");
						 if (!templateAPI.isProcedureTemplate(id))
							 element.setProperty("NOMBRE", element.getProperty("NOMBRE")+"(G)");	 
					}
				}
				
			}
		}
		
		
		if(listTiposDoc.size()>0){
		request.setAttribute("ltDocumentTypesTemplates", listTiposDoc);
		}
		if(listTask.size()>0){
		request.setAttribute("ltTasks", listTask);
		}
		if(request.getParameter("volver")!=null){
			request.setAttribute("volver", "true");
			
		}
		if(request.getParameter("idTaskPcd")!=null){
			request.setAttribute("idTaskPcd", request.getParameter("idTaskPcd"));
		}
		
		return mapping.findForward("success");
	}

}
