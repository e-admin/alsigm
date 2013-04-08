package solicitudes.prestamos.db;

import ieci.core.db.DbConnection;

import common.db.DbDataSource;

/**
 * Clase de implementacion para ORACLE.
 */
public class ProrrogaDBEntityImpl extends ProrrogaDBEntity {
	/**
	 * Constructor por defecto
	 * 
	 * @param dataSource
	 */
	public ProrrogaDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public ProrrogaDBEntityImpl(DbConnection conn) {
		super(conn);
	}
}
