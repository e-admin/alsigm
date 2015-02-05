/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.expression.Expression;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;

/**
 * Expresión 'es null'.
 */
public class IsNullExpression extends Expression {

	public IsNullExpression(IExpression expression) {
		this.expression = expression;
	}

	public IExpression getLeftExpression() {
		return expression;
	}

	public boolean isNot() {
		return not;
	}

	public void setLeftExpression(IExpression expression) {
		this.expression = expression;
	}

	public void setNot(boolean b) {
		not = b;
	}

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	public boolean isOrContains(ISqlNode node) {

		if (super.isOrContains(node)) {
			return true;
		}

		if (getLeftExpression() != null
				&& getLeftExpression().isOrContains(node)) {
			return true;
		}

		return false;
	}

	// Members

	private static final long serialVersionUID = -3636698962326205345L;

	private IExpression expression;

	private boolean not = false;

}
