package fondos.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;
import ieci.core.exception.IeciTdException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import se.usuarios.AppPermissions;
import se.usuarios.ServiceClient;
import valoracion.ValoracionConstants;
import valoracion.db.EliminacionSerieDBEntityImpl;
import valoracion.db.ValoracionDBEntityImpl;
import valoracion.vos.ValoracionSerieVO;
import xml.config.ConfiguracionDescripcion;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.db.ConstraintType;
import common.db.DBCommand;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.FieldConstraint;
import common.db.JoinDefinition;
import common.db.SigiaDbInputRecord;
import common.db.SigiaDbOutputRecord;
import common.db.SigiaDbOutputRecordset;
import common.db.TableDef;
import common.exceptions.DBException;
import common.exceptions.TooManyResultsException;
import common.lang.MutableInt;
import common.pagination.PageInfo;
import common.util.ArrayUtils;
import common.util.DateUtils;
import common.util.MapEntry;
import common.vos.ConsultaConnectBy;

import descripcion.db.DescriptorDBEntityImpl;
import descripcion.db.FechaDBEntityImpl;
import descripcion.db.ReferenciaDBEntityImpl;
import descripcion.db.TextoCortoDBEntityImpl;
import fondos.FondosConstants;
import fondos.db.oracle.ElementoCuadroClasificacionDBEntityImpl;
import fondos.model.ElementoCuadroClasificacion;
import fondos.model.IElementoCuadroClasificacion;
import fondos.model.Serie;
import fondos.model.UnidadDocumental;
import fondos.vos.BusquedaUdocsSerieVO;
import fondos.vos.SerieDocAntVO;
import fondos.vos.SerieVO;
import gcontrol.model.NivelAcceso;

/**
 * Implementación de los métodos de acceso a datos almacenados en la tabla en la
 * que se mantienen las series del cuadro de clasificación de fondos
 * documentales
 */
