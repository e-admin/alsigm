package ieci.tdw.ispac.ispactx.tx;

import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.procedure.PRelPlazoDAO;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;

public class TXResumeDeadline implements ITXAction {
	
	private final int idObjeto;
	private final int tipoObjeto;
	
	public TXResumeDeadline(int tipoObjeto, int idObjeto){
		super();
		this.tipoObjeto = tipoObjeto;
		this.idObjeto = idObjeto;
	}
	
	public void run(ClientContext cs, TXTransactionDataContainer dtc, ITXTransaction itx) throws ISPACException {
		dtc.resumeDeadline(tipoObjeto, idObjeto);
		if (tipoObjeto == PRelPlazoDAO.DEADLINE_OBJ_PROCEDURE){
			dtc.newMilestone(idObjeto,0,0,
					TXConstants.MILESTONE_RESUME_DEADLINEPROCESS);
		}else if (tipoObjeto == PRelPlazoDAO.DEADLINE_OBJ_STAGE){
			dtc.newMilestone(idObjeto,0,0,
					TXConstants.MILESTONE_RESUME_DEADLINESTAGE);
		}else if (tipoObjeto == PRelPlazoDAO.DEADLINE_OBJ_TASK){
			dtc.newMilestone(idObjeto,0,0,
					TXConstants.MILESTONE_RESUME_DEADLINETASK);
		}
	}

	public void lock(ClientContext cs, TXTransactionDataContainer dtc) throws ISPACException {
		//dtc.getLockManager().lockProcess(mnIdProc);
	}

	public Object getResult(String nameResult) {
		// TODO Auto-generated method stub
		return null;
	}

}
