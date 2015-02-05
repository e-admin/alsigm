package transferencias.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInputStatement;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import transferencias.EstadoCotejo;
import transferencias.TipoUInstalacion;
import transferencias.model.EstadoREntrega;
import transferencias.vos.UbicacionUnidadInstalacionVO;
import transferencias.vos.UnidadInstalacionVO;

import common.Constants;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.JoinDefinition;
import common.db.SigiaDbInputRecord;
import common.db.TableDef;
import common.lang.MutableBoolean;
import common.util.IntervalOption;
import common.util.IntervalOptions;
import common.util.IntervalRangeOption;
import common.util.IntervalSimpleOption;
import common.util.StringUtils;

import deposito.db.oracle.HuecoDBEntityImpl;

/**
 * Clase con los metodos encargados de recuperar y actualizar la tabla en la que
 * se almacenan los datos de unidades de instalacion
 * 
 */
public class UnidadInstalacionDBEntityImpl extends DBEntity implements
		IUnidadInstalacionDBEntity {
	static Logger logger = Logger
			.getLogger(UnidadInstalacionDBEntityImpl.class);

	public static final String TABLE_NAME = "ASGTUINSTALACIONRE";

	public static final String ID_COLUMN_NAME = "id";
	public static final String ID_RELACION_COLUMN_NAME = "idrelentrega";
	public static final String ORDEN_COLUMN_NAME = "orden";
	public static final String FORMATO_COLUMN_NAME = "idformato";
	public static final String SIGNATURA_COLUMN_NAME = "signaturaui";
	public static final String ESTADO_COTEJO_COLUMN_NAME = "estadocotejo";
	public static final String NOTAS_COTEJO_COLUMN_NAME = "notascotejo";
	public static final String DEVOLUCION_COLUMN_NAME = "devolucion";
	public static final String ID_UBICADA_COLUMN_NAME = "iduiubicada";

	public static final DbColumnDef ID_FIELD = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, false);
	public static final DbColumnDef ID_RELACION_FIELD = new DbColumnDef(null,
			TABLE_NAME, ID_RELACION_COLUMN_NAME, DbDataType.SHORT_TEXT, 64,
			false);
	public static final DbColumnDef ORDEN_FIELD = new DbColumnDef(null,
			TABLE_NAME, ORDEN_COLUMN_NAME, DbDataType.LONG_INTEGER, false);
	public static final DbColumnDef ID_FORMATO_FIELD = new DbColumnDef(null,
			TABLE_NAME, FORMATO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef SIGNATURA_FIELD = new DbColumnDef(null,
			TABLE_NAME, SIGNATURA_COLUMN_NAME, DbDataType.SHORT_TEXT, 16, false);
	public static final DbColumnDef ESTADO_COTEJO_FIELD = new DbColumnDef(null,
			TABLE_NAME, ESTADO_COTEJO_COLUMN_NAME, DbDataType.LONG_INTEGER, 64,
			false);
	public static final DbColumnDef NOTAS_COTEJO_FIELD = new DbColumnDef(null,
			TABLE_NAME, NOTAS_COTEJO_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			false);
	public static final DbColumnDef DEVOLUCION_FIELD = new DbColumnDef(null,
			TABLE_NAME, DEVOLUCION_COLUMN_NAME, DbDataType.SHORT_TEXT, 1, false);
	public static final DbColumnDef ID_UBICADA_FIELD = new DbColumnDef(null,
			TABLE_NAME, ID_UBICADA_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef[] TABLE_COLUMNS = { ID_FIELD,
			ID_RELACION_FIELD, ORDEN_FIELD, SIGNATURA_FIELD,
			ESTADO_COTEJO_FIELD, NOTAS_COTEJO_FIELD, DEVOLUCION_FIELD,
			ID_FORMATO_FIELD, ID_UBICADA_FIELD };
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

	public UnidadInstalacionDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public UnidadInstalacionDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Inserta una nueva fila en la tabla de unidades de instalacion
	 * 
	 * @param conn
	 *            Conexion a la base de datos
	 * @param unidadInstalacion
	 *            Datos de la unidad de instalacion a insertar
	 * @throws Exception
	 */
	public void insertRow(final UnidadInstalacionVO unidadInstalacion) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				unidadInstalacion.setId(getGuid(unidadInstalacion.getId()));
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						TABLE_COLUMNS, unidadInstalacion);
				DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
						inputRecord);
			}
		};
		command.execute();
	}

	/**
	 * Elimina una unidad de instalacion de la base de datos
	 * 
	 * @param unidadInstalacion
	 *            Unidad de instalacion a eliminar
	 */
	public void dropRow(final UnidadInstalacionVO unidadInstalacion) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer qual = new StringBuffer().append("where ").append(
						DBUtils.generateEQTokenField(ID_FIELD,
								unidadInstalacion.getId()));
				DbDeleteFns.delete(conn, TABLE_NAME, qual.toString());
				qual.setLength(0);
				// Una vez eliminada la entrada es necesario actualizar el
				// numero de orden de las unidades de instalacion
				// que estaban a continuacion de la eliminada
				qual.append("where ")
						.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
								unidadInstalacion.getIdRelEntrega()))
						.append(" and ")
						.append(DBUtils.generateGTTokenField(ORDEN_FIELD,
								unidadInstalacion.getOrden()));
				DbUpdateFns.incrementField(conn, TABLE_NAME, ORDEN_COLUMN_NAME,
						-1, qual.toString());
			}
		};
		command.execute();
	}

	private List getUnidadesInstalacion(String qual) {
		return getVOS(qual, TABLE_NAME, TABLE_COLUMNS,
				UnidadInstalacionVO.class);
	}

	private UnidadInstalacionVO getUnidadInstalacion(String qual) {
		return (UnidadInstalacionVO) getVO(qual, TABLE_NAME, TABLE_COLUMNS,
				UnidadInstalacionVO.class);
	}

	/**
	 * Obtiene las unidades de instalacion pertenecientes a una relacion de
	 * entrega
	 * 
	 * @param conn
	 *            Conexion a la base de datos
	 * @param idRelacion
	 *            Identificador de la relacion de entega
	 * @return lista con las unidades documentales pertenecientes a la relacion
	 *         de entrega
	 * @throws Exception
	 */
	public List fetchRowsByIdRelacion(String idRelacion) {
		/*
		 * StringBuffer qual = new StringBuffer("WHERE ")
		 * .append(DBUtils.generateEQTokenField(ID_RELACION_FIELD, idRelacion))
		 * .append("order by ").append(ORDEN_COLUMN_NAME); return
		 * getUnidadesInstalacion(qual.toString());
		 */
		return fetchRowsByIdRelacion(idRelacion,
				TipoUInstalacion.ALL.getIdentificador());
		/*
		 * LUIS String qual = new
		 * StringBuffer().append("WHERE ").append(ID_RELACION_COLUMN_NAME)
		 * .append("='").append(idRelacion).append("' ")
		 * .append("order by ").append(ORDEN_COLUMN_NAME).toString(); final
		 * ArrayList rows = new ArrayList(); DbOutputRecordSet rowSet = new
		 * AbstractDbOutputRecordSet() { DbOutputRecord aRow = new
		 * DbOutputRecord() { public void getStatementValues(DbOutputStatement
		 * stmt) throws Exception { UnidadInstalacion unidadInstalacion =
		 * getUInstalacionFilled(stmt); rows.add(unidadInstalacion); } }; public
		 * DbOutputRecord newRecord() throws Exception { return aRow; } };
		 * DbSelectFns
		 * .select(conn,TABLE_NAME,COLUM_NAMES_LIST,qual,false,rowSet); return
		 * rows.size()>0?rows:null;
		 */
	}

	/*
	 * public List fetchRowsByIdRelacion(String idRelacion, boolean asignado) {
	 * StringBuffer qual = new StringBuffer("WHERE ")
	 * .append(DBUtils.generateEQTokenField(ID_RELACION_FIELD, idRelacion));
	 * if(!asignado){ qual.append("order by ").append(ORDEN_COLUMN_NAME); return
	 * getUnidadesInstalacion(qual.toString()); } else{
	 * qual.append(" and ").append(ID_UBICADA_FIELD).append(" IS NULL");
	 * qual.append(" order by ").append(ORDEN_COLUMN_NAME); return
	 * getUnidadesInstalacion(qual.toString()); } }
	 */

	public List fetchRowsByIdRelacion(String idRelacion, int tipoUInstalacion) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(ID_RELACION_FIELD, idRelacion));
		if (TipoUInstalacion.ASIGNADAS.getIdentificador() == tipoUInstalacion) {
			qual.append(" and ").append(ID_UBICADA_FIELD)
					.append(" IS NOT NULL");
			qual.append(" order by ").append(ORDEN_COLUMN_NAME);
			return getUnidadesInstalacion(qual.toString());
		} else if (TipoUInstalacion.CREADAS.getIdentificador() == tipoUInstalacion) {
			qual.append(" and ").append(ID_UBICADA_FIELD).append(" IS NULL");
			qual.append(" order by ").append(ORDEN_COLUMN_NAME);
			return getUnidadesInstalacion(qual.toString());
		} else {
			qual.append("order by ").append(ORDEN_COLUMN_NAME);
			return getUnidadesInstalacion(qual.toString());
		}
	}

	/**
	 * Obtiene las unidades de instalacion de una relacion de entrega.
	 * 
	 * @param idRelacion
	 *            Identificador de relacion de entrega
	 * @param ordenes
	 *            Órdenes.
	 * @return Lista de unidades de instalacion {@link UnidadInstalacionVO}
	 */
	public List fetchRowsByIdRelacion(String idRelacion, IntervalOptions ordenes) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(ID_RELACION_FIELD, idRelacion));

		if ((ordenes != null) && !ordenes.isAllItems()
				&& (ordenes.getOptions().size() > 0)) {
			IntervalOption intOpt;

			qual.append(" AND (");

			for (int i = 0; i < ordenes.getOptions().size(); i++) {
				intOpt = (IntervalOption) ordenes.getOptions().get(i);

				if (i > 0)
					qual.append(" OR ");

				if (intOpt.getType() == IntervalOption.SIMPLE_ITEM)
					qual.append(DBUtils.generateEQTokenField(ORDEN_FIELD,
							((IntervalSimpleOption) intOpt).getItem()));
				else // if (intOpt.getType() == IntervalOption.ITEM_RANGE)
				{
					qual.append("(");

					if (StringUtils.isNotBlank(((IntervalRangeOption) intOpt)
							.getMinItem()))
						qual.append(DBUtils.generateGTEQTokenField(ORDEN_FIELD,
								((IntervalRangeOption) intOpt).getMinItem()));

					if (StringUtils.isNotBlank(((IntervalRangeOption) intOpt)
							.getMaxItem())) {
						if (StringUtils
								.isNotBlank(((IntervalRangeOption) intOpt)
										.getMinItem()))
							qual.append(" AND ");

						qual.append(DBUtils.generateLTEQTokenField(ORDEN_FIELD,
								((IntervalRangeOption) intOpt).getMaxItem()));
					}

					qual.append(")");
				}
			}

			qual.append(")");
		}

		qual.append(" ORDER BY ").append(ORDEN_COLUMN_NAME);

		return getUnidadesInstalacion(qual.toString());
	}

	/*
	 * LUIS public boolean checkIfTodasUnidadesInstalacionEnEstadosXIdRelacion(
	 * String idRelacion, int[] estados) { final MutableBoolean
	 * uisConMismoEstado = new MutableBoolean(false); DBCommand command = new
	 * DBCommand(this) { public void codeLogic(DbConnection conn) throws
	 * Exception { uisConMismoEstado.setValue( DbSelectFns.selectCount(conn,
	 * TABLE_NAME, qual.toString())); } }; command.execute(); return
	 * uisConMismoEstado.getValue(); String qual = new
	 * StringBuffer().append("WHERE NOT").append(ID_RELACION_COLUMN_NAME)
	 * .append("='").append(idRelacion).append("' ")
	 * .append(TransferenciasDBUtil.generateORTokens(ESTADO_COTEJO_FIELD,
	 * estados)) .append("order by ").append(ORDEN_COLUMN_NAME).toString();
	 * 
	 * return getRows(conn, qual).size()>0?true:false; }
	 */

	public boolean existUnidadesInstalacionEnEstadosXIdRelacion(
			final String idRelacion, final int[] estados) {
		final MutableBoolean uisEnEstado = new MutableBoolean(false);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer qual = new StringBuffer("WHERE ")
						.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
								idRelacion))
						.append(" AND ")
						.append(DBUtils.generateORTokens(ESTADO_COTEJO_FIELD,
								estados));
				uisEnEstado.setValue(DbSelectFns.selectCount(conn, TABLE_NAME,
						qual.toString()) > 0);
			}
		};
		command.execute();
		return uisEnEstado.getValue();

		/*
		 * LUIS String qual = new
		 * StringBuffer().append("WHERE ").append(ID_RELACION_COLUMN_NAME)
		 * .append("='").append(idRelacion).append("' ") .append(" AND ")
		 * .append(TransferenciasDBUtil.generateORTokens(ESTADO_COTEJO_FIELD,
		 * estados)) .append(" order by ").append(ORDEN_COLUMN_NAME).toString();
		 * 
		 * List rows = getRows(conn, qual); if (rows!=null && rows.size()>0)
		 * return true; return false;
		 */
	}

	/*
	 * LUIS static List getRows(DbConnection conn, String qual) throws
	 * Exception{ final ArrayList rows = new ArrayList(); DbOutputRecordSet
	 * rowSet = new AbstractDbOutputRecordSet() { DbOutputRecord aRow = new
	 * DbOutputRecord() { public void getStatementValues(DbOutputStatement stmt)
	 * throws Exception { UnidadInstalacion unidadInstalacion =
	 * getUInstalacionFilled(stmt); rows.add(unidadInstalacion); } }; public
	 * DbOutputRecord newRecord() throws Exception { return aRow; } };
	 * DbSelectFns.select(conn,TABLE_NAME, COLUM_NAMES_LIST,qual,false,rowSet);
	 * return rows.size()>0?rows:null; }
	 */

	public void updateFieldSignatura(String idUInstalacion, String signatura) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(ID_FIELD, idUInstalacion));
		Map colToUpdate = Collections.singletonMap(SIGNATURA_FIELD, signatura);
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
		/*
		 * LUIS String qual = new StringBuffer(" WHERE ")
		 * .append(TransferenciasDBUtil
		 * .generateEQTokenField(ID_FIELD,idUInstalacion)) .toString();
		 * 
		 * DbUpdateFns.update(conn,TABLE_NAME,SIGNATURA_COLUMN_NAME,new
		 * DbInputRecord() { public void setStatementValues(DbInputStatement
		 * stmt) throws Exception { stmt.setShortText(1, signatura); } },qual);
		 */
	}

	/**
	 * Actualiza el estado y flag de devolución de un conjunto de unidades de
	 * instalación.
	 * 
	 * @param idsUInstalacion
	 *            Lista de identificadores de unidad de instalacion
	 * @param estado
	 *            Nuevo estado al que se pasan la unidades de instalacion
	 * @param devuelta
	 *            Indica si las unidades de instalación están devueltas.
	 */
	public void updateFieldEstado(String[] idsUInstalacion, int estado,
			boolean devuelta) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateORTokens(ID_FIELD, idsUInstalacion));
		Map colToUpdate = new HashMap();
		colToUpdate.put(ESTADO_COTEJO_FIELD, String.valueOf(estado));
		colToUpdate.put(DEVOLUCION_FIELD, DBUtils.getStringValue(devuelta));
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}

	public void updateFieldEstado(String idUInstalacion, int estado) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(ID_FIELD, idUInstalacion));
		Map colToUpdate = Collections.singletonMap(ESTADO_COTEJO_FIELD,
				String.valueOf(estado));
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}

	/*
	 * LUIS METODO DUPLICADO public static void updateFieldEstado (DbConnection
	 * conn, String[] idsUInstalacion, int estado) throws Exception { String
	 * qual = new StringBuffer(" WHERE ")
	 * .append(TransferenciasDBUtil.generateORTokens
	 * (ID_FIELD,idsUInstalacion)).toString();
	 * 
	 * DbUpdateFns.updateLongInteger(conn, TABLE_NAME,
	 * ESTADO_COTEJO_COLUMN_NAME, estado, qual); }
	 */

	public void updateFieldEstadoYDevolver(String[] idsUInstalacion,
			int estado, String devolver) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateORTokens(ID_FIELD, idsUInstalacion));
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(ESTADO_COTEJO_FIELD, String.valueOf(estado));
		colsToUpdate.put(DEVOLUCION_FIELD, devolver);
		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);

		/*
		 * LUIS String qual = new StringBuffer(" WHERE ")
		 * .append(TransferenciasDBUtil
		 * .generateORTokens(ID_FIELD,idsUInstalacion)) .toString();
		 * 
		 * String colNames = ESTADO_COTEJO_COLUMN_NAME + "," +
		 * DEVOLUCION_COLUMN_NAME; DbInputRecord row =new DbInputRecord(){
		 * public void setStatementValues(DbInputStatement stmt) throws
		 * Exception { int i=1; stmt.setLongInteger(i++,estado);
		 * stmt.setLongText(i++,devolver); } };
		 * 
		 * DbUpdateFns.update(conn, TABLE_NAME, colNames, row, qual);
		 */
	}

	public void updateFieldEstadoYDevolverYNotasCotejo(String idUInstalacion,
			int estado, String devolver, String notasCotejo) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(ID_FIELD, idUInstalacion));
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(ESTADO_COTEJO_FIELD, String.valueOf(estado));
		colsToUpdate.put(DEVOLUCION_FIELD, devolver);
		colsToUpdate.put(NOTAS_COTEJO_FIELD, notasCotejo);
		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);

		/*
		 * LUIS String qual = new StringBuffer(" WHERE ")
		 * .append(TransferenciasDBUtil
		 * .generateEQTokenField(ID_FIELD,idUInstalacion)) .toString();
		 * 
		 * String colNames = ESTADO_COTEJO_COLUMN_NAME + "," +
		 * DEVOLUCION_COLUMN_NAME + "," + NOTAS_COTEJO_COLUMN_NAME;
		 * DbInputRecord row =new DbInputRecord(){ public void
		 * setStatementValues(DbInputStatement stmt) throws Exception { int i=1;
		 * stmt.setLongInteger(i++,estado); stmt.setLongText(i++,devolver);
		 * stmt.setLongText(i++,notasCotejo); } };
		 * 
		 * DbUpdateFns.update(conn, TABLE_NAME, colNames, row, qual);
		 */

	}

	public void deleteXIdRelacion(final String idRelacion) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				final StringBuffer qual = new StringBuffer("WHERE ")
						.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
								idRelacion));
				DbDeleteFns
						.delete(getConnection(), TABLE_NAME, qual.toString());
			}
		};
		command.execute();

		/*
		 * LUIS DbDeleteFns.delete(conn ,TABLE_NAME, "WHERE " +
		 * TransferenciasDBUtil
		 * .generateEQTokenField(ID_RELACION_FIELD,idRelacion));
		 */
	}

	/*
	 * public static void deleteNotNeeded(DbConnection conn, String
	 * codigoRelacion, int ultimaCajaUsada) throws Exception { String qual = new
	 * StringBuffer().append("WHERE ").append(CODIGO_RELACION_COLUMN_NAME)
	 * .append
	 * ("='").append(codigoRelacion).append("' AND ").append(NUMERO_CAJA_COLUMN_NAME
	 * ) .append(">").append(ultimaCajaUsada).toString();
	 * DbDeleteFns.delete(conn ,TABLE_NAME, qual); }
	 */

	public void setErroresCorregidos(String idRelacion) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
						idRelacion))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(ESTADO_COTEJO_FIELD,
						EstadoCotejo.ERRORES.getIdentificador()));
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(ESTADO_COTEJO_FIELD,
				String.valueOf(EstadoCotejo.PENDIENTE.getIdentificador()));
		colsToUpdate.put(DEVOLUCION_FIELD, Constants.FALSE_STRING);
		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);

		/*
		 * LUIS String sql = new
		 * StringBuffer().append("update ").append(TABLE_NAME)
		 * .append(" set ").append(ESTADO_COTEJO_COLUMN_NAME)
		 * .append("=").append(EstadoCotejo.PENDIENTE.getIdentificador())
		 * .append(", ").append(DEVOLUCION_COLUMN_NAME).append("='N'")
		 * .append(" where ").append(ID_RELACION_COLUMN_NAME).append("=?")
		 * .append(" and ").append(ESTADO_COTEJO_COLUMN_NAME).append("=?")
		 * .toString();
		 * 
		 * DbInputStatement stmt = new DbInputStatement();
		 * stmt.create(conn,sql); stmt.setShortText(1,idRelacion);
		 * stmt.setLongInteger(2,EstadoCotejo.ERRORES.getIdentificador());
		 * stmt.execute(); stmt.release();
		 */
	}

	public List getUnidadesInstalacionVacias(String idRelacion) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
						idRelacion))
				.append(" AND ")
				.append(ID_COLUMN_NAME)
				.append(" NOT IN ( SELECT DISTINCT ")
				.append(UdocEnUIDBEntityImpl.UINSTALACION_COLUMN_NAME)
				.append(" FROM ")
				.append(UdocEnUIDBEntityImpl.TABLE_NAME)
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(
						UdocEnUIDBEntityImpl.ID_RELACION_FIELD, idRelacion))
				.append(" )");
		return getUnidadesInstalacion(qual.toString());
	}

	public UnidadInstalacionVO fetchRowById(String idUinstalacion) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(ID_FIELD, idUinstalacion));
		return getUnidadInstalacion(qual.toString());
	}

	/**
	 * Obtiene las ubicaciones de las unidades de instalación de una relación de
	 * entrega.
	 * 
	 * @param idRelacion
	 *            Identificador de la relación de entrega.
	 * @return Lista de ubicaciones.
	 */
	public List getUbicacionesRelacion(String idRelacion) {

		/**
		 * Cambiado por esta query para multiarchivo SELECT
		 * asgdhueco.path,caja.signaturaui,asgdhueco.orden FROM asgdhueco,
		 * (SELECT asgtuinstalacionre.signaturaui, asgtuinstalacionre.id FROM
		 * asgtuinstalacionre WHERE asgtuinstalacionre.idrelentrega=
		 * '08efd62651091000000000000000001f' UNION SELECT
		 * asgtuinstalacionreea.signaturaui, asgtuinstalacionreea.iduideposito
		 * id FROM asgtuinstalacionreea WHERE asgtuinstalacionreea.idrelentrega=
		 * '08efd62651091000000000000000001f' )caja WHERE caja.id =
		 * asgdhueco.iduinstalacion
		 */

		StringBuffer sql = new StringBuffer(" SELECT ")
				.append(new DbColumnDef(new TableDef("CAJA"), SIGNATURA_FIELD)
						.getQualifiedName())
				.append(", ")
				.append(HuecoDBEntityImpl.PATH_COLUMN_NAME)
				.append(", ")
				.append(HuecoDBEntityImpl.CODORDEN_COLUMN_NAME)
				.append(" FROM ")
				.append(HuecoDBEntityImpl.TABLE_NAME)
				.append(", ")
				.append("(SELECT ")
				.append(UnidadInstalacionDBEntityImpl.SIGNATURA_COLUMN_NAME)
				.append(", ")
				.append(UnidadInstalacionDBEntityImpl.ID_COLUMN_NAME)
				.append(" AS ")
				.append(UnidadInstalacionReeaDBEntityImpl.ID_UIDEPOSITO_COLUMNNAME)
				.append(" FROM ")
				.append(UnidadInstalacionDBEntityImpl.TABLE_NAME)
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
						idRelacion))
				.append(DBUtils.UNION)
				.append(" SELECT ")
				.append(UnidadInstalacionReeaDBEntityImpl.SIGNATURA_COLUMN_NAME)
				.append(", ")
				.append(UnidadInstalacionReeaDBEntityImpl.ID_UIDEPOSITO_COLUMNNAME)
				.append(" FROM ")
				.append(UnidadInstalacionReeaDBEntityImpl.TABLE_NAME)
				.append(" WHERE ")
				.append(DBUtils
						.generateEQTokenField(
								UnidadInstalacionReeaDBEntityImpl.ID_RELACIONENTREGA_FIELD,
								idRelacion))
				.append(") CAJA ")
				.append(" WHERE ")
				.append(DBUtils.generateJoinCondition(new DbColumnDef(
						new TableDef("CAJA"),
						UnidadInstalacionReeaDBEntityImpl.ID_UIDEPOSITO_FIELD),
						HuecoDBEntityImpl.CAMPO_IDUINSTALACION))
				.append(" ORDER BY ")
				.append(HuecoDBEntityImpl.CAMPO_ORDEN_EN_RELACION
						.getQualifiedName())
				.append(" , ")
				.append(new DbColumnDef(new TableDef("CAJA"), SIGNATURA_FIELD)
						.getQualifiedName());

		DbColumnDef[] cols = new DbColumnDef[] { SIGNATURA_FIELD,
				HuecoDBEntityImpl.CAMPO_PATH, HuecoDBEntityImpl.CAMPO_CODORDEN };

		return getVOS(cols, sql.toString(), UbicacionUnidadInstalacionVO.class);

		/*
		 * Anterior
		 * 
		 * StringBuffer qual = new StringBuffer(" WHERE ")
		 * .append(DBUtils.generateEQTokenField(ID_RELACION_FIELD, idRelacion))
		 * .append(" AND ") .append(DBUtils.generateJoinCondition(ID_FIELD,
		 * HuecoDBEntityImpl.CAMPO_IDUINSTALACION)) .append(" ORDER BY ")
		 * .append(HuecoDBEntityImpl.CAMPO_ORDEN_EN_RELACION.getQualifiedName())
		 * .append(" , ") .append(SIGNATURA_FIELD.getQualifiedName());
		 * 
		 * 
		 * Map pairs = new HashMap(); pairs.put(TABLE_NAME, new DbColumnDef[] {
		 * SIGNATURA_FIELD } ); pairs.put(HuecoDBEntityImpl.TABLE_NAME, new
		 * DbColumnDef[] { HuecoDBEntityImpl.CAMPO_PATH } );
		 * 
		 * return getVOS(qual.toString(), pairs,
		 * UbicacionUnidadInstalacionVO.class);
		 */
	}

	/**
	 * Obtiene las ubicaciones de las unidades de instalación de una relación de
	 * entrega.
	 * 
	 * @param idRelacion
	 *            Identificador de la relación de entrega.
	 * @return Lista de ubicaciones.
	 */
	public List getUbicacionesRelacionReencajado(String idRelacion) {

		/**
		 * SELECT CAJA.signaturaui, path, codorden FROM asgdhueco, ( SELECT
		 * signaturaui, iduideposito FROM asgtuireeacr WHERE
		 * asgtuireeacr.idrelentrega= '0c47ac0d5f63300ARCHIVOPRODU0005e' ) CAJA
		 * WHERE CAJA.iduideposito=asgdhueco.iduinstalacion ORDER BY
		 * asgdhueco.ordenenrelacion , CAJA.signaturaui
		 */

		StringBuffer sql = new StringBuffer(" SELECT ")
				.append(new DbColumnDef(new TableDef("CAJA"), SIGNATURA_FIELD)
						.getQualifiedName())
				.append(", ")
				.append(HuecoDBEntityImpl.PATH_COLUMN_NAME)
				.append(", ")
				.append(HuecoDBEntityImpl.CODORDEN_COLUMN_NAME)
				.append(" FROM ")
				.append(HuecoDBEntityImpl.TABLE_NAME)
				.append(", ")
				.append("(SELECT ")
				.append(UIReeaCRDBEntityImpl.SIGNATURAUI_FIELD)
				.append(", ")
				.append(UIReeaCRDBEntityImpl.IDUIDEPOSITO_COLUMN_NAME)
				.append(" FROM ")
				.append(UIReeaCRDBEntityImpl.TABLE_NAME)
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(
						UIReeaCRDBEntityImpl.IDRELENTREGA_FIELD, idRelacion))
				.append(") CAJA ")
				.append(" WHERE ")
				.append(DBUtils.generateJoinCondition(new DbColumnDef(
						new TableDef("CAJA"),
						UnidadInstalacionReeaDBEntityImpl.ID_UIDEPOSITO_FIELD),
						HuecoDBEntityImpl.CAMPO_IDUINSTALACION))
				.append(" ORDER BY ")
				.append(HuecoDBEntityImpl.CAMPO_ORDEN_EN_RELACION
						.getQualifiedName())
				.append(" , ")
				.append(new DbColumnDef(new TableDef("CAJA"), SIGNATURA_FIELD)
						.getQualifiedName());

		DbColumnDef[] cols = new DbColumnDef[] { SIGNATURA_FIELD,
				HuecoDBEntityImpl.CAMPO_PATH, HuecoDBEntityImpl.CAMPO_CODORDEN };

		return getVOS(cols, sql.toString(), UbicacionUnidadInstalacionVO.class);

		/*
		 * Anterior
		 * 
		 * StringBuffer qual = new StringBuffer(" WHERE ")
		 * .append(DBUtils.generateEQTokenField(ID_RELACION_FIELD, idRelacion))
		 * .append(" AND ") .append(DBUtils.generateJoinCondition(ID_FIELD,
		 * HuecoDBEntityImpl.CAMPO_IDUINSTALACION)) .append(" ORDER BY ")
		 * .append(HuecoDBEntityImpl.CAMPO_ORDEN_EN_RELACION.getQualifiedName())
		 * .append(" , ") .append(SIGNATURA_FIELD.getQualifiedName());
		 * 
		 * 
		 * Map pairs = new HashMap(); pairs.put(TABLE_NAME, new DbColumnDef[] {
		 * SIGNATURA_FIELD } ); pairs.put(HuecoDBEntityImpl.TABLE_NAME, new
		 * DbColumnDef[] { HuecoDBEntityImpl.CAMPO_PATH } );
		 * 
		 * return getVOS(qual.toString(), pairs,
		 * UbicacionUnidadInstalacionVO.class);
		 */
	}

	public String generateOrdenesWhereCondition(IntervalOptions ordenes,
			DbColumnDef columnaOrden) {
		StringBuffer qual = new StringBuffer("");

		if ((ordenes != null) && !ordenes.isAllItems()
				&& (ordenes.getOptions().size() > 0)) {
			IntervalOption intOpt;

			qual.append("(");

			for (int i = 0; i < ordenes.getOptions().size(); i++) {
				intOpt = (IntervalOption) ordenes.getOptions().get(i);

				if (i > 0)
					qual.append(" OR ");

				if (intOpt.getType() == IntervalOption.SIMPLE_ITEM)
					qual.append(DBUtils.generateEQTokenField(columnaOrden,
							((IntervalSimpleOption) intOpt).getItem()));
				else // if (intOpt.getType() == IntervalOption.ITEM_RANGE)
				{
					qual.append("(");

					if (StringUtils.isNotBlank(((IntervalRangeOption) intOpt)
							.getMinItem()))
						qual.append(DBUtils.generateGTEQTokenField(
								columnaOrden,
								((IntervalRangeOption) intOpt).getMinItem()));

					if (StringUtils.isNotBlank(((IntervalRangeOption) intOpt)
							.getMaxItem())) {
						if (StringUtils
								.isNotBlank(((IntervalRangeOption) intOpt)
										.getMinItem()))
							qual.append(" AND ");

						qual.append(DBUtils.generateLTEQTokenField(
								columnaOrden,
								((IntervalRangeOption) intOpt).getMaxItem()));
					}

					qual.append(")");
				}
			}
			qual.append(")");
		}
		return qual.toString();
	}

	/**
	 * Obtiene las ubicaciones de las unidades de instalación de una relación de
	 * entrega. Realiza un outer join con hueco para que devuelva registros
	 * aunque no este ubicada.
	 * 
	 * @param idRelacion
	 *            Identificador de la relación de entrega.
	 * @param ordenes
	 *            Intervalo de ordenes a generar
	 * @param idDepositoDestino
	 *            Identificador del Depósito destino
	 * @return Lista de ubicaciones.
	 */
	public List getUbicacionesRelacionOuterJoin(String idRelacion,
			IntervalOptions ordenes, String idDepositoDestino) {

		/**
		 * Cambiado por esta query para multiarchivo SELECT asgdhueco.path,
		 * caja.signaturaui, asgdhueco.orden, caja.orden, caja.iduideposito AS
		 * id, caja.iduiubicada AS iduiubicada FROM asgdhueco RIGTH OUTER JOIN
		 * (SELECT asgtuinstalacionre.signaturaui, asgtuinstalacionre.id AS
		 * iduideposito, orden, null AS iduiubicada FROM asgtuinstalacionre
		 * WHERE asgtuinstalacionre.idrelentrega=
		 * '08efd62651091000000000000000001f' AND iduiubicada is null UNION
		 * SELECT asgtuinstalacionreea.signaturaui,
		 * asgtuinstalacionreea.iduideposito, orden, null AS iduiubicada FROM
		 * asgtuinstalacionreea WHERE asgtuinstalacionreea.idrelentrega=
		 * '08efd62651091000000000000000001f' UNION SELECT signaturaui,
		 * iduiubicada AS iduideposito, orden, iduiubicada AS iduiubicada FROM
		 * asgtuinstalacionre WHERE asgtuinstalacionre.idrelentrega=
		 * '08efd62651091000000000000000001f' AND iduiubicada is not null )caja
		 * ON caja.iduideposito = asgdhueco.iduinstalacion
		 */

		StringBuffer sql = new StringBuffer(" SELECT ")
				.append(new DbColumnDef(new TableDef("CAJA"), SIGNATURA_FIELD)
						.getQualifiedName())
				.append(", ")
				.append(HuecoDBEntityImpl.PATH_COLUMN_NAME)
				.append(", ")
				.append(HuecoDBEntityImpl.CODORDEN_COLUMN_NAME)
				.append(", ")
				.append(UnidadInstalacionDBEntityImpl.ORDEN_COLUMN_NAME)
				.append(", ")
				.append(new DbColumnDef(new TableDef("CAJA"),
						UnidadInstalacionReeaDBEntityImpl.ID_UIDEPOSITO_FIELD)
						.getQualifiedName()
						+ " AS " + ID_COLUMN_NAME)
				.append(", ")
				.append(new DbColumnDef(new TableDef("CAJA"), ID_UBICADA_FIELD)
						.getQualifiedName() + " AS " + ID_UBICADA_COLUMN_NAME)
				.append(" FROM ");

		String tabla1 = HuecoDBEntityImpl.TABLE_NAME;

		StringBuffer tabla2 = new StringBuffer("(SELECT ")
				.append(SIGNATURA_COLUMN_NAME)
				.append(", ")
				.append(ID_COLUMN_NAME)
				.append(" AS ")
				.append(UnidadInstalacionReeaDBEntityImpl.ID_UIDEPOSITO_COLUMNNAME)
				.append(", ")
				.append(ORDEN_COLUMN_NAME)
				.append(", ")
				.append(ID_UBICADA_COLUMN_NAME)
				.append(" FROM ")
				.append(TABLE_NAME)
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
						idRelacion));

		String cadOrdenes = generateOrdenesWhereCondition(ordenes, ORDEN_FIELD);
		if (!StringUtils.isEmpty(cadOrdenes))
			tabla2.append(" AND ").append(cadOrdenes);

		tabla2.append(DBUtils.UNION)
				.append(" SELECT ")
				.append(UnidadInstalacionReeaDBEntityImpl.SIGNATURA_FIELD
						.getQualifiedName())
				.append(", ")
				.append(UnidadInstalacionReeaDBEntityImpl.ID_UIDEPOSITO_FIELD
						.getQualifiedName())
				.append(", ")
				.append(UnidadInstalacionReeaDBEntityImpl.ORDEN_FIELD
						.getQualifiedName())
				.append(", ")
				.append("''")
				.append(" AS ")
				.append(ID_UBICADA_COLUMN_NAME)
				.append(" FROM ")
				.append(UnidadInstalacionReeaDBEntityImpl.TABLE_NAME)
				.append(" ")
				.append(UnidadInstalacionReeaDBEntityImpl.TABLE_NAME)
				.append(", ")
				.append(RelacionEntregaDBEntityBaseImpl.TABLE_NAME)
				.append(" ")
				.append(RelacionEntregaDBEntityBaseImpl.TABLE_NAME)
				.append(" WHERE ")
				.append(DBUtils
						.generateEQTokenField(
								UnidadInstalacionReeaDBEntityImpl.ID_RELACIONENTREGA_FIELD,
								idRelacion))
				.append(" AND ")
				.append(DBUtils
						.generateJoinCondition(
								UnidadInstalacionReeaDBEntityImpl.ID_RELACIONENTREGA_FIELD,
								RelacionEntregaDBEntityBaseImpl.CAMPO_ID))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						RelacionEntregaDBEntityBaseImpl.CAMPO_CONREENCAJADO,
						Constants.FALSE_STRING));

		cadOrdenes = generateOrdenesWhereCondition(ordenes,
				UnidadInstalacionReeaDBEntityImpl.ORDEN_FIELD);
		if (!StringUtils.isEmpty(cadOrdenes))
			tabla2.append(" AND ").append(cadOrdenes);

		tabla2.append(DBUtils.UNION)
				.append(" SELECT ")
				.append(UIReeaCRDBEntityImpl.SIGNATURAUI_FIELD
						.getQualifiedName())
				.append(", ")
				.append(UIReeaCRDBEntityImpl.ID_FIELD.getQualifiedName())
				.append(", ")
				.append(UIReeaCRDBEntityImpl.ORDEN_FIELD.getQualifiedName())
				.append(", ")
				.append(UIReeaCRDBEntityImpl.IDUIDEPOSITO_FIELD
						.getQualifiedName())
				.append(" AS ")
				.append(ID_UBICADA_COLUMN_NAME)
				.append(" FROM ")
				.append(UIReeaCRDBEntityImpl.TABLE_NAME)
				.append(" ")
				.append(UIReeaCRDBEntityImpl.TABLE_NAME)
				.append(", ")
				.append(RelacionEntregaDBEntityBaseImpl.TABLE_NAME)
				.append(" ")
				.append(RelacionEntregaDBEntityBaseImpl.TABLE_NAME)
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(
						UIReeaCRDBEntityImpl.IDRELENTREGA_FIELD, idRelacion))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						UIReeaCRDBEntityImpl.IDRELENTREGA_FIELD,
						RelacionEntregaDBEntityBaseImpl.CAMPO_ID))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						RelacionEntregaDBEntityBaseImpl.CAMPO_CONREENCAJADO,
						Constants.TRUE_STRING));

		cadOrdenes = generateOrdenesWhereCondition(ordenes,
				UIReeaCRDBEntityImpl.ORDEN_FIELD);
		if (!StringUtils.isEmpty(cadOrdenes))
			tabla2.append(" AND ").append(cadOrdenes);

		tabla2.append(") ").toString();

		DbColumnDef colIzq = new DbColumnDef(new TableDef(tabla1),
				HuecoDBEntityImpl.CAMPO_IDUINSTALACION);
		DbColumnDef colDer = new DbColumnDef(new TableDef(tabla2.toString(),
				"CAJA"), UnidadInstalacionReeaDBEntityImpl.ID_UIDEPOSITO_FIELD);
		JoinDefinition join = new JoinDefinition(colIzq, colDer);

		sql.append(
				DBUtils.generateOuterJoin(new JoinDefinition[] { join }, true))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						HuecoDBEntityImpl.CAMPO_IDDEPOSITO, idDepositoDestino))

				.append(" ORDER BY ")
				.append(HuecoDBEntityImpl.CAMPO_ORDEN_EN_RELACION
						.getQualifiedName())
				.append(" , ")
				.append(new DbColumnDef(new TableDef("CAJA"), SIGNATURA_FIELD)
						.getQualifiedName())
				.append(" , ")
				.append(new DbColumnDef(new TableDef("CAJA"), ORDEN_FIELD)
						.getQualifiedName());

		DbColumnDef[] cols = new DbColumnDef[] { SIGNATURA_FIELD,
				HuecoDBEntityImpl.CAMPO_PATH, HuecoDBEntityImpl.CAMPO_CODORDEN,
				ORDEN_FIELD, ID_FIELD, ID_UBICADA_FIELD };

		return getVOS(cols, sql.toString(), UbicacionUnidadInstalacionVO.class);
	}

	public int countUIsRelacion(String idRelacion) {
		/*
		 * StringBuffer qual = new StringBuffer("WHERE ")
		 * .append(DBUtils.generateEQTokenField(ID_RELACION_FIELD, idRelacion));
		 * return getVOCount(qual.toString(), TABLE_NAME);
		 */
		return countUIsRelacion(idRelacion, false);
	}

	public int countUIsRelacion(String idRelacion, boolean asignando) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(ID_RELACION_FIELD, idRelacion));
		if (!asignando)
			return getVOCount(qual.toString(), TABLE_NAME);
		else {
			qual.append(" AND ").append(ID_UBICADA_FIELD).append(" IS NULL");
			return getVOCount(qual.toString(), TABLE_NAME);
		}
	}

	public int countUIsAsignadasRelacion(String idRelacion) {
		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
						idRelacion)).append(" AND ").append(ID_UBICADA_FIELD)
				.append(" IS NOT NULL");
		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * transferencias.db.IUnidadInstalacionDBEntity#fetchRowBySignatura(java
	 * .lang.String)
	 */
	public UnidadInstalacionVO fetchRowBySignatura(String signatura) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(SIGNATURA_FIELD, signatura));
		return getUnidadInstalacion(qual.toString());
	}

	/*
	 * Obtiene la unidad de instalacion
	 */
	public UnidadInstalacionVO fetchRowBySignaturaRE(String idRelacion,
			String signatura) {
		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils
						.generateEQTokenField(SIGNATURA_FIELD, signatura))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
						idRelacion));
		return getUnidadInstalacion(qual.toString());
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * transferencias.db.IUnidadInstalacionDBEntity#fetchRowBySignaturaYArchivo
	 * (java.lang.String, java.lang.String)
	 */
	public UnidadInstalacionVO fetchRowBySignaturaYArchivo(String signatura,
			String idArchivo) {
		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils
						.generateEQTokenField(SIGNATURA_FIELD, signatura))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(ID_RELACION_FIELD,
						RelacionEntregaDBEntityBaseImpl.CAMPO_ID))
				.append(" AND ")
				.append(DBUtils
						.generateEQTokenField(
								RelacionEntregaDBEntityBaseImpl.CAMPO_IDARCHIVORECEPTOR,
								idArchivo));

		String tables = DBUtils.generateTableSet(new String[] { TABLE_NAME,
				RelacionEntregaDBEntityBaseImpl.TABLE_NAME });
		return (UnidadInstalacionVO) getVO(qual.toString(), tables,
				TABLE_COLUMNS, UnidadInstalacionVO.class);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * transferencias.db.IUnidadInstalacionDBEntity#maxOrdenUIsRelacion(java
	 * .lang.String)
	 */
	public int maxOrdenUIsRelacion(String idRelacion) {
		String qual = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(ID_RELACION_FIELD, idRelacion))
				.toString();
		return getMax(qual, TABLE_NAME, ORDEN_FIELD);
	}

	public boolean allUIsAsociadas(String idRelacion) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
						idRelacion)).append(" AND ").append(ID_UBICADA_FIELD)
				.append(" IS NOT NULL");

		return getVOCount(qual.toString(), TABLE_NAME) == countUIsRelacion(idRelacion) ? true
				: false;
	}

	/**
	 * Actualiza el orden de las unidades documentales pertenecientes a la
	 * relacion de entrega
	 * 
	 * @param idRelacionEntrega
	 * @param orden
	 */
	public void incrementOrdenUi(String idRelacionEntrega, int orden,
			int incremento) {
		final StringBuffer query = new StringBuffer(" UPDATE ")
				.append(TABLE_NAME)
				.append(" SET ")
				.append(ORDEN_COLUMN_NAME)
				.append(" = ")
				.append(ORDEN_COLUMN_NAME)
				.append(" + ")
				.append(incremento)
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
						idRelacionEntrega)).append(" AND ")
				.append(DBUtils.generateGTEQTokenField(ORDEN_FIELD, orden));
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbInputStatement stmt = new DbInputStatement();
				stmt.create(conn, query.toString());
				stmt.execute();
			}
		};
		command.execute();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUnidadInstalacionDBEntity#fetchRowBySignaturaYArchivoEnRENoFinalizada(java.lang.String,
	 *      java.lang.String)
	 */
	public UnidadInstalacionVO fetchRowBySignaturaYArchivoEnRENoValidada(
			String signatura, String idArchivo) {

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils
						.generateEQTokenField(SIGNATURA_FIELD, signatura))
				.append(DBUtils.AND)
				.append(DBUtils.generateJoinCondition(ID_RELACION_FIELD,
						RelacionEntregaDBEntityBaseImpl.CAMPO_ID))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(
								RelacionEntregaDBEntityBaseImpl.CAMPO_IDARCHIVORECEPTOR,
								idArchivo))
				.append(DBUtils.AND)
				.append(DBUtils.generateNEQTokenField(
						RelacionEntregaDBEntityBaseImpl.CAMPO_ESTADO,
						EstadoREntrega.VALIDADA.getIdentificador()));

		String tables = DBUtils.generateTableSet(new String[] { TABLE_NAME,
				RelacionEntregaDBEntityBaseImpl.TABLE_NAME });
		return (UnidadInstalacionVO) getVO(qual.toString(), tables,
				TABLE_COLUMNS, UnidadInstalacionVO.class);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUnidadInstalacionDBEntity#fetchRowBySignaturaYArchivoEnRENoFinalizada(java.lang.String,
	 *      java.lang.String)
	 */
	public UnidadInstalacionVO fetchRowBySignaturaYArchivoEnRENoValidadaOtraRelacion(
			String signatura, String idArchivo, String idRelEntregaExcluir) {

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils
						.generateEQTokenField(SIGNATURA_FIELD, signatura))
				.append(DBUtils.AND)
				.append(DBUtils.generateJoinCondition(ID_RELACION_FIELD,
						RelacionEntregaDBEntityBaseImpl.CAMPO_ID))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(
								RelacionEntregaDBEntityBaseImpl.CAMPO_IDARCHIVORECEPTOR,
								idArchivo))
				.append(DBUtils.AND)
				.append(DBUtils.generateNEQTokenField(
						RelacionEntregaDBEntityBaseImpl.CAMPO_ESTADO,
						EstadoREntrega.VALIDADA.getIdentificador()))
				.append(DBUtils.AND)
				.append(DBUtils.generateNEQTokenField(ID_RELACION_FIELD,
						idRelEntregaExcluir));

		String tables = DBUtils.generateTableSet(new String[] { TABLE_NAME,
				RelacionEntregaDBEntityBaseImpl.TABLE_NAME });
		return (UnidadInstalacionVO) getVO(qual.toString(), tables,
				TABLE_COLUMNS, UnidadInstalacionVO.class);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUnidadInstalacionDBEntity#fetchRowBySignaturaYArchivoEnRENoFinalizada(java.lang.String,
	 *      java.lang.String)
	 */
	public UnidadInstalacionVO fetchRowBySignaturaEnRENoValidada(
			String signatura) {

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils
						.generateEQTokenField(SIGNATURA_FIELD, signatura))
				.append(DBUtils.AND)
				.append(DBUtils.generateJoinCondition(ID_RELACION_FIELD,
						RelacionEntregaDBEntityBaseImpl.CAMPO_ID))
				.append(DBUtils.AND)
				.append(DBUtils.generateNEQTokenField(
						RelacionEntregaDBEntityBaseImpl.CAMPO_ESTADO,
						EstadoREntrega.VALIDADA.getIdentificador()));

		String tables = DBUtils.generateTableSet(new String[] { TABLE_NAME,
				RelacionEntregaDBEntityBaseImpl.TABLE_NAME });
		return (UnidadInstalacionVO) getVO(qual.toString(), tables,
				TABLE_COLUMNS, UnidadInstalacionVO.class);

	}

	public UnidadInstalacionVO getUIByIdRelEntregaYOrden(String idRelacion,
			int orden) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
						idRelacion)).append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(ORDEN_FIELD, orden));

		return (UnidadInstalacionVO) getVO(qual.toString(), TABLE_NAME,
				TABLE_COLUMNS, UnidadInstalacionVO.class);
	}

	public UnidadInstalacionVO fetchRowBySignaturaEnRENoValidadaOtraRelacion(
			String signatura, String idRelacion) {

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils
						.generateEQTokenField(SIGNATURA_FIELD, signatura))
				.append(DBUtils.AND)
				.append(DBUtils.generateJoinCondition(ID_RELACION_FIELD,
						RelacionEntregaDBEntityBaseImpl.CAMPO_ID))
				.append(DBUtils.AND)
				.append(DBUtils.generateNEQTokenField(ID_RELACION_FIELD,
						idRelacion))

				.append(DBUtils.AND)
				.append(DBUtils.generateNEQTokenField(
						RelacionEntregaDBEntityBaseImpl.CAMPO_ESTADO,
						EstadoREntrega.VALIDADA.getIdentificador()));

		String tables = DBUtils.generateTableSet(new String[] { TABLE_NAME,
				RelacionEntregaDBEntityBaseImpl.TABLE_NAME });
		return (UnidadInstalacionVO) getVO(qual.toString(), tables,
				TABLE_COLUMNS, UnidadInstalacionVO.class);

	}
}