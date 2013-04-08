package docelectronicos.db.sqlserver2000;

import ieci.core.db.DbConnection;
import common.db.DbDataSource;

import docelectronicos.db.DocClasifDescrDBEntityImplBase;
import docelectronicos.vos.DocClasificadorVO;

/**
 * 
 * TODO ZHACER SQLServer2000
 */
public class DocClasifDescrDBEntityImpl extends DocClasifDescrDBEntityImplBase {

	/**
	 * @param dataSource
	 */
	public DocClasifDescrDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public DocClasifDescrDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * docelectronicos.db.DocClasifDescrDBEntityImplBase#getClasificadorPadre
	 * (java.lang.String)
	 */
	public DocClasificadorVO getClasificadorPadre(String id) {
		// StringBuffer qual = new StringBuffer(" WHERE LEVEL=2 ")
		// .append("CONNECT BY PRIOR ")
		// .append(ID_CLF_PADRE_COLUMN_NAME).append("=").append(ID_COLUMN_NAME)
		// .append(" START WITH ").append(DBUtils.generateEQTokenField(CAMPO_ID,
		// id));
		// DocClasificadorVO clasificador =
		// (DocClasificadorVO) getVO(qual.toString(), TABLE_NAME, COL_DEFS,
		// DocClasificadorVO.class);
		// if (clasificador != null)
		// clasificador.setTipoObjeto(TipoObjeto.DESCRIPTOR);
		//
		// return clasificador;

		return null;
	}

}
