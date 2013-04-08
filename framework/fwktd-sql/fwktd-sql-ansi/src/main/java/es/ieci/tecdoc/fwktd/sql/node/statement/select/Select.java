/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.select;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.statement.Statement;
import es.ieci.tecdoc.fwktd.sql.node.visitor.StatementVisitor;

/**
 * Sentencia "SELECT".
 */
public class Select extends Statement {

	public Select(ISelectBody aSelectBody) {
		super();
		setSelectBody(aSelectBody);
	}

	public void accept(StatementVisitor aStatementVisitor) {
		aStatementVisitor.visit(this);
	}

	public ISelectBody getSelectBody() {
		return selectBody;
	}

	public void setSelectBody(ISelectBody aBody) {
		selectBody = aBody;
	}

	@Override
	public boolean isOrContains(ISqlNode aNode) {
		if (super.isOrContains(aNode)) {
			return true;
		}

		return (getSelectBody() != null && getSelectBody().isOrContains(aNode));
	}

	// Members

	private static final long serialVersionUID = 2204883407023513324L;

	private ISelectBody selectBody;
}
