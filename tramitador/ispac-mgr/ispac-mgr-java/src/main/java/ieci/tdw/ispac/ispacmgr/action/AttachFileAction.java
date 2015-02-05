package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AttachFileAction extends BaseAction {

	
	public ActionForward executeAction(ActionMapping mapping,
									   ActionForm form,
									   HttpServletRequest request,
									   HttpServletResponse response,
									   SessionAPI session) throws Exception {

	    ClientContext cct = session.getClientContext();

		///////////////////////////////////////////////
		// Se obtiene el estado de tramitación
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
		managerAPI.currentState(getStateticket(request));
		
		
		//Si recibo el id del trámite quiere decir que se esta asociando un documento a un reg de una entidad desde una fase
		//por lo que hay que crear el tramite al que pertenece el tipo de documento
		String idTask=request.getParameter("idTaskPcd");

		//Establecer el tipo de documento si previamente se ha seleccionado
		String documentTypeId = request.getParameter("documentTypeId");
		if (documentTypeId != null) {
			request.setAttribute("documentTypeId", documentTypeId);
		}
		
		if(StringUtils.isNotEmpty(idTask)){
			request.setAttribute("idTaskPcd", idTask);
			
		}

		return mapping.findForward("success");
	}
	
}
