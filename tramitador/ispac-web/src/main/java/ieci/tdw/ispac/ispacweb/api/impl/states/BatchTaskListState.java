package ieci.tdw.ispac.ispacweb.api.impl.states;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.HashMap;
import java.util.Map;

public class BatchTaskListState extends BaseState {
	
	public BatchTaskListState(String stateticket) throws ISPACException {
		super(stateticket);
	}

	public BatchTaskListState(StateContext stateContext){
		super(stateContext);
	}

	public BatchTaskListState (int state, Map params, IClientContext cct) throws ISPACException {
		
		getStateContext(state, cct);
	}
	
	/**
	 * Obtiene el stateContext correcto.
	 *
	 * @param state   identificador del estado
	 * @param cct	  ClientContext
	 * @throws ISPACException
	 */
	private void getStateContext(int state, IClientContext cct)
			throws ISPACException {

		mStateContext = new StateContext();
		mStateContext.setState(state);
	}

	public Map getParameters()
	{
		Map param = new HashMap();
		return param;
	}

	public String getLabel() {
		return ManagerState.LABEL_BATCHTASKSLIST;
	}

	public String getQueryString() {
		String params = "";
		
		return params;
	}

	public boolean equals(IState iState) {
		boolean result = super.equals(iState);

		return result;
	}

}