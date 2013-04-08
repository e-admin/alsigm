package valoracion.db;

import fondos.db.SerieDBEntityImpl;
import fondos.db.TablaTemporalDBEntityImpl;
import fondos.db.UnidadDocumentalDBEntityImpl;
import fondos.db.oracle.ElementoCuadroClasificacionDBEntityImpl;
import fondos.model.IElementoCuadroClasificacion;
import fondos.vos.TablaTemporalFondosVO;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbOutputStatement;
import ieci.core.db.DbSelectStatement;
import ieci.core.db.DbUtil;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import solicitudes.db.DetalleDBEntity;
import solicitudes.prestamos.PrestamosConstants;
import util.StringOwnTokenizer;
import valoracion.ValoracionConstants;
import valoracion.vos.BusquedaVO;
import valoracion.vos.EliminacionSerieVO;
import valoracion.vos.IUnidadDocumentalEliminacionVO;
import valoracion.vos.UnidadDocumentalEliminacionVO;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.db.AliasedColumnDef;
import common.db.ConstraintType;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.db.TableDef;
import common.exceptions.ArchivoModelException;
import common.exceptions.DBException;
import common.exceptions.TooManyResultsException;
import common.lang.MutableStringId;
import common.pagination.PageInfo;
import common.util.ArrayUtils;
import common.util.CustomDateFormat;
import common.util.DateUtils;
import common.util.ListUtils;
import common.vos.ConsultaSQL;

import deposito.db.UDocEnUiDepositoDbEntityImpl;
import deposito.db.UInstalacionDepositoDBEntity;
import deposito.db.oracle.HuecoDBEntityImpl;
import descripcion.db.FechaDBEntityImpl;
import descripcion.db.ReferenciaDBEntityImpl;
import docelectronicos.db.UnidadDocumentalElectronicaDBEntityImpl;

/**
 * Clase que trabaja con trabaja contra la base de datos sobre la tabla de
 * Eliminacion de valoracion de serie.
 */
