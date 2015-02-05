package ieci.tdw.ispac.ispactx.tx;

import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.tx.TXHitoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;

import java.util.Map;

/**
 * Acción para archivar un proceso.
 * Cambia el estado de CERRADO a ARCHIVADO.
 */
public class TXArchiveProcess implements ITXAction {
	
	/** 
	 * Identificador del proceso. 
	 */
	private final int processId;
	
	/**
	 * Parámetros para el contexto de las reglas.
	 */
	private final Map params;

	
	/**
	 * Constructor.
	 * @param processId Identificador del proceso.
	 */
	public TXArchiveProcess(int processId) {
		this(processId, null);
	}

	/**
	 * Constructor.
	 * @param processId Identificador del proceso.
	 */
	public TXArchiveProcess(int processId, Map params) {
		super();
		this.processId = processId;
		this.params = params;
	}

	/**
	 * Ejecuta la acción.
	 * @param cs Contexto de cliente.
	 * @param dtc Contenedor de los datos de la transacción.
	 * @param itx Transacción.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void run(ClientContext cs, TXTransactionDataContainer dtc, ITXTransaction itx) throws ISPACException {
		
		// Información del proceso
		TXProcesoDAO process = dtc.getProcess(processId);
		
		// Comprobar que el proceso pertenezca a un procedimiento
		if (process.isSubProcess()) {
			throw new ISPACInfo("exception.expedients.archiveProcess.subprocess",
					new String[]{process.getString("NUMEXP")});
		}
		
		// Comprobar que el estado sea CERRADO
		if (process.getInt("ESTADO") != TXConstants.STATUS_CLOSED) {
			throw new ISPACInfo("exception.expedients.archiveProcess.statusNotClosed",
					new String[]{process.getString("NUMEXP")});
		}

		// Se construye el contexto de eventos
		EventManager eventmgr = new EventManager(cs, params);
		eventmgr.getRuleContextBuilder().addContext(process);

		// Procesar evento de archivo de expediente
		eventmgr.processEvents(EventsDefines.EVENT_OBJ_PROCEDURE, process.getInt("ID_PCD"), 
				EventsDefines.EVENT_EXEC_ARCHIVE);

		// Actualizar el estado del proceso
		process.set("ESTADO", TXConstants.STATUS_ARCHIVED);

		// Crear un hito de archivo de expediente
		int milestoneType = TXConstants.MILESTONE_EXPED_ARCHIVE;
		if (process.isSubProcess()) {
			milestoneType = TXConstants.MILESTONE_SUBPROCESS_ARCHIVE;
		}
		TXHitoDAO hitoexp = dtc.newMilestone(process.getKeyInt(), 0, 0, milestoneType);
		hitoexp.set("FECHA_LIMITE", process.getDate("FECHA_LIMITE"));
	}

	/**
	 * Bloquea el objeto de la acción.
	 * @param cs Contexto de cliente.
	 * @param dtc Contenedor de los datos de la transacción.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void lock(ClientContext cs, TXTransactionDataContainer dtc) throws ISPACException {

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
