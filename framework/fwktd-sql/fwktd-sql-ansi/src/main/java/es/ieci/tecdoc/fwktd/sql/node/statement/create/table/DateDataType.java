/*
 *
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.create.table;

import es.ieci.tecdoc.fwktd.sql.node.visitor.StatementVisitor;


/**
 * <code>DataType</code> para columnas de tipo fecha.
 */
public class DateDataType extends ColDataType {

	public void accept(StatementVisitor aStatementVisitor) {
		aStatementVisitor.visit(this);
	}
	
	// Members

	private static final long serialVersionUID = 3695174006471503680L;
}
