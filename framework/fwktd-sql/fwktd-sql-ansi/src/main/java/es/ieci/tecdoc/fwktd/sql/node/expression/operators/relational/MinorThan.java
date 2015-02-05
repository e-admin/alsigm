/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational;

import es.ieci.tecdoc.fwktd.sql.node.expression.BinaryExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;


/**
 * Expresión 'menor que'.
 */
public class MinorThan extends BinaryExpression {

	public MinorThan(IExpression leftExpression, IExpression rightExpression) {
		super(leftExpression, rightExpression);
	}

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	public String getOperator() {
		return "<";
	}

	// Members
	
	private static final long serialVersionUID = 7057778118550882061L;
}
