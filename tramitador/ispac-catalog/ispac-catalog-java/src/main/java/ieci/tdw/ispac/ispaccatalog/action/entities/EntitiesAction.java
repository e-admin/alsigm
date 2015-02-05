package ieci.tdw.ispac.ispaccatalog.action.entities;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaccatalog.action.BaseDispatchAction;
import ieci.tdw.ispac.ispaccatalog.bean.PEventBean;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EntitiesAction extends BaseDispatchAction {

	/**
	 * Añade la entidad al procedimiento.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

		String sEntityId = request.getParameter("entityId");
		String sPcdId = request.getParameter("pcdId");

		if (StringUtils.isNotBlank(sEntityId)
				&& StringUtils.isNotBlank(sPcdId)) {

			int entityId = Integer.parseInt(sEntityId);
			int pcdId = Integer.parseInt(sPcdId);

			request.setAttribute("Refresh","true");

			IClientContext cct = session.getClientContext();
			IInvesflowAPI invesFlowAPI = session.getAPI();
			ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

			//Si ya existe no se crea y se debe notificar al usuario.
			String sqlquery = new StringBuffer()
				.append("WHERE ID_ENT=").append(entityId)
				.append(" AND ID_PCD=").append(pcdId)
				.toString();
			int ncount = catalogAPI.countCTEntities(
					ICatalogAPI.ENTITY_P_ENTITY, sqlquery);

			if (ncount == 0) {
				//Se calcula el orden para la nueva entidad
				IItem item = catalogAPI.createCTEntity(
						ICatalogAPI.ENTITY_P_ENTITY);
				item.set("ID_ENT", entityId);
				item.set("ID_PCD", pcdId);
				item.set("ORDEN",  item.getKeyInt());
				item.set("TP_REL", 0);
				item.store(cct);
			}
		}

		return mapping.findForward("closeIFrame");
	}

	/**
	 * Añade una aplicación o una regla a la entidad dentro del procedimiento.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward addApp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

        IClientContext cct = session.getClientContext();
		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		String sEntityId = request.getParameter("entityId");
		String parentElementId = request.getParameter("parentElementId");


		//obtencion del item a modificar o creacion del item seguna sea el caso
		IItem item=null;
		boolean isProcedure = (parentElementId==null || parentElementId.length()==0);
		if (isProcedure){

			int pentId = Integer.parseInt(request.getParameter("pentId"));

			item = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_ENTITY, pentId);
		}
		else {

			int parentEntityId = new Integer(request.getParameter("parentEntityId")).intValue();
			int entId = Integer.parseInt(request.getParameter("entId"));

			if ((parentEntityId == ICatalogAPI.ENTITY_P_STAGE) ||
				(parentEntityId == ICatalogAPI.ENTITY_P_ACTIVITIES)) {

				String query = "WHERE ID_ENT=" + entId + " AND ID_FASE=" + parentElementId + " ";
				IItemCollection frmsStage = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_P_FRMSTAGE, query);
				if (frmsStage.next()) {
					/*
					for (Iterator iter = frmsStage.iterator(); iter.hasNext();) {
						IItem frmStage = (IItem) iter.next();
						item = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_FRMSTAGE, frmStage.getKeyInt());
						break;
					}*/
					item = frmsStage.value();

				}
				else {
					item = catalogAPI.createCTEntity(ICatalogAPI.ENTITY_P_FRMSTAGE);
					// Por defecto el formulario es editable
					item.set("FRM_READONLY", ISPACEntities.ENTITY_FORM_EDITABLE);
				}

				item.set("ID_FASE",parentElementId);
			}
			else if (parentEntityId == ICatalogAPI.ENTITY_P_TASK) {

				String query = "WHERE ID_ENT=" + entId + " AND ID_TRAMITE=" + parentElementId + " ";
				IItemCollection frmsTask = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_P_FRMTASK, query);
				if (frmsTask.next()){
					/*
					for (Iterator iter = frmsTask.iterator(); iter.hasNext();) {
						IItem frmTask = (IItem) iter.next();
						item = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_FRMTASK, frmTask.getKeyInt());
						break;
					}*/
					item = frmsTask.value();
				}
				else{
					item = catalogAPI.createCTEntity(ICatalogAPI.ENTITY_P_FRMTASK);
					// Por defecto el formulario es editable
					item.set("FRM_READONLY", ISPACEntities.ENTITY_FORM_EDITABLE);
				}

				item.set("ID_TRAMITE",parentElementId);
			}

			item.set("ID_ENT", entId);
		}

		if (item != null) {

			String appId = request.getParameter("appId");
			if (appId == null || appId.length() == 0) {
			    item.set("FRM_EDIT", (Object) null);
			} else {
				// Selección de formulario
			    item.set("FRM_EDIT", Integer.parseInt(appId));
			}

			String ruleId = request.getParameter("ruleId");
			if (ruleId == null || ruleId.length() == 0) {
			    item.set("ID_RULE_FRM", (Object) null);
			} else {
				// Selección de regla para asignar el formulario
			    item.set("ID_RULE_FRM", Integer.parseInt(ruleId));
			}

			/* Se incluye botón para hacer visible la entidad (elimina la no visibilidad y la regla)
			if ((appId == null || appId.length() == 0) && (ruleId == null || ruleId.length() == 0)) {
				// Por defecto en la entidad a nivel de procedimiento
				item.set("ID_RULE_VISIBLE", (Object) null);
			}
			*/

			item.store(cct);
		}

		if (StringUtils.isNotBlank(request.getParameter("inFrame"))) {
			return mapping.findForward("closeIFrame");
		}
		else {
			String pcdId = item.get("ID_PCD").toString();
	   	 	return new ActionForward(new StringBuffer()
	   	 			.append("/showProcedureEntity.do?method=entities&entityId=")
	   	 			.append(sEntityId)
	   	 			.append("&regId=")
	   	 			.append(pcdId)
	   	 			.toString(), true);
		}
	}

	/**
	 * Modifica el orden de la entidad dentro del procedimiento.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward modEntityOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

        IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		String pcdId = request.getParameter("pcdId");

		//Ordenar entidades
		String modOrder=request.getParameter("modOrder");
		String entityId=request.getParameter("pentId");
	    if (modOrder.equalsIgnoreCase("INC")) {

	        catalogAPI.incOrderPEntity(Integer.parseInt(pcdId),
	        		Integer.parseInt(entityId));
	    }
	    else {
	        catalogAPI.decOrderPEntity(Integer.parseInt(pcdId),
	        		Integer.parseInt(entityId));
	    }

   	 	return new ActionForward(new StringBuffer()
   	 			.append("/showProcedureEntity.do?method=entities&entityId=")
   	 			.append(request.getParameter("entityId"))
   	 			.append("&regId=")
   	 			.append(pcdId)
   	 			.toString(), true);
    }

	/**
	 * Elimina la entidad asignada al procedimiento
	 * o el formulario asignado a la entidad en la fase o trámite.
	 *
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

        IClientContext cct = session.getClientContext();
        IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

		String delId = request.getParameter("delId");
		int delIdInt = ISPACEntities.ENTITY_NULLREGKEYID;
		if (delId != null) {
			delIdInt = Integer.parseInt(delId);
		}

		String entityId = request.getParameter("entityId");
		int entityIdInt = ISPACEntities.ENTITY_NULLREGKEYID;
		if (entityId != null) {
			entityIdInt = Integer.parseInt(entityId);
		}

		if ((delIdInt != ISPACEntities.ENTITY_NULLREGKEYID) &&
			(entityIdInt != ISPACEntities.ENTITY_NULLREGKEYID)) {

			IItem item = null;

			// Eliminar entidad del procedimiento o subproceso
			if ((entityIdInt == ICatalogAPI.ENTITY_P_PROCEDURE)	||
				(entityIdInt == ICatalogAPI.ENTITY_P_SUBPROCEDURE)) {

				IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();

				// Eliminar la entidad,
				// eliminando también las posibles asignaciones de formularios de la entidad a nivel de fase o trámite
				// y los eventos asociados a la entidad en el procedimiento
				procedureAPI.deletePcdEntity(delIdInt);
			}
			// Eliminar en la fase/actvidad, luego la entidad definida por defecto en el procedimiento
			else if ((entityIdInt == ICatalogAPI.ENTITY_P_STAGE) ||
					 (entityIdInt == ICatalogAPI.ENTITY_P_ACTIVITIES)) {

				// Formulario ya asignado
				item = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_FRMSTAGE, delIdInt);
			}
			// Eliminar en el trámite, luego la entidad definida por defecto en la fase
			else if (entityIdInt == ICatalogAPI.ENTITY_P_TASK) {

				// Formulario ya asignado
				item = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_FRMTASK, delIdInt);
			}

			if (item != null) {

				// Si formulario de entidad visible mediante regla
			    if ((item.get("ID_RULE_VISIBLE") != null) && (item.getInt("ID_RULE_VISIBLE") > 0)) {

			    	// Reinicializar campos
			    	item.set("FRM_EDIT", (Object) null);
					item.set("ID_RULE_FRM", (Object) null);

					item.store(cct);
			    } else {
					// Eliminar
					item.delete(cct);
			    }
			}
		}

   	 	return new ActionForward(new StringBuffer()
   	 			.append("/showProcedureEntity.do?method=entities&entityId=")
   	 			.append(entityId)
   	 			.append("&regId=")
   	 			.append(request.getParameter("regId"))
   	 			.toString(), true);
    }

	/**
	 * Establece la entidad seleccionada como no visible.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward novisible(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

        IClientContext cct = session.getClientContext();
        IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		String keyId = request.getParameter("keyId");
		int keyIdInt = ISPACEntities.ENTITY_NULLREGKEYID;
		if (keyId != null) {
			keyIdInt = Integer.parseInt(keyId);
		}

		String entityId = request.getParameter("entityId");
		int entityIdInt = ISPACEntities.ENTITY_NULLREGKEYID;
		if (entityId != null) {
			entityIdInt = Integer.parseInt(entityId);
		}

		String entId = request.getParameter("entId");
		String regId = request.getParameter("regId");

		IItem item = null;

		// Formulario a nivel de procedimiento o subproceso
		if ((entityIdInt == ICatalogAPI.ENTITY_P_PROCEDURE) ||
			(entityIdInt == ICatalogAPI.ENTITY_P_SUBPROCEDURE)) {

			// Formulario ya asignado (a este nivel el registro siempre existe)
			if (keyIdInt != ISPACEntities.ENTITY_NULLREGKEYID) {
				item = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_ENTITY, keyIdInt);
			}
		}
		// Formulario a nivel de fase o actividad
		else if ((entityIdInt == ICatalogAPI.ENTITY_P_STAGE) ||
				 (entityIdInt == ICatalogAPI.ENTITY_P_ACTIVITIES)) {

			// Formulario ya asignado
			if (keyIdInt != ISPACEntities.ENTITY_NULLREGKEYID) {
				item = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_FRMSTAGE, keyIdInt);
			}
			else {

				item = catalogAPI.createCTEntity(ICatalogAPI.ENTITY_P_FRMSTAGE);
				item.set("ID_ENT", Integer.parseInt(entId));
				item.set("ID_FASE", Integer.parseInt(regId));
			}
		}
		// Formulario a nivel de trámite
		else if (entityIdInt == ICatalogAPI.ENTITY_P_TASK) {

			// Formulario ya asignado
			if (keyIdInt != ISPACEntities.ENTITY_NULLREGKEYID) {
				item = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_FRMTASK, keyIdInt);
			}
			else {
				item = catalogAPI.createCTEntity(ICatalogAPI.ENTITY_P_FRMTASK);
				item.set("ID_ENT", Integer.parseInt(entId));
				item.set("ID_TRAMITE", Integer.parseInt(regId));
			}
		}

		if (item != null) {

			// Formulario de entidad no visible
			item.set("FRM_EDIT", ISPACEntities.ENTITY_FORM_NO_VISIBLE);

			// Reinicializar campos
			item.set("FRM_READONLY", ISPACEntities.ENTITY_FORM_EDITABLE);
			item.set("ID_RULE_FRM", (Object) null);
			item.set("ID_RULE_VISIBLE", (Object) null);

			item.store(cct);
		}

   	 	return new ActionForward(new StringBuffer()
   	 			.append("/showProcedureEntity.do?method=entities&entityId=")
   	 			.append(entityId)
   	 			.append("&regId=")
   	 			.append(regId)
   	 			.toString(), true);
    }

	/**
	 * Establece la entidad seleccionada como visible, eliminando la no visibilidad y la asignación de regla.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward visible(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

        IClientContext cct = session.getClientContext();
        IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		String keyId = request.getParameter("keyId");
		int keyIdInt = ISPACEntities.ENTITY_NULLREGKEYID;
		if (keyId != null) {
			keyIdInt = Integer.parseInt(keyId);
		}

		String entityId = request.getParameter("entityId");
		int entityIdInt = ISPACEntities.ENTITY_NULLREGKEYID;
		if (entityId != null) {
			entityIdInt = Integer.parseInt(entityId);
		}

		String entId = request.getParameter("entId");
		String regId = request.getParameter("regId");

		IItem item = null;

		// Formulario a nivel de procedimiento o subproceso
		if ((entityIdInt == ICatalogAPI.ENTITY_P_PROCEDURE) ||
			(entityIdInt == ICatalogAPI.ENTITY_P_SUBPROCEDURE)) {

			// Formulario ya asignado (a este nivel el registro siempre existe)
			if (keyIdInt != ISPACEntities.ENTITY_NULLREGKEYID) {
				item = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_ENTITY, keyIdInt);
			}
		}
		// Formulario a nivel de fase o actividad
		else if ((entityIdInt == ICatalogAPI.ENTITY_P_STAGE) ||
				 (entityIdInt == ICatalogAPI.ENTITY_P_ACTIVITIES)) {

			// Formulario ya asignado
			if (keyIdInt != ISPACEntities.ENTITY_NULLREGKEYID) {
				item = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_FRMSTAGE, keyIdInt);
			}
		}
		// Formulario a nivel de trámite
		else if (entityIdInt == ICatalogAPI.ENTITY_P_TASK) {

			// Formulario ya asignado
			if (keyIdInt != ISPACEntities.ENTITY_NULLREGKEYID) {
				item = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_FRMTASK, keyIdInt);
			}
		}

		if (item != null) {

			// Si formulario de entidad no visible
		    if ((item.get("FRM_EDIT") != null) && (item.getInt("FRM_EDIT") == ISPACEntities.ENTITY_FORM_NO_VISIBLE)) {

				// Formulario a nivel de procedimiento o subproceso
				if ((entityIdInt == ICatalogAPI.ENTITY_P_PROCEDURE) ||
					(entityIdInt == ICatalogAPI.ENTITY_P_SUBPROCEDURE)) {

			    	// Reinicializar campos
			    	item.set("FRM_EDIT", (Object) null);
					item.set("FRM_READONLY", ISPACEntities.ENTITY_FORM_EDITABLE);

					item.store(cct);
				} else {
					// Por defecto en el procedimiento o en la fase
					item.delete(cct);
				}
		    } else {
				// Reinicializar campos
				item.set("ID_RULE_VISIBLE", (Object) null);

				item.store(cct);
		    }
		}

   	 	return new ActionForward(new StringBuffer()
   	 			.append("/showProcedureEntity.do?method=entities&entityId=")
   	 			.append(entityId)
   	 			.append("&regId=")
   	 			.append(regId)
   	 			.toString(), true);
    }

	/**
	 * Añade una regla de visibilidad a la entidad dentro del procedimiento.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward addVisibleRule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

        IClientContext cct = session.getClientContext();
		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		String sEntityId = request.getParameter("entityId");
		String parentElementId = request.getParameter("parentElementId");


		//obtencion del item a modificar o creacion del item seguna sea el caso
		IItem item=null;
		boolean isProcedure = (parentElementId==null || parentElementId.length()==0);
		if (isProcedure){

			int pentId = Integer.parseInt(request.getParameter("pentId"));

			item = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_ENTITY, pentId);
		}
		else {

			int parentEntityId = new Integer(request.getParameter("parentEntityId")).intValue();
			int entId = Integer.parseInt(request.getParameter("entId"));

			if ((parentEntityId == ICatalogAPI.ENTITY_P_STAGE) ||
				(parentEntityId == ICatalogAPI.ENTITY_P_ACTIVITIES)) {

				String query = "WHERE ID_ENT=" + entId + " AND ID_FASE=" + parentElementId + " ";
				IItemCollection frmsStage = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_P_FRMSTAGE, query);
				if (frmsStage.next()) {
					/*
					for (Iterator iter = frmsStage.iterator(); iter.hasNext();) {
						IItem frmStage = (IItem) iter.next();
						item = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_FRMSTAGE, frmStage.getKeyInt());
						break;
					}*/
					item = frmsStage.value();

				}
				else {
					item = catalogAPI.createCTEntity(ICatalogAPI.ENTITY_P_FRMSTAGE);
					// Por defecto el formulario es editable
					item.set("FRM_READONLY", ISPACEntities.ENTITY_FORM_EDITABLE);
				}

				item.set("ID_FASE",parentElementId);
			}
			else if (parentEntityId == ICatalogAPI.ENTITY_P_TASK) {

				String query = "WHERE ID_ENT=" + entId + " AND ID_TRAMITE=" + parentElementId + " ";
				IItemCollection frmsTask = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_P_FRMTASK, query);
				if (frmsTask.next()){
					/*
					for (Iterator iter = frmsTask.iterator(); iter.hasNext();) {
						IItem frmTask = (IItem) iter.next();
						item = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_FRMTASK, frmTask.getKeyInt());
						break;
					}*/
					item = frmsTask.value();
				}
				else{
					item = catalogAPI.createCTEntity(ICatalogAPI.ENTITY_P_FRMTASK);
					// Por defecto el formulario es editable
					item.set("FRM_READONLY", ISPACEntities.ENTITY_FORM_EDITABLE);
				}

				item.set("ID_TRAMITE",parentElementId);
			}

			item.set("ID_ENT", entId);
		}

		if (item != null) {

			String ruleId = request.getParameter("ruleId");
			if (ruleId != null && ruleId.length() > 0) {

			    item.set("ID_RULE_VISIBLE", Integer.parseInt(ruleId));
			    if ((item.get("FRM_EDIT") != null) && (item.getInt("FRM_EDIT") == ISPACEntities.ENTITY_FORM_NO_VISIBLE)) {
			    	// Reinicializar campos
			    	item.set("FRM_EDIT", (Object) null);
			    }

			    item.store(cct);
			}
		}

		if (StringUtils.isNotBlank(request.getParameter("inFrame"))) {
			return mapping.findForward("closeIFrame");
		}
		else {
			String pcdId = item.get("ID_PCD").toString();
	   	 	return new ActionForward(new StringBuffer()
	   	 			.append("/showProcedureEntity.do?method=entities&entityId=")
	   	 			.append(sEntityId)
	   	 			.append("&regId=")
	   	 			.append(pcdId)
	   	 			.toString(), true);
		}
	}

	/**
	 * Establece la entidad seleccionada como no visible.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward readonly(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

        IClientContext cct = session.getClientContext();
        IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		String keyId = request.getParameter("keyId");
		int keyIdInt = ISPACEntities.ENTITY_NULLREGKEYID;
		if (keyId != null) {
			keyIdInt = Integer.parseInt(keyId);
		}

		String entityId = request.getParameter("entityId");
		int entityIdInt = ISPACEntities.ENTITY_NULLREGKEYID;
		if (entityId != null) {
			entityIdInt = Integer.parseInt(entityId);
		}

		String entId = request.getParameter("entId");
		String regId = request.getParameter("regId");

		IItem item = null;

		// Formulario a nivel de procedimiento o subproceso
		if ((entityIdInt == ICatalogAPI.ENTITY_P_PROCEDURE) ||
			(entityIdInt == ICatalogAPI.ENTITY_P_SUBPROCEDURE)) {

			// Formulario ya asignado (a este nivel el registro siempre existe)
			if (keyIdInt != ISPACEntities.ENTITY_NULLREGKEYID) {
				item = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_ENTITY, keyIdInt);
			}
		}
		// Formulario a nivel de fase o actividad
		else if ((entityIdInt == ICatalogAPI.ENTITY_P_STAGE) ||
				 (entityIdInt == ICatalogAPI.ENTITY_P_ACTIVITIES)) {

			// Formulario ya asignado
			if (keyIdInt != ISPACEntities.ENTITY_NULLREGKEYID) {
				item = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_FRMSTAGE, keyIdInt);
			}
			else {

				item = catalogAPI.createCTEntity(ICatalogAPI.ENTITY_P_FRMSTAGE);
				item.set("ID_ENT", Integer.parseInt(entId));
				item.set("ID_FASE", Integer.parseInt(regId));
			}
		}
		// Formulario a nivel de trámite
		else if (entityIdInt == ICatalogAPI.ENTITY_P_TASK) {

			// Formulario ya asignado
			if (keyIdInt != ISPACEntities.ENTITY_NULLREGKEYID) {
				item = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_FRMTASK, keyIdInt);
			}
			else {

				item = catalogAPI.createCTEntity(ICatalogAPI.ENTITY_P_FRMTASK);
				item.set("ID_ENT", Integer.parseInt(entId));
				item.set("ID_TRAMITE", Integer.parseInt(regId));
			}
		}

		if (item != null) {

			// Formulario a nivel de procedimiento o subproceso
			if ((entityIdInt == ICatalogAPI.ENTITY_P_PROCEDURE) ||
				(entityIdInt == ICatalogAPI.ENTITY_P_SUBPROCEDURE)) {

				// Modificar el indicador de formulario en sólo lectura
				String sReadonly = item.getString("FRM_READONLY");
				if (StringUtils.isNotBlank(sReadonly)) {

					// Lectura
					Object object = null;
					item.set("FRM_READONLY", object);
				}
				else {
					// Sólo lectura
					item.set("FRM_READONLY", ISPACEntities.ENTITY_FORM_READONLY);
				}
				item.store(cct);
			}
			else {
				// Modificar el indicador de formulario en sólo lectura
				String sReadonly = request.getParameter("readonly");
				if (StringUtils.isNotBlank(sReadonly)) {

					item.set("FRM_READONLY", sReadonly);
					item.store(cct);
				}
			}
		}

   	 	return new ActionForward(new StringBuffer()
   	 			.append("/showProcedureEntity.do?method=entities&entityId=")
   	 			.append(entityId)
   	 			.append("&regId=")
   	 			.append(regId)
   	 			.toString(), true);
    }

	/**
	 * Muestra los eventos asociados a la entidad en el procedimiento.
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

        // Se recoge el identificador concreto
        int intIdObj = Integer.parseInt(request.getParameter("regId"));
        int intTpObj = EventsDefines.EVENT_OBJ_ENTITY;
        int entityId = ICatalogAPI.ENTITY_P_ENTITY;

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
		request.setAttribute("label", request.getParameter("label"));

		request.setAttribute("EventRulesList", eventlist);
		request.setAttribute("TpObj", String.valueOf(intTpObj));
		request.setAttribute("IdObj", String.valueOf(intIdObj));
		request.setAttribute("entityId", String.valueOf(entityId));

		// Formateador para la lista de eventos
		if (FunctionHelper.userHasFunction(request, session.getClientContext(), ISecurityAPI.FUNC_INV_PROCEDURES_EDIT)) {
			setFormatter(request, "Formatter", "/formatters/events/objeventsformatter.xml");
		} else {
			setFormatter(request, "Formatter", "/formatters/events/objeventsreadonlyformatter.xml");
		}

		return mapping.findForward("events");
	}

}