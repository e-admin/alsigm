package organizacion.persistence.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import organizacion.model.vo.OrganizacionVO;
import organizacion.model.vo.UserOrganoVO;

import util.CollectionUtils;

import common.db.ConstraintType;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.JoinDefinition;
import common.db.SigiaDbInputRecord;
import common.db.TableDef;
import common.util.StringUtils;

/**
 * DBEntity para acceder a la tabla ORGANIZACION.
 */
public class OrganizacionDBEntityImpl extends DBEntity implements
		IOrganizacionDBEntity {

	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = "AOESTRORG";

	/** Nombre de columnas */
	public static final String ID_COLUMN_NAME = "id";

	public static final String CODIGO_COLUMN_NAME = "codigo";

	public static final String NOMBRE_COLUMN_NAME = "nombre";

	public static final String TIPO_COLUMN_NAME = "tipo";

	public static final String NIVEL_COLUMN_NAME = "nivel";

	public static final String ID_PADRE_COLUMN_NAME = "idorgpadre";

	public static final String ESTADO_COLUMN_NAME = "estado";

	public static final String FECHA_INI_COLUMN_NAME = "finiciovigencia";

	public static final String FECHA_FIN_COLUMN_NAME = "ffinvigencia";

	public static final String DESCRIPCION_COLUMN_NAME = "descripcion";

	/** Definiciones de columna */
	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_CODIGO = new DbColumnDef(null,
			TABLE_NAME, CODIGO_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, false);

	public static final DbColumnDef CAMPO_NOMBRE = new DbColumnDef(null,
			TABLE_NAME, NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 254, false);

	public static final DbColumnDef CAMPO_TIPO = new DbColumnDef(null,
			TABLE_NAME, TIPO_COLUMN_NAME, DbDataType.SHORT_INTEGER, false);

	public static final DbColumnDef CAMPO_NIVEL = new DbColumnDef(null,
			TABLE_NAME, NIVEL_COLUMN_NAME, DbDataType.SHORT_INTEGER, false);

	public static final DbColumnDef CAMPO_ID_PADRE = new DbColumnDef(null,
			TABLE_NAME, ID_PADRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, true);

	public static final DbColumnDef CAMPO_ESTADO = new DbColumnDef(null,
			TABLE_NAME, ESTADO_COLUMN_NAME, DbDataType.SHORT_INTEGER, false);

	public static final DbColumnDef CAMPO_FECHA_INI_VIGENCIA = new DbColumnDef(
			null, TABLE_NAME, FECHA_INI_COLUMN_NAME, DbDataType.DATE_TIME, 0,
			false);

	public static final DbColumnDef CAMPO_FECHA_FIN_VIGENCIA = new DbColumnDef(
			null, TABLE_NAME, FECHA_FIN_COLUMN_NAME, DbDataType.DATE_TIME, 0,
			true);

	public static final DbColumnDef CAMPO_DESCRIPCION = new DbColumnDef(null,
			TABLE_NAME, DESCRIPCION_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			true);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] { CAMPO_ID,
			CAMPO_CODIGO, CAMPO_NOMBRE, CAMPO_TIPO, CAMPO_NIVEL,
			CAMPO_ID_PADRE, CAMPO_ESTADO, CAMPO_FECHA_INI_VIGENCIA,
			CAMPO_FECHA_FIN_VIGENCIA, CAMPO_DESCRIPCION };

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
	public OrganizacionDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public OrganizacionDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	private OrganizacionVO getOrganizacionVO(final String qual) {
		return (OrganizacionVO) getVO(qual, TABLE_NAME, COL_DEFS,
				OrganizacionVO.class);
	}

	private List getOrganizacionesVO(final String qual) {
		return getVOS(qual, TABLE_NAME, COL_DEFS, OrganizacionVO.class);
	}

	public OrganizacionVO insertOrganizacion(final OrganizacionVO organizacionVO) {
		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {

				organizacionVO.setId(getGuid(organizacionVO.getId()));
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COL_DEFS, organizacionVO);
				DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
						inputRecord);
			}
		};
		command.execute();
		return organizacionVO;
	}

	public void updateOrganizacion(final OrganizacionVO organizacionVO) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, organizacionVO.getId()));
		updateVO(qual.toString(), TABLE_NAME, COL_DEFS, organizacionVO);
	}

	public void deleteOrganizacion(final String idOrganizacion) {
		final StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, idOrganizacion));
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual.toString());
			}
		};
		command.execute();
	}

	public OrganizacionVO getOrganizacionById(final String idOrganizacion) {

		String qual = new StringBuffer()
				.append("WHERE")
				.append(DBUtils.generateTokenFieldQualified(CAMPO_ID,
						idOrganizacion, TABLE_NAME, ConstraintType.EQUAL))
				.toString();

		return getOrganizacionVO(qual.toString());

	}

	public List searchOrganizaciones(final OrganizacionVO organizacionVO) {
		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotBlank(organizacionVO.getCodigo()))
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateEQTokenField(CAMPO_CODIGO,
							organizacionVO.getCodigo()));

		if (StringUtils.isNotBlank(organizacionVO.getNombre()))
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateEQTokenField(CAMPO_NOMBRE,
							organizacionVO.getNombre()));

		if (organizacionVO.getTipo() != null)
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateEQTokenField(CAMPO_TIPO, organizacionVO
							.getTipo().intValue()));

		if (organizacionVO.getEstado() != null)
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateEQTokenField(CAMPO_ESTADO, organizacionVO
							.getEstado().intValue()));

		qual.append(getOrderByClause());

		return getOrganizacionesVO(qual.toString());
	}

	// select aoestrorg.* from aoestrorg inner join aousrorgv on aoestrorg.id =
	// aousrorgv.idorgano where aousrorgv.nombreusuario like '%lucas%'
	public List searchOrganizaciones(final OrganizacionVO organizacionVO,
			final UserOrganoVO userOrganoVO) {

		StringBuffer qual = new StringBuffer();
		TableDef tableDef = new TableDef(TABLE_NAME);
		TableDef tableDefSinAlias = new TableDef(TABLE_NAME, "");
		JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(tableDef, CAMPO_ID), new DbColumnDef(
						new TableDef(UserOrganoDBEntityImpl.TABLE_NAME),
						UserOrganoDBEntityImpl.CAMPO_ID_ORGANO)), };

		if (StringUtils.isNotBlank(userOrganoVO.getNombreUsuario())) {
			qual.append(DBUtils.generateInnerJoinCondition(tableDefSinAlias,
					joins));
			qual.append(" WHERE ").append(
					DBUtils.generateLikeTokenField(
							UserOrganoDBEntityImpl.CAMPO_NOMBRE_USUARIO,
							userOrganoVO.getNombreUsuario()));
		}

		if (StringUtils.isNotBlank(organizacionVO.getCodigo()))
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateLikeTokenField(CAMPO_CODIGO,
							organizacionVO.getCodigo()));

		if (StringUtils.isNotBlank(organizacionVO.getNombre()))
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateLikeTokenField(CAMPO_NOMBRE,
							organizacionVO.getNombre()));

		if (organizacionVO.getTipo() != null)
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateEQTokenField(CAMPO_TIPO, organizacionVO
							.getTipo().intValue()));

		if (organizacionVO.getEstado() != null)
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateEQTokenField(CAMPO_ESTADO, organizacionVO
							.getEstado().intValue()));

		qual.append(getOrderByClause());

		return getOrganizacionesVO(qual.toString());
	}

	public List getOrganizaciones() {
		StringBuffer qual = new StringBuffer();
		qual.append(DBUtils.getCondition(qual.toString())).append(
				DBUtils.generateNEQTokenField(CAMPO_ESTADO,
						Integer.parseInt(IOrganizacionDBEntity.HISTORICO)));
		qual.append(" ORDER BY ").append(CAMPO_ID_PADRE).append(", ")
				.append(CAMPO_ID);

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS,
				OrganizacionVO.class);
	}

	public List getOrganizacionesByIdPadre(final String idPadre) {
		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotBlank(idPadre))
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateEQTokenField(CAMPO_ID_PADRE, idPadre));

		qual.append(DBUtils.getCondition(qual.toString())).append(
				DBUtils.generateNEQTokenField(CAMPO_ESTADO,
						Integer.parseInt(IOrganizacionDBEntity.HISTORICO)));

		qual.append(getOrderByClause());

		return getOrganizacionesVO(qual.toString());
	}

	public List getInstituciones() {
		StringBuffer qual = new StringBuffer();

		qual.append(DBUtils.getCondition(qual.toString())).append(
				DBUtils.generateEQTokenField(CAMPO_TIPO, INSTITUCION));

		qual.append(DBUtils.getCondition(qual.toString())).append(
				DBUtils.generateNEQTokenField(CAMPO_ESTADO,
						Integer.parseInt(IOrganizacionDBEntity.HISTORICO)));

		qual.append(getOrderByClause());

		return getOrganizacionesVO(qual.toString());
	}

	private String getOrderByClause() {
		return new StringBuffer().append(" ORDER BY ")
				.append(CAMPO_CODIGO.getQualifiedName()).toString();
	}

	public List getOrganizacionesEstadoPadre(String estado) {
		StringBuffer qual = new StringBuffer();
		if (IOrganizacionDBEntity.BORRADOR.equals(estado)) {
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateNEQTokenField(CAMPO_ESTADO,
							Integer.parseInt(IOrganizacionDBEntity.HISTORICO)));
		}

		if (IOrganizacionDBEntity.VIGENTE.equals(estado)) {
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateEQTokenField(CAMPO_ESTADO,
							Integer.parseInt(IOrganizacionDBEntity.VIGENTE)));
		}

		// qual.append(" ORDER BY ").append(CAMPO_ID_PADRE).append(", ").append(CAMPO_ID);
		qual.append(getOrderByClause());

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS,
				OrganizacionVO.class);
	}

	public List getAncestors(String idElemento) {
		List ret = new LinkedList();
		ret = getAncestorsRecursiva(idElemento);
		if (!CollectionUtils.isEmpty(ret)) {
			Collections.reverse(ret);
		}
		return ret;
	}

	private List getAncestorsRecursiva(String idElemento) {
		List ret = new LinkedList();
		OrganizacionVO elementoPadre = getElementoPadre(idElemento);
		if (elementoPadre != null) {
			ret.add(elementoPadre);
			if (elementoPadre.getIdOrgPadre() != null) {
				List abuelos = getAncestorsRecursiva(elementoPadre.getId());
				if (abuelos != null)
					ret.addAll(abuelos);

			}
		}
		return ret;
	}

	private OrganizacionVO getElementoPadre(String idElemento) {
		OrganizacionVO elemento = getOrganizacionById(idElemento);
		OrganizacionVO elementoPadre = null;
		if (elemento != null)
			elementoPadre = getOrganizacionById(elemento.getIdOrgPadre());
		return elementoPadre;
	}
}