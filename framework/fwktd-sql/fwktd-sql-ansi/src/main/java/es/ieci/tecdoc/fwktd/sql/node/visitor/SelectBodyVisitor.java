/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.visitor;

import es.ieci.tecdoc.fwktd.sql.node.schema.Column;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.AllColumns;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.AllTableColumns;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.ColumnIndex;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.ISelectBody;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.OrderBy;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.OrderByElement;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.PlainSelect;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.SelectExpressionItem;
import es.ieci.tecdoc.fwktd.sql.node.statement.select.Union;

/**
 * Visitor para instancias del interfaz <code>SelectBody</code>. Visita
 * también los elementos que lo forman
 * 
 * @author IECISA
 */
public interface SelectBodyVisitor {

	public void visit(ISelectBody aSelectBody);

	public void visit(PlainSelect aPlainSelect);

	public void visit(Union anUnion);

	public void visit(AllColumns anAllColumns);

	public void visit(AllTableColumns anAllTableColumns);

	public void visit(SelectExpressionItem aSelectExpressionItem);

	public void visit(OrderBy anOrderBy);

	public void visit(OrderByElement anOrderByElement);

	public void visit(ColumnIndex aColumnIndex);

	public void visit(Column aColumn);
}
