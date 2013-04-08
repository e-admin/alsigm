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

import es.ieci.tecdoc.fwktd.sql.node.expression.DoubleValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.MinorThan;
import es.ieci.tecdoc.fwktd.sql.node.schema.Column;
import es.ieci.tecdoc.fwktd.sql.node.schema.Table;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.AllColumns;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.ISelectBody;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.ISelectItem;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.ITableReference;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.Limit;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.OrderBy;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.OrderByElement;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.PlainSelect;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.SelectExpressionItem;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.Union;

/**
 * Prueba unitaria para el Renderer ANSI
 * 
 * @author IECISA
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class SelectBodyRendererTest {

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
	public void testPlainSelect() {

		PlainSelect plainSelect = new PlainSelect();

		Table table = new Table();
		table.setName("anyGivenTable");
		table.setSchemaName("anyGivenSchema");
		table.setAlias("anyGivenAlias");

		List<ISelectItem> selectItems = new ArrayList<ISelectItem>();
		selectItems.add(new SelectExpressionItem(new Column(table,
				"anyGivenColumn")));
		plainSelect.setSelectItems(selectItems);

		List<ITableReference> tableReferences = new ArrayList<ITableReference>();
		tableReferences.add(table);
		plainSelect.setTableReferences(tableReferences);

		plainSelect.setWhere(new MinorThan(new Column(table, "age"),
				new DoubleValue(50.0)));

		selectBodyRenderer.visit(plainSelect);

		assertNotNull(plainSelect.getSqlString());
	}

	@Test
	public void testPlainSelectWithOrderByAndLimit() {

		PlainSelect plainSelect = new PlainSelect();

		Table table = new Table();
		table.setName("anyGivenTable");
		table.setSchemaName("anyGivenSchema");
		table.setAlias("anyGivenAlias");

		List<ISelectItem> selectItems = new ArrayList<ISelectItem>();
		selectItems.add(new AllColumns());
		plainSelect.setSelectItems(selectItems);

		List<ITableReference> tableReferences = new ArrayList<ITableReference>();
		tableReferences.add(table);
		plainSelect.setTableReferences(tableReferences);

		// Parte orderBy
		List<OrderByElement> orderByElements = new ArrayList<OrderByElement>();
		orderByElements.add(new OrderByElement(new Column(table,
				"anyGivenColumn"), true));
		orderByElements.add(new OrderByElement(new Column(table,
				"anotherColumn"), false));
		plainSelect.setOrderBy(new OrderBy(orderByElements));

		// Parte limit
		plainSelect.setLimit(new Limit(100));

		selectBodyRenderer.visit(plainSelect);

		assertNotNull(plainSelect.getSqlString());
	}

	@Test
	public void testUnion() {

		PlainSelect leftPlainSelect = new PlainSelect();

		Table table = new Table();
		table.setName("anyGivenTable");
		table.setSchemaName("anyGivenSchema");
		table.setAlias("anyGivenAlias");

		List<ISelectItem> selectItems = new ArrayList<ISelectItem>();
		selectItems.add(new SelectExpressionItem(new Column(table,
				"anyGivenColumn"), "ID"));
		leftPlainSelect.setSelectItems(selectItems);

		List<ITableReference> tableReferences = new ArrayList<ITableReference>();
		tableReferences.add(table);
		leftPlainSelect.setTableReferences(tableReferences);

		PlainSelect rightPlainSelect = new PlainSelect();

		List<ISelectItem> rightSelectItems = new ArrayList<ISelectItem>();
		rightSelectItems.add(new SelectExpressionItem(new Column(table,
				"anotherColumn"), "ID"));
		rightPlainSelect.setSelectItems(rightSelectItems);

		rightPlainSelect.setTableReferences(tableReferences);

		Union union = new Union();
		List<ISelectBody> selectBodys = new ArrayList<ISelectBody>();
		selectBodys.add(leftPlainSelect);
		selectBodys.add(rightPlainSelect);
		union.setSelectBodys(selectBodys);

		selectBodyRenderer.visit(union);

		assertNotNull(union.getSqlString());
	}
}
