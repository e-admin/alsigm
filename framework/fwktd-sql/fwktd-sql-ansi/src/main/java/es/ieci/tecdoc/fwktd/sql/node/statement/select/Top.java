/*
 */
package es.ieci.tecdoc.fwktd.sql.node.statement.select;

import es.ieci.tecdoc.fwktd.sql.node.SqlNode;

/**
 * A top clause in the form [TOP row_count].
 */
public class Top extends SqlNode {

	public long getRowCount() {
		return rowCount;
	}

	public void setRowCount(long aRowCount) {
		rowCount = aRowCount;
	}

	public boolean isRowCountJdbcParameter() {
		return rowCountJdbcParameter;
	}

	public void setRowCountJdbcParameter(boolean b) {
		rowCountJdbcParameter = b;
	}

	// Members

	private static final long serialVersionUID = -1344625600591523403L;

	private long rowCount;

	private boolean rowCountJdbcParameter = false;
}
