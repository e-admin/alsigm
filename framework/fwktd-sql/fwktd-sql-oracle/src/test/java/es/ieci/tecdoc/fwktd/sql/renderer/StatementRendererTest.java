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
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.StringValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.ExpressionList;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.MinorThan;
import es.ieci.tecdoc.fwktd.sql.node.schema.Column;
import es.ieci.tecdoc.fwktd.sql.node.schema.Table;
import es.ieci.tecdoc.fwktd.sql.node.statement.alter.table.AlterTable;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.CharacterDataType;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.ColumnDefinition;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.CreateTable;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.DateDataType;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.NumericDataType;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.ReferentialConstraintDefinition;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.TableElement;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.UniqueConstraintDefinition;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.UniqueConstraintDefinition.UniqueConstraintType;
import es.ieci.tecdoc.fwktd.sql.node.statement.delete.Delete;
import es.ieci.tecdoc.fwktd.sql.node.statement.drop.Drop;
import es.ieci.tecdoc.fwktd.sql.node.statement.insert.Insert;
import es.ieci.tecdoc.fwktd.sql.node.statement.rename.Rename;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.ISelectItem;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.ITableReference;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.PlainSelect;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.Select;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.SelectExpressionItem;
import es.ieci.tecdoc.fwktd.sql.node.statement.truncate.Truncate;
import es.ieci.tecdoc.fwktd.sql.node.statement.update.SetClause;
import es.ieci.tecdoc.fwktd.sql.node.statement.update.Update;
import es.ieci.tecdoc.fwktd.sql.node.statement.update.SetClause.SetClauseValueType;

