/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.generator;

import java.util.List;

import es.ieci.tecdoc.fwktd.sql.node.statement.IStatement;

/**
 * Interface para los generadores de consultas SQL. Permite la generación de
 * consultas SQL para un sentencia o un conjunto de ellas, con la posibilidad de
 * aplicar reglas de optimización.
 */
public interface SqlGenerator {

	/**
	 * Genera la sentencia SQL equivalente a <code>statement</code>.
	 * 
	 * @param aStatement
	 * @return
	 */
	public String generateSQL(IStatement aStatement);

	/**
	 * Genera las sentencias SQL equivalentes al modelo de objetos que recibe en
	 * <code>statements</code>.
	 * 
	 * @param aStatements
	 *            Lista de <code>IStatements</code> a convertir en su
	 *            equivalente en SQL
	 * @return
	 */
	public String[] generateSQL(List<IStatement> aStatements);

	/**
	 * 
	 * @param aStatements
	 * @param aDoOptimize
	 *            indica si hay que optimizar las sentencias antes de generar su
	 *            SQL
	 * @return
	 */
	public String[] generateSQL(List<IStatement> aStatements, Boolean aDoOptimize);
}
