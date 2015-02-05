package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
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

public class SelectDocumentTypeAction extends BaseAction {

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
     
   	    IGenDocAPI gendocAPI = session.getAPI().getGenDocAPI();

		List list = new ArrayList();
		IItemCollection collection = null;
		
		// Obtener los tipos de documentos asociados al trámite
		if (currentState.getTaskCtlId() != 0){
			collection = gendocAPI.getDocTypesFromTaskCTL(currentState.getTaskCtlId());
		}
		else if (currentState.getTaskPcdId() != 0){
			collection = gendocAPI.getDocTypesFromTaskPCD(currentState.getTaskPcdId());
		}
		
		// Procesar para la vista
		if (collection != null) {
			list = CollectionBean.getBeanList(collection);
		}
		
		request.setAttribute("ltDocumentTypes", list);
		
		String action = request.getParameter("action");
		if (StringUtils.isBlank(action)) {
			action = "attachFile.do";
		}
		request.getSession().removeAttribute("docEntidad");
		request.setAttribute("action", action);
		
		return mapping.findForward("success");
	}

}
