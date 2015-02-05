package ieci.tdw.ispac.ispactx.tx;

import ieci.tdw.ispac.api.IBPMAPI;
import ieci.tdw.ispac.api.IRespManagerAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaclib.bpm.BpmUIDs;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispactx.TXDAOGen;
import ieci.tdw.ispac.ispactx.TXNodeActivationManager;
import ieci.tdw.ispac.ispactx.TXProcedure;
import ieci.tdw.ispac.ispactx.TXProcedureMgr;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;
import ieci.tdw.ispac.resp.ResponsibleHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  Crea un nuevo proceso a partir del procedimiento indicado
 *
 */
public class TXCreateProcess implements ITXAction
{
	protected final int mnIdProcedure;
	private final Map mparams;
	protected final String mnumexp;
	private int mType;
	private final String mSubProcessUID;
	private final String mActivityUID;
	private String mProcessRespId;
	private String mStageRespId;
	int mnIdProc;
	int mnIdStage;

	/*
	public TXCreateProcess(int nIdProcedure,String numexp,Map params)
	{
		super();
		mnIdProcedure = nIdProcedure;
		mnumexp=numexp;
		mparams=params;
	}
	*/
	
	public TXCreateProcess(int nIdProcedure,String numexp,Map params, int type)
	{
		super();
		mnIdProcedure = nIdProcedure;
		mnumexp=numexp;
		mparams=params;
		mType = type;
		mSubProcessUID = null;
		mActivityUID = null;
	}

	public TXCreateProcess(int nIdProcedure,String numexp, String subProcessUID, String activityUID, String subProcessRespId, String activityRespId, int type) {
		this(nIdProcedure, numexp, subProcessUID, activityUID, subProcessRespId, activityRespId, type, null);
	}

	public TXCreateProcess(int nIdProcedure,String numexp, String subProcessUID, String activityUID, String subProcessRespId, String activityRespId, int type, Map params) {
		mnIdProcedure = nIdProcedure;
		mnumexp = numexp;
		mType = type;
		mSubProcessUID = subProcessUID;
		mActivityUID = activityUID;
		mProcessRespId = subProcessRespId;
		mStageRespId = activityRespId; 
		mparams=params;
	}

