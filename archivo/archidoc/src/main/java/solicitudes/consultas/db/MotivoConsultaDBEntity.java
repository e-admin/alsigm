package solicitudes.consultas.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import solicitudes.consultas.vos.MotivoConsultaVO;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.util.ArrayUtils;

/**
 * Clase abstracta que se encarga de la persistencia de la tabla MOTIVOCONSULTA
 */
public abstract class MotivoConsultaDBEntity extends DBEntity implements
		IMotivoConsultaDBEntity {

	/** Nombre de la tabla */
	public static final String TABLE_NAME = "ASGPMTVCONSULTA";

	public static final String ID_MOTIVO = "id";

	public static final String MOTIVO = "motivo";

	public static final String TIPO_ENTIDAD = "tipoentidad";

	public static final String TIPO_CONSULTA = "tipoconsulta";

	public static final String VISIBILIDAD = "visibilidad";

	public static final DbColumnDef CAMPO_ID = new DbColumnDef(ID_MOTIVO,
			DbDataType.SHORT_TEXT, 254, false);

	public static final DbColumnDef CAMPO_TIPO_ENTIDAD = new DbColumnDef(
			TIPO_ENTIDAD, DbDataType.LONG_INTEGER, 2, false);

	public static final DbColumnDef CAMPO_MOTIVO = new DbColumnDef(MOTIVO,
			DbDataType.SHORT_TEXT, 254, false);

	public static final DbColumnDef CAMPO_TIPO_CONSULTA = new DbColumnDef(
			TIPO_CONSULTA, DbDataType.LONG_INTEGER, 2, false);

	public static final DbColumnDef CAMPO_VISIBILIDAD = new DbColumnDef(
			VISIBILIDAD, DbDataType.LONG_INTEGER, 2, true);

	// public static final DbColumnDef[] COLUMN_DEFINITIONS = { CAMPO_MOTIVO,
	// CAMPO_TIPO_ENTIDAD, CAMPO_TIPO_CONSULTA};

	public static final DbColumnDef[] COLUMN_DEFINITIONS = { CAMPO_ID,
			CAMPO_MOTIVO, CAMPO_TIPO_ENTIDAD, CAMPO_TIPO_CONSULTA,
			CAMPO_VISIBILIDAD };

	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(COLUMN_DEFINITIONS);

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
	 */
	public MotivoConsultaDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public MotivoConsultaDBEntity(DbConnection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Obtiene un listado de los motivos existentes para el tipo de entidad
	 * indicado.
	 * 
	 * @param tipo
	 *            TIpo de entidad del que deseamos obtener los motivos.
	 * @return Listado de los motivos existentes para el tipo de entidad
	 *         indicado.
	 */
	public List getMotivosByTipoEntidad(int tipoEntidad) {
		final String qual = getQualMotivo(null, null, new Integer(tipoEntidad),
				null, null);

		return getVOS(qual, TABLE_NAME, COLUMN_DEFINITIONS,
				MotivoConsultaVO.class);
	}

	/**
	 * Obtiene un listado de los motivos existentes para el tipo de consulta.
	 * 
	 * @param tipo
	 *            TIpo de consulta de la que deseamos obtener los motivos.
	 * @return Listado de los motivos existentes para el tipo de consulta
	 *         indicado.
	 */
	public List getMotivosByTipoConsulta(int tipoConsulta) {
		final String qual = getQualMotivo(null, null, null, new Integer(
				tipoConsulta), null);
		return getVOS(qual, TABLE_NAME, COLUMN_DEFINITIONS,
				MotivoConsultaVO.class);
	}

	public List getMotivosConsulta(Integer tipoEntidad, Integer tipoConsulta,
			Integer[] visibilidad) {

		StringBuffer qual = new StringBuffer();
		if (tipoEntidad != null) {
			qual.append(DBUtils.getCondition(qual.toString()));
			qual.append(DBUtils.generateEQTokenField(CAMPO_TIPO_ENTIDAD,
					tipoEntidad.intValue()));
		}

		if (tipoConsulta != null) {
			qual.append(DBUtils.getCondition(qual.toString()));
			qual.append(DBUtils.generateEQTokenField(CAMPO_TIPO_CONSULTA,
					tipoConsulta.intValue()));
		}

		if (ArrayUtils.isNotEmpty(visibilidad)) {
			qual.append(DBUtils.getCondition(qual.toString()));
			qual.append(DBUtils.generateInTokenField(CAMPO_VISIBILIDAD,
					visibilidad));
		}

		return getVOS(qual.toString(), TABLE_NAME, COLUMN_DEFINITIONS,
				MotivoConsultaVO.class);
	}

	public List getMotivosConsulta() {
		return getVOS(null, TABLE_NAME, COLUMN_DEFINITIONS,
				MotivoConsultaVO.class);
	}

	public void insertarMotivoConsulta(final MotivoConsultaVO motivoVO) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				motivoVO.setId(getGuid(motivoVO.getId()));

				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLUMN_DEFINITIONS, motivoVO);
				DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
						inputRecord);
			}
		};
		command.execute();
	}

	public MotivoConsultaVO getMotivoConsulta(final MotivoConsultaVO motivoVO) {
		final String qual = getQualMotivo(null, motivoVO.getMotivo(),
				motivoVO.getTipoEntidad(), motivoVO.getTipoConsulta(),
				motivoVO.getVisibilidad());
		return (MotivoConsultaVO) getVO(qual, TABLE_NAME, COLUMN_DEFINITIONS,
				MotivoConsultaVO.class);
	}

	public MotivoConsultaVO getMotivoConsultaById(final String idMotivo) {
		if (StringUtils.isEmpty(idMotivo))
			return null;

		final String qual = getQualMotivo(idMotivo, null, null, null, null);
		return (MotivoConsultaVO) getVO(qual, TABLE_NAME, COLUMN_DEFINITIONS,
				MotivoConsultaVO.class);
	}

	public void deleteMotivoConsulta(MotivoConsultaVO motivoVO) {
		final String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_ID, motivoVO.getId()))
				.toString();

		deleteVO(qual, TABLE_NAME);
	}

	public void actualizarMotivoConsulta(final MotivoConsultaVO motivoVO) {
		final String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_ID, motivoVO.getId()))
				.toString();

		updateVO(qual, TABLE_NAME, COLUMN_DEFINITIONS, motivoVO);
	}

	/**
	 * Metodo que genera la sentencia sql para buscar un motivo de consulta
	 * segun los parametros recibidos.
	 * 
	 * @param motivo
	 * @param tipoEntidad
	 * @param tipoConsulta
	 * @param visibilidad
	 * @return
	 */
	private String getQualMotivo(String id, String motivo, Integer tipoEntidad,
			Integer tipoConsulta, Integer visibilidad) {
		final StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotEmpty(id)) {
			qual.append(DBUtils.getCondition(qual.toString()));
			qual.append(DBUtils.generateEQTokenField(CAMPO_ID, id));
		}

		if (StringUtils.isNotEmpty(motivo)) {
			qual.append(DBUtils.getCondition(qual.toString()));
			qual.append(DBUtils.generateEQTokenField(CAMPO_MOTIVO, motivo));
		}

		if (tipoEntidad != null) {
			qual.append(DBUtils.getCondition(qual.toString()));
			qual.append(DBUtils.generateEQTokenField(CAMPO_TIPO_ENTIDAD,
					tipoEntidad.intValue()));
		}

		if (tipoConsulta != null) {
			qual.append(DBUtils.getCondition(qual.toString()));
			qual.append(DBUtils.generateEQTokenField(CAMPO_TIPO_CONSULTA,
					tipoConsulta.intValue()));
		}

		if (visibilidad != null) {
			qual.append(DBUtils.getCondition(qual.toString()));
			qual.append(DBUtils.generateEQTokenField(CAMPO_VISIBILIDAD,
					visibilidad.intValue()));
		}

		return qual.toString();
	}
}