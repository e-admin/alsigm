/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node;

import es.ieci.tecdoc.fwktd.core.model.BaseValueObject;

/**
 * @see <code>ISqlNode</code>
 */
public abstract class SqlNode extends BaseValueObject implements ISqlNode {

	public String getSqlString() {
		return sqlString;
	}

	public void setSqlString(String aSqlString) {
		this.sqlString = aSqlString;
	}

	public boolean isOrContains(ISqlNode aNode) {
		return this.equals(aNode);
	}

	// Members

	private static final long serialVersionUID = 1067990782719509085L;

	private String sqlString;
}
