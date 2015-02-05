/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.generator.impl;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import es.ieci.tecdoc.fwktd.sql.generator.SqlGenerator;
import es.ieci.tecdoc.fwktd.sql.node.expression.LongValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.StringValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.EqualsTo;
import es.ieci.tecdoc.fwktd.sql.node.schema.Column;
import es.ieci.tecdoc.fwktd.sql.node.schema.Table;
import es.ieci.tecdoc.fwktd.sql.node.statement.update.SetClause;
import es.ieci.tecdoc.fwktd.sql.node.statement.update.Update;
import es.ieci.tecdoc.fwktd.sql.node.statement.update.SetClause.SetClauseValueType;
import es.ieci.tecdoc.fwktd.sql.renderer.ansi.ANSIStatementRenderer;

/**
 * 
 * @author IECISA
 * 
 */
@RunWith(JUnit4.class)
public class UpdateTest {

	protected SqlGenerator sqlGenerator;

	@Before
	public void setUp() {
		sqlGenerator = new SqlGeneratorImpl();
		((SqlGeneratorImpl) sqlGenerator)
				.setRenderer(new ANSIStatementRenderer());
	}

	@Test
	public void update() {
		Update update = new Update();
		Table table = new Table("A_SCHEMA", "A_TABLE");
		update.setTable(table);

		update.setSetClauses(Arrays.asList(new SetClause[] { new SetClause(
				new Column(table, "column"), SetClauseValueType.expression,
				new StringValue("foo")) }));

		String sql = sqlGenerator.generateSQL(update);

		Assert.assertNotNull(sql);
		Assert.assertEquals("UPDATE A_SCHEMA.A_TABLE SET column='foo'", sql);
	}

	@Test
	public void updateWithWhere() {
		Update update = new Update();
		Table table = new Table("A_SCHEMA", "A_TABLE");
		update.setTable(new Table("A_SCHEMA", "A_TABLE"));

		update.setSetClauses(Arrays.asList(new SetClause[] { new SetClause(
				new Column(table, "column"), SetClauseValueType.expression,
				new StringValue("foo")) }));
		update.setWhere(new EqualsTo(new Column("id"), new LongValue("1")));

		String sql = sqlGenerator.generateSQL(update);

		Assert.assertNotNull(sql);
		Assert.assertEquals("UPDATE A_SCHEMA.A_TABLE SET column='foo' WHERE id = 1", sql);
	}

}
