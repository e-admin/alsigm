package solicitudes.db;

import ieci.core.db.DbConnection;

import common.db.DbDataSource;

/**
 * Clase que se encarga de la persistencia para la tabla MOTIVORECHAZO
 */
public class MotivoRechazoDBEntityImpl extends MotivoRechazoDBEntity {
	/**
	 * Constructor por defecto
	 * 
	 * @param dataSource
	 */
	public MotivoRechazoDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public MotivoRechazoDBEntityImpl(DbConnection conn) {
		super(conn);
	}
}
