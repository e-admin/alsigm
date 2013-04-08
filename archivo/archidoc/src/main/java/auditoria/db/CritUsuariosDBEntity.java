package auditoria.db;

import gcontrol.db.GrupoDBEntityImpl;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.Collection;
import java.util.HashMap;

import auditoria.vos.CritUsuarioVO;

import common.db.ConstraintType;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;

/**
 * Clase que encapsula todas las definiciones de los datos de los niveles de
 * criticidad de los usuarios , así como de las operaciones que se pueden
 * realizar sobre ellos.
 */
public abstract class CritUsuariosDBEntity extends DBEntity implements
		ICritUsuariosDBEntity {

	/** Nombre de la tabla */
	public static final String TABLE_NAME = "AANAUDITUSR";

	/** Nombre de los campos de la tabla */
	private static final String TIPOAUDITAR_COLUMN_NAME = "TIPOAUDITADO";
	private static final String IDAUDITAR_COLUMN_NAME = "IDAUDITADO";
	private static final String IDNIVEL_COLUMN_NAME = "NIVEL";

	/** Definicion de las columnas */
	public static final DbColumnDef CAMPO_TIPOAUDITAR = new DbColumnDef(
			"TIPOAUDITADO", TABLE_NAME, TIPOAUDITAR_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_IDAUDITAR = new DbColumnDef(
			"IDAUDITADO", TABLE_NAME, IDAUDITAR_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_IDNIVEL = new DbColumnDef("NIVEL",
			TABLE_NAME, IDNIVEL_COLUMN_NAME, DbDataType.LONG_INTEGER, false);

	/** Listado con las definiciones de las columnas */
	public static final DbColumnDef[] COLS_DEFS_CRITUSUARIOS = {
			CAMPO_TIPOAUDITAR, CAMPO_IDAUDITAR, CAMPO_IDNIVEL, };

	public static final String COLUMN_NAMES_CRITUSUARIOS = DbUtil
			.getColumnNames(COLS_DEFS_CRITUSUARIOS);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Constructor por defecto
	 * 
	 * @param dataSource
	 *            Gestor de transacciones
	 */
	public CritUsuariosDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public CritUsuariosDBEntity(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene el nivel de criticidad(log) del grupo de usuarios solicitado
	 * 
	 * @param i
	 *            Identificador del grupo de usuarios del que deseamos obtener
	 *            el nivel de criticidad.
	 * @return Nivel de criticidad para el grupo de usuario o Integer.MIN_VALUE
	 *         en caso de no estar definido.
	 */
	public int getGroupLogLevel(String i) {
		int groupLogLevel = Integer.MIN_VALUE;

		final String finalQual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateTokenFieldQualified(CAMPO_IDAUDITAR,
						i.toString(), TABLE_NAME, ConstraintType.EQUAL))
				.append(" AND ")
				.append(DBUtils.generateTokenFieldQualified(CAMPO_TIPOAUDITAR,
						CritUsuarioVO.TIPO_GRUPO, TABLE_NAME,
						ConstraintType.EQUAL)).toString();

		// Obtenemos la criticidad
		CritUsuarioVO critUsuarioVO = (CritUsuarioVO) getVO(finalQual,
				TABLE_NAME, COLS_DEFS_CRITUSUARIOS, CritUsuarioVO.class);
		// Si está definida la criticidad la devolvemos
		if (critUsuarioVO != null)
			groupLogLevel = critUsuarioVO.getNivel();

		return groupLogLevel;
	}

	/**
	 * Obtiene el nivel de criticidad(log) del rol solicitado
	 * 
	 * @param i
	 *            Identificador del rol del que deseamos obtener el nivel de
	 *            criticidad.
	 * @return Nivel de criticidad para el rol o Integer.MIN_VALUE en caso de no
	 *         esta definido.
	 */
	public int getRolLogLevel(String i) {
		int rolLogLevel = Integer.MIN_VALUE;

		final String finalQual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateTokenFieldQualified(CAMPO_IDAUDITAR,
						i.toString(), TABLE_NAME, ConstraintType.EQUAL))
				.append(" AND ")
				.append(DBUtils.generateTokenFieldQualified(CAMPO_TIPOAUDITAR,
						CritUsuarioVO.TIPO_ROL, TABLE_NAME,
						ConstraintType.EQUAL)).toString();

		// Obtenemos la criticidad
		CritUsuarioVO critUsuarioVO = (CritUsuarioVO) getVO(finalQual,
				TABLE_NAME, COLS_DEFS_CRITUSUARIOS, CritUsuarioVO.class);
		// Si está definida la criticidad la devolvemos
		if (critUsuarioVO != null)
			rolLogLevel = critUsuarioVO.getNivel();

		return rolLogLevel;
	}

	/**
	 * Obtiene el nivel de criticidad(log) del usuariosolicitado
	 * 
	 * @param i
	 *            Identificador del usuario del que deseamos obtener el nivel de
	 *            criticidad.
	 * @return Nivel de criticidad para el grupo de o Integer.MAX_VALUE en caso
	 *         de no esta definido.
	 */
	public int getUserLogLevel(String i) {
		int rolLogLevel = Integer.MAX_VALUE;

		final String finalQual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateTokenFieldQualified(CAMPO_IDAUDITAR,
						i.toString(), TABLE_NAME, ConstraintType.EQUAL))
				.append(" AND ")
				.append(DBUtils.generateTokenFieldQualified(CAMPO_TIPOAUDITAR,
						CritUsuarioVO.TIPO_USUARIO, TABLE_NAME,
						ConstraintType.EQUAL)).toString();

		// Obtenemos la criticidad
		CritUsuarioVO critUsuarioVO = (CritUsuarioVO) getVO(finalQual,
				TABLE_NAME, COLS_DEFS_CRITUSUARIOS, CritUsuarioVO.class);
		// Si está definida la criticidad la devolvemos
		if (critUsuarioVO != null)
			rolLogLevel = critUsuarioVO.getNivel();

		return rolLogLevel;
	}

	/**
	 * Obtiene los grupos de usuarios existentes en la aplicacion con su nivel
	 * de criticidad
	 * 
	 * @return Listado de los grupos de usuarios existentes en la aplicacion
	 */
	public Collection getGroupsWithLevels() {
		final StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateJoinCondition(
						GrupoDBEntityImpl.CAMPO_ID, CAMPO_IDAUDITAR))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPOAUDITAR,
						CritUsuarioVO.TIPO_GRUPO));

		HashMap pairsTableNameColsDefs = new HashMap();
		pairsTableNameColsDefs.put(TABLE_NAME, COLS_DEFS_CRITUSUARIOS);
		DbColumnDef[] COLS_DEFS_GROUP = { new DbColumnDef("name",
				GrupoDBEntityImpl.TABLE_NAME,
				GrupoDBEntityImpl.NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT,
				254, false) };
		pairsTableNameColsDefs.put(GrupoDBEntityImpl.TABLE_NAME,
				COLS_DEFS_GROUP);

		return getVOS(qual.toString(), pairsTableNameColsDefs,
				CritUsuarioVO.class);
		// Collection groups = MOCKUsersService.getGroups();
		//
		// //Añadimos el grupo de usuarios GLOBAL
		// CritUsuarioVO c = new CritUsuarioVO();
		// c.setIdAuditado( GLOBAL_GROUP );
		// c.setTipoAuditado("1");
		// c.setNivel(getUserLogLevel( GLOBAL_GROUP ));
		// c.setName("Global Group");
		// groups.add(c);
		//
		// Iterator it = groups.iterator();
		// while (it.hasNext()) {
		// CritUsuarioVO cu = (CritUsuarioVO)it.next();
		//
		// cu.setNivel( getGroupLogLevel(cu.getIdAuditado()));
		// }
		//
		// return groups;

	}

	/**
	 * Obtiene los usuarios existentes en la aplicacion con su nivel de
	 * criticidad
	 * 
	 * @return Listado de los usuarios existentes en la aplicacion
	 */
	public Collection getUsersWithLevels() {
		// Collection users = MOCKUsersService.getUsers();
		//
		// Iterator it = users.iterator();
		// while (it.hasNext()) {
		// CritUsuarioVO cu = (CritUsuarioVO)it.next();
		//
		// cu.setNivel( getUserLogLevel(cu.getIdAuditado()));
		// }

		return null;
	}

	/**
	 * Actualiza el nivel de criticidad de un grupo de usuarios.
	 * 
	 * @param id
	 *            Identificador del grupo de usuario que deseamos actualzar
	 * @param logLevel
	 *            Nuevo nivel de criticidad para le grupo de usuarios
	 * @param tipo
	 *            Tipo del grupo 0->Usuario o 1->Grupo. -1 si no se desea cribar
	 *            por tipo
	 */
	public void setGroupLogLevel(String id, int logLevel, int tipo) {
		final StringBuffer qual = new StringBuffer().append(" WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_IDAUDITAR, id));

		if (tipo >= 0)
			qual.append(" AND ").append(
					DBUtils.generateTokenFieldQualified(CAMPO_TIPOAUDITAR,
							tipo, TABLE_NAME, ConstraintType.EQUAL));

		CritUsuarioVO cu = new CritUsuarioVO();
		cu.setIdAuditado(id);
		cu.setNivel(logLevel);
		cu.setTipoAuditado("" + CritUsuarioVO.TIPO_GRUPO);

		updateVO(qual.toString(), TABLE_NAME, COLS_DEFS_CRITUSUARIOS, cu);
	}

	/**
	 * Actualiza el nivel de criticidad de un usuarios.
	 * 
	 * @param id
	 *            Identificador del usuario que deseamos actualzar
	 * @param logLevel
	 *            Nuevo nivel de criticidad para el usuario
	 */
	public void setUserLogLevel(String id, int logLevel) {
		final String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDAUDITAR, id))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPOAUDITAR,
						CritUsuarioVO.TIPO_USUARIO)).toString();

		CritUsuarioVO cu = new CritUsuarioVO();
		cu.setIdAuditado(id);
		cu.setNivel(logLevel);
		cu.setTipoAuditado("" + CritUsuarioVO.TIPO_USUARIO);

		updateVO(qual, TABLE_NAME, COLS_DEFS_CRITUSUARIOS, cu);
	}

	/**
	 * Obtiene los datos de un grupo a partir de su identificador
	 * 
	 * @param id
	 *            Identifcador del grupo que deseamos obtener sus datos
	 *            asociados
	 * @param tipo
	 *            Tipo del grupo 0->Usuario o 1->Grupo. -1 si no se desea cribar
	 *            por tipo
	 * @return Datos del grupo asociado a los datos
	 */
	public CritUsuarioVO getGroup(String id, int tipo) {
		StringBuffer finalQual = new StringBuffer().append(" WHERE ").append(
				DBUtils.generateTokenFieldQualified(CAMPO_IDAUDITAR, id,
						TABLE_NAME, ConstraintType.EQUAL));

		if (tipo >= 0)
			finalQual.append(" AND ").append(
					DBUtils.generateTokenFieldQualified(CAMPO_TIPOAUDITAR,
							tipo, TABLE_NAME, ConstraintType.EQUAL));

		// Obtenemos la criticidad
		CritUsuarioVO cu = (CritUsuarioVO) getVO(finalQual.toString(),
				TABLE_NAME, COLS_DEFS_CRITUSUARIOS, CritUsuarioVO.class);

		return cu;
	}

	/**
	 * Realiza el borrado de un grupo con su criticidad asocidad de la tabla de
	 * la base de datos
	 * 
	 * @param id
	 *            Identificador del grupo que deseamos borrar
	 * @param tipo
	 *            Tipo del grupo 0->Usuario o 1->Grupo. -1 si no se desea cribar
	 *            por tipo
	 */
	public void deleteGroupLogLevel(String id, int tipo) {
		final StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_IDAUDITAR, id));

		if (tipo >= 0)
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CAMPO_TIPOAUDITAR, tipo));

		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual.toString());
			}
		};

		command.execute();
	}

	public void deleteLogLevel(int tipo, String[] id) {
		if (id != null && id.length > 0) {
			StringBuffer qual = new StringBuffer(" WHERE ")
					.append(DBUtils.generateInTokenField(CAMPO_IDAUDITAR, id))
					.append(" AND ")
					.append(DBUtils.generateEQTokenField(CAMPO_TIPOAUDITAR,
							tipo));
			deleteVO(qual.toString(), TABLE_NAME);
		}
	}

	/**
	 * Crear el nivel de log de un usuario o grupo de usuarios.
	 * 
	 * @param id
	 *            Identificador del grupo o usuario a crear
	 * @param logLevel
	 *            Nivel de log de la accion
	 * @param tipo
	 *            Tipo del grupo 0->Usuario o 1->Grupo
	 * @return Elemento creado en la abse de datos
	 */
	public CritUsuarioVO insertGroupLogLevel(String id, int logLevel, int tipo) {
		final CritUsuarioVO cu = new CritUsuarioVO();
		cu.setIdAuditado(id);
		cu.setNivel(logLevel);
		cu.setTipoAuditado("" + tipo);

		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLS_DEFS_CRITUSUARIOS, cu);
				DbInsertFns.insert(conn, TABLE_NAME, COLUMN_NAMES_CRITUSUARIOS,
						inputRecord);
			}
		};

		command.execute();

		return cu;
	}
}
