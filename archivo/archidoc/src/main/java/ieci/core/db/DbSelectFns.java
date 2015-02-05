package ieci.core.db;

import ieci.core.collections.IeciTdDateTimeArrayList;
import ieci.core.collections.IeciTdLongDecimalArrayList;
import ieci.core.collections.IeciTdLongIntegerArrayList;
import ieci.core.collections.IeciTdLongTextArrayList;
import ieci.core.collections.IeciTdShortDecimalArrayList;
import ieci.core.collections.IeciTdShortIntegerArrayList;
import ieci.core.collections.IeciTdShortTextArrayList;
import ieci.core.exception.IeciTdException;

import java.util.Date;

import common.Constants;
import common.db.DBUtils;
import common.pagination.PageInfo;
import common.util.StringUtils;
import common.vos.ConsultaConnectBy;

public final class DbSelectFns {

	private DbSelectFns() {
	}

	public static void selectUnion(DbConnection conn, String tblNames1,
			String tblNames2, String colNames1, String colNames2, String qual1,
			String qual2, String orderBy, boolean distinct, DbOutputRecordSet rs)
			throws Exception {

		DbSelectStatement stmt = null;
		DbOutputRecord rec;
		int count = 0;

		try {
			if (rs.getMaxNumItems() > 0) {
				stmt = new DbSelectStatement();
				String sql1 = stmt.create(conn, tblNames1, colNames1, qual1,
						distinct);
				String sql2 = stmt.create(conn, tblNames2, colNames2, qual2,
						distinct);
				String sqlUnion = "(" + sql1 + ")" + " UNION " + "(" + sql2
						+ ")";

				if (!StringUtils.isBlank(orderBy)) {
					sqlUnion += orderBy;
				}

				stmt.create(conn, sqlUnion);
				stmt.execute();
				// int count = 0;
				while (stmt.next() && count++ < rs.getMaxNumItems()) {
					rec = rs.newRecord();
					rec.getStatementValues(stmt);
				}

				stmt.release();
			}
		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
		}

	}

	public static void selectUnionAll(DbConnection conn, String tblNames1,
			String tblNames2, String colNames1, String colNames2, String qual1,
			String qual2, String orderBy, boolean distinct, DbOutputRecordSet rs)
			throws Exception {

		DbSelectStatement stmt = null;
		DbOutputRecord rec;
		int count = 0;

		try {
			if (rs.getMaxNumItems() > 0) {
				stmt = new DbSelectStatement();
				String sql1 = stmt.create(conn, tblNames1, colNames1, qual1,
						distinct);
				String sql2 = stmt.create(conn, tblNames2, colNames2, qual2,
						distinct);
				String sqlUnion = "(" + sql1 + ")" + " UNION ALL " + "(" + sql2
						+ ")";

				if (!StringUtils.isBlank(orderBy)) {
					sqlUnion += orderBy;
				}

				stmt.create(conn, sqlUnion);
				stmt.execute();
				// int count = 0;
				while (stmt.next() && count++ < rs.getMaxNumItems()) {
					rec = rs.newRecord();
					rec.getStatementValues(stmt);
				}

				stmt.release();
			}
		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
		}

	}

	public static void select(DbConnection conn, String tblNames,
			String colNames, String qual, DbOutputRecord rec) throws Exception {

		DbSelectStatement stmt = null;

		try {

			stmt = new DbSelectStatement();
			/* String sql = */stmt
					.create(conn, tblNames, colNames, qual, false);
			stmt.execute();

			if (stmt.next())
				rec.getStatementValues(stmt);
			else
				throw new IeciTdException(DbError.EC_NOT_FOUND,
						DbError.EM_NOT_FOUND);

			stmt.release();

		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
		}

	}

	public static void select(DbConnection conn, String tblNames,
			String colNames, String qual, String hint, DbOutputRecord rec)
			throws Exception {

		DbSelectStatement stmt = null;

		try {

			stmt = new DbSelectStatement();

			if (conn.isAllowHint()) {
				/* String sql = */stmt.create(conn, null, tblNames, colNames,
						qual, false, hint);
			} else {
				/* String sql = */stmt.create(conn, null, tblNames, colNames,
						qual, false);
			}
			stmt.execute();

			if (stmt.next())
				rec.getStatementValues(stmt);
			else
				throw new IeciTdException(DbError.EC_NOT_FOUND,
						DbError.EM_NOT_FOUND);

			stmt.release();

		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
		}

	}

