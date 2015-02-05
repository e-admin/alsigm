package ieci.tdw.ispac.ispactx.tx;

import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.procedure.PRelPlazoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXTramiteDAO;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;



public class TXSetCalendar implements ITXAction {
	
	int tipoObjeto;
	int idObjeto;
	int idCalendario;
	
	public TXSetCalendar(int tipoObjeto, int idObjeto, int idCalendario){
		this.idObjeto = idObjeto;
		this.idCalendario = idCalendario;
		this.tipoObjeto = tipoObjeto;
	}
	
	public void run(ClientContext cs, TXTransactionDataContainer dtc,ITXTransaction itx)
	throws ISPACException
	{
		if (tipoObjeto==PRelPlazoDAO.DEADLINE_OBJ_PROCEDURE){
			TXProcesoDAO exped= dtc.getProcess(idObjeto);
			exped.set("ID_CALENDARIO", idCalendario);
			exped.store(cs);
			
		}else if (tipoObjeto==PRelPlazoDAO.DEADLINE_OBJ_STAGE){
			TXFaseDAO fase = dtc.getStage(idObjeto);
			fase.set("ID_CALENDARIO", idCalendario);
			fase.store(cs);
			
		}else if (tipoObjeto==PRelPlazoDAO.DEADLINE_OBJ_TASK){
			TXTramiteDAO task = dtc.getTask(idObjeto);
			task.set("ID_CALENDARIO", idCalendario);
			task.store(cs);
		}
	}

	public void lock(ClientContext cs, TXTransactionDataContainer dtc) throws ISPACException {
		if (tipoObjeto==PRelPlazoDAO.DEADLINE_OBJ_PROCEDURE){
			dtc.getLockManager().lockProcess(idObjeto);
		}
	}
	
	public Object getResult(String nameResult) {
		// TODO Auto-generated method stub
		return null;
	}
}
