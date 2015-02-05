/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.select;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.visitor.SelectBodyVisitor;

/**
 * A column reference in expressions as "GROUP BY 1" or as "ORDER BY COL2".
 */
public interface IColumnReference extends ISqlNode {

	public void accept(SelectBodyVisitor aSelectVisitor);
}
