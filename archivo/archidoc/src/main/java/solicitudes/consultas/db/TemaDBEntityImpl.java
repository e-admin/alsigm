package solicitudes.consultas.db;

import ieci.core.db.DbConnection;

import common.db.DbDataSource;

/**
 * Clase de implementacion especifica para ORACLE
 */
public class TemaDBEntityImpl extends TemaDBEntity {
	/**
	 * Constrcutor por defecto
	 * 
	 * @param dataSource
	 */
	public TemaDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public TemaDBEntityImpl(DbConnection conn) {
		super(conn);
	}
}
