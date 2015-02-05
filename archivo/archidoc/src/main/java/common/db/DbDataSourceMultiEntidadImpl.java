package common.db;

import ieci.core.db.DbConnection;
import ieci.core.db.DbEngine;

import javax.sql.DataSource;

import common.exceptions.DBException;

//Implementacion de una fuente de conexiones que permite obtener conexiones
//desde el pool de conexiones configurado para el sistema
public class DbDataSourceMultiEntidadImpl implements DbDataSource {
	DbConnection conn = null;
	DataSource dataSource = null;
	int idEngine = 0;
	private String dbFactoryClass;

	private boolean allowHint = true;

	public boolean getAllowHint() {
		return allowHint;
	}

	/**
	 * @param conn
	 */
	public DbDataSourceMultiEntidadImpl(DbEngine engine) {
		try {
			if (engine != null) {
				dataSource = engine.getDataSource();
				idEngine = engine.getIdEngine();
				allowHint = engine.getAllowHint();
				dbFactoryClass = engine.getDbFactoryClass();
			}
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.db.DbDataSource#obtenerConexion()
	 */
	public DbConnection obtenerConexion() {
		try {
			if (conn == null) {
				conn = new DbConnection(dataSource.getConnection(), idEngine);
				conn.setAllowHint(allowHint);
			}

			return conn;
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.db.DbDataSource#liberarConexion()
	 */
	public void liberarConexion() throws DBException {
		if (conn != null) {
			conn.close();
			conn = null;
		}
	}

	public void setDbFactoryClass(String dbFactory) {
		this.dbFactoryClass = dbFactory;
	}

	public String getDbFactoryClass() {
		return dbFactoryClass;
	}

}
