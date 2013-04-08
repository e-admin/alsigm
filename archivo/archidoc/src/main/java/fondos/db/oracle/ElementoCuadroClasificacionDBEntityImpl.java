package fondos.db.oracle;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import util.CollectionUtils;

import common.db.DBUtils;
import common.db.DbDataSource;
import common.util.CustomDateRange;

import fondos.db.ElementoCuadroClasificacionDBEntityImplBase;
import fondos.db.SerieDBEntityImpl;
import fondos.db.VolumenSerieDBEntityImpl;
import fondos.model.ElementoCuadroClasificacion;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.VolumenSerieVO;

/**
 * 
 * Se encarga del almacenamiento y recuperacion de la base de datos de la
 * informacion comun de los elementos que forman parte del cuadro de
 * clasificacion de fondos documentales: clasificadores de fondos, fondos,
 * clasificadores de serie, series y unidades documentales
 */
public class ElementoCuadroClasificacionDBEntityImpl extends
		ElementoCuadroClasificacionDBEntityImplBase {

	/**
	 * @param dataSource
	 */
	public ElementoCuadroClasificacionDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public ElementoCuadroClasificacionDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	public List getAncestors(String idElemento) {
		StringBuffer qual = new StringBuffer("CONNECT BY PRIOR ")
				.append(IDPADRE_COLUMN_NAME)
				.append("=")
				.append(ID_ELEMENTO_COLUMN_NAME_S)
				.append(" START WITH ")
				.append(DBUtils.generateEQTokenField(ID_ELEMENTO_FIELD,
						idElemento));
		List ancestors = getElementos(qual.toString(), (String) null);
		if (!CollectionUtils.isEmpty(ancestors)) {
			ancestors.remove(0);
			Collections.reverse(ancestors);
		}
		return ancestors;
	}

	public CustomDateRange getFechasExtremasClasificadorSeries(
			String idClfSeries) {
		String qual = new StringBuffer()
				.append("WHERE ")
				.append(SerieDBEntityImpl.CAMPO_ID.getQualifiedName())
				.append(" IN (SELECT ")
				.append(ID_ELEMENTO_FIELD.getQualifiedName())
				.append(" FROM ")
				.append(TABLE_NAME_ELEMENTO)
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(TIPO_ELEMENTO_FIELD,
						ElementoCuadroClasificacion.TIPO_SERIE))
				.append(" CONNECT BY PRIOR ")
				.append(DBUtils.generateJoinCondition(ID_ELEMENTO_FIELD,
						IDPADRE_FIELD))
				.append(" START WITH ")
				.append(DBUtils.generateEQTokenField(ID_ELEMENTO_FIELD,
						idClfSeries)).append(")").toString();

		DbColumnDef[] cols = new DbColumnDef[] {
				new DbColumnDef("initialDate", (String) null,
						"MIN("
								+ SerieDBEntityImpl.CAMPO_FECHAINICIO
										.getQualifiedName() + ")",
						DbDataType.DATE_TIME, false),
				new DbColumnDef("finalDate", (String) null, "MAX("
						+ SerieDBEntityImpl.CAMPO_FECHAFIN.getQualifiedName()
						+ ")", DbDataType.DATE_TIME, false) };

		return (CustomDateRange) getVO(qual,
				SerieDBEntityImpl.TABLE_NAME_SERIE, cols, CustomDateRange.class);
	}

	public ElementoCuadroClasificacionVO getElementoPadre(String idElemento) {
		StringBuffer qual = new StringBuffer(" WHERE LEVEL=2 ")
				.append("CONNECT BY PRIOR ")
				.append(IDPADRE_COLUMN_NAME)
				.append("=")
				.append(ID_ELEMENTO_COLUMN_NAME_S)
				.append(" START WITH ")
				.append(DBUtils.generateEQTokenField(ID_ELEMENTO_FIELD,
						idElemento));
		return getElementoCF(qual.toString());
	}

	public List getVolumenesSeriesClasificadorSeries(String idClfSeries) {
		Map pairs = new HashMap();
		pairs.put(
				VolumenSerieDBEntityImpl.TABLE_NAME,
				new DbColumnDef[] { VolumenSerieDBEntityImpl.CAMPO_TIPODOCUMENTAL });
		pairs.put(
				null,
				new DbColumnDef[] { new DbColumnDef("cantidad", (String) null,
						"SUM("
								+ VolumenSerieDBEntityImpl.CAMPO_CANTIDAD
										.getQualifiedName() + ")",
						DbDataType.LONG_INTEGER, true) });

		String qual = new StringBuffer()
				.append("WHERE ")
				.append(VolumenSerieDBEntityImpl.CAMPO_IDSERIE
						.getQualifiedName())
				.append(" IN (SELECT ")
				.append(ID_ELEMENTO_FIELD.getQualifiedName())
				.append(" FROM ")
				.append(TABLE_NAME_ELEMENTO)
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(TIPO_ELEMENTO_FIELD,
						ElementoCuadroClasificacion.TIPO_SERIE))
				.append(" CONNECT BY PRIOR ")
				.append(DBUtils.generateJoinCondition(ID_ELEMENTO_FIELD,
						IDPADRE_FIELD))
				.append(" START WITH ")
				.append(DBUtils.generateEQTokenField(ID_ELEMENTO_FIELD,
						idClfSeries))
				.append(") GROUP BY ")
				.append(VolumenSerieDBEntityImpl.CAMPO_TIPODOCUMENTAL
						.getQualifiedName())
				.append(" ORDER BY ")
				.append(VolumenSerieDBEntityImpl.CAMPO_TIPODOCUMENTAL
						.getQualifiedName()).toString();

		return getVOS(qual, pairs, VolumenSerieVO.class);
	}

	private class Id {
		String id;

		public String getId() {
			return id;
		}

		// public void setId(String id){
		// this.id=id;
		// }
	}

	public List getIdsHijos(String idElementoCuadro) {
		return getIdsHijos(idElementoCuadro, -1);
	}

	public List getIdsHijos(String idElementoCuadro, int tipoElemento) {
		List ret = new ArrayList();
		StringBuffer qual = new StringBuffer().append("SELECT ")
				.append(ID_ELEMENTO_FIELD.getQualifiedName()).append(" FROM ")
				.append(TABLE_NAME_ELEMENTO);

		if (tipoElemento >= 0) {
			qual.append(" WHERE ");
			qual.append(DBUtils.generateEQTokenField(TIPO_ELEMENTO_FIELD,
					tipoElemento));
		}

		qual.append(" CONNECT BY PRIOR ")
				.append(DBUtils.generateJoinCondition(ID_ELEMENTO_FIELD,
						IDPADRE_FIELD))
				.append(" START WITH ")
				.append(DBUtils.generateEQTokenField(ID_ELEMENTO_FIELD,
						idElementoCuadro));

		List hijosDelElemento = getVOS(qual.toString(), TABLE_NAME_ELEMENTO,
				new DbColumnDef[] { ID_ELEMENTO_FIELD }, Id.class);
		if (!CollectionUtils.isEmpty(hijosDelElemento)) {
			for (Iterator itHijos = hijosDelElemento.iterator(); itHijos
					.hasNext();) {
				Id hijo = (Id) itHijos.next();
				ret.add(hijo.getId());
				// obtener nietos
				List idsHijos = getIdsHijos(hijo.getId(), tipoElemento);
				ret.addAll(idsHijos);
			}
		}
		return ret;
	}

}