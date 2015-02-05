package transferencias.db.sqlserver2000;

import ieci.core.db.DbConnection;

import java.util.List;

import common.db.DbDataSource;
import common.exceptions.TooManyResultsException;
import common.pagination.PageInfo;

public class RelacionEntregaDBEntityImpl extends
		transferencias.db.oracle.RelacionEntregaDBEntityImpl {

	/**
	 * @param dataSource
	 */
	public RelacionEntregaDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public RelacionEntregaDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	public List getRelacionesXClasificadorSeries(String idClfSeries,
			PageInfo pageInfo) throws TooManyResultsException {
		// TODO ZHACER SQLSERVER2000
		return null;
	}

}
