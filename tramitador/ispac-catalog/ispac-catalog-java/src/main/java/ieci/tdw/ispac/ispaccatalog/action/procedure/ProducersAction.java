package ieci.tdw.ispac.ispaccatalog.action.procedure;
 
import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaccatalog.action.BaseDispatchAction;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.producers.IProducersConnector;
import ieci.tdw.ispac.ispaclib.producers.ProducersConnectorFactory;
import ieci.tdw.ispac.ispaclib.producers.vo.Producer;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * Gestiona los productores de un procedimiento.
 *
 */
public class ProducersAction extends BaseDispatchAction {

	/** 
	 * Logger de la clase. 
	 */
	protected static final Logger logger = Logger.getLogger(ProducersAction.class);


	/**
	 * Muestra la información del productor del procedimiento.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward form(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_READ,
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		int reg = Integer.parseInt(request.getParameter("retKeyId"));
		
		ICatalogAPI catalogAPI = session.getAPI().getCatalogAPI();
		IItem pcd = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_CT_PROCEDURE, reg);
		request.setAttribute("PCD_ITEM", new ItemBean(pcd));

		String orgUnitId = ((EntityForm)form).getProperty("UID");
		if (StringUtils.isNotBlank(orgUnitId)) {
			((EntityForm)form).setProperty("ID_UNIDAD_TRAMITADORA", orgUnitId);
			((EntityForm)form).setProperty("NOMBRE_UNIDAD_TRAMITADORA",	getOrgUnitName(session, orgUnitId));
		}
		
   	 	return mapping.findForward("form");
	}

	/**
	 * Muestra las unidades de tramitación.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward showOrgUnits(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_READ,
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		ProducersConnectorFactory producersConnectorFactory = ProducersConnectorFactory.getInstance();
		IProducersConnector producersConnector = producersConnectorFactory.getProducersConnector(session.getClientContext());

		String producerId = request.getParameter("uidint");

		if (StringUtils.isBlank(producerId)) {
			Producer producer = producersConnector.getRootProducer();
			if (producer != null) {
				producerId = producer.getId();
			}
		}

		List ancestors = producersConnector.getProducerAncestors(producerId);
		request.setAttribute("ancestors", ancestors);

		List children = producersConnector.getProducerChildren(producerId);
		request.setAttribute("responsibles", children);

   	 	return mapping.findForward("orgunits");
	}
	
	/**
	 * Guarda la información del productor del procedimiento.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		int pcdorgId = TypeConverter.parseInt(request.getParameter("pcdorgId"),
				ISPACEntities.ENTITY_NULLREGKEYID);
		
		ClientContext cct = session.getClientContext();
		ICatalogAPI catalogAPI = session.getAPI().getCatalogAPI();

		// Formulario asociado a la acción
		EntityForm defaultForm = (EntityForm) form;
		
		// Validar el formulario
		ActionMessages messages = validateProducerForm(defaultForm, request);
		if (!messages.isEmpty()) {
			addErrors(request, messages);
			return form(mapping, form, request, response, session);
		}

		IItem pcdorg = null;
		
		// Ejecución en un contexto transaccional
		boolean bCommit = false;
		
		try {
	        // Abrir transacción
	        cct.beginTX();
		
	        // Obtener la aplicación que gestiona la entidad
			if (pcdorgId == ISPACEntities.ENTITY_NULLREGKEYID) {
				pcdorg = catalogAPI.createCTEntity(ICatalogAPI.ENTITY_CT_PCDORG);
			    pcdorgId  = pcdorg.getKeyInt();
			} else {
				pcdorg = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_CT_PCDORG, pcdorgId);
			}
	
			// Permite modificar los datos del formulario
			defaultForm.setReadonly("false");
			// Salva el identificador de la entidad
			defaultForm.setEntity(Integer.toString(ICatalogAPI.ENTITY_CT_PCDORG));
			// Salva el identificador del registro
			defaultForm.setKey(Integer.toString(pcdorgId));
			
			pcdorg.set("COD_PCD", defaultForm.getProperty("COD_PCD"));
			pcdorg.set("ID_UNIDAD_TRAMITADORA", 
					defaultForm.getProperty("ID_UNIDAD_TRAMITADORA"));
			pcdorg.set("FECHA_INI_PROD", TypeConverter.toDate(
					defaultForm.getProperty("FECHA_INI_PROD")));
			pcdorg.set("ORDEN", pcdorg.getKeyInt());
			
			// Guardar la entidad
			pcdorg.store(cct);
			
			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
		}
		catch (Throwable t) {
			logger.error("Error al guardar la información del productor", t);
		}
		finally {
			cct.endTX(bCommit);
		}
			
   	 	return mapping.findForward("closeIFrameAndSave");
	}

	/**
	 * Reordena la lista de productores del procedimiento.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward reorder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		String pcdorgId = request.getParameter("pcdorgId");
		if (StringUtils.isNotBlank(pcdorgId)) {
	        
			IInvesflowAPI invesFlowAPI = session.getAPI();
			ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

			// Ordenar productores
			String modOrder=request.getParameter("modOrder");
		    if ("INC".equalsIgnoreCase(modOrder)) {
		        catalogAPI.incOrderPcdProducer(Integer.parseInt(pcdorgId));
		    } else if ("DEC".equalsIgnoreCase(modOrder)) {
		        catalogAPI.decOrderPcdProducer(Integer.parseInt(pcdorgId));
		    }
		}

   	 	return getActionForward(mapping, request, "pcd", true);
	}

	/**
	 * Elimina los productores del procedimiento seleccionados.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		// Indentificador del procedimiento
 		int pcdId = Integer.parseInt(request.getParameter("key"));

 		// Identificadores de los productores del procedimiento
 		String[] prodIds = ((EntityForm) form).getMultibox();

 		try {
	 		
 			// Eliminar los productores
	 		deleteProducers(session, prodIds);
	 		
 		} catch (ISPACException e) {
 			
 			logger.error("Error al eliminar los productores del procedimiento " + pcdId);
 			
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, 
					new ActionMessage("procedure.card.error.removeProducers",
							new Object[] { e.toString() }));
			saveAppErrors(request, errors);
			
			return getActionForward(mapping, request, "pcd", false);
 		}

		return getActionForward(mapping, request, "pcd", true);
	}

	private ActionForward getActionForward(ActionMapping mapping, 
			HttpServletRequest request, String name, boolean redirect) {

		ActionForward forward = mapping.findForward(name);

 		// Identificadores para el retorno
		int retEntityId = Integer.parseInt(request.getParameter("retEntityId"));
 		int retKeyId = Integer.parseInt(request.getParameter("retKeyId"));
		String block = request.getParameter("block");

		StringBuffer url = new StringBuffer(forward.getPath())
			.append("&entityId=").append(retEntityId)	
			.append("&regId=").append(retKeyId);
		
		// Mostrar el bloque adecuado
		if (StringUtils.isNotBlank(block)) {
			url.append("&block=").append(block);
		}

		return new ActionForward(forward.getName(), url.toString(), redirect);
	}

	private void deleteProducers(SessionAPI session, String[] prodIds)
			throws ISPACException {

		if (!ArrayUtils.isEmpty(prodIds)) {

			ICatalogAPI catalogAPI = session.getAPI().getCatalogAPI();
			IItem item;

			for (int i = 0; i < prodIds.length; i++) {
				item = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_CT_PCDORG,
						TypeConverter.parseInt(prodIds[i]));
				if (item != null) {
					item.delete(session.getClientContext());
				}
			}
		}
	}

	private String getOrgUnitName(SessionAPI session, String orgUnitId) 
			throws ISPACException {
		String orgUnitName = null;

		ProducersConnectorFactory producersConnectorFactory = ProducersConnectorFactory.getInstance();
		IProducersConnector producersConnector = producersConnectorFactory.getProducersConnector(session.getClientContext());
		Producer producer = producersConnector.getProducer(orgUnitId);
		if (producer != null) {
			orgUnitName = producer.getName();
		}
		
		return orgUnitName;
	}

	private ActionMessages validateProducerForm(EntityForm form, 
			HttpServletRequest request) {
		ActionMessages messages = new ActionMessages();
		
		EntityForm entityForm = form;
		if (StringUtils.isBlank(entityForm.getProperty("ID_UNIDAD_TRAMITADORA"))) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, 
					new ActionMessage("procedure.card.producer.msgs.orgunit"));
		}
		
		if (StringUtils.isBlank(form.getProperty("FECHA_INI_PROD"))) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, 
					new ActionMessage("procedure.card.producer.msgs.initdate"));
		}
	
		return messages;
	}
}
