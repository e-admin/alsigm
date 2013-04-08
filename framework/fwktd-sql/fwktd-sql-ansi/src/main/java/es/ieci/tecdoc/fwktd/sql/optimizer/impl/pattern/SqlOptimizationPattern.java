/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.optimizer.impl.pattern;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.sql.node.statement.IStatement;

/**
 * Patrón para la optimización de sentencias SQL
 */
public abstract class SqlOptimizationPattern implements ISqlOptimizationPattern {

	/**
	 * Dada una lista de sentencias y una sentencia contenida en dicha lista con
	 * índice i retorna la sublista formada desde los elementos i+1 en adelante
	 * 
	 * @param aStatements
	 *            lista de sentencias
	 * @param aStatement
	 *            sentencia a partir de la cual hacer la sublista
	 * @return sublista formada desde los elementos 1+1 en adelante
	 */
	protected List<IStatement> subList(List<IStatement> aStatements,
			IStatement aStatement) {

		Assert.isTrue(CollectionUtils.isNotEmpty(aStatements)
				&& aStatements.contains(aStatement));

		return aStatements.subList(aStatements.indexOf(aStatement) + 1, aStatements
				.size());
	}
}