	public static void select(DbConnection conn, String sqlCompleta,
			DbOutputRecord rec) throws Exception {
		DbSelectStatement stmt = null;

		try {
			stmt = new DbSelectStatement();

			stmt.create(conn, sqlCompleta);
			stmt.execute();

			if (stmt.next())
				rec.getStatementValues(stmt);
			else
				throw new IeciTdException(DbError.EC_NOT_FOUND,
						DbError.EM_NOT_FOUND);

			stmt.release();

		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
		}

	}

	public static void select(DbConnection conn, String tblNames,
			String colNames, String qual, boolean distinct, DbOutputRecordSet rs)
			throws Exception {

		select(conn, null, tblNames, colNames, qual, distinct, rs);
	}

	public static void select(DbConnection conn, String with, String tblNames,
			String colNames, String qual, boolean distinct, DbOutputRecordSet rs)
			throws Exception {

		DbSelectStatement stmt = null;
		DbOutputRecord rec;
		int count = 0;

		try {
			if (rs.getMaxNumItems() > 0) {
				stmt = new DbSelectStatement();
				stmt.create(conn, with, tblNames, colNames, qual, distinct);
				stmt.execute();
				while (stmt.next() && count++ < rs.getMaxNumItems()) {
					rec = rs.newRecord();
					rec.getStatementValues(stmt);
				}

				stmt.release();
			}
		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
		}
	}

	public static void select(DbConnection conn, String tblNames,
			String colNames, String qual, boolean distinct, String hint,
			DbOutputRecordSet rs) throws Exception {

		DbSelectStatement stmt = null;
		DbOutputRecord rec;

		try {
			if (rs.getMaxNumItems() > 0) {
				stmt = new DbSelectStatement();

				if (conn.isAllowHint()) {
					/* String sql = */stmt.create(conn, null, tblNames,
							colNames, qual, distinct, hint);
				} else {
					/* String sql = */stmt.create(conn, null, tblNames,
							colNames, qual, distinct);
				}
				stmt.execute();
				int count = 0;
				while (stmt.next() && count++ < rs.getMaxNumItems()) {
					rec = rs.newRecord();
					rec.getStatementValues(stmt);
				}

				stmt.release();
			}
		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
		}

	}

	public static void select(DbConnection conn, String tblNames,
			String colNames, String qual, boolean distinct,
			DbOutputPaginatedRecordSet rs, PageInfo pageInfo) throws Exception {
		DbSelectStatement stmt = null;
		DbOutputRecord rec;

		try {
			stmt = new DbSelectStatement();
			/* String sql = */stmt.createScrollable(conn, tblNames, colNames,
					qual, distinct);
			stmt.execute();

			int initialRecordNumber = pageInfo.getInitialRecordNumber();
			if (initialRecordNumber > 0)
				stmt.absolut(initialRecordNumber);

			int count = 0;
			while (stmt.next() && count++ < rs.getMaxNumItems()) {
				rec = rs.newRecord();
				rec.getStatementValues(stmt);
			}

			stmt.release();
		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
		}

	}

	public static void select(DbConnection conn, String tblNames,
			String colNames, String qual, boolean distinct, String hint,
			DbOutputPaginatedRecordSet rs, PageInfo pageInfo) throws Exception {
		DbSelectStatement stmt = null;
		DbOutputRecord rec;

		try {
			stmt = new DbSelectStatement();

			if (conn.isAllowHint()) {
				/* String sql = */stmt.createScrollable(conn, tblNames,
						colNames, qual, distinct, hint);
			} else {
				/* String sql = */stmt.createScrollable(conn, tblNames,
						colNames, qual, distinct);

			}
			stmt.execute();

			int initialRecordNumber = pageInfo.getInitialRecordNumber();
			if (initialRecordNumber > 0)
				stmt.absolut(initialRecordNumber);

			int count = 0;
			while (stmt.next() && count++ < rs.getMaxNumItems()) {
				rec = rs.newRecord();
				rec.getStatementValues(stmt);
			}

			stmt.release();
		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
		}

	}

