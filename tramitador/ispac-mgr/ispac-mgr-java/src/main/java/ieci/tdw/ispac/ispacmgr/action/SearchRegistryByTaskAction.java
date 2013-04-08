package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class SearchRegistryByTaskAction extends SearchRegistryAction {

	
	public ActionForward defaultExecute(ActionMapping mapping, 
			   ActionForm form,
			   HttpServletRequest request, 
			   HttpServletResponse response,
			   SessionAPI session) throws Exception {	
	
		ActionForward forward = super.defaultExecute(mapping, form, request, response, session);
		if (!StringUtils.equals((String)request.getAttribute("REG_FOUND"), "false")){
			ItemBean item = (ItemBean)request.getAttribute("Value");
			item.setProperty("ORIGINO_EXPEDIENTE", "NO");
			item.setProperty("ID_TRAMITE",request.getParameter("taskId"));
		}
		return forward;
	}
	
}
