/**
 *
 */
package fondos.db;

import fondos.vos.CatalogoTablaTemporalVO;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import common.Constants;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.util.DateUtils;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public class CatalogoTablasTemporalesDBEntityImpl extends DBEntity implements
		ICatalogoTablasTemporalesDBEntity {

	/*
	 * NUMERO smallint , NOMBRETABLA VARCHAR(16), IDUSUARIO VARCHAR(32), ESTADO
	 * smallint default 0, FECHA datetime,
	 */

	public static String TABLE_NAME = "ASGFCTLGTBLTMP";

	public static String ID_COLUMN_NAME = "ID";
	public static String NOMBRE_TABLA_COLUMN_NAME = "NOMBRETABLA";
	public static String IDUSUARIO_COLUMN_NAME = "IDUSUARIO";
	public static String ESTADO_COLUMN_NAME = "ESTADO";
	public static String FECHA_COLUMN_NAME = "FECHA";

	public static final DbColumnDef ID_FIELD = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_INTEGER, false);

	public static final DbColumnDef NOMBRE_TABLA_FIELD = new DbColumnDef(null,
			TABLE_NAME, NOMBRE_TABLA_COLUMN_NAME, DbDataType.SHORT_TEXT, 16,
			false);

	public static final DbColumnDef IDUSUARIO_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDUSUARIO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef ESTADO_FIELD = new DbColumnDef(null,
			TABLE_NAME, ESTADO_COLUMN_NAME, DbDataType.SHORT_INTEGER, false);

	public static final DbColumnDef FECHA_FIELD = new DbColumnDef(null,
			TABLE_NAME, FECHA_COLUMN_NAME, DbDataType.DATE_TIME, false);

	public static final DbColumnDef[] COLS_DEFS = new DbColumnDef[] { ID_FIELD,
			NOMBRE_TABLA_FIELD, IDUSUARIO_FIELD, ESTADO_FIELD, FECHA_FIELD };

	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(COLS_DEFS);

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
	public CatalogoTablasTemporalesDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public CatalogoTablasTemporalesDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see fondos.db.ICatalogoTablasTemporalesDBEntity#actualizarEstado(fondos.vos.CatalogoTablaTemporalVO)
	 */
	public int actualizarEstado(CatalogoTablaTemporalVO catalogoTablaTemporalVO) {

		Map colsToUpdate = new HashMap();
		colsToUpdate.put(ESTADO_FIELD, catalogoTablaTemporalVO.getEstado());
		colsToUpdate.put(FECHA_FIELD, DateUtils.getFechaActual());
		colsToUpdate.put(IDUSUARIO_FIELD,
				catalogoTablaTemporalVO.getIdUsuario());

		final StringBuffer qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(ID_FIELD,
						catalogoTablaTemporalVO.getIdAsString()));

		return updateFields(qual.toString(), colsToUpdate, TABLE_NAME);

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see fondos.db.ICatalogoTablasTemporalesDBEntity#actualizarEstado(fondos.vos.CatalogoTablaTemporalVO)
	 */
	public int reset(int numero) {
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(ESTADO_FIELD, Constants.TABLA_TEMPORAL_LIBRE_STATE);
		colsToUpdate.put(FECHA_FIELD, null);
		colsToUpdate.put(IDUSUARIO_FIELD, null);

		final StringBuffer qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(ID_FIELD, numero));

		return updateFields(qual.toString(), colsToUpdate, TABLE_NAME);

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see fondos.db.ICatalogoTablasTemporalesDBEntity#getByEstadoAndFecha(int)
	 */
	public CatalogoTablaTemporalVO getByEstadoAndUsuario(Integer estado,
			String idUsuario) {
		if (estado == null)
			estado = Constants.TABLA_TEMPORAL_LIBRE_STATE;
		final StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils
						.generateEQTokenField(IDUSUARIO_FIELD, idUsuario))
				.append(DBUtils.OR)
				.append(DBUtils.generateEQTokenField(ESTADO_FIELD,
						estado.intValue()))
				.append(DBUtils.generateOrderByDesc(ESTADO_FIELD));

		return (CatalogoTablaTemporalVO) getVO(qual.toString(), TABLE_NAME,
				COLS_DEFS, CatalogoTablaTemporalVO.class);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see fondos.db.ICatalogoTablasTemporalesDBEntity#getByEstadoAndFecha(int)
	 */
	public CatalogoTablaTemporalVO getTablaTemporalFromCaducadas() {

		Date fecha = DateUtils.getFechaActual();
		fecha = org.apache.commons.lang.time.DateUtils.addDays(fecha, -2);

		final StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateTokenFieldDateAnioMesDia(getConnection(),
						FECHA_FIELD, DBUtils.MENOR_IGUAL, fecha)).append(
				DBUtils.generateOrderBy(FECHA_FIELD));

		return (CatalogoTablaTemporalVO) getVO(qual.toString(), TABLE_NAME,
				COLS_DEFS, CatalogoTablaTemporalVO.class);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see fondos.db.ICatalogoTablasTemporalesDBEntity#insertar(fondos.vos.CatalogoTablaTemporalVO)
	 */
	public void insertar(final CatalogoTablaTemporalVO catalogoTablaTemporalVO) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				int id = getMax(Constants.STRING_EMPTY, TABLE_NAME, ID_FIELD);
				id++;
				catalogoTablaTemporalVO.setId(new Integer(id));
				catalogoTablaTemporalVO
						.setNombreTabla(TablaTemporalDBEntityImpl.TABLE_PREFFIX
								+ id);
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLS_DEFS, catalogoTablaTemporalVO);
				DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
						inputRecord);
			}
		};
		command.execute();
	}

	public void bloqueaTabla() throws Exception {
		DbUpdateFns.updateCustom(getConnection(), TABLE_NAME,
				ID_FIELD.getName(), ID_FIELD.getName(), "");
	}
}
