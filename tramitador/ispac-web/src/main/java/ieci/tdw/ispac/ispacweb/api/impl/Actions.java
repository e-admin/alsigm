package ieci.tdw.ispac.ispacweb.api.impl;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.expedients.adapter.Constants;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.ispaclib.bean.ActionBean;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.SearchActionBean;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.api.IActions;
import ieci.tdw.ispac.ispacweb.api.IScheme;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.struts.util.MessageResources;

public class Actions implements IActions {

	private final int THIRDPARTY_ENTITY  = 3;
	private final int DOCUMENT_ENTITY = 2;
	
    private ClientContext mccnt = null;

 // Fichero de recursos de la aplicación
	private static final String BUNDLE_NAME_TRAMITADOR = "ieci.tdw.ispac.ispacmgr.resources.ApplicationResources";
	
	static ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME_TRAMITADOR);
    
	public Actions( ClientContext ccnt) {
		this.mccnt = ccnt;
	}

	public List getStateActions(IState state, MessageResources resources) 
			throws ISPACException {
		switch (state.getState()) {
			case ManagerState.PROCESSESLIST: {
				return getStageProcessesListActions(state, resources);
			}
			case ManagerState.SUBPROCESSESLIST: {
				return getActivitySubProcessesListActions(state, resources);
			}
			case ManagerState.TASKSLIST: {
				return getTasksListActions(state, resources);
			}
			case ManagerState.EXPEDIENT: {
				return getStageActions(state, resources);
			}
			case ManagerState.TASK: {
				return getTaskActions(state, resources);
			}
			case ManagerState.BATCHTASK: {
				return getBatchTaskActions(state, resources);
			}
			default: {
				return new ArrayList();
			}
		}
	}

	public List getStateDocuments(IState state, MessageResources resources) 
			throws ISPACException {
		switch (state.getState()) {
			case ManagerState.PROCESSESLIST: {
			    return getStageDocuments(state, resources,
			    		//"javascript:takeElement(\"/generateDocument.do\", \"\");");
  		  				"javascript:takeElementInWorkFrame(\"/createDocumentStage.do\", \"idsStage\" , \""+bundle.getString("element.noSelect")+"\" , \""+bundle.getString("common.alert")+"\" , \""+bundle.getString("common.message.ok")+"\" , \""+bundle.getString("common.message.cancel")+"\");");
			}
			case ManagerState.EXPEDIENT: {
				return getStageDocuments(state, resources,
						"/generateDocument.do?stageId=" + state.getStageId());
			}
			case ManagerState.TASKSLIST: {
				return getTaskDocuments(state, resources,	
						"javascript:takeElement(\"/generateDocument.do\", \"\", \""+bundle.getString("element.noSelect")+"\" , \""+bundle.getString("common.alert")+"\", \""+bundle.getString("common.message.ok")+"\" , \""+bundle.getString("common.message.cancel")+"\");");
			}
			case ManagerState.TASK: {
				return getTaskDocuments(state, resources,
						"/generateDocument.do?taskId=" + state.getTaskId());
			}
			case ManagerState.SUBPROCESSESLIST: {
			    return getActivityDocuments(state, resources,
			    		//"javascript:takeElement(\"/generateDocument.do\", \"\");");
			    		  "javascript:takeElementInWorkFrame(\"/createDocumentStage.do\", \"idsStage\", \""+bundle.getString("element.noSelect")+"\" , \""+bundle.getString("common.alert")+"\", \""+bundle.getString("common.message.ok")+"\" , \""+bundle.getString("common.message.cancel")+"\");");
			}
			
		}

		return new ArrayList();
	}


	public List getStateDocuments(IState state, MessageResources resources, 
			String action) throws ISPACException {

		switch (state.getState()) {
			case ManagerState.PROCESSESLIST:
			case ManagerState.EXPEDIENT: {
				return getStageDocuments(state, resources, action);
			}
			case ManagerState.TASKSLIST:
			case ManagerState.TASK: {
				return getTaskDocuments(state, resources, action);
			}
			case ManagerState.SUBPROCESSESLIST:
			case ManagerState.SUBPROCESS: {
				return getActivityDocuments(state, resources, action);
			}
		}

		return new ArrayList();
	}

	public List getStateTasks(IState state, MessageResources resources) 
			throws ISPACException {
		switch (state.getState()) {
			case ManagerState.PROCESSESLIST: {
				return getStageTasks(state, resources,
						"javascript:takeElement(\"/createTsk.do\", \"\", \""+bundle.getString("element.noSelect")+"\" , \""+bundle.getString("common.alert")+"\", \""+bundle.getString("common.message.ok")+"\" , \""+bundle.getString("common.message.cancel")+"\");");
			}
			case ManagerState.EXPEDIENT: {
				return getStageTasks(state, resources,
						"/createTsk.do?stageId=" + state.getStageId());
			}
		}
		return new ArrayList();
	}

	public List getStateTasks(IState state, MessageResources resources, 
			String action) throws ISPACException {

		switch (state.getState()) {
			case ManagerState.PROCESSESLIST:
			case ManagerState.EXPEDIENT: {
				return getStageTasks(state, resources, action);
			}
		}

		return new ArrayList();
	}

	public List getStateTemplates(IState state, MessageResources resources, 
			int entdocId, int entdocregId) throws ISPACException {
		
	    if (entdocId==0 || entdocregId==0) {
	        return new ArrayList();
	    }

	    IInvesflowAPI invesFlowAPI = mccnt.getAPI();
	    IItem docitem=invesFlowAPI.getEntitiesAPI().getEntity(entdocId,
	    		entdocregId);
	    if (docitem==null) {
	        return new ArrayList();
	    }

	    return getStateTemplates(state, resources, docitem.getInt("ID_TPDOC"), 
	    		((docitem.get("INFOPAG")!= null 
	    				&& !docitem.get("INFOPAG").equals("")) ? true : false));
	}

	public List getStateTemplates(IState state, MessageResources resources, 
			String action, int entdocId, int entdocregId) 
			throws ISPACException {
		
	    if (entdocId==0 || entdocregId==0) {
	        return new ArrayList();
	    }

	    IInvesflowAPI invesFlowAPI = mccnt.getAPI();
	    IItem docitem=invesFlowAPI.getEntitiesAPI().getEntity(entdocId,
	    		entdocregId);
	    if (docitem==null) {
	        return new ArrayList();
	    }

	    return getStateTemplates(state, resources, action,
	    		docitem.getInt("ID_TPDOC"));
	}

	public List getStateTemplates(IState state, MessageResources resources, 
			int documentType, boolean existDocument ) throws ISPACException {

		switch (state.getState()) {
			case ManagerState.PROCESSESLIST:
			case ManagerState.EXPEDIENT: {
			    return  getStageTemplates(state, resources, 
			    		"javascript:attachTemplate(\"/attachTemplate.do\")", 
			    		documentType);
			}
			case ManagerState.TASKSLIST:
			case ManagerState.TASK: {
			    List templatelist;
			    // Si ya existe un documento creado mostramos un mensaje de 
			    // confirmacion antes de machacarlo
			    if (!existDocument) {
			        templatelist = getTaskTemplates(state, resources,
			        		"javascript:executeHideFrame(\"/attachTemplate.do\", \"workframe\", null)", 
			        		documentType);
			    } else {
			        templatelist = getTaskTemplates(state, resources,
			        		"javascript:executeHideFrame(\"/attachTemplate.do\", \"workframe\", \""
				        		+ resources.getMessage(mccnt.getLocale(), "msg.sustituirFichero")
				        		+ "\")", 
			        		documentType);
			    }
				return templatelist;
			}
			default: {
				return new ArrayList();
			}
		}
	}

	public List getStateTemplates(IState state, MessageResources resources, 
			String action, int documentType) throws ISPACException {

		switch (state.getState()) {
			case ManagerState.PROCESSESLIST:
			case ManagerState.EXPEDIENT: {
				return getStageTemplates(state, resources, action, documentType);
			}
			case ManagerState.TASKSLIST:
			case ManagerState.TASK: {
				return getTaskTemplates(state, resources, action, documentType);
			}
		}

		return new ArrayList();
	}

	public List getStageProcessesListActions(IState state, 
			MessageResources resources) throws ISPACException {

		IInvesflowAPI invesFlowAPI = mccnt.getAPI();
		ISecurityAPI  securityAPI =invesFlowAPI.getSecurityAPI();
		List list = new ArrayList();
		
		list.add( new ActionBean(
				resources.getMessage(mccnt.getLocale(), "ispac.action.stages.next"), 
				"javascript:takeElement(\"/closeStage.do\", \"idsStage\", \""+bundle.getString("element.noSelect")+"\" , \""+bundle.getString("common.alert")+"\", \""+bundle.getString("common.message.ok")+"\" , \""+bundle.getString("common.message.cancel")+"\", \""+bundle.getString("common.needConfirm")+"\");"));
		
		
		list.add(new ActionBean(
				resources.getMessage(mccnt.getLocale(), "ispac.action.stages.delegate"), 
				"javascript:takeElementAndShowFrame(\"viewUsersManager.do?captionkey=ispac.action.stages.delegate&action=delegateResp.do\", \"idsStage\", \""+bundle.getString("element.noSelect")+"\" , \""+bundle.getString("common.alert")+"\", \""+bundle.getString("common.message.ok")+"\" , \""+bundle.getString("common.message.cancel")+"\");"));
		
		list.add( new ActionBean(
				resources.getMessage(mccnt.getLocale(), "ispac.action.stages.return"), 
				"javascript:takeElement(\"/redeployPreviousStage.do\", \"idsStage\", \""+bundle.getString("element.noSelect")+"\" , \""+bundle.getString("common.alert")+"\", \""+bundle.getString("common.message.ok")+"\" , \""+bundle.getString("common.message.cancel")+"\", \""+bundle.getString("common.needConfirm")+"\");"));
		
		list.add(new ActionBean(
				resources.getMessage(mccnt.getLocale(), "ispac.action.batchTasks.create"),
				"javascript:takeElement(\"/createBatchTask.do\", \"\", \""+bundle.getString("element.noSelect")+"\" , \""+bundle.getString("common.alert")+"\", \""+bundle.getString("common.message.ok")+"\" , \""+bundle.getString("common.message.cancel")+"\", \""+bundle.getString("common.needConfirm")+"\");"));
		if(securityAPI.isFunction(mccnt.getRespId(), ISecurityAPI.FUNC_TOTALSUPERVISOR) ||
				securityAPI.isPermission(invesFlowAPI.getWorkListAPI().getRespString(), Constants.CAN_SEND_EXPEDIENTS_TO_TRASH, state.getPcdId())){
			list.add( new ActionBean(
				resources.getMessage(mccnt.getLocale(), "ispac.action.expedient.send.trash"),
				"javascript:takeElement(\"/sendTrash.do\", \"idsStage\", \""+bundle.getString("element.noSelect")+"\" , \""+bundle.getString("common.alert")+"\", \""+bundle.getString("common.message.ok")+"\" , \""+bundle.getString("common.message.cancel")+"\", \""+bundle.getString("common.needConfirm")+"\");"));
		}

		return list;
	}
	public List getActivitySubProcessesListActions(IState state,
			MessageResources resources) throws ISPACException {

		List list = new ArrayList();

		list.add( new ActionBean(
				resources.getMessage(mccnt.getLocale(), "ispac.action.activities.next"),
				"javascript:takeElement(\"/closeStage.do\", \"idsStage\", \""+bundle.getString("element.noSelect")+"\" , \""+bundle.getString("common.alert")+"\", \""+bundle.getString("common.message.ok")+"\" , \""+bundle.getString("common.message.cancel")+"\", \""+bundle.getString("common.needConfirm")+"\");"));

		list.add(new ActionBean(
				resources.getMessage(mccnt.getLocale(), "ispac.action.activities.delegate"),
				"javascript:takeElementAndShowFrame(\"viewUsersManager.do?captionkey=ispac.action.activities.delegate&action=delegateResp.do&pcdId="
				+ state.getPcdId() + "\", \"idsStage\", \""+bundle.getString("element.noSelect")+"\" , \""+bundle.getString("common.alert")+"\", \""+bundle.getString("common.message.ok")+"\" , \""+bundle.getString("common.message.cancel")+"\");"));

		list.add( new ActionBean(
				resources.getMessage(mccnt.getLocale(), "ispac.action.activities.return"),
				"javascript:takeElement(\"/redeployPreviousStage.do\", \"idsStage\", \""+bundle.getString("element.noSelect")+"\" , \""+bundle.getString("common.alert")+"\", \""+bundle.getString("common.message.ok")+"\" , \""+bundle.getString("common.message.cancel")+"\", \""+bundle.getString("common.needConfirm")+"\");"));


		return list;
	}



	public List getListas(IState state, MessageResources resources)
			throws ISPACException {

		List list = new ArrayList();

		//Obtenemos las entidades asociadas al esquema del procedimiento
		IScheme scheme =  new Scheme(mccnt);
		Map entitiesScheme = scheme.getSchemeEntities(state);

		if (entitiesScheme.containsKey(""+THIRDPARTY_ENTITY)) {
		    list.add( new ActionBean(
		    		resources.getMessage(mccnt.getLocale(), "ispac.lists.thirdParty"),
		    		"/showExpedient.do?entity="+THIRDPARTY_ENTITY+"&stageId="
		    			+state.getStageId()));
		}

		if (entitiesScheme.containsKey(""+DOCUMENT_ENTITY)) {
		    list.add( new ActionBean(
		    		resources.getMessage(mccnt.getLocale(), "ispac.lists.documents"),
		    		"/showExpedient.do?entity="+DOCUMENT_ENTITY+"&stageId="
		    			+state.getStageId()));
		}

		//list.add( new ActionBean("Tr&aacute;mites del Expediente", "/showTasksList.do"));

		return list;
	}

	public List getTasksListActions(IState state, MessageResources resources)
			throws ISPACException {

		List list = new ArrayList();

		// Mostramos un mensaje previo de confirmacion antes de cerrar los
		// tramites
		list.add(new ActionBean(
				resources.getMessage(mccnt.getLocale(), "ispac.action.tasks.close"),
				"javascript:takeElement(\"/closeTask.do\", \"idsTask\", "
					+ " \""+bundle.getString("element.noSelect")+"\" , \""+bundle.getString("common.alert")+"\", \""+bundle.getString("common.message.ok")+"\" , \""+bundle.getString("common.message.cancel")+"\", \""+ resources.getMessage(mccnt.getLocale(), "ispac.action.tasks.close.msg")+"\");"));

		list.add(new ActionBean(
				resources.getMessage(mccnt.getLocale(), "ispac.action.tasks.delegate"),
				"javascript:takeElementAndShowFrame(\"viewUsersManager.do?captionkey=ispac.action.tasks.delegate&action=delegateResp.do\", \"idsTask\", \""+bundle.getString("element.noSelect")+"\" , \""+bundle.getString("common.alert")+"\", \""+bundle.getString("common.message.ok")+"\" , \""+bundle.getString("common.message.cancel")+"\");"));

		return list;
	}

	public List getStageActions(IState state, MessageResources resources)
			throws ISPACException {

		List list = new ArrayList();
		IInvesflowAPI invesFlowAPI = mccnt.getAPI();
		ISecurityAPI  securityAPI =invesFlowAPI.getSecurityAPI();

		if (state.getStageId() != 0) {
		    //---------------
		    //list.add(new ActionBean(Integer.toString(state.getStageId()), "Terminar fase", "/closeStage.do"));
		    //list.add(new ActionBean("Terminar fase", "javascript:sure(\"closeStage.do?idsStage="+state.getStageId()+"\")"));

			list.add(new ActionBean(
					resources.getMessage(mccnt.getLocale(), "ispac.action.stage.delegate"),
					"javascript:showFrame(\"workframe\", \"viewUsersManager.do?captionkey=ispac.action.stage.delegate&action=delegateResp.do&idsStage="
					+ state.getStageId() + "\");"));

			boolean isFirstStage = mccnt.getAPI().getProcedureAPI().isFirstStage(state.getPcdId(), state.getStagePcdId());

			// Si no nos encontramos en la primera fase del procedimiento, damos la opcion de volver a la fase anterior
			if (!isFirstStage){
				list.add(new ActionBean(
						resources.getMessage(mccnt.getLocale(), "ispac.action.stage.return"),
						"javascript:sure(\"redeployPreviousStage.do?idsStage="+state.getStageId()
							+ "\", \""
							+ resources.getMessage(mccnt.getLocale(), "ispac.action.stage.return.msg")
							+ "\", \""
							+ resources.getMessage(mccnt.getLocale(), "common.confirm")
							+ "\", \""
							+ resources.getMessage(mccnt.getLocale(), "common.message.ok")
							+ "\", \""
							+ resources.getMessage(mccnt.getLocale(), "common.message.cancel")
							+ "\")"));
			}

			//La accion de 'Comprobar documentacion' sera mostrada si la fase actual tiene asociado el tramite de Solicitud de Subsanacion (segun id en spac_ct_tramites),
			//el cual esta configurado en BD
	        //Obtener Identificador del Tramite de Solicitud de Subsanación
			String idTaskCTStr = ConfigurationMgr.getVarGlobal(mccnt, ConfigurationMgr.ID_TASK_SOLICITUD_SUBSANACION);

			IEntitiesAPI entitiesAPI = mccnt.getAPI().getEntitiesAPI();
			Map tableentitymap = new HashMap();
			tableentitymap.put("SPAC_P_TRAMITES","SPAC_P_TRAMITES");
			IItemCollection itemCol =  entitiesAPI.queryEntities(tableentitymap, "WHERE ID_CTTRAMITE = " + idTaskCTStr + " AND ID_FASE = " + state.getStagePcdId());
			if (itemCol.next()){
		    	list.add(new ActionBean(
		    			Integer.toString(state.getStageId()),
		    			resources.getMessage(mccnt.getLocale(), "ispac.action.documentation.check"),
		    			"/comprobarDocumentacion.do"));
			}

			// Si nos encontramos en la primera fase del procedimiento, damos la opcion de clonar el expediente
			if (isFirstStage){
		    	list.add(new ActionBean(
    			resources.getMessage(mccnt.getLocale(), "ispac.action.expedient.clone"),
    			"/cloneExpedient.do?method=enter"));
			}

			//Si el procedimiento tiene la fase de Archivo permitir situarse en esa fase
			String archivoCtStageId = ConfigurationMgr.getVarGlobal(mccnt,  ConfigurationMgr.ID_STAGE_ARCHIVO);
			if (StringUtils.isNotBlank(archivoCtStageId)) {

				IItem archivoStage = mccnt.getAPI().getProcedureAPI().getStagePcd(state.getPcdId(), archivoCtStageId);
				//if (mccnt.getAPI().getProcedureAPI().isStageAsociated(state.getPcdId(), Integer.valueOf(archivoStageId).intValue())){
				if (archivoStage != null && state.getStagePcdId() != archivoStage.getKeyInt() ){
					list.add(new ActionBean(
							resources.getMessage(mccnt.getLocale(), "ispac.action.stage.goto", archivoStage.getString("NOMBRE")),
							"javascript:sure(\"gotoStage.do?idsStagePcd="+archivoStage.getKeyInt()
							+ "\", \""
							+ resources.getMessage(mccnt.getLocale(), "ispac.action.stage.archivo.msg")
							+ "\", \""
							+ resources.getMessage(mccnt.getLocale(), "common.confirm")
							+ "\", \""
							+ resources.getMessage(mccnt.getLocale(), "common.message.ok")
							+ "\", \""
							+ resources.getMessage(mccnt.getLocale(), "common.message.cancel")
							+ "\")")
					);
				}
			}
		}

		if( !invesFlowAPI.isExpedientSentToTrash(state.getNumexp()) &&(securityAPI.isFunction(mccnt.getRespId(), ISecurityAPI.FUNC_TOTALSUPERVISOR) ||
				securityAPI.isPermission(invesFlowAPI.getWorkListAPI().getRespString(), Constants.CAN_SEND_EXPEDIENTS_TO_TRASH, state.getPcdId()))){
				list.add(new ActionBean(
						resources.getMessage(mccnt.getLocale(), "ispac.action.expedient.send.trash"),
						"javascript:sure(\"sendTrash.do"
						+ "\", \""
						+ resources.getMessage(mccnt.getLocale(), "ispac.action.expedient.send.trash.msg")
						+ "\", \""
						+ resources.getMessage(mccnt.getLocale(), "common.confirm")
						+ "\", \""
						+ resources.getMessage(mccnt.getLocale(), "common.message.ok")
						+ "\", \""
						+ resources.getMessage(mccnt.getLocale(), "common.message.cancel")
						+ "\");"));
		 }

		return list;
	}

	public List getActivityActions(IState state, MessageResources resources) throws ISPACException {

		List list = new ArrayList();
		IInvesflowAPI invesFlowAPI = mccnt.getAPI();
		ISecurityAPI  securityAPI =invesFlowAPI.getSecurityAPI();

		if (state.getActivityId() != 0 && !state.getReadonly()) {

			list.add(new ActionBean(
					resources.getMessage(mccnt.getLocale(), "ispac.action.activity.delegate"),
					"javascript:showFrame(\"workframe\", \"viewUsersManager.do?captionkey=ispac.action.activity.delegate&action=delegateResp.do&idsStage="
					+ state.getActivityId() + "\");"));

			// Si no nos encontramos en la primera actividad del subproceso, damos la opcion de volver a la actividad anterior
			if (!mccnt.getAPI().getProcedureAPI().isFirstStage(state.getSubPcdId(), state.getActivityPcdId())){
				list.add(new ActionBean(
						resources.getMessage(mccnt.getLocale(), "ispac.action.activity.return"),
						"javascript:sure(\"redeployPreviousStage.do?idsStage="+state.getActivityId()
							+ "\", \""
							+ resources.getMessage(mccnt.getLocale(), "ispac.action.activity.return.msg")
							+ "\", \""
							+ resources.getMessage(mccnt.getLocale(), "common.confirm")
							+ "\", \""
							+ resources.getMessage(mccnt.getLocale(), "common.message.ok")
							+ "\", \""
							+ resources.getMessage(mccnt.getLocale(), "common.message.cancel")
							+ "\")"));
			}

			// Eliminar el subproceso
			if (!state.getReadonly()) {
				list.add(new ActionBean(resources.getMessage(mccnt.getLocale(),
						"ispac.action.subprocess.delete"),
						"javascript:sure(\"deleteSubProcess.do?idsTask="
								+ state.getTaskId()
								+ "\", \""
								+ resources.getMessage(mccnt.getLocale(),
										"ispac.action.subprocess.delete.msg")
								+ "\", \""
								+ resources.getMessage(mccnt.getLocale(),
										"common.confirm")
								+ "\", \""
								+ resources.getMessage(mccnt.getLocale(),
										"common.message.ok")
								+ "\", \""
								+ resources.getMessage(mccnt.getLocale(),
										"common.message.cancel") + "\")"));

				if (securityAPI.isFunction(mccnt.getRespId(),
						ISecurityAPI.FUNC_TOTALSUPERVISOR)
						|| securityAPI.isPermission(invesFlowAPI
								.getWorkListAPI().getRespString(),
								Constants.CAN_SEND_EXPEDIENTS_TO_TRASH, state
										.getPcdId())) {
					list.add(new ActionBean(
							resources.getMessage(mccnt.getLocale(),
									"ispac.action.expedient.send.trash"),
							"javascript:sure(\"sendTrash.do"
									+ "\", \""
									+ resources.getMessage(mccnt.getLocale(),
											"ispac.action.expedient.send.trash.msg")
									+ "\", \""
									+ resources.getMessage(mccnt.getLocale(),
											"common.confirm")
									+ "\", \""
									+ resources.getMessage(mccnt.getLocale(),
											"common.message.ok")
									+ "\", \""
									+ resources.getMessage(mccnt.getLocale(),
											"common.message.cancel") + "\");"));
				}
			}
		}

		return list;
	}

	public List getTaskActions(IState state, MessageResources resources)
			throws ISPACException {
		List list = new ArrayList();
		IInvesflowAPI invesFlowAPI = mccnt.getAPI();
		ISecurityAPI  securityAPI =invesFlowAPI.getSecurityAPI();

		if (state.getTaskId() != 0) {

			list.add(new ActionBean(resources.getMessage(mccnt.getLocale(), "ispac.action.task.delegate"),
					"javascript:showFrame(\"workframe\", \"viewUsersManager.do?captionkey=ispac.action.task.delegate&action=delegateResp.do&idsTask="
					+ state.getTaskId() + "\");"));

			list.add(new ActionBean(
					resources.getMessage(mccnt.getLocale(), "ispac.action.task.close"),
					"javascript:sure(\"closeTask.do?idsTask="+state.getTaskId()
						+ "\", \""
						+ resources.getMessage(mccnt.getLocale(), "ispac.action.task.close.msg")
						+ "\", \""
						+ resources.getMessage(mccnt.getLocale(), "common.confirm")
						+ "\", \""
						+ resources.getMessage(mccnt.getLocale(), "common.message.ok")
						+ "\", \""
						+ resources.getMessage(mccnt.getLocale(), "common.message.cancel")
						+ "\");"));
		}

		if(securityAPI.isFunction(mccnt.getRespId(), ISecurityAPI.FUNC_TOTALSUPERVISOR) ||
				securityAPI.isPermission(invesFlowAPI.getWorkListAPI().getRespString(), Constants.CAN_SEND_EXPEDIENTS_TO_TRASH, state.getPcdId())){
				list.add(new ActionBean(
						resources.getMessage(mccnt.getLocale(), "ispac.action.expedient.send.trash"),
						"javascript:sure(\"sendTrash.do"
						+ "\", \""
						+ resources.getMessage(mccnt.getLocale(), "ispac.action.expedient.send.trash.msg")
						+ "\", \""
						+ resources.getMessage(mccnt.getLocale(), "common.confirm")
						+ "\", \""
						+ resources.getMessage(mccnt.getLocale(), "common.message.ok")
						+ "\", \""
						+ resources.getMessage(mccnt.getLocale(), "common.message.cancel")
						+ "\");"));
		 }

		return list;
	}

	public List getBatchTaskActions(IState state, MessageResources resources)
	throws ISPACException {
		List list = new ArrayList();

		if (state.getBatchTaskId() != 0) {
			list.add(new ActionBean(Integer.toString(state.getBatchTaskId()),
					resources.getMessage(mccnt.getLocale(), "ispac.action.batchtask.nuevo"),
					"javascript:takeElementInForm(\"/createTsk.do\", \"\", \"batchTaskForm\", \""+bundle.getString("element.noSelect")+"\" , \""+bundle.getString("common.alert")+"\", \""+bundle.getString("common.message.ok")+"\" , \""+bundle.getString("common.message.cancel")+"\");"));

//			list.add(new ActionBean(
//					resources.getMessage(mccnt.getLocale(), "ispac.action.task.close"),
//					"javascript:sure(\"closeTask.do?idsTask="+state.getBatchTaskId()
//						+ "\", \""
//						+ resources.getMessage(mccnt.getLocale(), "ispac.action.task.close.msg")
//						+ "\");"));
		}

		return list;
	}

	// Documentos

	public List getStageDocuments(IState state, MessageResources resources,
			String action) throws ISPACException {

		List list = new ArrayList();

		if (state.getStagePcdId() != 0) {
			IInvesflowAPI invesFlowAPI = mccnt.getAPI();
			IGenDocAPI documentAPI = invesFlowAPI.getGenDocAPI();
			IItemCollection collection = documentAPI.getDocTypesFromStage(
					state.getStagePcdId());
			list = CollectionBean.getActionList(collection, action);
		}

		return list;
	}

	public List getTaskDocuments(IState state, MessageResources resources,
			String action) throws ISPACException {

		List list = new ArrayList();
		IInvesflowAPI invesFlowAPI = mccnt.getAPI();
		IGenDocAPI documentAPI = invesFlowAPI.getGenDocAPI();

		if (state.getTaskCtlId() != 0) {
			list = CollectionBean.getActionList(
					documentAPI.getDocTypesFromTaskCTL(state.getTaskCtlId()),
					action);
		} else if (state.getTaskPcdId() != 0) {
			list = CollectionBean.getActionList(
					documentAPI.getDocTypesFromTaskPCD(state.getTaskPcdId()), 
					action);
		}

		return list;
	}
	
	/**
	 * Indica si existe o no informes a los que tenga acceso el usuario conectado
	 * @param state
	 * @return
	 * @throws ISPACException
	 */
	public boolean hasReports(IState state) throws ISPACException {
		return hasReports(state, null);
	}
	
	/**
	 * Indica si existe o no informes a los que tenga acceso el usuario conectado
	 * @param state
	 * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return
	 * @throws ISPACException
	 */
	public boolean hasReports(IState state, String resp) throws ISPACException {
		IProcedureAPI procedureAPI = mccnt.getAPI().getProcedureAPI();
		return procedureAPI.hasReports(mccnt.getStateContext(),resp);
	}
	
	
	 /**
     * Indica si existe o no informes globales
     * @return
     * @throws ISPACException
     */
    public boolean hasGlobalReports() throws ISPACException{
    	
    	return hasGlobalReports(null);
    }
    
	 /**
     * Indica si existe o no informes globales a los que el usuario tenga acceso
     * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
     * @return
     * @throws ISPACException
     */
    public boolean hasGlobalReports(String resp) throws ISPACException{
    	
    	IProcedureAPI procedureAPI = mccnt.getAPI().getProcedureAPI();
		return procedureAPI.hasGlobalReports(resp);
    }
    
    /**
     * Indica si hay informes asociados al formulario de busqueda
     * @deprecated
     * @param idForm
     * @return
     * @throws ISPACException
     */
    public boolean hashSearchReport(int idForm) throws ISPACException{
    	
    	IProcedureAPI procedureAPI = mccnt.getAPI().getProcedureAPI();
		return procedureAPI.hasSearchReport(idForm,null);
    }
    
    /**
     * Indica si hay informes asociados al formulario de busqueda 
     * @param idForm
     * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
     * @return
     * @throws ISPACException
     */
    public boolean hasSearchReport(int idForm) throws ISPACException{
    	
    	return hasSearchReport(idForm,null);
    }
	
    
    /**
     * Indica si hay informes asociados al formulario de busqueda y que el usuario tenga acceso
     * @param idForm
     * @return
     * @throws ISPACException
     */
    public boolean hasSearchReport(int idForm, String resp) throws ISPACException{
    	
    	IProcedureAPI procedureAPI = mccnt.getAPI().getProcedureAPI();
		return procedureAPI.hasSearchReport(idForm,resp);
    }
    
	public List getActivityDocuments(IState state, MessageResources resources, 
			String action) throws ISPACException {

		List list = new ArrayList();

		if (state.getActivityPcdId() != 0) {
			IInvesflowAPI invesFlowAPI = mccnt.getAPI();
			IGenDocAPI documentAPI = invesFlowAPI.getGenDocAPI();
			IItemCollection collection = documentAPI.getDocTypesFromStage(
					state.getActivityPcdId());
			list = CollectionBean.getActionList(collection, action);
		}

		return list;
	}

	// Tareas
	public List getStageTasks(IState state, MessageResources resources, 
			String action) throws ISPACException {

		List list = new ArrayList();

		if (state.getStagePcdId() != 0) {
			IInvesflowAPI invesFlowAPI = mccnt.getAPI();
			IProcedure procedure = invesFlowAPI.getProcedure(state.getPcdId());
			list = CollectionBean.getActionList(
					procedure.getTasks(state.getStagePcdId()), action);
		}

		return list;
	}

	// Plantillas
	public List getStageTemplates(IState state, MessageResources resources, 
			String action, int documentType) throws ISPACException {
		
	    if (state.getStageId() == 0) {
	        return new ArrayList();
	    }

		IInvesflowAPI invesFlowAPI = mccnt.getAPI();
		IGenDocAPI documentAPI = invesFlowAPI.getGenDocAPI();

		return CollectionBean.getActionList(
				documentAPI.getTemplatesInStage(documentType,state.getStageId()), 
				action);
	}

	public List getTaskTemplates(IState state, MessageResources resources,
			String action, int documentType) throws ISPACException {

	    if (state.getTaskId() == 0) {
	        return new ArrayList();
	    }

		IInvesflowAPI invesFlowAPI = mccnt.getAPI();
		IGenDocAPI documentAPI = invesFlowAPI.getGenDocAPI();

		return CollectionBean.getActionList(
				documentAPI.getTemplatesInTask(documentType,state.getTaskId()),
				action);
	}

    public List getStateDocsActions(IState state, MessageResources resources)
    		throws ISPACException {
		List list = new ArrayList();

		if (state.getStageId() != 0) {
		    //Añadimos los trámites asociados a la fase
		    //list.addAll(getStateDocuments(state, resources, "javascript:generateStageDocument(\"entityDocuments.do\");"));
			list.addAll(getStateDocuments(state, resources, "javascript:generateStageDocument(\"createDocumentStage.do\");"));
		}
		return list;
    }

	/**
	 * @deprecated
	 */
	public List getSearchResultListActions(List searchActions,
			   MessageResources resources,
			  Map mapParams) throws ISPACException {

		return this.getSearchResultListActions(searchActions, resources, null, mapParams);
	}

	public List getSearchResultListActions(List searchActions,
										   MessageResources resources,
										   Properties properties,
										  Map mapParams) throws ISPACException {

		List list = new ArrayList();
		String queryString="";
		Iterator itr=mapParams.keySet().iterator();
		while(itr.hasNext()){
			String key=itr.next().toString();
			if(key.compareTo("idsStage")!=0){
				String [] obj= ((String[])mapParams.get(key));
				String valor="";
				if(obj.length>0){
					valor+=obj[0];
				}
				queryString+=key+"="+valor;
				if(itr.hasNext()){
					queryString+="&";
				}
			}
		}

		Iterator it = searchActions.iterator();
		while (it.hasNext()) {

			SearchActionBean bean = (SearchActionBean) it.next();

//			String message = bean.getTitle();
			String key = bean.getTitleKey();
//			if ((!StringUtils.isEmpty(key)) &&
//				(resources.isPresent(key))) {
//
//				message = resources.getMessage(mccnt.getLocale(), key);
//			}
			String message = null;
			if ((!StringUtils.isEmpty(key))) {
				if (properties != null){
					message = properties.getProperty(mccnt.getLocale().toString() +"."+ key);
					if (message== null){
						message = properties.getProperty(mccnt.getLocale().getLanguage() +"."+ key);
					}
					if (message== null){
						message = properties.getProperty(key);
					}
				}
				if (StringUtils.isEmpty(message) && resources.isPresent(key)){
					message = resources.getMessage(mccnt.getLocale(), key);
				}
			}
			if (StringUtils.isEmpty(message)){
				message = bean.getTitle();
			}

			String separator = "&";
			if (bean.getPath().indexOf("?") == -1)
				separator = "?";
			list.add(new ActionBean(message, "javascript:takeElement(\"" + bean.getPath() + separator + queryString + "\", \"idsStage\", \""+bundle.getString("element.noSelect")+"\" , \""+bundle.getString("common.alert")+"\", \""+bundle.getString("common.message.ok")+"\" , \""+bundle.getString("common.message.cancel")+"\", \""+bundle.getString("common.needConfirm")+"\");"));
		}

		return list;
	}

}