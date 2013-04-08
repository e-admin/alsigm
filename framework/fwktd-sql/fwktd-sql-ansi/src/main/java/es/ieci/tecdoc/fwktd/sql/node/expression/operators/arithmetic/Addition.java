/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.operators.arithmetic;

import es.ieci.tecdoc.fwktd.sql.node.expression.BinaryExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;


/**
 * Expresión 'suma'.
 */
public class Addition extends BinaryExpression {

	public Addition(IExpression leftExpression, IExpression rightExpression) {
		super(leftExpression, rightExpression);
	}

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	public String getOperator() {
		return "+";
	}
	
	// Members
	
	private static final long serialVersionUID = 4064789899722378959L;
}
