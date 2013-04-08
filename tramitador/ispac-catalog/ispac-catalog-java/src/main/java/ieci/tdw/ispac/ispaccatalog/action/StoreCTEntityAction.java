package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.Property;
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

public class StoreCTEntityAction extends BaseAction
{
	public ActionForward executeAction(ActionMapping mapping,
									   ActionForm form,
									   HttpServletRequest request,
									   HttpServletResponse response,
									   SessionAPI session) throws Exception	{
		
		ClientContext cct = session.getClientContext();
		
		IInvesflowAPI invesFlowAPI = session.getAPI();
        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

		// Formulario asociado a la acción
		EntityForm defaultForm = (EntityForm) form;

		int keyId = Integer.parseInt(defaultForm.getKey());
		int entityId = Integer.parseInt(defaultForm.getEntity());
		
		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkEditFunctions(request, session.getClientContext(), entityId);

		EntityApp entityapp = null;
		String path = getRealPath("");
		
		// Ejecución en un contexto transaccional
		boolean bCommit = false;
		
		try {
	        // Abrir transacción
	        cct.beginTX();
	        boolean nuevo=false;
	        // Obtener la aplicación que gestiona la entidad
			if (keyId == ISPACEntities.ENTITY_NULLREGKEYID) {
				nuevo=true;
			    entityapp = catalogAPI.newCTDefaultEntityApp(entityId, path);
			    keyId  = entityapp.getEntityRegId();
			  //Recorro todas las propiedas que pueda tener ahora el entityapp
				Property[] propiedades= (Property[]) entityapp.getItem().getProperties().getPropertyArray();
				for(int i=0; i<propiedades.length; i++){
					Property auxi= propiedades[i];
					if(StringUtils.isNotBlank(entityapp.getString(auxi.getName()))) {
						defaultForm.setProperty(auxi.getName(), entityapp.getValue(auxi.getName()));
					}
				}
			}
			else {
			   // entityapp = catalogAPI.getCTDefaultEntityApp(entityId, path);
				entityapp=catalogAPI.getCTDefaultEntityApp(entityId, keyId, path);
			}
			/*//Recorro todas las propiedas que pueda tener ahora el entityapp
			Property[] propiedades= (Property[]) entityapp.getItem().getProperties().getPropertyArray();
			for(int i=0; i<propiedades.length; i++){
				Property auxi= propiedades[i];
				if(StringUtils.isNotBlank(entityapp.getString(auxi.getName()))) {
					defaultForm.setProperty(auxi.getName(), entityapp.getValue(auxi.getName()));
				}
			}*/
			// Permite modificar los datos del formulario
			defaultForm.setReadonly("false");
			// Salva el identificador de la entidad
			defaultForm.setEntity(Integer.toString(entityId));
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
				if(nuevo){
					keyId=ISPACEntities.ENTITY_NULLREGKEYID;
				}
				request.getSession().setAttribute(BaseAction.APPLICATION_ERRORS, errors);
				// Página jsp asociada a la presentación de la entidad
				return new ActionForward(mapping.getInput(),   mapping.getInput() + "?entityId=" + entityId + "&regId=" + keyId,true);
			}

			// Guardar la entidad
			entityapp.store();
			// Salva el identificador del registro
			defaultForm.setKey(Integer.toString(keyId));
			
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

		ActionForward forward = mapping.findForward("ShowEntity" + entityId);
		if (forward == null) {
		    forward = mapping.findForward("reloadShowEntity" + entityId);
		    if (forward == null) {
		    	forward = mapping.findForward("reload");
		    }

		}
	    String redirected = forward.getPath() + "?entityId=" + entityId + "&regId=" + keyId;
	    if (request.getQueryString() != null) {
	    	redirected += "&" +request.getQueryString();
	    }
	    forward = new ActionForward( forward.getName(), redirected, true);

		return forward;
	}
	}