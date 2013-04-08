package gcontrol.db;

import gcontrol.vos.PermisoVO;
import gcontrol.vos.RolVO;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.List;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.JoinDefinition;
import common.db.SigiaDbInputRecord;
import common.db.TableDef;
import common.util.ArrayUtils;
import common.vos.ConsultaConnectBy;

/**
 * DBEntity para acceder a la tabla ASCAPERMGENROL.
 */
public class PermisoRolDBEntityImpl extends DBEntity implements
		IPermisoRolDBEntity {

	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = "ASCAPERMGENROL";

	/** Nombre de columnas */
	public static final String ID_ROL_COLUMN_NAME = "idrol";
	public static final String TIPO_MODULO_COLUMN_NAME = "tipomodulo";
	public static final String PERM_COLUMN_NAME = "perm";

	/** Definiciones de columna */
	public static final DbColumnDef CAMPO_ID_ROL = new DbColumnDef(null,
			TABLE_NAME, ID_ROL_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_TIPO_MODULO = new DbColumnDef(null,
			TABLE_NAME, TIPO_MODULO_COLUMN_NAME, DbDataType.SHORT_INTEGER, 0,
			false);
	public static final DbColumnDef CAMPO_PERM = new DbColumnDef(null,
			TABLE_NAME, PERM_COLUMN_NAME, DbDataType.LONG_INTEGER, 0, false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] {
			CAMPO_ID_ROL, CAMPO_TIPO_MODULO, CAMPO_PERM };

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
	public PermisoRolDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public PermisoRolDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene los permisos de un rol.
	 * 
	 * @param idRol
	 *            Identificador del rol.
	 * @return Permisos del rol.
	 */
	public List getPermisosRol(String idRol) {
		StringBuffer qual = new StringBuffer().append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_ROL, idRol))
				.append(" ORDER BY ").append(CAMPO_PERM.getName());

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS, PermisoVO.class);
	}

	/**
	 * Obtiene los roles que tiene asociado algunos de los permisos indicados
	 * 
	 * @param permisos
	 *            Conjunto de permisos posible de los que deseamos obtener los
	 *            roles
	 * @return Listado de roles que contienen alguno de los permisos
	 */
	public List getRolesPermisos(String permisos[]) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateORTokens(CAMPO_PERM, permisos))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(CAMPO_ID_ROL,
						RolDBEntityImpl.CAMPO_ID));

		String[] queryTables = { TABLE_NAME, RolDBEntityImpl.TABLE_NAME };
		return getDistinctVOS(qual.toString(),
				ArrayUtils.join(queryTables, ","), RolDBEntityImpl.COL_DEFS,
				RolVO.class);
	}

	/**
	 * Establece los permisos de un rol
	 * 
	 * @param id
	 *            Identificador de rol
	 * @param permisosRol
	 *            Lista de permisos a establecer
	 */
	public void setPermisosRol(final RolVO rol, final String[] permisosRol) {
		final StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID_ROL, rol.getId()));
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual.toString());
				SigiaDbInputRecord inputRecord = null;
				PermisoVO unPermiso = null;
				int nPermisos = permisosRol.length;
				for (int i = 0; i < nPermisos; i++) {
					unPermiso = new PermisoVO(rol.getId(), rol.getTipoModulo(),
							Integer.parseInt(permisosRol[i]));
					inputRecord = new SigiaDbInputRecord(COL_DEFS, unPermiso);
					DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
							inputRecord);
				}
			}
		};
		command.execute();
	}

	/**
	 * Quita un conjunto de permisos de un rol
	 * 
	 * @param idRol
	 *            Identificador de rol
	 * @param permisoRol
	 *            Lista de permisos
	 */
	public void quitarPermisosRol(String idRol, String[] permisoRol) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID_ROL, idRol));
		if (permisoRol != null)
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_PERM, permisoRol));
		deleteVO(qual.toString(), TABLE_NAME);
	}

	/**
	 * Agrega un conjunto de permisos a un rol
	 * 
	 * @param idRol
	 *            Identificador de rol
	 * @param permisoRol
	 *            Lista de permisos
	 */
	public void agregarPermisosRol(final RolVO rol, final String[] permisosRol) {
		// POSIBLE PROBLEMA final StringBuffer qual = new StringBuffer("WHERE ")
		// POSIBLE PROBLEMA .append(DBUtils.generateEQTokenField(CAMPO_ID_ROL,
		// rol.getId()));
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				SigiaDbInputRecord inputRecord = null;
				PermisoVO unPermiso = null;
				int nPermisos = permisosRol.length;
				for (int i = 0; i < nPermisos; i++) {
					unPermiso = new PermisoVO(rol.getId(), rol.getTipoModulo(),
							Integer.parseInt(permisosRol[i]));
					inputRecord = new SigiaDbInputRecord(COL_DEFS, unPermiso);
					DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
							inputRecord);
				}
			}
		};
		command.execute();
	}

	/**
	 * Quita todos los permisos asignados al conjunto de roles indicados
	 * 
	 * @param roles
	 *            Conjunto de identificadores de rol
	 */
	public void clearPermisosRol(String[] roles) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateInTokenField(CAMPO_ID_ROL, roles));
		deleteVO(qual.toString(), TABLE_NAME);
	}

	public List getPermisosUsuario(String idUsuario) {
		return getPermisosUsuario(idUsuario, null);
	}

	public List getPermisosUsuario(String idUsuario, String[] permisos) {
		/*
		 * SELECT ASCAPERMGENROL.* FROM ASCAPERMGENROL INNER JOIN ASCAROLUSR ON
		 * ASCAPERMGENROL.IDROL = ASCAROLUSR.IDROL WHERE ASCAROLUSR.IDUSUARIO=''
		 */

		StringBuffer selectClause = new StringBuffer(DBUtils.SELECT)
				.append(DbUtil.getColumnNames(COL_DEFS));

		// Map pairs = new HashMap();

		// INNER JOIN ASCAROLUSR ON ASCAPERMGENROL.IDROL = ASCAROLUSR.IDROL
		JoinDefinition joinRolUsr = new JoinDefinition(CAMPO_ID_ROL,
				RolUsuarioDBEntityImpl.CAMPO_ID_ROL);

		StringBuffer fromClause = new StringBuffer(DBUtils.FROM).append(DBUtils
				.generateLeftOuterJoinCondition(new TableDef(TABLE_NAME),
						new JoinDefinition[] { joinRolUsr, }));

		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(RolUsuarioDBEntityImpl.CAMPO_ID_USUARIO,
						idUsuario));

		if (ArrayUtils.isNotEmpty(permisos)) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateInTokenField(CAMPO_PERM, permisos));
		}

		StringBuffer orderByClause = new StringBuffer(
				DBUtils.generateOrderBy(CAMPO_PERM));

		final ConsultaConnectBy sql = new ConsultaConnectBy(
				selectClause.toString(), fromClause.toString(),
				qual.toString(), null, orderByClause.toString());

		return getVOS(COL_DEFS, sql, PermisoVO.class);
	}

}
