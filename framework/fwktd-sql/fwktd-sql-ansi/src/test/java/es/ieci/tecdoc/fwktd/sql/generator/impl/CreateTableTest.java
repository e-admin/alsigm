package es.ieci.tecdoc.fwktd.sql.generator.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import es.ieci.tecdoc.fwktd.sql.generator.SqlGenerator;
import es.ieci.tecdoc.fwktd.sql.node.schema.Table;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.CharacterDataType;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.ColumnDefinition;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.CreateTable;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.DateDataType;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.NumericDataType;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.ReferentialConstraintDefinition;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.TableElement;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.UniqueConstraintDefinition;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.UniqueConstraintDefinition.UniqueConstraintType;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.AllTableColumns;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.ISelectItem;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.PlainSelect;
import es.ieci.tecdoc.fwktd.sql.renderer.ansi.ANSIStatementRenderer;

@RunWith(JUnit4.class)
public class CreateTableTest {

	protected SqlGenerator sqlGenerator;

	@Before
	public void setUp() {
		sqlGenerator = new SqlGeneratorImpl();
		((SqlGeneratorImpl) sqlGenerator)
				.setRenderer(new ANSIStatementRenderer());
	}

	@Test
	public void testCreateTableWithSchemaAndSingleColumn() {
		CreateTable ct = new CreateTable(new Table("A_SCHEMA", "SIMPLE_TABLE"));
		List<TableElement> elements = new ArrayList<TableElement>();
		ColumnDefinition cd = new ColumnDefinition("field1",
				new CharacterDataType(25), true);
		elements.add(cd);
		ct.setTableElements(elements);

		String sql = sqlGenerator.generateSQL(ct);
		Assert.assertNotNull(sql);
		Assert
				.assertEquals(
						"CREATE TABLE A_SCHEMA.SIMPLE_TABLE ( field1 VARCHAR(25) NOT NULL )",
						sql);
	}

	@Test
	public void testCreateTableWithoutColumns() {
		CreateTable ct = new CreateTable(new Table("A_SCHEMA", "SIMPLE_TABLE"));
		ct.setTableElements(new ArrayList<TableElement>());

		String sql = sqlGenerator.generateSQL(ct);

		Assert.assertNotNull(sql);
		Assert.assertEquals("CREATE TABLE A_SCHEMA.SIMPLE_TABLE", sql);
	}

	@Test
	public void testCreateTemporaryComplexTableWithPrimaryKey() {
		CreateTable createTable = new CreateTable(new Table("A_SCHEMA",
				"COMPLEX_TABLE"));
		createTable.setTemporary(true);

		List<TableElement> columns = new ArrayList<TableElement>();
		columns.add(new ColumnDefinition("field1", new CharacterDataType(25),
				true));
		columns.add(new ColumnDefinition("field2", new CharacterDataType(25),
				true));
		columns.add(new ColumnDefinition("field3", new CharacterDataType(25)));
		columns.add(new UniqueConstraintDefinition(
				UniqueConstraintType.primaryKey, Arrays.asList(new String[] {
						"field1", "field2" })));
		createTable.setTableElements(columns);

		String sql = sqlGenerator.generateSQL(createTable);

		Assert.assertNotNull(sql);
		Assert
				.assertEquals(
						"CREATE GLOBAL TEMPORARY TABLE A_SCHEMA.COMPLEX_TABLE ( field1 VARCHAR(25) NOT NULL,\nfield2 VARCHAR(25) NOT NULL,\nfield3 VARCHAR(25),\nPRIMARY KEY (field1, field2) ) ON COMMIT PRESERVE ROWS",
						sql);
	}

	@Test
	public void createTableWithUniqueColumn() {
		CreateTable createTable = new CreateTable(new Table("A_SCHEMA",
				"A_TABLE"));

		List<TableElement> columns = new ArrayList<TableElement>();
		columns.add(new ColumnDefinition("field1", new DateDataType()));
		columns.add(new UniqueConstraintDefinition(UniqueConstraintType.unique,
				Arrays.asList(new String[] { "field1" })));
		createTable.setTableElements(columns);

		String sql = sqlGenerator.generateSQL(createTable);

		Assert.assertNotNull(createTable);
		Assert
				.assertEquals(
						"CREATE TABLE A_SCHEMA.A_TABLE ( field1 DATE,\nUNIQUE (field1) )",
						sql);
	}

	@Test
	public void createTemporaryTableAsSelect() {
		Table table = new Table("A_SCHEMA", "A_TABLE_AS_SELECT");
		CreateTable ct = new CreateTable(table);
		ct.setTemporary(true);

		PlainSelect select = new PlainSelect();
		Table selectTable = new Table("A_SCHEMA", "Table", "Alias");
		select
				.setSelectItems(Arrays
						.asList(new ISelectItem[] { new AllTableColumns(
								selectTable) }));
		select.setTableReference(selectTable);
		ct.setSelect(select);

		String sql = sqlGenerator.generateSQL(ct);

		Assert.assertNotNull(sql);
		Assert
				.assertEquals(
						"CREATE GLOBAL TEMPORARY TABLE A_SCHEMA.A_TABLE_AS_SELECT ON COMMIT PRESERVE ROWS AS SELECT Alias.* FROM A_SCHEMA.Table AS Alias",
						sql);

	}

	@Test
	public void createTableOneTypeEachColumn() {
		CreateTable createTable = new CreateTable(new Table("A_SCHEMA",
				"A_TABLE"));

		List<TableElement> columns = new ArrayList<TableElement>();
		columns.add(new ColumnDefinition("field1", new CharacterDataType(250)));
		columns.add(new ColumnDefinition("field2", new DateDataType()));
		columns.add(new ColumnDefinition("field3", new NumericDataType()));

		createTable.setTableElements(columns);

		String sql = sqlGenerator.generateSQL(createTable);

		Assert.assertNotNull(sql);
		Assert
				.assertEquals(
						"CREATE TABLE A_SCHEMA.A_TABLE ( field1 VARCHAR(250),\nfield2 DATE,\nfield3 NUMERIC )",
						sql);
	}

	@Test
	public void createTableWithForeignKey() {
		Table toCreate = new Table("A_SCHEMA", "A_TABLE");
		Table toReference = new Table("A_SCHEMA", "ANOTHER_TABLE");

		CreateTable createTable = new CreateTable(toCreate);

		List<TableElement> columns = new ArrayList<TableElement>();
		columns.add(new ColumnDefinition("id", new NumericDataType()));
		columns.add(new ColumnDefinition("fk", new NumericDataType()));
		columns.add(new ReferentialConstraintDefinition("FK", Arrays
				.asList(new String[] { "fk" }), toReference, Arrays
				.asList(new String[] { "id" })));

		createTable.setTableElements(columns);

		String sql = sqlGenerator.generateSQL(createTable);

		Assert.assertNotNull(sql);
		Assert.assertEquals(
				"CREATE TABLE A_SCHEMA.A_TABLE ( id NUMERIC,\nfk NUMERIC,\nCONSTRAINT FK FOREIGN KEY (fk) REFERENCES A_SCHEMA.ANOTHER_TABLE(id) )",
				sql);
	}
}