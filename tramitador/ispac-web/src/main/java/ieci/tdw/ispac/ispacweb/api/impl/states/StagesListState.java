package ieci.tdw.ispac.ispacweb.api.impl.states;

import java.util.Map;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

public class StagesListState extends BaseState {

	public StagesListState(String stateticket) throws ISPACException {
		super(stateticket);
	}

	public StagesListState(StateContext stateContext) {
		super(stateContext);
	}
	public StagesListState(int state, Map params) throws ISPACException
	{
		String[] pcdIdstr = (String[])params.get(ManagerState.PARAM_PCDID);
		int pcdId = 0;
		try
        {
            if (pcdIdstr != null)
            	pcdId = Integer.parseInt(pcdIdstr[pcdIdstr.length-1]);
        } catch (NumberFormatException e)
        {
            throw new ISPACException("ProcessListState: Parámetros incorrectos. ",e);
        }
		getStateContext(state, pcdId);
	}

	/**
	 * Obtiene el stateContext correcto
	 * @param state identificador del estado
	 * @param pcdId identificador del procedimiento
	 */
	private void getStateContext(int state, int pcdId) {
		mStateContext = new StateContext();
		mStateContext.setState(state);
		mStateContext.setPcdId(pcdId);
	}

	public void exit() {
	}

	public void refresh() {
	}

	public void enter() {
	}

	public String getLabel() {
		return ManagerState.LABEL_STAGESLIST;
	}

	public String getQueryString() {
		return "?" + ManagerState.PARAM_PCDID + "=" + getPcdId();
	}
}
