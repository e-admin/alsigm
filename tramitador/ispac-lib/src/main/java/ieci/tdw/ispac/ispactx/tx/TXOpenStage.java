package ieci.tdw.ispac.ispactx.tx;

import ieci.tdw.ispac.api.IBPMAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaclib.bpm.BpmUIDs;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PNodoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXHitoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispactx.TXDAOGen;
import ieci.tdw.ispac.ispactx.TXNodeActivationManager;
import ieci.tdw.ispac.ispactx.TXProcedure;
import ieci.tdw.ispac.ispactx.TXProcedureMgr;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;
import ieci.tdw.ispac.resp.ResponsibleHelper;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TXOpenStage implements ITXAction {
	
	private final int mnIdProcess;
	private final int mnIdPcdStageToActivate;

	/**
	 * Parámetros para el contexto de las reglas.
	 */
	private final Map mparams;

	public TXOpenStage(int nIdProcess, int nIdPcdStageActivator) {
		this(nIdProcess, nIdPcdStageActivator, null);
	}

	public TXOpenStage(int nIdProcess, int nIdPcdStageActivator, Map params) {
		super();
		mnIdProcess = nIdProcess;
		mnIdPcdStageToActivate = nIdPcdStageActivator;
		mparams = params;
	}

	public boolean testProcessClosed(ClientContext cs, TXTransactionDataContainer dtc,TXProcesoDAO exped)
	throws ISPACException
	{
		int nIdProc=exped.getKeyInt();

		int nCount=dtc.getProcessStages(nIdProc).size();
		nCount+=dtc.getProcessSyncNodes(nIdProc).size();
		nCount+=dtc.getProcessTasks(nIdProc).size();
		return (nCount==0);
	}

	
	public void run(ClientContext cs, TXTransactionDataContainer dtc,ITXTransaction itx)
	throws ISPACException{
		//-----
		//BPM
		//----		
		
		TXProcesoDAO process= dtc.getProcess(mnIdProcess);
		TXProcedure procedure=TXProcedureMgr.getInstance().getProcedure(cs,process.getIdProcedure());

		IBPMAPI bpmAPI = dtc.getBPMAPI();

		EventManager eventmgr=new EventManager(cs, mparams);
		eventmgr.newContext();
		eventmgr.getRuleContextBuilder().addContext(process);

		TXDAOGen genDAO= new TXDAOGen(cs,eventmgr);
		TXNodeActivationManager nodeActMgr=new TXNodeActivationManager(genDAO,procedure,dtc);
		
		String nodeUID = String.valueOf(mnIdPcdStageToActivate);

		PNodoDAO node = procedure.getNode(cs.getConnection(), nodeUID);
		if (node.isStage()){
			PFaseDAO pStage = procedure.getStageDAO(cs.getConnection(), nodeUID);
			instanceStage(bpmAPI, pStage, process, eventmgr, nodeActMgr, nodeUID);
		}else{//Se trata de un nodo de sincronizacion
			bpmAPI.processSyncNode(nodeUID);
			List nextSpacStages = nodeActMgr.processSyncNode(mnIdPcdStageToActivate, node.getKeyInt(), process);
			//Nos quedamos con las fases que nos retorna SPAC no las del BPM, ya que en el BPM propio para SPAC deberia tener acceso al dtc para aplicar los cambios 
			if (nextSpacStages !=null ){	
				for (Iterator iter = nextSpacStages.iterator(); iter.hasNext();) {
					int stageId = ((Integer) iter.next()).intValue();
					PFaseDAO pStage = procedure.getStageDAO(stageId);
					PNodoDAO node1 = procedure.getNode(cs.getConnection(), pStage.getKeyInt());
					if (!nodeActMgr.testStageOpen(process.getKeyInt(), pStage.getKeyInt()))
						instanceStage(bpmAPI, pStage, process, eventmgr, nodeActMgr, node1.getString("ID_NODO_BPM"));
				}
			}
		}
		
		// Cierra el expediente cuando no hay fases, nodos o trámites activos
		if (testProcessClosed(cs,dtc,process))
		{
			//Se invoca al BPM para el cierre del proceso
			bpmAPI.endProcess(process.getString("ID_PROCESO_BPM"));
			
			//Eliminamos los nodos de sincronizacion que hayan podido quedar.
			//Habran podido quedar nodos de sincronizacion de tipo OR, cuando teniendo más de un nodo de entrada al nodo de sincronizacion,
			//no hayan llegado todos los nodos porque p.ej: esa rama no se haya ejecutado.
			//dtc.deleteSyncNodes();
//			dtc.loadProcessSyncNodes(process.getKeyInt());
//			dtc.deleteProcessSyncNodes(process.getKeyInt());
			
			// Establecer la fecha de cierre del expediente
			if (process.isProcess()) {
				dtc.setExpedientEndDate(process.getString("NUMEXP"));
			}
			
			//Ejecutar eventos al cerrar expediente.
			int eventObjectType = EventsDefines.EVENT_OBJ_PROCEDURE;
			if (process.isSubProcess())
				eventObjectType = EventsDefines.EVENT_OBJ_SUBPROCEDURE;
			eventmgr.processEvents(	eventObjectType, process.getInt("ID_PCD"), EventsDefines.EVENT_EXEC_END);

			process.set("ESTADO",TXConstants.STATUS_CLOSED);

			int milestoneType = TXConstants.MILESTONE_EXPED_END;
			if (process.isSubProcess())
				milestoneType = TXConstants.MILESTONE_SUBPROCESS_END;
			TXHitoDAO hitoexp=dtc.newMilestone(process.getKeyInt(),0,0, milestoneType);

			hitoexp.set("FECHA_LIMITE",process.getDate("FECHA_LIMITE"));
		}				
		
		//----					
		
	}
	
	private void instanceStage(IBPMAPI bpmAPI, PFaseDAO pStage, TXProcesoDAO process, EventManager eventmgr, TXNodeActivationManager nodeActMgr, String nodeUID) throws ISPACException{
		
		//eventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_STAGEPCD, ""+pStage.getKeyInt());
		String processStageRespId = ResponsibleHelper.calculateStageResp(eventmgr, pStage, process.getString("ID_RESP"));
		
		eventmgr.newContext();
		
		//Se invoca al BPM para instanciar una fase
		BpmUIDs bpmUIDs = bpmAPI.instanceProcessStage(nodeUID, processStageRespId, process.getString("ID_PROCESO_BPM"));
		String processStageUID = bpmUIDs.getStageUID();
		//Se establece en la fase iniciada el UID retornado por el BPM
		TXFaseDAO stageInstanced = nodeActMgr.activateNode(mnIdPcdStageToActivate, pStage.getKeyInt(), process, processStageRespId);
		if (processStageUID == null)
			processStageUID = ""+stageInstanced.getKeyInt();
		//Se establece el UID de la fase instanciada retornado por el BPM y el responsable en SPAC
		stageInstanced.set("ID_FASE_BPM", processStageUID);
		//stageInstanced.set("ID_RESP", processStageRespId);
	}
	
	public Object getResult(String nameResult)
	{
		return null;
	}

	public void lock(ClientContext cs, TXTransactionDataContainer dtc)
			throws ISPACException
	{
//		TXFaseDAO stage=dtc.getStage(mnIdStage);
//		dtc.getLockManager().lockProcess(stage.getInt("ID_EXP"));
	}
	
}