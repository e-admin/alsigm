package solicitudes.db;

import ieci.core.db.DbConnection;

import common.db.DbDataSource;

/**
 * Clase que encapsula todas las definiciones de los detalles asi como de las
 * operaciones que se pueden realizar sobre ellos.
 */
public class DetalleDBEntityImpl extends DetalleDBEntity {
	/**
	 * Constructor por defecto de la clase
	 * 
	 * @param dataSource
	 */
	public DetalleDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public DetalleDBEntityImpl(DbConnection conn) {
		super(conn);
	}
}
