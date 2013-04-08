package descripcion.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;

import java.util.List;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.exceptions.DBException;
import common.util.StringUtils;

import descripcion.vos.CampoNumericoVO;
import descripcion.vos.ValorCampoGenericoVOBase;

/**
 * DBEntity para acceder a la tablas de descripción numéricas.
 */
public abstract class NumeroDBEntityImplBase extends DBEntity implements
		INumeroDBEntity {

	/** Nombre de columnas */
	public static final String IDCAMPO_COLUMN_NAME = "idcampo";
	public static final String VALOR_COLUMN_NAME = "valor";
	public static final String TIPOMEDIDA_COLUMN_NAME = "tipomedida";
	public static final String UNIDADMEDIDA_COLUMN_NAME = "unidadmedida";
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

	public abstract DbColumnDef getCampoValor();

	/**
	 * Constructor.
	 * 
	 * @param dataSource
	 *            Pool de conexiones de base de datos.
	 */
	public NumeroDBEntityImplBase(DbDataSource dataSource) {
		super(dataSource);
	}

	public NumeroDBEntityImplBase(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene la lista de valores de tipo numérico de la ficha.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @return Lista de valores.
	 */
	public List getValues(String idElementoCF, int tipoElemento) {
		StringBuffer qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(getCampoIdElemento(),
						idElementoCF));

		if (tipoElemento != ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO)
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(getCampoTipoElemento(),
							tipoElemento));

		qual.append(" ORDER BY " + IDCAMPO_COLUMN_NAME + ","
				+ ORDEN_COLUMN_NAME);

		return getVOS(qual.toString(), getTableName(), getHint(), getColDefs(),
				CampoNumericoVO.class);
	}

	/**
	 * Obtiene la lista de valores de tipo numérico de la ficha.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param idCampo
	 *            Identificador del campo.
	 * @return Lista de valores.
	 */
	public List getValues(String idElementoCF, String idCampo, int tipoElemento) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(getCampoIdElemento(),
						idElementoCF))
				.append(" AND ")
				.append(DBUtils
						.generateEQTokenField(getCampoIdCampo(), idCampo));

		if (tipoElemento != ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO)
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(getCampoTipoElemento(),
							tipoElemento));

		qual.append(" ORDER BY ").append(getCampoOrden().getName());

		return getVOS(qual.toString(), getTableName(), getHint(), getColDefs(),
				CampoNumericoVO.class);
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
	public CampoNumericoVO getValue(String idElementoCF, String idCampo,
			int orden, int tipoElemento) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(getCampoIdElemento(),
						idElementoCF))
				.append(" AND ")
				.append(DBUtils
						.generateEQTokenField(getCampoIdCampo(), idCampo))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(getCampoOrden(), orden));

		if (tipoElemento != ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO)
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(getCampoTipoElemento(),
							tipoElemento));

		return (CampoNumericoVO) getVO(qual.toString(), getTableName(),
				getHint(), getColDefs(), CampoNumericoVO.class);
	}

	/**
	 * Inserta un valor de tipo numerico corto.
	 * 
	 * @param value
	 *            Valor de tipo numerico a insertar.
	 * @return Valor de tipo numerico insertado.
	 */
	public CampoNumericoVO insertValue(final CampoNumericoVO value) {

		try {
			DbConnection conn = getConnection();
			DbInsertFns.insert(conn, getTableName(),
					DbUtil.getColumnNames(getColDefs()),
					new SigiaDbInputRecord(getColDefs(), value));
			return value;
		} catch (Exception e) {
			logger.error(
					"Error insertando campo de tipo numerico para el elemento "
							+ value.getIdObjeto(), e);
			throw new DBException("insertando campo de tipo numerico", e);
		}
	}

	/**
	 * Modifica un valor de tipo numérico.
	 * 
	 * @param value
	 *            Información del campo de tipo numérico.
	 */
	public void updateValue(final CampoNumericoVO value) {
		try {
			DbConnection conn = getConnection();
			final StringBuffer qual = new StringBuffer()
					.append(" WHERE ")
					.append(DBUtils.generateEQTokenField(getCampoIdElemento(),
							value.getIdObjeto()))
					.append(" AND ")
					.append(DBUtils.generateEQTokenField(getCampoIdCampo(),
							value.getIdCampo()))
					.append(" AND ")
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
					"Error modificando campo de tipo num\u00E9rico para el elemento "
							+ value.getIdObjeto(), e);
			throw new DBException("modificando campo de tipo num\u00E9rico", e);
		}
	}

	/**
	 * Elimina un valor de tipo numérico.
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
					.append(" WHERE ")
					.append(DBUtils.generateEQTokenField(getCampoIdElemento(),
							idElementoCF))
					.append(" AND ")
					.append(DBUtils.generateEQTokenField(getCampoIdCampo(),
							idCampo));

			if (StringUtils.isNotBlank(orden))
				qual.append(" AND ").append(
						DBUtils.generateEQTokenField(getCampoOrden(), orden));

			if (tipoElemento != ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO)
				qual.append(DBUtils.AND).append(
						DBUtils.generateEQTokenField(getCampoTipoElemento(),
								tipoElemento));

			DbDeleteFns.delete(conn, getTableName(), qual.toString());
		} catch (Exception e) {
			logger.error(
					"Error eliminando campo de tipo num\u00E9rico para el elemento "
							+ idElementoCF, e);
			throw new DBException("eliminando campo de tipo num\u00E9rico", e);
		}
	}

	/**
	 * Elementos todos los valores de numero que pertenezcan al elementoCF
	 * pasado como parametro
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

	/**
	 * Obtiene el Campo de descripcion de la Ficha.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param idCampo
	 *            Identificador del campo.
	 * @param valor
	 *            Valor del campo.
	 * @return Campo de la Ficha.
	 */
	public CampoNumericoVO getValue(String idElementoCF, String idCampo,
			String valor) {
		StringBuffer qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(getCampoIdElemento(),
						idElementoCF))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(getCampoIdCampo(), idCampo))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(getCampoValor(), valor));

		return (CampoNumericoVO) getVO(qual.toString(), getTableName(),
				getHint(), getColDefs(), CampoNumericoVO.class);
	}

	/**
	 * Elimina un valor de tipo numérico.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param idCampo
	 *            Identificador del campo.
	 * @param orden
	 *            Orden del valor.
	 */
	public void deleteValue(String idElementoCF, String idCampo, int orden) {
		try {
			DbConnection conn = getConnection();
			final StringBuffer qual = new StringBuffer()
					.append(DBUtils.WHERE)
					.append(DBUtils.generateEQTokenField(getCampoIdElemento(),
							idElementoCF))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(getCampoIdCampo(),
							idCampo))
					.append(DBUtils.AND)
					.append(DBUtils
							.generateEQTokenField(getCampoOrden(), orden));

			DbDeleteFns.delete(conn, getTableName(), qual.toString());
		} catch (Exception e) {
			logger.error(
					"Error eliminando campo de tipo num\u00E9rico para el elemento "
							+ idElementoCF, e);
			throw new DBException("eliminando campo de tipo num\u00E9rico", e);
		}
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