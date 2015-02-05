/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.select;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.visitor.SelectBodyVisitor;

/**
 * Una expresión dentro de una SELECT ("SELECT expr1 AS EXPR").
 */
public class SelectExpressionItem extends SelectItem {

	/**
	 * 
	 * @param anExpression
	 */
	public SelectExpressionItem(IExpression anExpression) {
		setExpression(anExpression);
	}

	/**
	 * 
	 * @param anExpression
	 * @param anAlias
	 */
	public SelectExpressionItem(IExpression anExpression, String anAlias) {
		this(anExpression);
		setAlias(anAlias);
	}

	public String getAlias() {
		return alias;
	}

	public IExpression getExpression() {
		return expression;
	}

	public void setAlias(String anAlias) {
		alias = anAlias;
	}

	public void setExpression(IExpression anExpression) {
		this.expression = anExpression;
	}

	public void accept(SelectBodyVisitor aSelectVisitor) {
		aSelectVisitor.visit(this);
	}

	@Override
	public boolean isOrContains(ISqlNode aNode) {
		if (super.isOrContains(aNode)) {
			return true;
		}
		return (getExpression().isOrContains(aNode));
	}

	// Members

	private static final long serialVersionUID = -2274607917301301821L;

	private IExpression expression;

	private String alias;
}
