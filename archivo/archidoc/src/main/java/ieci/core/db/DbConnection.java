package ieci.core.db;

import ieci.core.exception.IeciTdException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public final class DbConnection {

	private Connection m_jdbcConn;
	private int m_engine;
	private boolean m_inTrans;
	private boolean allowHint = true;

	public boolean isAllowHint() {
		return allowHint;
	}

	public void setAllowHint(boolean allowHint) {
		this.allowHint = allowHint;
	}

	public DbConnection(Connection jdbcConnection, int engine) {
		m_jdbcConn = jdbcConnection;
		m_engine = engine;
		m_inTrans = false;
	}

	public void open(int engine, String drvClsName, String url, String user,
			String pwd) throws Exception {
		m_engine = engine;
		Class.forName(drvClsName);
		drvMgrGetConnection(url, user, pwd);
	}

	public void open(int engine, String ctxName, String user, String pwd)
			throws Exception {

		Context ctx;
		DataSource ds;

		m_engine = engine;

		ctx = new InitialContext();

		ds = (DataSource) ctx.lookup(ctxName);

		m_jdbcConn = ds.getConnection(user, pwd);

	}

	public void close() {

		if (opened()) {
			try {
				m_jdbcConn.close();
			} catch (Exception e) {
			}
			m_jdbcConn = null;
			m_inTrans = false;
		}

	}

	public void beginTransaction() throws Exception {
		if (inTransaction()) {
			throw new IeciTdException(DbError.EC_ALREADY_IN_TRANS,
					DbError.EM_ALREADY_IN_TRANS);
		}
		m_jdbcConn.setAutoCommit(false);
		m_inTrans = true;
	}

	public void endTransaction(boolean commit) throws Exception {
		if (!inTransaction()) {
			throw new IeciTdException(DbError.EC_NOT_IN_TRANS,
					DbError.EM_NOT_IN_TRANS);
		}
		if (commit)
			m_jdbcConn.commit();
		else
			m_jdbcConn.rollback();
		m_jdbcConn.setAutoCommit(true);
		m_inTrans = false;
	}

	public boolean opened() {
		return (m_jdbcConn != null);
	}

	public boolean inTransaction() {
		return m_inTrans;
	}

	public static void ensureEndTransaction(DbConnection conn, Exception exc)
			throws Exception {

		try {
			if (conn != null) {
				if (conn.inTransaction()) {
					conn.endTransaction(false);
				}
			}
			throw exc;
		} catch (Exception e) {
			throw exc;
		}

	}

	public static void ensureClose(DbConnection conn, Exception exc)
			throws Exception {

		try {
			if (conn != null)
				conn.close();
			throw exc;
		} catch (Exception e) {
			throw exc;
		}

	}

	public Connection getJdbcConnection() {
		return m_jdbcConn;
	}

	int getEngine() {
		return m_engine;
	}

	// **************************************************************************

	private void drvMgrGetConnection(String url, String user, String pwd)
			throws Exception {

		String sqlState;

		try {
			m_jdbcConn = DriverManager.getConnection(url, user, pwd);
		} catch (SQLException e) {

			sqlState = e.getSQLState();

			if (sqlState.equals("28000")) {
				throw new IeciTdException(DbError.EC_INVALID_AUTH_SPEC,
						DbError.EM_INVALID_AUTH_SPEC, e);
			} else
				throw e;

		}

	}

	// **************************************************************************

	/*
	 * 
	 * private static final String CONN_STR_ENG = "Engine="; private static
	 * final String CONN_STR_POOL = ";Pooling="; private static final String
	 * CONN_STR_DRV = ";Driver="; private static final String CONN_STR_URL =
	 * ";Url="; private static final String CONN_STR_USER = ";User="; private
	 * static final String CONN_STR_PWD = ";Password="; private static final
	 * String CONN_STR_CTX = ";Context=";
	 * 
	 * public static DbConnection createConnection(String connStr) throws
	 * Exception {
	 * 
	 * DbConnection conn = null; int engPos1, engPos2, poolPos1, poolPos2,
	 * urlPos1, urlPos2; int userPos1, userPos2, pwdPos1, pwdPos2, ctxPos1,
	 * ctxPos2; int drvPos1, drvPos2, endPos; String engStr, poolStr, drv, url,
	 * user, pwd, ctx; int engine; boolean pooling;
	 * 
	 * engPos1 = connStr.indexOf(CONN_STR_ENG, 0); if (engPos1 == -1) { throw
	 * new IeciTecDocException(DbError.EC_INVALID_CONN_STR,
	 * DbError.EM_INVALID_CONN_STR); } engPos2 = engPos1 + CONN_STR_ENG.length()
	 * - 1;
	 * 
	 * poolPos1 = connStr.indexOf(CONN_STR_POOL, engPos2 + 1); if (poolPos1 ==
	 * -1) { throw new IeciTecDocException(DbError.EC_INVALID_CONN_STR,
	 * DbError.EM_INVALID_CONN_STR); } poolPos2 = poolPos1 +
	 * CONN_STR_POOL.length() - 1;
	 * 
	 * engStr = connStr.substring(engPos2 + 1, poolPos1); engine =
	 * Integer.parseInt(engStr);
	 * 
	 * poolStr = connStr.substring(poolPos2 + 1, poolPos2 + 2); if
	 * (Integer.parseInt(poolStr) == 1) pooling = true; else pooling = false;
	 * 
	 * if (pooling) {
	 * 
	 * ctxPos1 = connStr.indexOf(CONN_STR_CTX, poolPos2 + 1); if (ctxPos1 == -1)
	 * { throw new IeciTecDocException(DbError.EC_INVALID_CONN_STR,
	 * DbError.EM_INVALID_CONN_STR); } ctxPos2 = ctxPos1 + CONN_STR_CTX.length()
	 * - 1;
	 * 
	 * userPos1 = connStr.indexOf(CONN_STR_USER, ctxPos2 + 1); if (userPos1 ==
	 * -1) { throw new IeciTecDocException(DbError.EC_INVALID_CONN_STR,
	 * DbError.EM_INVALID_CONN_STR); } userPos2 = userPos1 +
	 * CONN_STR_USER.length() - 1;
	 * 
	 * pwdPos1 = connStr.indexOf(CONN_STR_PWD, userPos2 + 1); if (pwdPos1 == -1)
	 * { throw new IeciTecDocException(DbError.EC_INVALID_CONN_STR,
	 * DbError.EM_INVALID_CONN_STR); } pwdPos2 = pwdPos1 + CONN_STR_PWD.length()
	 * - 1;
	 * 
	 * endPos = connStr.length();
	 * 
	 * ctx = connStr.substring(ctxPos2 + 1, userPos1); user =
	 * connStr.substring(userPos2 + 1, pwdPos1); pwd = connStr.substring(pwdPos2
	 * + 1, endPos);
	 * 
	 * conn = new DbConnection(); conn.open(engine, ctx, user, pwd);
	 * 
	 * } else {
	 * 
	 * drvPos1 = connStr.indexOf(CONN_STR_DRV, poolPos2 + 1); if (drvPos1 == -1)
	 * { throw new IeciTecDocException(DbError.EC_INVALID_CONN_STR,
	 * DbError.EM_INVALID_CONN_STR); } drvPos2 = drvPos1 + CONN_STR_DRV.length()
	 * - 1;
	 * 
	 * urlPos1 = connStr.indexOf(CONN_STR_URL, drvPos2 + 1); if (urlPos1 == -1)
	 * { throw new IeciTecDocException(DbError.EC_INVALID_CONN_STR,
	 * DbError.EM_INVALID_CONN_STR); } urlPos2 = urlPos1 + CONN_STR_URL.length()
	 * - 1;
	 * 
	 * userPos1 = connStr.indexOf(CONN_STR_USER, urlPos2 + 1); if (userPos1 ==
	 * -1) { throw new IeciTecDocException(DbError.EC_INVALID_CONN_STR,
	 * DbError.EM_INVALID_CONN_STR); } userPos2 = userPos1 +
	 * CONN_STR_USER.length() - 1;
	 * 
	 * pwdPos1 = connStr.indexOf(CONN_STR_PWD, userPos2 + 1); if (pwdPos1 == -1)
	 * { throw new IeciTecDocException(DbError.EC_INVALID_CONN_STR,
	 * DbError.EM_INVALID_CONN_STR); } pwdPos2 = pwdPos1 + CONN_STR_PWD.length()
	 * - 1;
	 * 
	 * endPos = connStr.length();
	 * 
	 * drv = connStr.substring(drvPos2 + 1, urlPos1); url =
	 * connStr.substring(urlPos2 + 1, userPos1); user =
	 * connStr.substring(userPos2 + 1, pwdPos1); pwd = connStr.substring(pwdPos2
	 * + 1, endPos);
	 * 
	 * conn = new DbConnection(); conn.open(engine, drv, url, user, pwd);
	 * 
	 * }
	 * 
	 * return conn;
	 * 
	 * }
	 */

} // class
