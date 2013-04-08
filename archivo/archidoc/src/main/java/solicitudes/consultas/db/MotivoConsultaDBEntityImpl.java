package solicitudes.consultas.db;

import ieci.core.db.DbConnection;

import common.db.DbDataSource;

/**
 * Clase que se encarga de la persistencia de la tabla MOTIVOCONSULTA
 */
public class MotivoConsultaDBEntityImpl extends MotivoConsultaDBEntity {
	/**
	 * Constructor por defecto
	 * 
	 * @param dataSource
	 */
	public MotivoConsultaDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public MotivoConsultaDBEntityImpl(DbConnection conn) {
		super(conn);
	}
}
