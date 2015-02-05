package ieci.tdw.ispac.ispaclib.bpm;

import ieci.tdw.ispac.api.IBPMAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PNodoDAO;
import ieci.tdw.ispac.ispactx.TXProcedure;
import ieci.tdw.ispac.ispactx.TXProcedureMgr;
import ieci.tdw.ispac.ispactx.TXStateTable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * Implementación del API de BPM para SPAC.
 *
 */
public class BPMAPI implements IBPMAPI {

    /** 
     * Logger de la clase. 
     */
	protected static final Logger logger = Logger.getLogger(BPMAPI.class);
	
	/**
	 * Contexto de cliente.
	 */
	private ClientContext ctx = null;
	
	/**
	 * Constructor.
	 * @param ctx Contexto de cliente.
	 */
	public BPMAPI(ClientContext ctx) {
		super();
		this.ctx = ctx;
	}

	/**
	 * Constructor.
	 */
	public BPMAPI() {
		super();
	}
	
	/**
	 * Inicio de sesión del BPM
	 * @return Sesión de BPM
	 * @throws ISPACException si ocurre algún error.
	 */
	public IBPMSession initBPMSession() throws ISPACException {
		// TODO Implementar
		return null;
	}

	/**
	 * Inicio de sesión del BPM autenticando usuario
	 * @param user Usuario 
	 * @param password Clave
	 * @return Sesión de BPM
	 * @throws ISPACException
	 */
	public IBPMSession initBPMSession(String user, String password) 
			throws ISPACException {
		// TODO Implementar
		return null;
	}

	/**
	 * Inicio de sesión en el BPM según id de usuario conectado
	 * @param userId Identificador de usuario para el BPM
	 * @return Sesión de BPM
	 * @throws ISPACException
	 */
	public IBPMSession initBPMSession(String userId) 
			throws ISPACException {
		// TODO Implementar
		return null;
	}

	/**
	 * Finaliza la sesión del BPM
	 * @param commit true si finaliza correctamente, false en caso contrario. 
	 * @throws ISPACException si ocurre algún error.
	 */
	public void closeBPMSession(boolean commit) throws ISPACException {
		// TODO Implementar
	}
	/**
	 * Instancia un proceso.
	 * @param session Sesión de BPM
	 * @param processModelId Identificador del Modelo de proceso
	 * @param processRespId Identificador del responsable a establecer en el proceso a instanciar
	 * @param stageRespId Identificador del responsable a establecer en la primera fase del proceso a instanciar
	 * @return Jerarquía de UID's de los objetos implicados 
	 * @throws ISPACException
	 */
	public BpmUIDs instanceProcess(
			String processModelId, String processRespId, String stageRespId)
			throws ISPACException {
		return new BpmUIDs();
	}

	/**
	 * Instancia un trámtie.
	 * @param session Sesión de BPM
	 * @param processModelSubprocessId Identificador del Modelo de Subproceso
	 * @param taskRespId Identificador del responsable a establecer en el trámite a instanciar
	 * @param stageTaskRespId Identificador del responsable a establecer en la actividad del trámite a instanciar
	 * @return Jerarquía de UID's de los objetos implicados
	 * @throws ISPACException
	 */	
	public BpmUIDs instanceTask(String processModelTaskId,
			String processModelSubprocessId, String taskRespId,
			String stageTaskRespId, String stageId) throws ISPACException {
		return new BpmUIDs();
	}
	 
	
	

	/**
	 * Finaliza una fase de un proceso
	 * @param stageId Identificador de la fase instanciada
	 * @throws ISPACException
	 */
	public void closeProcesssStage( String stageId) 
			throws ISPACException {
		// TODO Implementar
	}

	
	/**
	 * Instancia una fase del modelo de proceso
	 * @param pmStageId Identificador de la fase del modelo de proceso a instanciar
	 * @param respId Identificador del responsable a establecer en la fase a instanciar
	 * @return Jerarquía de UID's de los objetos implicados
	 * @throws ISPACException
	 */
	public BpmUIDs instanceProcessStage(String pmStageId, String respId, String instancedProcessId) throws ISPACException {
		return new BpmUIDs();
	}
	
