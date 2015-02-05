package ieci.tdw.ispac.ispacweb.api.impl.states;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerState;
import ieci.tdw.ispac.lock.LockManager;

import java.util.HashMap;
import java.util.Map;

public class BatchTaskState extends BaseState {
	private static final String FIELD_IDRESP =    "ID_RESP";

	public BatchTaskState(String stateticket) throws ISPACException {
		super(stateticket);
	}

	public BatchTaskState(StateContext stateContext){
		super(stateContext);
	}

	public BatchTaskState (int state, Map params,
			IClientContext cct) throws ISPACException {
		String[] batchTaskIdStr = (String[])params.get(ManagerState.PARAM_BATCHTASKID);

		int batchTaskId = 0;
		int entity = 0;
		int key = 0;

		try
        { 
            if (batchTaskIdStr != null)
            	batchTaskId = Integer.parseInt(batchTaskIdStr[batchTaskIdStr.length-1]);
        } catch (NumberFormatException e)
        {
            throw new ISPACException("ProcessListState: Parámetros incorrectos. ",e);
        }

		getStateContext(state, batchTaskId,  entity, key, cct);
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
	private void getStateContext(int state, int batchTaskId,
			int entity, int key, IClientContext cct)
			throws ISPACException {

		mStateContext = new StateContext();
		//mStateContext.setTaskId(taskId);
		mStateContext.setBatchTaskId(batchTaskId);
		mStateContext.setState(state);
		mStateContext.setEntity(entity);
		mStateContext.setKey(key);
		

		try
		{
			IItem batchTask = cct.getAPI().getBatchTask(batchTaskId);
			String sUID = batchTask.getString(FIELD_IDRESP);
		  	boolean isResponsible = cct.getAPI().getWorkListAPI().isInResponsibleList(sUID , batchTask);

//			int pcdId = task.getInt(FIELD_IDPCD);
//			int taskCtlId = task.getInt(FIELD_IDCTTRAMITE);
//			int taskPcdId = task.getInt(FIELD_IDPCTRAMITE);
//			int processId = task.getInt(FIELD_IDEXP);
//			String numexp = task.getString(FIELD_NUMEXP);
//			int stageId = task.getInt(FIELD_IDFASEEXP);
//			int stagePcdId = task.getInt(FIELD_IDFASEPCD);

			mStateContext.setResponsible(isResponsible);
//			mStateContext.setPcdId(pcdId);
//			mStateContext.setTaskCtlId(taskCtlId);
//			mStateContext.setTaskPcdId(taskPcdId);
//			mStateContext.setProcessId(processId);
//			mStateContext.setNumexp(numexp);
//			mStateContext.setStageId(stageId);
//			mStateContext.setStagePcdId(stagePcdId);
		}
		// El trámite está cerrado
		catch (ISPACNullObject e)
		{
//			IItem task = cct.getAPI().getEntitiesAPI().getTask(taskId);
//			String numexp = task.getString(FIELD_NUMEXP);
//			IProcess process = cct.getAPI().getProcess(numexp);
//
//			mStateContext.setResponsible(false);
//			mStateContext.setPcdId(process.getInt(FIELD_IDPCD));
//			mStateContext.setTaskCtlId(task.getInt("ID_TRAM_CTL"));
//			mStateContext.setTaskPcdId(task.getInt("ID_TRAM_PCD"));
//			mStateContext.setProcessId(process.getInt("ID"));
//			mStateContext.setNumexp(numexp);
//			mStateContext.setStageId(task.getInt(FIELD_IDFASEEXP));
//
//			mStateContext.setStagePcdId(task.getInt(FIELD_IDFASEPCD));
		}

		finally
		{
			calcReadonly(cct);
		}
	}

//	public void exit(IClientContext cctx) throws ISPACException
//	{
//	    LockManager lockmgr=new LockManager(cctx);
//		int lock = lockmgr.isLock(LockManager.LOCKTYPE_TASK,getTaskId());
//		if (lock == LockManager.LOCKED_CURSESSION)
//		{
//			lockmgr.unlockTask(getTaskId());
//		}
//	}
//
//	public void refresh() {
//
//	}
//
//	public void enter(IClientContext cctx) throws ISPACException
//	{
//	    LockManager lockmgr=new LockManager(cctx);
//		int newtaskId = getTaskId();
//
//		if (!isResponsible())
//		    return;
//
//		int lock = lockmgr.isLock(LockManager.LOCKTYPE_TASK,newtaskId);
//		if(lock == LockManager.NOT_LOCKED)
//		{
//			lockmgr.lockTask(newtaskId);
//		}
//	}
//
//
	private void calcReadonly(IClientContext cctx) throws ISPACException
	{
	    LockManager lockmgr=new LockManager(cctx);
	    int newtaskId = getTaskId();

		if (!isResponsible())
		{
			mStateContext.setReadonly(true);
			//mStateContext.setReadonlyReason();
		    return;
		}
		int lock = lockmgr.isLock(LockManager.LOCKTYPE_TASK,newtaskId);
		//mStateContext.setReadOnly(lock == LockManager.LOCKED);
		boolean isLock = lock == LockManager.LOCKED;
		mStateContext.setReadonly(isLock);
		if (isLock)
			mStateContext.setReadonlyReason(StateContext.READONLYREASON_LOCK);
	}


	public Map getParameters()
	{
		Map param = new HashMap();
		param.put(ManagerState.PARAM_BATCHTASKID, String.valueOf(getBatchTaskId()));
		return param;
	}

	public String getLabel() {
		return ManagerState.LABEL_BATCHTASK;
	}

	public String getQueryString() {
		String params = "";
		
		return "?" + ManagerState.PARAM_BATCHTASKID +"=" + getBatchTaskId() + params;
	}

	public boolean equals(IState iState) {
		boolean result = super.equals(iState);
		if (result) {
			if (getBatchTaskId() != iState.getBatchTaskId())
				result = false;
		}
		return result;
	}

}
