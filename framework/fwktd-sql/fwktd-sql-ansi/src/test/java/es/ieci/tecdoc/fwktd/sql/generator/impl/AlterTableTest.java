package es.ieci.tecdoc.fwktd.sql.generator.impl;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import es.ieci.tecdoc.fwktd.sql.generator.SqlGenerator;
import es.ieci.tecdoc.fwktd.sql.node.schema.Table;
import es.ieci.tecdoc.fwktd.sql.node.statement.alter.table.AlterTable;
import es.ieci.tecdoc.fwktd.sql.renderer.ansi.ANSIStatementRenderer;

@RunWith(JUnit4.class)
public class AlterTableTest {

	protected SqlGenerator sqlGenerator;

	@Before
	public void setUp() {
		sqlGenerator = new SqlGeneratorImpl();
		((SqlGeneratorImpl) sqlGenerator)
				.setRenderer(new ANSIStatementRenderer());
	}

	@Test
	public void alterTableEnablingPK() {
		AlterTable alterTable = new AlterTable(
				new Table("A_SCHEMA", "A_TABLE"), true);

		String sql = sqlGenerator.generateSQL(alterTable);

		Assert.assertNotNull(sql);
		Assert.assertEquals(
				"ALTER TABLE A_SCHEMA.A_TABLE ENABLE PRIMARY KEY", sql);
	}
	
	@Test
	public void alterTableDisablingPK() {
		AlterTable alterTable = new AlterTable(
				new Table("A_SCHEMA", "A_TABLE"), false);

		String sql = sqlGenerator.generateSQL(alterTable);

		Assert.assertNotNull(sql);
		Assert.assertEquals(
				"ALTER TABLE A_SCHEMA.A_TABLE DISABLE PRIMARY KEY", sql);
	}
}
