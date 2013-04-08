package ieci.tdw.ispac.ispacweb.api.impl.states;

import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.Map;

public class SignDocumentState extends TaskState {

	protected static final String FIELD_DOC_IDTRAMITE = "ID_TRAMITE";
	
	protected static final String DOCUMENT_FIELD_NUMEXP =    "NUMEXP";
	protected static final String DOCUMENT_FIELD_IDFASE = "ID_FASE";
	protected static final String DOCUMENT_FIELD_IDFASEPCD = "ID_FASE_PCD";
	protected static final String PROCESS_FIELD_IDEXP = "ID";
	protected static final String PROCESS_FIELD_IDPCD = 	  "ID_PCD";
	
	
	
	public SignDocumentState(String stateticket) throws ISPACException {
		super(stateticket);
	}

	public SignDocumentState(StateContext stateContext){
		super(stateContext);
	}
	
	public SignDocumentState(int state, Map params, IClientContext cct) throws ISPACException{
		String[] keystr = (String[])params.get(ManagerState.PARAM_ENTREGID);

		int entity = ISPACEntities.DT_ID_DOCUMENTOS;
		int key = 0;


		try
        {
            if (keystr != null)
            	key = Integer.parseInt(keystr[keystr.length-1]);
        } catch (NumberFormatException e)
        {
            throw new ISPACException("SignDocumentState: Parámetros incorrectos. ",e);
        }
		IItem document = cct.getAPI().getEntitiesAPI().getDocument(key);
		int taskId = document.getInt(FIELD_DOC_IDTRAMITE);
		
		if (taskId != 0)
			super.getStateContext(state, taskId, entity, key,  cct);
		else
			getDocumentState(state, entity, key, cct);
	}

	
	private void getDocumentState(int state, int entity, int key, IClientContext cct) throws ISPACException {
		mStateContext = new StateContext();
		mStateContext.setState(state);
		mStateContext.setEntity(entity);
		mStateContext.setKey(key);

		IItem document = cct.getAPI().getEntitiesAPI().getDocument(key);;
		String numexp = document.getString(DOCUMENT_FIELD_NUMEXP);
		int stagePcdId = document.getInt(DOCUMENT_FIELD_IDFASEPCD);
		int stageId = document.getInt(DOCUMENT_FIELD_IDFASE);
		//IItem itemExp = cct.getAPI().getEntitiesAPI().getExpedient(numexp);
		IProcess process = cct.getAPI().getProcess(numexp);
		int pcdId = process.getInt(PROCESS_FIELD_IDPCD);
		int processId = process.getInt(PROCESS_FIELD_IDEXP);

		mStateContext.setResponsible(false);
		mStateContext.setNumexp(numexp);
		mStateContext.setPcdId(pcdId);
		mStateContext.setProcessId(processId);
		
		mStateContext.setStageId(stageId);
		mStateContext.setStagePcdId(stagePcdId);
	}

	public void exit() {
	}

	public void refresh() {
	}

	public void enter() {
	}

	
	public boolean equals(IState iState) {
		boolean result = false;
		int mstate = getState();
		int state = iState.getState();
		if (mstate == state)
			result = true;
		return result;
	}

	public String getLabel() {
		return ManagerState.LABEL_SIGN;
	}
}
