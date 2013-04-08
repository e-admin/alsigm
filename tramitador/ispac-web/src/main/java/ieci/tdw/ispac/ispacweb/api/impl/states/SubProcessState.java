package ieci.tdw.ispac.ispacweb.api.impl.states;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.api.item.ITask;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispaclib.dao.session.SessionDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.security.SecurityMgr;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerState;
import ieci.tdw.ispac.lock.LockManager;

import java.util.HashMap;
import java.util.Map;

public class SubProcessState extends TaskState {
	
	protected static final String FIELD_IDSUBPROCESO	= "ID_SUBPROCESO";
	protected static final String FIELD_IDACTIVITYPCD 	= "ID_FASE";
	
	private int activityLockStatus = LockManager.NOT_LOCKED;

	public SubProcessState(){
	}
	
	public SubProcessState(String stateticket) throws ISPACException {
		super(stateticket);
	}

	public SubProcessState(StateContext stateContext){
		super(stateContext);
	}

	public SubProcessState(int state, Map params, IClientContext cct) throws ISPACException {
		
		String[] taskIdstr = (String[])params.get(ManagerState.PARAM_TASKID);
		String[] activityIdstr = (String[])params.get(ManagerState.PARAM_ACTIVITYID);
		String[] entitystr = (String[])params.get(ManagerState.PARAM_ENTITYID);
		String[] keystr = (String[])params.get(ManagerState.PARAM_ENTREGID);

		int taskId = 0;
		int activityId = 0;
		int entity = 0;
		int key = 0;

		try
        {
            if (taskIdstr != null)
            	taskId = Integer.parseInt(taskIdstr[taskIdstr.length-1]);
            if (activityIdstr != null)
            	activityId = Integer.parseInt(activityIdstr[activityIdstr.length-1]);
            if (entitystr != null)
            	entity = Integer.parseInt(entitystr[entitystr.length-1]);
            if (keystr != null)
            	key = Integer.parseInt(keystr[keystr.length-1]);
        } catch (NumberFormatException e)
        {
            throw new ISPACException("SubProcessState: Parámetros incorrectos. ",e);
        }

		getStateContext(state, taskId, activityId, entity, key,  cct);
	}

	/**
	 * Obtiene el stateContext correcto. 
	 *
	 * @param state   		identificador del estado
	 * @param taskId 		identificador del trámite en el proceso
	 * @param activityId	identificador de la actividad
	 * @param entity  		identificador de entidad
	 * @param key	  		identificador de la entidad
	 * @param cct	  		ClientContext
	 * @throws ISPACException
	 */
	protected void getStateContext(int state,
								   int taskId,
								   int activityId,
								   int entity, 
								   int key, 
								   IClientContext cct) throws ISPACException {
		
		int subProcessId = 0;
		int subPcdId = 0;
		boolean isResponsible = false;

		mStateContext = new StateContext();
		
		mStateContext.setState(state);
		mStateContext.setEntity(entity);
		mStateContext.setKey(key);
		
		if (activityId > 0) {
				
			IStage activity = cct.getAPI().getStage(activityId);
			
			String sUID = activity.getString(FIELD_IDRESP);
			isResponsible = cct.getAPI().getWorkListAPI().isInResponsibleList( sUID, activity);
			
			subPcdId = activity.getInt(FIELD_IDPCD);
			subProcessId = activity.getInt(FIELD_IDEXP);
			int activityPcdId = activity.getInt(FIELD_IDACTIVITYPCD);
			
			mStateContext.setResponsible(isResponsible);
			mStateContext.setActivityId(activityId);
			mStateContext.setActivityPcdId(activityPcdId);
		}

		try {
			ITask task = null;
			if (taskId > 0) {
				task = cct.getAPI().getTask(taskId);
				
				if (subProcessId == 0)
					subProcessId = task.getInt(FIELD_IDSUBPROCESO);
				if (subPcdId == 0)
					subPcdId = task.getInt(FIELD_IDPCD);
			}
			else {
				task = cct.getAPI().getTaskBySubProcess(subProcessId);
				taskId = task.getKeyInt();
			}

			if (!isResponsible) {
				// Permiso para la responsabilidad del usuario conectado
				// sobre el trámite del subproceso
				calcResponsible(cct, task);
			}

			/*
			int pcdId = task.getInt(FIELD_IDPCD);
			int taskCtlId = task.getInt(FIELD_IDCTTRAMITE);
			int taskPcdId = task.getInt(FIELD_IDPCTRAMITE);
			int processId = task.getInt(FIELD_IDEXP);
			*/
			String numexp = task.getString(FIELD_NUMEXP);
			int stagePcdId = task.getInt(FIELD_IDFASEPCD);
			int stageId = getStageId(cct, numexp, stagePcdId);

			mStateContext.setPcdId(task.getInt(FIELD_IDPCD));
			mStateContext.setTaskId(taskId);
			mStateContext.setTaskCtlId(task.getInt(FIELD_IDCTTRAMITE));
			mStateContext.setTaskPcdId(task.getInt(FIELD_IDPCTRAMITE));
			mStateContext.setProcessId(task.getInt(FIELD_IDEXP));
			mStateContext.setNumexp(numexp);
			mStateContext.setStageId(stageId);
			mStateContext.setStagePcdId(stagePcdId);
			
			mStateContext.setSubProcessId(subProcessId);
			mStateContext.setSubPcdId(subPcdId);
		}
		// El trámite está cerrado
		catch (ISPACNullObject e) {
			
			IItem task = cct.getAPI().getEntitiesAPI().getTask(taskId);
			if (task == null) {

				// Trámite delegado borrado y no se tiene responsabilidad sobre el expediente
				throw new ISPACNullObject();
			}
			String numexp = task.getString(FIELD_NUMEXP);
			mStateContext.setTaskId(task.getInt(FIELD_IDTRAMEXP));
			mStateContext.setTaskCtlId(task.getInt(FIELD_IDTRAMCTL));
			mStateContext.setTaskPcdId(task.getInt(FIELD_IDTRAMPCD));
		
			mStateContext.setStagePcdId(task.getInt(FIELD_IDFASEPCD));
			//Información del proceso
			IProcess process = cct.getAPI().getProcess(numexp);
			mStateContext.setProcessId(process.getKeyInt());
			mStateContext.setNumexp(numexp);
			mStateContext.setPcdId(process.getInt(FIELD_IDPCD));
			mStateContext.setStageId(getStageId(cct, process, mStateContext.getStagePcdId()));
			
			//Información del subproceso
			subProcessId = task.getInt(FIELD_IDSUBPROCESO);
			mStateContext.setSubProcessId(subProcessId);
			IProcess subProcess = cct.getAPI().getProcess(subProcessId);
			subPcdId = subProcess.getInt(FIELD_IDPCD);
			mStateContext.setSubPcdId(subPcdId);
			mStateContext.setResponsible(false);
			// Trámite cerrado luego sólo consulta
			mStateContext.setReadonly(true);
		}
		finally
		{
			calcReadonly(cct);
		}
	}

