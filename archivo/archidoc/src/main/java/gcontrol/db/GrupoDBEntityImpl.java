package gcontrol.db;

import gcontrol.vos.GrupoVO;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;
import ieci.core.guid.GuidManager;

import java.util.List;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.util.StringUtils;

/**
 * DBEntity para acceder a la tabla ASCAGRUPO.
 */
public class GrupoDBEntityImpl extends DBEntity implements IGrupoDBEntity {

	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = "ASCAGRUPO";

	/** Nombre de columnas */
	public static final String ID_COLUMN_NAME = "id";
	public static final String NOMBRE_COLUMN_NAME = "nombre";
	public static final String ID_ARCHIVO_COLUMN_NAME = "idarchivo";
	public static final String DESCRIPCION_COLUMN_NAME = "descripcion";
	public static final String INFO_COLUMN_NAME = "info";

	/** Definiciones de columna */
	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_NOMBRE = new DbColumnDef(null,
			TABLE_NAME, NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 254, false);
	public static final DbColumnDef CAMPO_ID_ARCHIVO = new DbColumnDef(null,
			TABLE_NAME, ID_ARCHIVO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, true);
	public static final DbColumnDef CAMPO_DESCRIPCION = new DbColumnDef(null,
			TABLE_NAME, DESCRIPCION_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			true);
	public static final DbColumnDef CAMPO_INFO = new DbColumnDef(null,
			TABLE_NAME, INFO_COLUMN_NAME, DbDataType.SHORT_TEXT, 1024, true);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] { CAMPO_ID,
			CAMPO_NOMBRE, CAMPO_ID_ARCHIVO, CAMPO_DESCRIPCION, CAMPO_INFO };

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
	public GrupoDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public GrupoDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene los grupos asociados a un determinado archivo de custodia
	 * 
	 * @param idArchivo
	 *            Identificador de archivo
	 */
	public List getGruposArchivo(String idArchivo) {
		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_ARCHIVO,
						idArchivo)).append(" ORDER BY ").append(CAMPO_NOMBRE);
		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS, GrupoVO.class);
	}

	/**
	 * Obtiene los grupos existentes
	 * 
	 * @return Listado de grupos existentes
	 */
	public List getGrupos() {
		StringBuffer qual = new StringBuffer("ORDER BY ").append(CAMPO_NOMBRE);
		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS, GrupoVO.class);
	}

	/**
	 * Obtiene un grupo a partir de su identificador
	 * 
	 * @param id
	 *            Identificador del grupo
	 * @return Grupo asociado al id
	 */
	public GrupoVO getGrupo(String id) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, id));

		return (GrupoVO) getVO(qual.toString(), TABLE_NAME, COL_DEFS,
				GrupoVO.class);
	}

	/**
	 * Inserta en la base de datos un grupo
	 * 
	 * @param grupo
	 *            Datos de grupo
	 */
	public void insertGrupo(final GrupoVO grupo) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				grupo.setId(getGuid(grupo.getId()));
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COL_DEFS, grupo);
				DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
						inputRecord);
			}
		};
		command.execute();
	}

	/**
	 * Actualiza en la base de datos la informacion de un grupo de usuairos
	 * 
	 * @param grupo
	 *            Datos de grupo
	 */
	public void updateGrupo(GrupoVO grupo) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, grupo.getId()));
		updateVO(qual.toString(), TABLE_NAME, COL_DEFS, grupo);
	}

	/**
	 * Elimina de la base de datos grupos de usuario
	 * 
	 * @param grupos
	 *            Lista de identificadores de grupo a eliminar
	 */
	public void eliminarGrupos(String[] grupos) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateInTokenField(CAMPO_ID, grupos));
		deleteVO(qual.toString(), TABLE_NAME);
		qual.setLength(0);
		qual.append("WHERE ").append(
				DBUtils.generateInTokenField(
						GrupoUsuarioDBEntityImpl.CAMPO_ID_GRUPO, grupos));
		deleteVO(qual.toString(), GrupoUsuarioDBEntityImpl.TABLE_NAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gcontrol.db.GrupoDBEntity#getGrupos(java.lang.String[])
	 */
	public List getGrupos(String[] ids) {
		StringBuffer qual = new StringBuffer().append("WHERE ")
				.append(DBUtils.generateORTokens(CAMPO_ID, ids))
				.append(" ORDER BY ").append(CAMPO_NOMBRE);

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS, GrupoVO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gcontrol.db.GrupoDBEntity#findByName(java.lang.String)
	 */
	public List findByName(String query) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateLikeTokenField(CAMPO_NOMBRE, query, true));
		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS, GrupoVO.class);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * gcontrol.db.GrupoDBEntity#getGrupoXNombreConIdDistinto(java.lang.String,
	 * java.lang.String)
	 */
	public List getGruposXNombreConIdDistinto(String id, String nombre) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateLikeTokenField(CAMPO_NOMBRE, nombre));

		if (!StringUtils.isBlank(id)) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateNEQTokenField(CAMPO_ID, id));
		}

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS, GrupoVO.class);
	}

}