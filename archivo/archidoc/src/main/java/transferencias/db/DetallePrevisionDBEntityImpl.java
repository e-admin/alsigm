package transferencias.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInputStatement;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbOutputRecord;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbSelectStatement;
import ieci.core.db.DbTableDef;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import transferencias.db.oracle.RelacionEntregaDBEntityImpl;
import transferencias.vos.DetallePrevisionVO;

import common.Constants;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.db.TableDef;
import common.lang.MutableInt;

import deposito.db.FormatoDBEntity;
import deposito.db.UDocEnUiDepositoDbEntityImpl;
import deposito.db.UInstalacionDepositoDBEntity;
import deposito.vos.UInsDepositoVO;

/**
 * Clase con los metodos encargados de recuperar y almacenar en la base de datos
 * la informacion referente a un detalle de prevision
 *
 */
public class DetallePrevisionDBEntityImpl extends DBEntity implements
		IDetallePrevisionDBEntity {
//	private static final String FORMATO_FECHA = "dd/MM/yyyy";
//
//	protected static final SimpleDateFormat dateFormater = new SimpleDateFormat(
//			FORMATO_FECHA);

	protected abstract class DetallePrevisionVOFactory implements
			DbOutputRecord {
		DetallePrevisionVO infoDetallePrevision = null;

		DetallePrevisionVO getDetallePrevision() {
			return this.infoDetallePrevision;
		}
	}

	static Logger logger = Logger.getLogger(DetallePrevisionDBEntityImpl.class);

	public static final String TABLE_NAME = "ASGTDETALLEPREV";

	public static final DbTableDef TABLE_DEF = new DbTableDef(TABLE_NAME,
			"detalle");

	public static final String ID_COLUMN_NAME = "id";
	public static final String ID_PREVISION_COLUMN_NAME = "idprevision";
	public static final String ORDEN_COLUMN_NAME = "orden";
	public static final String COD_SISTEMA_PRODUCTOR_COLUMN_NAME = "codsistproductor";
	public static final String NOMBRE_SISTEMA_PRODUCTOR_COLUMN_NAME = "nombresistproductor";
	public static final String ID_PROCEDIMIENTO_COLUMN_NAME = "idprocedimiento";
	public static final String ID_SERIEDESTINO_COLUMN_NAME = "idseriedestino";
	public static final String ANO_INICIO_EXPEDIENTES_COLUMN_NAME = "anoiniudoc";
	public static final String ANO_FIN_EXPEDIENTES_COLUMN_NAME = "anofinudoc";
	public static final String UNIDADES_INSTALACION_COLUMN_NAME = "numuinstalacion";
	public static final String FORMATO_COLUMN_NAME = "idformatoui";
	public static final String FLAG_CERRADA_COLUMN_NAME = "cerrado";
	public static final String OBSERVACIONES_COLUMN_NAME = "observaciones";
	public static final String NUMRENTREGA_COLUMN_NAME = "numrentrega";
	public static final String ID_SERIEORIGEN_COLUMN_NAME = "idserieorigen";
	public static final String INFO_COLUMN_NAME = "info";

	public static final String UNIDADES_VALIDAS_COLUMN_NAME = "Unidadesvalidas";
	public static final String UNIDADES_TOTALES_COLUMN_NAME = "Unidadestotales";
	public static final String NOMBRE_FORMATO_COLUMN_NAME = "NombreFormato";

	public static final DbColumnDef ID_FIELD = new DbColumnDef(ID_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef ID_PREVISION_FIELD = new DbColumnDef(
			ID_PREVISION_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef NUM_ORDEN_FIELD = new DbColumnDef(
			"numeroOrden", TABLE_NAME, ORDEN_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, false);

	public static final DbColumnDef SISTEMA_PRODUCTOR_FIELD = new DbColumnDef(
			COD_SISTEMA_PRODUCTOR_COLUMN_NAME, DbDataType.SHORT_TEXT, 16, false);

	public static final DbColumnDef NOMBRE_SISTEMA_PRODUCTOR_FIELD = new DbColumnDef(
			NOMBRE_SISTEMA_PRODUCTOR_COLUMN_NAME, DbDataType.SHORT_TEXT, 64,
			false);

	public static final DbColumnDef PROCEDIMIENTO_FIELD = new DbColumnDef(
			ID_PROCEDIMIENTO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef ID_SERIEDESTINO_FIELD = new DbColumnDef(
			ID_SERIEDESTINO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef FECHA_INICIO_EXPEDIENTES_FIELD = new DbColumnDef(
			ANO_INICIO_EXPEDIENTES_COLUMN_NAME, DbDataType.SHORT_TEXT, 4, false);

	public static final DbColumnDef FECHA_FIN_EXPEDIENTES_FIELD = new DbColumnDef(
			ANO_FIN_EXPEDIENTES_COLUMN_NAME, DbDataType.SHORT_TEXT, 4, false);

	public static final DbColumnDef UNIDADES_INSTALACION_FIELD = new DbColumnDef(
			UNIDADES_INSTALACION_COLUMN_NAME, DbDataType.SHORT_INTEGER, false);

	public static final DbColumnDef FORMATO_FIELD = new DbColumnDef(
			FORMATO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef FLAG_CERRADA_FIELD = new DbColumnDef(
			FLAG_CERRADA_COLUMN_NAME, DbDataType.SHORT_TEXT, 1, false);

	public static final DbColumnDef OBSERVACIONES_FIELD = new DbColumnDef(
			OBSERVACIONES_COLUMN_NAME, DbDataType.SHORT_TEXT, 1024, false);

	public static final DbColumnDef NUMRENTREGA_FIELD = new DbColumnDef(
			NUMRENTREGA_COLUMN_NAME, DbDataType.SHORT_INTEGER, false);

	public static final DbColumnDef ID_SERIEDORIGEN_FIELD = new DbColumnDef(
			ID_SERIEORIGEN_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef INFO_FIELD = new DbColumnDef(
			INFO_COLUMN_NAME, DbDataType.SHORT_TEXT, 1024, false);

	public static final DbColumnDef UNIDADES_VALIDAS_FIELD = new DbColumnDef(
			UNIDADES_VALIDAS_COLUMN_NAME, DbDataType.SHORT_TEXT, false);

	public static final DbColumnDef UNIDADES_TOTALES_FIELD = new DbColumnDef(
			UNIDADES_TOTALES_COLUMN_NAME, DbDataType.SHORT_TEXT, false);

	public static final DbColumnDef[] TABLE_COLUMNS = { ID_FIELD,
			ID_PREVISION_FIELD, NUM_ORDEN_FIELD, SISTEMA_PRODUCTOR_FIELD,
			NOMBRE_SISTEMA_PRODUCTOR_FIELD, PROCEDIMIENTO_FIELD,
			ID_SERIEDESTINO_FIELD, FECHA_INICIO_EXPEDIENTES_FIELD,
			FECHA_FIN_EXPEDIENTES_FIELD, UNIDADES_INSTALACION_FIELD,
			FORMATO_FIELD, FLAG_CERRADA_FIELD, OBSERVACIONES_FIELD,
			NUMRENTREGA_FIELD, ID_SERIEDORIGEN_FIELD, INFO_FIELD };

	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(TABLE_COLUMNS);

	/**
	 * Obtiene el nombre de la tabla
	 *
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public DetallePrevisionDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public DetallePrevisionDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	protected List getDetallesPrevision(String qual) {
		return getVOS(qual, TABLE_NAME, TABLE_COLUMNS, DetallePrevisionVO.class);
	}

	protected DetallePrevisionVO getDetallePrevision(String qual) {
		return (DetallePrevisionVO) getVO(qual, TABLE_NAME, TABLE_COLUMNS,
				DetallePrevisionVO.class);
	}

	/**
	 * Obtiene de las lineas de detalle de una prevision
	 *
	 * @param codigoPrevision
	 *            Codigo de la prevision cuyos detalles se quieren recuperar
	 * @param conn
	 *            Conexión a la base de datos
	 * @return Lista con las lineas de detalle de la prevision
	 * @throws Exception
	 */
	public Collection fetchRowsByCodigoPrevision(String codigoPrevision) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(ID_PREVISION_FIELD, codigoPrevision));
		return getDetallesPrevision(qual.toString());

		/*
		 * LUIS String qual = new StringBuffer().append(
		 * MessageFormat.format(QUAL_BY_CODIGO_PREVISION, new String[] {
		 * codigoPrevision }))
		 * .append(" order by ").append(NUM_ORDEN_COLUMN_NAME).toString();
		 * return getRows(conn, qual);
		 */
	}

	/**
	 * Incorpora una nueva linea de detalle a una prevision
	 *
	 * @param conn
	 *            Conexion a la base de datos
	 * @param detallePrevision
	 *            Informacion de la linea de detalle a grabar en la base de
	 *            datos
	 * @throws Exception
	 */
	public void insertRow(final DetallePrevisionVO detallePrevision) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				// DbColumnDef[] COLS_TO_INSERT = {ID_FIELD, ID_PREVISION_FIELD,
				// SISTEMA_PRODUCTOR_FIELD,
				// NOMBRE_SISTEMA_PRODUCTOR_FIELD, PROCEDIMIENTO_FIELD,
				// SERIE_FIELD,
				// FECHA_INICIO_EXPEDIENTES_FIELD, FECHA_FIN_EXPEDIENTES_FIELD,
				// UNIDADES_INSTALACION_FIELD, FORMATO_FIELD,
				// FLAG_CERRADA_FIELD, OBSERVACIONES_FIELD,
				// NUMRENTREGA_FIELD };
				detallePrevision.setId(getGuid(detallePrevision.getId()));
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						TABLE_COLUMNS, detallePrevision);
				DbInsertFns.insert(conn, TABLE_NAME,
						DbUtil.getColumnNames(TABLE_COLUMNS), inputRecord);

				// StringBuffer query = new
				// StringBuffer("update ").append(TABLE_NAME).append(" set ").append(
				// NUM_ORDEN_COLUMN_NAME).append("=").append("(select nvl(max(").append(
				// NUM_ORDEN_COLUMN_NAME).append(")+1,1)").append(" from ").append(TABLE_NAME).append(
				// " where ").append(
				// DBUtils.generateEQTokenField(ID_PREVISION_FIELD,
				// detallePrevision
				// .getIdPrevision())).append(") where ").append(
				// DBUtils.generateEQTokenField(ID_FIELD,
				// detallePrevision.getId()));
				StringBuffer query = new StringBuffer("update ")
						.append(TABLE_NAME)
						.append(" set ")
						.append(ORDEN_COLUMN_NAME)
						.append("=")
						.append("(select ")
						.append(DBUtils.getNativeIfNullSintax(
								getConnection(),
								new StringBuffer("max(")
										.append(ORDEN_COLUMN_NAME)
										.append(")+1").toString(), "1"))
						.append(" from ")
						.append(TABLE_NAME)
						.append(" where ")
						.append(DBUtils.generateEQTokenField(
								ID_PREVISION_FIELD,
								detallePrevision.getIdPrevision()))
						.append(") where ")
						.append(DBUtils.generateEQTokenField(ID_FIELD,
								detallePrevision.getId()));

				DbInputStatement stmt = new DbInputStatement();
				stmt.create(conn, query.toString());
				stmt.execute();
				stmt.release();
			}
		};
		command.execute();
	}

	/**
	 * Actualiza la informacion de una linea de detalle
	 *
	 * @param conn
	 *            Conexion a la base de datos
	 * @param detallePrevision
	 *            Informacion de la linea de detalle a actualizar
	 * @throws Exception
	 */
	public void updateRow(final DetallePrevisionVO detallePrevision) {
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(UNIDADES_INSTALACION_FIELD, new Integer(
				detallePrevision.getNumUInstalacion()));
		colsToUpdate.put(FECHA_INICIO_EXPEDIENTES_FIELD,
				detallePrevision.getAnoIniUdoc());
		colsToUpdate.put(FECHA_FIN_EXPEDIENTES_FIELD,
				detallePrevision.getAnoFinUdoc());
		colsToUpdate.put(FORMATO_FIELD, detallePrevision.getIdFormatoUI());
		colsToUpdate.put(OBSERVACIONES_FIELD,
				detallePrevision.getObservaciones());
		colsToUpdate.put(PROCEDIMIENTO_FIELD,
				detallePrevision.getIdProcedimiento());
		colsToUpdate.put(ID_SERIEDORIGEN_FIELD,
				detallePrevision.getIdSerieOrigen());
		colsToUpdate.put(ID_SERIEDESTINO_FIELD,
				detallePrevision.getIdSerieDestino());
		colsToUpdate.put(NUM_ORDEN_FIELD,
				new Integer(detallePrevision.getNumeroOrden()));

		StringBuffer qual = new StringBuffer("where ").append(DBUtils
				.generateEQTokenField(ID_FIELD, detallePrevision.getId()));
		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);

		/*
		 * LUIS DbColumnDef[] COLUMNS_TO_UPDATE = { UNIDADES_INSTALACION_FIELD,
		 * FECHA_INICIO_EXPEDIENTES_FIELD, FECHA_FIN_EXPEDIENTES_FIELD,
		 * FORMATO_FIELD, OBSERVACIONES_FIELD, PROCEDIMIENTO_FIELD, SERIE_FIELD
		 * }; StringBuffer qual = new StringBuffer().append("where ").append(
		 * TransferenciasDBUtil.generateEQTokenField(ID_FIELD,
		 * detallePrevision.getId())); DbUpdateFns.update(conn, TABLE_NAME,
		 * DbUtil.getColumnNames(COLUMNS_TO_UPDATE), new DbInputRecord() {
		 * public void setStatementValues(DbInputStatement stmt) throws
		 * Exception { int i = 1; stmt.setLongInteger(i++,
		 * detallePrevision.getNumUInstalacion()); stmt.setShortText(i++,
		 * detallePrevision.getAnoIniUdoc()); stmt.setShortText(i++,
		 * detallePrevision.getAnoFinUdoc()); stmt.setShortText(i++,
		 * detallePrevision.getIdFormatoUI()); stmt.setShortText(i++,
		 * detallePrevision.getObservaciones()); stmt.setShortText(i++,
		 * detallePrevision.getIdProcedimiento()); stmt.setShortText(i++,
		 * detallePrevision.getIdSerie()); } }, qual.toString());
		 */
	}

	/**
	 * Obtiene la informacion correspondiente a una linea de detalle de una
	 * prevision
	 *
	 * @param conn
	 *            Conexion a la base de datos
	 * @param prevision
	 *            Identificador de la prevision a la que pertenece la linea de
	 *            detalle
	 * @param numeroDetalle
	 *            Numero de la linea de detalle
	 * @return Informacion de la linea de detalle
	 * @throws Exception
	 */
	public DetallePrevisionVO selectRow(String prevision, int numeroDetalle) {
		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateEQTokenField(ID_PREVISION_FIELD,
						prevision))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(NUM_ORDEN_FIELD,
						numeroDetalle));
		return getDetallePrevision(qual.toString());
		/*
		 * LUIS String qual = MessageFormat.format(ONE_ROW_QUAL, new String[] {
		 * prevision, String.valueOf(numeroDetalle) }); final DetallePrevisionVO
		 * detallePrevision = new DetallePrevisionVO(); DbOutputRecord aRow =
		 * new DbOutputRecord() { public void
		 * getStatementValues(DbOutputStatement stmt) throws Exception {
		 * fillDetallePrevisionVO(detallePrevision, stmt); } };
		 * DbSelectFns.select(conn, TABLE_NAME, COLUM_NAMES_LIST, qual, aRow);
		 * return detallePrevision;
		 */
	}

	/**
	 * Obtiene la informacion correspondiente a una linea de detalle de una
	 * prevision
	 *
	 * @param conn
	 *            Conexion a la base de datos
	 * @param idDetalle
	 *            Identificador de la linea de detalle
	 * @return Informacion de la linea de detalle
	 * @throws Exception
	 */
	public DetallePrevisionVO selectRow(String idDetalle) {
		StringBuffer qual = new StringBuffer("where ").append(DBUtils
				.generateEQTokenField(ID_FIELD, idDetalle));
		return getDetallePrevision(qual.toString());

		/*
		 * LUIS String qual = new StringBuffer().append(" WHERE ").append(
		 * TransferenciasDBUtil.generateEQTokenField(ID_FIELD,
		 * idDetalle)).toString();
		 *
		 * final DetallePrevisionVO detallePrevision = new DetallePrevisionVO();
		 * DbOutputRecord aRow = new DbOutputRecord() { public void
		 * getStatementValues(DbOutputStatement stmt) throws Exception {
		 * fillDetallePrevisionVO(detallePrevision, stmt); } };
		 * DbSelectFns.select(conn, TABLE_NAME, COLUM_NAMES_LIST, qual, aRow);
		 * return detallePrevision;
		 */
	}

	/**
	 * Borra lineas de detalle de una previsión. Caso de indicarse un
	 * identificador de línea de detalle únicamente la línea indicada es
	 * eliminada. Si no se indica línea de detalle se eliminan todas las líneas
	 * de detalle de la previsión
	 *
	 * @param prevision
	 *            Identificador de previsión de transferencia
	 * @param idDetalle
	 *            Identificador de línea de detalle. Puede ser nulo
	 */
	public void dropRow(final String prevision, final String idDetalle) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
						.generateEQTokenField(ID_PREVISION_FIELD, prevision));
				if (idDetalle != null)
					qual.append(" AND ")
							.append("(")
							.append(DBUtils.generateEQTokenField(ID_FIELD,
									idDetalle)).append(")");
				DbDeleteFns.delete(conn, TABLE_NAME, qual.toString());
			}
		};
		command.execute();
	}

	/**
	 * Obtiene la linea de detalle con expedientes mas recientes de un
	 * determinado procedimiento
	 *
	 */
	public DetallePrevisionVO selectLastRowByProcedimiento(String procedimiento) {
		StringBuffer qual = new StringBuffer("where ")
				.append(ANO_FIN_EXPEDIENTES_COLUMN_NAME)
				.append("=(")
				.append("select max(")
				.append(ANO_FIN_EXPEDIENTES_COLUMN_NAME)
				.append(") ")
				.append("from ")
				.append(TABLE_NAME)
				.append(" where ")
				.append(DBUtils.generateEQTokenField(PROCEDIMIENTO_FIELD,
						procedimiento)).append(")");
		return getDetallePrevision(qual.toString());
	}

	/*
	 * LUIS private static DetallePrevisionVO getRow(DbConnection conn, String
	 * qual) throws Exception { final DetallePrevisionVO detallePrevision = new
	 * DetallePrevisionVO(); DbOutputRecord aRow = new DbOutputRecord() { public
	 * void getStatementValues(DbOutputStatement stmt) throws Exception {
	 * fillDetallePrevisionVO(detallePrevision, stmt); } }; try {
	 * DbSelectFns.select(conn, TABLE_NAME, COLUM_NAMES_LIST, qual, aRow); }
	 * catch (IeciTdException iecie) { if
	 * (iecie.getErrorCode().equals(DbError.EC_NOT_FOUND)) return null; else
	 * throw iecie; }
	 *
	 * return detallePrevision; }
	 */

	public Collection selectRowsWithoutRelacion(String prevision) {
		StringBuffer qual = new StringBuffer("where ")
				.append(DBUtils.generateEQTokenField(ID_PREVISION_FIELD,
						prevision))
				.append("and ")
				.append(ID_COLUMN_NAME)
				.append(" not in (select ")
				.append(RelacionEntregaDBEntityImpl.DETALLEPREVISION)
				.append(" from ")
				.append(RelacionEntregaDBEntityImpl.TABLE_NAME)
				.append(" where ")
				.append(DBUtils.generateEQTokenField(
						RelacionEntregaDBEntityImpl.CAMPO_IDPREVISION,
						prevision)).append(")");
		return getDetallesPrevision(qual.toString());

		/*
		 * LUIS StringBuffer qual = new StringBuffer("where ")
		 * .append(DBUtils.generateEQTokenField(ID_PREVISION_FIELD,prevision))
		 * .append("and ") .append(ID_COLUMN_NAME).append(" not in ")
		 * .append("(select "
		 * ).append(RelacionEntregaTableDefinition.DETALLEPREVISION.getName())
		 * .append
		 * (" from ").append(RelacionEntregaTableDefinition.TABLE_NAME).append
		 * (" where ")
		 * .append(RelacionEntregaTableDefinition.IDPREVISION.getName
		 * ()).append("='").append( prevision).append("')").toString();
		 *
		 * return getRows(conn, qual);
		 */
	}

	public Collection selectRowsWithoutRelacionXFormato(String prevision,
			String idFormato) {
		StringBuffer qual = new StringBuffer("where ")
				.append(DBUtils.generateEQTokenField(ID_PREVISION_FIELD,
						prevision))
				.append(" and ")
				.append(ID_COLUMN_NAME)
				.append(" not in (select ")
				.append(RelacionEntregaDBEntityImpl.DETALLEPREVISION)
				.append(" from ")
				.append(RelacionEntregaDBEntityImpl.TABLE_NAME)
				.append(" where ")
				.append(DBUtils.generateEQTokenField(
						RelacionEntregaDBEntityImpl.CAMPO_IDPREVISION,
						prevision)).append(") AND ")
				.append(DBUtils.generateEQTokenField(FORMATO_FIELD, idFormato));
		return getDetallesPrevision(qual.toString());

		/*
		 * LUIS String qual = new
		 * StringBuffer().append("where ").append(ID_PREVISION_COLUMN_NAME
		 * ).append(
		 * "='").append(prevision).append("' and ").append(ID_COLUMN_NAME
		 * ).append( " not in (select ").append(RelacionEntregaTableDefinition.
		 * DETALLEPREVISION.getName())
		 * .append(" from ").append(RelacionEntregaTableDefinition
		 * .TABLE_NAME).append(" where ")
		 * .append(RelacionEntregaTableDefinition.
		 * IDPREVISION.getName()).append("='").append(
		 * prevision).append("')").append(" AND ").append(
		 * TransferenciasDBUtil.generateEQTokenField(FORMATO_FIELD,
		 * idFormato)).toString();
		 *
		 * return getRows(conn, qual);
		 */
	}

	/*
	 * LUIS public boolean isDetalleWithRelacionXFormato(String idDetalle,
	 * String idFormato) { return
	 * RelacionEntregaDBEntity.existRelacionesXDetallePrevisionYFormato(conn,
	 * idDetalle, idFormato); }
	 */

	/*
	 * LUIS private static Collection getRows(DbConnection conn, String qual)
	 * throws Exception { final MapColumnsNamesIndex map = TransferenciasDBUtil
	 * .getMapIndexColumnsNames(COLUM_NAMES_LIST); final ArrayList rows = new
	 * ArrayList(); DbOutputRecordSet rowSet = new AbstractDbOutputRecordSet() {
	 * DbOutputRecord aRow = new DbOutputRecord() { public void
	 * getStatementValues(DbOutputStatement stmt) throws Exception {
	 * DetallePrevisionVO infoDetallePrevision = new DetallePrevisionVO();
	 * fillDetallePrevisionVO(infoDetallePrevision, stmt); //int numRelaciones =
	 * stmt.getLongInteger(map.getNumMappedColumns()+1);
	 * rows.add(infoDetallePrevision); } };
	 *
	 * public DbOutputRecord newRecord() throws Exception { return aRow; }
	 *
	 * };
	 *
	 * DbSelectFns.select(conn, TABLE_NAME, COLUM_NAMES_LIST, qual, false,
	 * rowSet); return rows.size() > 0 ? rows : null; }
	 */

	/*
	 * LUIS static void fillDetallePrevisionVO(DetallePrevisionVO
	 * infoDetallePrevision, DbOutputStatement stmt) throws Exception {
	 * infoDetallePrevision
	 * .setId(stmt.getShortText(MAP_COLUM_NAMES_INDEX.get(ID_FIELD)));
	 * infoDetallePrevision
	 * .setIdPrevision(stmt.getShortText(MAP_COLUM_NAMES_INDEX
	 * .get(ID_PREVISION_FIELD))); infoDetallePrevision.numeroOrden =
	 * stmt.getLongInteger(MAP_COLUM_NAMES_INDEX .get(NUM_ORDEN_FIELD));
	 * infoDetallePrevision
	 * .setCodSistProductor(stmt.getShortText(MAP_COLUM_NAMES_INDEX
	 * .get(SISTEMA_PRODUCTOR_FIELD)));
	 * infoDetallePrevision.setNombreSistProductor
	 * (stmt.getShortText(MAP_COLUM_NAMES_INDEX
	 * .get(NOMBRE_SISTEMA_PRODUCTOR_FIELD)));
	 * infoDetallePrevision.setIdProcedimiento
	 * (stmt.getShortText(MAP_COLUM_NAMES_INDEX .get(PROCEDIMIENTO_FIELD)));
	 * infoDetallePrevision
	 * .setIdSerie(stmt.getShortText(MAP_COLUM_NAMES_INDEX.get(SERIE_FIELD)));
	 *
	 * infoDetallePrevision.anoInicioExpedientes =
	 * stmt.getShortText(MAP_COLUM_NAMES_INDEX
	 * .get(FECHA_INICIO_EXPEDIENTES_FIELD));
	 * infoDetallePrevision.anoFinExpedientes =
	 * stmt.getShortText(MAP_COLUM_NAMES_INDEX
	 * .get(FECHA_FIN_EXPEDIENTES_FIELD)); infoDetallePrevision.numUInstalacion
	 * = stmt.getLongInteger(MAP_COLUM_NAMES_INDEX
	 * .get(UNIDADES_INSTALACION_FIELD));
	 * infoDetallePrevision.setIdFormatoUI(stmt
	 * .getShortText(MAP_COLUM_NAMES_INDEX .get(FORMATO_FIELD)));
	 * infoDetallePrevision.cerrada =
	 * "S".equalsIgnoreCase(stmt.getShortText(MAP_COLUM_NAMES_INDEX
	 * .get(FLAG_CERRADA_FIELD))); infoDetallePrevision.observaciones =
	 * stmt.getShortText(MAP_COLUM_NAMES_INDEX .get(OBSERVACIONES_FIELD));
	 * infoDetallePrevision.numrentrega =
	 * stmt.getLongInteger(MAP_COLUM_NAMES_INDEX .get(NUMRENTREGA_FIELD)); }
	 *
	 * static int getIndex(MapColumnsNamesIndex mapColumnsNames, DbColumnDef
	 * columnDef) { return mapColumnsNames.get(columnDef); }
	 */

	public DetallePrevisionVO selectLastRowBySerie(String serie) {
		StringBuffer qual = new StringBuffer("where ")
				.append(ANO_FIN_EXPEDIENTES_COLUMN_NAME)
				.append("=(")
				.append("select max(")
				.append(ANO_FIN_EXPEDIENTES_COLUMN_NAME)
				.append(") where ")
				.append(DBUtils.generateEQTokenField(ID_SERIEDESTINO_FIELD,
						serie));
		return getDetallePrevision(qual.toString());
	}

	public int numeroDetallesPrevision(final String idPrevision) {
		final MutableInt nDetalles = new MutableInt(0);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
						.generateEQTokenField(ID_PREVISION_FIELD, idPrevision));
				nDetalles.setValue(DbSelectFns.selectCount(conn, TABLE_NAME,
						qual.toString()));
			}
		};
		command.execute();
		return nDetalles.getValue();
	}

	public int incNRelacionesAsociadasADetalle(final String idDetalle) {
		final MutableInt nRelaciones = new MutableInt(0);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
						.generateEQTokenField(ID_FIELD, idDetalle));
				DbUpdateFns.incrementField(conn, TABLE_NAME,
						NUMRENTREGA_COLUMN_NAME, 1, qual.toString());
				nRelaciones.setValue(DbSelectFns.selectLongInteger(conn,
						TABLE_NAME, NUMRENTREGA_COLUMN_NAME, qual.toString()));
			}
		};
		command.execute();
		return nRelaciones.getValue();

		/*
		 * LUIS int siguienteNumero = 1; String qual = new
		 * StringBuffer().append(" WHERE ").append(
		 * TransferenciasDBUtil.generateEQTokenField(ID_FIELD,
		 * idDetalle)).toString(); try { int numeroSecuencia =
		 * DbSelectFns.selectLongInteger(conn, TABLE_NAME, NUMRENTREGA_FIELD
		 * .getName(), qual);
		 *
		 * DbUpdateFns.incrementField(conn, TABLE_NAME,
		 * NUMRENTREGA_FIELD.getName(), 1, qual .toString());
		 *
		 * siguienteNumero = numeroSecuencia++;
		 *
		 * } catch (Exception ieciE) {
		 * logger.error("Error incNRelacionesAsociadasADetalle "); }
		 *
		 * return siguienteNumero;
		 */
	}

	public int decNRelacionesAsociadasADetalle(final String idDetalle) {
		final MutableInt nRelaciones = new MutableInt(0);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
						.generateEQTokenField(ID_FIELD, idDetalle));
				DbUpdateFns.incrementField(conn, TABLE_NAME,
						NUMRENTREGA_COLUMN_NAME, -1, qual.toString());
				nRelaciones.setValue(DbSelectFns.selectLongInteger(conn,
						TABLE_NAME, NUMRENTREGA_COLUMN_NAME, qual.toString()));
			}
		};
		command.execute();
		return nRelaciones.getValue();

		/*
		 * LUIS String qual = new StringBuffer().append(" WHERE ").append(
		 * TransferenciasDBUtil.generateEQTokenField(ID_FIELD,
		 * idDetalle)).toString();
		 *
		 * try { DbUpdateFns.incrementField(conn, TABLE_NAME,
		 * NUMRENTREGA_FIELD.getName(), -1, qual .toString());
		 *
		 * } catch (Exception ieciE) {
		 * logger.error("Error decNRelacionesAsociadasADetalle "); }
		 */
	}

	public boolean checkAllDetallesPrevisionCerrados(final String idPrevision) {
		final MutableInt nDetallesAbiertos = new MutableInt(0);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer qual = new StringBuffer(" WHERE ")
						.append(DBUtils.generateEQTokenField(
								ID_PREVISION_FIELD, idPrevision))
						.append(" AND ")
						.append(DBUtils.generateEQTokenField(
								FLAG_CERRADA_FIELD, Constants.FALSE_STRING));
				nDetallesAbiertos.setValue(DbSelectFns.selectCount(conn,
						TABLE_NAME, qual.toString()));
			}
		};
		command.execute();
		return nDetallesAbiertos.getValue() == 0;

		/*
		 * LUIS String qual = "WHERE "; qual = qual +
		 * TransferenciasDBUtil.generateANDTokens(new String[] {
		 * TransferenciasDBUtil.generateEQTokenField(ID_PREVISION_FIELD,
		 * idPrevision),
		 * TransferenciasDBUtil.generateEQTokenField(FLAG_CERRADA_FIELD,
		 * DBUtils.FALSE_STRING) });
		 *
		 * int numeroDetallesNoCerrados = DbSelectFns.selectCount(conn,
		 * TABLE_NAME, qual); return (numeroDetallesNoCerrados == 0);
		 */
	}

	public void cerrarDetalle(String idDetalle) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(ID_FIELD, idDetalle));
		Map colToUpdate = Collections.singletonMap(FLAG_CERRADA_FIELD,
				Constants.TRUE_STRING);
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);

		/*
		 * LUIS DbInputRecord aRow = new DbInputRecord() { public void
		 * setStatementValues(DbInputStatement stmt) throws Exception { int i =
		 * 1; stmt.setShortText(i++, "S"); } }; String qual =
		 * MessageFormat.format(ONE_ROW_QUAL, new String[] { idPrevision,
		 * idDetalle });
		 *
		 * DbUpdateFns.update(conn, TABLE_NAME, FLAG_CERRADA_COLUMN_NAME, aRow,
		 * qual);
		 */
	}

	/**
	 *
	 * @param idsUnidadesDocumentales
	 * @return
	 */
	public List getUInstXUdoc(List idsUnidadesDocumentales) {
		return getUInstXUdoc(idsUnidadesDocumentales, null);
	}

	public List getUInstXUdoc(List idsUnidadesDocumentales,
			List idsUnidadesExcluir) {

		/*
		 * SELECT A1.IDUINSTALACION,ASGDUINSTALACION.SIGNATURAUI,
		 * count(IDUNIDADDOC) AS UNIDADESVALIDAS, (SELECT COUNT(*) FROM
		 * ASGDUDOCENUI A2 WHERE A1.IDUINSTALACION=A2.IDUINSTALACION) AS
		 * UNIDADESTOTALES, ASGDUINSTALACION.IDFORMATO,AGFORMATO.NOMBRE FROM
		 * ASGDUDOCENUI A1,ASGDUINSTALACION WHERE ((A1.IDUNIDADDOC IN
		 * ('08d6c5af5d648000000000000000004d',
		 * '08d6c5b065fae000000000000000004d',
		 * '08d6c5b0a4155000000000000000004d',
		 * '08d6c5b08c479000000000000000004d'))) AND
		 * A1.IDUINSTALACION=ASGDUINSTALACION.ID AND ASGDUINSTALACION.IDFORMATO
		 * = AGFORMATO.ID GROUP BY A1.IDUINSTALACION,
		 * ASGDUINSTALACION.SIGNATURAUI, IDFORMATO, AGFORMATO.NOMBRE
		 */

		String alias1 = "A1";
		String alias2 = "A2";

		TableDef asgdudocenuiA1 = new TableDef(
				UDocEnUiDepositoDbEntityImpl.TABLE_NAME, alias1);
		TableDef asgdudocenuiA2 = new TableDef(
				UDocEnUiDepositoDbEntityImpl.TABLE_NAME, alias2);

		DbColumnDef idUinstalacionA1 = new DbColumnDef(asgdudocenuiA1,
				UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD);
		DbColumnDef idUinstalacionA2 = new DbColumnDef(asgdudocenuiA2,
				UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD);

		DbColumnDef idUnidadDoc = new DbColumnDef(asgdudocenuiA1,
				UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD);

		String tables = DBUtils.generateTableSet(new String[] {
				asgdudocenuiA1.getDeclaration(),
				UInstalacionDepositoDBEntity.TABLE_NAME,
				FormatoDBEntity.TABLE_NAME

		});

		StringBuffer qualCountAsgdudocenui = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateJoinCondition(idUinstalacionA1,
						idUinstalacionA2));

		StringBuffer sqlCountAsgdudocenui = new StringBuffer("(")
				.append(DbSelectStatement.getSelectStmtText(
						asgdudocenuiA2.getDeclaration(), DBUtils.COUNT_ALL,
						qualCountAsgdudocenui.toString(), false)).append(")")
				.append(" AS ").append(UNIDADES_TOTALES_COLUMN_NAME);

		StringBuffer sqlCountIdunidaddoc = new StringBuffer(
				DbUtil.getCountColumn(idUnidadDoc)).append(" AS ").append(
				UNIDADES_VALIDAS_COLUMN_NAME);

		DbColumnDef[] COLS = {
				idUinstalacionA1,
				UInstalacionDepositoDBEntity.SIGNATURAUI_FIELD,
				new DbColumnDef(UNIDADES_VALIDAS_COLUMN_NAME,
						sqlCountIdunidaddoc.toString(), DbDataType.SHORT_TEXT),
				new DbColumnDef(UNIDADES_TOTALES_COLUMN_NAME,
						sqlCountAsgdudocenui.toString(), DbDataType.SHORT_TEXT),
				UInstalacionDepositoDBEntity.IDFORMATO_FIELD,
				UInstalacionDepositoDBEntity.MARCAS_BLOQUEO_FIELD,
				new DbColumnDef(NOMBRE_FORMATO_COLUMN_NAME,
						FormatoDBEntity.CAMPO_NOMBRE) };

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateNotInTokenField(ID_FIELD,
						idsUnidadesExcluir))
				.append(DBUtils.generateInTokenField(idUnidadDoc,
						idsUnidadesDocumentales))
				.append(DBUtils.AND)
				.append(DBUtils.generateJoinCondition(idUinstalacionA1,
						UInstalacionDepositoDBEntity.ID_FIELD))
				.append(DBUtils.AND)
				.append(DBUtils.generateJoinCondition(
						UInstalacionDepositoDBEntity.IDFORMATO_FIELD,
						FormatoDBEntity.CAMPO_ID))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						UInstalacionDepositoDBEntity.MARCAS_BLOQUEO_FIELD, 0))
				.append(" GROUP BY ")
				.append(DbUtil.getColumnNames(idUinstalacionA1,
						UInstalacionDepositoDBEntity.SIGNATURAUI_FIELD,
						UInstalacionDepositoDBEntity.IDFORMATO_FIELD,
						FormatoDBEntity.CAMPO_NOMBRE,
						UInstalacionDepositoDBEntity.MARCAS_BLOQUEO_FIELD));

		qual.append(DBUtils
				.generateOrderBy(UInstalacionDepositoDBEntity.SIGNATURAUI_FIELD));

		return getVOS(qual.toString(), tables, COLS, UInsDepositoVO.class);

	}

	public DetallePrevisionVO getDetallePrevisionVO(DetallePrevisionVO detalle) {
		StringBuilder qual = new StringBuilder(DBUtils.WHERE)
		.append(DBUtils.generateEQTokenField(ID_PREVISION_FIELD, detalle.getIdPrevision()))
		.append(DBUtils.AND)
		.append(DBUtils.generateEQTokenField(SISTEMA_PRODUCTOR_FIELD, detalle.getCodSistProductor()))
		.append(DBUtils.AND)
		.append(DBUtils.generateEQTokenField(NOMBRE_SISTEMA_PRODUCTOR_FIELD, detalle.getNombreSistProductor()))
		.append(DBUtils.AND)
		.append(DBUtils.generateEQTokenField(PROCEDIMIENTO_FIELD , detalle.getIdProcedimiento()))
		.append(DBUtils.AND)
		.append(DBUtils.generateEQTokenField(ID_SERIEDESTINO_FIELD , detalle.getIdSerieDestino()));

		return getDetallePrevision(qual.toString());
	}

	public int getCountDetallesBySerie(String idSerie) {
		StringBuilder qual = new StringBuilder(DBUtils.WHERE)
		.append(DBUtils.generateEQTokenField(ID_SERIEDORIGEN_FIELD, idSerie))
		.append(DBUtils.OR)
		.append(DBUtils.generateEQTokenField(ID_SERIEDESTINO_FIELD, idSerie));

		return getVOCount(qual.toString(), TABLE_NAME);
	}


}