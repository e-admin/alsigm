package solicitudes.prestamos.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import solicitudes.prestamos.PrestamosConstants;
import solicitudes.prestamos.vos.ProrrogaVO;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;

/**
 * Clase que se encarga de la persistencia sobre la tabla prorrogas.
 */
public abstract class ProrrogaDBEntity extends DBEntity implements
		IProrrogaDBEntity {

	public static final String TABLE_NAME = "ASGPPRORROGA";

	public static final String ID = "ID";

	public static final String IDPRESTAMO = "IDPRESTAMO";

	public static final String ESTADO = "ESTADO";

	public static final String FESTADO = "FESTADO";

	public static final String MOTIVORECHAZO = "MOTIVORECHAZO";

	public static final String ID_MOTIVO = "IDMOTIVO";

	public static final String FECHA_FIN_PRORROGA = "FECHAFINPRORROGA";

	public static final String MOTIVO_PRORROGA = "MOTIVOPRORROGA";

	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_IDPRESTAMO = new DbColumnDef(null,
			TABLE_NAME, IDPRESTAMO, DbDataType.SHORT_TEXT, 4, false);

	public static final DbColumnDef CAMPO_ESTADO = new DbColumnDef(null,
			TABLE_NAME, ESTADO, DbDataType.LONG_INTEGER, 2, false);

	public static final DbColumnDef CAMPO_FESTADO = new DbColumnDef(null,
			TABLE_NAME, FESTADO, DbDataType.DATE_TIME, true);

	public static final DbColumnDef CAMPO_MOTIVORECHAZO = new DbColumnDef(null,
			TABLE_NAME, MOTIVORECHAZO, DbDataType.SHORT_TEXT, 254, true);

	public static final DbColumnDef CAMPO_ID_MOTIVO = new DbColumnDef(null,
			TABLE_NAME, ID_MOTIVO, DbDataType.SHORT_TEXT, 254, false);

	public static final DbColumnDef CAMPO_FECHA_DEVOLUCION = new DbColumnDef(
			null, TABLE_NAME, FECHA_FIN_PRORROGA, DbDataType.DATE_TIME, true);

	public static final DbColumnDef CAMPO_MOTIVO_PRORROGA = new DbColumnDef(
			null, TABLE_NAME, MOTIVO_PRORROGA, DbDataType.SHORT_TEXT, 512, true);

	public static final DbColumnDef[] COLUMN_DEFINITIONS = { CAMPO_ID,
			CAMPO_IDPRESTAMO, CAMPO_ESTADO, CAMPO_FESTADO, CAMPO_MOTIVORECHAZO,
			CAMPO_ID_MOTIVO, CAMPO_FECHA_DEVOLUCION, CAMPO_MOTIVO_PRORROGA };

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
	public ProrrogaDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public ProrrogaDBEntity(DbConnection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Comprueba si un préstamos tiene prorroga solicitada.
	 * 
	 * @param codigoPrestamo
	 *            Identificador del préstamos del que deseamos comprobar si
	 *            tiene prorroga solicitada.
	 * @return Objeto que encapsula los datos de una prorroga de un préstamo.
	 */
	public ProrrogaVO getProrrogaSolicitada(String codigoPrestamo) {
		final String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDPRESTAMO,
						codigoPrestamo))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_ESTADO,
						PrestamosConstants.ESTADO_PRORROGA_SOLICITADA))
				.toString();

		return (ProrrogaVO) getVO(qual, TABLE_NAME, COLUMN_DEFINITIONS,
				ProrrogaVO.class);
	}

	/**
	 * Obtiene un listado con las prorrogas de los préstamos solicitados y que
	 * vienen identificados por sus ids.
	 * 
	 * @param codigos
	 *            Listado de identificadores de los prestamos de los que
	 *            deseamos obtener sus prorrogas.
	 * @return Listado de las prorrogas para los préstamos solicitados.
	 */
	public Collection getProrrogas(String[] codigos) {
		final String qual = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateInTokenField(CAMPO_IDPRESTAMO, codigos))
				.toString();

		return getVOS(qual, TABLE_NAME, COLUMN_DEFINITIONS, ProrrogaVO.class);
	}

	/**
	 * Realiza la insercion de una prorroga de un préstamo reclamado.
	 * 
	 * @param prorrogaVO
	 *            Prorroga que deseamos insertar para el prestamo
	 * @return Prorroga insertada
	 */
	public ProrrogaVO insertProrroga(final ProrrogaVO prorrogaVO) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				prorrogaVO.setId(getGuid(prorrogaVO.getId()));

				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLUMN_DEFINITIONS, prorrogaVO);
				DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
						inputRecord);
			}
		};

		command.execute();

		return prorrogaVO;
	}

	/**
	 * Actualiza la prorroga de un prestamo. Se puede utilzar dentro de un
	 * transaccion si la conexion pasada es null.
	 * 
	 * @param prorrogaVO
	 *            Prorroga que deseamos actualizar
	 */
	public void updateProrroga(ProrrogaVO prorrogaVO) {
		final String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_ID,
						prorrogaVO.getId())).toString();

		updateVO(qual, TABLE_NAME, COLUMN_DEFINITIONS, prorrogaVO);
	}

	public int getCountProrrogasByIdMotivo(String idMotivo) {
		StringBuffer qual = new StringBuffer();
		if (StringUtils.isNotEmpty(idMotivo))
			qual.append(DBUtils.WHERE).append(
					DBUtils.generateEQTokenField(CAMPO_ID_MOTIVO, idMotivo));

		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see solicitudes.prestamos.db.IProrrogaDBEntity#getProrrogasByIdPrestamo(java.lang.String)
	 */
	public List getProrrogasByIdPrestamo(String idPrestamo) {
		if (StringUtils.isEmpty(idPrestamo)) {
			return new ArrayList();
		}
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(CAMPO_IDPRESTAMO, idPrestamo))
				.append(DBUtils.generateOrderByDesc(CAMPO_FESTADO));

		return getVOS(qual.toString(), TABLE_NAME, COLUMN_DEFINITIONS,
				ProrrogaVO.class);
	}

	/**
	 * Comprueba si un préstamos tiene prorroga solicitada.
	 * 
	 * @param codigoPrestamo
	 *            Identificador del préstamos del que deseamos comprobar si
	 *            tiene prorroga solicitada.
	 * @return Objeto que encapsula los datos de una prorroga de un préstamo.
	 */
	public ProrrogaVO getProrrogaActiva(String codigoPrestamo) {
		final String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDPRESTAMO,
						codigoPrestamo))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_ESTADO,
						PrestamosConstants.ESTADO_PRORROGA_AUTORIZADA))
				.append(DBUtils.generateOrderByDesc(CAMPO_FESTADO)).toString();

		return (ProrrogaVO) getVO(qual, TABLE_NAME, COLUMN_DEFINITIONS,
				ProrrogaVO.class);
	}

}
