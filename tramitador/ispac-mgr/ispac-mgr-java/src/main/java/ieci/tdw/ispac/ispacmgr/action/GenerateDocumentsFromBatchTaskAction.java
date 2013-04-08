package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACVariable;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.ITask;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.form.BatchTaskForm;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GenerateDocumentsFromBatchTaskAction extends BaseAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
		// Contexto de tramitación
		ClientContext cct = session.getClientContext();
		IInvesflowAPI invesFlowAPI = session.getAPI();
		IGenDocAPI genDocAPI = invesFlowAPI.getGenDocAPI();

		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
		Map params = request.getParameterMap();
		IState newState = managerAPI.enterState(getStateticket(request),ManagerState.BATCHTASK, params);

		BatchTaskForm documentsForm = (BatchTaskForm) form;
		IItem entity = null;

		ArrayList list = new ArrayList();
		ArrayList messageList = new ArrayList();
		
		String sParameter = documentsForm.getTpDocId();
		if (sParameter == null) {
			return null;
		}
		int documentId = Integer.parseInt(sParameter);

		sParameter = documentsForm.getTemplate();
		if (sParameter == null) {
			return null;
		}

		int templateId = Integer.parseInt(sParameter);

		String sFileTemplate = documentsForm.getFile();

		sParameter = documentsForm.getPrint();

		boolean printDocuments = false;

		if (sParameter != null) {
			printDocuments = true;
		}

		int[] tasksId = null;

		String[] stagesIds = ArrayUtils.getArray(documentsForm.getMultiboxString(),","); 
		String[] tasksIds =  ArrayUtils.getArray(documentsForm.getTaskIdsString(),",");

		int num=0;
		tasksId = new int[stagesIds.length];
		for (int i = 0; i < stagesIds.length; i++) {
			for (int j = 0; j < tasksIds.length; j++) {
				String[] pairStageIdTaskId = tasksIds[j].split(":");
				String taskId = pairStageIdTaskId[1];
				String stageId = pairStageIdTaskId[0];
				if (stagesIds[i].equalsIgnoreCase(stageId)){
					tasksId[num] = Integer.parseInt(taskId);
					num++;
				}
				
			}
		}

		// Añade la variable de sesión que indica que se están
		// generando los documentos en BATCH
		cct.setSsVariable(ISPACVariable.BATCH_DOCUMENT_GENERATION, "true");
		//comprobar si algun taskId es cero y avisar
		for (int i = 0; i < tasksId.length; i++) {
			if (!(tasksId[i]>0))
				throw new ISPACInfo(getResources(request).getMessage("forms.batchTask.task.error"), false);
		}

		String docref;
		IItem templateEntity;

		Object connectorSession = null;
		try {
			connectorSession = genDocAPI.createConnectorSession();

			for (int i = 0; i < tasksId.length; i++) {
				
				// Información del trámite
				ITask task = invesFlowAPI.getTask(tasksId[i]);
				
	    		// Comprobar si se tiene responsabilidad sobre el trámite
	    		String idResp = task.getString("ID_RESP");
	    		if (!invesFlowAPI.getWorkListAPI().isInResponsibleList(idResp , task)) {
	    			throw new ISPACInfo(getResources(request).getMessage("forms.batchTask.task.error.noResp"), new String[] { task.getString("NUMEXP")}, false);
	    		}
				
				String expedient = task.getString("NUMEXP");
				
				
				// Ejecución en un contexto transaccional
				boolean bCommit = false;
				
				try {
					// Comprueba que la plantilla está asociada a la tarea
					//templates = documentsAPI.getTemplatesInTask(documentId,
					//tasksId[i]);
					
					// Abrir transacción
					cct.beginTX();
					
					entity = genDocAPI.createBatchTaskDocument(Integer.parseInt(documentsForm.getBatchTaskId()),
					tasksId[i], documentId, Integer.parseInt(documentsForm
					.getTaskPcdId()), templateId);

					list.add(new ItemBean(entity));

					templateEntity = genDocAPI.attachTaskTemplate(connectorSession, 
							tasksId[i], entity.getKeyInt(), templateId, 
							sFileTemplate);

					// Actualizar la extensión del documento
					docref = templateEntity.getString("INFOPAG");
					if (StringUtils.isNotBlank(docref)) {
						String sMimetype = genDocAPI.getMimeType(connectorSession, docref);
						templateEntity.set("EXTENSION", 
								MimetypeMapping.getExtension(sMimetype));
						templateEntity.store(cct);
					}
					
					// Si todo ha sido correcto se hace commit de la transacción
					bCommit = true;
	
					messageList.add(expedient);
				}
				catch (Exception e) {
					
					throw new ISPACInfo(getResources(request).getMessage("error.documento"), e, false);
				}
				finally {
					cct.endTX(bCommit);
				}
			}
		}
		finally {
			if (connectorSession != null) {
				genDocAPI.closeConnectorSession(connectorSession);
			}
    	}				
		
		// Guardar el estado de tramitación
		storeStateticket(newState,response);
		
		request.setAttribute("MessageList", messageList);
		request.setAttribute("ErrorList", new ArrayList());
		
		// Elimina la variable de sesión que indica que se están
		// generando los documentos en BATCH
		cct.deleteSsVariable(ISPACVariable.BATCH_DOCUMENT_GENERATION);

		// Borra el fichero intermedio que contiene la plantilla editada.
		if (!StringUtils.isBlank(sFileTemplate)) {
			FileTemporaryManager temporaryManager = null;

			temporaryManager = FileTemporaryManager.getInstance();

			temporaryManager.delete(sFileTemplate);
		}

		if (printDocuments) {
			request.setAttribute("DocumentsList", list);
		}

		return mapping.findForward("success");
	}

}
