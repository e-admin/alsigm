/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.functions.agregate;

import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;

/**
 * Función media.
 */
public class AvgFunction extends AggregateFunction {

	public AvgFunction(IExpression anExpression) {
		super(anExpression);
	}

	public void accept(ExpressionVisitor anExpressionVisitor) {
		anExpressionVisitor.visit(this);
	}

	// Members

	private static final long serialVersionUID = -8437487966852293614L;
}
