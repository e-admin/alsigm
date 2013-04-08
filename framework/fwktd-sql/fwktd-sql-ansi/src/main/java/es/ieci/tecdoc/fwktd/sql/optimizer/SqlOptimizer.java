/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.optimizer;

import java.util.List;

import es.ieci.tecdoc.fwktd.sql.node.statement.IStatement;
import es.ieci.tecdoc.fwktd.sql.optimizer.impl.pattern.ISqlOptimizationPattern;


/**
 * Interface para los optimizadores de listas de consultas SQL
 */
public interface SqlOptimizer {

	/**
	 * Optimiza una lista de sentencias SQL
	 * 
	 * @param aStatements
	 *            lista de sentencias a optimizar
	 * @return lista de sentencias optimizadas
	 */
	public List<IStatement> optimize(List<IStatement> aStatements);

	/**
	 * Añadir patrones de optimización
	 * 
	 * @param aPatterns
	 *            Lista de patrones
	 */
	public void setPatterns(List<ISqlOptimizationPattern> aPatterns);
}
