package ieci.tdw.ispac.ispacweb.api.impl.states;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.api.item.ITask;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.DTTramiteDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.security.SecurityMgr;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerState;
import ieci.tdw.ispac.lock.LockManager;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;


public class TaskState extends BaseState {

	protected static final String FIELD_IDRESP 			= "ID_RESP";
	protected static final String FIELD_IDPCD 			= "ID_PCD";
	protected static final String FIELD_IDCTTRAMITE 	= "ID_CTTRAMITE";
	protected static final String FIELD_IDPCTRAMITE 	= "ID_TRAMITE";
	protected static final String FIELD_IDEXP 			= "ID_EXP";
	protected static final String FIELD_NUMEXP 			= "NUMEXP";
	protected static final String FIELD_IDFASEEXP 		= "ID_FASE_EXP";
	protected static final String FIELD_IDFASEPCD 		= "ID_FASE_PCD";
	protected static final String FIELD_IDTRAMCTL		= "ID_TRAM_CTL";
	protected static final String FIELD_IDTRAMPCD		= "ID_TRAM_PCD";
	protected static final String FIELD_IDTRAMEXP		= "ID_TRAM_EXP";
	protected static final String FIELD_IDRESPCLOSED	= "ID_RESP_CLOSED";
	
	private int taskLockStatus = LockManager.NOT_LOCKED;

	public TaskState(){
		
	}
	public TaskState(String stateticket) throws ISPACException {
		super(stateticket);
	}

	public TaskState(StateContext stateContext){
		super(stateContext);
	}

	public TaskState(int state, Map params,
			IClientContext cct) throws ISPACException {
		String[] taskIdstr = (String[])params.get(ManagerState.PARAM_TASKID);
		String[] entitystr = (String[])params.get(ManagerState.PARAM_ENTITYID);
		String[] keystr = (String[])params.get(ManagerState.PARAM_ENTREGID);

		int taskId = 0;
		int entity = 0;
		int key = 0;

		try
        {
            if (taskIdstr != null)
            	taskId = Integer.parseInt(taskIdstr[taskIdstr.length-1]);
            if (entitystr != null)
            	entity = Integer.parseInt(entitystr[entitystr.length-1]);
            if (keystr != null)
            	key = Integer.parseInt(keystr[keystr.length-1]);
        } catch (NumberFormatException e)
        {
            throw new ISPACException("TaskState: Parámetros incorrectos. ",e);
        }

		getStateContext(state, taskId, entity, key,  cct);
	}

