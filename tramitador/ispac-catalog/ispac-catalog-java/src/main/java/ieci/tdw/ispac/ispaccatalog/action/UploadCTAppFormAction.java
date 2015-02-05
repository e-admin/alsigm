package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.form.Upload2Form;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.app.EntityParameterDef;
import ieci.tdw.ispac.ispaclib.app.ParametersDef;
import ieci.tdw.ispac.ispaclib.builders.HTMLBuilder;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class UploadCTAppFormAction extends BaseAction {
    
	public ActionForward executeAction(ActionMapping mapping, 
									   ActionForm form, 
									   HttpServletRequest request,
									   HttpServletResponse response, 
									   SessionAPI session) throws Exception {
		
		ClientContext cct = session.getClientContext();
        
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_ENTITIES_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();
		
    	Upload2Form upload2Form = (Upload2Form) form;
		String upload = request.getParameter("upload");
    	
    	try {
    		
    		if (upload.equals("design")) {

    			FormFile formfile = upload2Form.getUploadFile();
    			String htmlCode = new String(formfile.getFileData());
            
	            if (!StringUtils.isEmpty(htmlCode)) {
	            
		     		// Obtener el formulario JSP de la aplicación
	         		IItem application = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_CT_APP, Integer.parseInt(upload2Form.getKey()));
	        		if (application != null) {
	        			
			 			List entities = new ArrayList();
			 			
			 			// Entidad principal
			 			entities.add(application.getString("ENT_PRINCIPAL_NOMBRE"));
			 			
			        	// Entidades de composición y de relación múltiple
			 			String parameters = application.getString("PARAMETROS");
			 			if (!StringUtils.isEmpty(parameters)) {
			 			
			 				ParametersDef xmlParameters = ParametersDef.parseParametersDef(parameters);
			 				List compositeEntities = xmlParameters.getCompositeMultipleRelationEntities();
			 				Iterator it = compositeEntities.iterator();
			 				while (it.hasNext()) {
			 					
			 					EntityParameterDef entityParameterDef = (EntityParameterDef) it.next();
			 					entities.add(entityParameterDef.getTable());
			 				}
			 			}
		    			
			 			String frm_jsp = application.getString("FRM_JSP");
		    			String jspCode = HTMLBuilder.updateHTML(htmlCode, frm_jsp, entities);
		    			if (jspCode != null) {
		    				
		    				application.set("FRM_JSP", jspCode);
			    			
			    			// Incrementar la versión del formulario
		    				int version = 1;
		    				String sVersion = application.getString("FRM_VERSION");
		    				if (!StringUtils.isEmpty(sVersion)) {
		    					version += Integer.parseInt(sVersion);
		    				}
			    			application.set("FRM_VERSION", version);
			    			
			    			application.store(cct);
		    			}
		    		}
	            }
	            else {
	            	throw new ISPACInfo("exception.uploadfile.empty");
	            }
    		}
    		else if (upload.equals("code")) {
    			
    			FormFile formfile2 = upload2Form.getUploadFile2();
    			String jspCode = new String(formfile2.getFileData());
    			
	            if (!StringUtils.isEmpty(jspCode)) {
		            
		     		// Obtener la aplicación
		    		IItemCollection itemcol = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_APP, "where ID =" + upload2Form.getKey());
		    		if (itemcol.next()) {
		    			
		    			IItem application = itemcol.value();
		    			application.set("FRM_JSP", jspCode);
		    			
		    			// Incrementar la versión del formulario
	    				int version = 1;
	    				String sVersion = application.getString("FRM_VERSION");
	    				if (!StringUtils.isEmpty(sVersion)) {
	    					version += Integer.parseInt(sVersion);
	    				}
		    			application.set("FRM_VERSION", version);
		    			
		    			application.store(cct);
		    		}
	            }
	            else {
	            	throw new ISPACInfo("exception.uploadfile.empty");
	            }
    		}
        }
        catch (IOException e) {
            throw new ISPACInfo("exception.uploadfile.error");
        }

        return mapping.findForward("success");
        
        /*
        request.setAttribute("refresh", "true");
        return mapping.findForward("success_upload");
        */
    }
	
}