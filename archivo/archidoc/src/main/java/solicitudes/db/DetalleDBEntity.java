package solicitudes.db;

import fondos.db.ElementoCuadroClasificacionDBEntityImplBase;
import fondos.db.UnidadDocumentalDBEntityImpl;
import fondos.db.oracle.ElementoCuadroClasificacionDBEntityImpl;
import fondos.vos.BusquedaElementosVO;
import gcontrol.db.ArchivoDbEntityImpl;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbUtil;
import ieci.core.exception.IeciTdException;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import salas.vos.BusquedaUsuarioSalaConsultaVO;
import solicitudes.EstadoSolicitudEntregaUDocConstants;
import solicitudes.SolicitudesConstants;
import solicitudes.consultas.ConsultasConstants;
import solicitudes.consultas.db.ConsultaDBEntity;
import solicitudes.consultas.vos.ConsultaVO;
import solicitudes.consultas.vos.DetalleConsultaVO;
import solicitudes.exceptions.DetalleNotFoundException;
import solicitudes.prestamos.PrestamosConstants;
import solicitudes.prestamos.db.PrestamoDBEntity;
import solicitudes.prestamos.vos.DetallePrestamoVO;
import solicitudes.prestamos.vos.PrestamoVO;
import solicitudes.vos.BusquedaDetalleVO;
import solicitudes.vos.DetalleVO;

import common.Constants;
import common.TiposUsuario;
import common.db.ConstraintType;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.JoinDefinition;
import common.db.SigiaDbInputRecord;
import common.db.TableDef;
import common.exceptions.TooManyResultsException;
import common.lang.MutableInt;
import common.util.ArrayUtils;
import common.util.CustomDateFormat;
import common.util.DateUtils;
import common.util.ListUtils;
import common.util.StringUtils;
import common.vos.ConsultaConnectBy;

import deposito.db.UDocEnUiDepositoDbEntityImpl;
import deposito.db.oracle.HuecoDBEntityImpl;

/**
 * Clase que encapsula todas las definiciones de los detalles asi como de las
 * operaciones que se pueden realizar sobre ellos.
 */
