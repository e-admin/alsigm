package ieci.tdw.ispac.api.rule.tasks;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.api.rule.helper.RuleHelper;

import org.apache.log4j.Logger;


public abstract class OnlyOneInstanceRule implements IRule {

	private static final Logger logger =
		Logger.getLogger(OnlyOneInstanceRule.class);

	public void cancel(IRuleContext rulectx) throws ISPACRuleException {
	}

	public Object execute(IRuleContext rulectx) throws ISPACRuleException {
		return null;
	}

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
		try {
			IEntitiesAPI entitiesAPI = rulectx.getClientContext().getAPI()
			.getEntitiesAPI();
			String sqlQuery = getQuery(rulectx);
			int count = entitiesAPI.countEntities(
					SpacEntities.SPAC_DT_TRAMITES, sqlQuery);
			if (count > 1) {
				String keyErrorMessage = getKeyErrorMessage();
				rulectx.setInfoMessage(RuleHelper.getMessage(rulectx
						.getClientContext().getLocale(), keyErrorMessage));
				return false;
			}
			return true;
		} catch (ISPACException e) {
			logger.error("Error en la regla " + this.getClass().getName(), e);
			throw new ISPACRuleException(e);
		}
	}

	protected abstract String getKeyErrorMessage() throws ISPACRuleException;

	protected abstract String getQuery(IRuleContext rulectx)
	throws ISPACRuleException;

}
