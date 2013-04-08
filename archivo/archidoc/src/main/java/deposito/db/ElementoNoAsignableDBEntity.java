package deposito.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.List;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.util.ArrayUtils;

import deposito.vos.ElementoNoAsignableVO;

public abstract class ElementoNoAsignableDBEntity extends DBEntity implements
		IElementoNoAsignableDBEntity {

	// private static final Logger logger =
	// Logger.getLogger(ElementoNoAsignableDBEntity.class);

	public static final String TABLE_NAME = "ASGDELEMNOASIG";

	public static final String ID_COLUMN_NAME = "id";
	public static final String IDTIPOELEMENTO_COLUMN_NAME = "idtipoelemento";
	public static final String NUMORDEN_COLUMN_NAME = "numorden";
	public static final String IDDEPOSITO_COLUMN_NAME = "iddeposito";
	public static final String NOMBRE_COLUMN_NAME = "nombre";
	public static final String IDPADRE_COLUMN_NAME = "idpadre";
	public static final String MARCAS_COLUMN_NAME = "marcas";
	public static final String CODORDEN_COLUMN_NAME = "codorden";

	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_IDTIPOELEMENTO = new DbColumnDef(
			null, IDTIPOELEMENTO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_NUMORDEN = new DbColumnDef(null,
			NUMORDEN_COLUMN_NAME, DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef CAMPO_IDDEPOSITO = new DbColumnDef(null,
			IDDEPOSITO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_NOMBRE = new DbColumnDef(null,
			NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, false);

	public static final DbColumnDef CAMPO_IDPADRE = new DbColumnDef(null,
			IDPADRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, true);

	public static final DbColumnDef CAMPO_MARCAS = new DbColumnDef(null,
			MARCAS_COLUMN_NAME, DbDataType.LONG_INTEGER, true);

	public static final DbColumnDef CAMPO_CODORDEN = new DbColumnDef(null,
			CODORDEN_COLUMN_NAME, DbDataType.SHORT_TEXT, 50, false);

	protected static final DbColumnDef[] TABLE_COLUMNS = { CAMPO_ID,
			CAMPO_IDTIPOELEMENTO, CAMPO_NUMORDEN, CAMPO_IDDEPOSITO,
			CAMPO_NOMBRE, CAMPO_IDPADRE, CAMPO_MARCAS, CAMPO_CODORDEN };

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

	public ElementoNoAsignableDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public ElementoNoAsignableDBEntity(DbConnection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void insertElemento(final ElementoNoAsignableVO noAsignableVO) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				String idNoAsignable = getGuid(noAsignableVO.getId());
				noAsignableVO.setId(idNoAsignable);
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						TABLE_COLUMNS, noAsignableVO);
				DbInsertFns.insert(conn, TABLE_NAME,
						DbUtil.getColumnNames(TABLE_COLUMNS), inputRecord);
			}
		};
		command.execute();
	}

	public void updateNoAsignable(ElementoNoAsignableVO noAsignableVO) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, noAsignableVO.getId()));

		updateVO(qual.toString(), TABLE_NAME, TABLE_COLUMNS, noAsignableVO);
	}

	public void delete(String id) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, id));
		deleteVO(qual.toString(), TABLE_NAME);
	}

	public void delete(String[] ids) {
		if (!ArrayUtils.isEmpty(ids)) {
			StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
					.generateInTokenField(CAMPO_ID, ids));
			deleteVO(qual.toString(), TABLE_NAME);
		}
	}

	/**
	 * Recupera el n�mero de elementos no asignables que contiene una
	 * ubicaci�n
	 *
	 * @param idUbicacion
	 *            Identificador de ubicaci�n
	 * @return N�mero de elementos no asignables que forman parte de la
	 *         estructura en la que que se organiza la ubicaci�n
	 */
	public int getNumeroElementosUbicacion(String idUbicacion) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_IDDEPOSITO, idUbicacion));
		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see deposito.db.IElementoNoAsignableDBEntity#isNumOrdenEnUso(boolean,
	 *      java.lang.String, java.lang.String, int)
	 */
	public boolean isNumOrdenEnUso(boolean padreIsUbicacion, String idDeposito,
			String idElemPadre, String idTipoElemento, int numOrden) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_NUMORDEN, numOrden))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_IDDEPOSITO,
						idDeposito))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_IDTIPOELEMENTO,
						idTipoElemento));

		if (padreIsUbicacion) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateIsNullCondition(getConnection(),
							CAMPO_IDPADRE));
		} else {
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(CAMPO_IDPADRE, idElemPadre));
		}

		if (getVOCount(qual.toString(), TABLE_NAME) > 0)
			return true;
		return false;
	}

	public abstract List getIdsDescendientes(String idElemento);

	/**
	 * {@inheritDoc}
	 *
	 * @see deposito.db.IElementoNoAsignableDBEntity#getById(java.lang.String)
	 */
	public ElementoNoAsignableVO getById(String idNoAsignable) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_ID, idNoAsignable));
		return (ElementoNoAsignableVO) getVO(qual.toString(), TABLE_NAME,
				TABLE_COLUMNS, ElementoNoAsignableVO.class);
	}

	public int getCountByTipoElemento(String idTipoElemento) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_IDTIPOELEMENTO, idTipoElemento));
		return getVOCount(qual.toString(), TABLE_NAME);
	}
}