	public void run(ClientContext cs, TXTransactionDataContainer dtc, ITXTransaction itx)
			throws ISPACException
	{
		IRespManagerAPI respAPI = cs.getAPI().getRespManagerAPI();
	    //Se añaden los parámetros suministrados como parámetros por defecto. Así
	    //estarán accesibles desde todas las reglas ejecutadas por la transacción de
	    //creación de expediente.
    	//----
    	//BPM
    	//----		
			EventManager eventmgr = new EventManager(cs,mparams);
			TXDAOGen genDAO = new TXDAOGen(cs, eventmgr);
			
			TXProcedure procedure = TXProcedureMgr.getInstance().getProcedure(cs, mnIdProcedure);

			PFaseDAO pStage = null;
			String processUID = null;
			String stageUID = null;
			
			if (mType == IProcess.PROCESS_TYPE){
				//Calculamos el responsable del proceso
				mProcessRespId = ResponsibleHelper.calculateProcessResp(eventmgr, procedure, cs);
		
				IBPMAPI bpmAPI = dtc.getBPMAPI();
				
				//Obtenemos la fase inicial del procedimineto
				//PFaseDAO pStage = procedure.getInitialStage();
				String pStageUID = bpmAPI.getInitialStage(procedure.getString("ID_PCD_BPM"));
				pStage = procedure.getStageDAO(cs.getConnection(), pStageUID);
				
				//Calculamos el responsable de la fase
				mStageRespId = ResponsibleHelper.calculateStageResp(eventmgr, pStage, mProcessRespId);
				
				//Se invoca al BPM para iniciar el proceso y, por consiguiente, la primera fase del proceso retornandonos el UID del proceso y de la fase instanciada  
				BpmUIDs bpmUIDs = bpmAPI.instanceProcess(procedure.getString("ID_PCD_BPM") , mProcessRespId, mStageRespId);
				processUID = bpmUIDs.getProcessUID();
				stageUID = bpmUIDs.getStageUID();
			}else{
				pStage = procedure.getInitialStage();
				processUID = mSubProcessUID;
				stageUID = mActivityUID;
			}
			
			//Se crea el proceso en el modelo de datos SPAC
			TXProcesoDAO process = dtc.newProcess();
			//Se establece el UID retornado por el BPM para el proceso, en caso de SPACBPM se retornaran a null,
			//por lo que establecemos los UID de BPM con los ids del objeto
			if (processUID == null)
				processUID = ""+process.getKeyInt();
			process.set("ID_PROCESO_BPM", processUID);
		
			String codProcedure = null;
			if (mparams != null) {
				codProcedure = (String) mparams.get("COD_PCD");
			}
			
			//Se inician los datos del proceso
			genDAO.instance(procedure.getProcedureDAO(), process, codProcedure, mnumexp, mType);
			process.set("ID_RESP", mProcessRespId);
			//Obtenemos el nombre del responsable
			IResponsible responsible=respAPI.getResp(mProcessRespId);
			process.set("RESP",responsible.getName());
			//Se crea el registro del expediente
			if (process.isProcess())
				createExpEntity(dtc,process);
			
			int milestoneType = TXConstants.MILESTONE_EXPED_START;
			if (mType == IProcess.SUBPROCESS_TYPE)
				milestoneType = TXConstants.MILESTONE_SUBPROCESS_START;

			dtc.newMilestone(process.getKeyInt(), 0, 0, milestoneType);
			
			//Se instancia la fase inicial
			TXNodeActivationManager nodeActMgr = new TXNodeActivationManager(genDAO, procedure, dtc);
			List list = new ArrayList();
			list.add(pStage.getKeyInteger());
			List  stagesInstanced = nodeActMgr.activateNodes(0, list.iterator(), process, mStageRespId);
		
			//Obtenemos la fase creada para establecerle el UID retornado por el BPM, en caso de SPACBPM se retornaran a null,
			//por lo que establecemos los UID de BPM con los ids del objeto
			if (stagesInstanced.isEmpty())
				throw new ISPACException("exception.expedients.createStages.error", new Object[]{process.getString("NUMEXP")},true);
			TXFaseDAO txStage = (TXFaseDAO)stagesInstanced.get(0);
			if (stageUID == null)
				stageUID = ""+txStage.getKeyInt();
			txStage.set("ID_FASE_BPM", stageUID);
			//txStage.set("ID_RESP", mStageRespId);
			txStage.set("TIPO", mType);
			//Obtenemos el nombre del responsable
			responsible=respAPI.getResp(mProcessRespId);
			txStage.set("RESP",responsible.getName());
			mnIdStage = txStage.getKeyInt();
		
			//Se construye el contexto de ejecución de eventos.
			eventmgr.newContext();
			eventmgr.getRuleContextBuilder().addContext(process);
			eventmgr.getRuleContextBuilder().addContext(txStage);
	
			int eventObjectType = EventsDefines.EVENT_OBJ_PROCEDURE;
			if (mType == IProcess.SUBPROCESS_TYPE)
				eventObjectType = EventsDefines.EVENT_OBJ_SUBPROCEDURE;
			
			//Ejecutar evento de sistema al iniciar proceso.
			eventmgr.processSystemEvents( eventObjectType, EventsDefines.EVENT_EXEC_START);
	
			//Ejecutar evento al iniciar proceso.
			eventmgr.processEvents( eventObjectType, mnIdProcedure, EventsDefines.EVENT_EXEC_START);

			// Ejecutar eventos tras iniciar proceso
			if ( (mparams == null) || (!"true".equals((String) mparams.get("OMITIR_EVENTO_TRAS_INICIAR"))) ) {

				// Ejecutar evento de sistema al iniciar proceso.
				eventmgr.processSystemEvents(eventObjectType, EventsDefines.EVENT_EXEC_START_AFTER);
	
				// Ejecutar evento al iniciar proceso.
				eventmgr.processEvents(eventObjectType, mnIdProcedure, EventsDefines.EVENT_EXEC_START_AFTER);
			}

			mnIdProc = process.getKeyInt();
    	//----		
	}
	/**
     * @param exped
     */
    private void createExpEntity(TXTransactionDataContainer dtc,TXProcesoDAO exped) throws ISPACException
    {
        //Sólo se crea la entidad de cliente si no se ha especificado número de expediente
        if (mnumexp!=null&&mnumexp.length()>0)
            return;

        dtc.createExpedientEntity(exped);
    }

//    private void validateNumExp(TXTransactionDataContainer dtc,TXProcesoDAO exped) throws ISPACException
//    {
//        if (mnumexp==null||mnumexp.length()==0)
//            return;
//
//        if (dtc.testExistNumExp(mnumexp))
//            throw new ISPACException("Error al crear proceso. Ya existe un proceso abierto o cancelado con el mismo número de expediente - "+mnumexp);
//
//        if (!dtc.testExistExpedientEntity(mnumexp))
//            throw new ISPACException("Error al crear proceso. No existe la entidad de expediente con NUMEXP = "+mnumexp);
//    }

    public Object getResult(String nameResult)
	{
    	return  new int[]{mnIdProc, mnIdStage};
	}

	public void lock(ClientContext cs, TXTransactionDataContainer dtc) throws ISPACException
	{
		// El proceso no está creado luego no es necesario bloquearlo.
	}

}