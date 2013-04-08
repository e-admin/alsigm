package es.ieci.tecdoc.fwktd.sql.generator.impl;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import es.ieci.tecdoc.fwktd.sql.generator.SqlGenerator;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.LongValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.Parenthesis;
import es.ieci.tecdoc.fwktd.sql.node.expression.StringValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.functions.agregate.AvgFunction;
import es.ieci.tecdoc.fwktd.sql.node.expression.functions.agregate.SumFunction;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.conditional.AndExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.conditional.OrExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.EqualsTo;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.ExpressionList;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.GreaterOrEqualsThan;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.GreaterThan;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.InExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.LikeExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.MinorOrEqualsThan;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.MinorThan;
import es.ieci.tecdoc.fwktd.sql.node.schema.Column;
import es.ieci.tecdoc.fwktd.sql.node.schema.Table;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.AllTableColumns;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.IColumnReference;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.ISelectItem;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.ITableReference;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.JoinCondition;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.JoinedTable;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.Limit;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.OrderBy;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.OrderByElement;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.PlainSelect;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.Select;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.SelectExpressionItem;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.SubSelect;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.JoinedTable.JoinType;
import es.ieci.tecdoc.fwktd.sql.renderer.ansi.ANSIStatementRenderer;

@RunWith(JUnit4.class)
public class SelectTest {

	protected SqlGenerator sqlGenerator;

	@Before
	public void setUp() {
		sqlGenerator = new SqlGeneratorImpl();
		((SqlGeneratorImpl) sqlGenerator)
				.setRenderer(new ANSIStatementRenderer());
	}

	@Test
	public void selectSimpleWithAlias() {
		Select select = new Select(new PlainSelect());
		((PlainSelect) select.getSelectBody()).setSelectItems(Arrays
				.asList(new ISelectItem[] { new AllTableColumns(new Table(null,
						null, "Alias")) }));
		((PlainSelect) select.getSelectBody()).setTableReference(new Table(
				"A_SCHEMA", "Table", "Alias"));

		String sql = sqlGenerator.generateSQL(select);

		Assert.assertNotNull(sql);
		Assert.assertEquals("SELECT Alias.* FROM A_SCHEMA.Table AS Alias", sql);
	}

	@Test
	public void selectWithColumnsInFromReferencedWithAlias() {
		Select select = new Select(new PlainSelect());

		Table table = new Table("A_Schema", "Table", "Alias");

		((PlainSelect) select.getSelectBody())
				.setSelectItems(Arrays
						.asList(new ISelectItem[] {
								new SelectExpressionItem(
										new Column(table, "PK"), "id"),
								new SelectExpressionItem(new Column(table,
										"NAME")) }));
		((PlainSelect) select.getSelectBody()).setTableReference(table);

		String sql = sqlGenerator.generateSQL(select);

		Assert.assertNotNull(sql);
		Assert
				.assertEquals(
						"SELECT Alias.PK AS id, Alias.NAME FROM A_Schema.Table AS Alias",
						sql);
	}

	@Test
	public void selectCrossingTwoTablesByPK() {
		Select select = new Select(new PlainSelect());

		Table table1 = new Table("A_SCHEMA", "Table1", "T1");
		Table table2 = new Table("A_SCHEMA", "Table2", "T2");

		((PlainSelect) select.getSelectBody()).setSelectItems(Arrays
				.asList(new ISelectItem[] {
						new SelectExpressionItem(new Column(table1, "PK"),
								"id_t1"),
						new SelectExpressionItem(new Column(table1, "NOMBRE")),
						new SelectExpressionItem(new Column(table2, "PK"),
								"id_t2") }));

		((PlainSelect) select.getSelectBody()).setTableReferences(Arrays
				.asList(new ITableReference[] { table1, table2 }));

		((PlainSelect) select.getSelectBody()).setWhere(new EqualsTo(
				new Column(table2, "PK"), new Column(table1, "PK")));

		String sql = sqlGenerator.generateSQL(select);

		Assert.assertNotNull(sql);
		Assert
				.assertEquals(
						"SELECT T1.PK AS id_t1, T1.NOMBRE, T2.PK AS id_t2 FROM A_SCHEMA.Table1 AS T1, A_SCHEMA.Table2 AS T2 WHERE T2.PK = T1.PK",
						sql);
	}

