/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.create.table;

import java.util.List;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.visitor.StatementVisitor;

/**
 * Definición de una columna en una sentencia CREATE TABLE.<br>
 * Ejemplo: mycol VARCHAR(30) NOT NULL.
 */
public class ColumnDefinition extends TableElement {

	public ColumnDefinition() {
		super();
	}

	public ColumnDefinition(String aName, ColDataType aColDataType,
			boolean aNotNull) {
		this();
		setName(aName);
		setColDataType(aColDataType);
		setNotNull(aNotNull);
	}

	public ColumnDefinition(String aName, ColDataType aColDataType) {
		this(aName, aColDataType, false);
	}

	/**
	 * A list of strings of every word after the datatype of the column.<br>
	 * Example ("NOT", "NULL")
	 */
	public List<String> getColumnSpecStrings() {
		return columnSpecStrings;
	}

	public void setColumnSpecStrings(List<String> list) {
		columnSpecStrings = list;
	}

	public ColDataType getColDataType() {
		return colDataType;
	}

	public void setColDataType(ColDataType type) {
		colDataType = type;
	}

	public boolean isNotNull() {
		return notNull;
	}

	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}

	public void accept(StatementVisitor statementVisitor) {
		statementVisitor.visit(this);
	}

	public boolean isOrContains(ISqlNode node) {

		if (super.isOrContains(node)) {
			return true;
		}
		return (getColDataType() != null && getColDataType().isOrContains(node));
	}

	// Members

	private static final long serialVersionUID = 1419070362285670273L;

	private ColDataType colDataType;

	private List<String> columnSpecStrings;

	/** Indica si la columna debe contener un valor */
	private boolean notNull;

}
