package deposito.db.oracle;

import ieci.core.db.DbConnection;
import ieci.core.db.DbOutputStatement;

import common.db.DBUtils;
import common.db.DbDataSource;
import common.exceptions.DBException;

import deposito.db.ElementoAsignableDBEntity;
import deposito.db.ElementoNoAsignableDBEntity;
import deposito.db.common.InformeOcupacionDBEntityBase;
import deposito.vos.HuecoVO;
import deposito.vos.InformeOcupacion;
import deposito.vos.OcupacionElementoDeposito;

/**
 * Implementación de los métodos de acceso a datos referentes a la ocupación de
 * los fondos físicos
 */
public class InformeOcupacionDBEntity extends InformeOcupacionDBEntityBase {

	public InformeOcupacionDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public InformeOcupacionDBEntity(DbConnection conn) {
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
	public InformeOcupacion getInformeOcupacionDeposito(String idDeposito) {
		StringBuffer query = new StringBuffer();
		query.append("select noasignable.nombre,");
		query.append("(select to_char(sum(asignable.numhuecos))||','||to_char(sum(asignable.LONGITUD))");
		query.append(" from asgdelemasig asignable where asignable.IDELEMNAPADRE in ");
		query.append("(select id from asgdelemnoasig start with id=noasignable.id connect by prior id=idpadre)),");
		query.append("(select count(*) from asgdhueco hueco where hueco.estado='L' and hueco.IDELEMAPADRE in ");
		query.append("(select id from asgdelemasig where idelemnapadre in ");
		query.append("(select id from asgdelemnoasig start with id=noasignable.id connect by prior id=idpadre))),");
		query.append("(select count(*) from asgdhueco hueco where hueco.estado='I' and hueco.IDELEMAPADRE in ");
		query.append("(select id from asgdelemasig where idelemnapadre in ");
		query.append("(select id from asgdelemnoasig start with id=noasignable.id connect by prior id=idpadre)))");
		query.append(",0,0,noasignable.idtipoelemento,noasignable.id");
		query.append(" from asgdelemnoasig noasignable where ");
		query.append("noasignable.iddeposito='").append(idDeposito)
				.append("' ");
		query.append("and noasignable.idpadre is null order by noasignable.idtipoelemento, noasignable.numorden");
		InformeOcupacion informeOcupacion = new InformeOcupacion();
		obtenerEntradasInforme(query.toString(), informeOcupacion);
		return informeOcupacion;
	}

	/**
	 * Recupera los datos de ocupación de un elemento no asignable del fondo
	 * físico
	 * 
	 * @param idElementoAsignable
	 *            Identificador del elemento no asignable
	 * @return Datos de ocupación del elemento
	 */
	public InformeOcupacion getInformeOcupacionElementoNoAsignable(
			String idNoAsignable) {
		StringBuffer query = new StringBuffer();
		query.append("select noasignable.nombre, ");
		query.append("(select to_char(sum(asignable.numhuecos))||','||to_char(sum(asignable.LONGITUD)) ");
		query.append("from asgdelemasig asignable where asignable.IDELEMNAPADRE in ");
		query.append("(select id from asgdelemnoasig start with id=noasignable.id connect by prior id=idpadre)),");
		query.append("(select count(*) from asgdhueco hueco where hueco.estado='L' and hueco.IDELEMAPADRE in ");
		query.append("(select id from asgdelemasig where idelemnapadre in ");
		query.append("(select id from asgdelemnoasig start with id=noasignable.id connect by prior id=idpadre))) huecosLibres, ");
		query.append("(select count(*) from asgdhueco hueco where hueco.estado='I' and hueco.IDELEMAPADRE in ");
		query.append("(select id from asgdelemasig where idelemnapadre in ");
		query.append("(select id from asgdelemnoasig start with id=noasignable.id connect by prior id=idpadre))) huecosInutilizados ");
		query.append(",0,0,noasignable.idtipoelemento,noasignable.id");
		query.append(" from asgdelemnoasig noasignable ");
		query.append("where noasignable.idpadre='")
				.append(idNoAsignable)
				.append("' order by noasignable.idtipoelemento, noasignable.numorden ");
		InformeOcupacion informeOcupacion = new InformeOcupacion();
		obtenerEntradasInforme(query.toString(), informeOcupacion);
		query.setLength(0);
		query.append("select asignable.NOMBRE, to_char(asignable.numhuecos)||','||to_char(asignable.LONGITUD),");
		query.append("(select count(*) from asgdhueco where idelemapadre=asignable.id and estado='L') ");
		query.append(",(select count(*) from asgdhueco where idelemapadre=asignable.id and estado='I') ");
		query.append(",0,0,asignable.idtipoelemento,asignable.id");
		query.append(" from asgdelemasig asignable where idelemnapadre='")
				.append(idNoAsignable)
				.append("' order by asignable.idtipoelemento, asignable.numorden");
		obtenerEntradasInforme(query.toString(), informeOcupacion);
		return informeOcupacion;
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
	public OcupacionElementoDeposito getOcupacionElementoNoAsignable(
			String idElemento, String tipoElemento, String idFormato) {
		StringBuffer sqlQuery = new StringBuffer()
				.append("select sum(numhuecos), sum(longitud) from asgdelemasig where 1=1 ");
		if (idFormato != null)
			sqlQuery.append(" AND ").append(
					DBUtils.generateEQTokenField(
							ElementoAsignableDBEntity.CAMPO_IDFMTHUECO,
							idFormato));
		sqlQuery.append(" AND idelemnapadre in ")
				.append("(select id from asgdelemnoasig start with ")
				.append(DBUtils.generateEQTokenField(
						ElementoNoAsignableDBEntity.CAMPO_ID, idElemento))
				.append(" connect by prior id=idpadre)");
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
					.append(DBUtils
							.generateEQTokenField(
									HuecoDBEntityImpl.CAMPO_ESTADO,
									HuecoVO.LIBRE_STATE))
					.append(" AND IDELEMAPADRE in ")
					.append(" (select id from asgdelemasig where 1=1 ");
			if (idFormato != null)
				sqlQuery.append(" AND ").append(
						DBUtils.generateEQTokenField(
								ElementoAsignableDBEntity.CAMPO_IDFMTHUECO,
								idFormato));
			sqlQuery.append(" AND idelemnapadre in ")
					.append("(select id from asgdelemnoasig start with ")
					.append(DBUtils.generateEQTokenField(
							ElementoNoAsignableDBEntity.CAMPO_ID, idElemento))
					.append(" connect by prior id=idpadre))");
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
	// private void obtenerEntradasInforme(String query, InformeOcupacion
	// informeOcupacion) {
	// DbOutputStatement stmt = null;
	// try {
	// stmt = new DbOutputStatement();
	// stmt.create(getConnection(), query.toString());
	// stmt.execute();
	// if (stmt.next()) {
	// String descripcionLineaInforme = null;
	// String[] jHuecos = null;
	// do {
	// jHuecos = stmt.getShortText(2).split(",");
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

}