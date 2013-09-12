package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
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

		// Parámetros
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

		// En el multibox de la tramitacion agrupada se han establecido los IDs
		// como 'ID_Fase:ID_Tramite' siendo el ID_Tramite = ENTITY_NULLREGKEYID
		// cuando el tramite seleccionado no existe en la fase
		String[] stageTaskIds = ArrayUtils.getArray(documentsForm.getMultiboxString(), ",");

		// Obtener los IDs de los trámites
		int[] taskIds = new int[stageTaskIds.length];
		for (int i = 0; i < stageTaskIds.length; i++) {

			String[] pairStageIdTaskId = stageTaskIds[i].split(":");
			if (pairStageIdTaskId.length > 1) {
				taskIds[i] = Integer.parseInt(pairStageIdTaskId[1]);
			} else {
				taskIds[i] = ISPACEntities.ENTITY_NULLREGKEYID;
			}

			// Comprobar que en el expediente existe el tramite
			// para proceder a crear el documento
			if (!(taskIds[i] > 0)) {
				throw new ISPACInfo("exception.expedients.batchTask.noTask", false);
			}
		}

		String docref;
		IItem templateEntity;

		Object connectorSession = null;
		try {
			connectorSession = genDocAPI.createConnectorSession();
			int taskId;

			for (int i = 0; i < taskIds.length; i++) {

				taskId = taskIds[i];

				// Información del trámite
				ITask task = invesFlowAPI.getTask(taskId);

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
					//taskId);

					// Abrir transacción
					cct.beginTX();

					entity = genDocAPI.createBatchTaskDocument(Integer.parseInt(documentsForm.getBatchTaskId()),
							taskId, documentId, Integer.parseInt(documentsForm
					.getTaskPcdId()), templateId);

					list.add(new ItemBean(entity));

					templateEntity = genDocAPI.attachTaskTemplate(connectorSession,
							taskId, entity.getKeyInt(), templateId,
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
