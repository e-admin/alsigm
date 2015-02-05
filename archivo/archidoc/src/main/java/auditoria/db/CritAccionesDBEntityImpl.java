package auditoria.db;

import ieci.core.db.DbConnection;
import common.db.DbDataSource;

/**
 * Implemetacion por defecto para ORACLE.
 */
public class CritAccionesDBEntityImpl extends CritAccionesDBEntity {
	public CritAccionesDBEntityImpl(DbDataSource db) {
		super(db);
	}

	public CritAccionesDBEntityImpl(DbConnection conn) {
		super(conn);
	}
}
