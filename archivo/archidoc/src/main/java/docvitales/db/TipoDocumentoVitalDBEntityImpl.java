package docvitales.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbUtil;

import java.util.List;

import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;

import docvitales.vos.TipoDocumentoVitalVO;

/**
 * DBEntity para acceder a la tabla de tipos de documentos vitales.
 */
public class TipoDocumentoVitalDBEntityImpl extends DBEntity implements
		ITipoDocumentoVitalDBEntity {
	/** Nombre de la tabla. */
	public static final String TABLE_NAME = "ADPCTIPODOCVIT";

	/** Nombre de columnas */
	public static final String ID_COLUMN_NAME = "id";
	public static final String NOMBRE_COLUMN_NAME = "nombre";
	public static final String DESCRIPCION_COLUMN_NAME = "descripcion";

	/** Definiciones de columnas */
	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_NOMBRE = new DbColumnDef(null,
			TABLE_NAME, NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 254, false);
	public static final DbColumnDef CAMPO_DESCRIPCION = new DbColumnDef(null,
			TABLE_NAME, DESCRIPCION_COLUMN_NAME, DbDataType.SHORT_TEXT, 512,
			true);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] { CAMPO_ID,
			CAMPO_NOMBRE, CAMPO_DESCRIPCION };

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
	public TipoDocumentoVitalDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public TipoDocumentoVitalDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene la lista de tipos de documentos vitales.
	 * 
	 * @return Lista de documentos vitales ({@link TipoDocumentoVitalVO}).
	 */
	public List getTiposDocumentosVitales() {
		StringBuffer qual = new StringBuffer().append(" ORDER BY ").append(
				CAMPO_NOMBRE.getQualifiedName());

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS,
				TipoDocumentoVitalVO.class);
	}

	/**
	 * Obtiene la lista de tipos de documentos vitales de un procedimiento.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 * @return Lista de documentos vitales ({@link TipoDocumentoVitalVO}).
	 */
	public List getTiposDocumentosVitales(String idProc) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(CAMPO_ID.getQualifiedName())
				.append(" IN (SELECT ")
				.append(TipoDocVitProcedimientoDBEntityImpl.CAMPO_ID_TIPO_DOC
						.getQualifiedName())
				.append(" FROM ")
				.append(TipoDocVitProcedimientoDBEntityImpl.TABLE_NAME)
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(
						TipoDocVitProcedimientoDBEntityImpl.CAMPO_ID_PROC,
						idProc)).append(") ORDER BY ")
				.append(CAMPO_NOMBRE.getQualifiedName());

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS,
				TipoDocumentoVitalVO.class);
	}

	/**
	 * Obtiene el tipo de documento vital.
	 * 
	 * @param id
	 *            Identificador del tipo de documento vital.
	 * @return Tipo de documento vital.
	 */
	public TipoDocumentoVitalVO getTipoDocumentoVital(String id) {
		StringBuffer qual = new StringBuffer().append(" WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_ID, id));

		return (TipoDocumentoVitalVO) getVO(qual.toString(), TABLE_NAME,
				COL_DEFS, TipoDocumentoVitalVO.class);
	}

	/**
	 * Inserta un tipo de documento vital.
	 * 
	 * @param tipo
	 *            Tipo de documento vital.
	 * @return Tipo de documento vital.
	 */
	public TipoDocumentoVitalVO insertTipoDocumentoVital(
			TipoDocumentoVitalVO tipo) {
		tipo.setId(getGuid(tipo.getId()));
		insertVO(TABLE_NAME, COL_DEFS, tipo);
		return tipo;
	}

	/**
	 * Modifica un tipo de documento vital.
	 * 
	 * @param tipo
	 *            Tipo de documento vital.
	 */
	public void updateTipoDocumentoVital(TipoDocumentoVitalVO tipo) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, tipo.getId()))
				.toString();

		updateVO(qual, TABLE_NAME, COL_DEFS, tipo);
	}

	/**
	 * Elimina un tipo de documento vital.
	 * 
	 * @param id
	 *            Identificador del tipo de documento vital.
	 */
	public void deleteTipoDocumentoVital(String id) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id)).toString();

		deleteVO(qual, TABLE_NAME);
	}

}
