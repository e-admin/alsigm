package transferencias.db.postgreSQL;

import fondos.db.IElementoCuadroClasificacionDbEntity;
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

import common.db.DBEntityFactory;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.exceptions.TooManyResultsException;
import common.pagination.PageInfo;

public class RelacionEntregaDBEntityImpl extends
		RelacionEntregaDBEntityBaseImpl {

	public RelacionEntregaDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public RelacionEntregaDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	public List getRelacionesXClasificadorSeries(String idClfSeries,
			PageInfo pageInfo) throws TooManyResultsException {
		// WHERE asgtrentrega.idserie IN (
		// SELECT ASGFELEMENTOCF.id
		// FROM ASGFELEMENTOCF
		// WHERE ASGFELEMENTOCF.idnivel=(
		// SELECT asgfnivelcf.id FROM asgfnivelcf WHERE asgfnivelcf.tipo = 4 )
		// START WITH ASGFELEMENTOCF.id= '0813dbf28281d0000000000000000040'
		// CONNECT BY PRIOR ASGFELEMENTOCF.id=ASGFELEMENTOCF.idpadre)
		// AND asgtrentrega.estado = 7 AND
		// asgtrentrega.idserie=ASGFELEMENTOCF.id

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
		// TODO ZMIGRACION BD - CONNECT BY PRIOR (PROBADO
		IElementoCuadroClasificacionDbEntity elementoEntity = DBEntityFactory
				.getInstance(getDbFactoryClass())
				.getElementoCuadroClasificacionDBEntity(getDataSource());

		List idsHijos = elementoEntity.getIdsHijos(idClfSeries);
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
				.append(" AND ")
				.append(DBUtils.generateInTokenField(CAMPO_ID, idsHijos))
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
