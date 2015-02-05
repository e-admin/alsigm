/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.statement;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.visitor.StatementVisitor;

/**
 * Sentencia sobre la base de datos.
 */
public interface IStatement extends ISqlNode {

	public void accept(StatementVisitor aStatementVisitor);
}