	/**
	 * Obtiene el stateContext correcto. Calcula si el trámite a visualizar
	 * es cerrado, delegado o abierto.
	 *
	 * @param state   identificador del estado
	 * @param taskId identificador del trámite en el proceso
	 * @param entity  identificador de entidad
	 * @param key	  identificador de la entidad
	 * @param cct	  ClientContext
	 * @throws ISPACException
	 */
	protected void getStateContext(int state, int taskId,
			int entity, int key, IClientContext cct)
			throws ISPACException {

		mStateContext = new StateContext();
		mStateContext.setTaskId(taskId);
		mStateContext.setState(state);
		mStateContext.setEntity(entity);
		mStateContext.setKey(key);

		try
		{
			ITask task = cct.getAPI().getTask(taskId);
			//String sUID = task.getString(FIELD_IDRESP);
		  	//boolean isResponsible = cct.getAPI().getWorkListAPI().isInResponsibleList(sUID);

			int pcdId = task.getInt(FIELD_IDPCD);
			int taskCtlId = task.getInt(FIELD_IDCTTRAMITE);
			int taskPcdId = task.getInt(FIELD_IDPCTRAMITE);
			int processId = task.getInt(FIELD_IDEXP);
			String numexp = task.getString(FIELD_NUMEXP);
			int stageId = task.getInt(FIELD_IDFASEEXP);
			int stagePcdId = task.getInt(FIELD_IDFASEPCD);

			//mStateContext.setResponsible(isResponsible);
			mStateContext.setPcdId(pcdId);
			mStateContext.setTaskCtlId(taskCtlId);
			mStateContext.setTaskPcdId(taskPcdId);
			mStateContext.setProcessId(processId);
			mStateContext.setNumexp(numexp);
			mStateContext.setStageId(stageId);
			mStateContext.setStagePcdId(stagePcdId);
			// Establecer la responsabilidad del trámite
			calcResponsible(cct, task);
			
			// Establecer sólo lectura o bloqueo
			calcReadonly(cct);
		}
		// El trámite está cerrado
		catch (ISPACNullObject e) {
			
			IItem task = cct.getAPI().getEntitiesAPI().getTask(taskId);
			if (task == null) {
				
				// Trámite delegado borrado y no se tiene responsabilidad sobre el expediente
				throw new ISPACNullObject();
			}
			
			String numexp = task.getString(FIELD_NUMEXP);
			IProcess process = cct.getAPI().getProcess(numexp);

			calcResponsible(cct, (EntityDAO)task);
			// Trámite cerrado luego sólo consulta
			mStateContext.setReadonly(true);
			mStateContext.setPcdId(process.getInt(FIELD_IDPCD));
			mStateContext.setTaskId(task.getInt(FIELD_IDTRAMEXP));
			mStateContext.setTaskCtlId(task.getInt(FIELD_IDTRAMCTL));
			mStateContext.setTaskPcdId(task.getInt(FIELD_IDTRAMPCD));
			mStateContext.setProcessId(process.getKeyInt());
			mStateContext.setNumexp(numexp);
			mStateContext.setStageId(getStageId(cct, process, task.getInt("ID_FASE_PCD")));

			mStateContext.setStagePcdId(task.getInt(FIELD_IDFASEPCD));
		}

	}