	@Test
	public void selectCrossingTwoTablesByIdAndConditionWithParenthesis() {

		Select select = new Select(new PlainSelect());

		Table table1 = new Table("A_SCHEMA", "Table1", "Table1");
		Table table2 = new Table("A_SCHEMA", "Table2", "Table2");

		((PlainSelect) select.getSelectBody()).setSelectItems(Arrays
				.asList(new ISelectItem[] {
						new SelectExpressionItem(new Column(table1, "PK"),
								"id_t1"),
						new SelectExpressionItem(new Column(table1, "NOMBRE")),
						new SelectExpressionItem(new Column(table2, "PK"),
								"id_t2") }));

		((PlainSelect) select.getSelectBody()).setTableReferences(Arrays
				.asList(new ITableReference[] { table1, table2 }));

		((PlainSelect) select.getSelectBody()).setWhere(new AndExpression(
				new Parenthesis(new EqualsTo(new Column(table1, "PK"),
						new Column(table2, "PK"))), new Parenthesis(
						new EqualsTo(new Column(table1, "NOMBRE"),
								new StringValue("Pepe")))));

		String sql = sqlGenerator.generateSQL(select);

		Assert.assertNotNull(sql);
		Assert
				.assertEquals(
						"SELECT Table1.PK AS id_t1, Table1.NOMBRE, Table2.PK AS id_t2 FROM A_SCHEMA.Table1 AS Table1, A_SCHEMA.Table2 AS Table2 WHERE (Table1.PK = Table2.PK) AND (Table1.NOMBRE = 'Pepe')",
						sql);
	}

	@Test
	public void selectComplexCrossingTwoTablesByIdWithOptionalConditionAndGroupingAndSumInFrom() {

		Select select = new Select(new PlainSelect());

		Table table1 = new Table(null, "Table1", "Table1");
		Table table2 = new Table(null, "Table2", "Table2");

		((PlainSelect) select.getSelectBody()).setSelectItems(Arrays
				.asList(new ISelectItem[] {
						new SelectExpressionItem(new Column(table1, "PK"),
								"id_t1"),
						new SelectExpressionItem(new Column(table1, "field1")),
						new SelectExpressionItem(new Column(table1, "field2")),
						new SelectExpressionItem(new Column(table2, "PK"),
								"id_t2"),
						new SelectExpressionItem(new SumFunction(new Column(
								table2, "field1")), "SUM_FIELD1") }));

		((PlainSelect) select.getSelectBody()).setTableReferences(Arrays
				.asList(new ITableReference[] { table1, table2 }));

		((PlainSelect) select.getSelectBody()).setWhere(new Parenthesis(
				new OrExpression(new Parenthesis(new AndExpression(
						new Parenthesis(new EqualsTo(new Column(table2, "PK"),
								new Column(table1, "PK"))), new Parenthesis(
								new EqualsTo(new Column(table1, "field1"),
										new StringValue("Pepe"))))),
						new Parenthesis(new GreaterThan(
								new Column("SUM_FIELD1"), new LongValue(Long
										.valueOf(20000)))))));

		((PlainSelect) select.getSelectBody())
				.setGroupByColumnReferences(Arrays
						.asList(new IColumnReference[] { new Column(table1,
								"field2") }));

		String sql = sqlGenerator.generateSQL(select);

		Assert.assertNotNull(sql);
		Assert
				.assertEquals(
						"SELECT Table1.PK AS id_t1, Table1.field1, Table1.field2, Table2.PK AS id_t2, SUM(Table2.field1) AS SUM_FIELD1 FROM Table1 AS Table1, Table2 AS Table2 WHERE (((Table2.PK = Table1.PK) AND (Table1.field1 = 'Pepe')) OR (SUM_FIELD1 > 20000)) GROUP BY Table1.field2",
						sql);

	}

