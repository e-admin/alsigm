package transferencias.db.oracle;

import fondos.db.NivelCFDBEntityImpl;
import fondos.db.oracle.ElementoCuadroClasificacionDBEntityImpl;
import fondos.model.ElementoCuadroClasificacion;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import transferencias.db.RelacionEntregaDBEntityBaseImpl;
import transferencias.model.EstadoREntrega;
import transferencias.vos.RelacionEntregaVO;

import common.db.DBUtils;
import common.db.DbDataSource;
import common.exceptions.TooManyResultsException;
import common.pagination.PageInfo;

/**
 * Clase con los metodos encargados de recuperar y almacenar datos referentes a
 * relaciones de entrega
 */
public class RelacionEntregaDBEntityImpl extends
		RelacionEntregaDBEntityBaseImpl {

	public RelacionEntregaDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public RelacionEntregaDBEntityImpl(DbConnection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public List getRelacionesXClasificadorSeries(String idClfSeries,
			PageInfo pageInfo) throws TooManyResultsException {

		Map pairs = new HashMap();
		pairs.put(TABLE_NAME, COLUMN_DEFINITIONS);
		pairs.put(
				ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO,
				new DbColumnDef[] {
						new DbColumnDef(
								"codigoSerie",
								ElementoCuadroClasificacionDBEntityImpl.CODIGO_FIELD),
						new DbColumnDef(
								"tituloSerie",
								ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD) });

		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(CAMPO_IDSERIEDESTINO.getQualifiedName())
				.append(" IN (SELECT ")
				.append(ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD
						.getQualifiedName())
				.append(" FROM ")
				.append(ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO)
				.append(" WHERE ")
				.append(ElementoCuadroClasificacionDBEntityImpl.NIVEL_FIELD
						.getQualifiedName())
				.append("=(SELECT ")
				.append(NivelCFDBEntityImpl.ID_NIVEL_FIELD.getQualifiedName())
				.append(" FROM ")
				.append(NivelCFDBEntityImpl.NIVELCF_TABLE_NAME)
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(
						NivelCFDBEntityImpl.TIPO_NIVEL_FIELD,
						ElementoCuadroClasificacion.TIPO_SERIE))
				.append(")")
				.append(" START WITH ")
				.append(DBUtils
						.generateEQTokenField(
								ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD,
								idClfSeries))
				.append(" CONNECT BY PRIOR ")
				.append(ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD
						.getQualifiedName())
				.append("=")
				.append(ElementoCuadroClasificacionDBEntityImpl.IDPADRE_FIELD
						.getQualifiedName())
				.append(") AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ESTADO,
						EstadoREntrega.VALIDADA.getIdentificador()))
				.append(" AND ")
				.append(DBUtils
						.generateJoinCondition(
								CAMPO_IDSERIEDESTINO,
								ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD));

		if (pageInfo != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("codigo", new DbColumnDef[] { CAMPO_ANO,
					CAMPO_ORDEN });
			criteriosOrdenacion.put("tipoTransferencia",
					CAMPO_TIPOTRANSFERENCIA);
			criteriosOrdenacion.put("estado", CAMPO_ESTADO);
			criteriosOrdenacion.put("fechaestado", CAMPO_FECHAESTADO);
			criteriosOrdenacion.put("procedimiento", CAMPO_IDPROC);
			criteriosOrdenacion.put("serie",
					ElementoCuadroClasificacionDBEntityImpl.CODIGO_FIELD);

			return getVOS(qual.toString(),
					pageInfo.getOrderBy(criteriosOrdenacion), pairs,
					RelacionEntregaVO.class, pageInfo);
		} else {
			qual.append(" ORDER BY ").append(CAMPO_ANO.getQualifiedName())
					.append(" DESC,").append(CAMPO_ORDEN.getQualifiedName())
					.append(" DESC");

			return getVOS(qual.toString(), pairs, RelacionEntregaVO.class);
		}
	}

}