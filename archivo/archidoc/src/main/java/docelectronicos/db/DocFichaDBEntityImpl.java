package docelectronicos.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbUtil;

import java.util.List;

import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;

import docelectronicos.vos.FichaClfVO;
import docelectronicos.vos.InfoBFichaClfVO;

/**
 * DBEntity para acceder a la tabla ADOCFICHACLF.
 */
public class DocFichaDBEntityImpl extends DBEntity implements IDocFichaDBEntity {
	/** Nombre de la tabla. */
	public static final String TABLE_NAME = "ADOCFICHACLF";

	/** Nombre de columnas */
	public static final String ID_COLUMN_NAME = "id";
	public static final String NOMBRE_COLUMN_NAME = "nombre";
	public static final String DEFINICION_COLUMN_NAME = "definicion";
	public static final String DESCRIPCION_COLUMN_NAME = "descripcion";

	/** Definiciones de columnas */
	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_NOMBRE = new DbColumnDef(null,
			TABLE_NAME, NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, false);
	public static final DbColumnDef CAMPO_DEFINICION = new DbColumnDef(null,
			TABLE_NAME, DEFINICION_COLUMN_NAME, DbDataType.LONG_TEXT, false);
	public static final DbColumnDef CAMPO_DESCRIPCION = new DbColumnDef(null,
			TABLE_NAME, DESCRIPCION_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			true);

	// Campo para join con listas descriptoras
	public static final DbColumnDef CAMPO_NOMBRE_FICHA_DOC = new DbColumnDef(
			"nombreFichaClfDocPref", TABLE_NAME, NOMBRE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 64, false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = { CAMPO_ID, CAMPO_NOMBRE,
			CAMPO_DEFINICION, CAMPO_DESCRIPCION };

	/** Lista de columnas reducida. */
	public static final DbColumnDef[] COL_DEFS_REDUCIDA = { CAMPO_ID,
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
	public DocFichaDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public DocFichaDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene la lista de fichas de clasificadores documentales.
	 * 
	 * @return Listas fichas de clasificadores documentales (
	 *         {@link InfoBFichaClfVO}).
	 */
	public List getFichas() {
		String qual = new StringBuffer().append(" ORDER BY ")
				.append(CAMPO_NOMBRE.getQualifiedName()).toString();

		return getVOS(qual, TABLE_NAME, COL_DEFS_REDUCIDA,
				InfoBFichaClfVO.class);
	}

	/**
	 * Obtiene la ficha de clasificadores documentales.
	 * 
	 * @param id
	 *            Identificador de la ficha.
	 * @return Ficha de clasificadores documentales.
	 */
	public FichaClfVO getFicha(String id) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id)).toString();

		return (FichaClfVO) getVO(qual, TABLE_NAME, COL_DEFS, FichaClfVO.class);
	}

	/**
	 * Obtiene la información básica de la ficha de clasificadores documentales.
	 * 
	 * @param id
	 *            Identificador de la ficha.
	 * @return Ficha de clasificadores documentales.
	 */
	public InfoBFichaClfVO getInfoBFicha(String id) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id)).toString();

		return (InfoBFichaClfVO) getVO(qual, TABLE_NAME, COL_DEFS_REDUCIDA,
				InfoBFichaClfVO.class);
	}

}
