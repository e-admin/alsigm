package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.bean.ValidationError;
import ieci.tdw.ispac.ispaclib.builders.JSPBuilder;
import ieci.tdw.ispac.ispaclib.context.ClientContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class StoreCTAppAction extends BaseAction
{
	public ActionForward executeAction(ActionMapping mapping,
									   ActionForm form,
									   HttpServletRequest request,
									   HttpServletResponse response,
									   SessionAPI session) throws Exception	{
		
		ClientContext cct = session.getClientContext();
		
		IInvesflowAPI invesFlowAPI = session.getAPI();
        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
        IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
        
		// Formulario asociado a la acción
		EntityForm defaultForm = (EntityForm) form;

		int keyId = Integer.parseInt(defaultForm.getKey());
		int entityId = Integer.parseInt(defaultForm.getEntity());

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
			}
			else {
			    entityapp = catalogAPI.getCTDefaultEntityApp(entityId, path);
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
	
			if (!entityapp.validate()) {
				
				ActionMessages errors = new ActionMessages();
				List errorList = entityapp.getErrors();
				Iterator iteError = errorList.iterator();
				while (iteError.hasNext()) {
					
					ValidationError validError = (ValidationError) iteError.next();
					ActionMessage error = new ActionMessage(validError.getErrorKey(), validError.getArgs());
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				}
				saveAppErrors(request, errors);
				
				return new ActionForward(mapping.getInput());
			}
			
			IItem application = entityapp.getItem();
			IItem entity = entitiesAPI.getCatalogEntity(application.getInt("ENT_PRINCIPAL_ID"));
			
			// Generar el JSP del formulario
			List entities = new ArrayList();
			entities.add(entity.getString("NOMBRE"));
			List dataBlocks = new ArrayList();
			dataBlocks.add(entity.getString("FRM_JSP"));
			String jspCode = JSPBuilder.generateTabsDataBlocks(entities, dataBlocks,false);
			
			application.set("FRM_JSP", jspCode);
			
			// Establecer/incrementar la versión del formulario
			if (application.getString("FRM_VERSION") == null) {
				application.set("FRM_VERSION", 1);
			}
			else {
				int version = application.getInt("FRM_VERSION") + 1;
				application.set("FRM_VERSION", version);
			}

			// Guardar la entidad
			entityapp.store();
			
			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
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
		
		/*
		ActionForward forward = mapping.findForward("finalize");
	    //String redirected = forward.getPath() + "?entityId=" + entityId + "&regId=" + keyId;
		//forward = new ActionForward( forward.getName(), redirected, true);

		return forward;
		*/
		
		ActionForward forward = mapping.findForward("ShowEntity" + entityId);
		if (forward == null) {
			
		    forward = mapping.findForward("reloadShowEntity" + entityId);
		    if (forward == null) {
		    	forward = mapping.findForward("reload");
		    }

		    String redirected = forward.getPath() + "?entityId=" + entityId + "&regId=" + keyId;
		    forward = new ActionForward( forward.getName(), redirected, true);
		}

		return forward;
	}
	
}