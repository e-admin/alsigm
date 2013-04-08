package ieci.tdw.ispac.ispacweb.api.impl.states;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

public class ExpsTrashListState extends BaseState {

	public ExpsTrashListState(int state) {
		super(state);
	}

	public ExpsTrashListState(String stateticket) throws ISPACException {
		super(stateticket);
	}

	public ExpsTrashListState(StateContext stateContext){
		super(stateContext);
	}

	public void exit() {
	}

	public void refresh() {
	}

	public void enter() {
	}

	public String getLabel() {
		return ManagerState.LABEL_EXPSTRASHLIST;
	}

	public String getQueryString() {
		return null;
	}

}
