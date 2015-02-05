package ieci.tecdoc.sgm.tram.rules;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispacweb.api.impl.states.ExpedientState;
import ieci.tecdoc.sgm.tram.rules.helpers.RuleHelper;

import org.apache.log4j.Logger;

/**
 * Comprueba que no hay ningún trámite abierto en el expediente.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class CheckAllTasksClosedRule implements IRule {

	/**
	 * Logger de la clase.
	 */
	protected static final Logger logger = Logger
			.getLogger(CheckAllTasksClosedRule.class);

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

		IClientContext ctx = rulectx.getClientContext();
		IInvesflowAPI invesflowAPI = ctx.getAPI();

		try {

			// Comprobar si hay algún trámite abierto excepto el trámite actual
			IItemCollection itemcol = invesflowAPI.getEntitiesAPI()
					.getEntities(SpacEntities.SPAC_TRAMITES,
							rulectx.getNumExp(),
							" ID <> " + rulectx.getTaskId());
			if (itemcol.next()) {

				RuleHelper.setInfoMessage(rulectx, ExpedientState.class,
						"operation.noExecute.tasks.open", null,
						"operation.noExecute.tasks.open.numexp",
						new String[] { rulectx.getNumExp() });

				return false;
			}

		} catch (ISPACException e) {
			logger
					.error("Error en la regla CheckAllTaskClosedRule:validate",
							e);
			throw new ISPACRuleException(e);
		}

		return true;
	}

}
