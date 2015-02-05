package ieci.tdw.ispac.ispactx.tx;

import java.util.Map;

import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.api.rule.RuleProperties;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.tx.TXHitoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;

import org.apache.log4j.Logger;

public class TXCleanProcess  extends TXDeleteProcess{

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(TXCleanProcess.class);
	

	public TXCleanProcess(int nIdProc) {
		super(nIdProc);
	}
	
	public TXCleanProcess(int nIdProc, Map params){
		super(nIdProc,params);
	}
	
	/**
	 * Al limpiar un procedimiento se elimina todos los hitos, y no se lanza ningún evento
	 */
	public void run(ClientContext cs, TXTransactionDataContainer dtc,
			ITXTransaction itx) throws ISPACException {
		
		
		TXProcesoDAO process=dtc.getProcess(nIdProc);

		deleteProcess(cs, process, dtc);

		dtc.deleteMilestones(process.getKeyInt(), -1, -1, -1);
		if (logger.isDebugEnabled()) {
			logger.debug("Se han eliminado los hitos del proceso "+process.getKeyInt() );
		}
		
	} 

}