/**
 * Prueba unitaria para el Renderer ANSI
 * 
 * @author IECISA
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class StatementRendererTest {

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

		selectBodyRenderer.setExpressionRenderer(expressionRenderer);
		selectBodyRenderer.setTableReferenceRenderer(tableReferenceRenderer);

		itemsListRenderer.setExpressionRenderer(expressionRenderer);
		itemsListRenderer.setSelectBodyRenderer(selectBodyRenderer);
	}

	@Test
	public void testCreate() {

		Table table = new Table();
		table.setName("anyGivenTable");
		table.setSchemaName("anyGivenSchema");
		// CREATE normal
		CreateTable createTable = new CreateTable(table);
		createTable.setTemporary(true);

		List<TableElement> tableElements = new ArrayList<TableElement>();
		tableElements.add(new ColumnDefinition("numericColumn",
				new NumericDataType(10, 2), true));
		tableElements.add(new ColumnDefinition("characterColumn",
				new CharacterDataType(15)));
		tableElements
				.add(new ColumnDefinition("dateColumn", new DateDataType()));
		List<String> pkColumns = new ArrayList<String>();
		pkColumns.add("numericColumn");
		pkColumns.add("characterColumn");
		tableElements.add(new UniqueConstraintDefinition("thePk",
				UniqueConstraintType.primaryKey, pkColumns));
		List<String> uniqueColumns = new ArrayList<String>();
		uniqueColumns.add("dateColumn");
		tableElements.add(new UniqueConstraintDefinition("theUnique",
				UniqueConstraintType.unique, uniqueColumns));

		List<String> fkColumns = new ArrayList<String>();
		fkColumns.add("fkColumn");
		List<String> referencedColumns = new ArrayList<String>();
		referencedColumns.add("referencedColumn");
		Table referencedTable = new Table();
		referencedTable.setName("anotherTable");
		referencedTable.setSchemaName("anotherSchema");
		tableElements.add(new ReferentialConstraintDefinition("aFk", fkColumns,
				referencedTable, referencedColumns));

		createTable.setTableElements(tableElements);

		statementRenderer.visit(createTable);

		assertNotNull(createTable.getSqlString());

		// CREATE AS SELECT
		createTable = new CreateTable(table);
		createTable.setTemporary(true);

		PlainSelect plainSelect = new PlainSelect();

		List<ISelectItem> selectItems = new ArrayList<ISelectItem>();
		selectItems.add(new SelectExpressionItem(new Column(table,
				"anyGivenColumn")));
		plainSelect.setSelectItems(selectItems);

		List<ITableReference> tableReferences = new ArrayList<ITableReference>();
		tableReferences.add(table);
		plainSelect.setTableReferences(tableReferences);

		MinorThan expression = new MinorThan(new Column(table, "age"),
				new DoubleValue(50.0));
		plainSelect.setWhere(expression);

		createTable.setSelect(plainSelect);

		statementRenderer.visit(createTable);

		assertNotNull(createTable.getSqlString());
	}

	@Test
	public void testDelete() {

		Delete delete = new Delete();

		Table table = new Table();
		table.setName("anyGivenTable");
		table.setSchemaName("anyGivenSchema");
		delete.setTable(table);

		statementRenderer.visit(delete);

		assertNotNull(delete.getSqlString());
	}

	@Test
	public void testDrop() {

		Table table = new Table();
		table.setName("anyGivenTable");
		table.setSchemaName("anyGivenSchema");
		Drop drop = new Drop(table);

		statementRenderer.visit(drop);

		assertNotNull(drop.getSqlString());
	}

	@Test
	public void testInsert() {

		Insert insert = new Insert();

		Table table = new Table();
		table.setName("anyGivenTable");
		table.setSchemaName("anyGivenSchema");
		insert.setTable(table);

		List<Column> columns = new ArrayList<Column>();
		columns.add(new Column(table, "firstColumn"));
		columns.add(new Column(table, "secondColumn"));
		insert.setColumns(columns);

		List<IExpression> expressions = new ArrayList<IExpression>();
		expressions.add(new StringValue("'anyGivenValue'"));
		expressions.add(new StringValue("'anotherValue'"));
		insert.setValuesList(new ExpressionList(expressions));

		statementRenderer.visit(insert);

		assertNotNull(insert.getSqlString());
	}

	@Test
	public void testReplace() {
		// El visit del replace no hace nada
	}

	@Test
	public void testSelect() {

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

		MinorThan expression = new MinorThan(new Column(table, "age"),
				new DoubleValue(50.0));
		plainSelect.setWhere(expression);

		Select select = new Select(plainSelect);
		statementRenderer.visit(select);

		assertNotNull(select.getSqlString());
	}

	@Test
	public void testTruncate() {

		Table table = new Table();
		table.setName("anyGivenTable");
		table.setSchemaName("anyGivenSchema");
		Truncate truncate = new Truncate(table);

		statementRenderer.visit(truncate);

		assertNotNull(truncate.getSqlString());
	}

	@Test
	public void testUpdate() {

		Update update = new Update();

		Table table = new Table();
		table.setName("anyGivenTable");
		table.setSchemaName("anyGivenSchema");
		update.setTable(table);

		List<SetClause> setClauses = new ArrayList<SetClause>();
		setClauses.add(new SetClause(new Column(table, "anyGivenColumn"),
				SetClauseValueType.expression, new StringValue("'anyValue'")));
		setClauses.add(new SetClause(new Column(table, "anotherColumn"),
				SetClauseValueType.null_value));
		setClauses.add(new SetClause(new Column(table, "anotherColumnMore"),
				SetClauseValueType.default_value));

		update.setSetClauses(setClauses);

		statementRenderer.visit(update);

		assertNotNull(update.getSqlString());
	}

	@Test
	public void testAlter() {

		Table table = new Table();
		table.setName("anyGivenTable");
		table.setSchemaName("anyGivenSchema");

		AlterTable alter = new AlterTable(table);
		statementRenderer.visit(alter);

		assertNotNull(alter.getSqlString());
	}

	@Test
	public void testRename() {

		Table table = new Table();
		table.setName("anyGivenTable");
		table.setSchemaName("anyGivenSchema");

		Rename rename = new Rename(table, "newName");
		statementRenderer.visit(rename);

		assertNotNull(rename.getSqlString());
	}
}
