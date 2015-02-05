/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.generator.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.sql.generator.SqlGenerator;
import es.ieci.tecdoc.fwktd.sql.node.statement.IStatement;
import es.ieci.tecdoc.fwktd.sql.node.statement.Statement;
import es.ieci.tecdoc.fwktd.sql.optimizer.SqlOptimizer;
import es.ieci.tecdoc.fwktd.sql.renderer.StatementRenderer;

/**
 * Generador de consultas SQL
 */
public class SqlGeneratorImpl implements SqlGenerator {

	/**
	 * {@inheritDoc}
	 */
	public String generateSQL(IStatement aStatement) {
		Assert.notNull(aStatement);

		aStatement.accept(getRenderer());

		return ((Statement) aStatement).getSqlString();
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] generateSQL(List<IStatement> aStatements) {

		Assert.notEmpty(aStatements);
		Assert.noNullElements(aStatements.toArray());

		return this.generateSQL(aStatements, Boolean.FALSE);
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] generateSQL(List<IStatement> aStatements,
			Boolean aDoOptimize) {

		Assert.notEmpty(aStatements);
		Assert.noNullElements(aStatements.toArray());

		if (aDoOptimize.booleanValue()) {
			Assert.notNull(optimizer);

			aStatements = optimizer.optimize(aStatements);
		}

		List<String> queries = new ArrayList<String>();
		for (IStatement statement : aStatements) {
			queries.add(generateSQL(statement));
		}

		return queries.toArray(new String[] {});
	}

	/**
	 * {@inheritDoc}
	 */
	public StatementRenderer getRenderer() {
		return renderer;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setRenderer(StatementRenderer aRenderer) {
		this.renderer = aRenderer;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setOptimizer(SqlOptimizer anOptimizer) {
		this.optimizer = anOptimizer;
	}

	// Members
	/** Logger de la clase */
	protected static final Logger logger = LoggerFactory
			.getLogger(SqlGeneratorImpl.class);

	/** Renderer para traducción a SQL */
	protected StatementRenderer renderer;

	/** Optimizador de listas de sentencias */
	protected SqlOptimizer optimizer;
}
