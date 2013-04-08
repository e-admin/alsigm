package ieci.tdw.ispac.ispactx.tx;

import ieci.tdw.ispac.api.IBPMAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IRespManagerAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.ITask;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.api.rule.RuleProperties;
import ieci.tdw.ispac.ispaclib.bpm.BpmUIDs;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PTramiteDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXHitoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXTramiteDAO;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispactx.TXDAOGen;
import ieci.tdw.ispac.ispactx.TXProcedure;
import ieci.tdw.ispac.ispactx.TXProcedureMgr;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;
import ieci.tdw.ispac.resp.ResponsibleHelper;

import java.util.Iterator;
import java.util.Map;


public class TXCreateTask implements ITXAction {
	
	private final int mnIdStage;
	private final int mnIdTaskPCD;
	private int mnIdTask;
	
	private final int mnIdProcedure;
	private final String mnumexp;
	private int mnIdActivity;
	
	/**
	 * Parámetros para el contexto de las reglas.
	 */
	private final Map mparams;
	

	/**
	 *
	 */
	public TXCreateTask(int nIdStage,int nIdTaskPCD) {
		this(0, nIdStage, nIdTaskPCD, null, null);
	}

	public TXCreateTask(int nIdStage,int nIdTaskPCD, Map params) {
		this(0, nIdStage, nIdTaskPCD, null, params);
	}

	public TXCreateTask(int nIdProcedure, int nIdStage, int nIdTaskPCD, String numExp) {
		this(nIdProcedure, nIdStage, nIdTaskPCD, numExp, null);
	}

	public TXCreateTask(int nIdProcedure, int nIdStage, int nIdTaskPCD, String numExp, Map params) {
		super();
		
		this.mnIdStage=nIdStage;
		this.mnIdTaskPCD=nIdTaskPCD;
		this.mnIdTask=0;

		this.mnIdProcedure = nIdProcedure;
		this.mnIdActivity = 0;
		this.mnumexp = numExp;

		this.mparams = params;
	}

