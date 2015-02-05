package ieci.tdw.ispac.ispacweb.api.impl.states;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

public class ProcedureListState extends BaseState {

	public ProcedureListState(int state) {
		super(state);
	}

	public ProcedureListState(String stateticket) throws ISPACException {
		super(stateticket);
	}

	public ProcedureListState(StateContext stateContext) {
		super(stateContext);
	}

	public void exit() {
	}

	public void refresh() {
	}

	public void enter() {
	}

	public String getLabel() {
		return ManagerState.LABEL_PROCEDURELIST;
	}

	public String getQueryString() {
		return null;
	}
}
