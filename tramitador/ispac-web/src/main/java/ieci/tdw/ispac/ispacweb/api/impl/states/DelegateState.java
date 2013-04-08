package ieci.tdw.ispac.ispacweb.api.impl.states;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.api.item.ITask;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.Map;


public class DelegateState extends BaseState {

	private static final String FIELD_IDPCD = 	  "ID_PCD";
	private static final String FIELD_IDFASE = 	  "ID_FASE";
	private static final String FIELD_IDEXP = 	  "ID_EXP";
	private static final String FIELD_NUMEXP = 	  "NUMEXP";
	private static final String FIELD_IDCTTRAMITE = "ID_CTTRAMITE";
	private static final String FIELD_IDFASEEXP = "ID_FASE_EXP";
	private static final String FIELD_IDFASEPCD = "ID_FASE_PCD";

/*	private static final int TYPE_STAGE = 0;
	private static final int TYPE_TASK = 1;
*/
	public DelegateState (String stateticket) throws ISPACException {
		super(stateticket);
	}

	public DelegateState (StateContext stateContext) {
		super(stateContext);
	}
	public DelegateState (int state, Map params,
			IClientContext cct) throws ISPACException {
		
		String[] stagesId = (String[])params.get(ManagerState.PARAM_IDSSTAGES);
		String[] tasksId = (String[])params.get(ManagerState.PARAM_IDSTASK);
		
		try
        {
			if (stagesId != null)
				getStateContextStage(state, Integer.parseInt(stagesId[0]), cct);
			if (tasksId != null)
				getStateContextTask(state, Integer.parseInt(tasksId[0]), cct);
        } 
		catch (NumberFormatException e)
        {
			getStateContext(state, cct);
        }
	}


	/**
	 * Obtiene el stateContext si tenemos el identificador de la fase
	 * @param state   identificador del estado
	 * @param stageId identificador de la fase en el proceso
	 * @param cct	  ClientContext
	 * @throws ISPACException
	 */
	private void getStateContextStage(int state, int stageId,
			IClientContext cct) throws ISPACException {
		
		IStage stage = cct.getAPI().getStage(stageId);
		int pcdId = stage.getInt(FIELD_IDPCD);
		int stagePcdId = stage.getInt(FIELD_IDFASE);
		int processId = stage.getInt(FIELD_IDEXP);
		String numexp = stage.getString(FIELD_NUMEXP);
		mStateContext = new StateContext();
		mStateContext.setStageId(stageId);
		mStateContext.setState(state);
		mStateContext.setPcdId(pcdId);
		mStateContext.setStagePcdId(stagePcdId);
		mStateContext.setProcessId(processId);
		mStateContext.setNumexp(numexp);
	}

	/**
	 * Obtiene el stateContext si tenemos el identificador del trámite
	 * @param state	  identificador del estado
	 * @param taskId  identificador del trámite en el proceso
	 * @param cct     ClientContext
	 * @throws ISPACException
	 */
	private void getStateContextTask(int state, int taskId,
			IClientContext cct) throws ISPACException {
		
		ITask task = cct.getAPI().getTask(taskId);
		int pcdId = task.getInt(FIELD_IDPCD);
		int taskCtlId = task.getInt(FIELD_IDCTTRAMITE);
		int processId = task.getInt(FIELD_IDEXP);
		String numexp = task.getString(FIELD_NUMEXP);
		int stageId = task.getInt(FIELD_IDFASEEXP);
		int stagePcdId = task.getInt(FIELD_IDFASEPCD);
		mStateContext = new StateContext();
		mStateContext.setTaskId(taskId);
		mStateContext.setState(state);
		mStateContext.setPcdId(pcdId);
		mStateContext.setTaskCtlId(taskCtlId);
		mStateContext.setProcessId(processId);
		mStateContext.setNumexp(numexp);
		mStateContext.setStageId(stageId);
		mStateContext.setStagePcdId(stagePcdId);
	}

	/**
	 * Obtiene el stateContext
	 * @param state identificador del estado
	 * @param cct ClientContext
	 * @throws ISPACException
	 */
	private void getStateContext(int state, IClientContext cct)
	throws ISPACException {
		mStateContext = new StateContext();
		mStateContext.setState(state);
	}

	public void exit() {
	}

	public void refresh() {
	}

	public void enter() {
	}

	public String getLabel() {

		return ManagerState.LABEL_DELEGATE;
	}

	public String getQueryString() 
	{
		String params = "";
		
		if (getStageId() > 0)
		{
			params = "?" + ManagerState.PARAM_IDSSTAGES + "=" + getStageId();
		}
		if (getTaskId() > 0)
		{
			params = "?" + ManagerState.PARAM_IDSTASK + "=" + getTaskId();
		}
		
		return params;
	}
}
