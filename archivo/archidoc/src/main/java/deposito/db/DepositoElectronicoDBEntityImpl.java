package deposito.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbUtil;

import java.util.List;

import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;

import deposito.vos.DepositoElectronicoVO;

/**
 * Implementación de los métodos de acceso a datos referentes a los depósitos
 * electrónicos.
 */
public class DepositoElectronicoDBEntityImpl extends DBEntity implements
		IDepositoElectronicoDBEntity {

	/** Logger de la clase. */
	// private static Logger logger =
	// Logger.getLogger(DepositoElectronicoDBEntityImpl.class);

	/** Nombre de la tabla. */
	public static final String TABLE_NAME = "ASGDDEPOSITOELECTRONICO";

	// Nombres de las columnas
	public static final String ID_COLUMN_NAME = "id";
	public static final String ID_EXT_COLUMN_NAME = "idext";
	public static final String NOMBRE_COLUMN_NAME = "nombre";
	public static final String DESCRIPCION_COLUMN_NAME = "descripcion";
	public static final String LOCALIZACION_COLUMN_NAME = "localizacion";
	public static final String USO_FIRMA_COLUMN_NAME = "usofirma";

	// Definiciones de las columnas
	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_ID_EXT = new DbColumnDef(null,
			TABLE_NAME, ID_EXT_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, false);

	public static final DbColumnDef CAMPO_NOMBRE = new DbColumnDef(null,
			TABLE_NAME, NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, false);

	public static final DbColumnDef CAMPO_DESCRIPCION = new DbColumnDef(null,
			TABLE_NAME, DESCRIPCION_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			true);

	public static final DbColumnDef CAMPO_LOCALIZACION = new DbColumnDef(null,
			TABLE_NAME, LOCALIZACION_COLUMN_NAME, DbDataType.LONG_TEXT, false);

	public static final DbColumnDef CAMPO_USO_FIRMA = new DbColumnDef(null,
			TABLE_NAME, USO_FIRMA_COLUMN_NAME, DbDataType.SHORT_TEXT, 1, false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] { CAMPO_ID,
			CAMPO_ID_EXT, CAMPO_NOMBRE, CAMPO_DESCRIPCION, CAMPO_LOCALIZACION,
			CAMPO_USO_FIRMA };

	/** Lista de nombres de columnas. */
	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(COL_DEFS);

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
	 *            Origen de datos.
	 */
	public DepositoElectronicoDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public DepositoElectronicoDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene la lista de depósitos electrónicos.
	 * 
	 * @return Lista de depósitos electrónicos ({@link DepositoElectronicoVO}).
	 */
	public List getDepositosElectronicos() {
		StringBuffer qual = new StringBuffer(" ORDER BY ").append(CAMPO_NOMBRE
				.getQualifiedName());
		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS,
				DepositoElectronicoVO.class);
	}

	/**
	 * Obtiene la información de un depósito electrónico.
	 * 
	 * @param id
	 *            Identificador del depósito electrónico.
	 * @return Depósito electrónico.
	 */
	public DepositoElectronicoVO getDepositoElectronico(String id) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, id));
		return (DepositoElectronicoVO) getVO(qual.toString(), TABLE_NAME,
				COL_DEFS, DepositoElectronicoVO.class);
	}

	/**
	 * Obtiene la información de un depósito electrónico.
	 * 
	 * @param idExt
	 *            Identificador externo del depósito electrónico.
	 * @return Depósito electrónico.
	 */
	public DepositoElectronicoVO getDepositoElectronicoByIdExt(String idExt) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID_EXT, idExt));
		return (DepositoElectronicoVO) getVO(qual.toString(), TABLE_NAME,
				COL_DEFS, DepositoElectronicoVO.class);
	}

	/**
	 * Crea un depósito electrónico.
	 * 
	 * @param deposito
	 *            Información del depósito electrónico.
	 * @return Depósito electrónico creado.
	 */
	public DepositoElectronicoVO insertDepositoElectronico(
			DepositoElectronicoVO deposito) {
		deposito.setId(getGuid(deposito.getId()));
		insertVO(TABLE_NAME, COL_DEFS, deposito);
		return deposito;
	}

	/**
	 * Modifica la información de un depósito electrónico.
	 * 
	 * @param deposito
	 *            Información del depósito electrónico.
	 */
	public void updateDepositoElectronico(DepositoElectronicoVO deposito) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, deposito.getId()));
		updateVO(qual.toString(), TABLE_NAME, COL_DEFS, deposito);
	}

	/**
	 * Elimina un depósito electrónico.
	 * 
	 * @param id
	 *            Identificador del depósito electrónico.
	 */
	public void deleteDepositoElectronico(String id) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, id));
		deleteVO(qual.toString(), TABLE_NAME);
	}
}
