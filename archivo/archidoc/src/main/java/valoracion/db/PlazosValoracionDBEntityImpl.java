package valoracion.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;

import java.util.List;

import org.apache.log4j.Logger;

import valoracion.vos.PlazoValoracionVO;
import valoracion.vos.ValoracionSerieVO;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.exceptions.TooManyResultsException;
import common.util.ArrayUtils;

/**
 * Clase que trabaja con trabaja contra la base de datos sobre la tabla de
 * ASGFPZTRVALSERIE.
 */
public class PlazosValoracionDBEntityImpl extends DBEntity implements
		IPlazosValoracionDBEntity {

	static Logger logger = Logger.getLogger(PlazosValoracionDBEntityImpl.class);

	public static final String TABLE_NAME = "ASGFPZTRVALSERIE";

	private static final String IDVALSERIE_COLUMN_NAME = "IDVALSERIE";
	private static final String PLAZO_COLUMN_NAME = "PLAZO";
	private static final String IDNIVELARCHORG_COLUMN_NAME = "IDNIVELARCHORG";
	private static final String IDNIVELARCHDST_COLUMN_NAME = "IDNIVELARCHDST";
	private static final String ORDEN_COLUMN_NAME = "ORDEN";

	public static final DbColumnDef CAMPO_IDVALSERIE = new DbColumnDef(
			"idValSerie", TABLE_NAME, IDVALSERIE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_PLAZO = new DbColumnDef("plazo",
			TABLE_NAME, PLAZO_COLUMN_NAME, DbDataType.SHORT_INTEGER, false);
	public static final DbColumnDef CAMPO_IDNIVELARCHORG = new DbColumnDef(
			"idNivelOrigen", TABLE_NAME, IDNIVELARCHORG_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_IDNIVELARCHDST = new DbColumnDef(
			"idNivelDestino", TABLE_NAME, IDNIVELARCHDST_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_ORDEN = new DbColumnDef("orden",
			TABLE_NAME, ORDEN_COLUMN_NAME, DbDataType.SHORT_INTEGER, false);

	public static final DbColumnDef[] COLS_DEFS = { CAMPO_IDVALSERIE,
			CAMPO_PLAZO, CAMPO_IDNIVELARCHORG, CAMPO_IDNIVELARCHDST,
			CAMPO_ORDEN };

	public static final String COLUMN_NAMES = DbUtil.getColumnNames(COLS_DEFS);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public PlazosValoracionDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public PlazosValoracionDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	public int getCountPlazosValoracion(String idValoracion) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_IDVALSERIE, idValoracion));
		return getVOCount(qual.toString(), TABLE_NAME);
	}

	public List getValoracionesPorPlazos(String idNivelOrigen,
			String idNivelDestino) {
		DbColumnDef[] columnas = new DbColumnDef[] {
				ValoracionDBEntityImpl.CAMPO_ID,
				ValoracionDBEntityImpl.CAMPO_TITULO };
		String[] tablas = { ValoracionDBEntityImpl.TABLE_NAME, TABLE_NAME };

		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateJoinCondition(CAMPO_IDVALSERIE,
						ValoracionDBEntityImpl.CAMPO_ID))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDNIVELARCHORG,
						idNivelOrigen))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDNIVELARCHDST,
						idNivelDestino));

		return getDistinctVOS(qual.toString(), ArrayUtils.join(tablas, ","),
				columnas, ValoracionSerieVO.class);
	}

	public List getValoracionesPorIdNivelOrigenDestino(String idNivel) {
		DbColumnDef[] columnas = new DbColumnDef[] {
				ValoracionDBEntityImpl.CAMPO_ID,
				ValoracionDBEntityImpl.CAMPO_TITULO };
		String[] tablas = { ValoracionDBEntityImpl.TABLE_NAME, TABLE_NAME };

		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateJoinCondition(CAMPO_IDVALSERIE,
						ValoracionDBEntityImpl.CAMPO_ID))
				.append(" AND (")
				.append(DBUtils.generateEQTokenField(CAMPO_IDNIVELARCHORG,
						idNivel))
				.append(" OR ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDNIVELARCHDST,
						idNivel)).append(")");

		return getDistinctVOS(qual.toString(), ArrayUtils.join(tablas, ","),
				columnas, ValoracionSerieVO.class);
	}

	public List getPlazosValoracion(String idValoracion) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_IDVALSERIE, idValoracion));
		StringBuffer orderBy = new StringBuffer("ORDER BY ORDEN");
		List plazos = null;
		try {
			plazos = getVOS(qual.toString(), TABLE_NAME, COLS_DEFS,
					PlazoValoracionVO.class, orderBy.toString(), 0);
		} catch (TooManyResultsException e) {
		}
		return plazos;
	}

	public PlazoValoracionVO getPlazoValoracion(String idValoracion, int orden) {
		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDVALSERIE,
						idValoracion)).append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ORDEN, orden));
		return (PlazoValoracionVO) getVO(qual.toString(), TABLE_NAME,
				COLS_DEFS, PlazoValoracionVO.class);
	}

	public void deletePlazoValoracion(final String idValoracion, final int orden) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer qual = new StringBuffer("WHERE ")
						.append(DBUtils.generateEQTokenField(CAMPO_IDVALSERIE,
								idValoracion))
						.append(" AND ")
						.append(DBUtils
								.generateEQTokenField(CAMPO_ORDEN, orden));
				DbDeleteFns
						.delete(getConnection(), TABLE_NAME, qual.toString());
			}
		};
		command.execute();
	}

	public void deletePlazosValoracion(final String idValoracion) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
						.generateEQTokenField(CAMPO_IDVALSERIE, idValoracion));
				DbDeleteFns
						.delete(getConnection(), TABLE_NAME, qual.toString());
			}
		};
		command.execute();
	}

	public void deletePlazosValoracionFromOrden(final String idValoracion,
			final int orden) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer qual = new StringBuffer("WHERE ")
						.append(DBUtils.generateEQTokenField(CAMPO_IDVALSERIE,
								idValoracion))
						.append(" AND ")
						.append(DBUtils.generateGTEQTokenField(CAMPO_ORDEN,
								orden));
				DbDeleteFns
						.delete(getConnection(), TABLE_NAME, qual.toString());
			}
		};
		command.execute();
	}

	public void insertPlazoValoracion(final PlazoValoracionVO plazoVO) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLS_DEFS, plazoVO);
				DbInsertFns.insert(conn, TABLE_NAME, COLUMN_NAMES, inputRecord);
			}
		};
		command.execute();
	}

	public void updatePlazoValoracion(final PlazoValoracionVO plazoVO) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer qual = new StringBuffer("WHERE ")
						.append(DBUtils.generateEQTokenField(CAMPO_IDVALSERIE,
								plazoVO.getIdValSerie()))
						.append(" AND ")
						.append(DBUtils.generateEQTokenField(CAMPO_ORDEN,
								plazoVO.getOrden()));
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLS_DEFS, plazoVO);
				DbUpdateFns.update(conn, TABLE_NAME, COLUMN_NAMES, inputRecord,
						qual.toString());
			}
		};
		command.execute();
	}
}