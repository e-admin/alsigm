package ieci.tdw.ispac.api.rule.tasks;

import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

/**
 * Comprueba si ya existe un tramite iniciado (abierto o cerrado) del mismo tipo
 * del tramite que se quiere iniciar
 *
 */
public class OnlyOneInstanceInitRule extends OnlyOneInstanceRule {

	protected String getQuery(IRuleContext rulectx) throws ISPACRuleException {
		return "WHERE NUMEXP = '" + DBUtil.replaceQuotes(rulectx.getNumExp())
				+ "' AND ID_FASE_PCD = " + rulectx.getStageProcedureId()
				+ " AND ID_TRAM_PCD = " + rulectx.getTaskProcedureId();
	}

	protected String getKeyErrorMessage() throws ISPACRuleException {
		return "tramite.iniciado.duplicado";
	}

}
