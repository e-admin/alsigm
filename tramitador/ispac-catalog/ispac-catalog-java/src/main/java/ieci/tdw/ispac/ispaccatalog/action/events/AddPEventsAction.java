package ieci.tdw.ispac.ispaccatalog.action.events;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.events.DescriptionsPEvents;
import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AddPEventsAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

        int intTpObj = Integer.parseInt(request.getParameter("TpObj"));
        int intIdObj = Integer.parseInt(request.getParameter("IdObj"));

        //Prepara las API's a utilizar
        IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		if (request.getParameter("idRule") != null)
		{
		    catalogAPI.addPRuleEvent(intTpObj, intIdObj,
		            Integer.parseInt(request.getParameter("codEvent")), Integer.parseInt(request.getParameter("idRule")));

		    ActionForward forward=mapping.findForward("showevents");
		    String path=forward.getPath()+"?TpObj="+intTpObj+"&IdObj="+intIdObj;
			return new ActionForward(forward.getName(),path,forward.getRedirect());
		}

	    request.setAttribute("TpObj", String.valueOf(intTpObj));
	    request.setAttribute("IdObj", String.valueOf(intIdObj));

	    CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
	    String ispacbase = (String) servlet.getServletContext().getAttribute("ispacbase");

	    if (request.getParameter("codEvent") != null) {
	        request.setAttribute("codEvent", request.getParameter("codEvent"));
	        IItemCollection rulecol = catalogAPI.getCTRules();
	        List rulelist=CollectionBean.getBeanList(rulecol);

	        request.setAttribute("RulesList", rulelist);

			BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/events/ruleslistformatter.xml"));

			request.setAttribute("RulesListFormatter", formatter);
	        request.setAttribute("application", StaticContext.rewritePage(ispacbase, "common/events/ruleslist.jsp"));
	    }
	    else {
	        List eventlist=DescriptionsPEvents.getDescEventsList(intTpObj, intIdObj);
	        request.setAttribute("EventsList", eventlist);

	        BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/events/eventslistformatter.xml"));

	        request.setAttribute("EventsListFormatter", formatter);
	        request.setAttribute("application", StaticContext.rewritePage(ispacbase, "common/events/eventslist.jsp"));
	    }

        return mapping.findForward("success");
    }

}
