package ieci.tdw.ispac.ispactx.tx;

import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.TransactionContainer;
import ieci.tdw.ispac.ispaclib.dao.tx.TXTramitacionAgrupadaDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXTramitacionAgrupadaExpsDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;

import java.util.Date;

public class TXCreateBatchTask implements ITXAction {
	
	private int id = 0;
	private int idFase;
	private String [] numExps = null;


	/**
	 * Contructor.
	 */
	public TXCreateBatchTask(int idFase, String [] numExps) {
		super();
		this.idFase = idFase;
		this.numExps = numExps;
	}

	public void run(ClientContext cs, TXTransactionDataContainer dtc,
			ITXTransaction itx) throws ISPACException {

		TransactionContainer txcontainer = cs.getTXContainer();
		
		try
		{
			txcontainer.begin();
			DbCnt cnt = txcontainer.getConnection();
			
			// Creación de la tramitación agruada
			TXTramitacionAgrupadaDAO batchTask = new TXTramitacionAgrupadaDAO(cnt);
			batchTask.createNew(cnt);
			batchTask.set("ID_RESP", cs.getRespId());
			batchTask.set("ESTADO", 1);
			batchTask.set("ID_FASE", idFase);
			batchTask.set("FECHA_CREACION", new Date());
			batchTask.store(txcontainer.getConnection());
			
			id = batchTask.getKeyInt();
			
			// Agregar relación de expedientes
			if ((numExps != null) && (numExps.length > 0)) {
				for (int i = 0; i < numExps.length; i++) {
					TXTramitacionAgrupadaExpsDAO batchTaskExps = 
						new TXTramitacionAgrupadaExpsDAO(cnt);
					batchTaskExps.createNew(txcontainer.getConnection());
					batchTaskExps.set("ID_TRAM_AGRUP", id);
					batchTaskExps.set("NUMEXP", numExps[i]);
					batchTaskExps.store(txcontainer.getConnection());
				}
			}
			
			txcontainer.commit();

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
