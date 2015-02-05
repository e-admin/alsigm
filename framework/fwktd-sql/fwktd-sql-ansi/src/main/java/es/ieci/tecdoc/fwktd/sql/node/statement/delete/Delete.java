/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.delete;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.statement.Statement;
import es.ieci.tecdoc.fwktd.sql.node.visitor.StatementVisitor;

/**
 * Sentencia "DELETE".
 */
public class Delete extends Statement {

	public void accept(StatementVisitor aStatementVisitor) {
		aStatementVisitor.visit(this);
	}

	public IExpression getWhere() {
		return where;
	}

	public void setWhere(IExpression anExpression) {
		where = anExpression;
	}

	@Override
	public boolean isOrContains(ISqlNode aNode) {

		if (super.isOrContains(aNode)) {
			return true;
		}
		return (getWhere() != null && getWhere().isOrContains(aNode));
	}

	// Members

	private static final long serialVersionUID = 525429460186795200L;

	/** Expresión que condiciona las filas a borrar */
	private IExpression where;

}
