/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.functions.agregate;

import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;


/**
 * Función máximo.
 */
public class MaxFunction extends AggregateFunction {

	public MaxFunction(IExpression anExpression) {
		super(anExpression);
	}

	public void accept(ExpressionVisitor anExpressionVisitor) {
		anExpressionVisitor.visit(this);
	}
	
	// Members

	private static final long serialVersionUID = -8922114689897676055L;

}
