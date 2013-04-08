package docelectronicos.db.postgreSQL;

import ieci.core.db.DbConnection;

import common.db.DbDataSource;

import docelectronicos.TipoObjeto;
import docelectronicos.db.DocClasifDescrDBEntityImplBase;
import docelectronicos.vos.DocClasificadorVO;

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

		// TODO ZMIGRACION BD - BY PRIOR - PROBAR2 - Los puntos de llamada están
		// comentados ¿En uso?

		StringBuffer sqlstr = new StringBuffer();
		sqlstr.append(" WHERE id IN (SELECT ID FROM").append(" ");
		sqlstr.append(" connectby('").append(TABLE_NAME).append("','")
				.append(ID_CLF_PADRE_COLUMN_NAME).append("','")
				.append(ID_COLUMN_NAME).append("','").append(id).append("',0)")
				.append(" ");
		sqlstr.append(" t(value text, idpadre text, level int) WHERE  level=2 )");

		DocClasificadorVO clasificador = (DocClasificadorVO) getVO(
				sqlstr.toString(), TABLE_NAME, COL_DEFS,
				DocClasificadorVO.class);
		if (clasificador != null)
			clasificador.setTipoObjeto(TipoObjeto.DESCRIPTOR);
		return clasificador;

	}

}
