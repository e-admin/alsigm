package fondos.db.ibmdb2;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.mutable.MutableObject;
import org.apache.log4j.Logger;

import common.db.DBUtils;
import common.db.DbDataSource;

import fondos.db.commonPostgreSQLServer.ElementoCuadroClasificacionPostgreeSQLServerBaseDBEntityImpl;

/**
 * 
 * Se encarga del almacenamiento y recuperacion de la base de datos de la
 * informacion comun de los elementos que forman parte del cuadro de
 * clasificacion de fondos documentales: clasificadores de fondos, fondos,
 * clasificadores de serie, series y unidades documentales
 */
public class ElementoCuadroClasificacionDBEntityImpl extends
		ElementoCuadroClasificacionPostgreeSQLServerBaseDBEntityImpl {
	static Logger logger = Logger
			.getLogger(ElementoCuadroClasificacionDBEntityImpl.class);

	/**
	 * @param dataSource
	 */
	public ElementoCuadroClasificacionDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public ElementoCuadroClasificacionDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	public List getIdsHijos(String idElementoCuadro, int tipoElemento) {

		List ret = new ArrayList();
		// obtener los hijos
		StringBuffer qual = new StringBuffer().append(" WHERE (");

		if (tipoElemento >= 0) {
			qual.append(DBUtils.generateEQTokenField(TIPO_ELEMENTO_FIELD,
					tipoElemento));
			qual.append(" AND ");
		}
		qual.append(
				DBUtils.generateEQTokenField(IDPADRE_FIELD, idElementoCuadro))
				.append(")");
		DbColumnDef columnaValue = new DbColumnDef("value", ID_ELEMENTO_FIELD);

		DbColumnDef[] cols = { columnaValue, TIPO_ELEMENTO_FIELD };

		// With
		String aliasTabla = " n ";

		String columnas = DbUtil.getColumnNamesOnly(cols);

		// Consulta1
		String columnas1 = DbUtil.getColumnNames(cols);
		String tabla1 = TABLE_NAME_ELEMENTO;
		String qual1 = DBUtils.WHERE
				+ DBUtils.generateEQTokenField(IDPADRE_FIELD, idElementoCuadro);

		// Consulta2
		String columnas2 = " nplus1.ID value, nplus1.TIPO ";
		String tablas2 = " ASGFELEMENTOCF AS nplus1,n ";
		String where2 = DBUtils.WHERE + "n.value = nplus1.IDPADRE ";

		// Consulta Final
		String whereFinal = null;
		if (tipoElemento != -1) {
			whereFinal = DBUtils.WHERE + TIPO_ELEMENTO_FIELD.getAliasOrName()
					+ " = " + String.valueOf(tipoElemento);
		}
		String columnasFinal = columnaValue.getAliasOrName();

		String sqlCompleta = DbUtil.generateDB2SqlConnectBy(aliasTabla,
				columnas, columnas1, tabla1, qual1, columnas2, tablas2, where2,
				whereFinal, columnasFinal);

		// logger.debug(sqlCompleta);

		ret = getVOS(new DbColumnDef[] { columnaValue }, sqlCompleta,
				MutableObject.class);
		/*
		 * List hijosDelElemento = getVOS(qual.toString(), TABLE_NAME_ELEMENTO,
		 * new DbColumnDef[]{ id },MutableObject.class ); if
		 * (!CollectionUtils.isEmpty(hijosDelElemento)){ for (Iterator itHijos =
		 * hijosDelElemento.iterator(); itHijos.hasNext();) { Mutable hijo =
		 * (Mutable)itHijos.next(); ret.add((String) hijo.getValue());
		 * 
		 * //obtener nietos List idsHijos =
		 * getIdsHijos((String)hijo.getValue(),tipoElemento);
		 * ret.addAll(idsHijos); } }
		 */
		return ret;
	}

	public List getIdsHijos(String idElementoCuadro) {
		return getIdsHijos(idElementoCuadro, -1);
	}

}