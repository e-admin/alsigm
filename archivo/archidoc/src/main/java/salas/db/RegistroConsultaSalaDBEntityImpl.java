package salas.db;

import gcontrol.db.ArchivoDbEntityImpl;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import salas.SalasConsultaConstants;
import salas.vos.BusquedaRegistroConsultaSalaVO;
import salas.vos.RegistroConsultaSalaVO;

import common.Constants;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.JoinDefinition;
import common.db.TableDef;
import common.util.ArrayUtils;
import common.util.StringUtils;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class RegistroConsultaSalaDBEntityImpl extends DBEntity implements
		IRegistroConsultaSalaDBEntity {

	public static final String TABLE_NAME = "ASGSREGCSALA";

	public static final String ID_COLUMN_NAME = "ID";
	public static final String USUARIO_SALA_COLUMN_NAME = "IDUSRCSALA";
	public static final String FECHA_ENTRADA_COLUMN_NAME = "FENTRADA";
	public static final String FECHA_SALIDA_COLUMN_NAME = "FSALIDA";
	public static final String NUMDOC_IDENTIFICACION_COLUMN_NAME = "NUMDOCIDENTIFICACION";
	public static final String NOMBRE_APELLIDOS_COLUMN_NAME = "NOMBREAPELLIDOS";
	public static final String USUARIO_APLICACION_COLUMN_NAME = "IDSCAUSR";
	public static final String ARCHIVO_COLUMN_NAME = "IDARCHIVO";
	public static final String MESA_ASIGNADA_COLUMN_NAME = "MESAASIGNADA";

	public static final DbColumnDef ID_FIELD = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef USUARIO_SALA_FIELD = new DbColumnDef(null,
			TABLE_NAME, USUARIO_SALA_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef FENTRADA_FIELD = new DbColumnDef(null,
			TABLE_NAME, FECHA_ENTRADA_COLUMN_NAME, DbDataType.DATE_TIME, false);

	public static final DbColumnDef FSALIDA_FIELD = new DbColumnDef(null,
			TABLE_NAME, FECHA_SALIDA_COLUMN_NAME, DbDataType.DATE_TIME, true);

	public static final DbColumnDef NUMDOC_IDENTIFICACION_FIELD = new DbColumnDef(
			null, TABLE_NAME, NUMDOC_IDENTIFICACION_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef NOMBRE_APELLIDOS_FIELD = new DbColumnDef(
			null, TABLE_NAME, NOMBRE_APELLIDOS_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 254, false);

	public static final DbColumnDef USUARIO_APLICACION_FIELD = new DbColumnDef(
			null, TABLE_NAME, USUARIO_APLICACION_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, true);

	public static final DbColumnDef ARCHIVO_FIELD = new DbColumnDef(null,
			TABLE_NAME, ARCHIVO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef MESA_ASIGNADA_FIELD = new DbColumnDef(null,
			TABLE_NAME, MESA_ASIGNADA_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			false);

	public static final DbColumnDef FECHA_ENTRADA_FIELD = new DbColumnDef(
			"fechaEntrada", TABLE_NAME, FENTRADA_FIELD);
	public static final DbColumnDef FECHA_SALIDA_FIELD = new DbColumnDef(
			"fechaSalida", TABLE_NAME, FSALIDA_FIELD);

	public static final DbColumnDef[] TABLE_COLUMNS = { ID_FIELD,
			USUARIO_SALA_FIELD, FECHA_ENTRADA_FIELD, FECHA_SALIDA_FIELD,
			NUMDOC_IDENTIFICACION_FIELD, NOMBRE_APELLIDOS_FIELD,
			USUARIO_APLICACION_FIELD, ARCHIVO_FIELD, MESA_ASIGNADA_FIELD };

	DbColumnDef columnaNombreArchivo = new DbColumnDef("nombreArchivo",
			new TableDef(ArchivoDbEntityImpl.TABLE_NAME,
					ArchivoDbEntityImpl.TABLE_NAME),
			ArchivoDbEntityImpl.NOMBRE_COLUMN_NAME,
			ArchivoDbEntityImpl.NOMBRE_FIELD.getDataType(), true);

	DbColumnDef[] COLS_DEF_QUERY = (DbColumnDef[]) ArrayUtils.concat(
			TABLE_COLUMNS, new DbColumnDef[] { columnaNombreArchivo });

	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(TABLE_COLUMNS);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	private String getQualById(String idRegistro) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_FIELD, idRegistro));

		return qual.toString();
	}

	private String getQualByIdUsuario(String idUsuarioConsulta) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(USUARIO_SALA_FIELD,
						idUsuarioConsulta))
				.append(DBUtils.AND)
				.append(DBUtils.generateIsNullCondition(getConnection(),
						FECHA_SALIDA_FIELD));

		return qual.toString();
	}

	private String getQualByFechaSalida() {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateIsNullCondition(getConnection(),
						FECHA_SALIDA_FIELD))
				.append(DBUtils.AND)
				.append(DBUtils.generateIsNullCondition(getConnection(),
						FECHA_SALIDA_FIELD));

		return qual.toString();
	}

	private String getQualByIds(String[] idsRegistro) {
		StringBuffer qual = new StringBuffer();

		if (ArrayUtils.isNotEmpty(idsRegistro)) {
			qual.append(DBUtils.WHERE).append(
					DBUtils.generateInTokenField(ID_FIELD, idsRegistro));
		}

		return qual.toString();
	}

	private String getQualRegistrosAbiertos() {
		StringBuffer qual = new StringBuffer();
		qual.append(DBUtils.WHERE).append(
				DBUtils.generateIsNullCondition(getConnection(),
						FECHA_SALIDA_FIELD));

		return qual.toString();
	}

	private String getQualForBusqueda(BusquedaRegistroConsultaSalaVO vo) {

		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotEmpty(vo.getNumDocIdentificacion())) {
			if (StringUtils.isNotEmpty(vo.getNumeroDoc_like())) {
				qual.append(DBUtils.getCondition(qual.toString()));
				if (SalasConsultaConstants.ETIQUETA_IGUAL.equals(vo
						.getNumeroDoc_like())) {
					qual.append(DBUtils.generateEQTokenField(
							NUMDOC_IDENTIFICACION_FIELD,
							vo.getNumDocIdentificacion()));
				} else if (SalasConsultaConstants.ETIQUETA_CONTIENE.equals(vo
						.getNumeroDoc_like())) {
					qual.append(DBUtils.generateLikeTokenField(
							NUMDOC_IDENTIFICACION_FIELD,
							vo.getNumDocIdentificacion()));
				}
			}
		}

		if (StringUtils.isNotEmpty(vo.getNombreApellidos())) {
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateLikeTokenField(NOMBRE_APELLIDOS_FIELD,
							vo.getNombreApellidos()));
		}

		if (StringUtils.isNotEmpty(vo.getIdArchivo())) {
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateEQTokenField(ARCHIVO_FIELD,
							vo.getIdArchivo()));
		} else {
			if (ArrayUtils.isNotEmpty(vo.getIdsArchivo())) {
				qual.append(DBUtils.getCondition(qual.toString())).append(
						DBUtils.generateInTokenField(ARCHIVO_FIELD,
								vo.getIdsArchivo()));
			}
		}

		if (StringUtils.isNotEmpty(vo.getMesaAsignada())) {
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateLikeTokenField(MESA_ASIGNADA_FIELD,
							vo.getMesaAsignada()));
		}

		if ((vo.getFechaIniEntrada() != null)
				|| (vo.getFechaFinEntrada() != null)) {
			if (vo.getFechaIniEntrada() != null) {
				qual.append(DBUtils.getCondition(qual.toString())).append(
						DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(), FECHA_ENTRADA_FIELD, ">=",
								vo.getFechaIniEntrada()));
			}
			if (vo.getFechaFinEntrada() != null) {
				qual.append(DBUtils.getCondition(qual.toString())).append(
						DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(), FECHA_ENTRADA_FIELD, "<=",
								vo.getFechaFinEntrada()));
			}
		}

		if (vo.isRegistrado()) {
			if ((vo.getFechaIniSalida() != null)
					|| (vo.getFechaFinSalida() != null)) {
				if (vo.getFechaIniSalida() != null) {
					qual.append(DBUtils.getCondition(qual.toString())).append(
							DBUtils.generateTokenFieldDateAnioMesDia(
									getConnection(), FECHA_SALIDA_FIELD, ">=",
									vo.getFechaIniSalida()));
				}
				if (vo.getFechaFinSalida() != null) {
					qual.append(DBUtils.getCondition(qual.toString())).append(
							DBUtils.generateTokenFieldDateAnioMesDia(
									getConnection(), FECHA_SALIDA_FIELD, "<=",
									vo.getFechaFinSalida()));
				}
			} else {
				qual.append(DBUtils.getCondition(qual.toString())).append(
						DBUtils.generateIsNotNullCondition(getConnection(),
								FECHA_SALIDA_FIELD));
			}
		} else {
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateIsNullCondition(getConnection(),
							FECHA_SALIDA_FIELD));
		}

		return qual.toString();
	}

	private String getJoinCondition() {
		/*
		 * SELECT ASGSREGCSALA.*, AGARCHIVO.NOMBRE FROM ASGSREGCSALA LEFT OUTER
		 * JOIN AGARCHIVO ON AGARCHIVO.ID = ASGSREGCSALA.IDARCHIVO;
		 */
		JoinDefinition join = new JoinDefinition(ARCHIVO_FIELD,
				ArchivoDbEntityImpl.ID_FIELD);
		StringBuffer fromSql = new StringBuffer().append(DBUtils
				.generateLeftOuterJoinCondition(new TableDef(TABLE_NAME),
						new JoinDefinition[] { join }));

		return fromSql.toString();
	}

	/**
	 * @param dataSource
	 */
	public RegistroConsultaSalaDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public RegistroConsultaSalaDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IRegistroConsultaSalaDBEntity#insertRegistroConsultaSala(salas.vos.RegistroConsultaSalaVO)
	 */
	public void insertRegistroConsultaSala(
			RegistroConsultaSalaVO registroConsultaSalaVO) {
		registroConsultaSalaVO.setId(getGuid(registroConsultaSalaVO.getId()));
		registroConsultaSalaVO.setFechaEntrada(new Date());
		insertVO(TABLE_NAME, TABLE_COLUMNS, registroConsultaSalaVO);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IRegistroConsultaSalaDBEntity#deleteRegistroConsultaSala(java.lang.String)
	 */
	public void deleteRegistroConsultaSala(String idRegistro) {
		if (StringUtils.isNotEmpty(idRegistro))
			deleteVO(getQualById(idRegistro), TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IRegistroConsultaSalaDBEntity#updateRegistroConsultaSala(salas.vos.RegistroConsultaSalaVO)
	 */
	public void updateRegistroConsultaSala(
			RegistroConsultaSalaVO registroConsultaSalaVO) {
		updateVO(getQualById(registroConsultaSalaVO.getId()), TABLE_NAME,
				TABLE_COLUMNS, registroConsultaSalaVO);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IRegistroConsultaSalaDBEntity#getRegistroConsultaSalaById(java.lang.String)
	 */
	public RegistroConsultaSalaVO getRegistroConsultaSalaById(String idRegistro) {
		if (StringUtils.isNotEmpty(idRegistro)) {
			return (RegistroConsultaSalaVO) getVO(getQualById(idRegistro),
					TABLE_NAME, TABLE_COLUMNS, RegistroConsultaSalaVO.class);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IRegistroConsultaSalaDBEntity#getRegistroConsultaSalaByIdUsuario(java.lang.String)
	 */
	public RegistroConsultaSalaVO getRegistroConsultaSalaByIdUsuario(
			String idUsuarioConsulta) {
		if (StringUtils.isNotEmpty(idUsuarioConsulta)) {
			return (RegistroConsultaSalaVO) getVO(
					getQualByIdUsuario(idUsuarioConsulta), TABLE_NAME,
					TABLE_COLUMNS, RegistroConsultaSalaVO.class);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IRegistroConsultaSalaDBEntity#getUsuariosRegistrados()
	 */
	public List getUsuariosRegistrados() {
		return getVOS(getQualByFechaSalida(), getJoinCondition(),
				COLS_DEF_QUERY, RegistroConsultaSalaVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IRegistroConsultaSalaDBEntity#getRegistrosConsultaSalaByIds(java.lang.String[])
	 */
	public List getRegistrosConsultaSalaByIds(String[] idsRegistro) {
		return getVOS(getQualByIds(idsRegistro), TABLE_NAME, TABLE_COLUMNS,
				RegistroConsultaSalaVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IRegistroConsultaSalaDBEntity#updateRegistrosConsultaSala(java.lang.String)
	 */
	public void updateRegistrosConsultaSala(String idRegistro) {
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(FECHA_SALIDA_FIELD, new Date());
		updateFields(getQualById(idRegistro), colsToUpdate, TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IRegistroConsultaSalaDBEntity#findRegistros(salas.vos.BusquedaRegistroConsultaSalaVO)
	 */
	public List findRegistros(
			BusquedaRegistroConsultaSalaVO busquedaRegistroConsultaSalaVO) {
		String qual = getQualForBusqueda(busquedaRegistroConsultaSalaVO);
		return getVOS(qual, getJoinCondition(), COLS_DEF_QUERY,
				RegistroConsultaSalaVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IRegistroConsultaSalaDBEntity#getRegistrosAbiertos()
	 */
	public List getRegistrosAbiertos() {
		String qual = getQualRegistrosAbiertos();
		return getVOS(qual, getJoinCondition(), COLS_DEF_QUERY,
				RegistroConsultaSalaVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IRegistroConsultaSalaDBEntity#countRegistrosAbiertos()
	 */
	public int countRegistrosAbiertos() {
		String qual = getQualRegistrosAbiertos();
		return getVOCount(qual, TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IRegistroConsultaSalaDBEntity#getRegistro(java.lang.String,
	 *      java.lang.String, java.lang.String, java.util.Date)
	 */
	public RegistroConsultaSalaVO getRegistro(String idArchivo,
			String idUsrCSala, String idScaUsr, Date fechaEntrada) {
		StringBuffer qual = new StringBuffer();
		qual.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(ARCHIVO_FIELD, idArchivo))
				.append(DBUtils.AND)
				.append(DBUtils.generateTokenFieldDateAnioMesDia(
						getConnection(), FECHA_ENTRADA_FIELD, Constants.EQUAL,
						fechaEntrada))
				.append(DBUtils.AND)
				.append(DBUtils.generateIsNullCondition(getConnection(),
						FECHA_SALIDA_FIELD));

		if (StringUtils.isNotEmpty(idUsrCSala)) {
			qual.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(USUARIO_SALA_FIELD,
							idUsrCSala));
		} else if (StringUtils.isNotEmpty(idScaUsr)) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(USUARIO_APLICACION_FIELD,
							idScaUsr));
		}

		return (RegistroConsultaSalaVO) getVO(qual.toString(), TABLE_NAME,
				TABLE_COLUMNS, RegistroConsultaSalaVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IRegistroConsultaSalaDBEntity#getCountRegistros(java.util.Date,
	 *      java.util.Date)
	 */
	public int getCountRegistros(Date fechaIni, Date fechaFin) {
		StringBuffer qual = new StringBuffer();

		if ((fechaIni != null) || (fechaFin != null)) {
			if (fechaIni != null) {
				qual.append(DBUtils.getCondition(qual.toString())).append(
						DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(), FECHA_ENTRADA_FIELD, ">=",
								fechaIni));
			}
			if (fechaFin != null) {
				qual.append(DBUtils.getCondition(qual.toString())).append(
						DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(), FECHA_ENTRADA_FIELD, "<=",
								fechaFin));
			}
		}

		return getVOCount(qual.toString(), TABLE_NAME);
	}
}