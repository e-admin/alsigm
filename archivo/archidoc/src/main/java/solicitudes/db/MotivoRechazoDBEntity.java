package solicitudes.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import solicitudes.vos.MotivoRechazoVO;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.exceptions.DBException;

/**
 * Clase que se encarga de la persistencia para la tabla MOTIVORECHAZO
 */
public abstract class MotivoRechazoDBEntity extends DBEntity implements
		IMotivoRechazoDBEntity {
	/** Tipos del rechazo */
	public static final int TIPO_SOLICITUD_PRESTAMOS = 1;
	public static final int TIPO_SOLICITUD_CONSULTAS = 2;
	public static final int TIPO_PRORROGA = 3;

	/** Nombre de la tabla en la base de datos */
	public static final String TABLE_NAME = "ASGPMTVRECHAZO";

	/** Columna Id */
	public static final String ID = "id";

	/** Columna Tipo */
	public static final String TIPO = "tiposolicitud";

	/** Columna motivo */
	public static final String MOTIVO = "motivo";

	/** Tipo de columna id */
	public static final DbColumnDef CAMPO_ID = new DbColumnDef(ID,
			DbDataType.SHORT_TEXT, 254, false);

	/** Tipo de columna tipo */
	public static final DbColumnDef CAMPO_TIPO = new DbColumnDef(TIPO,
			DbDataType.LONG_INTEGER, 2, false);

	/** Tipo de columna motivo */
	public static final DbColumnDef CAMPO_MOTIVO = new DbColumnDef(MOTIVO,
			DbDataType.SHORT_TEXT, 254, false);

	/** Listado de los tipos de columna */
	public static final DbColumnDef[] COLUMN_DEFINITIONS = { CAMPO_ID,
			CAMPO_TIPO, CAMPO_MOTIVO };

	/** Listado de los nombres de columnas */
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
	public MotivoRechazoDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public MotivoRechazoDBEntity(DbConnection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Obtiene los motivos de rechazo de un determinado tipo.
	 * 
	 * @param tipo
	 *            Tipo de los motivos de rechazo a obtener.
	 * @return Listado con los motivos de rechazo.
	 * @throws DBException
	 *             Si se produce algún problema durante el acceso a la base de
	 *             datos.
	 */
	public Collection getMotivos(int tipo) {
		final String qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_TIPO, tipo)).toString();

		return getVOS(qual, TABLE_NAME, COLUMN_DEFINITIONS,
				MotivoRechazoVO.class);
	}

	public List getMotivosRechazo() {
		return getVOS(null, TABLE_NAME, COLUMN_DEFINITIONS,
				MotivoRechazoVO.class);
	}

	public void insertarMotivoRechazo(final MotivoRechazoVO motivoVO) {
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

	public MotivoRechazoVO getMotivoRechazo(final MotivoRechazoVO motivoVO) {
		final String qual = getQualMotivo(null, motivoVO.getMotivo(),
				motivoVO.getTipoSolicitud());
		return (MotivoRechazoVO) getVO(qual, TABLE_NAME, COLUMN_DEFINITIONS,
				MotivoRechazoVO.class);
	}

	public MotivoRechazoVO getMotivoRechazoById(final String idMotivo) {
		final String qual = getQualMotivo(idMotivo, null, null);
		return (MotivoRechazoVO) getVO(qual, TABLE_NAME, COLUMN_DEFINITIONS,
				MotivoRechazoVO.class);
	}

	public void deleteMotivoRechazo(MotivoRechazoVO motivoVO) {
		final String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_ID, motivoVO.getId()))
				.toString();

		deleteVO(qual, TABLE_NAME);
	}

	public void actualizarMotivoRechazo(final MotivoRechazoVO motivoVO) {
		final String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_ID, motivoVO.getId()))
				.toString();

		updateVO(qual, TABLE_NAME, COLUMN_DEFINITIONS, motivoVO);
	}

	/**
	 * Metodo que genera la sentencia sql para buscar un motivo de rechazo segun
	 * los parametros recibidos.
	 * 
	 * @param motivo
	 * @param tipoSolicitud
	 * @return
	 */
	private String getQualMotivo(String id, String motivo, Integer tipoSolicitud) {
		final StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotEmpty(id)) {
			qual.append(DBUtils.getCondition(qual.toString()));
			qual.append(DBUtils.generateEQTokenField(CAMPO_ID, id));
		}

		if (StringUtils.isNotEmpty(motivo)) {
			qual.append(DBUtils.getCondition(qual.toString()));
			qual.append(DBUtils.generateEQTokenField(CAMPO_MOTIVO, motivo));
		}

		if (tipoSolicitud != null) {
			qual.append(DBUtils.getCondition(qual.toString()));
			qual.append(DBUtils.generateEQTokenField(CAMPO_TIPO,
					tipoSolicitud.intValue()));
		}

		return qual.toString();
	}
}
