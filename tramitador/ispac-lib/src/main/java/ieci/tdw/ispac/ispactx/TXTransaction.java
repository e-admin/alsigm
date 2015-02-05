package ieci.tdw.ispac.ispactx;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.constants.ExpedienteMapConstants;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.api.item.ITask;
import ieci.tdw.ispac.api.item.PropName;
import ieci.tdw.ispac.api.rule.IRuleContextParams;
import ieci.tdw.ispac.audit.IspacAuditConstants;
import ieci.tdw.ispac.audit.business.IspacAuditoriaManager;
import ieci.tdw.ispac.audit.business.manager.impl.IspacAuditoriaManagerImpl;
import ieci.tdw.ispac.audit.business.vo.AuditContext;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventExpedienteAPapeleraVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventExpedienteAltaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventExpedienteBajaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventTramiteAltaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventTramiteBajaVO;
import ieci.tdw.ispac.audit.context.AuditContextHolder;
import ieci.tdw.ispac.ispaclib.context.ClientContext;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class TXTransaction implements ITXTransaction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TXTransaction.class);

	private ClientContext mccxt;

	private IspacAuditoriaManager auditoriaManager;

	public TXTransaction(ClientContext cs) throws ISPACException {
		super();
		mccxt = cs;
		auditoriaManager = new IspacAuditoriaManagerImpl();
	}

	protected void run(ITXAction[] actions) throws ISPACException {

		ITXAction action = null;
		TXTransactionDataContainer dataContainer = null;

		try {
			dataContainer = new TXTransactionDataContainer(mccxt);
			for (int i = 0; i < actions.length; i++) {
				action = actions[i];
				action.lock(mccxt, dataContainer);
				action.run(mccxt, dataContainer, this);
			}
			dataContainer.persist();
		} catch (ISPACException e) {
			dataContainer.setError();
			throw e;
		} catch (Exception e) {
			dataContainer.setError();
			throw new ISPACException(e);
		} finally {
			dataContainer.release();
		}
	}

	protected void run(ITXAction action) throws ISPACException {
		TXTransactionDataContainer dataContainer = null;

		try {
			dataContainer = new TXTransactionDataContainer(mccxt);
			action.lock(mccxt, dataContainer);
			action.run(mccxt, dataContainer, this);
			dataContainer.persist();
		} catch (ISPACException e) {
			dataContainer.setError();
			throw e;
		} catch (Exception e) {
			dataContainer.setError();
			throw new ISPACException(e);
		} finally {
			dataContainer.release();
		}
	}

	public int createProcess(int nProcedure) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().createProcess(nProcedure, null, null,
				IProcess.PROCESS_TYPE);
		run(action);

		int[] ids = (int[]) action.getResult("");

		
		// TODO: Auditar creación de expediente
		int idProcess = ids[0];
		IInvesflowAPI invesFlowAPI = mccxt.getAPI();
		IProcess process = invesFlowAPI.getProcess(ids[0]);
		String numExpediente = process.getString(PropName.EXP_NUMEXP);
		auditCreacionRegistro(numExpediente, idProcess);
		return ids[0];
	}

	public int createProcess(int nProcedure, Map params) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().createProcess(nProcedure, null, params,
				IProcess.PROCESS_TYPE);
		run(action);

		int[] ids = (int[]) action.getResult("");

		// TODO: Auditar creación de expediente		
		String numProcedimiento = (String) params.get(ExpedienteMapConstants.COD_PROCEDIMIENTO);
		int idProcess = ids[0];
		IInvesflowAPI invesFlowAPI = mccxt.getAPI();
		IProcess process = invesFlowAPI.getProcess(ids[0]);
		String numExpediente = process.getString(PropName.EXP_NUMEXP);
		auditCreacionRegistro(numExpediente, idProcess);

		return ids[0];
	}

	public int createProcess(int nProcedure, String numexp, Map params, int type)
			throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().createProcess(nProcedure, numexp, params,
				type);
		run(action);

		int[] ids = (int[]) action.getResult("");

		// TODO: Auditar creación de expediente

		int idProcess = ids[0];
		IInvesflowAPI invesFlowAPI = mccxt.getAPI();
		IProcess process = invesFlowAPI.getProcess(ids[0]);
		String numExpediente = process.getString(PropName.EXP_NUMEXP);
		auditCreacionRegistro(numExpediente, idProcess);
		return ids[0];
	}

	/**
	 * @param numexp
	 */
	private void auditCreacionRegistro(String numexp, int idProcess) {
		AuditContext auditContext = AuditContextHolder.getAuditContext();

		IspacAuditEventExpedienteAltaVO evento = new IspacAuditEventExpedienteAltaVO();
		evento.setAppDescription(IspacAuditConstants.APP_DESCRIPTION);
		evento.setAppId(IspacAuditConstants.getAppId());
		evento.setNumExpediente(numexp);
		evento.setIdProceso(String.valueOf(idProcess));	
		evento.setIdUser("");
		evento.setUser("");
		evento.setUserHostName("");
		evento.setUserIp("");

		evento.setFecha(new Date());

		if (auditContext != null) {			
			evento.setUserHostName(auditContext.getUserHost());
			evento.setUserIp(auditContext.getUserIP());
			evento.setUser(auditContext.getUser());
			evento.setIdUser(auditContext.getUserId());
		} else {
			logger.error("ERROR EN LA AUDITORÍA. No está disponible el contexto de auditoría en el thread local. Faltan los siguientes valores por auditar: userId, user, userHost y userIp");
		}
		logger.info("Auditando la creación del expediente");
		auditoriaManager.audit(evento);
	}

	public int[] createSubProcess(int nProcedure, String numexp, String subProcessUID,
			String activityUID, String subProcessRespId, String activityRespId)
			throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().createSubProcess(nProcedure, numexp,
				subProcessUID, activityUID, subProcessRespId, activityRespId);
		run(action);

		return (int[]) action.getResult("");
	}

	public int[] createSubProcess(int nProcedure, String numexp, String subProcessUID,
			String activityUID, String subProcessRespId, String activityRespId, Map params)
			throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().createSubProcess(nProcedure, numexp,
				subProcessUID, activityUID, subProcessRespId, activityRespId, params);
		run(action);

		return (int[]) action.getResult("");
	}

	public void cancelProcess(int nIdProc) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().cancelProcess(nIdProc);
		run(action);
	}

	public void cancelProcess(int nIdProc, Map params) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().cancelProcess(nIdProc, params);
		run(action);
	}

	public void resumeProcess(int nIdProc) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().resumeProcess(nIdProc);
		run(action);
	}

	public void resumeProcess(int nIdProc, Map params) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().resumeProcess(nIdProc, params);
		run(action);
	}

	public void redeployProcess(int nIdProc, int nIdStagePCD) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().redeployProcess(nIdProc, nIdStagePCD);
		run(action);
	}

	public void activateStage(int nIdProc, int nIdStagePCD) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().activateStage(nIdProc, nIdStagePCD);
		run(action);
	}

	public void delegateProc(int nIdProc, String IdResp) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().delegateProc(nIdProc, IdResp);
		run(action);
	}

	public void delegateProc(int nIdProc, String IdResp, String nameResp) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().delegateProc(nIdProc, IdResp, nameResp);
		run(action);
	}

	public void delegateProc(int nIdProc, String IdResp, Map params) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().delegateProc(nIdProc, IdResp, params);
		run(action);
	}

	public void delegateProc(int nIdProc, String IdResp, String nameResp, Map params)
			throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().delegateProc(nIdProc, IdResp, nameResp,
				params);
		run(action);
	}

	public void delegateStage(int nIdStage, String IdResp) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().delegateStage(nIdStage, IdResp);
		run(action);
	}

	public void delegateStage(int nIdStage, String IdResp, Map params) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().delegateStage(nIdStage, IdResp, params);
		run(action);
	}

	public void delegateStage(int nIdStage, String IdResp, String nameResp) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().delegateStage(nIdStage, IdResp, nameResp);
		run(action);
	}

	public void delegateStage(int nIdStage, String IdResp, String nameResp, Map params)
			throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().delegateStage(nIdStage, IdResp, nameResp,
				params);
		run(action);
	}

	public void delegateTask(int nIdTask, String IdResp) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().delegateTask(nIdTask, IdResp);
		run(action);
	}

	public void delegateTask(int nIdTask, String IdResp, String nameResp) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().delegateTask(nIdTask, IdResp, nameResp);
		run(action);
	}

	public void delegateTask(int nIdTask, String IdResp, String nameResp, Map params)
			throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().delegateTask(nIdTask, IdResp, nameResp,
				params);
		run(action);
	}

	public void delegateTask(int nIdTask, String IdResp, Map params) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().delegateTask(nIdTask, IdResp, params);
		run(action);
	}

	public void closeStage(int nIdStage) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().closeStageForward(nIdStage);
		run(action);
	}

	public void closeStage(int nIdStage, Map params) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().closeStageForward(nIdStage, params);
		run(action);
	}

	public void closeStage(int nIdStage, boolean endTasks) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().closeStageForward(nIdStage, endTasks);
		run(action);
	}

	public void closeStage(int nIdStage, boolean endTasks, Map params) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().closeStageForward(nIdStage, endTasks,
				params);
		run(action);
	}

	public void openNextStages(int nIdProcess, int nIdPcdStage) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().openNextStages(nIdProcess, nIdPcdStage);
		run(action);
	}

	public void openNextStages(int nIdProcess, int nIdPcdStage, Map params) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().openNextStages(nIdProcess, nIdPcdStage,
				params);
		run(action);
	}

	public void openPreviousStage(int nIdProcess, int nIdPcdStage) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().openPreviousStage(nIdProcess, nIdPcdStage);
		run(action);
	}

	public void openPreviousStage(int nIdProcess, int nIdPcdStage, Map params)
			throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().openPreviousStage(nIdProcess, nIdPcdStage,
				params);
		run(action);
	}

	public void deployNextStage(int nIdProcess, int nIdPcdStage, int nIdStage)
			throws ISPACException {

		// ITXAction
		// action=TXActionFactory.getInstance().deployNextStage(nIdStage);
		// run(action);

		ITXAction actionClose = TXActionFactory.getInstance().closeStageForward(nIdStage);
		ITXAction actionOpen = TXActionFactory.getInstance()
				.openNextStages(nIdProcess, nIdPcdStage);
		run(new ITXAction[] { actionClose, actionOpen });
	}

	public void deployNextStage(int nIdProcess, int nIdPcdStage, int nIdStage, Map params)
			throws ISPACException {

		// ITXAction
		// action=TXActionFactory.getInstance().deployNextStage(nIdStage);
		// run(action);

		ITXAction actionClose = TXActionFactory.getInstance().closeStageForward(nIdStage, params);
		ITXAction actionOpen = TXActionFactory.getInstance().openNextStages(nIdProcess,
				nIdPcdStage, params);
		run(new ITXAction[] { actionClose, actionOpen });
	}

	public void gotoStage(int nIdProcess, int nIdPcdStage, int nIdStage, boolean endTasks)
			throws ISPACException {
		ITXAction actionClose = TXActionFactory.getInstance().closeStageForward(nIdStage, endTasks);
		ITXAction actionOpen = TXActionFactory.getInstance().openStage(nIdProcess, nIdPcdStage);
		run(new ITXAction[] { actionClose, actionOpen });
	}

	public void gotoStage(int nIdProcess, int nIdPcdStage, int nIdStage, boolean endTasks,
			Map params) throws ISPACException {
		ITXAction actionClose = TXActionFactory.getInstance().closeStageForward(nIdStage, endTasks,
				params);
		ITXAction actionOpen = TXActionFactory.getInstance().openStage(nIdProcess, nIdPcdStage,
				params);
		run(new ITXAction[] { actionClose, actionOpen });
	}

	public void gotoStage(int nIdProcess, int nIdPcdStage, int nIdStage) throws ISPACException {
		gotoStage(nIdProcess, nIdPcdStage, nIdStage, false);
	}

	public void gotoStage(int nIdProcess, int nIdPcdStage, boolean endTasks) throws ISPACException {
		IItemCollection itemcol = mccxt.getAPI().getStagesProcess(nIdProcess);

		List list = itemcol.toList();
		ITXAction[] txactions = new ITXAction[list.size() + 1];

		for (int i = 0; i < list.size(); i++) {
			IStage stage = (IStage) list.get(i);
			txactions[i] = TXActionFactory.getInstance().closeStageForward(stage.getKeyInt(),
					endTasks);
		}
		txactions[txactions.length - 1] = TXActionFactory.getInstance().openStage(nIdProcess,
				nIdPcdStage);
		run(txactions);
	}

	public void gotoStage(int nIdProcess, int nIdPcdStage, boolean endTasks, Map params)
			throws ISPACException {
		IItemCollection itemcol = mccxt.getAPI().getStagesProcess(nIdProcess);

		List list = itemcol.toList();
		ITXAction[] txactions = new ITXAction[list.size() + 1];

		for (int i = 0; i < list.size(); i++) {
			IStage stage = (IStage) list.get(i);
			txactions[i] = TXActionFactory.getInstance().closeStageForward(stage.getKeyInt(),
					endTasks, params);
		}
		txactions[txactions.length - 1] = TXActionFactory.getInstance().openStage(nIdProcess,
				nIdPcdStage, params);
		run(txactions);
	}

	public void gotoStage(int nIdProcess, int nIdPcdStage) throws ISPACException {
		gotoStage(nIdProcess, nIdPcdStage, false);
	}

	public void gotoStage(int nIdProcess, int nIdPcdStage, Map params) throws ISPACException {
		gotoStage(nIdProcess, nIdPcdStage, false, params);
	}

	public void closeProcess(int nIdProcess) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().closeProcess(nIdProcess);
		run(action);
	}

	public void closeProcess(int nIdProcess, Map params) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().closeProcess(nIdProcess, params);
		run(action);
	}

	/**
	 * Cambia el proceso a estado archivado.
	 * 
	 * @param nIdProcess
	 *            identificador del proceso
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public void archiveProcess(int nIdProcess) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().archiveProcess(nIdProcess);
		run(action);
	}

	public void archiveProcess(int nIdProcess, Map params) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().archiveProcess(nIdProcess, params);
		run(action);
	}

	public void closeTask(int nIdTask) throws ISPACException {
		
		ITask task = mccxt.getAPI().getTask(nIdTask);
		String numExpediente = task.getString(PropName.TRM_NUMEXP);
		
		ITXAction action = TXActionFactory.getInstance().closeTask(nIdTask);
		run(action);
		//TODO: Auditar
		auditDeleteTask(nIdTask, numExpediente);
	}

	public void closeTask(int nIdTask, Map params) throws ISPACException {
		ITask task = mccxt.getAPI().getTask(nIdTask);
		String numExpediente = task.getString(PropName.TRM_NUMEXP);
		
		ITXAction action = TXActionFactory.getInstance().closeTask(nIdTask, params);
		run(action);
		//TODO: Auditar
		auditDeleteTask(nIdTask, numExpediente);
	}

	public void redeployPreviousStage(int nIdProcess, int nIdPcdStage, int nIdStage)
			throws ISPACException {

		// ITXAction
		// action=TXActionFactory.getInstance().redeployPreviousStage(nIdStage);
		// run(action);

		ITXAction actionClose = TXActionFactory.getInstance().closeStageBack(nIdStage);
		ITXAction actionOpen = TXActionFactory.getInstance().openPreviousStage(nIdProcess,
				nIdPcdStage);
		run(new ITXAction[] { actionClose, actionOpen });
	}

	public void redeployPreviousStage(int nIdProcess, int nIdPcdStage, int nIdStage, Map params)
			throws ISPACException {

		// ITXAction
		// action=TXActionFactory.getInstance().redeployPreviousStage(nIdStage);
		// run(action);

		ITXAction actionClose = TXActionFactory.getInstance().closeStageBack(nIdStage, params);
		ITXAction actionOpen = TXActionFactory.getInstance().openPreviousStage(nIdProcess,
				nIdPcdStage, params);
		run(new ITXAction[] { actionClose, actionOpen });
	}

	public int createTask(int nIdStage, int nIdTaskPCD) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().createTask(nIdStage, nIdTaskPCD);
		run(action);
		// TODO: Auditar creación de trámite
		
		Integer idTask = (Integer) action.getResult("");
		auditCreateTask(idTask.intValue());
		return idTask.intValue();
	}

	public int createTask(int nIdStage, int nIdTaskPCD, Map params) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().createTask(nIdStage, nIdTaskPCD, params);
		run(action);
		// TODO: Auditar creación de trámite
		Integer idTask = (Integer) action.getResult("");
		
		
		auditCreateTask(idTask.intValue());
		return idTask.intValue();
	}

	public int createTask(int nIdPcd, int nIdStage, int nIdTaskPCD, String numExp)
			throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().createTask(nIdPcd, nIdStage, nIdTaskPCD,
				numExp);
		run(action);
		
		Integer idTask = (Integer) action.getResult("");
		auditCreateTask(idTask.intValue());
		return idTask.intValue();
	}

	/**
	 * @param nIdTaskPCD
	 * @param numExp
	 * @throws ISPACException 
	 */
	private void auditCreateTask(int idTask) throws ISPACException {
		
		ITask task = mccxt.getAPI().getTask(idTask);
		String numExpediente = task.getString(PropName.TRM_NUMEXP);
		
		AuditContext auditContext = AuditContextHolder.getAuditContext();

		IspacAuditEventTramiteAltaVO evento = new IspacAuditEventTramiteAltaVO();
		evento.setAppDescription(IspacAuditConstants.APP_DESCRIPTION);
		evento.setAppId(IspacAuditConstants.getAppId());
		evento.setIdUser("");
		evento.setUser("");
		evento.setNumExpediente(numExpediente);
		evento.setIdTramite(String.valueOf(idTask));

		evento.setFecha(new Date());

		if (auditContext != null) {
			evento.setUserHostName(auditContext.getUserHost());
			evento.setUserIp(auditContext.getUserIP());
			evento.setUser(auditContext.getUser());
			evento.setIdUser(auditContext.getUserId());
		} else {
			logger.error("ERROR EN LA AUDITORÍA. No está disponible el contexto de auditoría en el thread local. Faltan los siguientes valores por auditar: userId, user, userHost y userIp");
		}
		logger.info("Auditando la creación del trámite");
		auditoriaManager.audit(evento);
	}

	
	private void auditDeleteTask(int idTask, String numExpediente) throws ISPACException {
			
		AuditContext auditContext = AuditContextHolder.getAuditContext();

		IspacAuditEventTramiteBajaVO evento = new IspacAuditEventTramiteBajaVO();
		evento.setAppDescription(IspacAuditConstants.APP_DESCRIPTION);
		evento.setAppId(IspacAuditConstants.getAppId());
		evento.setIdUser("");
		evento.setUser("");
		evento.setUserHostName("");
		evento.setUserIp("");
		evento.setNumExpediente(numExpediente);
		evento.setId(String.valueOf(idTask));

		evento.setFecha(new Date());

		if (auditContext != null) {
			evento.setUserHostName(auditContext.getUserHost());
			evento.setUserIp(auditContext.getUserIP());
			evento.setUser(auditContext.getUser());
			evento.setIdUser(auditContext.getUserId());
		} else {
			logger.error("ERROR EN LA AUDITORÍA. No está disponible el contexto de auditoría en el thread local. Faltan los siguientes valores por auditar: userId, user, userHost y userIp");
		}
		logger.info("Auditando la creación del trámite");
		auditoriaManager.audit(evento);
	}
	
	public int createTask(int nIdPcd, int nIdStage, int nIdTaskPCD, String numExp, Map params)
			throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().createTask(nIdPcd, nIdStage, nIdTaskPCD,
				numExp, params);
		run(action);
		// TODO: Auditar creación de trámite
		return ((Integer) action.getResult("")).intValue();
	}

	/**
	 * Crea una nueva tramitación agrupada .
	 * 
	 * @param idFase
	 *            identificador de la fase en el proceso
	 * @param numExps
	 *            números de expedientes agrupados
	 * @return identificador de la tramitación agrupada creada
	 * @throws ISPACException
	 */
	public int createBatchTask(int idFase, String[] numExps) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().createBatchTask(idFase, numExps);
		run(action);
		return ((Integer) action.getResult("")).intValue();
	}

	public void deleteTask(int nIdTask) throws ISPACException {
		
		ITask task = mccxt.getAPI().getTask(nIdTask);
		String numExpediente = task.getString(PropName.TRM_NUMEXP);
		
		ITXAction action = TXActionFactory.getInstance().deleteTask(nIdTask);
		run(action);
		//TODO: Auditar
		auditDeleteTask(nIdTask, numExpediente);
	}

	public void deleteTask(int nIdTask, Map params) throws ISPACException {
		ITask task = mccxt.getAPI().getTask(nIdTask);
		String numExpediente = task.getString(PropName.TRM_NUMEXP);
		
		ITXAction action = TXActionFactory.getInstance().deleteTask(nIdTask, params);
		run(action);
		//TODO: Auditar
		auditDeleteTask(nIdTask, numExpediente);
	}

	public void deleteSubProcess(int nIdSubProcess, int nIdTask) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().deleteSubProcess(nIdSubProcess, nIdTask);
		run(action);
	}

	public void deleteSubProcess(int nIdSubProcess, int nIdTask, Map params) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().deleteSubProcess(nIdSubProcess, nIdTask,
				params);
		run(action);
	}

	public void newMilestone(int nIdProc, int nIdStagePCD, int nIdTaskPCD, int nIdInfo,
			String info, String desc) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().newMilestone(nIdProc, nIdStagePCD,
				nIdTaskPCD, nIdInfo, info, desc);
		run(action);
	}

	public void remark(int nIdProc, int nIdStage, int nIdTask, String Text) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().remark(nIdProc, nIdStage, nIdTask, Text);
		run(action);
	}

	public Object executeRule(int nIdRule) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().executeRule(nIdRule);
		run(action);
		return action.getResult("");
	}

	public Object executeRule(int nIdRule, Map parameters) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().executeRule(nIdRule, parameters);
		run(action);
		return action.getResult("");
	}

	public Object executeContextRule(int nIdRule, int nIdProc, int nIdStage, int nIdTask,
			Map parameters) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().executeContextRule(nIdRule, nIdProc,
				nIdStage, nIdTask, parameters);
		run(action);
		return action.getResult("");
	}

	public Object executeContextRule(int nIdRule, IRuleContextParams rctxobj) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().executeContextRule(nIdRule, rctxobj);
		run(action);
		return action.getResult("");
	}

	public Object executeEvents(int TypeObj, int nIdObj, int EventCode, IRuleContextParams rctxobj)
			throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().executeEvents(TypeObj, nIdObj, EventCode,
				rctxobj);
		run(action);
		return action.getResult("");
	}

	public int updateBatchTask(IItem batchTask) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().updateBatchTask(batchTask);
		run(action);
		return ((Integer) action.getResult("")).intValue();

	}

	/**
	 * Elimina la tramitación agrupada.
	 * 
	 * @param batchTaskId
	 *            Identificador de la tramitación agrupada.
	 * @return Número de tramitaciones agrupadas eliminadas.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public int deleteBatchTask(int batchTaskId) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().deleteBatchTask(batchTaskId);
		run(action);
		return ((Integer) action.getResult("")).intValue();
	}

	public void setCalendar(int tipoObjeto, int idObjeto, int idCalendario) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().setCalendar(tipoObjeto, idObjeto,
				idCalendario);
		run(action);
	}

	public void pauseDeadline(int tipoObjeto, int idObjeto) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().pauseDeadline(tipoObjeto, idObjeto);
		run(action);
	}

	public void resumeDeadline(int tipoObjeto, int idObjeto) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().resumeDeadline(tipoObjeto, idObjeto);
		run(action);
	}

	public void recalculateDeadline(int tipoObjeto, int idObjeto) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().recalculateDeadline(tipoObjeto, idObjeto);
		run(action);
	}

	public void recalculateDeadline(int tipoObjeto, int idObjeto, Map params) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().recalculateDeadline(tipoObjeto, idObjeto,
				params);
		run(action);
	}

	public void setBaseDate(int tipoObjeto, int idObjeto, Date baseDate) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance()
				.setBaseDate(tipoObjeto, idObjeto, baseDate);
		run(action);
	}

	public void sendProcessToTrash(int idProc, Map params) throws ISPACException {
		String numExpediente="";
		try {
			IInvesflowAPI invesFlowAPI = mccxt.getAPI();
			IProcess process = invesFlowAPI.getProcess(idProc);
			numExpediente = process.getString(PropName.EXP_NUMEXP);
		} catch (Exception e) {
			logger.error("Error al obtener el expediente para obtener el número de expediente", e);
		}
		
		ITXAction action = TXActionFactory.getInstance().sendProcessToTrash(idProc, params);
		run(action);
		auditSendProcessToTrash(idProc, numExpediente);
	}

	public void sendProcessToTrash(int idProc) throws ISPACException {
		
		String numExpediente="";
		try {
			IInvesflowAPI invesFlowAPI = mccxt.getAPI();
			IProcess process = invesFlowAPI.getProcess(idProc);
			numExpediente = process.getString(PropName.EXP_NUMEXP);
		} catch (Exception e) {
			logger.error("Error al obtener el expediente para obtener el número de expediente", e);
		}
		
		
		ITXAction action = TXActionFactory.getInstance().sendProcessToTrash(idProc);
		run(action);
		auditSendProcessToTrash(idProc,numExpediente);
	}

	/**
	 * @param idProc
	 */
	private void auditSendProcessToTrash(int idProc,String numExpediente) {
		AuditContext auditContext = AuditContextHolder.getAuditContext();

		IspacAuditEventExpedienteAPapeleraVO evento = new IspacAuditEventExpedienteAPapeleraVO();
		evento.setAppDescription(IspacAuditConstants.APP_DESCRIPTION);
		evento.setAppId(IspacAuditConstants.getAppId());
		evento.setNumExpediente(numExpediente);
		evento.setIdProcess(String.valueOf(idProc));
		evento.setUser("");
		evento.setIdUser("");
		evento.setUserHostName("");
		evento.setUserIp("");
		
		evento.setFecha(new Date());

		if (auditContext != null) {
			evento.setIdUser(auditContext.getUserId());
			evento.setUser(auditContext.getUser());
			evento.setUserHostName(auditContext.getUserHost());
			evento.setUserIp(auditContext.getUserIP());
		} else {
			logger.error("ERROR EN LA AUDITORÍA. No está disponible el contexto de auditoría en el thread local. Faltan los siguientes valores por auditar: userId, user, userHost y userIp");
		}
		logger.info("Auditando la eliminación del expediente");
		auditoriaManager.audit(evento);
	}
	
	/**
	 * @param idProc
	 */
	private void auditDeleteProcess(int idProc,String numExpediente) {
		AuditContext auditContext = AuditContextHolder.getAuditContext();

		IspacAuditEventExpedienteBajaVO evento = new IspacAuditEventExpedienteBajaVO();
		evento.setAppDescription(IspacAuditConstants.APP_DESCRIPTION);
		evento.setAppId(IspacAuditConstants.getAppId());
		evento.setNumExpediente(numExpediente);
		evento.setIdProcess(String.valueOf(idProc));

		evento.setIdUser("");
		evento.setUser("");
		evento.setUserHostName("");
		evento.setUserIp("");
		
		evento.setFecha(new Date());

		if (auditContext != null) {
			evento.setIdUser(auditContext.getUserId());
			evento.setUser(auditContext.getUser());
			evento.setUserHostName(auditContext.getUserHost());
			evento.setUserIp(auditContext.getUserIP());
		} else {
			logger.error("ERROR EN LA AUDITORÍA. No está disponible el contexto de auditoría en el thread local. Faltan los siguientes valores por auditar: userId, user, userHost y userIp");
		}
		logger.info("Auditando la eliminación del expediente");
		auditoriaManager.audit(evento);
	}

	public void cleanProcess(int idProc) throws ISPACException {

		ITXAction action = TXActionFactory.getInstance().cleanProcess(idProc);
		run(action);
	}

	public void cleanProcess(int idProc, Map params) throws ISPACException {

		ITXAction action = TXActionFactory.getInstance().cleanProcess(idProc, params);
		run(action);
	}

	public void deleteProcess(int idProc) throws ISPACException {
		
		String numExpediente="";
		try {
			IInvesflowAPI invesFlowAPI = mccxt.getAPI();
			IProcess process = invesFlowAPI.getProcess(idProc);
			numExpediente = process.getString(PropName.EXP_NUMEXP);
		} catch (Exception e) {
			logger.error("Error al obtener el expediente para obtener el número de expediente", e);
		}
		
		ITXAction action = TXActionFactory.getInstance().deleteProcess(idProc);
		run(action);

		//Auditar la eliminación definitiva del expediente
		auditDeleteProcess(idProc, numExpediente);
	}

	public void deleteProcess(int idProc, Map params) throws ISPACException {
		
		String numExpediente="";
		try {
			IInvesflowAPI invesFlowAPI = mccxt.getAPI();
			IProcess process = invesFlowAPI.getProcess(idProc);
			numExpediente = process.getString(PropName.EXP_NUMEXP);
		} catch (Exception e) {
			logger.error("Error al obtener el expediente para obtener el número de expediente", e);
		}
		
		ITXAction action = TXActionFactory.getInstance().deleteProcess(idProc, params);
		run(action);

		//Auditar la eliminación definitiva del expediente
		auditDeleteProcess(idProc, numExpediente);
	}

	public void restoreProcess(int idProc) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().restoreProcess(idProc);
		run(action);

	}

	public void restoreProcess(int idProc, Map params) throws ISPACException {
		ITXAction action = TXActionFactory.getInstance().restoreProcess(idProc, params);
		run(action);

	}
}
