package ieci.tdw.ispac.ispactx.tx;

import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.deadline.Deadline;
import ieci.tdw.ispac.deadline.DeadlineManager;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.procedure.PRelPlazoDAO;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;

import java.util.Date;
import java.util.Map;

public class TXRecalculateDeadline implements ITXAction {
	
	int idObjeto;
	int tipoObjeto;
	
	/**
	 * Parámetros para el contexto de las reglas.
	 */
	private final Map mparams;

	public TXRecalculateDeadline(int idObjeto, int tipoObjeto){
		this(idObjeto, tipoObjeto, null);
	}

	public TXRecalculateDeadline(int idObjeto, int tipoObjeto, Map params){
		this.idObjeto = idObjeto;
		this.tipoObjeto = tipoObjeto;
		this.mparams = params;
	}

	private Date getFechaLimite(Deadline deadline)throws ISPACException{
		if (deadline!=null){
			return  deadline.getFinalDate();
		}else
			return null;
	}
	public void run(ClientContext cs, TXTransactionDataContainer dtc, ITXTransaction itx)
			throws ISPACException {
		EventManager eventmgr = new EventManager(cs, mparams);
		DeadlineManager mdeadlinemgr = new DeadlineManager(cs, eventmgr);
		Deadline deadline = mdeadlinemgr.recalcDeadline(tipoObjeto, idObjeto);
		IItem object = null;
		if (tipoObjeto == PRelPlazoDAO.DEADLINE_OBJ_PROCEDURE) {
			object = dtc.getProcess(idObjeto);

		} else if (tipoObjeto == PRelPlazoDAO.DEADLINE_OBJ_STAGE) {
			object = dtc.getStage(idObjeto);

		} else if (tipoObjeto == PRelPlazoDAO.DEADLINE_OBJ_TASK) {
			object = dtc.getTask(idObjeto);
		}

		// basedate que tiene actualmente el proceso
		Date datebasedate = object.getDate("FECHA_INICIO_PLAZO");
		if (datebasedate==null)
			throw new ISPACException("El objeto no tiene FECHA_INICIO_PLAZO, no es posible recalcular el plazo");
		
		// actualizar fecha limite
		Date fechaLimite = getFechaLimite(deadline);
		object.set("FECHA_LIMITE", fechaLimite);

		// numero de dias del plazo
		if (tipoObjeto == PRelPlazoDAO.DEADLINE_OBJ_PROCEDURE) {
			if (fechaLimite != null)
				object.set("NUM_DIAS_PLAZO", calculateDays(datebasedate, fechaLimite));
		}

		object.store(cs);

	}
	
	private int calculateDays(Date initialDate, Date finalDate){
		long diffMillis = finalDate.getTime() - initialDate.getTime();
		long diffDays = diffMillis / (1000 * 60 * 60 * 24);
		return new Long(diffDays).intValue();
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
