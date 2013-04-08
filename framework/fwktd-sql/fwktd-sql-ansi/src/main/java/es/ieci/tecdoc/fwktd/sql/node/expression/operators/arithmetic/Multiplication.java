/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.operators.arithmetic;

import es.ieci.tecdoc.fwktd.sql.node.expression.BinaryExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;


/**
 * Expresión 'multiplicación'.
 */
public class Multiplication extends BinaryExpression {

	public Multiplication(IExpression leftExpression,
			IExpression rightExpression) {
		super(leftExpression, rightExpression);
	}

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	public String getOperator() {
		return "*";
	}
	
	// Members
	
	private static final long serialVersionUID = -2651053133735854045L;
}