	/**
	 * Ejecuta la consulta que se le pasa
	 * 
	 * @param conn
	 * @param sql
	 * @param rec
	 * @throws Exception
	 */
	public static void select(DbConnection conn, String sqlCompleta,
			DbOutputRecordSet rs) throws Exception {
		DbSelectStatement stmt = null;
		DbOutputRecord rec;
		try {
			stmt = new DbSelectStatement();
			stmt.create(conn, sqlCompleta);
			stmt.execute();

			int count = 0;
			while (stmt.next() && count++ < rs.getMaxNumItems()) {
				rec = rs.newRecord();
				rec.getStatementValues(stmt);
			}

			stmt.release();

		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
		}
	}

	public static void select(DbConnection conn, String sqlCompleta,
			DbOutputPaginatedRecordSet rs, PageInfo pageInfo) throws Exception {
		DbSelectStatement stmt = null;
		DbOutputRecord rec;

		try {
			stmt = new DbSelectStatement();
			/* String sql = */stmt.createScrollable(conn, sqlCompleta);
			stmt.execute();

			int initialRecordNumber = pageInfo.getInitialRecordNumber();
			if (initialRecordNumber > 0)
				stmt.absolut(initialRecordNumber);

			int count = 0;
			while (stmt.next() && count++ < rs.getMaxNumItems()) {
				rec = rs.newRecord();
				rec.getStatementValues(stmt);
			}

			stmt.release();
		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
		}

	}

	public static String selectShortText(DbConnection conn, String tblName,
			String colName, String qual) throws Exception {

		String val = DbDataType.NULL_SHORT_TEXT;
		DbSelectStatement stmt = null;

		try {

			stmt = new DbSelectStatement();
			/* String sql = */stmt.create(conn, tblName, colName, qual, false);
			stmt.execute();

			if (stmt.next()) {
				val = stmt.getShortText(1);
				stmt.release();
				return val;
			} else
				throw new IeciTdException(DbError.EC_NOT_FOUND,
						DbError.EM_NOT_FOUND);

		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
			throw e;
		}

	}

	public static String selectLongText(DbConnection conn, String tblName,
			String colName, String qual) throws Exception {

		String val = DbDataType.NULL_LONG_TEXT;
		DbSelectStatement stmt = null;

		try {

			stmt = new DbSelectStatement();
			/* String sql = */stmt.create(conn, tblName, colName, qual, false);
			stmt.execute();

			if (stmt.next()) {
				val = stmt.getLongText(1);
				stmt.release();
				return val;
			} else
				throw new IeciTdException(DbError.EC_NOT_FOUND,
						DbError.EM_NOT_FOUND);

		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
			throw e;
		}

	}

	public static short selectShortInteger(DbConnection conn, String tblName,
			String colName, String qual) throws Exception {

		short val = DbDataType.NULL_SHORT_INTEGER;
		DbSelectStatement stmt = null;

		try {

			stmt = new DbSelectStatement();
			/* String sql = */stmt.create(conn, tblName, colName, qual, false);
			stmt.execute();

			if (stmt.next()) {
				val = stmt.getShortInteger(1);
				stmt.release();
				return val;
			} else
				throw new IeciTdException(DbError.EC_NOT_FOUND,
						DbError.EM_NOT_FOUND);

		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
			throw e;
		}

	}

	public static int selectLongInteger(DbConnection conn, String tblName,
			String colName, String qual) throws Exception {

		int val = DbDataType.NULL_LONG_INTEGER;
		DbSelectStatement stmt = null;

		try {

			stmt = new DbSelectStatement();
			/* String sql = */stmt.create(conn, tblName, colName, qual, false);
			stmt.execute();

			if (stmt.next()) {
				val = stmt.getLongInteger(1);
				stmt.release();
				return val;
			} else
				throw new IeciTdException(DbError.EC_NOT_FOUND,
						DbError.EM_NOT_FOUND);

		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
			throw e;
		}
	}

