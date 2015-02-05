/*
 * Created on 17-ene-2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package common.db;

import fondos.db.IElementoCuadroClasificacionDbEntity;
import ieci.core.db.DbConnection;
import ieci.core.db.DbEngine;
import transferencias.db.IRelacionEntregaDBEntity;
import deposito.db.IElementoNoAsignableDBEntity;
import docelectronicos.db.IDocClasifCFDBEntity;
import docelectronicos.db.IDocClasifDescrDBEntity;

public class DBEntityFactoryPostgreSQL extends DBEntityFactoryPostgresSqlServer {

	public int getDataBaseType() {
		return DbEngine.POSTGRES;
	}

	public IRelacionEntregaDBEntity getRelacionDBEntity(DbDataSource dataSource) {
		return new transferencias.db.postgreSQL.RelacionEntregaDBEntityImpl(
				dataSource);
	}

	public IRelacionEntregaDBEntity getRelacionDBEntity(DbConnection conn) {
		return new transferencias.db.postgreSQL.RelacionEntregaDBEntityImpl(
				conn);
	}

	public IElementoCuadroClasificacionDbEntity getElementoCuadroClasificacionDBEntity(
			DbDataSource dataSource) {
		return new fondos.db.postgreSQL.ElementoCuadroClasificacionDBEntityImpl(
				dataSource);
	}

	public IElementoCuadroClasificacionDbEntity getElementoCuadroClasificacionDBEntity(
			DbConnection conn) {
		return new fondos.db.postgreSQL.ElementoCuadroClasificacionDBEntityImpl(
				conn);
	}

	public IDocClasifCFDBEntity getDocClasifCFDBEntity(DbDataSource dataSource) {
		return new docelectronicos.db.postgreSQL.DocClasifCFDBEntityImpl(
				dataSource);
	}

	public IDocClasifCFDBEntity getDocClasifCFDBEntity(DbConnection conn) {
		return new docelectronicos.db.postgreSQL.DocClasifCFDBEntityImpl(conn);
	}

	public IDocClasifDescrDBEntity getDocClasifDescrDBEntity(
			DbDataSource dataSource) {
		return new docelectronicos.db.postgreSQL.DocClasifDescrDBEntityImpl(
				dataSource);
	}

	public IDocClasifDescrDBEntity getDocClasifDescrDBEntity(DbConnection conn) {
		return new docelectronicos.db.postgreSQL.DocClasifDescrDBEntityImpl(
				conn);
	}

	public IElementoNoAsignableDBEntity getElmentoNoAsignableDBEntity(
			DbDataSource dataSource) {
		return new deposito.db.postgreSQL.NoAsignableDbEntity(dataSource);
	}

	public IElementoNoAsignableDBEntity getElmentoNoAsignableDBEntity(
			DbConnection conn) {
		return new deposito.db.postgreSQL.NoAsignableDbEntity(conn);
	}

}
