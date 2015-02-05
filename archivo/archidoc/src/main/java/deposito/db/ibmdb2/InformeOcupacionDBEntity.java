package deposito.db.ibmdb2;

import ieci.core.db.DbConnection;
import ieci.core.db.DbOutputStatement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import util.CollectionUtils;

import common.db.DBEntityFactory;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.exceptions.DBException;

import deposito.db.ElementoAsignableDBEntity;
import deposito.db.IElementoNoAsignableDBEntity;
import deposito.db.common.HuecoDbEntityImplBase;
import deposito.db.common.InformeOcupacionDBEntityBase;
import deposito.vos.HuecoVO;
import deposito.vos.InformeOcupacion;
import deposito.vos.OcupacionElementoDeposito;

public class InformeOcupacionDBEntity extends InformeOcupacionDBEntityBase {

	/**
	 * @param dataSource
	 */
	public InformeOcupacionDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public InformeOcupacionDBEntity(DbConnection conn) {
		super(conn);
	}

	private String getSqlHijosElemento(String idElementoNoAsignable) {
		StringBuffer queryIns = new StringBuffer("'").append(
				idElementoNoAsignable).append("'");
		List hijos = getIdsHijosNoAsignables(idElementoNoAsignable);
		for (Iterator itHijos = hijos.iterator(); itHijos.hasNext();) {
			String hijo = (String) itHijos.next();
			if (queryIns.length() > 0)
				queryIns.append(",");
			queryIns.append("'").append(hijo).append("'");
		}
		return queryIns.toString();
	}

