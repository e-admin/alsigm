/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.rename;

import es.ieci.tecdoc.fwktd.sql.node.schema.Table;
import es.ieci.tecdoc.fwktd.sql.node.statement.Statement;
import es.ieci.tecdoc.fwktd.sql.node.visitor.StatementVisitor;

/**
 * Sentencia de renombrado de una tabla.
 */
public class Rename extends Statement {

	public Rename(Table aTable, String aNewName) {
		super();
		setTable(aTable);
		setNewName(aNewName);
	}

	public void accept(StatementVisitor aStatementVisitor) {
		aStatementVisitor.visit(this);
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String aNewName) {
		this.newName = aNewName;
	}

	// Members

	private static final long serialVersionUID = -8105967493543143660L;

	/** Nuevo nombre */
	private String newName;

}
