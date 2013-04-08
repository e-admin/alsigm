package descripcion.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbUtil;

import common.db.DbDataSource;

/**
 * DBEntity para acceder a la tabla ADVCFECHAUDOCRE.
 */
public class FechaUDocREDBEntityImpl extends FechaDBEntityImplBase {

	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = "ADVCFECHAUDOCRE";

	/** Nombre de columnas */
	public static final String IDUNIDADDOCRE_COLUMN_NAME = "idudocre";
	public static final String TIPOUDOC_COLUMN_NAME = "tipoudoc";

	/** Definiciones de columna */
	public static final DbColumnDef CAMPO_ID_ELEMENTO = new DbColumnDef(
			IDOBJETO_COLUMN_ALIAS, TABLE_NAME, IDUNIDADDOCRE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_ID_CAMPO = new DbColumnDef(null,
			TABLE_NAME, IDCAMPO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_FECHA_INICIAL = new DbColumnDef(null,
			TABLE_NAME, FECHA_INICIAL_COLUMN_NAME, DbDataType.DATE_TIME, -1,
			true);
	public static final DbColumnDef CAMPO_FECHA_FINAL = new DbColumnDef(null,
			TABLE_NAME, FECHA_FINAL_COLUMN_NAME, DbDataType.DATE_TIME, -1, true);
	public static final DbColumnDef CAMPO_CALIFICADOR = new DbColumnDef(null,
			TABLE_NAME, CALIFICADOR_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);
	public static final DbColumnDef CAMPO_VALOR = new DbColumnDef(null,
			TABLE_NAME, VALOR_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, true);
	public static final DbColumnDef CAMPO_FORMATO = new DbColumnDef(null,
			TABLE_NAME, FORMATO_COLUMN_NAME, DbDataType.SHORT_TEXT, 16, true);
	public static final DbColumnDef CAMPO_SEP = new DbColumnDef(null,
			TABLE_NAME, SEP_COLUMN_NAME, DbDataType.SHORT_TEXT, 1, true);
	public static final DbColumnDef CAMPO_ORDEN = new DbColumnDef(null,
			TABLE_NAME, ORDEN_COLUMN_NAME, DbDataType.LONG_INTEGER, 0, false);
	public static final DbColumnDef CAMPO_TIPOUDOC = new DbColumnDef(null,
			TABLE_NAME, TIPOUDOC_COLUMN_NAME, DbDataType.SHORT_INTEGER, 2,
			false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] {
			CAMPO_ID_ELEMENTO, CAMPO_ID_CAMPO, CAMPO_FECHA_INICIAL,
			CAMPO_FECHA_FINAL, CAMPO_CALIFICADOR, CAMPO_VALOR, CAMPO_FORMATO,
			CAMPO_SEP, CAMPO_ORDEN, CAMPO_TIPOUDOC };

	/** Lista de nombres de columnas. */
	public static final String COLUMN_NAMES_LIST = DbUtil
			.getColumnNames(COL_DEFS);

	/** Hint para agilizar las búsquedas. */
	protected static final String HINT = "/*+INDEX(ADVCFECHAUDOCRE ADVCFECHAUDOCRE1)*/";

	/**
	 * Constructor.
	 * 
	 * @param dataSource
	 *            Pool de conexiones de base de datos.
	 */
	public FechaUDocREDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public FechaUDocREDBEntityImpl(DbConnection conn) {
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

	public DbColumnDef getCampoTipoElemento() {
		return CAMPO_TIPOUDOC;
	}
}