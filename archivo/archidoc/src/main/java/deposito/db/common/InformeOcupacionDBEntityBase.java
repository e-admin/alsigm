/*
 * Created on 17-ene-2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package deposito.db.common;

import ieci.core.db.DbConnection;
import ieci.core.db.DbOutputStatement;

import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.exceptions.DBException;

import deposito.db.ElementoAsignableDBEntity;
import deposito.db.IOcupacionDBEntity;
import deposito.vos.DepositoVO;
import deposito.vos.HuecoVO;
import deposito.vos.InformeOcupacion;
import deposito.vos.OcupacionElementoDeposito;

public abstract class InformeOcupacionDBEntityBase extends DBEntity implements
		IOcupacionDBEntity {

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return "";
	}

	/**
	 * @param dataSource
	 */
	public InformeOcupacionDBEntityBase(DbDataSource dataSource) {
		super(dataSource);
	}

	public InformeOcupacionDBEntityBase(DbConnection conn) {
		super(conn);
	}

	/**
	 * Recupera los datos de ocupación de una de las ubicaciones del fondo
	 * físico
	 * 
	 * @param idElementoAsignable
	 *            Identificador de la ubicación
	 * @return Datos de ocupación del elemento
	 */
	public abstract InformeOcupacion getInformeOcupacionDeposito(
			String idDeposito);

	/**
	 * Recupera los datos de ocupación de un elemento no asignable del fondo
	 * físico
	 * 
	 * @param idElementoAsignable
	 *            Identificador del elemento no asignable
	 * @return Datos de ocupación del elemento
	 */
	public abstract InformeOcupacion getInformeOcupacionElementoNoAsignable(
			String idNoAsignable);

	/**
	 * Recupera la ocupación de los huecos del formato indicado para una
	 * ubicación del fondo físico
	 * 
	 * @param idUbicacion
	 *            Identificador de ubicación
	 * @param idFormato
	 *            Identificador de formato. Puede ser nulo
	 * @return Datos de ocupación de la ubicación
	 */
	public OcupacionElementoDeposito getOcupacionUbicacion(String idUbicacion,
			String idFormato) {
		StringBuffer sqlQuery = new StringBuffer()
				.append("select sum(numhuecos), sum(longitud) from asgdelemasig where ")
				.append(DBUtils
						.generateEQTokenField(
								ElementoAsignableDBEntity.CAMPO_IDDEPOSITO,
								idUbicacion));
		if (idFormato != null)
			sqlQuery.append(" AND ").append(
					DBUtils.generateEQTokenField(
							ElementoAsignableDBEntity.CAMPO_IDFMTHUECO,
							idFormato));
		DbOutputStatement stmt = null;
		OcupacionElementoDeposito ocupacionDeposito = new OcupacionElementoDeposito(
				idUbicacion, DepositoVO.ID_TIPO_ELEMENTO_UBICACION);
		try {
			stmt = new DbOutputStatement();
			stmt.create(getConnection(), sqlQuery.toString());
			stmt.execute();
			if (stmt.next()) {
				ocupacionDeposito.setNumeroHuecos(stmt.getLongInteger(1));
				ocupacionDeposito.setLongitud(stmt.getLongInteger(2));
			}
			stmt.release();
			sqlQuery.setLength(0);
			sqlQuery.append("select count(*) from asgdhueco where ")
					.append(DBUtils
							.generateEQTokenField(
									HuecoDbEntityImplBase.CAMPO_IDDEPOSITO,
									idUbicacion))
					.append(" AND ")
					.append(DBUtils.generateEQTokenField(
							HuecoDbEntityImplBase.CAMPO_ESTADO,
							HuecoVO.LIBRE_STATE));
			if (idFormato != null)
				sqlQuery.append(" AND ")
						.append(DBUtils.generateEQTokenField(
								HuecoDbEntityImplBase.CAMPO_FORMATO, idFormato));
			stmt = new DbOutputStatement();
			stmt.create(getConnection(), sqlQuery.toString());
			stmt.execute();
			if (stmt.next())
				ocupacionDeposito.setHuecosLibres(stmt.getLongInteger(1));
			stmt.release();

			return ocupacionDeposito;
		} catch (Exception e) {
			try {
				DbOutputStatement.ensureRelease(stmt, e);
			} catch (Exception ere) {
			}
			throw new DBException(e);
		}
	}

	/**
	 * Recupera la ocupación de los huecos del formato indicado para un elemento
	 * no asignable del fondo físico
	 * 
	 * @param idUbicacion
	 *            Identificador de elemento no asignable
	 * @param tipoElemento
	 *            Tipo de elemento no asignable
	 * @param idFormato
	 *            Identificador de formato. Puede ser nulo
	 * @return Datos de ocupación del elemento no asignable
	 */
	public abstract OcupacionElementoDeposito getOcupacionElementoNoAsignable(
			String idElemento, String tipoElemento, String idFormato);

	/**
	 * Recupera la ocupación de los huecos del formato indicado para un elemento
	 * asignable del fondo físico
	 * 
	 * @param idElementoAsignable
	 *            Identificador de elemento asignable
	 * @param tipoElemento
	 *            Tipo de elemento asignable
	 * @param idFormato
	 *            Identificador de formato. Puede ser nulo
	 * @return Datos de ocupación del elemento asignable
	 */
	public OcupacionElementoDeposito getOcupacionElementoAsignable(
			String idElementoAsignable, String tipoElemento, String idFormato) {
		StringBuffer sqlQuery = new StringBuffer().append(
				"select numhuecos, longitud from asgdelemasig where ")
				.append(DBUtils
						.generateEQTokenField(
								ElementoAsignableDBEntity.CAMPO_ID,
								idElementoAsignable));
		if (idFormato != null)
			sqlQuery.append(" AND ").append(
					DBUtils.generateEQTokenField(
							ElementoAsignableDBEntity.CAMPO_IDFMTHUECO,
							idFormato));
		DbOutputStatement stmt = null;
		OcupacionElementoDeposito ocupacionDeposito = new OcupacionElementoDeposito(
				idElementoAsignable, tipoElemento);
		try {
			stmt = new DbOutputStatement();
			stmt.create(getConnection(), sqlQuery.toString());
			stmt.execute();
			if (stmt.next()) {
				ocupacionDeposito.setNumeroHuecos(stmt.getLongInteger(1));
				ocupacionDeposito.setLongitud(stmt.getLongInteger(2));
			}
			stmt.release();
			sqlQuery.setLength(0);
			sqlQuery.append("select count(*) from  asgdhueco where ")
					.append(DBUtils.generateEQTokenField(
							HuecoDbEntityImplBase.CAMPO_IDELEMPADRE,
							idElementoAsignable))
					.append(" AND ")
					.append(DBUtils.generateEQTokenField(
							HuecoDbEntityImplBase.CAMPO_ESTADO,
							HuecoVO.LIBRE_STATE));
			if (idFormato != null)
				sqlQuery.append(" AND ")
						.append(DBUtils.generateEQTokenField(
								HuecoDbEntityImplBase.CAMPO_FORMATO, idFormato));
			stmt = new DbOutputStatement();
			stmt.create(getConnection(), sqlQuery.toString());
			stmt.execute();
			if (stmt.next())
				ocupacionDeposito.setHuecosLibres(stmt.getLongInteger(1));
			stmt.release();

			return ocupacionDeposito;
		} catch (Exception e) {
			try {
				DbOutputStatement.ensureRelease(stmt, e);
			} catch (Exception ere) {
			}
			throw new DBException(e);
		}
	}

	protected void obtenerEntradasInforme(String query,
			InformeOcupacion informeOcupacion) {
		DbOutputStatement stmt = null;
		try {
			stmt = new DbOutputStatement();
			stmt.create(getConnection(), query.toString());
			stmt.execute();
			if (stmt.next()) {
				String descripcionLineaInforme = null;
				String[] jHuecos = null;
				do {
					jHuecos = stmt.getShortText(2).split(",");
					descripcionLineaInforme = stmt.getShortText(1);
					informeOcupacion.addEntradaInforme(
							stmt.getShortText(8),
							stmt.getShortText(7),
							descripcionLineaInforme,
							jHuecos.length > 0 ? Integer.parseInt(jHuecos[0]
									.trim()) : 0,
							stmt.getLongInteger(3),
							stmt.getLongInteger(4),
							jHuecos.length > 1 ? Float.parseFloat(jHuecos[1]
									.trim()) : 0, stmt.getLongInteger(5), stmt
									.getLongInteger(6));
				} while (stmt.next());
			}
			stmt.release();
		} catch (Exception e) {
			try {
				DbOutputStatement.ensureRelease(stmt, e);
			} catch (Exception ere) {
				throw new DBException(ere);
			}
		}
	}

}
