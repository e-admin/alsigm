package ieci.tdw.ispac.ispaccatalog.action.publisher;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaccatalog.breadcrumbs.BreadCrumbsContainer;
import ieci.tdw.ispac.ispaccatalog.breadcrumbs.BreadCrumbsManager;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.manager.ISPACRewrite;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ShowPubRuleAction extends BaseAction {

 	public ActionForward executeAction(ActionMapping mapping,
 									   ActionForm form,
 									   HttpServletRequest request,
 									   HttpServletResponse response,
 									   SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_PUB_RULES_READ,
				ISecurityAPI.FUNC_PUB_RULES_EDIT });

		EntityForm defaultForm = (EntityForm) form;

		String parameter = request.getParameter("entityId");
 		if (parameter == null) {
 			parameter = defaultForm.getEntity();
 		}
 		int entityId = Integer.parseInt(parameter);

		parameter = request.getParameter("regId");
 		if (parameter == null) {
 			parameter = defaultForm.getKey();
 		}
 		int keyId = Integer.parseInt(parameter);

 		ClientContext cct = session.getClientContext();
		IInvesflowAPI invesFlowAPI = session.getAPI();
        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

        String strURL = null;
		EntityApp entityapp = null;

        try {
            entityapp = catalogAPI.getCTDefaultEntityApp(entityId, keyId, getRealPath(""));

            //Si se debe crear una entrada cuando se guarden los datos del registro,
    		//se marca debidamente el campo clave del registro como nulo.
    		if (keyId==ISPACEntities.ENTITY_NULLREGKEYID) {
    		    entityapp.getItem().setKey(ISPACEntities.ENTITY_NULLREGKEYID);
    		}
        }
        catch (ISPACNullObject e) {

            ISPACRewrite ispacPath = new ISPACRewrite(getServlet().getServletContext());
            strURL = ispacPath.rewriteRelativePath("common/missingapp.jsp");
        }
        catch (ISPACException eie){

        	throw new ISPACInfo(eie.getMessage());
        }

        if (strURL == null) {
            strURL = entityapp.getURL();
        }

		// Visualiza los datos de la entidad
        // Si hay errores no se recargan los datos de la entidad
        // manteniéndose los datos introducidos que generan los errores
		if (((defaultForm.getActions() == null) ||
		     (defaultForm.getActions().equals("success"))) &&
		     (request.getAttribute(Globals.ERROR_KEY) == null)) {

			defaultForm.setEntity(Integer.toString(entityId));
			defaultForm.setKey(Integer.toString(keyId));
			defaultForm.setReadonly("false");

			defaultForm.setEntityApp(entityapp);
		}
		else {
			// Establecer la aplicación para acceder a los valores extra en el formulario
			defaultForm.setValuesExtra(entityapp);
		}

		// Página jsp asociada a la presentación de la entidad
		request.setAttribute("application", strURL);
		request.setAttribute("EntityId",Integer.toString(entityId));
		request.setAttribute("KeyId", Integer.toString(keyId));

		// Generamos la ruta de navegación hasta la pantalla actual.
		BreadCrumbsContainer bcm = BreadCrumbsManager.getInstance(catalogAPI).getBreadCrumbs(request);
 		request.setAttribute("BreadCrumbs", bcm.getList());

 		String pcdId = request.getParameter("pcdId");
 		String stageId = request.getParameter("stageId");
 		String taskId = request.getParameter("taskId");
 		String typeDoc = request.getParameter("typeDoc");

 		if (keyId == -1) {

 			// Descripción del procedimiento
			if (!StringUtils.isEmpty(pcdId)) {
		   	 	if (pcdId.equals("-1")) {
		   	 		defaultForm.setProperty("SPAC_P_PROCEDIMIENTOS:NOMBRE", getMessage(request, cct.getLocale(), "form.pubRule.msg.all"));
		   	 	}
		   	 	else {
			   	 	IItem procedure = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_PROCEDURE, Integer.parseInt(pcdId));
			   	 	defaultForm.setProperty("SPAC_P_PROCEDIMIENTOS:NOMBRE", procedure.getString("NOMBRE"));
			   	 	defaultForm.setProperty("SPAC_P_PROCEDIMIENTOS:NVERSION", procedure.getString("NVERSION"));
			   	 	defaultForm.setProperty("SPAC_P_PROCEDIMIENTOS:ESTADO", procedure.getString("ESTADO"));
			   	 	defaultForm.setProperty("SPAC_P_PROCEDIMIENTOS:ID_GROUP", procedure.getString("ID_GROUP"));
		   	 	}
		   	 	defaultForm.setProperty("PUB_REGLAS:ID_PCD", pcdId);
			}

	   	 	// Descripción de la fase
			if (!StringUtils.isEmpty(stageId)) {
		   	 	if (stageId.equals("-1")) {
		   	 		defaultForm.setProperty("SPAC_P_FASES:NOMBRE", getMessage(request, cct.getLocale(), "form.pubRule.msg.all.a"));
		   	 	}
		   	 	else if (!stageId.equals("0")) {
			   	 	IItem stage = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_STAGE, Integer.parseInt(stageId));
			   	 	defaultForm.setProperty("SPAC_P_FASES:NOMBRE", stage.getString("NOMBRE"));
		   	 	}
		   	 	defaultForm.setProperty("PUB_REGLAS:ID_FASE", stageId);
			}

	   	 	// Descripción del trámite
			if (!StringUtils.isEmpty(taskId)) {
		   	 	if (taskId.equals("-1")) {
		   	 		defaultForm.setProperty("SPAC_P_TRAMITES:NOMBRE", getMessage(request, cct.getLocale(), "form.pubRule.msg.all"));
		   	 	}
		   	 	else if (!taskId.equals("0")) {
				 	IItem task = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_TASK, Integer.parseInt(taskId));
				 	defaultForm.setProperty("SPAC_P_TRAMITES:NOMBRE", task.getString("NOMBRE"));
		   	 	}
		   	 	defaultForm.setProperty("PUB_REGLAS:ID_TRAMITE", taskId);
			}

	   	 	// Descripción del tipo de documento
			if (!StringUtils.isEmpty(typeDoc)) {
		   	 	if (typeDoc.equals("-1")) {
		   	 		defaultForm.setProperty("SPAC_CT_TPDOC:NOMBRE", getMessage(request, cct.getLocale(), "form.pubRule.msg.all"));
		   	 	}
		   	 	else if (!typeDoc.equals("0")) {
				 	IItem docType = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_CT_TYPEDOC, Integer.parseInt(typeDoc));
				 	defaultForm.setProperty("SPAC_CT_TPDOC:NOMBRE", docType.getString("NOMBRE"));
		   	 	}
		   	 	defaultForm.setProperty("PUB_REGLAS:TIPO_DOC", typeDoc);
			}
 		}

		request.setAttribute("pcdId", pcdId);
		request.setAttribute("stageId", stageId);
		request.setAttribute("taskId", taskId);
		request.setAttribute("typeDoc", typeDoc);

		return mapping.findForward("success");
	}

}