	@Test
	public void selectComplexCrossingTwoTablesByIdWithOptionalConditionAndGroupingAndHavingAndSumInFrom() {

		Select select = new Select(new PlainSelect());

		Table table1 = new Table("A_SCHEMA", "Table1", "Table1");
		Table table2 = new Table("A_SCHEMA", "Table2", "Table2");

		((PlainSelect) select.getSelectBody()).setSelectItems(Arrays
				.asList(new ISelectItem[] {
						new SelectExpressionItem(new Column(table1, "PK"),
								"id_t1"),
						new SelectExpressionItem(new Column(table1, "field1")),
						new SelectExpressionItem(new Column(table1, "field2")),
						new SelectExpressionItem(new Column(table2, "PK"),
								"id_t2"),
						new SelectExpressionItem(new SumFunction(new Column(
								table2, "field3")), "SUM_FIELD3") }));

		((PlainSelect) select.getSelectBody()).setTableReferences(Arrays
				.asList(new ITableReference[] { table1, table2 }));

		// (((Table2.PK = Table1.PK) AND (Table1.field1 = 'Pepe')) OR
		// (SUM_FIELD3 > '20000'))

		((PlainSelect) select.getSelectBody()).setWhere(new Parenthesis(
				new OrExpression(new Parenthesis(new AndExpression(
						new Parenthesis(new EqualsTo(new Column(table2, "PK"),
								new Column(table1, "PK"))), new Parenthesis(
								new EqualsTo(new Column(table1, "field1"),
										new StringValue("Pepe"))))),
						new Parenthesis(new GreaterThan(
								new Column("SUM_FIELD3"), new StringValue(
										"20000"))))));

		((PlainSelect) select.getSelectBody())
				.setGroupByColumnReferences(Arrays
						.asList(new IColumnReference[] { new Column(table1,
								"field2") }));
		((PlainSelect) select.getSelectBody())
				.setHaving(new GreaterThan(
						new AvgFunction(new Column("field3")), new StringValue(
								"5000")));

		String sql = sqlGenerator.generateSQL(select);

		Assert.assertNotNull(sql);
		Assert
				.assertEquals(
						"SELECT Table1.PK AS id_t1, Table1.field1, Table1.field2, Table2.PK AS id_t2, SUM(Table2.field3) AS SUM_FIELD3 FROM A_SCHEMA.Table1 AS Table1, A_SCHEMA.Table2 AS Table2 WHERE (((Table2.PK = Table1.PK) AND (Table1.field1 = 'Pepe')) OR (SUM_FIELD3 > '20000')) GROUP BY Table1.field2 HAVING AVG(field3) > '5000'",
						sql);

	}

	@Test
	public void selectWithInSelectInsideWhere() {

		Select select = new Select(new PlainSelect());
		Table table = new Table("A_SCHEMA", "Table", "Table");
		((PlainSelect) select.getSelectBody()).setTableReference(table);
		((PlainSelect) select.getSelectBody()).setSelectItems(Arrays
				.asList(new ISelectItem[] { new AllTableColumns(table) }));

		SubSelect inSelect = new SubSelect(new PlainSelect());
		Table inTable = new Table("A_SCHEMA", "Table2", "Table2");
		((PlainSelect) inSelect.getSelectBody()).setTableReference(inTable);
		((PlainSelect) inSelect.getSelectBody()).setSelectItems(Arrays
				.asList(new ISelectItem[] { new AllTableColumns(inTable) }));

		((PlainSelect) select.getSelectBody()).setWhere(new InExpression(
				new Column(table, "PK"), inSelect));

		String sql = sqlGenerator.generateSQL(select);

		Assert.assertNotNull(sql);
		Assert
				.assertEquals(
						"SELECT Table.* FROM A_SCHEMA.Table AS Table WHERE Table.PK IN (SELECT Table2.* FROM A_SCHEMA.Table2 AS Table2)",
						sql);
	}

	@Test
	public void selectWithInValuesListInsideWhere() {

		Select select = new Select(new PlainSelect());
		Table table = new Table("A_SCHEMA", "Table", "Alias");
		((PlainSelect) select.getSelectBody()).setTableReference(table);
		((PlainSelect) select.getSelectBody()).setSelectItems(Arrays
				.asList(new ISelectItem[] { new AllTableColumns(table) }));

		((PlainSelect) select.getSelectBody()).setWhere(new InExpression(
				new Column(table, "PK"), new ExpressionList(Arrays
						.asList(new IExpression[] {
								new LongValue(Long.valueOf(1)),
								new LongValue(Long.valueOf(2)),
								new LongValue(Long.valueOf(3)) }))));

		String sql = sqlGenerator.generateSQL(select);

		Assert.assertNotNull(sql);
		Assert
				.assertEquals(
						"SELECT Alias.* FROM A_SCHEMA.Table AS Alias WHERE Alias.PK IN (1, 2, 3)",
						sql);
	}