		protected void calcResponsible(IClientContext cct, EntityDAO task) throws ISPACException {
			// Responsable del trámite cerrado
			String sUID = task.getString(FIELD_IDRESPCLOSED);
		  	boolean isResponsible = false;
		  	if (StringUtils.isNotEmpty(sUID)){
		  		isResponsible = cct.getAPI().getWorkListAPI().isInResponsibleList(sUID);	
		  	}

		  	if (!isResponsible) {

				DbCnt cnt = cct.getConnection();
				try {
					SecurityMgr securityMgr = new SecurityMgr(cnt);
					IInvesflowAPI invesFlowApi = cct.getAPI();
					String resp = invesFlowApi.getWorkListAPI().getRespString();

					if (!Responsible.SUPERVISOR.equals(resp)) {
						// Comprobar si existe algún permiso para la responsabilidad
						// del usuario conectado
						Map permissions = securityMgr.getPermissions(task, resp);

						if (permissions.containsKey(new Integer(ISecurityAPI.PERMISSION_TYPE_READ)) ||
								permissions.containsKey(new Integer(ISecurityAPI.PERMISSION_TYPE_EDIT))
							){
							isResponsible=true;
						}
					}else{
						isResponsible = true;
					}
				}
				finally	{
					cct.releaseConnection(cnt);
				}
			}
		  	if (!isResponsible){
		  		throw new ISPACInfo("exception.task.responsability");
		  	}
			mStateContext.setResponsible(isResponsible);
				
		}
		protected void calcResponsible(IClientContext cct, ITask task) throws ISPACException {

		// Responsable del trámite
		String sUID = task.getString(FIELD_IDRESP);
	  	boolean isResponsible = cct.getAPI().getWorkListAPI().isInResponsibleList(sUID);

		if (!isResponsible) {

			DbCnt cnt = cct.getConnection();
			try {
				SecurityMgr securityMgr = new SecurityMgr(cnt);
				IInvesflowAPI invesFlowApi = cct.getAPI();
				String resp = invesFlowApi.getWorkListAPI().getRespString();
				ISecurityAPI securityAPI = invesFlowApi.getSecurityAPI();
				IResponsible user = cct.getResponsible();

				if (!Responsible.SUPERVISOR.equals(resp)) {
					// Comprobar si existe algún permiso para la responsabilidad
					// del usuario conectado
					Map permissions = securityMgr.getPermissions(task, resp);

					if (permissions.containsKey(new Integer(ISecurityAPI.PERMISSION_TYPE_EDIT))) {
						//Hay que comprobar si alguno de los uid's que me otorga el permiso
						//de edición no es en modo seguimiento (supervisor en modo consulta) , porque
						//si todos son en modo seguimiento no soy responsable solo puedo visualizar
						if(securityAPI.existPermissionEditNotFollowSupervision(task, resp,user)){
							isResponsible = true;
						}
						else{
							mStateContext.setReadonly(true);
							mStateContext.setReadonlyReason(StateContext.READONLYREASON_FOLLOW_SUPERVISION);
						}
					} else if (permissions.containsKey(new Integer(ISecurityAPI.PERMISSION_TYPE_READ))) {
						mStateContext.setReadonly(true);
						mStateContext.setReadonlyReason(StateContext.READONLYREASON_FORM);
					}
				}
			}
			finally	{
				cct.releaseConnection(cnt);
			}
		}

		mStateContext.setResponsible(isResponsible);
	}
	/**
	 * @param cct Contexto del Cliente
	 * @param numexp Nº de expediente
	 * @param stagePcdId Identificador de la fase en el procedimiento.
	 * @return identificador de la fase instanciada segun expediente e identificador de fase en el procedimiento
	 * @throws ISPACException
	 */
	protected int getStageId(IClientContext cct, IProcess process, int stagePcdId) throws ISPACException {
		
		DbCnt cnt = cct.getConnection();
		
		try {
			
			IStage stage = process.getStage(cnt, stagePcdId);
			if (stage == null) {
				return 0;
			}
			return stage.getKeyInt();
			
		} finally	{
			cct.releaseConnection(cnt);
		}
	}
	
	public void exit(IClientContext cctx) throws ISPACException
	{
	    LockManager lockmgr = new LockManager(cctx);
	    
	    // Desbloquear el trámite?
		int lock = lockmgr.isLock(LockManager.LOCKTYPE_TASK, getTaskId());
		if (lock == LockManager.LOCKED_CURSESSION) {
			lockmgr.unlockTask(getTaskId());
		}
		
		// Desbloquear la entidad en el expediente?
		unblockEntity(cctx);
	}

	public void refresh() {

	}

	public void enter(IClientContext cctx) throws ISPACException
	{
		/*
	    LockManager lockmgr=new LockManager(cctx);
		int newtaskId = getTaskId();
		*/

		if (!isResponsible())
		    return;

		/*
		int lock = lockmgr.isLock(LockManager.LOCKTYPE_TASK,newtaskId);
		if(lock == LockManager.NOT_LOCKED)
		{
			lockmgr.lockTask(newtaskId);
		}
		*/
		
		// Comprobar si el trámite ha sido bloqueado por la sesión actual
		// y bloquear la entidad en el expediente o entidad ya bloqueada?
		if (taskLockStatus == LockManager.LOCKED_CURSESSION) {
		
			blockEntity(cctx);
		}
	}