	public static long selectLong(DbConnection conn, String tblName,
			String colName, String qual) throws Exception {

		long val = DbDataType.NULL_LONG;
		DbSelectStatement stmt = null;

		try {
			stmt = new DbSelectStatement();
			/* String sql = */stmt.create(conn, tblName, colName, qual, false);
			stmt.execute();

			if (stmt.next()) {
				val = stmt.getLong(1);
				stmt.release();
				return val;
			} else
				throw new IeciTdException(DbError.EC_NOT_FOUND,
						DbError.EM_NOT_FOUND);
		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
			throw e;
		}
	}

	public static float selectShortDecimal(DbConnection conn, String tblName,
			String colName, String qual) throws Exception {

		float val = DbDataType.NULL_SHORT_DECIMAL;
		DbSelectStatement stmt = null;

		try {

			stmt = new DbSelectStatement();
			/* String sql = */stmt.create(conn, tblName, colName, qual, false);
			stmt.execute();

			if (stmt.next()) {
				val = stmt.getShortDecimal(1);
				stmt.release();
				return val;
			} else
				throw new IeciTdException(DbError.EC_NOT_FOUND,
						DbError.EM_NOT_FOUND);

		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
			throw e;
		}

	}

	public static double selectLongDecimal(DbConnection conn, String tblName,
			String colName, String qual) throws Exception {

		double val = DbDataType.NULL_LONG_DECIMAL;
		DbSelectStatement stmt = null;

		try {

			stmt = new DbSelectStatement();
			/* String sql = */stmt.create(conn, tblName, colName, qual, false);
			stmt.execute();

			if (stmt.next()) {
				val = stmt.getLongDecimal(1);
				stmt.release();
				return val;
			} else
				throw new IeciTdException(DbError.EC_NOT_FOUND,
						DbError.EM_NOT_FOUND);

		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
			throw e;
		}

	}

	public static Date selectDateTime(DbConnection conn, String tblName,
			String colName, String qual) throws Exception {

		Date val = DbDataType.NULL_DATE_TIME;
		DbSelectStatement stmt = null;

		try {

			stmt = new DbSelectStatement();
			/* String sql = */stmt.create(conn, tblName, colName, qual, false);
			stmt.execute();

			if (stmt.next()) {
				val = stmt.getDateTime(1);
				stmt.release();
				return val;
			} else
				throw new IeciTdException(DbError.EC_NOT_FOUND,
						DbError.EM_NOT_FOUND);

		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
			throw e;
		}

	}

	public static void select(DbConnection conn, String tblName,
			String colName, String qual, String withQuery, boolean distinct,
			IeciTdShortTextArrayList vals) throws Exception {

		DbSelectStatement stmt = null;

		try {

			stmt = new DbSelectStatement();
			/* String sql = */stmt.create(conn, withQuery, tblName, colName,
					qual, distinct);
			stmt.execute();

			while (stmt.next()) {
				vals.add(stmt.getShortText(1));
			}

			stmt.release();

		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
		}
	}

	// Con columnas de tipo LONG no se puede poner la clausula distinct en
	// una sentencia select
	public static void select(DbConnection conn, String tblName,
			String colName, String qual, IeciTdLongTextArrayList vals)
			throws Exception {

		DbSelectStatement stmt = null;

		try {

			stmt = new DbSelectStatement();
			/* String sql = */stmt.create(conn, tblName, colName, qual, false);
			stmt.execute();

			while (stmt.next()) {
				vals.add(stmt.getLongText(1));
			}

			stmt.release();

		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
		}
	}

	public static void select(DbConnection conn, String tblName,
			String colName, String qual, boolean distinct,
			IeciTdShortIntegerArrayList vals) throws Exception {

		DbSelectStatement stmt = null;

		try {

			stmt = new DbSelectStatement();
			/* String sql = */stmt.create(conn, tblName, colName, qual,
					distinct);
			stmt.execute();

			while (stmt.next()) {
				vals.add(stmt.getShortInteger(1));
			}

			stmt.release();

		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
		}
	}

	public static void select(DbConnection conn, String tblName,
			String colName, String qual, boolean distinct,
			IeciTdLongIntegerArrayList vals) throws Exception {

		DbSelectStatement stmt = null;

		try {

			stmt = new DbSelectStatement();
			/* String sql = */stmt.create(conn, tblName, colName, qual,
					distinct);
			stmt.execute();

			while (stmt.next()) {
				vals.add(stmt.getLongInteger(1));
			}

			stmt.release();

		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
		}
	}

