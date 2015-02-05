package ieci.tdw.ispac.ispactx.tx;

import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.procedure.PRelPlazoDAO;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;

import java.util.Date;

public class TXSetBaseDate implements ITXAction {
	int idObjeto;
	int tipoObjeto;
	Date baseDate;
	
	public TXSetBaseDate(int idObjeto, int tipoObjeto, Date baseDate){
		this.idObjeto = idObjeto;
		this.tipoObjeto = tipoObjeto;
		this.baseDate = baseDate;
	}

	public void run(ClientContext cs, TXTransactionDataContainer dtc, ITXTransaction itx) throws ISPACException {
		IItem object = null;
		if (tipoObjeto==PRelPlazoDAO.DEADLINE_OBJ_PROCEDURE){
			object = dtc.getProcess(idObjeto);
			
		}else if (tipoObjeto==PRelPlazoDAO.DEADLINE_OBJ_STAGE){
			object = dtc.getStage(idObjeto);
			
		}else if (tipoObjeto==PRelPlazoDAO.DEADLINE_OBJ_TASK){
			object = dtc.getTask(idObjeto);
		}
		object.set("FECHA_INICIO_PLAZO", baseDate);
		object.store(cs);
	}

	public void lock(ClientContext cs, TXTransactionDataContainer dtc) throws ISPACException {
		// TODO Auto-generated method stub
		
	}

	public Object getResult(String nameResult) {
		// TODO Auto-generated method stub
		return null;
	}

}
