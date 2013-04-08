package ieci.tdw.ispac.api.rule;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.dao.cat.CTApplicationDAO;

public class ReturnFormApplicationRuleTest implements IRule {

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public Object execute(IRuleContext rulectx) throws ISPACRuleException {
		try {
			CTApplicationDAO application =  new CTApplicationDAO(rulectx.getClientContext().getConnection());
			application.set("NOMBRE", "Aplicacion de test");
			return application;
		} catch (ISPACException e) {
			throw new ISPACRuleException(e);
		}
	}

	public void cancel(IRuleContext rulectx) throws ISPACRuleException {
	}
}
