package gcontrol.db;

import gcontrol.vos.GrupoVO;
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
 * DBEntity para acceder a la tabla ASCAUSRGRP.
 */
public class GrupoUsuarioDBEntityImpl extends DBEntity implements
		IGrupoUsuarioDBEntity {

	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = "ASCAUSRGRP";

	/** Nombre de columnas */
	public static final String ID_USUARIO_COLUMN_NAME = "idusuario";
	public static final String ID_GRUPO_COLUMN_NAME = "idgrupo";

	/** Definiciones de columna */
	public static final DbColumnDef CAMPO_ID_USUARIO = new DbColumnDef(null,
			TABLE_NAME, ID_USUARIO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);
	public static final DbColumnDef CAMPO_ID_GRUPO = new DbColumnDef(null,
			TABLE_NAME, ID_GRUPO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] {
			CAMPO_ID_USUARIO, CAMPO_ID_GRUPO };

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
	public GrupoUsuarioDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public GrupoUsuarioDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene los grupos del usuario.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @return Grupos del usuario.
	 */
	public List getGruposUsuario(String idUsuario) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_USUARIO,
						idUsuario))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(TABLE_NAME,
						CAMPO_ID_GRUPO, GrupoDBEntityImpl.TABLE_NAME,
						GrupoDBEntityImpl.CAMPO_ID)).append(" ORDER BY ")
				.append(GrupoDBEntityImpl.CAMPO_NOMBRE.getName());

		HashMap pairsTableNameColsDefs = new HashMap();
		pairsTableNameColsDefs.put(TABLE_NAME, new DbColumnDef[0]);
		pairsTableNameColsDefs.put(GrupoDBEntityImpl.TABLE_NAME,
				GrupoDBEntityImpl.COL_DEFS);

		return getVOS(qual.toString(), pairsTableNameColsDefs, GrupoVO.class);
	}

	/**
	 * Obtiene los usuarios que pertenecen a un grupo
	 * 
	 * @param idGrupo
	 *            Identificador de grupo
	 * @return Lista de usuarios {@link gcontrol.vos.UsuarioVO}
	 */
	public List getUsuariosGrupo(String idGrupo) {
		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_GRUPO, idGrupo))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(CAMPO_ID_USUARIO,
						UsuarioDBEntityImpl.CAMPO_ID));
		String[] queryTables = { TABLE_NAME, UsuarioDBEntityImpl.TABLE_NAME };
		return getVOS(qual.toString(), ArrayUtils.join(queryTables, ","),
				UsuarioDBEntityImpl.COL_DEFS, UsuarioVO.class);
	}

	/**
	 * Incorpora un usuario a un grupo
	 * 
	 * @param idGrupo
	 *            Identificador de grupo
	 * @param idUsuario
	 *            Identificador de usuario
	 */
	public void insertGrupoUsuario(String idGrupo, String idUsuario) {
		final GrupoUsuarioVO grupoUsuario = new GrupoUsuarioVO(idUsuario,
				idGrupo);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COL_DEFS, grupoUsuario);
				DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
						inputRecord);
			}
		};
		command.execute();
	}

	/**
	 * Elimina un conjunto de usuarios de un grupo
	 * 
	 * @param idGrupo
	 *            Identificador de grupo
	 * @param idUsuario
	 *            Lista de identificadores de usuario. En caso de ser null se
	 *            eliminaran todos los usuarios del grupo
	 */
	public void removeGrupoUsuario(String idGrupo, String[] idUsuario) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID_GRUPO, idGrupo));
		if (idUsuario != null)
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_ID_USUARIO, idUsuario));
		deleteVO(qual.toString(), TABLE_NAME);
	}

	public class GrupoUsuarioVO {
		String idUsuario = null;
		String idGrupo = null;

		GrupoUsuarioVO(String idUsuario, String idGrupo) {
			super();
			this.idUsuario = idUsuario;
			this.idGrupo = idGrupo;
		}

		public String getIdGrupo() {
			return idGrupo;
		}

		public String getIdUsuario() {
			return idUsuario;
		}
	}
}