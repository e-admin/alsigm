package ieci.tdw.ispac.ispaccatalog.action.procedure;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.BaseDispatchAction;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.bean.ValidationError;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class StoreProcedureAction extends BaseDispatchAction {

	/**
	 * Guarda la información de la ficha catalográfica de la entidad.
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

		// Identificador de la entidad
		//int entityId = ICatalogAPI.ENTITY_CT_PROCEDURE;
		int entityId = Integer.parseInt(request.getParameter("entity"));

		// Indentificador del registro
 		int keyId = Integer.parseInt(request.getParameter("key"));

 		// Identificadores para el retorno
		int retEntityId = Integer.parseInt(request.getParameter("retEntityId"));
 		int retKeyId = Integer.parseInt(request.getParameter("retKeyId"));

		ActionForward forward = storeEntity(mapping, form, request, session,
				"card", entityId, keyId, retEntityId, retKeyId);

		// Mostrar el bloque adecuado
		String block = request.getParameter("block");
		if (StringUtils.isNotBlank(block)) {
			String path = new StringBuffer(forward.getPath())
				.append("&block=").append(block).toString();
			forward = new ActionForward(forward.getName(), path,
					forward.getRedirect());
		}

		return forward;
	}

	/**
	 * Guarda las propiedades de la entidad.
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

		// Identificador de la entidad
		int entityId = Integer.parseInt(request.getParameter("entity"));

		// Identificador del registro
 		int keyId = Integer.parseInt(request.getParameter("key"));

		return storeEntity(mapping, form, request, session,
				"properties", entityId, keyId, entityId, keyId);
	}

	public ActionForward initdeadlines(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

		// Identificador de la entidad
		int entityId = Integer.parseInt(request.getParameter("entity"));

		// Identificador del registro
 		int keyId = Integer.parseInt(request.getParameter("key"));

		return storeEntity(mapping, form, request, session,
				"deadlines", entityId, keyId, entityId, keyId);
	}

	/**
	 * Guarda los plazos de la entidad.
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

		// Identificador de la entidad
		int entityId = Integer.parseInt(request.getParameter("entity"));

		// Identificador del registro
 		int keyId = Integer.parseInt(request.getParameter("key"));

		return storeEntity(mapping, form, request, session,
				"deadlines", entityId, keyId, entityId, keyId);
	}

	private ActionForward storeEntity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, SessionAPI session,
			String forwardName, int entityId, int keyId,
			int retEntityId, int retKeyId) throws ISPACException {

		ClientContext cct = session.getClientContext();

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, cct, new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

		// Formulario asociado a la acción
		EntityForm defaultForm = (EntityForm) form;

		EntityApp entityapp = null;
		String path = getRealPath("");

		// Ejecución en un contexto transaccional
		boolean bCommit = false;

		try {
	        // Abrir transacción
	        cct.beginTX();

	        // Obtener la aplicación que gestiona la entidad
			if (keyId == ISPACEntities.ENTITY_NULLREGKEYID) {
			    entityapp = catalogAPI.newCTDefaultEntityApp(entityId, path);
			    keyId  = entityapp.getEntityRegId();
			} else {
			    entityapp = catalogAPI.getCTDefaultEntityApp(entityId, keyId, path);
			}

			// Permite modificar los datos del formulario
			defaultForm.setReadonly("false");
			// Salva el identificador de la entidad
			defaultForm.setEntity(Integer.toString(entityId));
			// Salva el identificador del registro
			defaultForm.setKey(Integer.toString(keyId));
			defaultForm.processEntityApp(entityapp);

			//Se le asigna la clave del registro. Es necesario ya que el
			//item al que hace referencia puede estar recien creado y por tanto
			//tendría su campo clave a -1 (ISPACEntities.ENTITY_REGKEYID)
			entityapp.getItem().setKey(keyId);

			if (defaultForm.getProperty("SPAC_P_TRAMITES:OBLIGATORIO").equals(String.valueOf(ISPACEntities.PCD_TASK_OBLIGATORY)))
				entityapp.setProperty("SPAC_P_TRAMITES:OBLIGATORIO", new Integer(ISPACEntities.PCD_TASK_OBLIGATORY));
			else
				entityapp.setProperty("SPAC_P_TRAMITES:OBLIGATORIO", (Object) null);

			// Validación
			if (entityapp.validate()) {

				// Guardar la entidad
				entityapp.store();

				// Si todo ha sido correcto se hace commit de la transacción
				bCommit = true;
			}
			else {
				ActionMessages errors = new ActionMessages();
				List errorList = entityapp.getErrors();
				Iterator iteError = errorList.iterator();
				while (iteError.hasNext()) {
					ValidationError error = (ValidationError) iteError.next();
					errors.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage(error.getErrorKey(),
									error.getArgs()));
				}
				saveAppErrors(request, errors);

				return getActionForward(mapping, forwardName, retEntityId,
						retKeyId, false);
			}
		}
		catch(ISPACException e) {

			if (entityapp != null) {

				// Establecer la aplicación para acceder a los valores extra en el formulario
				defaultForm.setValuesExtra(entityapp);

				// Página jsp asociada a la presentación de la entidad
				request.setAttribute("application", entityapp.getURL());
				request.setAttribute("EntityId",Integer.toString(entityId));
				request.setAttribute("KeyId", Integer.toString(keyId));

				throw new ISPACInfo(e.getMessage());
			}
			else {
				// Suele producirse error en las secuencias al estar mal inicializadas
				// provocando una duplicación de keys
				throw e;
			}
		}
		finally {
			cct.endTX(bCommit);
		}

		return getActionForward(mapping, forwardName, retEntityId, retKeyId, true);
	}

	protected ActionForward getActionForward(ActionMapping mapping, String name,
			int entityId, int regId, boolean redirect) {

		ActionForward forward = mapping.findForward(name);
		String url = new StringBuffer(forward.getPath())
			.append("&entityId=").append(entityId)
			.append("&regId=").append(regId)
			.toString();
		return new ActionForward(forward.getName(), url, redirect);
	}
}