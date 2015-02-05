/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.select;

import es.ieci.tecdoc.fwktd.sql.node.SqlNode;

/**
 * @see <code>ITableReference</code>.
 */
public abstract class TableReference extends SqlNode implements ITableReference {

	public String getAlias() {
		return alias;
	}

	public void setAlias(String anAlias) {
		this.alias = anAlias;
	}

	// Members

	private static final long serialVersionUID = 3632575978376943140L;

	/** Alias */
	private String alias;
}
