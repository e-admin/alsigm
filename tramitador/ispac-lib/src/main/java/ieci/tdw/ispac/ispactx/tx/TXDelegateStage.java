package ieci.tdw.ispac.ispactx.tx;

import ieci.tdw.ispac.api.IBPMAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.api.rule.RuleProperties;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.tx.TXFaseDAO;
import ieci.tdw.ispac.ispaclib.notices.Notices;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;

import java.util.Map;

/**
 * Acción para delegar una fase.
 */
public class TXDelegateStage implements ITXAction {
	
	/** 
	 * Identificador de la fase instanciada. 
	 */
	private final int mnIdStage;

	/** 
	 * Identificador del responsable. 
	 */
	private final String mIdResp;

	
	/**
	 * Nombre del responsable
	 */
	private String mNameResp;
	
	/**
	 * Parámetros para el contexto de las reglas.
	 */
	private final Map mparams;

	/**
	 * Constructor
	 * @param nIdStage Identificador de la fase instanciada.
	 * @param IdResp Identificador del responsable.
	 * @param nameResp Nombre del responsable.
	 */
	public TXDelegateStage (int nIdStage, String IdResp, String nameResp){
		this (nIdStage,IdResp,nameResp,null);
	}
	/**
	 * Constructor.
	 * @param nIdStage Identificador de la fase instanciada.
	 * @param IdResp Identificador del responsable.
	 */
	public TXDelegateStage(int nIdStage,String IdResp) {
		this(nIdStage, IdResp,null, null);
	}

	/**
	 * Constructor.
	 * @param nIdStage Identificador de la fase instanciada.
	 * @param IdResp Identificador del responsable.
	 * @param params Parámetros para el contexto de las reglas.
	 */
	public TXDelegateStage(int nIdStage,String IdResp, Map params) {
		this(nIdStage, IdResp,null, params);
	}
	
	/**
	 * Constructor.
	 * @param nIdStage Identificador de la fase instanciada.
	 * @param IdResp Identificador del responsable.
	 * @param params Parámetros para el contexto de las reglas.
	 * @param nameResp Nombre del responsable
	 */
	public TXDelegateStage(int nIdStage,String IdResp,String nameResp, Map params) {
		mnIdStage=nIdStage;
		mIdResp=IdResp;
		mNameResp=nameResp;
		mparams=params;
	}


