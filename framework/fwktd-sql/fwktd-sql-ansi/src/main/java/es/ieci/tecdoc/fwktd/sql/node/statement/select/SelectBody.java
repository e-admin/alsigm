/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.select;

import es.ieci.tecdoc.fwktd.sql.node.ISqlNode;
import es.ieci.tecdoc.fwktd.sql.node.SqlNode;

/**
 * @see <code>ISelectBody</code>.
 */
public abstract class SelectBody extends SqlNode implements ISelectBody {

	public OrderBy getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(OrderBy anOrderBy) {
		this.orderBy = anOrderBy;
	}

	public Limit getLimit() {
		return limit;
	}

	public void setLimit(Limit aLimit) {
		this.limit = aLimit;
	}

	@Override
	public boolean isOrContains(ISqlNode aNode) {
		if (super.isOrContains(aNode)) {
			return true;
		}

		if (getOrderBy() != null && getOrderBy().isOrContains(aNode)) {
			return true;
		}

		return (getLimit() != null && getLimit().isOrContains(aNode));
	}

	// Members

	private static final long serialVersionUID = 2634725684961469410L;

	/** Order by */
	private OrderBy orderBy;

	/** Limit */
	private Limit limit;
}
