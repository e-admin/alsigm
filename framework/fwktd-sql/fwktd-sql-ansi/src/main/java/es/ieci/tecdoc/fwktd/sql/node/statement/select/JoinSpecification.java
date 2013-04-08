/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.select;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.visitor.TableReferenceVisitor;

/**
 * Condición para un JOIN.
 */
public interface JoinSpecification extends ISqlNode {

	public void accept(TableReferenceVisitor aTableReferenceVisitor);
}
