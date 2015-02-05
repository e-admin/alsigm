package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
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

		// Comprobar si el tramite tiene plantillas
		if (frm.getTemplate() == null) {
			throw new ISPACInfo("exception.expedients.batchTask.noTemplate", false);
		}

		// Se esta editando la plantilla?
		boolean isEditandoPlantilla = false;
		String sParameter = frm.getTipoAccion();
		if (sParameter != null && sParameter.equalsIgnoreCase("edit")) {
			isEditandoPlantilla = true;
		}

		// En el multibox de la tramitacion agrupada se han establecido los IDs
		// como 'ID_Fase:ID_Tramite' siendo el ID_Tramite = ENTITY_NULLREGKEYID
		// cuando el tramite seleccionado no existe en la fase
		String[] stageTaskIds = frm.getMultibox();
		if (!isEditandoPlantilla){
			frm.setMultiboxString(ArrayUtils.join(frm.getMultibox(), ","));
			stageTaskIds = frm.getMultibox();
		} else{
			stageTaskIds = ArrayUtils.getArray(frm.getMultiboxString(), ",");
		}

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

		// Gestion de documentos
		if (isEditandoPlantilla) {

			IGenDocAPI documentsAPI = invesFlowAPI.getGenDocAPI();

			String sTemplateName = documentsAPI.getTemporaryTemplate(Integer.parseInt(frm
				.getTemplate()));
			frm.setFile(sTemplateName);
		}

		// Redireccion a la pantalla de generacion de documentos
		return mapping.findForward("success");

		/*
		HashSet ignoredParams = new HashSet();
		ignoredParams.add(ActionsConstants.PARAM_FORM_REFRESHER);
		return composeActionForward(form,"/showBatchTask.do",ignoredParams);
		*/
	}

}