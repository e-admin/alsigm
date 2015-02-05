/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.select;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.visitor.SelectBodyVisitor;

/**
 * Cuerpo de un select.
 */
public interface ISelectBody extends ISqlNode {

	public void accept(SelectBodyVisitor aSelectVisitor);

	public OrderBy getOrderBy();

	public void setOrderBy(OrderBy anOrderBy);

	public Limit getLimit();

	public void setLimit(Limit aLimit);
}
