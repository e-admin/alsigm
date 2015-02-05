package descripcion.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xml.config.ConfiguracionDescripcion;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.db.TableDef;
import common.exceptions.DBException;
import common.util.StringUtils;

import descripcion.vos.CampoFechaVO;
import descripcion.vos.FechaInicialFinalElementoCF;
import descripcion.vos.ValorCampoGenericoVOBase;
import fondos.db.UnidadDocumentalDBEntityImpl;

/**
 * DBEntity para acceder a las tablas de fechas.
 */
public abstract class FechaDBEntityImplBase extends DBEntity implements
		IFechaDBEntity {

	/** Nombre de columnas */
	public static final String IDCAMPO_COLUMN_NAME = "idcampo";
	public static final String VALOR_COLUMN_NAME = "valor";
	public static final String FECHA_INICIAL_COLUMN_NAME = "fechaini";
	public static final String FECHA_FINAL_COLUMN_NAME = "fechafin";
	public static final String FORMATO_COLUMN_NAME = "formato";
	public static final String SEP_COLUMN_NAME = "sep";
	public static final String CALIFICADOR_COLUMN_NAME = "calificador";
	public static final String ORDEN_COLUMN_NAME = "orden";
	public static final String IDOBJETO_COLUMN_ALIAS = "idobjeto";

	/** Métodos abstractos que los hijos deben implementar */
	public abstract String getTableName();

	public abstract DbColumnDef getCampoIdElemento();

	public abstract DbColumnDef getCampoIdCampo();

	public abstract DbColumnDef getCampoOrden();

	public abstract DbColumnDef getCampoTipoElemento();

	public abstract DbColumnDef[] getColDefs();

	public abstract String getHint();

	public abstract String getColumnNamesList();

	/**
	 * Constructor.
	 * 
	 * @param dataSource
	 *            Pool de conexiones de base de datos.
	 */
	public FechaDBEntityImplBase(DbDataSource dataSource) {
		super(dataSource);
	}

	public FechaDBEntityImplBase(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene la lista de valores de tipo fecha de la ficha.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @return Lista de valores.
	 */
	public List getValues(String idElementoCF, int tipoElemento) {

		return getValues(idElementoCF, tipoElemento, null);

	}

	/**
	 * Obtiene la lista de valores de tipo fecha de la ficha.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @return Lista de valores.
	 */
	public List getValues(String idElementoCF, int tipoElemento,
			ArrayList listaCamposIgnorar) {
		StringBuffer qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(getCampoIdElemento(),
						idElementoCF));

		if (tipoElemento != ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO)
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(getCampoTipoElemento(),
							tipoElemento));

		if (listaCamposIgnorar != null && listaCamposIgnorar.size() > 0)
			qual.append(DBUtils.AND).append(
					DBUtils.generateNotInTokenField(getCampoIdCampo(),
							listaCamposIgnorar));

		qual.append(" ORDER BY ").append(getCampoIdCampo().getName())
				.append(",").append(getCampoOrden().getName());

		return getVOS(qual.toString(), getTableName(), getHint(), getColDefs(),
				CampoFechaVO.class);
	}

	/**
	 * Obtiene la lista de valores de tipo fecha de la ficha.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param idCampo
	 *            Identificador del campo.
	 * @return Lista de valores.
	 */
	public List getValues(String idElementoCF, String idCampo, int tipoElemento) {
		StringBuffer qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(getCampoIdElemento(),
						idElementoCF))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(getCampoIdCampo(), idCampo));

		if (tipoElemento != ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO)
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(getCampoTipoElemento(),
							tipoElemento));

		qual.append(" ORDER BY ").append(getCampoOrden().getName());

		return getVOS(qual.toString(), getTableName(), getHint(), getColDefs(),
				CampoFechaVO.class);

	}

	/**
	 * Obtiene el valor de la ficha.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param idCampo
	 *            Identificador del campo.
	 * @param orden
	 *            Orden del valor.
	 * @return Valor de la ficha.
	 */
	public CampoFechaVO getValue(String idElementoCF, String idCampo,
			int orden, int tipoElemento) {
		StringBuffer qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(getCampoIdElemento(),
						idElementoCF))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(getCampoIdCampo(), idCampo))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(getCampoOrden(), orden));

		if (tipoElemento != ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO)
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(getCampoTipoElemento(),
							tipoElemento));

		return (CampoFechaVO) getVO(qual.toString(), getTableName(), getHint(),
				getColDefs(), CampoFechaVO.class);
	}

	/**
	 * Inserta un valor de tipo fecha.
	 * 
	 * @param value
	 *            Valor de tipo fecha a insertar.
	 * @return Valor de tipo fecha insertado.
	 */
	public CampoFechaVO insertValue(final CampoFechaVO value) {
		try {
			DbConnection conn = getConnection();
			DbInsertFns.insert(conn, getTableName(),
					DbUtil.getColumnNames(getColDefs()),
					new SigiaDbInputRecord(getColDefs(), value));
			return value;
		} catch (Exception e) {
			logger.error(
					"Error insertando campo de tipo fecha para el elemento "
							+ value.getIdObjeto(), e);
			throw new DBException("insertando campo de tipo fecha", e);
		}
	}

	/**
	 * Modifica un valor de tipo fecha.
	 * 
	 * @param value
	 *            Información del campo de tipo fecha.
	 */
	public void updateValue(final CampoFechaVO value) {
		try {
			DbConnection conn = getConnection();
			final StringBuffer qual = new StringBuffer()
					.append(DBUtils.WHERE)
					.append(DBUtils.generateEQTokenField(getCampoIdElemento(),
							value.getIdObjeto()))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(getCampoIdCampo(),
							value.getIdCampo()))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(getCampoOrden(),
							value.getOrden()));

			if (value.getTipoElemento() != ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO)
				qual.append(DBUtils.AND).append(
						DBUtils.generateEQTokenField(getCampoTipoElemento(),
								value.getTipoElemento()));

			DbUpdateFns.update(conn, getTableName(), getColumnNamesList(),
					new SigiaDbInputRecord(getColDefs(), value),
					qual.toString());
		} catch (Exception e) {
			logger.error(
					"Error modificando campo de tipo fecha para el elemento "
							+ value.getIdObjeto(), e);
			throw new DBException("modificando campo de tipo fecha", e);
		}
	}

	/**
	 * Elimina un valor de tipo fecha.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param idCampo
	 *            Identificador del campo.
	 * @param orden
	 *            Orden del valor.
	 */
	public void deleteValue(String idElementoCF, String idCampo, String orden,
			int tipoElemento) {
		try {
			DbConnection conn = getConnection();
			final StringBuffer qual = new StringBuffer()
					.append(DBUtils.WHERE)
					.append(DBUtils.generateEQTokenField(getCampoIdElemento(),
							idElementoCF))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(getCampoIdCampo(),
							idCampo));

			if (StringUtils.isNotBlank(orden))
				qual.append(DBUtils.AND).append(
						DBUtils.generateEQTokenField(getCampoOrden(), orden));

			if (tipoElemento != ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO)
				qual.append(DBUtils.AND).append(
						DBUtils.generateEQTokenField(getCampoTipoElemento(),
								tipoElemento));

			DbDeleteFns.delete(conn, getTableName(), qual.toString());
		} catch (Exception e) {
			logger.error(
					"Error eliminando campo de tipo fecha para el elemento "
							+ idElementoCF, e);
			throw new DBException("eliminando campo de tipo fecha", e);
		}
	}

	/**
	 * Elementos todos los valores de fecha que pertenezcan al elementoCF pasado
	 * como parametro
	 * 
	 * @param idElementoCF
	 */
	public void deleteValueXIdElemento(String idElementoCF, int tipoElemento) {
		final StringBuffer qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(getCampoIdElemento(),
						idElementoCF));

		if (tipoElemento != ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO)
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(getCampoTipoElemento(),
							tipoElemento));

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, getTableName(), qual.toString());
			}
		};

		command.execute();

	}

	/*
	 * Obtiene la fecha máxima de las unidades documentales incluidas en una
	 * serie cuyo idCampo es el pasado como parámetro correspondiente se indica
	 * como parámetro
	 * 
	 * @param idSerie Identificador de la serie
	 * 
	 * @param idCampo Identificador del campo de fecha correspondiente
	 * 
	 * @return Fecha máxima
	 */
	public Date getFechaMaximaUDocs(String idSerie, String idCampo) {

		final StringBuffer tblNames = new StringBuffer()
				.append(UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL)
				.append(",").append(getTableName());

		Date ret = null;

		/*
		 * DbColumnDef CAMPO_FECHA_MAXIMA = new DbColumnDef( null,
		 * UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL, "maximo",
		 * DbDataType.DATE_TIME, -1 , true);
		 */
		// DbColumnDef [] columns = new DbColumnDef [] {CAMPO_FECHA_FINAL};
		// SigiaDbOutputRecordset rowSet = new
		// SigiaDbOutputRecordset(columns,CampoFechaVO.class);

		/*
		 * final StringBuffer sql = new StringBuffer("SELECT ") .append("MAX(")
		 * .append(CAMPO_FECHA_FINAL) .append(") AS ")
		 * .append(FECHA_FINAL_COLUMN_NAME) .append(" FROM ")
		 * .append(UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL)
		 * .append(",") .append(TABLE_NAME) .append(" WHERE ")
		 * .append(DBUtils.generateEQTokenField
		 * (UnidadDocumentalDBEntityImpl.CAMPO_IDSERIE, idSerie))
		 * .append(" AND ") .append(DBUtils.generateJoinCondition(
		 * UnidadDocumentalDBEntityImpl.CAMPO_ID, CAMPO_ID_ELEMENTO))
		 * .append(" AND ") .append(DBUtils.generateEQTokenField(
		 * CAMPO_ID_CAMPO, idCampo)) .append(" GROUP BY ")
		 * .append(CAMPO_ID_CAMPO);
		 */

		/*
		 * final StringBuffer tables = new
		 * StringBuffer(UnidadDocumentalDBEntityImpl
		 * .TABLE_NAME_UNIDAD_DOCUMENTAL) .append(",") .append(TABLE_NAME);
		 */

		final StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(
						UnidadDocumentalDBEntityImpl.CAMPO_IDSERIE, idSerie))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						UnidadDocumentalDBEntityImpl.CAMPO_ID,
						getCampoIdElemento()))
				.append(" AND ")
				.append(DBUtils
						.generateEQTokenField(getCampoIdCampo(), idCampo))
				.append(" GROUP BY ").append(getCampoIdCampo());

		try {
			ret = DbSelectFns.selectMaxDateTime(getConnection(),
					tblNames.toString(), FECHA_FINAL_COLUMN_NAME,
					qual.toString());
		} catch (Exception e) {
			// logger.error("Error eliminando campo de tipo fecha para el elemento "
			// + idElementoCF, e);
			throw new DBException(
					"Buscando fecha máxima de unidades documentales en serie",
					e);
		}

		return ret;
	}

	/*
	 * Obtiene la fecha mínima de las unidades documentales incluidas en una
	 * serie cuyo idCampo es el pasado como parámetro correspondiente se indica
	 * como parámetro
	 * 
	 * @param idSerie Identificador de la serie
	 * 
	 * @param idCampo Identificador del campo de fecha correspondiente
	 * 
	 * @return Fecha máxima
	 */
	public Date getFechaMinimaUDocs(String idSerie, String idCampo) {

		final StringBuffer tblNames = new StringBuffer()
				.append(UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL)
				.append(",").append(getTableName());

		Date ret = null;

		final StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(
						UnidadDocumentalDBEntityImpl.CAMPO_IDSERIE, idSerie))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						UnidadDocumentalDBEntityImpl.CAMPO_ID,
						getCampoIdElemento()))
				.append(" AND ")
				.append(DBUtils
						.generateEQTokenField(getCampoIdCampo(), idCampo))
				.append(" GROUP BY ").append(getCampoIdCampo());

		try {
			ret = DbSelectFns.selectMinDateTime(getConnection(),
					tblNames.toString(), FECHA_INICIAL_COLUMN_NAME,
					qual.toString());
		} catch (Exception e) {
			// logger.error("Error eliminando campo de tipo fecha para el elemento "
			// + idElementoCF, e);
			throw new DBException(
					"Buscando fecha mínima de unidades documentales en serie",
					e);
		}

		return ret;
	}

	public List getFechasByIdsElementosCF(String[] idsElementosCF) {

		// Obtener los Identificadores de los campos.
		ConfiguracionDescripcion configDesc = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion();

		String idCampoFechaExtremaInicial = configDesc.getFechaExtremaInicial();
		String idCampoFechaExtremaFinal = configDesc.getFechaExtremaFinal();

		/*
		 * 
		 * SELECT finicio.fechaini, ffin.fechafin FROM advcfechacf finicio,
		 * advcfechacf ffin WHERE finicio.idelementocf IN (...) AND
		 * finicio.idcampo = '3' AND finicio.idelementocf = ffin.idelementocf
		 * AND ffin.idcampo = '4'
		 */

		String aliasTableFechaInicio = "finicio";
		String aliasTableFechaFin = "ffin";

		DbColumnDef columnaIdElementoFechaInicial = new DbColumnDef(
				"IDELEMENTOCF", new TableDef(FechaDBEntityImpl.TABLE_NAME,
						aliasTableFechaInicio),
				FechaDBEntityImpl.IDELEMENTOCF_COLUMN_NAME,
				FechaDBEntityImpl.CAMPO_ID_ELEMENTO.getDataType(), true);
		DbColumnDef columnaIdCampoFechaInicial = new DbColumnDef("IDCAMPO",
				new TableDef(FechaDBEntityImpl.TABLE_NAME,
						aliasTableFechaInicio),
				FechaDBEntityImpl.IDCAMPO_COLUMN_NAME,
				FechaDBEntityImpl.CAMPO_ID_ELEMENTO.getDataType(), true);
		DbColumnDef columnaFechaInicio = new DbColumnDef("FECHAINICIO",
				new TableDef(FechaDBEntityImpl.TABLE_NAME,
						aliasTableFechaInicio),
				FechaDBEntityImpl.FECHA_INICIAL_COLUMN_NAME,
				FechaDBEntityImpl.CAMPO_FECHA_INICIAL.getDataType(), true);
		DbColumnDef columnaIdElementoFechaFinal = new DbColumnDef(
				"IDELEMENTOCF", new TableDef(FechaDBEntityImpl.TABLE_NAME,
						aliasTableFechaFin),
				FechaDBEntityImpl.IDELEMENTOCF_COLUMN_NAME,
				FechaDBEntityImpl.CAMPO_ID_ELEMENTO.getDataType(), true);
		DbColumnDef columnaIdCampoFechaFinal = new DbColumnDef("IDCAMPO",
				new TableDef(FechaDBEntityImpl.TABLE_NAME, aliasTableFechaFin),
				FechaDBEntityImpl.IDCAMPO_COLUMN_NAME,
				FechaDBEntityImpl.CAMPO_ID_ELEMENTO.getDataType(), true);
		DbColumnDef columnaFechaFin = new DbColumnDef("FECHAFIN", new TableDef(
				FechaDBEntityImpl.TABLE_NAME, aliasTableFechaFin),
				FechaDBEntityImpl.FECHA_FINAL_COLUMN_NAME,
				FechaDBEntityImpl.CAMPO_FECHA_FINAL.getDataType(), true);

		TableDef tableFechaInicio = new TableDef(getTableName(),
				aliasTableFechaInicio);
		TableDef tableFechaFin = new TableDef(getTableName(),
				aliasTableFechaFin);

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateInTokenField(
						columnaIdElementoFechaInicial, idsElementosCF))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						columnaIdCampoFechaInicial, idCampoFechaExtremaInicial))
				.append(DBUtils.AND)
				.append(DBUtils.generateJoinCondition(
						columnaIdElementoFechaInicial,
						columnaIdElementoFechaFinal))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(columnaIdCampoFechaFinal,
						idCampoFechaExtremaFinal));

		StringBuffer sqlFrom = new StringBuffer().append(DBUtils
				.generateTableSet(new String[] {
						tableFechaInicio.getDeclaration(),
						tableFechaFin.getDeclaration() }));

		DbColumnDef[] cols = new DbColumnDef[] { columnaIdElementoFechaInicial,
				columnaFechaInicio, columnaFechaFin };

		return getVOS(qual.toString(), sqlFrom.toString(), cols,
				FechaInicialFinalElementoCF.class);

	}

	public int getMaxOrden(String idElemento, String idCampo) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(getCampoIdElemento(),
						idElemento))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(getCampoIdCampo(), idCampo));

		return getMax(qual.toString(), getTableName(), getCampoOrden());
	}

}