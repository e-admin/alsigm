/*
 * 
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.expression;

import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;


/**
 * Expresión valor 'NULL'.
 */
public class NullValue extends Expression {

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}
	
	// Members
	
	private static final long serialVersionUID = -6322985438390659852L;
}
