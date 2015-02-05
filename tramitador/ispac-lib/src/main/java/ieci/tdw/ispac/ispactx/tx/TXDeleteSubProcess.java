package ieci.tdw.ispac.ispactx.tx;

import java.util.Map;

import ieci.tdw.ispac.api.IBPMAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXTramiteDAO;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;

/**
 * Acción para eliminar un subproceso
 */
public class TXDeleteSubProcess implements ITXAction {
	
	/** Identificador del subproceso instanciado. */
	private final int mnIdSubProcess;
	
	/** Identificador del trámite instanciado. */
	private final int mnIdTask;
	
	/**
	 * Parámetros para el contexto de las reglas.
	 */
	private final Map mparams;

	/**
	 * Constructor.
	 * @param nIdSubProcess Identificador del subproceso instanciado. 
	 */
	public TXDeleteSubProcess(int nIdSubProcess, int nIdTask) {
		this(nIdSubProcess, nIdTask, null);
	}

	/**
	 * Constructor.
	 * @param nIdSubProcess Identificador del subproceso instanciado. 
	 */
	public TXDeleteSubProcess(int nIdSubProcess, int nIdTask, Map params) {
		super();
		mnIdSubProcess = nIdSubProcess;
		mnIdTask = nIdTask;
		mparams = params;
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
		
		// Información del subproceso
		TXProcesoDAO subProcess = dtc.getProcess(mnIdSubProcess);
		int nIdPCD = subProcess.getInt("ID_PCD");

		String bpmProcId = subProcess.getString("ID_PROCESO_BPM");

		// Obtener el API de BPM
		IBPMAPI bpmAPI = dtc.getBPMAPI();

		// Eliminar el subproceso en el BPM
		bpmAPI.deleteProcess(bpmProcId);
		
		// Se eliminan los nodos de sincronización de las actividades del subproceso
		dtc.loadProcessSyncNodes(mnIdSubProcess);
		dtc.deleteProcessSyncNodes(mnIdSubProcess);
		
		// Se eliminan las actividades del subproceso
		dtc.loadProcessStages(mnIdSubProcess);
		dtc.deleteProcessStages(mnIdSubProcess);
		
		// Se elimina el subproceso
		dtc.deleteProcess(mnIdSubProcess);
		
		EventManager eventmgr = new EventManager(cs, mparams);

		// Se construye el contexto de ejecución de scripts.
		eventmgr.getRuleContextBuilder().addContext(subProcess);
		
		// Ejecutar eventos al borrar el subproceso
		eventmgr.processEvents(EventsDefines.EVENT_OBJ_SUBPROCEDURE, nIdPCD, EventsDefines.EVENT_EXEC_DELETE);
		
		// Marcar el hito
		dtc.newMilestone(mnIdSubProcess, 0, 0, TXConstants.MILESTONE_SUBPROCESS_DELETE);
		
		// Información del trámite
		TXTramiteDAO task = dtc.getTask(mnIdTask);
		int nIdPCDTask = task.getInt("ID_TRAMITE");
		int nIdProc = task.getInt("ID_EXP");
		int nIdPCDStage = task.getInt("ID_FASE_PCD");

		// Se elimina el tramite 
		dtc.deleteTask(mnIdTask);
		
		// Se anota en la entidad del trámite la finalización del mismo
		dtc.deleteTaskEntity(mnIdTask);

		// Se construye el contexto de ejecución de scripts
		eventmgr.getRuleContextBuilder().addContext(task);
		
		// Ejecutar eventos al borrar el trámite
		eventmgr.processEvents(EventsDefines.EVENT_OBJ_TASK, nIdPCDTask, EventsDefines.EVENT_EXEC_DELETE);
		
		// Marcar el hito
		dtc.newMilestone(nIdProc, nIdPCDStage, nIdPCDTask, TXConstants.MILESTONE_TASK_DELETE);
	}

	/**
	 * Bloquea el objeto de la acción.
	 * @param cs Contexto de cliente.
	 * @param dtc Contenedor de los datos de la transacción.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void lock(ClientContext cs, TXTransactionDataContainer dtc)
			throws ISPACException {
		
		TXTramiteDAO task = dtc.getTask(mnIdTask);
		dtc.getLockManager().lockProcess(task.getInt("ID_EXP"));
		dtc.getLockManager().lockTask(mnIdTask);
		dtc.getLockManager().lockProcess(task.getInt("ID_SUBPROCESO"));
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