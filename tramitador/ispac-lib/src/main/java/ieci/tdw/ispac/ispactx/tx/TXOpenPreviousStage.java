package ieci.tdw.ispac.ispactx.tx;

import ieci.tdw.ispac.api.IBPMAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaclib.bpm.BpmUIDs;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PNodoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispactx.TXDAOGen;
import ieci.tdw.ispac.ispactx.TXNodeActivationManager;
import ieci.tdw.ispac.ispactx.TXProcedure;
import ieci.tdw.ispac.ispactx.TXProcedureMgr;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;
import ieci.tdw.ispac.resp.ResponsibleHelper;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class TXOpenPreviousStage implements ITXAction {

	
	/**
	 * Identificdaor del proceso 
	 */
	private final int mnIdProcess;
	
	/**
	 * Identificador de la fase en el procedimiento usada de partida para retroceder 
	 */
	private final int mnIdPcdStage;

	/**
	 * Parámetros para el contexto de las reglas.
	 */
	private final Map mparams;

	public TXOpenPreviousStage(int nIdProcess, int nIdPcdStage) {
		this(nIdProcess, nIdPcdStage, null);
	}

	public TXOpenPreviousStage(int nIdProcess, int nIdPcdStage, Map params) {
		super();
		mnIdProcess = nIdProcess;
		mnIdPcdStage = nIdPcdStage;
		mparams = params;
	}

	public void run(ClientContext cs, TXTransactionDataContainer dtc,
			ITXTransaction itx) throws ISPACException {

		// Activar las fases anteriores según lo indicado en la definición
		// del procedimiento
		
		TXProcesoDAO process= dtc.getProcess(mnIdProcess);
		TXProcedure procedure=TXProcedureMgr.getInstance().getProcedure(cs,process.getIdProcedure());
		
		PNodoDAO pNodo = procedure.getNode(cs.getConnection(), mnIdPcdStage);
		
		EventManager eventmgr=new EventManager(cs, mparams);
		eventmgr.newContext();
		eventmgr.getRuleContextBuilder().addContext(process);

		//Obtenemos la fase previa a la actual
		IBPMAPI bpmAPI = dtc.getBPMAPI();
		String previousStageUID = bpmAPI.getPreviousStage(process.getString("ID_PROCESO_BPM"),pNodo.getString("ID_NODO_BPM"));
		
		int milestoneType = TXConstants.MILESTONE_EXPED_RELOCATED;
		if (process.isSubProcess())
			milestoneType = TXConstants.MILESTONE_SUBPROCESS_RELOCATED;
		dtc.newMilestone(process.getKeyInt(),0,0,milestoneType);	
		
		PFaseDAO previousPStage = procedure.getStageDAO(cs.getConnection(), previousStageUID);
		//Se calcula el responsable a establecer en la fase a iniciar
		String processStageRespId = ResponsibleHelper.calculateStageResp(eventmgr, previousPStage, process.getString("ID_RESP"));
		String processStageRespName = null;
		if (StringUtils.isNotBlank(processStageRespId)) {
			IResponsible responsible = cs.getAPI().getRespManagerAPI().getResp(processStageRespId);
			if (responsible != null) {
				processStageRespName = responsible.getName();
			}
		}
		
		eventmgr.newContext();
		
		//Se invoca al BPM para instanciar una fase
		BpmUIDs bpmUIDs = bpmAPI.instanceProcessStage(previousStageUID, processStageRespId, process.getString("ID_PROCESO_BPM"));
		String processStageUID = bpmUIDs.getStageUID();
		
		//Se activa la fase en SPAC
		TXDAOGen genDAO= new TXDAOGen(cs,eventmgr);
		TXNodeActivationManager nodeActMgr=new TXNodeActivationManager(genDAO,procedure,dtc);
		TXFaseDAO stageInstanced = nodeActMgr.activateNode(mnIdPcdStage,previousPStage.getKeyInt(),process,processStageRespId);
		//Si no se instancio una fase sera que ya existe una fase de ese tipo instanciada
		if (stageInstanced == null)
			return;
		if (processStageUID == null)
			processStageUID = ""+stageInstanced.getKeyInt();
		
		//Se establece el UID de la fase instanciada retornado por el BPM y el responsable en SPAC
		stageInstanced.set("ID_FASE_BPM", processStageUID);
		stageInstanced.set("RESP", processStageRespName);
		//stageInstanced.set("ID_RESP", processStageRespId);
		
		int eventObjectType = EventsDefines.EVENT_OBJ_PROCEDURE;
		if (process.isSubProcess())
			eventObjectType = EventsDefines.EVENT_OBJ_SUBPROCEDURE;
		//Ejecutar evento al reubicar el expediente.
		eventmgr.processEvents(eventObjectType, process.getInt("ID_PCD"), EventsDefines.EVENT_EXEC_REDEPLOY);
		
		eventObjectType = EventsDefines.EVENT_OBJ_STAGE;
		if(stageInstanced.isActivity())
			eventObjectType = EventsDefines.EVENT_OBJ_ACTIVITY;
		
		//Ejecutar evento al reubicar cada fase
		eventmgr.processEvents(eventObjectType, stageInstanced.getKeyInt(), EventsDefines.EVENT_EXEC_REDEPLOY);
	}
	public void lock(ClientContext cs, TXTransactionDataContainer dtc)
			throws ISPACException {
	}
	public Object getResult(String nameResult) {
		return null;
	}
}