	private void obtenerNumhuecosYLongitud(OcupacionElementoDeposito entrada) {
		// DbConnection conn = getConnection();
		// String concat = DBUtils.getNativeConcatSyntax(conn);
		String sqlForIn = getSqlHijosElemento(entrada.getIdElemento());
		StringBuffer sqlNumHuecosyLongitud = new StringBuffer();
		if (sqlForIn.length() > 0) {

			DbOutputStatement stmt = null;
			try {

				sqlNumHuecosyLongitud
						.append("(")
						.append(DBUtils.SELECT)
						.append(DBUtils.generateSUM(
								ElementoAsignableDBEntity.CAMPO_NUMHUECOS,
								"numeroHuecos"))
						.append(",")
						.append(DBUtils.generateSUM(
								ElementoAsignableDBEntity.CAMPO_LONGITUD,
								"longitud"))
						.append(DBUtils.FROM)
						.append(ElementoAsignableDBEntity.TABLE_NAME)
						.append(DBUtils.WHERE)
						.append(DBUtils.generateInTokenFieldSubQuery(
								ElementoAsignableDBEntity.CAMPO_IDELEMNPADRE,
								sqlForIn)).append(")");

				/*
				 * sqlNumHuecosyLongitud.append("(select ")
				 * .append(DBUtils.getNativeStrSyntax(conn,
				 * "sum(asignable.numhuecos)"
				 * )).append(concat).append("','").append(concat)
				 * .append(DBUtils.getNativeStrSyntax(conn,
				 * "sum(asignable.LONGITUD)")) .append(
				 * "from asgdelemasig asignable where asignable.IDELEMNAPADRE in ("
				 * ).append(sqlForIn).append("))");
				 */

				stmt = new DbOutputStatement();
				stmt.create(getConnection(), sqlNumHuecosyLongitud.toString());
				stmt.execute();
				if (stmt.next()) {
					// String[] jHuecos = null;
					do {
						if (stmt.getShortText(1) != null) {
							int numeroHuecos = stmt.getLongInteger(1);
							entrada.setNumeroHuecos(numeroHuecos);

							logger.debug(String.valueOf(stmt.getShortDecimal(2)));

							float longitud = stmt.getShortDecimal(2);
							entrada.setLongitud(longitud);

							/*
							 * jHuecos = stmt.getShortText(1).split(",");
							 * entrada.setNumeroHuecos(jHuecos.length > 0 ?
							 * Integer.parseInt(jHuecos[0].trim()) : 0);
							 * entrada.setLongitud(jHuecos.length > 1 ?
							 * Integer.parseInt(jHuecos[1].trim()) : 0);
							 */
						}
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

	// TODO ZMIGRACION BD - CONSULTAS JERÁRQUICAS - PROBADO ALICIA
	private void obtenerNumHuecosLibres(OcupacionElementoDeposito entrada) {
		String sqlForIn = getSqlHijosElemento(entrada.getIdElemento());
		if (sqlForIn.length() > 0) {
			StringBuffer sqlNumHuecosLibres = new StringBuffer();
			sqlNumHuecosLibres
					.append("select count(*) from asgdhueco hueco where hueco.estado='L'");
			sqlNumHuecosLibres
					.append("	and hueco.IDELEMAPADRE in (select id from asgdelemasig where idelemnapadre in (");
			sqlNumHuecosLibres.append(sqlForIn).append("))");
			DbOutputStatement stmt = null;
			try {
				stmt = new DbOutputStatement();
				stmt.create(getConnection(), sqlNumHuecosLibres.toString());
				stmt.execute();
				if (stmt.next()) {
					do {
						entrada.setHuecosLibres(stmt.getLongInteger(1));
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

	private void obtenerNumHuecosInutilizados(OcupacionElementoDeposito entrada) {
		String sqlForIn = getSqlHijosElemento(entrada.getIdElemento());
		if (sqlForIn.length() > 0) {
			StringBuffer sqlNumHuecosInutilizados = new StringBuffer();
			sqlNumHuecosInutilizados
					.append("select count(*) from asgdhueco hueco where hueco.estado='I'");
			sqlNumHuecosInutilizados
					.append("	and hueco.IDELEMAPADRE in (select id from asgdelemasig where idelemnapadre in (");
			sqlNumHuecosInutilizados.append(sqlForIn).append("))");
			DbOutputStatement stmt = null;
			try {
				stmt = new DbOutputStatement();
				stmt.create(getConnection(),
						sqlNumHuecosInutilizados.toString());
				stmt.execute();
				if (stmt.next()) {
					do {
						entrada.setHuecosInutilizados(stmt.getLongInteger(1));
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

	public InformeOcupacion getInformeOcupacionDeposito(String idDeposito) {
		// obtener no asignables del deposito
		StringBuffer querynoAsignablesDelDeposito = new StringBuffer(
				"select noasignable.nombre,0,0,noasignable.idtipoelemento,noasignable.id");
		querynoAsignablesDelDeposito
				.append(" from asgdelemnoasig noasignable ");
		querynoAsignablesDelDeposito.append(" where noasignable.iddeposito='")
				.append(idDeposito).append("' ");
		querynoAsignablesDelDeposito
				.append(" and noasignable.idpadre is null order by noasignable.idtipoelemento, noasignable.numorden ");

		List entradasInforme = obtenerInfoNoAsignableEntradasInforme(querynoAsignablesDelDeposito
				.toString());

		// actualizar numero de huecos y longitud
		InformeOcupacion informeOcupacion = new InformeOcupacion();

		if (!CollectionUtils.isEmpty(entradasInforme)) {
			for (Iterator itentradas = entradasInforme.iterator(); itentradas
					.hasNext();) {
				RetObtenerInfoNoAsignableEntradasInforme entrada = (RetObtenerInfoNoAsignableEntradasInforme) itentradas
						.next();
				OcupacionElementoDeposito ocupacion = entrada.ocupacion;
				obtenerNumhuecosYLongitud(entrada.ocupacion);
				obtenerNumHuecosLibres(entrada.ocupacion);
				obtenerNumHuecosInutilizados(entrada.ocupacion);
				informeOcupacion.addEntradaInforme(ocupacion.getIdElemento(),
						ocupacion.getIdTipoElemento(),
						ocupacion.getDescripcion(),
						ocupacion.getNumeroHuecos(),
						ocupacion.getHuecosLibres(),
						ocupacion.getHuecosInutilizados(),
						ocupacion.getLongitud(), entrada.huecosOcupados,
						entrada.huecosReservados);

			}
		}
		return informeOcupacion;
	}

	private class RetObtenerInfoNoAsignableEntradasInforme {
		OcupacionElementoDeposito ocupacion;
		int huecosOcupados;
		int huecosReservados;
	}

	private List obtenerInfoNoAsignableEntradasInforme(String query) {

		List entradas = new ArrayList();
		DbOutputStatement stmt = null;
		try {
			stmt = new DbOutputStatement();
			stmt.create(getConnection(), query.toString());
			stmt.execute();
			if (stmt.next()) {
				String descripcionLineaInforme = null;
				do {
					descripcionLineaInforme = stmt.getShortText(1);

					OcupacionElementoDeposito ocupacion = new OcupacionElementoDeposito(
							stmt.getShortText(5), stmt.getShortText(4),
							descripcionLineaInforme, 0, 0, 0, 0);

					RetObtenerInfoNoAsignableEntradasInforme retObtenerInfoNoAsignableEntradasInforme = new RetObtenerInfoNoAsignableEntradasInforme();
					retObtenerInfoNoAsignableEntradasInforme.ocupacion = ocupacion;
					retObtenerInfoNoAsignableEntradasInforme.huecosOcupados = stmt
							.getLongInteger(2);// huecos ocupados;
					retObtenerInfoNoAsignableEntradasInforme.huecosReservados = stmt
							.getLongInteger(3);// huecos reservados

					entradas.add(retObtenerInfoNoAsignableEntradasInforme);

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
		return entradas;
	}

	// private void obtenerNumHuecosYLongitudNoAsignableEntradasInforme(String
	// query, InformeOcupacion informeOcupacion) {
	// DbOutputStatement stmt = null;
	// try {
	// stmt = new DbOutputStatement();
	// stmt.create(getConnection(), query.toString());
	// stmt.execute();
	// if (stmt.next()) {
	// String descripcionLineaInforme = null;
	// String[] jHuecos = null;
	// do {
	// jHuecos = stmt.getShortText(1).split(",");
	// descripcionLineaInforme = stmt.getShortText(1);
	// informeOcupacion.addEntradaInforme(stmt.getShortText(7),
	// stmt.getShortText(6),
	// descripcionLineaInforme,
	// jHuecos.length > 0 ? Integer.parseInt(jHuecos[0]) : 0,
	// stmt.getLongInteger(3),
	// jHuecos.length > 1 ? Integer.parseInt(jHuecos[1]) : 0,
	// stmt.getLongInteger(4), stmt.getLongInteger(5));
	// } while (stmt.next());
	// }
	// stmt.release();
	// } catch (Exception e) {
	// try {
	// DbOutputStatement.ensureRelease(stmt, e);
	// } catch (Exception ere) {
	// throw new DBException(ere);
	// }
	// }
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see deposito.db.InformeOcupacionDBEntityBase#
	 * getInformeOcupacionElementoNoAsignable(java.lang.String)
	 */
	// TODO ZMIGRACION BD - BY PRIOR ( PROBADO
	public InformeOcupacion getInformeOcupacionElementoNoAsignable(
			String idNoAsignable) {
		// obtener no asignables del deposito
		StringBuffer querynoAsignablesDelDeposito = new StringBuffer(
				"select noasignable.nombre,0,0,noasignable.idtipoelemento,noasignable.id");
		querynoAsignablesDelDeposito
				.append(" from asgdelemnoasig noasignable ");
		querynoAsignablesDelDeposito
				.append("where noasignable.idpadre='")
				.append(idNoAsignable)
				.append("' order by noasignable.idtipoelemento, noasignable.numorden ");
		InformeOcupacion informeOcupacion = new InformeOcupacion();

		List entradasInforme = obtenerInfoNoAsignableEntradasInforme(querynoAsignablesDelDeposito
				.toString());

		// actualizar numero de huecos y longitud

		if (!CollectionUtils.isEmpty(entradasInforme)) {
			for (Iterator itentradas = entradasInforme.iterator(); itentradas
					.hasNext();) {
				RetObtenerInfoNoAsignableEntradasInforme entrada = (RetObtenerInfoNoAsignableEntradasInforme) itentradas
						.next();
				OcupacionElementoDeposito ocupacion = entrada.ocupacion;
				obtenerNumhuecosYLongitud(entrada.ocupacion);
				obtenerNumHuecosLibres(entrada.ocupacion);
				obtenerNumHuecosInutilizados(entrada.ocupacion);
				informeOcupacion.addEntradaInforme(ocupacion.getIdElemento(),
						ocupacion.getIdTipoElemento(),
						ocupacion.getDescripcion(),
						ocupacion.getNumeroHuecos(),
						ocupacion.getHuecosLibres(),
						ocupacion.getHuecosInutilizados(),
						ocupacion.getLongitud(), entrada.huecosOcupados,
						entrada.huecosReservados);

			}
		}

		// //actualizar numero de huecos y longitud
		// List entradasInforme = informeOcupacion.getEntradasInforme();
		// if (!CollectionUtils.isEmpty(entradasInforme)){
		// for (Iterator itentradas = entradasInforme.iterator();
		// itentradas.hasNext();) {
		// OcupacionElementoDeposito entrada = (OcupacionElementoDeposito)
		// itentradas.next();
		// obtenerNumhuecosYLongitud(entrada);
		// obtenerNumHuecosLibres(entrada);
		// }
		// }

		StringBuffer query = new StringBuffer();
		query.setLength(0);

		query.append("select asignable.NOMBRE, ")
				.append("asignable.numhuecos, ")
				.append("(select count(*) from asgdhueco where idelemapadre=asignable.id and estado='L'), ")
				.append("(select count(*) from asgdhueco where idelemapadre=asignable.id and estado='I') ")
				.append(",0,0,asignable.idtipoelemento,asignable.id, asignable.LONGITUD")
				.append(" from asgdelemasig asignable where idelemnapadre='")
				.append(idNoAsignable)
				.append("' order by asignable.idtipoelemento, asignable.numorden");
		obtenerEntradasInforme(query.toString(), informeOcupacion);
		return informeOcupacion;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * deposito.db.InformeOcupacionDBEntityBase#getOcupacionElementoNoAsignable
	 * (java.lang.String, java.lang.String, java.lang.String)
	 */
	// TODO ZMIGRACION BD - BY PRIOR ( PROBADO CON COMPROBAR RESERVA
	public OcupacionElementoDeposito getOcupacionElementoNoAsignable(
			String idElemento, String tipoElemento, String idFormato) {
		String sqlHijosnoAsignableDeElemento = getSqlHijosElemento(idElemento);
		StringBuffer sqlQuery = new StringBuffer()
				.append("select sum(numhuecos), decimal(sum(longitud)) from asgdelemasig where 1=1 ");
		if (idFormato != null)
			sqlQuery.append(" AND ").append(
					DBUtils.generateEQTokenField(
							ElementoAsignableDBEntity.CAMPO_IDFMTHUECO,
							idFormato));
		sqlQuery.append(" AND idelemnapadre in ").append("(")
				.append(sqlHijosnoAsignableDeElemento).append(")");
		DbOutputStatement stmt = null;
		OcupacionElementoDeposito ocupacionDeposito = new OcupacionElementoDeposito(
				idElemento, tipoElemento);
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
					.append(DBUtils.generateEQTokenField(
							HuecoDbEntityImplBase.CAMPO_ESTADO,
							HuecoVO.LIBRE_STATE))
					.append(" AND IDELEMAPADRE in ")
					.append(" (select id from asgdelemasig where 1=1 ");
			if (idFormato != null)
				sqlQuery.append(" AND ").append(
						DBUtils.generateEQTokenField(
								ElementoAsignableDBEntity.CAMPO_IDFMTHUECO,
								idFormato));
			sqlQuery.append(" AND idelemnapadre in ").append("(")
					.append(sqlHijosnoAsignableDeElemento).append("))");
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

	public List getIdsHijosNoAsignables(String idNoAsignablePadre) {
		IElementoNoAsignableDBEntity noAsignableEntity = DBEntityFactory
				.getInstance(getDbFactoryClass())
				.getElmentoNoAsignableDBEntity(getDataSource());
		return noAsignableEntity.getIdsDescendientes(idNoAsignablePadre);
	}

	protected void obtenerEntradasInforme(String query,
			InformeOcupacion informeOcupacion) {
		DbOutputStatement stmt = null;
		try {
			stmt = new DbOutputStatement();
			stmt.create(getConnection(), query.toString());
			stmt.execute();
			if (stmt.next()) {
				// String descripcionLineaInforme = null;
				// String[] jHuecos = null;
				do {
					// jHuecos = stmt.getShortText(2).split(",");
					// descripcionLineaInforme = stmt.getShortText(1);

					String idElemento = stmt.getShortText(7);
					String idTipoElemento = stmt.getShortText(6);
					String descripcion = stmt.getShortText(1);
					int numeroHuecos = stmt.getLongInteger(2);
					int huecosLibres = stmt.getLongInteger(3);
					int huecosInutilizados = stmt.getLongInteger(4);
					int huecosOcupados = stmt.getLongInteger(5);
					int huecosReservados = stmt.getLongInteger(6);
					float longitud = stmt.getShortDecimal(9);

					informeOcupacion.addEntradaInforme(idElemento,
							idTipoElemento, descripcion, numeroHuecos,
							huecosLibres, huecosInutilizados, longitud,
							huecosOcupados, huecosReservados);

					/*
					 * informeOcupacion.addEntradaInforme(stmt.getShortText(7),
					 * stmt.getShortText(6), descripcionLineaInforme,
					 * jHuecos.length > 0 ? Integer.parseInt(jHuecos[0].trim())
					 * : 0, stmt.getLongInteger(3), jHuecos.length > 1 ?
					 * Integer.parseInt(jHuecos[1].trim()) : 0,
					 * stmt.getLongInteger(4), stmt.getLongInteger(5));
					 */
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
