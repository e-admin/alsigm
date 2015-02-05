package ieci.core.db;

import util.StringOwnTokenizer;

import common.Constants;

public final class DbUpdateStatement extends DbInputStatement {

	public DbUpdateStatement() {
		super();
	}

	public void create(DbConnection conn, String tblName, String colNames,
			String qual, String withQuery) throws Exception {
		super.create(conn,
				getUpdateStmtText(tblName, colNames, qual, withQuery));
	}

	public void create(DbConnection conn, String sql) throws Exception {
		super.create(conn, sql);
	}

	// **************************************************************************

	private static String getUpdateStmtText(String tblName, String colNames,
			String qual, String withQuery) {
		if (colNames.indexOf(',', 0) != -1)
			return getMCUpdateStmtText(tblName, colNames, qual, withQuery);
		else
			return get1CUpdateStmtText(tblName, colNames, qual, withQuery);
	}

	private static String getMCUpdateStmtText(String tblName, String colNames,
			String qual, String withQuery) {

		StringBuffer tbdr;
		StringOwnTokenizer strTkr;
		int count, i;

		tbdr = new StringBuffer();

		if (withQuery != null) {
			tbdr.append(withQuery + Constants.BLANK);
		}

		tbdr.append("UPDATE ").append(tblName).append(" SET ");

		strTkr = new StringOwnTokenizer(colNames, ",");

		count = strTkr.countTokens();

		for (i = 1; i < count; i++)
			tbdr.append(strTkr.nextToken()).append(" = ?, ");

		tbdr.append(strTkr.nextToken()).append(" = ?");

		if (qual != null)
			tbdr.append(" ").append(qual);

		return tbdr.toString();

	}

	private static String get1CUpdateStmtText(String tblName, String colName,
			String qual, String withQuery) {

		StringBuffer tbdr;

		tbdr = new StringBuffer();

		if (withQuery != null) {
			tbdr.append(withQuery + Constants.BLANK);
		}

		tbdr.append("UPDATE ").append(tblName).append(" SET ");

		tbdr.append(colName).append(" = ?");

		if (qual != null)
			tbdr.append(" ").append(qual);

		return tbdr.toString();

	}

} // class
