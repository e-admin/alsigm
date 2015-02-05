package gcontrol.db;

import gcontrol.vos.RolVO;
import gcontrol.vos.UsuarioVO;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.HashMap;
import java.util.List;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.util.ArrayUtils;

/**
 * DBEntity para acceder a la tabla ASCAROLUSR.
 */
public class RolUsuarioDBEntityImpl extends DBEntity implements
		IRolUsuarioDBEntity {

	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = "ASCAROLUSR";

	/** Nombre de columnas */
	public static final String ID_ROL_COLUMN_NAME = "idrol";
	public static final String TIPO_MODULO_COLUMN_NAME = "tipomodulo";
	public static final String ID_USUARIO_COLUMN_NAME = "idusuario";

	/** Definiciones de columna */
	public static final DbColumnDef CAMPO_ID_ROL = new DbColumnDef(null,
			TABLE_NAME, ID_ROL_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_TIPO_MODULO = new DbColumnDef(null,
			TABLE_NAME, TIPO_MODULO_COLUMN_NAME, DbDataType.SHORT_INTEGER, 0,
			false);
	public static final DbColumnDef CAMPO_ID_USUARIO = new DbColumnDef(null,
			TABLE_NAME, ID_USUARIO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] {
			CAMPO_ID_ROL, CAMPO_TIPO_MODULO, CAMPO_ID_USUARIO };

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
	public RolUsuarioDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public RolUsuarioDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene los roles de un usuario.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @return Roles del usuario.
	 */
	public List getRolesUsuario(String idUsuario) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_USUARIO,
						idUsuario))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(TABLE_NAME, CAMPO_ID_ROL,
						RolDBEntityImpl.TABLE_NAME, RolDBEntityImpl.CAMPO_ID))
				.append(" ORDER BY ")
				.append(RolDBEntityImpl.CAMPO_NOMBRE.getName());

		HashMap pairsTableNameColsDefs = new HashMap();
		pairsTableNameColsDefs.put(TABLE_NAME, new DbColumnDef[0]);
		pairsTableNameColsDefs.put(RolDBEntityImpl.TABLE_NAME,
				RolDBEntityImpl.COL_DEFS);

		return getVOS(qual.toString(), pairsTableNameColsDefs, RolVO.class);
	}

	/**
	 * Obtiene los usuarios que tienen asociado un determinado rol
	 * 
	 * @param idRol
	 *            Identificador de rol
	 * @return Lista de usuarios {@link UsuarioVO}
	 */
	public List getUsuariosByRol(String idRol) {
		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_ROL, idRol))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(CAMPO_ID_USUARIO,
						UsuarioDBEntityImpl.CAMPO_ID));
		String[] queryTables = { TABLE_NAME, UsuarioDBEntityImpl.TABLE_NAME };
		return getVOS(qual.toString(), ArrayUtils.join(queryTables, ","),
				UsuarioDBEntityImpl.COL_DEFS, UsuarioVO.class);
	}

	/**
	 * Elimina roles del conjunto de roles asociados a un usuario
	 * 
	 * @param idUsuario
	 *            Identificador de usuario. Si es nulo se eliminan todos los
	 *            usuarios de los roles suministrados
	 * @param roles
	 *            Lista de identificadores de los roles a eliminar En caso de
	 *            ser null se eliminan todos los roles del usuario
	 */
	public void removeRolesUsuario(String idUsuario, String[] roles) {
		StringBuffer qual = new StringBuffer("WHERE 1=1 ");
		if (idUsuario != null)
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CAMPO_ID_USUARIO, idUsuario));
		if (roles != null)
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_ID_ROL, roles));
		deleteVO(qual.toString(), TABLE_NAME);
	}

	/**
	 * Incorpora un rol al conjunto de roles asociados a un usuario
	 * 
	 * @param idUsuario
	 *            Identificador de usuario
	 * @param rol
	 *            Rol a incorporar {@link gcontrol.vos.RolVO}
	 */
	public void insertRolUsuario(String idUsuario, RolVO rol) {
		final RolUsuarioVO rolUsuario = new RolUsuarioVO(idUsuario,
				rol.getId(), rol.getTipoModulo());
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COL_DEFS, rolUsuario);
				DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
						inputRecord);
			}
		};
		command.execute();
	}

	/**
	 * Desasigna todos los usuarios actualmente asignados a los roles que se
	 * indican
	 * 
	 * @param roles
	 *            Lista de identificadores de rol
	 */
	public void clearUsuariosRol(String[] roles) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateInTokenField(CAMPO_ID_ROL, roles));
		deleteVO(qual.toString(), TABLE_NAME);
	}

	public class RolUsuarioVO {
		String idUsuario;
		String idRol;
		int tipoModulo;

		public RolUsuarioVO(String idUsuario, String idRol, int tipoModulo) {
			super();
			this.idUsuario = idUsuario;
			this.idRol = idRol;
			this.tipoModulo = tipoModulo;
		}

		public String getIdRol() {
			return idRol;
		}

		public String getIdUsuario() {
			return idUsuario;
		}

		public int getTipoModulo() {
			return tipoModulo;
		}
	}
}