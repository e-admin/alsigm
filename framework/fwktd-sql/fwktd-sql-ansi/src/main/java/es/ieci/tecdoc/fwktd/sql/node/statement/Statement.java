/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.SqlNode;
import es.ieci.tecdoc.fwktd.sql.node.schema.Table;

/**
 * @see <code>IStatement</code>.
 */
public abstract class Statement extends SqlNode implements IStatement {

	public Table getTable() {
		return table;
	}

	public void setTable(Table aTable) {
		this.table = aTable;
	}

	public boolean isOrContains(ISqlNode aNode) {

		if (super.isOrContains(aNode)) {
			return true;
		}

		return (getTable() != null && getTable().equals(aNode));
	}

	// Members

	private static final long serialVersionUID = -6497581135181761233L;

	/** Tabla principal sobre la que opera la sentencia */
	private Table table;
}
