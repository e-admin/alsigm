package ieci.core.guid;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbTableFns;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;

final class GuidDaoGenTbl {

	// **************************************************************************

	private static final String TABLE_NAME = "ITDGUIDGEN";

	// MAC Address
	private static final DbColumnDef CD_NODE = new DbColumnDef("CNODE",
			DbDataType.SHORT_TEXT, 12, false);

	// Last Proccess Id
	private static final DbColumnDef CD_LPID = new DbColumnDef("CLPID",
			DbDataType.LONG_INTEGER, false);

	private static final DbColumnDef[] ACD = { CD_NODE, CD_LPID };

	private static final String ACN = DbUtil.getColumnNames(ACD);

	// **************************************************************************

	public GuidDaoGenTbl() {
	}

	// **************************************************************************

	public static void createTable(DbConnection conn) throws Exception {
		DbTableFns.createTable(conn, TABLE_NAME, ACD);
	}

	static void dropTable(DbConnection conn) throws Exception {
		DbTableFns.dropTable(conn, TABLE_NAME);
	}

	// **************************************************************************

	public static void insertRow(DbConnection conn, GuidDaoGenRecAc rec)
			throws Exception {
		DbInsertFns.insert(conn, TABLE_NAME, ACN, rec);
	}

	// **************************************************************************

	public static void deleteRow(DbConnection conn) throws Exception {
		DbDeleteFns.delete(conn, TABLE_NAME, null);
	}

	// **************************************************************************

	public static void updateRow(DbConnection conn, GuidDaoGenRecAc rec)
			throws Exception {
		DbUpdateFns.update(conn, TABLE_NAME, ACN, rec, null);
	}

	public static void incrementLpid(DbConnection conn) throws Exception {

		String stmtText;

		stmtText = "UPDATE " + TABLE_NAME + " SET " + CD_LPID.getName() + "="
				+ CD_LPID.getName() + "+1";

		DbUtil.executeStatement(conn, stmtText);

	}

	public static void resetLpid(DbConnection conn) throws Exception {

		String stmtText;

		stmtText = "UPDATE " + TABLE_NAME + " SET " + CD_LPID.getName() + "=0";

		DbUtil.executeStatement(conn, stmtText);

	}

	// **************************************************************************

	public static GuidDaoGenRecAc selectRecAc(DbConnection conn)
			throws Exception {

		GuidDaoGenRecAc rec = new GuidDaoGenRecAc();

		DbSelectFns.select(conn, TABLE_NAME, ACN, null, rec);

		return rec;

	}

	public static String selectNode(DbConnection conn) throws Exception {
		return DbSelectFns.selectShortText(conn, TABLE_NAME, CD_NODE.getName(),
				null);
	}

} // class
