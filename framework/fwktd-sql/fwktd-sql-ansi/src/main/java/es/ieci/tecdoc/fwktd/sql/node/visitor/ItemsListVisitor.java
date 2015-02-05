/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.visitor;

import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.ExpressionList;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.SubSelect;

/**
 * Visitor para instancias del interfaz <code>ItemList</code>
 * 
 * @author IECISA
 */
public interface ItemsListVisitor {

	public void visit(SubSelect aSubSelect);

	public void visit(ExpressionList anExpressionList);
}
