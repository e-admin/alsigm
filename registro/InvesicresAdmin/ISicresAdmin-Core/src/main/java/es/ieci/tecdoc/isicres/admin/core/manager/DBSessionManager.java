package es.ieci.tecdoc.isicres.admin.core.manager;

/*
 * $Id: DBSessionManager.java,v 1.1.2.2 2008/04/14 17:50:50 jconca Exp $
 */
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.db.DbEngine;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;
import es.ieci.tecdoc.isicres.admin.database.DataSourceManager;

public class DBSessionManager {
	public static Logger logger = Logger.getLogger(DBSessionManager.class);

	private static String CASE_SENSITIVE = "CS";
	private static String CASE_INSENSITIVE = "CI";

	public static Connection getSession(String entidad)
			throws ISicresAdminDAOException {
		try {
			return DataSourceManager.getConnection(entidad);

		} catch (Exception e) {
			logger.error("Error obteniendo conexión con la BBDD");
			throw new ISicresAdminDAOException(ISicresAdminDAOException.NO_BD, e);
		}
	}

	/**
	 * Obtenemos el tipo de base de datos (CaseSensitive o CaseInsensitive)
	 *
	 * @param entidad
	 * @return
	 * @throws ISicresAdminDAOException
	 * @throws SQLException
	 */
	public static String getDBCaseSensitive(String entidad)
			throws ISicresAdminDAOException, SQLException {
		Connection con = getSession(entidad);

		DatabaseMetaData metaData = con.getMetaData();
		String dbVendor = metaData.getDatabaseProductName();

		if (dbVendor.indexOf(DbEngine.SQLSERVER_STR) != -1) {
			return CASE_INSENSITIVE;
		} else if (dbVendor.indexOf(DbEngine.POSTGRESQL_STR) != -1) {
			return CASE_SENSITIVE;
		} else if (dbVendor.indexOf(DbEngine.DB2_STR) != -1) {
			return CASE_SENSITIVE;
		} else if (dbVendor.indexOf(DbEngine.ORACLE_STR) != -1) {

			Statement statement = null;
			String dbVendorVersion = null;
			ResultSet resultSet = null;

			try {
				statement = con.createStatement();
				resultSet = statement
						.executeQuery("select value from nls_database_parameters where parameter = 'NLS_RDBMS_VERSION'");
				if (resultSet.next()) {
					dbVendorVersion = resultSet.getString(1);
				}

			} catch (SQLException e) {
				logger.warn("Error obteniendo la versión." + e.getMessage());
			} catch (Throwable e) {
				logger.warn("Error obteniendo la versión.." + e.getMessage());
			} finally {
				statement.close();
			}

			// String dbVendorVersion = metaData.getDatabaseProductVersion();

			String aux1 = dbVendorVersion.substring(0, dbVendorVersion
					.indexOf("."));
			String aux = dbVendorVersion.substring(
					dbVendorVersion.indexOf(".") + 1, dbVendorVersion.length());

			if (new Integer(aux1).intValue() < 10) {
				dbVendorVersion = "1";
			} else {
				dbVendorVersion = aux.substring(0, aux.indexOf("."));
			}

			if ("1".equals(dbVendorVersion)) {
				return CASE_SENSITIVE;
			} else {
				return CASE_INSENSITIVE;
			}
		} else {
			return CASE_INSENSITIVE;
		}
	}

	/**
	 * Obtenemos el tipo de base de datos que es
	 *
	 * @param entidad
	 * @return
	 * @throws ISicresAdminDAOException
	 * @throws SQLException
	 */
	public static int getDataBase(String entidad) throws ISicresAdminDAOException,
			SQLException {
		Connection con = getSession(entidad);

		DatabaseMetaData metaData = con.getMetaData();
		String dbVendor = metaData.getDatabaseProductName();

		if (dbVendor.indexOf(DbEngine.SQLSERVER_STR) != -1) {
			return DbEngine.SQLSERVER;
		} else if (dbVendor.indexOf(DbEngine.POSTGRESQL_STR) != -1) {
			return DbEngine.POSTGRESQL;
		} else if (dbVendor.indexOf(DbEngine.DB2_STR) != -1) {
			return DbEngine.DB2;
		} else if (dbVendor.indexOf(DbEngine.ORACLE_STR) != -1) {
			return DbEngine.ORACLE;
		}

		return 0;
	}
}
