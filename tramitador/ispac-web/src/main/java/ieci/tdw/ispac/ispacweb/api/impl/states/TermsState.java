package ieci.tdw.ispac.ispacweb.api.impl.states;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

public class TermsState extends BaseState {

	public TermsState(int state) {
		super(state);
	}

	public TermsState(String stateticket) throws ISPACException {
		super(stateticket);
	}

	public TermsState(StateContext stateContext){
		super(stateContext);
	}

	public void exit() {
	}

	public void refresh() {
	}

	public void enter() {
	}

	public String getLabel() {
		return ManagerState.LABEL_TERMS;
	}

	public String getQueryString() {
		return null;
	}
}
