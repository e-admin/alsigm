package ieci.core.db;

import java.util.Date;

import common.Constants;
import common.db.DBUtils;

public final class DbUpdateFns {

	private DbUpdateFns() {
	}

	public static int update(DbConnection conn, String tblName,
			String colNames, DbInputRecord colValues, String qual)
			throws Exception {
		return update(conn, tblName, colNames, colValues, qual, Constants.BLANK);
	}

	public static int update(DbConnection conn, String tblName,
			String colNames, DbInputRecord colValues, String qual,
			String withQuery) throws Exception {

		DbUpdateStatement stmt = null;

		try {

			stmt = new DbUpdateStatement();
			String finalColNames = DbUtil.getColNamesForInsertSentenceSyntax(
					conn, colNames);
			stmt.create(conn, tblName, finalColNames, qual, withQuery);

			colValues.setStatementValues(stmt);

			int result = stmt.execute();
			stmt.release();
			return result;
		} catch (Exception e) {
			DbUpdateStatement.ensureRelease(stmt, e);
			throw e;
		}

	}

	public static int update(DbConnection conn, String sql) throws Exception {

		DbUpdateStatement stmt = null;

		try {

			stmt = new DbUpdateStatement();
			stmt.create(conn, sql);
			int result = stmt.execute();
			stmt.release();
			return result;
		} catch (Exception e) {
			DbUpdateStatement.ensureRelease(stmt, e);
			throw e;
		}
	}

	public static void updateShortText(DbConnection conn, String tblName,
			String colName, String colValue, String qual) throws Exception {

		updateShortText(conn, tblName, colName, colValue, qual, Constants.BLANK);
	}

	public static void updateShortText(DbConnection conn, String tblName,
			String colName, String colValue, String qual, String withQuery)
			throws Exception {

		DbUpdateStatement stmt = null;

		try {

			stmt = new DbUpdateStatement();
			stmt.create(conn, tblName, colName, qual, withQuery);

			stmt.setShortText(1, colValue);

			stmt.execute();
			stmt.release();

		} catch (Exception e) {
			DbUpdateStatement.ensureRelease(stmt, e);
		}

	}

	public static void updateLongText(DbConnection conn, String tblName,
			String colName, String colValue, String qual) throws Exception {

		updateLongText(conn, tblName, colName, colValue, qual, Constants.BLANK);
	}

	public static void updateLongText(DbConnection conn, String tblName,
			String colName, String colValue, String qual, String withQuery)
			throws Exception {

		DbUpdateStatement stmt = null;

		try {

			stmt = new DbUpdateStatement();
			stmt.create(conn, tblName, colName, qual, withQuery);

			stmt.setLongText(1, colValue);

			stmt.execute();
			stmt.release();

		} catch (Exception e) {
			DbUpdateStatement.ensureRelease(stmt, e);
		}

	}

	public static void updateShortInteger(DbConnection conn, String tblName,
			String colName, short colValue, String qual) throws Exception {

		updateShortInteger(conn, tblName, colName, colValue, qual,
				Constants.BLANK);
	}

	public static void updateShortInteger(DbConnection conn, String tblName,
			String colName, short colValue, String qual, String withQuery)
			throws Exception {

		DbUpdateStatement stmt = null;

		try {

			stmt = new DbUpdateStatement();
			stmt.create(conn, tblName, colName, qual, withQuery);

			stmt.setShortInteger(1, colValue);

			stmt.execute();
			stmt.release();

		} catch (Exception e) {
			DbUpdateStatement.ensureRelease(stmt, e);
		}

	}

	public static void updateLongInteger(DbConnection conn, String tblName,
			String colName, int colValue, String qual) throws Exception {

		updateLongInteger(conn, tblName, colName, colValue, qual,
				Constants.BLANK);
	}

	public static void updateLongInteger(DbConnection conn, String tblName,
			String colName, int colValue, String qual, String withQuery)
			throws Exception {

		DbUpdateStatement stmt = null;

		try {

			stmt = new DbUpdateStatement();
			stmt.create(conn, tblName, colName, qual, withQuery);

			stmt.setLongInteger(1, colValue);

			stmt.execute();
			stmt.release();

		} catch (Exception e) {
			DbUpdateStatement.ensureRelease(stmt, e);
		}

	}

	public static void updateLong(DbConnection conn, String tblName,
			String colName, long colValue, String qual) throws Exception {

		updateLong(conn, tblName, colName, colValue, qual, Constants.BLANK);
	}

	public static void updateLong(DbConnection conn, String tblName,
			String colName, long colValue, String qual, String withQuery)
			throws Exception {
		DbUpdateStatement stmt = null;
		try {
			stmt = new DbUpdateStatement();
			stmt.create(conn, tblName, colName, qual, withQuery);

			stmt.setLong(1, colValue);

			stmt.execute();
			stmt.release();
		} catch (Exception e) {
			DbUpdateStatement.ensureRelease(stmt, e);
		}
	}

