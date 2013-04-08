package valoracion.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.List;

import org.apache.log4j.Logger;

import valoracion.vos.IUnidadDocumentalEliminacionVO;
import valoracion.vos.UnidadDocumentalEliminacionVO;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;

/**
 * Clase que trabaja con trabaja contra la base de datos sobre la tabla de
 * ASGFELIMUDOCCONS.
 */
public class EliminacionUDOCEliminadaDBEntityImpl extends DBEntity implements
		IEliminacionUDOCEliminadaDBEntity {

	static Logger logger = Logger.getLogger(EliminacionSerieDBEntityImpl.class);

	public static final String TABLE_NAME = "ASGFELIMSELUDOC";

	public static final String IDELIMINACION_COLUMN_NAME = "IDELIMINACION";
	public static final String IDUDOC_COLUMN_NAME = "IDUDOC";
	public static final String IDFONDO_COLUMN_NAME = "IDFONDO";
	public static final String CODREFERENCIA_COLUMN_NAME = "CODREFERENCIA";
	public static final String CODIGO_COLUMN_NAME = "CODIGO";
	public static final String SIGNATURAUDOC_COLUMN_NAME = "SIGNATURAUDOC";
	public static final String EXPEDIENTEUDOC_COLUMN_NAME = "EXPEDIENTEUDOC";
	public static final String TITULO_COLUMN_NAME = "TITULO";
	public static final String CODSISTPRODUCTOR_COLUMN_NAME = "CODSISTPRODUCTOR";
	public static final String TIPODOCUMENTAL_COLUMN_NAME = "TIPODOCUMENTAL";
	public static final String IDUINSTALACION_COLUMN_NAME = "IDUINSTALACION";
	public static final String UBICACION_COLUMN_NAME = "UBICACION";
	public static final String FECHAINIUDOC_COLUMN_NAME = "FECHAINIUDOC";
	public static final String FECHAFINUDOC_COLUMN_NAME = "FECHAFINUDOC";
	public static final String NUMERO_COLUMN_NAME = "NUMERO";

	public static final DbColumnDef IDELIMINACION_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDELIMINACION_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);
	public static final DbColumnDef IDUDOC_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDUDOC_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef IDFONDO_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDFONDO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, true);
	public static final DbColumnDef CODREFERENCIA_FIELD = new DbColumnDef(null,
			TABLE_NAME, CODREFERENCIA_COLUMN_NAME, DbDataType.SHORT_TEXT, 128,
			true);
	public static final DbColumnDef CODIGO_FIELD = new DbColumnDef(null,
			TABLE_NAME, CODIGO_COLUMN_NAME, DbDataType.SHORT_TEXT, 128, true);
	public static final DbColumnDef SIGNATURAUDOC_FIELD = new DbColumnDef(null,
			TABLE_NAME, SIGNATURAUDOC_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			true);
	public static final DbColumnDef EXPEDIENTEUDOC_FIELD = new DbColumnDef(
			null, TABLE_NAME, EXPEDIENTEUDOC_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 256, true);
	public static final DbColumnDef TITULO_FIELD = new DbColumnDef(null,
			TABLE_NAME, TITULO_COLUMN_NAME, DbDataType.SHORT_TEXT, 1024, true);
	public static final DbColumnDef CODSISTPRODUCTOR_FIELD = new DbColumnDef(
			null, TABLE_NAME, CODSISTPRODUCTOR_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 16, true);
	public static final DbColumnDef TIPODOCUMENTAL_FIELD = new DbColumnDef(
			null, TABLE_NAME, TIPODOCUMENTAL_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 254, true);
	public static final DbColumnDef IDUINSTALACION_FIELD = new DbColumnDef(
			null, TABLE_NAME, IDUINSTALACION_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, true);
	public static final DbColumnDef UBICACION_FIELD = new DbColumnDef(null,
			TABLE_NAME, UBICACION_COLUMN_NAME, DbDataType.SHORT_TEXT, 512, true);
	public static final DbColumnDef FECHAINIUDOC_FIELD = new DbColumnDef(null,
			TABLE_NAME, FECHAINIUDOC_COLUMN_NAME, DbDataType.DATE_TIME, true);
	public static final DbColumnDef FECHAFINUDOC_FIELD = new DbColumnDef(null,
			TABLE_NAME, FECHAFINUDOC_COLUMN_NAME, DbDataType.DATE_TIME, true);
	public static final DbColumnDef NUMERO_FIELD = new DbColumnDef(null,
			TABLE_NAME, NUMERO_COLUMN_NAME, DbDataType.SHORT_TEXT, 10, true);

	public static final DbColumnDef[] COLS_DEFS = { IDELIMINACION_FIELD,
			IDUDOC_FIELD, IDFONDO_FIELD, CODREFERENCIA_FIELD, CODIGO_FIELD,
			SIGNATURAUDOC_FIELD, EXPEDIENTEUDOC_FIELD, TITULO_FIELD,
			CODSISTPRODUCTOR_FIELD, TIPODOCUMENTAL_FIELD, IDUINSTALACION_FIELD,
			UBICACION_FIELD, FECHAINIUDOC_FIELD, FECHAFINUDOC_FIELD,
			NUMERO_FIELD };

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public static final String COLUMN_NAMES = DbUtil.getColumnNames(COLS_DEFS);

	public EliminacionUDOCEliminadaDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public EliminacionUDOCEliminadaDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.db.IEliminacionUDOCEliminadaDBEntity#getUdocsAEliminarXId(java.lang.String)
	 */
	public List getUdocsAEliminarXId(String idEliminacion) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(IDELIMINACION_FIELD, idEliminacion));
		return getVOS(qual.toString(), TABLE_NAME, COLS_DEFS,
				UnidadDocumentalEliminacionVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.db.IEliminacionUDOCEliminadaDBEntity#insertUdocAEliminar(valoracion.vos.IUnidadDocumentalEliminacionVO)
	 */
	public void insertUdocAEliminar(
			final IUnidadDocumentalEliminacionVO unidadDocumentalEliminacionVO) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLS_DEFS, unidadDocumentalEliminacionVO);
				DbInsertFns.insert(conn, TABLE_NAME, COLUMN_NAMES, inputRecord);
			}
		};
		command.execute();
	}

	public int getCountNumeroUinstalacion(String idEliminacion) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(IDELIMINACION_FIELD, idEliminacion));

		final DbColumnDef[] columnasCount = new DbColumnDef[] { IDUINSTALACION_FIELD };

		return getDistintVOCount(columnasCount, qual.toString(), TABLE_NAME);
	}
}