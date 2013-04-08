package ieci.tecdoc.sgm.tram.rules;

import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tecdoc.sgm.tram.rules.helpers.RuleHelper;

/**
 * No permite anexar documentos externos, sólo desde plantilla.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class NoAttachExternalDocumentsRule implements IRule {

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see ieci.tdw.ispac.api.rule.IRule#cancel(ieci.tdw.ispac.api.rule.IRuleContext)
	 */
	public void cancel(IRuleContext rulectx) throws ISPACRuleException {
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see ieci.tdw.ispac.api.rule.IRule#execute(ieci.tdw.ispac.api.rule.IRuleContext)
	 */
	public Object execute(IRuleContext rulectx) throws ISPACRuleException {
		return null;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see ieci.tdw.ispac.api.rule.IRule#init(ieci.tdw.ispac.api.rule.IRuleContext)
	 */
	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see ieci.tdw.ispac.api.rule.IRule#validate(ieci.tdw.ispac.api.rule.IRuleContext)
	 */
	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {

		rulectx.setInfoMessage(RuleHelper.getMessage(rulectx.getClientContext()
				.getLocale(), "attach.externalDocuments.noExecute"));

		return false;
	}

}
