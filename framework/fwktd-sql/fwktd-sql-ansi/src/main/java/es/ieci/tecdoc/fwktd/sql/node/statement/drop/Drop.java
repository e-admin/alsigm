/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.drop;

import java.util.List;

import es.ieci.tecdoc.fwktd.sql.node.schema.Table;
import es.ieci.tecdoc.fwktd.sql.node.statement.Statement;
import es.ieci.tecdoc.fwktd.sql.node.visitor.StatementVisitor;

/**
 * Sentencia "DROP" para tablas.
 */
public class Drop extends Statement {

	public Drop(Table aTable) {
		super();
		setTable(aTable);
	}

	public void accept(StatementVisitor aStatementVisitor) {
		aStatementVisitor.visit(this);
	}

	public List<String> getParameters() {
		return parameters;
	}

	public void setParameters(List<String> aList) {
		parameters = aList;
	}

	// Members

	private static final long serialVersionUID = -6633012000382404100L;

	private List<String> parameters;

}
