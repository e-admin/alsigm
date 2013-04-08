package valoracion.db;

import fondos.db.SerieDBEntityImpl;
import fondos.db.oracle.ElementoCuadroClasificacionDBEntityImpl;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;

import valoracion.vos.BusquedaVO;
import valoracion.vos.SerieSeleccionableVO;
import valoracion.vos.ValoracionSerieVO;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.db.ConstraintType;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.exceptions.TooManyResultsException;
import common.pagination.PageInfo;
import common.util.ArrayUtils;
import common.util.StringUtils;

import descripcion.db.FechaDBEntityImpl;

/**
 * Clase que trabaja con trabaja contra la base de datos sobre la tabla de
 * Valoracion
 */
public class ValoracionDBEntityImpl extends DBEntity implements
		IValoracionDBEntity {

	static Logger logger = Logger.getLogger(ValoracionDBEntityImpl.class);

	public static final String TABLE_NAME = "ASGFVALSERIE";

	private static final String ID_COLUMN_NAME = "ID";
	private static final String ID_SERIE_COLUMN_NAME = "IDSERIE";
	private static final String TITULO_COLUMN_NAME = "TITULO";
	private static final String ESTADO_COLUMN_NAME = "ESTADO";
	private static final String FECHAESTADO_COLUMN_NAME = "FECHAESTADO";
	private static final String MOTIVORECHAZO_COLUMN_NAME = "MOTIVORECHAZO";
	private static final String ORDSERIEPRIMERNIVEL_COLUMN_NAME = "ORDSERIEPRIMERNIVEL";
	private static final String ORDSERIESEGNIVEL_COLUMN_NAME = "ORDSERIESEGNIVEL";
	private static final String SERIESPRECEDENTES_COLUMN_NAME = "SERIESPRECEDENTES";
	private static final String SERIESRELACIONADAS_COLUMN_NAME = "SERIESRELACIONADAS";
	private static final String DOCSRECOPILATORIOS_COLUMN_NAME = "DOCSRECOPILATORIOS";
	private static final String VALORADMTIPO_COLUMN_NAME = "VALORADMTIPO";
	private static final String VALORADMVIG_COLUMN_NAME = "VALORADMVIG";
	private static final String VALORADMJUST_COLUMN_NAME = "VALORADMJUST";
	private static final String VALORLEGALTIPO_COLUMN_NAME = "VALORLEGALTIPO";
	private static final String VALORLEGALVIG_COLUMN_NAME = "VALORLEGALVIG";
	private static final String VALORLEGALJUST_COLUMN_NAME = "VALORLEGALJUST";
	private static final String VALORINFTIPO_COLUMN_NAME = "VALORINFTIPO";
	private static final String VALORINFJUST_COLUMN_NAME = "VALORINFJUST";
	private static final String VALORDICTJUST_COLUMN_NAME = "VALORDICTJUST";
	private static final String VALORCIENTIFICOTIPO_COLUMN_NAME = "VALORCIENTTIPO";
	private static final String VALORCIENTIFICO_COLUMN_NAME = "VALORCIENTJUST";
	private static final String VALORTECNOLOGICOTIPO_COLUMN_NAME = "VALORTECNTIPO";
	private static final String VALORTECNOLOGICO_COLUMN_NAME = "VALORTECNJUST";
	private static final String VALORCULTURALTIPO_COLUMN_NAME = "VALORCULTTIPO";
	private static final String VALORCULTURAL_COLUMN_NAME = "VALORCULTJUST";
	// private static final String VALORHISTTIPO_COLUMN_NAME = "VALORHISTTIPO";
	// private static final String VALORHISTJUST_COLUMN_NAME = "VALORHISTJUST";
	private static final String TECNICAMUESTREO_COLUMN_NAME = "TECNICAMUESTREO";
	private static final String NUMREGISTRO_COLUMN_NAME = "NUMREGISTRO";
	private static final String FVALIDACION_COLUMN_NAME = "FVALIDACION";
	private static final String FEVALUACION_COLUMN_NAME = "FEVALUACION";
	private static final String FDICTAMEN_COLUMN_NAME = "FDICTAMEN";
	private static final String VALORDICTAMEN_COLUMN_NAME = "VALORDICTAMEN";
	private static final String FRESOLDICTAMEN_COLUMN_NAME = "FRESOLDICTAMEN";
	private static final String BOLETINDICTAMEN_COLUMN_NAME = "BOLETINDICTAMEN";
	private static final String FBOLETINDICTAMEN_COLUMN_NAME = "FBOLETINDICTAMEN";
	private static final String IDUSRGESTORSERIE_COLUMN_NAME = "IDUSRGESTORSERIE";
	private static final String REGIMENACCESOTIPO_COLUMN_NAME = "REGIMENACCESOTIPO";
	private static final String REGIMENACCESOJUST_COLUMN_NAME = "REGIMENACCESOJUST";
	private static final String REGIMENACCESOVIG_COLUMN_NAME = "REGIMENACCESOVIG";
	private static final String INFOSERIE_COLUMN_NAME = "INFOSERIE";
	private static final String REGIMENACCESOSUBTIPO_COLUMN_NAME = "REGIMENACCESOSUBTIPO";

	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_ID_SERIE = new DbColumnDef("IdSerie",
			TABLE_NAME, ID_SERIE_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_TITULO = new DbColumnDef(null,
			TABLE_NAME, TITULO_COLUMN_NAME, DbDataType.SHORT_TEXT, 254, false);
	public static final DbColumnDef CAMPO_ESTADO = new DbColumnDef(null,
			TABLE_NAME, ESTADO_COLUMN_NAME, DbDataType.SHORT_INTEGER, false);
	public static final DbColumnDef CAMPO_FECHAESTADO = new DbColumnDef(
			"FechaEstado", TABLE_NAME, FECHAESTADO_COLUMN_NAME,
			DbDataType.DATE_TIME, false);
	public static final DbColumnDef CAMPO_MOTIVORECHAZO = new DbColumnDef(
			"MotivoRechazo", TABLE_NAME, MOTIVORECHAZO_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 254, true);
	public static final DbColumnDef CAMPO_ORDSERIEPRIMERNIVEL = new DbColumnDef(
			"OrdenacionSerie1", TABLE_NAME, ORDSERIEPRIMERNIVEL_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, true);
	public static final DbColumnDef CAMPO_ORDSERIESEGNIVEL = new DbColumnDef(
			"OrdenacionSerie2", TABLE_NAME, ORDSERIESEGNIVEL_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, true);
	public static final DbColumnDef CAMPO_SERIESPRECEDENTES = new DbColumnDef(
			"SeriesPrecedentes", TABLE_NAME, SERIESPRECEDENTES_COLUMN_NAME,
			DbDataType.LONG_TEXT, 100 * 1024, true);
	public static final DbColumnDef CAMPO_SERIESRELACIONADAS = new DbColumnDef(
			"SeriesRelacionadas", TABLE_NAME, SERIESRELACIONADAS_COLUMN_NAME,
			DbDataType.LONG_TEXT, 100 * 1024, true);
	public static final DbColumnDef CAMPO_DOCSRECOPILATORIOS = new DbColumnDef(
			"DocumentosRecopilatorios", TABLE_NAME,
			DOCSRECOPILATORIOS_COLUMN_NAME, DbDataType.SHORT_TEXT, 1024, true);
	public static final DbColumnDef CAMPO_VALORADMTIPO = new DbColumnDef(
			"TipoValorAdministrativo", TABLE_NAME, VALORADMTIPO_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, true);
	public static final DbColumnDef CAMPO_VALORADMVIG = new DbColumnDef(
			"AnosVigenciaAdministrativa", TABLE_NAME, VALORADMVIG_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, true);
	public static final DbColumnDef CAMPO_VALORADMJUST = new DbColumnDef(
			"ValorAdministrativo", TABLE_NAME, VALORADMJUST_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 1024, true);
	public static final DbColumnDef CAMPO_VALORLEGALTIPO = new DbColumnDef(
			"TipoValorLegal", TABLE_NAME, VALORLEGALTIPO_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, true);
	public static final DbColumnDef CAMPO_VALORLEGALVIG = new DbColumnDef(
			"AnosVigenciaLegal", TABLE_NAME, VALORLEGALVIG_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, true);
	public static final DbColumnDef CAMPO_VALORLEGALJUST = new DbColumnDef(
			"ValorLegal", TABLE_NAME, VALORLEGALJUST_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 1024, true);
	public static final DbColumnDef CAMPO_VALORINFTIPO = new DbColumnDef(
			"TipoValorInformativo", TABLE_NAME, VALORINFTIPO_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, true);
	public static final DbColumnDef CAMPO_VALORINFJUST = new DbColumnDef(
			"ValorInformativo", TABLE_NAME, VALORINFJUST_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 1024, true);
	public static final DbColumnDef CAMPO_VALORDICTJUST = new DbColumnDef(
			"ValorDictamenValue", TABLE_NAME, VALORDICTJUST_COLUMN_NAME,
			DbDataType.LONG_TEXT, 0, true);
	public static final DbColumnDef CAMPO_VALORCIENTIFICOTIPO = new DbColumnDef(
			"TipoValorCientifico", TABLE_NAME, VALORCIENTIFICOTIPO_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, true);
	public static final DbColumnDef CAMPO_VALORCIENTIFICO = new DbColumnDef(
			"ValorCientifico", TABLE_NAME, VALORCIENTIFICO_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 1024, true);
	public static final DbColumnDef CAMPO_VALORTECNOLOGICOTIPO = new DbColumnDef(
			"TipoValorTecnologico", TABLE_NAME,
			VALORTECNOLOGICOTIPO_COLUMN_NAME, DbDataType.SHORT_INTEGER, true);
	public static final DbColumnDef CAMPO_VALORTECNOLOGICO = new DbColumnDef(
			"ValorTecnologico", TABLE_NAME, VALORTECNOLOGICO_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 1024, true);
	public static final DbColumnDef CAMPO_VALORCULTURALTIPO = new DbColumnDef(
			"TipoValorCultural", TABLE_NAME, VALORCULTURALTIPO_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, true);
	public static final DbColumnDef CAMPO_VALORCULTURAL = new DbColumnDef(
			"ValorCultural", TABLE_NAME, VALORCULTURAL_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 1024, true);
	// public static final DbColumnDef CAMPO_VALORHISTTIPO = new
	// DbColumnDef("TipoValorHistorico", TABLE_NAME, VALORHISTTIPO_COLUMN_NAME,
	// DbDataType.SHORT_INTEGER, true);
	// public static final DbColumnDef CAMPO_VALORHISTJUST = new
	// DbColumnDef("ValorHistorico", TABLE_NAME, VALORHISTJUST_COLUMN_NAME,
	// DbDataType.SHORT_TEXT, 254, true);
	public static final DbColumnDef CAMPO_TECNICAMUESTREO = new DbColumnDef(
			"TecnicaMuestreo", TABLE_NAME, TECNICAMUESTREO_COLUMN_NAME,
			DbDataType.LONG_INTEGER, true);
	public static final DbColumnDef CAMPO_NUMREGISTRO = new DbColumnDef(
			"NumRegistro", TABLE_NAME, NUMREGISTRO_COLUMN_NAME,
			DbDataType.LONG_INTEGER, true);
	public static final DbColumnDef CAMPO_FVALIDACION = new DbColumnDef(
			"FechaValidacion", TABLE_NAME, FVALIDACION_COLUMN_NAME,
			DbDataType.DATE_TIME, true);
	public static final DbColumnDef CAMPO_FEVALUACION = new DbColumnDef(
			"FechaEvaluacion", TABLE_NAME, FEVALUACION_COLUMN_NAME,
			DbDataType.DATE_TIME, true);
	public static final DbColumnDef CAMPO_FDICTAMEN = new DbColumnDef(
			"FechaDictamen", TABLE_NAME, FDICTAMEN_COLUMN_NAME,
			DbDataType.DATE_TIME, true);
	public static final DbColumnDef CAMPO_VALORDICTAMEN = new DbColumnDef(
			"ValorDictamen", TABLE_NAME, VALORDICTAMEN_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, true);
	public static final DbColumnDef CAMPO_FRESOLDICTAMEN = new DbColumnDef(
			"FechaResolucionDictamen", TABLE_NAME, FRESOLDICTAMEN_COLUMN_NAME,
			DbDataType.DATE_TIME, true);
	public static final DbColumnDef CAMPO_BOLETINDICTAMEN = new DbColumnDef(
			"BoletinDictamen", TABLE_NAME, BOLETINDICTAMEN_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 128, true);
	public static final DbColumnDef CAMPO_FBOLETINDICTAMEN = new DbColumnDef(
			"FechaBoletinDictamen", TABLE_NAME, FBOLETINDICTAMEN_COLUMN_NAME,
			DbDataType.DATE_TIME, true);
	public static final DbColumnDef CAMPO_IDUSRGESTORSERIE = new DbColumnDef(
			"IdUsuarioGestor", TABLE_NAME, IDUSRGESTORSERIE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, true);
	public static final DbColumnDef CAMPO_REGIMENACCESOTIPO = new DbColumnDef(
			"TipoRegimenAcceso", TABLE_NAME, REGIMENACCESOTIPO_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, true);
	public static final DbColumnDef CAMPO_REGIMENACCESOJUST = new DbColumnDef(
			"RegimenAcceso", TABLE_NAME, REGIMENACCESOJUST_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 1024, true);
	public static final DbColumnDef CAMPO_REGIMENACCESOVIG = new DbColumnDef(
			"AnosRegimenAcceso", TABLE_NAME, REGIMENACCESOVIG_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, true);
	public static final DbColumnDef CAMPO_INFOSERIE = new DbColumnDef(
			"InfoSerie", TABLE_NAME, INFOSERIE_COLUMN_NAME,
			DbDataType.LONG_TEXT, 0, true);
	public static final DbColumnDef CAMPO_REGIMENACCESOSUBTIPO = new DbColumnDef(
			"TipoRegimenAccesoTemporal", TABLE_NAME,
			REGIMENACCESOSUBTIPO_COLUMN_NAME, DbDataType.SHORT_INTEGER, true);

	public static final DbColumnDef[] COLS_DEFS = { CAMPO_ID, CAMPO_ID_SERIE,
			CAMPO_TITULO, CAMPO_ESTADO, CAMPO_FECHAESTADO, CAMPO_MOTIVORECHAZO,
			CAMPO_ORDSERIEPRIMERNIVEL, CAMPO_ORDSERIESEGNIVEL,
			CAMPO_SERIESPRECEDENTES, CAMPO_SERIESRELACIONADAS,
			CAMPO_DOCSRECOPILATORIOS, CAMPO_VALORADMTIPO, CAMPO_VALORADMVIG,
			CAMPO_VALORADMJUST, CAMPO_VALORLEGALTIPO, CAMPO_VALORLEGALVIG,
			CAMPO_VALORLEGALJUST, CAMPO_VALORINFTIPO, CAMPO_VALORINFJUST,
			CAMPO_VALORDICTJUST, CAMPO_VALORCIENTIFICO,
			CAMPO_VALORCIENTIFICOTIPO, CAMPO_VALORTECNOLOGICO,
			CAMPO_VALORTECNOLOGICOTIPO, CAMPO_VALORCULTURAL,
			CAMPO_VALORCULTURALTIPO, CAMPO_TECNICAMUESTREO, CAMPO_NUMREGISTRO,
			CAMPO_FVALIDACION, CAMPO_FEVALUACION, CAMPO_FDICTAMEN,
			CAMPO_VALORDICTAMEN, CAMPO_FRESOLDICTAMEN, CAMPO_BOLETINDICTAMEN,
			CAMPO_FBOLETINDICTAMEN, CAMPO_IDUSRGESTORSERIE,
			CAMPO_REGIMENACCESOTIPO, CAMPO_REGIMENACCESOJUST,
			CAMPO_REGIMENACCESOVIG, CAMPO_INFOSERIE, CAMPO_REGIMENACCESOSUBTIPO };

	public static final String COLUMN_NAMES = DbUtil.getColumnNames(COLS_DEFS);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public ValoracionDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public ValoracionDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * valoracion.db.IValoracionDBEntity#getCountValoracionesXGestor(java.lang
	 * .String, int[])
	 */
	public int getCountValoracionesXGestor(String idGestor, int[] estados) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_IDUSRGESTORSERIE, idGestor));

		if (estados != null)
			qual.append(" AND ").append(
					DBUtils.generateORTokens(CAMPO_ESTADO, estados));

		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * valoracion.db.IValoracionDBEntity#getValoracionesXGestor(java.lang.String
	 * , int[])
	 */
	public List getValoracionesXGestor(String idGestor, int[] estados) {
		DbColumnDef tituloSerie = new DbColumnDef("tituloSerie",
				ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD);
		DbColumnDef[] columnas = (DbColumnDef[]) ArrayUtils.concat(COLS_DEFS,
				new DbColumnDef[] { tituloSerie });

		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_IDUSRGESTORSERIE, idGestor));

		if (estados != null)
			qual.append(" AND ").append(
					DBUtils.generateORTokens(CAMPO_ESTADO, estados));

		qual.append(" AND ")
				.append(DBUtils
						.generateJoinCondition(
								ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD,
								CAMPO_ID_SERIE));

		String[] tablas = {
				ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO,
				TABLE_NAME };
		return getVOS(qual.toString(), ArrayUtils.join(tablas, ","), columnas,
				ValoracionSerieVO.class);
	}

	/**
	 * Obtiene una valoración de una serie a partir de su identificador en la
	 * base de datos.
	 * 
	 * @param codigo
	 *            Identificador de la valoración en la base de datos
	 * @return Objeto {@link ValoracionSerieVO} con los detalles de la
	 *         valoración.
	 */
	public ValoracionSerieVO getValoracion(String codigo) {
		String qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateTokenFieldQualified(CAMPO_ID, codigo,
						TABLE_NAME, ConstraintType.EQUAL)).toString();

		return (ValoracionSerieVO) getVO(qual, TABLE_NAME, COLS_DEFS,
				ValoracionSerieVO.class);
	}

	/**
	 * Obtiene un listado de las valoraciones de una serie dada por su
	 * identificador y cuyo estado de valoracion se encuentra en unos de los
	 * pasados, o todas las valoraciones de la series en caso de no venir
	 * definidos los estados.
	 * 
	 * @param codigoSerie
	 *            Serie de la que deseamos obtener las valoraciones. Puede ser
	 *            null
	 * @param estados
	 *            Listado de los estado que deben tener las valoraciones o null
	 *            si deseamos obtener todas
	 * @return Listado de valoraciones de la serie
	 */
	public List getValoracionesSerie(String codigoSerie, int[] estados) {
		StringBuffer qual = new StringBuffer();
		if (codigoSerie != null)
			qual.append(DBUtils.generateEQTokenField(CAMPO_ID_SERIE,
					codigoSerie));

		if (estados != null && estados.length > 0) {
			if (qual.length() > 0)
				qual.append(" AND ");
			qual.append(DBUtils.generateInTokenField(CAMPO_ESTADO, estados));
		}
		qual.append(" ORDER BY ").append(FECHAESTADO_COLUMN_NAME)
				.append(" DESC");

		qual.insert(0, "WHERE ");
		return getVOS(qual.toString(), TABLE_NAME, COLS_DEFS,
				ValoracionSerieVO.class);
	}

	public List getIDsValoracionSerie(String idSerie, int[] estados) {
		StringBuffer qual = new StringBuffer();
		if (idSerie != null)
			qual.append(DBUtils.generateEQTokenField(CAMPO_ID_SERIE, idSerie));

		if (estados != null && estados.length > 0) {
			if (qual.length() > 0)
				qual.append(" AND ");
			qual.append(DBUtils.generateInTokenField(CAMPO_ESTADO, estados));
		}
		qual.insert(0, "WHERE ");

		return getFieldValues(qual.toString(), TABLE_NAME, CAMPO_ID);
	}

	/**
	 * Obtiene un listado de las valoraciones dadas por su identificador de bd.
	 * 
	 * @param ids
	 *            Lsitado de identificadores de las valoraciones que deseamos
	 *            recuperar.
	 * @return Valoraciones deseadas
	 */
	public List getValoraciones(String[] ids) {
		String qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateInTokenField(CAMPO_ID, ids)).toString();

		return getVOS(qual, TABLE_NAME, COLS_DEFS, ValoracionSerieVO.class);
	}

	/**
	 * Realiza la actualización de la valoracion dada en la base de datos
	 * 
	 * @param valoracion
	 *            Valoración que deseamos actualizar
	 */
	public void updateValoracionSerie(ValoracionSerieVO valoracion) {
		checkValoracion(valoracion);
		String qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateTokenFieldQualified(CAMPO_ID,
						valoracion.getId(), TABLE_NAME, ConstraintType.EQUAL))
				.toString();

		updateVO(qual, TABLE_NAME, COLS_DEFS, valoracion);
	}

	/**
	 * Inserta una valoracion en la base de datos creado por el usuario del
	 * servicio.
	 * 
	 * @param valoracion
	 *            Valoracion insertar en la base de datos.
	 */
	public void insertValoracion(final ValoracionSerieVO valoracion) {
		checkValoracion(valoracion);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				valoracion.setId(getGuid(valoracion.getId()));

				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLS_DEFS, valoracion);
				DbInsertFns.insert(conn, TABLE_NAME, COLUMN_NAMES, inputRecord);
			}
		};

		command.execute();
	}

	/**
	 * Comprueba los valores ocultos de la valoración.
	 * 
	 * @param valoracion
	 *            Información de la valoración.
	 */
	private void checkValoracion(ValoracionSerieVO valoracion) {
		// Período Vigencia Administrativa
		if (valoracion.getTipoValorAdministrativo() == ValoracionSerieVO.VALOR_ADMINISTRATIVO_PERMANENTE)
			valoracion.setAnosVigenciaAdministrativa(0);

		// Período Vigencia Legal
		if (valoracion.getTipoValorLegal() == ValoracionSerieVO.VALOR_LEGAL_PERMANENTE)
			valoracion.setAnosVigenciaLegal(0);

		// Período Restricción
		if (valoracion.getTipoRegimenAcceso() != ValoracionSerieVO.REGIMEN_ACCESO_RESTRINGIDO_TEMPORAL)
			valoracion.setAnosRegimenAcceso(0);

		// Técnica de Muestreo
		if (valoracion.getValorDictamen() != ValoracionSerieVO.VALOR_DICTAMEN_CONSERVACION_PARCIAL)
			valoracion.setTecnicaMuestreo(0);
	}

	/**
	 * Realiza el borrado de una valoracion dado por su identificador.
	 * 
	 * @param idPrestamo
	 *            Identificador de la valoracion.
	 */
	public void deleteValoracion(String id) {
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
	 * Obtiene las valoraciones que cumplen los filtros de busqueda
	 * 
	 * @param busqueda
	 *            Objeto que contiene los elementos del formulario de búsqueda.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getValoraciones(BusquedaVO busqueda)
			throws TooManyResultsException {
		HashMap pairsTableNameColsDefs = new HashMap();
		DbColumnDef[] columns = { CAMPO_ID, CAMPO_ID_SERIE, CAMPO_TITULO,
				CAMPO_ESTADO, CAMPO_FECHAESTADO, CAMPO_MOTIVORECHAZO,
				CAMPO_ORDSERIEPRIMERNIVEL, CAMPO_ORDSERIESEGNIVEL,
				CAMPO_DOCSRECOPILATORIOS, CAMPO_VALORADMTIPO,
				CAMPO_VALORADMVIG, CAMPO_VALORADMJUST, CAMPO_VALORLEGALTIPO,
				CAMPO_VALORLEGALVIG, CAMPO_VALORLEGALJUST, CAMPO_VALORINFTIPO,
				CAMPO_VALORINFJUST, CAMPO_TECNICAMUESTREO, CAMPO_NUMREGISTRO,
				CAMPO_FVALIDACION, CAMPO_FEVALUACION, CAMPO_FDICTAMEN,
				CAMPO_VALORDICTAMEN, CAMPO_FRESOLDICTAMEN,
				CAMPO_BOLETINDICTAMEN, CAMPO_FBOLETINDICTAMEN,
				CAMPO_IDUSRGESTORSERIE };

		pairsTableNameColsDefs.put(TABLE_NAME, columns);
		pairsTableNameColsDefs.put(SerieDBEntityImpl.TABLE_NAME_SERIE,
				new DbColumnDef[] {});
		DbColumnDef tituloSerie = new DbColumnDef("tituloSerie",
				ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD);
		pairsTableNameColsDefs.put(
				ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO,
				new DbColumnDef[] { tituloSerie });

		String qual = getBusquedaWhereClause(busqueda);

		if (busqueda.getPageInfo() != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("codigo", CAMPO_TITULO);
			criteriosOrdenacion.put("tituloSerie",
					ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD);
			criteriosOrdenacion.put("estado", CAMPO_ESTADO);

			return getDistinctVOS(qual,
					busqueda.getPageInfo().getOrderBy(criteriosOrdenacion),
					pairsTableNameColsDefs, ValoracionSerieVO.class,
					busqueda.getPageInfo());
		} else {
			StringBuffer sbQual = new StringBuffer(qual).append(" ORDER BY ")
					.append(CAMPO_TITULO.getQualifiedName());

			return getDistinctVOS(sbQual.toString(), pairsTableNameColsDefs,
					ValoracionSerieVO.class);
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
				.append(DBUtils.generateJoinCondition(
						SerieDBEntityImpl.CAMPO_ID, CAMPO_ID_SERIE))
				.append(" AND ")
				.append(DBUtils
						.generateJoinCondition(
								ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD,
								CAMPO_ID_SERIE));

		if (!GenericValidator.isBlankOrNull(busqueda.getTituloValoracion())) {
			where.append(" AND ").append(
					DBUtils.generateLikeTokenField(CAMPO_TITULO,
							busqueda.getTituloValoracion()));
		}

		if (!GenericValidator.isBlankOrNull(busqueda.getCodigo())) {
			where.append(" AND ").append(
					DBUtils.generateLikeTokenField(
							SerieDBEntityImpl.CODIGO_FIELD,
							busqueda.getCodigo()));
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
							SerieDBEntityImpl.IDXTITULO_FIELD,
							busqueda.getTituloSerie()));
		}

		if (!ArrayUtils.isEmpty(busqueda.getEstados())) {
			where.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_ESTADO,
							busqueda.getEstados()));
		}

		if (StringUtils.isNotBlank(busqueda.getIdGestor())) {
			where.append(" AND ").append(
					DBUtils.generateEQTokenField(CAMPO_IDUSRGESTORSERIE,
							busqueda.getIdGestor()));
		}

		return where.toString();
	}

	/**
	 * Actualiza el gestor de un conjunto de valoraciones
	 * 
	 * @param valoraciones
	 *            Conjunto de identificadores de valoracion
	 * @param idGestor
	 *            Identificador de usuario
	 */
	public void updateGestorValoracion(String[] valoraciones, String idGestor) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateORTokens(CAMPO_ID, valoraciones));
		Map fieldsToUpdate = Collections.singletonMap(CAMPO_IDUSRGESTORSERIE,
				idGestor);
		updateFields(qual.toString(), fieldsToUpdate, TABLE_NAME);
	}

	/**
	 * Obtiene las series con un número mínimo de unidades documentales
	 * seleccionables.
	 * 
	 * @param idFondo
	 *            Identificador del fondo.
	 * @param minNumUdocs
	 *            Número mínimo de unidades documentales.
	 * @param pageInfo
	 *            Información de la paginación.
	 * @return Series seleccionables ({@link SerieSeleccionableVO}).
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getSeriesSeleccionables(String idFondo, int minNumUdocs,
			PageInfo pageInfo) throws TooManyResultsException {
		final String ID_CAMPO_FECHA_FINAL = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getFechaExtremaFinal();

		// Nombres de las columnas
		DbColumnDef[] colDefs = new DbColumnDef[] {
				new DbColumnDef(
						"id",
						"S",
						ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD),
				new DbColumnDef("codigo", "S",
						ElementoCuadroClasificacionDBEntityImpl.CODIGO_FIELD),
				new DbColumnDef("titulo", "S",
						ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD),
				new DbColumnDef("fondo", "F",
						ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD
								.getName() + " as fondo",
						DbDataType.SHORT_TEXT, false),
				/*
				 * new DbColumnDef("fondo", "F",
				 * ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD),
				 */
				new DbColumnDef(
						"numeroudocs",
						(String) null,
						"COUNT(U."
								+ ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_COLUMN_NAME_S
								+ ") AS NUMEROUDOCS", DbDataType.LONG_INTEGER,
						false) };

		// Nombres de las tablas
		StringBuffer tableNames = new StringBuffer()
				.append(TABLE_NAME + ",")
				.append(ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO
						+ " S,")
				.append(ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO
						+ " F,")
				.append(ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO
						+ " U,").append(FechaDBEntityImpl.TABLE_NAME);

		// Condiciones
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ESTADO,
						ValoracionSerieVO.ESTADO_DICTAMINADA))
				.append(" AND ")
				.append(DBUtils.generateNEQTokenField(CAMPO_VALORDICTAMEN,
						ValoracionSerieVO.VALOR_DICTAMEN_CONSERVACION_TOTAL))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_VALORADMTIPO,
						ValoracionSerieVO.VALOR_ADMINISTRATIVO_TEMPORAL))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_VALORLEGALTIPO,
						ValoracionSerieVO.VALOR_LEGAL_TEMPORAL))
				.append(" AND ")
				.append(CAMPO_ID_SERIE.getQualifiedName())
				.append("=S."
						+ ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_COLUMN_NAME_S)
				.append(" AND S."
						+ ElementoCuadroClasificacionDBEntityImpl.IDFONDO_COLUMN_NAME)
				.append("=F."
						+ ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_COLUMN_NAME_S);

		if (StringUtils.isNotBlank(idFondo))
			qual.append(" AND F.")
					.append(ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_COLUMN_NAME_S)
					.append("='").append(idFondo).append("'");
		// qual.append(" AND ")
		// .append(CAMPO_ID_SERIE.getQualifiedName())
		// .append("=U." +
		// ElementoCuadroClasificacionDBEntityImpl.IDPADRE_COLUMN_NAME)
		// .append(" AND U.")
		// .append(ElementoCuadroClasificacionDBEntityImpl.ID_ELIMINACION_COLUMN_NAME)
		// .append(" IS NULL AND U.")
		// .append(ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_COLUMN_NAME_S)
		// .append("=")
		// .append(FechaDBEntityImpl.CAMPO_ID_ELEMENTO.getQualifiedName())
		// .append(" AND ")
		// .append(DBUtils.generateEQTokenField(
		// FechaDBEntityImpl.CAMPO_ID_CAMPO, ID_CAMPO_FECHA_FINAL))
		// .append(" AND ")
		// .append(FechaDBEntityImpl.CAMPO_FECHA_FINAL.getQualifiedName())
		// .append("<TO_DATE(TO_CHAR(").append(DBUtils.getNativeSysDateSyntax(getConnection())).append(", 'DD/MM/')||TO_CHAR(TO_NUMBER(TO_CHAR(SYSDATE, 'YYYY')) - GREATEST(")
		// .append(CAMPO_VALORADMVIG.getQualifiedName())
		// .append(",")
		// .append(CAMPO_VALORLEGALVIG.getQualifiedName())
		// .append(")), 'DD/MM/YYYY')");

		qual.append(" AND ")
				.append(CAMPO_ID_SERIE.getQualifiedName())
				.append("=U."
						+ ElementoCuadroClasificacionDBEntityImpl.IDPADRE_COLUMN_NAME)
				.append(" AND U.")
				.append(ElementoCuadroClasificacionDBEntityImpl.ID_ELIMINACION_COLUMN_NAME)
				.append(" IS NULL AND U.")
				.append(ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_COLUMN_NAME_S)
				.append("=")
				.append(FechaDBEntityImpl.CAMPO_ID_ELEMENTO.getQualifiedName())
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						FechaDBEntityImpl.CAMPO_ID_CAMPO, ID_CAMPO_FECHA_FINAL))
				.append(" AND ")
				.append(FechaDBEntityImpl.CAMPO_FECHA_FINAL.getQualifiedName());

		String greatest = DBUtils.getNativeGreatestSyntax(getConnection(),
				CAMPO_VALORADMVIG.getQualifiedName(),
				CAMPO_VALORLEGALVIG.getQualifiedName());

		qual.append("<").append(
				DBUtils.getNativeAddYearSyntax(getConnection(), DBUtils
						.getNativeDateTimeSyntax(getConnection(), new Date(),
								true), "-" + greatest));

		qual.append(" GROUP BY S.")
				.append(ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_COLUMN_NAME_S)
				.append(",S.")
				.append(ElementoCuadroClasificacionDBEntityImpl.CODIGO_COLUMN_NAME)
				.append(",S.")
				.append(ElementoCuadroClasificacionDBEntityImpl.TITULO_COLUMN_NAME)
				.append(",F.")
				.append(ElementoCuadroClasificacionDBEntityImpl.TITULO_COLUMN_NAME);

		if (minNumUdocs > 0)
			qual.append(" HAVING COUNT(U.")
					.append(ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_COLUMN_NAME_S)
					.append(")>=").append(minNumUdocs);

		if (pageInfo != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion
					.put("codigo",
							new StringBuffer()
									.append("S.")
									.append(ElementoCuadroClasificacionDBEntityImpl.CODIGO_COLUMN_NAME)
									.toString());
			criteriosOrdenacion
					.put("titulo",
							new StringBuffer()
									.append("S.")
									.append(ElementoCuadroClasificacionDBEntityImpl.TITULO_COLUMN_NAME)
									.toString());
			criteriosOrdenacion
					.put("fondo",
							new StringBuffer()
									.append("F.")
									.append(ElementoCuadroClasificacionDBEntityImpl.TITULO_COLUMN_NAME)
									.toString());
			criteriosOrdenacion.put("numeroUdocs", "NUMEROUDOCS");

			return getVOS(qual.toString(),
					pageInfo.getOrderBy(criteriosOrdenacion),
					tableNames.toString(), colDefs, SerieSeleccionableVO.class,
					pageInfo);
		} else {
			qual.append(" ORDER BY S.").append(
					ElementoCuadroClasificacionDBEntityImpl.CODIGO_COLUMN_NAME);

			return getVOS(qual.toString(), tableNames.toString(), colDefs,
					SerieSeleccionableVO.class);
		}
	}

	public ValoracionSerieVO getValoracionMasReciente(String idSerie,
			int[] estados) {
		StringBuffer qual = new StringBuffer();
		if (idSerie != null)
			qual.append(DBUtils.generateEQTokenField(CAMPO_ID_SERIE, idSerie));

		if (estados != null && estados.length > 0) {
			if (qual.length() > 0)
				qual.append(" AND ");
			qual.append(DBUtils.generateInTokenField(CAMPO_ESTADO, estados));
		}
		qual.append(" ORDER BY ").append(FECHAESTADO_COLUMN_NAME)
				.append(" DESC");

		qual.insert(0, "WHERE ");
		return (ValoracionSerieVO) getVO(qual.toString(), TABLE_NAME,
				COLS_DEFS, ValoracionSerieVO.class);
	}

	public int getNumValoraciones(String idSerie, int[] estados) {
		StringBuffer qual = new StringBuffer();
		if (idSerie != null)
			qual.append(DBUtils.generateEQTokenField(CAMPO_ID_SERIE, idSerie));

		if (estados != null && estados.length > 0) {
			if (qual.length() > 0)
				qual.append(" AND ");
			qual.append(DBUtils.generateInTokenField(CAMPO_ESTADO, estados));
		}

		qual.insert(0, "WHERE ");

		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see valoracion.db.IValoracionDBEntity#getCountValoraciones(int[])
	 */
	public int getCountValoraciones(int[] estados) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateInTokenField(CAMPO_ESTADO, estados));
		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see valoracion.db.IValoracionDBEntity#getValoraciones(int[])
	 */
	public List getValoraciones(int[] estados) {
		// DbColumnDef codigoSerie = new DbColumnDef("codigoSerie",
		// ElementoCuadroClasificacionDBEntityImpl.CODIGO_FIELD);
		DbColumnDef tituloSerie = new DbColumnDef("tituloSerie",
				ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD);
		DbColumnDef[] columnas = (DbColumnDef[]) ArrayUtils.concat(COLS_DEFS,
				new DbColumnDef[] { tituloSerie });
		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateInTokenField(CAMPO_ESTADO, estados))
				.append(" AND ")
				.append(DBUtils
						.generateJoinCondition(
								ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD,
								CAMPO_ID_SERIE));
		String[] tablas = {
				ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO,
				TABLE_NAME };
		return getVOS(qual.toString(), ArrayUtils.join(tablas, ","), columnas,
				ValoracionSerieVO.class);
	}
}