	/**
	 * Obtiene las fases siguientes para el proceso instanciado
	 * @param processId Identificador del proceso instanciado
	 * @return Lista de identificadores de las siguientes fases del flujo para el proceso instanciado 
	 * @throws ISPACException
	 */
	public List getNextStages(String processId, String processModelStageId)
			throws ISPACException {
		// El UID del BPM cuando este es SPAC coincide con el ID del proceso
		IProcess process = ctx.getAPI().getProcess(Integer.parseInt(processId));

		TXProcedure procedure = TXProcedureMgr.getInstance().getProcedure(ctx,
				process.getInt("ID_PCD"));
		TXStateTable statetbl = procedure.getStateTable();
		
		// Contexto de ejecución de las reglas
		EventManager eventmgr = new EventManager(ctx);
		eventmgr.newContext();
		eventmgr.getRuleContextBuilder().addContext(process);

		Iterator it = statetbl.nextStages(eventmgr,
				Integer.parseInt(processModelStageId)).iterator();
		List list = new ArrayList();
		while (it.hasNext()) {
			PNodoDAO pNodo = procedure.getNode(ctx.getConnection(),
					((Integer) it.next()).intValue());
			list.add(pNodo.getString("ID_NODO_BPM"));
		}
		return list;
	}
	
	/**
	 * Obtiene la fase previa para el proceso instanciado
	 * @param processId Identificador del proceso instanciado
	 * @param processStageId Identificador de la fase de partida para obtener la anterior
	 * @return Identificador de la fase previa en el flujo para el proceso instanciado
	 * @throws ISPACException
	 */
	public String getPreviousStage(String processId, String processModelStageId)
			throws ISPACException {
		IProcess process = ctx.getAPI().getProcess(Integer.parseInt(processId));
		TXProcedure procedure = TXProcedureMgr.getInstance().getProcedure(ctx,
				process.getInt("ID_PCD"));
		TXStateTable statetbl = procedure.getStateTable();

		String previousStageUID = null;
		Set set = statetbl
				.previousStages(Integer.parseInt(processModelStageId));
		if (set.size() > 1) {
			throw new ISPACInfo("exception.expedients.previousStage.moreThanOne");
		}
		Iterator it = set.iterator();
		if (it.hasNext()) {

			int nodeId = ((Integer) it.next()).intValue();

			// Si el nodo anterior es un punto de sincronización, error
			PNodoDAO pNodo = procedure.getNode(ctx.getConnection(), nodeId);
			if (pNodo.isSyncNode()){
				String errorMessage ="exception.expedients.previousStage.isNode";
				if (process.isSubProcess())
					errorMessage ="exception.expedients.previousActivity.isNode";
				throw new ISPACInfo(errorMessage);
			}
			previousStageUID = String.valueOf(nodeId);
		}

		return previousStageUID;
	}
	

	public String getInitialStage(String processModelId) throws ISPACException {
		TXProcedure procedure = TXProcedureMgr.getInstance().getProcedure(ctx,
				Integer.parseInt(processModelId));
		PFaseDAO pStage = procedure.getInitialStage();
		return "" + pStage.getKeyInt();
	}	
	

	
	/**
	 * Finaliza una actividad de un subproceso
	 * @param activityId Identificador de una actividad instanciada 
	 * @throws ISPACException
	 */
//	public void closeTaskActivity( String activityId)
//			throws ISPACException {
//		// TODO Implementar
//	}

	
	/**
	 * Instancia una actividad para un subproceso
	 * @param subprocessId Identificador del subproceso instaciado
	 * @param pmActivityId Identificador de la actividad a instanciar
	 * @param respId Identificador del responsable a establecer en la actividad a instaciar
	 * @return Jerarquía de UID's de los objetos implicados
	 * @throws ISPACException
	 */
//	public BpmUIDs instanceTaskActivity( 
//				String subprocessId, String pmActivityId, String respId)
//			throws ISPACException {
//		return new BpmUIDs();
//	}
	
	/**
	 * Obtiene las actividades siguientes para el subproceso instanciado
	 * @param taskId Identificador del subproceso instanciado
	 * @return Lista de identificadores de las siguientes actividades del flujo para el subproceso instanciado 
	 * @throws ISPACException
	 */
//	public List getNextActivities( String subprocessId)
//			throws ISPACException {
//		// TODO Implementar
//		return null;
//	}

