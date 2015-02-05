package docvitales.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbUtil;

import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;

import docvitales.vos.TipoDocVitProcedimientoVO;

/**
 * DBEntity para acceder a la tabla ADPCTIPODOCPROC.
 */
public class TipoDocVitProcedimientoDBEntityImpl extends DBEntity implements
		ITipoDocVitProcedimientoDBEntity {
	/** Nombre de la tabla. */
	public static final String TABLE_NAME = "ADPCTIPODOCPROC";

	/** Nombre de columnas */
	public static final String ID_TIPO_DOC_COLUMN_NAME = "idtipodoc";
	public static final String ID_PROC_COLUMN_NAME = "idproc";

	/** Definiciones de columnas */
	public static final DbColumnDef CAMPO_ID_TIPO_DOC = new DbColumnDef(null,
			TABLE_NAME, ID_TIPO_DOC_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);
	public static final DbColumnDef CAMPO_ID_PROC = new DbColumnDef(null,
			TABLE_NAME, ID_PROC_COLUMN_NAME, DbDataType.SHORT_TEXT, 254, false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] {
			CAMPO_ID_TIPO_DOC, CAMPO_ID_PROC };

	/** Lista de nombres de columnas. */
	public static final String COL_NAMES = DbUtil.getColumnNames(COL_DEFS);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Constructor.
	 * 
	 * @param dataSource
	 *            Pool de conexiones de base de datos.
	 */
	public TipoDocVitProcedimientoDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public TipoDocVitProcedimientoDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Crea una relación entre un procedimiento y un tipo de documento vital.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 * @param idTipoDocVit
	 *            Identificador del tipo de documento vital.
	 */
	public void insert(String idProc, String idTipoDocVit) {
		insertVO(TABLE_NAME, COL_DEFS, new TipoDocVitProcedimientoVO(idProc,
				idTipoDocVit));
	}

	/**
	 * Elimina todas las relaciones de un tipo de documento vital con
	 * procedientos.
	 * 
	 * @param idTipoDocVit
	 *            Identificador del tipo de documento vital.
	 */
	public void deleteByIdTipoDocVit(String idTipoDocVit) {
		String qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_TIPO_DOC,
						idTipoDocVit)).toString();

		deleteVO(qual, TABLE_NAME);
	}

	/**
	 * Elimina todas las relaciones entre tipos de documentos vitales con un
	 * procedimiento.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 */
	public void deleteByIdProc(String idProc) {
		String qual = new StringBuffer().append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_PROC, idProc))
				.toString();

		deleteVO(qual, TABLE_NAME);
	}

	/**
	 * Elimina la relación entre un procedimiento y un tipo de documento vital.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 * @param idTipoDocVit
	 *            Identificador del tipo de documento vital.
	 */
	public void delete(String idProc, String idTipoDocVit) {
		String qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_PROC, idProc))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_TIPO_DOC,
						idTipoDocVit)).toString();

		deleteVO(qual, TABLE_NAME);
	}

	/**
	 * Elimina la relación entre un procedimiento y una lista de tipos de
	 * documentos vitales.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 * @param idsTipoDocVit
	 *            Lista de identificadores de tipos de documentos vitales.
	 */
	public void delete(String idProc, String[] idsTipoDocVit) {
		String qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_PROC, idProc))
				.append(" AND ")
				.append(DBUtils.generateInTokenField(CAMPO_ID_TIPO_DOC,
						idsTipoDocVit)).toString();

		deleteVO(qual, TABLE_NAME);
	}
}
