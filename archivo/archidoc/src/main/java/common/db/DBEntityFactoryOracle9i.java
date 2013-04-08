package common.db;

import ieci.core.db.DbConnection;
import deposito.db.IElementoAsignableDBEntity;
import deposito.db.IElementoNoAsignableDBEntity;

/**
 * Factoria a través de la que crear objetos de acceso a datos con los que
 * recuperar y almacenar información en una base de datos Oracle 9i
 */
public class DBEntityFactoryOracle9i extends DBEntityFactoryOracle {

	public DBEntityFactoryOracle9i() {
	}

	public IElementoNoAsignableDBEntity getElmentoNoAsignableDBEntity(
			DbDataSource dataSource) {
		return new deposito.db.oracle9i.NoAsignableDBEntity(dataSource);
	}

	public IElementoNoAsignableDBEntity getElmentoNoAsignableDBEntity(
			DbConnection conn) {
		return new deposito.db.oracle9i.NoAsignableDBEntity(conn);
	}

	public IElementoAsignableDBEntity getElementoAsignableDBEntity(
			DbDataSource dataSource) {
		return new deposito.db.oracle9i.AsignableDbEntity(dataSource);
	}

	public IElementoAsignableDBEntity getElementoAsignableDBEntity(
			DbConnection conn) {
		return new deposito.db.oracle9i.AsignableDbEntity(conn);
	}

}