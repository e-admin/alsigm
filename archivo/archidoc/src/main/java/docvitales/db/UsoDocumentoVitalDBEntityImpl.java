package docvitales.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbUtil;

import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;

import docvitales.vos.UsoDocumentoVitalVO;

/**
 * DBEntity para acceder a la tabla ADPCUSODOCVIT.
 */
public class UsoDocumentoVitalDBEntityImpl extends DBEntity implements
		IUsoDocumentoVitalDBEntity {
	/** Nombre de la tabla. */
	public static final String TABLE_NAME = "ADPCUSODOCVIT";

	/** Nombre de columnas */
	public static final String ID_DOC_VIT_COLUMN_NAME = "iddocvit";
	public static final String ID_EXP_COLUMN_NAME = "idexp";
	public static final String ID_SIST_COLUMN_NAME = "idsist";
	public static final String FECHA_USO_COLUMN_NAME = "fechauso";
	public static final String USUARIO_COLUMN_NAME = "usuario";

	/** Definiciones de columnas */
	public static final DbColumnDef CAMPO_ID_DOC_VIT = new DbColumnDef(null,
			TABLE_NAME, ID_DOC_VIT_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);
	public static final DbColumnDef CAMPO_ID_EXP = new DbColumnDef(null,
			TABLE_NAME, ID_EXP_COLUMN_NAME, DbDataType.SHORT_TEXT, 254, false);
	public static final DbColumnDef CAMPO_ID_SIST = new DbColumnDef(null,
			TABLE_NAME, ID_SIST_COLUMN_NAME, DbDataType.SHORT_TEXT, 254, false);
	public static final DbColumnDef CAMPO_FECHA_USO = new DbColumnDef(null,
			TABLE_NAME, FECHA_USO_COLUMN_NAME, DbDataType.DATE_TIME, false);
	public static final DbColumnDef CAMPO_USUARIO = new DbColumnDef(null,
			TABLE_NAME, USUARIO_COLUMN_NAME, DbDataType.SHORT_TEXT, 254, false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] {
			CAMPO_ID_DOC_VIT, CAMPO_ID_EXP, CAMPO_ID_SIST, CAMPO_FECHA_USO,
			CAMPO_USUARIO };

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
	public UsoDocumentoVitalDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public UsoDocumentoVitalDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Comprueba si un expediente está referenciando el documento vital.
	 * 
	 * @param idDocVit
	 *            Identificador del documento vital.
	 * @param idExp
	 *            Identificador del expediente de backoffice.
	 * @param idSist
	 *            Identificador del sistema de backoffice.
	 * @return true si el expediente utiliza en documento vital.
	 */
	public boolean existeUso(String idDocVit, String idExp, String idSist) {
		String qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils
						.generateEQTokenField(CAMPO_ID_DOC_VIT, idDocVit))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_EXP, idExp))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_SIST, idSist))
				.toString();

		return (getVOCount(qual, TABLE_NAME) > 0);
	}

	/**
	 * Inserta la información de uso de un documento vital.
	 * 
	 * @param uso
	 *            Información de uso.
	 */
	public void insertUsoDocumentoVital(UsoDocumentoVitalVO uso) {
		insertVO(TABLE_NAME, COL_DEFS, uso);
	}

	/**
	 * Elimina la información de uso de documentos vitales.
	 * 
	 * @param idExp
	 *            Identificador del expediente de backoffice.
	 * @param idSist
	 *            Identificador del sistema de backoffice.
	 */
	public void deleteUsos(String idExp, String idSist) {
		String qual = new StringBuffer().append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_EXP, idExp))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_SIST, idSist))
				.toString();

		deleteVO(qual, TABLE_NAME);
	}

}