	/**
	 * Ejecuta la acción.
	 * @param cs Contexto de cliente.
	 * @param dtc Contenedor de los datos de la transacción.
	 * @param itx Transacción.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void run(ClientContext cs, TXTransactionDataContainer dtc,
			ITXTransaction itx) throws ISPACException {
		
			// Información de la fase
			TXFaseDAO stage = dtc.getStage(mnIdStage);
			
			//invoqueBPM(dtc, stage, mIdResp);
			String bpmStageId = stage.getString("ID_FASE_BPM");
			// Obtener el API de BPM
			//IBPMAPI bpmAPI = cs.getAPI().getBPMAPI();
			IBPMAPI bpmAPI =dtc.getBPMAPI();
			// Delegar la fase en el BPM
			bpmAPI.delegateStage(bpmStageId, mIdResp);			
			
		    // Actualizar el responsable de la fase
			stage.set("ID_RESP",mIdResp);
	
			if(StringUtils.isBlank(mNameResp)){
				IResponsible resp = cs.getAPI().getRespManagerAPI().getResp(mIdResp);
				mNameResp=resp.getName();
			}
			
			//Actualizamos el nombre del responsable de la fase
			stage.set("RESP", mNameResp);
			
			// Se añade una descripción al hito (en este caso a quién se delega)
			String desc = new StringBuffer("Delegado a '")
				.append(mNameResp)
				.append("' UID[").append(mIdResp).append("]")
				.toString();
	
			// Se construye el contexto de ejecución de scripts.
			EventManager eventmgr=new EventManager(cs, mparams);
			eventmgr.getRuleContextBuilder().addContext(stage);
			eventmgr.getRuleContextBuilder().addContext( RuleProperties.RCTX_RESPDELEGATEID, mIdResp);
			eventmgr.getRuleContextBuilder().addContext( RuleProperties.RCTX_RESPDELEGATENAME, mNameResp);
			eventmgr.getRuleContextBuilder().setItem(stage);
	
			// Ejecutar evento
			eventmgr.processSystemEvents(EventsDefines.EVENT_EXEC_DELEGATE);
			
			// Ejecutar evento al delegar fase / actividad
			int eventObjectType = EventsDefines.EVENT_OBJ_STAGE;
			if (stage.isActivity())
				eventObjectType = EventsDefines.EVENT_OBJ_ACTIVITY;
			
			eventmgr.processEvents(eventObjectType, 
								   stage.getInt("ID_FASE"), 
								   EventsDefines.EVENT_EXEC_DELEGATE);			
			
			// Marcar hito en expediente / subproceso
			int milestoneType = TXConstants.MILESTONE_EXPED_DELEGATED;
			if (stage.isActivity())
				milestoneType = TXConstants.MILESTONE_SUBPROCESS_DELEGATED;
			
			dtc.newMilestone(stage.getInt("ID_EXP"), 
							 stage.getInt("ID_FASE"), 
							 0, 
							 milestoneType, 
							 desc);

			//Se crea un aviso electrónico indicando que la fase/actividad ha sido delegada
			Notices notices = new Notices(cs);
			int tipoAviso = Notices.TIPO_AVISO_FASE_DELEGADA;
			int idTramite = 0;
			String message = "notice.delegateStage";
			if (stage.isActivity()){
				tipoAviso = Notices.TIPO_AVISO_ACTIVIDAD_DELEGADA;
				message = "notice.delegateActivity";
				//Se obtiene el id del tramite para informarlo en el aviso
				IEntitiesAPI entitiesAPI = cs.getAPI().getEntitiesAPI();
				IItemCollection col = entitiesAPI.queryEntities(SpacEntities.SPAC_TRAMITES, "WHERE ID_SUBPROCESO = "+stage.getInt("ID_EXP"));
				if (!col.next()){
					throw new ISPACException("Información del subproceso no encontrada para incluirla en el aviso electrónico de delegación");
				}
				idTramite = col.value().getKeyInt();
			}
			notices.generateDelegateObjectNotice(stage.getInt("ID_EXP"), stage.getKeyInt(), idTramite, cs.getStateContext().getNumexp(), message, mIdResp,tipoAviso);
	}

	/**
	 * Bloquea el objeto de la acción.
	 * @param cs Contexto de cliente.
	 * @param dtc Contenedor de los datos de la transacción.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void lock(ClientContext cs, TXTransactionDataContainer dtc)
			throws ISPACException {
		
		TXFaseDAO stage=dtc.getStage(mnIdStage);
		dtc.getLockManager().lockProcess(stage.getInt("ID_EXP"));
	}

	/**
	 * Obtiene el resultado de la acción.
	 * @param nameResult Nombre del resultado.
	 * @return Resultado de la acción.
	 */
	public Object getResult(String nameResult) {
		return null;
	}
	
//	protected void invoqueBPM(TXTransactionDataContainer dtc, TXFaseDAO stage, String idResp) throws ISPACException {
//		String bpmStageId = stage.getString("ID_FASE_BPM");
//		// Obtener el API de BPM
//		//IBPMAPI bpmAPI = cs.getAPI().getBPMAPI();
//		IBPMAPI bpmAPI =dtc.getBPMAPI();
//		// Delegar la fase en el BPM
//		bpmAPI.delegateStage(bpmStageId, idResp);
//	}
//
//	
//	protected void executeEvents(EventManager eventmgr, int idPcd) throws ISPACException{
//		// Ejecutar evento
//		eventmgr.processSystemEvents(EventsDefines.EVENT_EXEC_DELEGATE);
//		// Ejecutar evento al cancelar fase.
//		eventmgr.processEvents(	EventsDefines.EVENT_OBJ_STAGE,
//		        				idPcd,
//								EventsDefines.EVENT_EXEC_DELEGATE);
//	}
	
}
