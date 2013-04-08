package ieci.tdw.ispac.ispaccatalog.action.procedure;
 
import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.IRespManagerAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.ITemplateAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaccatalog.action.BaseDispatchAction;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaccatalog.action.procedure.tree.CuadroEntidadTreeView;
import ieci.tdw.ispac.ispaccatalog.action.procedure.tree.ElementoCuadro;
import ieci.tdw.ispac.ispaccatalog.bean.PEventBean;
import ieci.tdw.ispac.ispaccatalog.bean.PcdVersionBean;
import ieci.tdw.ispac.ispaccatalog.bean.permissions.TypePermissionsBean;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.bean.TreeNode;
import ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PNodoDAO;
import ieci.tdw.ispac.ispaclib.producers.IProducersConnector;
import ieci.tdw.ispac.ispaclib.producers.ProducersConnectorFactory;
import ieci.tdw.ispac.ispaclib.producers.vo.Producer;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Muestra la información de un procedimiento.
 *
 */
public class ShowProcedureEntityAction extends BaseDispatchAction {

	/** Logger de la clase. */
	protected static final Logger logger = 
		Logger.getLogger(ShowProcedureEntityAction.class);
	
	
	/**
	 * Muestra la ficha catalográfica de la entidad.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward card(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_READ,
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		// Identificador de la entidad
		int entityId = Integer.parseInt(request.getParameter("entityId"));
		
		// Indentificador del registro
 		int keyId = Integer.parseInt(request.getParameter("regId"));

        EntityApp entityApp;
        int ctEntityId;
        int ctRegId;
        switch (entityId) {

	        case ICatalogAPI.ENTITY_P_PROCEDURE:
	        	ctEntityId = ICatalogAPI.ENTITY_CT_PROCEDURE;
	        	ctRegId = keyId;
	        	setProcedureInfo(session, request, keyId);
	        	
	        	// Obtener la lista de productores
	        	request.setAttribute("PRODUCER_LIST", 
	        			getProducerList(session, keyId));
	        	break;

	        case ICatalogAPI.ENTITY_P_SUBPROCEDURE:
	        	ctEntityId = entityId;
	        	ctRegId = keyId;
	        	setProcedureInfo(session, request, keyId);
	        	break;
	        	
        	case ICatalogAPI.ENTITY_P_STAGE:
	        	ctEntityId = ICatalogAPI.ENTITY_CT_STAGE;
	        	entityApp = getEntityApp(session, entityId, keyId);
	        	ctRegId = entityApp.getItem().getInt("SPAC_P_FASES:ID_CTFASE");
	        	setStageInfo(session, request, keyId);
	        	break;

        	case ICatalogAPI.ENTITY_P_ACTIVITIES:
	        	ctEntityId = entityId;
	        	entityApp = getEntityApp(session, entityId, keyId);
	        	ctRegId = keyId;
	        	setStageInfo(session, request, keyId);
	        	break;

        	case ICatalogAPI.ENTITY_P_SINCNODE:
	        	ctEntityId = entityId;
	        	entityApp = getEntityApp(session, entityId, keyId);
	        	ctRegId = keyId;
	        	setSincNodeInfo(session, request, keyId);
	        	break;
	
	        case ICatalogAPI.ENTITY_P_TASK:
	        	ctEntityId = ICatalogAPI.ENTITY_CT_TASK;
	        	entityApp = getEntityApp(session, entityId, keyId);
	        	ctRegId = entityApp.getItem().getInt("SPAC_P_TRAMITES:ID_CTTRAMITE");
	        	setTaskInfo(session, request, keyId);
	        	break;

	        case ICatalogAPI.ENTITY_P_FSTD:
	        	ctEntityId = ICatalogAPI.ENTITY_CT_TYPEDOC;
	        	ctRegId = keyId;
	        	CuadroEntidadTreeView tree = (CuadroEntidadTreeView)request.getSession().getAttribute(
						ManageVistaCuadroProcedimientoAction.CUADRO_PROCEDIMIENTO);
				if (tree != null) {
		        	ElementoCuadro stage = (ElementoCuadro) tree.getSelectedNode()
					.getParent().getModelItem();
		        	setStageDocTypeInfo(session, request, keyId, stage);
				}
	        	break;
	        case ICatalogAPI.ENTITY_CT_TYPEDOC:
	        	ctEntityId = entityId; 
	        	ctRegId = keyId;
	        	CuadroEntidadTreeView tree2 = (CuadroEntidadTreeView)request.getSession().getAttribute(
						ManageVistaCuadroProcedimientoAction.CUADRO_PROCEDIMIENTO);
				if (tree2 != null) {
		        	ElementoCuadro task = (ElementoCuadro) tree2.getSelectedNode()
					.getParent().getModelItem();
		        	setDocTypeInfo(session, request, keyId, task);
				}
	        	break;
	        case ICatalogAPI.ENTITY_CT_TEMPLATE:
	        	ctEntityId = entityId;
	        	ctRegId = keyId;
			   	setTemplateInfo(session, request, keyId);
	        	break;
	        default:
	        	logger.error("En entityId no es válido: " + entityId);
	        	return mapping.findForward("empty");
	    }

 		// Establecer la información del registro
 		setEntityAppInForm(request, session, (EntityForm) form, ctEntityId, 
 				ctRegId);
 		
 		request.setAttribute("RET_ENTITY_ID", String.valueOf(entityId));
 		request.setAttribute("RET_KEY_ID", String.valueOf(keyId));
 		
		return mapping.findForward("card" + entityId);
	}

	/**
	 * Muestra las propiedades de la entidad.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward properties(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_READ,
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		// Identificador de la entidad
		//int entityId = ICatalogAPI.ENTITY_P_PROCEDURE;
		int entityId = Integer.parseInt(request.getParameter("entityId"));
		
		// Identificador del registro
 		int keyId = Integer.parseInt(request.getParameter("regId"));

 		// Establecer la información del registro
 		setEntityAppInForm(request, session, (EntityForm) form, entityId, keyId);

 		// Establecer la lista de versiones si es un procedimiento o subproceso
 		if ((entityId == ICatalogAPI.ENTITY_P_PROCEDURE)
 				|| (entityId == ICatalogAPI.ENTITY_P_SUBPROCEDURE)){
 			int groupId = Integer.parseInt(
 					((EntityForm)form).getProperty("SPAC_P_PROCEDIMIENTOS:ID_GROUP"));
 			request.setAttribute("versionsList", 
 					getVersionList(session, groupId));
 		}

		return mapping.findForward("properties" + entityId);
	}
	
	protected int getTipoObj(int entityId)
	{
		int intTpObj;
		 switch (entityId) {
	    	case ICatalogAPI.ENTITY_P_PROCEDURE:
	    		intTpObj = EventsDefines.EVENT_OBJ_PROCEDURE;
	    		break;

	    	case ICatalogAPI.ENTITY_P_SUBPROCEDURE:
	    		intTpObj = EventsDefines.EVENT_OBJ_SUBPROCEDURE;
	    		break;

	    	case ICatalogAPI.ENTITY_P_STAGE:
	    		intTpObj = EventsDefines.EVENT_OBJ_STAGE;
	    		break;

	    	case ICatalogAPI.ENTITY_P_ACTIVITIES:
	    		intTpObj = EventsDefines.EVENT_OBJ_ACTIVITY;
	    		break;

	    	case ICatalogAPI.ENTITY_P_TASK:
	    		intTpObj = EventsDefines.EVENT_OBJ_TASK;
	    		break;
	    		
	    	case ICatalogAPI.ENTITY_P_FLUJOS:
	    		intTpObj = EventsDefines.EVENT_OBJ_FLOW;
	    		break;
	    		
	    	default:
	    		intTpObj = EventsDefines.EVENT_OBJ_ENTITY;
	    		break;
     }
		 return intTpObj;
	}

	/**
	 * Muestra los eventos de la entidad.
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
				ISecurityAPI.FUNC_INV_PROCEDURES_READ,
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

        // Se recoge el tipo de objeto y su identificador concreto
        //int intTpObj = EventsDefines.EVENT_OBJ_PROCEDURE;
		int entityId = Integer.parseInt(request.getParameter("entityId"));
        int intIdObj = Integer.parseInt(request.getParameter("regId"));

        int intTpObj=getTipoObj(entityId);
       

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
		if (FunctionHelper.userHasFunction(request, session.getClientContext(), ISecurityAPI.FUNC_INV_PROCEDURES_EDIT)) {
			
			// Formateador de edición
			setFormatter(request, "Formatter", "/formatters/events/objeventsformatter.xml");
			
		} else {
			
			// Formaterador de solo lectura
			setFormatter(request, "Formatter", "/formatters/events/objeventsreadonlyformatter.xml");
		}

		return mapping.findForward("events" + entityId);
	}
	
	
	public ActionForward reports (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_READ,
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		int entityId = Integer.parseInt(request.getParameter("entityId"));
		int id = Integer.parseInt(request.getParameter("regId"));
		
		ClientContext cct = session.getClientContext();
		
	    IInvesflowAPI invesFlowAPI = cct.getAPI();
		IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();
		
		// Recoje los informes asociados al procedimiento
		IItemCollection collection = procedureAPI.getPReports(id, getTipoObj(entityId));
        List reportList = CollectionBean.getBeanList(collection);
        
		request.setAttribute("reportList", reportList);
		request.setAttribute("TpObj", String.valueOf(getTipoObj(entityId)));
		request.setAttribute("pcdId", String.valueOf(id));
		request.setAttribute("entityId", String.valueOf(entityId));

		if (FunctionHelper.userHasFunction(request, session.getClientContext(), ISecurityAPI.FUNC_INV_PROCEDURES_EDIT)) {
			setFormatter(request, "Formatter", "/formatters/reports/reportsformatter.xml");
		} else {
			setFormatter(request, "Formatter", "/formatters/reports/reportsreadonlyformatter.xml");
		}

        return mapping.findForward("reports" + entityId);
	}
	
	/**
	 * Muestra los circuitos de firma asoaciados al procedimiento
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws ISPACException
	 */
	public ActionForward ctosfirma(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_READ,
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		int entityId = Integer.parseInt(request.getParameter("entityId"));
		int pcdId = Integer.parseInt(request.getParameter("regId"));

		IClientContext cct = session.getClientContext();
	    IInvesflowAPI invesFlowAPI = cct.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();
		
		// Recoje los circuitos de firmas asociados al procedimiento
		IItemCollection collection = catalogAPI.getCtosFirmasProcedure(pcdId);
        List ctosfirmaList = CollectionBean.getBeanList(collection);
        
//        String query = "WHERE TIPO = 2 AND ID_CIRCUITO NOT IN ( SELECT ID_CIRCUITO FROM SPAC_P_CTOSFIRMA AS PCF " +
//		"WHERE PCF.ID_CIRCUITO = SPAC_CTOS_FIRMA_CABECERA.ID_CIRCUITO AND PCF.ID_PCD =" +pcdId+")";
        String query = "WHERE TIPO = 2 AND ID_CIRCUITO NOT IN ( SELECT ID_CIRCUITO FROM SPAC_P_CTOSFIRMA WHERE ID_PCD =" +pcdId+")";
        
		request.setAttribute("CtosFirmaList", ctosfirmaList);
		request.setAttribute("pcdId", String.valueOf(pcdId));
		request.setAttribute("entityId", String.valueOf(entityId));
		request.setAttribute("sqlquery", query);

		// Indicar el formateador de los resultados
		if (FunctionHelper.userHasFunction(request, session.getClientContext(), ISecurityAPI.FUNC_INV_PROCEDURES_EDIT)) {
			setFormatter(request, "Formatter",
					"/formatters/ctosfirma/ctosfirmaformatter.xml");
		} else {
			setFormatter(request, "Formatter",
					"/formatters/ctosfirma/ctosfirmareadonlyformatter.xml");
		}

        return mapping.findForward("ctosfirma" + entityId);
	}

