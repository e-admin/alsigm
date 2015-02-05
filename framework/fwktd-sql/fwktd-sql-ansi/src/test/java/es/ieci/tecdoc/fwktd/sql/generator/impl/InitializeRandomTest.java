/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.generator.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import es.ieci.tecdoc.fwktd.sql.generator.SqlGenerator;
import es.ieci.tecdoc.fwktd.sql.node.schema.Table;
import es.ieci.tecdoc.fwktd.sql.node.statement.initialize.random.InitializeRandom;
import es.ieci.tecdoc.fwktd.sql.renderer.ansi.ANSIStatementRenderer;

/**
 * 
 * @author IECISA
 * 
 */
@RunWith(JUnit4.class)
public class InitializeRandomTest {

	protected SqlGenerator sqlGenerator;

	@Before
	public void setUp() {
		sqlGenerator = new SqlGeneratorImpl();
		((SqlGeneratorImpl) sqlGenerator)
				.setRenderer(new ANSIStatementRenderer());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void initializeRandom() {
		InitializeRandom initializeRandom = new InitializeRandom(345);
		initializeRandom.setTable(new Table("A_SCHEMA", "A_TABLE"));

		sqlGenerator.generateSQL(initializeRandom);
	}

}
