package descripcion.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;

import java.util.ArrayList;
import java.util.List;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.exceptions.DBException;
import common.util.StringUtils;

import descripcion.vos.CampoTextoVO;
import descripcion.vos.ValorCampoGenericoVOBase;

/**
 * DBEntity base para acceder a las tablas de textos (cortos y largos).
 */
public abstract class TextoDBEntityImplBase extends DBEntity implements
		ITextoDBEntity {
	/** Nombre de columnas */
	public static final String IDCAMPO_COLUMN_NAME = "idcampo";
	public static final String VALOR_COLUMN_NAME = "valor";
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
	public TextoDBEntityImplBase(DbDataSource dataSource) {
		super(dataSource);
	}

	public TextoDBEntityImplBase(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene la lista de valores de tipo texto de la ficha.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @return Lista de valores.
	 */
	public List getValues(String idElementoCF, int tipoElemento) {
		// StringBuffer qual = new StringBuffer().append(DBUtils.WHERE)
		// // .append(DBUtils.generateEQTokenField(CAMPO_ID_ELEMENTO,
		// idElementoCF))
		// .append(DBUtils.generateEQTokenField(getCampoIdElemento(),
		// idElementoCF));
		//
		// if (tipoElemento !=
		// ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO)
		// qual.append(DBUtils.AND)
		// .append(DBUtils.generateEQTokenField(getCampoTipoElemento(),
		// tipoElemento));
		//
		// qual.append(" ORDER BY ")
		// .append(getCampoIdCampo().getName())
		// .append(",")
		// .append(getCampoOrden().getName());
		//
		// // return getVOS(qual.toString(), TABLE_NAME, HINT, COL_DEFS,
		// return getVOS(qual.toString(), getTableName(), getHint(),
		// getColDefs(),
		// CampoTextoVO.class);

		return getValues(idElementoCF, tipoElemento, null);
	}

	/**
	 * Obtiene la lista de valores de tipo texto corto de la ficha.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param idCampo
	 *            Identificador del campo.
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
				CampoTextoVO.class);
	}

	/**
	 * Obtiene la lista de valores de tipo texto corto de la ficha.
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
				CampoTextoVO.class);
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
	public CampoTextoVO getValue(String idElementoCF, String idCampo,
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

		if (getCampoTipoElemento() != null
				&& tipoElemento != ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO)
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(getCampoTipoElemento(),
							tipoElemento));

		return (CampoTextoVO) getVO(qual.toString(), getTableName(), getHint(),
				getColDefs(), CampoTextoVO.class);
	}

	/**
	 * Inserta un valor de tipo texto corto.
	 * 
	 * @param value
	 *            Valor de tipo texto corto a insertar.
	 * @return Valor de tipo texto corto insertado.
	 */
	public CampoTextoVO insertValue(final CampoTextoVO value) {
		try {
			DbConnection conn = getConnection();
			DbInsertFns.insert(conn, getTableName(),
					DbUtil.getColumnNames(getColDefs()),
					new SigiaDbInputRecord(getColDefs(), value));
			return value;
		} catch (Exception e) {
			logger.error(
					"Error insertando campo de tipo texto para el elemento "
							+ value.getIdObjeto(), e);
			throw new DBException("insertando campo de tipo corto", e);
		}
	}

	/**
	 * Modifica un valor de tipo texto corto.
	 * 
	 * @param value
	 *            Información del campo de tipo texto corto.
	 */
	public void updateValue(final CampoTextoVO value) {
		try {
			DbConnection conn = getConnection();
			final StringBuffer qual = new StringBuffer()
					.append(" WHERE ")
					// .append(DBUtils.generateEQTokenField(CAMPO_ID_ELEMENTO,
					// value.getIdObjeto()))
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

			DbUpdateFns.update(conn, getTableName(),
			// COLUMN_NAMES_LIST,
					getColumnNamesList(), new SigiaDbInputRecord(getColDefs(),
							value), qual.toString());
		} catch (Exception e) {
			logger.error(
					"Error modificando campo de tipo texto para el elemento "
							+ value.getIdObjeto(), e);
			throw new DBException("modificando campo de tipo texto", e);
		}
	}

	/**
	 * Elimina un valor de tipo texto corto.
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
		DbConnection conn = getConnection();

		try {
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
					"Error eliminando campo de tipo texto para el elemento "
							+ idElementoCF, e);
			throw new DBException("eliminando campo de tipo texto", e);
		}
	}

	/**
	 * Elementos todos los valores de texto que pertenezcan al elementoCF pasado
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
	public CampoTextoVO getValue(String idElementoCF, String idCampo,
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

		return (CampoTextoVO) getVO(qual.toString(), getTableName(), getHint(),
				getColDefs(), CampoTextoVO.class);
	}

	/**
	 * Elimina un valor de tipo texto corto.
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param idCampo
	 *            Identificador del campo.
	 * @param orden
	 *            Orden del valor.
	 */
	public void deleteValue(String idElementoCF, String idCampo, int orden) {
		DbConnection conn = getConnection();
		try {
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
					"Error eliminando campo de tipo texto para el elemento "
							+ idElementoCF, e);
			throw new DBException("eliminando campo de tipo texto", e);
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