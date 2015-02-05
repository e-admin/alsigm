package common.db;

import fondos.db.IElementoCuadroClasificacionDbEntity;
import fondos.db.IProductorSerieDbEntity;
import ieci.core.db.DbConnection;
import ieci.core.db.DbEngine;
import transferencias.db.IRelacionEntregaDBEntity;

import common.lock.db.ILockDBEntity;

import deposito.db.IElementoAsignableDBEntity;
import deposito.db.IElementoNoAsignableDBEntity;
import deposito.db.IHuecoDBEntity;
import deposito.db.IOcupacionDBEntity;
import docelectronicos.db.IDocClasifCFDBEntity;
import docelectronicos.db.IDocClasifDescrDBEntity;

/**
 * Factoría de Oracle
 * 
 * @author IECISA
 * 
 */
public abstract class DBEntityFactoryOracle extends DbEntityFactoryBase {

	public int getDataBaseType() {
		return DbEngine.ORACLE;
	}

	public IRelacionEntregaDBEntity getRelacionDBEntity(DbDataSource dataSource) {
		return new transferencias.db.oracle.RelacionEntregaDBEntityImpl(
				dataSource);
	}

	public IRelacionEntregaDBEntity getRelacionDBEntity(DbConnection conn) {
		return new transferencias.db.oracle.RelacionEntregaDBEntityImpl(conn);
	}

	public IElementoCuadroClasificacionDbEntity getElementoCuadroClasificacionDBEntity(
			DbDataSource dataSource) {
		return new fondos.db.oracle.ElementoCuadroClasificacionDBEntityImpl(
				dataSource);
	}

	public IElementoCuadroClasificacionDbEntity getElementoCuadroClasificacionDBEntity(
			DbConnection conn) {
		return new fondos.db.oracle.ElementoCuadroClasificacionDBEntityImpl(
				conn);
	}

	public IProductorSerieDbEntity getProductorSerieDBEntity(
			DbDataSource dataSource) {
		return new fondos.db.oracle.ProductorSerieDBEntityImpl(dataSource);
	}

	public IProductorSerieDbEntity getProductorSerieDBEntity(DbConnection conn) {
		return new fondos.db.oracle.ProductorSerieDBEntityImpl(conn);
	}

	public IDocClasifCFDBEntity getDocClasifCFDBEntity(DbDataSource dataSource) {
		return new docelectronicos.db.DocClasifCFDBEntityImpl(dataSource);
	}

	public IDocClasifCFDBEntity getDocClasifCFDBEntity(DbConnection conn) {
		return new docelectronicos.db.DocClasifCFDBEntityImpl(conn);
	}

	public IDocClasifDescrDBEntity getDocClasifDescrDBEntity(
			DbDataSource dataSource) {
		return new docelectronicos.db.DocClasifDescrDBEntityImpl(dataSource);
	}

	public IDocClasifDescrDBEntity getDocClasifDescrDBEntity(DbConnection conn) {
		return new docelectronicos.db.DocClasifDescrDBEntityImpl(conn);
	}

	public ILockDBEntity getLockDBEntityImplBase(DbDataSource dataSource) {
		return new common.lock.db.oracle.LockDBEntityImpl(dataSource);
	}

	public ILockDBEntity getLockDBEntityImplBase(DbConnection conn) {
		return new common.lock.db.oracle.LockDBEntityImpl(conn);
	}

	public IHuecoDBEntity getHuecoDBEntity(DbDataSource dataSource) {
		return new deposito.db.oracle.HuecoDBEntityImpl(dataSource);
	}

	public IHuecoDBEntity getHuecoDBEntity(DbConnection conn) {
		return new deposito.db.oracle.HuecoDBEntityImpl(conn);
	}

	public IOcupacionDBEntity getOcupacionDBEntity(DbDataSource dataSource) {
		return new deposito.db.oracle.InformeOcupacionDBEntity(dataSource);
	}

	public IOcupacionDBEntity getOcupacionDBEntity(DbConnection conn) {
		return new deposito.db.oracle.InformeOcupacionDBEntity(conn);
	}

	public abstract IElementoNoAsignableDBEntity getElmentoNoAsignableDBEntity(
			DbDataSource dataSource);

	public abstract IElementoNoAsignableDBEntity getElmentoNoAsignableDBEntity(
			DbConnection conn);

	public abstract IElementoAsignableDBEntity getElementoAsignableDBEntity(
			DbDataSource dataSource);

	public abstract IElementoAsignableDBEntity getElementoAsignableDBEntity(
			DbConnection conn);

}