package ieci.tdw.ispac.ispacweb.api.impl.states;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.lock.LockManager;

import java.util.HashMap;
import java.util.Map;

public class BaseState implements IState {

	protected StateContext mStateContext;

	public BaseState() {
		this.mStateContext = new StateContext();
	}
 
	public BaseState(String stateticket) throws ISPACException {
		this.mStateContext = new StateContext(stateticket);
	}

	public BaseState(StateContext stateContext) {
		this.mStateContext = stateContext;
	}

	public BaseState(int state) {
		mStateContext = new StateContext();
		mStateContext.setState(state);
	}

	public int getPcdId() {
		return mStateContext.getPcdId();
	}

	public int getStagePcdId() {
		return mStateContext.getStagePcdId();
	}

	public int getStageId() {
		return mStateContext.getStageId();
	}

	public int getTaskCtlId() {
		return mStateContext.getTaskCtlId();
	}

	public int getTaskPcdId() {
		return mStateContext.getTaskPcdId();
	}

	public int getTaskId() {
		return mStateContext.getTaskId();
	}
	public int getProcessId() {
		return mStateContext.getProcessId();
	}

	public int getState() {
		return mStateContext.getState();
	}

	public String getNumexp() {
		return mStateContext.getNumexp();
	}

	public int getEntityId() {
		return mStateContext.getEntity();
	}

	public int getEntityRegId() {
		return mStateContext.getKey();
	}

	public boolean isResponsible() {
	    return mStateContext.getResponsible();
	}

	public void setEntityId(int entityId) {
		mStateContext.setEntity(entityId);
	}

	public void setEntityRegId(int entityRegId) {
		mStateContext.setKey(entityRegId);
	}

	public void exit(IClientContext cctx) throws ISPACException {
	}

	public void refresh() {
	}

	public void enter(IClientContext cctx) throws ISPACException {
	}
	
	public Map getParameters()
	{
		Map param = new HashMap();
		return param;
	}
	
	public String getLabel() {

		return null;
	}

	public String getQueryString() {
		return null;
	}

	public boolean equals(IState iState) {
		boolean result = false;
		int mstate = getState();
		int state = iState.getState();
		if (mstate == state)
			result = true;
		return result;
	}

	public StateContext getStateContext() {
		return mStateContext;
	}

    public String getTicket() throws ISPACException
    {
        return mStateContext.getStateTicket();
    }

	public int getBatchTaskId() {
		return mStateContext.getBatchTaskId();
	}
	
	public boolean getReadonly() {
		return mStateContext.getReadonly();
	}
	public void setReadonly(boolean readonly) {
		mStateContext.setReadonly(readonly);
	}

	public int getReadonlyReason() {
		return mStateContext.getReadonlyReason();
	}	
	public void setReadonlyReason(int readonlyReason) {
		mStateContext.setReadonlyReason(readonlyReason);
	}
	
	public int getActivityId() {
		return mStateContext.getActivityId();
	}

	public int getActivityPcdId() {
		return mStateContext.getActivityPcdId();
	}

	public int getSubPcdId() {
		return mStateContext.getSubPcdId();
	}

	public int getSubProcessId() {
		return mStateContext.getSubProcessId();
	}
	
	public void blockEntity(IClientContext cctx) throws ISPACException {
		
		if ((getEntityId() != 0) &&
			(StringUtils.isNotBlank(getNumexp()))) {
			
			LockManager lockmgr = new LockManager(cctx);
			
			// Entidad bloqueada en el expediente?
			int lock = lockmgr.isLock(LockManager.LOCKTYPE_ENTITY, getEntityId(), getNumexp());
			if (lock == LockManager.NOT_LOCKED) {
				
				// Bloquear la entidad en el expediente
				lockmgr.lockEntity(getEntityId(), getNumexp());
			}
			else {
				boolean isLock = lock == LockManager.LOCKED;
				mStateContext.setReadonly(isLock);
				if (isLock) {
					mStateContext.setReadonlyReason(StateContext.READONLYREASON_LOCK);
				}
			}
		}
	}
	
	public void unblockEntity(IClientContext cctx) throws ISPACException {
		
		if ((getEntityId() != 0) &&
			(StringUtils.isNotBlank(getNumexp()))) {
			
			LockManager lockmgr = new LockManager(cctx);
			
			// Entidad bloqueada en el expediente?
			int lock = lockmgr.isLock(LockManager.LOCKTYPE_ENTITY, getEntityId(), getNumexp());
			if (lock == LockManager.LOCKED_CURSESSION) {
				
				// Desbloquear la entidad
				lockmgr.unlockEntity(getEntityId(), getNumexp());
			}
		}
	}
	
	public void checkNewEntity(IState iState, IClientContext cctx) throws ISPACException {
	}
	
	public void blockDefaultEntity(IClientContext cctx) throws ISPACException {
	}
	
	public boolean isLockByCurrentSession() {
		
		return false;
	}
	
}