	/**
	 * Muestra las entidades del procedimiento.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward entities(ActionMapping mapping, 
								  ActionForm form,
								  HttpServletRequest request, 
								  HttpServletResponse response,
								  SessionAPI session) throws ISPACException {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_READ,
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

        IClientContext cct = session.getClientContext();
        IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		
		// Identificador de la entidad
		int entityId = Integer.parseInt(request.getParameter("entityId"));
		
		// Identificador del procedimiento
		int pcdId = -1;
		if ((entityId==ICatalogAPI.ENTITY_P_PROCEDURE) ||
			(entityId==ICatalogAPI.ENTITY_P_SUBPROCEDURE)) {
			
			pcdId = Integer.parseInt(request.getParameter("regId"));
			
			// Operaciones para las entidades en el procedimiento
			
			// Eliminar entidad
			String delId = request.getParameter("delId");
			if (delId != null) {
				
			    IItem item = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_ENTITY);
			    item.setKey(Integer.parseInt(delId));
			    item.delete(cct);
			}

			// Ordenar entidades
			String modOrder = request.getParameter("modOrder");
			if (modOrder != null) {
				
				int entity = Integer.parseInt(request.getParameter("pentId"));
			    if (modOrder.equalsIgnoreCase("INC")) {
			        
			    	catalogAPI.incOrderPEntity(pcdId, entity);
			    }
			    else {
			        catalogAPI.decOrderPEntity(pcdId, entity);
			    }
			}
		}
		else{
			// Obtener el identificador del procedimiento a partir del nodo raíz del árbol
			CuadroEntidadTreeView tree = (CuadroEntidadTreeView)request.getSession().getAttribute(ManageVistaCuadroProcedimientoAction.CUADRO_PROCEDIMIENTO);
			if (tree != null) {
				
				ElementoCuadro proc = (ElementoCuadro)((TreeNode)tree.getRootNodes().iterator().next()).getModelItem();
				pcdId = Integer.valueOf(proc.getRegId()).intValue();
			}
		}
		
		// Identificador del registro para el que se visualizan las entidades (procedimiento, fase, o trámite)
		int regId = Integer.parseInt(request.getParameter("regId"));
		
		List itemBeanList = null;
		
		// Obtener las credenciales del usuario conectado
		boolean readonly = !FunctionHelper.userHasFunction(request,
				session.getClientContext(),
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT);

		if (entityId == ICatalogAPI.ENTITY_P_PROCEDURE) {

			// Entidades con los formularios asociados a nivel de procedimiento
			itemBeanList = catalogAPI.getProcedureEntitiesForm(pcdId);
			
			if (readonly) {
				setFormatter(request, "ItemsListFormatter",	"/formatters/entities/pcdentitiesreadonlyformatter.xml");
			} else {
				setFormatter(request, "ItemsListFormatter",	"/formatters/entities/pcdentitiesformatter.xml");
			}
		}
		else if (entityId == ICatalogAPI.ENTITY_P_SUBPROCEDURE) {
			
			// Entidades con los formularios asociados a nivel de subproceso
			itemBeanList = catalogAPI.getSubprocedureEntitiesForm(pcdId);

			if (readonly) {
				setFormatter(request, "ItemsListFormatter",	"/formatters/entities/subpcdentitiesreadonlyformatter.xml");
			} else {
				setFormatter(request, "ItemsListFormatter", "/formatters/entities/subpcdentitiesformatter.xml");
			}
		}
		else if ((entityId == ICatalogAPI.ENTITY_P_STAGE) || 
				 (entityId == ICatalogAPI.ENTITY_P_ACTIVITIES)) {
			
			if (entityId == ICatalogAPI.ENTITY_P_STAGE) {
				// Entidades con los formularios asociados a nivel de fase en el procedimiento
		   		itemBeanList = catalogAPI.getStageEntities(pcdId, regId, "procedure.entity.form.procedure");
			}
			else {
				// Entidades con los formularios asociados a nivel de actividad en el subproceso
		   		itemBeanList = catalogAPI.getStageEntities(pcdId, regId, "procedure.entity.form.subprocedure");
		   		request.setAttribute("isActivity", Boolean.TRUE);
			}
	   		
			if (readonly) {
				setFormatter(request, "ItemsListFormatter",	"/formatters/entities/stagesentitiesreadonlyformatter.xml");
			} else {
				setFormatter(request, "ItemsListFormatter", "/formatters/entities/stagesentitiesformatter.xml");
			}
	   	}
		else {
			
			// Entidades con los formularios asociados a nivel de trámite en el procedimiento
	   		itemBeanList = catalogAPI.getTaskEntities(pcdId, regId);
	   		
			if (readonly) {
				setFormatter(request, "ItemsListFormatter",	"/formatters/entities/tasksentitiesreadonlyformatter.xml");
			} else {
				setFormatter(request, "ItemsListFormatter", "/formatters/entities/tasksentitiesformatter.xml");
			}
	   	}
		
		if ((itemBeanList != null) &&
			(!itemBeanList.isEmpty())) {
			
			// Obtener los recursos para los nombres de las entidades
			String[] entitiesNames = new String[itemBeanList.size()];
			int i=0;
			for (Iterator iter = itemBeanList.iterator(); iter.hasNext();) {
				
				ItemBean itemBean = (ItemBean) iter.next();
				entitiesNames[i] = itemBean.getString("CTENTITY:NOMBRE");
				i++;
			}
		
			Map resourcesEntities = entitiesAPI.getEntitiesResourcesMap(entitiesNames);
			for (Iterator iter = itemBeanList.iterator(); iter.hasNext();) {
				
				ItemBean itemBean = (ItemBean) iter.next();
				EntityDAO entityResource = ( (EntityDAO) resourcesEntities.get(itemBean.getString("CTENTITY:NOMBRE")));
				if (entityResource != null) {
					
					itemBean.setProperty("ETIQUETA", entityResource.get("VALOR"));
				}
				
				// Entidades no eliminables a nivel de procedimiento
				if (entityId == ICatalogAPI.ENTITY_P_PROCEDURE) {
					
					int ctEntityId = itemBean.getItem().getInt("CTENTITY:ID");
					if ((ctEntityId == SpacEntities.SPAC_EXPEDIENTES) ||
						(ctEntityId == SpacEntities.SPAC_DT_INTERVINIENTES) ||
						(ctEntityId == SpacEntities.SPAC_DT_DOCUMENTOS) ||
						(ctEntityId == SpacEntities.SPAC_DT_TRAMITES)) {
						
						itemBean.setProperty("NO_DELETE", Boolean.TRUE);
					}
				}
			}
		}

   	 	request.setAttribute("ItemsList", itemBeanList);
   	 	request.setAttribute("entityId", String.valueOf(entityId));
   	 	request.setAttribute("pcdId", String.valueOf(pcdId));
   	 	request.setAttribute("regId", String.valueOf(regId));
   	 
		return mapping.findForward("entities" + entityId);
	}

	/**
	 * Muestra los permisos del procedimiento.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward rights(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_READ,
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		// Identificador de la entidad
		int entityId = Integer.parseInt(request.getParameter("entityId"));

		// Identificador del registro
        int pcdId = Integer.parseInt(request.getParameter("regId"));

        // Permisos del procedimiento
        request.setAttribute("ProceduresList", getRights(session, pcdId));

		// Indicar el formateador de los resultados
		if (FunctionHelper.userHasFunction(request, session.getClientContext(), ISecurityAPI.FUNC_INV_PROCEDURES_EDIT)) {
			setFormatter(request, "ProceduresListFormatter", 
					"/formatters/permissions/permissionsformatter.xml");
		} else {
			setFormatter(request, "ProceduresListFormatter", 
					"/formatters/permissions/permissionsreadonlyformatter.xml");
		}

		return mapping.findForward("rights" + entityId);
	}

	/**
	 * Muestra los plazos de la entidad.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward deadlines(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_READ,
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		//int entityId = Integer.parseInt(request.getParameter("entityId"));
		
			// Identificador de la entidad
			//int entityId = ICatalogAPI.ENTITY_P_PROCEDURE;
			//int entityId = Integer.parseInt(request.getParameter("entityId"));
	
 		// Identificador del registro
		int  regId = Integer.parseInt(request.getParameter("regId"));
		int entityId = Integer.parseInt(request.getParameter("entityId"));
		int pcdId=-1;
		if ((entityId==ICatalogAPI.ENTITY_P_PROCEDURE)
				|| (entityId==ICatalogAPI.ENTITY_P_SUBPROCEDURE)){
			pcdId = regId;
		}else{
			IItemCollection entidad = session.getAPI().getCatalogAPI().queryCTEntities(entityId, "WHERE ID="+regId);
			if (entidad.next()){
				IItem aItem = (IItem)entidad.iterator().next();
				pcdId = Integer.parseInt(aItem.getString("ID_PCD"));
			}
		}

 		// Establecer la información del registro
 		setEntityAppInForm(request, session, (EntityForm) form, entityId, regId);
   	 	request.setAttribute("entityId", String.valueOf(entityId));
   	 	request.setAttribute("pcdId", String.valueOf(pcdId));
		
		return mapping.findForward("deadlines" + entityId);
	}
	
	/**
	 * Muestra el grafo de la entidad.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward graph(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_READ,
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		// Identificador de la entidad
		int entityId = Integer.parseInt(request.getParameter("entityId"));

		// Identificador del registro
        int regId = Integer.parseInt(request.getParameter("regId"));

   	 	request.setAttribute("entityId", String.valueOf(entityId));
   	 	request.setAttribute("regId", String.valueOf(regId));

        return mapping.findForward("graph" + entityId);
	}

	/**
	 * Muestra los flujos relacionados con la fase.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward flows(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_READ,
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		// Identificador de la fase
		int nodeId = Integer.parseInt(request.getParameter("nodeId"));
		
		// Tipo de flujo: IN/OUT
		String flowType = request.getParameter("flowType");
		
        // Obtener la información del nodo
        IInvesflowAPI invesFlowAPI = session.getAPI();
		IItem node = invesFlowAPI.getProcedureNode(nodeId);
		int nodeType = node.getInt("TIPO");
		
		if (nodeType == PNodoDAO.NODE_OBJ_STAGE) {
		
			// Establecer la información de la fase
			setStageInfo(session, request, nodeId);
			
		} else if (nodeType == PNodoDAO.NODE_OBJ_SYNCNODE) {

			// Establecer la información del nodo de sincronización
			setSincNodeInfo(session, request, nodeId);

		}
		
		request.setAttribute("FLOW_ITEM", flowType);
		
		return mapping.findForward("flows");
	}
	
	/**
	 * Muestra las dependencias de los trámites.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws ISPACException
	 */
	public ActionForward dependencies(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_READ,
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		int entityId = Integer.parseInt(request.getParameter("entityId"));
		int regId = Integer.parseInt(request.getParameter("regId"));
		
		ClientContext cct = session.getClientContext();
		
	    IInvesflowAPI invesFlowAPI = cct.getAPI();
		IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();
		
		// Recoje la lista de trámites de los que depende el trámite actual
		IItemCollection collection = procedureAPI.getTaskDependencies(regId);
        List dependencyList = CollectionBean.getBeanList(collection);
        
		request.setAttribute("entityId", String.valueOf(entityId));
		request.setAttribute("regId", String.valueOf(regId));
		request.setAttribute("dependencyList", dependencyList);
		
		try {
	        // Construir la SQL para la lista de trámites que se pueden agregar
	        IItem ptask = invesFlowAPI.getProcedureTaskPCD(regId);
			String query = new StringBuffer("WHERE ID_FASE=")
					.append(ptask.getInt("ID_FASE"))
					.append(" AND ID!=")
					.append(regId)
					.append(" AND ID NOT IN (SELECT ID_TRAMITE_PADRE FROM SPAC_P_DEP_TRAMITES WHERE ID_TRAMITE_HIJO=")
					.append(regId).append(")").toString();

			request.setAttribute("sqlquery", URLEncoder.encode(query, "ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			logger.error("Error al construir la SQL de consulta de trámites", e);
			throw new ISPACInfo(e.getMessage());
		}

		// Indicar el formateador de los resultados
		if (FunctionHelper.userHasFunction(request, session.getClientContext(), ISecurityAPI.FUNC_INV_PROCEDURES_EDIT)) {
			setFormatter(request, "Formatter", "/formatters/dependencies/dependenciesformatter.xml");
		} else {
			setFormatter(request, "Formatter", "/formatters/dependencies/dependenciesreadonlyformatter.xml");
		}
		
        return mapping.findForward("dependencies" + entityId);
	}

	protected void setEntityAppInForm(HttpServletRequest request, 
				SessionAPI session, EntityForm form, int entityId, int keyId) 
			throws ISPACInfo {
	    
		try {
	    	EntityApp entityapp = getEntityApp(session, entityId, keyId);
	
			if (((form.getActions() == null) ||
			     (form.getActions().equals("success"))) &&
			     (request.getAttribute(Globals.ERROR_KEY) == null)) {
				
				form.setEntity(Integer.toString(entityId));
				form.setKey(Integer.toString(keyId));
				form.setReadonly("false");
				
				form.setEntityApp(entityapp);
			} else {
				form.setValuesExtra(entityapp);
			}
	    } catch (ISPACException eie){
	    	throw new ISPACInfo(eie.getMessage());
	    }
	}
	
	private EntityApp getEntityApp(SessionAPI session, int entityId, int keyId) 
			throws ISPACException {

		IInvesflowAPI invesFlowAPI = session.getAPI();
        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
		
        EntityApp entityApp = catalogAPI.getCTDefaultEntityApp(entityId, keyId, 
        		getRealPath(""));

        //Si se debe crear una entrada cuando se guarden los datos del registro,
		//se marca debidamente el campo clave del registro como nulo.
		if (keyId==ISPACEntities.ENTITY_NULLREGKEYID) {
		    entityApp.getItem().setKey(ISPACEntities.ENTITY_NULLREGKEYID);
		}
        
        return entityApp;
	}
	
	private List getVersionList(SessionAPI session, int groupId) 
			throws ISPACException {

		String sql = new StringBuffer()
			.append(" WHERE ID_GROUP=").append(groupId)
			.toString();
		
		IItemCollection itemcol = session.getAPI().getProcedures(sql);

	    return CollectionBean.getBeanList(PcdVersionBean.class, itemcol);
	}
	
	private List getRights(SessionAPI session, int pcdId) 
			throws ISPACException {
		
        IInvesflowAPI invesflowapi = session.getAPI();
        IRespManagerAPI respAPI = invesflowapi.getRespManagerAPI();
       
       // IItemCollection resps = respAPI.getAllRespPermissions(pcdId);
        //Map respsMap = CollectionBean.getBeanMap(ItemBean.class, resps);

        //IItemCollection rights = respAPI.getPermissions(pcdId);
        //Permisos solo a nivel de procedimiento Iniciar y Eliminar expediente
       
        /*IItemCollection rights  = respAPI.getPermissions( pcdId);
       List rightsList = CollectionBean.getBeanList(TypePermissionsBean.class, 
       		rights);

       //Permisos en función del tipo de objeto Consultar y Editar
       rights  = respAPI.getPermissions(ISecurityAPI.PERMISSION_TPOBJ_PROCEDURE, pcdId);
      
       rightsList.addAll(CollectionBean.getBeanList(TypePermissionsBean.class, 
            		rights));
       
        */
        
        IItemCollection rights = respAPI.getAllPermissionsByPcd(pcdId,null);
        List rightsList = CollectionBean.getBeanList(TypePermissionsBean.class, 
           		rights);
        Map respsMap= respAPI.getResp(rights.toList(), "UID_USR");
        TypePermissionsBean tpbean = null;
   
        for (int i = 0; i < rightsList.size(); i++) {
        	
            tpbean = (TypePermissionsBean)rightsList.get(i);
            //tpbean.uidName(respsMap);
            tpbean.setUidName(respsMap);
        }
        
        return rightsList;
	}
	
	private void setStageDocTypeInfo(SessionAPI session, HttpServletRequest request, 
			int reg, ElementoCuadro stage) {

		try {
			IItem item = getEntity(session, ICatalogAPI.ENTITY_CT_TYPEDOC, reg);
			request.setAttribute("DOCTYPE_ITEM", new ItemBean(item));

				if (stage != null) {
					String stageId = stage.getRegId();
					if (StringUtils.isNotBlank(stageId)) {
						setStageInfo(session, request, Integer.parseInt(stageId));
					}
				}
			
		} catch (ISPACException e) {
			logger.info("Error al obtener la información del tipo de documento #" + reg, e);
		}
	}
	private void setDocTypeInfo(SessionAPI session, HttpServletRequest request, 
			int reg, ElementoCuadro parent) {

		try {
			IItem item = getEntity(session, ICatalogAPI.ENTITY_CT_TYPEDOC, reg);
			request.setAttribute("DOCTYPE_ITEM", new ItemBean(item));
			
				if (parent != null) {
					String parentId = parent.getRegId();
					if (StringUtils.isNotBlank(parentId)) {
						//if (parent.isEntityPlantillaStageTipoDoc()){
						if (parent.isEntityTramiteFase()){
							setTaskInfo(session, request, Integer.parseInt(parentId));
						}else{
							setStageInfo(session, request, Integer.parseInt(parentId));
						}					
					}
				}
			
		} catch (ISPACException e) {
			logger.info("Error al obtener la información del tipo de documento #" + reg, e);
		}
	}
	private void setTemplateInfo(SessionAPI session, HttpServletRequest request, 
			int reg) {

		try {
			IItem item = getEntity(session, ICatalogAPI.ENTITY_CT_TEMPLATE, reg);
			ItemBean itemBean = new ItemBean(item);
			
			// Para sacar si la plantilla es especifica o generica en la vista del formulario.			
        	ITemplateAPI templateAPI = session.getAPI().getTemplateAPI();
        	int id = item.getInt("ID");
        	if(templateAPI.isProcedureTemplate(id))
        		itemBean.setProperty("PROCEDURE_TEMPLATE", getResources(request).getMessage("template.especifica"));
 	        else 
 	        	itemBean.setProperty("PROCEDURE_TEMPLATE", getResources(request).getMessage("template.generica"));	
			
			request.setAttribute("TEMPLATE_ITEM", itemBean);

			// Establecer la información del trámite
			CuadroEntidadTreeView tree = (CuadroEntidadTreeView)request.getSession().getAttribute(
						ManageVistaCuadroProcedimientoAction.CUADRO_PROCEDIMIENTO);
			if (tree != null) {
				ElementoCuadro typeDoc = (ElementoCuadro) tree.getSelectedNode().getModelItem();
				
				if (typeDoc != null) {
					String typeDocId = typeDoc.getProperty("ID_TPDOC");
					if (StringUtils.isNotBlank(typeDocId)) {
						if (tree != null) {
							//la plantilla puede tener como padre una fase o un tramite
							ElementoCuadro parent = (ElementoCuadro) tree.getSelectedNode()
								.getParent().getParent().getModelItem();
							setDocTypeInfo(session, request, Integer.parseInt(typeDocId), parent);
						}
					}
				}
			}
		} catch (ISPACException e) {
			logger.info("Error al obtener la información del tipo de documento #" + reg, e);
		}
	}

	private void setTaskInfo(SessionAPI session, HttpServletRequest request, 
			int reg) {

		try {
			IItem item = getEntity(session, ICatalogAPI.ENTITY_P_TASK, reg);
			request.setAttribute("TASK_ITEM", new ItemBean(item));

			// Establecer la información de la fase del trámite
			if (item != null) {
				int stageId = item.getInt("ID_FASE");
				setStageInfo(session, request, stageId);
			}
		} catch (ISPACException e) {
			logger.info("Error al obtener la información del trámite #" + reg, e);
		}
	}

	private void setStageInfo(SessionAPI session, HttpServletRequest request, 
			int reg) {

		try {
			
			IItem stage = getEntity(session, ICatalogAPI.ENTITY_P_STAGE, reg);
			if (stage != null) {
				
				// Identificador del procedimiento/subproceso
				int pcdId = stage.getInt("ID_PCD");
				
				// Información del procedimiento/subproceso
				IItem pcd = getEntity(session, ICatalogAPI.ENTITY_P_PROCEDURE, pcdId);
				
				// Tipo del procedimiento/subproceso
				int pcdType = pcd.getInt("TIPO");
	
				if (pcdType == IPcdElement.TYPE_SUBPROCEDURE) {
					request.setAttribute("ACTIVITY_ITEM", new ItemBean(stage));
				} else { //if (pcdType == IPcdElement.TYPE_PROCEDURE) {
					request.setAttribute("STAGE_ITEM", new ItemBean(stage));
				}
				
				// Establecer la información del procedimiento/subproceso
				setProcedureInfo(session, request, pcd);
			}			
		} catch (ISPACException e) {
			logger.info("Error al obtener la información de la fase/actividad #" + reg, e);
		}
	}

	private void setSincNodeInfo(SessionAPI session, HttpServletRequest request, 
			int reg) {

		try {
			IItem item = getEntity(session, ICatalogAPI.ENTITY_P_SINCNODE, reg);
			request.setAttribute("SINCNODE_ITEM", new ItemBean(item));

			// Establecer la información del procedimiento de la fase
			if (item != null) {
				int pcdId = item.getInt("ID_PCD");
				setProcedureInfo(session, request, pcdId);
			}
		} catch (ISPACException e) {
			logger.info("Error al obtener la información del nodo de sincronización #" + reg, e);
		}
	}

	private void setProcedureInfo(SessionAPI session, HttpServletRequest request, 
			int reg) {

		try {
			IItem item = getEntity(session, ICatalogAPI.ENTITY_P_PROCEDURE, reg);
			int type = item.getInt("TIPO");

			if (type == IPcdElement.TYPE_SUBPROCEDURE) {
				request.setAttribute("SUBPCD_ITEM", new ItemBean(item));
			} else { //if (type == IPcdElement.TYPE_PROCEDURE) {
				request.setAttribute("PCD_ITEM", new ItemBean(item));
				
				// Procedimiento en borrador
				if (item.getInt("ESTADO") == IProcedure.PCD_STATE_DRAFT) {
					request.setAttribute("PCD_DRAFT", Boolean.TRUE);
				}
			}
		} catch (ISPACException e) {
			logger.info("Error al obtener la información del procedimiento/subproceso #" + reg, e);
		}
	}

	private void setProcedureInfo(SessionAPI session, HttpServletRequest request, 
			IItem pcd) {

		try {
			int type = pcd.getInt("TIPO");
			if (type == IPcdElement.TYPE_SUBPROCEDURE) {
				request.setAttribute("SUBPCD_ITEM", new ItemBean(pcd));
			} else { //if (type == IPcdElement.TYPE_PROCEDURE) {
				request.setAttribute("PCD_ITEM", new ItemBean(pcd));
				
				// Procedimiento en borrador
				if (pcd.getInt("ESTADO") == IProcedure.PCD_STATE_DRAFT) {
					request.setAttribute("PCD_DRAFT", Boolean.TRUE);
				}
			}
		} catch (ISPACException e) {
			logger.info("Error al obtener la información del procedimiento/subproceso: " + pcd, e);
		}
	}

	private IItem getEntity(SessionAPI session, int entity, int regid) 
			throws ISPACException {
		
		IItem item = null;
		
		ICatalogAPI catalogAPI = session.getAPI().getCatalogAPI();
        IItemCollection collection = catalogAPI.queryCTEntities(entity, 
        		new StringBuffer("WHERE ID=").append(regid).toString());
        if (collection != null) {
        	if (collection.iterator().hasNext())
        		item = (EntityDAO)collection.iterator().next();
        	else{
        		item=null;
        	}
        }
        
		return item;
	}

	private List getProducerList(SessionAPI session, int reg) {
		List producerList = new ArrayList();
		
		try {
			IEntitiesAPI entapi = session.getAPI().getEntitiesAPI();
			
			ProducersConnectorFactory producersConnectorFactory = ProducersConnectorFactory.getInstance();
			IProducersConnector producersConnector = producersConnectorFactory.getProducersConnector(session.getClientContext());

			// Obtener la lista de unidades tramitadoras del procedimiento
			Map mapTableEntity = new HashMap();
			mapTableEntity.put("SPAC_CT_PROCEDIMIENTOS", "SPAC_CT_PROCEDIMIENTOS");
			mapTableEntity.put("SPAC_CT_PCDORG", "SPAC_CT_PCDORG");

			IItemCollection producers = entapi.queryEntities(mapTableEntity,
					new StringBuffer("WHERE SPAC_CT_PROCEDIMIENTOS.ID=").append(reg)
						.append(" AND SPAC_CT_PROCEDIMIENTOS.COD_PCD=SPAC_CT_PCDORG.COD_PCD")
						.append(" ORDER BY SPAC_CT_PCDORG.ORDEN")
						.toString());
			producerList.addAll(CollectionBean.getBeanList(producers));
			
			// Obtener el nombre de las unidades tramitadoras
			ItemBean bean;
			String producerId;
			Producer producer;
			for (int i = 0; i < producerList.size(); i++) {
				bean = (ItemBean) producerList.get(i);
				producerId = (String) bean.getProperty("SPAC_CT_PCDORG:ID_UNIDAD_TRAMITADORA");
				producer = producersConnector.getProducer(producerId);
				if (producer != null) {
					bean.setProperty("NOMBRE_UNIDAD_TRAMITADORA", producer.getName());
				}
			}
			
		} catch (ISPACException e) {
			logger.info("Error al obtener la información de los productores del procedimiento #" + reg, e);
		}
		
		return producerList;
	}
}
