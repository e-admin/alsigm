package common.db;

import fondos.db.IElementoCuadroClasificacionDbEntity;
import fondos.db.IProductorSerieDbEntity;
import fondos.db.ibmdb2.ProductorSerieDBEntityDB2Impl;
import ieci.core.db.DbConnection;
import ieci.core.db.DbEngine;
import transferencias.db.IRelacionEntregaDBEntity;

import common.lock.db.ILockDBEntity;
import common.lock.db.sqlserver2000.LockDBEntityImpl;

import deposito.db.IElementoAsignableDBEntity;
import deposito.db.IElementoNoAsignableDBEntity;
import deposito.db.IHuecoDBEntity;
import deposito.db.IOcupacionDBEntity;
import docelectronicos.db.IDocClasifCFDBEntity;
import docelectronicos.db.IDocClasifDescrDBEntity;
import docelectronicos.db.sqlserver2000.DocClasifCFDBEntityImpl;
import docelectronicos.db.sqlserver2000.DocClasifDescrDBEntityImpl;

/**
 * Factoría DB2
 * 
 * @author IECISA
 * 
 */
public class DBEntityFactoryDB2 extends DbEntityFactoryBase {

	public int getDataBaseType() {
		return DbEngine.DB2;
	}

	public IRelacionEntregaDBEntity getRelacionDBEntity(DbDataSource dataSource) {
		return new transferencias.db.ibmdb2.RelacionEntregaDBEntityImpl(
				dataSource);
	}

	public IRelacionEntregaDBEntity getRelacionDBEntity(DbConnection conn) {
		return new transferencias.db.ibmdb2.RelacionEntregaDBEntityImpl(conn);
	}

	public IElementoCuadroClasificacionDbEntity getElementoCuadroClasificacionDBEntity(
			DbDataSource dataSource) {
		return new fondos.db.ibmdb2.ElementoCuadroClasificacionDBEntityImpl(
				dataSource);
	}

	public IElementoCuadroClasificacionDbEntity getElementoCuadroClasificacionDBEntity(
			DbConnection conn) {
		return new fondos.db.ibmdb2.ElementoCuadroClasificacionDBEntityImpl(
				conn);
	}

	public IProductorSerieDbEntity getProductorSerieDBEntity(
			DbDataSource dataSource) {
		return new ProductorSerieDBEntityDB2Impl(dataSource);
	}

	public IProductorSerieDbEntity getProductorSerieDBEntity(DbConnection conn) {
		return new ProductorSerieDBEntityDB2Impl(conn);
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

	public IElementoNoAsignableDBEntity getElmentoNoAsignableDBEntity(
			DbDataSource dataSource) {
		return new deposito.db.ibmdb2.NoAsignableDbEntity(dataSource);
	}

	public IElementoNoAsignableDBEntity getElmentoNoAsignableDBEntity(
			DbConnection conn) {
		return new deposito.db.ibmdb2.NoAsignableDbEntity(conn);
	}

	public IDocClasifCFDBEntity getDocClasifCFDBEntity(DbDataSource dataSource) {
		return new DocClasifCFDBEntityImpl(dataSource);
	}

	public IDocClasifCFDBEntity getDocClasifCFDBEntity(DbConnection conn) {
		return new DocClasifCFDBEntityImpl(conn);
	}

	public IDocClasifDescrDBEntity getDocClasifDescrDBEntity(
			DbDataSource dataSource) {
		return new DocClasifDescrDBEntityImpl(dataSource);
	}

	public IDocClasifDescrDBEntity getDocClasifDescrDBEntity(DbConnection conn) {
		return new DocClasifDescrDBEntityImpl(conn);
	}

	public IOcupacionDBEntity getOcupacionDBEntity(DbDataSource dataSource) {
		return new deposito.db.ibmdb2.InformeOcupacionDBEntity(dataSource);
	}

	public IOcupacionDBEntity getOcupacionDBEntity(DbConnection conn) {
		return new deposito.db.ibmdb2.InformeOcupacionDBEntity(conn);
	}

	public IHuecoDBEntity getHuecoDBEntity(DbDataSource dataSource) {
		return new deposito.db.commonPostgreSQLServer.HuecoDBEntityImpl(
				dataSource);
	}

	public IHuecoDBEntity getHuecoDBEntity(DbConnection conn) {
		return new deposito.db.commonPostgreSQLServer.HuecoDBEntityImpl(conn);
	}

	public ILockDBEntity getLockDBEntityImplBase(DbDataSource dataSource) {
		return new LockDBEntityImpl(dataSource);
	}

	public ILockDBEntity getLockDBEntityImplBase(DbConnection conn) {
		return new LockDBEntityImpl(conn);
	}
}
