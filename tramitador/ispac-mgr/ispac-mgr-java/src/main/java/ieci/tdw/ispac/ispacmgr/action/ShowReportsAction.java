package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.IWorklist;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowReportsAction extends BaseAction {
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
		ClientContext cct = session.getClientContext();
		
		// Estado actual de tramitación
        IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
        IState state = managerAPI.currentState(getStateticket(request));
        
		String tipo=request.getParameter("tipo");
		String idFrm=request.getParameter("idFrm");
		IWorklist managerwl = managerAPI.getWorklistAPI();
		String resp = managerwl.getRespString(state);
		
    	
        IProcedureAPI procedureAPI = session.getAPI().getProcedureAPI();
        IItemCollection itemcol = null;
        
        if(StringUtils.isEmpty(tipo) && StringUtils.isEmpty(idFrm)){
        	 itemcol = procedureAPI.getReports(cct.getStateContext(),resp);
        }
        else if(StringUtils.isNotEmpty(tipo)){
	    	itemcol = procedureAPI.getGlobalReports(resp);
        }
        else if(StringUtils.isNotEmpty(idFrm)){
        	 itemcol = procedureAPI.getSearchReports(Integer.parseInt(idFrm),resp);
        }
		request.setAttribute("ReportList", CollectionBean.getBeanList(itemcol));

		return mapping.findForward("success");
	}
}