package gcontrol.db;

import gcontrol.vos.CAOrganoVO;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;
import ieci.core.guid.GuidManager;

import java.util.List;

import common.Constants;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.util.StringUtils;

/**
 * Implementación de los métodos de acceso a datos referentes a órganos
 */
public class CAOrganoDbEntityImpl extends DBEntity implements ICAOrganoDbEntity {

	public static final String TABLE_NAME_ORG = "ASCAORGANO";

	public static final String ID_COLUMN_NAME = "ID";

	public static final String CODIGO_COLUMN_NAME = "CODIGO";

	public static final String NOMBRE_COLUMN_NAME = "NOMBRE";

	public static final String NOMBRELARGO_COLUMN_NAME = "NOMBRELARGO";

	public static final String IDARCHIVORECEPTOR_COLUMN_NAME = "IDARCHIVORECEPTOR";

	public static final String SISTEXTGESTOR_COLUMN_NAME = "SISTEXTGESTOR";

	public static final String IDORGSEXTGESTOR_COLUMN_NAME = "IDORGSEXTGESTOR";

	public static final String VIGENTE_COLUMN_NAME = "VIGENTE";

	public static final String DESCRIPCION_COLUMN_NAME = "DESCRIPCION";

	public static final DbColumnDef CAMPO_ID = new DbColumnDef("idOrg",
			TABLE_NAME_ORG, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_CODIGO = new DbColumnDef(null,
			TABLE_NAME_ORG, CODIGO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef CAMPO_NOMBRE = new DbColumnDef(null,
			TABLE_NAME_ORG, NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef CAMPO_NOMBRE_LARGO = new DbColumnDef(null,
			TABLE_NAME_ORG, NOMBRELARGO_COLUMN_NAME, DbDataType.SHORT_TEXT,
			1024, false);

	public static final DbColumnDef CAMPO_IDARCHIVORECEPTOR = new DbColumnDef(
			null, TABLE_NAME_ORG, IDARCHIVORECEPTOR_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_SISTEXTGESTOR = new DbColumnDef(null,
			TABLE_NAME_ORG, SISTEXTGESTOR_COLUMN_NAME, DbDataType.SHORT_TEXT,
			32, false);

	public static final DbColumnDef CAMPO_IDORGSEXTGESTOR = new DbColumnDef(
			null, TABLE_NAME_ORG, IDORGSEXTGESTOR_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 64, false);

	public static final DbColumnDef CAMPO_VIGENTE = new DbColumnDef(null,
			TABLE_NAME_ORG, VIGENTE_COLUMN_NAME, DbDataType.SHORT_TEXT, 1,
			false);

	public static final DbColumnDef CAMPO_DESCRIPCION = new DbColumnDef(null,
			TABLE_NAME_ORG, DESCRIPCION_COLUMN_NAME, DbDataType.SHORT_TEXT, 1,
			false);

	public static final DbColumnDef[] COLS_DEF_ASCAORG = new DbColumnDef[] {
			CAMPO_ID, CAMPO_CODIGO, CAMPO_NOMBRE, CAMPO_NOMBRE_LARGO,
			CAMPO_IDARCHIVORECEPTOR, CAMPO_SISTEXTGESTOR,
			CAMPO_IDORGSEXTGESTOR, CAMPO_VIGENTE, CAMPO_DESCRIPCION };
	public final static String COLS_NAMES = DbUtil
			.getColumnNames(COLS_DEF_ASCAORG);

	public static final DbColumnDef CAMPO_NOMBRE_ORGANO = new DbColumnDef(
			"nombreorgano", TABLE_NAME_ORG, NOMBRE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME_ORG;
	}

	/**
	 * Obtiene la lista de órganos productores.
	 * 
	 * @param vigente
	 *            Indica si los órganos deben ser vigentes o no vigentes. Si es
	 *            nulo, se devolverán todos los órganos productores.
	 * @return Lista de órganos productores.
	 */
	public List getCAOrgProductoresVOList(Boolean vigente) {
		StringBuffer qual = new StringBuffer();

		if (vigente != null) {
			qual.append(DBUtils.WHERE);
			qual.append(DBUtils.generateEQTokenField(CAMPO_VIGENTE, vigente
					.booleanValue() ? Constants.TRUE_STRING
					: Constants.FALSE_STRING));
		}

		qual.append(DBUtils.generateOrderByDesc(CAMPO_VIGENTE))

		.append(Constants.COMMA).append(NOMBRE_COLUMN_NAME);

		return getCAOrgProductoresVO(qual.toString());
	}

	/**
	 * Obtiene la información de un órgano.
	 * 
	 * @param idOrgano
	 *            Identificador del órgano.
	 * @return Información de un órgano.
	 */
	public CAOrganoVO getCAOrgProductorVOXId(String idOrgano) {
		String qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_ID, idOrgano)).toString();

		return getCAOrgProductorVO(qual);
	}

	public CAOrganoVO getCAOrgProductorVOByNombreLargo(String nombreLargo) {
		String qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_NOMBRE_LARGO, nombreLargo))
				.toString();

		return getCAOrgProductorVO(qual);
	}

	public List getCAOrgProductorVOXId(String[] idOrgano) {
		StringBuffer qual = new StringBuffer(" WHERE 1=1 ");
		if (idOrgano != null)
			qual.append("AND ").append(
					DBUtils.generateORTokens(CAMPO_ID, idOrgano));

		return getCAOrgProductoresVO(qual.toString());
	}

