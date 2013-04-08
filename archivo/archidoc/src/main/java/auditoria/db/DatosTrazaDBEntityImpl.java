package auditoria.db;

import ieci.core.db.DbConnection;

import common.db.DbDataSource;

/**
 * Clase que encapsula todas las definiciones de los datos de las trazas de
 * auditoría, así como de las operaciones que se pueden realizar sobre ellos.
 */
public class DatosTrazaDBEntityImpl extends DatosTrazaDBEntity {
	/**
	 * Constructor por defecto de la clase.
	 * 
	 * @param ta
	 *            Gestor de transacciones
	 */
	public DatosTrazaDBEntityImpl(DbDataSource ta) {
		super(ta);
	}

	public DatosTrazaDBEntityImpl(DbConnection conn) {
		super(conn);
	}
}
