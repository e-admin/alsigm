/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.functions.agregate;

import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;


/**
 * Función suma.
 */
public class SumFunction extends AggregateFunction {

	public SumFunction(IExpression anExpression) {
		super(anExpression);
	}

	public void accept(ExpressionVisitor anExpressionVisitor) {
		anExpressionVisitor.visit(this);
	}
	
	// Members
	
	private static final long serialVersionUID = -396534479190218722L;
}
