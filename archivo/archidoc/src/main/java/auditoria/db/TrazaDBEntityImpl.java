package auditoria.db;

import ieci.core.db.DbConnection;

import common.db.DbDataSource;

/**
 * Clase que encapsula todas las definiciones de las trazas de auditoría, así
 * como de las operaciones que se pueden realizar sobre ellos.
 */
public class TrazaDBEntityImpl extends TrazaDBEntity {
	/**
	 * Constructor por defecto de la clase
	 * 
	 * @param ta
	 *            TransactionManager de la entidad
	 */
	public TrazaDBEntityImpl(DbDataSource ta) {
		super(ta);
	}

	public TrazaDBEntityImpl(DbConnection conn) {
		super(conn);
	}
}
