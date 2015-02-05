/*
 * Created on 17-ene-2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package common.db;

import common.lock.db.ILockDBEntity;

import ieci.core.db.DbConnection;
import deposito.db.IElementoAsignableDBEntity;
import deposito.db.IHuecoDBEntity;
import deposito.db.IOcupacionDBEntity;
import fondos.db.IProductorSerieDbEntity;

public abstract class DBEntityFactoryPostgresSqlServer extends
		DbEntityFactoryBase {

	public IHuecoDBEntity getHuecoDBEntity(DbDataSource dataSource) {
		return new deposito.db.commonPostgreSQLServer.HuecoDBEntityImpl(
				dataSource);
	}

	public IHuecoDBEntity getHuecoDBEntity(DbConnection conn) {
		return new deposito.db.commonPostgreSQLServer.HuecoDBEntityImpl(conn);
	}

	public IOcupacionDBEntity getOcupacionDBEntity(DbDataSource dataSource) {
		return new deposito.db.commonPostgreSQLServer.InformeOcupacionDBEntity(
				dataSource);
	}

	public IOcupacionDBEntity getOcupacionDBEntity(DbConnection conn) {
		return new deposito.db.commonPostgreSQLServer.InformeOcupacionDBEntity(
				conn);
	}

	public IElementoAsignableDBEntity getElementoAsignableDBEntity(
			DbDataSource dataSource) {
		return new deposito.db.commonPostgreSQLServer.AsignableDBEntity(
				dataSource);
	}

	public IElementoAsignableDBEntity getElementoAsignableDBEntity(
			DbConnection conn) {
		return new deposito.db.commonPostgreSQLServer.AsignableDBEntity(conn);
	}

	public IProductorSerieDbEntity getProductorSerieDBEntity(
			DbDataSource dataSource) {
		return new fondos.db.commonPostgreSQLServer.ProductorSerieDBEntityPostgreSQLServerImpl(
				dataSource);
	}

	public IProductorSerieDbEntity getProductorSerieDBEntity(DbConnection conn) {
		return new fondos.db.commonPostgreSQLServer.ProductorSerieDBEntityPostgreSQLServerImpl(
				conn);
	}

	public ILockDBEntity getLockDBEntityImplBase(DbDataSource dataSource) {
		return new common.lock.db.sqlserver2000.LockDBEntityImpl(dataSource);
	}

	public ILockDBEntity getLockDBEntityImplBase(DbConnection conn) {
		return new common.lock.db.sqlserver2000.LockDBEntityImpl(conn);
	}

}
