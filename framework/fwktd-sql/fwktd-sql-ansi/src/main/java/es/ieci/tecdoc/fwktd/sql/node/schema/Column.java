/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.schema;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.SqlNode;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.IColumnReference;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;
import es.ieci.tecdoc.fwktd.sql.node.visitor.SelectBodyVisitor;

/**
 * Una columna. Puede tener la tabla a la que pertenece.
 */
public class Column extends SqlNode implements IExpression, IColumnReference {

	public Column() {
		super();
	}

	public Column(String aColumnName) {
		this(null, aColumnName);
	}

	public Column(Table aTable, String aColumnName) {
		this();
		setTable(aTable);
		setColumnName(aColumnName);
	}

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	public void accept(SelectBodyVisitor selectVisitor) {
		selectVisitor.visit(this);
	}

	public String getColumnName() {
		return columnName;
	}

	public Table getTable() {
		return table;
	}

	public void setColumnName(String string) {
		columnName = string;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public boolean isOrContains(ISqlNode node) {

		if (super.isOrContains(node)) {
			return true;
		}

		if (getTable() != null && getTable().isOrContains(node)) {
			return true;
		}

		return false;
	}

	// Members

	private static final long serialVersionUID = -3552116994565748357L;

	/** Tabla a la que pertenece */
	private Table table;

	/** Nombre de la columna */
	private String columnName;
}
