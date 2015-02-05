package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InitNotificacionAction extends BaseAction {

	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
		String method = request.getParameter("method");
		if (StringUtils.equals(method, "inf")){
			
			//mostramos pantalla de confirmacion
			return mapping.findForward("inf");
		}

		IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(session.getClientContext());
    	IState state = managerAPI.currentState(getStateticket(request));

		// Iniciar la notificación del documento
		session.getAPI().getGenDocAPI().initNotification(
				state.getProcessId(), state.getStagePcdId(), 
				state.getTaskPcdId(), state.getEntityRegId());

		return mapping.findForward("success");
	}

}
