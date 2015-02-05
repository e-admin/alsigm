package ieci.tdw.ispac.api.rule;

import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.events.ValidateConditionRule;
import ieci.tdw.ispac.ispaclib.classloaderUtil.*;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.context.*;

import ieci.tdw.ispac.ispaclib.dao.cat.*;

public class RuleFactory
{
	
	/**
	 * Contexto del cliente.
	 */
	IClientContext mcctx;
	
	
	/**
	 * Constructor.
	 * @param cctx Contexto del cliente.
	 */
	public RuleFactory(IClientContext cctx)
	{
		mcctx=cctx;
	}
	
	public IRule instanceRule(IItem pevent)
		throws ISPACException
	{
		int ruleId = pevent.getInt("ID_REGLA");
		if (ruleId > 0) {
			return instanceRule(ruleId);
		} else {
			return new ValidateConditionRule(pevent);
		}
	}

	public IRule instanceRule(int nIdRule)
		throws ISPACException
	{
		CTRuleDAO rule=new CTRuleDAO(mcctx.getConnection(),nIdRule);
		return instanceRule(rule.getString("CLASE"));
	}
	
	public IRule instanceRule(String ruleClassName)
		throws ISPACException
	{
		ClassloaderUtil clsloader=new ClassloaderUtil();
		return (IRule)clsloader.getInstance(ruleClassName);
	}
}
