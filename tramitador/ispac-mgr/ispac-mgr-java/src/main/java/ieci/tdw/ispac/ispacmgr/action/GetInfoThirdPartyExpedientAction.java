package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GetInfoThirdPartyExpedientAction extends BaseAction {
    
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
   	    
   	    
   	    //Obtengo el número de expediente porque solamente me interesan los intervinientes del expediente con el que estoy trabajando.
   	    String numexp = currentState.getNumexp();

   	    // Obtener el  expediente para ver el interesado principal
   	     IItem expediente = entitiesAPI.getExpedient(numexp);

   	    	String nombre = request.getParameter("nombre");
   	    	
   	    	if(expediente!=null &&
   	    		 expediente.get("IDENTIDADTITULAR")!=null && expediente.get("IDENTIDADTITULAR").equals(nombre)){
   	    		
   	    	
   	 		
   	    		request.setAttribute("esTercero","false");	
   	    		
   	    		//Obtengo el tipo de notificacion 
   	    		request.setAttribute("tipoDireccion",expediente.getString("TIPODIRECCIONINTERESADO"));	
   	    	}
   	    
   	    else{
   	    	IItemCollection participants = entitiesAPI.getParticipants(numexp, "NOMBRE='"+nombre +"'", "NDOC");
   	    	if(participants!=null && participants.iterator().hasNext()){
   	    		IItem participante= (IItem) participants.iterator().next();
   	    	
   	    		request.setAttribute("tipoDireccion",participante.get("TIPO_DIRECCION"));
   	    		if(participante.get("ID_EXT")==null ||participante.get("ID_EXT").equals("0") ){
   	    		request.setAttribute("esTercero","false");	
   	    		}
   	    		else{
   	    			request.setAttribute("esTercero","false");	
   	    			
   	    		}
   	    		
   	    	}
   	    }
   	   
    	return mapping.findForward("updateInfoDataDocument");
   	    
    }
    
   
    
}