	public static void select(DbConnection conn, String tblName,
			String colName, String qual, boolean distinct,
			IeciTdShortDecimalArrayList vals) throws Exception {

		DbSelectStatement stmt = null;

		try {

			stmt = new DbSelectStatement();
			/* String sql = */stmt.create(conn, tblName, colName, qual,
					distinct);
			stmt.execute();

			while (stmt.next()) {
				vals.add(stmt.getShortDecimal(1));
			}

			stmt.release();

		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
		}
	}

	public static void select(DbConnection conn, String tblName,
			String colName, String qual, boolean distinct,
			IeciTdLongDecimalArrayList vals) throws Exception {

		DbSelectStatement stmt = null;

		try {

			stmt = new DbSelectStatement();
			/* String sql = */stmt.create(conn, tblName, colName, qual,
					distinct);
			stmt.execute();

			while (stmt.next()) {
				vals.add(stmt.getLongDecimal(1));
			}

			stmt.release();

		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
		}
	}

	public static void select(DbConnection conn, String tblName,
			String colName, String qual, boolean distinct,
			IeciTdDateTimeArrayList vals) throws Exception {

		DbSelectStatement stmt = null;

		try {

			stmt = new DbSelectStatement();
			/* String sql = */stmt.create(conn, tblName, colName, qual,
					distinct);
			stmt.execute();

			while (stmt.next()) {
				vals.add(stmt.getDateTime(1));
			}

			stmt.release();

		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
		}
	}

	public static int selectMaxInt(DbConnection conn, String tblName,
			String colName, String qual) throws Exception {
		return selectMaxInt(conn, tblName, colName, colName, qual);
	}

	public static int selectMaxInt(DbConnection conn, String tblName,
			String colName, String alias, String qual) throws Exception {

		int val = 0;
		DbSelectStatement stmt = null;
		String stmtText;

		try {

			stmt = new DbSelectStatement();

			if (qual == null)
				stmtText = "SELECT MAX(" + colName + ") AS " + alias + " FROM "
						+ tblName;
			else
				stmtText = "SELECT MAX(" + colName + ") AS " + alias + " FROM "
						+ tblName + " " + qual;

			stmt.create(conn, stmtText);
			stmt.execute();

			if (stmt.next()) {
				val = stmt.getShortInteger(1);
				stmt.release();
			}
			/*
			 * else throw new IeciTdException(DbError.EC_NOT_FOUND,
			 * DbError.EM_NOT_FOUND);
			 */

			stmt.release();

			if (val == DbDataType.NULL_SHORT_INTEGER)
				val = 0;
			return val;

		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
			throw e;
		}
	}

	public static long selectMaxLong(DbConnection conn, String tblName,
			String colName, String qual) throws Exception {
		return selectMaxLong(conn, tblName, colName, colName, qual);
	}

	public static long selectMaxLong(DbConnection conn, String tblName,
			String colName, String alias, String qual) throws Exception {
		long val = 0;
		DbSelectStatement stmt = null;
		String stmtText;

		try {
			stmt = new DbSelectStatement();

			if (qual == null)
				stmtText = "SELECT MAX(" + colName + ") AS " + alias + " FROM "
						+ tblName;
			else
				stmtText = "SELECT MAX(" + colName + ") AS " + alias + " FROM "
						+ tblName + " " + qual;

			stmt.create(conn, stmtText);
			stmt.execute();

			if (stmt.next()) {
				val = stmt.getLong(1);
				stmt.release();
			}
			/*
			 * else throw new IeciTdException(DbError.EC_NOT_FOUND,
			 * DbError.EM_NOT_FOUND);
			 */

			stmt.release();

			if (val == DbDataType.NULL_LONG)
				val = 0;
			return val;
		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
			throw e;
		}
	}

