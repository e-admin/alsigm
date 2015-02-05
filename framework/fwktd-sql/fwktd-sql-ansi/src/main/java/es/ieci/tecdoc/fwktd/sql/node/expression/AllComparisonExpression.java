/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.expression;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.SubSelect;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;

/**
 * 
 * @author IECISA
 * 
 */
public class AllComparisonExpression extends Expression {

	public AllComparisonExpression(SubSelect aSubSelect) {
		setSubSelect(aSubSelect);
	}

	public SubSelect getSubSelect() {
		return subSelect;
	}

	public void setSubSelect(SubSelect aSubSelect) {
		this.subSelect = aSubSelect;
	}

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	public boolean isOrContains(ISqlNode node) {

		if (super.isOrContains(node)) {
			return true;
		}

		if (getSubSelect() != null && getSubSelect().isOrContains(node)) {
			return true;
		}

		return false;
	}

	// Members

	private static final long serialVersionUID = -1789046991811306578L;

	private SubSelect subSelect;
}
