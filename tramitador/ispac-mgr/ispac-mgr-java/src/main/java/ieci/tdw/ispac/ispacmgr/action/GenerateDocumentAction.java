package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispacmgr.action.form.BatchForm;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;
import ieci.tdw.ispac.ispacweb.context.NextActivity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GenerateDocumentAction extends BaseAction
{
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
	HttpServletRequest request, HttpServletResponse response,
	SessionAPI session) throws Exception
	{
		ClientContext cct = session.getClientContext();
		IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(cct);
		IState currentstate = managerAPI.currentState(getStateticket(request));

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IGenDocAPI gendocAPI = invesFlowAPI.getGenDocAPI();

		int state = currentstate.getState();

		String sParameter = request.getParameter("documentId");

		if (sParameter == null)
			return null;

		int documentId = Integer.parseInt(sParameter);

		BatchForm batchForm = null;
		IItem entity = null;
		int stageId = 0;
		int taskId = 0;
		boolean multipleId = false;

		switch(state)
		{
			case ManagerState.EXPEDIENT :
				sParameter = request.getParameter("stageId");
				if (sParameter == null)
					throw new ISPACInfo("No se ha indicado la fase");
				stageId = Integer.parseInt(sParameter);
				entity = gendocAPI.createStageDocument(stageId,documentId);
				break;
			case ManagerState.TASK :
				sParameter = request.getParameter("taskId");
				if (sParameter == null)
					throw new ISPACInfo("No se ha indicado la tarea");
				taskId = Integer.parseInt(sParameter);
				entity = gendocAPI.createTaskDocument(taskId,documentId);
				break;
		case ManagerState.PROCESSESLIST :
				batchForm = (BatchForm)form;
				// recogemos los identificadores seleccionados
				String[] stageids = batchForm.getMultibox();
				if (stageids.length > 1)
					multipleId = true;
				if (stageids.length <= 0)
					throw new ISPACInfo("No se ha indicado la fase");
				for (int i = 0; i < stageids.length; i++) {
					stageId = Integer.parseInt(stageids[i]);
					entity = gendocAPI.createStageDocument(stageId,documentId);
				}
				break;
			case ManagerState.TASKSLIST :
				batchForm = (BatchForm)form;
				// recogemos los identificadores seleccionados
				String[] taskids = batchForm.getMultibox();
				if (taskids.length > 1)
					multipleId = true;
				if (taskids.length <= 0)
					throw new ISPACInfo("No se ha indicado la tarea");
				for (int i = 0; i < taskids.length; i++) {
					taskId = Integer.parseInt(taskids[i]);
					entity = gendocAPI.createTaskDocument(taskId,documentId);
				}
				break;
			default :
				return null;
		}
		// Visualiza la entidad de documentos creada
		int entityId = ISPACEntities.DT_ID_DOCUMENTOS;
		int keyId = entity.getKeyInt();

		if (!multipleId) {
			if (state == ManagerState.TASKSLIST || state == ManagerState.TASK)
				return NextActivity.afterGenerateDocumentInTask(entityId, keyId, taskId, mapping);
			if (state == ManagerState.PROCESSESLIST || state == ManagerState.EXPEDIENT)
				return NextActivity.afterGenerateDocumentInStage(entityId, keyId, stageId, mapping);
		}

		return NextActivity.refresh(currentstate,mapping);
	}
}
