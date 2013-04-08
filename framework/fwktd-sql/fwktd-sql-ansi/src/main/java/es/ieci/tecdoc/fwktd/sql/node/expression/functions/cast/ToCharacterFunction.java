/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.functions.cast;

import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;

/**
 * Conversión de expresiones a cadena.
 */
public class ToCharacterFunction extends CastFunction {

	public ToCharacterFunction(IExpression anExpression) {
		super(anExpression);
	}

	public ToCharacterFunction(IExpression anExpression, String aMask) {
		super(anExpression, aMask);
	}

	public void accept(ExpressionVisitor anExpressionVisitor) {
		anExpressionVisitor.visit(this);
	}

	// Members

	private static final long serialVersionUID = -6676677006362791693L;
}
