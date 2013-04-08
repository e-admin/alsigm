package ieci.core.db;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

import common.startup.ProfileLogLevel;

public class DbOutputStatement {
	protected static final Logger PROFILE_LOGGER = Logger.getLogger("PROFILE");

	private Statement m_jdbcStmt;
	private ResultSet m_jdbcRS;
	private String m_stmtText;
	private DbConnection m_conn;

	public DbOutputStatement() {
		m_jdbcStmt = null;
		m_jdbcRS = null;
		m_stmtText = null;
	}

	public void create(DbConnection conn, String stmtText) throws Exception {
		m_jdbcStmt = conn.getJdbcConnection().createStatement();
		m_stmtText = stmtText;
		m_conn = conn;
	}

	public void createScrollable(DbConnection conn, String stmtText)
			throws Exception {
		m_jdbcStmt = conn.getJdbcConnection().createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		m_stmtText = stmtText;
		m_conn = conn;
	}

	public void release() throws Exception {

		m_stmtText = null;

		if (m_jdbcRS != null) {
			m_jdbcRS.close();
			m_jdbcRS = null;
		}

		if (m_jdbcStmt != null) {
			m_jdbcStmt.close();
			m_jdbcStmt = null;
		}

	}

	public void execute() throws Exception {
		if (PROFILE_LOGGER.isEnabledFor(ProfileLogLevel.BEGIN_QUERY)) {
			StringBuffer logMessage = new StringBuffer()
					.append(System.currentTimeMillis()).append(" [")
					.append(m_stmtText).append("]");
			PROFILE_LOGGER.log(ProfileLogLevel.BEGIN_QUERY, logMessage);
		}

		m_jdbcRS = m_jdbcStmt.executeQuery(m_stmtText);

		if (PROFILE_LOGGER.isEnabledFor(ProfileLogLevel.END_QUERY))
			PROFILE_LOGGER.log(ProfileLogLevel.END_QUERY,
					String.valueOf(System.currentTimeMillis()));

	}

	public int getColumnCount() throws Exception {
		ResultSetMetaData rsMetaData = m_jdbcRS.getMetaData();

		int numberOfColumns = 0;
		if (rsMetaData != null) {
			numberOfColumns = rsMetaData.getColumnCount();
		}
		return numberOfColumns;
	}

	public String[] getMetadataColumns() throws Exception {
		ResultSetMetaData rsMetaData = m_jdbcRS.getMetaData();

		int numberOfColumns = 0;
		String[] res = null;
		if (rsMetaData != null) {
			numberOfColumns = rsMetaData.getColumnCount();
			if (numberOfColumns > 0) {
				res = new String[numberOfColumns];
			}
			for (int i = 0; i < numberOfColumns; i++) {
				res[i] = rsMetaData.getColumnName(i + 1) + "("
						+ rsMetaData.getColumnTypeName(i + 1) + ")";
			}
		}
		return res;
	}

	public boolean next() throws Exception {
		return m_jdbcRS.next();
	}

	public boolean absolut(int row) throws Exception {
		return m_jdbcRS.absolute(row);
	}

	public boolean last() throws Exception {
		return m_jdbcRS.last();
	}

	public int getRow() throws Exception {
		return m_jdbcRS.getRow();
	}

	public String getShortText(int index) throws Exception {

		String val;

		val = m_jdbcRS.getString(index);
		if (m_jdbcRS.wasNull())
			val = DbDataType.NULL_SHORT_TEXT;

		return val;

	}

	public String getLongText(int index) throws Exception {

		String val = null;
		int length;
		Clob clob = null;

		if (m_conn.getEngine() == DbEngine.ORACLE) {
			clob = m_jdbcRS.getClob(index);
			if (clob != null) {
				length = (int) clob.length();
				val = clob.getSubString(1, length);
				clob = null;
			}
		} else
			val = m_jdbcRS.getString(index);

		if (m_jdbcRS.wasNull())
			val = DbDataType.NULL_LONG_TEXT;

		return val;

	}

	public short getShortInteger(int index) throws Exception {

		short val;

		val = m_jdbcRS.getShort(index);
		if (m_jdbcRS.wasNull())
			val = DbDataType.NULL_SHORT_INTEGER;

		return val;

	}

	public int getLongInteger(int index) throws Exception {

		int val;

		val = m_jdbcRS.getInt(index);
		if (m_jdbcRS.wasNull())
			val = DbDataType.NULL_LONG_INTEGER;

		return val;

	}

	public long getLong(int index) throws Exception {
		long val;

		val = m_jdbcRS.getLong(index);
		if (m_jdbcRS.wasNull())
			val = DbDataType.NULL_LONG;

		return val;
	}

	public float getShortDecimal(int index) throws Exception {

		float val;

		val = m_jdbcRS.getFloat(index);
		if (m_jdbcRS.wasNull())
			val = DbDataType.NULL_SHORT_DECIMAL;

		return val;

	}

	public double getLongDecimal(int index) throws Exception {

		double val;

		val = m_jdbcRS.getDouble(index);
		if (m_jdbcRS.wasNull())
			val = DbDataType.NULL_LONG_DECIMAL;

		return val;

	}

	public Date getDateTime(int index) throws Exception {

		Date val;
		Timestamp ts;

		ts = m_jdbcRS.getTimestamp(index);
		if (m_jdbcRS.wasNull())
			val = DbDataType.NULL_DATE_TIME;
		else
			val = new Date(ts.getTime());

		return val;

	}

	public static void ensureRelease(DbOutputStatement stmt, Exception exc)
			throws Exception {

		try {
			if (stmt != null)
				stmt.release();
			throw exc;
		} catch (Exception e) {
			throw exc;
		}

	}

} // class