	protected void calcResponsible(IClientContext cct, ITask task) throws ISPACException {
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
						mStateContext.setResponsible(true);
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
	/*private int getStageId(IClientContext cct, String numexp, int stagePcdId) throws ISPACException {
		IProcess process = cct.getAPI().getProcess(numexp);
		IStage stage = process.getStage(cct.getConnection(), stagePcdId);
		if (stage == null)
			return 0;
		return stage.getKeyInt();
	}*/

	protected int getStageId(IClientContext cct, String numexp, int stagePcdId) throws ISPACException {
		IProcess process = cct.getAPI().getProcess(numexp);

		DbCnt cnt = cct.getConnection();
		try {
			IStage stage = process.getStage(cnt, stagePcdId);
			if (stage == null)
				return 0;
			return stage.getKeyInt();
		}
		finally	{
			cct.releaseConnection(cnt);
		}
	}
	public void exit(IClientContext cctx) throws ISPACException
	{
	    LockManager lockmgr = new LockManager(cctx);
	    
	    // Desbloquear la actividad?
		int lock = lockmgr.isLock(LockManager.LOCKTYPE_STAGE, getActivityId());
		if (lock == LockManager.LOCKED_CURSESSION) {
			lockmgr.unlockStage(getActivityId());
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
		int newActivityId = getActivityId();
		*/

		if (!isResponsible())
		    return;

		/*
		// Actividad bloqueada? (Ya se bloquea en calcReadonly)
		int lock = lockmgr.isLock(LockManager.LOCKTYPE_STAGE,newActivityId);
		if (lock == LockManager.NOT_LOCKED) {
			
			// Bloquear la actividad
			lockmgr.lockStage(newActivityId);
			activityLockStatus = LockManager.LOCKED_CURSESSION;
		}
		*/
		
		// Comprobar si la actividad ha sido bloqueada por la sesión actual
		// y bloquear la entidad en el expediente o entidad ya bloqueada?
		if (activityLockStatus == LockManager.LOCKED_CURSESSION) {

			blockEntity(cctx);
		}
	}

	private void calcReadonly(IClientContext cctx) throws ISPACException
	{
	    LockManager lockmgr=new LockManager(cctx);
	    int newActivityId = getActivityId();

		if (!isResponsible())
		{
			if(!mStateContext.getReadonly()) {
			mStateContext.setReadonly(true);
			
			try {
				SecurityMgr securityMgr = new SecurityMgr(cctx.getConnection());
				
				IStage itemActivity = cctx.getAPI().getStage(newActivityId);
				String supervisedUID = itemActivity.getString(FIELD_IDRESP);
				IResponsible user = cctx.getResponsible();
				
				// Si el usuario no es responsable comprobar si es supervisor o tiene supervision en modo control de seguimiento 
				// o simplemente no le corresponde la responsabilidad
				if ((securityMgr.isFunction(user.getUID(), ISecurityAPI.FUNC_MONITORINGSUPERVISOR)) || 
					(securityMgr.isSupervised(user, supervisedUID, ISecurityAPI.SUPERV_FOLLOWEDMODE))) {
					
					mStateContext.setReadonlyReason(StateContext.READONLYREASON_FOLLOW_SUPERVISION);
				}
				else {
					mStateContext.setReadonlyReason(StateContext.READONLYREASON_NOT_RESPONSABILITY);
				}
					
			}
			catch(ISPACNullObject e) {
			}
		}
		    return;
		}
		
		// Bloqueo a nivel de actividad
		activityLockStatus = lockmgr.isLock(LockManager.LOCKTYPE_STAGE,newActivityId);
		boolean isLock = activityLockStatus == LockManager.LOCKED;
		mStateContext.setReadonly(isLock);
		if (isLock) {
			mStateContext.setReadonlyReason(StateContext.READONLYREASON_LOCK);
		}
		else if (activityLockStatus == LockManager.NOT_LOCKED) {
			
			// Bloquear la actividad
			lockmgr.lockStage(newActivityId);
			activityLockStatus = LockManager.LOCKED_CURSESSION;
		}
		
		/*
		else if (getEntityId() != 0) {
			
			// Bloqueo a nivel de entidad en el expediente
			lock = lockmgr.isLock(LockManager.LOCKTYPE_ENTITY, getEntityId(), getNumexp());
			isLock = lock == LockManager.LOCKED;
			mStateContext.setReadonly(isLock);
			if (isLock) {
				mStateContext.setReadonlyReason(StateContext.READONLYREASON_LOCK);
			}
		}
		*/
	}

	public Map getParameters()
	{
		Map param = new HashMap();
		param.put(ManagerState.PARAM_TASKID, String.valueOf(getTaskId()));
		param.put(ManagerState.PARAM_ACTIVITYID, String.valueOf(getActivityId()));
		return param;
	}

	public String getLabel() {
		return ManagerState.LABEL_SUBPROCESS;
	}

	public String getQueryString() {
		String params = "";
		if (getActivityId() > 0)
			params = params + "&" + ManagerState.PARAM_ACTIVITYID + "=" + getActivityId();
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
			if (getActivityId() != iState.getActivityId())
				result = false;	
		}
		return result;
	}
	
	// Devuelve el usuario que tiene bloqueada la actividad
    public String getLockedActivityUser(IClientContext ctx,int nIdActivity)
    throws ISPACException
    {
        DbCnt cnt = ctx.getConnection();
		try
		{
		    String sqlquery=" WHERE ID = (SELECT ID FROM SPAC_S_BLOQUEOS WHERE TP_OBJ=" + LockManager.LOCKTYPE_STAGE + " AND ID_OBJ=" + nIdActivity + ")";
		    SessionDAO sessionDAO = new SessionDAO(cnt);
			sessionDAO.load(cnt,sqlquery);

			return sessionDAO.getString("USUARIO");
		}
		catch (ISPACException ie)
		{
			return "";
		}
		finally
		{
		    ctx.releaseConnection(cnt);
		}
    }
    
	public void checkNewEntity(IState iState, IClientContext cctx) throws ISPACException {
		
		if (!isResponsible())
		    return;
		
		// Comprobar si la entidad seleccionada ha cambiado
		// cuando la actividad está bloqueada por la sesión actual
		if ((getEntityId() != iState.getEntityId()) &&
			(activityLockStatus == LockManager.LOCKED_CURSESSION)) {
						
			// Desbloquear la entidad en el expediente del estado anterior?
			iState.unblockEntity(cctx);
		}
		
		// Si la entidad no ha cambiado (refresco de la página o activación de la misma pestaña) 
		// cuando la actividad está bloqueada por la sesión actual
		// bloquear la entidad en el expediente para el estado actual 
		// o entidad ya bloqueada?
		if (activityLockStatus == LockManager.LOCKED_CURSESSION) {
			
			blockEntity(cctx);
		}
	}
	
	public void blockDefaultEntity(IClientContext cctx) throws ISPACException {
		
		if (!isResponsible())
		    return;
		
		// Comprobar si la actividad está bloqueada por la sesión actual
		// para bloquear la entidad que se ha establecido por defecto en el Scheme
		if (activityLockStatus == LockManager.LOCKED_CURSESSION) {
						
			// Bloquear la entidad en el expediente para el estado actual o entidad ya bloqueada?
			blockEntity(cctx);
		}
	}
	
	public boolean isLockByCurrentSession() {
		
		return (activityLockStatus == LockManager.LOCKED_CURSESSION);
	}
	
}