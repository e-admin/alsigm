/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.visitor;

import es.ieci.tecdoc.fwktd.sql.node.schema.Table;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.ITableReference;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.JoinCondition;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.JoinedTable;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.NamedColumnsJoin;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.SubSelect;

/**
 * Visitor para instancias del interfaz <code>TableReference</code>
 * 
 * @author IECISA
 */
public interface TableReferenceVisitor {

	public void visit(ITableReference aTableReference);
	
	public void visit(Table aTableName);

	public void visit(SubSelect aSubSelect);

	public void visit(JoinedTable aJoinedTable);

	public void visit(NamedColumnsJoin aNamedColumnsJoin);

	public void visit(JoinCondition aJoinCondition);
}