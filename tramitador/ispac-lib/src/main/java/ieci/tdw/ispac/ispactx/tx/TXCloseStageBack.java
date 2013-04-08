package ieci.tdw.ispac.ispactx.tx;

import java.util.Map;

import ieci.tdw.ispac.api.IBPMAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.procedure.PNodoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXHitoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispactx.TXProcedure;
import ieci.tdw.ispac.ispactx.TXProcedureMgr;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;

public class TXCloseStageBack extends TXCloseStage {

	/**
	 * Parámetros para el contexto de las reglas.
	 */
	private final Map params;

	public TXCloseStageBack(int nIdStage) {
		this(nIdStage, null);
	}

	public TXCloseStageBack(int nIdStage, Map params) {
		super(nIdStage);
		this.params = params;
	}

	public TXCloseStageBack(int nIdStage, boolean endTasks) {
		this(nIdStage, endTasks, null);
	}	

	public TXCloseStageBack(int nIdStage, boolean endTasks, Map params) {
		super(nIdStage, endTasks);
		this.params = params;
	}	

	protected void validate(ClientContext cs, TXTransactionDataContainer dtc, TXProcesoDAO process,
			TXFaseDAO stage) throws ISPACException {
		
		IBPMAPI bpmAPI = dtc.getBPMAPI();
		TXProcedure procedure=TXProcedureMgr.getInstance().getProcedure(cs,process.getIdProcedure());
		//PFaseDAO pStage = procedure.getStageDAO(stage.getInt("ID_FASE"));
		PNodoDAO pNode = procedure.getNode(cs.getConnection(), stage.getInt("ID_FASE"));
		
		
		String previousStageUID = bpmAPI.getPreviousStage(process.getString("ID_PROCESO_BPM"), pNode.getString("ID_NODO_BPM"));
		if (StringUtils.isEmpty(previousStageUID)) {
			if (stage.getInt("TIPO") == IStage.STAGE_TYPE)
				throw new ISPACInfo("exception.expedients.previousStage.noStage");
			else 
				throw new ISPACInfo("exception.subprocess.previousActivitiy.noActivity");
		}
	}

	protected void executeEvents(ClientContext cs, TXProcesoDAO process, TXFaseDAO stage) throws ISPACException {
		
		EventManager eventmgr=new EventManager(cs, params);
		// Se construye el contexto de ejecución de scripts.
		eventmgr.getRuleContextBuilder().addContext(process);
		eventmgr.getRuleContextBuilder().addContext(stage);

		int eventObjectType = EventsDefines.EVENT_OBJ_STAGE;
		if (stage.isActivity())
			eventObjectType = EventsDefines.EVENT_OBJ_ACTIVITY;
		
		//Ejecutar evento de sistema al cerrar fase por retroceder fase / actividad
		eventmgr.processSystemEvents(eventObjectType, EventsDefines.EVENT_EXEC_END_REDEPLOY);

		//Ejecutar evento al cerrar fase por retroceder fase / actividad
		eventmgr.processEvents(eventObjectType, stage.getInt("ID_FASE"), EventsDefines.EVENT_EXEC_END_REDEPLOY);
	}
	
	protected void executeAfterEvents(ClientContext cs, TXProcesoDAO process, TXFaseDAO stage) throws ISPACException {
		
		EventManager eventmgr=new EventManager(cs, params);
		// Se construye el contexto de ejecución de scripts.
		eventmgr.getRuleContextBuilder().addContext(process);
		eventmgr.getRuleContextBuilder().addContext(stage);

		int eventObjectType = EventsDefines.EVENT_OBJ_STAGE;
		if (stage.isActivity())
			eventObjectType = EventsDefines.EVENT_OBJ_ACTIVITY;
		
		//Ejecutar evento de sistema tras cerrar fase por retroceder fase / actividad
		eventmgr.processSystemEvents(eventObjectType, EventsDefines.EVENT_EXEC_END_AFTER_REDEPLOY);

		//Ejecutar evento tras cerrar fase por retroceder fase / actividad
		eventmgr.processEvents(eventObjectType, stage.getInt("ID_FASE"), EventsDefines.EVENT_EXEC_END_AFTER_REDEPLOY);
	}
	
	/*
	protected void executeEvents(ClientContext cs, TXProcesoDAO process, TXFaseDAO stage) throws ISPACException {
		executeEvents(cs, process, stage, EventsDefines.EVENT_OBJ_STAGE);
	}
	*/

	protected TXHitoDAO insertMilestone(TXTransactionDataContainer dtc, TXProcesoDAO process, int nIdPCDStage) throws ISPACException {
		
		int milestoneType = TXConstants.MILESTONE_STAGE_END_RELOCATED;
		if (process.isSubProcess())
			milestoneType = TXConstants.MILESTONE_ACTIVITY_END_RELOCATED;
		
		TXHitoDAO hito=dtc.newMilestone(process.getKeyInt(),nIdPCDStage,0, milestoneType);
		return hito;
	}

}
