package docelectronicos.db;

import ieci.core.db.DbConnection;
import common.db.DBUtils;
import common.db.DbDataSource;

import docelectronicos.TipoObjeto;
import docelectronicos.vos.DocClasificadorVO;

/**
 * DBEntity para acceder a la tabla ADOCCLASIFCF.
 */
public class DocClasifCFDBEntityImpl extends DocClasifCFDBEntityImplBase {

	/**
	 * @param dataSource
	 */
	public DocClasifCFDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public DocClasifCFDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	public DocClasificadorVO getClasificadorPadre(String id) {
		StringBuffer qual = new StringBuffer(" WHERE LEVEL=2 ")
				.append("CONNECT BY PRIOR ").append(ID_CLF_PADRE_COLUMN_NAME)
				.append("=").append(ID_COLUMN_NAME).append(" START WITH ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id));
		DocClasificadorVO clasificador = (DocClasificadorVO) getVO(
				qual.toString(), TABLE_NAME, COL_DEFS, DocClasificadorVO.class);
		if (clasificador != null)
			clasificador.setTipoObjeto(TipoObjeto.ELEMENTO_CF);

		return clasificador;
	}

}
