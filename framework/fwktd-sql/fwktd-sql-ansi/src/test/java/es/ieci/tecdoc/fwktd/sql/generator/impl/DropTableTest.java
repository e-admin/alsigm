package es.ieci.tecdoc.fwktd.sql.generator.impl;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import es.ieci.tecdoc.fwktd.sql.generator.SqlGenerator;
import es.ieci.tecdoc.fwktd.sql.node.schema.Table;
import es.ieci.tecdoc.fwktd.sql.node.statement.drop.Drop;
import es.ieci.tecdoc.fwktd.sql.renderer.ansi.ANSIStatementRenderer;

@RunWith(JUnit4.class)
public class DropTableTest {

	protected SqlGenerator sqlGenerator;

	@Before
	public void setUp() {
		sqlGenerator = new SqlGeneratorImpl();
		((SqlGeneratorImpl) sqlGenerator)
				.setRenderer(new ANSIStatementRenderer());
	}

	@Test
	public void dropTable() {
		Drop drop = new Drop(new Table("A_SCHEMA", "A_TABLE"));

		String sql = sqlGenerator.generateSQL(drop);

		Assert.assertNotNull(sql);
		Assert.assertEquals("DROP TABLE A_SCHEMA.A_TABLE", sql);
	}

	@Test
	public void dropTableWithCascade() {
		Drop drop = new Drop(new Table("A_SCHEMA", "A_TABLE"));
		drop.setParameters(Arrays.asList(new String[] { "CASCADE" }));

		String sql = sqlGenerator.generateSQL(drop);

		Assert.assertNotNull(sql);
		Assert.assertEquals("DROP TABLE A_SCHEMA.A_TABLE CASCADE", sql);
	}

}
