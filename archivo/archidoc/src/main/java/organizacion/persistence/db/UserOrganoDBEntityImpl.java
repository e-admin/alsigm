package organizacion.persistence.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbUtil;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import organizacion.model.vo.UserOrganoVO;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;

/**
 * DBEntity para acceder a la tabla USER_ORGANO.
 */
public class UserOrganoDBEntityImpl extends DBEntity implements
		IUserOrganoDBEntity {

	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = "AOUSRORGV";

	/** Nombre de columnas */
	public static final String ID_USUARIO_COLUMN_NAME = "idusuario";

	public static final String NOMBRE_USUARIO_COLUMN_NAME = "nombreusuario";

	public static final String ID_ORGANO_COLUMN_NAME = "idorgano";

	/** Definiciones de columna */
	public static final DbColumnDef CAMPO_ID_USUARIO = new DbColumnDef(null,
			TABLE_NAME, ID_USUARIO_COLUMN_NAME, DbDataType.SHORT_TEXT, 64,
			false);

	public static final DbColumnDef CAMPO_NOMBRE_USUARIO = new DbColumnDef(
			null, TABLE_NAME, NOMBRE_USUARIO_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 254, false);

	public static final DbColumnDef CAMPO_ID_ORGANO = new DbColumnDef(null,
			TABLE_NAME, ID_ORGANO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] {
			CAMPO_ID_USUARIO, CAMPO_NOMBRE_USUARIO, CAMPO_ID_ORGANO };

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
	public UserOrganoDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public UserOrganoDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	private UserOrganoVO getUsuarioOrganoVO(final String qual) {
		return (UserOrganoVO) getVO(qual, TABLE_NAME, COL_DEFS,
				UserOrganoVO.class);
	}

	private List getUsuariosOrganosVO(final String qual) {
		return getVOS(qual, TABLE_NAME, COL_DEFS, UserOrganoVO.class);
	}

	public UserOrganoVO insertUsuarioOrgano(final UserOrganoVO userOrganoVO) {
		insertVO(TABLE_NAME, COL_DEFS, userOrganoVO);
		return userOrganoVO;
	}

	public void updateUsuarioOrgano(final UserOrganoVO userOrganoVO) {
		final String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_USUARIO,
						userOrganoVO.getIdUsuario())).toString();

		updateVO(qual, TABLE_NAME, COL_DEFS, userOrganoVO);
	}

	public void deleteUsuarioOrgano(final String idUsuario) {
		final String qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_ID_USUARIO, idUsuario))
				.toString();

		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual);
			}
		};
		command.execute();
	}

	public List getUsuarios() {

		StringBuffer qual = new StringBuffer();
		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS, UserOrganoVO.class);
	}

	public UserOrganoVO getUsuarioOrganoById(String idUsuario) {
		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotBlank(idUsuario))
			qual.append(DBUtils.getCondition(qual.toString()))
					.append(DBUtils.generateLikeTokenField(CAMPO_ID_USUARIO,
							idUsuario));

		qual.append(getOrderByClause());

		return getUsuarioOrganoVO(qual.toString());
	}

	public UserOrganoVO getUsuarioOrgano(final String idUsuario,
			final String idOrgano) {

		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotBlank(idUsuario))
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateEQTokenField(CAMPO_ID_USUARIO, idUsuario));

		if (StringUtils.isNotBlank(idOrgano))
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateEQTokenField(CAMPO_ID_ORGANO, idOrgano));

		return getUsuarioOrganoVO(qual.toString());
	}

	public List searchUsuariosOrganos(final UserOrganoVO userOrganoVO) {
		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotBlank(userOrganoVO.getNombreUsuario()))
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateLikeTokenField(CAMPO_NOMBRE_USUARIO,
							userOrganoVO.getNombreUsuario()));

		qual.append(getOrderByClause());

		return getUsuariosOrganosVO(qual.toString());
	}

	private String getOrderByClause() {
		return new StringBuffer().append(" ORDER BY ")
				.append(CAMPO_NOMBRE_USUARIO.getQualifiedName()).toString();
	}

	public List getUsuariosByOrgano(final String idOrgano) {

		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotBlank(idOrgano))
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateEQTokenField(CAMPO_ID_ORGANO, idOrgano));

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS, UserOrganoVO.class);
	}

	public void deleteUsuariosOrgano(String idOrgano) {
		final String qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_ID_ORGANO, idOrgano))
				.toString();

		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual);
			}
		};
		command.execute();
	}
}