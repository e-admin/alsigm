/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.create.table;

import es.ieci.tecdoc.fwktd.sql.node.SqlNode;
import es.ieci.tecdoc.fwktd.sql.node.visitor.StatementVisitor;

/**
 * Elemento de la definición de una tabla.
 */
public abstract class TableElement extends SqlNode {

	public String getName() {
		return name;
	}

	public void setName(String aName) {
		this.name = aName;
	}

	public abstract void accept(StatementVisitor aStatementVisitor);

	// Members

	private static final long serialVersionUID = 2488211184089678707L;

	private String name;

}
