/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.expression;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.SubSelect;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;

/**
 * Expresión 'any'.
 */
public class AnyComparisonExpression extends Expression {

	public AnyComparisonExpression(SubSelect aSubSelect) {
		setSubSelect(aSubSelect);
	}

	public SubSelect getSubSelect() {
		return subSelect;
	}

	public void setSubSelect(SubSelect aSubSelect) {
		this.subSelect = aSubSelect;
	}

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	public boolean isOrContains(ISqlNode node) {

		if (super.isOrContains(node)) {
			return true;
		}

		if (getSubSelect() != null && getSubSelect().isOrContains(node)) {
			return true;
		}

		return false;
	}

	// Members

	private static final long serialVersionUID = 4489113997084390754L;

	private SubSelect subSelect;
}
