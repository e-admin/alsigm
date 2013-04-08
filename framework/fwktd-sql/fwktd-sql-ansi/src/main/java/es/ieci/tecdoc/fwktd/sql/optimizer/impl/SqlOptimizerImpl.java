/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.optimizer.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.ComparatorUtils;

import es.ieci.tecdoc.fwktd.sql.node.statement.IStatement;
import es.ieci.tecdoc.fwktd.sql.optimizer.exception.OptimizeException;
import es.ieci.tecdoc.fwktd.sql.optimizer.impl.pattern.ISqlOptimizationPattern;
import es.ieci.tecdoc.fwktd.sql.optimizer.impl.pattern.command.ISqlOptimizationCommand;

/**
 * Optimizador para listas de sentencias SQL
 */
public class SqlOptimizerImpl extends AbstractSqlOptimizer {

	@Override
	protected void doOptimize(List<IStatement> aStatements) {

		try {
			// Ordenamos los patrones según su prioridad
			this.sortPatterns();
			/*
			 * Buscamos un patrón aplicable al conjunto de sentencias y lo
			 * aplicamos. Repetimos hasta que no haya ningún patrón aplicable
			 */
			ISqlOptimizationCommand command = null;
			while ((command = applyAnyPattern(aStatements)) != null) {
				command.execute();
			}
		} catch (RuntimeException e) {
			throw new OptimizeException("Error optimizando sentencias SQL:"
					+ e.getLocalizedMessage());
		}
	}

	/**
	 * Busca un patrón aplicable a una lista de sentencias, y en caso de que lo
	 * haya retorna el <code>Command</code> que realiza la optimización
	 * 
	 * @param aStatements
	 *            Lista de sentencias a optimizar
	 * @return Lista de sentencias optimizada
	 */
	protected ISqlOptimizationCommand applyAnyPattern(
			List<IStatement> aStatements) {

		ISqlOptimizationCommand command = null;
		for (ISqlOptimizationPattern pattern : patterns) {
			command = pattern.enable(aStatements);
			if (command != null) {
				break;
			}
		}

		return command;
	}

	/**
	 * Ordena los patrones en función de su prioridad
	 */
	protected void sortPatterns() {

		Collections.sort(patterns, new Comparator<ISqlOptimizationPattern>() {

			@SuppressWarnings("unchecked")
			public int compare(ISqlOptimizationPattern pattern1,
					ISqlOptimizationPattern pattern2) {

				return ComparatorUtils.NATURAL_COMPARATOR.compare(pattern1
						.getPriority(), pattern2.getPriority());
			}
		});
	}
}
