package es.ieci.tecdoc.fwktd.sql.optimizer.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.sql.node.statement.IStatement;
import es.ieci.tecdoc.fwktd.sql.node.statement.Statement;
import es.ieci.tecdoc.fwktd.sql.optimizer.SqlOptimizer;
import es.ieci.tecdoc.fwktd.sql.optimizer.exception.OptimizeException;
import es.ieci.tecdoc.fwktd.sql.optimizer.impl.pattern.ISqlOptimizationPattern;

/**
 * Clase Abstracta para el optimizador de sentencias <code>IStatement</code>
 * 
 */
public abstract class AbstractSqlOptimizer implements SqlOptimizer {

	/**
	 * Logger de la clase
	 */
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Lista de Patrones
	 */
	protected List<ISqlOptimizationPattern> patterns = new ArrayList<ISqlOptimizationPattern>();

	public List<IStatement> optimize(List<IStatement> aStatements) {
		List<IStatement> statementsOptimized = new ArrayList<IStatement>();

		for (IStatement statement : aStatements) {
			try {
				statementsOptimized.add((IStatement) ((Statement) statement)
						.clone());
			} catch (CloneNotSupportedException e) {
				logger
						.error("No es posible clonar la lista de Statements : (clone ignorado, se trabajará con la lista original)"
								+ e.getMessage());
				statementsOptimized = aStatements;
			}
		}
		Boolean errors = Boolean.FALSE;
		doPreOptimize(statementsOptimized);
		try {
			doOptimize(statementsOptimized);
		} catch (OptimizeException e) {
			errors = doCatchException(aStatements, statementsOptimized, e);
		} finally {
			doFinally(statementsOptimized);
		}
		if (errors) {
			return aStatements;
		} else {
			return statementsOptimized;
		}
	}

	/**
	 * Método abstracto, extender para implementar el cuerpo del optimizador
	 * 
	 * @param aStatements
	 *            lista de <code>IStatements</code> a optimizar
	 */
	protected abstract void doOptimize(List<IStatement> aStatements);

	/**
	 * Antes del proceso de optimización
	 * 
	 * @param aStatements
	 *            lista de <code>IStatements</code> a optimizar
	 */
	protected void doPreOptimize(List<IStatement> aStatements) {
		debugStatements(aStatements);
	}

	/**
	 * Tratamiento de excepciones durante el proceso de optimización
	 * 
	 * @param aStatements
	 *            lista de <code>IStatements</code> a optimizar
	 * @param aStatementsOptimized
	 *            lista de <code>IStatements</code> bajo optimización
	 * @param anOptimizeException
	 *            Excepción de optimización
	 */
	protected Boolean doCatchException(List<IStatement> aStatements,
			List<IStatement> aStatementsOptimized,
			OptimizeException anOptimizeException) {
		logger.error("Excepción optimizando, optimización ignorada : {}",
				anOptimizeException.getMessage(), anOptimizeException);
		
		return Boolean.TRUE;
	}

	/**
	 * Tras el proceso de optimización
	 * 
	 * @param aStatements
	 *            lista de <code>IStatements</code> a optimizar
	 */
	protected void doFinally(List<IStatement> aStatements) {
		debugStatements(aStatements);
	}

	/**
	 * Debug del proceso
	 * 
	 * @param aStatements
	 *            lista de <code>IStatements</code> a optimizar
	 */
	protected void debugStatements(List<IStatement> aStatements) {
		logger.debug("Lista de Statements: {}", aStatements);
	}

	public void setPatterns(List<ISqlOptimizationPattern> aPatterns) {
		this.patterns = aPatterns;
	}
}
