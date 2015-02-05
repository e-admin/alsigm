/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.expression.Expression;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;


/**
 * Expresión "existe"
 */
public class ExistsExpression extends Expression {

	public IExpression getRightExpression() {
		return rightExpression;
	}

	public void setRightExpression(IExpression expression) {
		rightExpression = expression;
	}

	public boolean isNot() {
		return not;
	}

	public void setNot(boolean b) {
		not = b;
	}

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	public boolean isOrContains(ISqlNode node) {

		if (super.isOrContains(node)) {
			return true;
		}

		if (getRightExpression() != null
				&& getRightExpression().isOrContains(node)) {
			return true;
		}

		return false;
	}
	
	// Members
	
	private static final long serialVersionUID = -7014807668837386764L;

	private IExpression rightExpression;

	private boolean not = false;
}
