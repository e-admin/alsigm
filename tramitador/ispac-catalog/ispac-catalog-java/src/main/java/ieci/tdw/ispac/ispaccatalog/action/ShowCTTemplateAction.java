package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.ITemplateAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaccatalog.action.form.UploadForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.app.EntityApp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ShowCTTemplateAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_TEMPLATES_READ,
				ISecurityAPI.FUNC_INV_TEMPLATES_EDIT });

		int entityId = 0;
		int keyId = 0;

		UploadForm defaultForm = (UploadForm) form;

		String parameter = request.getParameter("entityId");
		if (parameter == null) {
			parameter = defaultForm.getEntity();
		}
		entityId = Integer.parseInt(parameter);

		parameter = request.getParameter("regId");
		if (parameter == null) {
			parameter = defaultForm.getKey();
		}
		keyId = Integer.parseInt(parameter);

		defaultForm.setEntity(Integer.toString(entityId));
		defaultForm.setKey(Integer.toString(keyId));
		defaultForm.setReadonly("false");

		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

		EntityApp entityapp = catalogAPI.getCTDefaultEntityApp(entityId, keyId, getRealPath(""));
		if (keyId == ISPACEntities.ENTITY_NULLREGKEYID) {
			entityapp.getItem().setKey(ISPACEntities.ENTITY_NULLREGKEYID);
		}
		
		// Establecer el nombre del tipo de documento.
		if (entityapp.getItem() != null) {
			int docTypeId = entityapp.getItem().getInt("ID_TPDOC");
			if (docTypeId > 0) {
				IItem docTypeItem = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_CT_TYPEDOC, docTypeId);
				entityapp.setProperty("NOMBRE_TPDOC", docTypeItem.getString("NOMBRE"));
			}
		}
		
		// Visualiza los datos de la entidad
		if (defaultForm.getActions() == null || defaultForm.getActions().equals("success")) {
			defaultForm.setEntityApp(entityapp);
		}

		// Página jsp asociada a la presentación de la entidad
		request.setAttribute("application", entityapp.getURL());
		request.setAttribute("EntityId", Integer.toString(entityId));
		request.setAttribute("KeyId", Integer.toString(keyId));

		boolean isEspecifica = false;
		if (keyId > 0) {
			ITemplateAPI templateAPI = session.getAPI().getTemplateAPI();
			int numProc = templateAPI.countTemplatesProcedure(keyId);
			if (numProc > 0) {
				request.setAttribute("NUM_PROC", new Integer(numProc));
			}

			// calcular si es específica o genérica
			if (templateAPI.isProcedureTemplate(keyId)) {
				isEspecifica = true;
			}
		}

		if (isEspecifica) {
			entityapp.setProperty("PROCEDURE_TEMPLATE", getResources(request).getMessage("template.especifica"));
		} else {
			entityapp.setProperty("PROCEDURE_TEMPLATE", getResources(request).getMessage("template.generica"));
		}
		
		return mapping.findForward("success");
	}

}