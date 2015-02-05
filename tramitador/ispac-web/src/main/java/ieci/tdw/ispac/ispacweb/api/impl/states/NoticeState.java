package ieci.tdw.ispac.ispacweb.api.impl.states;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

public class NoticeState extends BaseState {
	public NoticeState(int state) {
		super(state);
	}

	public NoticeState(String stateticket) throws ISPACException {
		super(stateticket);
	}

	public NoticeState(StateContext stateContext){
		super(stateContext);
	}

	public void exit() {
	}

	public void refresh() {
	}

	public void enter() {
	}

	public String getLabel() {
		return ManagerState.LABEL_NOTICE;
	}

	public String getQueryString() {
		return null;
	}
}
