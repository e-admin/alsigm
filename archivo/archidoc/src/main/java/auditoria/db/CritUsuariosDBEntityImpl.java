package auditoria.db;

import ieci.core.db.DbConnection;

import common.db.DbDataSource;

/**
 * Clase que encapsula todas las definiciones de los datos de los niveles de
 * criticidad de los usuarios , así como de las operaciones que se pueden
 * realizar sobre ellos.
 */
public class CritUsuariosDBEntityImpl extends CritUsuariosDBEntity {
	/**
	 * Constructor por defecto
	 * 
	 * @param ta
	 *            Gestor de transacciones
	 */
	public CritUsuariosDBEntityImpl(DbDataSource ta) {
		super(ta);
	}

	public CritUsuariosDBEntityImpl(DbConnection conn) {
		super(conn);
	}
}
