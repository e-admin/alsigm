/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.functions.agregate;

import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;


/**
 * Función mínimo.
 */
public class MinFunction extends AggregateFunction {

	public MinFunction(IExpression anExpression) {
		super(anExpression);
	}

	public void accept(ExpressionVisitor anExpressionVisitor) {
		anExpressionVisitor.visit(this);
	}
	
	// Members
	
	private static final long serialVersionUID = 564854058299102144L;
}
