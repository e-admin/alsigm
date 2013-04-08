/*
 *
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ItemsListVisitor;

/**
 * Lista de valores para una sentencia "INSERT" (por ejemplo, una SELECT o una
 * lista de expresiones)
 */
public interface IItemsList extends ISqlNode {

	public void accept(ItemsListVisitor itemsListVisitor);
}