public class SerieDBEntityImpl extends ElementoCuadroClasificacionDBEntityImpl
		implements ISerieDbEntity {

	/** Logger de la clase */
	protected final static Logger logger = Logger
			.getLogger(SerieDBEntityImpl.class);

	public static final String TABLE_NAME_SERIE = "ASGFSERIE";

	public static final String IDELEMENTOCF_COLUMN_NAME = "IDELEMENTOCF";
	public static final String IDFONDO_COLUMN_NAME = "IDFONDO";
	public static final String ESTADO_COLUMN_NAME = "ESTADOSERIE";
	public static final String ULTIMOESTADOAUTORIZ_COLUMN_NAME = "ULTIMOESTADOAUTORIZ";
	public static final String FEXTREMAINICIAL_COLUMN_NAME = "FEXTREMAINICIAL";
	public static final String FEXTREMAFINAL_COLUMN_NAME = "FEXTREMAFINAL";
	public static final String NUMUNIDADDOC_COLUMN_NAME = "NUMUNIDADDOC";
	public static final String NUMUINSTALACION_COLUMN_NAME = "NUMUINSTALACION";
	public static final String IDENTIFICACION_COLUMN_NAME = "IDENTIFICACION";
	public static final String OBSERVACIONES_COLUMN_NAME = "OBSERVACIONES";
	public static final String FECHAESTADO_COLUMN_NAME = "FECHAESTADO";
	public static final String IDUSRGESTOR_COLUMN_NAME = "IDUSRGESTOR";
	public static final String IDPROCEDIMIENTO_COLUMN_NAME = "IDPROCEDIMIENTO";
	public static final String TIPOPROCEDIMIENTO_COLUMN_NAME = "TIPOPROCEDIMIENTO";
	public static final String VOLUMEN_COLUMN_NAME = "VOLUMEN";
	public static final String INFO_FICHA_UDOC_REP_ECM = "INFOFICHAUDOCREPECM";

	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME_SERIE, IDELEMENTOCF_COLUMN_NAME, DbDataType.SHORT_TEXT,
			32, false);

	public static final DbColumnDef CAMPO_IDFONDO = new DbColumnDef(null,
			TABLE_NAME_SERIE, IDFONDO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef CAMPO_ESTADO = new DbColumnDef(null,
			TABLE_NAME_SERIE, ESTADO_COLUMN_NAME, DbDataType.LONG_INTEGER, 1,
			false);

	public static final DbColumnDef CAMPO_FECHAESTADO = new DbColumnDef(null,
			TABLE_NAME_SERIE, FECHAESTADO_COLUMN_NAME, DbDataType.DATE_TIME,
			false);

	public static final DbColumnDef CAMPO_ULTIMOESTADOAUTORIZ = new DbColumnDef(
			null, TABLE_NAME_SERIE, ULTIMOESTADOAUTORIZ_COLUMN_NAME,
			DbDataType.LONG_INTEGER, 1, false);

	public static final DbColumnDef CAMPO_IDUSRGESTOR = new DbColumnDef(
			IDUSRGESTOR_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_FECHAINICIO = new DbColumnDef(null,
			TABLE_NAME_SERIE, FEXTREMAINICIAL_COLUMN_NAME,
			DbDataType.DATE_TIME, false);

	public static final DbColumnDef CAMPO_FECHAFIN = new DbColumnDef(null,
			TABLE_NAME_SERIE, FEXTREMAFINAL_COLUMN_NAME, DbDataType.DATE_TIME,
			false);

	public static final DbColumnDef CAMPO_NUMUNIDADDOC = new DbColumnDef(null,
			TABLE_NAME_SERIE, NUMUNIDADDOC_COLUMN_NAME,
			DbDataType.LONG_INTEGER, 1, false);

	public static final DbColumnDef CAMPO_NUMUINSTALACION = new DbColumnDef(
			null, TABLE_NAME_SERIE, NUMUINSTALACION_COLUMN_NAME,
			DbDataType.LONG_INTEGER, 1, false);

	public static final DbColumnDef CAMPO_IDENTIFICACION = new DbColumnDef(
			null, TABLE_NAME_SERIE, IDENTIFICACION_COLUMN_NAME,
			DbDataType.LONG_TEXT, 1000, false);

	public static final DbColumnDef CAMPO_OBSERVACIONES = new DbColumnDef(null,
			TABLE_NAME_SERIE, OBSERVACIONES_COLUMN_NAME, DbDataType.SHORT_TEXT,
			254, false);

	public static final DbColumnDef CAMPO_IDPROCEDIMIENTO = new DbColumnDef(
			null, TABLE_NAME_SERIE, IDPROCEDIMIENTO_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_TIPOPROCEDIMIENTO = new DbColumnDef(
			null, TABLE_NAME_SERIE, TIPOPROCEDIMIENTO_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, 1, false);

	public static final DbColumnDef CAMPO_VOLUMEN = new DbColumnDef(null,
			TABLE_NAME_SERIE, VOLUMEN_COLUMN_NAME, DbDataType.LONG_DECIMAL,
			false);

	public static final DbColumnDef CAMPO_INFO_FICHA_UDOC_REP_ECM = new DbColumnDef(
			null, TABLE_NAME_SERIE, INFO_FICHA_UDOC_REP_ECM,
			DbDataType.LONG_TEXT, 0, false);

	private static final DbColumnDef[] COL_DEFS_SERIE = new DbColumnDef[] {
			CAMPO_ID, CAMPO_IDFONDO, CAMPO_ESTADO, CAMPO_FECHAESTADO,
			CAMPO_ULTIMOESTADOAUTORIZ, CAMPO_IDUSRGESTOR, CAMPO_FECHAINICIO,
			CAMPO_FECHAFIN, CAMPO_NUMUNIDADDOC, CAMPO_NUMUINSTALACION,
			CAMPO_IDENTIFICACION, CAMPO_OBSERVACIONES, CAMPO_IDPROCEDIMIENTO,
			CAMPO_TIPOPROCEDIMIENTO, CAMPO_VOLUMEN,
			CAMPO_INFO_FICHA_UDOC_REP_ECM };

	public static final String ALL_COLUMN_NAMES = DbUtil
			.getColumnNames(COL_DEFS_SERIE);

	private static final String HINT_SELECT_SERIES_DOC_ANT = "/*+ USE_NL( "
			+ TextoCortoDBEntityImpl.TABLE_NAME + ")*/";

	/*
	 * (non-Javadoc)
	 * 
	 * @see fondos.db.ElementoCuadroClasificacionDBEntityImplBase#getTableName()
	 */
	@Override
	public String getTableName() {
		return TABLE_NAME_SERIE;
	}

	public SerieDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public SerieDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	public SerieVO getSerie(String idSerie) {

		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, idSerie))
				.append(" AND ")
				.append(DBUtils
						.generateJoinCondition(
								ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO,
								ID_ELEMENTO_FIELD, TABLE_NAME_SERIE, CAMPO_ID))
				.toString();
		HashMap map = new HashMap();
		map.put(TABLE_NAME_ELEMENTO, COLS_DEFS_ELEMENTO);
		map.put(TABLE_NAME_SERIE, COL_DEFS_SERIE);

		return (SerieVO) getVO(qual, map, Serie.class);
	}

	public List getSeries(String[] serieIDs) {
		StringBuffer qual = new StringBuffer("where").append(DBUtils
				.generateORTokens(CAMPO_ID, serieIDs));
		return selectSeries(qual.toString(), null);
	}

	public SerieVO insertSerie(SerieVO serie, String idElementoCF) {
		DbConnection conn = null;
		try {
			serie.setFechaestado(DateUtils.getFechaActual());
			conn = getConnection();
			serie.setIdelementocf(idElementoCF);
			DbInsertFns.insert(conn, TABLE_NAME_SERIE,
					DbUtil.getColumnNames(COL_DEFS_SERIE),
					new SigiaDbInputRecord(COL_DEFS_SERIE, serie));

			return serie;
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	public List getSeriesXFondoYEstados(String idFondo, int[] estados) {
		String qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDFONDO, idFondo))
				.append(" AND ")
				.append(DBUtils.generateORTokens(CAMPO_ESTADO, estados))
				.toString();

		return selectSeries(qual, null);
	}

	public List getSeriesXFondo(String idFondo) {
		String qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_IDFONDO, idFondo))
				.toString();

		return selectSeries(qual, null);
	}

	public void updateFieldsSerie(String idSerie, final Map columnsToUpdate) {
		String qual = new StringBuffer().append(" where ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, idSerie))
				.toString();

		updateFields(qual, columnsToUpdate, TABLE_NAME_SERIE);

	}

	public void updateEstadoSerie(String idSerie, final int estado,
			final int estadoPrevio) {
		String qual = new StringBuffer("WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_ID, idSerie)).toString();
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_ESTADO, String.valueOf(estado));
		colsToUpdate.put(CAMPO_ULTIMOESTADOAUTORIZ,
				String.valueOf(estadoPrevio));
		colsToUpdate.put(CAMPO_FECHAESTADO, DateUtils.getFechaActual());
		updateFields(qual.toString(), colsToUpdate, TABLE_NAME_SERIE);
	}

	public void setEstadoSerie(String idSerie, int estado, Date fechaEstado) {
		String qual = new StringBuffer("WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_ID, idSerie)).toString();
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_ESTADO, String.valueOf(estado));
		if (fechaEstado == null)
			fechaEstado = DateUtils.getFechaActual();
		colsToUpdate.put(CAMPO_FECHAESTADO, fechaEstado);
		updateFields(qual.toString(), colsToUpdate, TABLE_NAME_SERIE);
	}

	public void setEstadoSerie(String idSerie, int estado, int estadoPrevio,
			Date fechaEstado) {
		String qual = new StringBuffer("WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_ID, idSerie)).toString();
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_ESTADO, String.valueOf(estado));
		colsToUpdate.put(CAMPO_ULTIMOESTADOAUTORIZ,
				String.valueOf(estadoPrevio));
		if (fechaEstado == null)
			fechaEstado = DateUtils.getFechaActual();
		colsToUpdate.put(CAMPO_FECHAESTADO, fechaEstado);
		updateFields(qual.toString(), colsToUpdate, TABLE_NAME_SERIE);
	}

	/**
	 * Devuelve el sql para recuperar las series asignadas a un determinado
	 * gestor que se encuentren en alguno de los estados que se indican
	 * 
	 * @param idGestor
	 *            Identificador de usuario.
	 * @param estados
	 *            Lista de posibles estados de serie. Puede ser nulo.
	 * @return Lista de series {@link SerieVO}
	 */
	private String getSeriesXGestorSql(String idGestor, int[] estados) {
		StringBuffer qual = new StringBuffer(" WHERE 1=1 AND ").append(DBUtils
				.generateEQTokenField(CAMPO_IDUSRGESTOR, idGestor));
		if (estados != null)
			qual.append(" AND ").append(
					DBUtils.generateORTokens(CAMPO_ESTADO, estados));
		return qual.toString();
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see fondos.db.ISerieDbEntity#getCountSeriesXGestor(java.lang.String,
	 * int[])
	 */
	public int getCountSeriesXGestor(String idGestor, int[] estados) {
		String qual = getSeriesXGestorSql(idGestor, estados);
		return selectCountSeries(qual);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see fondos.db.ISerieDbEntity#getSeriesXGestor(java.lang.String, int[])
	 */
	public List getSeriesXGestor(String idGestor, int[] estados) {
		String qual = getSeriesXGestorSql(idGestor, estados);
		return selectSeries(qual, null);
	}

	public List getSerieXProcedimiento(String idProcedimiento) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDPROCEDIMIENTO,
						idProcedimiento)).toString();
		return selectSeries(qual, null);
	}

	public List getSerieXProcedimientos(String[] idProcedimiento) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateORTokens(CAMPO_IDPROCEDIMIENTO,
						idProcedimiento)).toString();
		return selectSeries(qual, null);
	}

	public SerieVO getSerieXCodigo(String codigoSerie, String idFondo) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CODIGO_FIELD, codigoSerie));
		if (idFondo != null)
			qual.append(" and ").append(
					DBUtils.generateEQTokenField(CAMPO_IDFONDO, idFondo));
		return selectSerie(qual.toString());
	}

	public List findSeries(List searchConditions) {
		StringBuffer qual = new StringBuffer("where ");
		FieldConstraint aConstraint = null;
		int nConstraints = searchConditions != null ? searchConditions.size()
				: 0;
		for (int i = 0; i < nConstraints; i++) {
			aConstraint = (FieldConstraint) searchConditions.get(i);
			if (i > 0)
				qual.append(" and ");
			if (aConstraint.getOperator() == ConstraintType.CONTAINS)
				qual.append(DBUtils.generateContainsTokenField(getConnection(),
						aConstraint.getColumn(), aConstraint.getIdxColumn(),
						aConstraint.getValue()));
			else
				qual.append(DBUtils.generateTokenField(aConstraint.getColumn(),
						aConstraint.getOperator(), aConstraint.getValue()));
		}
		return selectSeries(qual.toString(), null);
	}

	public List findSeriesInEstados(List searchConditions, int[] estados) {
		return findSeriesInEstados(searchConditions, estados, null, false);
	}

	public List findSeriesInEstados(List searchConditions, int[] estados,
			String[] idsSerie, boolean alwaysAddIdsSerie) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE);
		FieldConstraint aConstraint = null;
		int nConstraints = searchConditions != null ? searchConditions.size()
				: 0;
		for (int i = 0; i < nConstraints; i++) {
			aConstraint = (FieldConstraint) searchConditions.get(i);
			if (i > 0)
				qual.append(DBUtils.AND);
			if (aConstraint.getOperator() == ConstraintType.CONTAINS)
				qual.append(DBUtils.generateContainsTokenField(getConnection(),
						aConstraint.getColumn(), aConstraint.getIdxColumn(),
						aConstraint.getValue()));
			else
				qual.append(DBUtils.generateTokenField(aConstraint.getColumn(),
						aConstraint.getOperator(), aConstraint.getValue()));
		}
		if (estados != null && estados.length > 0) {
			if (searchConditions.size() > 0)
				qual.append(DBUtils.AND);
			qual.append(DBUtils.generateInTokenField(CAMPO_ESTADO, estados));
		}

		if (ArrayUtils.isNotEmpty(idsSerie)) {
			if (searchConditions.size() > 0)
				qual.append(DBUtils.AND);
			qual.append(DBUtils.generateInTokenField(CAMPO_ID, idsSerie));
		} else if (alwaysAddIdsSerie) {
			// Si la lista de ids de serie esta vacia y es necesario aniador los
			// ids esto quiere decir
			// que no se debe devolver ninguna serie
			return new ArrayList();
		}

		String order = DBUtils.generateOrderBy(TITULO_FIELD);
		// " ORDER BY " + TITULO_FIELD.getQualifiedName();

		return selectSeries(qual.toString(), order);
	}

	/**
	 * Obtiene la lista de series valorables.
	 * 
	 * @param searchConditions
	 *            Condiciones de la búsqueda.
	 * @param estadosSerie
	 *            Estados de la serie.
	 * @return Lista de series.
	 */
	public List findSeriesValorables(List searchConditions, int[] estadosSerie) {
		final int[] estadosValoracionNoValorable = {
				ValoracionSerieVO.ESTADO_ABIERTA,
				ValoracionSerieVO.ESTADO_PENDIENTE_DE_VALIDAR,
				ValoracionSerieVO.ESTADO_VALIDADA,
				ValoracionSerieVO.ESTADO_VALIDACION_RECHAZADA,
				ValoracionSerieVO.ESTADO_EVALUADA };

		StringBuffer qual = new StringBuffer("WHERE ");

		// Condiciones de la serie
		FieldConstraint aConstraint = null;
		int nConstraints = searchConditions != null ? searchConditions.size()
				: 0;
		for (int i = 0; i < nConstraints; i++) {
			aConstraint = (FieldConstraint) searchConditions.get(i);
			if (i > 0)
				qual.append(" and ");
			if (aConstraint.getOperator() == ConstraintType.CONTAINS)
				qual.append(DBUtils.generateContainsTokenField(getConnection(),
						aConstraint.getColumn(), aConstraint.getIdxColumn(),
						aConstraint.getValue()));
			else
				qual.append(DBUtils.generateTokenFieldQualified(
						aConstraint.getColumn(), aConstraint.getValue(), null,
						aConstraint.getOperator()));
		}

		// Estados de la serie
		if (searchConditions.size() > 0)
			qual.append(" AND ");
		qual.append(DBUtils.generateInTokenField(CAMPO_ESTADO, estadosSerie));

		// Sin valoración 'viva'
		qual.append(" AND ")
				.append(CAMPO_ID.getQualifiedName())
				.append(" NOT IN (SELECT ")
				.append(ValoracionDBEntityImpl.CAMPO_ID_SERIE
						.getQualifiedName())
				.append(" FROM ")
				.append(ValoracionDBEntityImpl.TABLE_NAME)
				.append(" WHERE ")
				.append(DBUtils.generateJoinCondition(
						ValoracionDBEntityImpl.CAMPO_ID_SERIE, CAMPO_ID))
				.append(" AND ")
				.append(DBUtils.generateInTokenField(
						ValoracionDBEntityImpl.CAMPO_ESTADO,
						estadosValoracionNoValorable)).append(")");

		return selectSeries(qual.toString(), null);
	}

	/**
	 * Obtiene la lista de series seleccionables.
	 * 
	 * @param searchConditions
	 *            Condiciones de la búsqueda.
	 * @param estadosSerie
	 *            Estados de la serie.
	 * @return Lista de series.
	 */
	public List findSeriesSeleccionables(List searchConditions,
			int[] estadosSerie) {
		final int[] estadosEliminacionNoSeleccionables = {
				ValoracionConstants.ESTADO_ELIMINACION_ABIERTA,
				ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_DE_APROBAR,
				ValoracionConstants.ESTADO_ELIMINACION_APROBADA,
				ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_DESTRUCCION,
				ValoracionConstants.ESTADO_ELIMINACION_DESTRUCCION_REALIZADA,
				ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_FINALIZACION };

		StringBuffer qual = new StringBuffer("WHERE ");

		// Condiciones de la serie
		FieldConstraint aConstraint = null;
		int nConstraints = searchConditions != null ? searchConditions.size()
				: 0;
		for (int i = 0; i < nConstraints; i++) {
			aConstraint = (FieldConstraint) searchConditions.get(i);
			if (i > 0)
				qual.append(" and ");
			if (aConstraint.getOperator() == ConstraintType.CONTAINS)
				qual.append(DBUtils.generateContainsTokenField(getConnection(),
						aConstraint.getColumn(), aConstraint.getIdxColumn(),
						aConstraint.getValue()));
			else
				qual.append(DBUtils.generateTokenFieldQualified(
						aConstraint.getColumn(), aConstraint.getValue(), null,
						aConstraint.getOperator()));
		}

		// Estados de la serie
		if (searchConditions.size() > 0)
			qual.append(" AND ");
		qual.append(DBUtils.generateInTokenField(CAMPO_ESTADO, estadosSerie));

		// Con valoración dictaminada y no "Conservación Total"
		qual.append(" AND ")
				.append(CAMPO_ID.getQualifiedName())
				.append(" IN (SELECT DISTINCT ")
				.append(ValoracionDBEntityImpl.CAMPO_ID_SERIE
						.getQualifiedName())
				.append(" FROM ")
				.append(ValoracionDBEntityImpl.TABLE_NAME)
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(
						ValoracionDBEntityImpl.CAMPO_ESTADO,
						ValoracionSerieVO.ESTADO_DICTAMINADA))
				.append(" AND ")
				.append(DBUtils.generateNEQTokenField(
						ValoracionDBEntityImpl.CAMPO_VALORDICTAMEN,
						ValoracionSerieVO.VALOR_DICTAMEN_CONSERVACION_TOTAL))
				.append(")");

		// Sin eliminaciones activas
		qual.append(" AND ")
				.append(CAMPO_ID.getQualifiedName())
				.append(" NOT IN (SELECT DISTINCT ")
				.append(EliminacionSerieDBEntityImpl.CAMPO_ID_SERIE
						.getQualifiedName())
				.append(" FROM ")
				.append(EliminacionSerieDBEntityImpl.TABLE_NAME)
				.append(" WHERE ")
				.append(DBUtils.generateInTokenField(
						EliminacionSerieDBEntityImpl.CAMPO_ESTADO,
						estadosEliminacionNoSeleccionables)).append(")");

		return selectSeries(qual.toString(), null);
	}

	protected SerieVO selectSerie(String qual) {
		StringBuffer whereClause = new StringBuffer(qual);
		if (whereClause.length() > 0)
			whereClause.append(" and ");
		whereClause
				.append("(")
				.append(DBUtils.generateJoinCondition(TABLE_NAME_ELEMENTO,
						ID_ELEMENTO_FIELD, TABLE_NAME_SERIE, CAMPO_ID))
				.append(")");
		final Serie serie = new Serie(null);
		try {
			DbColumnDef[] allColumns = (DbColumnDef[]) ArrayUtils.concat(
					COL_DEFS_SERIE, COLS_DEFS_ELEMENTO);
			SigiaDbOutputRecord row = new SigiaDbOutputRecord(serie, allColumns);

			StringBuffer columNames = new StringBuffer()
					.append(DBUtils.getQualifiedColumnNames(TABLE_NAME_SERIE,
							COL_DEFS_SERIE))
					.append(",")
					.append(DBUtils.getQualifiedColumnNames(
							TABLE_NAME_ELEMENTO, COLS_DEFS_ELEMENTO));

			String[] tableSet = { TABLE_NAME_SERIE, TABLE_NAME_ELEMENTO };
			try {
				DbSelectFns.select(getConnection(),
						DBUtils.generateTableSet(tableSet),
						columNames.toString(), whereClause.toString(), row);

			} catch (IeciTdException iecie) {
				return null;
			}
		} catch (Exception e) {
			throw new DBException(e);
		}
		return serie;

	}

	protected int selectCountSeries(String qual) {
		int rows = 0;
		try {
			try {
				rows = DbSelectFns.selectCount(getConnection(),
						TABLE_NAME_SERIE, CAMPO_ID.getQualifiedName(), qual,
						false);

			} catch (IeciTdException iecie) {
				return 0;
			}
		} catch (Exception e) {
			throw new DBException(e);
		}
		return rows;
	}

	protected List selectSeries(String qual, String orderBy) {
		SigiaDbOutputRecordset rows = null;
		try {
			StringBuffer whereClause = new StringBuffer(qual);
			if (whereClause.length() > 0)
				whereClause.append(" and ");
			whereClause
					.append("(")
					.append(DBUtils.generateJoinCondition(TABLE_NAME_ELEMENTO,
							ID_ELEMENTO_FIELD, TABLE_NAME_SERIE, CAMPO_ID))
					.append(") ");
			if (orderBy != null)
				whereClause.append(orderBy);
			DbColumnDef[] allColumns = (DbColumnDef[]) ArrayUtils.concat(
					COL_DEFS_SERIE, COLS_DEFS_ELEMENTO);
			rows = new SigiaDbOutputRecordset(allColumns, Serie.class);

			StringBuffer columNames = new StringBuffer()
					.append(DBUtils.getQualifiedColumnNames(TABLE_NAME_SERIE,
							COL_DEFS_SERIE))
					.append(",")
					.append(DBUtils.getQualifiedColumnNames(
							TABLE_NAME_ELEMENTO, COLS_DEFS_ELEMENTO));

			String[] tableSet = { TABLE_NAME_SERIE, TABLE_NAME_ELEMENTO };
			try {
				DbSelectFns.select(getConnection(),
						DBUtils.generateTableSet(tableSet),
						columNames.toString(), whereClause.toString(), false,
						rows);
			} catch (IeciTdException iecie) {
				return null;
			}
		} catch (Exception e) {
			throw new DBException(e);
		}
		return rows;
	}

	public void updateVolumenSerie(String idSerie, int numUdocs, int numUIs,
			double volumen) {
		Map colsToUpdate = new HashMap();

		colsToUpdate.put(CAMPO_NUMUNIDADDOC, String.valueOf(numUdocs));
		colsToUpdate.put(CAMPO_NUMUINSTALACION, String.valueOf(numUIs));
		colsToUpdate.put(CAMPO_VOLUMEN, new Double(volumen));

		String qual = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(CAMPO_ID, idSerie)).toString();

		updateFields(qual, colsToUpdate, TABLE_NAME_SERIE);
	}

	/**
	 * Elimina una serie de la tabla de series en la base de datos
	 * 
	 * @param idSerie
	 *            Identificador de la serie a eliminar
	 */
	public void removeSerie(String idSerie) {
		final String qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_ID, idSerie)).toString();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME_SERIE, qual);
			}
		};

		command.execute();
	}

	/**
	 * Actualiza en la base de datos los datos de identificacion de una serie
	 * 
	 * @param identificacion
	 *            Texto XML con la identificacion de la serie
	 * @param procedimiento
	 *            Procedimiento que ha sido vinculado a la serie
	 * @param tipoProcedimiento
	 *            Tipo de procedimiento (AUTOMATIZADO o NO AUTOMATIZADO)
	 */
	public void updateIdentificacion(String idSerie, String identificacion,
			String procedimiento, int tipoProcedimiento) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, idSerie));
		Map columnsToUpdate = new HashMap();
		columnsToUpdate.put(CAMPO_IDENTIFICACION, identificacion);
		columnsToUpdate.put(CAMPO_TIPOPROCEDIMIENTO,
				String.valueOf(tipoProcedimiento));
		columnsToUpdate.put(CAMPO_IDPROCEDIMIENTO, procedimiento);
		updateFields(qual.toString(), columnsToUpdate, TABLE_NAME_SERIE);
	}

	/**
	 * Actualiza la columna de la tabla de series en la que se especifica el
	 * gestor de la serie
	 * 
	 * @param idSerie
	 *            Identificador de la serie a actualizar
	 * @param idGestor
	 *            Identificador del usuario que se establece como gestor de la
	 *            serie
	 */
	public void updateGestor(String idSerie, String idGestor) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, idSerie));
		Map toUpdate = Collections.singletonMap(
				SerieDBEntityImpl.CAMPO_IDUSRGESTOR, idGestor);
		updateFields(qual.toString(), toUpdate, TABLE_NAME_SERIE);
	}

	/**
	 * Cuenta el número de series que contiene un fondo del cuadro de
	 * clasificación de fondos
	 * 
	 * @param idFondo
	 *            Identificador de fondo
	 * @return Número de series que contiene el fondo
	 */
	public int countSeriesEnFondo(final String idFondo) {
		final MutableInt numSeriesEnFondo = new MutableInt(0);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
						.generateEQTokenField(CAMPO_IDFONDO, idFondo));
				numSeriesEnFondo.setValue(DbSelectFns.selectCount(conn,
						TABLE_NAME_SERIE, qual.toString()));
			}
		};
		command.execute();
		return numSeriesEnFondo.getValue();
	}

	/**
	 * Actualiza los valores establecidos para las opciones de almacenamiento y
	 * descripción de una serie del cuadro de clasificacion de fondos
	 * documentales
	 * 
	 * @param Identificador
	 *            de serie del cuadro de clasificación de fondos documentales
	 * @param fichaDescripcionSerie
	 *            Identificador de la definición de ficha descriptiva a utilizar
	 *            para la descripción de la serie
	 * @param idRepEcmSerie
	 *            Identificador del repositorio ECM en el que se guardarán los
	 *            documentos anexados a la serie
	 * @param fichaDescripcionUdoc
	 *            Identificador de la definición de ficha descriptiva que se
	 *            empleará por defecto para la descripción de las unidades
	 *            documentales de la serie
	 */

	public void updateInfoSerie(String idSerie, String fichaDescripcionSerie,
			String idRepEcmSerie, String infoFichaUDocRepEcm) {
		updateInfoSerie(idSerie, fichaDescripcionSerie, idRepEcmSerie,
				infoFichaUDocRepEcm, null, 0);
	}

	public void updateInfoSerie(String idSerie, String fichaDescripcionSerie,
			String idRepositorioEcm, String infoFichaUDocRepEcm, String idLCA,
			int nivelAcceso) {

		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_ELEMENTO_FIELD, idSerie));

		Map columnsToUpdate = new HashMap();

		columnsToUpdate.put(IDFICHADESCR_FIELD, fichaDescripcionSerie);
		columnsToUpdate.put(LISTA_ACCESO_FIELD, idLCA);

		if (nivelAcceso > 0)
			columnsToUpdate.put(NIVEL_ACCESO_FIELD, new Integer(nivelAcceso));

		columnsToUpdate.put(REPOSITORIO_ECM_FIELD, idRepositorioEcm);

		updateFields(qual.toString(), columnsToUpdate, TABLE_NAME_ELEMENTO);

		columnsToUpdate.clear();
		qual.setLength(0);
		qual.append(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(CAMPO_ID, idSerie));
		columnsToUpdate.put(CAMPO_INFO_FICHA_UDOC_REP_ECM, infoFichaUDocRepEcm);

		updateFields(qual.toString(), columnsToUpdate, TABLE_NAME_SERIE);
	}

	/**
	 * Actualiza las fechas extremas entre las que está comprendida la
	 * documentación contenida en una serie del cuadro de clasificacion
	 * 
	 * @param idSerie
	 *            Identificador de serie
	 * @param fechaInicio
	 *            Fecha a establecer como fecha extrema inicial
	 * @param fechaFin
	 *            Fecha a establecer como fecha extrema final
	 */
	public void updateFechasExtremas(String idSerie, Date fechaInicio,
			Date fechaFin) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, idSerie));
		Map columnsToUpdate = new HashMap();
		columnsToUpdate.put(CAMPO_FECHAINICIO, fechaInicio);
		columnsToUpdate.put(CAMPO_FECHAFIN, fechaFin);
		updateFields(qual.toString(), columnsToUpdate, TABLE_NAME_SERIE);
	}

	/**
	 * Actualiza las fechas extremas entre las que está comprendida la
	 * documentación contenida en una serie del cuadro de clasificacion
	 * 
	 * @param idSerie
	 *            Identificador de serie
	 */
	// TODO ZMIGRACION BD - CORREGIDO ERROR EN POSTGRE, COLUMNAS CUALIFICADAS
	public void updateFechasExtremas(String idSerie) {
		ConfiguracionDescripcion cfgDesc = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion();

		DbConnection conn = null;

		try {
			conn = getConnection();

			final StringBuffer sql = new StringBuffer("UPDATE ")
					.append(TABLE_NAME_SERIE)
					.append(" SET ")
					// .append(CAMPO_FECHAINICIO)
					.append(DbUtil.getColNamesForInsertSentenceSyntax(conn,
							CAMPO_FECHAINICIO.toString()))
					.append("=(SELECT MIN(")
					.append(FechaDBEntityImpl.CAMPO_FECHA_INICIAL)
					.append(") FROM ")
					.append(UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL)
					.append(",")
					.append(FechaDBEntityImpl.TABLE_NAME)
					.append(" WHERE ")
					.append(DBUtils
							.generateEQTokenField(
									UnidadDocumentalDBEntityImpl.CAMPO_IDSERIE,
									idSerie))
					.append(" AND ")
					.append(DBUtils.generateJoinCondition(
							UnidadDocumentalDBEntityImpl.CAMPO_ID,
							FechaDBEntityImpl.CAMPO_ID_ELEMENTO))
					.append(" AND ")
					.append(DBUtils.generateEQTokenField(
							FechaDBEntityImpl.CAMPO_ID_CAMPO,
							cfgDesc.getFechaExtremaInicial()))
					.append(" AND ")
					.append(DBUtils.generateEQTokenField(
							FechaDBEntityImpl.CAMPO_ORDEN, 1))
					.append("),")
					// .append(CAMPO_FECHAFIN)
					.append(DbUtil.getColNamesForInsertSentenceSyntax(conn,
							CAMPO_FECHAFIN.toString()))
					.append("=(SELECT MAX(")
					.append(FechaDBEntityImpl.CAMPO_FECHA_FINAL)
					.append(") FROM ")
					.append(UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL)
					.append(",")
					.append(FechaDBEntityImpl.TABLE_NAME)
					.append(" WHERE ")
					.append(DBUtils
							.generateEQTokenField(
									UnidadDocumentalDBEntityImpl.CAMPO_IDSERIE,
									idSerie))
					.append(" AND ")
					.append(DBUtils.generateJoinCondition(
							UnidadDocumentalDBEntityImpl.CAMPO_ID,
							FechaDBEntityImpl.CAMPO_ID_ELEMENTO))
					.append(" AND ")
					.append(DBUtils.generateEQTokenField(
							FechaDBEntityImpl.CAMPO_ID_CAMPO,
							cfgDesc.getFechaExtremaFinal()))
					.append(" AND ")
					.append(DBUtils.generateEQTokenField(
							FechaDBEntityImpl.CAMPO_ORDEN, 1))
					.append(") WHERE ")
					.append(DBUtils.generateEQTokenField(CAMPO_ID, idSerie));

			DBCommand command = new DBCommand(this) {
				public void codeLogic(DbConnection conn) throws Exception {
					DbUpdateFns.update(conn, sql.toString());
				}
			};

			command.execute();
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	/**
	 * Obtiene las series que están vinculadas con los procedimientos que se
	 * indican
	 * 
	 * @param procedimientoIDs
	 *            Conjunto de códigos de procedimiento
	 * @return Conjunto de pares clave - valor en los que como clave aparece el
	 *         identificador de procedimientos y como clave el identificador de
	 *         la serie a la que está asociado
	 */
	public Map getSeriesAsociadas(String[] procedimientoIDs) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateInTokenField(CAMPO_IDPROCEDIMIENTO, procedimientoIDs));
		DbColumnDef[] columnSet = {
				new DbColumnDef("key", CAMPO_IDPROCEDIMIENTO),
				new DbColumnDef("value", CAMPO_ID) };
		List listaSeriesAsociadas = getVOS(qual.toString(), TABLE_NAME_SERIE,
				columnSet, MapEntry.class);
		Map seriesAsociadas = new HashMap();
		for (Iterator i = listaSeriesAsociadas.iterator(); i.hasNext();) {
			Map.Entry mapEntry = (Map.Entry) i.next();
			seriesAsociadas.put(mapEntry.getKey(), mapEntry.getValue());
		}
		return seriesAsociadas;
	}

	/**
	 * Permite obtener las unidades documentales de una serie
	 * 
	 * @param idPadre
	 *            Id de la serie
	 * @param estados
	 *            Estados de las unidades documentales
	 * @param pageInfo
	 *            información de paginación
	 * @param ignorarConservadas
	 *            indica si se ignoran o no las conservadas
	 * @return Lista de unidades documentales
	 * @throws TooManyResultsException
	 */
	public List getUdocsSerie(String idPadre, int[] estados, PageInfo pageInfo,
			boolean ignorarConservadas) throws TooManyResultsException {
		return getUdocsSerie(idPadre, estados, pageInfo, ignorarConservadas,
				true);
	}

	/**
	 * Permite obtener las unidades documentales de una serie con su nivel
	 * 
	 * @param idPadre
	 *            Id de la serie
	 * @param estados
	 *            Estados de las unidades documentales
	 * @param pageInfo
	 *            información de paginación
	 * @param ignorarConservadas
	 *            indica si se ignoran o no las conservadas
	 * @param conNivel
	 *            indica si se obtiene o no el nivel
	 * @return Lista de unidades documentales
	 * @throws TooManyResultsException
	 */
	public List getUdocsSerie(String idPadre, int[] estados, PageInfo pageInfo,
			boolean ignorarConservadas, boolean conNivel)
			throws TooManyResultsException {

		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(IDPADRE_FIELD, idPadre));

		if (estados != null)
			qual.append(" AND ").append(
					DBUtils.generateORTokens(ESTADO_ELEMENTO_FIELD, estados));
		// if (ignorarConservadas)
		// qual.append(" AND ")
		// .append(DBUtils.generateEQTokenField(ID_ELIMINACION_FIELD, null));
		if (conNivel)
			qual.append(" AND ").append(
					DBUtils.generateJoinCondition(NIVEL_FIELD,
							NivelCFDBEntityImpl.ID_NIVEL_FIELD));

		HashMap map = new HashMap();
		map.put(TABLE_NAME_ELEMENTO, COLS_DEFS_ELEMENTO);

		if (conNivel) {
			DbColumnDef columnDefNivel = new DbColumnDef("nombreNivel",
					NivelCFDBEntityImpl.NOMBRE_NIVEL_FIELD);
			map.put(NivelCFDBEntityImpl.NIVELCF_TABLE_NAME,
					new DbColumnDef[] { columnDefNivel });
		}

		// Orden
		if (pageInfo != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("codigo", CODIGO_FIELD);
			criteriosOrdenacion.put("titulo", TITULO_FIELD);
			criteriosOrdenacion.put("nombreNivel", NIVEL_FIELD);

			return getVOS(qual.toString(),
					pageInfo.getOrderBy(criteriosOrdenacion), map,
					ElementoCuadroClasificacion.class, pageInfo);
			/*
			 * return getVOS(qual.toString(),
			 * pageInfo.getOrderBy(criteriosOrdenacion), TABLE_NAME_ELEMENTO,
			 * COLS_DEFS_ELEMENTO, ElementoCuadroClasificacion.class, pageInfo);
			 */
		} else {
			qual.append(" ORDER BY ").append(CODIGO_FIELD.getQualifiedName());

			return getVOS(qual.toString(), map,
					ElementoCuadroClasificacion.class);
			/*
			 * return getVOS(qual.toString(), TABLE_NAME_ELEMENTO,
			 * COLS_DEFS_ELEMENTO, ElementoCuadroClasificacion.class);
			 */
		}
	}

	/**
	 * Permite obtener las unidades documentales de una serie con su nivel
	 * 
	 * @param idSerie
	 *            Id de la serie
	 * @param pageInfo
	 *            información de paginación
	 * @param idDescrProductor
	 *            identificador del productor por el que se busca
	 * @return Lista de unidades documentales
	 * @throws TooManyResultsException
	 */
	public List getUdocsSerieConFechasExtremas(ServiceClient serviceClient,
			String idSerie, PageInfo pageInfo, BusquedaUdocsSerieVO busquedaVO)
			throws TooManyResultsException {

		// TableDef tableF1 = new TableDef(FechaDBEntityImpl.TABLE_NAME, "f1");
		// TableDef tableF2 = new TableDef(FechaDBEntityImpl.TABLE_NAME, "f2");
		//
		// String tables = DBUtils.generateTableSet(new String[] {
		// TABLE_NAME_ELEMENTO,
		// tableF1.getDeclaration(),
		// tableF2.getDeclaration() });
		//
		// DbColumnDef colFechaIni = new DbColumnDef("fechaInicial",
		// tableF1.getAlias(), FechaDBEntityImpl.CAMPO_FECHA_INICIAL);
		// DbColumnDef colFechaFin = new DbColumnDef("fechaFinal",
		// tableF2.getAlias(), FechaDBEntityImpl.CAMPO_FECHA_FINAL);
		// DbColumnDef colIdCampoFechaIni = new DbColumnDef(tableF1,
		// FechaDBEntityImpl.CAMPO_ID_CAMPO);
		// DbColumnDef colIdCampoFechaFin = new DbColumnDef(tableF2,
		// FechaDBEntityImpl.CAMPO_ID_CAMPO);
		//
		// final ConfiguracionSistemaArchivo csa =
		// ConfiguracionSistemaArchivoFactory.getConfiguracionSistemaArchivo();
		// final String ID_CAMPO_FECHA_INICIAL =
		// csa.getConfiguracionDescripcion().getFechaExtremaInicial();
		// final String ID_CAMPO_FECHA_FINAL =
		// csa.getConfiguracionDescripcion().getFechaExtremaFinal();
		//
		//
		DbColumnDef columnaNumExp = new DbColumnDef("numExp", new TableDef(
				UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL,
				UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL),
				UnidadDocumentalDBEntityImpl.NUMERO_EXPEDIENTE_COLUMN_NAME,
				UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE
						.getDataType(), true);
		DbColumnDef columnDefNivel = new DbColumnDef("nombreNivel",
				NivelCFDBEntityImpl.NOMBRE_NIVEL_FIELD);

		DbColumnDef[] COLS_DEF = (DbColumnDef[]) ArrayUtils.concat(
				COLS_DEFS_ELEMENTO, new DbColumnDef[] {
						UnidadDocumentalDBEntityImpl.CAMPO_MARCAS_BLOQUEO,
						columnaNumExp, columnDefNivel });

		ConfiguracionDescripcion configDescripcion = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion();
		String idProductor = configDescripcion.getProductor();

		String[] tables = new String[] {
				new TableDef(
						ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO)
						.getDeclaration(),
				new TableDef(
						UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL)
						.getDeclaration(),
				new TableDef(NivelCFDBEntityImpl.NIVELCF_TABLE_NAME)
						.getDeclaration() };

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDPADRE_FIELD, idSerie))
				.append(DBUtils.AND)
				.append(DBUtils.generateJoinCondition(NIVEL_FIELD,
						NivelCFDBEntityImpl.ID_NIVEL_FIELD))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateJoinCondition(
								UnidadDocumentalDBEntityImpl.CAMPO_ID,
								ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(ESTADO_ELEMENTO_FIELD,
						IElementoCuadroClasificacion.VIGENTE));

		if (busquedaVO != null) {
			if (StringUtils.isNotEmpty(busquedaVO.getProductor())
					&& !FondosConstants.TODOS_PRODUCTORES.equals(busquedaVO
							.getProductor())) {
				if (FondosConstants.SIN_PRODUCTOR.equals(busquedaVO
						.getProductor())) {
					StringBuffer subconsulta = new StringBuffer();
					subconsulta
							.append(DBUtils.SELECT)
							.append(ID_ELEMENTO_FIELD)
							.append(DBUtils.FROM)
							.append(TABLE_NAME_ELEMENTO)
							.append(DBUtils.WHERE)
							.append(DBUtils.generateEQTokenField(IDPADRE_FIELD,
									idSerie));

					StringBuffer subquery = new StringBuffer();
					subquery.append(DBUtils.SELECT)
							.append(ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO)
							.append(DBUtils.FROM)
							.append(ReferenciaDBEntityImpl.TABLE_NAME)
							.append(DBUtils.WHERE)
							.append(DBUtils.generateEQTokenField(
									ReferenciaDBEntityImpl.CAMPO_ID_CAMPO,
									idProductor))
							.append(DBUtils.AND)
							.append(DBUtils.generateInTokenFieldSubQuery(
									ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO,
									subconsulta.toString()));

					qual.append(DBUtils.AND);
					qual.append(DBUtils.generateNotInTokenFieldSubQuery(
							ID_ELEMENTO_FIELD, subquery.toString()));
				} else {
					DbColumnDef columnaNombreProductor = new DbColumnDef(
							"nombreProductor", new TableDef(
									DescriptorDBEntityImpl.TABLE_NAME,
									DescriptorDBEntityImpl.TABLE_NAME),
							DescriptorDBEntityImpl.NOMBRE_COLUMN_NAME,
							DescriptorDBEntityImpl.CAMPO_NOMBRE.getDataType(),
							true);
					COLS_DEF = (DbColumnDef[]) ArrayUtils.concat(COLS_DEF,
							new DbColumnDef[] { columnaNombreProductor });
					tables = (String[]) ArrayUtils.concat(tables,
							new String[] { new TableDef(
									ReferenciaDBEntityImpl.TABLE_NAME)
									.getDeclaration() });
					tables = (String[]) ArrayUtils.concat(tables,
							new String[] { new TableDef(
									DescriptorDBEntityImpl.TABLE_NAME)
									.getDeclaration() });
					qual.append(DBUtils.AND)
							.append(DBUtils
									.generateJoinCondition(
											ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD,
											ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO));
					qual.append(DBUtils.AND).append(
							DBUtils.generateJoinCondition(
									ReferenciaDBEntityImpl.CAMPO_IDOBJREF,
									DescriptorDBEntityImpl.CAMPO_ID));
					qual.append(DBUtils.AND).append(
							DBUtils.generateEQTokenField(
									ReferenciaDBEntityImpl.CAMPO_IDOBJREF,
									busquedaVO.getProductor()));
				}
			}

			// Titulo
			if (StringUtils.isNotEmpty(busquedaVO.getTitulo())) {
				qual.append(DBUtils.AND).append(
						DBUtils.generateLikeTokenField(TITULO_FIELD,
								busquedaVO.getTitulo(), true));
			}

			// Codigo
			if (StringUtils.isNotEmpty(busquedaVO.getCodigo())) {
				qual.append(DBUtils.AND).append(
						DBUtils.generateLikeTokenField(CODIGO_FIELD,
								busquedaVO.getCodigo(), true));
			}

			// Numero Expediente
			if (StringUtils.isNotEmpty(busquedaVO.getNumeroExpediente())) {
				qual.append(DBUtils.AND).append(
						DBUtils.generateLikeTokenField(columnaNumExp,
								busquedaVO.getNumeroExpediente(), true));
			}

		}
		DBUtils.addPermissionsCheckingClausesWithPermissions(serviceClient,
				new StringBuffer(DBUtils.generateTableSet(tables)), qual,
				NIVEL_ACCESO_FIELD, ARCHIVO_FIELD, LISTA_ACCESO_FIELD,
				new String[] { AppPermissions.CONSULTA_CUADRO_CLASIFICACION,
						AppPermissions.MODIFICACION_CUADRO_CLASIFICACION });

		// Orden
		if (pageInfo != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("codigo", CODIGO_FIELD);
			criteriosOrdenacion.put("titulo", TITULO_FIELD);
			criteriosOrdenacion.put("nombreNivel", NIVEL_FIELD);
			return getDistinctVOS(qual.toString(),
					pageInfo.getOrderBy(criteriosOrdenacion),
					DBUtils.generateTableSet(tables), COLS_DEF,
					UnidadDocumental.class, pageInfo);
		} else {
			qual.append(DBUtils.generateOrderBy(CODIGO_FIELD));
			return getDistinctVOS(qual.toString(),
					DBUtils.generateTableSet(tables), COLS_DEF,
					UnidadDocumental.class);
		}
	}

	/**
	 * Recupera los identificadores de los procedimientos que han sido
	 * vinculados a alguna serie
	 * 
	 * @return Lista de identificadores de procedimiento
	 */
	public List getProcedimientosIdentificados() {
		return getFieldValues("", TABLE_NAME_SERIE, CAMPO_IDPROCEDIMIENTO);
	}

	/**
	 * Obtiene las series que contienen documentos antecedentes de un tercero.
	 * 
	 * @param idTercero
	 *            Identificador del tercero.
	 * @return Lista de series ({@link SerieDocAntVO}).
	 */
	public List getSeriesDocumentosAntecedentes(String idTercero) {
		final DbColumnDef[] CUSTOM_COLS = new DbColumnDef[] {
				new DbColumnDef(
						"id",
						"S",
						ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD),
				new DbColumnDef("titulo", "S",
						ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD),
				new DbColumnDef(
						"estado",
						"S",
						ElementoCuadroClasificacionDBEntityImpl.ESTADO_ELEMENTO_FIELD),
				new DbColumnDef("fondo", "F",
						ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD),
				new DbColumnDef(
						"numUdocs",
						(String) null,
						"COUNT(U."
								+ ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_COLUMN_NAME_S
								+ ")", DbDataType.LONG_INTEGER, false) };

		final String[] CUSTOM_TABLE_NAMES = new String[] {
				TextoCortoDBEntityImpl.TABLE_NAME,
				new StringBuffer(TABLE_NAME_ELEMENTO).append(" U").toString(),
				new StringBuffer(TABLE_NAME_ELEMENTO).append(" S").toString(),
				new StringBuffer(TABLE_NAME_ELEMENTO).append(" F").toString() };

		final StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(
						TextoCortoDBEntityImpl.CAMPO_ID_CAMPO,
						ConfiguracionSistemaArchivoFactory
								.getConfiguracionSistemaArchivo()
								.getConfiguracionDescripcion()
								.getIdTerceroInteresado()))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						TextoCortoDBEntityImpl.CAMPO_VALOR, idTercero))
				.append(" AND ")
				.append(DBUtils
						.generateJoinCondition(
								TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO,
								new DbColumnDef(
										null,
										"U",
										ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD)))
				.append(" AND ")
				.append(DBUtils
						.generateEQTokenField(
								new DbColumnDef(
										null,
										"U",
										ElementoCuadroClasificacionDBEntityImpl.NIVEL_ACCESO_FIELD),
								NivelAcceso.PUBLICO))
				.append(" AND ")
				.append(DBUtils
						.generateJoinCondition(
								new DbColumnDef(
										null,
										"U",
										ElementoCuadroClasificacionDBEntityImpl.IDPADRE_FIELD),
								new DbColumnDef(
										null,
										"S",
										ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD)))
				.append(" AND ")
				.append(DBUtils
						.generateJoinCondition(
								new DbColumnDef(
										null,
										"U",
										ElementoCuadroClasificacionDBEntityImpl.IDFONDO_FIELD),
								new DbColumnDef(
										null,
										"F",
										ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD)))
				.append(" GROUP BY S.")
				.append(ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_COLUMN_NAME_S)
				.append(",S.")
				.append(ElementoCuadroClasificacionDBEntityImpl.TITULO_COLUMN_NAME)
				.append(",S.")
				.append(ElementoCuadroClasificacionDBEntityImpl.ESTADO_ELEMENTO_COLUMN_NAME)
				.append(",F.")
				.append(ElementoCuadroClasificacionDBEntityImpl.TITULO_COLUMN_NAME)
				.append(" ORDER BY S.")
				.append(ElementoCuadroClasificacionDBEntityImpl.TITULO_COLUMN_NAME)
				// .append(",S.")
				.append(",F.")
				.append(ElementoCuadroClasificacionDBEntityImpl.TITULO_COLUMN_NAME);

		return getVOS(qual.toString(),
				ArrayUtils.join(CUSTOM_TABLE_NAMES, ","),
				HINT_SELECT_SERIES_DOC_ANT, CUSTOM_COLS, SerieDocAntVO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fondos.db.ISerieDbEntity#updateSerie(fondos.vos.SerieVO)
	 */
	public void updateSerie(SerieVO serie) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, serie.getId()));

		updateVO(qual.toString(), TABLE_NAME_SERIE, COL_DEFS_SERIE, serie);
	}

	public void updateSeriesFondo(List ids) {
		// da igual los ids que lleguen en idsUdocs, la consulta solo se va a
		// quedar con aquellos que sean
		// series.

		// UPDATE ASGFSERIE
		// SET IDFONDO=(SELECT IDFONDO FROM ASGFELEMENTOCF WHERE
		// ASGFSERIE.IDELEMENTOCF=ASGFELEMENTOCF.ID)
		// WHERE IDELEMENTOCF IN ('0a3e123e714290000000000000000008')

		StringBuffer sqlUpdate = new StringBuffer("UPDATE ")
				.append(TABLE_NAME_SERIE + " ");

		sqlUpdate
				.append("SET ")
				.append(CAMPO_IDFONDO.getName())
				.append(" = ")
				.append(" (SELECT ")
				.append(IDFONDO_FIELD.getQualifiedName())
				.append(" ")
				.append(" FROM " + TABLE_NAME_ELEMENTO)
				.append(" WHERE " + CAMPO_ID.getQualifiedName() + "="
						+ ID_ELEMENTO_FIELD.getQualifiedName() + ") ")
				.append(" WHERE ")
				.append(DBUtils.generateInTokenField(CAMPO_ID, ids));

		try {
			DbUpdateFns.update(getConnection(), sqlUpdate.toString());
		} catch (Exception e) {
			logger.debug(e);
		}
	}

	public Date getFechaInicialExtremaUdocsSerie(String idSerie) {
		// select min(advcfechacf.fechaini)
		// from asgfelementocf asgfelementocf, advcfechacf advcfechacf
		// where asgfelementocf.idpadre='0a4dac4b32a33000000000000000005d'
		// and asgfelementocf.id=advcfechacf.idelementocf
		// and advcfechacf.idcampo in (3)

		String tables = ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO
				+ " "
				+ ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO
				+ ","
				+ FechaDBEntityImpl.TABLE_NAME
				+ " "
				+ FechaDBEntityImpl.TABLE_NAME;

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils
						.generateJoinCondition(
								ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO,
								ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD,
								FechaDBEntityImpl.TABLE_NAME,
								FechaDBEntityImpl.CAMPO_ID_ELEMENTO))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(
								ElementoCuadroClasificacionDBEntityImplBase.IDPADRE_FIELD,
								idSerie))
				.append(DBUtils.AND)
				.append(DBUtils.generateInTokenField(
						FechaDBEntityImpl.CAMPO_ID_CAMPO, new String[] { "3" }));

		return getMinDate(qual.toString(), tables,
				FechaDBEntityImpl.CAMPO_FECHA_INICIAL);
	}

	public Date getFechaFinalExtremaUdocsSerie(String idSerie) {
		// select max(advcfechacf.fechafin)
		// from asgfelementocf asgfelementocf, advcfechacf advcfechacf
		// where asgfelementocf.idpadre='0a4dac4b32a33000000000000000005d'
		// and asgfelementocf.id=advcfechacf.idelementocf
		// and advcfechacf.idcampo in (4)

		String tables = ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO
				+ " "
				+ ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO
				+ ","
				+ FechaDBEntityImpl.TABLE_NAME
				+ " "
				+ FechaDBEntityImpl.TABLE_NAME;

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils
						.generateJoinCondition(
								ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO,
								ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD,
								FechaDBEntityImpl.TABLE_NAME,
								FechaDBEntityImpl.CAMPO_ID_ELEMENTO))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(
								ElementoCuadroClasificacionDBEntityImplBase.IDPADRE_FIELD,
								idSerie))
				.append(DBUtils.AND)
				.append(DBUtils.generateInTokenField(
						FechaDBEntityImpl.CAMPO_ID_CAMPO, new String[] { "4" }));

		return getMaxDate(qual.toString(), tables,
				FechaDBEntityImpl.CAMPO_FECHA_FINAL);
	}

	/**
	 * Permite obtener los productores de las unidades documentales de una serie
	 * 
	 * @param idPadre
	 *            Id de la serie
	 * @return Lista de unidades documentales
	 * @throws TooManyResultsException
	 */
	public List getProductoresUdocsSerie(ServiceClient serviceClient,
			String idSerie) throws TooManyResultsException {

		/*
		 * SELECT DISTINCT ADVCREFCF.idobjref, ADDESCRIPTOR.nombre FROM
		 * ASGFELEMENTOCF ASGFELEMENTOCF LEFT OUTER JOIN ADVCREFCF ADVCREFCF ON
		 * ASGFELEMENTOCF.id=ADVCREFCF.idelementocf AND ADVCREFCF.idcampo= '16'
		 * LEFT OUTER JOIN ADDESCRIPTOR ADDESCRIPTOR ON
		 * ADDESCRIPTOR.id=ADVCREFCF.idobjref WHERE ASGFELEMENTOCF.idpadre=
		 * 'idSerie' ORDER BY ADDESCRIPTOR.nombre ASC
		 */

		DbColumnDef columnaNombreProductor = new DbColumnDef("nombreProductor",
				new TableDef(DescriptorDBEntityImpl.TABLE_NAME,
						DescriptorDBEntityImpl.TABLE_NAME),
				DescriptorDBEntityImpl.NOMBRE_COLUMN_NAME,
				DescriptorDBEntityImpl.CAMPO_NOMBRE.getDataType(), true);

		DbColumnDef[] COLS_DEF = (DbColumnDef[]) new DbColumnDef[] {
				ReferenciaDBEntityImpl.CAMPO_IDOBJREF, columnaNombreProductor };

		ConfiguracionDescripcion cd = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion();
		String idProductor = cd.getProductor();

		StringBuffer sqljoinRef = new StringBuffer().append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						ReferenciaDBEntityImpl.CAMPO_ID_CAMPO, idProductor));
		JoinDefinition joinRef = new JoinDefinition(
				ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD,
				ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO, sqljoinRef.toString());

		JoinDefinition joinDescriptor = new JoinDefinition(
				ReferenciaDBEntityImpl.CAMPO_IDOBJREF,
				DescriptorDBEntityImpl.CAMPO_ID);

		StringBuffer fromSql = new StringBuffer()
				.append(DBUtils
						.generateLeftOuterJoinCondition(
								new TableDef(
										ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO),
								new JoinDefinition[] { joinRef, joinDescriptor }));

		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(IDPADRE_FIELD, idSerie));

		DBUtils.addPermissionsCheckingClausesWithPermissions(serviceClient,
				fromSql, qual, NIVEL_ACCESO_FIELD, ARCHIVO_FIELD,
				LISTA_ACCESO_FIELD, new String[] {
						AppPermissions.CONSULTA_CUADRO_CLASIFICACION,
						AppPermissions.MODIFICACION_CUADRO_CLASIFICACION });

		// Orden
		qual.append(DBUtils.ORDER_BY).append(
				columnaNombreProductor.getQualifiedName());
		return getDistinctVOS(qual.toString(), fromSql.toString(), COLS_DEF,
				ElementoCuadroClasificacion.class);
	}

	/**
	 * Permite obtener el numero de productores de las unidades documentales de
	 * una serie
	 * 
	 * @param idPadre
	 *            Id de la serie
	 * @return Número de unidades documentales
	 * @throws TooManyResultsException
	 */
	public int getCountProductoresUdocsSerie(ServiceClient serviceClient,
			String idSerie) {
		/*
		 * select count(*) from asgfelementocf asgfelementocf where
		 * idpadre='idSerie' and id not in (select idelementocf from advcrefcf
		 * where idcampo='16' and idelementocf in (select id from asgfelementocf
		 * asgfelementocf where idpadre='idSerie'))
		 */

		ConfiguracionDescripcion cd = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion();
		String idProductor = cd.getProductor();

		StringBuffer subquery = new StringBuffer(DBUtils.SELECT)
				.append(ID_ELEMENTO_FIELD).append(DBUtils.FROM)
				.append(TABLE_NAME_ELEMENTO).append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDPADRE_FIELD, idSerie));

		StringBuffer subconsulta = new StringBuffer(DBUtils.SELECT)
				.append(ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO)
				.append(DBUtils.FROM)
				.append(ReferenciaDBEntityImpl.TABLE_NAME)
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(
						ReferenciaDBEntityImpl.CAMPO_ID_CAMPO, idProductor))
				.append(DBUtils.AND)
				.append(DBUtils.generateInTokenFieldSubQuery(
						ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO,
						subquery.toString()));

		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(IDPADRE_FIELD, idSerie));
		qual.append(DBUtils.AND).append(
				DBUtils.generateNotInTokenFieldSubQuery(ID_ELEMENTO_FIELD,
						subconsulta.toString()));

		return getVOCount(qual.toString(), TABLE_NAME_ELEMENTO);
	}

	public int getCountUdocsSerieByProductor(String idSerie, String idProductor) {

		/*
		 * SELECT COUNT(1) FROM FROM ASGFELEMENTOCF ASGFELEMENTOCF LEFT OUTER
		 * JOIN ADVCREFCF ADVCREFCF ON ASGFELEMENTOCF.id=ADVCREFCF.idelementocf
		 * AND ADVCREFCF.idcampo= '16' WHERE ASGFELEMENTOCF.idpadre= 'idSerie'
		 * AND ADVCREFCF.idobjref = 'idProductor'
		 */

		ConfiguracionDescripcion cd = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion();
		String campoIdProductor = cd.getProductor();

		StringBuffer sqljoinRef = new StringBuffer().append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(
								ReferenciaDBEntityImpl.CAMPO_ID_CAMPO,
								campoIdProductor));
		JoinDefinition joinRef = new JoinDefinition(
				ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD,
				ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO, sqljoinRef.toString());

		StringBuffer fromSql = new StringBuffer()
				.append(DBUtils
						.generateLeftOuterJoinCondition(
								new TableDef(
										ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO),
								new JoinDefinition[] { joinRef }));

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDPADRE_FIELD, idSerie))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						ReferenciaDBEntityImpl.CAMPO_IDOBJREF, idProductor));

		return getVOCount(qual.toString(), fromSql.toString());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.db.ISerieDbEntity#getUdocsSerieByIdsElementos(java.lang.String[])
	 */
	public List getUdocsSerieByIdsElementos(String idSerie,
			String[] idsElementos, boolean conservadas) {

		DbColumnDef columnaNumExp = new DbColumnDef("numExp", new TableDef(
				UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL,
				UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL),
				UnidadDocumentalDBEntityImpl.NUMERO_EXPEDIENTE_COLUMN_NAME,
				UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE
						.getDataType(), true);
		DbColumnDef columnDefNivel = new DbColumnDef("nombreNivel",
				NivelCFDBEntityImpl.NOMBRE_NIVEL_FIELD);
		DbColumnDef columnaNombreProductor = new DbColumnDef("nombreProductor",
				new TableDef(DescriptorDBEntityImpl.TABLE_NAME,
						DescriptorDBEntityImpl.TABLE_NAME),
				DescriptorDBEntityImpl.NOMBRE_COLUMN_NAME,
				DescriptorDBEntityImpl.CAMPO_NOMBRE.getDataType(), true);

		final DbColumnDef[] COLS_BUSQUEDA = (DbColumnDef[]) ArrayUtils
				.addAll(COLS_DEFS_ELEMENTO, new DbColumnDef[] {
						UnidadDocumentalDBEntityImpl.CAMPO_MARCAS_BLOQUEO,
						columnaNumExp, columnDefNivel, columnaNombreProductor });

		StringBuffer selectClause = new StringBuffer(DBUtils.SELECT)
				.append(DbUtil.getColumnNames(COLS_BUSQUEDA));

		// LEFT OUTER JOIN ASGFUNIDADDOC ASGFUNIDADDOC ON ASGFELEMENTOCF.ID =
		// ASGFUNIDADDOC.IDELEMENTOCF
		JoinDefinition joinUnidadDoc = new JoinDefinition(ID_ELEMENTO_FIELD,
				UnidadDocumentalDBEntityImpl.CAMPO_ID);

		// LEFT OUTER JOIN ASGFNIVELCF ASGFNIVELCF ON
		// ASGFELEMENTOCF.IDNIVEL=ASGFNIVELCF.ID
		JoinDefinition joinNivelcf = new JoinDefinition(NIVEL_FIELD,
				NivelCFDBEntityImpl.ID_NIVEL_FIELD);

		// LEFT OUTER JOIN ADVCREFCF ADVCREFCF ON
		// ASGFELEMENTOCF.ID=ADVCREFCF.IDELEMENTOCF
		JoinDefinition joinReferencia = new JoinDefinition(ID_ELEMENTO_FIELD,
				ReferenciaDBEntityImpl.CAMPO_ID_ELEMENTO);

		// LEFT OUTER JOIN ADDESCRIPTOR ADDESCRIPTOR ON
		// ADVCREFCF.IDOBJREF=ADDESCRIPTOR.ID
		JoinDefinition joinDescriptor = new JoinDefinition(
				ReferenciaDBEntityImpl.CAMPO_IDOBJREF,
				DescriptorDBEntityImpl.CAMPO_ID);

		StringBuffer fromClause = new StringBuffer(DBUtils.FROM)
				.append(DBUtils
						.generateLeftOuterJoinCondition(
								new TableDef(
										ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO),
								new JoinDefinition[] { joinUnidadDoc,
										joinNivelcf, joinReferencia,
										joinDescriptor, }));

		String idProductor = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getProductor();

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)

				.append(DBUtils.generateEQTokenField(ESTADO_ELEMENTO_FIELD,
						IElementoCuadroClasificacion.VIGENTE))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						ReferenciaDBEntityImpl.CAMPO_ID_CAMPO, idProductor));

		if (ArrayUtils.isNotEmptyOrBlank(idsElementos)) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateORTokens(ID_ELEMENTO_FIELD, idsElementos));
		}

		if (StringUtils.isNotEmpty(idSerie)) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(IDPADRE_FIELD, idSerie));
		}

		if (conservadas) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateIsNotNullCondition(getConnection(),
							ID_ELIMINACION_FIELD));
		}
		// Orden
		StringBuffer orderByClause = new StringBuffer().append(DBUtils
				.generateOrderBy(CODIGO_FIELD));

		final ConsultaConnectBy sql = new ConsultaConnectBy(
				selectClause.toString(), fromClause.toString(),
				qual.toString(), null, orderByClause.toString());
		return getVOS(COLS_BUSQUEDA, sql, UnidadDocumental.class);
	}
}
