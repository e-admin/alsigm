package ieci.tdw.ispac.ispacweb.api.impl.states;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.api.item.IStage;
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


public class ExpedientState extends BaseState {

	private static final String FIELD_IDRESP 	= "ID_RESP";
	private static final String FIELD_IDPCD 	= "ID_PCD";
	private static final String FIELD_IDFASE 	= "ID_FASE";
	private static final String FIELD_IDEXP 	= "ID_EXP";
	private static final String FIELD_NUMEXP 	= "NUMEXP";
	
	private int stageLockStatus = LockManager.NOT_LOCKED;

	public ExpedientState(String stateticket) throws ISPACException {
		super(stateticket);
	}

	public ExpedientState(StateContext stateContext) {
		super(stateContext);
	}

	public ExpedientState (int state, Map params,
			IClientContext cct) throws ISPACException {
		String[] stageIdstr = (String[])params.get(ManagerState.PARAM_STAGEID);
		String[] entitystr = (String[])params.get(ManagerState.PARAM_ENTITYID);
		String[] keystr = (String[])params.get(ManagerState.PARAM_ENTREGID);
		int stageId = 0;
		int entity = 0;
		int key = 0;

		try
        {
            if (stageIdstr != null)
            	stageId = Integer.parseInt(stageIdstr[stageIdstr.length-1]);
            if (entitystr != null)
            	entity = Integer.parseInt(entitystr[entitystr.length-1]);
            if (keystr != null)
            	key = Integer.parseInt(keystr[keystr.length-1]);
        } catch (NumberFormatException e)
        {
            throw new ISPACException("ExpedientState: Parámetros incorrectos. ",e);
        }

		getStateContext(state, stageId, entity, key,cct);
	}

	/**
	 * Obtiene el stateContext correcto
	 * @param state     identificador del estado
	 * @param stageId   identificador de la fase en el proceso
	 * @param entity    identificador de entidad
	 * @param key	    identificador de la entidad
	 * @param cct	    ClientContext
	 * @throws ISPACException
	 */
	private void getStateContext(int state, 
								 int stageId,
								 int entity, 
								 int key, 
								 IClientContext cct)	throws ISPACException {
		
	
		IStage stage = cct.getAPI().getStage(stageId);

		/*
	 	String sUID = stage.getString(FIELD_IDRESP);
		boolean isResponsible = cct.getAPI().getWorkListAPI().isInResponsibleList( sUID);
		int pcdId = stage.getInt(FIELD_IDPCD);
		int stagePcdId = stage.getInt(FIELD_IDFASE);
		int processId = stage.getInt(FIELD_IDEXP);
		String numexp = stage.getString(FIELD_NUMEXP);
		*/

		mStateContext = new StateContext();
		mStateContext.setStageId(stageId);
		mStateContext.setState(state);
		mStateContext.setPcdId(stage.getInt(FIELD_IDPCD));
		mStateContext.setStagePcdId(stage.getInt(FIELD_IDFASE));
		mStateContext.setProcessId(stage.getInt(FIELD_IDEXP));
		mStateContext.setNumexp(stage.getString(FIELD_NUMEXP));
		mStateContext.setEntity(entity);
		mStateContext.setKey(key);

		// Establecer la responsabilidad de la fase
		calcResponsible(cct, stage);

		// Establecer sólo lectura o bloqueo
		calcReadonly(cct);
	}

