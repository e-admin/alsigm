package ieci.tdw.ispac.ispactx.tx;

import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;

/**
 * @author juanin
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TXRedeployProcess implements ITXAction
{

	private final int mnIdProc;
	//private final int mnIdPCDStage;

	/**
	 *
	 */
	public TXRedeployProcess(int nIdProc, int nIdPCDStage)
	{
		mnIdProc=nIdProc;
		//mnIdPCDStage=nIdPCDStage;
	}

	public void run(ClientContext cs, TXTransactionDataContainer dtc,
			ITXTransaction itx) throws ISPACException
	{
		//TODO NO ESTA EN USO, INDEPENDIZAR DEL BPM
//		TXProcesoDAO process= dtc.getProcess(mnIdProc);
//
//	    //La fase no debe tener trámites abiertos.
//		TXFaseDAO currentStage = new TXFaseDAO(cnt);
//		currentStage.load(cs.getConnection(), "WHERE ID_EXP = '"+process.getKeyInt()+"'");
//		if (currentStage.existTask(cs.getConnection()))
//			throw new ISPACInfo("exception.expedients.closeStage.openTask", new String[]{process.getString("NUMEXP")});		
//	    
//	    
//	    EventManager eventmgr=new EventManager(cs);
//		TXDAOGen genDAO= new TXDAOGen(cs,eventmgr);
//
//		dtc.deleteProcessStages(mnIdProc);
//		dtc.deleteProcessTasks(mnIdProc);
//		dtc.deleteProcessSyncNodes(mnIdProc);
//
//		process.deleteStages(cs.getConnection());
//		process.deleteTasks(cs.getConnection());
//		process.deleteSyncNodes(cs.getConnection());
//
//		process.set("ESTADO",TXConstants.STATUS_OPEN);
//		process.set("FECHA_CIERRE",(Object)null);
//
//		TXProcedure procedure=TXProcedureMgr.getInstance().getProcedure(cs,process.getIdProcedure());
//		PFaseDAO mpStage=procedure.getStageDAO(mnIdPCDStage);
//
//		TXFaseDAO stage=dtc.newStage();
//
//		//Ejecutar evento al iniciar fase se realiza en genDAO.instance();
//		genDAO.instance(mpStage,stage,process);
//
//		// Se construye el contexto de ejecución de scripts.
//		eventmgr.getRuleContextBuilder().addContext(process);
//		eventmgr.getRuleContextBuilder().addContext(stage);
//
//		int eventObjectType = EventsDefines.EVENT_OBJ_PROCEDURE;
//		if (process.isSubProcess())
//			eventObjectType = EventsDefines.EVENT_OBJ_SUBPROCEDURE;
//		//Ejecutar evento al reubicar el expediente.
//		eventmgr.processEvents(	eventObjectType, process.getInt("ID_PCD"), EventsDefines.EVENT_EXEC_REDEPLOY);
//
//		eventObjectType = EventsDefines.EVENT_OBJ_STAGE;
//		if (stage.isActivity())
//			eventObjectType = EventsDefines.EVENT_OBJ_ACTIVITY;
//		//Ejecutar evento al reubicar fase.
//		eventmgr.processEvents(	eventObjectType, mnIdPCDStage, EventsDefines.EVENT_EXEC_REDEPLOY);
//
//		int milestoneType = TXConstants.MILESTONE_EXPED_RELOCATED;
//		if (process.isSubProcess())
//			milestoneType = TXConstants.MILESTONE_SUBPROCESS_RELOCATED;
//		dtc.newMilestone(process.getKeyInt(),mnIdPCDStage,0, milestoneType);
	}

	public Object getResult(String nameResult)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispactx.ITXAction#lock(ieci.tdw.ispac.ispaclib.context.ClientContext, ieci.tdw.ispac.ispactx.TXTransactionDataContainer)
	 */
	public void lock(ClientContext cs, TXTransactionDataContainer dtc)
			throws ISPACException
	{
		dtc.getLockManager().lockProcess(mnIdProc);
	}
}
