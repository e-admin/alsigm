
package ieci.tdw.ispac.api.rule.procedures;

import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;

public class TestPcdId implements IRule {

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	public Object execute(IRuleContext rulectx) throws ISPACRuleException {
		String pcdId = rulectx.get("pcdId");
		if (pcdId.equals(Integer.toString(rulectx.getProcedureId())))
				return new Boolean(true);
		return new Boolean(false);
	}

	public void cancel(IRuleContext rulectx) throws ISPACRuleException {

	}

}
