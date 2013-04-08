package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.form.BatchForm;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.context.NextActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.util.CollectionUtils;

public class CreateTaskAction extends BaseAction
{
	public ActionForward executeAction(ActionMapping mapping, 
									   ActionForm form, 
									   HttpServletRequest request, 
									   HttpServletResponse response, 
									   SessionAPI session) throws Exception {
		
		ClientContext cct = session.getClientContext();
		
		IInvesflowAPI invesflowAPI = session.getAPI();
		
		// Estado del contexto de tramitación
		IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(cct);
		IState currentstate = managerAPI.currentState(getStateticket(request));
		IEntitiesAPI entitiesAPI = invesflowAPI.getEntitiesAPI(); 
		
		// formulario asociado
		BatchForm batchForm = (BatchForm)form;
		String taskPcdId = request.getParameter("taskPcdId");
		String batchTaskPcdId = request.getParameter("batchTaskId");
		String stageid = null;
		
		//La llamada no es desde tramitacion agrupada
		if (batchTaskPcdId == null) {
			stageid = request.getParameter("stageId");
		}
		
		//Actualizo stagePcdId con el valor actual
	
		request.getSession().setAttribute("stagePcdIdActual", cct.getStateContext().getStagePcdId()+"");

    	// recogemos los identificadores seleccionados (solo los hay en tramitacion agrupada)
		String[] stageids = batchForm.getMultibox();
		if (stageids == null)
			stageids = new String[0];

		//String[] newTasks = new String[stageids.length];
		
    	int nIdTaskPCD = Integer.parseInt(taskPcdId);
    	int nIdStage = 0;
    	int nIdProcess = 0;
    	int nNewTask = 0;
    	int nNewActivity = 0;
    	int nIdPcd = 0;
    	String numExp = null;

    	ITXTransaction tx = invesflowAPI.getTransactionAPI();
    	if (stageid != null) { // cuando ha llegado un sólo identificador
    		stageids = new String[1];
			stageids[0] = stageid;
		}

    	// Dependencias del trámite en la fase del procedimiento
    	List taskPcdDependencies = invesflowAPI.getProcedureAPI().getTaskDependencies(nIdTaskPCD).toList();
    	
    	IItem batchTask = null;
    	if (batchTaskPcdId!=null){
        	//que todos los expedientes se encuetran en la fase de la tramitacion agrupada
    		batchTask = invesflowAPI.getBatchTask(new Integer(batchTaskPcdId).intValue());
    		String idFaseTramAgrupada = batchTask.getString("ID_FASE");
    		
    		List expErrors = new ArrayList(); 
    		List respErrors = new ArrayList();
    		List depErrors = new ArrayList();
    		
	    	for (int i = 0; i < stageids.length; i++) {
	    		IItem stageExpediente = invesflowAPI.getStage(Integer.parseInt(stageids[i]));
	    		
	    		// Comprobar si se tiene responsabilidad sobre la fase del expediente
	    		String idResp = stageExpediente.getString("ID_RESP");
	    		if (!invesflowAPI.getWorkListAPI().isInResponsibleList(idResp, stageExpediente)) {
	    			respErrors.add(stageExpediente.getString("NUMEXP"));
	    		}
	    		
	    		String idFase = stageExpediente.getString("ID_FASE");
	    		if (!idFaseTramAgrupada.equals(idFase)){
	    			expErrors.add(stageExpediente.getString("NUMEXP"));
	    		}
	    		
				// Comprobar las dependencias del trámite
				if (!CollectionUtils.isEmpty(taskPcdDependencies)) {
					
			        // Trámites cerrados en la misma fase
					Map closedTaskMap = entitiesAPI.queryEntities(
							SpacEntities.SPAC_DT_TRAMITES,
							new StringBuffer("WHERE NUMEXP = '")
									.append(DBUtil.replaceQuotes(stageExpediente.getString("NUMEXP")))
									.append("' AND ID_FASE_PCD = ")
									.append(stageExpediente.getInt("ID_FASE"))
									.append(" AND ESTADO = ").append(3)/* CERRADO */
									.toString()).toMapStringKey("ID_TRAM_PCD");

					for (int depCont = 0; depCont < taskPcdDependencies.size(); depCont++) {
						IItem taskDependency = (IItem) taskPcdDependencies.get(depCont);
						int parentTaskId = taskDependency.getInt("SPAC_P_DEP_TRAMITES:ID_TRAMITE_PADRE");
						if (closedTaskMap.get(String.valueOf(parentTaskId)) == null) {
							depErrors.add(stageExpediente.getString("NUMEXP"));
							break;
						}
					}
				}
	    	}
	    	
	    	// Expedientes con fase activa diferente a la de la tramitación agrupada
	    	if (expErrors.size() > 0 ) {
	    		
	    		StringBuffer str = new StringBuffer();
	    		for (Iterator iter = expErrors.iterator(); iter.hasNext();) {
	    			if (str.length()>0) str.append(", ");
	    			str.append((String) iter.next());
				}

	    		throw new ISPACInfo("exception.expedients.batchTask.createTask", new String[] {str.toString(), batchTask.getString("FASE")}, false);
	    	}

	    	// Expedientes sobre los que no se tiene responsabilidad
	    	if (respErrors.size() > 0 ) {
	    		
	    		StringBuffer str = new StringBuffer();
	    		for (Iterator iter = respErrors.iterator(); iter.hasNext();) {
	    			if (str.length()>0) str.append(", ");
	    			str.append((String) iter.next());
				}

	    		throw new ISPACInfo("exception.expedients.batchTask.createTask.noResp", new String[] {str.toString()}, false);
	    	}

	    	// Expedientes que no cumplen las dependencias del trámite
	    	if (depErrors.size() > 0 ) {
	    		
	    		StringBuffer str = new StringBuffer();
	    		for (Iterator iter = depErrors.iterator(); iter.hasNext();) {
	    			if (str.length()>0) str.append(", ");
	    			str.append((String) iter.next());
				}

	    		throw new ISPACInfo("exception.expedients.batchTask.createTask.dependencies", new String[] {str.toString()}, false);
	    	}
    	}

    	// Información del trámite
		IItem taskPcd = invesflowAPI.getProcedureTaskPCD(nIdTaskPCD);
		
    	boolean creandoUnSoloTramite = (stageids.length == 1);
    	for (int i = 0; i < stageids.length; i++) {
    		
    		nIdStage = Integer.parseInt(stageids[i]);

    		//caso creacion desde el expediente (NO TRAMITACION AGRUPADA)
    		IItem stage = invesflowAPI.getStage(nIdStage);
    		nIdPcd = stage.getInt("ID_PCD");
    		numExp = stage.getString("NUMEXP");
			if (creandoUnSoloTramite) {
				//nIdStage = Integer.parseInt(stageids[0]);
				nIdProcess = stage.getInt("ID_EXP");
			}
			
			//Comprobamos si el tramite a crear es simple o complejo
			String idSubProcess = taskPcd.getString("ID_PCD_SUB");
			if (StringUtils.isNotEmpty(idSubProcess) && !StringUtils.equals(idSubProcess, "0") ){
				//creacion en el subproceso
				nNewActivity = tx.createTask(nIdPcd, nIdStage, nIdTaskPCD, numExp);
				
			}
			else {
				//creacion de tramite normal
				nNewTask = tx.createTask(nIdStage, nIdTaskPCD);	
			}
			
//			if (newTasks.length != 0)
//			    newTasks[i] = ""+nNewTask;
		}

    	//actualizar la tramitacion agrupada (si viene de una tramitacion agrupada)
		if (batchTaskPcdId!=null){
			//llamada desde una tarea agrupada =>establecemos la fase
			//IItem batchTask = invesflowAPI.getBatchTask(new Integer(batchTaskPcdId).intValue());
			batchTask.set("ID_ULTIMO_TRAMITE", nIdTaskPCD);
			tx.updateBatchTask(batchTask);
			//return NextActivity.afterCreateBatchTask(batchTaskPcdId, mapping);
			return mapping.findForward("success");
		}

    	if (creandoUnSoloTramite) {
			if (nNewActivity != 0)
				return NextActivity.afterCreateSubProcess(nIdProcess, nNewActivity, invesflowAPI,
						mapping);
			return NextActivity.afterCreateTask(nIdProcess, nIdStage, nNewTask, invesflowAPI,
					mapping);

    	}/* else if (nIdStage != 0) {
			return NextActivity.afterCreateBatchTask(batchTaskPcdId, mapping);
		}*/

		return NextActivity.refresh(currentstate,mapping);
	}
}
