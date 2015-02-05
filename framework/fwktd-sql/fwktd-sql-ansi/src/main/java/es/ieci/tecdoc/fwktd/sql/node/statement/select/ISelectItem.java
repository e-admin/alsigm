/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.select;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.visitor.SelectBodyVisitor;

/**
 * Anything between "SELECT" and "FROM"<BR>
 * (that is, any column or expression etc to be retrieved with the query).
 */
public interface ISelectItem extends ISqlNode {

	public void accept(SelectBodyVisitor aSelectVisitor);
}
