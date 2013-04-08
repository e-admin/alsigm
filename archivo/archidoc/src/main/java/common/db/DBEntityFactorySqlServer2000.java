/*
 * Created on 17-ene-2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package common.db;

import ieci.core.db.DbConnection;
import ieci.core.db.DbEngine;
import transferencias.db.IRelacionEntregaDBEntity;
import deposito.db.IElementoNoAsignableDBEntity;
import docelectronicos.db.IDocClasifCFDBEntity;
import docelectronicos.db.IDocClasifDescrDBEntity;
import fondos.db.IElementoCuadroClasificacionDbEntity;

public class DBEntityFactorySqlServer2000 extends
		DBEntityFactoryPostgresSqlServer {

	public int getDataBaseType() {
		return DbEngine.SQLSERVER;
	}

	public IRelacionEntregaDBEntity getRelacionDBEntity(DbDataSource dataSource) {
		return new transferencias.db.sqlserver2000.RelacionEntregaDBEntityImpl(
				dataSource);
	}

	public IRelacionEntregaDBEntity getRelacionDBEntity(DbConnection conn) {
		return new transferencias.db.sqlserver2000.RelacionEntregaDBEntityImpl(
				conn);
	}

	public IElementoNoAsignableDBEntity getElmentoNoAsignableDBEntity(
			DbDataSource dataSource) {
		return new deposito.db.sqlserver2000.NoAsignableDBEntity(dataSource);
	}

	public IElementoNoAsignableDBEntity getElmentoNoAsignableDBEntity(
			DbConnection conn) {
		return new deposito.db.sqlserver2000.NoAsignableDBEntity(conn);
	}

	public IElementoCuadroClasificacionDbEntity getElementoCuadroClasificacionDBEntity(
			DbDataSource dataSource) {
		return new fondos.db.sqlserver2000.ElementoCuadroClasificacionDbEntityImpl(
				dataSource);
	}

	public IElementoCuadroClasificacionDbEntity getElementoCuadroClasificacionDBEntity(
			DbConnection conn) {
		return new fondos.db.sqlserver2000.ElementoCuadroClasificacionDbEntityImpl(
				conn);
	}

	public IDocClasifCFDBEntity getDocClasifCFDBEntity(DbDataSource dataSource) {
		return new docelectronicos.db.sqlserver2000.DocClasifCFDBEntityImpl(
				dataSource);
	}

	public IDocClasifCFDBEntity getDocClasifCFDBEntity(DbConnection conn) {
		return new docelectronicos.db.sqlserver2000.DocClasifCFDBEntityImpl(
				conn);
	}

	public IDocClasifDescrDBEntity getDocClasifDescrDBEntity(
			DbDataSource dataSource) {
		return new docelectronicos.db.sqlserver2000.DocClasifDescrDBEntityImpl(
				dataSource);
	}

	public IDocClasifDescrDBEntity getDocClasifDescrDBEntity(DbConnection conn) {
		return new docelectronicos.db.sqlserver2000.DocClasifDescrDBEntityImpl(
				conn);
	}

}
