package ieci.tdw.ispac.ispactx.tx;

import ieci.tdw.ispac.api.IBPMAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.errors.ISPACLockedObject;
import ieci.tdw.ispac.api.errors.ISPACOpenTasksInfo;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.tx.TXFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXHitoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXTramiteDAO;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;

import java.util.Date;
import java.util.Map;

/**
 * Acción para cerrar un proceso.
 */
public class TXCloseProcess implements ITXAction {
	
	/** 
	 * Identificador de la fase instanciada. 
	 */
	private final int mnIdProcess;
	
	private boolean mnEndTasks = false;

	/**
	 * Parámetros para el contexto de las reglas.
	 */
	private final Map params;

	
	/**
	 * Constructor.
	 * @param nIdStage Identificador de la fase instanciada.
	 */
	public TXCloseProcess(int nIdProcess) {
		this(nIdProcess, false, null);
	}

	public TXCloseProcess(int nIdProcess, Map params) {
		this(nIdProcess, false, params);
	}

	public TXCloseProcess(int nIdProcess, boolean endTasks) {
		this(nIdProcess, endTasks, null);
	}

	public TXCloseProcess(int nIdProcess, boolean endTasks, Map params) {
		super();
		this.mnIdProcess = nIdProcess;
		this.mnEndTasks = endTasks;
		this.params = params;
	}

