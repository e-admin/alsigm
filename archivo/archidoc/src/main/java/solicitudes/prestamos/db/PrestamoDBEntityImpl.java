package solicitudes.prestamos.db;

import ieci.core.db.DbConnection;

import common.db.DbDataSource;

/**
 * Clase que encapsula los metodos de persistencia de prestamos.
 */
public class PrestamoDBEntityImpl extends PrestamoDBEntity {
	/**
	 * Constructor por defecto
	 */
	public PrestamoDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public PrestamoDBEntityImpl(DbConnection conn) {
		super(conn);
	}

}