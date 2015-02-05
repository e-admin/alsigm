/**
 *
 */
package deposito.db;

import gcontrol.db.ArchivoDbEntityImpl;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.List;

import common.db.ConstraintType;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.JoinDefinition;
import common.db.SigiaDbInputRecord;
import common.db.TableDef;
import common.util.ArrayUtils;
import common.util.StringUtils;

import deposito.vos.BusquedaHistUInstDepositoVO;
import deposito.vos.HistUInstDepositoVO;

/**
 * @author lucas
 *
 */
public class HistUInstalacionDepositoDBEntity extends DBEntity implements
		IHistUInstalacionDepositoDBEntity {

	public static final String TABLE_NAME = "ASGDHISTUINSTALACION";

	public static final String ID_COLUMN_NAME = "ID";
	public static final String IDARCHIVO_COLUMN_NAME = "IDARCHIVO";
	public static final String IDFORMATO_COLUMN_NAME = "IDFORMATO";
	public static final String SIGNATURAUI_COLUMN_NAME = "SIGNATURAUI";
	public static final String IDENTIFICACION_COLUMN_NAME = "IDENTIFICACION";
	public static final String FECHA_ELIMINACION_COLUMN_NAME = "FELIMINACION";
	public static final String MOTIVO_COLUMN_NAME = "MOTIVO";

	public static final DbColumnDef ID_FIELD = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef IDARCHIVO_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDARCHIVO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef IDFORMATO_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDFORMATO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef SIGNATURAUI_FIELD = new DbColumnDef(null,
			TABLE_NAME, SIGNATURAUI_COLUMN_NAME, DbDataType.SHORT_TEXT, 16,
			false);

	public static final DbColumnDef IDENTIFICACION_FIELD = new DbColumnDef(
			null, TABLE_NAME, IDENTIFICACION_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 254, false);

	public static final DbColumnDef FECHA_ELIMINACION_FIELD = new DbColumnDef(
			null, TABLE_NAME, FECHA_ELIMINACION_COLUMN_NAME,
			DbDataType.DATE_TIME, false);

	public static final DbColumnDef MOTIVO_FIELD = new DbColumnDef(null,
			TABLE_NAME, MOTIVO_COLUMN_NAME, DbDataType.SHORT_INTEGER, false);

	public static final DbColumnDef[] TABLE_COLUMNS = { ID_FIELD,
			IDARCHIVO_FIELD, IDFORMATO_FIELD, SIGNATURAUI_FIELD,
			IDENTIFICACION_FIELD, MOTIVO_FIELD, FECHA_ELIMINACION_FIELD };

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

	public HistUInstalacionDepositoDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public HistUInstalacionDepositoDBEntity(DbConnection conn) {
		super(conn);
	}

	private String getQualById(String id, boolean addWhere) {
		StringBuffer qual = new StringBuffer("");

		if (addWhere) {
			qual.append(DBUtils.WHERE);
		}

		qual.append(DBUtils.generateEQTokenField(ID_FIELD, id));

		return qual.toString();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * deposito.db.IHistUInstalacionDepositoDBEntity#getById(java.lang.String)
	 */
	public HistUInstDepositoVO getById(String id) {
		return (HistUInstDepositoVO) getVO(getQualById(id, true), TABLE_NAME,
				TABLE_COLUMNS, HistUInstDepositoVO.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see deposito.db.IHistUInstalacionDepositoDBEntity#insert(deposito.vos.
	 * HistUInstDepositoVO)
	 */
	public void insert(final HistUInstDepositoVO histUInstDepositoVO) {
		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						TABLE_COLUMNS, histUInstDepositoVO);
				DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
						inputRecord);
			}
		};
		command.execute();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see deposito.db.IHistUInstalacionDepositoDBEntity#list(deposito.vos.
	 * HistUInstDepositoVO)
	 */
	@SuppressWarnings("unused")
	public List find(BusquedaHistUInstDepositoVO busquedaHistUInstDepositoVO) {
		/*
		 * SELECT A.ID,A.IDFORMATO,B.NOMBRE,A.SIGNATURAUI,
		 * A.IDENTIFICACION,A.FELIMINACION,A.MOTIVO FROM
		 * dbo.ASGDHISTUINSTALACION A LEFT OUTER JOIN AGFORMATO B ON A.IDFORMATO
		 * = B.ID
		 */

		// Comprobacion de que se ha seleccionado archivo/s y motivo/s
		if (ArrayUtils.isEmpty(busquedaHistUInstDepositoVO.getMotivos())
				|| ArrayUtils
						.isEmpty(busquedaHistUInstDepositoVO.getArchivos())) {
			return null;
		}

		TableDef tablaFormatos = new TableDef(FormatoDBEntity.TABLE_NAME);
		TableDef tablaArchivos = new TableDef(ArchivoDbEntityImpl.TABLE_NAME);

		JoinDefinition joinFormatos = new JoinDefinition(new DbColumnDef(
				new TableDef(TABLE_NAME), IDFORMATO_FIELD), new DbColumnDef(
				tablaFormatos, FormatoDBEntity.CAMPO_ID));

		JoinDefinition joinArchivos = new JoinDefinition(new DbColumnDef(
				new TableDef(TABLE_NAME), IDARCHIVO_FIELD), new DbColumnDef(
				tablaArchivos, ArchivoDbEntityImpl.ID_FIELD));

		JoinDefinition[] joins = new JoinDefinition[] { joinFormatos,
				joinArchivos };

		StringBuffer sqlFrom = new StringBuffer();
		sqlFrom.append(DBUtils.generateLeftOuterJoinCondition(new TableDef(
				TABLE_NAME), joins));

		DbColumnDef columnaNombreFormato = new DbColumnDef("nombreFormato",
				FormatoDBEntity.TABLE_NAME, FormatoDBEntity.CAMPO_NOMBRE);
		DbColumnDef columnaNombreArchivo = new DbColumnDef("nombreArchivo",
				ArchivoDbEntityImpl.TABLE_NAME,
				ArchivoDbEntityImpl.NOMBRE_FIELD);

		DbColumnDef[] COLS_DEF_QUERY = (DbColumnDef[]) ArrayUtils
				.concat(new DbColumnDef[] { columnaNombreArchivo,
						columnaNombreFormato }, TABLE_COLUMNS);

		if (busquedaHistUInstDepositoVO == null)
			return null;

		final StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateInTokenField(MOTIVO_FIELD,
						busquedaHistUInstDepositoVO.getMotivos()))
				.append(DBUtils.AND)
				.append(DBUtils.generateInTokenField(IDARCHIVO_FIELD,
						busquedaHistUInstDepositoVO.getArchivos()));

		if (StringUtils.isNotEmpty(busquedaHistUInstDepositoVO.getIdFormato())) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(IDFORMATO_FIELD,
							busquedaHistUInstDepositoVO.getIdFormato()));
		}

		if (StringUtils
				.isNotEmpty(busquedaHistUInstDepositoVO.getSignaturaUI())) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateLikeFieldQualified(SIGNATURAUI_FIELD,
							busquedaHistUInstDepositoVO.getSignaturaUI(),
							TABLE_NAME,
							busquedaHistUInstDepositoVO.getSignatura_like(),
							true));
		}

		// if(StringUtils.isNotEmpty(busquedaHistUInstDepositoVO.getIdentificacion())){
		// qual.append(DBUtils.AND)
		// .append(DBUtils.generateLikeFieldQualified(IDENTIFICACION_FIELD,
		// busquedaHistUInstDepositoVO.getIdentificacion(), TABLE_NAME,
		// busquedaHistUInstDepositoVO.getIdentificacion(), true));
		// }

		if (busquedaHistUInstDepositoVO.getFechaIni() != null) {
			qual.append(DBUtils.AND)
					.append("(")
					.append(DBUtils.generateTokenFieldDateAnioMesDia(
							getConnection(),
							FECHA_ELIMINACION_FIELD,
							ConstraintType
									.getSymbol(ConstraintType.GREATER_OR_EQUAL),
							busquedaHistUInstDepositoVO.getFechaIni()))
					.append(")");
		}

		if (busquedaHistUInstDepositoVO.getFechaFin() != null) {
			qual.append(DBUtils.AND)
					.append("(")
					.append(DBUtils.generateTokenFieldDateAnioMesDia(
							getConnection(), FECHA_ELIMINACION_FIELD,
							ConstraintType
									.getSymbol(ConstraintType.LESS_OR_EQUAL),
							busquedaHistUInstDepositoVO.getFechaFin()))
					.append(")");
		}

		// Añadir el Order By
		qual.append(DBUtils.generateOrderBy(SIGNATURAUI_FIELD));

		return getVOS(qual.toString(), sqlFrom.toString(), COLS_DEF_QUERY,
				BusquedaHistUInstDepositoVO.class);

	}

}
