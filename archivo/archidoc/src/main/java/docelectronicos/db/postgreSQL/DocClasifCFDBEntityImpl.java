package docelectronicos.db.postgreSQL;

import ieci.core.db.DbConnection;
import common.db.DbDataSource;

import docelectronicos.TipoObjeto;
import docelectronicos.db.DocClasifCFDBEntityImplBase;
import docelectronicos.vos.DocClasificadorVO;

public class DocClasifCFDBEntityImpl extends DocClasifCFDBEntityImplBase {

	public DocClasifCFDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public DocClasifCFDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	public DocClasificadorVO getClasificadorPadre(String id) {
		// TODO ZMIGRACION BD - BY PRIOR - PROBAR2 - Los puntos de llamada están
		// comentados. ¿En uso?
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
			clasificador.setTipoObjeto(TipoObjeto.ELEMENTO_CF);
		return clasificador;

	}

}
