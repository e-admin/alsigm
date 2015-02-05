/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.expression.Expression;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;


/**
 * Expresión "entre"
 */
public class Between extends Expression {

	public IExpression getBetweenExpressionEnd() {
		return betweenExpressionEnd;
	}

	public IExpression getBetweenExpressionStart() {
		return betweenExpressionStart;
	}

	public IExpression getLeftExpression() {
		return leftExpression;
	}

	public void setBetweenExpressionEnd(IExpression expression) {
		betweenExpressionEnd = expression;
	}

	public void setBetweenExpressionStart(IExpression expression) {
		betweenExpressionStart = expression;
	}

	public void setLeftExpression(IExpression expression) {
		leftExpression = expression;
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

	public boolean isOrContains(ISqlNode node) {

		if (super.isOrContains(node)) {
			return true;
		}

		if (getLeftExpression() != null
				&& getLeftExpression().isOrContains(node)) {
			return true;
		}

		if (getBetweenExpressionStart() != null
				&& getBetweenExpressionStart().isOrContains(node)) {
			return true;
		}

		if (getBetweenExpressionEnd() != null
				&& getBetweenExpressionEnd().isOrContains(node)) {
			return true;
		}

		return false;
	}
	
	// Members
	
	private static final long serialVersionUID = -5604951164891568184L;

	private IExpression leftExpression;

	private boolean not = false;

	private IExpression betweenExpressionStart;

	private IExpression betweenExpressionEnd;
}
