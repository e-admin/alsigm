package ieci.tdw.ispac.api.rule.processes;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.dao.procedure.PRelPlazoDAO;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

public class RecalcularDeadlineRule implements IRule {

	private static final Logger logger = 
		Logger.getLogger(RecalcularDeadlineRule.class);
	


	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		// TODO No importa lo q se retorne
		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
		
		try {
			if (rulectx.getStageId() > 0) {
				//establecer fecha inicio
				Calendar cal = new GregorianCalendar();
				cal.setTime(new Date());
				cal.add(Calendar.DATE, 1);
				rulectx.getClientContext().getAPI().getTransactionAPI().setBaseDate(
						PRelPlazoDAO.DEADLINE_OBJ_STAGE, rulectx.getStageId(), cal.getTime());
				//recalcular fecha limite
				rulectx.getClientContext().getAPI().getTransactionAPI().recalculateDeadline(
						PRelPlazoDAO.DEADLINE_OBJ_STAGE, rulectx.getStageId());
			}
			
		} catch (ISPACException e) {
			logger.error("Error en la regla " + this.getClass().getName(), e);
			throw new ISPACRuleException("Error Recalculando Plazos.", e);
		}
		return true;
	}

	public Object execute(IRuleContext rulectx) throws ISPACRuleException {
		// TODO Auto-generated method stub
		return null;
	}

	public void cancel(IRuleContext rulectx) throws ISPACRuleException {
		// TODO Auto-generated method stub

	}

}
