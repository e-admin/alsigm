package ieci.tdw.ispac.ispactx.tx;

import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.TransactionContainer;
import ieci.tdw.ispac.ispaclib.dao.tx.TXTramitacionAgrupadaDAO;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;

public class TXUpdateBatchTask  implements ITXAction {
	
	private IItem batchTaskItem = null;
	private int id;
	
	public TXUpdateBatchTask(IItem batchTask){
		this.batchTaskItem = batchTask;
	}
	
	public void run(ClientContext cs, TXTransactionDataContainer dtc,
			ITXTransaction itx) throws ISPACException {

		TransactionContainer txcontainer = cs.getTXContainer();
		
		try
		{
			txcontainer.begin();
			
			// Creación de la tramitación agruada
			TXTramitacionAgrupadaDAO batchTask = new TXTramitacionAgrupadaDAO(txcontainer.getConnection(), batchTaskItem.getInt("ID_AGRUPACION"));
			
			batchTask.set("ID_RESP", batchTaskItem.get("ID_RESP"));
			batchTask.set("ESTADO", batchTaskItem.get("ESTADO"));
			batchTask.set("ID_FASE", batchTaskItem.get("ID_FASE"));
			batchTask.set("FECHA_CREACION", batchTaskItem.get("FECHA_CREACION"));
			batchTask.set("ID_ULTIMO_TRAMITE", batchTaskItem.get("ID_ULTIMO_TRAMITE"));
			batchTask.set("ID_ULTIMO_TEMPLATE", batchTaskItem.get("ID_ULTIMO_TEMPLATE"));
			batchTask.set("ID_ULTIMO_TIPODOC", batchTaskItem.get("ID_ULTIMO_TIPODOC"));
			batchTask.store(txcontainer.getConnection());

			txcontainer.commit();
			
			//valor para getResult
			id = batchTask.getKeyInt();

		} finally {
			txcontainer.release();
		}
	}

	public Object getResult(String nameResult) {
		return new Integer(id);
	}


	public void lock(ClientContext cs, TXTransactionDataContainer dtc)
			throws ISPACException {}


}