	@Test
	public void selectWithRightJoin() {

		Select select = new Select(new PlainSelect());
		Table table1 = new Table("A_SCHEMA", "Table1", "Table1");
		Table table2 = new Table("A_SCHEMA", "Table2", "Table2");

		JoinedTable joinedTable = new JoinedTable(table1, table2);
		joinedTable.setJoinSpecification(new JoinCondition(new EqualsTo(
				new Column(table1, "PK"), new Column(table2, "PK"))));
		joinedTable.setJoinType(JoinType.right);

		((PlainSelect) select.getSelectBody()).setSelectItems(Arrays
				.asList(new ISelectItem[] { new SelectExpressionItem(
						new Column(table1, "PK"), "id_t1") }));
		((PlainSelect) select.getSelectBody()).setTableReferences(Arrays
				.asList(new ITableReference[] { joinedTable }));

		String sql = sqlGenerator.generateSQL(select);

		Assert.assertNotNull(sql);
		Assert
				.assertEquals(
						"SELECT Table1.PK AS id_t1 FROM A_SCHEMA.Table1 AS Table1 RIGHT JOIN A_SCHEMA.Table2 AS Table2 ON Table1.PK = Table2.PK",
						sql);
	}

	@Test
	public void selectWithConstants() {

		Select select = new Select(new PlainSelect());
		Table table = new Table("A_SCHEMA", "Table", "Alias");

		((PlainSelect) select.getSelectBody()).setSelectItems(Arrays
				.asList(new ISelectItem[] {
						new AllTableColumns(table),
						new SelectExpressionItem(new StringValue("constant1")),
						new SelectExpressionItem(new StringValue("constant2"),
								"alias2") }));
		((PlainSelect) select.getSelectBody()).setTableReference(table);

		String sql = sqlGenerator.generateSQL(select);

		Assert.assertNotNull(sql);
		Assert
				.assertEquals(
						"SELECT Alias.*, 'constant1', 'constant2' AS alias2 FROM A_SCHEMA.Table AS Alias",
						sql);
	}

	@Test
	public void selectWithOrderByAscAndDesc() {

		Select select = new Select(new PlainSelect());
		Table table = new Table("A_SCHEMA", "Table", "Alias");

		((PlainSelect) select.getSelectBody()).setSelectItems(Arrays
				.asList(new ISelectItem[] { new AllTableColumns(table) }));
		((PlainSelect) select.getSelectBody()).setTableReference(table);
		((PlainSelect) select.getSelectBody()).setOrderBy(new OrderBy(
				Arrays
						.asList(new OrderByElement[] {
								new OrderByElement(new Column(table, "field1"),
										true),
								new OrderByElement(new Column(table, "field2"),
										false) })));

		String sql = sqlGenerator.generateSQL(select);

		Assert.assertNotNull(sql);
		Assert
				.assertEquals(
						"SELECT Alias.* FROM A_SCHEMA.Table AS Alias ORDER BY Alias.field1 ASC, Alias.field2 DESC",
						sql);

	}

