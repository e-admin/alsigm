package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;
import ieci.tdw.ispac.ispacmgr.menus.MenuFactory;
import ieci.tdw.ispac.ispacmgr.mgr.SchemeMgr;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IScheme;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;
import ieci.tdw.ispac.ispacweb.api.impl.states.SubProcessState;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowSubProcessAction extends BaseAction {

 	public ActionForward executeAction(ActionMapping mapping,
 									   ActionForm form,
 									   HttpServletRequest request,
 									   HttpServletResponse response,
 									   SessionAPI session) throws Exception {
 		
 		//TODO BPM. Cuando se realicen pruebas con otros BPM revisar si es necesario incluir aki las llamadas para adquirir expediente.
 		//Este Action se realizo despues de la adaptacion a varios BPM's, por lo que habra que estudiar como es afectado
		ClientContext cct = session.getClientContext();
		IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(cct);
		IInvesflowAPI invesFlowAPI = session.getAPI();
		// Se cambia el estado de tramitación
		IState state = null;
		Map params = request.getParameterMap();
		try {
			state = managerAPI.enterState(getStateticket(request),ManagerState.SUBPROCESS, params);
		}
    	catch (ISPACNullObject e) {
    		
    		// No se ha recuperado la actividad porque el subproceso ha cambiado de actividad
    		// o se ha introducido un id de actividad que no existe
    		ISPACInfo info = new ISPACInfo("exception.expedients.noActivity.numexp", new String[] {managerAPI.currentState(getStateticket(request)).getNumexp()});
    		
			// Redireccionar a la página principal con el mensaje de aviso
			request.setAttribute(Globals.MESSAGE_KEY, info);
			request.setAttribute("refresh", "/showProcedureList.do");
			return mapping.findForward("refresh");
    	}
    	
        //Cargamos los datos del esquema
    	IScheme scheme = SchemeMgr.loadScheme(mapping, request, session,state);
    	
        //Cargamos el expediente
        //SpacMgr.loadExpedient(session, state, request);
        //Cargamos enlaces para los expedientes relacionados
        //SpacMgr.loadRelatedExpedient(session, request, state.getNumexp(), SpacMgr.ALL_EXPEDIENTS );
        //Cargamos las aplicaciones de gestion asociadas
        //SpacMgr.loadAppGestion(session, state, request);
        
        if (!state.getReadonly()) {
        	//Eliminamos si existe el atributo que indica el usuario que tiene 
        	//bloqueado el expediente
            request.getSession(false).removeAttribute("userLock");
        }
        else {
            String username = ((SubProcessState)state).getLockedActivityUser(cct, state.getActivityId());
            //Insertamos el atributo que indica el usuario que tiene bloqueado 
            //el expediente
            request.getSession(false).setAttribute("userLock",username);            
        }

        //////////////////////////////////////////////////////////////////////
        // Formulario asociado a la acción
        EntityForm defaultForm = (EntityForm) form;
        EntityApp entityapp = null;
	    
		// Visualiza los datos de la entidad
        // Si hay errores no se recargan los datos de la entidad
        // manteniéndose los datos introducidos que generan los errores
	    if ( (request.getAttribute(Globals.ERROR_KEY) == null) 
	    		&& (request.getSession().getAttribute("infoAlert") == null)) {
	    	
	        String path = getRealPath("");
	        
	        try {
	        	// Obtener la aplicación que gestiona la entidad
	            entityapp = scheme.getDefaultEntityApp(state, path, true);
	        }
	        catch(ISPACNullObject e) {
	        	
	       		// Si no existe ningun registro creado para la entidad indicada en el estado
	        	// buscamos el EntityApp pasando un registro vacio (caso de Alta de entidad)
	        	entityapp = scheme.getEntityApp(state, state.getEntityId(), ISPACEntities.ENTITY_NULLREGKEYID, path, 0, true);
	        }
	    	
	    	// Limpiar el formulario
	    	defaultForm.reset();
	    	
	    	// Establecer los datos
	    	defaultForm.setEntityApp(entityapp, cct.getLocale());
	    }
	    
        // Introducimos como atributo el action utilizado como enlace para todas las entidades
   		ActionForward action = mapping.findForward("showsubprocess");
   		String urlExp = action.getPath();
   		String sep = "?";
	    
	    // Permite modificar los datos del formulario
	    defaultForm.setReadonly(Boolean.toString(state.getReadonly()));
	    if (state.getReadonly()) {
	    	
	    	// Expediente finalizado
	    	String readonly = request.getParameter(ManagerState.PARAM_READONLY);
	    	if ((readonly != null) &&
	    		(String.valueOf(ManagerState.READONLY_REASON_EXPEDIENT_CLOSED).equals(readonly))) {
	    		
	    		state.setReadonlyReason(Integer.parseInt(readonly));
	    		urlExp = urlExp + sep + ManagerState.PARAM_READONLY + "=" + readonly;
	    		sep = "&";
	    	}
	    	
    		defaultForm.setReadonlyReason(""+state.getReadonlyReason());
        	request.setAttribute(ActionsConstants.READONLYSTATE, ""+state.getReadonlyReason());
	    }
	    
   		request.setAttribute("urlExp", urlExp);
   		
   		// Y se mantiene la ordenación de las listas
   		String displayTagOrderParams = getDisplayTagOrderParams(request);	
   		if (!StringUtils.isEmpty(displayTagOrderParams)) {
   			urlExp = urlExp + sep + displayTagOrderParams;
   		}
   		request.setAttribute("urlExpDisplayTagOrderParams", urlExp);
   		request.setAttribute("displayTagOrderParams", displayTagOrderParams);

        // Se actualiza el estado de tramitación.
        storeStateticket(state, response);

        // Mandamos los parámetros entity y regentity
        request.setAttribute("entityid", Integer.toString(state.getEntityId()));
        request.setAttribute("entityregid", Integer.toString(state.getEntityRegId()));
        request.setAttribute("application", defaultForm.getEntityApp().getURL());
        
        // Pasamos el atributo del pcdId para la ayuda en linea
        request.setAttribute("pcdid", Integer.toString(state.getPcdId()));
        
        // Modificación de los documentos asociados a la fase o actividad activas
		request.setAttribute("stageId", Integer.toString(state.getStageId()));
        request.setAttribute("stagePcdId", Integer.toString(state.getStagePcdId()));
        request.setAttribute("activityPcdId", Integer.toString(state.getActivityPcdId()));
           		
        // enviamos un mapa con parámetros para los enlaces
        Map linkParams = new HashMap();
        linkParams.put("taskId", String.valueOf(state.getTaskId()));
        if (state.getActivityId() > 0) {
        	linkParams.put("activityId", String.valueOf(state.getActivityId()));
        }
        request.setAttribute("Params", linkParams);

   		// Si se recibe el parametro 'form' con el valor 'single'
        if (request.getParameter("form")!= null && request.getParameter("form").equals("single")) {
        	return mapping.findForward("singleSuccess");
        }
        
        // Para que no aparezcan los trámites en el menú
        request.setAttribute("isSubProcess", Boolean.TRUE);
        
        //Cargamos el contexto de la cabecera (miga de pan)
        SchemeMgr.loadContextHeader(state, request, getResources(request), session);
        
        //Comprobamos si el expedienteestá en la papelera
       
       IProcess process=invesFlowAPI.getProcess(state.getNumexp());
       if(process.getInt("ESTADO")==TXConstants.STATUS_DELETED){
    	   state.setReadonly(true); 
    	   state.setReadonlyReason(StateContext.READONLYREASON_DELETED_EXPEDIENT);
    	   defaultForm.setReadonly(Boolean.toString(state.getReadonly()));
    	   defaultForm.setReadonlyReason(""+state.getReadonlyReason());
    	   request.setAttribute(ActionsConstants.READONLYSTATE, ""+state.getReadonlyReason());
       }
        
        //Menús
        request.setAttribute("menus", MenuFactory.getActivityMenu(cct, state,  getResources(request),null));
    	
        return mapping.findForward("success");
	}
 	
}