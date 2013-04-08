/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.expression;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;

/**
 * CASE/WHEN expression.
 * 
 * Syntax: <code><pre>
 * CASE 
 * WHEN condition THEN expression
 * [WHEN condition THEN expression]...
 * [ELSE expression]
 * END
 * </pre></code>
 * 
 * <br/>
 * or <br/>
 * <br/>
 * 
 * <code><pre>
 * CASE expression 
 * WHEN condition THEN expression
 * [WHEN condition THEN expression]...
 * [ELSE expression]
 * END
 * </pre></code>
 * 
 * See also: https://aurora.vcu.edu/db2help/db2s0/frame3.htm#casexp
 * http://sybooks
 * .sybase.com/onlinebooks/group-as/asg1251e/commands/@ebt-link;pt=
 * 5954?target=%25N%15_52628_START_RESTART_N%25
 */
public class CaseExpression extends Expression {

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	/**
	 * @return Returns the switchExpression.
	 */
	public IExpression getSwitchExpression() {
		return switchExpression;
	}

	/**
	 * @param switchExpression
	 *            The switchExpression to set.
	 */
	public void setSwitchExpression(IExpression switchExpression) {
		this.switchExpression = switchExpression;
	}

	/**
	 * @return Returns the elseExpression.
	 */
	public IExpression getElseExpression() {
		return elseExpression;
	}

	/**
	 * @param elseExpression
	 *            The elseExpression to set.
	 */
	public void setElseExpression(IExpression elseExpression) {
		this.elseExpression = elseExpression;
	}

	/**
	 * @return Returns the whenClauses.
	 */
	public List<IExpression> getWhenClauses() {
		return whenClauses;
	}

	/**
	 * @param whenClauses
	 *            The whenClauses to set.
	 */
	public void setWhenClauses(List<IExpression> whenClauses) {
		this.whenClauses = whenClauses;
	}

	public boolean isOrContains(ISqlNode node) {

		if (super.isOrContains(node)) {
			return true;
		}

		if (getSwitchExpression() != null
				&& getSwitchExpression().isOrContains(node)) {
			return true;
		}

		if (getElseExpression() != null
				&& getElseExpression().isOrContains(node)) {
			return true;
		}

		if (CollectionUtils.isNotEmpty(getWhenClauses())) {
			for (IExpression clause : getWhenClauses()) {
				if (clause.isOrContains(node)) {
					return true;
				}
			}
		}

		return false;
	}

	// Members
	private static final long serialVersionUID = -8744054228143477959L;

	private IExpression switchExpression;

	private List<IExpression> whenClauses;

	private IExpression elseExpression;
}
