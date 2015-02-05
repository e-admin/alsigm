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

public class ModOrderPEventsAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

        IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		if (request.getParameter("ModOrden").equalsIgnoreCase("INC"))
		{
		    catalogAPI.incOrderPEvents(
		        Integer.parseInt(request.getParameter("TpObj")),
		        Integer.parseInt(request.getParameter("IdObj")),
		        Integer.parseInt(request.getParameter("EventCod")),
		        Integer.parseInt(request.getParameter("RuleId")),
		        Integer.parseInt(request.getParameter("Order")));
		}
		else
		{
		    catalogAPI.decOrderPEvents(
		        Integer.parseInt(request.getParameter("TpObj")),
	            Integer.parseInt(request.getParameter("IdObj")),
		        Integer.parseInt(request.getParameter("EventCod")),
	            Integer.parseInt(request.getParameter("RuleId")),
	            Integer.parseInt(request.getParameter("Order")));
		}
        return mapping.findForward("success");
    }

}
