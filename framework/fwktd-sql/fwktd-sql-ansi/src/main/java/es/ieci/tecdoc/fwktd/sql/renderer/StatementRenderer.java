/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.renderer;

import java.util.Iterator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.sql.node.schema.Column;
import es.ieci.tecdoc.fwktd.sql.node.statement.alter.table.AlterTable;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.CharacterDataType;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.ColDataType;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.ColumnDefinition;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.CreateTable;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.DateDataType;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.NumericDataType;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.ReferentialConstraintDefinition;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.TableConstraintDefinition;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.TableElement;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.UniqueConstraintDefinition;
import es.ieci.tecdoc.fwktd.sql.node.statement.create.table.UniqueConstraintDefinition.UniqueConstraintType;
import es.ieci.tecdoc.fwktd.sql.node.statement.delete.Delete;
import es.ieci.tecdoc.fwktd.sql.node.statement.drop.Drop;
import es.ieci.tecdoc.fwktd.sql.node.statement.initialize.random.InitializeRandom;
import es.ieci.tecdoc.fwktd.sql.node.statement.insert.Insert;
import es.ieci.tecdoc.fwktd.sql.node.statement.rename.Rename;
import es.ieci.tecdoc.fwktd.sql.node.statement.replace.Replace;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.Select;
import es.ieci.tecdoc.fwktd.sql.node.statement.truncate.Truncate;
import es.ieci.tecdoc.fwktd.sql.node.statement.update.SetClause;
import es.ieci.tecdoc.fwktd.sql.node.statement.update.Update;
import es.ieci.tecdoc.fwktd.sql.node.statement.update.SetClause.SetClauseValueType;
import es.ieci.tecdoc.fwktd.sql.node.visitor.StatementVisitor;
import es.ieci.tecdoc.fwktd.sql.util.SqlUtils;

/**
 * Genera el código SQL correspondiente a la representación intermedia de una
 * sentencia
 * 
 * @author IECISA
 */
public class StatementRenderer implements StatementVisitor {

	public StatementRenderer() {
	}

	/**
	 * Decorador aplicable a cualquier statement en el standard no se decora
	 * 
	 * @param aName
	 *            Elemento a decorar
	 * @return Elemento decorado, por defecto no se aplican decoradores
	 */
	protected String decorate(String aName) {
		return aName;
	}

	public void visit(CreateTable aCreateTable) {

		StringBuffer buffer = new StringBuffer();

		buffer.append("CREATE ");

		if (aCreateTable.isTemporary()) {
			buffer.append("GLOBAL TEMPORARY ");
		}

		aCreateTable.getTable().accept(getTableReferenceRenderer());
		buffer.append("TABLE ").append(
				aCreateTable.getTable().getWholeTableName());

		if (aCreateTable.getSelect() == null) {
			// Añade la definición de los elementos de la tabla
			this.addColumnTableElements(buffer, aCreateTable);
		}

		if (aCreateTable.isTemporary()) {
			buffer.append(" ON COMMIT PRESERVE ROWS");
		}

		if (aCreateTable.getSelect() != null) {
			// Se trata de un CREATE AS SELECT
			aCreateTable.getSelect().accept(getSelectBodyRenderer());
			buffer.append(" AS ").append(
					aCreateTable.getSelect().getSqlString());
		}

		aCreateTable.setSqlString(buffer.toString());
	}

	public void visit(AlterTable anAlterTable) {

		StringBuffer buffer = new StringBuffer();

		buffer.append("ALTER TABLE ");

		anAlterTable.getTable().accept(getTableReferenceRenderer());
		buffer.append(anAlterTable.getTable().getWholeTableName());

		if (anAlterTable.isEnable()) {
			buffer.append(" ENABLE");
		} else {
			buffer.append(" DISABLE");
		}
		buffer.append(" PRIMARY KEY");

		anAlterTable.setSqlString(buffer.toString());
	}

	public void visit(Delete aDelete) {

		StringBuffer buffer = new StringBuffer();

		aDelete.getTable().accept(getTableReferenceRenderer());
		buffer.append("DELETE FROM " + aDelete.getTable().getWholeTableName());
		if (aDelete.getWhere() != null) {
			aDelete.getWhere().accept(getExpressionRenderer());
			buffer.append(" WHERE ").append(aDelete.getWhere().getSqlString());
		}

		aDelete.setSqlString(buffer.toString());
	}

	public void visit(Drop aDrop) {

		StringBuffer buffer = new StringBuffer();

		aDrop.getTable().accept(getTableReferenceRenderer());
		buffer.append("DROP TABLE " + aDrop.getTable().getWholeTableName());
		if (CollectionUtils.isNotEmpty(aDrop.getParameters())) {
			buffer.append(" " + SqlUtils.getStringList(aDrop.getParameters()));
		}

		aDrop.setSqlString(buffer.toString());
	}

