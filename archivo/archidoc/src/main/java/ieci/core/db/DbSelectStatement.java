package ieci.core.db;

import common.Constants;
import common.util.StringUtils;

public final class DbSelectStatement extends DbOutputStatement {

	public DbSelectStatement() {
		super();
	}

	public String create(DbConnection conn, String with, String tblNames,
			String colNames, String qual, boolean distinct) throws Exception {
		return create(conn, with, tblNames, colNames, qual, distinct, null);
	}

	public String create(DbConnection conn, String tblNames, String colNames,
			String qual, boolean distinct) throws Exception {
		return create(conn, null, tblNames, colNames, qual, distinct, null);
	}

	public String create(DbConnection conn, String with, String tblNames,
			String colNames, String qual, boolean distinct, String hint)
			throws Exception {
		String sql = getSelectStmtText(tblNames, colNames, qual, distinct, hint);

		if (StringUtils.isNotEmpty(with)) {
			sql = with + Constants.BLANK + sql;
		}

		super.create(conn, sql);
		return sql;
	}

	public String createScrollable(DbConnection conn, String tblNames,
			String colNames, String qual, boolean distinct) throws Exception {
		return createScrollable(conn, tblNames, colNames, qual, distinct, null);
	}

	public String createScrollable(DbConnection conn, String tblNames,
			String colNames, String qual, boolean distinct, String hint)
			throws Exception {
		String sql = getSelectStmtText(tblNames, colNames, qual, distinct, hint);
		super.createScrollable(conn, sql);
		return sql;
	}

	// **************************************************************************

	public static String getSelectStmtText(String tblNames, String colNames,
			String qual, boolean distinct) {
		return getSelectStmtText(tblNames, colNames, qual, distinct, null);
	}

	public static String getSelectStmtText(String withQuery, String tblNames,
			String colNames, String qual, boolean distinct) {
		return getSelectStmtText(withQuery, tblNames, colNames, qual, distinct,
				null);
	}

	public static String getSelectStmtText(String tblNames, String colNames,
			String qual, boolean distinct, String hint) {
		return DbSelectStatement.getSelectStmtText(null, tblNames, colNames,
				qual, distinct, hint);
	}

	public static String getSelectStmtText(String withQuery, String tblNames,
			String colNames, String qual, boolean distinct, String hint) {

		StringBuffer tbdr;

		tbdr = new StringBuffer();

		if (withQuery != null) {
			tbdr.append(withQuery + Constants.BLANK);
		}

		tbdr.append("SELECT ");

		if (hint != null)
			tbdr.append(hint).append(" ");

		if (distinct)
			tbdr.append("DISTINCT ");

		tbdr.append(colNames).append(" FROM ").append(tblNames);

		if (qual != null)
			tbdr.append(" ").append(qual);

		return tbdr.toString();
	}

	public static String getSelectStmtText(String tblNames, String colNames,
			String qual, String groupBy, String having) {
		StringBuffer tbdr = new StringBuffer();

		tbdr.append("SELECT ").append(colNames).append(" FROM ")
				.append(tblNames);

		if (qual != null)
			tbdr.append(" ").append(qual);
		if (groupBy != null)
			tbdr.append(" GROUP BY ").append(groupBy);
		if (having != null)
			tbdr.append(" HAVING ").append(having);

		return tbdr.toString();
	}

	public static String getSelectStmtText(String colNames) {
		StringBuffer tbdr = new StringBuffer();

		tbdr.append("SELECT ").append(colNames);

		return tbdr.toString();
	}
} // class
