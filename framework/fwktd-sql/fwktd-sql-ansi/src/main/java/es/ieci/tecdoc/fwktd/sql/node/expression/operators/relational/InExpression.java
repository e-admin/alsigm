/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.expression.Expression;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;

/**
 * Expresión 'en'.
 */
public class InExpression extends Expression {

	public InExpression() {
	}

	public InExpression(IExpression leftExpression, IItemsList itemsList) {
		setLeftExpression(leftExpression);
		setItemsList(itemsList);
	}

	public IItemsList getItemsList() {
		return itemsList;
	}

	public IExpression getLeftExpression() {
		return leftExpression;
	}

	public void setItemsList(IItemsList list) {
		itemsList = list;
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

		if (getItemsList() != null && getItemsList().isOrContains(node)) {
			return true;
		}

		return false;
	}

	// Members

	private static final long serialVersionUID = -1769883732047942342L;

	/** Expresión a evaluar */
	private IExpression leftExpression;

	/** Lista de valores */
	private IItemsList itemsList;

	/** Flag de negación */
	private boolean not = false;
}
