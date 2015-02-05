package ieci.tdw.ispac.api.rule.test;

import org.apache.log4j.Logger;

import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public class TestFalseRule implements IRule {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(TestFalseRule.class);

	/**
	 * Constructor.
	 */
	public TestFalseRule() {
		super();
		logger.info("Llamada al constructor de la clase: "
				+ this.getClass().getName());
	}

	/**
	 * Inicializa la regla.
	 *
	 * @param rulectx
	 *            Contexto de la regla.
	 * @return true si la inicialización se ha ejecutado correctamente.
	 * @throws ISPACRuleException
	 *             si ocurre algún error.
	 */
	public boolean init(IRuleContext rulectx) throws ISPACRuleException {
		log("init", rulectx);
		return true;
	}

	/**
	 * Valida la regla.
	 *
	 * @param rulectx
	 *            Contexto de la regla.
	 * @return true si la regla se ha validado.
	 * @throws ISPACRuleException
	 *             si ocurre algún error.
	 */
	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
		log("validate", rulectx);
		rulectx.setInfoMessage("TestFalseRule - Mensaje de información");
		return false;
	}

	/**
	 * Ejecuta la regla.
	 *
	 * @param rulectx
	 *            Contexto de la regla.
	 * @return Objeto de respuesta de la regla.
	 * @throws ISPACRuleException
	 *             si ocurre algún error.
	 */
	public Object execute(IRuleContext rulectx) throws ISPACRuleException {
		log("execute", rulectx);
		return Boolean.FALSE;
	}

	/**
	 * El sistema invoca esta función si se encuentra con algún problema durante
	 * la ejecución del evento. Permitiría deshacer operaciones realizadas en el
	 * método execute().
	 *
	 * @param rulectx
	 *            Contexto de la regla.
	 * @throws ISPACRuleException
	 *             si ocurre algún error.
	 */
	public void cancel(IRuleContext rulectx) throws ISPACRuleException {
		log("cancel", rulectx);
	}

	protected void log(String method, IRuleContext rulectx) throws ISPACRuleException {
		if (logger.isInfoEnabled()) {
			logger.info("Se ha ejecutado el método [" + method + "] de la regla [" + this.getClass().getName() + "]");
			if (logger.isDebugEnabled()) {

				logger.debug("Contenido del contexto de la regla:");
				try{logger.debug("- docContext: " + rulectx.getDocContext());}catch(Exception e){logger.debug("No es posible mostrar el valor de 'docContext'");}
				try{logger.debug("- infoMessage: " + rulectx.getInfoMessage());}catch(Exception e){logger.debug("No es posible mostrar el valor de 'infoMessage'");}
				try{logger.debug("- clientContext: " + rulectx.getClientContext());}catch(Exception e){logger.debug("No es posible mostrar el valor de 'clientContext'");}
				try{logger.debug("- numExp: " + rulectx.getNumExp());}catch(Exception e){logger.debug("No es posible mostrar el valor de 'numExp'");}
				try{logger.debug("- processId: " + rulectx.getProcessId());}catch(Exception e){logger.debug("No es posible mostrar el valor de 'processId'");}
				try{logger.debug("- subprocessId: " + rulectx.getSubProcessId());}catch(Exception e){logger.debug("No es posible mostrar el valor de 'subprocessId'");}
				try{logger.debug("- procedureId: " + rulectx.getProcedureId());}catch(Exception e){logger.debug("No es posible mostrar el valor de 'procedureId'");}
				try{logger.debug("- stageProcedureId: " + rulectx.getStageProcedureId());}catch(Exception e){logger.debug("No es posible mostrar el valor de 'stageProcedureId'");}
				try{logger.debug("- stageId: " + rulectx.getStageId());}catch(Exception e){logger.debug("No es posible mostrar el valor de 'stageId'");}
				try{logger.debug("- taskProcedureId: " + rulectx.getTaskProcedureId());}catch(Exception e){logger.debug("No es posible mostrar el valor de 'taskProcedureId'");}
				try{logger.debug("- taskId: " + rulectx.getTaskId());}catch(Exception e){logger.debug("No es posible mostrar el valor de 'taskId'");}
				try{logger.debug("- item: " + rulectx.getItem().toXml());}catch(Exception e){logger.debug("No es posible mostrar el valor de 'item'");}
				try{logger.debug("- entityid: " + rulectx.get("_entityid"));}catch(Exception e){logger.debug("No es posible mostrar el valor de 'entityid'");}
				try{logger.debug("- regentityid: " + rulectx.get("_regentityid"));}catch(Exception e){logger.debug("No es posible mostrar el valor de 'regentityid'");}
				try{logger.debug("- respdelegateid: " + rulectx.get("_respdelegateid"));}catch(Exception e){logger.debug("No es posible mostrar el valor de 'respdelegateid'");}
				try{logger.debug("- respdelegatename: " + rulectx.get("_respdelegatename"));}catch(Exception e){logger.debug("No es posible mostrar el valor de 'respdelegatename'");}
				try{logger.debug("- documentid: " + rulectx.get("_documentid"));}catch(Exception e){logger.debug("No es posible mostrar el valor de 'documentid'");}
				try{logger.debug("- documenttype: " + rulectx.get("_documenttype"));}catch(Exception e){logger.debug("No es posible mostrar el valor de 'documenttype'");}
				try{logger.debug("- documentname: " + rulectx.get("_documentname"));}catch(Exception e){logger.debug("No es posible mostrar el valor de 'documentname'");}
				try{logger.debug("- documentauthor: " + rulectx.get("_documentauthor"));}catch(Exception e){logger.debug("No es posible mostrar el valor de 'documentauthor'");}
				try{logger.debug("- documentauthorname: " + rulectx.get("_documentauthorname"));}catch(Exception e){logger.debug("No es posible mostrar el valor de 'documentauthorname'");}
				try{logger.debug("- documentref: " + rulectx.get("_documentref"));}catch(Exception e){logger.debug("No es posible mostrar el valor de 'documentref'");}
				try{logger.debug("- documentmimetype: " + rulectx.get("_documentmimetype"));}catch(Exception e){logger.debug("No es posible mostrar el valor de 'documentmimetype'");}
				try{logger.debug("- templateid: " + rulectx.get("_templateid"));}catch(Exception e){logger.debug("No es posible mostrar el valor de 'templateid'");}
				try{logger.debug("- templatename: " + rulectx.get("_templatename"));}catch(Exception e){logger.debug("No es posible mostrar el valor de 'templatename'");}
				try{logger.debug("- registerid: " + rulectx.get("_registerid"));}catch(Exception e){logger.debug("No es posible mostrar el valor de 'registerid'");}
				try{logger.debug("- objectid: " + rulectx.getObjectId());}catch(Exception e){logger.debug("No es posible mostrar el valor de 'objectid'");}
				try{logger.debug("- objecttype: " + rulectx.getObjectType());}catch(Exception e){logger.debug("No es posible mostrar el valor de 'objecttype'");}
				try{logger.debug("- event: " + rulectx.getEvent());}catch(Exception e){logger.debug("No es posible mostrar el valor de 'event'");}
			}
		}
	}
}
