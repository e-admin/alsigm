package gcontrol.db;

import gcontrol.vos.CAOrganoVO;
import gcontrol.vos.UsuarioOrganoVO;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;

/**
 * DBEntity para acceder a la tabla ASCAUSRORG.
 */
public class OrganoUsuarioDBEntityImpl extends DBEntity implements
		IOrganoUsuarioDBEntity {

	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = "ASCAUSRORG";

	/** Nombre de columnas */
	public static final String ID_USUARIO_COLUMN_NAME = "idusuario";
	public static final String ID_ORGANO_COLUMN_NAME = "idorgano";
	public static final String FECHA_INI_COLUMN_NAME = "fechaini";
	public static final String FECHA_FIN_COLUMN_NAME = "fechafin";

	/** Definiciones de columna */
	public static final DbColumnDef CAMPO_ID_USUARIO = new DbColumnDef(null,
			TABLE_NAME, ID_USUARIO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);
	public static final DbColumnDef CAMPO_ID_ORGANO = new DbColumnDef(null,
			TABLE_NAME, ID_ORGANO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_FECHA_INI = new DbColumnDef(null,
			TABLE_NAME, FECHA_INI_COLUMN_NAME, DbDataType.DATE_TIME, 0, false);
	public static final DbColumnDef CAMPO_FECHA_FIN = new DbColumnDef(null,
			TABLE_NAME, FECHA_FIN_COLUMN_NAME, DbDataType.DATE_TIME, 0, false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] {
			CAMPO_ID_USUARIO, CAMPO_ID_ORGANO, CAMPO_FECHA_INI, CAMPO_FECHA_FIN };

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
	public OrganoUsuarioDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public OrganoUsuarioDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene el organo de un usuario valido para ese organo(comprueba las
	 * fechas de ini y fin del usu en el organo)
	 *
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @return Roles del usuario.
	 */
	public CAOrganoVO getCAOrganoUsuario(String idUsuario) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_USUARIO,
						idUsuario))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(TABLE_NAME,
						CAMPO_ID_ORGANO, CAOrganoDbEntityImpl.TABLE_NAME_ORG,
						CAOrganoDbEntityImpl.CAMPO_ID));

		HashMap pairsTableNameColsDefs = new HashMap();
		pairsTableNameColsDefs.put(TABLE_NAME, new DbColumnDef[0]);
		pairsTableNameColsDefs.put(CAOrganoDbEntityImpl.TABLE_NAME_ORG,
				CAOrganoDbEntityImpl.COLS_DEF_ASCAORG);

		return (CAOrganoVO) getVO(qual.toString(), pairsTableNameColsDefs,
				CAOrganoVO.class);
	}

	/**
	 * Obtiene la lista de identificadores de usuarios validos (comprueba la
	 * fecha de ini y fin ) de órganos.
	 *
	 * @param idOrgs
	 *            Lista de identificadores de órganos.
	 * @return Lista de identificadores de usuarios.
	 */
	public List getUsuariosValidosEnOrganos(List idOrgs) {
		List usuarios = new ArrayList();
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateInTokenField(CAMPO_ID_ORGANO, idOrgs))
				.append(" AND ")
				.append(CAMPO_FECHA_INI.getQualifiedName())
				.append("<=")
				.append(DBUtils.getNativeSysDateSyntax(getConnection()))
				.append(" AND ")
				.append(DBUtils.ABRIR_PARENTESIS)
				.append(CAMPO_FECHA_FIN.getQualifiedName())
				.append(">")
				.append(DBUtils.getNativeSysDateSyntax(getConnection()))
				.append(" OR ")
				.append(DBUtils.generateIsNullCondition(getConnection(),
						CAMPO_FECHA_FIN))
				.append(DBUtils.CERRAR_PARENTESIS);

		// Recuperar el CAMPO_ID_USUARIO
		DbColumnDef[] CUSTOM_COL_DEFS = new DbColumnDef[] { CAMPO_ID_USUARIO };

		// Leer los órganos
		List organos = getVOS(qual.toString(), TABLE_NAME, CUSTOM_COL_DEFS,
				UsuarioOrganoVO.class);
		if (organos != null) {
			// Recoger los identificadores de usuarios
			for (int i = 0; i < organos.size(); i++)
				usuarios.add(((UsuarioOrganoVO) organos.get(i)).getIdUsuario());
		}

		return usuarios;
	}

	public UsuarioOrganoVO getUsuarioOrgano(String idUsuario) {
		// List usuarios = null;

		StringBuffer qual = new StringBuffer().append("WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_ID_USUARIO, idUsuario));

		return (UsuarioOrganoVO) getVO(qual.toString(), TABLE_NAME, COL_DEFS,
				UsuarioOrganoVO.class);
	}

	public void insertUsuarioOrgano(UsuarioOrganoVO usuarioOrganoVO) {

		insertVO(TABLE_NAME, COL_DEFS, usuarioOrganoVO);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see gcontrol.db.IOrganoUsuarioDBEntity#getOrgano(java.lang.String[])
	 */
	public List getOrganos(String[] ids) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateORTokens(CAMPO_ID_ORGANO, ids))
				.toString();

		return getVOS(qual, TABLE_NAME, COL_DEFS, CAOrganoVO.class);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see gcontrol.db.IOrganoUsuarioDBEntity#updateUsuarioOrgano(gcontrol.vos.
	 * UsuarioOrganoVO)
	 */
	public void updateUsuarioOrgano(UsuarioOrganoVO usuarioOrganoVO) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_USUARIO,
						usuarioOrganoVO.getIdUsuario())).toString();

		updateVO(qual, TABLE_NAME, COL_DEFS, usuarioOrganoVO);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * gcontrol.db.IOrganoUsuarioDBEntity#removeOrganosUsuario(java.lang.String)
	 */
	public void deleteOrganosUsuario(String idUsuario) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_USUARIO,
						idUsuario)).toString();

		deleteVO(qual, TABLE_NAME);

	}

}