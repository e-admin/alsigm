package ieci.tdw.ispac.ispactx.tx;

import ieci.tdw.ispac.api.IBPMAPI;
import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISignAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.api.rule.RuleProperties;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.sign.SignCircuitInstanceDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXHitoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispaclib.dao.wl.WLBatchTaskExpsDAO;
import ieci.tdw.ispac.ispaclib.notices.Notices;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;

import java.util.Map;

import org.apache.log4j.Logger;

public class TXDeleteProcess  implements ITXAction {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(TXDeleteProcess.class);
	
	/** 
	 * Identificador del proceso instanciado. 
	 */
	protected final int nIdProc;
	
	/**
	 * Parámetros para el contexto de las reglas.
	 */
	protected final Map mparams;
	
	
	public TXDeleteProcess(int nIdProc, Map mparams) {
		this.nIdProc = nIdProc;
		this.mparams = mparams;
	}

	public TXDeleteProcess(int nIdProc) {
		this(nIdProc, null);
	}

	
	public Object getResult(String nameResult) {
		return null;
	}

	public void lock(ClientContext cs, TXTransactionDataContainer dtc)
			throws ISPACException {
		
		dtc.getLockManager().lockProcess(nIdProc);
		if (logger.isDebugEnabled()) {
			logger.debug("Se ha bloquedado el proceso : " + nIdProc);
		}
		
	}

	public void run(ClientContext cs, TXTransactionDataContainer dtc,
			ITXTransaction itx) throws ISPACException {
		
		
		TXProcesoDAO process=dtc.getProcess(nIdProc);
		String numExp= process.getString("NUMEXP");
		int nIdProcedure=process.getInt("ID_PCD"); 
		
		int eventObjectType = EventsDefines.EVENT_OBJ_PROCEDURE;
		// Se construye el contexto de ejecución de scripts.
		EventManager eventmgr=new EventManager(cs, mparams);
		eventmgr.getRuleContextBuilder().addContext(process);
		eventmgr.getRuleContextBuilder().addContext( RuleProperties.RCTX_PROCESS , nIdProc+"");
		eventmgr.getRuleContextBuilder().addContext( RuleProperties.RCTX_NUMEXP , numExp);
		eventmgr.getRuleContextBuilder().setItem(process);

		
		//Ejecutar evento de sistema al enviar a la papelera
		eventmgr.processSystemEvents( eventObjectType, EventsDefines.EVENT_EXEC_BEFORE_DELETE);

		//Ejecutar evento al enviar a la papelera
		eventmgr.processEvents( eventObjectType, nIdProcedure, EventsDefines.EVENT_EXEC_BEFORE_DELETE);
		
		deleteProcess(cs, process, dtc);
		
		// Ejecutar evento tras enviar a la papelera
		eventmgr.processSystemEvents(eventObjectType, EventsDefines.EVENT_EXEC_AFTER_DELETE);

		// Ejecutar evento tras enviar a la papelera
		eventmgr.processEvents(eventObjectType, nIdProcedure, EventsDefines.EVENT_EXEC_AFTER_DELETE);
		
		
		// Marcar hito en expediente
		int milestoneType = TXConstants.MILESTONE_EXPED_DELETED_SYSTEM;
	
		
		TXHitoDAO hito=dtc.newMilestone(nIdProc, 
						 0, 
						 0, 
						 milestoneType, 
						 numExp);
		
		if (logger.isDebugEnabled()) {
			logger.debug("Se ha creado el hito : " + hito.getKeyName()+" con id "+hito.getKeyInt());
		}
		
	} 

	
	protected void deleteProcess(ClientContext cs , TXProcesoDAO process ,TXTransactionDataContainer dtc) throws ISPACException{

		IInvesflowAPI invesFlowAPI = cs.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();
		IEntitiesAPI entitiesAPI=invesFlowAPI.getEntitiesAPI();
		String numExp= process.getString("NUMEXP");
		//Eliminar las relaciones que pudiera tener el expediente con otros.
		//Eliminar tramites y fases ya cerradas para el expediente por numexp
		
		entitiesAPI.deleteEntities("SPAC_EXP_RELACIONADOS", " WHERE NUMEXP_PADRE='"+DBUtil.replaceQuotes(numExp)+"' OR NUMEXP_HIJO='"+DBUtil.replaceQuotes(numExp)+"'");
		
	
		//Eliminar las tramitaciones agrupadas en las que participa el expediente
		WLBatchTaskExpsDAO.deleteExpOfAllBatchTasks(cs.getConnection(), numExp);
		
		
		//Eliminamos los circuitos de firma
		SignCircuitInstanceDAO.deleteAllInstancedSingCircuitOfNumExp(cs.getConnection(), numExp);

		//Eliminamos los avisos electrónicos en los que participa el expediente
		Notices.deleteExpOfAllNotices(cs.getConnection(), numExp);
		
		
				
		process.deleteStages(cs.getConnection());
		process.deleteTasks(cs.getConnection());
		
		
		//Obtenemos los subprocesos creados para el proceso actual
		CollectionDAO col=dtc.getSubProcess(numExp);
		if(col!=null ){
			while(col.next()){
				TXProcesoDAO txproc=(TXProcesoDAO) col.value();
				//Eliminamos las actividades
				txproc.deleteStages(cs.getConnection());
				//Eliminamos el subproceso
				txproc.delete(cs.getConnection());
			}
		}
		
		//Eliminar todos los documentos
		entitiesAPI.deleteAllDocumentsOfNumExp(numExp);
		String condicion="WHERE TIPO = "+ICatalogAPI.ENTITY_CT_ENTITY;
		IItemCollection itemcol = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_ENTITY, condicion.toString());
		while(itemcol.next()){
			IItem value= itemcol.value();
			String nombre=value.getString("NOMBRE");
			String campo_numexp=value.getString("CAMPO_NUMEXP");
			if(StringUtils.isNotBlank(nombre) && StringUtils.isNotBlank(campo_numexp)){
				entitiesAPI.deleteEntities(nombre, "WHERE "+campo_numexp+"='"+DBUtil.replaceQuotes(numExp)+"'");
			}
		}
		
	
		// Eliminar el proceso en el BPM
		String bpmProcessId = process.getString("ID_PROCESO_BPM");
		if (StringUtils.isNotBlank(bpmProcessId)) {
				// Obtener el API de BPM
				IBPMAPI bpmAPI = dtc.getBPMAPI();
				// Cerrar el proceso en el BPM
				bpmAPI.deleteProcess(bpmProcessId);
			}

		process.delete(cs);

	}
}