	/**
	 * Obtiene la actividad previa para el subproceso instanciado
	 * @param subprocessId Identificador del subproceso instanciado
	 * @return Identificador de la actividad previa en el flujo para el subproceso instanciado
	 * @throws ISPACException
	 */	
//	public String getPreviousActivity( String subprocessId)
//			throws ISPACException {
//		// TODO Implementar
//		return null;
//	}
	
	
	/**
	 * Finaliza un trámite
	 * @param taskId Identificador de un trámite instanciado
	 * @throws ISPACException
	 */
	public void endTask( String taskId) 
			throws ISPACException {
		if (logger.isInfoEnabled()) {
			logger.info("Cerrar trámite en BPM SPAC: " + taskId);
		}
	}

	/**
	 * Finaliza un conjunto de trámites de una fase de un proceso
	 * @param processId Identificador del proceso instanciado
	 * @param stageId Identificador de fase instanciada de un proceso
	 * @throws ISPACException
	 */
	public void endTasks( String processId, String stageId)
			throws ISPACException {
		// TODO Implementar
	}

	/**
	 * Finaliza un conjunto de trámites
	 * @param taskIds Lista de identificadores de trámites a finalizar
	 * @throws ISPACException
	 */
	public void endTasks( List taskIds) 
			throws ISPACException {
		// TODO Implementar
	}
	
	/**
	 * Delega un proceso
	 * @param processId Identificador de un proceso
	 * @param respId Identificador de responsable
	 * @throws ISPACException si ocurre algún error.
	 */
	public void delegateProcess( String processId, 
			String respId) throws ISPACException {
		if (logger.isInfoEnabled()) {
			logger.info("Delegar proceso en BPM SPAC: " + processId + " => "
					+ respId);
		}
	}
	
	/**
	 * Delega una fase
	 * @param stageId Identificador de una fase
	 * @param respId Identificador de responsable
	 * @throws ISPACException si ocurre algún error.
	 */
	public void delegateStage( String stageId, String respId)
			throws ISPACException {
		if (logger.isInfoEnabled()) {
			logger.info("Delegar fase en BPM SPAC: " + stageId + " => "
					+ respId);
		}
	}

	/**
	 * Delega un trámite
	 * @param taskId Identificador de un trámite 
	 * @param respId Identificador de responsable
	 * @throws ISPACException si ocurre algún error.
	 */
	public void delegateTask( String taskId, String respId)
			throws ISPACException {
		if (logger.isInfoEnabled()) {
			logger.info("Delegar trámite en BPM SPAC: " + taskId + " => "
					+ respId);
		}
	}

	/**
	 * Delega una actividad
	 * @param stageId Identificador de una fase instanciada
	 * @param respId Identificador de responsable
	 * @throws ISPACException
	 */
//	public void delegateTaskActiviy( String stageId, 
//			String respId) throws ISPACException {
//		// TODO Implementar
//	}
	
	/**
	 * Elimina un trámite.
	 * @param taskId Identificador de un trámite. 
	 * @throws ISPACException si ocurre algún error.
	 */
	public void deleteTask( String taskId)
			throws ISPACException {
		if (logger.isInfoEnabled()) {
			logger.info("Eliminar trámite en BPM SPAC: " + taskId);
		}
	}

	/**
	 * Adquiere una fase de un proceso por parte de un usuario.
	 * @param stageId Identificador de la fase instanciada a adquirir
	 * @param respId Identificador del responsable que va a adquirir la fase
	 * @throws ISPACException
	 */
	public boolean acquireStage( String stageId, String respId)
			throws ISPACException {
		boolean result = true;
		return result;
	}

	/**
	 * Adquiere una actividad de un trámite por parte de un usuario.
	 * @param activityId Identificador de la actividad instanciada a adquirir 
	 * @param respId Identificador del responsable que va a adquirir la actividad
	 * @throws ISPACException
	 */
//	public void acquireTaskActivity( String activityId, 
//			String respId) throws ISPACException {
//		// TODO Implementar
//	}
	
	/**
	 * Finaliza un proceso
	 * @param processId Identificador del proceso a finalizar
	 * @throws ISPACException
	 */
	public void endProcess( String processId)
			throws ISPACException {
		if (logger.isInfoEnabled()) {
			logger.info("Cerrar proceso en BPM SPAC: " + processId);
		}
	}
	
	
	/**
	 * Finaliza un subproceso
	 * @param subProcessId Identificador del subproceso
	 * @throws ISPACException
	 */
	public void endSubprocess( String subProcessId)
			throws ISPACException {
		// TODO Implementar
	}

