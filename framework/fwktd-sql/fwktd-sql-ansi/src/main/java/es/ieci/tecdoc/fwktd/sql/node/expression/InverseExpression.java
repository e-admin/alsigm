/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.expression;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;


/**
 * It represents a "-" before an expression.
 */
public class InverseExpression extends Expression {

	public InverseExpression() {
	}

	public InverseExpression(IExpression expression) {
		setExpression(expression);
	}

	public IExpression getExpression() {
		return expression;
	}

	public void setExpression(IExpression expression) {
		this.expression = expression;
	}

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	public boolean isOrContains(ISqlNode node) {

		if (super.isOrContains(node)) {
			return true;
		}

		if (getExpression() != null && getExpression().isOrContains(node)) {
			return true;
		}

		return false;
	}
	
	// Members

	private static final long serialVersionUID = -1075156859125453995L;

	private IExpression expression;
}
