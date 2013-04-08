/*
 * Created on 17-ene-2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package fondos.db.commonPostgreSQLServer;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import util.CollectionUtils;

import common.db.DBEntityFactory;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.util.CustomDateRange;

import fondos.db.ElementoCuadroClasificacionDBEntityImplBase;
import fondos.db.IElementoCuadroClasificacionDbEntity;
import fondos.db.SerieDBEntityImpl;
import fondos.db.VolumenSerieDBEntityImpl;
import fondos.model.ElementoCuadroClasificacion;
import fondos.model.IElementoCuadroClasificacion;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.VolumenSerieVO;

public abstract class ElementoCuadroClasificacionPostgreeSQLServerBaseDBEntityImpl
		extends ElementoCuadroClasificacionDBEntityImplBase {
	/**
	 * @param dataSource
	 */
	public ElementoCuadroClasificacionPostgreeSQLServerBaseDBEntityImpl(
			DbDataSource dataSource) {
		super(dataSource);
	}

	public ElementoCuadroClasificacionPostgreeSQLServerBaseDBEntityImpl(
			DbConnection conn) {
		super(conn);
	}

	// TODO ZMIGRACION BD (PROBADA
	public List getAncestors(String idElemento) {
		List ret = new LinkedList();
		ret = getAncestorsRecursiva(idElemento);
		if (!CollectionUtils.isEmpty(ret)) {
			Collections.reverse(ret);
		}
		return ret;
	}

	private List getAncestorsRecursiva(String idElemento) {
		List ret = new LinkedList();
		ElementoCuadroClasificacionVO elementoPadre = getElementoPadre(idElemento);
		if (elementoPadre != null) {
			ret.add(elementoPadre);
			if (elementoPadre.getIdPadre() != null) {
				List abuelos = getAncestorsRecursiva(elementoPadre.getId());
				if (abuelos != null)
					ret.addAll(abuelos);

			}
		}
		return ret;
	}

	// TODO ZMIGRACION BD ( PROBADO CON LA ACTUALIZACION DE LA DECRIPCION DEL
	// CLASFICADOR
	public CustomDateRange getFechasExtremasClasificadorSeries(
			String idClfSeries) {
		List idsHijos = getIdsHijos(idClfSeries,
				ElementoCuadroClasificacion.TIPO_SERIE);
		if (!CollectionUtils.isEmpty(idsHijos)) {
			String qual = new StringBuffer()
					.append("WHERE ")
					.append(DBUtils.generateInTokenField(
							SerieDBEntityImpl.CAMPO_ID, idsHijos)).toString();

			DbColumnDef[] cols = new DbColumnDef[] {
					new DbColumnDef("initialDate", (String) null,
							"MIN("
									+ SerieDBEntityImpl.CAMPO_FECHAINICIO
											.getQualifiedName() + ")",
							DbDataType.DATE_TIME, false),
					new DbColumnDef("finalDate", (String) null,
							"MAX("
									+ SerieDBEntityImpl.CAMPO_FECHAFIN
											.getQualifiedName() + ")",
							DbDataType.DATE_TIME, false) };

			return (CustomDateRange) getVO(qual,
					SerieDBEntityImpl.TABLE_NAME_SERIE, cols,
					CustomDateRange.class);
		}
		return null;
		// return
		// getFechasExtremasClasificadorSeriesRecursiva(getElementoCuadroClasificacion(idClfSeries));

	}

	public class Id {
		String id;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
	}

	// TODO ZMIGRACION BD (PROBADA
	public ElementoCuadroClasificacionVO getElementoPadre(String idElemento) {
		// obtener el elemento
		IElementoCuadroClasificacion elementoCuadro = getElementoCuadroClasificacion(idElemento);
		IElementoCuadroClasificacion elementoPadre = null;
		if (elementoCuadro != null)
			elementoPadre = getElementoCuadroClasificacion(elementoCuadro
					.getIdPadre());
		return elementoPadre;
	}

	// TODO ZPROBADO CON LA ACTUALIZACION DE LA DESCRIPCION DE UN CL
	public List getVolumenesSeriesClasificadorSeries(String idClfSeries) {
		IElementoCuadroClasificacion elementoCuadro = getElementoCuadroClasificacion(idClfSeries);
		List ret = new LinkedList();
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

		// obtener serie hijas del clasificador de serie
		IElementoCuadroClasificacionDbEntity elementoDBEntity = DBEntityFactory
				.getInstance(getDbFactoryClass())
				.getElementoCuadroClasificacionDBEntity(getDataSource());
		List idsHijos = elementoDBEntity.getIdsHijos(elementoCuadro.getId(),
				ElementoCuadroClasificacion.TIPO_SERIE);
		if (!CollectionUtils.isEmpty(idsHijos)) {
			// obtener las listas de volumnes del elemento
			String qual = new StringBuffer()
					.append(" WHERE ")
					.append(" ")
					.append(DBUtils.generateInTokenField(
							VolumenSerieDBEntityImpl.CAMPO_IDSERIE, idsHijos))
					.append(" GROUP BY ")
					.append(VolumenSerieDBEntityImpl.CAMPO_TIPODOCUMENTAL
							.getQualifiedName())
					.append(" ORDER BY ")
					.append(VolumenSerieDBEntityImpl.CAMPO_TIPODOCUMENTAL
							.getQualifiedName()).toString();

			ret = getVOS(qual, pairs, VolumenSerieVO.class);
		}
		return ret;

	}

}
