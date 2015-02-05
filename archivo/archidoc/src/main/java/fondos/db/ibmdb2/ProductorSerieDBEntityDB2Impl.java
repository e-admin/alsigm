package fondos.db.ibmdb2;

import ieci.core.db.DbConnection;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.mutable.MutableObject;

import common.db.DBEntityFactory;
import common.db.DbDataSource;

import fondos.db.IElementoCuadroClasificacionDbEntity;
import fondos.db.ProductorSerieDBEntityImplBase;
import fondos.model.ElementoCuadroClasificacion;

public class ProductorSerieDBEntityDB2Impl extends
		ProductorSerieDBEntityImplBase {

	/**
	 * @param dataSource
	 */
	public ProductorSerieDBEntityDB2Impl(DbDataSource dataSource) {
		super(dataSource);
	}

	public ProductorSerieDBEntityDB2Impl(DbConnection conn) {
		super(conn);
	}

	public List getProductoresXIdClasificadorSeries(String idClfSeries) {
		IElementoCuadroClasificacionDbEntity elementoEntity = DBEntityFactory
				.getInstance(getDbFactoryClass())
				.getElementoCuadroClasificacionDBEntity(getDataSource());
		List idsHijos = elementoEntity.getIdsHijos(idClfSeries,
				ElementoCuadroClasificacion.TIPO_SERIE);
		StringBuffer inQuery = new StringBuffer();
		for (Iterator itIdsHijos = idsHijos.iterator(); itIdsHijos.hasNext();) {
			MutableObject mutableObject = (MutableObject) itIdsHijos.next();
			String id = (String) mutableObject.getValue();
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
