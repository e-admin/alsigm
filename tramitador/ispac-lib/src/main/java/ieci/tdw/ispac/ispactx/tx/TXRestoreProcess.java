package ieci.tdw.ispac.ispactx.tx;

import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.api.rule.RuleProperties;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXHitoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;

import java.util.Map;

import org.apache.log4j.Logger;

public class TXRestoreProcess  implements ITXAction {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(TXRestoreProcess.class);
	
	/** 
	 * Identificador del proceso instanciado. 
	 */
	private final int nIdProc;
	
	/**
	 * Parámetros para el contexto de las reglas.
	 */
	private final Map mparams;



	public TXRestoreProcess(int nIdProc, Map mparams) {
		this.nIdProc = nIdProc;
		this.mparams = mparams;
	}

	public TXRestoreProcess(int nIdProc) {
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
		int nIdProcedure=process.getInt("ID_PCD"); 
		String numExp= process.getString("NUMEXP");
		CollectionDAO col= dtc.getSubProcess(numExp);
		
		//Si hay subprocesos los eliminamos , y a sus actividades también
		if(col!=null ){
			while(col.next()){
				TXProcesoDAO trxtproc= new TXProcesoDAO(cs.getConnection());
				trxtproc=(TXProcesoDAO) col.value();
				updateToRestoreProcess(trxtproc , cs.getConnection());
			}
		}
		
		
		
		int eventObjectType = EventsDefines.EVENT_OBJ_PROCEDURE;
		// Se construye el contexto de ejecución de scripts.
		EventManager eventmgr=new EventManager(cs, mparams);
		eventmgr.getRuleContextBuilder().addContext(process);
		eventmgr.getRuleContextBuilder().addContext( RuleProperties.RCTX_PROCESS , nIdProc+"");
		eventmgr.getRuleContextBuilder().addContext( RuleProperties.RCTX_NUMEXP , numExp);
		eventmgr.getRuleContextBuilder().setItem(process);

		//Ejecutar evento de sistema al resturar
		eventmgr.processSystemEvents( eventObjectType, EventsDefines.EVENT_EXEC_BEFORE_RESTORE);

		//Ejecutar evento al enviar al restaurar
		eventmgr.processEvents( eventObjectType, nIdProcedure, EventsDefines.EVENT_EXEC_BEFORE_RESTORE);
		
		updateToRestoreProcess(process, cs.getConnection());
		
		// Ejecutar evento tras restaurar
		eventmgr.processSystemEvents(eventObjectType, EventsDefines.EVENT_EXEC_AFTER_RESTORE);

		// Ejecutar evento tras restaurar
		eventmgr.processEvents(eventObjectType, nIdProcedure, EventsDefines.EVENT_EXEC_AFTER_RESTORE);
		
		
		// Marcar hito en expediente / subproceso
		int milestoneType = TXConstants.MILESTONE_EXPED_RESTORE;
	
		
		TXHitoDAO hito=dtc.newMilestone(process.getKeyInt(), 
						 0, 
						 0, 
						 milestoneType, 
						"");
		
		if (logger.isDebugEnabled()) {
			logger.debug("Se ha creado el hito : " + hito.getKeyName()+" con id "+hito.getKeyInt());
		}
	} 
	
	private void updateToRestoreProcess(TXProcesoDAO process , DbCnt cnt) throws ISPACException{
		
		process.set("ESTADO", process.getInt("ESTADO_ANTERIOR"));
		process.set("ESTADO_ANTERIOR", TXConstants.NO_STATUS);;
		process.store(cnt);

		CollectionDAO col =process.getStages(cnt);
		if(col!=null){
			while(col.next())
			{
				ObjectDAO obj=col.value();
				obj.set("ESTADO",obj.getInt("ESTADO_ANTERIOR"));
				obj.set("ESTADO_ANTERIOR", TXConstants.NO_STATUS);
				obj.store(cnt);
			}
		}
		if(!process.isSubProcess()){
			col =process.getTasks(cnt);
			if(col!=null){
				while(col.next())
				{
					ObjectDAO obj=col.value();
					obj.set("ESTADO",obj.getInt("ESTADO_ANTERIOR"));
					obj.set("ESTADO_ANTERIOR", TXConstants.NO_STATUS);
					obj.store(cnt);
				}
			}
		}
		
	}
	
}
