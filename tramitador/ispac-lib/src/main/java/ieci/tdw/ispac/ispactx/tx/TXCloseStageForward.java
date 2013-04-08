package ieci.tdw.ispac.ispactx.tx;

import java.util.Map;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.tx.TXFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXHitoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;

public class TXCloseStageForward extends TXCloseStage {

	/**
	 * Parámetros para el contexto de las reglas.
	 */
	private final Map params;

	public TXCloseStageForward(int nIdStage) {
		this(nIdStage, null);
	}

	public TXCloseStageForward(int nIdStage, Map params) {
		super(nIdStage);
		this.params = params;
	}

	public TXCloseStageForward(int nIdStage, boolean endTasks) {
		this(nIdStage, endTasks, null);
	}

	public TXCloseStageForward(int nIdStage, boolean endTasks, Map params) {
		super(nIdStage, endTasks);
		this.params = params;
	}

	protected void executeEvents(ClientContext cs, TXProcesoDAO process, TXFaseDAO stage) throws ISPACException {
		
		EventManager eventmgr=new EventManager(cs, params);
		// Se construye el contexto de ejecución de scripts.
		eventmgr.getRuleContextBuilder().addContext(process);
		eventmgr.getRuleContextBuilder().addContext(stage);

		int eventObjectType = EventsDefines.EVENT_OBJ_STAGE;
		if (stage.isActivity())
			eventObjectType = EventsDefines.EVENT_OBJ_ACTIVITY;
		
		//Ejecutar evento de sistema al cerrar fase / actividad
		eventmgr.processSystemEvents(eventObjectType, EventsDefines.EVENT_EXEC_END);

		//Ejecutar evento al cerrar fase / actividad
		eventmgr.processEvents(eventObjectType, stage.getInt("ID_FASE"), EventsDefines.EVENT_EXEC_END);
	}
	
	protected void executeAfterEvents(ClientContext cs, TXProcesoDAO process, TXFaseDAO stage) throws ISPACException {
		
		EventManager eventmgr=new EventManager(cs, params);
		// Se construye el contexto de ejecución de scripts.
		eventmgr.getRuleContextBuilder().addContext(process);
		eventmgr.getRuleContextBuilder().addContext(stage);

		int eventObjectType = EventsDefines.EVENT_OBJ_STAGE;
		if (stage.isActivity())
			eventObjectType = EventsDefines.EVENT_OBJ_ACTIVITY;
		
		//Ejecutar evento de sistema tras cerrar fase / actividad
		eventmgr.processSystemEvents(eventObjectType, EventsDefines.EVENT_EXEC_END_AFTER);

		//Ejecutar evento tras cerrar fase / actividad
		eventmgr.processEvents(eventObjectType, stage.getInt("ID_FASE"), EventsDefines.EVENT_EXEC_END_AFTER);
	}
	
	protected TXHitoDAO insertMilestone(TXTransactionDataContainer dtc, TXProcesoDAO process, int nIdPCDStage) throws ISPACException {
		
		int milestoneType = TXConstants.MILESTONE_STAGE_END;
		if (process.isSubProcess())
			milestoneType = TXConstants.MILESTONE_ACTIVITY_END;
		
		TXHitoDAO hito=dtc.newMilestone(process.getKeyInt(),nIdPCDStage,0, milestoneType);
		return hito;
	}

}
