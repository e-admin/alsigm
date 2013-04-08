package ieci.tdw.ispac.ispactx.tx;

import java.util.Map;

import ieci.tdw.ispac.api.IBPMAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.tx.TXTramiteDAO;
import ieci.tdw.ispac.ispaclib.notices.Notices;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;

/**
 * Acción para eliminar un trámite.
 */
public class TXDeleteTask implements ITXAction {
	
	/** Identificador del trámite instanciado. */
	private final int mnIdTask;
	
	/**
	 * Parámetros para el contexto de las reglas.
	 */
	private final Map mparams;

	/**
	 * Constructor.
	 * @param nIdTask Identificador del trámite instanciado. 
	 */
	public TXDeleteTask(int nIdTask) {
		this(nIdTask, null);
	}

	/**
	 * Constructor.
	 * @param nIdTask Identificador del trámite instanciado. 
	 */
	public TXDeleteTask(int nIdTask, Map params) {
		super();
		mnIdTask=nIdTask;
		mparams=params;
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
		TXTramiteDAO task = dtc.getTask(mnIdTask);
		int nIdPCDTask = task.getInt("ID_TRAMITE");
		int nIdProc = task.getInt("ID_EXP");
		int nIdPCDStage = task.getInt("ID_FASE_PCD");

		String bpmTaskId = task.getString("ID_TRAMITE_BPM");

		// Obtener el API de BPM
		IBPMAPI bpmAPI = dtc.getBPMAPI();

		// Eliminar el trámite en el BPM
		bpmAPI.deleteTask( bpmTaskId);

		//Se elimina el tramite 
		dtc.deleteTask(mnIdTask);
		
		//Se anota en la entidad del trámite la finalización del mismo.
		dtc.deleteTaskEntity(mnIdTask);
		
		EventManager eventmgr = new EventManager(cs, mparams);

		// Se construye el contexto de ejecución de scripts.
		eventmgr.getRuleContextBuilder().addContext(task);
		
		//Ejecutar eventos al borrar el trámite.
		eventmgr.processEvents(	EventsDefines.EVENT_OBJ_TASK, nIdPCDTask, EventsDefines.EVENT_EXEC_DELETE);
		
		// Marcar el hito
		dtc.newMilestone(nIdProc, nIdPCDStage, nIdPCDTask, TXConstants.MILESTONE_TASK_DELETE);
		
		//Si existe un aviso electronico que indica que el tramite a eliminar ha sido delegado, se archiva
		Notices notices = new Notices(cs);
		notices.archiveDelegateTaskNotice(task.getInt("ID_FASE_EXP") , task.getKeyInt());
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
