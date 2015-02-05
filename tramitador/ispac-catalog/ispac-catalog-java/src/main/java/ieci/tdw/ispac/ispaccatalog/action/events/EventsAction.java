package ieci.tdw.ispac.ispaccatalog.action.events;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.BaseDispatchAction;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.events.DescriptionsPEvents;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Gestiona las acciones con los eventos.
 */
public class EventsAction extends BaseDispatchAction {
	
	/** Logger de la clase. */
	private static final Logger logger = Logger.getLogger(EventsAction.class);
	

	/**
	 * Muestra los eventos.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward events(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

        int intTpObj = Integer.parseInt(request.getParameter("TpObj"));
        int intIdObj = Integer.parseInt(request.getParameter("IdObj"));
        int entityId = Integer.parseInt(request.getParameter("entityId"));

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkReadOnlyFunctions(request,
				session.getClientContext(), entityId);

	    request.setAttribute("TpObj", String.valueOf(intTpObj));
	    request.setAttribute("IdObj", String.valueOf(intIdObj));
	    request.setAttribute("entityId", String.valueOf(entityId));

        List eventlist = DescriptionsPEvents.getDescEventsList(intTpObj, 
        		intIdObj);
        request.setAttribute("EventsList", eventlist);

        // Establece el formateador
        setFormatter(request, "EventsListFormatter", 
        		"/formatters/events/eventsformatter.xml");

        return mapping.findForward("events");
    }

	/**
	 * Muestra las reglas. 
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward rules(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

        int intTpObj = Integer.parseInt(request.getParameter("TpObj"));
        int intIdObj = Integer.parseInt(request.getParameter("IdObj"));
        int entityId = Integer.parseInt(request.getParameter("entityId"));

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkReadOnlyFunctions(request,
				session.getClientContext(), entityId);

	    request.setAttribute("TpObj", String.valueOf(intTpObj));
	    request.setAttribute("IdObj", String.valueOf(intIdObj));
	    request.setAttribute("entityId", String.valueOf(entityId));
	    request.setAttribute("codEvent", request.getParameter("codEvent"));
	    
        //Prepara las API's a utilizar
        IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();
		
		// Obtener las reglas que ya han sido seleccionadas
		String query = "WHERE ID_OBJ="+intIdObj+" AND TP_OBJ="+intTpObj+" AND EVENTO="+request.getParameter("codEvent");
        IItemCollection ruleselected = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_P_EVENTOS, query);
        List rulelist1 = CollectionBean.getBeanList(ruleselected);
		
		// Obtener las reglas
        IItemCollection rulecol = catalogAPI.getCTRules();
        List rulelist = CollectionBean.getBeanList(rulecol);
        
        //Borrar los elementos de la lista general que han sido seleccionados.
        List aux = new ArrayList();
        for (Iterator iter = rulelist.iterator(); iter.hasNext();){
        	ItemBean item = (ItemBean)iter.next();
        	for(Iterator sel=rulelist1.iterator(); sel.hasNext();){
        		ItemBean item1 = (ItemBean)sel.next();
        		if(item.getProperty("ID").equals(item1.getProperty("ID_REGLA")))
        			aux.add(item);
        	}        	
        }        
        rulelist.removeAll(aux);
        
        request.setAttribute("RulesList", rulelist);

		setFormatter(request, "RulesListFormatter", 
				"/formatters/events/rulesformatter.xml");

        return mapping.findForward("rules");
    }
	
	/**
	 * Añade las reglas al evento seleccionado.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward addRules(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

        String tpObj = request.getParameter("TpObj");
        String idObj = request.getParameter("IdObj");
        String codEvent = request.getParameter("codEvent");
        
        EntityForm defaultForm = (EntityForm) form;
        String [] ruleIds = defaultForm.getMultibox();
        
        // Añadir las reglas al evento
        addRuleEvents(session, tpObj, idObj, codEvent, ruleIds);
        
		return mapping.findForward("closeIFrame");
    }

	private void addRuleEvents(SessionAPI session, String tpObj, String idObj, 
			String codEvent, String [] ruleIds) throws ISPACException {

        int intTpObj = Integer.parseInt(tpObj);
        int intIdObj = Integer.parseInt(idObj);
        int intCodEvent = Integer.parseInt(codEvent);

		//Prepara las API's a utilizar
		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		if (!ArrayUtils.isEmpty(ruleIds)) {
			for (int i = 0; i < ruleIds.length; i++) {
				try {
					catalogAPI.addPRuleEvent(intTpObj, intIdObj, intCodEvent, 
							Integer.parseInt(ruleIds[i]));
				} catch (Throwable t) {
					logger.warn("Error al añadir regla: " 
							+ "TpObj=" + tpObj
							+ "IdObj=" + idObj
							+ "codEvent=" + codEvent
							+ "ruleId=" + ruleIds[i]);
				}
			}
		}
	}
	
	/**
	 * Elimina el evento seleccionado.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward delEvent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

		int intTpObj = Integer.parseInt(request.getParameter("TpObj"));
        int intIdObj = Integer.parseInt(request.getParameter("IdObj"));
        int entityId = Integer.parseInt(request.getParameter("entityId"));
        int intEvent = Integer.parseInt(request.getParameter("delEvent"));

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkEditFunctions(request, session.getClientContext(),
				entityId);

        //Prepara las API's a utilizar
        IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

	    catalogAPI.delPEvent(intTpObj, intIdObj, intEvent);
	    
	    // Evento a nivel de entidad en el procedimiento
	    if (entityId == ICatalogAPI.ENTITY_P_ENTITY) {
	    	
	    	StringBuffer forward = new StringBuffer()
	    		.append("/entities.do?method=events&regId=")
	    		.append(intIdObj)
	    		.append("&label=");
    	
			String labelentity = request.getParameter("label");
			if (StringUtils.isNotBlank(labelentity)) {
				forward.append(labelentity);
			}
	    	
		    return new ActionForward(forward.toString(), true);
	    }
	    else {
		    return new ActionForward(new StringBuffer()
		    		.append("/showProcedureEntity.do?method=events&entityId=")
		    		.append(entityId)
		    		.append("&regId=")
		    		.append(intIdObj)
		    		.toString(), true);
	    }
	}

	/**
	 * Elimina la regla del evento.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward delEventRule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

		int intTpObj = Integer.parseInt(request.getParameter("TpObj"));
        int intIdObj = Integer.parseInt(request.getParameter("IdObj"));
        int entityId = Integer.parseInt(request.getParameter("entityId"));
        int intEvent = Integer.parseInt(request.getParameter("delEvent"));
	    int rule = Integer.parseInt(request.getParameter("delRule"));
	    int order = Integer.parseInt(request.getParameter("delOrder"));

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkEditFunctions(request, session.getClientContext(),
				entityId);

        //Prepara las API's a utilizar
        IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

	    catalogAPI.delPRuleEvent(intTpObj, intIdObj, intEvent, rule, order);
	    
	    // Evento a nivel de entidad en el procedimiento
	    if (entityId == ICatalogAPI.ENTITY_P_ENTITY) {
	    	
	    	StringBuffer forward = new StringBuffer()
	    		.append("/entities.do?method=events&regId=")
		    	.append(intIdObj)
		    	.append("&label=");
	    	
			String labelentity = request.getParameter("label");
			if (StringUtils.isNotBlank(labelentity)) {
				forward.append(labelentity);
			}
	    	
		    return new ActionForward(forward.toString(), true);
	    }
	    else {
		    return new ActionForward(new StringBuffer()
		    		.append("/showProcedureEntity.do?method=events&entityId=")
		    		.append(entityId)
		    		.append("&regId=")
		    		.append(intIdObj)
		    		.toString(), true);
	    }
	}

	/**
	 * Modifica el orden de una regla dentro de un evento.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward modRuleOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

		int intTpObj = Integer.parseInt(request.getParameter("TpObj"));
		int intIdObj = Integer.parseInt(request.getParameter("IdObj"));
		int entityId = Integer.parseInt(request.getParameter("entityId"));
        int codEvent = Integer.parseInt(request.getParameter("EventCod"));
        int ruleId = Integer.parseInt(request.getParameter("RuleId"));
        int order = Integer.parseInt(request.getParameter("Order"));
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkEditFunctions(request, session.getClientContext(),
				entityId);

        IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		if (request.getParameter("ModOrden").equalsIgnoreCase("INC")) {
		    catalogAPI.incOrderPEvents(
		    		intTpObj, intIdObj, codEvent, ruleId, order);
		}
		else {
		    catalogAPI.decOrderPEvents(
		    		intTpObj, intIdObj, codEvent, ruleId, order);
		}
		
		// Evento a nivel de entidad en el procedimiento
	    if (entityId == ICatalogAPI.ENTITY_P_ENTITY) {
	    	
	    	StringBuffer forward = new StringBuffer()
	    		.append("/entities.do?method=events&regId=")
	    		.append(intIdObj)
	    		.append("&label=");
	
			String labelentity = request.getParameter("label");
			if (StringUtils.isNotBlank(labelentity)) {
				forward.append(labelentity);
			}
	    	
		    return new ActionForward(forward.toString(), true);
	    }
	    else {
		    return new ActionForward(new StringBuffer()
		    		.append("/showProcedureEntity.do?method=events&entityId=")
		    		.append(entityId)
		    		.append("&regId=")
		    		.append(intIdObj)
		    		.toString(), true);
	    }
	}
	
}