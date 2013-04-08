package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispacmgr.action.form.BatchTaskForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class BatchTaskDocumentsAction extends BaseAction {
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
		IInvesflowAPI invesFlowAPI = session.getAPI();

		BatchTaskForm frm = (BatchTaskForm) form;

		int[] tasksId = null;
		boolean isEditandoPlantilla = false;
		String sParameter = frm.getTipoAccion();
		if (sParameter != null && sParameter.equalsIgnoreCase("edit")) {
			isEditandoPlantilla = true;
		}

		String[] stagesIds = frm.getMultibox(); 
		String[] tasksIds =  frm.getTaskIds();
		if (!isEditandoPlantilla){
			frm.setMultiboxString(ArrayUtils.join(frm.getMultibox(), ","));
			frm.setTaskIdsString(ArrayUtils.join(frm.getTaskIds(), ","));
			stagesIds = frm.getMultibox(); 
			tasksIds =  frm.getTaskIds();
		}else{
			stagesIds = ArrayUtils.getArray(frm.getMultiboxString(),","); 
			tasksIds =  ArrayUtils.getArray(frm.getTaskIdsString(),",");
		}

		int num=0;
		tasksId = new int[tasksIds.length];
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

		//comprobar si algun taskId es cero y avisar
		for (int i = 0; i < tasksIds.length; i++) {
			if (!(tasksId[0]>0))
				throw new ISPACInfo("exception.expedients.batchTask.noTask", false);
		}
		
		//Para comprobar si el tramite tiene plantillas
		if(frm.getTemplate() == null)
			throw new ISPACInfo("exception.expedients.batchTask.noTemplate", false);
				
		//Gestion de documentos
		if (isEditandoPlantilla) {
			IGenDocAPI documentsAPI = invesFlowAPI.getGenDocAPI();

			String sTemplateName = documentsAPI.getTemporaryTemplate(Integer.parseInt(frm
				.getTemplate()));
			frm.setFile(sTemplateName);
		}

		//redirigo a la pantalla de generacion de documentos
		return mapping.findForward("success");
			
//		HashSet ignoredParams = new HashSet();
//		ignoredParams.add(ActionsConstants.PARAM_FORM_REFRESHER);
//		return composeActionForward(form,"/showBatchTask.do",ignoredParams);	
	}
	
}