	@Test(expected = UnsupportedOperationException.class)
	public void selectWithRandomOrderBy() {

		SubSelect subSelect = new SubSelect(new PlainSelect());
		((PlainSelect) subSelect.getSelectBody()).setSelectItems(Arrays
				.asList(new ISelectItem[] { new SelectExpressionItem(
						new Column("id")) }));
		((PlainSelect) subSelect.getSelectBody()).setTableReference(new Table(
				"A_SCHEMA", "Table", "Alias"));
		((PlainSelect) subSelect.getSelectBody()).setOrderBy(new OrderBy(true));

		Select select = new Select(new PlainSelect());
		((PlainSelect) select.getSelectBody()).setSelectItems(Arrays
				.asList(new ISelectItem[] { new SelectExpressionItem(
						new Column("id")) }));
		((PlainSelect) select.getSelectBody()).setTableReference(subSelect);

		sqlGenerator.generateSQL(select);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void selectWithLimit() {

		Select select = new Select(new PlainSelect());
		((PlainSelect) select.getSelectBody()).setSelectItems(Arrays
				.asList(new ISelectItem[] { new SelectExpressionItem(
						new Column("id")) }));
		((PlainSelect) select.getSelectBody()).setTableReference(new Table(
				"A_SCHEMA", "Table", "Alias"));
		((PlainSelect) select.getSelectBody()).setLimit(new Limit(50));

		sqlGenerator.generateSQL(select);
	}

	@Test
	public void selectWithLike() {

		Select select = new Select(new PlainSelect());
		((PlainSelect) select.getSelectBody()).setSelectItems(Arrays
				.asList(new ISelectItem[] { new SelectExpressionItem(
						new Column("id")) }));
		((PlainSelect) select.getSelectBody()).setTableReference(new Table(
				"A_SCHEMA", "Table", "Alias"));
		((PlainSelect) select.getSelectBody()).setWhere(new LikeExpression(
				new Column("name"), new StringValue("Juan")));

		String sql = sqlGenerator.generateSQL(select);

		Assert.assertNotNull(sql);
		Assert
				.assertEquals(
						"SELECT id FROM A_SCHEMA.Table AS Alias WHERE name LIKE 'Juan'",
						sql);
	}

	@Test
	public void selectWithNotLike() {

		Select select = new Select(new PlainSelect());
		((PlainSelect) select.getSelectBody()).setSelectItems(Arrays
				.asList(new ISelectItem[] { new SelectExpressionItem(
						new Column("id")) }));
		((PlainSelect) select.getSelectBody()).setTableReference(new Table(
				"A_SCHEMA", "Table", "Alias"));

		LikeExpression like = new LikeExpression(new Column("name"),
				new StringValue("Juan"));
		like.setNot(true);

		((PlainSelect) select.getSelectBody()).setWhere(like);

		String sql = sqlGenerator.generateSQL(select);

		Assert.assertNotNull(sql);
		Assert
				.assertEquals(
						"SELECT id FROM A_SCHEMA.Table AS Alias WHERE name NOT LIKE 'Juan'",
						sql);
	}

	@Test
	public void selectWithGreaterThan() {

		Select select = new Select(new PlainSelect());
		((PlainSelect) select.getSelectBody()).setSelectItems(Arrays
				.asList(new ISelectItem[] { new SelectExpressionItem(
						new Column("id")) }));
		((PlainSelect) select.getSelectBody()).setTableReference(new Table(
				"A_SCHEMA", "Table", "Alias"));

		GreaterThan greater = new GreaterThan(new Column("oneColumn"),
				new Column("anotherColumn"));

		((PlainSelect) select.getSelectBody()).setWhere(greater);

		String sql = sqlGenerator.generateSQL(select);

		Assert.assertNotNull(sql);
		Assert
				.assertEquals(
						"SELECT id FROM A_SCHEMA.Table AS Alias WHERE oneColumn > anotherColumn",
						sql);

	}

	@Test
	public void selectWithGreaterOrEqualsThan() {

		Select select = new Select(new PlainSelect());
		((PlainSelect) select.getSelectBody()).setSelectItems(Arrays
				.asList(new ISelectItem[] { new SelectExpressionItem(
						new Column("id")) }));
		((PlainSelect) select.getSelectBody()).setTableReference(new Table(
				"A_SCHEMA", "Table", "Alias"));

		GreaterOrEqualsThan greaterOrEquals = new GreaterOrEqualsThan(
				new Column("oneColumn"), new Column("anotherColumn"));

		((PlainSelect) select.getSelectBody()).setWhere(greaterOrEquals);

		String sql = sqlGenerator.generateSQL(select);

		Assert.assertNotNull(sql);
		Assert
				.assertEquals(
						"SELECT id FROM A_SCHEMA.Table AS Alias WHERE oneColumn >= anotherColumn",
						sql);

	}

	@Test
	public void selectWithMinorThan() {

		Select select = new Select(new PlainSelect());
		((PlainSelect) select.getSelectBody()).setSelectItems(Arrays
				.asList(new ISelectItem[] { new SelectExpressionItem(
						new Column("id")) }));
		((PlainSelect) select.getSelectBody()).setTableReference(new Table(
				"A_SCHEMA", "Table", "Alias"));

		MinorThan minor = new MinorThan(new Column("oneColumn"), new Column(
				"anotherColumn"));

		((PlainSelect) select.getSelectBody()).setWhere(minor);

		String sql = sqlGenerator.generateSQL(select);

		Assert.assertNotNull(sql);
		Assert
				.assertEquals(
						"SELECT id FROM A_SCHEMA.Table AS Alias WHERE oneColumn < anotherColumn",
						sql);
	}

	@Test
	public void selectWithMinorOrEqualsThan() {

		Select select = new Select(new PlainSelect());
		((PlainSelect) select.getSelectBody()).setSelectItems(Arrays
				.asList(new ISelectItem[] { new SelectExpressionItem(
						new Column("id")) }));
		((PlainSelect) select.getSelectBody()).setTableReference(new Table(
				"A_SCHEMA", "Table", "Alias"));

		MinorOrEqualsThan minorOrEquals = new MinorOrEqualsThan(new Column(
				"oneColumn"), new Column("anotherColumn"));

		((PlainSelect) select.getSelectBody()).setWhere(minorOrEquals);

		String sql = sqlGenerator.generateSQL(select);

		Assert.assertNotNull(sql);
		Assert
				.assertEquals(
						"SELECT id FROM A_SCHEMA.Table AS Alias WHERE oneColumn <= anotherColumn",
						sql);
	}
}