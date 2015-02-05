/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.generator.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import es.ieci.tecdoc.fwktd.sql.generator.SqlGenerator;
import es.ieci.tecdoc.fwktd.sql.node.schema.Table;
import es.ieci.tecdoc.fwktd.sql.node.statement.IStatement;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.AllColumns;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.ISelectItem;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.PlainSelect;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.Select;
import es.ieci.tecdoc.fwktd.sql.optimizer.impl.SqlOptimizerImpl;
import es.ieci.tecdoc.fwktd.sql.renderer.ansi.ANSIStatementRenderer;

/**
 * 
 * @author IECISA
 * 
 */
@RunWith(JUnit4.class)
public class SQLGeneratorImplTest {

	@Test(expected = IllegalArgumentException.class)
	public void generateNullStatement() {
		SqlGenerator sqlGenerator = new SqlGeneratorImpl();

		sqlGenerator.generateSQL((IStatement) null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void generateStatementsWithNullValues() {
		SqlGenerator sqlGenerator = new SqlGeneratorImpl();

		Select select = new Select(new PlainSelect());

		List<IStatement> statements = new ArrayList<IStatement>();
		statements.add(select);
		statements.add(null);

		sqlGenerator.generateSQL(statements);

	}

	@Test(expected = NullPointerException.class)
	public void wrongInitialization() {
		SqlGenerator sqlGenerator = new SqlGeneratorImpl();

		Select select = new Select(new PlainSelect());
		sqlGenerator.generateSQL(select);
	}

	@Test
	public void generateSimpleSQL() {
		SqlGenerator sqlGenerator = new SqlGeneratorImpl();
		((SqlGeneratorImpl) sqlGenerator)
				.setRenderer(new ANSIStatementRenderer());

		Select select = new Select(new PlainSelect());
		AllColumns allColumns = new AllColumns();
		List<ISelectItem> items = new ArrayList<ISelectItem>();
		items.add(allColumns);
		((PlainSelect) select.getSelectBody()).setSelectItems(items);
		((PlainSelect) select.getSelectBody()).setTableReference(new Table(
				"table"));

		String sql = sqlGenerator.generateSQL(select);

		Assert.assertNotNull(sql);
		Assert.assertEquals("SELECT * FROM table", sql);
	}

	@Test(expected = IllegalArgumentException.class)
	public void generateSQLWithOptimizationAndWrongInitializationOfOptimizer() {
		SqlGenerator sqlGenerator = new SqlGeneratorImpl();

		Select select = new Select(new PlainSelect());
		AllColumns allColumns = new AllColumns();
		List<ISelectItem> items = new ArrayList<ISelectItem>();
		items.add(allColumns);
		((PlainSelect) select.getSelectBody()).setSelectItems(items);
		((PlainSelect) select.getSelectBody()).setTableReference(new Table(
				"table"));

		List<IStatement> statements = new ArrayList<IStatement>();
		statements.add(select);

		sqlGenerator.generateSQL(statements, Boolean.TRUE);

	}

	@Test
	public void generateSQLWithOptimization() {
		SqlGenerator sqlGenerator = new SqlGeneratorImpl();
		((SqlGeneratorImpl) sqlGenerator)
				.setRenderer(new ANSIStatementRenderer());
		((SqlGeneratorImpl) sqlGenerator).setOptimizer(new SqlOptimizerImpl());

		Select select = new Select(new PlainSelect());
		AllColumns allColumns = new AllColumns();
		List<ISelectItem> items = new ArrayList<ISelectItem>();
		items.add(allColumns);
		((PlainSelect) select.getSelectBody()).setSelectItems(items);
		((PlainSelect) select.getSelectBody()).setTableReference(new Table(
				"table"));

		List<IStatement> statements = new ArrayList<IStatement>();
		statements.add(select);

		String[] sqls = sqlGenerator.generateSQL(statements, Boolean.TRUE);

		Assert.assertNotNull(sqls);
		Assert.assertEquals(1, sqls.length);

	}

	@Test
	public void generateSQLsWithoutOptimization() {
		SqlGenerator sqlGenerator = new SqlGeneratorImpl();
		((SqlGeneratorImpl) sqlGenerator)
				.setRenderer(new ANSIStatementRenderer());

		Select select = new Select(new PlainSelect());
		AllColumns allColumns = new AllColumns();
		List<ISelectItem> items = new ArrayList<ISelectItem>();
		items.add(allColumns);
		((PlainSelect) select.getSelectBody()).setSelectItems(items);
		((PlainSelect) select.getSelectBody()).setTableReference(new Table(
				"table"));

		List<IStatement> statements = new ArrayList<IStatement>();
		statements.add(select);

		String[] sqls = sqlGenerator.generateSQL(statements);

		Assert.assertNotNull(sqls);
		Assert.assertEquals(1, sqls.length);

	}
}
