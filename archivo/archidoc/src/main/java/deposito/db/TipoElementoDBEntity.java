package deposito.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.util.ArrayUtils;

import deposito.vos.TipoElementoVO;

public class TipoElementoDBEntity extends DBEntity implements
		ITipoElementoDBEntity {

	public static final String TABLE_NAME = "ASGDTIPOELEMENTO";

	public static final String ID_COLUMN_NAME = "ID";
	public static final String NOMBRE_COLUMN_NAME = "NOMBRE";
	public static final String NOMBREABREV_COLUMN_NAME = "NOMBREABREV";
	public static final String IDPADRE_COLUMN_NAME = "IDPADRE";
	public static final String ASIGNABLE_COLUMN_NAME = "ASIGNABLE";
	public static final String DESCRIPCION_COLUMN_NAME = "DESCRIPCION";
	public static final String CARACTERORDEN_COLUMN_NAME = "CARACTERORDEN";
	public static final String TIPOORD_COLUMN_NAME = "TIPOORD";

	public static final DbColumnDef CAMPO_ID = new DbColumnDef(ID_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_NOMBRE = new DbColumnDef(
			NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, false);

	public static final DbColumnDef CAMPO_NOMBREABREV = new DbColumnDef(
			"nombreAbreviado", NOMBREABREV_COLUMN_NAME, DbDataType.SHORT_TEXT,
			16, true);

	public static final DbColumnDef CAMPO_IDPADRE = new DbColumnDef(
			IDPADRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, true);

	public static final DbColumnDef CAMPO_ASIGNABLE = new DbColumnDef(
			ASIGNABLE_COLUMN_NAME, DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef CAMPO_DESCRIPCION = new DbColumnDef(
			DESCRIPCION_COLUMN_NAME, DbDataType.SHORT_TEXT, 254, true);

	public static final DbColumnDef CAMPO_CARACTERORDEN = new DbColumnDef(
			CARACTERORDEN_COLUMN_NAME, DbDataType.SHORT_TEXT, 1, true);

	public static final DbColumnDef CAMPO_TIPOORD = new DbColumnDef(
			TIPOORD_COLUMN_NAME, DbDataType.SHORT_INTEGER, 1, true);

	private static final DbColumnDef[] TABLE_COLUMNS = { CAMPO_ID,
			CAMPO_NOMBRE, CAMPO_NOMBREABREV, CAMPO_IDPADRE, CAMPO_ASIGNABLE,
			CAMPO_DESCRIPCION, CAMPO_CARACTERORDEN, CAMPO_TIPOORD };

	private static final DbColumnDef[] COLUMNS_TO_UPDATE = { CAMPO_NOMBRE,
			CAMPO_NOMBREABREV, CAMPO_IDPADRE, CAMPO_ASIGNABLE,
			CAMPO_DESCRIPCION, CAMPO_CARACTERORDEN, CAMPO_TIPOORD };

	protected static final String ALL_COLUMN_NAMES = DbUtil
			.getColumnNames(TABLE_COLUMNS);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public TipoElementoDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public TipoElementoDBEntity(DbConnection conn) {
		super(conn);
	}

	public List getTiposElemento(String idTipoElementoPadre, String[] exclude) {
		StringBuffer qual = new StringBuffer();
		if (StringUtils.isNotEmpty(idTipoElementoPadre)) {
			qual.append(DBUtils.WHERE).append(
					DBUtils.generateEQTokenField(CAMPO_IDPADRE,
							idTipoElementoPadre));
		}

		if (ArrayUtils.isNotEmpty(exclude)) {
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateNotInTokenField(CAMPO_ID, exclude));
		}

		qual.append(DBUtils

		.generateOrderBy(CAMPO_ID));

		return getVOS(qual.toString(), TABLE_NAME, TABLE_COLUMNS,
				TipoElementoVO.class);
	}

	public void deleteTipoElemento(String idTipoElemento) {
		deleteVO(ROW_QUAL_ID(idTipoElemento), TABLE_NAME);
	}

	public void insertTipoElemento(final TipoElementoVO tipoElemento) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				if (StringUtils.isEmpty(tipoElemento.getId())) {
					String id = getGuid(tipoElemento.getId());
					tipoElemento.setId(id);
				}
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						TABLE_COLUMNS, tipoElemento);
				DbInsertFns.insert(conn, TABLE_NAME, ALL_COLUMN_NAMES,
						inputRecord);
			}
		};
		command.execute();
	}

	public void updateTipoElemento(TipoElementoVO tipoElemento) {
		if (tipoElemento != null) {
			updateVO(ROW_QUAL_ID(tipoElemento.getId()), TABLE_NAME,
					COLUMNS_TO_UPDATE, tipoElemento);
		}
	}

	public TipoElementoVO getTipoElemento(String idTipoElemento) {
		return (TipoElementoVO) getVO(ROW_QUAL_ID(idTipoElemento), TABLE_NAME,
				TABLE_COLUMNS, TipoElementoVO.class);
	}

	public boolean hasChildsAsignables(String idTipoElemento) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDPADRE,
						idTipoElemento))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_ASIGNABLE,
						TipoElementoVO.TIPO_ASIGNABLE));
		;

		int numTipos = getVOCount(qual.toString(), TABLE_NAME);

		if (numTipos > 0)
			return true;

		return false;

	}

	public boolean hasChilds(String idTipoElemento) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_IDPADRE, idTipoElemento));

		int numTipos = getVOCount(qual.toString(), TABLE_NAME);

		if (numTipos > 0)
			return true;

		return false;

	}

	private String ROW_QUAL_ID(String idTipoElemento) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_ID, idTipoElemento));

		return qual.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see deposito.db.ITipoElementoDBEntity#getTipoElemento(deposito.vos.TipoElementoVO)
	 */
	public TipoElementoVO getTipoElemento(TipoElementoVO tipoElementoVO) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.ABRIR_PARENTESIS)

				.append(DBUtils.generateLikeFieldQualified(CAMPO_NOMBRE,
						tipoElementoVO.getNombre(), TABLE_NAME,
						DBUtils.IGUAL_LIKE, true))
				.append(DBUtils.OR)
				.append(DBUtils.generateLikeFieldQualified(CAMPO_NOMBREABREV,
						tipoElementoVO.getNombreAbreviado(), TABLE_NAME,
						DBUtils.IGUAL_LIKE, true))
				.append(DBUtils.OR)
				.append(DBUtils.ABRIR_PARENTESIS)
				.append(DBUtils.generateEQTokenField(CAMPO_IDPADRE,
						tipoElementoVO.getIdpadre()))
				.append(DBUtils.AND)
				.append(DBUtils.generateLikeFieldQualified(CAMPO_CARACTERORDEN,
						tipoElementoVO.getCaracterorden(), TABLE_NAME,
						DBUtils.IGUAL_LIKE, true))
				.append(DBUtils.CERRAR_PARENTESIS)
				.append(DBUtils.CERRAR_PARENTESIS);

		if (StringUtils.isNotEmpty(tipoElementoVO.getId())) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateNEQTokenField(CAMPO_ID,
							tipoElementoVO.getId()));
		}

		return (TipoElementoVO) getVO(qual.toString(), TABLE_NAME,
				TABLE_COLUMNS, TipoElementoVO.class);
	}
}