	public static void incrementField(DbConnection conn, String tblName,
			String colName, long increment, String qual) throws Exception {

		DbInputStatement stmt = null;

		try {

			stmt = new DbInputStatement();
			stmt.create(
					conn,
					getIncrementFieldStmtText(tblName, colName, increment, qual));

			stmt.execute();
			stmt.release();

		} catch (Exception e) {
			DbInputStatement.ensureRelease(stmt, e);
		}

	}

	private static String getIncrementFieldStmtText(String tblName,
			String colName, long increment, String qual) {
		StringBuffer stmtSQL = new StringBuffer();
		stmtSQL.append("UPDATE ").append(tblName);
		stmtSQL.append(" SET ").append(colName).append("=").append(colName)
				.append("+(").append(increment).append(")");
		if (qual != null)
			stmtSQL.append(" ").append(qual);
		return stmtSQL.toString();
	}

	public static void updateCustom(DbConnection conn, String tblName,
			String colName, String colValue, String qual) throws Exception {

		DbInputStatement stmt = null;

		try {

			stmt = new DbInputStatement();
			stmt.create(conn,
					getCustomUpdateStmtText(tblName, colName, colValue, qual));

			stmt.execute();
			stmt.release();

		} catch (Exception e) {
			DbInputStatement.ensureRelease(stmt, e);
		}

	}

	public static void updateCustom(DbConnection conn, String tblName,
			String[] colNames, String[] colValues, String qual)
			throws Exception {

		DbInputStatement stmt = null;

		try {

			stmt = new DbInputStatement();
			stmt.create(conn,
					getCustomUpdateStmtText(tblName, colNames, colValues, qual));

			stmt.execute();
			stmt.release();

		} catch (Exception e) {
			DbInputStatement.ensureRelease(stmt, e);
		}

	}

	private static String getCustomUpdateStmtText(String tblName,
			String colName, String colValue, String qual) {
		StringBuffer stmtSQL = new StringBuffer();
		stmtSQL.append("UPDATE ").append(tblName);
		stmtSQL.append(" SET ").append(colName).append("=").append(colValue);
		if (qual != null)
			stmtSQL.append(" ").append(qual);
		return stmtSQL.toString();
	}

	private static String getCustomUpdateStmtText(String tblName,
			String[] colNames, String[] colValues, String qual) {
		StringBuffer stmtSQL = new StringBuffer();
		stmtSQL.append(DBUtils.UPDATE).append(tblName);

		stmtSQL.append(DBUtils.SET);
		for (int i = 0; i < colNames.length; i++) {
			if (i > 0) {
				stmtSQL.append(Constants.COMMA);
			}
			stmtSQL.append(colNames[i]).append(DBUtils.IGUAL)
					.append(colValues[i]);
		}

		if (qual != null)
			stmtSQL.append(" ").append(qual);
		return stmtSQL.toString();
	}

	public static void updateShortDecimal(DbConnection conn, String tblName,
			String colName, float colValue, String qual) throws Exception {

		updateShortDecimal(conn, tblName, colName, colValue, qual,
				Constants.BLANK);
	}

	public static void updateShortDecimal(DbConnection conn, String tblName,
			String colName, float colValue, String qual, String withQuery)
			throws Exception {

		DbUpdateStatement stmt = null;

		try {

			stmt = new DbUpdateStatement();
			stmt.create(conn, tblName, colName, qual, withQuery);

			stmt.setShortDecimal(1, colValue);

			stmt.execute();
			stmt.release();

		} catch (Exception e) {
			DbUpdateStatement.ensureRelease(stmt, e);
		}

	}

	public static void updateLongDecimal(DbConnection conn, String tblName,
			String colName, double colValue, String qual) throws Exception {

		updateLongDecimal(conn, tblName, colName, colValue, qual,
				Constants.BLANK);
	}

	public static void updateLongDecimal(DbConnection conn, String tblName,
			String colName, double colValue, String qual, String withQuery)
			throws Exception {

		DbUpdateStatement stmt = null;

		try {

			stmt = new DbUpdateStatement();
			stmt.create(conn, tblName, colName, qual, withQuery);

			stmt.setLongDecimal(1, colValue);

			stmt.execute();
			stmt.release();

		} catch (Exception e) {
			DbUpdateStatement.ensureRelease(stmt, e);
		}

	}

	public static int updateDateTime(DbConnection conn, String tblName,
			String colName, Date colValue, String qual) throws Exception {

		return updateDateTime(conn, tblName, colName, colValue, qual,
				Constants.BLANK);
	}

	public static int updateDateTime(DbConnection conn, String tblName,
			String colName, Date colValue, String qual, String withQuery)
			throws Exception {

		DbUpdateStatement stmt = null;
		int result = 0;
		try {

			stmt = new DbUpdateStatement();
			stmt.create(conn, tblName, colName, qual, withQuery);

			stmt.setDateTime(1, colValue);

			result = stmt.execute();
			stmt.release();

		} catch (Exception e) {
			DbUpdateStatement.ensureRelease(stmt, e);
			result = 0;
		}
		return result;
	}

} // class
