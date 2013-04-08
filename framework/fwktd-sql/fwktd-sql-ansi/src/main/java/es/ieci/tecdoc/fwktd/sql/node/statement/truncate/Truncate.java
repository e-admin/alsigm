/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.truncate;

import es.ieci.tecdoc.fwktd.sql.node.schema.Table;
import es.ieci.tecdoc.fwktd.sql.node.statement.Statement;
import es.ieci.tecdoc.fwktd.sql.node.visitor.StatementVisitor;

/**
 * Sentencia "TRUNCATE TABLE".
 */
public class Truncate extends Statement {

	public Truncate(Table aTable) {
		super();
		setTable(aTable);
	}

	public void accept(StatementVisitor aStatementVisitor) {
		aStatementVisitor.visit(this);
	}

	// Members

	private static final long serialVersionUID = -8831983372407746903L;
}
