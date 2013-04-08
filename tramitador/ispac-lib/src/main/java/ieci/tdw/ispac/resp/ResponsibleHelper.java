package ieci.tdw.ispac.resp;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.api.rule.RuleProperties;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PTramiteDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispactx.TXProcedure;

import java.util.List;

public class ResponsibleHelper {
	
	private static String setDefaultResp(String objectResp ,String sRespId)
	throws ISPACException
	{
		if (objectResp == null || objectResp.length()==0 ){
			objectResp = sRespId;
		}
		return objectResp;
	}	
	public static String calculateProcessResp(EventManager eventmgr, TXProcedure procedure, ClientContext cs) throws ISPACException{
		String processRespId =  procedure.getString("ID_RESP");
		eventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_PROCEDURE, String.valueOf(procedure.getKeyInt()));
		
		List resplist=eventmgr.processEvents(EventsDefines.EVENT_OBJ_PROCEDURE,
		        							procedure.getKeyInt(),
											EventsDefines.EVENT_EXEC_RESPCALC);
		if (resplist!=null && resplist.size()>0){
		    processRespId =(String)resplist.get(0);
		}
		processRespId = setDefaultResp(processRespId,cs.getRespId());
		return processRespId;
	}
	
	public static String calculateStageResp(EventManager eventmgr,PFaseDAO pstage, String processRespId) throws ISPACException{
		
		IClientContext cct = eventmgr.getRuleContextBuilder().getRuleContext().getClientContext();
		String stageRespId = pstage.getString("ID_RESP");
		
		eventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_STAGEPCD, String.valueOf(pstage.getKeyInt()));
		
		List resplist=eventmgr.processEvents(EventsDefines.EVENT_OBJ_STAGE,
											pstage.getKeyInt(),
											EventsDefines.EVENT_EXEC_RESPCALC);
		if (resplist!=null && resplist.size()>0){
			stageRespId = (String)resplist.get(0);
		}
		stageRespId = setDefaultResp(stageRespId,processRespId);
		stageRespId = setDefaultResp(stageRespId, cct.getRespId());
		
		return stageRespId;
	}
	public static String calculateTaskResp(EventManager eventmgr, PTramiteDAO pTask, TXFaseDAO stage, TXProcesoDAO process, ClientContext cs) throws ISPACException{
		String taskRespId = pTask.getString("ID_RESP"); 

		// Se construye el contexto de ejecución de scripts.
		eventmgr.getRuleContextBuilder().addContext(stage);
		eventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_TASKPCD, String.valueOf(pTask.getKeyInt()));

		//Calcular responsable del trámite.
		List resplist=eventmgr.processEvents(EventsDefines.EVENT_OBJ_TASK,
								pTask.getKeyInt(),
								EventsDefines.EVENT_EXEC_RESPCALC);

		if (resplist!=null && resplist.size()>0){
			taskRespId  =(String)resplist.get(0);
		}

		taskRespId = setDefaultResp(taskRespId,stage.getString("ID_RESP"));
		taskRespId = setDefaultResp(taskRespId,process.getString("ID_RESP"));
		taskRespId = setDefaultResp(taskRespId,cs.getRespId());
		return taskRespId;
	}	
	

	public static String calculateTaskActivityResp(EventManager eventmgr, PFaseDAO pActivity, PTramiteDAO pTask, TXProcesoDAO process, ClientContext cs, String taskRespId) throws ISPACException{
		String taskActivityRespId = pActivity.getString("ID_RESP"); 
		// Se construye el contexto de ejecución de scripts.
		eventmgr.getRuleContextBuilder().addContext(process);
		eventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_STAGEPCD, String.valueOf(pActivity.getKeyInt()));
		eventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_TASKPCD, String.valueOf(pTask.getKeyInt()));

		//Calcular responsable del trámite.
		List resplist=eventmgr.processEvents(EventsDefines.EVENT_OBJ_STAGE,
								pActivity.getKeyInt(),
								EventsDefines.EVENT_EXEC_RESPCALC);

		if (resplist!=null && resplist.size()>0){
			taskActivityRespId  =(String)resplist.get(0);
		}
		taskActivityRespId = setDefaultResp(taskActivityRespId,taskRespId);
		return taskActivityRespId;
	}	
}
