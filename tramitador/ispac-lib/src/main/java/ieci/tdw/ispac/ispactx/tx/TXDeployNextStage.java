/*
 * Created on 11-may-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.ispactx.tx;

import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.errors.ISPACLockedObject;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.tx.TXFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;


//TODO Eliminar

/**
 * @author juanin
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TXDeployNextStage implements ITXAction
{
	protected final int mnIdStage;

	/**
	 *
	 */
	public TXDeployNextStage(int nIdStage)
	{
		super();
		mnIdStage=nIdStage;
	}

	public TXDeployNextStage()
	{
		super();
		mnIdStage = 0;
	}

	public boolean testProcessClosed(ClientContext cs, TXTransactionDataContainer dtc,TXProcesoDAO exped)
	throws ISPACException
	{
//		int nIdProc=exped.getKeyInt();
//
//		int nCount=dtc.getProcessStages(nIdProc).size();
//		nCount+=dtc.getProcessSyncNodes(nIdProc).size();
//		nCount+=dtc.getProcessTasks(nIdProc).size();
//		return (nCount==0);
		return true;
	}

//	protected void runCloseStage(TXFaseDAO stage, IBPMAPI bpmAPI, TXTransactionDataContainer dtc, ClientContext cs)throws ISPACException{
//		//TXFaseDAO stage=dtc.getStage(mnIdStage);
//		int nIdProc=stage.getInt("ID_EXP");
//		int nIdPCDStage=stage.getInt("ID_FASE");
//		Date stagedeadline=stage.getDate("FECHA_LIMITE");
//
//		TXProcesoDAO process= dtc.getProcess(nIdProc);
//
//		if ((process.getInt("ESTADO")==TXConstants.STATUS_CANCELED)||
//			(stage.getInt("ESTADO")==TXConstants.STATUS_CANCELED)) {
//			throw new ISPACInfo("exception.expedients.nextStage.statusCanceled");
//		}
//
//		// La fase no debe tener trámites abiertos.
//		if (stage.existTask(cs.getConnection())) {
//			throw new ISPACOpenTasksInfo("exception.expedients.closeStage.openTask", new String[]{process.getString("NUMEXP")});			
//		}			
//		
//
//		//Se invoca al BPM para el cierre de la fase 
//		bpmAPI.closeProcesssStage(stage.getString("ID_FASE_BPM"));
//	
//		EventManager eventmgr=new EventManager(cs);
//		// Se construye el contexto de ejecución de scripts.
//		eventmgr.getRuleContextBuilder().addContext(process);
//		eventmgr.getRuleContextBuilder().addContext(stage);
//
//		//Ejecutar evento de sistema al cerrar fase.
//		eventmgr.processSystemEvents(EventsDefines.EVENT_OBJ_STAGE,
//								EventsDefines.EVENT_EXEC_END);
//
//		//Ejecutar evento al cerrar fase.
//		eventmgr.processEvents(EventsDefines.EVENT_OBJ_STAGE,
//								nIdPCDStage,
//								EventsDefines.EVENT_EXEC_END);
//
//		//Se elimina la fase cerrada
//		dtc.deleteStage(mnIdStage);
//
//		TXHitoDAO hito=dtc.newMilestone(process.getKeyInt(),nIdPCDStage,0,
//				TXConstants.MILESTONE_STAGE_END);
//
//		hito.set("FECHA_LIMITE",stagedeadline);
//		
//	}
//	
//	protected void runOpenStages(PFaseDAO activatorStage, TXProcesoDAO process, IBPMAPI bpmAPI, TXTransactionDataContainer dtc, ClientContext cs)throws ISPACException{
////		int nIdProc=stage.getInt("ID_EXP");
////		int nIdPCDStage=stage.getInt("ID_FASE");
//		//TXProcesoDAO process= dtc.getProcess(nIdProc);
//		
//		TXProcedure procedure=TXProcedureMgr.getInstance().getProcedure(cs,process.getIdProcedure());
//		//IItem itemPStage = procedure.getStage(stage.getInt("ID_FASE"));
//		//PNodoDAO pNodo = procedure.getNode(cs.getConnection(), itemPStage.getKeyInt());
//		PNodoDAO pNodo = procedure.getNode(cs.getConnection(), activatorStage.getKeyInt());
//
//
//		EventManager eventmgr=new EventManager(cs);
//		
//		//Se invoca al BPM para obtener las siguientes fases a activar
//		List nextStages = bpmAPI.getNextStages(process.getString("ID_PROCESO_BPM"), pNodo.getString("ID_NODO_BPM"));
//		Iterator it = nextStages.iterator();
//		
//		TXDAOGen genDAO= new TXDAOGen(cs,eventmgr);
//		TXNodeActivationManager nodeActMgr=new TXNodeActivationManager(genDAO,procedure,dtc);
//		
//		while(it.hasNext()){
//			String stageUID = (String)it.next();
//			PFaseDAO pStage = procedure.getStageDAO(cs.getConnection(), stageUID);
//			String processStageRespId = ResponsibleHelper.calculateStageResp(eventmgr, pStage, cs, process.getString("ID_RESP"));
//			//Se invoca al BPM para instanciar una fase
//			HierarchicalUID hProcessStageUID = bpmAPI.instanceProcessStage(stageUID, processStageRespId);
//			String processStageUID = hProcessStageUID.getUID();
//			//Se establece en la fase iniciada el UID retornado por el BPM
//			TXFaseDAO stageInstanced = nodeActMgr.activateNode(activatorStage.getKeyInt(), pStage.getKeyInt(), process);
//			if (processStageUID == null)
//				processStageUID = ""+stageInstanced.getKeyInt();
//			//Se establece el UID de la fase instanciada retornado por el BPM
//			stageInstanced.set("ID_FASE_BPM", processStageUID);
//		}
//		
//		// Cierra el expediente cuando no hay fases, nodos o trámites activos
//		if (testProcessClosed(cs,dtc,process))
//		{
//			
//			//Se invoca al BPM para el cierre del proceso
//			bpmAPI.endProcess(process.getString("ID_PROCESO_BPM"));
//			
//			//Ejecutar eventos al cerrar expediente.
//			eventmgr.newContext();
//			eventmgr.getRuleContextBuilder().addContext(process);
//			eventmgr.processEvents(	EventsDefines.EVENT_OBJ_PROCEDURE,
//									process.getInt("ID_PCD"),
//									EventsDefines.EVENT_EXEC_END);
//
//			process.set("ESTADO",TXConstants.STATUS_CLOSED);
//
//			TXHitoDAO hitoexp=dtc.newMilestone(process.getKeyInt(),0,0,
//					TXConstants.MILESTONE_EXPED_END);
//
//			hitoexp.set("FECHA_LIMITE",process.getDate("FECHA_LIMITE"));
//		}		
//	}
//	

	
	public void run(ClientContext cs, TXTransactionDataContainer dtc,ITXTransaction itx)
	throws ISPACException
	{	
			//-----
			//BPM
			//----		
//				TXFaseDAO stage=dtc.getStage(mnIdStage);
//				
//				IBPMAPI bpmAPI = dtc.getBPMAPI();
//				runCloseStage(stage, bpmAPI, dtc, cs);
//				//runOpenStages(stage, BPMAPI, dtc, cs);
//				
//				int nIdProc=stage.getInt("ID_EXP");
//				int nIdPCDStage=stage.getInt("ID_FASE");
//				TXProcesoDAO process= dtc.getProcess(nIdProc);
//				
//				TXProcedure procedure=TXProcedureMgr.getInstance().getProcedure(cs,process.getIdProcedure());
//				PFaseDAO pStage = procedure.getStageDAO(nIdPCDStage);
//				runOpenStages(pStage, process, bpmAPI, dtc, cs);
//				
		//----					
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
			throw new ISPACInfo("exception.expedients.nextStage.statusBlocked");
		}
	}
	
}