/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.operators.conditional;

import es.ieci.tecdoc.fwktd.sql.node.expression.BinaryExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;


/**
 * Expresión 'or'.
 */
public class OrExpression extends BinaryExpression {

	public OrExpression(IExpression leftExpression, IExpression rightExpression) {
		super(leftExpression, rightExpression);
	}

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	public String getOperator() {
		return "OR";
	}
	
	// Members
	
	private static final long serialVersionUID = 1557904247094144744L;
}