public class EliminacionSerieDBEntityImpl extends DBEntity implements
		IEliminacionSerieDBEntity {

	static Logger logger = Logger.getLogger(EliminacionSerieDBEntityImpl.class);

	public static final String TABLE_NAME = "ASGFELIMSERIE";

	private static final String ID_COLUMN_NAME = "ID";
	private static final String ID_VALORACION_COLUMN_NAME = "IDVALORACION";
	private static final String ID_SERIE_COLUMN_NAME = "IDSERIE";
	private static final String TITULO_COLUMN_NAME = "TITULO";
	private static final String ESTADO_COLUMN_NAME = "ESTADO";
	private static final String FECHAESTADO_COLUMN_NAME = "FECHAESTADO";
	private static final String MOTIVORECHAZO_COLUMN_NAME = "MOTIVORECHAZO";
	private static final String NOTAS_COLUMN_NAME = "NOTAS";
	private static final String MAXANOSVIGENCIA_COLUMN_NAME = "MAXANOSVIGENCIA";
	private static final String CONDBUSQANADIDA_COLUMN_NAME = "CONDBUSQANADIDA";
	private static final String TIPOELIMINACION_COLUMN_NAME = "TIPOELIMINACION";
	private static final String SELECCIONUDOC_COLUMN_NAME = "SELECCIONUDOC";
	private static final String FEJECUCION_COLUMN_NAME = "FEJECUCION";
	private static final String FAPROBACION_COLUMN_NAME = "FAPROBACION";
	private static final String FDESTRUCCION_COLUMN_NAME = "FDESTRUCCION";
	private static final String FFINALIZACION_COLUMN_NAME = "FFINALIZACION";
	private static final String ID_ARCHIVO_COLUMN_NAME = "IDARCHIVO";

	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_ID_VALORACION = new DbColumnDef(null,
			TABLE_NAME, ID_VALORACION_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);
	public static final DbColumnDef CAMPO_ID_SERIE = new DbColumnDef(null,
			TABLE_NAME, ID_SERIE_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_TITULO = new DbColumnDef(null,
			TABLE_NAME, TITULO_COLUMN_NAME, DbDataType.SHORT_TEXT, 254, false);
	public static final DbColumnDef CAMPO_ESTADO = new DbColumnDef(null,
			TABLE_NAME, ESTADO_COLUMN_NAME, DbDataType.SHORT_INTEGER, false);
	public static final DbColumnDef CAMPO_FECHAESTADO = new DbColumnDef(null,
			TABLE_NAME, FECHAESTADO_COLUMN_NAME, DbDataType.DATE_TIME, false);
	public static final DbColumnDef CAMPO_MOTIVORECHAZO = new DbColumnDef(null,
			TABLE_NAME, MOTIVORECHAZO_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			true);
	public static final DbColumnDef CAMPO_NOTAS = new DbColumnDef(null,
			TABLE_NAME, NOTAS_COLUMN_NAME, DbDataType.SHORT_TEXT, 1024, true);
	public static final DbColumnDef CAMPO_MAXANOSVIGENCIA = new DbColumnDef(
			null, TABLE_NAME, MAXANOSVIGENCIA_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, true);
	public static final DbColumnDef CAMPO_CONDBUSQANADIDA = new DbColumnDef(
			"condicionBusqueda", TABLE_NAME, CONDBUSQANADIDA_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 1024, true);
	public static final DbColumnDef CAMPO_TIPOELIMINACION = new DbColumnDef(
			null, TABLE_NAME, TIPOELIMINACION_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, true);
	public static final DbColumnDef CAMPO_SELECCIONUDOC = new DbColumnDef(null,
			TABLE_NAME, SELECCIONUDOC_COLUMN_NAME, DbDataType.SHORT_TEXT, 1,
			true);
	public static final DbColumnDef CAMPO_FEJECUCION = new DbColumnDef(
			"fechaEjecucion", TABLE_NAME, FEJECUCION_COLUMN_NAME,
			DbDataType.DATE_TIME, true);
	public static final DbColumnDef CAMPO_FAPROBACION = new DbColumnDef(
			"fechaAprobacion", TABLE_NAME, FAPROBACION_COLUMN_NAME,
			DbDataType.DATE_TIME, true);
	public static final DbColumnDef CAMPO_FDESTRUCCION = new DbColumnDef(
			"fechaDestruccion", TABLE_NAME, FDESTRUCCION_COLUMN_NAME,
			DbDataType.DATE_TIME, true);
	public static final DbColumnDef CAMPO_FFINALIZACION = new DbColumnDef(
			"fechaFinalizacion", TABLE_NAME, FFINALIZACION_COLUMN_NAME,
			DbDataType.DATE_TIME, true);
	public static final DbColumnDef CAMPO_ID_ARCHIVO = new DbColumnDef(null,
			TABLE_NAME, ID_ARCHIVO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef[] COLS_DEFS = { CAMPO_ID,
			CAMPO_ID_VALORACION, CAMPO_ID_SERIE, CAMPO_TITULO, CAMPO_ESTADO,
			CAMPO_FECHAESTADO, CAMPO_MOTIVORECHAZO, CAMPO_NOTAS,
			CAMPO_MAXANOSVIGENCIA, CAMPO_CONDBUSQANADIDA,
			CAMPO_TIPOELIMINACION, CAMPO_SELECCIONUDOC, CAMPO_FEJECUCION,
			CAMPO_FAPROBACION, CAMPO_FDESTRUCCION, CAMPO_FFINALIZACION,
			CAMPO_ID_ARCHIVO };

	public static final String COLUMN_NAMES = DbUtil.getColumnNames(COLS_DEFS);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public EliminacionSerieDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public EliminacionSerieDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene las eliminaciones de un gestor que se encuentren en alguno de los
	 * estados indicados
	 * 
	 * @param idGestor
	 *            Identificador del usuario gestor de las eliminaciones. Puede
	 *            ser nulo
	 * @param estado
	 *            Conjunto de estados de eliminación. Puede ser nulo
	 * @return Listado de las eliminaciones {@link EliminacionSerieVO}
	 */
	public List getEliminacionesXGestor(String idGestor, int[] estado) {
		StringBuffer qual = new StringBuffer(" WHERE 1=1 ");
		if (idGestor != null)
			qual.append(" AND ")
					.append(DBUtils.generateJoinCondition(CAMPO_ID_VALORACION,
							ValoracionDBEntityImpl.CAMPO_ID))
					.append(" AND ")
					.append(DBUtils.generateEQTokenField(
							ValoracionDBEntityImpl.CAMPO_IDUSRGESTORSERIE,
							idGestor));
		if (estado != null)
			qual.append(" AND ").append(
					DBUtils.generateORTokens(CAMPO_ESTADO, estado));

		String[] queryTables = { TABLE_NAME, ValoracionDBEntityImpl.TABLE_NAME };
		return getVOS(qual.toString(), ArrayUtils.join(queryTables, ","),
				COLS_DEFS, EliminacionSerieVO.class);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * valoracion.db.IEliminacionSerieDBEntity#getCountEliminacionesXEstado(
	 * int[])
	 */
	public int getCountEliminacionesXEstado(int[] estado, String[] idsArchivo) {

		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils
						.generateJoinCondition(
								ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD,
								CAMPO_ID_SERIE))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						ValoracionDBEntityImpl.CAMPO_ID, CAMPO_ID_VALORACION));

		if (estado != null)
			qual.append(" AND ").append(
					DBUtils.generateORTokens(CAMPO_ESTADO, estado));

		if (idsArchivo != null)
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_ID_ARCHIVO, idsArchivo));

		String[] tablas = {
				ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO,
				ValoracionDBEntityImpl.TABLE_NAME, TABLE_NAME };

		return getVOCount(qual.toString(), ArrayUtils.join(tablas, ","));
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * valoracion.db.IEliminacionSerieDBEntity#getEliminacionesXEstado(int[])
	 */
	public List getEliminacionesXEstado(int[] estado, String[] idsArchivo) {
		DbColumnDef tituloSerie = new DbColumnDef("tituloSerie",
				ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD);
		DbColumnDef tituloValoracion = new DbColumnDef("tituloValoracion",
				ValoracionDBEntityImpl.CAMPO_TITULO);
		DbColumnDef[] columnas = (DbColumnDef[]) ArrayUtils.concat(COLS_DEFS,
				new DbColumnDef[] { tituloSerie, tituloValoracion });

		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils
						.generateJoinCondition(
								ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD,
								CAMPO_ID_SERIE))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						ValoracionDBEntityImpl.CAMPO_ID, CAMPO_ID_VALORACION));

		if (estado != null)
			qual.append(" AND ").append(
					DBUtils.generateORTokens(CAMPO_ESTADO, estado));

		if (idsArchivo != null)
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_ID_ARCHIVO, idsArchivo));

		qual.append(" ORDER BY ").append(CAMPO_TITULO.getQualifiedName());

		String[] tablas = {
				ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO,
				ValoracionDBEntityImpl.TABLE_NAME, TABLE_NAME };

		return getVOS(qual.toString(), ArrayUtils.join(tablas, ","), columnas,
				EliminacionSerieVO.class);
	}

	/**
	 * Inserta una eliminacion en la base de datos .
	 * 
	 * @param eliminacion
	 *            Eliminacion que se desea insertar
	 */
	public void insertEliminacion(final EliminacionSerieVO eliminacion) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				eliminacion.setId(getGuid(eliminacion.getId()));

				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLS_DEFS, eliminacion);
				DbInsertFns.insert(conn, TABLE_NAME, COLUMN_NAMES, inputRecord);
			}
		};

		command.execute();
	}

	/**
	 * Realiza la actualización de la eliminacion dada en la base de datos
	 * 
	 * @param eliminacion
	 *            Eliminación que deseamos actualizar
	 */
	public void updateEliminacion(EliminacionSerieVO eliminacion) {
		String qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateTokenFieldQualified(CAMPO_ID,
						eliminacion.getId(), TABLE_NAME, ConstraintType.EQUAL))
				.toString();

		updateVO(qual, TABLE_NAME, COLS_DEFS, eliminacion);
	}

	/**
	 * Obtiene un listado de las eliminacion de una valoracion dada por su
	 * identificador y cuyo estado de eliminacion se encuentra en unos de los
	 * pasados, o todas las eliminaciones en caso de no venir definidos los
	 * estados.
	 * 
	 * @param codigoValoracion
	 *            Valoracion de la que deseamos obtener las eliminaciones.
	 * @param estados
	 *            Listado de los estado que deben tener las eliminacion o null
	 *            si deseamos obtener todas
	 * @return Listado de eliminaciones de la valoracion
	 */
	public List getEliminaciones(String codigoValoracion, int[] estados) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateTokenFieldQualified(CAMPO_ID_VALORACION,
						codigoValoracion, TABLE_NAME, ConstraintType.EQUAL));

		// Añadimos la restriccion por estados en caso de existir
		if (estados != null && estados.length > 0) {
			qual.append(" AND ");
			qual.append(DBUtils.generateInTokenField(CAMPO_ESTADO, estados));
		}

		return getVOS(qual.toString(), TABLE_NAME, COLS_DEFS,
				EliminacionSerieVO.class);
	}

	/**
	 * Obtiene una eliminacion de una valoración a partir de su identificador en
	 * la base de datos.
	 * 
	 * @param codigo
	 *            Identificador de la eliminacion en la base de datos
	 * @return Objeto {@link EliminacionSerieVO} con los detalles de la
	 *         eliminacion.
	 */
	public EliminacionSerieVO getEliminacion(String codigo) {
		String qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateTokenFieldQualified(CAMPO_ID, codigo,
						TABLE_NAME, ConstraintType.EQUAL)).toString();

		return (EliminacionSerieVO) getVO(qual, TABLE_NAME, COLS_DEFS,
				EliminacionSerieVO.class);
	}

	/**
	 * Obtiene un listado de las eliminaciones dadas por su identificador de bd.
	 * 
	 * @param ids
	 *            Lsitado de identificadores de las eliminaciones que deseamos
	 *            recuperar.
	 * @return Eliminaciones deseadas
	 */
	public List getEliminaciones(String[] ids) {
		String qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateInTokenField(CAMPO_ID, ids)).toString();

		return getVOS(qual, TABLE_NAME, COLS_DEFS, EliminacionSerieVO.class);
	}

	/**
	 * Realiza el borrado de una eliminación dada por su identificador.
	 * 
	 * @param idPrestamo
	 *            Identificador de la eliminación.
	 */
	public void deleteEliminacion(String id) {
		final String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id)).toString();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual);

			}
		};

		command.execute();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.db.IEliminacionSerieDBEntity#getIDsUdocsAConservar(valoracion.vos.EliminacionSerieVO,
	 *      java.lang.String)
	 */
	public String[] getIDsUdocsAConservar(EliminacionSerieVO seleccion,
			String subqueryIdsUdocConservar) {
		try {

			List udocs = getListaUdocsAConservar(seleccion,
					seleccion.getIdArchivo(), subqueryIdsUdocConservar, null);
			List ltIdsUdocs = new ArrayList();

			if (ListUtils.isNotEmpty(udocs)) {
				Iterator it = udocs.iterator();
				while (it.hasNext()) {
					IUnidadDocumentalEliminacionVO unidadDocumentalEliminacionVO = (IUnidadDocumentalEliminacionVO) it
							.next();
					ltIdsUdocs.add(unidadDocumentalEliminacionVO.getIdudoc());
				}

				return (String[]) ltIdsUdocs
						.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.db.IEliminacionSerieDBEntity#getIDsUdocsAEliminar(valoracion.vos.EliminacionSerieVO,
	 *      java.lang.String)
	 */
	public String[] getIDsUdocsAEliminar(EliminacionSerieVO seleccion,
			String subqueryIdsUdocConservar) {
		try {
			List idList = new ArrayList();
			DbOutputStatement stmt = new DbOutputStatement();
			stmt.create(
					getConnection(),
					createQueryUdocsSeleccion(seleccion.getIdSerie(),
							seleccion.getFechaEjecucion(),
							seleccion.getMaxAnosVigencia(),
							seleccion.getCondicionBusqueda(),
							seleccion.getIdArchivo(), subqueryIdsUdocConservar,
							true));
			stmt.execute();
			while (stmt.next())
				idList.add(stmt.getShortText(1));

			stmt.release();
			return (String[]) idList.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.db.IEliminacionSerieDBEntity#getSubConsultaIDsUdocsAEliminar(valoracion.vos.EliminacionSerieVO,
	 *      java.lang.String)
	 */
	public String getSubConsultaIDsUdocsAEliminar(EliminacionSerieVO seleccion,
			String subqueryIdsUdocConservar) {
		try {
			return createQueryUdocsSeleccion(seleccion.getIdSerie(),
					seleccion.getFechaEjecucion(),
					seleccion.getMaxAnosVigencia(),
					seleccion.getCondicionBusqueda(), seleccion.getIdArchivo(),
					subqueryIdsUdocConservar, true);
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	private String createQueryUdocsRelSeleccionEliminar(String idSerie,
			Date fechaEjecucion, int anosVigencia,
			String xmlCondicionesConservacion, String idArchivo,
			String subqueryIdsUdocConservar) {

		ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo();
		String idUdocRel = csa.getConfiguracionDescripcion().getUdocsRel();

		StringBuffer buff = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateInTokenFieldSubQuery(
						ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO,
						createQueryUdocsSeleccion(idSerie, fechaEjecucion,
								anosVigencia, xmlCondicionesConservacion,
								idArchivo, subqueryIdsUdocConservar, true)))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						ReferenciaDBEntityImpl.CAMPO_ID_CAMPO, idUdocRel))
				.append(DBUtils.AND)
				.append(DBUtils.generateNotInTokenFieldSubQuery(
						ReferenciaDBEntityImpl.CAMPO_IDOBJREF,
						createQueryUdocsSeleccion(idSerie, fechaEjecucion,
								anosVigencia, xmlCondicionesConservacion,
								idArchivo, subqueryIdsUdocConservar, true)));

		String result = DbSelectStatement.getSelectStmtText(
				ReferenciaDBEntityImpl.TABLE_NAME,
				ReferenciaDBEntityImpl.CAMPO_IDOBJREF.getName(),
				buff.toString(), false);

		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.db.IEliminacionSerieDBEntity#getIDsUdocsRelAEliminar(valoracion.vos.EliminacionSerieVO,
	 *      java.lang.String)
	 */
	public String[] getIDsUdocsRelAEliminar(EliminacionSerieVO seleccion,
			String subqueryIdsUdocConservar) {
		try {
			List idList = new ArrayList();
			DbOutputStatement stmt = new DbOutputStatement();
			stmt.create(
					getConnection(),
					createQueryUdocsRelSeleccionEliminar(
							seleccion.getIdSerie(),
							seleccion.getFechaEjecucion(),
							seleccion.getMaxAnosVigencia(),
							seleccion.getCondicionBusqueda(),
							seleccion.getIdArchivo(), subqueryIdsUdocConservar));
			stmt.execute();
			while (stmt.next())
				idList.add(stmt.getShortText(1));

			stmt.release();
			return (String[]) idList.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	/**
	 * Permite obtener las unidades documentales a conservar o eliminar de una
	 * eliminación
	 * 
	 * @param seleccion
	 *            Identificador de la seleccion
	 * @param idArchivo
	 *            Identificador del archivo
	 * @param subqueryIdsUdocConservar
	 *            Subquery de las unidades a conservar
	 * @param getUnidadesConservar
	 *            Indica si las unidades que se esperan son a conservar o a
	 *            eliminar
	 * @param pageInfo
	 * @return Lista de {@link UnidadDocumentalEliminacionVO}
	 * @throws TooManyResultsException
	 */
	private List getListaUdocs(EliminacionSerieVO seleccion, String idArchivo,
			String subqueryIdsUdocConservar, boolean getUnidadesConservar,
			PageInfo pageInfo) {

		ConsultaSQL consultaSQL = getConsultaListaUdocs(seleccion, idArchivo,
				subqueryIdsUdocConservar, getUnidadesConservar, pageInfo, false);

		if (pageInfo != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("codigo",
					ElementoCuadroClasificacionDBEntityImpl.CODIGO_FIELD);

			try {
				return getDistinctVOS(consultaSQL.getWhereCompleta(),
						pageInfo.getOrderBy(criteriosOrdenacion),
						consultaSQL.getFromDefinition(),
						consultaSQL.getColumnas(),
						UnidadDocumentalEliminacionVO.class, pageInfo);
			} catch (TooManyResultsException e) {
				return null;
			}
		} else {
			String where = consultaSQL.getWhereCompleta()
					+ DBUtils
							.generateOrderBy(ElementoCuadroClasificacionDBEntityImpl.CODIGO_FIELD);

			return getDistinctVOS(where, consultaSQL.getFromDefinition(),
					consultaSQL.getColumnas(),
					UnidadDocumentalEliminacionVO.class);
		}
	}

	private List getListaIdsUdocs(EliminacionSerieVO seleccion,
			String idArchivo, String subqueryIdsUdocConservar,
			boolean getUnidadesConservar, PageInfo pageInfo) {
		ConsultaSQL consultaSQL = getConsultaListaUdocs(seleccion, idArchivo,
				subqueryIdsUdocConservar, getUnidadesConservar, pageInfo, true);

		return getDistinctVOS(consultaSQL.getWhereCompleta(),
				consultaSQL.getFromDefinition(), consultaSQL.getColumnas(),
				MutableStringId.class);
	}

	/**
	 * Permite obtener las unidades documentales a conservar o eliminar de una
	 * eliminación
	 * 
	 * @param seleccion
	 *            Identificador de la seleccion
	 * @param idArchivo
	 *            Identificador del archivo
	 * @param subqueryIdsUdocConservar
	 *            Subquery de las unidades a conservar
	 * @param getUnidadesConservar
	 *            Indica si las unidades que se esperan son a conservar o a
	 *            eliminar
	 * @param pageInfo
	 * @return Lista de {@link UnidadDocumentalEliminacionVO}
	 * @throws TooManyResultsException
	 */
	private ConsultaSQL getConsultaListaUdocs(EliminacionSerieVO seleccion,
			String idArchivo, String subqueryIdsUdocConservar,
			boolean getUnidadesConservar, PageInfo pageInfo, boolean onlyIds) {
		DbColumnDef[] columnas = null;

		StringBuffer fromClause = new StringBuffer();
		StringBuffer whereClause = new StringBuffer();

		boolean seleccionEjecutada = (seleccion.getEstado() == ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_DESTRUCCION)
				|| (seleccion.getEstado() == ValoracionConstants.ESTADO_ELIMINACION_DESTRUCCION_REALIZADA)
				|| (seleccion.getEstado() == ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_FINALIZACION)
				|| (seleccion.getEstado() == ValoracionConstants.ESTADO_ELIMINACION_FINALIZADA);

		ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo();
		String fechaFinal = csa.getConfiguracionDescripcion()
				.getFechaExtremaFinal();
		String fechaInicial = csa.getConfiguracionDescripcion()
				.getFechaExtremaInicial();

		// Calculo de la fecha limite
		Calendar fechaLimite = new GregorianCalendar();
		fechaLimite.setTime(seleccion.getFechaEjecucion());
		fechaLimite.add(Calendar.YEAR, (-1) * seleccion.getMaxAnosVigencia());

		// Definicion de tablas auxiliares
		TableDef tablaElementoCuadro1 = new TableDef(
				ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO,
				ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO);
		TableDef tablaFecha1 = new TableDef(FechaDBEntityImpl.TABLE_NAME, "f1");
		TableDef tablaFecha2 = new TableDef(FechaDBEntityImpl.TABLE_NAME, "f2");
		TableDef tablaUnidadDocumental = new TableDef(
				UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL,
				UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL);
		TableDef tablaUdocDeposito = new TableDef(
				UDocEnUiDepositoDbEntityImpl.TABLE_NAME,
				UDocEnUiDepositoDbEntityImpl.TABLE_NAME);
		TableDef tablaUiDeposito = new TableDef(
				UInstalacionDepositoDBEntity.TABLE_NAME,
				UInstalacionDepositoDBEntity.TABLE_NAME);
		TableDef tablaHueco = new TableDef(HuecoDBEntityImpl.TABLE_NAME,
				HuecoDBEntityImpl.TABLE_NAME);

		// Definicion de columnas auxiliares
		DbColumnDef idElementoCuadro1 = new AliasedColumnDef(
				ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD,
				tablaElementoCuadro1);
		DbColumnDef idArchivo1 = new AliasedColumnDef(
				ElementoCuadroClasificacionDBEntityImpl.ARCHIVO_FIELD,
				tablaElementoCuadro1);
		DbColumnDef idElementoCampoFecha1 = new AliasedColumnDef(
				FechaDBEntityImpl.CAMPO_ID_ELEMENTO, tablaFecha1);
		DbColumnDef idElementoCampoFecha2 = new AliasedColumnDef(
				FechaDBEntityImpl.CAMPO_ID_ELEMENTO, tablaFecha2);
		DbColumnDef idEliminacion1 = new AliasedColumnDef(
				ElementoCuadroClasificacionDBEntityImpl.ID_ELIMINACION_FIELD,
				tablaElementoCuadro1);
		DbColumnDef fechaFinal1 = new AliasedColumnDef(
				FechaDBEntityImpl.CAMPO_FECHA_FINAL, tablaFecha1);
		DbColumnDef fechaFinal2 = new AliasedColumnDef(
				FechaDBEntityImpl.CAMPO_FECHA_FINAL, tablaFecha2);

		DbColumnDef colFechaIni = new DbColumnDef("fechaIniUdoc",
				tablaFecha2.getAlias(), FechaDBEntityImpl.CAMPO_FECHA_INICIAL);
		DbColumnDef colFechaFin = new DbColumnDef("fechaFinUdoc",
				tablaFecha1.getAlias(), FechaDBEntityImpl.CAMPO_FECHA_FINAL);

		if (onlyIds) {
			columnas = new DbColumnDef[] { new DbColumnDef("id",
					UnidadDocumentalDBEntityImpl.CAMPO_ID) };
		} else {
			columnas = new DbColumnDef[] {
					new DbColumnDef(
							"Expedienteudoc",
							UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE),
					UnidadDocumentalDBEntityImpl.CAMPO_SISTEMA_PRODUCTOR,
					new DbColumnDef("tipoDocumental",
							UnidadDocumentalDBEntityImpl.CAMPO_TIPO_DOCUMENTAL),
					new DbColumnDef("Idudoc",
							UnidadDocumentalDBEntityImpl.CAMPO_ID),
					new DbColumnDef("ubicacion", HuecoDBEntityImpl.CAMPO_PATH),
					UDocEnUiDepositoDbEntityImpl.SIGNATURAUDOC_FIELD,
					UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD,
					ElementoCuadroClasificacionDBEntityImpl.CODIGO_FIELD,
					ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD,
					ElementoCuadroClasificacionDBEntityImpl.IDFONDO_FIELD,
					ElementoCuadroClasificacionDBEntityImpl.CODIGO_REFERENCIA_FIELD,
					colFechaIni, colFechaFin };

		}

		// Tablas de la consulta
		// StringBuffer strBufferTableNames=new StringBuffer();
		fromClause.append(tablaFecha1.getDeclaration()).append(Constants.COMMA)
				.append(tablaUnidadDocumental.getDeclaration())
				.append(Constants.COMMA)
				.append(tablaElementoCuadro1.getDeclaration())
				.append(Constants.COMMA)
				.append(tablaUdocDeposito.getDeclaration())
				.append(Constants.COMMA)
				.append(tablaUiDeposito.getDeclaration())
				.append(Constants.COMMA).append(tablaHueco.getDeclaration())
				.append(Constants.COMMA).append(tablaFecha2.getDeclaration());

		// Padre = Identificador de la serie
		whereClause.append(DBUtils.generateEQTokenField(new AliasedColumnDef(
				ElementoCuadroClasificacionDBEntityImpl.IDPADRE_FIELD,
				tablaElementoCuadro1), seleccion.getIdSerie()));

		// Identificador de archivo
		if (idArchivo != null) {
			whereClause.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(idArchivo1, idArchivo));
		}

		// Identificador de eliminación
		if (seleccionEjecutada) {
			whereClause.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(idEliminacion1,
							seleccion.getId()));
		} else {
			whereClause.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(idEliminacion1, null));
		}

		// Union con tabla de unidades documentales
		whereClause.append(DBUtils.AND).append(
				DBUtils.generateJoinCondition(
						UnidadDocumentalDBEntityImpl.CAMPO_ID,
						idElementoCuadro1));

		// Para obtener solamente las unidades documentales que no esten
		// bloqueadas
		whereClause.append(DBUtils.AND).append(
				DBUtils.generateEQTokenField(
						UnidadDocumentalDBEntityImpl.CAMPO_MARCAS_BLOQUEO, 0));

		// Union con la tabla de fecha1
		whereClause.append(DBUtils.AND).append(
				DBUtils.generateJoinCondition(idElementoCuadro1,
						idElementoCampoFecha1));

		// Restringir fechas al campo fecha final
		whereClause.append(DBUtils.AND).append(
				DBUtils.generateEQTokenField(new AliasedColumnDef(
						FechaDBEntityImpl.CAMPO_ID_CAMPO, tablaFecha1),
						fechaFinal));

		// Aniadir la union con la tabla de elemento del cuadro
		whereClause.append(DBUtils.AND).append(
				DBUtils.generateJoinCondition(idElementoCuadro1,
						idElementoCampoFecha2));

		// Restringir fecha inicial
		whereClause.append(DBUtils.AND).append(
				DBUtils.generateEQTokenField(new AliasedColumnDef(
						FechaDBEntityImpl.CAMPO_ID_CAMPO, tablaFecha2),
						fechaInicial));

		// Restringir las unidades a las que tienen fecha menor o igual que la
		// del plazo de conservacion
		if (!seleccionEjecutada) {
			whereClause
					.append(DBUtils.AND)
					.append(fechaFinal1.getQualifiedName())
					.append(DBUtils.MENOR_IGUAL)
					.append(DBUtils.getNativeToDateSyntax(getConnection(),
							fechaLimite.getTime(),
							CustomDateFormat.SDF_YYYYMMDD));

			// Si hay condiciones avanzadas incluirlas
			if (StringUtils.isNotBlank(seleccion.getCondicionBusqueda())) {
				List condicionesConservacion = getCondicionesBusqueda(seleccion
						.getCondicionBusqueda());

				if (getUnidadesConservar == false) {
					// Aniadir las condiciones de conservacion
					for (Iterator i = condicionesConservacion.iterator(); i
							.hasNext();) {
						whereClause
								.append(DBUtils.AND)
								.append(DBUtils.NOT)
								.append(DBUtils.ABRIR_PARENTESIS)
								.append(((CondicionConservacion) i.next())
										.toSQL(fechaFinal2.getQualifiedName()))
								.append(DBUtils.CERRAR_PARENTESIS);
					}
				}
			}
		}

		// Union con la tabla de unidades en deposito
		whereClause.append(DBUtils.AND).append(
				DBUtils.generateJoinCondition(
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD,
						idElementoCuadro1));

		// Union con la tabla de unidades de instalacion en deposito
		whereClause.append(DBUtils.AND).append(
				DBUtils.generateJoinCondition(
						UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD,
						UInstalacionDepositoDBEntity.ID_FIELD));

		// Union con la tabla de huecos
		whereClause.append(DBUtils.AND).append(
				DBUtils.generateJoinCondition(
						UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD,
						HuecoDBEntityImpl.CAMPO_IDUINSTALACION));

		if (seleccionEjecutada) {
			whereClause
					.append(DBUtils.AND)
					.append(DBUtils
							.generateEQTokenField(
									ElementoCuadroClasificacionDBEntityImpl.ESTADO_ELEMENTO_FIELD,
									IElementoCuadroClasificacion.ESTADO_ELIMINADO));
		} else {
			// Subconsulta con las unidades que no se incluyen
			whereClause
					.append(DBUtils.AND)
					.append(idElementoCuadro1.getQualifiedName())
					.append(DBUtils.NOT_IN)
					.append(DBUtils.ABRIR_PARENTESIS)
					.append(createQueryUdocsSeleccion(seleccion.getIdSerie(),
							seleccion.getFechaEjecucion(),
							seleccion.getMaxAnosVigencia(),
							seleccion.getCondicionBusqueda(),
							seleccion.getIdArchivo(), subqueryIdsUdocConservar,
							getUnidadesConservar))
					.append(DBUtils.CERRAR_PARENTESIS);
		}

		ConsultaSQL consulta = new ConsultaSQL(columnas, fromClause.toString(),
				whereClause.toString());

		return consulta;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.db.IEliminacionSerieDBEntity#getListaUdocsAEliminar(valoracion.vos.EliminacionSerieVO,
	 *      java.lang.String, java.lang.String, common.pagination.PageInfo)
	 */
	public List getListaUdocsAEliminar(EliminacionSerieVO seleccion,
			String idArchivo, String subqueryIdsUdocConservar, PageInfo pageInfo) {
		return getListaUdocs(seleccion, idArchivo, subqueryIdsUdocConservar,
				false, pageInfo);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.db.IEliminacionSerieDBEntity#getListaUdocsAEliminar(valoracion.vos.EliminacionSerieVO,
	 *      java.lang.String, java.lang.String, common.pagination.PageInfo)
	 */
	public List getListaIdsUdocsAEliminar(EliminacionSerieVO seleccion,
			String idArchivo, String subqueryIdsUdocConservar) {
		return getListaIdsUdocs(seleccion, idArchivo, subqueryIdsUdocConservar,
				false, null);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.db.IEliminacionSerieDBEntity#getListaUdocsAConservar(valoracion.vos.EliminacionSerieVO,
	 *      java.lang.String, java.lang.String, common.pagination.PageInfo)
	 */
	public List getListaUdocsAConservar(EliminacionSerieVO seleccion,
			String idArchivo, String subqueryIdsUdocConservar, PageInfo pageInfo)
			throws TooManyResultsException {
		return getListaUdocs(seleccion, idArchivo, subqueryIdsUdocConservar,
				true, pageInfo);
	}

	/**
	 * Permite obtener las unidades documentales relacionadas a las que se
	 * eliminan en la selección
	 * 
	 * @param seleccion
	 *            Selección de unidades documentales
	 * @param idsUdocConservar
	 *            Identificador de las unidades documentales a conservar en la
	 *            eliminación
	 * @param pageInfo
	 *            Información de paginación
	 * @return Lista de unidades documentales
	 *         {@link UnidadDocumentalEliminacionVO}
	 * @throws TooManyResultsException
	 */
	public List getListaUdocsRelAEliminar(EliminacionSerieVO seleccion,
			String subqueryIdsUdocConservar, PageInfo pageInfo)
			throws TooManyResultsException {
		TableDef tableF1 = new TableDef(FechaDBEntityImpl.TABLE_NAME, "f1");
		TableDef tableF2 = new TableDef(FechaDBEntityImpl.TABLE_NAME, "f2");

		DbColumnDef colFechaIni = new DbColumnDef("fechaIniUdoc",
				tableF1.getAlias(), FechaDBEntityImpl.CAMPO_FECHA_INICIAL);
		DbColumnDef colFechaFin = new DbColumnDef("fechaFinUdoc",
				tableF2.getAlias(), FechaDBEntityImpl.CAMPO_FECHA_FINAL);
		DbColumnDef colIdCampoFechaIni = new DbColumnDef(tableF1,
				FechaDBEntityImpl.CAMPO_ID_CAMPO);
		DbColumnDef colIdCampoFechaFin = new DbColumnDef(tableF2,
				FechaDBEntityImpl.CAMPO_ID_CAMPO);

		DbColumnDef[] columnas = {
				new DbColumnDef("Expedienteudoc",
						UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE),
				UnidadDocumentalDBEntityImpl.CAMPO_SISTEMA_PRODUCTOR,
				new DbColumnDef("tipoDocumental",
						UnidadDocumentalDBEntityImpl.CAMPO_TIPO_DOCUMENTAL),
				new DbColumnDef("Idudoc", UnidadDocumentalDBEntityImpl.CAMPO_ID),
				UDocEnUiDepositoDbEntityImpl.SIGNATURAUDOC_FIELD,
				UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD,
				ElementoCuadroClasificacionDBEntityImpl.CODIGO_FIELD,
				ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD,
				ElementoCuadroClasificacionDBEntityImpl.IDFONDO_FIELD,
				ElementoCuadroClasificacionDBEntityImpl.CODIGO_REFERENCIA_FIELD,
				colFechaIni, colFechaFin };

		StringBuffer strBufferTableNames = new StringBuffer(
				tableF1.getDeclaration()
						+ ","
						+ tableF2.getDeclaration()
						+ ","
						+ UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL
						+ ",");

		strBufferTableNames
				.append(ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO
						+ " LEFT OUTER JOIN "
						+ UDocEnUiDepositoDbEntityImpl.TABLE_NAME
						+ " ON "
						+ UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD
								.getQualifiedName()
						+ " = "
						+ ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD
								.getQualifiedName());

		StringBuffer qual = new StringBuffer(" WHERE ");

		// Obtener los ids de las unidades relacionadas a eliminar
		qual.append(
				ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD
						.getQualifiedName())
				.append(" IN (")
				.append(createQueryUdocsRelSeleccionEliminar(
						seleccion.getIdSerie(), seleccion.getFechaEjecucion(),
						seleccion.getMaxAnosVigencia(),
						seleccion.getCondicionBusqueda(),
						seleccion.getIdArchivo(), subqueryIdsUdocConservar))
				.append(") ");

		qual.append(" AND ")
				.append(DBUtils
						.generateJoinCondition(
								ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD,
								UnidadDocumentalDBEntityImpl.CAMPO_ID));

		// Obtener los ids de campo de fechas
		final ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo();
		final String ID_CAMPO_FECHA_INICIAL = csa.getConfiguracionDescripcion()
				.getFechaExtremaInicial();
		final String ID_CAMPO_FECHA_FINAL = csa.getConfiguracionDescripcion()
				.getFechaExtremaFinal();

		qual.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						tableF1.getAlias(),
						FechaDBEntityImpl.CAMPO_ID_ELEMENTO,
						ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO,
						ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(colIdCampoFechaIni,
						ID_CAMPO_FECHA_INICIAL))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						tableF2.getAlias(),
						FechaDBEntityImpl.CAMPO_ID_ELEMENTO,
						ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO,
						ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(colIdCampoFechaFin,
						ID_CAMPO_FECHA_FINAL));

		if (pageInfo != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("codigo",
					ElementoCuadroClasificacionDBEntityImpl.CODIGO_FIELD);

			return getDistinctVOS(qual.toString(),
					pageInfo.getOrderBy(criteriosOrdenacion),
					strBufferTableNames.toString(), columnas,
					UnidadDocumentalEliminacionVO.class, pageInfo);
		} else {
			qual.append(" ORDER BY ")

			.append(ElementoCuadroClasificacionDBEntityImpl.CODIGO_FIELD
					.getQualifiedName());
			return getDistinctVOS(qual.toString(),
					strBufferTableNames.toString(), columnas,
					UnidadDocumentalEliminacionVO.class);
		}
	}

	/**
	 * Obtiene las unidades documentales seleccionables de una serie.
	 * 
	 * @param idSerie
	 *            Identificador de la serie.
	 * @param fechaEjecucion
	 *            Fecha de ejecución de la selección.
	 * @param anosVigencia
	 *            Años de vigencia.
	 * @param xmlCondicionesConservacion
	 *            Condiciones de conservación.
	 * @param pageInfo
	 *            Información de la paginación.
	 * @return Lista de unidades documentales (
	 *         {@link UnidadDocumentalEliminacionVO}).
	 * @throws TooManyResultsException
	 *             si se excede el número máximo de resultados.
	 */
	public List getListaUdocsSeleccionables(String idSerie,
			Date fechaEjecucion, int anosVigencia,
			String xmlCondicionesConservacion, PageInfo pageInfo)
			throws TooManyResultsException {
		// Definicion de columnas
		DbColumnDef[] columnas = {
				new DbColumnDef("Idudoc", UnidadDocumentalDBEntityImpl.CAMPO_ID),
				ElementoCuadroClasificacionDBEntityImpl.CODIGO_FIELD,
				ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD };

		// Definicion de tablas
		String[] tablas = {
				ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO,
				UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL };

		StringBuffer where = new StringBuffer(DBUtils.WHERE);

		// Aniadir la union con la tabla de unidades documentales
		where.append(DBUtils.generateJoinCondition(
				UnidadDocumentalDBEntityImpl.CAMPO_ID,
				ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD));

		// Aniadir la condicion de ideliminacion null
		where.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(
								ElementoCuadroClasificacionDBEntityImpl.ID_ELIMINACION_FIELD,
								null));

		// Obtener solamente las unidades documentales que no esten bloqueadas
		where.append(DBUtils.AND).append(
				DBUtils.generateEQTokenField(
						UnidadDocumentalDBEntityImpl.CAMPO_MARCAS_BLOQUEO, 0));

		// Aniadir la subquery con las unidades a incluir
		where.append(DBUtils.AND)
				.append(ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD
						.getQualifiedName())
				.append(DBUtils.IN)
				.append(DBUtils.ABRIR_PARENTESIS)
				.append(createQueryUdocsSeleccion(idSerie, fechaEjecucion,
						anosVigencia, xmlCondicionesConservacion, null, null,
						true)).append(DBUtils.CERRAR_PARENTESIS);

		if (pageInfo != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("codigo",
					ElementoCuadroClasificacionDBEntityImpl.CODIGO_FIELD);
			criteriosOrdenacion.put("signaturaudoc",
					UDocEnUiDepositoDbEntityImpl.SIGNATURAUDOC_FIELD);

			return getDistinctVOS(where.toString(),
					pageInfo.getOrderBy(criteriosOrdenacion),
					ArrayUtils.join(tablas, ","), columnas,
					UnidadDocumentalEliminacionVO.class, pageInfo);
		} else {
			where.append(" ORDER BY ").append(
					UDocEnUiDepositoDbEntityImpl.SIGNATURAUDOC_FIELD
							.getQualifiedName());
			return getDistinctVOS(where.toString(),
					ArrayUtils.join(tablas, ","), columnas,
					UnidadDocumentalEliminacionVO.class);
		}
	}

	protected final static int UDOCS_A_ELIMINAR = 0;
	protected final static int UDOCS_A_CONSERVAR = 1;

	/**
	 * Genera la subconsulta para descartar las unidades documentales que
	 * cumplen ciertas condiciones
	 * 
	 * @param idElementoCuadro
	 *            Columna con el identificador del elemento en el cuadro
	 * @param subQueryIdsUdocConservar
	 *            Subquery de las unidades documentales a conservar
	 * @return consulta
	 */
	private String createSubQueryUdocsNotInEliminacion(
			DbColumnDef idElementoCuadro, String subQueryIdsUdocConservar) {
		StringBuffer select = new StringBuffer();

		TableDef tablaUdocDeposito = new TableDef(
				UDocEnUiDepositoDbEntityImpl.TABLE_NAME,
				UDocEnUiDepositoDbEntityImpl.TABLE_NAME);
		TableDef tablaUiDeposito = new TableDef(
				UInstalacionDepositoDBEntity.TABLE_NAME,
				UInstalacionDepositoDBEntity.TABLE_NAME);
		TableDef tablaSolicitudUdoc = new TableDef(DetalleDBEntity.TABLE_NAME,
				DetalleDBEntity.TABLE_NAME);
		TableDef tablaUnidadElectronica = new TableDef(
				UnidadDocumentalElectronicaDBEntityImpl.TABLE_NAME,
				UnidadDocumentalElectronicaDBEntityImpl.TABLE_NAME);

		// No incluir unidades que estan en prestamos o consultas
		select.append(DBUtils.SELECT)
				.append(DetalleDBEntity.IDUDOC_FIELD)
				.append(DBUtils.FROM)
				.append(tablaSolicitudUdoc.getDeclaration())
				.append(DBUtils.WHERE)
				.append(DBUtils.generateNEQTokenField(
						DetalleDBEntity.ESTADO_FIELD,
						PrestamosConstants.ESTADO_DETALLE_DENEGADA))
				.append(DBUtils.AND)
				.append(DBUtils.generateNEQTokenField(
						DetalleDBEntity.ESTADO_FIELD,
						PrestamosConstants.ESTADO_DETALLE_DEVUELTA))
				.append(DBUtils.UNION)

				// No incluir unidades electronicas bloqueadas
				.append(DBUtils.SELECT)
				.append(UnidadDocumentalElectronicaDBEntityImpl.IDELEMENTOCF_FIELD)
				.append(DBUtils.FROM)
				.append(tablaUnidadElectronica.getDeclaration())
				.append(DBUtils.WHERE)
				.append(DBUtils
						.generateNEQTokenField(
								UnidadDocumentalElectronicaDBEntityImpl.MARCAS_BLOQUEO_FIELD,
								0))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateJoinCondition(
								UnidadDocumentalElectronicaDBEntityImpl.IDELEMENTOCF_FIELD,
								idElementoCuadro))
				.append(DBUtils.UNION)

				// No incluir unidades documentales en unidades de instalacion
				// bloqueadas
				.append(DBUtils.SELECT)
				.append(UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD)
				.append(DBUtils.FROM)
				.append(tablaUdocDeposito.getDeclaration())
				.append(Constants.COMMA)
				.append(tablaUiDeposito.getDeclaration())
				.append(DBUtils.WHERE)
				.append(DBUtils.generateJoinCondition(
						UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD,
						UInstalacionDepositoDBEntity.ID_FIELD))
				.append(DBUtils.AND)
				.append(DBUtils.generateJoinCondition(
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD,
						idElementoCuadro))
				.append(DBUtils.AND)
				.append(DBUtils.generateNEQTokenField(
						UInstalacionDepositoDBEntity.MARCAS_BLOQUEO_FIELD, 0));

		if (!StringUtils.isEmpty(subQueryIdsUdocConservar)) {
			// No incluir unidades descartadas por busqueda avanzada
			select.append(DBUtils.UNION).append(subQueryIdsUdocConservar);
		}

		return select.toString();
	}

	/**
	 * @param idSerie
	 * @param fechaEjecucion
	 * @param anosVigencia
	 * @param xmlCondicionesConservacion
	 * @param idArchivo
	 * @param subQueryIdsUdocConservar
	 * @param getUnidadesConservar
	 *            Indica si la consulta es para obtener las unidades a conservar
	 *            (true) o no (false)
	 * @return
	 */
	private String createQueryUdocsSeleccion(String idSerie,
			Date fechaEjecucion, int anosVigencia,
			String xmlCondicionesConservacion, String idArchivo,
			String subQueryIdsUdocConservar, boolean getUnidadesConservar) {

		ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo();
		String fechaFinal = csa.getConfiguracionDescripcion()
				.getFechaExtremaFinal();
		String fechaInicial = csa.getConfiguracionDescripcion()
				.getFechaExtremaInicial();

		// Calculo de la fecha limite
		Calendar fechaLimite = new GregorianCalendar();
		fechaLimite.setTime(fechaEjecucion);
		fechaLimite.add(Calendar.YEAR, (-1) * anosVigencia);

		// Definicion de tablas auxiliares
		TableDef tablaElementoCuadro1 = new TableDef(
				ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO,
				"a1");
		TableDef tablaFecha1 = new TableDef(FechaDBEntityImpl.TABLE_NAME, "f1");
		TableDef tablaFecha2 = new TableDef(FechaDBEntityImpl.TABLE_NAME, "f2");
		TableDef tablaUnidadDocumental = new TableDef(
				UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL,
				UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL);

		// Definicion de columnas auxiliares
		DbColumnDef idElementoCuadro1 = new AliasedColumnDef(
				ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD,
				tablaElementoCuadro1);
		DbColumnDef idArchivo1 = new AliasedColumnDef(
				ElementoCuadroClasificacionDBEntityImpl.ARCHIVO_FIELD,
				tablaElementoCuadro1);
		DbColumnDef idElementoCampoFecha1 = new AliasedColumnDef(
				FechaDBEntityImpl.CAMPO_ID_ELEMENTO, tablaFecha1);
		DbColumnDef idElementoCampoFecha2 = new AliasedColumnDef(
				FechaDBEntityImpl.CAMPO_ID_ELEMENTO, tablaFecha2);
		DbColumnDef idEliminacion1 = new AliasedColumnDef(
				ElementoCuadroClasificacionDBEntityImpl.ID_ELIMINACION_FIELD,
				tablaElementoCuadro1);
		DbColumnDef fechaFinal1 = new AliasedColumnDef(
				FechaDBEntityImpl.CAMPO_FECHA_FINAL, tablaFecha1);
		DbColumnDef fechaFinal2 = new AliasedColumnDef(
				FechaDBEntityImpl.CAMPO_FECHA_FINAL, tablaFecha2);

		// Tablas de la consulta
		StringBuffer strBufferTableNames = new StringBuffer();
		strBufferTableNames.append(tablaFecha1.getDeclaration())
				.append(Constants.COMMA)
				.append(tablaUnidadDocumental.getDeclaration())
				.append(Constants.COMMA)
				.append(tablaElementoCuadro1.getDeclaration());

		// Creacion del where
		StringBuffer where = new StringBuffer(DBUtils.WHERE);

		// Padre = Identificador de la serie
		where.append(DBUtils.generateEQTokenField(new AliasedColumnDef(
				ElementoCuadroClasificacionDBEntityImpl.IDPADRE_FIELD,
				tablaElementoCuadro1), idSerie));

		// Identificador de archivo
		if (idArchivo != null) {
			where.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(idArchivo1, idArchivo));
		}

		// Identificador de eliminación
		where.append(DBUtils.AND).append(
				DBUtils.generateEQTokenField(idEliminacion1, null));

		// Union con tabla de unidades documentales
		where.append(DBUtils.AND).append(
				DBUtils.generateJoinCondition(
						UnidadDocumentalDBEntityImpl.CAMPO_ID,
						idElementoCuadro1));

		// Para obtener solamente las unidades documentales que no esten
		// bloqueadas
		where.append(DBUtils.AND).append(
				DBUtils.generateEQTokenField(
						UnidadDocumentalDBEntityImpl.CAMPO_MARCAS_BLOQUEO, 0));

		// Subconsulta con las unidades que se expluyen o incluyen
		where.append(DBUtils.AND).append(idElementoCuadro1.getQualifiedName());

		if (getUnidadesConservar) {
			// Las unidades que se devuelven son a conservar
			where.append(DBUtils.NOT_IN);
		} else {
			// Las unidades que se devuelven son a eliminar
			where.append(DBUtils.IN);
		}

		where.append(DBUtils.ABRIR_PARENTESIS)
				.append(createSubQueryUdocsNotInEliminacion(idElementoCuadro1,
						subQueryIdsUdocConservar))
				.append(DBUtils.CERRAR_PARENTESIS);

		// Union con la tabla de fecha1
		where.append(DBUtils.AND).append(
				DBUtils.generateJoinCondition(idElementoCuadro1,
						idElementoCampoFecha1));

		// Restringir fechas al campo fecha final
		where.append(DBUtils.AND).append(
				DBUtils.generateEQTokenField(new AliasedColumnDef(
						FechaDBEntityImpl.CAMPO_ID_CAMPO, tablaFecha1),
						fechaFinal));

		// Restringir las unidades a las que tienen fecha menor o igual que la
		// del plazo de conservacion
		where.append(DBUtils.AND)
				.append(fechaFinal1.getQualifiedName())
				.append(DBUtils.MENOR_IGUAL)
				.append(DBUtils.getNativeToDateSyntax(getConnection(),
						fechaLimite.getTime(), CustomDateFormat.SDF_YYYYMMDD));

		// Si hay condiciones avanzadas incluirlas
		if (StringUtils.isNotBlank(xmlCondicionesConservacion)) {
			List condicionesConservacion = getCondicionesBusqueda(xmlCondicionesConservacion);

			// Aniadir la tabla de fecha2
			strBufferTableNames.append(Constants.COMMA).append(
					tablaFecha2.getDeclaration());

			// Aniadir la union con la tabla de elemento del cuadro
			where.append(DBUtils.AND).append(
					DBUtils.generateJoinCondition(idElementoCuadro1,
							idElementoCampoFecha2));

			// Restringir fecha inicial
			where.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(new AliasedColumnDef(
							FechaDBEntityImpl.CAMPO_ID_CAMPO, tablaFecha2),
							fechaInicial));

			// Aniadir las condiciones de conservacion
			for (Iterator i = condicionesConservacion.iterator(); i.hasNext();) {
				where.append(DBUtils.AND)
						.append(DBUtils.NOT)
						.append(DBUtils.ABRIR_PARENTESIS)
						.append(((CondicionConservacion) i.next())
								.toSQL(fechaFinal2.getQualifiedName()))
						.append(DBUtils.CERRAR_PARENTESIS);
			}
		}

		// Generar la consulta
		String result = DbSelectStatement.getSelectStmtText(
				strBufferTableNames.toString(),
				idElementoCuadro1.getQualifiedName(), where.toString(), false);

		return result;
	}

	/**
	 * Obtiene las eliminaciones que cumplen los filtros de busqueda
	 * 
	 * @param busqueda
	 *            Objeto que contiene los elementos del formulario de búsqueda.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getEliminaciones(BusquedaVO busqueda)
			throws TooManyResultsException {
		HashMap pairsTableNameColsDefs = new HashMap();
		pairsTableNameColsDefs.put(TABLE_NAME, COLS_DEFS);
		pairsTableNameColsDefs.put(SerieDBEntityImpl.TABLE_NAME_SERIE,
				new DbColumnDef[] {});
		pairsTableNameColsDefs.put(ValoracionDBEntityImpl.TABLE_NAME,
				new DbColumnDef[] { new DbColumnDef("tituloValoracion",
						ValoracionDBEntityImpl.CAMPO_TITULO) });
		pairsTableNameColsDefs
				.put(ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO,
						new DbColumnDef[] { new DbColumnDef(
								"tituloSerie",
								ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD) });

		String qual = getBusquedaWhereClause(busqueda);

		if (busqueda.getPageInfo() != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("codigo", CAMPO_TITULO);
			criteriosOrdenacion.put("tituloSerie",
					ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD);
			criteriosOrdenacion.put("codigoValoracion",
					ValoracionDBEntityImpl.CAMPO_TITULO);
			criteriosOrdenacion.put("estado", CAMPO_ESTADO);

			return getDistinctVOS(qual,
					busqueda.getPageInfo().getOrderBy(criteriosOrdenacion),
					pairsTableNameColsDefs, EliminacionSerieVO.class,
					busqueda.getPageInfo());
		} else {
			StringBuffer sbQual = new StringBuffer(qual).append(" ORDER BY ")
					.append(CAMPO_TITULO.getQualifiedName());

			return getDistinctVOS(sbQual.toString(), pairsTableNameColsDefs,
					EliminacionSerieVO.class);
		}
	}

	/**
	 * Obtiene la clásula WHERE para la búsqueda de las valoraciones.
	 * 
	 * @param busqueda
	 *            Información del formulario de búsqueda.
	 * @return Cláususla WHERE.
	 */
	private String getBusquedaWhereClause(BusquedaVO busqueda) {
		StringBuffer where = new StringBuffer(" WHERE ")
				.append(DBUtils.generateJoinCondition(CAMPO_ID_VALORACION,
						ValoracionDBEntityImpl.CAMPO_ID))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						SerieDBEntityImpl.CAMPO_ID,
						ValoracionDBEntityImpl.CAMPO_ID_SERIE))
				.append(" AND ")
				.append(DBUtils
						.generateJoinCondition(
								ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD,
								ValoracionDBEntityImpl.CAMPO_ID_SERIE));

		if (!GenericValidator.isBlankOrNull(busqueda.getTitulo())) {
			where.append(" AND ").append(
					DBUtils.generateLikeTokenField(CAMPO_TITULO, busqueda
							.getTitulo().toUpperCase(), true));
		}

		if (!GenericValidator.isBlankOrNull(busqueda.getCodigo())) {
			where.append(" AND ").append(
					DBUtils.generateLikeTokenField(
							SerieDBEntityImpl.CODIGO_FIELD, busqueda
									.getCodigo().toUpperCase()));
		}

		if (!GenericValidator.isBlankOrNull(busqueda.getFondo())) {
			where.append(" AND ").append(
					DBUtils.generateEQTokenField(
							SerieDBEntityImpl.CAMPO_IDFONDO,
							busqueda.getFondo()));

		}

		if (!GenericValidator.isBlankOrNull(busqueda.getTituloSerie())) {
			where.append(" AND ").append(
					DBUtils.generateContainsTokenField(getConnection(),
							SerieDBEntityImpl.TITULO_FIELD,
							SerieDBEntityImpl.IDXTITULO_FIELD, busqueda
									.getTituloSerie().toUpperCase()));
		}

		if (!GenericValidator.isBlankOrNull(busqueda.getTituloValoracion())) {
			where.append(" AND ").append(
					DBUtils.generateLikeTokenField(
							ValoracionDBEntityImpl.CAMPO_TITULO,
							busqueda.getTituloValoracion()));
		}

		if (busqueda.getEstados() != null && busqueda.getEstados().length > 0) {
			where.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_ESTADO,
							busqueda.getEstados()));
		}

		return where.toString();
	}

	class CondicionConservacion {
		String diaInicioRango = null;
		String mesInicioRango = null;
		String operardorInicial = null;

		String diaFinalRango = null;
		String mesFinalRango = null;
		String operadorFinal = null;

		void setInicioCondicion(String mes, String dia, String operador) {
			this.diaInicioRango = dia;
			this.mesInicioRango = mes;
			this.operardorInicial = operador;
		}

		void setFinalCondicion(String mes, String dia, String operador) {
			this.diaFinalRango = dia;
			this.mesFinalRango = mes;
			this.operadorFinal = operador;
		}

		String toSQL(String columnName) {
			StringBuffer sql = new StringBuffer()
					.append(DBUtils.getNativeToCharSyntax(getConnection(),
							columnName, DbUtil.TO_CHAR_PATTERN_MMDD))
					.append(operardorInicial)
					.append("'")
					.append(mesInicioRango)
					.append("/")
					.append(diaInicioRango)
					.append("/'")
					.append(" AND ")
					.append(DBUtils.getNativeToCharSyntax(getConnection(),
							columnName, DbUtil.TO_CHAR_PATTERN_MMDD))
					.append(operadorFinal).append("'").append(mesFinalRango)
					.append("/").append(diaFinalRango).append("/'");
			return sql.toString();

		}
	}

	private List getCondicionesBusqueda(String xmlCondiciones) {
		SAXReader saxReader = new SAXReader();
		StringBuffer xml = new StringBuffer(xmlCondiciones);

		Document udocInfo = null;
		try {
			udocInfo = saxReader.read(new StringReader(xml.toString()));
		} catch (DocumentException e) {
			logger.error("Se ha producido un error generando el reader para la consulta de la eliminacion "
					+ xmlCondiciones);
			throw new ArchivoModelException(e, "getConsultaEliminacion",
					"Error leyendo las condiciones de búsqueda de la eliminacion "
							+ xmlCondiciones);
		}

		List nCondiciones = udocInfo
				.selectNodes("/Criterios_Busqueda/Condiciones_Fechas_Extremas/Condicion");
		List condiciones = new ArrayList();
		CondicionConservacion condicionConservacion = null;
		for (Iterator iter = nCondiciones.iterator(); iter.hasNext();) {
			Node condicion = (Node) iter.next();
			condicionConservacion = new CondicionConservacion();
			Node nOperadorInicio = condicion
					.selectSingleNode("Fecha_Inicial/Operador");
			Node nValorInicioCondicion = condicion
					.selectSingleNode("Fecha_Inicial/Valor");

			String valorInicioCondicion = nValorInicioCondicion
					.getStringValue();
			if (StringUtils.isNotEmpty(valorInicioCondicion)) {

				StringOwnTokenizer st = new StringOwnTokenizer(
						valorInicioCondicion, "/");
				condicionConservacion.setInicioCondicion(st.nextToken(),
						st.nextToken(), nOperadorInicio.getStringValue());
			}

			Node nOperadorFinal = condicion
					.selectSingleNode("Fecha_Final/Operador");
			Node nValorFinalCondicion = condicion
					.selectSingleNode("Fecha_Final/Valor");
			String valorFinalCondicion = nValorFinalCondicion.getStringValue();
			if (StringUtils.isNotEmpty(valorFinalCondicion)) {
				StringOwnTokenizer st = new StringOwnTokenizer(
						valorFinalCondicion, "/");
				condicionConservacion.setFinalCondicion(st.nextToken(),
						st.nextToken(), nOperadorFinal.getStringValue());
			}
			condiciones.add(condicionConservacion);
		}
		return condiciones;
	}

	/**
	 * Recupera las eliminaciones existentes sobre una serie que se encuentran
	 * en alguno de los estados indicados
	 * 
	 * @param idSerie
	 *            Identificador de serie
	 * @param estadosEliminacion
	 *            Conjundo de posibles estados de eliminación. Puede ser nulo
	 * @return Lista de eliminaciones {@link EliminacionSerieVO}
	 */
	public List getEliminacionesSerie(String idSerie, int[] estadosEliminacion) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID_SERIE, idSerie));
		if (estadosEliminacion != null)
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_ESTADO,
							estadosEliminacion));
		qual.append(" ORDER BY ").append(FECHAESTADO_COLUMN_NAME);

		return getVOS(qual.toString(), TABLE_NAME, COLS_DEFS,
				EliminacionSerieVO.class);
	}

	/**
	 * Actualiza el valor del campo estado
	 * 
	 * @param idEliminacion
	 *            Identificador de eliminación
	 * @param estado
	 *            Valor a establacer en el campo estado
	 */
	public void updateEstado(String idEliminacion, int estado) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, idEliminacion));
		Map colsToUpdate = new HashMap(2);
		colsToUpdate.put(CAMPO_ESTADO, String.valueOf(estado));
		colsToUpdate.put(CAMPO_FECHAESTADO, DateUtils.getFechaActual());

		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	public int getCountEliminacionesConUdocsSeleccionadas(
			TablaTemporalFondosVO tablaTemporalFondosVO, int[] estadosExcluir) {

		/*
		 * SELECT COUNT(1) FROM ASGFELIMSERIE WHERE ESTADO NOT IN(8) AND ID IN(
		 * ( SELECT IDELIMINACION FROM ASGFELIMSELUDOC WHERE IDUDOC IN( SELECT
		 * IDELEMENTOCF FROM ASGFTMP1 ) UNION SELECT IDELIMINACION FROM
		 * ASGFELIMUDOCCONS WHERE IDUDOC IN( SELECT IDELEMENTOCF FROM ASGFTMP1 )
		 * ) )
		 */

		String subquerytablatemporal = TablaTemporalDBEntityImpl
				.getSelectIdElementocfFromTemporal(tablaTemporalFondosVO);

		StringBuffer subquery = new StringBuffer(DBUtils.ABRIR_PARENTESIS)

				.append(DBUtils.SELECT)
				.append(EliminacionUDOCEliminadaDBEntityImpl.IDELIMINACION_FIELD)
				.append(DBUtils.FROM)
				.append(EliminacionUDOCEliminadaDBEntityImpl.TABLE_NAME)
				.append(DBUtils.WHERE)
				.append(DBUtils.generateInTokenFieldSubQuery(
						EliminacionUDOCEliminadaDBEntityImpl.IDUDOC_FIELD,
						subquerytablatemporal))

				.append(DBUtils.UNION)

				.append(DBUtils.SELECT)
				.append(EliminacionUDOCConservadaDBEntityImpl.CAMPO_IDELIMINACION)
				.append(DBUtils.FROM)
				.append(EliminacionUDOCConservadaDBEntityImpl.TABLE_NAME)
				.append(DBUtils.WHERE)
				.append(DBUtils.generateInTokenFieldSubQuery(
						EliminacionUDOCConservadaDBEntityImpl.CAMPO_IDUDOC,
						subquerytablatemporal))

				.append(DBUtils.CERRAR_PARENTESIS);

		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateInTokenFieldSubQuery(CAMPO_ID, subquery.toString()));

		if (estadosExcluir != null && estadosExcluir.length > 0) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateNotInTokenField(CAMPO_ESTADO,
							estadosExcluir));
		}

		return getVOCount(qual.toString(), TABLE_NAME);
	}

}