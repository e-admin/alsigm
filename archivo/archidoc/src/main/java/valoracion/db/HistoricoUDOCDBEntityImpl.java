package valoracion.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.List;

import org.apache.log4j.Logger;

import valoracion.vos.HistoricoUDOCVO;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;

/**
 * Clase que trabaja con trabaja contra la base de datos sobre la tabla de
 * ASGFHISTUDOC.
 */
public class HistoricoUDOCDBEntityImpl extends DBEntity implements
		IHistoricoUDOCDBEntity {

	static Logger logger = Logger.getLogger(EliminacionSerieDBEntityImpl.class);

	public static final String TABLE_NAME = "ASGFHISTUDOC";

	private static final String IDELIMINACION_COLUMN_NAME = "IDELIMINACION";
	private static final String IDUDOC_COLUMN_NAME = "IDUDOC";
	private static final String SIGNATURAUDOC_COLUMN_NAME = "SIGNATURAUDOC";
	private static final String TITULOUDOC_COLUMN_NAME = "TITULOUDOC";
	private static final String NUMEXPUDOC_COLUMN_NAME = "NUMEXPUDOC";
	private static final String CODREFUDOC_COLUMN_NAME = "CODREFUDOC";
	private static final String DATOSDESCRUDOC_COLUMN_NAME = "DATOSDESCRUDOC";

	public static final DbColumnDef CAMPO_IDELIMINACION = new DbColumnDef(null,
			TABLE_NAME, IDELIMINACION_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);
	public static final DbColumnDef CAMPO_IDUDOC = new DbColumnDef(null,
			TABLE_NAME, IDUDOC_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_SIGNATURAUDOC = new DbColumnDef(null,
			TABLE_NAME, SIGNATURAUDOC_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);
	public static final DbColumnDef CAMPO_TITULOUDOC = new DbColumnDef(null,
			TABLE_NAME, TITULOUDOC_COLUMN_NAME, DbDataType.SHORT_TEXT, 1024,
			false);
	public static final DbColumnDef CAMPO_NUMEXPUDOC = new DbColumnDef(null,
			TABLE_NAME, NUMEXPUDOC_COLUMN_NAME, DbDataType.SHORT_TEXT, 256,
			false);
	public static final DbColumnDef CAMPO_CODREFUDOC = new DbColumnDef(null,
			TABLE_NAME, CODREFUDOC_COLUMN_NAME, DbDataType.SHORT_TEXT, 1024,
			false);
	public static final DbColumnDef CAMPO_DATOSDESCRUDOC = new DbColumnDef(
			"xmlInfo", TABLE_NAME, DATOSDESCRUDOC_COLUMN_NAME,
			DbDataType.LONG_TEXT, false);

	public static final DbColumnDef[] COLS_DEFS = { CAMPO_IDELIMINACION,
			CAMPO_IDUDOC, CAMPO_SIGNATURAUDOC, CAMPO_TITULOUDOC,
			CAMPO_NUMEXPUDOC, CAMPO_CODREFUDOC, CAMPO_DATOSDESCRUDOC };

	public static final String COLUMN_NAMES = DbUtil.getColumnNames(COLS_DEFS);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public HistoricoUDOCDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public HistoricoUDOCDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Inserta un historico de unidad documental en la base de datos.
	 * 
	 * @param historico
	 *            Historico a insertar.
	 */
	public void insertHistoricoUdoc(final HistoricoUDOCVO historico) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLS_DEFS, historico);
				DbInsertFns.insert(conn, TABLE_NAME, COLUMN_NAMES, inputRecord);
			}
		};

		command.execute();
	}

	/**
	 * Obtiene la lista de unidades documentales en histórico procesadas en una
	 * eliminación.
	 * 
	 * @param idEliminacion
	 *            Identificador de la eliminación.
	 * @return Lista de unidades documentales ({@link HistoricoUDOCVO}).
	 */
	public List getUdocsByIdEliminacion(String idEliminacion) {
		String qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDELIMINACION,
						idEliminacion)).append(" ORDER BY ")
				.append(CAMPO_SIGNATURAUDOC).toString();

		return getVOS(qual, TABLE_NAME, COLS_DEFS, HistoricoUDOCVO.class);
	}
}