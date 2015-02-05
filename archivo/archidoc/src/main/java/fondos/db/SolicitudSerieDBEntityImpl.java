package fondos.db;

import fondos.model.SolicitudSerie;
import fondos.vos.SolicitudSerieVO;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInputRecord;
import ieci.core.db.DbInputStatement;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbOutputRecord;
import ieci.core.db.DbOutputStatement;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.db.AbstractDbOutputRecordSet;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.exceptions.DBException;
import common.util.DateUtils;

/**
 * Implementación de los métodos de acceso a los datos almacenados en la base de
 * datos referentes a solicitudes de autorización de las acciones implicadas en
 * la gestión de series documentales
 */
public class SolicitudSerieDBEntityImpl extends DBEntity implements
		ISolicitudSerieDbEntity {

	public static final String TABLE_NAME = "ASGFSOLICITUDSERIE";

	private static final String ID_COLUMN_NAME = "ID";
	private static final String ID_SERIE_COLUMN_NAME = "IDSERIE";
	private static final String ETIQUETA_SERIE_COLUMN_NAME = "ETIQUETASERIE";
	private static final String TIPO_COLUMN_NAME = "TIPO";
	private static final String FECHA_COLUMN_NAME = "FECHA";
	private static final String INFO_COLUMN_NAME = "INFO";
	private static final String MOTIVO_SOLICITUD_COLUMN_NAME = "MOTIVOSOLICITUD";
	private static final String USR_SOLICITANTE_COLUMN_NAME = "IDUSRSOLICITANTE";
	private static final String ESTADO_COLUMN_NAME = "ESTADO";
	private static final String FECHA_ESTADO_COLUMN_NAME = "FECHAESTADO";
	private static final String USR_GESTOR_COLUMN_NAME = "IDUSRGESTOR";
	private static final String MOTIVO_RECHAZO_COLUMN_NAME = "MOTIVORECHAZO";

	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_ID_SERIE = new DbColumnDef(null,
			TABLE_NAME, ID_SERIE_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_ETIQUETA_SERIE = new DbColumnDef(
			null, TABLE_NAME, ETIQUETA_SERIE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_TIPO = new DbColumnDef(null,
			TABLE_NAME, TIPO_COLUMN_NAME, DbDataType.LONG_INTEGER, 32, false);
	public static final DbColumnDef CAMPO_FECHA = new DbColumnDef(null,
			TABLE_NAME, FECHA_COLUMN_NAME, DbDataType.DATE_TIME, 32, false);
	public static final DbColumnDef CAMPO_INFO = new DbColumnDef(null,
			TABLE_NAME, INFO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_MOTIVO = new DbColumnDef(null,
			TABLE_NAME, MOTIVO_SOLICITUD_COLUMN_NAME, DbDataType.SHORT_TEXT,
			32, false);
	public static final DbColumnDef CAMPO_USR_SOLICITANTE = new DbColumnDef(
			null, TABLE_NAME, USR_SOLICITANTE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_ESTADO = new DbColumnDef(null,
			TABLE_NAME, ESTADO_COLUMN_NAME, DbDataType.LONG_INTEGER, 32, false);
	public static final DbColumnDef CAMPO_FECHA_ESTADO = new DbColumnDef(null,
			TABLE_NAME, FECHA_ESTADO_COLUMN_NAME, DbDataType.DATE_TIME, 32,
			false);
	public static final DbColumnDef CAMPO_USR_GESTOR = new DbColumnDef(null,
			TABLE_NAME, USR_GESTOR_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);
	public static final DbColumnDef CAMPO_MOTIVO_RECHAZO = new DbColumnDef(
			null, TABLE_NAME, MOTIVO_RECHAZO_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	private static final DbColumnDef[] ALL_COLUMNS_DEF = new DbColumnDef[] {
			CAMPO_ID, CAMPO_ID_SERIE, CAMPO_ETIQUETA_SERIE, CAMPO_TIPO,
			CAMPO_FECHA, CAMPO_INFO, CAMPO_MOTIVO, CAMPO_USR_SOLICITANTE,
			CAMPO_ESTADO, CAMPO_FECHA_ESTADO, CAMPO_USR_GESTOR,
			CAMPO_MOTIVO_RECHAZO };

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public SolicitudSerieDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public SolicitudSerieDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	public SolicitudSerieVO insertSolicitud(SolicitudSerieVO solicitud) {
		DbConnection conn = null;
		try {
			conn = getConnection();
			solicitud.setId(getGuid(solicitud.getId()));
			solicitud.setFecha(new Date());
			// DbColumnDef[] colsToInsert = {CAMPO_ID};
			DbInsertFns.insert(conn, TABLE_NAME,
					DbUtil.getColumnNames(ALL_COLUMNS_DEF),
					new SigiaDbInputRecord(ALL_COLUMNS_DEF, solicitud));
			return solicitud;
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	private String getSolicitudesSql(final String idUser, final int[] tipos,
			int[] estadosAMostrar) {
		StringBuffer qual = new StringBuffer();
		if (idUser != null)
			qual.append(DBUtils.generateEQTokenField(CAMPO_USR_SOLICITANTE,
					idUser));
		if (tipos != null) {
			if (qual.length() > 0)
				qual.append(" and ");
			qual.append(DBUtils.generateORTokens(CAMPO_TIPO, tipos));
		}
		if (estadosAMostrar != null) {
			if (qual.length() > 0)
				qual.append(" and ");
			qual.append(DBUtils.generateORTokens(CAMPO_ESTADO, estadosAMostrar));
		}
		if (qual.length() > 0)
			qual.insert(0, " where ");

		return qual.toString();
	}

	public int getCountSolicitudes(final String idUser, final int[] tipos,
			int[] estadosAMostrar) {
		DbConnection conn = null;
		try {
			conn = getConnection();
			String qual = getSolicitudesSql(idUser, tipos, estadosAMostrar);
			int solicitudes = DbSelectFns.selectCount(conn, TABLE_NAME,
					CAMPO_ID.getQualifiedName(), qual, false);
			return solicitudes;
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	public List getSolicitudes(final String idUser, final int[] tipos,
			int[] estadosAMostrar) {
		DbConnection conn = null;
		try {
			conn = getConnection();
			String qual = getSolicitudesSql(idUser, tipos, estadosAMostrar);
			final ArrayList solicitudes = new ArrayList();
			DbSelectFns.select(conn, TABLE_NAME,
					DbUtil.getColumnNames(ALL_COLUMNS_DEF), qual, false,
					new AbstractDbOutputRecordSet() {
						DbOutputRecord aRow = new DbOutputRecord() {
							public void getStatementValues(
									DbOutputStatement stmt) throws Exception {
								SolicitudSerieVO unaSolicitud = new SolicitudSerie();
								DBUtils.fillVoWithDbOutputStament(
										ALL_COLUMNS_DEF, stmt, unaSolicitud);
								solicitudes.add(unaSolicitud);
							}
						};

						public DbOutputRecord newRecord() throws Exception {
							return aRow;
						}
					});
			return solicitudes;
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	public SolicitudSerieVO getSolicitud(String idSolicitud) {
		DbConnection conn = null;
		try {
			conn = getConnection();
			StringBuffer qual = new StringBuffer().append(" where ").append(
					DBUtils.generateEQTokenField(CAMPO_ID, idSolicitud));
			final SolicitudSerieVO solicitud = new SolicitudSerie();
			DbSelectFns.select(conn, TABLE_NAME,
					DbUtil.getColumnNames(ALL_COLUMNS_DEF), qual.toString(),
					new DbOutputRecord() {
						public void getStatementValues(DbOutputStatement stmt)
								throws Exception {
							DBUtils.fillVoWithDbOutputStament(ALL_COLUMNS_DEF,
									stmt, solicitud);
						}
					});
			return solicitud;
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	public SolicitudSerieVO getSolicitudBySerie(String idSerie) {
		DbConnection conn = null;
		try {
			conn = getConnection();
			StringBuffer qual = new StringBuffer().append(" where ").append(
					DBUtils.generateEQTokenField(CAMPO_ID_SERIE, idSerie));
			final SolicitudSerieVO solicitud = new SolicitudSerie();
			DbSelectFns.select(conn, TABLE_NAME,
					DbUtil.getColumnNames(ALL_COLUMNS_DEF), qual.toString(),
					new DbOutputRecord() {
						public void getStatementValues(DbOutputStatement stmt)
								throws Exception {
							DBUtils.fillVoWithDbOutputStament(ALL_COLUMNS_DEF,
									stmt, solicitud);
						}
					});
			return solicitud;
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	public void updateFields(String idSolicitud, final Map columnsToUpdate) {
		DbConnection conn = null;
		try {
			conn = getConnection();
			StringBuffer qual = new StringBuffer().append(" where ").append(
					DBUtils.generateEQTokenField(CAMPO_ID, idSolicitud));
			final DbColumnDef[] colsToUpdate = (DbColumnDef[]) columnsToUpdate
					.keySet().toArray(EMPTY_COLDEF_ARRAY);
			DbUpdateFns.update(conn, TABLE_NAME,
					DbUtil.getColumnNames(colsToUpdate), new DbInputRecord() {
						public void setStatementValues(DbInputStatement stmt)
								throws Exception {
							for (int i = 0; i < colsToUpdate.length; i++)
								stmt.setObject(i + 1,
										columnsToUpdate.get(colsToUpdate[i]));
						}
					}, qual.toString());
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	public void saveAutorizacionSolicitud(String idSolicitud, String usuario) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, idSolicitud));
		Map columnsToUpdate = new HashMap();
		columnsToUpdate.put(CAMPO_ESTADO,
				String.valueOf(SolicitudSerie.ESTADO_EJECUTADA));
		columnsToUpdate.put(CAMPO_FECHA_ESTADO, DateUtils.getFechaActual());
		if (usuario != null)
			columnsToUpdate.put(CAMPO_USR_GESTOR, usuario);
		updateFields(qual.toString(), columnsToUpdate, TABLE_NAME);
	}

	public void saveRechazoSolicitud(String idSolicitud, String usuario,
			String motivoRechazo) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, idSolicitud));
		Map columnsToUpdate = new HashMap();
		columnsToUpdate.put(CAMPO_ESTADO,
				String.valueOf(SolicitudSerie.ESTADO_DENEGADA));
		columnsToUpdate.put(CAMPO_FECHA_ESTADO, DateUtils.getFechaActual());
		if (usuario != null)
			columnsToUpdate.put(CAMPO_USR_GESTOR, usuario);
		if (motivoRechazo != null)
			columnsToUpdate.put(CAMPO_MOTIVO_RECHAZO, motivoRechazo);
		updateFields(qual.toString(), columnsToUpdate, TABLE_NAME);
	}

	/**
	 * Elimina las entradas que se indican de la tabla en la que se almacenan
	 * las solicitudes de autorización de las acciones implicadas en la gestión
	 * de series documentales
	 * 
	 * @param idSolicitud
	 *            Identificadores de las solicitudes a eliminar
	 */
	public void eliminarSolicitudes(String[] idSolicitud) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateInTokenField(CAMPO_ID, idSolicitud));
		deleteVO(qual.toString(), TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.db.ISolicitudSerieDbEntity#eliminarSolicitudesByIdSerie(java.lang.String)
	 */
	public void eliminarSolicitudesByIdSerie(String idSerie) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_ID_SERIE, idSerie));
		deleteVO(qual.toString(), TABLE_NAME);
	}
}