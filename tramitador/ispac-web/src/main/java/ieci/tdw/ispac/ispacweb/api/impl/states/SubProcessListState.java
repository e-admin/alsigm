package ieci.tdw.ispac.ispacweb.api.impl.states;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.Map;

public class SubProcessListState extends TasksListState {
	private static final String FIELD_IDPCD = "ID_PCD";

	public SubProcessListState(String stateticket) throws ISPACException {
		super(stateticket);
	} 
	

	public SubProcessListState (StateContext stateContext) {
		super(stateContext);
	}
	public SubProcessListState (int state, Map params,
			IClientContext cct) throws ISPACException {
		
		super(state, params, cct);
		String[] activityPcdIdstr = (String[])params.get(ManagerState.PARAM_ACTIVITYPCDID);
		String[] pcdIdstr = (String[])params.get(ManagerState.PARAM_PCDID);
		int activityPcdId = 0;
		int pcdId = 0;
		try
        {
            if (activityPcdIdstr != null)
            	activityPcdId = Integer.parseInt(activityPcdIdstr[activityPcdIdstr.length-1]);
            if (pcdIdstr != null)
            	pcdId = Integer.parseInt(pcdIdstr[pcdIdstr.length-1]);
        } catch (NumberFormatException e)
        {
            throw new ISPACException("SubProcessListState: Parámetros incorrectos.",e);
        }
		getStateContext(state, activityPcdId, pcdId, cct);
	}

	/**
	 * Obtiene el stateContext correcto
	 * @param state identificador del estado
	 * @param activityPcdId identificador de la fase en el procedimiento
	 * @param pcdId Identificador del procedimiento padre.
	 * @param cct ClientContext
	 * @throws ISPACException
	 */
	private void getStateContext(int state,
			int activityPcdId, int pcdId, IClientContext cct) throws ISPACException {
		IItem item = cct.getAPI().getProcedureStage(activityPcdId);
		int subpcdId = item.getInt(FIELD_IDPCD);
		if (mStateContext == null)
			mStateContext = new StateContext();
		mStateContext.setState(state);
		mStateContext.setPcdId(pcdId);
		mStateContext.setSubPcdId(subpcdId);
		mStateContext.setActivityPcdId(activityPcdId);
	}

	public void exit() {
	}

	public void refresh() {
	}

	public void enter() {
	}

	public String getLabel() {
		return ManagerState.LABEL_SUBPROCESSLIST;
	}

	public String getQueryString() {
		return "?" + ManagerState.PARAM_ACTIVITYPCDID + "=" + getActivityPcdId() + "&" +  ManagerState.PARAM_TASKCTLID + "=" +getTaskCtlId();
	}

	public boolean equals(IState iState) {
		boolean result = super.equals(iState);
		if (result) {
			if (getStagePcdId() != iState.getStagePcdId())
				result = false;
		}
		return result;
	}

}
