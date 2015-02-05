package solicitudes.consultas.db;

import ieci.core.db.DbConnection;

import common.db.DbDataSource;

/**
 * Clase que encapsula la persisntencia sobra la tabla de consultas
 */
public class ConsultaDBEntityImpl extends ConsultaDBEntity {
	/**
	 * Constructor por defecto de la clase
	 * 
	 * @param dataSource
	 */
	public ConsultaDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public ConsultaDBEntityImpl(DbConnection conn) {
		super(conn);
	}

}
