package ieci.core.db;

public final class DbInsertStatement extends DbInputStatement {

	public DbInsertStatement() {
		super();
	}

	public void create(DbConnection conn, String tblName, String colNames)
			throws Exception {
		super.create(conn, getInsertStmtText(tblName, colNames));
	}

	public void create(DbConnection conn, String stmtText) throws Exception {
		super.create(conn, stmtText);
	}

	public void create(DbConnection conn, String tblName, String colNames,
			String sqlInsert) throws Exception {
		String sql = getInsertAsSelectStmtText(tblName, colNames) + " "
				+ sqlInsert;
		super.create(conn, sql);
	}

	// **************************************************************************

	private static String getInsertStmtText(String tblName, String colNames) {

		StringBuffer tbdr;
		int index, count, i;

		index = 0;
		count = 0;

		while (index != -1) {
			count++;
			index = colNames.indexOf(',', index + 1);
		}
		tbdr = new StringBuffer();

		// obtener los nombres de columna en funcion de la base de datos sobre
		// la q trabajamos

		tbdr.append("INSERT INTO ").append(tblName);
		tbdr.append(" (").append(colNames).append(") VALUES (?");

		for (i = 1; i < count; i++)
			tbdr.append(",?");

		tbdr.append(")");

		return tbdr.toString();

	}

	// **************************************************************************

	private static String getInsertAsSelectStmtText(String tblName,
			String colNames) {

		StringBuffer tbdr;
		int index, count;

		index = 0;
		count = 0;

		while (index != -1) {
			count++;
			index = colNames.indexOf(',', index + 1);
		}
		tbdr = new StringBuffer();

		tbdr.append("INSERT INTO ").append(tblName);
		tbdr.append(" (").append(colNames).append(") ");

		return tbdr.toString();

	}

} // class
