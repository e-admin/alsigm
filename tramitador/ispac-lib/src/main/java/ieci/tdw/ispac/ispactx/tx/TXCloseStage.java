package ieci.tdw.ispac.ispactx.tx;

import ieci.tdw.ispac.api.IBPMAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.ISignAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.errors.ISPACLockedObject;
import ieci.tdw.ispac.api.errors.ISPACOpenTasksInfo;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.tx.TXFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXHitoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXTramiteDAO;
import ieci.tdw.ispac.ispaclib.notices.Notices;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public abstract class TXCloseStage implements ITXAction {
	
	private final int mnIdStage;
	private boolean mnEndTasks = false;

	public TXCloseStage(int nIdStage)
	{
		super();
		mnIdStage=nIdStage;
	}

	public TXCloseStage(int nIdStage, boolean nEndTasks)
	{
		super();
		mnIdStage=nIdStage;
		mnEndTasks=nEndTasks;
	}
	
	
	protected abstract void executeEvents(ClientContext cs, TXProcesoDAO process, TXFaseDAO stage) throws ISPACException;
	protected abstract void executeAfterEvents(ClientContext cs, TXProcesoDAO process, TXFaseDAO stage) throws ISPACException;
	
	protected abstract TXHitoDAO  insertMilestone(TXTransactionDataContainer dtc, TXProcesoDAO process, int nIdPCDStage)throws ISPACException;
	
	protected  void validate(ClientContext cs, TXTransactionDataContainer dtc, TXProcesoDAO process,TXFaseDAO stage)throws ISPACException{}
	
	public void run(ClientContext cs, TXTransactionDataContainer dtc,ITXTransaction itx)
	throws ISPACException{
		//-----
		//BPM
		//----		
		
		TXFaseDAO stage=dtc.getStage(mnIdStage);
		int nIdProc=stage.getInt("ID_EXP");
		int nIdPCDStage=stage.getInt("ID_FASE");
		Date stagedeadline=stage.getDate("FECHA_LIMITE");

		TXProcesoDAO process= dtc.getProcess(nIdProc);

		if ((process.getInt("ESTADO")==TXConstants.STATUS_CANCELED)||
			(stage.getInt("ESTADO")==TXConstants.STATUS_CANCELED)) {
				throw new ISPACInfo("exception.expedients.nextStage.statusCanceled", 
					new String[]{process.getString("NUMEXP")});
		} else if ((process.getInt("ESTADO")==TXConstants.STATUS_ARCHIVED)||
			(stage.getInt("ESTADO")==TXConstants.STATUS_ARCHIVED)) {
				throw new ISPACInfo("exception.expedients.nextStage.statusArchived", 
					new String[]{process.getString("NUMEXP")});
		}

		if (!mnEndTasks){
		
			// La fase no debe tener trámites abiertos.
			if (stage.existTask(cs.getConnection())) {
				throw new ISPACOpenTasksInfo("exception.expedients.closeStage.openTask", new String[]{process.getString("NUMEXP")});
			}
			
			// Trámites obligatorios que tiene la fase en el procedimiento seleccionado
			Map tasks = stage.getPCDObligatoryTasks(cs.getConnection()).toMapStringKey("ID_CTTRAMITE");
			IItemCollection collection = stage.getDTTasks(cs.getConnection(), stage.getString("NUMEXP"), nIdPCDStage).disconnect();		
			for(Iterator iter = collection.iterator(); iter.hasNext();){			
				String idTramCtl = String.valueOf(((IItem) iter.next()).getInt("ID_TRAM_CTL"));
				if(tasks.containsKey(idTramCtl))
					tasks.remove(idTramCtl);
			}
		
			// La fase no debe tener trámites obligatorios sin terminar
			if(!tasks.isEmpty()) 		
				throw new ISPACOpenTasksInfo("exception.expedients.closeStage.obligatoryTask", new String[]{process.getString("NUMEXP")});
			
			// Comprobar si la fase tiene documentos pendientes de firma
			IEntitiesAPI entitiesAPI = cs.getAPI().getEntitiesAPI();
			if (entitiesAPI.countStageDocumentsInSignCircuit(stage.getKeyInt()) > 0) {
				throw new ISPACInfo("exception.expedients.closeStage.docsInSignCircuit", 
						new String[] { stage.getString("NUMEXP")});
			}
		}
		
		//Se ejecutan validaciones previas al cierre de la fase
		validate(cs, dtc, process, stage);
		
		IBPMAPI bpmAPI = dtc.getBPMAPI();
		
		//Se finalizan todos los tramites si es indicado
		if (mnEndTasks){
			
			// Eliminar las referencias a los pasos en los circuitos de firma de los documentos de la fase.
			ISignAPI signAPI = cs.getAPI().getSignAPI();
			signAPI.deleteStepsByStage(stage.getKeyInt());
			
			//bpmAPI.endTasks(String.valueOf(process.getString("ID_PROCESO_BPM")), stage.getString("ID_FASE_BPM"));
			IItemCollection itemcol = stage.getTasks(cs.getConnection()).disconnect();
			while(itemcol.next()){
				TXTramiteDAO task = (TXTramiteDAO)itemcol.value();
				
				String idRespClosedTask = task.getString("ID_RESP");
				//Se elimina el tramite cerrado
				dtc.deleteTask(task.getKeyInt());
				//Se anota en la entidad del trámite la finalización del mismo y se establece responsable del tramite finalizado
				dtc.closeTaskEntity(task.getKeyInt(), idRespClosedTask);
			}		
		}
		//Se invoca al BPM para el cierre de la fase 
		bpmAPI.closeProcesssStage(stage.getString("ID_FASE_BPM"));
	
		// Se lanza los eventos correspondientes antes de terminar
		executeEvents(cs, process, stage);

		//Se elimina la fase cerrada
		dtc.deleteStage(mnIdStage);
		
		// Marcar el hito
		TXHitoDAO hito = insertMilestone(dtc, process, nIdPCDStage);
		
		hito.set("INFO", composeInfo());
		hito.set("FECHA_LIMITE",stagedeadline);
		
		// Se lanza los eventos correspondientes tras terminar
		executeAfterEvents(cs, process, stage);
		
		//Si existe un aviso electronico que indica que la fase/actividad a cerrar ha sido delegada, se archiva
		Notices notices = new Notices(cs);
		notices.archiveDelegateStageNotice(stage.getKeyInt());
	}
	
	private String composeInfo(){
		return new StringBuffer()
			.append("<?xml version='1.0' encoding='ISO-8859-1'?>")
			.append("<infoaux><id_fase>")
			.append(mnIdStage)
			.append("</id_fase></infoaux>")
			.toString();
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
			throw new ISPACInfo("exception.expedients.closeStage.statusBlocked");
		}
	}
	
}