	public String getResponsibleProcess( String processId) throws ISPACException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getResponsibleTask( String taskId) throws ISPACException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getResponsibleProcessStage( String stageId) throws ISPACException {
		IStage stage = ctx.getAPI().getStage(Integer.parseInt(stageId));
		return stage.getString("ID_RESP");
	}
	
	
//	public String getResponsibleTaskActivity( String taskActivyId) throws ISPACException {
//		// TODO Auto-generated method stub
//		return null;
//	}


	public List processSyncNode(String nodeUID) throws ISPACException {
		return null;
	}

	/* 
	 * =========================================================================
	 * =========================================================================
	 * 
	 * 						CATÁLOGO DE PROCEDIMIENTOS
	 * 
	 * =========================================================================
	 * =========================================================================
	 */

	
	/* 
	 * =========================================================================
	 * Métodos para gestionar el modelo de procesos
	 * =========================================================================
	 */
	
	/**
	 * Crea el modelo de procesos.
	 * @param processModel Información del modelo de procesos.
	 * @return Identificador del modelo de procesos creado.
	 * @throws ISPACException si ocurre algún error.
	 */
	public String createProcess(Process processModel) 
			throws ISPACException {
		if (logger.isInfoEnabled()) {
			logger.info("Crear modelo de procesos en BPM SPAC: " 
					+ processModel);
		}
		return String.valueOf(processModel.getSpacProcessId());
	}

	
	public void installProcess(String processId) throws ISPACException {
		if (logger.isInfoEnabled()) {
			logger.info("Pasar a vigente el proceso identificado por: "+processId);
		}
	}
	
	
	public String createTask(Process taskModel) throws ISPACException {
		if (logger.isInfoEnabled()) {
			logger.info("Crear trámite en BPM SPAC: " 
					+ taskModel);
		}
		return String.valueOf(taskModel.getSpacProcessId());
	}
	
	
	/**
	 * Actualiza el responsable del modelo de procesos.
	 * @param processModelId Identificador del modelo de procesos.
	 * @param respId Identificador del responsable.
	 * @throws ISPACException si ocurre algún error. 
	 */
	public void updateProcessResponsible(String processModelId, 
			String respId) throws ISPACException {
		if (logger.isInfoEnabled()) {
			logger.info("Actualizar responsable del modelo de procesos en BPM SPAC: " 
					+ processModelId + " - " + respId);
		}
	}

	/**
	 * Elimina el modelo de procesos.
	 * @param processModelId Identificador del modelo de procesos.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void deleteProcess(String processModelId) throws ISPACException {
		if (logger.isInfoEnabled()) {
			logger.info("Eliminar modelo de procesos en BPM SPAC: " 
					+ processModelId);
		}
	}

	
	/* 
	 * =========================================================================
	 * Métodos para gestionar los nodos del modelo de procesos
	 * =========================================================================
	 */

	/**
	 * Crea un nodo.
	 * @param processId Identificador del proceso.
	 * @param node Información del nodo.
	 * @return Identificador del nodo creado.
	 * @throws ISPACException si ocurre algún error. 
	 */
	public String createNode(String processId, ProcessNode node) 
			throws ISPACException {
		if (logger.isInfoEnabled()) {
			logger.info("Crear nodo en BPM SPAC: " + node);
		}
		return String.valueOf(node.getSpacNodeId());
	}

	/**
	 * Actualiza el responsable del nodo.
	 * @param nodeId Identificador del nodo.
	 * @param respId Identificador del responsable.
	 * @throws ISPACException si ocurre algún error. 
	 */
	public void updateNodeResponsible(String nodeId, String respId) 
			throws ISPACException {
		if (logger.isInfoEnabled()) {
			logger.info("Actualizar responsable de nodo en BPM SPAC: " + nodeId 
					+ " - " + respId);
		}
	}

