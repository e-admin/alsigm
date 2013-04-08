package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaccatalog.breadcrumbs.BreadCrumbsContainer;
import ieci.tdw.ispac.ispaccatalog.breadcrumbs.BreadCrumbsManager;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispacweb.manager.ISPACRewrite;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ShowCTEntityAction extends BaseAction {

	
 	public ActionForward executeAction(ActionMapping mapping,
 									   ActionForm form,
 									   HttpServletRequest request,
 									   HttpServletResponse response,
 									   SessionAPI session) throws Exception {

		EntityForm defaultForm = (EntityForm) form;

		String parameter = request.getParameter("entityId");
 		if (parameter == null) {
 			parameter = defaultForm.getEntity();
 		}
 		int entityId = Integer.parseInt(parameter);

		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkReadOnlyFunctions(request, session.getClientContext(), entityId);

		parameter = request.getParameter("regId");
 		if (parameter == null) {
 			parameter = defaultForm.getKey();
 		}
 		int keyId = Integer.parseInt(parameter);

		IInvesflowAPI invesFlowAPI = session.getAPI();
        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

        String strURL = null;
		EntityApp entityapp = null;
//		 try {
	            entityapp = catalogAPI.getCTDefaultEntityApp(entityId, keyId, getRealPath(""));
//		 }catch (Exception e) {
//			 try{
//			 entityapp = invesFlowAPI.getEntitiesAPI().getEntityApp(null, 11, entityId, keyId, getRealPath(""));
//			 }catch (Exception e1) {
//				 entityapp = null;
//			}
//			 //entityapp = catalogAPI.getCTDefaultEntityApp(entityId, keyId, getRealPath(""));
//		 }
		 
        try {
            //entityapp = catalogAPI.getCTDefaultEntityApp(entityId, keyId, getRealPath(""));
        	if (entityapp==null)
        		throw new ISPACNullObject();
            //Si se debe crear una entrada cuando se guarden los datos del registro,
    		//se marca debidamente el campo clave del registro como nulo.
    		if (keyId==ISPACEntities.ENTITY_NULLREGKEYID) {
    		    entityapp.getItem().setKey(ISPACEntities.ENTITY_NULLREGKEYID);
    		}
        }catch (ISPACNullObject e) {
        	
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

		
		//En caso de que hubiera errores almacenados en la session se pasan a la request 
		//para que sean presentandos y se eliminan de la sesión
		if(request.getSession().getAttribute(BaseAction.APPLICATION_ERRORS)!=null){ 
			request.setAttribute(BaseAction.APPLICATION_ERRORS, request.getSession().getAttribute(BaseAction.APPLICATION_ERRORS));
			request.getSession().removeAttribute(BaseAction.APPLICATION_ERRORS);
		}
		
		// Página jsp asociada a la presentación de la entidad
		request.setAttribute("application", strURL);
		request.setAttribute("EntityId",Integer.toString(entityId));
		request.setAttribute("KeyId", Integer.toString(keyId));

		ClientContext cct=session.getClientContext();
		// Generamos la ruta de navegación hasta la pantalla actual.
		BreadCrumbsContainer bcm = BreadCrumbsManager.getInstance(catalogAPI).getBreadCrumbs(request);
 		request.setAttribute("BreadCrumbs", bcm.getList());
 		
 		// Idiomas soportados
    	request.setAttribute("languages", 
    						ConfigurationMgr.getLanguages(cct, getResources(request)));

		return mapping.findForward("success");
	}
 	
}