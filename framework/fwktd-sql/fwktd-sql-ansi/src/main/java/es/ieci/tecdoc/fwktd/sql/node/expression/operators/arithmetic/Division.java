/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.operators.arithmetic;

import es.ieci.tecdoc.fwktd.sql.node.expression.BinaryExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;


/**
 * Expresión 'división'.
 */
public class Division extends BinaryExpression {

	public Division(IExpression leftExpression, IExpression rightExpression) {
		super(leftExpression, rightExpression);
	}

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	public String getOperator() {
		return "/";
	}
	
	// Members

	private static final long serialVersionUID = 1072927373374803010L;
}
