package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.impl.SessionAPI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DistributeAction extends BaseAction {

	public ActionForward executeAction(
	ActionMapping mapping, 
	ActionForm form,
	HttpServletRequest request, 
	HttpServletResponse response,
	SessionAPI session) throws Exception {

//		String register = request.getParameter( "register");
//	  	String uid = request.getParameter("uid");
//
//		IInvesflowAPI invesFlowAPI = session.getAPI();
//	  	IRespManagerAPI responsibleAPI = invesFlowAPI.getRespManagerAPI();
//    	IInboxAPI inbox = invesFlowAPI.getInboxAPI();
//
//	  	Responsible responsible = (Responsible) responsibleAPI.getResp(uid);
//	  	
//        // Formulario asociado a la acción
//        EntityForm defaultForm = (EntityForm) form;
//
//        String archive = defaultForm.getProperty("REGISTER:ARCHIVE");
//        String message = defaultForm.getProperty("REGISTER:MESSAGE");
//        
//	  	inbox.distribute( register, responsible, message, archive.equalsIgnoreCase("on"));
	  	
		return mapping.findForward("success");
	}
}
