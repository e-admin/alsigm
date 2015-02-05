/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.expression;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;


/**
 * Expresión.
 */
public interface IExpression extends ISqlNode {

	public void accept(ExpressionVisitor expressionVisitor);
}
