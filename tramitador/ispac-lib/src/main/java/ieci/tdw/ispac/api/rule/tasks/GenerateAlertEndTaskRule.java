package ieci.tdw.ispac.api.rule.tasks;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.notices.Notices;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public abstract class GenerateAlertEndTaskRule implements IRule {

	private static final Logger logger = Logger.getLogger(GenerateAlertEndTaskRule.class);

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public Object execute(IRuleContext rulectx) throws ISPACRuleException {
		
		try {
			
			String idResp = getResponsible(rulectx); 
			//Si se ha obtenido ningun destinatario para el aviso se genera, en caso contrario no se genera el aviso
			if (StringUtils.isNotEmpty(idResp)){
				String message = "notice.endTask";
				Notices.generateNotice(rulectx.getClientContext(), rulectx.getProcessId(), rulectx.getStageId(), rulectx.getTaskId(), rulectx.getNumExp(), message, idResp, Notices.TIPO_AVISO_TRAMITE_FINALIZADO, rulectx.getStageProcedureId(), rulectx.getTaskProcedureId());
			}
		} catch (ISPACException e) {
			logger.error("Error en la regla " + this.getClass().getName(), e);
			throw new ISPACRuleException(e);
		}
		
		return null;
	}

	public void cancel(IRuleContext rulectx) throws ISPACRuleException {

	}
	
	protected abstract String getResponsible(IRuleContext rulectx) throws ISPACException ;

}