	/**
	 * Ejecuta la acción.
	 * @param cs Contexto de cliente.
	 * @param dtc Contenedor de los datos de la transacción.
	 * @param itx Transacción.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void run(ClientContext cs, TXTransactionDataContainer dtc,ITXTransaction itx)
			throws ISPACException {
		
			// Información del proceso activo
			TXProcesoDAO process= dtc.getProcess(mnIdProcess);
			if ((process.getInt("ESTADO")==TXConstants.STATUS_CANCELED)){
				throw new ISPACInfo("exception.expedients.closeProcess.statusCanceled", 
						new String[]{process.getString("NUMEXP")});
			} else if ((process.getInt("ESTADO")==TXConstants.STATUS_ARCHIVED)) {
				throw new ISPACInfo("exception.expedients.closeProcess.statusArchived", 
						new String[]{process.getString("NUMEXP")});
			}			
			
			
			//Todas las fases activas del proceso
			IItemCollection stages = cs.getAPI().getStagesProcess(mnIdProcess);
			
			//Validaciones sobre las fases
			while(stages.next()){
				TXFaseDAO stage = (TXFaseDAO)stages.value();
				//La fase no debe estar cancelada
				if (stage.getInt("ESTADO")==TXConstants.STATUS_CANCELED) {
					throw new ISPACInfo("exception.expedients.closeProcess.statusCanceled", new String[]{process.getString("NUMEXP")});
				}
				// La fase no debe tener trámites abiertos
				if (!mnEndTasks && stage.existTask(cs.getConnection())) {
					throw new ISPACOpenTasksInfo("exception.expedients.closeStage.openTask", new String[]{process.getString("NUMEXP")});
				}
			}
			
			// Cerrar el proceso en el BPM
			String bpmProcessId = process.getString("ID_PROCESO_BPM");
			if (StringUtils.isNotBlank(bpmProcessId)) {

				// Obtener el API de BPM
				IBPMAPI bpmAPI = dtc.getBPMAPI();
				
				// Cerrar el proceso en el BPM
				bpmAPI.endProcess(bpmProcessId);
			}

			//reseteamos la coleccion para volver a recogerla
			stages.reset();
			//Acciones sobre las fases
			while(stages.next()){
				TXFaseDAO stage = (TXFaseDAO)stages.value();
				//TODO Controlar si el tramite a cerrar es un subproceso
				//Si se indica que se deben cerrar los trámites se procede a ello
				if (mnEndTasks){
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
				
				int nIdPCDStage=stage.getInt("ID_FASE");
				Date stagedeadline=stage.getDate("FECHA_LIMITE");
				
				// Se construye el contexto de ejecución de scripts
				EventManager eventmgr=new EventManager(cs, params);
				eventmgr.getRuleContextBuilder().addContext(process);
				eventmgr.getRuleContextBuilder().addContext(stage);
		
				int eventObjectType = EventsDefines.EVENT_OBJ_STAGE;
				if (stage.isActivity())
					eventObjectType = EventsDefines.EVENT_OBJ_ACTIVITY;
				
				//Ejecutar evento de sistema al cerrar fase
				eventmgr.processSystemEvents(eventObjectType, EventsDefines.EVENT_EXEC_END);
		
				//Ejecutar evento al cerrar fase
				eventmgr.processEvents(eventObjectType, nIdPCDStage, EventsDefines.EVENT_EXEC_END);
		
				//Se elimina la fase cerrada
				dtc.deleteStage(stage.getKeyInt());
				
				int milestoneType = TXConstants.MILESTONE_STAGE_END;
				if(stage.isActivity())
					milestoneType = TXConstants.MILESTONE_ACTIVITY_END;
				
				// Marcar el hito
				TXHitoDAO hito=dtc.newMilestone(process.getKeyInt(),nIdPCDStage,0, milestoneType);
		
				hito.set("FECHA_LIMITE",stagedeadline);
				
				//Ejecutar evento de sistema tras cerrar la fase
				eventmgr.processSystemEvents(eventObjectType, EventsDefines.EVENT_EXEC_END_AFTER);
		
				//Ejecutar evento tras cerrar la fase
				eventmgr.processEvents(eventObjectType, nIdPCDStage, EventsDefines.EVENT_EXEC_END_AFTER);
			}
			
			
			dtc.deleteProcessStages(mnIdProcess);
			dtc.deleteProcessTasks(mnIdProcess);
			dtc.deleteProcessSyncNodes(mnIdProcess);
	
			process.deleteStages(cs.getConnection());
			process.deleteTasks(cs.getConnection());
			process.deleteSyncNodes(cs.getConnection());
			
			// Establecer la fecha de cierre del expediente
			dtc.setExpedientEndDate(process.getString("NUMEXP"));
			
			//Ejecutar eventos al cerrar expediente
			EventManager eventmgr=new EventManager(cs);
			eventmgr.newContext();
			eventmgr.getRuleContextBuilder().addContext(process);
			
			int eventObjectType = EventsDefines.EVENT_OBJ_PROCEDURE;
			if (process.isSubProcess())
				eventObjectType = EventsDefines.EVENT_OBJ_SUBPROCEDURE;
			eventmgr.processEvents(eventObjectType	, process.getInt("ID_PCD"), EventsDefines.EVENT_EXEC_END);
	
			process.set("ESTADO",TXConstants.STATUS_CLOSED);
	
			int milestoneType = TXConstants.MILESTONE_EXPED_END;
			if (process.isSubProcess())
				milestoneType = TXConstants.MILESTONE_SUBPROCESS_END;
			TXHitoDAO hitoexp=dtc.newMilestone(process.getKeyInt(),0,0, milestoneType);
	
			hitoexp.set("FECHA_LIMITE",process.getDate("FECHA_LIMITE"));
	}

	/**
	 * Bloquea el objeto de la acción.
	 * @param cs Contexto de cliente.
	 * @param dtc Contenedor de los datos de la transacción.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void lock(ClientContext cs, TXTransactionDataContainer dtc)
			throws ISPACException {
		
		IItemCollection stages = cs.getAPI().getStagesProcess(mnIdProcess);
		
		try {
			dtc.getLockManager().lockProcess(mnIdProcess);
			while(stages.next()){
				TXFaseDAO stage = (TXFaseDAO)stages.value();
				dtc.getLockManager().lockStage(stage.getKeyInt());
			}
			
		} catch (ISPACLockedObject ilo) {
			throw new ISPACInfo("exception.expedients.closeProcess.statusBlocked");
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
