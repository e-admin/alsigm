package solicitudes.prestamos.db;

import gcontrol.db.ArchivoDbEntityImpl;
import gcontrol.db.UsuarioDBEntityImpl;
import gcontrol.vos.CAOrganoVO;
import gcontrol.vos.UsuarioVO;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbSelectFns;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import solicitudes.SolicitudesConstants;
import solicitudes.db.DetalleDBEntityImpl;
import solicitudes.prestamos.PrestamosConstants;
import solicitudes.prestamos.vos.BusquedaVO;
import solicitudes.prestamos.vos.PrestamoVO;
import solicitudes.utils.PropertyHelper;
import util.CollectionUtils;

import common.CodigoTransferenciaUtils;
import common.ConfigConstants;
import common.db.ConstraintType;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.exceptions.TooManyResultsException;
import common.lang.MutableInt;
import common.util.ArrayUtils;
import common.util.CustomDateFormat;
import common.util.DateUtils;

/**
 * Clase que encapsula los metodos de persistencia de prestamos.
 */
public class PrestamoDBEntity extends DBEntity implements IPrestamoDBEntity {
	/** Nombre de la tabla */
	public static final String TABLE_NAME = "ASGPPRESTAMO";

	/** NOmbre de las columnas de la tabla */
	public static final String ID_COLUMN_NAME = "id";
	public static final String ANO_COLUMN_NAME = "ano";
	public static final String ORDEN_COLUMN_NAME = "orden";
	public static final String NORGSOLICITANTE_COLUMN_NAME = "norgsolicitante";
	public static final String NUSRSOLICITANTE_COLUMN_NAME = "nusrsolicitante";
	public static final String IDUSRSOLICITANTE_COLUMN_NAME = "idusrsolicitante";
	public static final String FINICIALRESERVA_COLUMN_NAME = "finicialreserva";
	public static final String FENTREGA_COLUMN_NAME = "fentrega";
	public static final String FMAXFINPRESTAMO_COLUMN_NAME = "fmaxfinprestamo";
	public static final String ESTADO_COLUMN_NAME = "estado";
	public static final String FESTADO_COLUMN_NAME = "festado";
	public static final String IDARCHIVO_COLUMN_NAME = "idarchivo";
	public static final String IDUSRGESTOR_COLUMN_NAME = "idusrgestor";
	public static final String NUMRECLAMACIONES_COLUMN_NAME = "numreclamaciones";
	public static final String FRECLAMACION1_COLUMN_NAME = "freclamacion1";
	public static final String FRECLAMACION2_COLUMN_NAME = "freclamacion2";
	public static final String IDORGSOLICITANTE_COLUMN_NAME = "idorgsolicitante";
	public static final String DATOSAUTORIZADO_COLUMN_NAME = "datosautorizado";
	public static final String DATOSSOLICITANTE_COLUMN_NAME = "datossolicitante";
	public static final String TIPOENTREGA_COLUMN_NAME = "tipoentrega";
	public static final String OBSERVACIONES_COLUMN_NAME = "observaciones";
	public static final String ID_MOTIVO_COLUMN_NAME = "idmotivo";