public abstract class DetalleDBEntity extends DBEntity implements
		IDetalleDBEntity {

	/** Tipo del detalle 1-Prestamos 2-Consultas */
	public static final String TIPO_DETALLE_PRESTAMO = "1";
	public static final String TIPO_DETALLE_CONSULTA = "2";

	/** Nombre de la tabla */
	public static final String TABLE_NAME = "ASGPSOLICITUDUDOC";
	/** Campos de la tabla */
	public static final String IDSOLICITUD_COLUMN_NAME = "idsolicitud";
	public static final String TIPOSOLICITUD_COLUMN_NAME = "tiposolicitud";
	public static final String IDUDOC_COLUMN_NAME = "idudoc";
	public static final String TITULO_COLUMN_NAME = "titulo";
	public static final String SIGNATURAUDOC_COLUMN_NAME = "signaturaudoc";
	public static final String EXPEDIENTEUDOC_COLUMN_NAME = "expedienteudoc";
	public static final String IDENTIFICACION_COLUMN_NAME = "identificacion";
	public static final String ESTADO_COLUMN_NAME = "estado";
	public static final String FESTADO_COLUMN_NAME = "festado";
	public static final String FINICIALUSO_COLUMN_NAME = "finicialuso";
	public static final String FFINALUSO_COLUMN_NAME = "ffinaluso";
	public static final String MOTIVORECHAZO_COLUMN_NAME = "motivorechazo";
	public static final String INFORMACION_COLUMN_NAME = "informacion";
	public static final String IDFONDO_COLUMN_NAME = "idfondo";
	public static final String IDMOTIVO_COLUMN_NAME = "idmotivo";

	public static final DbColumnDef IDSOLICITUD_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDSOLICITUD_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);
	public static final DbColumnDef TIPOSOLICITUD_FIELD = new DbColumnDef(null,
			TABLE_NAME, TIPOSOLICITUD_COLUMN_NAME, DbDataType.SHORT_INTEGER, 2,
			false);
	public static final DbColumnDef IDUDOC_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDUDOC_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef TITULO_FIELD = new DbColumnDef(null,
			TABLE_NAME, TITULO_COLUMN_NAME, DbDataType.SHORT_TEXT, 254, false);
	public static final DbColumnDef SIGNATURAUDOC_FIELD = new DbColumnDef(null,
			TABLE_NAME, SIGNATURAUDOC_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			false);
	public static final DbColumnDef EXPEDIENTEUDOC_FIELD = new DbColumnDef(
			null, TABLE_NAME, EXPEDIENTEUDOC_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 128, false);
	public static final DbColumnDef IDENTIFICACION_FIELD = new DbColumnDef(
			null, TABLE_NAME, IDENTIFICACION_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 254, false);
	public static final DbColumnDef ESTADO_FIELD = new DbColumnDef(null,
			TABLE_NAME, ESTADO_COLUMN_NAME, DbDataType.SHORT_INTEGER, 2, false);
	public static final DbColumnDef FESTADO_FIELD = new DbColumnDef(null,
			TABLE_NAME, FESTADO_COLUMN_NAME, DbDataType.DATE_TIME, false);
	public static final DbColumnDef FINICIALUSO_FIELD = new DbColumnDef(null,
			TABLE_NAME, FINICIALUSO_COLUMN_NAME, DbDataType.DATE_TIME, true);
	public static final DbColumnDef FFINALUSO_FIELD = new DbColumnDef(null,
			TABLE_NAME, FFINALUSO_COLUMN_NAME, DbDataType.DATE_TIME, true);
	public static final DbColumnDef MOTIVORECHAZO_FIELD = new DbColumnDef(null,
			TABLE_NAME, MOTIVORECHAZO_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			true);
	public static final DbColumnDef INFORMACION_FIELD = new DbColumnDef(null,
			TABLE_NAME, INFORMACION_COLUMN_NAME, DbDataType.SHORT_TEXT, 1024,
			true);
	public static final DbColumnDef IDFONDO_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDFONDO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, true);
	public static final DbColumnDef IDMOTIVO_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDMOTIVO_COLUMN_NAME, DbDataType.SHORT_TEXT, 254, true);
	public static final DbColumnDef[] TABLE_COLUMNS = { IDSOLICITUD_FIELD,
			TIPOSOLICITUD_FIELD, IDUDOC_FIELD, TITULO_FIELD,
			SIGNATURAUDOC_FIELD, EXPEDIENTEUDOC_FIELD, IDENTIFICACION_FIELD,
			ESTADO_FIELD, FESTADO_FIELD, FINICIALUSO_FIELD, FFINALUSO_FIELD,
			MOTIVORECHAZO_FIELD, INFORMACION_FIELD, IDFONDO_FIELD,
			IDMOTIVO_FIELD };

	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(TABLE_COLUMNS);

	/*********/

	public static final String COD_REFERENCIA_COLUMN_NAME = "codReferencia";
	public static final String NOMBRE_SERIE_COLUMN_NAME = "nombreSerie";

	private final TableDef ASGPSOLICITUDUDOC_TABLE = new TableDef(
			DetalleDBEntityImpl.TABLE_NAME, DetalleDBEntityImpl.TABLE_NAME);
	private final TableDef ASGPCONSULTA_TABLE = new TableDef(
			ConsultaDBEntity.TABLE_NAME, ConsultaDBEntity.TABLE_NAME);
	private final TableDef ASGPPRESTAMO_TABLE = new TableDef(
			PrestamoDBEntity.TABLE_NAME, PrestamoDBEntity.TABLE_NAME);
	private final TableDef ASGFELEMENTOCF_TABLE = new TableDef(
			ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO,
			ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO);
	private final TableDef ASGFELEMENTOCF2_TABLE = new TableDef(
			ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO,
			ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO + "2");
	private final DbColumnDef idSolicitudColumnDef = new DbColumnDef(
			ASGPSOLICITUDUDOC_TABLE, DetalleDBEntityImpl.IDSOLICITUD_FIELD);
	private final DbColumnDef idColumnDef = new DbColumnDef(
			ASGPSOLICITUDUDOC_TABLE, DetalleDBEntityImpl.IDUDOC_FIELD);
	private final DbColumnDef signaturaColumnDef = new DbColumnDef(
			ASGPSOLICITUDUDOC_TABLE, DetalleDBEntityImpl.SIGNATURAUDOC_FIELD);
	protected final DbColumnDef numExpColumnDef = new DbColumnDef(
			ASGPSOLICITUDUDOC_TABLE, DetalleDBEntityImpl.EXPEDIENTEUDOC_FIELD);

	private final DbColumnDef signaturaUDocColumnDef = new DbColumnDef(
			ASGPSOLICITUDUDOC_TABLE, DetalleDBEntityImpl.SIGNATURAUDOC_FIELD);
	private final DbColumnDef tituloColumnDef = new DbColumnDef(
			ASGPSOLICITUDUDOC_TABLE, DetalleDBEntityImpl.TITULO_FIELD);
	protected final DbColumnDef estadoConsultaColumnDef = new DbColumnDef(
			ASGPCONSULTA_TABLE, ConsultaDBEntity.CAMPO_ESTADO);
	protected final DbColumnDef estadoPrestamoColumnDef = new DbColumnDef(
			ASGPPRESTAMO_TABLE, PrestamoDBEntity.CAMPO_ESTADO);
	private final DbColumnDef fechaEntregaPrestamoColumnDef = new DbColumnDef(
			ASGPPRESTAMO_TABLE, PrestamoDBEntity.CAMPO_FENTREGA);
	private final DbColumnDef fechaEntregaConsultaColumnDef = new DbColumnDef(
			ASGPCONSULTA_TABLE, ConsultaDBEntity.CAMPO_FENTREGA);
	private final DbColumnDef tipoSolicitudColumnDef = new DbColumnDef(
			ASGPSOLICITUDUDOC_TABLE, DetalleDBEntityImpl.TIPOSOLICITUD_FIELD);
	private final DbColumnDef nombreSerieColumnDef = new DbColumnDef(
			ASGFELEMENTOCF2_TABLE, new DbColumnDef("nombreSerie",
					ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD
							.getName() + " AS nombreSerie ",
					ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD
							.getDataType()));
	private final DbColumnDef tituloElementoCF2 = new DbColumnDef(
			ASGFELEMENTOCF2_TABLE,
			ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD);
	private final DbColumnDef estadoSolicitudColumnDef = new DbColumnDef(
			ASGPSOLICITUDUDOC_TABLE, DetalleDBEntityImpl.ESTADO_FIELD);
	private final DbColumnDef fechaEstadoSolicitudColumnDef = new DbColumnDef(
			ASGPSOLICITUDUDOC_TABLE, DetalleDBEntityImpl.FESTADO_FIELD);

	private final DbColumnDef idConsulta = new DbColumnDef(ASGPCONSULTA_TABLE,
			ConsultaDBEntity.CAMPO_ID);
	private final DbColumnDef idElementoCF = new DbColumnDef(
			ASGFELEMENTOCF_TABLE,
			ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD);
	protected final DbColumnDef idFondoElemntoCF = new DbColumnDef(
			ASGFELEMENTOCF_TABLE,
			ElementoCuadroClasificacionDBEntityImpl.IDFONDO_FIELD);
	private final DbColumnDef codReferenciaCF = new DbColumnDef(
			ASGFELEMENTOCF_TABLE,
			ElementoCuadroClasificacionDBEntityImpl.CODIGO_REFERENCIA_FIELD);
	private final DbColumnDef idElementoCF2 = new DbColumnDef(
			ASGFELEMENTOCF2_TABLE,
			ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD);
	private final DbColumnDef idPrestamoColumnDef = new DbColumnDef(
			ASGPPRESTAMO_TABLE, PrestamoDBEntity.CAMPO_ID);
	private final DbColumnDef idConsultaColumnDef = new DbColumnDef(
			ASGPCONSULTA_TABLE, ConsultaDBEntity.CAMPO_ID);
	private final DbColumnDef idPadreElementoCF = new DbColumnDef(
			ASGFELEMENTOCF_TABLE,
			ElementoCuadroClasificacionDBEntityImpl.IDPADRE_FIELD);

	private final DbColumnDef columnaMotivo = new DbColumnDef("motivo",
			new TableDef(ConsultaDBEntity.TABLE_NAME,
					ConsultaDBEntity.TABLE_NAME), ConsultaDBEntity.MOTIVO,
			ConsultaDBEntity.CAMPO_MOTIVO.getDataType(), true);

	private final DbColumnDef columnaNumVeces = new DbColumnDef("numVeces",
			DbUtil.getCountColumn(IDUDOC_FIELD), DbDataType.SHORT_INTEGER);

	DbColumnDef[] COLS_DEF_SALA = new DbColumnDef[] { FESTADO_FIELD,
			SIGNATURAUDOC_FIELD, columnaMotivo };
	DbColumnDef[] COLS_DEF_UNIDADES = new DbColumnDef[] { TITULO_FIELD,
			SIGNATURAUDOC_FIELD, columnaNumVeces };

	/*********/

	/**
	 * Obtiene el nombre de la tabla
    *
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Constructor por defecto de la clase
    *
	 * @param dataSource
	 */
	public DetalleDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public DetalleDBEntity(DbConnection conn) {
		super(conn);
	}

	private DbColumnDef[] getCustomColsDefs() {
		DbColumnDef[] cols = (DbColumnDef[]) ArrayUtils
				.concat(new DbColumnDef[] {
						new DbColumnDef(
								new TableDef(
										ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO),
								new DbColumnDef(
										"idElementoCF",
										ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD)),
						new DbColumnDef(
								new TableDef(
										ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO),
								new DbColumnDef(
										"subtipo",
										ElementoCuadroClasificacionDBEntityImpl.SUBTIPO_ELEMENTO_FIELD)) },
						TABLE_COLUMNS);

		return cols;
	}

	private String getCustomSQLFrom() {
		TableDef tablaDetalle = new TableDef(TABLE_NAME);
		JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(tablaDetalle, IDUDOC_FIELD),
				new DbColumnDef(
						new TableDef(
								ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO),
						ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD)), };
		StringBuffer sqlFrom = new StringBuffer();
		sqlFrom.append(DBUtils.generateLeftOuterJoinCondition(tablaDetalle,
				joins));
		return sqlFrom.toString();

	}

	/**
	 * Elimina los detalles(udocumentals) identificado por su id de la unidad
	 * documental y su signatura y que esta asociado a una solicitud(de préstamo
	 * o consulta).
    *
	 * @param idSolicitud
	 *            Identificador de la solicitud ala que va asociado el detalle
	 * @param idudoc
	 *            Identificador del detalle(udocumental) que deseamos eliminar.
	 * @param signatura
	 *            Signatura del detalle(udocumental) que deseamos eliminar.
	 */
	public void deleteDetalle(String idSolicitud, String idUdoc,
			String signatura, String type) {
		final String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDSOLICITUD_FIELD,
						idSolicitud))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(TIPOSOLICITUD_FIELD, type))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(IDUDOC_FIELD, idUdoc))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(SIGNATURAUDOC_FIELD,
						signatura)).toString();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual);
			}

		};

		command.execute();
	}

	/*
	 * (sin Javadoc)
    *
	 * @see
	 * solicitudes.db.IDetalleDBEntity#getUDocsEnPrestamoByEstado(java.util.
	 * List, int[])
	 */
	public List getUDocsEnPrestamoByEstado(List idsUDocs, int[] estados) {
		String qual = new String();
		for (int i = 0; i < estados.length; i++) {
			if (qual.length() == 0) {
				qual += "("
						+ DBUtils
								.generateEQTokenField(ESTADO_FIELD, estados[i]);
			} else {
				qual += " OR ";
				qual += DBUtils.generateEQTokenField(ESTADO_FIELD, estados[i]);
			}
		}
		qual += ") AND ";
		qual += DBUtils.generateInTokenField(IDUDOC_FIELD, idsUDocs);
		qual = " WHERE " + qual;

		return getVOS(qual, TABLE_NAME, TABLE_COLUMNS, DetallePrestamoVO.class);
	}

	/**
	 * Obtiene un listado de los detalles con el identificador de udoc y la
	 * signatura pasados.
    *
	 * @param idudoc
	 *            Identificador de la unidad documental
	 * @param signatura
	 *            Signatura de la unidad documental
	 * @param type
	 *            Tipo del detalle al que deseamos castear los resultado
	 *            obtenidos(detalle de prestamo o de consulta)
	 * @return Listado de los detalles
	 */
	public Collection getDetalles(String idudoc, String signatura, String type) {
		return getDetalles(idudoc, signatura, type, null);
	}

	public List getDetalles(String idUdoc, int[] estados) {
		StringBuffer qual = new StringBuffer().append(" WHERE ").append(
				DBUtils.generateEQTokenField(IDUDOC_FIELD, idUdoc));

		if (estados != null && estados.length > 0) {
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(ESTADO_FIELD, estados));
		}

		qual.append(" ORDER BY ").append(FINICIALUSO_FIELD.getQualifiedName());

		return getVOS(qual.toString(), getCustomSQLFrom(), getCustomColsDefs(),
				DetalleVO.class);
	}

	/**
	 * Realiza la autorización de una unidad documental de un determinado
	 * préstamo.
    *
	 * @param prestamo
	 *            Prestamo al que pertenece la unidad documental.
	 * @param idudoc
	 *            Identificador de la unidad documental que se desea autorizar.
	 * @param signaturaudoc
	 *            Signatura de la uniadad documental que se desea autorizar.
	 */
	public void autorizarDetallePrestamo(PrestamoVO prestamo, String idudoc,
			String signaturaudoc) {
		String qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDSOLICITUD_FIELD,
						prestamo.getId()))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(IDUDOC_FIELD, idudoc))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(SIGNATURAUDOC_FIELD,
						signaturaudoc)).toString();

		DetallePrestamoVO detalle = new DetallePrestamoVO();
		// Borramos los motivos de rechazo
		detalle.setMotivorechazo("");
		// Establecemos el estado
		if (prestamo.tieneReserva()) {
			detalle.setEstado(PrestamosConstants.ESTADO_DETALLE_RESERVADA);
			detalle.setFinicialuso(prestamo.getFinicialreserva());
			detalle.setFfinaluso(prestamo.getFfinalreserva());
		} else {
			detalle.setEstado(PrestamosConstants.ESTADO_DETALLE_AUTORIZADA);
			detalle.setFinicialuso(prestamo.getFInicialPrestamo());
			detalle.setFfinaluso(prestamo.getFFinalPrestamo());
		}
		// Establecemos la fecha del estado
		detalle.setFestado(DBUtils.getFechaActual());

		DbColumnDef[] columnas = { ESTADO_FIELD, FESTADO_FIELD,
				FINICIALUSO_FIELD, FFINALUSO_FIELD, MOTIVORECHAZO_FIELD };

		updateVO(qual, TABLE_NAME, columnas, detalle);
	}

	/**
	 * Permite bloquear un detalle de préstamo para comprobar su disponibilidad
	 * (debe ser dentro de una transacción)
    *
	 * @param prestamo
	 *            Prestamo al que pertenece la unidad documental.
	 * @param idudoc
	 *            Identificador de la unidad documental que se desea autorizar.
	 * @param signaturaudoc
	 *            Signatura de la uniadad documental que se desea autorizar.
	 */
	public void bloquearDetallePrestamo(PrestamoVO prestamo, String idudoc,
			String signaturaudoc) {

		String qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDUDOC_FIELD, idudoc))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(SIGNATURAUDOC_FIELD,
						signaturaudoc)).toString();

		DetalleVO detalle = new DetalleVO();
		detalle.setIdudoc(idudoc);

		DbColumnDef[] columnas = { IDUDOC_FIELD };

		updateVO(qual, TABLE_NAME, columnas, detalle);
	}

	/**
	 * Realiza la autorización de una unidad documental de una determinada
	 * consulta.
    *
	 * @param consulta
	 *            Consulta a la que pertenece la unidad documental.
	 * @param idudoc
	 *            Identificador de la unidad documental que se desea autorizar.
	 * @param signaturaudoc
	 *            Signatura de la uniadad documental que se desea autorizar.
	 */
	public void autorizarDetalleConsulta(ConsultaVO consulta, String idudoc,
			String signaturaudoc) {
		String qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDSOLICITUD_FIELD,
						consulta.getId()))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(IDUDOC_FIELD, idudoc))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(SIGNATURAUDOC_FIELD,
						signaturaudoc)).toString();

		DetalleConsultaVO detalle = new DetalleConsultaVO();
		// Borramos los motivos de rechazo
		detalle.setMotivorechazo("");
		// Establecemos el estado
		if (consulta.tieneReserva())
			detalle.setEstado(ConsultasConstants.ESTADO_DETALLE_RESERVADA);
		else
			detalle.setEstado(ConsultasConstants.ESTADO_DETALLE_AUTORIZADA);
		// Establecemos la fecha del estado
		detalle.setFestado(DBUtils.getFechaActual());

		DbColumnDef[] columnas = { ESTADO_FIELD, FESTADO_FIELD,
				MOTIVORECHAZO_FIELD };

		updateVO(qual, TABLE_NAME, columnas, detalle);
	}

	/**
	 * Realiza la denegación de una unidad documental de un determinado
	 * préstamo.
    *
	 * @param prestamo
	 *            Prestamo al que pertenece la unidad documental.
	 * @param idudoc
	 *            Identificador de la unidad documental que se desea denegar.
	 * @param signaturaudoc
	 *            Signatura de la uniadad documental que se desea denegar.
	 * @param motivo
	 *            Motivo de la denegación del préstamo.
	 */
	public void denegarDetallePrestamo(PrestamoVO prestamo, String idudoc,
			String signaturaudoc, String motivo, String idMotivoRechazo) {
		String qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDSOLICITUD_FIELD,
						prestamo.getId()))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(IDUDOC_FIELD, idudoc))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(SIGNATURAUDOC_FIELD,
						signaturaudoc)).toString();

		// Buscamos nuestra solicitud
		DetallePrestamoVO detalle = new DetallePrestamoVO();
		detalle.setMotivorechazo(motivo);
		detalle.setEstado(PrestamosConstants.ESTADO_DETALLE_DENEGADA);
		detalle.setFestado(DBUtils.getFechaActual());
		detalle.setIdMotivo(idMotivoRechazo);

		DbColumnDef[] columnas = { ESTADO_FIELD, FESTADO_FIELD,
				MOTIVORECHAZO_FIELD, IDMOTIVO_FIELD };

		updateVO(qual, TABLE_NAME, columnas, detalle);
	}

	/**
	 * Realiza la denegación de una unidad documental de una determinada
	 * consulta.
    *
	 * @param consulta
	 *            Consulta al que pertenece la unidad documental.
	 * @param idudoc
	 *            Identificador de la unidad documental que se desea denegar.
	 * @param signaturaudoc
	 *            Signatura de la uniadad documental que se desea denegar.
	 * @param motivo
	 *            Motivo de la denegación del préstamo.
	 */
	public void denegarDetalleConsulta(ConsultaVO consulta, String idudoc,
			String signaturaudoc, String motivo, String idMotivoRechazo) {
		String qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDSOLICITUD_FIELD,
						consulta.getId()))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(IDUDOC_FIELD, idudoc))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(SIGNATURAUDOC_FIELD,
						signaturaudoc)).toString();

		// Buscamos nuestra solicitud
		DetalleConsultaVO detalle = new DetalleConsultaVO();
		detalle.setMotivorechazo(motivo);
		detalle.setEstado(ConsultasConstants.ESTADO_DETALLE_DENEGADA);
		detalle.setFestado(DBUtils.getFechaActual());
		detalle.setIdMotivo(idMotivoRechazo);

		DbColumnDef[] columnas = { ESTADO_FIELD, FESTADO_FIELD,
				MOTIVORECHAZO_FIELD, IDMOTIVO_FIELD };

		updateVO(qual, TABLE_NAME, columnas, detalle);
	}

	/**
	 * Obtiene un listado de todas las unidades documentales para un determinado
	 * solicitud(préstamo o consulta) dada por su identificador.
    *
	 * @param codigo
	 *            Identificador de la solicitud de la que deseamos obtener las
	 *            unidades documentales.
	 * @param type
	 *            Tipo de la solicitud 1-Prestamos 2 Consultas
	 * @return Un listados de todas las unidades documentales para una
	 *         determinada solicitud
	 */
	public Collection getDetallesXTipo(String idPrestamo, String type) {
		Collection result = null;

		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDSOLICITUD_FIELD,
						idPrestamo))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(TIPOSOLICITUD_FIELD, type))
				.append(DBUtils.generateOrderBy(SIGNATURAUDOC_FIELD))
				.toString();

		if (type.equals(TIPO_DETALLE_PRESTAMO))
			result = getVOS(qual, getCustomSQLFrom(), getCustomColsDefs(),
					DetallePrestamoVO.class);
		else
			result = getVOS(qual, getCustomSQLFrom(), getCustomColsDefs(),
					DetalleConsultaVO.class);

		return result;
	}

	/**
	 * Actualiza un detalle dado por su id solicitud,id udoc, signatura y tipo
    *
	 * @param detalle
	 *            Detalle a actualizar
	 */
	public void actualizarDetalle(DetalleVO detalle) {
		String qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDSOLICITUD_FIELD,
						detalle.getIdsolicitud()))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(IDUDOC_FIELD,
						detalle.getIdudoc()))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(SIGNATURAUDOC_FIELD,
						detalle.getSignaturaudoc()))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(TIPOSOLICITUD_FIELD,
						detalle.getTiposolicitud())).toString();

		updateVO(qual, TABLE_NAME, TABLE_COLUMNS, detalle);
	}

	/**
	 * Realiza el borrado del detalle identificado por su id y signatura de un
	 * préstamo por su id. O todos en caso de no venir los ids de los detalles.
    *
	 * @param id
	 *            Identificador de la solicitud de la que se desean eliminar sus
	 *            detalles
	 * @param type
	 *            Tipo de la solicitud de la que deseamos borrar los detalles
	 */
	public void deleteDetalles(String id, String type) {
		final String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDSOLICITUD_FIELD, id))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(TIPOSOLICITUD_FIELD, type))
				.toString();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual);
			}
		};

		command.execute();
	}

	/**
	 * Obtiene un listado de las unidades documentales devueltas de una
	 * determinada solicitud(prestamo o consulta)
    *
	 * @param codigo
	 *            Identificador de la solicitud de la que deseamos obtener los
	 *            detalles devueltos
	 * @return Listado de los detalles de la solicitud
	 */
	public Collection getUnidadesDevueltas(String codigo, String type) {
		Collection result = null;
		/**
		 * Restringe por el id de la solicitud, de tipo prestamo(1) y estado
		 * devuelta
		 */
		final String qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDSOLICITUD_FIELD, codigo))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(TIPOSOLICITUD_FIELD, type))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(ESTADO_FIELD,
						PrestamosConstants.ESTADO_SOLICITUD_DEVUELTA))
				.append(" order by ").append(IDSOLICITUD_COLUMN_NAME)
				.toString();

		if (type.equals(TIPO_DETALLE_CONSULTA))
			result = getVOS(qual, getCustomSQLFrom(), getCustomColsDefs(),
					DetalleConsultaVO.class);
		else
			result = getVOS(qual, getCustomSQLFrom(), getCustomColsDefs(),
					DetallePrestamoVO.class);

		return result;
	}

	/**
	 * Obtiene la ubicacion de una unidad documental(detalle) de un préstamo.
	 * NOTA: select asgdhueco.path,asgdudocenui.identificacion from asgdhueco,
	 * asgdudocenui, asgfunidaddoc , asgfelementocf where
	 * asgdhueco.iduinstalacion=asgdudocenui.iduinstalacion and
	 * asgfunidaddoc.idelementocf = asgfelementocf.id and asgfelementocf.codigo
	 * = asgdudocenui.signaturaudoc --and asgdudocenui.signaturaudoc = 'xxx'
	 * --and asgdudocenui.idudoc
    *
	 * @param idudoc
	 *            Identificador de la unidad documental
	 * @param signatura
	 *            Signatura de la unidad documental
	 * @return Ubicacion de la unidad documental.
	 */
	public String getUbicacionDetalle(String idUdoc, String signatura) {
		final String qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateJoinCondition(
						HuecoDBEntityImpl.CAMPO_IDUINSTALACION,
						UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD))
				// " asgdhueco.iduinstalacion=asgdudocenui.iduinstalacion "
				.append(" AND ")
				.append(DBUtils
						.generateJoinCondition(
								UnidadDocumentalDBEntityImpl.CAMPO_ID,
								ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD))
				// "and asgfunidaddoc.idelementocf = asgfelementocf.id "
				.append(" AND ")
				.append(DBUtils
						.generateJoinCondition(
								ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD,
								UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD))
				// "and asgfelementocf.id = asgdudocenui.idunidaddoc "
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						UDocEnUiDepositoDbEntityImpl.SIGNATURAUDOC_FIELD,
						signatura))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD, idUdoc))
				.toString();

		HashMap pairsTableNamesColsDefs = new HashMap();
		DbColumnDef[] definicionesHueco = { new DbColumnDef("Ubicacion",
				HuecoDBEntityImpl.TABLE_NAME,
				HuecoDBEntityImpl.PATH_COLUMN_NAME, DbDataType.SHORT_TEXT, 512,
				true) };
		pairsTableNamesColsDefs.put(HuecoDBEntityImpl.TABLE_NAME,
				definicionesHueco);
		pairsTableNamesColsDefs.put(UDocEnUiDepositoDbEntityImpl.TABLE_NAME,
				new DbColumnDef[0]);
		pairsTableNamesColsDefs.put(
				UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL,
				new DbColumnDef[0]);
		pairsTableNamesColsDefs.put(
				ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO,
				new DbColumnDef[0]);

		DetallePrestamoVO detalle = (DetallePrestamoVO) getVO(qual,
				pairsTableNamesColsDefs, DetallePrestamoVO.class);

		return (detalle != null ? detalle.getUbicacion() : null);
	}

	/**
	 * Obtiene un detalle entrega del tipo indicado
    *
	 * @param idUdoc
	 *            Identifcador de la unidad documental
	 * @param signatura
	 *            Signatura de la udoc
	 * @param type
	 *            Tipo del detalle(prestamo o consulta)
	 * @return Detalle buscado para el tipo (DetallePRestamoVO o
	 *         DetalleConsultaVO)
	 */
	// public DetalleVO getDetalleEntregado(String idUdoc,String
	// signatura,String type) {
	public DetalleVO getDetalleEntregado(String idSolicitud, String idUdoc,
			String signatura, String type) {
		DetalleVO detalle = null;
		String qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDUDOC_FIELD, idUdoc))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(SIGNATURAUDOC_FIELD,
						signatura))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(ESTADO_FIELD,
						PrestamosConstants.ESTADO_DETALLE_ENTREGADA))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(IDSOLICITUD_FIELD,
						idSolicitud)).toString();

		if (type.equalsIgnoreCase(DetalleDBEntity.TIPO_DETALLE_PRESTAMO))
			detalle = (DetallePrestamoVO) getVO(qual, getCustomSQLFrom(),
					getCustomColsDefs(), DetallePrestamoVO.class);
		else
			detalle = (DetalleConsultaVO) getVO(qual, getCustomSQLFrom(),
					getCustomColsDefs(), DetalleConsultaVO.class);

		return detalle;

	}

	/**
	 * Obtiene el detalle entregado a partir de su identificador de bd.
    *
	 * @param identificacion
	 *            Identificador del detalle que deseamos recuperar.
	 * @param type
	 *            Tipo del detalla(prestamo o consulta)
	 * @return Detalle de la solicitud
	 */
	public DetalleVO getDetalleEntregadoXID(String identificacion, String type) {
		DetalleVO detalle = null;
		final String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDENTIFICACION_FIELD,
						identificacion))
				.append(DBUtils.generateEQTokenField(TIPOSOLICITUD_FIELD, type))
				.append(DBUtils.generateEQTokenField(ESTADO_FIELD,
						PrestamosConstants.ESTADO_DETALLE_ENTREGADA))
				.toString();

		TableDef tablaDetalle = new TableDef(TABLE_NAME);
		JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(tablaDetalle, IDUDOC_FIELD),
				new DbColumnDef(
						new TableDef(
								ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO),
						ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD)), };
		StringBuffer sqlFrom = new StringBuffer();
		sqlFrom.append(DBUtils.generateLeftOuterJoinCondition(tablaDetalle,
				joins));

		if (type.equalsIgnoreCase(DetalleDBEntity.TIPO_DETALLE_PRESTAMO)) {
			detalle = (DetallePrestamoVO) getVO(qual, sqlFrom.toString(),
					getCustomColsDefs(), DetallePrestamoVO.class);
		} else {
			detalle = (DetalleConsultaVO) getVO(qual, sqlFrom.toString(),
					getCustomColsDefs(), DetalleConsultaVO.class);
		}

		return detalle;
	}

	/**
	 * Obtiene el numero de detalles entregados para una solicitud
    *
	 * @param idSolicitud
	 *            Identificador de la solicitud que deseamos comprobar
	 * @return Numero de detalles entregados para la solicitud
	 */
	public int getNumDetallesEntregados(final String idSolicitud) {
		final MutableInt nDetalles = new MutableInt(0);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				String qual = new StringBuffer()
						.append(" WHERE ")
						.append(DBUtils.generateEQTokenField(IDSOLICITUD_FIELD,
								idSolicitud))
						.append(" AND ")
						.append(DBUtils.generateEQTokenField(ESTADO_FIELD,
								PrestamosConstants.ESTADO_DETALLE_ENTREGADA))
						.toString();

				nDetalles.setValue(DbSelectFns.selectCount(conn, TABLE_NAME,
						qual.toString()));
			}
		};

		command.execute();

		return nDetalles.getValue();
	}

	/**
	 * Obtiene el numero de detalles devueltos para una solicitud
    *
	 * @param idSolicitud
	 *            Identificador de la solicitud que deseamos comprobar
	 * @return Numero de detalles devuelto para la solicitud
	 */
	public int getNumDetallesDevueltos(final String idSolicitud) {
		final MutableInt nDetalles = new MutableInt(0);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				String qual = new StringBuffer()
						.append(" WHERE ")
						.append(DBUtils.generateEQTokenField(IDSOLICITUD_FIELD,
								idSolicitud))
						.append(" AND ")
						.append(DBUtils.generateEQTokenField(ESTADO_FIELD,
								PrestamosConstants.ESTADO_DETALLE_DEVUELTA))
						.toString();

				nDetalles.setValue(DbSelectFns.selectCount(conn, TABLE_NAME,
						qual.toString()));
			}
		};

		command.execute();

		return nDetalles.getValue();
	}

	/**
	 * Obtiene el numero de detalles para un solicitud
    *
	 * @param idSolicitud
	 *            Identificador de la solicitud que deseamos comprobar
	 * @return Numero de detalles
	 */
	public int getNumDetalles(final String idSolicitud) {
		final MutableInt nDetalles = new MutableInt(0);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				String qual = new StringBuffer()
						.append(" WHERE ")
						.append(DBUtils.generateEQTokenField(IDSOLICITUD_FIELD,
								idSolicitud)).toString();

				nDetalles.setValue(DbSelectFns.selectCount(conn, TABLE_NAME,
						qual.toString()));
			}
		};

		command.execute();

		return nDetalles.getValue();
	}

	/**
	 * Obtiene los detalles de una solicitud que se encuentran en un determinado
	 * estado.
    *
	 * @param estados
	 *            Estados en los que se debe encotrar el detalles
	 * @param identificacion
	 *            Identificador de la solicitud a la que pertenece los detalles
	 * @param type
	 *            Tipo del detalla(prestamo o consulta)
	 * @return Detalle de la solicitud
	 */
	public Collection getDetallesSolicitudXEstado(List estados,
			String identificacion, String type) {
		Collection detalles = null;
		final String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDSOLICITUD_FIELD,
						identificacion))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(TIPOSOLICITUD_FIELD, type))
				.append(" AND ")
				.append(DBUtils.generateInTokenField(ESTADO_FIELD, estados))
				.toString();

		if (type.equalsIgnoreCase(DetalleDBEntity.TIPO_DETALLE_PRESTAMO)) {
			detalles = getVOS(qual.toString(), getCustomSQLFrom(),
					getCustomColsDefs(), DetallePrestamoVO.class);
		} else {
			detalles = getVOS(qual.toString(), getCustomSQLFrom(),
					getCustomColsDefs(), DetalleConsultaVO.class);
		}

		return detalles;
	}

	/**
	 * Obtiene un listado de unidades documentales entregadas que pertenecen a
	 * un determinado expediente.
    *
	 * @param num_exp
	 *            Expediente del que deseamos obtener las unidades documentales.
	 * @param like
	 *            Like con la restriccion para la busqueda
	 * @return Unidades documentales que pertenecen al expediente y están
	 *         entregadas.
	 */
	public Collection busquedaEntregadaXNumExp(String num_exp, String like) {
		Collection prestamos = null;
		Collection consultas = null;

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateJoinCondition(IDUDOC_FIELD,
						UnidadDocumentalDBEntityImpl.CAMPO_ID))
				// s.SIGNATURAUDOC=ud.SIGNATURAUDOC
				.append(" AND ")
				.append(DBUtils
						.generateJoinCondition(
								UnidadDocumentalDBEntityImpl.CAMPO_ID,
								ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD))
				// uf.idelementocf=c.id
				.append(" AND ")
				.append(DBUtils.generateTokenFieldQualified(ESTADO_FIELD,
						PrestamosConstants.ESTADO_DETALLE_ENTREGADA,
						TABLE_NAME, ConstraintType.EQUAL))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						ElementoCuadroClasificacionDBEntityImpl.ARCHIVO_FIELD,
						ArchivoDbEntityImpl.ID_FIELD));// elementocf.idArchivo =
														// archivo.id

		if (!StringUtils.isEmpty(num_exp))
			qual.append(DBUtils.AND)
					.append(" (")
					.append(DBUtils
							.generateLikeFieldQualified(
									UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE,
									num_exp,
									UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL,
									like))
					.append(DBUtils.OR)
					.append(DBUtils.generateLikeFieldQualified(
							EXPEDIENTEUDOC_FIELD, num_exp, TABLE_NAME, like))
					.append(") ");

		StringBuffer prestamoConsulta = new StringBuffer(qual.toString())
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(TIPOSOLICITUD_FIELD,
						TIPO_DETALLE_PRESTAMO))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(IDSOLICITUD_FIELD,
						PrestamoDBEntity.CAMPO_ID));

		HashMap pairsTableNamesColsDefs = new HashMap();

		DbColumnDef[] definiciones2 = {
				ElementoCuadroClasificacionDBEntityImpl.CODIGO_FIELD,
				ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD,
				ElementoCuadroClasificacionDBEntityImpl.IDFONDO_FIELD,
				ElementoCuadroClasificacionDBEntityImpl.SUBTIPO_ELEMENTO_FIELD };
		pairsTableNamesColsDefs.put(
				ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO,
				definiciones2);

		pairsTableNamesColsDefs
				.put(UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL,
						null);

		DbColumnDef[] definiciones4 = { TIPOSOLICITUD_FIELD,
				EXPEDIENTEUDOC_FIELD, SIGNATURAUDOC_FIELD, IDUDOC_FIELD };
		pairsTableNamesColsDefs.put(TABLE_NAME, definiciones4);

		DbColumnDef[] definiciones5 = {
				new DbColumnDef("idSolicitud", PrestamoDBEntity.TABLE_NAME,
						PrestamoDBEntity.ID_COLUMN_NAME, DbDataType.SHORT_TEXT,
						32, false),
				new DbColumnDef("anioSolicitud", PrestamoDBEntity.TABLE_NAME,
						PrestamoDBEntity.ANO_COLUMN_NAME,
						DbDataType.SHORT_TEXT, 4, false),
				new DbColumnDef("ordenSolicitud", PrestamoDBEntity.TABLE_NAME,
						PrestamoDBEntity.ORDEN_COLUMN_NAME,
						DbDataType.LONG_INTEGER, 10, false) };
		pairsTableNamesColsDefs.put(PrestamoDBEntity.TABLE_NAME, definiciones5);

		DbColumnDef[] archivoDef = { new DbColumnDef("codigoArchivo",
				ArchivoDbEntityImpl.TABLE_NAME,
				ArchivoDbEntityImpl.CODIGO_COLUMN_NAME, DbDataType.SHORT_TEXT,
				32, false) };
		pairsTableNamesColsDefs.put(ArchivoDbEntityImpl.TABLE_NAME, archivoDef);

		prestamos = getDistinctVOS(prestamoConsulta.toString(),
				pairsTableNamesColsDefs, BusquedaDetalleVO.class);

		StringBuffer consultaConsulta = new StringBuffer(qual.toString())
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(TIPOSOLICITUD_FIELD,
						TIPO_DETALLE_CONSULTA))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(IDSOLICITUD_FIELD,
						ConsultaDBEntity.CAMPO_ID));

		pairsTableNamesColsDefs.remove(PrestamoDBEntity.TABLE_NAME);
		DbColumnDef[] definiciones6 = {
				new DbColumnDef("idSolicitud", ConsultaDBEntity.TABLE_NAME,
						ConsultaDBEntity.ID, DbDataType.SHORT_TEXT, 32, false),
				new DbColumnDef("anioSolicitud", ConsultaDBEntity.TABLE_NAME,
						ConsultaDBEntity.ANO, DbDataType.SHORT_TEXT, 4, false),
				new DbColumnDef("ordenSolicitud", ConsultaDBEntity.TABLE_NAME,
						ConsultaDBEntity.ORDEN, DbDataType.LONG_INTEGER, 10,
						false) };
		pairsTableNamesColsDefs.put(ConsultaDBEntity.TABLE_NAME, definiciones6);

		consultas = getDistinctVOS(consultaConsulta.toString(),
				pairsTableNamesColsDefs, BusquedaDetalleVO.class);

		// Añadimos los de tipo consulta
		prestamos.addAll(consultas);

		return prestamos;
	}

	/**
	 * Obtiene un listado de unidades documentales entregadas con una signatura.
    *
	 * @param signatura
	 *            Signatura de la u.documental
	 * @return Unidades documentales.
	 */
	public Collection busquedaEntregadasXSignatura(String signatura, String like) {
		Collection prestamos = null;
		Collection consultas = null;

		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateJoinCondition(IDUDOC_FIELD,
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD))
				// where s.SIGNATURAUDOC=ud.SIGNATURAUDOC AND
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(SIGNATURAUDOC_FIELD,
						UDocEnUiDepositoDbEntityImpl.SIGNATURAUDOC_FIELD))
				.append(" AND ")
				// producia problemas no aparecian las partes y ademas tampoco
				// el prestamo si se entregaba la primera parte
				.append(DBUtils
						.generateJoinCondition(
								UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD,
								ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						ElementoCuadroClasificacionDBEntityImpl.ARCHIVO_FIELD,
						ArchivoDbEntityImpl.ID_FIELD));// elementocf.idArchivo =
														// archivo.id

		if (!StringUtils.isEmpty(signatura)) {
			qual.append(" AND ").append(
					DBUtils.generateLikeFieldQualified(
							UDocEnUiDepositoDbEntityImpl.SIGNATURAUDOC_FIELD,
							signatura, UDocEnUiDepositoDbEntityImpl.TABLE_NAME,
							like));// ud.signaturaudoc like '" + signatura +
									// "%'");
		}

		StringBuffer prestamoConsulta = new StringBuffer(qual.toString())
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(ESTADO_FIELD,
						PrestamosConstants.ESTADO_DETALLE_ENTREGADA))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(TIPOSOLICITUD_FIELD,
						TIPO_DETALLE_PRESTAMO))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(IDSOLICITUD_FIELD,
						PrestamoDBEntity.CAMPO_ID));// s.IDSOLICITUD=p.ID

		HashMap pairsTableNamesColsDefs = new HashMap();
		DbColumnDef[] definiciones = {
				new DbColumnDef("Idudoc",
						UDocEnUiDepositoDbEntityImpl.TABLE_NAME,
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD),
				UDocEnUiDepositoDbEntityImpl.SIGNATURAUDOC_FIELD };

		pairsTableNamesColsDefs.put(UDocEnUiDepositoDbEntityImpl.TABLE_NAME,
				definiciones);

		DbColumnDef[] definiciones2 = {
				ElementoCuadroClasificacionDBEntityImpl.CODIGO_FIELD,
				ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD,
				ElementoCuadroClasificacionDBEntityImpl.IDFONDO_FIELD,
				ElementoCuadroClasificacionDBEntityImpl.SUBTIPO_ELEMENTO_FIELD };
		pairsTableNamesColsDefs.put(
				ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO,
				definiciones2);

		DbColumnDef[] definiciones4 = { TIPOSOLICITUD_FIELD, /* FESTADO_FIELD, */
				EXPEDIENTEUDOC_FIELD };
		pairsTableNamesColsDefs.put(TABLE_NAME, definiciones4);

		DbColumnDef[] definiciones5 = {
				new DbColumnDef("idSolicitud", PrestamoDBEntity.TABLE_NAME,
						PrestamoDBEntity.CAMPO_ID),
				new DbColumnDef("anioSolicitud", PrestamoDBEntity.TABLE_NAME,
						PrestamoDBEntity.CAMPO_ANO),
				new DbColumnDef("ordenSolicitud", PrestamoDBEntity.TABLE_NAME,
						PrestamoDBEntity.CAMPO_ORDEN) };
		pairsTableNamesColsDefs.put(PrestamoDBEntity.TABLE_NAME, definiciones5);

		DbColumnDef[] archivoDef = { new DbColumnDef("codigoArchivo",
				ArchivoDbEntityImpl.TABLE_NAME,
				ArchivoDbEntityImpl.CODIGO_COLUMN_NAME, DbDataType.SHORT_TEXT,
				32, false) };
		pairsTableNamesColsDefs.put(ArchivoDbEntityImpl.TABLE_NAME, archivoDef);

		// Obtenemos los que son prestamos
		prestamos = getDistinctVOS(prestamoConsulta.toString(),
				pairsTableNamesColsDefs, BusquedaDetalleVO.class);

		StringBuffer consultaConsulta = new StringBuffer(qual.toString())
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(ESTADO_FIELD,
						PrestamosConstants.ESTADO_DETALLE_ENTREGADA))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(TIPOSOLICITUD_FIELD,
						TIPO_DETALLE_CONSULTA))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(IDSOLICITUD_FIELD,
						ConsultaDBEntity.CAMPO_ID));

		pairsTableNamesColsDefs.remove(PrestamoDBEntity.TABLE_NAME);
		DbColumnDef[] definiciones6 = {
				new DbColumnDef("idSolicitud", ConsultaDBEntity.TABLE_NAME,
						ConsultaDBEntity.CAMPO_ID),
				new DbColumnDef("anioSolicitud", ConsultaDBEntity.TABLE_NAME,
						ConsultaDBEntity.CAMPO_ANO),
				new DbColumnDef("ordenSolicitud", ConsultaDBEntity.TABLE_NAME,
						ConsultaDBEntity.CAMPO_ORDEN) };
		pairsTableNamesColsDefs.put(ConsultaDBEntity.TABLE_NAME, definiciones6);

		consultas = getDistinctVOS(consultaConsulta.toString(),
				pairsTableNamesColsDefs, BusquedaDetalleVO.class);

		// Añadimos los de tipo consulta
		prestamos.addAll(consultas);

		return prestamos;
	}

	/**
	 * Obtiene un detalle de una solicitud (prestamo o consulta) entregada.
    *
	 * @param id
	 *            identificador de la solicitud.
	 * @param idUdoc
	 *            Identificador de la unidad documental
	 * @param signatura
	 *            Signatura del detalle
	 * @param type
	 *            Tipo de la solicitud(prestamo o consulta)
	 * @return Detalle de la solicitud entregada
	 */
	public BusquedaDetalleVO getUnidadEntregada(String id, String idUdoc,
			String signatura, String type) {
		HashMap pairs = new HashMap();
		pairs.put(TABLE_NAME, new DbColumnDef[] { TIPOSOLICITUD_FIELD,
				FESTADO_FIELD, IDUDOC_FIELD, EXPEDIENTEUDOC_FIELD });

		pairs.put(UDocEnUiDepositoDbEntityImpl.TABLE_NAME, new DbColumnDef[] {
				new DbColumnDef("Idudoc",
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD),
				UDocEnUiDepositoDbEntityImpl.SIGNATURAUDOC_FIELD });

		pairs.put(
				ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO,
				new DbColumnDef[] {
						ElementoCuadroClasificacionDBEntityImpl.CODIGO_FIELD,
						ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD,
						ElementoCuadroClasificacionDBEntityImpl.IDFONDO_FIELD,
						ElementoCuadroClasificacionDBEntityImpl.SUBTIPO_ELEMENTO_FIELD });

		pairs.put(ArchivoDbEntityImpl.TABLE_NAME,
				new DbColumnDef[] { new DbColumnDef("codigoArchivo",
						ArchivoDbEntityImpl.CODIGO_FIELD) });

		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(IDSOLICITUD_FIELD, id))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(IDUDOC_FIELD, idUdoc))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(SIGNATURAUDOC_FIELD,
						signatura))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(TIPOSOLICITUD_FIELD, type))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(IDUDOC_FIELD,
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(SIGNATURAUDOC_FIELD,
						UDocEnUiDepositoDbEntityImpl.SIGNATURAUDOC_FIELD))
				.append(" AND ")

				.append(DBUtils
						.generateJoinCondition(
								UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD,
								ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD))

				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						ElementoCuadroClasificacionDBEntityImpl.ARCHIVO_FIELD,
						ArchivoDbEntityImpl.ID_FIELD));

		if (TIPO_DETALLE_PRESTAMO.equals(type)) {
			qual.append(" AND ")
					.append(DBUtils.generateEQTokenField(ESTADO_FIELD,
							PrestamosConstants.ESTADO_DETALLE_ENTREGADA))
					.append(" AND ")
					.append(DBUtils.generateJoinCondition(IDSOLICITUD_FIELD,
							PrestamoDBEntity.CAMPO_ID));

			pairs.put(PrestamoDBEntity.TABLE_NAME, new DbColumnDef[] {
					new DbColumnDef("idSolicitud", PrestamoDBEntity.CAMPO_ID),
					new DbColumnDef("anioSolicitud",
							PrestamoDBEntity.TABLE_NAME,
							PrestamoDBEntity.CAMPO_ANO),
					new DbColumnDef("ordenSolicitud",
							PrestamoDBEntity.TABLE_NAME,
							PrestamoDBEntity.CAMPO_ORDEN) });

		} else // if (TIPO_DETALLE_CONSULTA.equals(type))
		{
			qual.append(" AND ")
					.append(DBUtils.generateEQTokenField(ESTADO_FIELD,
							ConsultasConstants.ESTADO_DETALLE_ENTREGADA))
					.append(" AND ")
					.append(DBUtils.generateJoinCondition(IDSOLICITUD_FIELD,
							ConsultaDBEntity.CAMPO_ID));

			pairs.put(ConsultaDBEntity.TABLE_NAME, new DbColumnDef[] {
					new DbColumnDef("idSolicitud", ConsultaDBEntity.CAMPO_ID),
					new DbColumnDef("anioSolicitud",
							ConsultaDBEntity.TABLE_NAME,
							ConsultaDBEntity.CAMPO_ANO),
					new DbColumnDef("ordenSolicitud",
							ConsultaDBEntity.TABLE_NAME,
							ConsultaDBEntity.CAMPO_ORDEN) });
		}

		return (BusquedaDetalleVO) getVO(qual.toString(), pairs,
				BusquedaDetalleVO.class);
	}

	public void insertDetallePrestamo(final DetallePrestamoVO detalle) {

		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						TABLE_COLUMNS, detalle);
				DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
						inputRecord);

			}

		};

		command.execute();
	}

	public void insertDetalleConsulta(final DetalleConsultaVO detalle) {

		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						TABLE_COLUMNS, detalle);
				DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
						inputRecord);

			}

		};

		command.execute();
	}

	/**
	 * Obtiene un detalle de una solicitud(prestamo o consulta) identificado por
	 * su id y por los ids de la unidad documental.
    *
	 * @param id
	 *            identificador de la solicitud.
	 * @param idUdoc
	 *            Identificador de la unidad documental
	 * @param Signatura
	 *            Signatura del detalle
	 * @param type
	 *            Tipo de la solicitud(prestamo o consulta)
	 * @return Detalle de la solicitud
	 * @throws DetalleNotFoundException
	 *             Si no se encuentra el elemento buscado.
	 */
	public DetalleVO getDetalle(String id, String idUdoc, String Signatura,
			String type) throws DetalleNotFoundException {
		DetalleVO d = null;

		final String qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDSOLICITUD_FIELD, id))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(TIPOSOLICITUD_FIELD, type))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(IDUDOC_FIELD, idUdoc))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(SIGNATURAUDOC_FIELD,
						Signatura)).toString();

		if (type.equals(TIPO_DETALLE_PRESTAMO)) {
			d = (DetallePrestamoVO) getVO(qual.toString(), getCustomSQLFrom(),
					getCustomColsDefs(), DetallePrestamoVO.class);
		} else {
			d = (DetalleConsultaVO) getVO(qual.toString(), getCustomSQLFrom(),
					getCustomColsDefs(), DetalleConsultaVO.class);
		}

		if (d == null)
			throw new DetalleNotFoundException(
					"No se ha encontrado el detalle del prestamo");

		return d;
	}

	public List getDetallesEntregadosByFechas(Date fechaIni, Date fechaFin) {

		StringBuffer qual = new StringBuffer().append("WHERE ");
		if (fechaIni != null) {
			qual.append(FESTADO_FIELD)
					.append(">=")
					.append(DBUtils.getNativeToDateSyntax(getConnection(),
							fechaIni, CustomDateFormat.SDF_YYYYMMDD));
		}

		if (fechaFin != null) {
			qual.append(DBUtils.getCondition(qual.toString()))
					.append(FESTADO_FIELD)
					.append("<")
					.append(DBUtils.getNativeToDateSyntax(getConnection(),
							DateUtils.incDay(fechaFin),
							CustomDateFormat.SDF_YYYYMMDD));
		}

		qual.append(DBUtils.getCondition(qual.toString()))
				.append(DBUtils.generateEQTokenField(ESTADO_FIELD, 6))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(IDUDOC_FIELD,
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD,
						HuecoDBEntityImpl.CAMPO_IDUINSTALACION))
				.append(" ORDER BY ").append(SIGNATURAUDOC_FIELD).append(",")
				.append(FESTADO_FIELD);

		TableDef tablaDetalle = new TableDef(TABLE_NAME);
		JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(tablaDetalle, IDUDOC_FIELD),
				new DbColumnDef(
						new TableDef(
								ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO),
						ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD)), };
		StringBuffer sqlFrom = new StringBuffer();
		sqlFrom.append(DBUtils.generateLeftOuterJoinCondition(tablaDetalle,
				joins));
		sqlFrom.append(",");
		sqlFrom.append(UDocEnUiDepositoDbEntityImpl.TABLE_NAME);
		sqlFrom.append(",");
		sqlFrom.append(HuecoDBEntityImpl.TABLE_NAME);

		DbColumnDef[] COLS_DEF_QUERY = (DbColumnDef[]) ArrayUtils
				.concat(new DbColumnDef[] {
						new DbColumnDef(new TableDef(
								HuecoDBEntityImpl.TABLE_NAME), new DbColumnDef(
								"ubicacion", HuecoDBEntityImpl.CAMPO_PATH)),
						new DbColumnDef(
								new TableDef(
										ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO),
								new DbColumnDef(
										"idElementoCF",
										ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD)),
						new DbColumnDef(
								new TableDef(
										ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO),
								new DbColumnDef(
										"subtipo",
										ElementoCuadroClasificacionDBEntityImpl.SUBTIPO_ELEMENTO_FIELD)) },
						TABLE_COLUMNS);

		return getVOS(qual.toString(), sqlFrom.toString(), COLS_DEF_QUERY,
				DetalleVO.class);

	}

	/*
	 * (sin Javadoc)
    *
	 * @see
	 * solicitudes.db.IDetalleDBEntity#getUDocsNoDispoblesParaRelacion(java.
	 * lang.String)
	 */
	public List getUDocsNoDispoblesParaRelacion(String idUinstalacion) {
		/*
		 * SELECT ASGPSOLICITUDUDOC.* FROM ASGPSOLICITUDUDOC INNER JOIN
		 * ASGDUDOCENUI ON ASGPSOLICITUDUDOC.IDUDOC = ASGDUDOCENUI.IDUNIDADDOC
		 * AND ASGPSOLICITUDUDOC.SIGNATURAUDOC = ASGDUDOCENUI.SIGNATURAUDOC
		 * WHERE ASGDUDOCENUI.IDUINSTALACION = '<idUinstalacion>' AND
		 * ASGPSOLICITUDUDOC.ESTADO IN(Constants.ESTADOS_DETALLES_EN_PRESTAMO)
		 */

		int[] estados = Constants.ESTADOS_DETALLES_EN_PRESTAMOS;

		TableDef tablaDetalle = new TableDef(TABLE_NAME);
		JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(tablaDetalle, IDUDOC_FIELD), new DbColumnDef(
						new TableDef(UDocEnUiDepositoDbEntityImpl.TABLE_NAME),
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD)) };

		StringBuffer sqlFrom = new StringBuffer()
				.append(DBUtils.generateInnerJoinCondition(tablaDetalle, joins))
				.append(DBUtils.AND)
				.append(DBUtils.generateJoinCondition(IDUDOC_FIELD,
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD));

		String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateInTokenField(ESTADO_FIELD, estados))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD,
						idUinstalacion)).toString();

		return getVOS(qual.toString(), sqlFrom.toString(), TABLE_COLUMNS,
				DetalleVO.class);
	}

	/**
	 * Añade a la condición de una consulta, la subconsulta referida a los
	 * estados.
    *
	 * @param campoEstado
	 *            . El campo de la tabla de la base de datos que contiene los
	 *            estados
	 * @param estados
	 *            . Array de enteros que representan los estados
	 * @param andOperator
	 *            . boleano que indica si se quiere añadir al principio de la
	 *            subconsulta el operador "AND"
	 * @return texto sql de la subconsulta generada.
	 */
	private String addEstadosInWhereCondition(String campoEstado,
			String[] estados, boolean andOperator) {
		StringBuffer qual = new StringBuffer("");
		if (estados != null && estados.length > 0) {
			if (andOperator)
				qual.append(DBUtils.AND);

			qual.append("(");
			for (int i = 0; i < estados.length; i++) {
				qual.append(campoEstado + "=" + estados[i]);
				if (i + 1 < estados.length) {
					qual.append(DBUtils.OR);
				}
			}
			qual.append(")");
		}
		return qual.toString();
	}

	/**
	 * Añade a la condición de una consulta, la subconsulta referida a los
	 * codigos de referencia.
    *
	 * @param campoEstado
	 *            . El campo de la tabla de la base de datos que contiene los
	 *            estados
	 * @param codRefs
	 *            . Array de Strings que representan los codigos de referencia
	 * @param andOperator
	 *            boleano que indica si se quiere añadir al principio de la
	 *            subconsulta el operador "AND"
	 * @return texto sql de la subconsulta generada.
	 */

	/**
	 * @param busquedaElementosVO
	 *            . Contiene los criterios de búsqueda
	 * @return Obtiene el texto sql referido a la sentencia union all para
	 *         prestamos y consultas
	 */
	private String getUnionSubSqlPrestamosConsultas(
			BusquedaElementosVO busquedaElementosVO) {
		StringBuffer sql = new StringBuffer();
		boolean concatenar = false;

		sql.append("SELECT ").append(PrestamoDBEntity.ID_COLUMN_NAME)
				.append(",").append(PrestamoDBEntity.FENTREGA_COLUMN_NAME)
				.append(" FROM ").append(PrestamoDBEntity.TABLE_NAME);

		if (TiposUsuario.INTERNO_VALUE.equalsIgnoreCase(busquedaElementosVO
				.getTipoUsuarioPrestamos())) {
			sql.append(DBUtils.WHERE).append(
					DBUtils.generateIsNotNullCondition(getConnection(),
							PrestamoDBEntity.CAMPO_IDUSRSOLICITANTE));
			concatenar = true;
		} else if (TiposUsuario.EXTERNO_VALUE
				.equalsIgnoreCase(busquedaElementosVO.getTipoUsuarioPrestamos())) {
			sql.append(DBUtils.WHERE).append(
					DBUtils.generateIsNullCondition(getConnection(),
							PrestamoDBEntity.CAMPO_IDUSRSOLICITANTE));
			concatenar = true;
		}
		if (StringUtils.isNotEmpty(busquedaElementosVO.getSolicitante())) {
			if (concatenar)
				sql.append(DBUtils.AND);
			else
				sql.append(DBUtils.WHERE);
			sql.append(DBUtils.generateLikeTokenField(
					PrestamoDBEntity.CAMPO_NUSRSOLICITANTE,
					busquedaElementosVO.getSolicitante()));
		}
		concatenar = false;
		sql.append(" UNION ALL ").append("SELECT ").append(ConsultaDBEntity.ID)
				.append(",").append(ConsultaDBEntity.FENTREGA).append(" FROM ")
				.append(ConsultaDBEntity.TABLE_NAME);

		if (!StringUtils.isEmpty(busquedaElementosVO.getTipoUsuarioConsultas())) {
			sql.append(DBUtils.WHERE).append(
					DBUtils.generateEQTokenField(
							ConsultaDBEntity.CAMPO_TIPOENTCONSULTORA,
							busquedaElementosVO.getTipoUsuarioConsultas()));
			concatenar = true;
		}
		if (StringUtils.isNotEmpty(busquedaElementosVO.getSolicitante())) {
			if (concatenar)
				sql.append(DBUtils.AND);
			else
				sql.append(DBUtils.WHERE);
			sql.append(DBUtils.generateLikeTokenField(
					ConsultaDBEntity.CAMPO_NUSRCONSULTOR,
					busquedaElementosVO.getSolicitante()));
		}
		return sql.toString();
	}

	/*
	 * (sin Javadoc)
    *
	 * @see
	 * solicitudes.db.IDetalleDBEntity#getIdsElementosEnPrestamosOrConsultas
	 * (fondos.vos.BusquedaElementosVO, java.lang.String)
	 */
	public List getIdsElementosEnPrestamosOrConsultas(
			BusquedaElementosVO busquedaElementosVO, String tipoServicio,
			int numMaxResults) throws TooManyResultsException {

		DbColumnDef fEntregaColumnDef = null;
		String servicioTable = null;
		DbColumnDef idServicioColumnDef = null;
		DbColumnDef solicitanteColumnDef = null;
		StringBuffer sqlUser = new StringBuffer();

		if (PrestamosConstants.TIPO_SOLICITUD_PRESTAMO == Integer
				.parseInt(tipoServicio)) {
			fEntregaColumnDef = fechaEntregaPrestamoColumnDef;
			servicioTable = ASGPPRESTAMO_TABLE.getDeclaration();
			idServicioColumnDef = idPrestamoColumnDef;
			solicitanteColumnDef = PrestamoDBEntity.CAMPO_NUSRSOLICITANTE;

			if (TiposUsuario.INTERNO_VALUE.equalsIgnoreCase(busquedaElementosVO
					.getTipoUsuarioPrestamos()))
				sqlUser.append(DBUtils.generateIsNotNullCondition(
						getConnection(),
						PrestamoDBEntity.CAMPO_IDUSRSOLICITANTE));

			else if (TiposUsuario.EXTERNO_VALUE
					.equalsIgnoreCase(busquedaElementosVO
							.getTipoUsuarioPrestamos()))
				sqlUser.append(DBUtils.generateIsNullCondition(getConnection(),
						PrestamoDBEntity.CAMPO_IDUSRSOLICITANTE));

		} else {
			fEntregaColumnDef = fechaEntregaConsultaColumnDef;
			servicioTable = ASGPCONSULTA_TABLE.getDeclaration();
			idServicioColumnDef = idConsultaColumnDef;
			solicitanteColumnDef = ConsultaDBEntity.CAMPO_NUSRCONSULTOR;
			if (!StringUtils.isEmpty(busquedaElementosVO
					.getTipoUsuarioConsultas()))
				sqlUser.append(DBUtils.generateEQTokenField(
						ConsultaDBEntity.CAMPO_TIPOENTCONSULTORA,
						busquedaElementosVO.getTipoUsuarioConsultas()));
		}

		DbColumnDef[] COLS = { DetalleDBEntityImpl.IDUDOC_FIELD,
				DetalleDBEntityImpl.IDSOLICITUD_FIELD,
				DetalleDBEntityImpl.SIGNATURAUDOC_FIELD };

		String tables = ASGPSOLICITUDUDOC_TABLE.getDeclaration() + ","
				+ servicioTable;

		StringBuffer with = new StringBuffer();
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(idSolicitudColumnDef.getQualifiedName()).append(" = ")
				.append(idServicioColumnDef.getQualifiedName());

		if (tituloElementoCF2.getQualifiedName().equalsIgnoreCase(
				busquedaElementosVO.getOrderColumnName())) {
			tables += "," + ASGFELEMENTOCF2_TABLE.getDeclaration();
			qual.append(DBUtils.AND)
					.append(idPadreElementoCF.getQualifiedName()).append("=")
					.append(idElementoCF2.getQualifiedName());
		}

		if (!StringUtils.isEmpty(sqlUser.toString())) {
			qual.append(DBUtils.AND);
			qual.append(sqlUser);
		}

		if (busquedaElementosVO.getFechaIniIni() != null) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateTokenFieldDateAnioMesDia(getConnection(),
							fEntregaColumnDef, ">=",
							busquedaElementosVO.getFechaIniIni()));
		}

		if (busquedaElementosVO.getFechaIniFin() != null) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateTokenFieldDateAnioMesDia(getConnection(),
							fEntregaColumnDef, "<=",
							busquedaElementosVO.getFechaIniFin()));
		}

		if (StringUtils.isNotEmpty(busquedaElementosVO.getNumeroExpediente())) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateLikeTokenField(numExpColumnDef,
							busquedaElementosVO.getNumeroExpediente()));
		}

		if (StringUtils.isNotEmpty(busquedaElementosVO.getSignatura())) {
			qual.append(" AND ").append(
					DBUtils.generateLikeFieldQualified(SIGNATURAUDOC_FIELD,
							busquedaElementosVO.getSignatura(), TABLE_NAME,
							busquedaElementosVO.getSignaturaLike()));
		}

		if (StringUtils.isNotEmpty(busquedaElementosVO.getSolicitante())) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateLikeTokenField(solicitanteColumnDef,
							busquedaElementosVO.getSolicitante()));
		}

		if (busquedaElementosVO.getFechaFinIni() != null) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateTokenFieldDateAnioMesDia(getConnection(),
							FESTADO_FIELD, ">=",
							busquedaElementosVO.getFechaFinIni()));
		}

		if (busquedaElementosVO.getFechaFinFin() != null) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateTokenFieldDateAnioMesDia(getConnection(),
							FESTADO_FIELD, "<=",
							busquedaElementosVO.getFechaFinFin()));
		}

		String[] estados = null;
		if (busquedaElementosVO.getFechaFinIni() != null
				&& busquedaElementosVO.getFechaFinFin() != null) {
			estados = new String[] { String
					.valueOf(EstadoSolicitudEntregaUDocConstants.DEVUELTA) };
		} else {
			estados = new String[] {
					String.valueOf(EstadoSolicitudEntregaUDocConstants.ENTREGADA),
					String.valueOf(EstadoSolicitudEntregaUDocConstants.DEVUELTA) };
		}
		qual.append(addEstadosInWhereCondition(
				estadoSolicitudColumnDef.getQualifiedName(), estados, true));

		if (!StringUtils.isContenidoVacio(busquedaElementosVO
				.getIdObjetoAmbito())) {
			ConsultaConnectBy consulta = null;
			try {
				consulta = componerCondicionIdAmbito(TABLE_NAME,
						busquedaElementosVO.getIdObjetoAmbito(), new HashMap());
				if (consulta != null) {
					if (StringUtils.isNotEmpty(consulta.getWithClause())) {
						with.append(consulta.getWithClause());
					}
					if (StringUtils.isNotEmpty(consulta.getSqlClause())) {
						qual.append(DBUtils.AND);
						qual.append(consulta.getSqlClause());
					}
				}
			} catch (IeciTdException e) {
				logger.error("Error al Generar la condición de ámbito", e);
			}
		}
		return getVOS(with.toString(), qual.toString(), tables, COLS,
				DetalleVO.class, "", numMaxResults, tables,
				getCustomizedField(IDSOLICITUD_FIELD, TABLE_NAME)
						.getQualifiedName());
	}

	/*
	 * (sin Javadoc)
    *
	 * @see
	 * solicitudes.db.IDetalleDBEntity#getIdsElementosEnPrestamosYConsultas(
	 * fondos.vos.BusquedaElementosVO)
	 */
	public List getIdsElementosEnPrestamosYConsultas(
			BusquedaElementosVO busquedaElementosVO, int numMaxResults)
			throws TooManyResultsException {

		TableDef PRESCON_TABLE = new TableDef("PRESCON", "PRESCON");

		DbColumnDef[] COLS = { DetalleDBEntityImpl.IDUDOC_FIELD,
				DetalleDBEntityImpl.IDSOLICITUD_FIELD,
				DetalleDBEntityImpl.SIGNATURAUDOC_FIELD };
		DbColumnDef presconIdColumnDef = new DbColumnDef(null,
				PRESCON_TABLE.getName(), "id", DbDataType.SHORT_TEXT, 32, false);
		DbColumnDef presconFEntregaColumnDef = new DbColumnDef(PRESCON_TABLE,
				PrestamoDBEntity.CAMPO_FENTREGA);
		String sqlInterna = getUnionSubSqlPrestamosConsultas(busquedaElementosVO);

		String tables = ASGPSOLICITUDUDOC_TABLE.getDeclaration() + ","
				+ ASGFELEMENTOCF_TABLE.getDeclaration() + ",(" + sqlInterna
				+ ")" + PRESCON_TABLE.getName();

		StringBuffer with = new StringBuffer();
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(idColumnDef.getQualifiedName()).append("=")
				.append(idElementoCF.getQualifiedName())

				.append(" AND ")
				.append(idSolicitudColumnDef.getQualifiedName()).append(" = ")
				.append(presconIdColumnDef.getQualifiedName());

		if (tituloElementoCF2.getQualifiedName().equalsIgnoreCase(
				busquedaElementosVO.getOrderColumnName())) {
			tables += "," + ASGFELEMENTOCF2_TABLE.getDeclaration();
			qual.append(DBUtils.AND)
					.append(idPadreElementoCF.getQualifiedName()).append("=")
					.append(idElementoCF2.getQualifiedName());
		}

		if (busquedaElementosVO.getFechaIniIni() != null) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateTokenFieldDateAnioMesDia(getConnection(),
							presconFEntregaColumnDef, ">=",
							busquedaElementosVO.getFechaIniIni()));
		}

		if (busquedaElementosVO.getFechaIniFin() != null) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateTokenFieldDateAnioMesDia(getConnection(),
							presconFEntregaColumnDef, "<=",
							busquedaElementosVO.getFechaIniFin()));
		}

		if (StringUtils.isNotEmpty(busquedaElementosVO.getNumeroExpediente())) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateLikeTokenField(numExpColumnDef,
							busquedaElementosVO.getNumeroExpediente()));
		}

		if (busquedaElementosVO.getFechaFinIni() != null) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateTokenFieldDateAnioMesDia(getConnection(),
							FESTADO_FIELD, ">=",
							busquedaElementosVO.getFechaFinIni()));
		}

		if (busquedaElementosVO.getFechaFinFin() != null) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateTokenFieldDateAnioMesDia(getConnection(),
							FESTADO_FIELD, "<=",
							busquedaElementosVO.getFechaFinFin()));
		}

		if (StringUtils.isNotEmpty(busquedaElementosVO.getSignatura())) {
			qual.append(" AND ").append(
					DBUtils.generateLikeFieldQualified(SIGNATURAUDOC_FIELD,
							busquedaElementosVO.getSignatura(), TABLE_NAME,
							busquedaElementosVO.getSignaturaLike()));
		}

		String[] estados = null;
		if (busquedaElementosVO.getFechaFinIni() != null
				&& busquedaElementosVO.getFechaFinFin() != null) {
			estados = new String[] { String
					.valueOf(EstadoSolicitudEntregaUDocConstants.DEVUELTA) };
		} else {
			estados = new String[] {
					String.valueOf(EstadoSolicitudEntregaUDocConstants.ENTREGADA),
					String.valueOf(EstadoSolicitudEntregaUDocConstants.DEVUELTA) };
		}

		qual.append(addEstadosInWhereCondition(
				estadoSolicitudColumnDef.getQualifiedName(), estados, true));

		if (!StringUtils.isContenidoVacio(busquedaElementosVO
				.getIdObjetoAmbito())) {
			ConsultaConnectBy consulta = null;
			try {
				consulta = componerCondicionIdAmbito(TABLE_NAME,
						busquedaElementosVO.getIdObjetoAmbito(), new HashMap());
				if (consulta != null) {
					if (StringUtils.isNotEmpty(consulta.getWithClause())) {
						with.append(consulta.getWithClause());
					}
					if (StringUtils.isNotEmpty(consulta.getSqlClause())) {
						qual.append(DBUtils.AND);
						qual.append(consulta.getSqlClause());
					}
				}
			} catch (IeciTdException e) {
				logger.error("Error al Generar la condición de ámbito", e);
			}
		}
		return getVOS(with.toString(), qual.toString(), tables, COLS,
				DetalleVO.class, "", numMaxResults, tables,
				getCustomizedField(IDSOLICITUD_FIELD, TABLE_NAME)
						.getQualifiedName());
	}

	/*
	 * (sin Javadoc)
    *
	 * @see
	 * solicitudes.db.IDetalleDBEntity#getElementosEnPrestamosOrConsultas(java
	 * .util.List, fondos.vos.BusquedaElementosVO, java.lang.String)
	 */
	public List getElementosEnPrestamosOrConsultas(List idsTiposToShow,
			BusquedaElementosVO busquedaElementosVO, String tipoServicio) {
		DbColumnDef fechaEntregaColumnDef = null;
		DbColumnDef fechaEstadoColumnDef = fechaEstadoSolicitudColumnDef;
		DbColumnDef nUsrSolicitudColumnDef = null;
		JoinDefinition[] joinsSolicitudIdServicio = null;

		String sqlColumnSolicitantePrestamo = "SELECT "
				+ PrestamoDBEntity.CAMPO_NUSRSOLICITANTE.getQualifiedName()
				+ " FROM " + ASGPPRESTAMO_TABLE.getDeclaration() + " WHERE "
				+ idSolicitudColumnDef.getQualifiedName() + " = "
				+ idPrestamoColumnDef.getQualifiedName();

		String sqlColumnSolicitanteConsulta = "SELECT "
				+ ConsultaDBEntity.CAMPO_NUSRCONSULTOR.getQualifiedName()
				+ " FROM " + ASGPCONSULTA_TABLE.getDeclaration() + " WHERE "
				+ idSolicitudColumnDef.getQualifiedName() + " = "
				+ idConsulta.getQualifiedName();

		if (PrestamosConstants.TIPO_SOLICITUD_PRESTAMO == Integer
				.parseInt(tipoServicio)) {
			fechaEntregaColumnDef = fechaEntregaPrestamoColumnDef;
			nUsrSolicitudColumnDef = new DbColumnDef("solicitante", "("
					+ sqlColumnSolicitantePrestamo + ") as solicitante",
					DbDataType.SHORT_TEXT);
			joinsSolicitudIdServicio = new JoinDefinition[] { new JoinDefinition(
					idSolicitudColumnDef, idPrestamoColumnDef)

			};
		} else {
			fechaEntregaColumnDef = fechaEntregaConsultaColumnDef;
			nUsrSolicitudColumnDef = new DbColumnDef("solicitante", "("
					+ sqlColumnSolicitanteConsulta + ") as solicitante",
					DbDataType.SHORT_TEXT);
			joinsSolicitudIdServicio = new JoinDefinition[] { new JoinDefinition(
					idSolicitudColumnDef, idConsultaColumnDef)

			};
		}

		DbColumnDef[] COLS = { idSolicitudColumnDef, idColumnDef,
				codReferenciaCF, tituloColumnDef, signaturaUDocColumnDef,
				nombreSerieColumnDef, tipoSolicitudColumnDef,
				fechaEntregaColumnDef,
				ElementoCuadroClasificacionDBEntityImpl.SUBTIPO_ELEMENTO_FIELD,
				EXPEDIENTEUDOC_FIELD, estadoSolicitudColumnDef,
				fechaEstadoColumnDef, nUsrSolicitudColumnDef };

		JoinDefinition[] joinsSolicitudElementoCF = new JoinDefinition[] { new JoinDefinition(
				idColumnDef, idElementoCF)

		};

		JoinDefinition[] joinsElementoCFElementoCF2 = new JoinDefinition[] { new JoinDefinition(
				idPadreElementoCF, idElementoCF2)

		};

		String TABLES = DBUtils.generateLeftOuterJoinCondition(
				ASGPSOLICITUDUDOC_TABLE, joinsSolicitudElementoCF)
				+ DBUtils
						.generateLeftOuterJoinChainedCondition(joinsElementoCFElementoCF2)
				+ DBUtils
						.generateLeftOuterJoinChainedCondition(joinsSolicitudIdServicio);

		StringBuffer qual = new StringBuffer();
		qual.append(generateSqlTextToIds(idsTiposToShow, idColumnDef,
				idSolicitudColumnDef, signaturaColumnDef, true));

		String[] estados = new String[] {
				String.valueOf(EstadoSolicitudEntregaUDocConstants.ENTREGADA),
				String.valueOf(EstadoSolicitudEntregaUDocConstants.DEVUELTA) };
		qual.append(addEstadosInWhereCondition(
				estadoSolicitudColumnDef.getQualifiedName(), estados, true));

		if (busquedaElementosVO.getOrderColumnName() != null) {
			qual.append(" ORDER BY ")
					.append(getColumnNameInOrderBy(busquedaElementosVO,
							codReferenciaCF, tituloColumnDef,
							signaturaUDocColumnDef, tituloElementoCF2,
							fechaEntregaColumnDef.getQualifiedName(),
							estadoSolicitudColumnDef,
							fechaEstadoSolicitudColumnDef, "solicitante"))
					.append(busquedaElementosVO.getTipoOrdenacion());
		}
		return getVOS(qual.toString(), TABLES, COLS, DetalleVO.class);
	}

	/*
	 * (sin Javadoc)
    *
	 * @see
	 * solicitudes.db.IDetalleDBEntity#getElementosEnPrestamosYConsultas(java
	 * .util.List, fondos.vos.BusquedaElementosVO)
	 */
	public List getElementosEnPrestamosYConsultas(List idsTiposToShow,
			BusquedaElementosVO busquedaElementosVO) {

		String sqlColumnFEntregaPrestamo = "SELECT "
				+ fechaEntregaPrestamoColumnDef + " FROM "
				+ ASGPPRESTAMO_TABLE.getDeclaration() + " WHERE "
				+ idSolicitudColumnDef.getQualifiedName() + " = "
				+ idPrestamoColumnDef.getQualifiedName();

		String sqlColumnFEntregaConsulta = "SELECT "
				+ fechaEntregaConsultaColumnDef + " FROM "
				+ ASGPCONSULTA_TABLE.getDeclaration() + " WHERE "
				+ idSolicitudColumnDef.getQualifiedName() + " = "
				+ idConsulta.getQualifiedName();

		String sqlColumnEstadoPrestamoYConsulta = sqlColumnFEntregaPrestamo
				+ " UNION ALL " + sqlColumnFEntregaConsulta;

		String sqlColumnSolicitantePrestamo = "SELECT "
				+ PrestamoDBEntity.CAMPO_NUSRSOLICITANTE.getQualifiedName()
				+ " FROM " + ASGPPRESTAMO_TABLE.getDeclaration() + " WHERE "
				+ idSolicitudColumnDef.getQualifiedName() + " = "
				+ idPrestamoColumnDef.getQualifiedName();

		String sqlColumnSolicitanteConsulta = "SELECT "
				+ ConsultaDBEntity.CAMPO_NUSRCONSULTOR.getQualifiedName()
				+ " FROM " + ASGPCONSULTA_TABLE.getDeclaration() + " WHERE "
				+ idSolicitudColumnDef.getQualifiedName() + " = "
				+ idConsulta.getQualifiedName();

		String sqlColumnSolicitantePrestamoYConsulta = sqlColumnSolicitantePrestamo
				+ " UNION ALL " + sqlColumnSolicitanteConsulta;

		DbColumnDef[] COLS = { idSolicitudColumnDef, idColumnDef,
				codReferenciaCF, tituloColumnDef, signaturaUDocColumnDef,
				nombreSerieColumnDef, tipoSolicitudColumnDef,
				ElementoCuadroClasificacionDBEntityImpl.SUBTIPO_ELEMENTO_FIELD,
				EXPEDIENTEUDOC_FIELD, null, ESTADO_FIELD, FESTADO_FIELD, null };

		JoinDefinition[] joinsSolicitudElementoCF = new JoinDefinition[] { new JoinDefinition(
				idColumnDef, idElementoCF)

		};

		JoinDefinition[] joinsElementoCFElementoCF2 = new JoinDefinition[] { new JoinDefinition(
				idPadreElementoCF, idElementoCF2)

		};

		String[] tipoServicio = busquedaElementosVO.getTiposServicio();

		if (tipoServicio.length == 2) {
			COLS[9] = new DbColumnDef("fentrega", "("
					+ sqlColumnEstadoPrestamoYConsulta + ") as fentrega",
					DbDataType.DATE_TIME);
			COLS[12] = new DbColumnDef("solicitante", "("
					+ sqlColumnSolicitantePrestamoYConsulta
					+ ") as solicitante", DbDataType.SHORT_TEXT);
		} else if (tipoServicio[0].equalsIgnoreCase(String
				.valueOf(PrestamosConstants.TIPO_SOLICITUD_PRESTAMO))) {
			COLS[9] = new DbColumnDef("fentrega", "("
					+ sqlColumnFEntregaPrestamo + ") as fentrega",
					DbDataType.DATE_TIME);
			COLS[12] = new DbColumnDef("solicitante", "("
					+ sqlColumnSolicitantePrestamo + ") as solicitante",
					DbDataType.SHORT_TEXT);
		} else {
			COLS[9] = new DbColumnDef("fentrega", "("
					+ sqlColumnFEntregaConsulta + ") as fentrega",
					DbDataType.DATE_TIME);
			COLS[12] = new DbColumnDef("solicitante", "("
					+ sqlColumnSolicitanteConsulta + ") as solicitante",
					DbDataType.SHORT_TEXT);
		}

		String TABLES = DBUtils.generateInnerJoinCondition(
				ASGPSOLICITUDUDOC_TABLE, joinsSolicitudElementoCF)
				+ DBUtils
						.generateInnerJoinChainedCondition(joinsElementoCFElementoCF2);

		StringBuffer qual = new StringBuffer();
		qual.append(generateSqlTextToIds(idsTiposToShow, idColumnDef,
				idSolicitudColumnDef, signaturaColumnDef, true));

		String[] estados = new String[] {
				String.valueOf(EstadoSolicitudEntregaUDocConstants.ENTREGADA),
				String.valueOf(EstadoSolicitudEntregaUDocConstants.DEVUELTA) };
		qual.append(addEstadosInWhereCondition(
				estadoSolicitudColumnDef.getQualifiedName(), estados, true));

		if (busquedaElementosVO.getOrderColumnName() != null) {
			qual.append(" ORDER BY ")
					.append(getColumnNameInOrderBy(busquedaElementosVO,
							codReferenciaCF, tituloColumnDef,
							signaturaUDocColumnDef, tituloElementoCF2,
							"fentrega", ESTADO_FIELD, FESTADO_FIELD,
							"solicitante"))
					.append(busquedaElementosVO.getTipoOrdenacion());
		}
		return getVOS(qual.toString(), TABLES, COLS, DetalleVO.class);

	}

	/**
	 * @param list
	 *            . Lista de {@link DetalleVO}. Cada uno de ellos contiene el id
	 *            de la unidad documental el de la solicitud y la signatura
	 * @param idColumnDef
	 *            . Columna que representa el id de la unidad documental
	 * @param idSolicitudColumnDef
	 *            . Columna que representa el id de la solicitud
	 * @param whereOperator
	 *            . true si queremos que al comienzo añada el operador where,
	 *            false en caso contrario
	 * @return El texto sql con los ids de los elementos a mostrar
	 */
	private String generateSqlTextToIds(List list, DbColumnDef idColumnDef,
			DbColumnDef idSolicitudColumnDef, DbColumnDef signaturaColumnDef,
			boolean whereOperator) {
		StringBuffer sqlText = new StringBuffer();
		if (!ListUtils.isEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				DetalleVO detalleVO = (DetalleVO) list.get(i);

				if (sqlText.toString().length() > 0)
					sqlText.append(DBUtils.OR);

				sqlText.append("(")
						.append((DBUtils.generateEQTokenField(idColumnDef,
								detalleVO.getIdudoc())))
						.append(DBUtils.AND)
						.append((DBUtils.generateEQTokenField(
								idSolicitudColumnDef,
								String.valueOf(detalleVO.getIdsolicitud()))))
						.append(DBUtils.AND)
						.append((DBUtils.generateEQTokenField(
								signaturaColumnDef,
								String.valueOf(detalleVO.getSignaturaudoc()))))
						.append(")");
			}
		}
		if (sqlText.toString().length() > 0 && whereOperator)
			sqlText.insert(0, DBUtils.WHERE);

		return sqlText.toString();
	}

	/**
    *
	 * @param busquedaElementosVO
	 *            . Contiene los criterios de búsqueda
	 * @param codReferenciaCF
	 *            . Columna que representa el código de referencia
	 * @param tituloColumnDef
	 *            . Columna que representa el titulo de la unidad documental
	 * @param signaturaUDocColumnDef
	 *            . Columna que representa la signatura
	 * @param tituloElementoCF2
	 *            . Columna que representa el nombre de la signatura a la que
	 *            pertenece la unidad documental
	 * @param fEntrega
	 *            . Fecha de entrega de la unidad documental
	 * @return nombre de la columna por la que ordenar de acuerdo con los
	 *         criterios de búsqueda
	 */
	private String getColumnNameInOrderBy(
			BusquedaElementosVO busquedaElementosVO,
			DbColumnDef codReferenciaCF, DbColumnDef tituloColumnDef,
			DbColumnDef signaturaUDocColumnDef, DbColumnDef tituloElementoCF2,
			String fEntrega, DbColumnDef estado, DbColumnDef fEstado,
			String solicitante) {

		String columnName = null;

		if (busquedaElementosVO.getOrderColumnName() == null)
			columnName = null;
		else if (busquedaElementosVO.getOrderColumnName().equalsIgnoreCase(
				COD_REFERENCIA_COLUMN_NAME))
			columnName = codReferenciaCF.getQualifiedName();
		else if (busquedaElementosVO.getOrderColumnName().equalsIgnoreCase(
				TITULO_COLUMN_NAME))
			columnName = tituloColumnDef.getQualifiedName();
		else if (busquedaElementosVO.getOrderColumnName().equalsIgnoreCase(
				SIGNATURAUDOC_COLUMN_NAME))
			columnName = signaturaUDocColumnDef.getQualifiedName();
		else if (busquedaElementosVO.getOrderColumnName().equalsIgnoreCase(
				"nombreSerie"))
			columnName = tituloElementoCF2.getQualifiedName();
		else if (busquedaElementosVO.getOrderColumnName().equalsIgnoreCase(
				"fentrega"))
			columnName = fEntrega;
		else if (busquedaElementosVO.getOrderColumnName().equalsIgnoreCase(
				ESTADO_COLUMN_NAME))
			columnName = estado.getQualifiedName();
		else if (busquedaElementosVO.getOrderColumnName().equalsIgnoreCase(
				FESTADO_COLUMN_NAME))
			columnName = fEstado.getQualifiedName();
		else if (busquedaElementosVO.getOrderColumnName().equalsIgnoreCase(
				"solicitante"))
			columnName = solicitante;
		else
			columnName = codReferenciaCF.getQualifiedName();

		return columnName;
	}

	/**
	 * Obtiene un listado de los detalles con el identificador de udoc y la
	 * signatura pasados y en los estados pasados
    *
	 * @param idudoc
	 *            Identificador de la unidad documental
	 * @param signatura
	 *            Signatura de la unidad documental
	 * @param type
	 *            Tipo del detalle al que deseamos castear los resultado
	 *            obtenidos(detalle de prestamo o de consulta)
	 * @param estados
	 *            Estados de los detalles que queremos obtener
	 * @return Listado de los detalles
	 */
	public Collection getDetalles(String idudoc, String signatura, String type,
			String[] estados) {
		Collection result = null;

		StringBuffer qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDUDOC_FIELD, idudoc))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(SIGNATURAUDOC_FIELD,
						signatura));
		if (ArrayUtils.isNotEmpty(estados)) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateInTokenField(ESTADO_FIELD, estados));
		}
		qual.append(" ORDER BY ").append(FINICIALUSO_FIELD.getQualifiedName())
				.toString();

		if (type.equals(TIPO_DETALLE_PRESTAMO))
			result = getVOS(qual.toString(), getCustomSQLFrom(),
					getCustomColsDefs(), DetallePrestamoVO.class);
		else
			result = getVOS(qual.toString(), getCustomSQLFrom(),
					getCustomColsDefs(), DetalleConsultaVO.class);

		return result;
	}

	public int getCountSolicitudesByIdMotivo(String idMotivo, String type) {
		StringBuffer qual = new StringBuffer();
		if (StringUtils.isNotEmpty(idMotivo)) {
			qual.append(DBUtils.getCondition(qual.toString()));
			qual.append(DBUtils.generateEQTokenField(IDMOTIVO_FIELD, idMotivo));
		}

		if (StringUtils.isNotEmpty(type)) {
			qual.append(DBUtils.getCondition(qual.toString()));
			qual.append(DBUtils.generateEQTokenField(TIPOSOLICITUD_FIELD, type));
		}

		return getVOCount(qual.toString(), TABLE_NAME);
	}

	private ConsultaConnectBy componerCondicionIdAmbito(String aliasElemento,
			String[] idsAmbito, Map condicionesMap) throws IeciTdException {
		ConsultaConnectBy consultaConnectBy = null;

		if (ArrayUtils.isNotEmpty(idsAmbito)) {
			consultaConnectBy = DbUtil
					.generateNativeSQLWithConnectBy(
							getConnection(),
							new TableDef(
									ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO),
							idElementoCF, idPadreElementoCF, idsAmbito,
							condicionesMap);

			if (consultaConnectBy != null) {
				if (StringUtils.isNotEmpty(consultaConnectBy.getSqlClause())) {
					StringBuffer sql = new StringBuffer()
							.append(getCustomizedField(IDUDOC_FIELD,
									aliasElemento)).append(DBUtils.IN)
							.append("(")
							.append(consultaConnectBy.getSqlClause())
							.append(")");

					consultaConnectBy.setSqlClause(sql.toString());
				}
			}
		}
		return consultaConnectBy;
	}

	public DbColumnDef getCustomizedField(DbColumnDef column, String alias) {
		return new DbColumnDef(column.getBindPropertyVO(), alias,
				column.getName(), column.getDataType(), column.getMaxLen(),
				column.isNullable());
	}

	public boolean existeDetalle(String tipoSolicitud, String idSolicitud,
			String idudoc, String signatura) {

		StringBuffer qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(TIPOSOLICITUD_FIELD,
						tipoSolicitud))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(IDSOLICITUD_FIELD,
						idSolicitud))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(IDUDOC_FIELD, idudoc))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(SIGNATURAUDOC_FIELD,
						signatura));

		int result = getVOCount(qual.toString(), TABLE_NAME);

		if (result > 0)
			return true;

		return false;
	}

	/**
	 * {@inheritDoc}
    *
	 * @see solicitudes.db.IDetalleDBEntity#getExpedientesUsuarioConsulta(salas.vos.BusquedaUsuarioSalaConsultaVO)
	 */
	public List getExpedientesUsuarioConsulta(
			BusquedaUsuarioSalaConsultaVO busquedaUsuarioSala) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE);
		qual.append(IDSOLICITUD_FIELD)
				.append(Constants.EQUAL)
				.append(ConsultaDBEntity.CAMPO_ID)
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(TIPOSOLICITUD_FIELD,
						SolicitudesConstants.TIPO_SOLICITUD_CONSULTA_INT))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						ConsultaDBEntity.CAMPO_IDUSRCSALA,
						busquedaUsuarioSala.getId()))
				.append(DBUtils.AND)
				.append(DBUtils.generateInTokenField(ESTADO_FIELD, new int[] {
						ConsultasConstants.ESTADO_CONSULTA_DEVUELTA,
						ConsultasConstants.ESTADO_CONSULTA_ENTREGADA }));

		if ((busquedaUsuarioSala.getFechaIniExp() != null)
				|| (busquedaUsuarioSala.getFechaFinExp() != null)) {
			if (busquedaUsuarioSala.getFechaIniExp() != null) {
				qual.append(DBUtils.getCondition(qual.toString())).append(
						DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(), FESTADO_FIELD, ">=",
								busquedaUsuarioSala.getFechaIniExp()));
			}
			if (busquedaUsuarioSala.getFechaFinExp() != null) {
				qual.append(DBUtils.getCondition(qual.toString())).append(
						DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(), FESTADO_FIELD, "<=",
								busquedaUsuarioSala.getFechaFinExp()));
			}
		}
		String tables = TABLE_NAME + Constants.COMMA
				+ ConsultaDBEntity.TABLE_NAME;
		return getVOS(qual.toString(), tables, COLS_DEF_SALA,
				DetalleConsultaVO.class);
	}

	/**
	 * {@inheritDoc}
    *
	 * @see solicitudes.db.IDetalleDBEntity#getCountUnidadesConsultadas(java.util.Date,
	 *      java.util.Date)
	 */
	public int getCountUnidadesConsultadas(Date fechaIni, Date fechaFin) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE);
		qual.append(IDSOLICITUD_FIELD)
				.append(Constants.EQUAL)
				.append(ConsultaDBEntity.CAMPO_ID)
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(TIPOSOLICITUD_FIELD,
						SolicitudesConstants.TIPO_SOLICITUD_CONSULTA_INT))
				.append(DBUtils.AND)
				.append(DBUtils.generateInTokenField(ESTADO_FIELD, new int[] {
						ConsultasConstants.ESTADO_CONSULTA_DEVUELTA,
						ConsultasConstants.ESTADO_CONSULTA_ENTREGADA }));

		if ((fechaIni != null) || (fechaFin != null)) {
			if (fechaIni != null) {
				qual.append(DBUtils.getCondition(qual.toString()))
						.append(DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(), FESTADO_FIELD, ">=", fechaIni));
			}
			if (fechaFin != null) {
				qual.append(DBUtils.getCondition(qual.toString()))
						.append(DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(), FESTADO_FIELD, "<=", fechaFin));
			}
		}
		String tables = TABLE_NAME + Constants.COMMA
				+ ConsultaDBEntity.TABLE_NAME;
		return getVOCount(qual.toString(), tables);
	}

	/**
	 * {@inheritDoc}
    *
	 * @see solicitudes.db.IDetalleDBEntity#getUnidadesConsultadas(salas.vos.BusquedaUsuarioSalaConsultaVO)
	 */
	public List getUnidadesConsultadas(
			BusquedaUsuarioSalaConsultaVO busquedaUsuarioSala) {
		StringBuffer qual = new StringBuffer();
		/*
		 * qual.append(DBUtils.SELECT)
		 * .append(DbUtil.getColumnNamesWithAlias(COLS_DEF_UNIDADES))
		 * .append(DBUtils.FROM) .append(tables)
		 */
		qual.append(DBUtils.WHERE)
				.append(IDSOLICITUD_FIELD)
				.append(Constants.EQUAL)
				.append(ConsultaDBEntity.CAMPO_ID)
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(TIPOSOLICITUD_FIELD,
						SolicitudesConstants.TIPO_SOLICITUD_CONSULTA_INT))
				.append(DBUtils.AND)
				.append(DBUtils.generateIsNotNullCondition(getConnection(),
						ConsultaDBEntity.CAMPO_IDUSRCSALA));

		if ((busquedaUsuarioSala.getFechaIniExp() != null)
				|| (busquedaUsuarioSala.getFechaFinExp() != null)) {
			if (busquedaUsuarioSala.getFechaIniExp() != null) {
				qual.append(DBUtils.getCondition(qual.toString())).append(
						DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(), FESTADO_FIELD, ">=",
								busquedaUsuarioSala.getFechaIniExp()));
			}
			if (busquedaUsuarioSala.getFechaFinExp() != null) {
				qual.append(DBUtils.getCondition(qual.toString())).append(
						DBUtils.generateTokenFieldDateAnioMesDia(
								getConnection(), FESTADO_FIELD, "<=",
								busquedaUsuarioSala.getFechaFinExp()));
			}
		}
		qual.append(DBUtils.GROUPBY).append(
				DbUtil.getColumnNames(new DbColumnDef[] { TITULO_FIELD,
						SIGNATURAUDOC_FIELD }));

		if (busquedaUsuarioSala.getNumVeces() != null) {
			qual.append(DBUtils.HAVING)
					.append(DbUtil.getCountColumn(IDUDOC_FIELD)
							+ Constants.MAYOR)
					.append(busquedaUsuarioSala.getNumVeces().intValue());
		}
		String tables = TABLE_NAME + Constants.COMMA
				+ ConsultaDBEntity.TABLE_NAME;
		return getVOS(qual.toString(), tables, COLS_DEF_UNIDADES,
				DetalleConsultaVO.class);
	}

	/*
	 * (non-Javadoc)
    *
	 * @see solicitudes.db.IDetalleDBEntity#getCountDetallesByEstado(int[])
	 */
	public int getCountDetallesByEstado(String idUdoc, int[] estados) {

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDUDOC_FIELD, idUdoc))
				.append(DBUtils.AND)
				.append(DBUtils.generateInTokenField(ESTADO_FIELD, estados));

		return getVOCount(qual.toString(), TABLE_NAME);
	}

	public void cambiarEstadosASolicitadoDetallesAutorizados(String idSolicitud, int tipoSolicitud, int[] estadosOrigen, int estadoDestino){

		String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDSOLICITUD_FIELD,idSolicitud))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(TIPOSOLICITUD_FIELD, tipoSolicitud))
				.append(DBUtils.AND)
				.append(DBUtils.generateInTokenField(ESTADO_FIELD, estadosOrigen))
				.toString();

		DetalleConsultaVO detalle = new DetalleConsultaVO();
		// Establecemos el estado
		detalle.setEstado(estadoDestino);
		detalle.setFestado(DateUtils.getFechaActual());

		// Establecemos la fecha del estado
		detalle.setFestado(DBUtils.getFechaActual());

		DbColumnDef[] columnas = { ESTADO_FIELD, FESTADO_FIELD };

		updateVO(qual, TABLE_NAME, columnas, detalle);
	}



}
