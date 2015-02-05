package ieci.tdw.ispac.ispaccatalog.action.events;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.bean.PEventBean;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.events.DescriptionsPEvents;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowPEventsAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_READ,
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

        //Se recoje el tipo de objeto y su identificador concreto
        int intTpObj = Integer.parseInt(request.getParameter("TpObj"));
        int intIdObj = Integer.parseInt(request.getParameter("IdObj"));

        //Prepara las API's a utilizar
        IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		//Recoje los eventos relacionados a el objeto de tipo e identificador concretos
		IItemCollection eventcol = catalogAPI.getPEvents(intTpObj, intIdObj);

		//Todas las reglas de SPAC_CT_REGLAS e inicializa un array de Object's
		Map rulesmap=catalogAPI.getCTRules().toMap();
		Object[] beanargs = new Object[]{rulesmap};
		//Obtiene un lista con los eventos procesando el codigo y añadir el nombre del evento
		List eventlist=CollectionBean.getBeanList(PEventBean.class, eventcol, beanargs);

		String objdesc=DescriptionsPEvents.getDescripcionObject(intTpObj);
		if (intTpObj == EventsDefines.EVENT_OBJ_SYSTEM)
		{
		    objdesc+=" - " + DescriptionsPEvents.getDescripcionObject(intIdObj);
		}

		request.setAttribute("Title", objdesc);
		request.setAttribute("EventRulesList", eventlist);
		request.setAttribute("TpObj", String.valueOf(intTpObj));
		request.setAttribute("IdObj", String.valueOf(intIdObj));

		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/events/showpeventsformatter.xml"));
		request.setAttribute("Formatter", formatter);

		return mapping.findForward("success");
    }

}
