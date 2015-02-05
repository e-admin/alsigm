/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.generator.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import es.ieci.tecdoc.fwktd.sql.generator.SqlGenerator;
import es.ieci.tecdoc.fwktd.sql.node.expression.LongValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.EqualsTo;
import es.ieci.tecdoc.fwktd.sql.node.schema.Column;
import es.ieci.tecdoc.fwktd.sql.node.schema.Table;
import es.ieci.tecdoc.fwktd.sql.node.statement.delete.Delete;
import es.ieci.tecdoc.fwktd.sql.renderer.ansi.ANSIStatementRenderer;

/**
 * 
 * @author IECISA
 * 
 */
@RunWith(JUnit4.class)
public class DeleteTest {

	protected SqlGenerator sqlGenerator;

	@Before
	public void setUp() {
		sqlGenerator = new SqlGeneratorImpl();
		((SqlGeneratorImpl) sqlGenerator)
				.setRenderer(new ANSIStatementRenderer());
	}

	@Test
	public void delete() {
		Delete delete = new Delete();
		delete.setTable(new Table("A_SCHEMA", "A_TABLE"));

		String sql = sqlGenerator.generateSQL(delete);

		Assert.assertNotNull(sql);
		Assert.assertEquals("DELETE FROM A_SCHEMA.A_TABLE", sql);
	}

	@Test
	public void deleteWithFilter() {
		Delete delete = new Delete();
		delete.setTable(new Table("A_SCHEMA", "A_TABLE"));
		delete.setWhere(new EqualsTo(new Column("id"), new LongValue(Long
				.valueOf(15))));

		String sql = sqlGenerator.generateSQL(delete);

		Assert.assertNotNull(sql);
		Assert.assertEquals("DELETE FROM A_SCHEMA.A_TABLE WHERE id = 15", sql);
	}

}
