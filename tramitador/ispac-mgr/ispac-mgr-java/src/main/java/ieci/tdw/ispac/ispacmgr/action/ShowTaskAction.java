package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IBPMAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.api.item.ITask;
import ieci.tdw.ispac.api.item.PropName;
import ieci.tdw.ispac.audit.IspacAuditConstants;
import ieci.tdw.ispac.audit.business.IspacAuditoriaManager;
import ieci.tdw.ispac.audit.business.manager.impl.IspacAuditoriaManagerImpl;
import ieci.tdw.ispac.audit.business.vo.AuditContext;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventTramiteConsultaVO;
import ieci.tdw.ispac.audit.context.AuditContextHolder;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.common.constants.DocumentLockStates;
import ieci.tdw.ispac.ispaclib.common.constants.SignStatesConstants;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispaclib.sign.portafirmas.ProcessSignConnectorFactory;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;
import ieci.tdw.ispac.ispacmgr.action.form.SearchForm;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;
import ieci.tdw.ispac.ispacmgr.menus.MenuFactory;
import ieci.tdw.ispac.ispacmgr.mgr.SchemeMgr;
import ieci.tdw.ispac.ispacmgr.mgr.SpacMgr;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IScheme;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.IWorklist;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowTaskAction extends BaseAction
{
	IspacAuditoriaManager auditoriaManager = new IspacAuditoriaManagerImpl();

	public ActionForward executeAction(ActionMapping mapping,
									   ActionForm form,
									   HttpServletRequest request,
									   HttpServletResponse response,
									   SessionAPI session) throws Exception {

		ClientContext cct = session.getClientContext();

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);

    	//----
    	//BPM
    	//----
    	//Se adquiere el expediente
    	IBPMAPI bpmAPI = null;
    	boolean commit = true;
    	boolean inTrash=false;
    	
    	//TODO: Auditoría: Añadir en el ThreadLocal el objeto AuditContext.
		AuditContext auditContext = new AuditContext();
		auditContext.setUserHost(request.getRemoteHost());
		auditContext.setUserIP(request.getRemoteAddr());
		auditContext.setUser(session.getUserName());
		auditContext.setUserId(session.getClientContext().getUser().getUID());		
		AuditContextHolder.setAuditContext(auditContext);
    	try{
    		bpmAPI = invesFlowAPI.getBPMAPI();
			//Iniciamos la sesion con el BPM
			bpmAPI.initBPMSession();

			if(ProcessSignConnectorFactory.getInstance().isDefaultConnector()){
				request.getSession().setAttribute("defaultPortafirmas", true);
			}


			
			String taskIdParam = request
					.getParameter(ManagerState.PARAM_TASKID);
			int taskId = Integer.parseInt(taskIdParam);
			//Obtenemos el identificador de la fase instanciada a la que pertenece el tramite
			ITask task =invesFlowAPI.getTask(taskId);
			if(task.getInt("ESTADO")!=TXConstants.STATUS_DELETED){
				//IItem item = itemcol.value();
				int stageId = task.getInt("ID_FASE_EXP");
				IStage stage = invesFlowAPI.getStage(stageId);

				//Se invoca al BPM para adquirir la fase del expediente, si es necesario
				bpmAPI.acquireStage(stage.getString("ID_FASE_BPM"), cct.getRespId());
				
				String numExpediente = task.getString(PropName.TRM_NUMEXP);
				
				//Auditar la consulta del trámite
				this.auditConsultaTramite(taskId, numExpediente, cct);
			}
			else{
				inTrash=true;
			}

		}catch(ISPACNullObject e){
			commit = false;
		}
		catch(ISPACException e){
			commit = false;
			throw e;
		}finally{
			if (bpmAPI != null)
				bpmAPI.closeBPMSession(commit);
		}

		// Se cambia el estado de tramitación
		IState state = null;
		Map params = request.getParameterMap();
		try {
			state = managerAPI.enterState(getStateticket(request), ManagerState.TASK, params);
		}
    	catch (ISPACNullObject e) {

			// Redireccionar a la página principal
			return mapping.findForward("showmain");
    	}

		//[ildfns]Como este action ('showTask') es llamado tanto para mostrar
    	//un Tramite como un documento de un tramite, comprobamos si vamos a
    	//mostrar un tramite. Esto sera cuando en el estado el 'entityId == 0'
    	//o sea el valor de la entidad de tramites
    	/*
		if ( (state.getEntityId() == 0)
				|| (state.getEntityId() == SpacEntities.SPAC_DT_TRAMITES) ){

			//Comprobaciones al abrir un tramite
		    List list = selectEditTaskDocEntities(cct, state.getTaskPcdId(),
		    		state.getTaskId());

		    //Como retorno podemos obtener el identificador del documento a
		    //mostrar. Si tenemos algun elemento de retorno...
		    if (list!= null && list.size() != 0 && list.get(0) != null) {

		        //Cambiamos el estado de tramitación, ya que vamos a situarnos
		    	//en un documento para lo cual pasamos el identificador de la
		    	//entidad de documentos (entity), y el campo clave del documento
		    	//(key)
		        Map _params = new LinkedHashMap(params);
		        _params.put("entity", new String[] {
		        		""+SpacEntities.SPAC_DT_DOCUMENTOS});
		        _params.put("key", new String[] {""+list.get(0)});

		        //Cambiamos el estado ya que ahora nos situamos en un documento
		        //TODO: No sería mejor crear un estado State.DOCUMENT para situarnos en un documento???
		        state = managerAPI.enterState(getStateticket(request),
		        		ManagerState.TASK,_params);

		    } else {
		        state.setEntityId(SpacEntities.SPAC_DT_TRAMITES);
		        IItemCollection col = entitiesAPI.queryEntities(
		        		SpacEntities.SPAC_DT_TRAMITES,
		        		"WHERE ID_TRAM_EXP = " + state.getTaskId());
		        if (col.next()) {
		            state.setEntityRegId(col.value().getKeyInt());
		        }
		    }
		}

		//Documentos creados para un tramite
        IItemCollection itemCollection = entitiesAPI.queryEntities(
        		ISPACEntities.DT_ID_DOCUMENTOS,
        		"WHERE ID_TRAMITE = " + state.getTaskId());
        request.setAttribute("docList",
        		CollectionBean.getBeanList(itemCollection));
        */

    	if (state.getEntityId() == 0) {
	        state.setEntityId(SpacEntities.SPAC_DT_TRAMITES);

		    if (state.getEntityRegId() == 0) {
		        IItemCollection col = entitiesAPI.queryEntities(SpacEntities.SPAC_DT_TRAMITES, "WHERE ID_TRAM_EXP = " + state.getTaskId());
		        if (col.next())
		            state.setEntityRegId(col.value().getKeyInt());
		    }
    	}

    	// Expediente finalizado
    	String readonly = request.getParameter(ManagerState.PARAM_READONLY);
    	if ((readonly != null) &&
	    	(String.valueOf(ManagerState.READONLY_REASON_EXPEDIENT_CLOSED).equals(readonly))) {

    		state.setReadonlyReason(Integer.parseInt(readonly));
    	}
    	else if(inTrash){
    		state.setReadonly(true);
    		state.setReadonlyReason(StateContext.READONLYREASON_DELETED_EXPEDIENT);
    	}

        //Cargamos los datos del esquema
		IScheme scheme = SchemeMgr.loadScheme(mapping, request, session,state);

        //[ildfns] Cargamos el expediente
        //SpacMgr.loadExpedient(session, state, request);

		//////////////////////////////////////////////////////////////////////
		// Formulario asociado a la acción
		EntityForm defaultForm = (EntityForm) form;
		EntityApp entityapp = null;

		// Comprobar si hay que borrar los documentos marcados en la lista de documentos del trámite
		deleteDocuments(request, defaultForm, entitiesAPI);

		// Visualiza los datos de la entidad
        // Si hay errores no se recargan los datos de la entidad
        // manteniéndose los datos introducidos que generan los errores
		/*
	    if (((defaultForm.getActions() == null) ||
	    	 (defaultForm.getActions().equals("success"))) &&
	    	 (request.getAttribute(Globals.ERROR_KEY) == null)) {
	    */

		// No utilizar el formulario por defecto
		// (para ver los documentos del expediente en el trámite)
		boolean noDefault = false;
		if (request.getParameter("nodefault")!= null && request.getParameter("nodefault").equals("true")) {
			noDefault = true;
		}

	    if ( (request.getAttribute(Globals.ERROR_KEY) == null)
	    		&& (request.getSession().getAttribute("infoAlert") == null)) {

	        String path = getRealPath("");

	        try {
	        	// Obtener la aplicación que gestiona la entidad
	            entityapp = scheme.getDefaultEntityApp(state, path, noDefault);
	        }
	        catch(ISPACNullObject e) {

	       		// Si no existe ningun registro creado para la entidad indicada en el estado
	        	// buscamos el EntityApp pasando un registro vacio (caso de Alta de entidad)
	        	entityapp = scheme.getEntityApp(state, state.getEntityId(), ISPACEntities.ENTITY_NULLREGKEYID, path, 0, noDefault);
	        }

	    	// Limpiar el formulario
	    	defaultForm.reset();

	    	// Establecer los datos
	    	defaultForm.setEntityApp(entityapp, cct.getLocale());
	    }

		// Permite modificar los datos del formulario
		defaultForm.setReadonly(Boolean.toString(state.getReadonly()));
	    if (state.getReadonly()) {

	    	// Expediente finalizado
	    	if ((readonly != null) &&
	    	    (String.valueOf(ManagerState.READONLY_REASON_EXPEDIENT_CLOSED).equals(readonly))) {

	    		state.setReadonlyReason(Integer.parseInt(readonly));
	    	}

    		defaultForm.setReadonlyReason(""+state.getReadonlyReason());
        	request.setAttribute(ActionsConstants.READONLYSTATE, ""+state.getReadonlyReason());
	    }

		//enviamos un map con parámetros para el enlace de los hitos
		Map linkParams = new HashMap();

		//Cambiamos el parámetro a mandar, poniendo ahora 'stageId'
		//que es utilizado para los enlaces de los 'tabs'
		linkParams.put("taskId", String.valueOf(state.getTaskId()));
		//linkParams.put("stageId", String.valueOf(state.getStageId()));
		linkParams.put("numexp", state.getNumexp());

		request.setAttribute("Params",linkParams);

		// Atributo utilizado en actionsTask.jsp
		// Mandamos si el tramite tiene documentos con circuito de firma pendiente
		String querycount = "WHERE ID_TRAMITE = " + state.getTaskId() + " AND ESTADOFIRMA = '" + SignStatesConstants.PENDIENTE_CIRCUITO_FIRMA + "'";

		String existCircuit = "false";
		if (entitiesAPI.countEntities(SpacEntities.SPAC_DT_DOCUMENTOS, querycount) > 0)
			existCircuit = "true";

		request.setAttribute("existCircuit", existCircuit);

		//Atributos utilizados en el schemeTabs.jsp
		//mandamos los parámetros entity y regentity
		request.setAttribute("entityid", Integer.toString(state.getEntityId()));
		request.setAttribute("entityregid", Integer.toString(state.getEntityRegId()));
		String stageId=state.getStageId()+"";
		String stagePcdId=request.getParameter("stagePcdIdActual");

		if (state.getStageId() != 0) {
			request.getSession().setAttribute("stageId", Integer.toString(state.getStageId()));
		}
		else {
			stageId=(String) request.getSession().getAttribute("stageId");
		}

		if(StringUtils.isEmpty(stagePcdId)){
			stagePcdId=(String) request.getSession().getAttribute("stagePcdIdActual");
		}
		else{
			request.getSession().setAttribute("stagePcdIdActual", stagePcdId);
		}



		request.setAttribute("stagePcdId", Integer.toString(state.getStagePcdId()));


        //Pasamos el atributo del pcdId para la ayuda en linea
        request.setAttribute("pcdid", Integer.toString(state.getPcdId()));

		//Se actualiza el estado de tramitación.
		storeStateticket(state,response);

		// Determina la página jsp asociada a la presentación de la entidad
		request.setAttribute("application", defaultForm.getEntityApp().getURL());

		//Se calcula el destinatario si se devuelve el tramite
		IItemCollection itemcol = invesFlowAPI.getMilestones(state.getProcessId(), state.getStagePcdId(), state.getTaskPcdId(), state.getTaskId(), TXConstants.MILESTONE_EXPED_DELEGATED);
		String returnUID = null;
		if (itemcol.next()){
			returnUID = itemcol.value().getString("AUTOR");
		}else{
			itemcol = invesFlowAPI.getMilestones(state.getProcessId(), state.getStagePcdId(), state.getTaskPcdId(), state.getTaskId(), TXConstants.MILESTONE_TASK_START);
			if (itemcol.next()){
				returnUID = itemcol.value().getString("AUTOR");
			}
		}
		if (StringUtils.isNotEmpty(returnUID) && !StringUtils.equals(returnUID, session.getClientContext().getRespId())){
			request.setAttribute("returnUID", returnUID);
		}


		ActionForward action = mapping.findForward("showtask");
		try {
		    // Si no encuentra la fase
			// expediente que no está en ninguna fase (archivados, finalizados).
		    invesFlowAPI.getStage(Integer.parseInt(stageId));
		}
		catch(ISPACException e) {

	        request.setAttribute("expedientState", "closed");
	        linkParams.put("readonly", String.valueOf(ManagerState.READONLY_REASON_EXPEDIENT_CLOSED));
		}

		// Introducimos como atributo el action utilizado como enlace para todas las entidades
	    request.setAttribute("urlExp", action.getPath());

	    // Y se mantiene la ordenación de las listas
   		String displayTagOrderParams = getDisplayTagOrderParams(request);
   		request.setAttribute("displayTagOrderParams", displayTagOrderParams);

   		// Si se recibe el parametro 'form' con el valor 'single'
        if (request.getParameter("form")!= null && request.getParameter("form").equals("single")) {

        	return mapping.findForward("singleSuccess");
        }

        //Cargamos el contexto de la cabecera (miga de pan)con la stagePcdId del tramite cerrado
        if(!StringUtils.isEmpty(stagePcdId)){
        SchemeMgr.loadContextHeader(state, request, getResources(request), session , stagePcdId);
        }
        else{//En caso contrario se obtendra del contexto
        	SchemeMgr.loadContextHeader(state, request, getResources(request), session );
        }

        // Ahora el formulario de búsqueda está en sesión y se mantienen los parámetros de la última búsqueda realizada
        String returnToSearch = null;
        SearchForm searchForm = (SearchForm) request.getSession().getAttribute(ActionsConstants.FORM_SEARCH_RESULTS);
        if (searchForm != null) {

        	returnToSearch = searchForm.getDisplayTagParams();
        }

        IWorklist managerwl = managerAPI.getWorklistAPI();

		// Responsabilidades del usuario conectado
		String resp = managerwl.getRespString(state);
		// Menús
	    request.setAttribute("menus", MenuFactory.getTaskMenu(cct, state, (String) servlet.getServletContext().getAttribute("ispacbase"), getResources(request), stageId, returnToSearch,resp));

        //Cargamos enlaces para los expedientes relacionados
        SpacMgr.loadRelatedExpedient(session, request, state.getNumexp(), SpacMgr.ALL_EXPEDIENTS );

        //Cargamos las aplicaciones de gestion asociadas
        SpacMgr.loadAppGestion(session, state, request);

        if (StringUtils.equals(request.getParameter("actions"), "deleteDocument")){
        	action = mapping.findForward("showtask");
    		return new ActionForward(action.getName(), action.getPath() + "?taskId=" + String.valueOf(state.getTaskId()), action.getRedirect());
        }
        return  mapping.findForward("success");
	}

    /**
     * @return
     * @throws ISPACException
     */
	/*
    private List selectEditTaskDocEntities(IClientContext ctx, int taskPcdId,
    		int taskId) throws ISPACException {

        List list = new ArrayList();

        //Obtenemos las apis necesarios
		IGenDocAPI gendocAPI = ctx.getAPI().getGenDocAPI();
		IEntitiesAPI entapi = ctx.getAPI().getEntitiesAPI();

		//Documentos creados para el tramite identificado por
		//<code>taskId</code>
		IItemCollection itemCollection = entapi.queryEntities(
				ISPACEntities.DT_ID_DOCUMENTOS,
				"WHERE ID_TRAMITE = " + taskId);

		List docList = itemCollection.toList();
		//Si ya hay algun documento creado...
		//Si tiene un solo documento creado...
		if (docList.size() >= 1 ){
		    //...devolvemos el identificador del primer documento creado,
			//pudiendo ser el unico creado
		    list.add(((IItem)docList.get(0)).getKey());
		    return list;
		}

		//Tipos de documentos asociados al Tramite del Procedimiento
		IItemCollection doctypecollection =
			gendocAPI.getDocTypesFromTaskPCD(taskPcdId);

		//Comprobamos el numero de tipos de documentos asociados al tramite
		List doctypelist=doctypecollection.toList();

		//Si el trámite tiene asociados más de un TIPO DE DOCUMENTO...
		if (doctypelist.size()!=1){
		    //...No creammos ningun documento, presentaremos los datos del
			//trámite solo.
		    return null;
		}
		//Si el tramite tiene asociado un único TIPO DE DOCUMENTO
		IItem doctype=(IItem)doctypelist.get(0);
		//Creamos un documento, ya que si llegamos hasta aqui es que no hay
		//ninguno creado
		IItem item = gendocAPI.createTaskDocument(taskId,doctype.getKeyInt());
		//Devolvemos el identificador del documento creado

	    list.add(item.getKey());
	    return list;
    }
    */

	/**
	 * Borrar los documentos marcados en la lista de documentos del trámite
	 *
     * @throws ISPACException
	 */
	private void deleteDocuments(HttpServletRequest request,
								 EntityForm defaultForm,
								 IEntitiesAPI entitiesAPI) throws ISPACException {

		// Comprobar si hay que borrar documentos
		String actions = request.getParameter("actions");
		String[] multibox = defaultForm.getMultibox();
		if ((actions != null) &&
			(actions.equals("deleteDocument")) &&
			(multibox != null)) {

			for (int i = 0; i < multibox.length; i++) {

		    	IItem document = entitiesAPI.getEntity(SpacEntities.SPAC_DT_DOCUMENTOS, Integer.parseInt(multibox[i]));

		    	// Un documento se puede eliminar si no tiene ningun tipo de bloqueo y no está registrado de salida.
		    	if (!DocumentLockStates.hasAnyLock(document.getString("BLOQUEO"))
		    			&& !("SALIDA".equalsIgnoreCase(document.getString("TP_REG"))
		    					&& StringUtils.isNotBlank(document.getString("NREG")))) {
		    		entitiesAPI.deleteDocument(document);
		    	}
			}

			defaultForm.setMultibox(null);
		}
	}

	
	private void auditConsultaTramite(int idTask, String numExpediente, ClientContext cct) {
		AuditContext auditContext = AuditContextHolder.getAuditContext();

		IspacAuditEventTramiteConsultaVO evento = new IspacAuditEventTramiteConsultaVO();
		evento.setAppDescription(IspacAuditConstants.APP_DESCRIPTION);
		evento.setAppId(IspacAuditConstants.getAppId());
		evento.setIdUser("");
		evento.setUser("");
		evento.setUserHostName("");
		evento.setUserIp("");
	
		//Añadir también el número de expediente del trámite
		evento.setNumExpediente(numExpediente);
		evento.setIdTramite(String.valueOf(idTask));
						
		evento.setFecha(new Date());

		if (auditContext != null) {
			evento.setUserHostName(auditContext.getUserHost());
			evento.setUserIp(auditContext.getUserIP());
			evento.setUser(auditContext.getUser());
			evento.setIdUser(auditContext.getUserId());
		} else {
			logger.error("ERROR EN LA AUDITORÍA. No está disponible el contexto de auditoría en el thread local. Faltan los siguientes valores por auditar: userId, user, userHost y userIp");
		}
		logger.info("Auditando la creación del trámite");
		auditoriaManager.audit(evento);
	}
	
}