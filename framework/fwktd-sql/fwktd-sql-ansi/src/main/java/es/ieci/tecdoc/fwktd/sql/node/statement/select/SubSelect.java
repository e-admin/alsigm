/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.select;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.SqlNode;
import es.ieci.tecdoc.fwktd.sql.node.expression.IExpression;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.IItemsList;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ItemsListVisitor;
import es.ieci.tecdoc.fwktd.sql.node.visitor.TableReferenceVisitor;

/**
 * Una subselect seguida opcionalment de un alias.
 */
public class SubSelect extends SqlNode implements IExpression, ITableReference,
		IItemsList {

	public SubSelect(ISelectBody aSelectBody) {
		super();
		setSelectBody(aSelectBody);
	}

	public SubSelect(ISelectBody aSelectBody, String anAlias) {
		this(aSelectBody);
		setAlias(anAlias);
	}

	public ISelectBody getSelectBody() {
		return selectBody;
	}

	public void setSelectBody(ISelectBody aBody) {
		selectBody = aBody;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String anAlias) {
		alias = anAlias;
	}

	public void accept(TableReferenceVisitor aTableReferenceVisitor) {
		aTableReferenceVisitor.visit(this);
	}

	public void accept(ExpressionVisitor anExpressionVisitor) {
		anExpressionVisitor.visit(this);
	}

	public void accept(ItemsListVisitor anItemsListVisitor) {
		anItemsListVisitor.visit(this);
	}

	@Override
	public boolean isOrContains(ISqlNode aNode) {
		if (super.isOrContains(aNode)) {
			return true;
		}

		return (getSelectBody() != null && getSelectBody().isOrContains(aNode));
	}

	// Members

	private static final long serialVersionUID = 1851538654073689733L;

	private ISelectBody selectBody;

	private String alias;

}
