package ieci.tdw.ispac.ispactx.tx;

import ieci.tdw.ispac.api.IBPMAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.errors.ISPACLockedObject;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.tx.TXHitoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXTramiteDAO;
import ieci.tdw.ispac.ispaclib.notices.Notices;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;

import java.util.Date;
import java.util.Map;

/**
 * Acción para cerrar un trámite.
 */
public class TXCloseTask implements ITXAction {
	
	/** 
	 * Identificador del trámite instanciado. 
	 */
	private final int mnIdTask;
	
	/**
	 * Parámetros para el contexto de las reglas.
	 */
	private final Map params;

	
	/**
	 * Constructor.
	 * @param nIdTask Identificador del trámite instanciado.
	 */
	public TXCloseTask(int nIdTask) {
		this(nIdTask, null);
	}

	/**
	 * Constructor.
	 * @param nIdTask Identificador del trámite instanciado.
	 */
	public TXCloseTask(int nIdTask, Map params) {
		super();
		this.mnIdTask = nIdTask;
		this.params = params;
	}

	/**
	 * Ejecuta la acción.
	 * @param cs Contexto de cliente.
	 * @param dtc Contenedor de los datos de la transacción.
	 * @param itx Transacción.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void run(ClientContext cs, TXTransactionDataContainer dtc,
			ITXTransaction itx) throws ISPACException {
		

			// Información del trámite
			TXTramiteDAO task=dtc.getTask(mnIdTask);
			int nIdPCDTask=task.getInt("ID_TRAMITE");
			int nIdProc=task.getInt("ID_EXP");
			int nIdPCDStage=task.getInt("ID_FASE_PCD");
			Date taskdeadline=task.getDate("FECHA_LIMITE");

			// Comprobar si el trámite tiene documentos pendientes de firma
			IEntitiesAPI entitiesAPI = cs.getAPI().getEntitiesAPI();
			if (entitiesAPI.countTaskDocumentsInSignCircuit(task.getKeyInt()) > 0) {
				throw new ISPACInfo("exception.expedients.closeTask.docsInSignCircuit", 
						new String[] { task.getString("NOMBRE"), task.getString("NUMEXP")});
			}
			
			String bpmTaskId = task.getString("ID_TRAMITE_BPM");

			// Obtener el API de BPM
			IBPMAPI bpmAPI = dtc.getBPMAPI();
			
			//Se invoca al BPM para el cierre del trámite
			bpmAPI.endTask(bpmTaskId);

			EventManager eventmgr=new EventManager(cs, params);
			
			// Se construye el contexto de ejecución de scripts
			eventmgr.getRuleContextBuilder().addContext(task);
	
			//Ejecutar eventos de sistema al cerrar trámite
			eventmgr.processSystemEvents(EventsDefines.EVENT_OBJ_TASK,
										 EventsDefines.EVENT_EXEC_END);
	
			//Ejecutar eventos al cerrar trámite
			eventmgr.processEvents(	EventsDefines.EVENT_OBJ_TASK,
									nIdPCDTask,
									EventsDefines.EVENT_EXEC_END);
			String idRespClosedTask = task.getString("ID_RESP");
			
			//Se elimina el tramite cerrado
			dtc.deleteTask(mnIdTask);
	
			//Se anota en la entidad del trámite la finalización del mismo y se establece responsable del tramite finalizado
			dtc.closeTaskEntity(mnIdTask, idRespClosedTask);
	
			// Marcar el hito
			TXHitoDAO hito=dtc.newMilestone(nIdProc,nIdPCDStage,nIdPCDTask, TXConstants.MILESTONE_TASK_END);
			
			hito.set("INFO", composeInfo());
			hito.set("FECHA_LIMITE",taskdeadline);
			
			//Ejecutar eventos de sistema tras terminar el trámite.
			eventmgr.processSystemEvents(EventsDefines.EVENT_OBJ_TASK,
										 EventsDefines.EVENT_EXEC_END_AFTER);
	
			//Ejecutar eventos tras terminar el trámite.
			eventmgr.processEvents(	EventsDefines.EVENT_OBJ_TASK,
									nIdPCDTask,
									EventsDefines.EVENT_EXEC_END_AFTER);

			//Si existe un aviso electronico que indica que el tramite a cerrar ha sido delegado, se archiva
			Notices notices = new Notices(cs);
			notices.archiveDelegateTaskNotice(task.getInt("ID_FASE_EXP"), task.getKeyInt());
	}

	private String composeInfo(){
		return new StringBuffer()
			.append("<?xml version='1.0' encoding='ISO-8859-1'?>")
			.append("<infoaux><id_tramite>")
			.append(mnIdTask)
			.append("</id_tramite></infoaux>")
			.toString();
	}

	/**
	 * Bloquea el objeto de la acción.
	 * @param cs Contexto de cliente.
	 * @param dtc Contenedor de los datos de la transacción.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void lock(ClientContext cs, TXTransactionDataContainer dtc)
			throws ISPACException {
		
		TXTramiteDAO task=dtc.getTask(mnIdTask);
		dtc.getLockManager().lockProcess(task.getInt("ID_EXP"));
		
		try {
			dtc.getLockManager().lockProcess(task.getInt("ID_EXP"));
			dtc.getLockManager().lockTask(mnIdTask);
		} catch (ISPACLockedObject ilo) {
			throw new ISPACInfo("exception.expedients.closeTask.statusBlocked");
		}
	}

	/**
	 * Obtiene el resultado de la acción.
	 * @param nameResult Nombre del resultado.
	 * @return Resultado de la acción.
	 */
	public Object getResult(String nameResult) {
		return null;
	}
}
