package ieci.tdw.ispac.ispactx.tx;

import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.TransactionContainer;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXTramitacionAgrupadaDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXTramitacionAgrupadaExpsDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;

public class TXDeleteBatchTask  implements ITXAction {
	
	private int batchTaskId = 0;
	
	public TXDeleteBatchTask(int batchTaskId){
		this.batchTaskId = batchTaskId;
	}
	
	public void run(ClientContext cs, TXTransactionDataContainer dtc,
			ITXTransaction itx) throws ISPACException {

		TransactionContainer txcontainer = cs.getTXContainer();
		
		try
		{
			txcontainer.begin();
			
			// Conexión con la base de datos
			DbCnt cnt = txcontainer.getConnection();

			// Eliminación de los expedientes de la tramitación agrupada
			CollectionDAO batchTaskExps = new CollectionDAO(TXTramitacionAgrupadaExpsDAO.class, cnt);
			batchTaskExps.delete(cnt, new StringBuffer("WHERE ID_TRAM_AGRUP=")
					.append(batchTaskId).toString());
			
			// Eliminación de la tramitación agrupada
			TXTramitacionAgrupadaDAO batchTask = new TXTramitacionAgrupadaDAO(
					cnt, batchTaskId);

			batchTask.delete(cnt);

			txcontainer.commit();

		} finally {
			txcontainer.release();
		}
	}

	public Object getResult(String nameResult) {
		return new Integer(1);
	}

	public void lock(ClientContext cs, TXTransactionDataContainer dtc)
			throws ISPACException {}

}
