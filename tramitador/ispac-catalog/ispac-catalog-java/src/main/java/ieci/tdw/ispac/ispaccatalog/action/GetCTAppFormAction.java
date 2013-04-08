package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.app.EntityParameterDef;
import ieci.tdw.ispac.ispaclib.app.ParametersDef;
import ieci.tdw.ispac.ispaclib.builders.HTMLBuilder;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GetCTAppFormAction extends BaseAction {

 	public ActionForward executeAction(ActionMapping mapping,
 									   ActionForm form,
 									   HttpServletRequest request,
 									   HttpServletResponse response,
 									   SessionAPI session) throws Exception {
 		
 		ClientContext cct = session.getClientContext();

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, cct, new int[] {
				ISecurityAPI.FUNC_COMP_ENTITIES_READ,
				ISecurityAPI.FUNC_COMP_ENTITIES_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
		
 		// Obtener la aplicación
 		String id = request.getParameter("appId");
 		
 		IItem application = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_CT_APP, Integer.parseInt(id));
		if (application != null) {
			
			int entityId = application.getInt("ENT_PRINCIPAL_ID");
			String entityName = application.getString("ENT_PRINCIPAL_NOMBRE");
			
			// Obtener el formulario JSP de la aplicación
			String frm_jsp = application.getString("FRM_JSP");
			if (StringUtils.isNotBlank(frm_jsp)) {
				
				String fileName = null;
				String fileData = null;
			
				// Obtener el formulario JSP
		 		String unload = request.getParameter("unload");
		 		if ((!StringUtils.isEmpty(unload)) &&
		 			(unload.equals("code"))) {
		 			
					// Nombre del fichero a descargar
					fileName = getMessage(request, cct.getLocale(), "entityManage.form.unload.code.fileName");
					if (StringUtils.isEmpty(fileName)) {
						fileName = "form.jsp";
					}
					
					fileData = frm_jsp;
		 		}
		 		// Obtener el diseño
		 		else {
		 			
		 			String appLanguage = cct.getAppLanguage();
		 			
		 			List entities = new ArrayList();
		 			entities.add(entityName);
				
					// Obtener los recursos de la entidad principal
					Map resources = new HashMap();
					IItemCollection collection = entitiesAPI.getEntityResources(entityId, appLanguage);
			    	while (collection.next()) {
			    		
			    		IItem resource = collection.value();
			    		resources.put(entityName + ":" + resource.getString("CLAVE"), resource.getString("VALOR"));
			    	}
			    	
			    	// Obtener los recursos de las entidades de composición y de relación múltiple
		 			String parameters = application.getString("PARAMETROS");
		 			if (!StringUtils.isEmpty(parameters)) {
		 			
		 				ParametersDef xmlParameters = ParametersDef.parseParametersDef(parameters);
		 				List compositeEntities = xmlParameters.getCompositeMultipleRelationEntities();
		 				Iterator it = compositeEntities.iterator();
		 				while (it.hasNext()) {
		 					
		 					EntityParameterDef entityParameterDef = (EntityParameterDef) it.next();
		 					IItem entity = entitiesAPI.getCatalogEntity(entityParameterDef.getTable());
		 					
		 					// Añadir los formularios de las entidades de composición y de relación múltiple
		 					entityName = entity.getString("NOMBRE");
		 					entities.add(entityName);
		 					collection = entitiesAPI.getEntityResources(entity.getKeyInt(), appLanguage);
		 			    	while (collection.next()) {
		 			    		
		 			    		IItem resource = collection.value();
		 			    		resources.put(entityName + ":" + resource.getString("CLAVE"), resource.getString("VALOR"));
		 			    	}
		 				}
		 			}
					
					String htmlCode = HTMLBuilder.generateHTML(frm_jsp, entities, resources);
					
					// Nombre del fichero a descargar
					fileName = getMessage(request, cct.getLocale(), "entityManage.form.unload.design.fileName");
					if (StringUtils.isEmpty(fileName)) {
						fileName = "form.html";
					}
					
					fileData = htmlCode;
		 		}
		 		
		 		// Descargar el formulario JSP o el diseño
		 		ServletOutputStream outputStream = response.getOutputStream();
		 		
		    	response.setHeader("Pragma", "public");
		    	response.setHeader("Cache-Control", "max-age=0");
				response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
				response.setContentLength(fileData.length());
				
				outputStream.print(fileData);
				outputStream.flush();
				outputStream.close();
			}
			else {
				 throw new ISPACInfo("exception.entities.form.empty");
			}
		}

		return null;
	}
 	
}