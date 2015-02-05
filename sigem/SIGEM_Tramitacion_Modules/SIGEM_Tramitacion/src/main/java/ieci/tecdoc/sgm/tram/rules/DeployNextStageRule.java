package ieci.tecdoc.sgm.tram.rules;

import org.apache.log4j.Logger;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.context.TransactionContainer;
import ieci.tdw.ispac.ispaclib.dao.tx.TXFaseDAO;

/**
 * Cerrar Fase y Avanzar.
 *
 * Cierra la fase actual y activa la/s siguiente/s fase/s o nodo/s según la definición del procedimiento,
 * ejecutando la operación en un contenedor de transacciones nuevo pues
 * hace el commit para el contenedor de transacciones ya existente.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DeployNextStageRule implements IRule {

	/**
	 * Logger de la clase.
	 */
	protected static final Logger logger = Logger
			.getLogger(DeployNextStageRule.class);

	/**
	 *
	 * {@inheritDoc}
	 * @see ieci.tdw.ispac.api.rule.IRule#init(ieci.tdw.ispac.api.rule.IRuleContext)
	 */
	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see ieci.tdw.ispac.api.rule.IRule#validate(ieci.tdw.ispac.api.rule.IRuleContext)
	 */
	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
		return true;
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see ieci.tdw.ispac.api.rule.IRule#execute(ieci.tdw.ispac.api.rule.IRuleContext)
	 */
	public Object execute(IRuleContext rulectx) throws ISPACRuleException {

		IClientContext cct = rulectx.getClientContext();

		try {

			// Commit y liberación de la transacción ya existente
			// para cuando la regla se asocia al evento de cerrar el trámite
			// ya que para avanzar de fase es obligatorio que todos los trámites
			// de la fase actual estén cerrados
			TransactionContainer txContainer = cct.getTXContainer();
			txContainer.commit();
			txContainer.release();

			IInvesflowAPI invesflowAPI = cct.getAPI();
			ITXTransaction tx = invesflowAPI.getTransactionAPI();

			// Fase a cerrar
			int stageId = rulectx.getStageId();
			TXFaseDAO stage = (TXFaseDAO) invesflowAPI.getStage(stageId);

			int nIdProcess = stage.getInt("ID_EXP");
			int nIdPcdStage = stage.getInt("ID_FASE");
			int nidstage = stage.getKeyInt();

			try {
				// Cerrar la fase y avanzar
				tx.deployNextStage(nIdProcess, nIdPcdStage, nidstage);
			}
			catch (ISPACException e) {
				// Si no se puede cerrar la fase no se relanza la excepción
				// ya que el proceso es transparente al usuario y el commit
				// inicial ya no se podría deshacer
				logger.info(
						"Error en la regla DeployNextStageRule:execute al cerrar y avanzar de fase",
						e);
			}
		}
		catch (ISPACException e){
			logger.error(
					"Error en la regla DeployNextStageRule:execute",
					e);
			throw new ISPACRuleException(e);
		}

		return null;
	}

	public void cancel(IRuleContext rulectx) throws ISPACRuleException {
	}

}
