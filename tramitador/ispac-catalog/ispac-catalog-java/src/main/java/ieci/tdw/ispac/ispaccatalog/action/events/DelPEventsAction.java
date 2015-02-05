package ieci.tdw.ispac.ispaccatalog.action.events;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DelPEventsAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

        int intTpObj = Integer.parseInt(request.getParameter("TpObj"));
        int intIdObj = Integer.parseInt(request.getParameter("IdObj"));
        int intEvent = Integer.parseInt(request.getParameter("delEvent"));

        //Prepara las API's a utilizar
        IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		if (request.getParameter("delRule") != null)
		{
		    int rule=Integer.parseInt(request.getParameter("delRule"));
		    int order=Integer.parseInt(request.getParameter("delOrder"));
		    catalogAPI.delPRuleEvent(intTpObj, intIdObj, intEvent,rule,order );
		}
		else
		    catalogAPI.delPEvent(intTpObj, intIdObj, intEvent);

        return mapping.findForward("success");
    }

}