	public static long selectMaxString(DbConnection conn, String tblName,
			String colName, String qual) throws Exception {
		long val = 0;
		DbSelectStatement stmt = null;
		String stmtText;
		try {
			stmt = new DbSelectStatement();

			if (qual == null)
				stmtText = "SELECT MAX("
						+ DBUtils.getNativeToNumberSyntax(conn, colName, 16)
						+ ") AS " + colName + " FROM " + tblName;
			else
				stmtText = "SELECT MAX("
						+ DBUtils.getNativeToNumberSyntax(conn, colName, 16)
						+ ") AS " + colName + " FROM " + tblName + " " + qual;

			stmt.create(conn, stmtText);
			stmt.execute();
			if (stmt.next()) {
				val = stmt.getLong(1);
				stmt.release();
			}
			stmt.release();
			if (val == DbDataType.NULL_SHORT_INTEGER)
				val = 0;
			return val;
		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
			throw e;
		}
	}

	public static Date selectMaxDate(DbConnection conn, String tblName,
			String colName, String qual) throws Exception {
		Date val = null;
		DbSelectStatement stmt = null;
		String stmtText;
		try {
			stmt = new DbSelectStatement();
			if (qual == null)
				stmtText = "SELECT MAX(" + colName + ") AS " + colName
						+ " FROM " + tblName;
			else
				stmtText = "SELECT MAX(" + colName + ") AS " + colName
						+ " FROM " + tblName + " " + qual;

			stmt.create(conn, stmtText);
			stmt.execute();
			if (stmt.next()) {
				val = stmt.getDateTime(1);
				stmt.release();
			}
			stmt.release();
			if (val == DbDataType.NULL_DATE_TIME)
				val = null;
			return val;
		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
			throw e;
		}
	}

	public static int selectMinInt(DbConnection conn, String tblName,
			String colName, String qual) throws Exception {
		int val = 0;
		DbSelectStatement stmt = null;
		String stmtText;
		try {
			stmt = new DbSelectStatement();
			if (qual == null)
				stmtText = "SELECT MIN(" + colName + ") AS " + colName
						+ " FROM " + tblName;
			else
				stmtText = "SELECT MIN(" + colName + ") AS " + colName
						+ " FROM " + tblName + " " + qual;

			stmt.create(conn, stmtText);
			stmt.execute();

			if (stmt.next()) {
				val = stmt.getShortInteger(1);
				stmt.release();
			}
			stmt.release();
			if (val == DbDataType.NULL_SHORT_INTEGER)
				val = 0;
			return val;
		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
			throw e;
		}
	}

	public static long selectMinString(DbConnection conn, String tblName,
			String colName, String qual) throws Exception {
		long val = 0;
		DbSelectStatement stmt = null;
		String stmtText;
		try {
			stmt = new DbSelectStatement();
			if (qual == null)
				stmtText = "SELECT MIN("
						+ DBUtils.getNativeToNumberSyntax(conn, colName, 16)
						+ ") AS " + colName + " FROM " + tblName;
			else
				stmtText = "SELECT MIN("
						+ DBUtils.getNativeToNumberSyntax(conn, colName, 16)
						+ ") AS " + colName + " FROM " + tblName + " " + qual;

			stmt.create(conn, stmtText);
			stmt.execute();

			if (stmt.next()) {
				val = stmt.getLong(1);
				stmt.release();
			}
			stmt.release();
			if (val == DbDataType.NULL_SHORT_INTEGER)
				val = 0;
			return val;
		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
			throw e;
		}
	}

	public static Date selectMinDate(DbConnection conn, String tblName,
			String colName, String qual) throws Exception {
		Date val = null;
		DbSelectStatement stmt = null;
		String stmtText;
		try {
			stmt = new DbSelectStatement();
			if (qual == null)
				stmtText = "SELECT MIN(" + colName + ") AS " + colName
						+ " FROM " + tblName;
			else
				stmtText = "SELECT MIN(" + colName + ") AS " + colName
						+ " FROM " + tblName + " " + qual;

			stmt.create(conn, stmtText);
			stmt.execute();

			if (stmt.next()) {
				val = stmt.getDateTime(1);
				stmt.release();
			}
			stmt.release();
			if (val == DbDataType.NULL_DATE_TIME)
				val = null;
			return val;
		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
			throw e;
		}
	}

	public static long selectMinLong(DbConnection conn, String tblName,
			String colName, String qual) throws Exception {
		return selectMinLong(conn, tblName, colName, colName, qual);
	}

