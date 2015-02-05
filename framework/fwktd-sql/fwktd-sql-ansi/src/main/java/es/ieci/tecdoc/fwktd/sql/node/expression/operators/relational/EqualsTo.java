/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational;

import es.ieci.tecdoc.fwktd.sql.node.expression.BinaryExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;


/**
 * Expresión "igual"
 */
public class EqualsTo extends BinaryExpression {

	/**
	 * 
	 * @param leftExpression
	 * @param rightExpression
	 */
	public EqualsTo(IExpression leftExpression, IExpression rightExpression) {
		super(leftExpression, rightExpression);
	}

	/**
	 * 
	 */
	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	/**
	 * 
	 */
	public String getOperator() {
		return "=";
	}
	
	// Members
	
	private static final long serialVersionUID = -4864966470978087172L;
}
