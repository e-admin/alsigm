package ieci.core.db;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

import common.startup.ProfileLogLevel;

public class DbInputStatement {
	protected static final Logger PROFILE_LOGGER = Logger.getLogger("PROFILE");
	protected static final Logger logger = Logger
			.getLogger(DbInputStatement.class);

	private String m_stmtText = null;
	private PreparedStatement m_jdbcStmt;

	public DbInputStatement() {
		m_jdbcStmt = null;
	}

	public void create(DbConnection conn, String stmtText) throws Exception {
		this.m_stmtText = stmtText;
		m_jdbcStmt = conn.getJdbcConnection().prepareStatement(stmtText);
	}

	public void release() throws Exception {
		if (m_jdbcStmt != null) {
			m_jdbcStmt.close();
			m_jdbcStmt = null;
		}
	}

	public int execute() throws Exception {
		if (PROFILE_LOGGER.isEnabledFor(ProfileLogLevel.BEGIN_QUERY)) {
			StringBuffer logMessage = new StringBuffer()
					.append(System.currentTimeMillis()).append(" [")
					.append(m_stmtText).append("]");
			PROFILE_LOGGER.log(ProfileLogLevel.BEGIN_QUERY, logMessage);
		}
		int returnValue = m_jdbcStmt.executeUpdate();

		if (PROFILE_LOGGER.isEnabledFor(ProfileLogLevel.END_QUERY))
			PROFILE_LOGGER.log(ProfileLogLevel.END_QUERY,
					String.valueOf(System.currentTimeMillis()));

		return returnValue;
	}

	public void setShortText(int index, String value) throws Exception {
		if (DbDataType.isNullShortText(value)) {
			m_jdbcStmt.setNull(index,
					DbDataType.getJdbcType(DbDataType.SHORT_TEXT));
		} else
			m_jdbcStmt.setString(index, value);
	}

	public void setLongText(int index, String value) throws Exception {
		if (DbDataType.isNullLongText(value)) {
			m_jdbcStmt.setNull(index,
					DbDataType.getJdbcType(DbDataType.LONG_TEXT));
		} else {
			// byte[] valueBytes = value.getBytes();
			// ByteArrayInputStream in = new ByteArrayInputStream(valueBytes);
			// m_jdbcStmt.setAsciiStream(index, in, valueBytes.length);
			m_jdbcStmt.setCharacterStream(index, new StringReader(value),
					value.length());
		}
		// m_jdbcStmt.setString(index, value);
	}

	public void setShortInteger(int index, short value) throws Exception {
		if (DbDataType.isNullShortInteger(value)) {
			m_jdbcStmt.setNull(index,
					DbDataType.getJdbcType(DbDataType.SHORT_INTEGER));
		} else
			m_jdbcStmt.setShort(index, value);
	}

	public void setLongInteger(int index, int value) throws Exception {
		if (DbDataType.isNullLongInteger(value)) {
			m_jdbcStmt.setNull(index,
					DbDataType.getJdbcType(DbDataType.LONG_INTEGER));
		} else
			m_jdbcStmt.setInt(index, value);
	}

	public void setLong(int index, long value) throws Exception {
		if (DbDataType.isNullLong(value)) {
			m_jdbcStmt.setNull(index,
					DbDataType.getJdbcType(DbDataType.LONG_INTEGER));
		} else
			m_jdbcStmt.setLong(index, value);
	}

	public void setShortDecimal(int index, float value) throws Exception {
		if (DbDataType.isNullShortDecimal(value)) {
			m_jdbcStmt.setNull(index,
					DbDataType.getJdbcType(DbDataType.SHORT_DECIMAL));
		} else
			m_jdbcStmt.setFloat(index, value);
	}

	public void setLongDecimal(int index, double value) throws Exception {
		if (DbDataType.isNullLongDecimal(value)) {
			m_jdbcStmt.setNull(index,
					DbDataType.getJdbcType(DbDataType.LONG_DECIMAL));
		} else
			m_jdbcStmt.setDouble(index, value);
	}

	public void setDateTime(int index, Date value) throws Exception {
		if (DbDataType.isNullDateTime(value)) {
			m_jdbcStmt.setNull(index,
					DbDataType.getJdbcType(DbDataType.DATE_TIME));
		} else {
			Timestamp ts = new Timestamp(value.getTime());
			m_jdbcStmt.setTimestamp(index, ts);
		}
	}

	public void setObject(int index, Object value) throws Exception {
		if (DbDataType.isNullObject(value)) {
			m_jdbcStmt
					.setNull(index, DbDataType.getJdbcType(DbDataType.OBJECT));
		} else {
			m_jdbcStmt.setObject(index, value);
		}
	}

	public static void ensureRelease(DbInputStatement stmt, Exception exc)
			throws Exception {

		logger.error(exc);
		try {
			if (stmt != null)
				stmt.release();
			throw exc;
		} catch (Exception e) {
			throw exc;
		}

	}

} // class
