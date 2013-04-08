/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.functions.cast;

import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;


/**
 * Conversión de expresiones a fecha.
 */
public class ToDateFunction extends CastFunction {

	public ToDateFunction(IExpression anExpression) {
		super(anExpression);
	}

	public ToDateFunction(IExpression anExpression, String aMask) {
		super(anExpression, aMask);
	}

	public void accept(ExpressionVisitor anExpressionVisitor) {
		anExpressionVisitor.visit(this);
	}
	
	// Members
	
	private static final long serialVersionUID = -6243893835671317563L;
}
