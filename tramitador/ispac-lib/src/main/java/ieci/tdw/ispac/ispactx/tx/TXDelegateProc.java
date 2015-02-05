package ieci.tdw.ispac.ispactx.tx;

import java.util.Map;

import ieci.tdw.ispac.api.IBPMAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.api.rule.RuleProperties;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;


/**
 * Acción para delegar un proceso.
 */
public class TXDelegateProc implements ITXAction {
	
	/** 
	 * Identificador del proceso instanciado. 
	 */
	private final int mnIdProc;
	
	/** 
	 * Identificador del responsable. 
	 */
	private final String mIdResp;
	
	/**
	 * Nombre del responsable.
	 */
	private String mNameResp;
	
	/**
	 * Parámetros para el contexto de las reglas.
	 */
	private final Map mparams;

	
	/**
	 * Constructor.
	 * @param nIdProc Identificador del proceso instanciado.
	 * @param IdResp Identificador del responsable.
	 */
	public TXDelegateProc(int nIdProc,String IdResp) {
		this(nIdProc, IdResp, null,null);
	}
	/**
	 * Constructor.
	 * @param nIdProc Identificador del proceso instanciado.
	 * @param IdResp Identificador del responsable.
	 * @param nameResp Nombre del responsable
	 */
	public TXDelegateProc(int nIdProc,String IdResp, String nameResp) {
		this(nIdProc, IdResp,nameResp, null);
	}

	/**
	 * Constructor.
	 * @param nIdProc Identificador del proceso instanciado.
	 * @param IdResp Identificador del responsable.
	 * @param nameResp Nombre del responsable 
	 * @param params Parámetros para el contexto de las reglas.
	 */
	public TXDelegateProc(int nIdProc,String IdResp,String nameResp, Map params) {
		mnIdProc=nIdProc;
		mIdResp=IdResp;
		mparams=params;
		mNameResp=nameResp;
	}
	/**
	 * Constructor.
	 * @param nIdProc Identificador del proceso instanciado.
	 * @param IdResp Identificador del responsable.
	 */
	public TXDelegateProc(int nIdProc,String IdResp, Map params) {
		this(nIdProc, IdResp,null, params);
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
		
			// Información del proceso
			TXProcesoDAO process = dtc.getProcess(mnIdProc);
			String bpmProcessId = process.getString("ID_PROCESO_BPM");

			// Obtener el API de BPM
			IBPMAPI bpmAPI = dtc.getBPMAPI();

	
			// Delegar el proceso en el BPM
			bpmAPI.delegateProcess(bpmProcessId, mIdResp);
			
		    // Actualizar el responsable del proceso
			process.set("ID_RESP", mIdResp);
			
			if(StringUtils.isBlank(mNameResp)){
				IResponsible resp = cs.getAPI().getRespManagerAPI().getResp(mIdResp);
				mNameResp=resp.getName();
			}
			
			process.set("RESP", mNameResp);
	
			// Se añade una descripción al hito (en este caso a quién se delega)
			String desc = new StringBuffer("Delegado a '")
				.append(mNameResp)
				.append("' UID[").append(mIdResp).append("]")
				.toString();
	
			// Se construye el contexto de ejecución de scripts.
			EventManager eventmgr=new EventManager(cs, mparams);
			eventmgr.getRuleContextBuilder().addContext(process);
			eventmgr.getRuleContextBuilder().addContext( RuleProperties.RCTX_RESPDELEGATEID,mIdResp);
			eventmgr.getRuleContextBuilder().addContext( RuleProperties.RCTX_RESPDELEGATENAME,mNameResp);
			eventmgr.getRuleContextBuilder().setItem(process);
	
			// Ejecutar evento
			eventmgr.processSystemEvents(EventsDefines.EVENT_EXEC_DELEGATE);
	
			// Ejecutar evento al delegar procedimiento / subproceso
			int eventObjectType = EventsDefines.EVENT_OBJ_PROCEDURE;
			if (process.isSubProcess())
				eventObjectType = EventsDefines.EVENT_OBJ_SUBPROCEDURE;
			
			eventmgr.processEvents(eventObjectType, 
								   process.getInt("ID_PCD"), 
								   EventsDefines.EVENT_EXEC_DELEGATE);
			
			// Marcar hito en expediente / subproceso
			int milestoneType = TXConstants.MILESTONE_EXPED_DELEGATED;
			if (process.isSubProcess())
				milestoneType = TXConstants.MILESTONE_SUBPROCESS_DELEGATED;
			
			dtc.newMilestone(process.getKeyInt(), 
							 0, 
							 0, 
							 milestoneType, 
							 desc);
	}

	/**
	 * Bloquea el objeto de la acción.
	 * @param cs Contexto de cliente.
	 * @param dtc Contenedor de los datos de la transacción.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void lock(ClientContext cs, TXTransactionDataContainer dtc)
			throws ISPACException {
		dtc.getLockManager().lockProcess(mnIdProc);
	}

	/**
	 * Obtiene el resultado de la acción.
	 * @param nameResult Nombre del resultado.
	 * @return Resultado de la acción.
	 */
	public Object getResult(String nameResult) {
		return null;
	}
}
