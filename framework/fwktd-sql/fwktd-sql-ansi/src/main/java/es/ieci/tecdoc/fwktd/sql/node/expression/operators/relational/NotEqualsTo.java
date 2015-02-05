/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational;

import es.ieci.tecdoc.fwktd.sql.node.expression.BinaryExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;

/**
 * Expresión 'distinto de'.
 */
public class NotEqualsTo extends BinaryExpression {

	public NotEqualsTo(IExpression leftExpression, IExpression rightExpression) {
		super(leftExpression, rightExpression);
	}

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	public String getOperator() {
		return "<>";
	}
	
	// Members
	
	private static final long serialVersionUID = 8306071942630247937L;
}
