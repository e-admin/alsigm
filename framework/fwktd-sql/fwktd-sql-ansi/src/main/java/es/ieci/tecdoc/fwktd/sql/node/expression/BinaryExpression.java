/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.expression;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;

/**
 * Clase base para las expresiones binarias.
 */
public abstract class BinaryExpression extends Expression {

	public BinaryExpression(IExpression aLeftExpression,
			IExpression aRightExpression) {
		setLeftExpression(aLeftExpression);
		setRightExpression(aRightExpression);
	}

	public IExpression getLeftExpression() {
		return leftExpression;
	}

	public IExpression getRightExpression() {
		return rightExpression;
	}

	public void setLeftExpression(IExpression expression) {
		leftExpression = expression;
	}

	public void setRightExpression(IExpression expression) {
		rightExpression = expression;
	}

	public abstract String getOperator();

	public boolean isOrContains(ISqlNode node) {

		if (super.isOrContains(node)) {
			return true;
		}

		if (getLeftExpression() != null
				&& getLeftExpression().isOrContains(node)) {
			return true;
		}

		if (getRightExpression() != null
				&& getRightExpression().isOrContains(node)) {
			return true;
		}

		return false;
	}

	// Members

	private static final long serialVersionUID = -4512775885317352866L;

	private IExpression leftExpression;

	private IExpression rightExpression;

}