	/**
	 * Obtiene la información de un órgano.
	 * 
	 * @param sistExtGestor
	 *            Sistema Gestor de Organismos externo.
	 * @param idEnSistExt
	 *            Identificador del organismo en el Sistema Gestor de Organismos
	 *            externo.
	 * @return Información de un órgano.
	 */
	public CAOrganoVO getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestor(
			final String sistExtGestor, final String idEnSistExt) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_SISTEXTGESTOR,
						sistExtGestor))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDORGSEXTGESTOR,
						idEnSistExt)).toString();

		return getCAOrgProductorVO(qual);
	}

	public List getCAOrgProductorVOXSistExtGestorYVigencia(
			final String sistExtGestor, final boolean vigente) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_SISTEXTGESTOR,
						sistExtGestor))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_VIGENTE,
						vigente ? Constants.TRUE_STRING
								: Constants.FALSE_STRING)).toString();

		return getCAOrgProductoresVO(qual);
	}

	public CAOrganoVO getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestorYVigencia(
			final String sistExtGestor, final String idEnSistExt,
			final boolean vigente) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_SISTEXTGESTOR,
						sistExtGestor))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDORGSEXTGESTOR,
						idEnSistExt))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_VIGENTE,
						vigente ? Constants.TRUE_STRING
								: Constants.FALSE_STRING)).toString();

		return getCAOrgProductorVO(qual);
	}

	/**
	 * Obtiene la lista de órganos a partir del Sistema Gestor Externo y una
	 * lista de identificadores en ese sistema.
	 * 
	 * @param sistExtGestor
	 *            Sistema Gestor de Organismos Externo.
	 * @param idsEnSistExt
	 *            Lista de identificadores en el Sistema Gestor de Organismos
	 *            Externo.
	 * @return Lista de órganos.
	 */
	public List getCAOrgProductorVOListXSistExtGestorYIdOrgsExtGestor(
			final String sistExtGestor, final Object idsEnSistExt) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_SISTEXTGESTOR,
						sistExtGestor))
				.append(" AND ")
				.append(DBUtils.generateInTokenField(CAMPO_IDORGSEXTGESTOR,
						idsEnSistExt)).append(" ORDER BY ")
				.append(CAMPO_NOMBRE.getQualifiedName()).toString();

		return getCAOrgProductoresVO(qual);
	}

	private CAOrganoVO getCAOrgProductorVO(final String qual) {
		return (CAOrganoVO) getVO(qual, TABLE_NAME_ORG, COLS_DEF_ASCAORG,
				CAOrganoVO.class);
	}

	private List getCAOrgProductoresVO(final String qual) {

		return getVOS(qual, TABLE_NAME_ORG, COLS_DEF_ASCAORG, CAOrganoVO.class);
	}

	public CAOrganoVO insertCAOrgVO(final CAOrganoVO caOrganoVO) {
		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				if (caOrganoVO.getIdOrg() == null)
					caOrganoVO.setIdOrg(getGuid(caOrganoVO.getIdOrg()));

				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLS_DEF_ASCAORG, caOrganoVO);
				DbInsertFns.insert(conn, TABLE_NAME_ORG, COLS_NAMES,
						inputRecord);
			}
		};
		command.execute();
		return caOrganoVO;
	}

	// TODO VERIFICAR QUE SOLO HAY QUE ACTUALIZAR ESTA TABLA Y NADA DE
	// DESCRIPTOR
	public void updateCAOrgVO(CAOrganoVO organo) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, organo.getIdOrg()));
		updateVO(qual.toString(), TABLE_NAME_ORG, COLS_DEF_ASCAORG, organo);
	}

	public void eliminarOrgano(String idOrgano) {
		final StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, idOrgano));
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME_ORG, qual.toString());
			}
		};
		command.execute();
	}

	/**
	 * Constructor.
	 * 
	 * @param dataSource
	 *            Pool de conexiones de base de datos.
	 */
	public CAOrganoDbEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public CAOrganoDbEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Recupera los órganos por código y/o nombre.
	 * 
	 * @param code
	 *            Código del órgano.
	 * @param name
	 *            Nombre del órgano.
	 * @return Lista de órganos {@link CAOrganoVO}.
	 */
	public List findByCodeAndName(String code, String name, String vigente) {
		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotBlank(code)) {
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateLikeTokenField(CAMPO_CODIGO, code, true));
		}

		if (StringUtils.isNotBlank(name)) {
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateLikeTokenField(CAMPO_NOMBRE, name, true));
		}

		if (StringUtils.isNotBlank(vigente)) {
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateEQTokenField(CAMPO_VIGENTE, vigente));
		}

		qual.append(" ORDER BY ").append(CAMPO_CODIGO.getQualifiedName());

		return getCAOrgProductoresVO(qual.toString());

	}

	/**
	 * Recupera los órganos cuyo nombre contiene la cadena suministrada
	 * 
	 * @param query
	 *            Patrón de búsqueda a localizar en el nombre del órgano
	 * @param externalSystem
	 *            Identificador del sistema externo del que se ha importado el
	 *            órgano. Puede ser nulo
	 * @return Lista de órganos cuyo nombre contiene el patrón indicado
	 *         {@link CAOrganoVO}
	 */
	public List findByName(String query, String externalSystem) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateLikeTokenField(CAMPO_NOMBRE, query, true));
		if (externalSystem != null)
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CAMPO_SISTEXTGESTOR,
							externalSystem));
		qual.append(" ORDER BY ").append(CODIGO_COLUMN_NAME);

		return getCAOrgProductoresVO(qual.toString());

	}
}
