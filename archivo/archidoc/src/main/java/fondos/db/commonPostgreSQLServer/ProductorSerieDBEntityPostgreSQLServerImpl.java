package fondos.db.commonPostgreSQLServer;

import ieci.core.db.DbConnection;

import java.util.Iterator;
import java.util.List;

import common.db.DBEntityFactory;
import common.db.DbDataSource;

import fondos.db.IElementoCuadroClasificacionDbEntity;
import fondos.db.ProductorSerieDBEntityImplBase;
import fondos.model.ElementoCuadroClasificacion;

public class ProductorSerieDBEntityPostgreSQLServerImpl extends
		ProductorSerieDBEntityImplBase {

	/**
	 * @param dataSource
	 */
	public ProductorSerieDBEntityPostgreSQLServerImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public ProductorSerieDBEntityPostgreSQLServerImpl(DbConnection conn) {
		super(conn);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fondos.db.ProductorSerieDBEntityImplBase#getProductoresXIdClasificadorSeries
	 * (java.lang.String)
	 */
	// TODO ZMIGRACION BD - BY PRIOR ( PROBADO CON LA ACTUALIZACION DE LA
	// DECRIPCION DEL CL DE SERIE
	public List getProductoresXIdClasificadorSeries(String idClfSeries) {
		IElementoCuadroClasificacionDbEntity elementoEntity = DBEntityFactory
				.getInstance(getDbFactoryClass())
				.getElementoCuadroClasificacionDBEntity(getDataSource());
		List idsHijos = elementoEntity.getIdsHijos(idClfSeries,
				ElementoCuadroClasificacion.TIPO_SERIE);
		StringBuffer inQuery = new StringBuffer();
		for (Iterator itIdsHijos = idsHijos.iterator(); itIdsHijos.hasNext();) {
			String id = (String) itIdsHijos.next();
			if (inQuery.length() > 0)
				inQuery.append(",");
			inQuery.append("'").append(id).append("'");
		}

		if (inQuery.length() > 0) {
			String qual = new StringBuffer().append("WHERE ")
					.append(CAMPO_IDSERIE.getQualifiedName()).append(" IN ( ")
					.append(inQuery.toString()).append(" )").toString();

			return getProductoresSerieVO(qual);
		}
		return null;
	}

}