	/** Defincion de los campos de la tabla */
	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_ANO = new DbColumnDef(null,
			TABLE_NAME, ANO_COLUMN_NAME, DbDataType.SHORT_TEXT, 4, false);
	public static final DbColumnDef CAMPO_ORDEN = new DbColumnDef(null,
			TABLE_NAME, ORDEN_COLUMN_NAME, DbDataType.LONG_INTEGER, 10, false);
	public static final DbColumnDef CAMPO_NORGSOLICITANTE = new DbColumnDef(
			null, TABLE_NAME, NORGSOLICITANTE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 254, false);
	public static final DbColumnDef CAMPO_NUSRSOLICITANTE = new DbColumnDef(
			null, TABLE_NAME, NUSRSOLICITANTE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 254, false);
	public static final DbColumnDef CAMPO_IDUSRSOLICITANTE = new DbColumnDef(
			null, TABLE_NAME, IDUSRSOLICITANTE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, true);
	public static final DbColumnDef CAMPO_FINICIALRESERVA = new DbColumnDef(
			null, TABLE_NAME, FINICIALRESERVA_COLUMN_NAME,
			DbDataType.DATE_TIME, true);
	public static final DbColumnDef CAMPO_FENTREGA = new DbColumnDef(null,
			TABLE_NAME, FENTREGA_COLUMN_NAME, DbDataType.DATE_TIME, true);
	public static final DbColumnDef CAMPO_FMAXFINPRESTAMO = new DbColumnDef(
			null, TABLE_NAME, FMAXFINPRESTAMO_COLUMN_NAME,
			DbDataType.DATE_TIME, true);
	public static final DbColumnDef CAMPO_ESTADO = new DbColumnDef(null,
			TABLE_NAME, ESTADO_COLUMN_NAME, DbDataType.LONG_INTEGER, 2, false);
	public static final DbColumnDef CAMPO_FESTADO = new DbColumnDef(null,
			TABLE_NAME, FESTADO_COLUMN_NAME, DbDataType.DATE_TIME, true);
	public static final DbColumnDef CAMPO_IDARCHIVO = new DbColumnDef(null,
			TABLE_NAME, IDARCHIVO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_IDUSRGESTOR = new DbColumnDef(null,
			TABLE_NAME, IDUSRGESTOR_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);
	public static final DbColumnDef CAMPO_NUMRECLAMACIONES = new DbColumnDef(
			null, TABLE_NAME, NUMRECLAMACIONES_COLUMN_NAME,
			DbDataType.LONG_INTEGER, 2, false);
	public static final DbColumnDef CAMPO_FRECLAMACION1 = new DbColumnDef(null,
			TABLE_NAME, FRECLAMACION1_COLUMN_NAME, DbDataType.DATE_TIME, true);
	public static final DbColumnDef CAMPO_FRECLAMACION2 = new DbColumnDef(null,
			TABLE_NAME, FRECLAMACION2_COLUMN_NAME, DbDataType.DATE_TIME, true);
	public static final DbColumnDef CAMPO_IDORGSOLICITANTE = new DbColumnDef(
			null, TABLE_NAME, IDORGSOLICITANTE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, true);
	public static final DbColumnDef CAMPO_DATOSAUTORIZADO = new DbColumnDef(
			null, TABLE_NAME, DATOSAUTORIZADO_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 254, true);
	public static final DbColumnDef CAMPO_DATOSSOLICITANTE = new DbColumnDef(
			null, TABLE_NAME, DATOSSOLICITANTE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 512, true);
	public static final DbColumnDef CAMPO_TIPOENTREGA = new DbColumnDef(null,
			TABLE_NAME, TIPOENTREGA_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			true);
	public static final DbColumnDef CAMPO_OBSERVACIONES = new DbColumnDef(null,
			TABLE_NAME, OBSERVACIONES_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			true);
	public static final DbColumnDef CAMPO_ID_MOTIVO = new DbColumnDef(null,
			TABLE_NAME, ID_MOTIVO_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			false);

	public static final DbColumnDef[] COLUMN_DEFINITIONS = { CAMPO_ID,
			CAMPO_ANO, CAMPO_ORDEN, CAMPO_NORGSOLICITANTE,
			CAMPO_NUSRSOLICITANTE, CAMPO_IDUSRSOLICITANTE,
			CAMPO_FINICIALRESERVA, CAMPO_FENTREGA, CAMPO_FMAXFINPRESTAMO,
			CAMPO_ESTADO, CAMPO_FESTADO, CAMPO_IDARCHIVO, CAMPO_IDUSRGESTOR,
			CAMPO_NUMRECLAMACIONES, CAMPO_FRECLAMACION1, CAMPO_FRECLAMACION2,
			CAMPO_IDORGSOLICITANTE, CAMPO_DATOSAUTORIZADO,
			CAMPO_DATOSSOLICITANTE, CAMPO_TIPOENTREGA, CAMPO_OBSERVACIONES,
			CAMPO_ID_MOTIVO };

	public static final String COLUM_NAMES_LIST = DBUtils
			.getQualifiedColumnNames(TABLE_NAME, COLUMN_DEFINITIONS);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public PrestamoDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public PrestamoDBEntity(DbConnection conn) {
		super(conn);
	}

	/**
	 * Inserta un prestamo en la base de datos creado por el usuario del
	 * servicio.
	 * 
	 * @param userVO
	 *            Usuario que está creando el préstamo.
	 * @param prestamoVO
	 *            Prestamo a insertar en la base de datos.
	 */
	public void insertPrestamo(final PrestamoVO prestamo) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				prestamo.setId(getGuid(prestamo.getId()));

				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLUMN_DEFINITIONS, prestamo);
				DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
						inputRecord);
			}
		};

		command.execute();
	}

	/**
	 * Actualiza un prestamo.
	 * 
	 * @param prestamoVO
	 *            Prestamo que deseamos actualizar
	 */
	public void updatePrestamo(final PrestamoVO prestamo) {
		final String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, prestamo.getId()))
				.toString();

		updateVO(qual, TABLE_NAME, COLUMN_DEFINITIONS, prestamo);
	}

	/**
	 * Obtiene un préstamo a partir de su identificador en la base de datos.
	 * 
	 * @param codprestamo
	 *            Identificador del préstamo en la base de datos
	 * @return Objeto {@link PrestamoVO} con los detalles del préstamo.
	 */
	public PrestamoVO getPrestamo(String codigoPrestamo) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateTokenFieldQualified(CAMPO_ID, codigoPrestamo,
						TABLE_NAME, ConstraintType.EQUAL));

		return (PrestamoVO) getVO(qual.toString(), TABLE_NAME,
				COLUMN_DEFINITIONS, PrestamoVO.class);
	}

	/**
	 * Obtiene un listado de prestamos a partir de sus identificadores.
	 * 
	 * @param codigos
	 *            Listado de los identificadores de los prestamos que deseamo
	 *            recuperar
	 * @return Listado de los prestamos
	 */
	public Collection getPrestamos(String[] codigos) {
		StringBuffer qual = new StringBuffer().append("WHERE").append(
				DBUtils.generateORTokens(CAMPO_ID, codigos));

		qual.append(" ORDER BY  ").append(CAMPO_FESTADO.getName())
				.append(" DESC  ").toString();

		return getVOS(qual.toString(), TABLE_NAME, COLUMN_DEFINITIONS,
				PrestamoVO.class);
	}

	public String getPrestamosXUsuarioSolicitanteSql(String idUsuario,
			int[] estados) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_IDUSRGESTOR, idUsuario));
		if (estados != null)
			qual.append(" AND ").append(
					DBUtils.generateORTokens(CAMPO_ESTADO, estados));

		return qual.toString();
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see solicitudes.prestamos.db.IPrestamoDBEntity#
	 * getCountPrestamosXUsuarioSolicitante(java.lang.String, int[])
	 */
	public int getCountPrestamosXUsuarioSolicitante(String idUsuario,
			int[] estados) {
		String qual = getPrestamosXUsuarioSolicitanteSql(idUsuario, estados);
		return getVOCount(qual, TABLE_NAME);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * solicitudes.prestamos.db.IPrestamoDBEntity#getPrestamosXUsuarioSolicitante
	 * (java.lang.String, int[])
	 */
	public List getPrestamosXUsuarioSolicitante(String idUsuario, int[] estados) {
		StringBuffer qual = new StringBuffer(
				getPrestamosXUsuarioSolicitanteSql(idUsuario, estados));
		qual.append(" ORDER BY  ").append(CAMPO_FESTADO.getName())
				.append(" DESC ");

		return getVOS(qual.toString(), TABLE_NAME, COLUMN_DEFINITIONS,
				PrestamoVO.class);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see solicitudes.prestamos.db.IPrestamoDBEntity#
	 * getCountPrestamosXUsuarioSolicitanteBuscar(java.lang.String,
	 * java.lang.String[])
	 */
	public int getCountPrestamosXUsuarioSolicitanteBuscar(String idUsuario,
			String[] estados) {

		StringBuffer qual = new StringBuffer().append(" WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_IDUSRGESTOR, idUsuario));

		if (estados != null)
			qual.append(" AND ").append(
					DBUtils.generateORTokens(CAMPO_ESTADO, estados));

		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see solicitudes.prestamos.db.IPrestamoDBEntity#
	 * getCountPrestamosXUsuarioSolicitanteBuscar(java.lang.String,
	 * java.lang.String[], java.lang.String[])
	 */
	public int getCountPrestamosXUsuarioSolicitanteBuscar(String idUsuario,
			String[] estados, String[] idsArchivo) {

		StringBuffer qual = new StringBuffer().append(" WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_IDUSRGESTOR, idUsuario));

		if (estados != null)
			qual.append(" AND ").append(
					DBUtils.generateORTokens(CAMPO_ESTADO, estados));

		if (ArrayUtils.isNotEmpty(idsArchivo)) {
			qual.append(" AND ").append(
					DBUtils.generateORTokens(CAMPO_IDARCHIVO, idsArchivo));
		}

		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/**
	 * Obtiene los prestamos para un usuario determinado.Utilizado para las
	 * busquedas
	 * 
	 * @param idUsuario
	 *            identificador del usuario del que deseamos obtener los
	 *            prestamos
	 * @param busqueda
	 *            Objeto con las restricciones de una busqueda
	 * @return Un listado de los prestamos del usuario
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getPrestamosXUsuarioSolicitanteBuscar(String idUsuario,
			BusquedaVO busqueda) throws TooManyResultsException {
		List prestamos = null;

		HashMap pairsTableNamesColsDefs = new HashMap();
		ConsultaObject consulta = null;
		StringBuffer qual = new StringBuffer().append(" WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_IDUSRGESTOR, idUsuario));

		// Comprobamos si tiene restricciones para añardirlas
		if (busqueda != null) {
			consulta = getWhereClause(busqueda);

			qual.append(" AND ").append(consulta.getWhere());

			pairsTableNamesColsDefs.put(TABLE_NAME, COLUMN_DEFINITIONS);
			Iterator it = consulta.getTableList().iterator();
			while (it.hasNext()) {
				String tableName = (String) it.next();
				pairsTableNamesColsDefs.put(tableName, new DbColumnDef[0]);
			}
		} else {
			pairsTableNamesColsDefs.put(TABLE_NAME, COLUMN_DEFINITIONS);
		}

		if (busqueda != null && busqueda.getPageInfo() != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("codigo", new DbColumnDef[] { CAMPO_ANO,
					CAMPO_ORDEN });
			criteriosOrdenacion.put("organo", CAMPO_NORGSOLICITANTE);
			criteriosOrdenacion.put("usuario", CAMPO_NUSRSOLICITANTE);
			criteriosOrdenacion.put("estado", CAMPO_ESTADO);
			criteriosOrdenacion.put("fechaEstado", CAMPO_FESTADO);
			criteriosOrdenacion.put("fechaMax", CAMPO_FMAXFINPRESTAMO);

			prestamos = getVOS(qual.toString(), busqueda.getPageInfo()
					.getOrderBy(criteriosOrdenacion), pairsTableNamesColsDefs,
					PrestamoVO.class, busqueda.getPageInfo());
		} else {
			qual.append(" ORDER BY  ").append(CAMPO_FESTADO.getName())
					.append(" DESC  ");

			prestamos = getVOS(qual.toString(), pairsTableNamesColsDefs,
					PrestamoVO.class);
		}

		return prestamos;
	}

	/**
	 * Indica si un usuario tiene préstamos en los estados determinados.
	 * 
	 * @param idUsuario
	 *            Identificador de usuario.
	 * @param estados
	 *            Estados de los préstamos.
	 * @return true si el usuario tiene préstamos en curso.
	 */
	public boolean hasPrestamos(String idUsuario, String[] estados) {
		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotBlank(idUsuario)) {
			qual.append(DBUtils.getCondition(qual.toString()))
					.append("(")
					.append(DBUtils.generateEQTokenField(CAMPO_IDUSRGESTOR,
							idUsuario))
					.append(" OR ")
					.append(DBUtils.generateEQTokenField(
							CAMPO_IDUSRSOLICITANTE, idUsuario)).append(")");
		}

		if (ArrayUtils.isNotEmpty(estados)) {
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateInTokenField(CAMPO_ESTADO, estados));
		}

		return getVOCount(qual.toString(), TABLE_NAME) > 0;
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * solicitudes.prestamos.db.IPrestamoDBEntity#getCountPrestamosXEstados(
	 * java.lang.String[])
	 */
	public int getCountPrestamosXEstados(String[] estados, String[] idsArchivos) {
		StringBuffer qual = new StringBuffer().append(" WHERE 1=1");

		if (!ArrayUtils.isEmpty(estados)) {
			qual.append(" AND ")
					.append(DBUtils.generateORTokens(CAMPO_ESTADO, estados))
					.toString();
		}
		if (ArrayUtils.isNotEmpty(idsArchivos)) {
			qual.append(" AND ").append(
					DBUtils.generateORTokens(CAMPO_IDARCHIVO, idsArchivos));
		}

		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/**
	 * Muestra todos los préstamos existentes para los estados indicados.
	 * 
	 * @param estados
	 *            Listado de los estados de los que deseamos mostrar sus
	 *            préstamos.
	 * @param busqueda
	 *            Objeto con las restricciones de una busqueda
	 * @return Listado de los préstamos cuyo estado se encuentra en alguno de
	 *         los indicados.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getPrestamosXEstados(String[] estados, BusquedaVO busqueda,
			String[] idsArchivo) throws TooManyResultsException {
		List prestamos = null;
		HashMap pairsTableNamesColsDefs = new HashMap();
		ConsultaObject consulta = null;
		StringBuffer qual = new StringBuffer();

		if (ArrayUtils.isNotEmpty(idsArchivo)) {

			if (estados != null && estados.length > 0)
				qual.append(" WHERE ").append(
						DBUtils.generateORTokens(CAMPO_ESTADO, estados));

			if (ArrayUtils.isNotEmpty(idsArchivo)) {

				if (qual.length() > 0)
					qual.append(" AND ");
				else
					qual.append(" WHERE ");

				qual.append(DBUtils.generateORTokens(CAMPO_IDARCHIVO,
						idsArchivo));
			}

			// Comprobamos si tiene restricciones para añardirlas
			if (busqueda != null) {
				consulta = getWhereClause(busqueda);

				Iterator it = consulta.getTableList().iterator();
				if (!CollectionUtils.isEmpty(consulta.getTableList())) {
					if (qual.length() > 0)
						qual.append(" AND ");
					else
						qual.append(" WHERE ");

				}
				qual.append(consulta.getWhere());

				pairsTableNamesColsDefs.put(TABLE_NAME, COLUMN_DEFINITIONS);

				while (it.hasNext()) {
					String tableName = (String) it.next();
					pairsTableNamesColsDefs.put(tableName, new DbColumnDef[0]);
				}
			} else {
				pairsTableNamesColsDefs.put(TABLE_NAME, COLUMN_DEFINITIONS);
			}

			if (busqueda != null && busqueda.getPageInfo() != null) {
				Map criteriosOrdenacion = new HashMap();
				criteriosOrdenacion.put("codigo", new DbColumnDef[] {
						CAMPO_ANO, CAMPO_ORDEN });
				criteriosOrdenacion.put("organo", CAMPO_NORGSOLICITANTE);
				criteriosOrdenacion.put("usuario", CAMPO_NUSRSOLICITANTE);
				criteriosOrdenacion.put("estado", CAMPO_ESTADO);
				criteriosOrdenacion.put("fechaEstado", CAMPO_FESTADO);
				criteriosOrdenacion.put("fechaMax", CAMPO_FMAXFINPRESTAMO);

				prestamos = getVOS(qual.toString(), busqueda.getPageInfo()
						.getOrderBy(criteriosOrdenacion),
						pairsTableNamesColsDefs, PrestamoVO.class,
						busqueda.getPageInfo());
			} else {
				qual.append(" ORDER BY  ")
						.append(TABLE_NAME + "." + CAMPO_FESTADO.getName())
						.append(" DESC  ");

				prestamos = getVOS(qual.toString(), pairsTableNamesColsDefs,
						PrestamoVO.class);
			}
		}
		return prestamos;
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * solicitudes.prestamos.db.IPrestamoDBEntity#getCountPrestamosXUsuarioGestor
	 * (java.lang.String, java.lang.String[], java.lang.String[])
	 */
	public int getCountPrestamosXUsuarioGestor(String idUsuarioSolicitante,
			String[] descendientes, String[] estados) {
		StringBuffer qual = new StringBuffer("WHERE 1=1 ");

		if (!ArrayUtils.isEmpty(descendientes))
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_IDORGSOLICITANTE,
							descendientes));

		if (!ArrayUtils.isEmpty(estados))
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_ESTADO, estados));

		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * solicitudes.prestamos.db.IPrestamoDBEntity#getCountPrestamosXUsuarioGestor
	 * (java.lang.String, java.lang.String[], java.lang.String[])
	 */
	public int getCountPrestamosXUsuarioGestor(String idUsuarioSolicitante,
			String[] descendientes, String[] estados, String[] idsArchivo) {
		StringBuffer qual = new StringBuffer("WHERE 1=1 ");

		if (!ArrayUtils.isEmpty(descendientes))
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_IDORGSOLICITANTE,
							descendientes));

		if (!ArrayUtils.isEmpty(estados))
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_ESTADO, estados));

		if (!ArrayUtils.isEmpty(idsArchivo))
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_IDARCHIVO, idsArchivo));

		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/**
	 * Obtiene los prestamos de un usuario gestor dado por su identificador,
	 * además de los de su organismo y de organismos dependientes.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario gestor del que deseamos obtener sus
	 *            prestamos.
	 * @param descendientes
	 *            Lista de identificadores de los organismo
	 *            dependientes(incluido el suyo propio)
	 * @param busqueda
	 *            Objeto con las restricciones de una busqueda
	 * @return Listado de prestamos para el usuario
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getPrestamosXUsuarioGestor(String idUsuarioSolicitante,
			String[] descendientes, BusquedaVO busqueda)
			throws TooManyResultsException {
		List prestamos = null;

		ConsultaObject consulta = null;
		HashMap pairsTableNamesColsDefs = new HashMap();
		StringBuffer qual = new StringBuffer();

		if (!ArrayUtils.isEmpty(descendientes))
			qual.append("WHERE ").append(
					DBUtils.generateInTokenField(CAMPO_IDORGSOLICITANTE,
							descendientes));

		// Comprobamos si tiene restricciones para añardirlas
		if (busqueda != null) {
			consulta = getWhereClause(busqueda);

			if (qual.length() == 0)
				qual.append("WHERE ");
			else
				qual.append(" AND ");

			qual.append(consulta.getWhere());
			pairsTableNamesColsDefs.put(TABLE_NAME, COLUMN_DEFINITIONS);
			Iterator it = consulta.getTableList().iterator();
			while (it.hasNext()) {
				String tableName = (String) it.next();
				pairsTableNamesColsDefs.put(tableName, new DbColumnDef[0]);
			}
		} else {
			pairsTableNamesColsDefs.put(TABLE_NAME, COLUMN_DEFINITIONS);
		}

		if (busqueda != null && busqueda.getPageInfo() != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("codigo", new DbColumnDef[] { CAMPO_ANO,
					CAMPO_ORDEN });
			criteriosOrdenacion.put("organo", CAMPO_NORGSOLICITANTE);
			criteriosOrdenacion.put("usuario", CAMPO_NUSRSOLICITANTE);
			criteriosOrdenacion.put("estado", CAMPO_ESTADO);
			criteriosOrdenacion.put("fechaEstado", CAMPO_FESTADO);
			criteriosOrdenacion.put("fechaMax", CAMPO_FMAXFINPRESTAMO);

			prestamos = getVOS(qual.toString(), busqueda.getPageInfo()
					.getOrderBy(criteriosOrdenacion), pairsTableNamesColsDefs,
					PrestamoVO.class, busqueda.getPageInfo());
		} else {
			qual.append(" ORDER BY  ").append(CAMPO_FESTADO.getName())
					.append(" DESC  ");

			prestamos = getVOS(qual.toString(), pairsTableNamesColsDefs,
					PrestamoVO.class);
		}
		return prestamos;
	}

	/**
	 * Obtiene un listado de los usuarios solicitantes de prestamo cuyo estado
	 * es NO ABIERTO y, en caso que este presente,cuyo organo sea algún órgano
	 * de la lista(los que dependen del órgano del usuario conectado)
	 * 
	 * @param organos
	 *            Lista de órganos en los cuales debe estar presente el usuario
	 *            solicitante del prestamo
	 * @return Listado de los usuarios
	 */
	public Collection getUsuariosBusquedaPrestamos(List organos) {
		StringBuffer qual = new StringBuffer();

		if (organos != null && organos.size() > 0) {
			String[] idsOrgano = new String[organos.size()];
			int i = 0;

			Iterator it = organos.iterator();
			while (it.hasNext()) {
				CAOrganoVO organo = (CAOrganoVO) it.next();

				idsOrgano[i++] = organo.getIdOrg();
			}

			qual.append(" WHERE ").append(
					DBUtils.generateInTokenField(CAMPO_IDORGSOLICITANTE,
							idsOrgano));
		}

		DbColumnDef[] columnas = { CAMPO_IDUSRSOLICITANTE };

		return getDistinctVOS(qual.toString(), TABLE_NAME, columnas,
				PrestamoVO.class);
	}

	/**
	 * Realiza el envío de los detalles de un prestamo
	 * 
	 * @param idPrestamo
	 *            Identificador del prestamo.
	 * @param tipoUsuario
	 *            Tipo del usuario que va realiza el envio
	 */
	public void enviarPrestamo(PrestamoVO prestamoVO, String tipoUsuario) {
		String qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateTokenFieldQualified(CAMPO_ID,
						prestamoVO.getId(), TABLE_NAME, ConstraintType.EQUAL))
				.toString();

		prestamoVO.setEstado(PrestamosConstants.ESTADO_PRESTAMO_SOLICITADO);
		prestamoVO.setFestado(DateUtils.getFechaActual());

		// Comprobamos si es una reserva
		if (!prestamoVO.tieneReserva()) {
			String dias_reserva = PropertyHelper
					.getProperty(PropertyHelper.PLAZO_PRESTAMO);

			Date hoy = Calendar.getInstance().getTime();
			Calendar fechaFinal = new GregorianCalendar();
			fechaFinal.setTime(hoy);
			fechaFinal.add(Calendar.HOUR, (Integer.parseInt(dias_reserva))
					* PrestamosConstants.HORAS_DIA);

			prestamoVO.setFmaxfinprestamo(fechaFinal.getTime());
		} else {
			prestamoVO.setFmaxfinprestamo(prestamoVO.getFfinalreserva());
		}

		DbColumnDef[] columnas = { CAMPO_ESTADO, CAMPO_FESTADO,
				CAMPO_FMAXFINPRESTAMO };

		// TODO Revisar: Comprobamos si el tipo de usuario es externo
		// if ( tipoUsuario.equals(TipoUsuario.INTERNO) ) {
		if (prestamoVO.getIdusrsolicitante() != null) {
			columnas = new DbColumnDef[4];
			columnas[0] = CAMPO_ESTADO;
			columnas[1] = CAMPO_FESTADO;
			columnas[2] = CAMPO_FMAXFINPRESTAMO;
			columnas[3] = CAMPO_IDUSRGESTOR;

			prestamoVO.setIdusrgestor(prestamoVO.getIdusrsolicitante());
		}

		updateVO(qual, TABLE_NAME, columnas, prestamoVO);
	}

	/**
	 * Realiza el borrado de un prestamo dado por su identificador.
	 * 
	 * @param idPrestamo
	 *            Identificador del prestamo.
	 */
	public void deletePrestamo(String idPrestamo) {
		final String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, idPrestamo))
				.toString();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual);

			}
		};

		command.execute();
	}

	/**
	 * Obtiene el número de detalles que están asociados a un determinado
	 * préstamo.
	 * 
	 * @param idPrestamo
	 *            Identificador del prestamo del que deseamos conocer su numero
	 *            de detalles asociados.
	 * @return Numero de detalles de prestamo asociados al prestamo.
	 */
	public int numeroDetallesPrestamo(final String idPrestamo) {
		final MutableInt nDetalles = new MutableInt(0);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				String qual = new StringBuffer()
						.append(" WHERE ")
						.append(DBUtils.generateEQTokenField(CAMPO_ID,
								idPrestamo)).toString();

				nDetalles.setValue(DbSelectFns.selectCount(conn, TABLE_NAME,
						qual.toString()));
			}
		};

		command.execute();

		return nDetalles.getValue();
	}

	/**
	 * Obtiene un listado de los prestamos que tiene abiertos por el nombre de
	 * usuario solicitante.
	 * 
	 * @param nombreUsuarioSolicitante
	 *            Nombre del usuario solicitante.
	 * @return Listado de los prestamos abierto por el usuario
	 */
	public Collection getPrestamosXUsuarioSolicitanteAbiertos(
			String nombreUsuario) {
		String qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_NUSRSOLICITANTE,
						nombreUsuario))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ESTADO, Integer
						.toString(PrestamosConstants.ESTADO_PRESTAMO_ABIERTO)))
				.toString();

		return getVOS(qual, TABLE_NAME, COLUMN_DEFINITIONS, PrestamoVO.class);
	}

	/**
	 * Obtiene un listado de los prestamos que tiene abiertos por el id de
	 * usuario solicitante.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario solicitante.
	 * @return Listado de los prestamos abierto por el usuario
	 */
	public Collection getPrestamosXIdUsuarioSolicitanteAbiertos(String idUsuario) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDUSRSOLICITANTE,
						idUsuario))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ESTADO,
						PrestamosConstants.ESTADO_PRESTAMO_ABIERTO)).toString();

		return getVOS(qual, TABLE_NAME, COLUMN_DEFINITIONS, PrestamoVO.class);
	}

	/**
	 * Obtiene un listado de los prestamos que tiene abiertos por el id de
	 * usuario solicitante.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario solicitante.
	 * @return Listado de los prestamos abierto por el usuario
	 */
	public Collection getPrestamosEnElaboracionXIdUsuario(String idUsuario) {
		String qual = getWhereClausePrestamosEnElaboracionByIdUsuario(idUsuario);
		return getVOS(qual, TABLE_NAME, COLUMN_DEFINITIONS, PrestamoVO.class);
	}

	/**
	 * Obtiene un listado de los prestamos que tiene abiertos por el id de
	 * usuario solicitante.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario solicitante.
	 * @return Listado de los prestamos abierto por el usuario
	 */
	public int getCountPrestamosEnElaboracionXIdUsuario(String idUsuario) {
		String qual = getWhereClausePrestamosEnElaboracionByIdUsuario(idUsuario);
		return getVOCount(qual, TABLE_NAME);
	}

	private String getWhereClausePrestamosEnElaboracionByIdUsuario(
			String idUsuario) {
		String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append("(")
				.append(DBUtils.generateEQTokenField(CAMPO_IDUSRSOLICITANTE,
						idUsuario))
				.append(DBUtils.OR)
				.append(DBUtils.generateEQTokenField(CAMPO_IDUSRGESTOR,
						idUsuario))
				.append(")")
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_ESTADO,
						PrestamosConstants.ESTADO_PRESTAMO_ABIERTO)).toString();

		return qual;
	}

	/**
	 * Obtiene un listado de los prestamos que tiene abiertos como gestor un
	 * determinado usuario.
	 * 
	 * @param idUsuario
	 *            Identificador en la base de datos del usuario.
	 * @return Listado de los prestamos abierto por el usuario
	 */
	public Collection getPrestamosXUsuarioGestorAbiertos(String idUsuario) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDUSRGESTOR,
						idUsuario))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ESTADO,
						PrestamosConstants.ESTADO_PRESTAMO_ABIERTO)).toString();

		return getVOS(qual, TABLE_NAME, COLUMN_DEFINITIONS, PrestamoVO.class);
	}

	/**
	 * Obtiene un listado de los prestamos que pertenecen a un determinado
	 * organo
	 * 
	 * @param idOrgano
	 *            Identificador del organo del que deseamos obtener los
	 *            prestamos.
	 * @return Listado de los prestamos
	 */
	public Collection getPrestamosXOrganoSolicitante(String idOrgano) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_NORGSOLICITANTE,
						idOrgano)).toString();

		return getVOS(qual, TABLE_NAME, COLUMN_DEFINITIONS, PrestamoVO.class);
	}

	/**
	 * Obtiene un listado de los prestamos externos (que no tienen un
	 * identificador de organo solicitante establecido)
	 * 
	 * @return Listado de los prestamos
	 */
	public Collection getPrestamosExternos() {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDORGSOLICITANTE,
						null)).toString();

		return getVOS(qual, TABLE_NAME, COLUMN_DEFINITIONS, PrestamoVO.class);
	}

	/**
	 * Obtiene la clásula WHERE para la búsqueda con las restricciones del
	 * formulario de búsqueda.
	 * 
	 * @param busquedaVO
	 *            Información del formulario de búsqueda.
	 * @return Objeto con la consulta generada compuesta por la clausula WHERE y
	 *         las tabla necesarias para su correcto funcionamiento(siempre se
	 *         incluye por defecto la tabla PRESTAMO)
	 */
	protected ConsultaObject getWhereClause(BusquedaVO busquedaVO) {
		ConsultaObject consulta = new ConsultaObject();
		StringBuffer clausulaWhere = new StringBuffer();

		compruebaEstados(busquedaVO, clausulaWhere);
		// TODO ZMIGRACION BD - LPAD y CONCATENACION - PROBAR - EN TEORÍA
		// ENTRARÍA CON UNOS PERMISOS CONCRETOS, PERO LOGRÉ QUE LLEGASE AQUÍ CON
		// BUSQUEDAVO != NULL

		// Código
		if (StringUtils.isNotBlank(busquedaVO.getCodigo()))
			// clausulaWhere.append(" AND ")
			// .append(CAMPO_ANO.getQualifiedName())
			// .append("||'/'||LPAD(")
			// .append(CAMPO_ORDEN.getQualifiedName())
			// .append(",")
			// .append(PrestamosConstants.FORMAT_ID_PRESTAMO.length())
			// .append(",'0') LIKE '%")
			// .append(busquedaVO.getCodigo())
			// .append("%'");
			clausulaWhere
					.append(" AND ")
					.append(CAMPO_ANO.getQualifiedName())
					.append(DBUtils.getNativeConcatSyntax(getConnection()))
					.append("'/'")
					.append(DBUtils.getNativeConcatSyntax(getConnection()))
					.append(DBUtils.getNativeLPadSyntax(getConnection(),
							CAMPO_ORDEN.getQualifiedName(),
							SolicitudesConstants.FORMAT_ID_SOLICITUD.length(),
							"0")).append(" LIKE '%")
					.append(busquedaVO.getCodigo()).append("%'");

		// Id usuario gestor
		if (StringUtils.isNotBlank(busquedaVO.getGestor()))
			clausulaWhere
					.append(DBUtils.getCondition(clausulaWhere.toString()))
					.append(DBUtils.generateEQTokenField(CAMPO_IDUSRGESTOR,
							busquedaVO.getGestor()));

		compruebaSolicitante(busquedaVO, clausulaWhere);

		compruebaNotas(busquedaVO, clausulaWhere);

		compruebaFechaEntrega(busquedaVO, clausulaWhere);

		// compruebaFechaEstado(busquedaVO, clausulaWhere);

		consulta.setWhere(clausulaWhere.toString());
		consulta.setOrderBy(null);

		return consulta;
	}

	// /**
	// * @param busquedaVO
	// * @param clausulaWhere
	// */
	// private void compruebaFechaEstado(BusquedaVO busquedaVO, StringBuffer
	// clausulaWhere) {
	// //Fecha Estado
	// if ((busquedaVO.getFechaInicioEstado() != null) ||
	// (busquedaVO.getFechaFinEstado() != null)) {
	// SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
	//
	// if (busquedaVO.getFechaInicioEstado() != null) {
	// clausulaWhere.append(TABLE_NAME).append(".")
	// .append(FESTADO_COLUMN_NAME)
	// .append("<=TO_DATE('");
	// clausulaWhere.append(df.format(busquedaVO.getFechaInicioEstado()));
	// clausulaWhere.append("','yyyy/mm/dd')");
	// }
	//
	// if (busquedaVO.getFechaFinEstado() != null) {
	// if (busquedaVO.getFechaInicioEstado() != null)
	// clausulaWhere.append("AND ");
	//
	// clausulaWhere.append(TABLE_NAME).append(".")
	// .append(FESTADO_COLUMN_NAME)
	// .append("<=TO_DATE('");
	// clausulaWhere.append(df.format(busquedaVO.getFechaFinEstado()));
	// clausulaWhere.append("','yyyy/mm/dd')");
	// }
	// }
	// }

	/**
	 * @param busquedaVO
	 * @param clausulaWhere
	 */
	private void compruebaFechaEntrega(BusquedaVO vo, StringBuffer qual) {
		// Fecha Entrega
		if ((vo.getFechaInicioEntrega() != null)
				|| (vo.getFechaFinEntrega() != null)) {
			// SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

			// Fecha de inicio
			if (vo.getFechaInicioEntrega() != null) {
				// qual.append(DBUtils.getCondition(qual.toString()))
				// .append(TABLE_NAME).append(".")
				// .append(FENTREGA_COLUMN_NAME)
				// .append(">=TO_DATE('")
				// .append(df.format(vo.getFechaInicioEntrega()))
				// .append("','yyyy/mm/dd')");
				qual.append(DBUtils.getCondition(qual.toString()))
						.append(TABLE_NAME)
						.append(".")
						.append(FENTREGA_COLUMN_NAME)
						.append(">=")
						.append(DBUtils.getNativeToDateSyntax(getConnection(),
								vo.getFechaInicioEntrega(),
								CustomDateFormat.SDF_YYYYMMDD));

			}

			// Fecha de fin
			if (vo.getFechaFinEntrega() != null) {
				// qual.append(DBUtils.getCondition(qual.toString()))
				// .append(TABLE_NAME).append(".")
				// .append(FENTREGA_COLUMN_NAME)
				// .append("<=TO_DATE('")
				// .append(df.format(vo.getFechaFinEntrega()))
				// .append("','yyyy/mm/dd')");
				qual.append(DBUtils.getCondition(qual.toString()))
						.append(TABLE_NAME)
						.append(".")
						.append(FENTREGA_COLUMN_NAME)
						.append("<=")
						.append(DBUtils.getNativeToDateSyntax(getConnection(),
								vo.getFechaFinEntrega(),
								CustomDateFormat.SDF_YYYYMMDD));
			}
		}
	}

	/**
	 * @param busquedaVO
	 * @param consulta
	 * @param clausulaWhere
	 */
	private void compruebaNotas(BusquedaVO busquedaVO,
			StringBuffer clausulaWhere) {
		// Notas
		if (!ArrayUtils.isEmpty(busquedaVO.getNotas())) {
			String nota;
			List estadosProrrogas = new ArrayList();
			boolean comprobarCaducidad = false;
			boolean comprobarReclamacion1 = false;
			boolean comprobarReclamacion2 = false;

			for (int i = 0; i < busquedaVO.getNotas().length; i++) {
				nota = busquedaVO.getNotas()[i];
				if ("PS".equals(nota))
					estadosProrrogas.add(""
							+ PrestamosConstants.ESTADO_PRORROGA_SOLICITADA);
				else if ("PD".equals(nota))
					estadosProrrogas.add(""
							+ PrestamosConstants.ESTADO_PRORROGA_DENEGADA);
				else if ("C".equals(nota))
					comprobarCaducidad = true;
				else if ("R1".equals(nota))
					comprobarReclamacion1 = true;
				else if ("R2".equals(nota))
					comprobarReclamacion2 = true;
			}

			StringBuffer condiciones = new StringBuffer();

			if (estadosProrrogas.size() > 0) {
				condiciones
						.append(CAMPO_ID.getQualifiedName())
						.append(" IN (SELECT ")
						.append(ProrrogaDBEntity.CAMPO_IDPRESTAMO
								.getQualifiedName())
						.append(" FROM ")
						.append(ProrrogaDBEntity.TABLE_NAME)
						.append(" WHERE ")
						.append(DBUtils.generateORTokens(
								ProrrogaDBEntity.CAMPO_ESTADO,
								(String[]) estadosProrrogas
										.toArray(new String[estadosProrrogas
												.size()]))).append(")");
			}

			if (comprobarCaducidad) {
				if (condiciones.length() > 0)
					condiciones.append(" OR ");
				condiciones
						.append("(")
						.append(CAMPO_FMAXFINPRESTAMO.getQualifiedName())
						.append("<")
						.append(DBUtils.getNativeSysDateSyntax(getConnection()))
						.append(" AND ")
						.append(DBUtils
								.generateInTokenField(
										CAMPO_ESTADO,
										new Integer[] {
												new Integer(
														PrestamosConstants.ESTADO_PRESTAMO_ENTREGADO),
												new Integer(
														PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO) }))
						.append(")");
			}

			if (comprobarReclamacion1) {
				if (condiciones.length() > 0)
					condiciones.append(" OR ");

				condiciones.append(DBUtils.generateEQTokenField(
						CAMPO_NUMRECLAMACIONES, 1));
			}

			if (comprobarReclamacion2) {
				if (condiciones.length() > 0)
					condiciones.append(" OR ");

				condiciones.append(DBUtils.generateEQTokenField(
						CAMPO_NUMRECLAMACIONES, 2));
			}

			if (condiciones.length() > 0)
				clausulaWhere
						.append(DBUtils.getCondition(clausulaWhere.toString()))
						.append(" (").append(condiciones).append(")");
		}
	}

	/**
	 * @param busquedaVO
	 * @param clausulaWhere
	 */
	private void compruebaEstados(BusquedaVO busquedaVO,
			StringBuffer clausulaWhere) {
		// Estados
		if ((busquedaVO.getEstados() != null)
				&& (busquedaVO.getEstados().length > 0))
			clausulaWhere.append(DBUtils.generateInTokenField(CAMPO_ESTADO,
					busquedaVO.getEstados()));
	}

	/**
	 * @param busquedaVO
	 * @param clausulaWhere
	 */
	private void compruebaSolicitante(BusquedaVO vo, StringBuffer qual) {
		// Usuario Solicitante
		if (StringUtils.isNotBlank(vo.getSolicitante()))
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateLikeTokenField(CAMPO_NUSRSOLICITANTE,
							vo.getSolicitante(), true));

		// Organo del usuario
		if (StringUtils.isNotBlank(vo.getOrgano()))
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateLikeTokenField(CAMPO_NORGSOLICITANTE,
							vo.getOrgano(), true));
	}

	/**
	 * Obtiene la lista de gestores con préstamos.
	 * 
	 * @param idOrgano
	 *            Identificador del órgano del gesto.
	 * @return Lista de Gestores ({@link UsuarioVO}).
	 */
	public List getGestoresConPrestamos(String idOrgano) {
		Map pairs = new HashMap();
		pairs.put(TABLE_NAME, null);
		pairs.put(UsuarioDBEntityImpl.TABLE_NAME, UsuarioDBEntityImpl.COL_DEFS);

		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils
						.generateInTokenField(
								CAMPO_ESTADO,
								new int[] {
										PrestamosConstants.ESTADO_PRESTAMO_ABIERTO,
										PrestamosConstants.ESTADO_PRESTAMO_SOLICITADO,
										PrestamosConstants.ESTADO_PRESTAMO_RESERVADO,
										PrestamosConstants.ESTADO_PRESTAMO_AUTORIZADO,
										PrestamosConstants.ESTADO_PRESTAMO_ENTREGADO,
										PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO }));

		// Órgano
		if (StringUtils.isNotBlank(idOrgano))
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CAMPO_IDORGSOLICITANTE,
							idOrgano));

		qual.append(" AND ").append(
				DBUtils.generateJoinCondition(CAMPO_IDUSRGESTOR,
						UsuarioDBEntityImpl.CAMPO_ID));

		qual.append(" ORDER BY ")
				.append(UsuarioDBEntityImpl.CAMPO_APELLIDOS.getQualifiedName())
				.append(",")
				.append(UsuarioDBEntityImpl.CAMPO_NOMBRE.getQualifiedName());

		return getDistinctVOS(qual.toString(), pairs, UsuarioVO.class);
	}

	/**
	 * Obtiene la lista de préstamos que cumplan los criterios especificados.
	 * 
	 * @param vo
	 *            Criterios de búsqueda.
	 * @return Lista de préstamos ({@link PrestamoVO}).
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getPrestamos(BusquedaVO vo, String[] idsArchivos)
			throws TooManyResultsException {

		// if(ArrayUtils.isEmpty(idsArchivos)){
		//
		// return new ArrayList();
		// }

		StringBuffer qual = new StringBuffer();

		// String TABLES = TABLE_NAME;

		Map pairs = new HashMap();
		pairs.put(TABLE_NAME, COLUMN_DEFINITIONS);
		// pairs.put(UsuarioDBEntityImpl.TABLE_NAME,
		// UsuarioDBEntityImpl.COL_DEFS);

		// Estados
		if (!ArrayUtils.isEmpty(vo.getEstados()))
			qual.append(DBUtils.getCondition(qual.toString()))
					.append(DBUtils.generateInTokenField(CAMPO_ESTADO,
							vo.getEstados()));

		// Archivos
		if (!ArrayUtils.isEmpty(idsArchivos))
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateInTokenField(CAMPO_IDARCHIVO, idsArchivos));

		// Lista de órganos del usuario solicitante
		if (!ArrayUtils.isEmpty(vo.getOrganosUsuarioSolicitante()))
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateInTokenField(CAMPO_IDORGSOLICITANTE,
							vo.getOrganosUsuarioSolicitante()));

		if (ConfigConstants.getInstance()
				.getPermitirTransferenciasEntreArchivos()) {
			// Añadir la join
			qual.append(" AND ").append(
					DBUtils.generateJoinCondition(CAMPO_IDARCHIVO,
							ArchivoDbEntityImpl.ID_FIELD));

			// TABLES+=","+ArchivoDbEntityImpl.TABLE_NAME;
			pairs.put(ArchivoDbEntityImpl.TABLE_NAME, null);
		}

		if (StringUtils.isNotBlank(vo.getCodigo()))
			qual.append(" AND ")
					.append(CodigoTransferenciaUtils
							.textSqlCodigoTransferenciaPrestamo(
									CAMPO_ANO.getQualifiedName(),
									CAMPO_ORDEN.getQualifiedName(),
									getConnection())).append(" LIKE '%")
					.append(vo.getCodigo()).append("%'");

		// Id usuario gestor
		if (StringUtils.isNotBlank(vo.getGestor()))
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateEQTokenField(CAMPO_IDUSRGESTOR,
							vo.getGestor()));

		// Nombre del solicitante
		compruebaSolicitante(vo, qual);

		// Notas
		compruebaNotas(vo, qual);

		// Fecha de entrega
		compruebaFechaEntrega(vo, qual);

		// Datos autorizado
		if (StringUtils.isNotEmpty(vo.getDatosautorizado())) {
			qual.append(" AND ").append(
					DBUtils.generateLikeFieldQualified(CAMPO_DATOSAUTORIZADO,
							vo.getDatosautorizado(), TABLE_NAME,
							DBUtils.CONTIENE_LIKE));
		}
		// Tipo entrega
		if (StringUtils.isNotEmpty(vo.getTipoentrega())) {
			qual.append(" AND ").append(
					DBUtils.generateLikeFieldQualified(CAMPO_TIPOENTREGA,
							vo.getTipoentrega(), TABLE_NAME,
							DBUtils.CONTIENE_LIKE));
		}

		// Restriccion Usuarios
		if (StringUtils.isNotEmpty(vo.getIdAppUser())) {
			String subQuery = getSubQueryRestriccionAppUser(vo.getIdAppUser(),
					vo.getIdsArchivosUser());
			qual.append(DBUtils.AND).append(
					DBUtils.generateInTokenFieldSubQuery(CAMPO_ID, subQuery));
		}

		// Número de expediente
		if (StringUtils.isNotEmpty(vo.getExpedienteudoc())) {
			// Añadimos la tabla de ASGPSOLICITUDUDOC al conjunto de tablas
			pairs.put(DetalleDBEntityImpl.TABLE_NAME, null);

			// Añadir la join
			qual.append(DBUtils.AND)
					.append(DBUtils.generateJoinCondition(CAMPO_ID,
							DetalleDBEntityImpl.IDSOLICITUD_FIELD))
					.append(DBUtils.AND)
					.append(DBUtils.generateLikeTokenField(
							DetalleDBEntityImpl.EXPEDIENTEUDOC_FIELD,
							vo.getExpedienteudoc()));
		}

		if (vo.getPageInfo() != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("codigo", new DbColumnDef[] { CAMPO_ANO,
					CAMPO_ORDEN });
			criteriosOrdenacion.put("norgsolicitante", CAMPO_NORGSOLICITANTE);
			criteriosOrdenacion.put("nusrsolicitante", CAMPO_NUSRSOLICITANTE);
			criteriosOrdenacion.put("estado", CAMPO_ESTADO);
			criteriosOrdenacion.put("festado", CAMPO_FESTADO);
			criteriosOrdenacion.put("fmaxfinprestamo", CAMPO_FMAXFINPRESTAMO);

			/*
			 * return getVOS(qual.toString(),
			 * vo.getPageInfo().getOrderBy(criteriosOrdenacion), TABLES,
			 * COLUMN_DEFINITIONS, PrestamoVO.class, vo.getPageInfo());
			 */

			return getDistinctVOS(qual.toString(),
					vo.getPageInfo().getOrderBy(criteriosOrdenacion), pairs,
					PrestamoVO.class, vo.getPageInfo());
		} else {
			qual.append(" ORDER BY ").append(CAMPO_ANO.getQualifiedName())
					.append(" DESC,").append(CAMPO_ORDEN.getQualifiedName())
					.append(" DESC");

			return getDistinctVOS(qual.toString(), pairs, PrestamoVO.class);
			// return getVOS(qual.toString(), TABLES, COLUMN_DEFINITIONS,
			// PrestamoVO.class);
		}
	}

	private String getSubQueryRestriccionAppUser(String idAppUsr,
			String[] idsArchivo) {
		StringBuffer subQuery = new StringBuffer();

		int[] estadosIniciales = new int[] { PrestamosConstants.ESTADO_PRESTAMO_ABIERTO };

		if (StringUtils.isNotEmpty(idAppUsr)) {
			subQuery.append(DBUtils.SELECT)
					.append(CAMPO_ID.getQualifiedName())
					.append(DBUtils.FROM)
					.append(TABLE_NAME)
					.append(DBUtils.WHERE)
					.append(DBUtils.ABRIR_PARENTESIS)
					.append(DBUtils.generateInTokenField(CAMPO_ESTADO,
							estadosIniciales))
					.append(DBUtils.AND)
					.append(DBUtils.ABRIR_PARENTESIS)
					.append(DBUtils.generateEQTokenField(
							CAMPO_IDUSRSOLICITANTE, idAppUsr))
					.append(DBUtils.OR)
					.append(DBUtils.generateEQTokenField(CAMPO_IDUSRGESTOR,
							idAppUsr)).append(DBUtils.CERRAR_PARENTESIS)
					.append(DBUtils.CERRAR_PARENTESIS);

			subQuery.append(DBUtils.OR).append(
					DBUtils.generateEQTokenField(CAMPO_IDUSRGESTOR, idAppUsr));

			if (ArrayUtils.isNotEmptyOrBlank(idsArchivo)) {
				subQuery.append(DBUtils.OR)
						.append(DBUtils.ABRIR_PARENTESIS)
						.append(DBUtils.generateInTokenField(CAMPO_IDARCHIVO,
								idsArchivo)).append(DBUtils.CERRAR_PARENTESIS);
			}
		}

		return subQuery.toString();
	}

	/**
	 * Actualiza las observacioens de la Consulta.
	 */
	public void updateObservaciones(String id, String observaciones) {
		final String qual = new StringBuffer().append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id)).toString();

		HashMap mapColumnsToUpdate = new HashMap();
		mapColumnsToUpdate.put(CAMPO_OBSERVACIONES, observaciones);
		updateFields(qual, mapColumnsToUpdate, TABLE_NAME);
	}

	public int getCountPrestamosByIdMotivo(String idMotivo) {
		StringBuffer qual = new StringBuffer();
		if (StringUtils.isNotEmpty(idMotivo))
			qual.append(DBUtils.WHERE).append(
					DBUtils.generateEQTokenField(CAMPO_ID_MOTIVO, idMotivo));

		return getVOCount(qual.toString(), TABLE_NAME);
	}

	public List getPrestamosByIdarchivoAndIdUsuarioGestor(String idArchivo,
			String idUsuario, String[] estados) {

		String qual = new StringBuffer(
				getWhereClausePrestamosEnElaboracionByIdUsuario(idUsuario))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(CAMPO_IDARCHIVO, idArchivo))
				.toString();

		return getVOS(qual, TABLE_NAME, COLUMN_DEFINITIONS, PrestamoVO.class);
	}
}