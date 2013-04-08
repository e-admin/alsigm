/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.SqlNode;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ItemsListVisitor;

/**
 * Lista de expresiones. Un ejemplo de uso podría ser SELECT A FROM TAB WHERE B
 * IN (expr1,expr2,expr3)
 */
public class ExpressionList extends SqlNode implements IItemsList {

	public ExpressionList() {
	}

	public ExpressionList(List<IExpression> expressions) {
		setExpressions(expressions);
	}

	public List<IExpression> getExpressions() {
		return expressions;
	}

	public void setExpressions(List<IExpression> list) {
		expressions = list;
	}

	public void addExpression(IExpression expression) {
		if (null == this.expressions) {
			this.expressions = new ArrayList<IExpression>();
		}
		this.expressions.add(expression);
	}

	public void accept(ItemsListVisitor itemsListVisitor) {
		itemsListVisitor.visit(this);
	}

	public boolean isOrContains(ISqlNode node) {

		if (super.isOrContains(node)) {
			return true;
		}

		if (CollectionUtils.isNotEmpty(getExpressions())) {
			for (IExpression expression : getExpressions()) {
				if (expression.isOrContains(node)) {
					return true;
				}
			}
		}
		return false;
	}

	// Members

	private static final long serialVersionUID = -1861564374652011155L;

	private List<IExpression> expressions;
}
