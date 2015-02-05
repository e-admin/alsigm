package descripcion.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbUtil;

import common.db.DBEntity;
import common.db.DbDataSource;

/**
 * DBEntity para acceder a la tabla ADVCTEXTLCF.
 */
public class TextoLargoDBEntityImpl extends TextoDBEntityImplBase implements
		ITextoDBEntity {

	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = "ADVCTEXTLCF";

	/** Nombre de columnas */
	public static final String IDELEMENTOCF_COLUMN_NAME = "idelementocf";
	public static final String TIMESTAMP_COLUMN_NAME = "timestamp";
	public static final String IDXVALOR_COLUMN_NAME = DBEntity.LONG_TEXT_POSTGRES_PREFFIX
			+ "valor";

	/** Definiciones de columna */
	public static final DbColumnDef CAMPO_ID_ELEMENTO = new DbColumnDef(
			IDOBJETO_COLUMN_ALIAS, TABLE_NAME, IDELEMENTOCF_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_ID_CAMPO = new DbColumnDef(null,
			TABLE_NAME, IDCAMPO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_VALOR = new DbColumnDef(null,
			TABLE_NAME, VALOR_COLUMN_NAME, DbDataType.LONG_TEXT, 0, false);
	public static final DbColumnDef CAMPO_IDXVALOR = new DbColumnDef(null,
			TABLE_NAME, IDXVALOR_COLUMN_NAME, DbDataType.LONG_TEXT, 0, false);
	public static final DbColumnDef CAMPO_ORDEN = new DbColumnDef(null,
			TABLE_NAME, ORDEN_COLUMN_NAME, DbDataType.LONG_INTEGER, 0, false);
	public static final DbColumnDef CAMPO_TIMESTAMP = new DbColumnDef(null,
			TABLE_NAME, TIMESTAMP_COLUMN_NAME, DbDataType.DATE_TIME, 0, false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] {
			CAMPO_ID_ELEMENTO, CAMPO_ID_CAMPO, CAMPO_VALOR, CAMPO_ORDEN,
			CAMPO_TIMESTAMP };

	/** Lista de nombres de columnas. */
	public static final String COLUMN_NAMES_LIST = DbUtil
			.getColumnNames(COL_DEFS);

	/** Hint para agilizar las búsquedas. */
	protected static final String HINT = "/*+INDEX(ADVCTEXTLCF ADVCTEXTLCF1)*/";

	/**
	 * Constructor.
	 * 
	 * @param dataSource
	 *            Pool de conexiones de base de datos.
	 */
	public TextoLargoDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public TextoLargoDBEntityImpl(DbConnection conn) {
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
		return null;
	}

	public DbColumnDef getCampoValor() {
		return CAMPO_VALOR;
	}
}