package descripcion.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
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

/**
 * DBEntity para acceder a la tabla ADVCNUMDESCR.
 */
public class NumeroDescrDBEntityImpl extends DBEntity implements
		INumeroDescrDBEntity {

	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = "ADVCNUMDESCR";

	/** Nombre de columnas */
	public static final String ID_OBJETO_COLUMN_NAME = "idobjeto";
	public static final String ID_DESCR_COLUMN_NAME = "iddescr";
	public static final String IDCAMPO_COLUMN_NAME = "idcampo";
	public static final String VALOR_COLUMN_NAME = "valor";
	public static final String TIPOMEDIDA_COLUMN_NAME = "tipomedida";
	public static final String UNIDADMEDIDA_COLUMN_NAME = "unidadmedida";
	public static final String ORDEN_COLUMN_NAME = "orden";
	public static final String TIMESTAMP_COLUMN_NAME = "timestamp";

	/** Definiciones de columna */
	public static final DbColumnDef CAMPO_ID_DESCR = new DbColumnDef(
			ID_OBJETO_COLUMN_NAME, TABLE_NAME, ID_DESCR_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_ID_CAMPO = new DbColumnDef(null,
			TABLE_NAME, IDCAMPO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_TIPOMEDIDA = new DbColumnDef(null,
			TABLE_NAME, TIPOMEDIDA_COLUMN_NAME, DbDataType.SHORT_INTEGER, false);
	public static final DbColumnDef CAMPO_UNIDADMEDIDA = new DbColumnDef(null,
			TABLE_NAME, UNIDADMEDIDA_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);
	public static final DbColumnDef CAMPO_VALOR = new DbColumnDef(null,
			TABLE_NAME, VALOR_COLUMN_NAME, DbDataType.LONG_DECIMAL, false);
	public static final DbColumnDef CAMPO_ORDEN = new DbColumnDef(null,
			TABLE_NAME, ORDEN_COLUMN_NAME, DbDataType.LONG_INTEGER, false);
	public static final DbColumnDef CAMPO_TIMESTAMP = new DbColumnDef(null,
			TABLE_NAME, TIMESTAMP_COLUMN_NAME, DbDataType.DATE_TIME, false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] {
			CAMPO_ID_DESCR, CAMPO_ID_CAMPO, CAMPO_VALOR, CAMPO_TIPOMEDIDA,
			CAMPO_UNIDADMEDIDA, CAMPO_ORDEN, CAMPO_TIMESTAMP };

	/** Lista de nombres de columnas. */
	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(COL_DEFS);

	/** Hint para agilizar las búsquedas. */
	protected static final String HINT = "/*+INDEX(ADVCNUMDESCR ADVCNUMDESCR1)*/";

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Constructor.
	 * 
	 * @param dataSource
	 *            Pool de conexiones de base de datos.
	 */
	public NumeroDescrDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public NumeroDescrDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene la lista de valores de tipo numérico de la ficha.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @return Lista de valores.
	 */
	public List getValues(String idDescr) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_DESCR, idDescr))
				.append(" ORDER BY " + IDCAMPO_COLUMN_NAME + ","
						+ ORDEN_COLUMN_NAME);

		return getVOS(qual.toString(), TABLE_NAME, HINT, COL_DEFS,
				CampoNumericoVO.class);
	}

	/**
	 * Obtiene la lista de valores de tipo numérico de la ficha.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @param idCampo
	 *            Identificador del campo.
	 * @return Lista de valores.
	 */
	public List getValues(String idDescr, String idCampo) {
		StringBuffer qual = new StringBuffer().append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_DESCR, idDescr))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_CAMPO, idCampo))
				.append(" ORDER BY ").append(CAMPO_ORDEN.getName());

		return getVOS(qual.toString(), TABLE_NAME, HINT, COL_DEFS,
				CampoNumericoVO.class);
	}

	/**
	 * Obtiene el valor de la ficha.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @param idCampo
	 *            Identificador del campo.
	 * @param orden
	 *            Orden del valor.
	 * @return Valor de la ficha.
	 */
	public CampoNumericoVO getValue(String idDescr, String idCampo, int orden) {
		StringBuffer qual = new StringBuffer().append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_DESCR, idDescr))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_CAMPO, idCampo))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ORDEN, orden));

		return (CampoNumericoVO) getVO(qual.toString(), TABLE_NAME, HINT,
				COL_DEFS, CampoNumericoVO.class);
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
			DbInsertFns.insert(conn, TABLE_NAME, DbUtil
					.getColumnNames(COL_DEFS), new SigiaDbInputRecord(COL_DEFS,
					value));
			return value;
		} catch (Exception e) {
			logger.error(
					"Error insertando campo de tipo numerico para el descriptor "
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
			final String qual = new StringBuffer()
					.append(" WHERE ")
					.append(DBUtils.generateEQTokenField(CAMPO_ID_DESCR,
							value.getIdObjeto()))
					.append(" AND ")
					.append(DBUtils.generateEQTokenField(CAMPO_ID_CAMPO,
							value.getIdCampo()))
					.append(" AND ")
					.append(DBUtils.generateEQTokenField(CAMPO_ORDEN,
							value.getOrden())).toString();

			DbUpdateFns.update(conn, TABLE_NAME, COLUM_NAMES_LIST,
					new SigiaDbInputRecord(COL_DEFS, value), qual.toString());
		} catch (Exception e) {
			logger.error(
					"Error modificando campo de tipo num\u00E9rico para el descriptor "
							+ value.getIdObjeto(), e);
			throw new DBException("modificando campo de tipo num\u00E9rico", e);
		}
	}

	/**
	 * Elimina un valor de tipo numérico.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @param idCampo
	 *            Identificador del campo.
	 * @param orden
	 *            Orden del valor.
	 */
	public void deleteValue(String idDescr, String idCampo, String orden) {
		try {
			DbConnection conn = getConnection();
			final StringBuffer qual = new StringBuffer()
					.append(" WHERE ")
					.append(DBUtils.generateEQTokenField(CAMPO_ID_DESCR,
							idDescr))
					.append(" AND ")
					.append(DBUtils.generateEQTokenField(CAMPO_ID_CAMPO,
							idCampo));

			if (StringUtils.isNotBlank(orden))
				qual.append(" AND ").append(
						DBUtils.generateEQTokenField(CAMPO_ORDEN, orden));

			DbDeleteFns.delete(conn, TABLE_NAME, qual.toString());
		} catch (Exception e) {
			logger.error(
					"Error eliminando campo de tipo num\u00E9rico para el descriptor "
							+ idDescr, e);
			throw new DBException("eliminando campo de tipo num\u00E9rico", e);
		}
	}

	/**
	 * Elementos todos los valores de numero que pertenezcan al descriptor
	 * pasado como parametro.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 */
	public void deleteValues(String idDescr) {
		final String qual = new StringBuffer().append("WHERE")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_DESCR, idDescr))
				.toString();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual);
			}
		};

		command.execute();

	}

	public void deleteDescriptores(String[] idsDescriptores) {
		final String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateInTokenField(CAMPO_ID_DESCR,
						idsDescriptores)).toString();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual);
			}
		};

		command.execute();
	}

}