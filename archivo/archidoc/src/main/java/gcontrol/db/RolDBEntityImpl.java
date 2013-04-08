package gcontrol.db;

import gcontrol.vos.RolVO;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.List;

import common.ConfigConstants;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.definitions.ArchivoModules;

/**
 * DBEntity para acceder a la tabla ASCAROL.
 */
public class RolDBEntityImpl extends DBEntity implements IRolDBEntity {

	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = "ASCAROL";

	/** Nombre de columnas */
	public static final String ID_COLUMN_NAME = "id";
	public static final String NOMBRE_COLUMN_NAME = "nombre";
	public static final String TIPO_MODULO_COLUMN_NAME = "tipomodulo";
	public static final String DESCRIPCION_COLUMN_NAME = "descripcion";

	/** Definiciones de columna */
	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_NOMBRE = new DbColumnDef(null,
			TABLE_NAME, NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, false);
	public static final DbColumnDef CAMPO_TIPO_MODULO = new DbColumnDef(null,
			TABLE_NAME, TIPO_MODULO_COLUMN_NAME, DbDataType.SHORT_INTEGER, 0,
			true);
	public static final DbColumnDef CAMPO_DESCRIPCION = new DbColumnDef(null,
			TABLE_NAME, DESCRIPCION_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			true);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] { CAMPO_ID,
			CAMPO_NOMBRE, CAMPO_TIPO_MODULO, CAMPO_DESCRIPCION };

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
	 *            Pool de conexiones de base de datos.
	 */
	public RolDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public RolDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene la lista de roles asociados a alguno de los modulos indicados.
	 * 
	 * @param modules
	 *            Lista de identificadores de modulo. Si es nulo dev todos los
	 *            roles del sistema
	 * @return Lista de roles {@link gcontrol.vos.RolVO}
	 */
	public List getRoles(int[] modules) {
		StringBuffer qual = new StringBuffer();
		if (modules != null && modules.length > 0)
			qual.append("WHERE ").append(
					DBUtils.generateORTokens(CAMPO_TIPO_MODULO, modules));

		if (!ConfigConstants.getInstance().getMostrarDocVitales()) {
			if (modules != null && modules.length > 0)
				qual.append(" AND ");
			else
				qual.append(" WHERE ");

			qual.append(DBUtils.generateNEQTokenField(CAMPO_TIPO_MODULO,
					ArchivoModules.DOCUMENTOS_VITALES_MODULE));

		}

		qual.append(" ORDER BY ").append(CAMPO_NOMBRE.getQualifiedName());

		return getRoles(qual.toString());
	}

	public List getRoles(String qual) {
		return getVOS(qual, TABLE_NAME, COL_DEFS, RolVO.class);
	}

	public RolVO getRol(String qual) {
		return (RolVO) getVO(qual, TABLE_NAME, COL_DEFS, RolVO.class);
	}

	/**
	 * Obtiene la informacion de un rol de usuario
	 * 
	 * @param idRol
	 *            Identificador de rol
	 * @return Datos de rol {@link RolVO}
	 */
	public RolVO getRolById(String idRol) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, idRol));
		return getRol(qual.toString());
	}

	/**
	 * Inserta un rol en la base de datos
	 * 
	 * @param rol
	 *            Datos de un rol
	 */
	public void insertRol(final RolVO rol) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				rol.setId(getGuid(rol.getId()));
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COL_DEFS, rol);
				DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
						inputRecord);
			}
		};
		command.execute();
	}

	/**
	 * Actualiza en la base de datos la informacion de un rol
	 * 
	 * @param rol
	 *            Datos del rol a actualizar
	 */
	public void updateRol(RolVO rol) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, rol.getId()));
		updateVO(qual.toString(), TABLE_NAME, COL_DEFS, rol);
	}

	/**
	 * Elimina de la base de datos roles de usuario
	 * 
	 * @param roles
	 *            Lista de identificadores de rol a eliminar
	 */
	public void eliminarRoles(String[] roles) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateInTokenField(CAMPO_ID, roles));
		deleteVO(qual.toString(), TABLE_NAME);
		qual.setLength(0);
		qual.append("WHERE ").append(
				DBUtils.generateInTokenField(
						PermisoRolDBEntityImpl.CAMPO_ID_ROL, roles));
		deleteVO(qual.toString(), PermisoRolDBEntityImpl.TABLE_NAME);
	}
}