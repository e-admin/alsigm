package ieci.tdw.ispac.ispacweb.api.impl.states;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

public class IntrayState extends BaseState {

	public IntrayState(int state) {
		super(state);
	}

	public IntrayState(String stateticket) throws ISPACException {
		super(stateticket);
	}

	public IntrayState(StateContext stateContext){
		super(stateContext);
	}

	public void exit() {
	}

	public void refresh() {
	}

	public void enter() {
	}

	public String getLabel() {
		return ManagerState.LABEL_TRAY;
	}

	public String getQueryString() {
		return null;
	}
}
