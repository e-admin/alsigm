package ieci.tdw.ispac.ispacweb.api.impl.states;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

public class SearchResultsState extends BaseState {

	public SearchResultsState(int state) {
		super(state);
	}

	public SearchResultsState(String stateticket) throws ISPACException {
		super(stateticket);
	}

	public SearchResultsState(StateContext stateContext) {
		super(stateContext);
	}

	public void exit() {
	}

	public void refresh() {
	}

	public void enter() {
	}

	public String getLabel() {
		return ManagerState.LABEL_SEARCHRESULTS;
	}

	public String getQueryString() {
		return ManagerState.PARAM_QUERYSTRING;
	}
	
}
