package solicitudes.prestamos.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import solicitudes.prestamos.vos.MotivoPrestamoVO;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;

/**
 * Clase abstracta que se encarga de la persistencia de la tabla MOTIVOPRESTAMO
 */
public abstract class MotivoPrestamoDBEntity extends DBEntity implements
		IMotivoPrestamoDBEntity {

	/** Nombre de la tabla */
	public static final String TABLE_NAME = "ASGPMTVPRESTAMO";

	public static final String ID_MOTIVO = "id";

	public static final String MOTIVO = "motivo";

	public static final String TIPO_USUARIO = "tipousuario";

	public static final String VISIBILIDAD = "visibilidad";

	public static final DbColumnDef CAMPO_ID = new DbColumnDef(ID_MOTIVO,
			DbDataType.SHORT_TEXT, 254, false);

	public static final DbColumnDef CAMPO_TIPO_USUARIO = new DbColumnDef(
			TIPO_USUARIO, DbDataType.LONG_INTEGER, 2, false);

	public static final DbColumnDef CAMPO_MOTIVO = new DbColumnDef(MOTIVO,
			DbDataType.SHORT_TEXT, 254, false);

	public static final DbColumnDef CAMPO_VISIBILIDAD = new DbColumnDef(
			VISIBILIDAD, DbDataType.LONG_INTEGER, 2, true);

	public static final DbColumnDef[] COLUMN_DEFINITIONS = { CAMPO_ID,
			CAMPO_MOTIVO, CAMPO_TIPO_USUARIO, CAMPO_VISIBILIDAD };

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
	public MotivoPrestamoDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public MotivoPrestamoDBEntity(DbConnection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public List getMotivosPrestamo() {
		return getVOS(null, TABLE_NAME, COLUMN_DEFINITIONS,
				MotivoPrestamoVO.class);
	}

	public void insertarMotivoPrestamo(final MotivoPrestamoVO motivoVO) {
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

	public MotivoPrestamoVO getMotivoPrestamo(final MotivoPrestamoVO motivoVO) {
		final String qual = getQualMotivo(null, motivoVO.getMotivo(),
				motivoVO.getTipoUsuario(),
				new Integer[] { motivoVO.getVisibilidad() });
		return (MotivoPrestamoVO) getVO(qual, TABLE_NAME, COLUMN_DEFINITIONS,
				MotivoPrestamoVO.class);
	}

	public MotivoPrestamoVO getMotivoPrestamoById(final String idMotivo) {
		if (StringUtils.isEmpty(idMotivo))
			return null;
		final String qual = getQualMotivo(idMotivo, null, null, null);
		return (MotivoPrestamoVO) getVO(qual, TABLE_NAME, COLUMN_DEFINITIONS,
				MotivoPrestamoVO.class);
	}

	public void deleteMotivoPrestamo(MotivoPrestamoVO motivoVO) {
		final String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_ID, motivoVO.getId()))
				.toString();

		deleteVO(qual, TABLE_NAME);
	}

	public void actualizarMotivoPrestamo(final MotivoPrestamoVO motivoVO) {
		final String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_ID, motivoVO.getId()))
				.toString();

		updateVO(qual, TABLE_NAME, COLUMN_DEFINITIONS, motivoVO);
	}

	public Collection getMotivosByTipoUsuario(Integer tipoUsuario,
			Integer[] visibilidad) {
		final String qual = getQualMotivo(null, null, tipoUsuario, visibilidad);
		return getVOS(qual.toString(), TABLE_NAME, COLUMN_DEFINITIONS,
				MotivoPrestamoVO.class);
	}

	/**
	 * Metodo que genera la sentencia sql para buscar un motivo de prestamo
	 * segun los parametros recibidos.
	 * 
	 * @param motivo
	 * @param tipoUsuario
	 * @param tipoPrestamo
	 * @param visibilidad
	 * @return
	 */
	private String getQualMotivo(String id, String motivo, Integer tipoUsuario,
			Integer[] visibilidad) {
		final StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotEmpty(id)) {
			qual.append(DBUtils.getCondition(qual.toString()));
			qual.append(DBUtils.generateEQTokenField(CAMPO_ID, id));
		}

		if (StringUtils.isNotEmpty(motivo)) {
			qual.append(DBUtils.getCondition(qual.toString()));
			qual.append(DBUtils.generateEQTokenField(CAMPO_MOTIVO, motivo));
		}

		if (tipoUsuario != null) {
			qual.append(DBUtils.getCondition(qual.toString()));
			qual.append(DBUtils.generateEQTokenField(CAMPO_TIPO_USUARIO,
					tipoUsuario.intValue()));
		}

		if (visibilidad != null) {
			qual.append(DBUtils.getCondition(qual.toString()));
			qual.append(DBUtils.generateInTokenField(CAMPO_VISIBILIDAD,
					visibilidad));
		}
		return qual.toString();
	}
}