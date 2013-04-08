package gcontrol.db;

import gcontrol.vos.UsuarioVO;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import se.usuarios.TipoUsuario;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.TableDef;
import common.exceptions.TooManyResultsException;
import common.pagination.PageInfo;
import common.util.ArrayUtils;
import common.util.ListUtils;

/**
 * DBEntity para acceder a la tabla ASCAUSUARIO.
 */
public class UsuarioDBEntityImpl extends DBEntity implements IUsuarioDBEntity {

	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = "ASCAUSUARIO";

	/** Nombre de columnas */
	public static final String ID_COLUMN_NAME = "id";
	public static final String NOMBRE_COLUMN_NAME = "nombre";
	public static final String APELLIDOS_COLUMN_NAME = "apellidos";
	public static final String EMAIL_COLUMN_NAME = "email";
	public static final String DIRECCION_COLUMN_NAME = "direccion";
	public static final String TIPO_COLUMN_NAME = "tipo";
	public static final String HABILITADO_COLUMN_NAME = "habilitado";
	public static final String FMAXVIGENCIA_COLUMN_NAME = "fmaxvigencia";
	public static final String IDUSRSEXTGESTOR_COLUMN_NAME = "idusrsextgestor";
	public static final String DESCRIPCION_COLUMN_NAME = "descripcion";
	public static final String IDUSRSISTORG_COLUMN_NAME = "idusrsistorg";

