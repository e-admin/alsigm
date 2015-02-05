package ieci.tecdoc.sgm.tram.rules.task;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispacweb.api.impl.states.TaskState;
import ieci.tecdoc.sgm.tram.rules.helpers.RuleHelper;

import org.apache.log4j.Logger;

/**
 * No permite anexar más de un documento al trámite.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class CheckOnlyOneDocumentRule implements IRule {

	/**
	 * Logger de la clase.
	 */
	protected static final Logger logger = Logger
			.getLogger(CheckOnlyOneDocumentRule.class);

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

			// Comprobar que no se hayan generado documentos para el trámite
			// actual
			int countDocumentos = invesflowAPI.getEntitiesAPI().countEntities(
					SpacEntities.SPAC_DT_DOCUMENTOS,
					" WHERE ID_TRAMITE = " + rulectx.getTaskId());

			// La regla se ejecuta tras la generación del documento
			// por lo que la comprobación es mayor que uno
			if (countDocumentos > 1) {

				RuleHelper.setInfoMessage(rulectx, TaskState.class,
						"operation.noExecute.task.oneDocument", null,
						"operation.noExecute.task.oneDocument.numexp",
						new String[] { rulectx.getNumExp() });

				return false;
			}

		} catch (ISPACException e) {
			logger.error("Error en la regla CheckOnlyOneDocumentRule:validate",
					e);
			throw new ISPACRuleException(e);
		}

		return true;
	}

}