	/**
	 * Asocia un subproceso a un nodo.
	 * @param nodeId Identificador del nodo.
	 * @param subProcessId Identificador del subproceso.
	 * @throws ISPACException si ocurre algún error. 
	 */
	public void addNodeSubProcess(String nodeId, String subProcessId) 
			throws ISPACException {
		if (logger.isInfoEnabled()) {
			logger.info("Añadir el subproceso al nodo: " + nodeId 
					+ " - " + subProcessId);
		}
	}

	/**
	 * Elimina la relación entre un subproceso y un nodo.
	 * @param nodeId Identificador del nodo.
	 * @param subProcessId Identificador del subproceso.
	 * @throws ISPACException si ocurre algún error. 
	 */
	public void removeNodeSubProcess(String nodeId, String subProcessId) 
			throws ISPACException {
		if (logger.isInfoEnabled()) {
			logger.info("Elimina el subproceso del nodo: " + nodeId 
					+ " - " + subProcessId);
		}
	}

	/**
	 * Elimina la relación de los procesos de un nodo.
	 * @param nodeId Identificador del nodo.
	 * @throws ISPACException si ocurre algún error. 
	 */
	public void removeNodeSubProcess(String nodeId) throws ISPACException {
		if (logger.isInfoEnabled()) {
			logger.info("Elimina los subprocesos del nodo: " + nodeId);
		}
	}

	/**
	 * Elimina la información de un nodo.
	 * @param nodeId Identificador del nodo.
	 * @throws ISPACException si ocurre algún error. 
	 */
	public void deleteNode(String processId, String nodeId) throws ISPACException {
		if (logger.isInfoEnabled()) {
			logger.info("Eliminar nodo en BPM SPAC: " + nodeId);
		}
	}
	

	/* 
	 * =========================================================================
	 * Métodos para gestionar los flujos del modelo de procesos
	 * =========================================================================
	 */

	/**
	 * Crea un flujo.
	 * @param processId Identificador del proceso.
	 * @param flow Información del flujo.
	 * @return Identificador del flujo creado.
	 * @throws ISPACException si ocurre algún error. 
	 */
	public String createFlow(String processId, ProcessFlow flow) 
			throws ISPACException {
		if (logger.isInfoEnabled()) {
			logger.info("Crear flujo en BPM SPAC: " + flow);
		}
		return String.valueOf(flow.getSpacFlowId());
	}

	/**
	 * Elimina la información de un flujo.
	 * @param flowId Identificador del flujo.
	 * @throws ISPACException si ocurre algún error. 
	 */
	 	
	public void deleteFlow(String processId, String flowId) throws ISPACException{
		if (logger.isInfoEnabled()) {
			logger.info("Eliminar flujo en BPM SPAC: " + flowId);
		}
	}

	
	/* 
	 * =========================================================================
	 * Métodos para gestionar los subprocesos del modelo de procesos
	 * =========================================================================
	 */

	/**
	 * Crea un subproceso.
	 * @param subProcessModel Información del modelo de subprocesos.
	 * @return Identificador del subproceso creado.
	 * @throws ISPACException si ocurre algún error.
	 */
	public String createSubProcess(Process subProcessModel) 
			throws ISPACException {
		if (logger.isInfoEnabled()) {
			logger.info("Crear modelo de subprocesos en BPM SPAC: " 
					+ subProcessModel);
		}
		return String.valueOf(subProcessModel.getSpacProcessId());
		
	}

	/**
	 * Actualiza el responsable del subproceso.
	 * @param subProcessId Identificador del subproceso.
	 * @param respId Identificador del responsable.
	 * @throws ISPACException si ocurre algún error. 
	 */
	public void updateSubProcessResponsible(String subProcessId, 
			String respId) throws ISPACException {
		if (logger.isInfoEnabled()) {
			logger.info("Actualizar responsable del modelo de subprocesos en BPM SPAC: " 
					+ subProcessId + " - " + respId);
		}
	}

	/**
	 * Elimina un subproceso.
	 * @param subProcessId Identificador del subproceso.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void deleteSubProcess(String subProcessId) throws ISPACException {
		if (logger.isInfoEnabled()) {
			logger.info("Eliminar modelo de subprocesos en BPM SPAC: " 
					+ subProcessId);
		}
	}

	public void captureStage(String stageId, String respId) throws ISPACException {
		
	}

	public void updateTaskResponsible(String taskId, String respId) throws ISPACException {
		// TODO Implementar
		
	}
}
