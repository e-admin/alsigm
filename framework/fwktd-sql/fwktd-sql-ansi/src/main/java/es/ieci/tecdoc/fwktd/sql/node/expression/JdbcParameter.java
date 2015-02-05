/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.expression;

import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;


/**
 * A '?' in a statement.
 */
public class JdbcParameter extends Expression {

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}
	
	// Members
	
	private static final long serialVersionUID = 8506500263553032079L;
}