	public static long selectMinLong(DbConnection conn, String tblName,
			String colName, String alias, String qual) throws Exception {
		long val = 0;
		DbSelectStatement stmt = null;
		String stmtText;

		try {
			stmt = new DbSelectStatement();

			if (qual == null)
				stmtText = "SELECT MIN(" + colName + ") AS " + alias + " FROM "
						+ tblName;
			else
				stmtText = "SELECT MIN(" + colName + ") AS " + alias + " FROM "
						+ tblName + " " + qual;

			stmt.create(conn, stmtText);
			stmt.execute();

			if (stmt.next()) {
				val = stmt.getShortInteger(1);
				stmt.release();
			}
			stmt.release();

			if (val == DbDataType.NULL_LONG)
				val = 0;
			return val;
		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
			throw e;
		}
	}

	public static Date selectMaxDateTime(DbConnection conn, String tblName,
			String colName, String qual) throws Exception {

		Date val = DbDataType.NULL_DATE_TIME;
		DbSelectStatement stmt = null;
		String stmtText;

		try {
			stmt = new DbSelectStatement();
			if (qual == null)
				stmtText = "SELECT MAX(" + colName + ") AS " + colName
						+ " FROM " + tblName;
			else
				stmtText = "SELECT MAX(" + colName + ") AS " + colName
						+ " FROM " + tblName + " " + qual;

			stmt.create(conn, stmtText);
			stmt.execute();

			if (stmt.next()) {
				val = stmt.getDateTime(1);
				stmt.release();
			}
			/*
			 * else throw new IeciTdException(DbError.EC_NOT_FOUND,
			 * DbError.EM_NOT_FOUND);
			 */

			stmt.release();

			return val;

		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
			throw e;
		}
	}

	public static Date selectMinDateTime(DbConnection conn, String tblName,
			String colName, String qual) throws Exception {

		Date val = DbDataType.NULL_DATE_TIME;
		DbSelectStatement stmt = null;
		String stmtText;

		try {
			stmt = new DbSelectStatement();
			if (qual == null)
				stmtText = "SELECT MIN(" + colName + ") AS " + colName
						+ " FROM " + tblName;
			else
				stmtText = "SELECT MIN(" + colName + ") AS " + colName
						+ " FROM " + tblName + " " + qual;

			stmt.create(conn, stmtText);
			stmt.execute();

			if (stmt.next()) {
				val = stmt.getDateTime(1);
				stmt.release();
			}
			/*
			 * else throw new IeciTdException(DbError.EC_NOT_FOUND,
			 * DbError.EM_NOT_FOUND);
			 */

			stmt.release();

			return val;

		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
			throw e;
		}
	}

	public static int selectCount(DbConnection conn, String tblName, String qual)
			throws Exception {
		return DbSelectFns.selectCount_WITH(conn, null, tblName, qual);
	}

	public static int selectCount_WITH(DbConnection conn, String withQuery,
			String tblName, String qual) throws Exception {
		String stmtText;
		if (qual == null)
			stmtText = "SELECT COUNT(*) FROM " + tblName;
		else
			stmtText = "SELECT COUNT(*) FROM " + tblName + " " + qual;

		if (withQuery != null) {
			stmtText = withQuery + Constants.BLANK + stmtText;
		}

		return selectCountBase(conn, stmtText);

	}

	public static int selectCount(DbConnection conn,
			ConsultaConnectBy consultaConnectBy) throws Exception {
		return selectCountBase(conn, consultaConnectBy.getSqlCount());
	}

	public static double selectSum(DbConnection conn,
			ConsultaConnectBy consultaConnectBy) throws Exception {
		return selectSumBase(conn, consultaConnectBy.getSqlSum());
	}

	private static double selectSumBase(DbConnection conn, String stmtText)
			throws Exception {
		double val = 0;
		DbSelectStatement stmt = null;
		try {
			stmt = new DbSelectStatement();

			stmt.create(conn, stmtText);
			stmt.execute();

			if (stmt.next())
				val = stmt.getLongDecimal(1);
			else
				val = 0;

			stmt.release();

			return val;

		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
			throw e;
		}
	}

