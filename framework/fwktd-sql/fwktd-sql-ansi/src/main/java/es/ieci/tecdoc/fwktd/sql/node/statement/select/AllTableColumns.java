/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.select;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.schema.Table;
import es.ieci.tecdoc.fwktd.sql.node.visitor.SelectBodyVisitor;

/**
 * Todas las columnas de una tabla (p.e., "SELECT Tabla.* FROM ...").
 */
public class AllTableColumns extends SelectItem {

	/**
	 * Constructor por defecto.
	 */
	public AllTableColumns() {
		super();
	}

	public AllTableColumns(Table aTable) {
		this();
		setTable(aTable);
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table aTable) {
		this.table = aTable;
	}

	public void accept(SelectBodyVisitor aSelectBodyVisitor) {
		aSelectBodyVisitor.visit(this);
	}

	@Override
	public boolean isOrContains(ISqlNode aNode) {
		if (super.isOrContains(aNode)) {
			return true;
		}

		return (getTable() != null && getTable().isOrContains(aNode));
	}

	// Members

	private static final long serialVersionUID = -4731917871002095171L;

	private Table table;
}
