package ieci.tdw.ispac.ispaccatalog.action.events;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaccatalog.action.BaseDispatchAction;
import ieci.tdw.ispac.ispaccatalog.bean.PEventBean;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.sign.portafirmas.ProcessSignConnector;
import ieci.tdw.ispac.ispaclib.sign.portafirmas.ProcessSignConnectorFactory;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Gestiona las acciones con los eventos en los circuitos de firma
 */
public class EventsSingCircuitAction extends BaseDispatchAction {

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

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_SIGN_CIRCUITS_READ,
				ISecurityAPI.FUNC_INV_SIGN_CIRCUITS_EDIT });

		ProcessSignConnector processSignConnector = ProcessSignConnectorFactory
		.getInstance().getProcessSignConnector();

		int entityId = Integer.parseInt(request.getParameter("entityId"));
        int intIdObj = Integer.parseInt(request.getParameter("regId"));

        int intTpObj=processSignConnector.getTypeObject();


        // Prepara las API's a utilizar
        IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		// Recoge los eventos relacionados con el objeto de tipo e identificador concretos
		IItemCollection eventcol = catalogAPI.getPEvents(intTpObj, intIdObj);

		// Todas las reglas de SPAC_CT_REGLAS e inicializa un array de Object's
		Map rulesmap=catalogAPI.getCTRules().toMap();
		Object[] beanargs = new Object[]{rulesmap};

		// Obtiene un lista con los eventos procesando el codigo y añadir la clave para el nombre del evento
		List eventlist=CollectionBean.getBeanList(PEventBean.class, eventcol, beanargs);

		/*
		String objdesc = getMessage(request, DescriptionsPEvents.getDescripcionObject(intTpObj));
		if (intTpObj == EventsDefines.EVENT_OBJ_SYSTEM) {
		    objdesc += " - " + getMessage(request, DescriptionsPEvents.getDescripcionObject(intIdObj));
		}
		request.setAttribute("Title", objdesc);
		*/

		request.setAttribute("EventRulesList", eventlist);
		request.setAttribute("TpObj", String.valueOf(intTpObj));
		request.setAttribute("IdObj", String.valueOf(intIdObj));
		request.setAttribute("entityId", String.valueOf(entityId));

		// Formateador para la lista de eventos
		if (FunctionHelper.userHasFunction(request, session.getClientContext(), ISecurityAPI.FUNC_INV_SIGN_CIRCUITS_EDIT)) {
			setFormatter(request, "Formatter", "/formatters/events/objeventssigncircuitformatter.xml");
		} else {
			setFormatter(request, "Formatter", "/formatters/events/objeventssigncircuitreadonlyformatter.xml");
		}

		return mapping.findForward("sucess");
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

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_SIGN_CIRCUITS_EDIT });

		int intTpObj = Integer.parseInt(request.getParameter("TpObj"));
        int intIdObj = Integer.parseInt(request.getParameter("IdObj"));
        int entityId = Integer.parseInt(request.getParameter("entityId"));
        int intEvent = Integer.parseInt(request.getParameter("delEvent"));

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
		    		.append("/eventsSingCircuit.do?method=events")
		    		.append("&entityId=")
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

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_SIGN_CIRCUITS_EDIT });

		int intTpObj = Integer.parseInt(request.getParameter("TpObj"));
        int intIdObj = Integer.parseInt(request.getParameter("IdObj"));
        int entityId = Integer.parseInt(request.getParameter("entityId"));
        int intEvent = Integer.parseInt(request.getParameter("delEvent"));
	    int rule = Integer.parseInt(request.getParameter("delRule"));
	    int order = Integer.parseInt(request.getParameter("delOrder"));

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
    		.append("/eventsSingCircuit.do?method=events")
    		.append("&entityId=")
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

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_SIGN_CIRCUITS_EDIT });

		int intTpObj = Integer.parseInt(request.getParameter("TpObj"));
		int intIdObj = Integer.parseInt(request.getParameter("IdObj"));
		int entityId = Integer.parseInt(request.getParameter("entityId"));
        int codEvent = Integer.parseInt(request.getParameter("EventCod"));
        int ruleId = Integer.parseInt(request.getParameter("RuleId"));
        int order = Integer.parseInt(request.getParameter("Order"));

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
    		.append("/eventsSingCircuit.do?method=events")
    		.append("&entityId=")
    		.append(entityId)
    		.append("&regId=")
    		.append(intIdObj)
    		.toString(), true);
	    }
	}

}