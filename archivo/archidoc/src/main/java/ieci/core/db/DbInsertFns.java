package ieci.core.db;

public final class DbInsertFns {

	private DbInsertFns() {
	}

	public static void insert(DbConnection conn, String tblName,
			String colNames, DbInputRecord colValues) throws Exception {
		DbInsertStatement stmt = null;

		try {

			String finalColNames = DbUtil.getColNamesForInsertSentenceSyntax(
					conn, colNames);
			stmt = new DbInsertStatement();
			stmt.create(conn, tblName, finalColNames);

			colValues.setStatementValues(stmt);

			stmt.execute();
			stmt.release();

		} catch (Exception e) {
			DbInsertStatement.ensureRelease(stmt, e);
		}

	}

	public static void insertAsSelect(DbConnection conn, String tblName,
			String colNames, String selectInsert) throws Exception {
		DbInsertStatement stmt = null;

		try {

			String finalColNames = DbUtil.getColNamesForInsertSentenceSyntax(
					conn, colNames);
			stmt = new DbInsertStatement();

			stmt.create(conn, tblName, finalColNames, selectInsert);

			stmt.execute();
			stmt.release();

		} catch (Exception e) {
			DbInsertStatement.ensureRelease(stmt, e);
		}

	}

} // class
