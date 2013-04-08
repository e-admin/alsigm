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
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.ExpressionList;
import es.ieci.tecdoc.fwktd.sql.node.schema.Column;
import es.ieci.tecdoc.fwktd.sql.node.schema.Table;
import es.ieci.tecdoc.fwktd.sql.node.statement.insert.Insert;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.AllTableColumns;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.ISelectItem;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.PlainSelect;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.SubSelect;
import es.ieci.tecdoc.fwktd.sql.renderer.ansi.ANSIStatementRenderer;

@RunWith(JUnit4.class)
public class InsertTest {

	protected SqlGenerator sqlGenerator;

	@Before
	public void setUp() {
		sqlGenerator = new SqlGeneratorImpl();
		((SqlGeneratorImpl) sqlGenerator)
				.setRenderer(new ANSIStatementRenderer());
	}

	@Test
	public void insertOneValue() {
		Insert insert = new Insert();
		insert.setTable(new Table("A_SCHEMA", "A_TABLE"));
		ExpressionList values = new ExpressionList();
		values.addExpression(new LongValue(new Long(1)));
		insert.setValuesList(values);

		String sql = sqlGenerator.generateSQL(insert);

		Assert.assertNotNull(sql);
		Assert.assertEquals("INSERT INTO A_SCHEMA.A_TABLE VALUES (1)", sql);
	}

	@Test
	public void insertSeveralValues() {
		Insert insert = new Insert();
		insert.setTable(new Table("A_SCHEMA", "A_TABLE"));
		ExpressionList values = new ExpressionList();
		values.addExpression(new LongValue(new Long(1)));
		values.addExpression(new StringValue("String"));
		values.addExpression(new LongValue(new Long(2)));
		insert.setValuesList(values);

		String sql = sqlGenerator.generateSQL(insert);

		Assert.assertNotNull(sql);
		Assert.assertEquals(
				"INSERT INTO A_SCHEMA.A_TABLE VALUES (1, 'String', 2)", sql);
	}

	@Test
	public void insertWithSelect() {
		Insert insert = new Insert();
		insert.setTable(new Table("A_SCHEMA", "A_TABLE"));
		insert.setUseValues(false);

		SubSelect select = new SubSelect(new PlainSelect());
		Table selectTable = new Table("A_SCHEMA", "Table", "Alias");
		((PlainSelect) select.getSelectBody())
				.setSelectItems(Arrays
						.asList(new ISelectItem[] { new AllTableColumns(
								selectTable) }));
		((PlainSelect) select.getSelectBody()).setTableReference(selectTable);

		ExpressionList values = new ExpressionList();
		values.addExpression(select);
		insert.setValuesList(values);

		String sql = sqlGenerator.generateSQL(insert);

		Assert.assertNotNull(sql);
		Assert
				.assertEquals(
						"INSERT INTO A_SCHEMA.A_TABLE(SELECT Alias.* FROM A_SCHEMA.Table AS Alias)",
						sql);
	}

	@Test
	public void insertWithSelectAndTableColumns() {
		Insert insert = new Insert();
		insert.setTable(new Table("A_SCHEMA", "A_TABLE"));
		insert.setUseValues(false);

		insert.setColumns(Arrays.asList(new Column[] { new Column("id") }));

		PlainSelect select = new PlainSelect();
		Table selectTable = new Table("A_SCHEMA", "Table", "Alias");
		select
				.setSelectItems(Arrays
						.asList(new ISelectItem[] { new AllTableColumns(
								selectTable) }));
		select.setTableReference(selectTable);

		ExpressionList values = new ExpressionList();
		values.addExpression(new SubSelect(select));
		insert.setValuesList(values);

		String sql = sqlGenerator.generateSQL(insert);

		Assert.assertNotNull(sql);
		Assert
				.assertEquals(
						"INSERT INTO A_SCHEMA.A_TABLE(id)(SELECT Alias.* FROM A_SCHEMA.Table AS Alias)",
						sql);
	}

	@Test
	public void insertWithColumnsAndValues() {
		Insert insert = new Insert();
		insert.setTable(new Table("A_SCHEMA", "A_TABLE"));

		insert.setColumns(Arrays.asList(new Column[] { new Column("col1"),
				new Column("col2"), new Column("col3") }));
		insert.setUseValues(true);

		ExpressionList values = new ExpressionList();
		values.addExpression(new LongValue("1"));
		values.addExpression(new StringValue("String"));
		values.addExpression(new LongValue("2"));
		insert.setValuesList(values);

		String sql = sqlGenerator.generateSQL(insert);

		Assert.assertNotNull(sql);
		Assert
				.assertEquals(
						"INSERT INTO A_SCHEMA.A_TABLE(col1, col2, col3) VALUES (1, 'String', 2)",
						sql);
	}
}