	/** Definiciones de columna */
	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_NOMBRE = new DbColumnDef(null,
			TABLE_NAME, NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 254, false);
	public static final DbColumnDef CAMPO_APELLIDOS = new DbColumnDef(null,
			TABLE_NAME, APELLIDOS_COLUMN_NAME, DbDataType.SHORT_TEXT, 254, true);
	public static final DbColumnDef CAMPO_EMAIL = new DbColumnDef(null,
			TABLE_NAME, EMAIL_COLUMN_NAME, DbDataType.SHORT_TEXT, 254, true);
	public static final DbColumnDef CAMPO_DIRECCION = new DbColumnDef(null,
			TABLE_NAME, DIRECCION_COLUMN_NAME, DbDataType.SHORT_TEXT, 254, true);
	public static final DbColumnDef CAMPO_TIPO = new DbColumnDef(null,
			TABLE_NAME, TIPO_COLUMN_NAME, DbDataType.SHORT_TEXT, 1, false);
	public static final DbColumnDef CAMPO_HABILITADO = new DbColumnDef(null,
			TABLE_NAME, HABILITADO_COLUMN_NAME, DbDataType.SHORT_TEXT, 1, false);
	public static final DbColumnDef CAMPO_FMAXVIGENCIA = new DbColumnDef(null,
			TABLE_NAME, FMAXVIGENCIA_COLUMN_NAME, DbDataType.DATE_TIME, 0, true);
	public static final DbColumnDef CAMPO_IDUSRSEXTGESTOR = new DbColumnDef(
			null, TABLE_NAME, IDUSRSEXTGESTOR_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 64, false);
	public static final DbColumnDef CAMPO_DESCRIPCION = new DbColumnDef(null,
			TABLE_NAME, DESCRIPCION_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			true);
	public static final DbColumnDef CAMPO_IDUSRSISTORG = new DbColumnDef(null,
			TABLE_NAME, IDUSRSISTORG_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			true);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] { CAMPO_ID,
			CAMPO_NOMBRE, CAMPO_APELLIDOS, CAMPO_EMAIL, CAMPO_DIRECCION,
			CAMPO_TIPO, CAMPO_HABILITADO, CAMPO_FMAXVIGENCIA,
			CAMPO_IDUSRSEXTGESTOR, CAMPO_DESCRIPCION, CAMPO_IDUSRSISTORG };

	/** Lista de nombres de columnas. */
	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(COL_DEFS);

	public static final DbColumnDef CAMPO_NOMBRE_USUARIO = new DbColumnDef(
			"nombreusuario", TABLE_NAME, NOMBRE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 254, false);
	public static final DbColumnDef CAMPO_APELLIDOS_USUARIO = new DbColumnDef(
			"apellidosusuario", TABLE_NAME, APELLIDOS_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 254, false);

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
	public UsuarioDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public UsuarioDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene la información de un usuario en archivo.
	 *
	 * @param userType
	 *            Tipo de usuario.
	 * @param idUsrExtGestor
	 *            Identificador del usuario en el sistema gestor.
	 * @return Información de un usuario de archivo.
	 */
	public UsuarioVO getUsuario(String userType, String idUsrExtGestor) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDUSRSEXTGESTOR,
						idUsrExtGestor)).append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPO, userType));

		return (UsuarioVO) getVO(qual.toString(), TABLE_NAME, COL_DEFS,
				UsuarioVO.class);
	}

	/**
	 * Obtiene la información de un superusuario en archivo.
	 *
	 * @param nombreUsuario
	 *            Nombre del usuario que se busca
	 * @return Información de un superusuario de archivo.
	 */
	public UsuarioVO getSuperusuario(String nombreUsuario) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPO,
						TipoUsuario.ADMINISTRADOR))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_NOMBRE,
						nombreUsuario));

		return (UsuarioVO) getVO(qual.toString(), TABLE_NAME, COL_DEFS,
				UsuarioVO.class);
	}

	/**
	 * Obtiene la información de un usuario en archivo.
	 *
	 * @param idUsuario
	 *            Identificador del usuario en la aplicación.
	 * @return Información de un usuario de archivo.
	 */
	public UsuarioVO getUsuario(String idUsuario) {
		StringBuffer qual = new StringBuffer().append("WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_ID, idUsuario));

		return (UsuarioVO) getVO(qual.toString(), TABLE_NAME, COL_DEFS,
				UsuarioVO.class);
	}

	/**
	 * Obtiene la información de un conjunto de usuarios en archivo.
	 *
	 * @param idUsuario
	 *            Identificadores de usuario en la aplicación.
	 * @return Información de usuarios de archivo.
	 */
	public List<UsuarioVO> getUsuarios(String[] idUsuario) {
		StringBuffer qual = new StringBuffer().append("WHERE ")
				.append(DBUtils.generateORTokens(CAMPO_ID, idUsuario))
				.append(getOrderByClause());

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS, UsuarioVO.class);
	}

	/**
	 * Obtiene la información de un conjunto de usuarios en archivo.
	 *
	 * @param idsUsuario
	 *            Identificadores de los usuario en el sistema de organización.
	 * @param permisos
	 *            Permisos de los usuarios.
	 * @return Información de usuarios de archivo.
	 */
	public List getUsuariosXIdsEnSistOrg(String[] idsUsuario, String[] permisos) {
		Map<String, DbColumnDef[]> pairs = new HashMap<String, DbColumnDef[]>();
		pairs.put(TABLE_NAME, COL_DEFS);
		pairs.put(RolUsuarioDBEntityImpl.TABLE_NAME, null);
		pairs.put(PermisoRolDBEntityImpl.TABLE_NAME, null);

		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateInTokenField(CAMPO_IDUSRSISTORG,
						idsUsuario))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(CAMPO_ID,
						RolUsuarioDBEntityImpl.CAMPO_ID_USUARIO))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						RolUsuarioDBEntityImpl.CAMPO_ID_ROL,
						PermisoRolDBEntityImpl.CAMPO_ID_ROL));

		if (!ArrayUtils.isEmpty(permisos))
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(
							PermisoRolDBEntityImpl.CAMPO_PERM, permisos));

		qual.append(getOrderByClause());

		return getDistinctVOS(qual.toString(), pairs, UsuarioVO.class);
	}

	/**
	 * Obtiene la lista de usuarios activos.
	 *
	 * @return Lista de usuarios activos.
	 */
	public List<UsuarioVO> getUsuariosActivos() {
		StringBuffer qual = new StringBuffer().append("WHERE ")
				.append(getFilterUsuariosActivos()).append(getOrderByClause());

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS, UsuarioVO.class);
	}

	/**
	 * Obtiene una lista de usuarios que tiene alguno de los roles indicados
	 *
	 * @param ids
	 *            [] Listado de los identificadores de los roles que pueden
	 *            tener los usuarios
	 * @return Listado de usuarios con alguno de los roles indicados.
	 */
	public List getUsuariosWithRoles(String ids[]) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateJoinCondition(TABLE_NAME, CAMPO_ID,
						RolUsuarioDBEntityImpl.TABLE_NAME,
						RolUsuarioDBEntityImpl.CAMPO_ID_USUARIO))
				.append(" AND ")
				.append(DBUtils.generateInTokenField(
						RolUsuarioDBEntityImpl.CAMPO_ID_ROL, ids))
				.append(getOrderByClause());

		String[] queryTables = { TABLE_NAME, RolUsuarioDBEntityImpl.TABLE_NAME };

		return getDistinctVOS(qual.toString(),
				ArrayUtils.join(queryTables, ","), COL_DEFS, UsuarioVO.class);
	}

	/**
	 * Obtiene una lista de usuarios que tienen alguno de los permisos
	 * indicados.
	 *
	 * @param permisos
	 *            Listado de permisos
	 * @return Listado de usuarios ({@link UsuarioVO}).
	 */
	public List getUsuariosWithPermissions(String[] permisos) {
		final StringBuffer qual = new StringBuffer().append("WHERE ")
				.append(getFilterUsuariosActivos()).append(" AND ")
				.append(getFilterWithPermissions(permisos))
				.append(getOrderByClause());

		final String[] queryTables = { TABLE_NAME,
				RolUsuarioDBEntityImpl.TABLE_NAME,
				PermisoRolDBEntityImpl.TABLE_NAME };

		List usuarios = getDistinctVOS(qual.toString(),
				ArrayUtils.join(queryTables, ","), COL_DEFS, UsuarioVO.class);

		// Obtener los usuarios administradores
		final StringBuffer qualAdm = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPO,
						TipoUsuario.ADMINISTRADOR)).append(getOrderByClause());

		List usuariosAdm = getDistinctVOS(qualAdm.toString(), TABLE_NAME,
				COL_DEFS, UsuarioVO.class);

		if (!ListUtils.isEmpty(usuariosAdm)) {
			usuarios.addAll(usuariosAdm);
		}

		return usuarios;

	}

	public List getUsuariosWithPermissions(String[] permisos, String filtro) {
		final StringBuffer qual = new StringBuffer().append(DBUtils.WHERE)
				.append(getFilterUsuariosActivos()).append(DBUtils.AND)
				.append(getFilterWithPermissions(permisos));

		if (StringUtils.isNotEmpty(filtro)) {
			qual.append(DBUtils.AND)
					.append(DBUtils.generateLikeTokenField(
							CAMPO_NOMBRE_USUARIO,
							StringUtils.replace(filtro, " ", "%")))
					.append(DBUtils.OR)
					.append(DBUtils.generateLikeTokenField(
							CAMPO_APELLIDOS_USUARIO,
							StringUtils.replace(filtro, " ", "%")));
		}

		qual.append(getOrderByClause());

		final String[] queryTables = { TABLE_NAME,
				RolUsuarioDBEntityImpl.TABLE_NAME,
				PermisoRolDBEntityImpl.TABLE_NAME };

		List usuarios = getDistinctVOS(qual.toString(),
				ArrayUtils.join(queryTables, ","), COL_DEFS, UsuarioVO.class);

		// Obtener los usuarios administradores
		final StringBuffer qualAdm = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_TIPO,
						TipoUsuario.ADMINISTRADOR));

		if (StringUtils.isNotEmpty(filtro)) {
			qualAdm.append(DBUtils.AND + "(")
					.append(DBUtils.generateLikeTokenField(
							CAMPO_NOMBRE_USUARIO,
							StringUtils.replace(filtro, " ", "%")))
					.append(DBUtils.OR)
					.append(DBUtils.generateLikeTokenField(
							CAMPO_APELLIDOS_USUARIO,
							StringUtils.replace(filtro, " ", "%")))
					.append(") ");
		}
		qualAdm.append(getOrderByClause());

		List usuariosAdm = getDistinctVOS(qualAdm.toString(), TABLE_NAME,
				COL_DEFS, UsuarioVO.class);

		if (!ListUtils.isEmpty(usuariosAdm)) {
			usuarios.addAll(usuariosAdm);
		}
		return usuarios;
	}

	/**
	 * Obtiene una lista de usuarios que tienen alguno de los permisos indicados
	 * y pertenecen al archivo especificado.
	 *
	 * @param permisos
	 *            Listado de permisos
	 * @param idArchivos
	 *            Identificadores de archivos.
	 * @return Listado de usuarios ({@link UsuarioVO}).
	 */
	public List getUsuariosWithPermissionsAndArchive(String[] permisos,
			String[] idArchivos) {
		final StringBuffer qual = new StringBuffer().append("WHERE ")
				.append(getFilterUsuariosActivos()).append(" AND ")
				.append(getFilterWithPermissions(permisos)).append(" AND ")
				.append(getFilterWithArchive(idArchivos))
				.append(getOrderByClause());

		final String[] queryTables = { TABLE_NAME,
				RolUsuarioDBEntityImpl.TABLE_NAME,
				PermisoRolDBEntityImpl.TABLE_NAME,
				GrupoUsuarioDBEntityImpl.TABLE_NAME,
				GrupoDBEntityImpl.TABLE_NAME };

		List usuarios = getDistinctVOS(qual.toString(),
				ArrayUtils.join(queryTables, ","), COL_DEFS, UsuarioVO.class);

		// Obtener los usuarios administradores
		final StringBuffer qualAdm = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPO,
						TipoUsuario.ADMINISTRADOR)).append(getOrderByClause());

		List usuariosAdm = getDistinctVOS(qualAdm.toString(), TABLE_NAME,
				COL_DEFS, UsuarioVO.class);

		if (!ListUtils.isEmpty(usuariosAdm)) {
			usuarios.addAll(usuariosAdm);
		}

		return usuarios;
	}

	public UsuarioVO insert(final UsuarioVO user) {
		user.setId(getGuid(user.getId()));
		insertVO(TABLE_NAME, COL_DEFS, user);
		return user;
	}

	public void updateUsuario(final UsuarioVO user) {
		final String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, user.getId()))
				.toString();

		updateVO(qual, TABLE_NAME, COL_DEFS, user);
	}

	/**
	 * Recupera todos los usuarios dados de alta en el sistema
	 *
	 * @param pageInfo
	 *            Datos para paginación de resultados
	 * @return Lista de usuarios {@link UsuarioVO}
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List<UsuarioVO> getUsuarios(PageInfo pageInfo)
			throws TooManyResultsException {
		StringBuffer qual = new StringBuffer();

		// Orden
		if (pageInfo != null) {
			Map<String, Object> criteriosOrdenacion = new HashMap<String, Object>();
			criteriosOrdenacion.put("usuario", new DbColumnDef[] {
					CAMPO_APELLIDOS, CAMPO_NOMBRE });
			criteriosOrdenacion.put("tipo", CAMPO_TIPO);
			criteriosOrdenacion.put("habilitado", CAMPO_HABILITADO);

			return getVOS(qual.toString(),
					pageInfo.getOrderBy(criteriosOrdenacion), TABLE_NAME,
					COL_DEFS, UsuarioVO.class, pageInfo);
		} else {
			qual.append(getOrderByClause());

			return getVOS(qual.toString(), TABLE_NAME, COL_DEFS,
					UsuarioVO.class);
		}
	}

	/**
	 * Busqueda de usuarios por nombre
	 *
	 * @param query
	 *            Cadena que debe estar contenida en el nombre o apellidos de
	 *            los usuarios
	 * @return Lista de usuarios {@link UsuarioVO}
	 */
	public List<UsuarioVO> findByName(String query) {

		// Sólo mostrar los usuarios que pertenecen a un tipo visible
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateInTokenField(CAMPO_TIPO,
						ConfiguracionSistemaArchivoFactory
								.getConfiguracionSistemaArchivo()
								.getConfiguracionControlAcceso()
								.getIdsUsuarios()))
				.append("AND ( ")
				.append(DBUtils.generateLikeTokenField(CAMPO_NOMBRE, query,
						true))
				.append(" OR ")
				.append(DBUtils.generateLikeTokenField(CAMPO_APELLIDOS, query,
						true)).append(")");
		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS, UsuarioVO.class);
	}

	/**
	 * Busqueda de usuarios por nombre y apellidos
	 *
	 * @param query
	 *            Cadena con el nombre y los apellidos de los usuarios
	 * @return Lista de usuarios {@link UsuarioVO}
	 */
	// public List findByNameSurname(String query) {
	// StringBuffer qual = new StringBuffer("WHERE UPPER(")
	// .append(CAMPO_NOMBRE.getQualifiedName())
	// .append(" || ' ' || ")
	// .append(CAMPO_APELLIDOS.getQualifiedName())
	// .append(" ) like '%")
	// .append(query.toUpperCase())
	// .append("%'");
	// return getVOS(qual.toString(), TABLE_NAME, COL_DEFS, UsuarioVO.class);
	// }
	public List<UsuarioVO> findByNameSurname(String name, String surname) {
		StringBuffer qual = new StringBuffer("WHERE ");

		if (name == null) {
			qual.append("(")
					.append(DBUtils.generateLikeTokenField(CAMPO_NOMBRE, "",
							true)).append(DBUtils.OR)
					.append(CAMPO_NOMBRE.getQualifiedName())
					.append(" IS NULL)");
		} else
			qual.append(DBUtils
					.generateLikeTokenField(CAMPO_NOMBRE, name, true));

		qual.append(" AND ");

		if (surname == null) {
			qual.append("(")
					.append(DBUtils.generateLikeTokenField(CAMPO_APELLIDOS, "",
							true)).append(DBUtils.OR)
					.append(CAMPO_APELLIDOS.getQualifiedName())
					.append(" IS NULL)");
		} else
			qual.append(DBUtils.generateLikeTokenField(CAMPO_APELLIDOS,
					surname, true));

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS, UsuarioVO.class);
	}

	/**
	 *
	 * SELECT * FROM ASCAUSUARIO WHERE UPPER(ASCAUSUARIO.apellidos) like
	 * ('%APELLIDOS1 %') AND UPPER(ASCAUSUARIO.nombre) like ('%NOMBRE%')
	 *
	 */

	/*
	 * (non-Javadoc)
	 *
	 * @see gcontrol.db.IUsuarioDBEntity#removeUsuario(gcontrol.vos.UsuarioVO)
	 */
	public void deleteUsuario(UsuarioVO user) {
		final String qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_ID, user.getId()))
				.toString();

		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {

				DbDeleteFns.delete(conn, TABLE_NAME, qual);

			}

		};

		command.execute();

	}

	/**
	 * Busca en la base de datos los usuarios que verifican los criterios que se
	 * indican
	 *
	 * @param tipoUsuario
	 *            Tipo de usuario. Puede ser nulo.
	 * @param searchTokenNombre
	 *            . Texto que debe estar contenido en el nombre del usuario.
	 *            Puede ser nulo
	 * @param searchTokenApellidos
	 *            . Texto que debe estar contenido en los apellidos del usuario.
	 *            Puede ser nulo
	 * @return Lista de usurios existentes en la base de datos y que verifican
	 *         los criterios indicados {@link UsuarioVO}
	 */
	public List<UsuarioVO> findUsuarios(String tipoUsuario,
			String searchTokenNombre, String searchTokenApellidos) {
		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotBlank(tipoUsuario))
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateEQTokenField(CAMPO_TIPO, tipoUsuario));

		if (StringUtils.isNotBlank(searchTokenNombre))
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateLikeTokenField(CAMPO_NOMBRE,
							searchTokenNombre));

		if (StringUtils.isNotBlank(searchTokenApellidos))
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateLikeTokenField(CAMPO_APELLIDOS,
							searchTokenApellidos));

		qual.append(getOrderByClause());

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS, UsuarioVO.class);
	}

	private String getOrderByClause() {
		return new StringBuffer().append(" ORDER BY ")
				.append(CAMPO_APELLIDOS.getQualifiedName()).append(",")
				.append(CAMPO_NOMBRE.getQualifiedName()).toString();
	}

	private String getFilterUsuariosActivos() {
		return new StringBuffer()
				.append(DBUtils.generateEQTokenField(CAMPO_HABILITADO, "S"))
				.append(" AND (")
				.append(DBUtils.generateEQTokenField(CAMPO_FMAXVIGENCIA, null))
				.append(" OR ").append(CAMPO_FMAXVIGENCIA.getQualifiedName())
				.append(" > ")
				.append(DBUtils.getNativeSysDateSyntax(getConnection()))
				.append(")").toString();
	}

	private String getFilterWithPermissions(String[] permissions) {
		/*
		 * Tablas utilizadas: RolUsuarioDBEntityImpl.TABLE_NAME
		 * PermisoRolDBEntityImpl.TABLE_NAME
		 */
		return new StringBuffer()
				.append(DBUtils.generateJoinCondition(CAMPO_ID,
						RolUsuarioDBEntityImpl.CAMPO_ID_USUARIO))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						RolUsuarioDBEntityImpl.CAMPO_ID_ROL,
						PermisoRolDBEntityImpl.CAMPO_ID_ROL))
				.append(" AND ")
				.append(DBUtils.generateInTokenField(
						PermisoRolDBEntityImpl.CAMPO_PERM, permissions))
				.toString();
	}

	private String getFilterWithArchive(String[] archiveIds) {
		/*
		 * Tablas utilizadas: GrupoUsuarioDBEntityImpl.TABLE_NAME
		 * GrupoDBEntityImpl.TABLE_NAME
		 */
		return new StringBuffer()
				.append(DBUtils.generateJoinCondition(CAMPO_ID,
						GrupoUsuarioDBEntityImpl.CAMPO_ID_USUARIO))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						GrupoUsuarioDBEntityImpl.CAMPO_ID_GRUPO,
						GrupoDBEntityImpl.CAMPO_ID))
				.append(" AND ")
				.append(DBUtils.generateInTokenField(
						GrupoDBEntityImpl.CAMPO_ID_ARCHIVO, archiveIds))
				.toString();
	}

	public List<UsuarioVO> getUsuariosByTipo(String tipo, String filtro) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_TIPO, tipo));

		if (StringUtils.isNotBlank(filtro)) {
			qual.append(DBUtils.AND)
					.append(" (")
					.append(DBUtils.generateLikeTokenField(CAMPO_NOMBRE,
							filtro, true))
					.append(DBUtils.OR)
					.append(DBUtils.generateLikeTokenField(CAMPO_APELLIDOS,
							filtro, true)).append(") ");

		}

		qual.append(getOrderByClause());

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS, UsuarioVO.class);
	}

	public List<UsuarioVO> getUsuariosVigentesAsociadosAOrgano(String idOrgano) {

		StringBuffer subquery = new StringBuffer(DBUtils.SELECT)
				.append(OrganoUsuarioDBEntityImpl.CAMPO_ID_USUARIO
						.getQualifiedName())
				.append(DBUtils.FROM)
				.append(new TableDef(OrganoUsuarioDBEntityImpl.TABLE_NAME)
						.getDeclaration())
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(
						OrganoUsuarioDBEntityImpl.CAMPO_ID_ORGANO, idOrgano))
				.append(DBUtils.AND)
				.append(OrganoUsuarioDBEntityImpl.CAMPO_FECHA_INI
						.getQualifiedName())
				.append(DBUtils.MENOR_IGUAL)
				.append(DBUtils.getNativeSysDateSyntax(getConnection()))
				.append(DBUtils.AND)
				.append(DBUtils.ABRIR_PARENTESIS)
				.append(OrganoUsuarioDBEntityImpl.CAMPO_FECHA_FIN
						.getQualifiedName())
				.append(DBUtils.MAYOR)
				.append(DBUtils.getNativeSysDateSyntax(getConnection()))
				.append(DBUtils.OR)
				.append(DBUtils.generateIsNullCondition(getConnection(),
						OrganoUsuarioDBEntityImpl.CAMPO_FECHA_FIN))
				.append(DBUtils.CERRAR_PARENTESIS);

		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateInTokenFieldSubQuery(CAMPO_ID, subquery.toString()));

		qual.append(getOrderByClause());

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS, UsuarioVO.class);
	}
}