	public void run(ClientContext cs, TXTransactionDataContainer dtc,ITXTransaction itx) 
			throws ISPACException {
		
		EventManager eventmgr=new EventManager(cs, mparams);
		TXDAOGen genDAO= new TXDAOGen(cs,eventmgr);


		TXFaseDAO stage=dtc.getStage(mnIdStage);
		int nIdProc=stage.getInt("ID_EXP");
		int nIdPCDStage=stage.getInt("ID_FASE");

		TXProcesoDAO process= dtc.getProcess(nIdProc);
		TXProcedure procedure=TXProcedureMgr.getInstance().getProcedure(cs,process.getIdProcedure());

		PTramiteDAO pcdtask=procedure.getTaskDAO(mnIdTaskPCD);
		
		if (process.getInt("ESTADO")==TXConstants.STATUS_CLOSED) {
			throw new ISPACInfo("exception.expedients.createTask.statusClosed",
					new String[]{pcdtask.getString("NOMBRE"), process.getString("NUMEXP")});
		} else if (process.getInt("ESTADO")==TXConstants.STATUS_CANCELED) {
			throw new ISPACInfo("exception.expedients.createTask.statusCanceled", 
					new String[]{pcdtask.getString("NOMBRE"), process.getString("NUMEXP")});
		} else if (process.getInt("ESTADO")==TXConstants.STATUS_ARCHIVED) {
			throw new ISPACInfo("exception.expedients.createTask.statusArchived", 
					new String[]{pcdtask.getString("NOMBRE"), process.getString("NUMEXP")});
		} 

		//-----
		//BPM
		//----
		IBPMAPI bpmAPI = dtc.getBPMAPI();
		IInvesflowAPI invesFlowAPI = cs.getAPI();
		IRespManagerAPI respManagerAPI=invesFlowAPI.getRespManagerAPI();
		//Se calcula el responsable del trámite
		String taskRespId = ResponsibleHelper.calculateTaskResp(eventmgr, pcdtask,stage,process , cs);
		
		String nombreRespId=((Responsible)respManagerAPI.getResp(taskRespId)).getName();
		String taskActivityRespId = null;
		
		//boolean isSubProcess = false;
		boolean isSubProcess = mnIdProcedure != 0;
		int taskType = ITask.SIMPLE_TASK_TYPE;
		String idSubPcdBPM = null;
		//Si el tramite es complejo se calcula el responsable a asignar a la actividad inicial del subproceso
		//if (StringUtils.isNotEmpty(pcdtask.getString("ID_PCD_SUB")) && !StringUtils.equals(pcdtask.getString("ID_PCD_SUB"), "0")){
		if (isSubProcess){
			taskType = ITask.COMPLEX_TASK_TYPE;

			TXProcedure subProcess=TXProcedureMgr.getInstance().getProcedure(cs,mnIdProcedure);
			Iterator it = subProcess.getStateTable().getStartStages().iterator();
			
			if (!it.hasNext())
				throw new ISPACException("No se han encontrado actividades para el subproceso '" + pcdtask.getString("ID_PCD_SUB") + "'");
			int activityId  = ((Integer)it.next()).intValue();
			PFaseDAO pActivity = subProcess.getStageDAO(activityId);
			taskActivityRespId = ResponsibleHelper.calculateTaskActivityResp(eventmgr, pActivity, pcdtask, process, cs, taskRespId );
			
			IItem itemSubprocedure = invesFlowAPI.getProcedure(pcdtask.getInt("ID_PCD_SUB"));
			idSubPcdBPM = itemSubprocedure.getString("ID_PCD_BPM");
		}
		
		//BpmUIDs bpmUIDs = bpmAPI.instanceTask(pcdtask.getString("ID_TRAMITE_BPM"), pcdtask.getString("ID_PCD_SUB"), taskRespId, taskActivityRespId,String.valueOf(stage.getKeyInt()));
		BpmUIDs bpmUIDs = bpmAPI.instanceTask(pcdtask.getString("ID_TRAMITE_BPM"), idSubPcdBPM, taskRespId, taskActivityRespId,stage.getString("ID_FASE_BPM"));

		//Identificador de tramite creado
		String taskUID = bpmUIDs.getTaskUID();
		//Identificador de subproceso instanciado
		String subProcessUID = bpmUIDs.getSubProcessUID();
		//Identificador de actividad creada
		String activityUID = bpmUIDs.getActivityUID();
		

		TXTramiteDAO task=dtc.newTask();
		genDAO.instance(pcdtask,task,stage,process);

		//Establecemos el UID del tramite instanciado retornado por el BPM
		if (taskUID == null)
			taskUID = ""+task.getKeyInt();
		task.set("ID_TRAMITE_BPM", taskUID);
		task.set("ID_RESP", taskRespId);
		task.set("RESP",  nombreRespId);
		task.set("TIPO", taskType);
		//Si es un tramite complejo (subproceso) habrá que instanciar el subproceso
		if (isSubProcess){
			int[] ids = itx.createSubProcess(pcdtask.getInt("ID_PCD_SUB"), mnumexp, subProcessUID, activityUID, taskRespId, taskActivityRespId);
			task.set("ID_SUBPROCESO", (int)ids[0]);
			mnIdActivity = (int)ids[1];
			//			//Obtenemos la actividad inicia
			//			Iterator it =dtc.getStages(); 
			//			while (it.hasNext()){
			//				TXFaseDAO _stage = (TXFaseDAO)((Map.Entry)it.next()).getValue();
			//				if (_stage.getInt("ID_EXP") == idSubProcess){
			//					mnIdActivity = _stage.getKeyInt();
			//					break;
			//				}
			
			eventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_SUBPROCESS, ""+ids[0]);
			
		}

		// Guardar la información del trámite
		task.store(cs);

		dtc.createTaskEntity(task);

		mnIdTask=task.getKeyInt();

		// Insertar el hito
		TXHitoDAO hito = dtc.newMilestone(process.getKeyInt(), nIdPCDStage,
				mnIdTaskPCD, TXConstants.MILESTONE_TASK_START);
		hito.set("INFO", composeInfo());

		// Se construye el contexto de ejecución de scripts.
		eventmgr.getRuleContextBuilder().addContext(process);
		eventmgr.getRuleContextBuilder().addContext(task);

		//Ejecutar eventos de sistema de creación de trámite.
		eventmgr.processSystemEvents(EventsDefines.EVENT_OBJ_TASK,
		        					 EventsDefines.EVENT_EXEC_START);


		//Ejecutar evento al cancelar trámite.
		eventmgr.processEvents(	EventsDefines.EVENT_OBJ_TASK,
								mnIdTaskPCD,
								EventsDefines.EVENT_EXEC_START);
	}

	
	private String composeInfo(){
		return new StringBuffer()
			.append("<?xml version='1.0' encoding='ISO-8859-1'?>")
			.append("<infoaux><id_tramite>")
			.append(mnIdTask)
			.append("</id_tramite></infoaux>")
			.toString();
	}
	
	public Object getResult(String nameResult)
	{
		if (mnIdActivity != 0)
			return new Integer(mnIdActivity);
		return new Integer(mnIdTask);
	}


	public void lock(ClientContext cs, TXTransactionDataContainer dtc)
			throws ISPACException
	{
		TXFaseDAO stage=dtc.getStage(mnIdStage);
		dtc.getLockManager().lockProcess(stage.getInt("ID_EXP"));
	}
}
