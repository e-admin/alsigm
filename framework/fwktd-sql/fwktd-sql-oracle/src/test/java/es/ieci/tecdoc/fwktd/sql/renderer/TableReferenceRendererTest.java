/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.renderer;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.EqualsTo;
import es.ieci.tecdoc.fwktd.sql.node.schema.Column;
import es.ieci.tecdoc.fwktd.sql.node.schema.Table;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.JoinCondition;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.JoinedTable;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.NamedColumnsJoin;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.JoinedTable.JoinType;

/**
 * Prueba unitaria para el Renderer ANSI.
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class TableReferenceRendererTest {

	private StatementRenderer statementRenderer;

	private ExpressionRenderer expressionRenderer;

	private TableReferenceRenderer tableReferenceRenderer;

	private ItemsListRenderer itemsListRenderer;

	private SelectBodyRenderer selectBodyRenderer;

	@Before
	public void setUp() throws Exception {

		// Damos de alta todos los visitor y los relacionamos

		statementRenderer = new OracleStatementRenderer();
		expressionRenderer = new OracleExpressionRenderer();
		tableReferenceRenderer = new OracleTableReferenceRenderer();
		itemsListRenderer = new OracleItemsListRenderer();
		selectBodyRenderer = new OracleSelectBodyRenderer();

		statementRenderer.setExpressionRenderer(expressionRenderer);
		statementRenderer.setItemListRenderer(itemsListRenderer);
		statementRenderer.setSelectBodyRenderer(selectBodyRenderer);
		statementRenderer.setTableReferenceRenderer(tableReferenceRenderer);

		expressionRenderer.setSelectBodyRenderer(selectBodyRenderer);
		expressionRenderer.setTableReferenceRenderer(tableReferenceRenderer);

		tableReferenceRenderer.setSelectBodyRenderer(selectBodyRenderer);
		tableReferenceRenderer.setExpressionRenderer(expressionRenderer);

		selectBodyRenderer.setExpressionRenderer(expressionRenderer);
		selectBodyRenderer.setTableReferenceRenderer(tableReferenceRenderer);

		itemsListRenderer.setExpressionRenderer(expressionRenderer);
		itemsListRenderer.setSelectBodyRenderer(selectBodyRenderer);
	}

	@Test
	public void testVisitTable() {

		Table table = new Table();
		table.setName("anyGivenTable");
		table.setSchemaName("anyGivenSchema");
		table.setAlias("anyGivenAlias");

		tableReferenceRenderer.visit(table);

		assertNotNull(table.getSqlString());
	}

	@Test
	public void testVisitJoinedTableWithJoinCondition() {

		Table leftTable = new Table();
		leftTable.setName("anyGivenTable");
		leftTable.setSchemaName("anyGivenSchema");
		leftTable.setAlias("anyGivenAlias");

		Table rightTable = new Table();
		rightTable.setName("anotherTable");
		rightTable.setSchemaName("anotherSchema");
		rightTable.setAlias("anotherAlias");

		JoinedTable joinedTable = new JoinedTable(leftTable, rightTable);

		joinedTable.setJoinType(JoinType.left);

		Table leftTableAlias = new Table();
		leftTableAlias.setName("anyGivenAlias");

		joinedTable
				.setJoinSpecification(new JoinCondition(new EqualsTo(
						new Column(leftTableAlias, "pk"), new Column(
								rightTable, "pk"))));

		tableReferenceRenderer.visit(joinedTable);

		assertNotNull(joinedTable.getSqlString());
	}

	@Test
	public void testVisitJoinedTableWithNamedColumnsJoin() {

		Table leftTable = new Table();
		leftTable.setName("anyGivenTable");
		leftTable.setSchemaName("anyGivenSchema");
		leftTable.setAlias("anyGivenAlias");

		Table rightTable = new Table();
		rightTable.setName("anotherTable");
		rightTable.setSchemaName("anotherSchema");
		rightTable.setAlias("anotherAlias");

		JoinedTable joinedTable = new JoinedTable(leftTable, rightTable);

		joinedTable.setJoinType(JoinType.left);

		List<Column> columns = new ArrayList<Column>();
		columns.add(new Column(leftTable, "anyGivenColumn"));
		columns.add(new Column(rightTable, "anotherColumn"));

		NamedColumnsJoin namedColumnsJoin = new NamedColumnsJoin(columns);
		joinedTable.setJoinSpecification(namedColumnsJoin);

		tableReferenceRenderer.visit(joinedTable);

		assertNotNull(joinedTable.getSqlString());
	}
}
