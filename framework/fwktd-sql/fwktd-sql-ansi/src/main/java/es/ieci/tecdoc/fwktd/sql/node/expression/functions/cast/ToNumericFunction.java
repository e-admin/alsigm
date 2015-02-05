/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.functions.cast;

import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;

/**
 * Conversión de expresiones a número.
 */
public class ToNumericFunction extends CastFunction {


	public ToNumericFunction(IExpression anExpression) {
		super(anExpression);
	}

	public ToNumericFunction(IExpression anExpression, String aMask) {
		super(anExpression, aMask);
	}

	public void accept(ExpressionVisitor anExpressionVisitor) {
		anExpressionVisitor.visit(this);
	}
	
	// Members
	
	private static final long serialVersionUID = -5406432598426588914L;

}
