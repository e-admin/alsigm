/**
 *
 */
package salas.db;

import gcontrol.db.ArchivoDbEntityImpl;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import salas.vos.BusquedaUsuarioSalaConsultaVO;
import salas.vos.UsuarioSalasConsultaVO;
import solicitudes.consultas.db.TemaDBEntity;

import common.Constants;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.TableDef;
import common.util.ArrayUtils;
import common.util.DateUtils;
import common.util.StringUtils;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class UsuarioSalaConsultaDBEntityImpl extends DBEntity implements
		IUsuarioSalaConsultaDBEntity {

	/*
	 * ID Texto corto 32 TIPODOCIDENTIFICACION Entero corto NUMDOCIDENTIFICACION
	 * Texto corto 32 NOMBRE Texto corto 64 APELLIDOS Texto corto 254
	 * NACIONALIDAD Texto corto 64 TELEFONOS Texto corto 128 EMAIL Texto corto
	 * 128 DIRECCION Texto corto 254 FALTA Fecha VIGENTE Texto corto 1 IDSCAUSR
	 * Texto corto 32
	 */

	public static final String TABLE_NAME = "ASGSUSRCSALA";

	public static final String ID_COLUMN_NAME = "ID";

	public static final String TIPO_DOC_IDENTIFICACION_COLUMN_NAME = "TIPODOCIDENTIFICACION";

	public static final String NUM_DOC_IDENTIFICACION_COLUMN_NAME = "NUMDOCIDENTIFICACION";

	public static final String NOMBRE_COLUMN_NAME = "NOMBRE";

	public static final String APELLIDOS_COLUMN_NAME = "APELLIDOS";

	public static final String NACIONALIDAD_COLUMN_NAME = "NACIONALIDAD";

	public static final String TELEFONOS_COLUMN_NAME = "TELEFONOS";

	public static final String EMAIL_COLUMN_NAME = "EMAIL";

	public static final String DIRECCION_COLUMN_NAME = "DIRECCION";

	public static final String FECHA_ALTA_COLUMN_NAME = "FECHAALTA";

	public static final String VIGENTE_COLUMN_NAME = "VIGENTE";

	public static final String IDSCAUSR_COLUMN_NAME = "IDSCAUSR";

	public static final DbColumnDef ID_FIELD = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef TIPO_DOC_IDENTIFICACION_FIELD = new DbColumnDef(
			null, TABLE_NAME, TIPO_DOC_IDENTIFICACION_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, false);

	public static final DbColumnDef NUM_DOC_IDENTIFICACION_FIELD = new DbColumnDef(
			null, TABLE_NAME, NUM_DOC_IDENTIFICACION_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef NOMBRE_FIELD = new DbColumnDef(null,
			TABLE_NAME, NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, false);

	public static final DbColumnDef APELLIDOS_FIELD = new DbColumnDef(null,
			TABLE_NAME, APELLIDOS_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			false);

	public static final DbColumnDef NACIONALIDAD_FIELD = new DbColumnDef(null,
			TABLE_NAME, NACIONALIDAD_COLUMN_NAME, DbDataType.SHORT_TEXT, 64,
			false);

	public static final DbColumnDef TELEFONOS_FIELD = new DbColumnDef(null,
			TABLE_NAME, TELEFONOS_COLUMN_NAME, DbDataType.SHORT_TEXT, 128,
			false);

	public static final DbColumnDef EMAIL_FIELD = new DbColumnDef(null,
			TABLE_NAME, EMAIL_COLUMN_NAME, DbDataType.SHORT_TEXT, 128, false);

	public static final DbColumnDef DIRECCION_FIELD = new DbColumnDef(null,
			TABLE_NAME, DIRECCION_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			false);

	public static final DbColumnDef FECHA_ALTA_FIELD = new DbColumnDef(null,
			TABLE_NAME, FECHA_ALTA_COLUMN_NAME, DbDataType.DATE_TIME, false);

	public static final DbColumnDef VIGENTE_FIELD = new DbColumnDef(null,
			TABLE_NAME, VIGENTE_COLUMN_NAME, DbDataType.SHORT_TEXT, 1, false);

	public static final DbColumnDef IDSCAUSR_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDSCAUSR_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef[] TABLE_COLUMNS = { ID_FIELD,
			TIPO_DOC_IDENTIFICACION_FIELD, NUM_DOC_IDENTIFICACION_FIELD,
			NOMBRE_FIELD, APELLIDOS_FIELD, NACIONALIDAD_FIELD, TELEFONOS_FIELD,
			EMAIL_FIELD, DIRECCION_FIELD, FECHA_ALTA_FIELD, VIGENTE_FIELD,
			IDSCAUSR_FIELD };

	public static final DbColumnDef[] TABLE_COLUMNS_UPDATE = {
			TIPO_DOC_IDENTIFICACION_FIELD, NUM_DOC_IDENTIFICACION_FIELD,
			NOMBRE_FIELD, APELLIDOS_FIELD, NACIONALIDAD_FIELD, TELEFONOS_FIELD,
			EMAIL_FIELD, DIRECCION_FIELD, VIGENTE_FIELD, IDSCAUSR_FIELD };

	DbColumnDef columnaNombreArchivo = new DbColumnDef("nombreArchivo",
			new TableDef(ArchivoDbEntityImpl.TABLE_NAME,
					ArchivoDbEntityImpl.TABLE_NAME),
			ArchivoDbEntityImpl.NOMBRE_COLUMN_NAME,
			ArchivoDbEntityImpl.NOMBRE_FIELD.getDataType(), true);

	DbColumnDef[] COLS_DEF_QUERY = (DbColumnDef[]) ArrayUtils.concat(
			TABLE_COLUMNS, new DbColumnDef[] { columnaNombreArchivo });

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	private DbColumnDef[] COLUMNS_ORDER_BY = { APELLIDOS_FIELD, NOMBRE_FIELD, };

	DbColumnDef temaUsuario = new DbColumnDef("nombreTema", new TableDef(
			TemaDBEntity.TABLE_NAME, TemaDBEntity.TABLE_NAME),
			TemaDBEntity.TEMA, TemaDBEntity.CAMPO_TEMA.getDataType(), true);

	DbColumnDef[] COLS_DEF_TEMA = new DbColumnDef[] { NOMBRE_FIELD,
			NUM_DOC_IDENTIFICACION_FIELD, temaUsuario };

	public UsuarioSalaConsultaDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public UsuarioSalaConsultaDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IUsuarioSalaConsultaDBEntity#deleteUsuario(java.lang.String)
	 */
	public void deleteUsuario(String idUsuario) {
		deleteVO(getQualById(idUsuario), TABLE_NAME);
	}

	public List findUsuarios(BusquedaUsuarioSalaConsultaVO vo) {
		String qual = getQualByIdsArchivo(getQualForBusqueda(vo),
				vo.getIdsArchivo());
		qual += getDefaultOrder();
		return getVOS(qual, TABLE_NAME, TABLE_COLUMNS,
				UsuarioSalasConsultaVO.class);
	}

	public List findUsuariosByArchivo(BusquedaUsuarioSalaConsultaVO vo) {
		StringBuffer qual = new StringBuffer(getQualByIdsArchivo(
				getQualForBusqueda(vo), vo.getIdsArchivo()));
		qual.append(DBUtils.getCondition(qual.toString()))
				.append(ID_FIELD)
				.append(Constants.EQUAL)
				.append(UsuarioArchivoSalasConsultaDBEntityImpl.IDUSUARIO_FIELD)
				.append(DBUtils.AND)
				.append(UsuarioArchivoSalasConsultaDBEntityImpl.IDARCHIVO_FIELD)
				.append(Constants.EQUAL).append(ArchivoDbEntityImpl.ID_FIELD);

		if (ArrayUtils.isNotEmpty(vo.getIdsArchivo())) {
			qual.append(DBUtils.AND)
					.append(DBUtils
							.generateInTokenField(
									UsuarioArchivoSalasConsultaDBEntityImpl.IDARCHIVO_FIELD,
									vo.getIdsArchivo()));
		}
		qual.append(DBUtils.generateOrderBy(new DbColumnDef[] {
				ArchivoDbEntityImpl.NOMBRE_FIELD, NOMBRE_FIELD }));

		String tables = TABLE_NAME + Constants.COMMA
				+ UsuarioArchivoSalasConsultaDBEntityImpl.TABLE_NAME
				+ Constants.COMMA + ArchivoDbEntityImpl.TABLE_NAME;

		return getVOS(qual.toString(), tables, COLS_DEF_QUERY,
				UsuarioSalasConsultaVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IUsuarioSalaConsultaDBEntity#getUsuarioById(java.lang.String)
	 */
	public UsuarioSalasConsultaVO getUsuarioById(String idUsuario) {
		return (UsuarioSalasConsultaVO) getVO(getQualById(idUsuario),
				TABLE_NAME, TABLE_COLUMNS, UsuarioSalasConsultaVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IUsuarioSalaConsultaDBEntity#getUsuariosVigentes()
	 */
	public List getUsuariosByVigencia(String vigente) {
		StringBuffer qual = new StringBuffer(getQualByVigencia(vigente))
				.append(getDefaultOrder());

		return getVOS(qual.toString(), TABLE_NAME, TABLE_COLUMNS,
				UsuarioSalasConsultaVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IUsuarioSalaConsultaDBEntity#insertUsuario(salas.vos.UsuarioSalasConsultaVO)
	 */
	public void insertUsuario(UsuarioSalasConsultaVO usuarioSalaConsultaVO) {
		if (StringUtils.isEmpty(usuarioSalaConsultaVO.getId())) {
			usuarioSalaConsultaVO.setId(getGuid(usuarioSalaConsultaVO.getId()));
		}
		if (usuarioSalaConsultaVO.getFechaAlta() == null) {
			usuarioSalaConsultaVO.setFechaAlta(DateUtils.getFechaActual());
		}

		insertVO(TABLE_NAME, TABLE_COLUMNS, usuarioSalaConsultaVO);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IUsuarioSalaConsultaDBEntity#updateUsuario(salas.vos.UsuarioSalasConsultaVO)
	 */
	public void updateUsuario(UsuarioSalasConsultaVO vo) {

		HashMap columnsToUpdate = new HashMap();

		columnsToUpdate.put(TIPO_DOC_IDENTIFICACION_FIELD,
				vo.getTipoDocIdentificacion());
		columnsToUpdate.put(NUM_DOC_IDENTIFICACION_FIELD,
				vo.getNumDocIdentificacion());
		columnsToUpdate.put(NOMBRE_FIELD, vo.getNombre());
		columnsToUpdate.put(APELLIDOS_FIELD, vo.getApellidos());
		columnsToUpdate.put(NACIONALIDAD_FIELD, vo.getNacionalidad());
		columnsToUpdate.put(TELEFONOS_FIELD, vo.getTelefonos());
		columnsToUpdate.put(EMAIL_FIELD, vo.getEmail());
		columnsToUpdate.put(DIRECCION_FIELD, vo.getDireccion());
		columnsToUpdate.put(VIGENTE_FIELD, vo.getVigente());
		columnsToUpdate.put(IDSCAUSR_FIELD, vo.getIdscausr());

		updateFields(getQualById(vo.getId()), columnsToUpdate, TABLE_NAME);
	}

	private String getQualById(String idUsuario) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_FIELD, idUsuario));

		return qual.toString();
	}

	private String getQualByVigencia(String vigente) {
		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotEmpty(vigente)) {
			qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
					.generateEQTokenField(VIGENTE_FIELD, vigente));
			return qual.toString();
		}

		return qual.toString();

	}

	private String getQualForBusqueda(BusquedaUsuarioSalaConsultaVO vo) {

		StringBuffer qual = new StringBuffer();

		if (vo.getTipoDocIdentificacion() != null
				&& vo.getTipoDocIdentificacion().intValue() != 0) {
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateEQTokenField(TIPO_DOC_IDENTIFICACION_FIELD,
							vo.getTipoDocIdentificacion().intValue()));
		}

		if (StringUtils.isNotEmpty(vo.getNumDocIdentificacion())) {
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateLikeTokenField(
							NUM_DOC_IDENTIFICACION_FIELD,
							vo.getNumDocIdentificacion()));
		}

		if (StringUtils.isNotEmpty(vo.getNombre())) {
			qual.append(DBUtils.getCondition(qual.toString()))
					.append(DBUtils.generateLikeTokenField(NOMBRE_FIELD,
							vo.getNombre()));
		}

		if (StringUtils.isNotEmpty(vo.getApellidos())) {
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateLikeTokenField(APELLIDOS_FIELD,
							vo.getApellidos()));
		}

		if (StringUtils.isNotEmpty(vo.getVigente())) {
			qual.append(DBUtils.getCondition(qual.toString()))
					.append(DBUtils.generateEQTokenField(VIGENTE_FIELD,
							vo.getVigente()));
		}

		if ((vo.getFechaIniAlta() != null) || (vo.getFechaFinAlta() != null)) {
			if (vo.getFechaIniAlta() != null) {
				qual.append(DBUtils.getCondition(qual.toString())).append(
						DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(), FECHA_ALTA_FIELD, ">=",
								vo.getFechaIniAlta()));
			}
			if (vo.getFechaFinAlta() != null) {
				qual.append(DBUtils.getCondition(qual.toString())).append(
						DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(), FECHA_ALTA_FIELD, "<=",
								vo.getFechaFinAlta()));
			}
		}

		return qual.toString();
	}

	private String getDefaultOrder() {
		return DBUtils.generateOrderBy(COLUMNS_ORDER_BY);
	}

	private String getQualByIdsArchivo(String qual, String[] idsArchivo) {
		StringBuffer consulta = new StringBuffer(qual);
		if (ArrayUtils.isNotEmpty(idsArchivo)) {
			StringBuffer subConsulta = new StringBuffer();
			subConsulta
					.append(DBUtils.SELECT)
					.append(UsuarioArchivoSalasConsultaDBEntityImpl.IDUSUARIO_FIELD
							.getQualifiedName())
					.append(DBUtils.FROM)
					.append(UsuarioArchivoSalasConsultaDBEntityImpl.TABLE_NAME)
					.append(DBUtils.WHERE)
					.append(DBUtils
							.generateInTokenField(
									UsuarioArchivoSalasConsultaDBEntityImpl.IDARCHIVO_FIELD,
									idsArchivo));

			consulta.append(DBUtils.getCondition(consulta.toString())).append(
					DBUtils.generateInTokenFieldSubQuery(ID_FIELD,
							subConsulta.toString()));
		}
		return consulta.toString();
	}

	/**
	 * Permite contar los usuarios que cumplen ciertas condiciones
	 * 
	 * @param idUsuarioExterno
	 *            Identificador del usuario externo
	 * @param idDistintoUsuario
	 *            Identificador del usuario para excluirlo en la consulta
	 * @param vigente
	 *            Indica si se buscan usuarios vigentes o no
	 * @return numero de resultados
	 */
	private int getCountUsuarios(String idUsuarioExterno,
			String idDistintoUsuario, String vigente) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(IDSCAUSR_FIELD, idUsuarioExterno));

		if (StringUtils.isNotEmpty(vigente)) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(VIGENTE_FIELD, vigente));
		}

		if (StringUtils.isNotEmpty(idDistintoUsuario)) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateNEQTokenField(ID_FIELD, idDistintoUsuario));
		}

		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IUsuarioSalaConsultaDBEntity#getCountUsuariosByIdExterno(java.lang.String,
	 *      java.lang.String)
	 */
	public int getCountUsuariosByIdExterno(String idUsuarioExterno,
			String vigente) {
		return getCountUsuarios(idUsuarioExterno, null, vigente);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IUsuarioSalaConsultaDBEntity#getCountUsuariosByIdExternoDistintosUsuario(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public int getCountUsuariosByIdExternoDistintosUsuario(
			String idUsuarioExterno, String idDistintoUsuario, String vigente) {
		return getCountUsuarios(idUsuarioExterno, idDistintoUsuario, vigente);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IUsuarioSalaConsultaDBEntity#getUsuarioExterno(java.lang.String,
	 *      java.lang.String)
	 */
	public UsuarioSalasConsultaVO getUsuarioExterno(String idUsuarioExterno,
			String vigente) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(IDSCAUSR_FIELD, idUsuarioExterno));

		if (StringUtils.isNotEmpty(vigente)) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(VIGENTE_FIELD, vigente));
		}

		return (UsuarioSalasConsultaVO) getVO(qual.toString(), TABLE_NAME,
				TABLE_COLUMNS, UsuarioSalasConsultaVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IUsuarioSalaConsultaDBEntity#getUsuariosConPermisoConsultaSala(java.lang.String)
	 */
	public List getUsuariosConPermisoConsultaSala(String idArchivo,
			String filtro) {
		/*
		 * select asgsusrcsala.* from asgsusrcsala, asgsusrcsarch where
		 * asgsusrcsala.id = asgsusrcsarch.idusrcsala and
		 * asgsusrcsarch.idarchivo = 'idArchivo' and asgsusrcsala.vigente='S'
		 */
		StringBuffer qual = new StringBuffer();
		qual.append(DBUtils.WHERE)
				.append(UsuarioSalaConsultaDBEntityImpl.ID_FIELD)
				.append(Constants.EQUAL)
				.append(UsuarioArchivoSalasConsultaDBEntityImpl.IDUSUARIO_FIELD)
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						UsuarioSalaConsultaDBEntityImpl.VIGENTE_FIELD,
						Constants.TRUE_STRING))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(
								UsuarioArchivoSalasConsultaDBEntityImpl.IDARCHIVO_FIELD,
								idArchivo));

		if (StringUtils.isNotEmpty(filtro)) {
			qual.append(DBUtils.AND)
					.append(" (")
					.append(DBUtils.generateLikeTokenField(NOMBRE_FIELD,
							filtro, true))
					.append(DBUtils.OR)
					.append(DBUtils.generateLikeTokenField(APELLIDOS_FIELD,
							filtro, true))

					.append(") ");
		}

		String tables = TABLE_NAME + Constants.COMMA
				+ UsuarioArchivoSalasConsultaDBEntityImpl.TABLE_NAME;

		return getVOS(qual.toString(), tables, TABLE_COLUMNS,
				UsuarioSalasConsultaVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IUsuarioSalaConsultaDBEntity#getCountUsuariosConsultaSala(java.util.Date,
	 *      java.util.Date)
	 */
	public int getCountUsuariosConsultaSala(Date fechaIni, Date fechaFin) {
		StringBuffer qual = new StringBuffer();

		if ((fechaIni != null) || (fechaFin != null)) {
			if (fechaIni != null) {
				qual.append(DBUtils.getCondition(qual.toString())).append(
						DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(), FECHA_ALTA_FIELD, ">=",
								fechaIni));
			}
			if (fechaFin != null) {
				qual.append(DBUtils.getCondition(qual.toString())).append(
						DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(), FECHA_ALTA_FIELD, "<=",
								fechaFin));
			}
		}

		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IUsuarioSalaConsultaDBEntity#getTemasUsuarioConsulta(java.lang.String)
	 */
	public List getTemasUsuarioConsulta(String idUsrCSala) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE);
		qual.append(ID_FIELD).append(Constants.EQUAL)
				.append(TemaDBEntity.CAMPO_IDUSRCSALA);
		if (StringUtils.isNotEmpty(idUsrCSala)) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(ID_FIELD, idUsrCSala));
		}

		String tables = TABLE_NAME + Constants.COMMA + TemaDBEntity.TABLE_NAME;

		return getVOS(qual.toString(), tables, COLS_DEF_TEMA,
				UsuarioSalasConsultaVO.class);
	}
}