	public void visit(Insert anInsert) {

		StringBuffer buffer = new StringBuffer();

		buffer.append("INSERT INTO ");
		anInsert.getTable().accept(getTableReferenceRenderer());
		buffer.append(anInsert.getTable().getWholeTableName());

		if (CollectionUtils.isNotEmpty(anInsert.getColumns())) {

			buffer.append("(");
			for (Iterator<Column> iter = anInsert.getColumns().iterator(); iter
					.hasNext();) {
				Column column = (Column) iter.next();
				buffer.append(decorate(column.getColumnName()));
				if (iter.hasNext()) {
					buffer.append(", ");
				}
			}
			buffer.append(")");
		}

		Assert.notNull(anInsert.getValuesList(),
				"Insert sin elementos que insertar");

		if (anInsert.isUseValues()) {
			buffer.append(" VALUES (");
		}

		anInsert.getValuesList().accept(getItemListRenderer());
		buffer.append(anInsert.getValuesList().getSqlString());

		if (anInsert.isUseValues()) {
			buffer.append(")");
		}

		anInsert.setSqlString(buffer.toString());
	}

	public void visit(Replace aReplace) {
		// No implementado
	}

	public void visit(Select aSelect) {

		aSelect.getSelectBody().accept(getSelectBodyRenderer());

		aSelect.setSqlString(aSelect.getSelectBody().getSqlString());
	}

	public void visit(Truncate aTruncate) {
		/*
		 * El TRUNCATE no está soportado en el ANSI, por lo que lo emulamos con
		 * un DELETE
		 */
		StringBuffer buffer = new StringBuffer();

		aTruncate.getTable().accept(getTableReferenceRenderer());
		buffer
				.append("DELETE FROM "
						+ aTruncate.getTable().getWholeTableName());

		aTruncate.setSqlString(buffer.toString());
	}

	public void visit(Update anUpdate) {

		StringBuffer buffer = new StringBuffer();

		anUpdate.getTable().accept(getTableReferenceRenderer());
		buffer.append("UPDATE " + anUpdate.getTable().getWholeTableName()
				+ " SET ");

		SetClause setClause = null;
		for (Iterator<SetClause> iter = anUpdate.getSetClauses().iterator(); iter
				.hasNext();) {

			setClause = (SetClause) iter.next();

			buffer.append(setClause.getColumn().getColumnName()).append("=");

			if (SetClauseValueType.null_value.equals(setClause
					.getSetClauseValueType())) {
				buffer.append("NULL");
			} else if (SetClauseValueType.default_value.equals(setClause
					.getSetClauseValueType())) {
				buffer.append("DEFAULT");
			} else {
				// Es una expresión
				setClause.getExpression().accept(getExpressionRenderer());
				buffer.append(setClause.getExpression().getSqlString());
			}

			if (iter.hasNext()) {
				buffer.append(", ");
			}
		}

		if (anUpdate.getWhere() != null) {
			buffer.append(" WHERE ");
			anUpdate.getWhere().accept(getExpressionRenderer());
			buffer.append(anUpdate.getWhere().getSqlString());
		}

		anUpdate.setSqlString(buffer.toString());
	}

	public void visit(ColumnDefinition aColumnDefinition) {

		StringBuffer buffer = new StringBuffer();

		buffer.append(decorate(aColumnDefinition.getName()));
		buffer.append(" ");

		// Visitamos el tipo de dato y lo añadimos
		aColumnDefinition.getColDataType().accept(this);
		buffer.append(aColumnDefinition.getColDataType().getSqlString());

		if (aColumnDefinition.isNotNull()) {
			buffer.append(" NOT NULL");
		}

		if (aColumnDefinition.getColumnSpecStrings() != null) {
			for (Iterator<String> iterator = aColumnDefinition
					.getColumnSpecStrings().iterator(); iterator.hasNext();) {
				buffer.append(" ").append((String) iterator.next());
			}
		}

		aColumnDefinition.setSqlString(buffer.toString());
	}

	public void visit(TableConstraintDefinition aTableConstraintDefinition) {

		StringBuffer buffer = new StringBuffer();

		if (StringUtils.isNotEmpty(aTableConstraintDefinition.getName())) {
			buffer.append("CONSTRAINT ").append(
					aTableConstraintDefinition.getName()).append(" ");
		}

		aTableConstraintDefinition.setSqlString(buffer.toString());
	}

	public void visit(UniqueConstraintDefinition anUniqueConstraintDefinition) {

		this.visit((TableConstraintDefinition) anUniqueConstraintDefinition);

		StringBuffer buffer = new StringBuffer(anUniqueConstraintDefinition
				.getSqlString());

		if (UniqueConstraintType.unique.equals(anUniqueConstraintDefinition
				.getType())) {
			buffer.append("UNIQUE ");
		} else {
			buffer.append("PRIMARY KEY ");
		}

		buffer.append(SqlUtils.getStringList(anUniqueConstraintDefinition
				.getColumnsNames(), true, true));

		anUniqueConstraintDefinition.setSqlString(buffer.toString());
	}

