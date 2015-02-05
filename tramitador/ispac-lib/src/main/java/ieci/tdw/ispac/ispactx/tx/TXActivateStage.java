package ieci.tdw.ispac.ispactx.tx;

import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;


public class TXActivateStage implements ITXAction
{

	private final int mnIdProc;

	/**
	 *
	 */
	public TXActivateStage(int nIdProc, int nIdStagePCD)
	{
		mnIdProc=nIdProc;
	}

	public void run(ClientContext cs, TXTransactionDataContainer dtc,ITXTransaction itx)
	throws ISPACException
	{
		
		//TODO NO ESTA EN USO, INDEPENDIZAR DEL BPM
//		EventManager eventmgr=new EventManager(cs);
//		TXDAOGen genDAO= new TXDAOGen(cs,eventmgr);
//
//		TXProcesoDAO exped= dtc.getProcess(mnIdProc);
//
//		TXProcedure procedure=TXProcedureMgr.getInstance().getProcedure(cs,exped.getIdProcedure());
//		PFaseDAO mpStage=procedure.getStageDAO(mnIdStagePCD);
//
//
//		//Se necesitan todas las fases del expediente en TXTransactionDataContainer
//		//para que testStageOpen funcione correctamente.
////		dtc.loadProcessStages(mnIdProc);
////		if (dtc.testStageOpen(exped.getKeyInt(),mpStage.getKeyInt()))
////		{
////			return;
////		}
//
//
//		TXNodeActivationManager nodeActMgr=new TXNodeActivationManager(genDAO,procedure,dtc);
//		if (nodeActMgr.testStageOpen(exped.getKeyInt(),mpStage.getKeyInt()))
//			return;
//
//		TXFaseDAO stage=dtc.newStage();
//		genDAO.instance(mpStage,stage,exped);
//
//		// Se construye el contexto de ejecución de scripts.
//		eventmgr.getRuleContextBuilder().addContext(exped);
//		eventmgr.getRuleContextBuilder().addContext(stage);
//
//		//Ejecutar evento al reubicar fase.
//		eventmgr.processEvents(	EventsDefines.EVENT_OBJ_STAGE,
//								mnIdStagePCD,
//								EventsDefines.EVENT_EXEC_REDEPLOY);
//
//
//		//Ejecutar evento de sistema al iniciar fase.
//		eventmgr.processSystemEvents(EventsDefines.EVENT_OBJ_STAGE,
//								EventsDefines.EVENT_EXEC_START);
//
//
//		//Ejecutar evento al iniciar fase.
//		eventmgr.processEvents(	EventsDefines.EVENT_OBJ_STAGE,
//								mnIdStagePCD,
//								EventsDefines.EVENT_EXEC_START);
//
//
//		dtc.newMilestone(exped.getKeyInt(),mnIdStagePCD,0,
//				TXConstants.MILESTONE_STAGE_ACTIVATED);

	}

	public Object getResult(String nameResult)
	{
		return null;
	}

	public void lock(ClientContext cs, TXTransactionDataContainer dtc)
			throws ISPACException
	{
		dtc.getLockManager().lockProcess(mnIdProc);
	}
}
