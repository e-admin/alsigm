package ieci.core.db;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import common.exceptions.ArchivoModelException;
import common.util.DBEngineUtils;
import common.vos.EngineInfoVO;

//DB2 Añadida nueva base de datos.
public final class DbEngine implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final String oracleDatabaseName = "ORACLE";
	public static final String sqlserverDatabaseName = "MICROSOFT SQL SERVER";
	public static final String postgresSQlDatabaseName = "PostgreSQL";
	public static final String db2DatabaseName = "DB2";

	/**
	 * Microsoft SQL Server
	 */
	public static final int SQLSERVER = 1;

	/**
	 * Oracle
	 */
	public static final int ORACLE = 2;

	/**
	 * PostgreSQL
	 */
	public static final int POSTGRES = 3;

	/**
	 * IBM DB2
	 */
	public static final int DB2 = 4;

	private int idEngine = 0;

	DataSource dataSource = null;

	private boolean allowHint = true;

	private EngineInfoVO engineInfoVO;

	public boolean getAllowHint() {
		return allowHint;
	}

	// private DbEngine() throws Exception {
	// Properties dbEngineProperties = new Properties();
	// dbEngineProperties.load(new
	// FileInputStream(DBConstants.DB_ENGINE_CONFIG_FILE));
	// String dataSourceNameEnvEntry =
	// dbEngineProperties.getProperty("dsName_env_entry");
	// if (dataSourceNameEnvEntry != null) {
	// Context context = new InitialContext();
	// if (context != null)
	// dataSource = (DataSource)context.lookup(dataSourceNameEnvEntry);
	// }
	// }

	/*
	 * public static DbEngine getDbEngine() { if (instance == null){ instance =
	 * new DbEngine(); } return instance; }
	 */

	public DataSource getDataSource() throws Exception {
		if (this.dataSource == null)
			throw new Exception("DataSource no disponible");
		return this.dataSource;
	}

	public DbEngine(DataSource dataSource, String dbFactoryClass)
			throws ArchivoModelException {
		this.dataSource = dataSource;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			engineInfoVO = DBEngineUtils.getEngineInfo(conn, dbFactoryClass);

			if (engineInfoVO != null) {
				idEngine = engineInfoVO.getIdEngine();
			}

			// String databaseProductName = conn.getMetaData()
			// .getDatabaseProductName();
			// if
			// (databaseProductName.toLowerCase().indexOf(oracleDatabaseName.toLowerCase())!=
			// -1) {
			// idEngine = ORACLE;
			// } else if
			// (databaseProductName.toLowerCase().indexOf(sqlserverDatabaseName.toLowerCase())!=
			// -1) {
			// idEngine = SQLSERVER;
			// } else if
			// (databaseProductName.toLowerCase().indexOf(postgresSQlDatabaseName.toLowerCase())!=
			// -1) {
			// idEngine = POSTGRES;
			// } else if
			// (databaseProductName.toLowerCase().indexOf(db2DatabaseName.toLowerCase())!=
			// -1) {
			// idEngine = DB2;
			// allowHint = false;
			// } else {
			// throw new ArchivoModelException(DbEngine.class,
			// "setDataSource",
			// "Base de datos no soportada por la aplicacion");
			// }

		} catch (SQLException e) {
			throw new ArchivoModelException(DbEngine.class, "setDataSource",
					e.getMessage());
		} catch (Exception e) {
			throw new ArchivoModelException(DbEngine.class, "setDataSource",
					e.getMessage());
		} finally {
			if (conn != null)
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					throw new ArchivoModelException(DbEngine.class,
							"setDataSource", "Cierre de conexión fallido");
				}
		}
	}

	public DbConnection getConnection() throws Exception {

		if (dataSource == null)
			throw new Exception("Acceso a datos no configurado");
		return new DbConnection(dataSource.getConnection(), idEngine);
	}

	public void closeConnection(DbConnection conn) throws Exception {
		if (conn != null)
			conn.close();
	}

	public int getIdEngine() {
		return idEngine;
	}

	public void setIdEngine(int idEngine) {
		this.idEngine = idEngine;
	}

	public void setEngineInfoVO(EngineInfoVO engineInfoVO) {
		this.engineInfoVO = engineInfoVO;
	}

	public EngineInfoVO getEngineInfoVO() {
		return engineInfoVO;
	}

	public String getDbFactoryClass() {
		if (engineInfoVO != null) {
			return engineInfoVO.getDbFactoryClass();
		}

		return null;
	}

} // class
