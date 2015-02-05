/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.optimizer.impl.pattern;

import java.util.List;

import es.ieci.tecdoc.fwktd.sql.node.statement.IStatement;
import es.ieci.tecdoc.fwktd.sql.optimizer.impl.pattern.command.ISqlOptimizationCommand;

/**
 * Patrón para la optimización de sentencias SQL
 */
public interface ISqlOptimizationPattern {

	/**
	 * Devuelve la prioridad del patrón
	 */
	public int getPriority();

	/**
	 * Calcula si un patrón es aplicable para una lista de sentencias, y en caso
	 * de que así sea retorna el command que realiza la optimización
	 * 
	 * @param aStatements
	 *            Lista de sentencias a optimizar
	 * @return Command que realiza la optimización. null si la optimización no
	 *         es aplicable
	 */
	public ISqlOptimizationCommand enable(List<IStatement> aStatements);
}
