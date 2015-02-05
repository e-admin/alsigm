package ieci.tdw.ispac.ispactx.tx;

import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.errors.ISPACLockedObject;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.tx.TXFaseDAO;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;

public class TXRedeployPreviousStage implements ITXAction {
	
//	TODO Eliminar	
	private final int mnIdStage;

	public TXRedeployPreviousStage(int nIdStage)
	{
		super();
		mnIdStage=nIdStage;
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispactx.tx.TXAction#run(ieci.tdw.ispac.ispactx.session.ClientSession, ieci.tdw.ispac.ispactx.tx.TXTransactionDataContainer)
	 */
	public void run(ClientContext cs, TXTransactionDataContainer dtc,ITXTransaction itx)
			throws ISPACException
	{
//		EventManager eventmgr=new EventManager(cs);
//
//		TXFaseDAO stage=dtc.getStage(mnIdStage);
//		int nIdProc=stage.getInt("ID_EXP");
//		int nIdPCDStage=stage.getInt("ID_FASE");
//		Date stagedeadline=stage.getDate("FECHA_LIMITE");
//
//		TXProcesoDAO exped= dtc.getProcess(nIdProc);
//
//		if ((exped.getInt("ESTADO")==TXConstants.STATUS_CANCELED)||
//			(stage.getInt("ESTADO")==TXConstants.STATUS_CANCELED)) {
//			throw new ISPACInfo("exception.expedients.previousStage.statusCanceled");
//		}
//
//		// La fase no debe tener trámites abiertos
//		if (stage.existTask(cs.getConnection())) {
//			throw new ISPACOpenTasksInfo("exception.expedients.closeStage.openTask");
//		}
//		
//		// Comprobar si hay fases anteriores
//		TXProcedure procedure=TXProcedureMgr.getInstance().getProcedure(cs,exped.getIdProcedure());
//		TXStateTable statetbl=procedure.getStateTable();
//		Set previousStages=statetbl.previousStages(nIdPCDStage);
//		
//		if (previousStages.isEmpty()) {
//			throw new ISPACInfo("exception.expedients.previousStage.noStage");
//		}
//
//		// Se construye el contexto de ejecución de scripts.
//		eventmgr.getRuleContextBuilder().addContext(exped);
//		eventmgr.getRuleContextBuilder().addContext(stage);
//
//		//Ejecutar evento de sistema al cerrar fase por retroceder fase
//		eventmgr.processSystemEvents(EventsDefines.EVENT_OBJ_STAGE,
//									 EventsDefines.EVENT_EXEC_END_REDEPLOY);
//
//		//Ejecutar evento al cerrar fase por retroceder fase
//		eventmgr.processEvents(EventsDefines.EVENT_OBJ_STAGE,
//							   nIdPCDStage,
//							   EventsDefines.EVENT_EXEC_END_REDEPLOY);
//
//		//Se elimina la fase cerrada
//		dtc.deleteStage(mnIdStage);
//
//		TXHitoDAO hito=dtc.newMilestone(exped.getKeyInt(),nIdPCDStage,0,
//				TXConstants.MILESTONE_STAGE_END_RELOCATED);
//
//		hito.set("FECHA_LIMITE",stagedeadline);
//
//		// Activar las fases anteriores según lo indicado en la definición
//		// del procedimiento
//		Iterator it=previousStages.iterator();
//		
//		TXDAOGen genDAO= new TXDAOGen(cs,eventmgr);
//		TXNodeActivationManager nodeActMgr=new TXNodeActivationManager(genDAO,procedure,dtc);
//		nodeActMgr.activateNodes(nIdPCDStage,it,exped);
//		
//		//Ejecutar evento al reubicar el expediente.
//		eventmgr.processEvents(EventsDefines.EVENT_OBJ_PROCEDURE,
//							   exped.getInt("ID_PCD"),
//							   EventsDefines.EVENT_EXEC_REDEPLOY);
//		
//		it = previousStages.iterator();
//		while (it.hasNext()) {
//			
//			PFaseDAO pstage = procedure.getStageDAO(((Integer)it.next()).intValue());
//			
//			//Ejecutar evento al reubicar cada fase
//			eventmgr.processEvents(EventsDefines.EVENT_OBJ_STAGE,
//								   pstage.getKeyInt(),
//								   EventsDefines.EVENT_EXEC_REDEPLOY);
//
//			dtc.newMilestone(exped.getKeyInt(),pstage.getKeyInt(),0,
//					TXConstants.MILESTONE_EXPED_RELOCATED);	
//		}
	}

	public Object getResult(String nameResult)
	{
		return null;
	}

	public void lock(ClientContext cs, TXTransactionDataContainer dtc)
			throws ISPACException
	{
		TXFaseDAO stage=dtc.getStage(mnIdStage);
		
		try {
			dtc.getLockManager().lockProcess(stage.getInt("ID_EXP"));
			dtc.getLockManager().lockStage(mnIdStage);
		}
		catch (ISPACLockedObject ilo) {
			throw new ISPACInfo("exception.expedients.previousStage.statusBlocked");
		}
	}
	
}