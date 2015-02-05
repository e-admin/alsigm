package ieci.tdw.ispac.api.rule;

import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;

public class ReturnFormNullRuleTest implements IRule {

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public Object execute(IRuleContext rulectx) throws ISPACRuleException {
		return null;
	}

	public void cancel(IRuleContext rulectx) throws ISPACRuleException {
	}
}