	protected void calcResponsible(IClientContext cct, IStage stage) throws ISPACException {

		// Responsable de la fase
		String sUID = stage.getString(FIELD_IDRESP);
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
					Map permissions = securityMgr.getPermissions(stage, resp);

					if (permissions.containsKey(new Integer(ISecurityAPI.PERMISSION_TYPE_EDIT))) {
						
						//Hay que comprobar si alguno de los uid's que me otorga el permiso
						//de edición no es en modo seguimiento (supervisor en modo consulta) , porque
						//si todos son en modo seguimiento no soy responsable solo puedo visualizar
						if(securityAPI.existPermissionEditNotFollowSupervision(stage,resp, user)){
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

	public void exit(IClientContext cctx) throws ISPACException
	{
	    LockManager lockmgr = new LockManager(cctx);
	    
	    // Desbloquear la fase?
		int lock = lockmgr.isLock(LockManager.LOCKTYPE_STAGE, getStageId());
		if (lock == LockManager.LOCKED_CURSESSION) {
			lockmgr.unlockStage(getStageId());
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
		int newstageId = getStageId();
		*/

		if (!isResponsible())
		    return;

		/*
		// Fase bloqueada? (Ya se bloquea en calcReadonly)
		int lock = lockmgr.isLock(LockManager.LOCKTYPE_STAGE,newstageId);
		if (lock == LockManager.NOT_LOCKED) {
			
			// Bloquear la fase
			lockmgr.lockStage(newstageId);
			stageLockStatus = LockManager.LOCKED_CURSESSION;
		}
		*/
		
		// Comprobar si la fase ha sido bloqueada por la sesión actual
		// y bloquear la entidad en el expediente o entidad ya bloqueada?
		if (stageLockStatus == LockManager.LOCKED_CURSESSION) {
		
			blockEntity(cctx);
		}
	}

	private void calcReadonly(IClientContext cctx) throws ISPACException
	{
		LockManager lockmgr=new LockManager(cctx);
		int newstageId = getStageId();

		if (!isResponsible())
		{
			if(!mStateContext.getReadonly()) {	
				mStateContext.setReadonly(true);

				DbCnt cnt = cctx.getConnection();
				try {
					SecurityMgr securityMgr = new SecurityMgr(cnt);

					IStage itemStage = cctx.getAPI().getStage(mStateContext.getStageId());
					String supervisedUID = itemStage.getString(FIELD_IDRESP);
					IResponsible user = cctx.getResponsible();

					// Si el usuario no es responsable comprobar si es supervisor o tiene supervision en modo control de seguimiento
					// o simplemente no le corresponde la responsabilidad
					if ((securityMgr.isFunction(user.getUID(), ISecurityAPI.FUNC_MONITORINGSUPERVISOR)) ||
							(securityMgr.isSupervised(user, supervisedUID, ISecurityAPI.SUPERV_FOLLOWEDMODE))) {

						mStateContext.setReadonlyReason(StateContext.READONLYREASON_FOLLOW_SUPERVISION);
					}
					else {
						mStateContext.setReadonlyReason(StateContext.READONLYREASON_NOT_RESPONSABILITY);
						throw new ISPACInfo("exception.expedients.responsability", new Object[]{this.getNumexp()});
					}
				}
				finally	{
					cctx.releaseConnection(cnt);
				}
			}

			return;
		}

		// Bloqueo a nivel de fase
		stageLockStatus = lockmgr.isLock(LockManager.LOCKTYPE_STAGE,newstageId);
		boolean isLock = stageLockStatus == LockManager.LOCKED;
		mStateContext.setReadonly(isLock);
		if (isLock) {
			mStateContext.setReadonlyReason(StateContext.READONLYREASON_LOCK);
		}
		else if (stageLockStatus == LockManager.NOT_LOCKED) {

			// Bloquear la fase
			lockmgr.lockStage(newstageId);
			stageLockStatus = LockManager.LOCKED_CURSESSION;
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
	
	//Devuelve el usuario que tiene bloqueado un expediente
    public String getLockedStageUser(IClientContext ctx,int nIdStage)
    throws ISPACException
    {
        DbCnt cnt = ctx.getConnection();
		try
		{
		    String sqlquery=" WHERE ID = (SELECT ID FROM SPAC_S_BLOQUEOS WHERE TP_OBJ=" + LockManager.LOCKTYPE_STAGE + " AND ID_OBJ=" + nIdStage + ")";
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

	public Map getParameters()
	{
		Map param = new HashMap();
		param.put(ManagerState.PARAM_STAGEID, String.valueOf(getStageId()));
		return param;
	}

	public String getLabel() {
		return ManagerState.LABEL_EXPEDIENT;
	}

	public String getQueryString() {
		String params = "";
		if (getEntityId() > 0)
			params = params + "&" + ManagerState.PARAM_ENTITYID + "=" + getEntityId();
		if (getEntityRegId() != 0)
			params = params + "&" + ManagerState.PARAM_ENTREGID + "=" + getEntityRegId();
		
		return "?" + ManagerState.PARAM_STAGEID + "=" + getStageId() + params;
	}

	public boolean equals(IState iState) {
		boolean result = super.equals(iState);
		if (result) {
			if (getStageId() != iState.getStageId())
				return false;
			if (getStagePcdId() != iState.getStagePcdId())
				return false;
			if (getPcdId() != iState.getPcdId())
				return false;
		}
		return result;
	}
	
	public void checkNewEntity(IState iState, IClientContext cctx) throws ISPACException {
		
		if (!isResponsible())
		    return;
		
		// Comprobar si la entidad seleccionada ha cambiado
		// cuando la fase está bloqueada por la sesión actual
		if ((getEntityId() != iState.getEntityId()) &&
			(stageLockStatus == LockManager.LOCKED_CURSESSION)) {
						
			// Desbloquear la entidad en el expediente del estado anterior?
			iState.unblockEntity(cctx);
		}
		
		// Si la entidad no ha cambiado (refresco de la página o activación de la misma pestaña) 
		// cuando la fase está bloqueada por la sesión actual
		// bloquear la entidad en el expediente para el estado actual 
		// o entidad ya bloqueada?
		if (stageLockStatus == LockManager.LOCKED_CURSESSION) {
			
			blockEntity(cctx);
		}
	}
	
	public void blockDefaultEntity(IClientContext cctx) throws ISPACException {
		
		if (!isResponsible())
		    return;
		
		// Comprobar si la fase está bloqueada por la sesión actual
		// para bloquear la entidad que se ha establecido por defecto en el Scheme
		if (stageLockStatus == LockManager.LOCKED_CURSESSION) {
						
			// Bloquear la entidad en el expediente para el estado actual o entidad ya bloqueada?
			blockEntity(cctx);
		}
	}
	
	public boolean isLockByCurrentSession() {
		
		return (stageLockStatus == LockManager.LOCKED_CURSESSION);
	}
		
}