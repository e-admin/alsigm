package fondos.db;

import ieci.core.db.AutoDbColumnDef;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbSelectStatement;
import ieci.core.db.DbTableDef;
import ieci.core.db.DbUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbOutputPaginatedRecordset;
import common.exceptions.TooManyResultsException;
import common.pagination.PageInfo;
import common.util.ArrayUtils;
import common.util.CustomDateFormat;
import common.util.StringUtils;

import fondos.model.SolicitudSerie;
import fondos.vos.BusquedaSolicitudesSerieVO;
import fondos.vos.SolicitudSerieExtended;
import gcontrol.db.UsuarioDBEntityImpl;

/**
 * Clase para la prueba de autoDbEntity
 */
public class SolicitudSerieBusquedasDbEntityImpl extends DBEntity implements
		ISolicitudSerieBusquedasDbEntity {

	private static final String TABLE_NAME = "ASGFSOLICITUDSERIE";

	// definicion de tablas y alias
	public static final DbTableDef TABLE_SOLICITUD = new DbTableDef(TABLE_NAME,
			null);

	public static final String aliasGestoresTableUsuarios = "gestores";
	public static final String aliasSolicitantesTableUsuarios = "solicitantes";
	public static final DbTableDef TABLE_GESTORES = new DbTableDef(
			UsuarioDBEntityImpl.TABLE_NAME, aliasGestoresTableUsuarios);
	public static final DbTableDef TABLE_SOLICITANTES = new DbTableDef(
			UsuarioDBEntityImpl.TABLE_NAME, aliasSolicitantesTableUsuarios);

	protected static final String ID_COLUMN_NAME = "ID";
	protected static final String ID_SERIE_COLUMN_NAME = "IDSERIE";
	private static final String ETIQUETA_SERIE_COLUMN_NAME = "ETIQUETASERIE";
	private static final String TIPO_COLUMN_NAME = "TIPO";
	protected static final String FECHA_COLUMN_NAME = "FECHA";
	protected static final String INFO_COLUMN_NAME = "INFO";
	protected static final String MOTIVO_SOLICITUD_COLUMN_NAME = "MOTIVOSOLICITUD";
	private static final String USR_SOLICITANTE_COLUMN_NAME = "IDUSRSOLICITANTE";
	private static final String ESTADO_COLUMN_NAME = "ESTADO";
	private static final String FECHA_ESTADO_COLUMN_NAME = "FECHAESTADO";
	private static final String USR_GESTOR_COLUMN_NAME = "IDUSRGESTOR";
	protected static final String MOTIVO_RECHAZO_COLUMN_NAME = "MOTIVORECHAZO";

	public static final DbColumnDef CAMPO_ETIQUETA_SERIE = getInstanceColumnSolicitudSerieTable(
			ETIQUETA_SERIE_COLUMN_NAME, DbDataType.SHORT_TEXT);
	public static final DbColumnDef CAMPO_USR_SOLICITANTE = getInstanceColumnSolicitudSerieTable(
			USR_SOLICITANTE_COLUMN_NAME, DbDataType.SHORT_TEXT);
	public static final DbColumnDef CAMPO_ESTADO = getInstanceColumnSolicitudSerieTable(
			ESTADO_COLUMN_NAME, DbDataType.SHORT_INTEGER);
	public static final DbColumnDef CAMPO_FECHA_ESTADO = getInstanceColumnSolicitudSerieTable(
			FECHA_ESTADO_COLUMN_NAME, DbDataType.DATE_TIME);
	public static final DbColumnDef CAMPO_USR_GESTOR = getInstanceColumnSolicitudSerieTable(
			USR_GESTOR_COLUMN_NAME, DbDataType.SHORT_TEXT);
	public static final DbColumnDef CAMPO_TIPO = getInstanceColumnSolicitudSerieTable(
			TIPO_COLUMN_NAME, DbDataType.SHORT_INTEGER);
	public static final DbColumnDef CAMPO_ID = getInstanceColumnSolicitudSerieTable(
			ID_COLUMN_NAME, DbDataType.SHORT_TEXT);
	public static final DbColumnDef CAMPO_IDSERIE = getInstanceColumnSolicitudSerieTable(
			ID_SERIE_COLUMN_NAME, DbDataType.SHORT_TEXT);
	public static final DbColumnDef CAMPO_FECHA = getInstanceColumnSolicitudSerieTable(
			FECHA_COLUMN_NAME, DbDataType.DATE_TIME);
	public static final DbColumnDef CAMPO_INFO = getInstanceColumnSolicitudSerieTable(
			INFO_COLUMN_NAME, DbDataType.SHORT_TEXT);
	public static final DbColumnDef CAMPO_MOTIVO_SOLICITUD = getInstanceColumnSolicitudSerieTable(
			MOTIVO_SOLICITUD_COLUMN_NAME, DbDataType.SHORT_TEXT);
	public static final DbColumnDef CAMPO_MOTIVO_RECHAZO = getInstanceColumnSolicitudSerieTable(
			MOTIVO_RECHAZO_COLUMN_NAME, DbDataType.SHORT_TEXT);

	private static DbColumnDef getInstanceColumnSolicitudSerieTable(
			String columnName, int tipo) {
		return new AutoDbColumnDef(null, TABLE_SOLICITUD, columnName, tipo);
	}

	public static final DbColumnDef CAMPO_NOMBRE_USRSOLICITANTE = getInstanceColumnSolicitantesTable(
			"nombreUsuarioSolicitante",
			UsuarioDBEntityImpl.CAMPO_NOMBRE.getName(), DbDataType.SHORT_TEXT);

	public static final DbColumnDef CAMPO_APELLIDOS_USRSOLICITANTE = getInstanceColumnSolicitantesTable(
			"apellidosUsuarioSolicitante",
			UsuarioDBEntityImpl.CAMPO_APELLIDOS.getName(),
			DbDataType.SHORT_TEXT);

	public static final DbColumnDef CAMPO_NOMBRE_USRGESTOR = getInstanceColumnGestoresTable(
			"nombreUsuarioGestor", UsuarioDBEntityImpl.CAMPO_NOMBRE.getName());

	public static final DbColumnDef CAMPO_APELLIDOS_USRGESTOR = getInstanceColumnGestoresTable(
			"apellidosUsuarioGestor",
			UsuarioDBEntityImpl.CAMPO_APELLIDOS.getName());

	private static DbColumnDef getInstanceColumnGestoresTable(String alias,
			String columnName) {
		return new AutoDbColumnDef(alias, TABLE_GESTORES, columnName);
	}

	private static DbColumnDef getInstanceColumnSolicitantesTable(String alias,
			String columnName, int tipo) {
		return new AutoDbColumnDef(alias, TABLE_SOLICITANTES, columnName, tipo);
	}

	private static DbColumnDef[] TABLE_SOLICITUD_COLUMNS_DEF = new DbColumnDef[] {
			CAMPO_ID, CAMPO_IDSERIE, CAMPO_ETIQUETA_SERIE, CAMPO_TIPO,
			CAMPO_FECHA, CAMPO_INFO, CAMPO_MOTIVO_SOLICITUD,
			CAMPO_USR_SOLICITANTE, CAMPO_ESTADO, CAMPO_FECHA_ESTADO,
			CAMPO_USR_GESTOR, CAMPO_MOTIVO_RECHAZO };

	private static DbColumnDef[] ALL_COLUMNS_DEF = (DbColumnDef[]) ArrayUtils
			.concat(TABLE_SOLICITUD_COLUMNS_DEF, new DbColumnDef[] {
					CAMPO_NOMBRE_USRSOLICITANTE,
					CAMPO_APELLIDOS_USRSOLICITANTE, CAMPO_NOMBRE_USRGESTOR,
					CAMPO_APELLIDOS_USRGESTOR });

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * @param dataSource
	 */
	public SolicitudSerieBusquedasDbEntityImpl(DbDataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub
	}

	public SolicitudSerieBusquedasDbEntityImpl(DbConnection conn) {
		super(conn);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.db.AutoDbEntity#getColumnsDefs()
	 */
	public DbColumnDef[] getColumnsDefs() {
		return ALL_COLUMNS_DEF;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.db.AutoDbEntity#getTableNames()
	 */
	public DbTableDef[] getTablesDefs() {
		return new DbTableDef[] { TABLE_SOLICITUD, TABLE_GESTORES,
				TABLE_SOLICITANTES };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fondos.db.ISolicitudSerieDbEntity#findSolicitudes(fondos.vos.
	 * BusquedaSolicitudesSerieVO)
	 */
	// public Collection findSolicitudes(BusquedaSolicitudesSerieVO
	// busquedaSolicitudes) throws TooManyResultsException {
	//
	// //relaciones con tablas usuarios
	// StringBuffer qual = new StringBuffer().append(" WHERE ")
	// .append(generateJoinCondition(TABLE_SOLICITUD, CAMPO_USR_SOLICITANTE,
	// TABLE_SOLICITANTES, UsuarioDBEntityImpl.CAMPO_ID))
	// .append(" AND ");
	// //si contiene los estados al 1 ( estado=1 Or
	// TABLE_SOLICITUD.CAMPO_USR_GESTOR=TABLE_GESTORES.CAMPO_ID )
	// if(!ArrayUtils.isEmpty(busquedaSolicitudes.getEstados())){
	// boolean conEstadoSolicitada=false;
	// for(int i=0;i<busquedaSolicitudes.getEstados().length;i++){
	// if(busquedaSolicitudes.getEstados()[i].equals(""+SolicitudSerie.ESTADO_SOLICITADA)){
	// conEstadoSolicitada=true;
	// }
	// }
	// if(conEstadoSolicitada){
	// qual.append("(")
	// .append(DBUtils.generateEQTokenField(CAMPO_ESTADO,SolicitudSerie.ESTADO_SOLICITADA))
	// .append(" OR ");
	// }
	// qual.append(
	// generateJoinCondition(TABLE_SOLICITUD, CAMPO_USR_GESTOR,
	// TABLE_GESTORES, UsuarioDBEntityImpl.CAMPO_ID));
	// if(conEstadoSolicitada){
	// qual.append(")");
	// }
	// }
	//
	// // etiqueta
	// if (StringUtils.isNotBlank(busquedaSolicitudes.getEtiqueta()))
	// {
	// qual.append(DBUtils.getCondition(qual.toString()))
	// .append(DBUtils.generateLikeTokenField(
	// CAMPO_ETIQUETA_SERIE,
	// busquedaSolicitudes.getEtiqueta().toUpperCase()));
	// }
	//
	// // Estados
	// if (!ArrayUtils.isEmpty(busquedaSolicitudes.getEstados()))
	// {
	// qual.append(DBUtils.getCondition(qual.toString()))
	// .append(DBUtils.generateInTokenField(
	// CAMPO_ESTADO, busquedaSolicitudes.getEstados()));
	// }
	//
	// // Fecha estado
	// if (busquedaSolicitudes.getFechaInicioEstado() != null)
	// {
	// // qual.append(DBUtils.getCondition(qual.toString()))
	// // .append(CAMPO_FECHA_ESTADO.getQualifiedName())
	// // .append(">=TO_DATE('")
	// //
	// .append(CustomDateFormat.SDF_YYYYMMDD.format(busquedaSolicitudes.getFechaInicioEstado()))
	// // .append("','")
	// // .append(CustomDateFormat.SDF_YYYYMMDD.toPattern())
	// // .append("')");
	// qual.append(DBUtils.getCondition(qual.toString()))
	// .append(CAMPO_FECHA_ESTADO.getQualifiedName())
	// .append(">=")
	// .append(DBUtils.getNativeToDateSyntax(getConnection(),
	// busquedaSolicitudes.getFechaInicioEstado(),
	// CustomDateFormat.SDF_YYYYMMDD));
	//
	// }
	// if (busquedaSolicitudes.getFechaFinEstado() != null)
	// {
	// // qual.append(DBUtils.getCondition(qual.toString()))
	// // .append(CAMPO_FECHA_ESTADO.getQualifiedName())
	// // .append("<=TO_DATE('")
	// //
	// .append(CustomDateFormat.SDF_YYYYMMDD.format(busquedaSolicitudes.getFechaFinEstado()))
	// // .append("','")
	// // .append(CustomDateFormat.SDF_YYYYMMDD.toPattern())
	// // .append("')");
	// qual.append(DBUtils.getCondition(qual.toString()))
	// .append(CAMPO_FECHA_ESTADO.getQualifiedName())
	// .append("<=")
	// .append(DBUtils.getNativeToDateSyntax(getConnection(),
	// busquedaSolicitudes.getFechaFinEstado(),
	// CustomDateFormat.SDF_YYYYMMDD));
	// }
	//
	// String concatSymbol = DBUtils.getNativeConcatSyntax(getConnection());
	// // Nombre Usuario gestor
	// if (StringUtils.isNotBlank(busquedaSolicitudes.getNombreUsuarioGestor()))
	// qual.append(" AND UPPER(")
	// .append(DBUtils.getQualifiedColumnName(aliasGestoresTableUsuarios,
	// UsuarioDBEntityImpl.CAMPO_NOMBRE))
	// .append(concatSymbol).append("' '").append(concatSymbol)
	// .append(DBUtils.getNativeIfNullSintax(getConnection(),
	// DBUtils.getQualifiedColumnName(aliasGestoresTableUsuarios,UsuarioDBEntityImpl.CAMPO_APELLIDOS),"''"))
	// .append(") LIKE '%")
	// .append(busquedaSolicitudes.getNombreUsuarioGestor().toUpperCase())
	// .append("%'");
	//
	// //id usuario gestor
	// if (StringUtils.isNotBlank(busquedaSolicitudes.getIdUsuarioGestor()))
	// qual.append(" AND ")
	// .append(
	// DBUtils.generateEQTokenField(UsuarioDBEntityImpl.CAMPO_ID,
	// busquedaSolicitudes
	// .getIdUsuarioGestor()));
	//
	// // Nombre Usuario solicitante
	// if
	// (StringUtils.isNotBlank(busquedaSolicitudes.getNombreUsuarioSolicitante()))
	// qual.append(" AND UPPER(")
	// .append(DBUtils.getQualifiedColumnName(aliasSolicitantesTableUsuarios,
	// UsuarioDBEntityImpl.CAMPO_NOMBRE))
	// .append(concatSymbol).append("' '").append(concatSymbol)
	// .append(DBUtils.getNativeIfNullSintax(getConnection(),
	// DBUtils.getQualifiedColumnName(aliasSolicitantesTableUsuarios,
	// UsuarioDBEntityImpl.CAMPO_APELLIDOS), "''"))
	// .append(") LIKE '%")
	// .append(busquedaSolicitudes.getNombreUsuarioSolicitante().toUpperCase())
	// .append("%'");
	//
	// //id usuario solicitante
	// if
	// (StringUtils.isNotBlank(busquedaSolicitudes.getIdUsuarioSolicitante()))
	// qual.append(" AND ")
	// .append(
	// DBUtils.generateEQTokenField(UsuarioDBEntityImpl.CAMPO_ID,
	// busquedaSolicitudes
	// .getIdUsuarioSolicitante()));
	//
	// // Ordenación
	// if (busquedaSolicitudes.getPageInfo() != null)
	// {
	// Map criteriosOrdenacion = new HashMap();
	// criteriosOrdenacion.put("etiqueta", CAMPO_ETIQUETA_SERIE);
	// criteriosOrdenacion.put("fechaEstado", CAMPO_FECHA_ESTADO);
	// criteriosOrdenacion.put("estado", CAMPO_ESTADO);
	// criteriosOrdenacion.put("tipo", CAMPO_TIPO);
	// criteriosOrdenacion.put("apellidosUsuarioSolicitante",
	// CAMPO_APELLIDOS_USRSOLICITANTE);
	// criteriosOrdenacion.put("apellidosUsuarioGestor",
	// CAMPO_APELLIDOS_USRGESTOR);
	//
	// //DbTableDef[] tablas=null;
	// //if(){TABLE_SOLICITUD, TABLE_GESTORES, TABLE_SOLICITANTES};
	//
	// return getVOS(qual.toString(),
	// busquedaSolicitudes.getPageInfo().getOrderBy(criteriosOrdenacion),
	// getTablesDefs(),
	// getColumnsDefs(),
	// SolicitudSerieExtended.class,
	// busquedaSolicitudes.getPageInfo());
	//
	// }
	// else
	// {
	// StringBuffer sbQual = new StringBuffer(qual.toString());
	// sbQual.append(" ORDER BY ")
	// .append(CAMPO_FECHA_ESTADO.getQualifiedName());
	//
	// return getVOS(qual.toString(),"order",
	// getTablesDefs(),
	// getColumnsDefs(),
	// SolicitudSerie.class,
	// busquedaSolicitudes.getPageInfo());
	//
	//
	// }
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see fondos.db.ISolicitudSerieDbEntity#findSolicitudes(fondos.vos.
	 * BusquedaSolicitudesSerieVO)
	 */
	public Collection findSolicitudes(
			BusquedaSolicitudesSerieVO busquedaSolicitudes)
			throws TooManyResultsException {

		// relaciones con tablas usuarios
		StringBuffer qual = new StringBuffer().append(" WHERE ").append(
				DBUtils.generateJoinCondition(TABLE_SOLICITUD.getName(),
						CAMPO_USR_SOLICITANTE, TABLE_SOLICITANTES.getAlias(),
						UsuarioDBEntityImpl.CAMPO_ID));

		// si contiene los estados al 1

		// Estados
		StringBuffer qualSolicitada = new StringBuffer();
		StringBuffer qualComun = new StringBuffer();

		boolean conEstadoSolicitada = false;
		if (!ArrayUtils.isEmpty(busquedaSolicitudes.getEstados())) {
			for (int i = 0; i < busquedaSolicitudes.getEstados().length; i++) {
				if (busquedaSolicitudes.getEstados()[i].equals(""
						+ SolicitudSerie.ESTADO_SOLICITADA)) {
					conEstadoSolicitada = true;
					break;
				}
			}
			if (conEstadoSolicitada) {
				qualSolicitada = new StringBuffer(qual.toString());
				qualSolicitada.append(" AND ").append(
						DBUtils.generateEQTokenField(CAMPO_ESTADO,
								SolicitudSerie.ESTADO_SOLICITADA));
			}

			qual.append(" AND ")
					.append(DBUtils.generateInTokenField(CAMPO_ESTADO,
							busquedaSolicitudes.getEstados()))
					.append(" AND ")
					.append(DBUtils.generateJoinCondition(
							TABLE_SOLICITUD.getName(), CAMPO_USR_GESTOR,
							TABLE_GESTORES.getAlias(),
							UsuarioDBEntityImpl.CAMPO_ID));
		}

		// el resto es comun para las dos consultas de la union

		// etiqueta
		if (StringUtils.isNotBlank(busquedaSolicitudes.getEtiqueta())) {
			qualComun.append(DBUtils.getAnd(qualComun.toString())).append(
					DBUtils.generateLikeTokenField(CAMPO_ETIQUETA_SERIE,
							busquedaSolicitudes.getEtiqueta().toUpperCase()));
		}

		// Fecha estado
		if (busquedaSolicitudes.getFechaInicioEstado() != null) {
			qualComun
					.append(DBUtils.getAnd(qualComun.toString()))
					.append(CAMPO_FECHA_ESTADO.getQualifiedName())
					.append(">=")
					.append(DBUtils.getNativeToDateSyntax(getConnection(),
							busquedaSolicitudes.getFechaInicioEstado(),
							CustomDateFormat.SDF_YYYYMMDD));
		}
		if (busquedaSolicitudes.getFechaFinEstado() != null) {
			qualComun
					.append(DBUtils.getAnd(qualComun.toString()))
					.append(CAMPO_FECHA_ESTADO.getQualifiedName())
					.append("<=")
					.append(DBUtils.getNativeToDateSyntax(getConnection(),
							busquedaSolicitudes.getFechaFinEstado(),
							CustomDateFormat.SDF_YYYYMMDD));
		}

		String concatSymbol = DBUtils.getNativeConcatSyntax(getConnection());

		// Nombre Usuario gestor
		if (StringUtils
				.isNotBlank(busquedaSolicitudes.getNombreUsuarioGestor())) {
			qualSolicitada = null;
			qual.append(" AND UPPER(")
					.append(DBUtils.getQualifiedColumnName(
							aliasGestoresTableUsuarios,
							UsuarioDBEntityImpl.CAMPO_NOMBRE))
					.append(concatSymbol)
					.append("' '")
					.append(concatSymbol)
					.append(DBUtils.getNativeIfNullSintax(getConnection(),
							DBUtils.getQualifiedColumnName(
									aliasGestoresTableUsuarios,
									UsuarioDBEntityImpl.CAMPO_APELLIDOS), "''"))
					.append(") LIKE '%")
					.append(busquedaSolicitudes.getNombreUsuarioGestor()
							.toUpperCase()).append("%'");
		}

		// Nombre Usuario solicitante
		if (StringUtils.isNotBlank(busquedaSolicitudes
				.getNombreUsuarioSolicitante()))
			qualComun
					.append(DBUtils.getAnd(qualComun.toString()))
					.append(" UPPER(")
					.append(DBUtils.getQualifiedColumnName(
							aliasSolicitantesTableUsuarios,
							UsuarioDBEntityImpl.CAMPO_NOMBRE))
					.append(concatSymbol)
					.append("' '")
					.append(concatSymbol)
					.append(DBUtils.getNativeIfNullSintax(getConnection(),
							DBUtils.getQualifiedColumnName(
									aliasSolicitantesTableUsuarios,
									UsuarioDBEntityImpl.CAMPO_APELLIDOS), "''"))
					.append(") LIKE '%")
					.append(busquedaSolicitudes.getNombreUsuarioSolicitante()
							.toUpperCase()).append("%'");

		// componer la sql de la union

		// tablas
		DbTableDef[] tablasSqlSolicitada = new DbTableDef[] { TABLE_SOLICITUD,
				TABLE_SOLICITANTES };
		DbTableDef[] tablas = getTablesDefs();

		// qualSolicitada -> SQL para consulta con estado solicitada (sin tabla
		// de usuario gestor)
		// qual -> SQL para consulta con todos los estados (con tabla de usuario
		// gestor)
		// qualComun -> Parte comun a ambas consultas de la union

		DbColumnDef[] COLS_DEF = (DbColumnDef[]) ArrayUtils.concat(
				TABLE_SOLICITUD_COLUMNS_DEF, new DbColumnDef[] {
						CAMPO_NOMBRE_USRSOLICITANTE,
						CAMPO_APELLIDOS_USRSOLICITANTE });

		qual.append(DBUtils.getAnd(qualComun.toString())).append(qualComun);

		String sqlSolictada = null;
		if (qualSolicitada.length() > 0) {
			qualSolicitada.append(DBUtils.getAnd(qualComun.toString())).append(
					qualComun);
			sqlSolictada = DbSelectStatement
					.getSelectStmtText(
							DbUtil.getTableNames(tablasSqlSolicitada),
							DbUtil.getColumnNames(COLS_DEF)
									+ ", '' AS nombreUsuarioGestor, '' AS apellidosUsuarioGestor",
							qualSolicitada.toString(), false);
		}

		String sql = DbSelectStatement.getSelectStmtText(
				DbUtil.getTableNames(tablas), DbUtil.getColumnNames(COLS_DEF)
						+ ", " + CAMPO_NOMBRE_USRGESTOR.getQualifiedName()
						+ " AS nombreUsuarioGestor" + ", "
						+ CAMPO_APELLIDOS_USRGESTOR.getQualifiedName()
						+ " AS apellidosUsuarioGestor", qual.toString(), false);

		StringBuffer sqlQuery = null;
		if (conEstadoSolicitada)
			sqlQuery = new StringBuffer(sqlSolictada).append(" UNION ").append(
					sql);
		else
			sqlQuery = new StringBuffer(sql);

		StringBuffer sqlCompleta = new StringBuffer("SELECT * FROM (").append(
				sqlQuery).append(") a");

		// Ordenación
		StringBuffer sbQual = new StringBuffer();
		if (busquedaSolicitudes.getPageInfo() != null) {
			Map criteriosOrdenacion = new HashMap();
			DbColumnDef CAMPO_ORD_ETIQUETA_SERIE = new DbColumnDef("etiqueta",
					(String) null, ETIQUETA_SERIE_COLUMN_NAME, -1, false);
			DbColumnDef CAMPO_ORD_FECHA_ESTADO = new DbColumnDef("fechaEstado",
					(String) null, FECHA_ESTADO_COLUMN_NAME, -1, false);
			DbColumnDef CAMPO_ORD_ESTADO = new DbColumnDef("estado",
					(String) null, ESTADO_COLUMN_NAME, -1, false);
			DbColumnDef CAMPO_ORD_TIPO = new DbColumnDef("tipo", (String) null,
					TIPO_COLUMN_NAME, -1, false);
			DbColumnDef CAMPO_ORD_APELLIDOS_SOL = new DbColumnDef(
					"apellidosUsuarioSolicitante", (String) null,
					UsuarioDBEntityImpl.APELLIDOS_COLUMN_NAME, 1, false);
			DbColumnDef CAMPO_ORD_APELLIDOS_GESTOR = new DbColumnDef(
					"apellidosUsuarioGestor", (String) null,
					"apellidosUsuarioGestor", 1, false);

			criteriosOrdenacion.put("etiqueta", CAMPO_ORD_ETIQUETA_SERIE);
			criteriosOrdenacion.put("fechaEstado", CAMPO_ORD_FECHA_ESTADO);
			criteriosOrdenacion.put("estado", CAMPO_ORD_ESTADO);
			criteriosOrdenacion.put("tipo", CAMPO_ORD_TIPO);
			criteriosOrdenacion.put("apellidosUsuarioSolicitante",
					CAMPO_ORD_APELLIDOS_SOL);
			criteriosOrdenacion.put("apellidosUsuarioGestor",
					CAMPO_ORD_APELLIDOS_GESTOR);
			sbQual.append(busquedaSolicitudes.getPageInfo().getOrderBy(
					criteriosOrdenacion));
		} else {
			sbQual.append(" ORDER BY ").append(CAMPO_FECHA_ESTADO.getName());
		}

		DbColumnDef CAMPO_NOMBRE_GESTOR = new DbColumnDef(
				"nombreUsuarioGestor", (String) null, "nombreUsuarioGestor", 1,
				false);
		DbColumnDef CAMPO_APELLIDOS_GESTOR = new DbColumnDef(
				"apellidosUsuarioGestor", (String) null,
				"apellidosUsuarioGestor", 1, false);

		DbColumnDef[] COLS_DEF_FINAL = (DbColumnDef[]) ArrayUtils.concat(
				TABLE_SOLICITUD_COLUMNS_DEF, new DbColumnDef[] {
						CAMPO_NOMBRE_USRSOLICITANTE,
						CAMPO_APELLIDOS_USRSOLICITANTE, CAMPO_NOMBRE_GESTOR,
						CAMPO_APELLIDOS_GESTOR });

		return getVOS(COLS_DEF_FINAL, sqlCompleta.toString(),
				sbQual.toString(), SolicitudSerieExtended.class,
				busquedaSolicitudes.getPageInfo());
	}

	protected List getVOS(final DbColumnDef[] COLS_DEFS, final String sqlQuery,
			final String orderBy, final Class classObjectVO,
			final PageInfo pageInfo) throws TooManyResultsException {

		final SigiaDbOutputPaginatedRecordset rowSet = new SigiaDbOutputPaginatedRecordset(
				COLS_DEFS, classObjectVO, pageInfo);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				int size;

				if (StringUtils.contains(sqlQuery, "GROUP BY"))
					size = DbSelectFns.selectCount(conn, "(" + sqlQuery + ")",
							null);
				else
					size = DbSelectFns.selectCount(conn,
							"(" + sqlQuery + ") b", null);

				rowSet.setSize(size);

				new StringBuffer(sqlQuery).append(" " + orderBy).toString();

				if (pageInfo != null) {
					if (size > pageInfo.getMaxNumItems()
							&& pageInfo.getMaxNumItems() >= 0)
						throw new TooManyResultsException(size,
								pageInfo.getMaxNumItems());

					if (size > 0) {
						DbSelectFns.select(conn, sqlQuery, rowSet, pageInfo);
					}
				} else {
					if (size > 0) {
						DbSelectFns.select(conn, sqlQuery, rowSet);
					}
				}
			}
		};

		command.executeWithMaxNumResults();

		return rowSet;
	}

}
