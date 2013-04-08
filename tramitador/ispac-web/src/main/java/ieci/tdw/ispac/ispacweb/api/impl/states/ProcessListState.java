package ieci.tdw.ispac.ispacweb.api.impl.states;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.Map;


public class ProcessListState extends BaseState {

	private static final String FIELD_IDPCD = "ID_PCD";

	public ProcessListState(String stateticket) throws ISPACException {
		super(stateticket);
	} 
	

	public ProcessListState (StateContext stateContext) {
		super(stateContext);
	}
	public ProcessListState (int state, Map params,
			IClientContext cct) throws ISPACException {
		String[] stagePcdIdstr = (String[])params.get(ManagerState.PARAM_STAGEPCDID);
		int stagePcdId = 0;
		try
        {
            if (stagePcdIdstr != null)
            	stagePcdId = Integer.parseInt(stagePcdIdstr[stagePcdIdstr.length-1]);
        } catch (NumberFormatException e)
        {
            throw new ISPACException("ProcessListState: Parámetros incorrectos.",e);
        }
		getStateContext(state, stagePcdId, cct);
	}

	/**
	 * Obtiene el stateContext correcto
	 * @param state identificador del estado
	 * @param stagePcdId identificador de la fase en el procedimiento
	 * @param cct ClientContext
	 * @throws ISPACException
	 */
	private void getStateContext(int state,
			int stagePcdId, IClientContext cct) throws ISPACException {
		IItem item = cct.getAPI().getProcedureStage(stagePcdId);
		int pcdId = item.getInt(FIELD_IDPCD);
		mStateContext = new StateContext();
		mStateContext.setState(state);
		mStateContext.setPcdId(pcdId);
		mStateContext.setStagePcdId(stagePcdId);
	}

	public void exit() {
	}

	public void refresh() {
	}

	public void enter() {
	}

	public String getLabel() {
		return ManagerState.LABEL_PROCESSLIST;
	}

	public String getQueryString() {
		return "?" + ManagerState.PARAM_STAGEPCDID + "=" + getStagePcdId();
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
