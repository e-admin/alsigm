/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational;

import es.ieci.tecdoc.fwktd.sql.node.expression.BinaryExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;

/**
 * Expresión 'como'.
 */
public class LikeExpression extends BinaryExpression {

	public LikeExpression(IExpression leftExpression,
			IExpression rightExpression) {
		super(leftExpression, rightExpression);
	}

	public boolean isNot() {
		return not;
	}

	public void setNot(boolean b) {
		not = b;
	}

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	public String getOperator() {
		return ((isNot()) ? "NOT " : "") + "LIKE";
	}

	public String getEscape() {
		return escape;
	}

	public void setEscape(String escape) {
		this.escape = escape;
	}

	// Members

	private static final long serialVersionUID = -2888616051644452328L;

	private boolean not = false;

	private String escape = null;

}
