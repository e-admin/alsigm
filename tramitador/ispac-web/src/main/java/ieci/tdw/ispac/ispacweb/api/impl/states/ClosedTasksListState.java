package ieci.tdw.ispac.ispacweb.api.impl.states;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.Map;


public class ClosedTasksListState extends BaseState {

	public ClosedTasksListState(String stateticket) throws ISPACException {
		super(stateticket);
	}

	public ClosedTasksListState (StateContext stateContext) {
		super(stateContext);
	}

	public ClosedTasksListState (int state, Map params,
			IClientContext cct) throws ISPACException {
		String[] taskCtlIdstr = (String[])params.get(ManagerState.PARAM_TASKCTLID);
		String[] taskPcdIdstr = (String[])params.get(ManagerState.PARAM_TASKPCDID);
		String[] pcdIdstr = (String[])params.get(ManagerState.PARAM_PCDID);
		int taskCtlId = 0;
		int taskPcdId = 0;
		int pcdId = 0;
		try
        {
            if (taskCtlIdstr != null)
            	taskCtlId = Integer.parseInt(taskCtlIdstr[taskCtlIdstr.length-1]);
            if (taskPcdIdstr != null)
            	taskPcdId = Integer.parseInt(taskPcdIdstr[taskPcdIdstr.length-1]);
            if (pcdIdstr != null)
            pcdId = Integer.parseInt(pcdIdstr[pcdIdstr.length-1]);
        }
		catch (NumberFormatException e)
        {
            throw new ISPACException("ProcessListState: Parámetros incorrectos. ",e);
        }

		getStateContext(state, taskCtlId, taskPcdId, pcdId, cct);
	}

	/**
	 * Obtiene el stateContext correcto
	 * @param state identificador del estado
    * @param taskCtlId identificador del trámite en el catálogo
    * @param taskPcdId identificador del trámite en el procedimiento
    * @param pcdId identificador del procedimiento
	 * @param cct ClientContext
	 */
	private void getStateContext(int state, int taskCtlId, int taskPcdId, int pcdId, IClientContext cct){
		mStateContext = new StateContext();
		mStateContext.setState(state);
		if (taskPcdId != 0)
			mStateContext.setTaskPcdId(taskPcdId);
		if (taskCtlId != 0)
		    mStateContext.setTaskCtlId(taskCtlId);
		if (pcdId != 0)
			mStateContext.setPcdId(pcdId);
	}

	public void exit() {
	}

	public void refresh() {
	}

	public void enter() {
	}

	public String getLabel() {
		return ManagerState.LABEL_CLOSEDTASKSLIST;
	}

	public String getQueryString() {
		StringBuffer paramPcdId = new StringBuffer();
		if (getPcdId() != 0) {
			paramPcdId.append("&")
					 .append(ManagerState.PARAM_PCDID)
					 .append("=")
					 .append(getPcdId());
		}
	    if (getTaskPcdId() != 0)
           return "?" + ManagerState.PARAM_TASKPCDID + "=" + getTaskPcdId() + paramPcdId.toString();
        return "?" + ManagerState.PARAM_TASKCTLID + "=" + getTaskCtlId() + paramPcdId.toString();
	}

	public boolean equals(IState iState) {
		boolean result = super.equals(iState);
		if (result) {
			if (getTaskCtlId() != iState.getTaskCtlId())
				result = false;
		}
		return result;
	}
}
