package solicitudes.db;

import ieci.core.db.DbConnection;
import common.db.DbDataSource;

/**
 * Clase de implementacion para ORACLe
 */
public class NSecDBEntityImpl extends NSecDBEntity {
	public NSecDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public NSecDBEntityImpl(DbConnection conn) {
		super(conn);
	}
}