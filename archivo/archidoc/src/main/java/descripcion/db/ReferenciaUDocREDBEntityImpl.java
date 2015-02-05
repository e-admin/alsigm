package descripcion.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbUtil;

import common.db.DbDataSource;

/**
 * DBEntity para acceder a la tabla ADVCREFUDOCRE.
 */
public class ReferenciaUDocREDBEntityImpl extends ReferenciaDBEntityImplBase {

	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = "ADVCREFUDOCRE";

	/** Nombre de columnas */
	public static final String IDUNIDADDOCRE_COLUMN_NAME = "idudocre";
	public static final String TIPOUDOC_COLUMN_NAME = "tipoudoc";

	/** Definiciones de columna */
	public static final DbColumnDef CAMPO_ID_ELEMENTO = new DbColumnDef(
			IDOBJETO_COLUMN_ALIAS, TABLE_NAME, IDUNIDADDOCRE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_ID_CAMPO = new DbColumnDef(null,
			TABLE_NAME, IDCAMPO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_TIPOOBJREF = new DbColumnDef(null,
			TABLE_NAME, TIPOOBJREF_COLUMN_NAME, DbDataType.SHORT_INTEGER, 0,
			false);
	public static final DbColumnDef CAMPO_IDOBJREF = new DbColumnDef(null,
			TABLE_NAME, IDOBJREF_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_ORDEN = new DbColumnDef(null,
			TABLE_NAME, ORDEN_COLUMN_NAME, DbDataType.LONG_INTEGER, 0, false);
	public static final DbColumnDef CAMPO_TIPOUDOC = new DbColumnDef(null,
			TABLE_NAME, TIPOUDOC_COLUMN_NAME, DbDataType.SHORT_INTEGER, 0,
			false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] {
			CAMPO_ID_ELEMENTO, CAMPO_ID_CAMPO, CAMPO_TIPOOBJREF,
			CAMPO_IDOBJREF, CAMPO_ORDEN, CAMPO_TIPOUDOC };

	/** Lista de columnas para el contenido de la referencia. */
	protected static final DbColumnDef[] CUSTOM_COL_DEFS = new DbColumnDef[] {
			CAMPO_ID_ELEMENTO,
			CAMPO_ID_CAMPO,
			CAMPO_TIPOOBJREF,
			CAMPO_IDOBJREF,
			CAMPO_ORDEN,
			new DbColumnDef(
					"nombre",
					new StringBuffer()
							.append("CASE WHEN ADVCREFUDOCRE.TIPOOBJREF = 1 THEN ")
							.append("(SELECT ADDESCRIPTOR.NOMBRE FROM ADDESCRIPTOR WHERE ADDESCRIPTOR.ID=ADVCREFUDOCRE.IDOBJREF)")
							.append("WHEN ADVCREFUDOCRE.TIPOOBJREF = 2 THEN ")
							.append("(SELECT ASGFELEMENTOCF.CODREFERENCIA FROM ASGFELEMENTOCF WHERE ASGFELEMENTOCF.ID=ADVCREFUDOCRE.IDOBJREF)")
							.append("END").toString(), DbDataType.SHORT_TEXT,
					1024, true),
			new DbColumnDef(
					"descripcion",
					new StringBuffer()
							.append("CASE WHEN ADVCREFUDOCRE.TIPOOBJREF = 2 THEN ")
							.append("(SELECT ASGFELEMENTOCF.TITULO FROM ASGFELEMENTOCF WHERE ASGFELEMENTOCF.ID=ADVCREFUDOCRE.IDOBJREF)")
							.append("END").toString(), DbDataType.SHORT_TEXT,
					1024, true), CAMPO_TIPOUDOC };

	/** Lista de nombres de columnas. */
	public static final String COLUMN_NAMES_LIST = DbUtil
			.getColumnNames(COL_DEFS);

	/** Hint para agilizar las búsquedas. */
	protected static final String HINT = "/*+INDEX(ADVCREFUDOCRE ADVCREFUDOCRE1)*/";

	/** Hint para agilizar las búsquedas. */
	protected static final String HINT_TIPOOBJ_IDOBJ = "/*+INDEX(ADVCREFUDOCRE ADVCREFUDOCRE3)*/";

	/**
	 * Constructor.
	 * 
	 * @param dataSource
	 *            Pool de conexiones de base de datos.
	 */
	public ReferenciaUDocREDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public ReferenciaUDocREDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	public DbColumnDef getCampoIdElemento() {
		return CAMPO_ID_ELEMENTO;
	}

	public DbColumnDef[] getColDefs() {
		return COL_DEFS;
	}

	public String getHint() {
		return HINT;
	}

	public String getColumnNamesList() {
		return COLUMN_NAMES_LIST;
	}

	public DbColumnDef getCampoIdCampo() {
		return CAMPO_ID_CAMPO;
	}

	public DbColumnDef getCampoOrden() {
		return CAMPO_ORDEN;
	}

	public String getTableName() {
		return TABLE_NAME;
	}

	public DbColumnDef getCampoTipoObjRef() {
		return CAMPO_TIPOOBJREF;
	}

	public DbColumnDef getCampoIdObjRef() {
		return CAMPO_IDOBJREF;
	}

	public String getHintTipoObjIdObj() {
		return HINT_TIPOOBJ_IDOBJ;
	}

	public DbColumnDef[] getCustomColDefs() {
		return CUSTOM_COL_DEFS;
	}

	public DbColumnDef getCampoTipoElemento() {
		return CAMPO_TIPOUDOC;
	}
}