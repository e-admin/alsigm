package ieci.tdw.ispac.ispacweb.api.impl.states;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.Map;


public class DataState extends BaseState {

	private static final String FIELD_ID = 	  "ID";
	private static final String FIELD_IDPCD = "ID_PCD";
  	
	public DataState (String stateticket) throws ISPACException {
		super(stateticket);
	}

	public DataState (StateContext stateContext){
		super(stateContext);
	}

	public DataState (int state, Map params,
			IClientContext cct) throws ISPACException {
		String[] numexp = (String[])params.get(ManagerState.PARAM_NUMEXP);
		String[] entitystr = (String[])params.get(ManagerState.PARAM_ENTITYID);
		String[] keystr = (String[])params.get(ManagerState.PARAM_ENTREGID);
		int entity = 0;
		int key = 0;

		try
        {
            if (entitystr != null)
            	entity = Integer.parseInt(entitystr[entitystr.length-1]);
            if (keystr != null)
            	key = Integer.parseInt(keystr[keystr.length-1]);
        } catch (NumberFormatException e)
        {
            throw new ISPACException("DataState: Parámetros incorrectos. ",e);
        }

		getStateContext(state, numexp[numexp.length-1], entity, key, cct);
	}

	/**
	 * Obtiene el StateContext correcto
	 * @param state  identificador del estado
	 * @param numexp número de expediente
	 * @param entity identificador de entidad
	 * @param key    identificador de la entidad
	 * @param cct    ClientContext
	 * @throws ISPACException
	 */
	public void getStateContext(int state, String numexp, int entity,
			int key, IClientContext cct) throws ISPACException {
		IProcess process = cct.getAPI().getProcess(numexp);
		int processId = process.getInt(FIELD_ID);
		int pcdId = process.getInt(FIELD_IDPCD);
		int estado=process.getInt("ESTADO");
		mStateContext = new StateContext();
		mStateContext.setState(state);
		mStateContext.setNumexp(numexp);
		mStateContext.setProcessId(processId);
		mStateContext.setPcdId(pcdId);
		mStateContext.setEntity(entity);
		mStateContext.setKey(key);
		mStateContext.setReadonly(true);
		switch(estado){
		
		case IProcess.DELETED:mStateContext.setReadonlyReason(StateContext.READONLYREASON_DELETED_EXPEDIENT);break;
		case IProcess.ARCHIVED:mStateContext.setReadonlyReason(StateContext.READONLYREASON_ARCHIVED_EXPEDIENT);break;
		case IProcess.CLOSED: mStateContext.setReadonlyReason(StateContext.READONLYREASON_FINISHED_EXPEDIENT);break;
		}
		
	}

	public void exit() {
	}

	public void refresh() {
	}

	public void enter() {
	}

	public String getLabel() {
		return ManagerState.LABEL_DATA;
	}

	public String getQueryString() {
		String params = "";
		if (getEntityId() > 0)
			params = params + "&" + ManagerState.PARAM_ENTITYID + "=" + getEntityId();
		if (getEntityRegId() != 0)
			params = params + "&" + ManagerState.PARAM_ENTREGID + "=" + getEntityRegId();

		return "?" + ManagerState.PARAM_NUMEXP + "=" + getNumexp() + params;
	}
}
