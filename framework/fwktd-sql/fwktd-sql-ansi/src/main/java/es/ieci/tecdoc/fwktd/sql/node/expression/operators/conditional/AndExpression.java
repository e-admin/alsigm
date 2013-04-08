/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.operators.conditional;

import es.ieci.tecdoc.fwktd.sql.node.expression.BinaryExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;


/**
 * Expresión 'and'.
 */
public class AndExpression extends BinaryExpression {	

	public AndExpression(IExpression leftExpression, IExpression rightExpression) {
		super(leftExpression, rightExpression);
	}

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	public String getOperator() {
		return "AND";
	}
	
	// Members
	
	private static final long serialVersionUID = 5174164441760816577L;
}
