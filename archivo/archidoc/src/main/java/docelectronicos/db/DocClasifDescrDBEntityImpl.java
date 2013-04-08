package docelectronicos.db;

import ieci.core.db.DbConnection;

import common.db.DBUtils;
import common.db.DbDataSource;

import docelectronicos.TipoObjeto;
import docelectronicos.vos.DocClasificadorVO;

/**
 * DBEntity para acceder a la tabla ADOCCLASIFDESCR.
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

	public DocClasificadorVO getClasificadorPadre(String id) {
		StringBuffer qual = new StringBuffer(" WHERE LEVEL=2 ")
				.append("CONNECT BY PRIOR ").append(ID_CLF_PADRE_COLUMN_NAME)
				.append("=").append(ID_COLUMN_NAME).append(" START WITH ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id));
		DocClasificadorVO clasificador = (DocClasificadorVO) getVO(
				qual.toString(), TABLE_NAME, COL_DEFS, DocClasificadorVO.class);
		if (clasificador != null)
			clasificador.setTipoObjeto(TipoObjeto.DESCRIPTOR);

		return clasificador;
	}

}