	private void calcReadonly(IClientContext cctx) throws ISPACException
	{
	    LockManager lockmgr=new LockManager(cctx);
	    int newtaskId = getTaskId();

		if (!isResponsible())
		{
			if(!mStateContext.getReadonly()) {
				mStateContext.setReadonly(true);
				
				try {
					SecurityMgr securityMgr = new SecurityMgr(cctx.getConnection());
					
					ITask itemTask = cctx.getAPI().getTask(newtaskId);
					String supervisedUID = itemTask.getString(FIELD_IDRESP);
					IResponsible user = cctx.getResponsible();
					
					// Si el usuario no es responsable comprobar si es supervisor o tiene supervision en modo control de seguimiento 
					// o simplemente no le corresponde la responsabilidad
					if ((securityMgr.isFunction(user.getUID(), ISecurityAPI.FUNC_MONITORINGSUPERVISOR)) || 
						(securityMgr.isSupervised(user, supervisedUID, ISecurityAPI.SUPERV_FOLLOWEDMODE))) {
							
						mStateContext.setReadonlyReason(StateContext.READONLYREASON_FOLLOW_SUPERVISION);
					}
					else {
						mStateContext.setReadonlyReason(StateContext.READONLYREASON_NOT_RESPONSABILITY);
						throw new ISPACInfo("exception.task.responsability");
					}
				}
				catch(ISPACNullObject e) {
				}
			}
		    return;
		}
		
		// Bloqueo a nivel de trámite
		taskLockStatus = lockmgr.isLock(LockManager.LOCKTYPE_TASK,newtaskId);
		boolean isLock = taskLockStatus == LockManager.LOCKED;
		mStateContext.setReadonly(isLock);
		if (isLock) {
			mStateContext.setReadonlyReason(StateContext.READONLYREASON_LOCK);
		}
		else if (taskLockStatus == LockManager.NOT_LOCKED) {
			
			// Bloquear el trámite
			lockmgr.lockTask(newtaskId);
			taskLockStatus = LockManager.LOCKED_CURSESSION;
		}
	}

	public Map getParameters()
	{
		Map param = new HashMap();
		param.put(ManagerState.PARAM_TASKID, String.valueOf(getTaskId()));
		return param;
	}

	public String getLabel() {
		return ManagerState.LABEL_TASK;
	}

	public String getQueryString() {
		String params = "";
		if (getEntityId() > 0)
			params = params + "&" + ManagerState.PARAM_ENTITYID + "=" + getEntityId();
		if (getEntityRegId() != 0)
			params = params + "&" + ManagerState.PARAM_ENTREGID + "=" + getEntityRegId();

		return "?" + ManagerState.PARAM_TASKID +"=" + getTaskId() + params;
	}

	public boolean equals(IState iState) {
		boolean result = super.equals(iState);
		if (result) {
			if (getTaskId() != iState.getTaskId())
				result = false;
		}
		return result;
	}
	
	public void checkNewEntity(IState iState, IClientContext cctx) throws ISPACException {
		
		if (!isResponsible())
		    return;
		
		// Comprobar si la entidad seleccionada ha cambiado
		// cuando el trámite está bloqueado por la sesión actual
		if ((getEntityId() != iState.getEntityId()) &&
			(taskLockStatus == LockManager.LOCKED_CURSESSION)) {
						
			// Desbloquear la entidad en el expediente del estado anterior?
			iState.unblockEntity(cctx);
		}
		
		// Si la entidad no ha cambiado (refresco de la página o activación de la misma pestaña) 
		// cuando el trámite está bloqueado por la sesión actual
		// bloquear la entidad en el expediente para el estado actual 
		// o entidad ya bloqueada?
		if (taskLockStatus == LockManager.LOCKED_CURSESSION) {
			
			blockEntity(cctx);
		}
	}
	
	public void blockDefaultEntity(IClientContext cctx) throws ISPACException {
		
		if (!isResponsible())
		    return;
		
		// Comprobar si el trámite está bloqueado por la sesión actual
		// para bloquear la entidad que se ha establecido por defecto en el Scheme
		if (taskLockStatus == LockManager.LOCKED_CURSESSION) {
						
			// Bloquear la entidad en el expediente para el estado actual o entidad ya bloqueada?
			blockEntity(cctx);
		}
	}
	
	public boolean isLockByCurrentSession() {
		
		return (taskLockStatus == LockManager.LOCKED_CURSESSION);
	}
	
}