	public void visit(
			ReferentialConstraintDefinition aReferentialConstraintDefinition) {

		this
				.visit((TableConstraintDefinition) aReferentialConstraintDefinition);

		StringBuffer buffer = new StringBuffer(aReferentialConstraintDefinition
				.getSqlString());

		buffer.append("FOREIGN KEY ").append(
				SqlUtils.getStringList(aReferentialConstraintDefinition
						.getColumnsNames(), true, true));

		aReferentialConstraintDefinition.getReferencedTable().accept(
				getTableReferenceRenderer());
		buffer.append(" REFERENCES ").append(
				aReferentialConstraintDefinition.getReferencedTable()
						.getWholeTableName()).append(
				SqlUtils.getStringList(aReferentialConstraintDefinition
						.getReferencedColumns(), true, true));

		aReferentialConstraintDefinition.setSqlString(buffer.toString());
	}

	public void visit(ColDataType aColDataType) {

		// Concatena al sqlString del tipo de dato la parte de los argumentos
		StringBuffer buffer = new StringBuffer(aColDataType.getSqlString());

		if (CollectionUtils.isNotEmpty(aColDataType.getArgumentsStringList())) {
			buffer.append(" ").append(
					SqlUtils.getStringList(aColDataType
							.getArgumentsStringList(), true, true));
		}

		aColDataType.setSqlString(buffer.toString());
	}

	public void visit(NumericDataType aNumericDataType) {

		StringBuffer buffer = new StringBuffer();

		buffer.append("NUMERIC");
		if (aNumericDataType.getPrecision() != null) {

			buffer.append("(").append(aNumericDataType.getPrecision());
			if (aNumericDataType.getScale() != null) {
				buffer.append(", ").append(aNumericDataType.getScale());
			}
			buffer.append(")");
		}

		aNumericDataType.setSqlString(buffer.toString());

		this.visit((ColDataType) aNumericDataType);
	}

	public void visit(CharacterDataType aCharacterDataType) {

		StringBuffer buffer = new StringBuffer();

		buffer.append("VARCHAR");
		if (aCharacterDataType.getSize() != null) {
			buffer.append("(").append(aCharacterDataType.getSize()).append(")");
		}

		aCharacterDataType.setSqlString(buffer.toString());
		this.visit((ColDataType) aCharacterDataType);
	}

	public void visit(DateDataType aDateDataType) {

		aDateDataType.setSqlString("DATE");

		this.visit((ColDataType) aDateDataType);
	}

	public void visit(SetClause aSetClause) {

	}

	public void visit(Rename aRename) {
		// El Standard ANSI SQL 92 no soporta Rename
		throw new UnsupportedOperationException(
				"Sentencia RENAME TABLE no soportada en Standard ANSI SQL 92");
	}

	public void visit(InitializeRandom anInitializeRandom) {
		// El Standard ANSI SQL 92 no soporta este procedimiento
		throw new UnsupportedOperationException(
				"Procedimiento 'Intialize random' no soportado en Standard ANSI SQL 92");
	}

	/**
	 * Añade la información de descripción de los elementos de la tabla
	 * 
	 * @param aBuffer
	 *            buffer al que añadir la información de los elementos de la
	 *            tabla
	 * @param aCreateTable
	 *            tabla de la que describir sus elementos
	 */
	protected void addColumnTableElements(StringBuffer aBuffer,
			CreateTable aCreateTable) {

		if (CollectionUtils.isNotEmpty(aCreateTable.getTableElements())) {

			aBuffer.append(" ( ");
			for (Iterator<TableElement> iter = aCreateTable.getTableElements()
					.iterator(); iter.hasNext();) {

				TableElement tableElement = (TableElement) iter.next();
				tableElement.accept(this);
				aBuffer.append(tableElement.getSqlString());

				if (iter.hasNext())
					aBuffer.append(",\n");
			}

			aBuffer.append(" )");
		}
	}

	public ItemsListRenderer getItemListRenderer() {
		return itemListRenderer;
	}

	public TableReferenceRenderer getTableReferenceRenderer() {
		return tableReferenceRenderer;
	}

	public SelectBodyRenderer getSelectBodyRenderer() {
		return selectBodyRenderer;
	}

	public ExpressionRenderer getExpressionRenderer() {
		return expressionRenderer;
	}

	public void setExpressionRenderer(ExpressionRenderer anExpressionRenderer) {
		this.expressionRenderer = anExpressionRenderer;
	}

	public void setItemListRenderer(ItemsListRenderer anItemListRenderer) {
		this.itemListRenderer = anItemListRenderer;
	}

	public void setSelectBodyRenderer(SelectBodyRenderer aSelectBodyRenderer) {
		this.selectBodyRenderer = aSelectBodyRenderer;
	}

	public void setTableReferenceRenderer(
			TableReferenceRenderer aTableReferenceRenderer) {
		this.tableReferenceRenderer = aTableReferenceRenderer;
	}

	// Members

	/** Renderer para el cuerpo de sentencias SELECT */
	protected SelectBodyRenderer selectBodyRenderer;

	/** Renderer para expresiones */
	protected ExpressionRenderer expressionRenderer;

	/** Renderer para Listas de elementos */
	protected ItemsListRenderer itemListRenderer;

	/** Renderer para Tablas */
	protected TableReferenceRenderer tableReferenceRenderer;
}
