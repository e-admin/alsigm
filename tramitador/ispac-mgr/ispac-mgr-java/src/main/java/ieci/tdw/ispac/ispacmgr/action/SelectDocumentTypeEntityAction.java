package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SelectDocumentTypeEntityAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping,
	    							   ActionForm form,
	    							   HttpServletRequest request,
	    							   HttpServletResponse response,
	    							   SessionAPI session) throws Exception {
		
        ClientContext cct = session.getClientContext();
        String idTaskPcd=request.getParameter("idTaskPcd");
        if(request.getParameter("entity")!=null){
        	request.getSession().removeAttribute("idTaskPcd");
        	idTaskPcd=null;
        }
        
		///////////////////////////////////////////////
		// Se obtiene el estado de tramitación
        IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
   	    IState currentState = managerAPI.currentState(getStateticket(request));
     
   	    IGenDocAPI gendocAPI = session.getAPI().getGenDocAPI();

		
		IItemCollection collection = null;
		IItemCollection collectionTask=null;
		List listTiposDoc = new ArrayList(); 
		List listTask= new ArrayList();
		IInvesflowAPI invesFlowAPI = session.getAPI();
		
		// Obtener los tipos de documentos asociados al trámite
		if (currentState.getTaskCtlId() != 0){
			collection = gendocAPI.getDocTypesFromTaskCTL(currentState.getTaskCtlId());
		}
		else if (currentState.getTaskPcdId() != 0){
			collection = gendocAPI.getDocTypesFromTaskPCD(currentState.getTaskPcdId());
		}
		else if(StringUtils.isNotEmpty(idTaskPcd)){
			collection = gendocAPI.getDocTypesFromTaskPCD(Integer.parseInt(idTaskPcd));
		}
		//Si estamos es una fase sacamos los tipos documentales asociados a esa fase y el listado de trámites asociados a esa fase
		else if(currentState.getStagePcdId()!=0){
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
		

    		if(listTask.size()>0){
    		request.setAttribute("ltTasks", listTask);
    		}
    		if(request.getParameter("volver")!=null){
    			request.setAttribute("volver", "true");
    			
    		}
    		else{
    			request.getSession().removeAttribute("idTaskPcd");
    		}
    	
    		if(idTaskPcd!=null){
    			request.getSession().removeAttribute("idTaskPcd");
    			request.getSession().setAttribute("idTaskPcd", idTaskPcd);
    		}
    		
    		
    	
    		request.getSession().setAttribute("docEntidad", "true");
    		
    		
    		request.setAttribute("ltDocumentTypes", listTiposDoc);
		
		String action = request.getParameter("action");
		if (StringUtils.isBlank(action)) {
			action = "attachFile.do";
		}
		request.setAttribute("action", action);
		
	
		return mapping.findForward("success");
	}

}
