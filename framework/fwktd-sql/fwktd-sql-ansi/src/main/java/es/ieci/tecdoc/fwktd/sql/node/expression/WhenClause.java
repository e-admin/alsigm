/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.expression;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;

/**
 * Expresión con la siguiente sintaxis: WHEN condition THEN expression. Es parte
 * de una <code>CaseExpression</code>.
 * 
 */
public class WhenClause extends Expression {

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	public IExpression getThenExpression() {
		return thenExpression;
	}

	public void setThenExpression(IExpression thenExpression) {
		this.thenExpression = thenExpression;
	}

	public IExpression getWhenExpression() {
		return whenExpression;
	}

	public void setWhenExpression(IExpression whenExpression) {
		this.whenExpression = whenExpression;
	}

	public boolean isOrContains(ISqlNode node) {

		if (super.isOrContains(node)) {
			return true;
		}

		if (getWhenExpression() != null
				&& getWhenExpression().isOrContains(node)) {
			return true;
		}

		if (getThenExpression() != null
				&& getThenExpression().isOrContains(node)) {
			return true;
		}

		return false;
	}

	// Members

	private static final long serialVersionUID = -8967864910080599450L;

	private IExpression whenExpression;

	private IExpression thenExpression;
}
