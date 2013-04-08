package common.db;

import ieci.core.db.DbConnection;
import deposito.db.IElementoAsignableDBEntity;
import deposito.db.IElementoNoAsignableDBEntity;

/**
 * @author IECISA
 * 
 */
public class DBEntityFactoryOracle8i extends DBEntityFactoryOracle {

	public DBEntityFactoryOracle8i() {
	}

	public IElementoNoAsignableDBEntity getElmentoNoAsignableDBEntity(
			DbDataSource dataSource) {
		return new deposito.db.oracle8i.NoAsignableDBEntity(dataSource);
	}

	public IElementoNoAsignableDBEntity getElmentoNoAsignableDBEntity(
			DbConnection conn) {
		return new deposito.db.oracle8i.NoAsignableDBEntity(conn);
	}

	public IElementoAsignableDBEntity getElementoAsignableDBEntity(
			DbDataSource dataSource) {
		return new deposito.db.oracle8i.AsignableDBEntity(dataSource);
	}

	public IElementoAsignableDBEntity getElementoAsignableDBEntity(
			DbConnection conn) {
		return new deposito.db.oracle8i.AsignableDBEntity(conn);
	}

}