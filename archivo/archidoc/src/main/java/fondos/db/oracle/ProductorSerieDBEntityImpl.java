package fondos.db.oracle;

import ieci.core.db.DbConnection;

import java.util.List;

import org.apache.log4j.Logger;

import common.db.DBUtils;
import common.db.DbDataSource;

import fondos.db.ProductorSerieDBEntityImplBase;
import fondos.model.ElementoCuadroClasificacion;

/**
 * Implementación de los métodos de acceso a datos almacenados en la tabla en la
 * que se mantienen los productores de las series del cuadro de clasificación de
 * fondos documentales
 */
public class ProductorSerieDBEntityImpl extends ProductorSerieDBEntityImplBase {

	/** Logger de la clase */
	static Logger logger = Logger.getLogger(ProductorSerieDBEntityImpl.class);

	/**
	 * @param dataSource
	 */
	public ProductorSerieDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public ProductorSerieDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fondos.db.ProductorSerieDBEntityImplBase#getProductoresXIdClasificadorSeries
	 * (java.lang.String)
	 */
	public List getProductoresXIdClasificadorSeries(String idClfSeries) {
		String qual = new StringBuffer()
				.append("WHERE ")
				.append(CAMPO_IDSERIE.getQualifiedName())
				.append(" IN (SELECT ")
				.append(ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD
						.getQualifiedName())
				.append(" FROM ")
				.append(ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO)
				.append(" WHERE ")
				.append(DBUtils
						.generateEQTokenField(
								ElementoCuadroClasificacionDBEntityImpl.TIPO_ELEMENTO_FIELD,
								ElementoCuadroClasificacion.TIPO_SERIE))
				.append(" CONNECT BY PRIOR ")
				.append(DBUtils
						.generateJoinCondition(
								ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD,
								ElementoCuadroClasificacionDBEntityImpl.IDPADRE_FIELD))
				.append(" START WITH ")
				.append(DBUtils
						.generateEQTokenField(
								ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD,
								idClfSeries)).append(")").toString();

		return getProductoresSerieVO(qual);
	}

}