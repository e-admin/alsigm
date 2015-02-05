/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.select;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.visitor.TableReferenceVisitor;

/**
 * Elemento de la parte FROM.
 */
public interface ITableReference extends ISqlNode {

	public void accept(TableReferenceVisitor aTableReferenceVisitor);

	public String getAlias();

	public void setAlias(String anAlias);
}
