package ieci.tdw.ispac.ispacweb.api.impl.states;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

public class CreateProcessState extends BaseState {
	
	
	public CreateProcessState(int state) {
		super(state);
	}
	
	public CreateProcessState(String stateticket) throws ISPACException {
		super(stateticket);
	}
	
	public CreateProcessState(StateContext stateContext) {
		super(stateContext);
	}
	
	public void exit() {
		// TODO Auto-generated method stub
	}
	
	public void refresh() {
		// TODO Auto-generated method stub
	}
	
	public void enter() {
		// TODO Auto-generated method stub
	}
	
	public String getLabel() {
		// TODO Auto-generated method stub
		return ManagerState.LABEL_CREATEPROCESS;
	}
	
	public String getQueryString() {
		// TODO Auto-generated method stub
		return null;
	}
}
