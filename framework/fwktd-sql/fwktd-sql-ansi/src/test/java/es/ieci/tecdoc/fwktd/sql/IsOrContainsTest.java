/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import es.ieci.tecdoc.fwktd.sql.node.expression.DoubleValue;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.MinorThan;
import es.ieci.tecdoc.fwktd.sql.node.schema.Column;
import es.ieci.tecdoc.fwktd.sql.node.schema.Table;
import es.ieci.tecdoc.fwktd.sql.node.statement.IStatement;
import es.ieci.tecdoc.fwktd.sql.node.statement.alter.table.AlterTable;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.CreateTable;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.NumericDataType;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.ISelectItem;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.ITableReference;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.PlainSelect;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.SelectExpressionItem;

/**
 * 
 * Pruebas unitarias para el método <code>isOrContains</code>.
 * 
 * @see IStatement
 */
@RunWith(JUnit4.class)
public class IsOrContainsTest {

	@Test
	public void testAlterTable() {

		Table table = new Table("hola", "adios");

		AlterTable alterTable = new AlterTable(table);

		assertTrue(alterTable.isOrContains(table));

		Table table2 = new Table("hola", "hola");

		assertFalse(alterTable.isOrContains(table2));

		Table table3 = new Table("hola", "adios");

		assertTrue(alterTable.isOrContains(table3));
	}

	@Test
	public void testNumericDataType() {
		NumericDataType type = new NumericDataType(23);

		NumericDataType type2 = new NumericDataType(23);

		NumericDataType type3 = new NumericDataType(232);

		assertTrue(type.isOrContains(type2));
		assertTrue(type.isOrContains(type));

		assertFalse(type.isOrContains(type3));
	}

	@Test
	public void testTableInCreate() {

		Table table = new Table();
		table.setName("anyGivenTable");
		table.setSchemaName("anyGivenSchema");

		CreateTable createTable = new CreateTable(table);
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

		Table table2 = new Table();
		table.setName("anyGivenTable");
		table.setSchemaName("OTHER");

		assertFalse(createTable.isOrContains(table2));

		assertTrue(createTable.isOrContains(table));
	}
}
