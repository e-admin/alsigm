package solicitudes.prestamos.db;

import ieci.core.db.DbConnection;

import common.db.DbDataSource;

/**
 * Clase que se encarga de la persistencia de la tabla MOTIVOPRESTAMO
 */
public class MotivoPrestamoDBEntityImpl extends MotivoPrestamoDBEntity {
	/**
	 * Constructor por defecto
	 * 
	 * @param dataSource
	 */
	public MotivoPrestamoDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public MotivoPrestamoDBEntityImpl(DbConnection conn) {
		super(conn);
	}
}
