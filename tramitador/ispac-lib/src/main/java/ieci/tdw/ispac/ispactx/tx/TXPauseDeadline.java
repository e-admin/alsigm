package ieci.tdw.ispac.ispactx.tx;

import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.procedure.PRelPlazoDAO;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;

public class TXPauseDeadline implements ITXAction {
	
	int idObjeto;
	int tipoObjeto;
	
	public TXPauseDeadline(int tipoOjeto, int idObjeto){
		this.idObjeto = idObjeto;
		this.tipoObjeto = tipoOjeto;
	}
	
	public void run(ClientContext cs, TXTransactionDataContainer dtc,ITXTransaction itx)
	throws ISPACException
	{
		dtc.pauseDeadline(tipoObjeto, idObjeto);
		if (tipoObjeto == PRelPlazoDAO.DEADLINE_OBJ_PROCEDURE){
			dtc.newMilestone(idObjeto,0,0,
					TXConstants.MILESTONE_PAUSE_DEADLINEPROCESS);
		}else if (tipoObjeto == PRelPlazoDAO.DEADLINE_OBJ_STAGE){
			dtc.newMilestone(idObjeto,0,0,
					TXConstants.MILESTONE_PAUSE_DEADLINESTAGE);
		}else if (tipoObjeto == PRelPlazoDAO.DEADLINE_OBJ_TASK){
			dtc.newMilestone(idObjeto,0,0,
					TXConstants.MILESTONE_PAUSE_DEADLINETASK);
		}

	}

	public void lock(ClientContext cs, TXTransactionDataContainer dtc) throws ISPACException {
		
	}

	public Object getResult(String nameResult) {
		return null;
	}

}