	private static int selectCountBase(DbConnection conn, String stmtText)
			throws Exception {
		int val = 0;
		DbSelectStatement stmt = null;
		try {
			stmt = new DbSelectStatement();

			stmt.create(conn, stmtText);
			stmt.execute();

			if (stmt.next())
				val = stmt.getLongInteger(1);
			else
				val = 0;

			stmt.release();

			return val;

		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
			throw e;
		}
	}

	// private static String getStmtTextCount(String withQuery, String tblName,
	// String qual){
	// String stmtText = "";
	//
	// if (qual == null)
	// stmtText = "SELECT COUNT(*) FROM " + tblName;
	// else
	// stmtText = "SELECT COUNT(*) FROM " + tblName + " " + qual;
	//
	// if(withQuery != null){
	// stmtText = withQuery + Constants.BLANK + stmtText;
	// }
	//
	// return stmtText;
	// }

	// private static String getStmtTextCount(String withQuery, String
	// sqlCount){
	// String stmtText = "";
	//
	// stmtText = "SELECT COUNT(*) FROM " + sqlCount;
	//
	// if(withQuery != null){
	// stmtText = withQuery + Constants.BLANK + stmtText;
	// }
	//
	// return stmtText;
	// }

	public static int selectCountDistinct(DbConnection conn, String tblName,
			String qual, String distinctFields) throws Exception {
		return selectCountDistinct(conn, null, tblName, qual, distinctFields);
	}

	public static int selectCountDistinct(DbConnection conn, String with,
			String tblName, String qual, String distinctFields)
			throws Exception {

		int val = 0;
		DbSelectStatement stmt = null;
		String stmtText;

		try {
			stmt = new DbSelectStatement();
			if (qual == null)
				stmtText = "SELECT COUNT(DISTINCT " + distinctFields
						+ ") FROM " + tblName;
			else
				stmtText = "SELECT COUNT(DISTINCT " + distinctFields
						+ ") FROM " + tblName + " " + qual;

			if (with != null) {
				stmtText = with + Constants.BLANK + stmtText;
			}

			stmt.create(conn, stmtText);
			stmt.execute();

			if (stmt.next())
				val = stmt.getLongInteger(1);
			else
				val = 0;

			stmt.release();

			return val;

		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
			throw e;
		}
	}

	public static int selectCount(DbConnection conn, String tblName,
			String colName, String qual, boolean distinct) throws Exception {

		int val = 0;
		DbSelectStatement stmt = null;
		StringBuffer stmtText = new StringBuffer();

		try {
			stmt = new DbSelectStatement();

			stmtText.append("SELECT ");

			stmtText.append("COUNT(");

			if (distinct) {
				stmtText.append("DISTINCT ");
			}

			stmtText.append(colName).append(") FROM ").append(tblName);

			if (qual != null)
				stmtText.append(" ").append(qual);

			stmt.create(conn, stmtText.toString());
			stmt.execute();

			if (stmt.next())
				val = stmt.getLongInteger(1);
			else
				val = 0;

			stmt.release();

			return val;

		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
			throw e;
		}
	}

	public static int selectCount(DbConnection conn, String tblName,
			String qual, String hint) throws Exception {

		int val = 0;
		DbSelectStatement stmt = null;
		StringBuffer stmtText = new StringBuffer();

		try {

			stmt = new DbSelectStatement();

			stmtText.append("SELECT ");

			if (conn.isAllowHint() && hint != null) {
				stmtText.append(hint).append(" ");
			}

			stmtText.append("COUNT(*) FROM ").append(tblName);

			if (qual != null)
				stmtText.append(" ").append(qual);

			stmt.create(conn, stmtText.toString());
			stmt.execute();

			if (stmt.next())
				val = stmt.getLongInteger(1);
			else
				val = 0;

			stmt.release();

			return val;

		} catch (Exception e) {
			DbSelectStatement.ensureRelease(stmt, e);
			throw e;
		}

	}

	public static boolean rowExists(DbConnection conn, String tblName,
			String qual) throws Exception {

		boolean exists = false;
		int count;

		count = selectCount(conn, tblName, qual);
		if (count > 0)
			exists = true;

		return exists;

	}

} // class
