package descripcion.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbUtil;

import common.db.DbDataSource;

/**
 * DBEntity para acceder a la tabla ADVCNUMCF.
 */
public class NumeroUDocREDBEntityImpl extends NumeroDBEntityImplBase {

	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = "ADVCNUMUDOCRE";

	/** Nombre de columnas */
	public static final String IDUNIDADDOCRE_COLUMN_NAME = "idudocre";
	public static final String TIPOUDOC_COLUMN_NAME = "tipoudoc";

	/** Definiciones de columna */
	public static final DbColumnDef CAMPO_ID_ELEMENTO = new DbColumnDef(
			IDOBJETO_COLUMN_ALIAS, TABLE_NAME, IDUNIDADDOCRE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_ID_CAMPO = new DbColumnDef(null,
			TABLE_NAME, IDCAMPO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_TIPOMEDIDA = new DbColumnDef(null,
			TABLE_NAME, TIPOMEDIDA_COLUMN_NAME, DbDataType.SHORT_INTEGER, false);
	public static final DbColumnDef CAMPO_UNIDADMEDIDA = new DbColumnDef(null,
			TABLE_NAME, UNIDADMEDIDA_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);
	public static final DbColumnDef CAMPO_VALOR = new DbColumnDef(null,
			TABLE_NAME, VALOR_COLUMN_NAME, DbDataType.LONG_DECIMAL, false);
	public static final DbColumnDef CAMPO_ORDEN = new DbColumnDef(null,
			TABLE_NAME, ORDEN_COLUMN_NAME, DbDataType.LONG_INTEGER, false);
	public static final DbColumnDef CAMPO_TIPOELEMENTO = new DbColumnDef(null,
			TABLE_NAME, TIPOUDOC_COLUMN_NAME, DbDataType.SHORT_INTEGER, 0,
			false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] {
			CAMPO_ID_ELEMENTO, CAMPO_ID_CAMPO, CAMPO_VALOR, CAMPO_TIPOMEDIDA,
			CAMPO_UNIDADMEDIDA, CAMPO_ORDEN, CAMPO_TIPOELEMENTO };

	/** Lista de nombres de columnas. */
	public static final String COLUMN_NAMES_LIST = DbUtil
			.getColumnNames(COL_DEFS);

	/** Hint para agilizar las búsquedas. */
	protected static final String HINT = "/*+INDEX(ADVCNUMUDOCRE ADVCNUMUDOCRE1)*/";

	/**
	 * Constructor.
	 * 
	 * @param dataSource
	 *            Pool de conexiones de base de datos.
	 */
	public NumeroUDocREDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public NumeroUDocREDBEntityImpl(DbConnection conn) {
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
		return CAMPO_TIPOELEMENTO;
	}

	public DbColumnDef getCampoValor() {
		return CAMPO_VALOR;
	}

}