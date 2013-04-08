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
import es.ieci.tecdoc.fwktd.sql.node.schema.Table;
import es.ieci.tecdoc.fwktd.sql.node.statement.truncate.Truncate;
import es.ieci.tecdoc.fwktd.sql.renderer.ansi.ANSIStatementRenderer;

/**
 * 
 * @author IECISA
 * 
 */
@RunWith(JUnit4.class)
public class TruncateTest {

	protected SqlGenerator sqlGenerator;

	@Before
	public void setUp() {
		sqlGenerator = new SqlGeneratorImpl();
		((SqlGeneratorImpl) sqlGenerator)
				.setRenderer(new ANSIStatementRenderer());
	}

	@Test
	public void truncateTable() {
		Truncate truncate = new Truncate(new Table("A_SCHEMA", "A_TABLE"));

		String sql = sqlGenerator.generateSQL(truncate);

		Assert.assertNotNull(sql);
		Assert
				.assertEquals("DELETE FROM A_SCHEMA.A_TABLE",
						sql);
	}
}
