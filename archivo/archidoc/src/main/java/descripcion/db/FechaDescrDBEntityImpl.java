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

import descripcion.vos.CampoFechaVO;

/**
 * DBEntity para acceder a la tabla ADVCFECHADESCR.
 */
public class FechaDescrDBEntityImpl extends DBEntity implements
		IFechaDescrDBEntity {

	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = "ADVCFECHADESCR";

	/** Nombre de columnas */
	public static final String ID_OBJETO_COLUMN_NAME = "idobjeto";
	public static final String ID_DESCR_COLUMN_NAME = "iddescr";
	public static final String IDCAMPO_COLUMN_NAME = "idcampo";
	public static final String VALOR_COLUMN_NAME = "valor";
	public static final String FECHA_INICIAL_COLUMN_NAME = "fechaini";
	public static final String FECHA_FINAL_COLUMN_NAME = "fechafin";
	public static final String FORMATO_COLUMN_NAME = "formato";
	public static final String SEP_COLUMN_NAME = "sep";
	public static final String CALIFICADOR_COLUMN_NAME = "calificador";
	public static final String ORDEN_COLUMN_NAME = "orden";
	public static final String TIMESTAMP_COLUMN_NAME = "timestamp";

	/** Definiciones de columna */
	public static final DbColumnDef CAMPO_ID_DESCR = new DbColumnDef(
			ID_OBJETO_COLUMN_NAME, TABLE_NAME, ID_DESCR_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_ID_CAMPO = new DbColumnDef(null,
			TABLE_NAME, IDCAMPO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_FECHA_INICIAL = new DbColumnDef(null,
			TABLE_NAME, FECHA_INICIAL_COLUMN_NAME, DbDataType.DATE_TIME, -1,
			true);
	public static final DbColumnDef CAMPO_FECHA_FINAL = new DbColumnDef(null,
			TABLE_NAME, FECHA_FINAL_COLUMN_NAME, DbDataType.DATE_TIME, -1, true);
	public static final DbColumnDef CAMPO_CALIFICADOR = new DbColumnDef(null,
			TABLE_NAME, CALIFICADOR_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);
	public static final DbColumnDef CAMPO_VALOR = new DbColumnDef(null,
			TABLE_NAME, VALOR_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, true);
	public static final DbColumnDef CAMPO_FORMATO = new DbColumnDef(null,
			TABLE_NAME, FORMATO_COLUMN_NAME, DbDataType.SHORT_TEXT, 16, true);
	public static final DbColumnDef CAMPO_SEP = new DbColumnDef(null,
			TABLE_NAME, SEP_COLUMN_NAME, DbDataType.SHORT_TEXT, 1, true);
	public static final DbColumnDef CAMPO_ORDEN = new DbColumnDef(null,
			TABLE_NAME, ORDEN_COLUMN_NAME, DbDataType.LONG_INTEGER, 0, false);
	public static final DbColumnDef CAMPO_TIMESTAMP = new DbColumnDef(null,
			TABLE_NAME, TIMESTAMP_COLUMN_NAME, DbDataType.DATE_TIME, 0, false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] {
			CAMPO_ID_DESCR, CAMPO_ID_CAMPO, CAMPO_FECHA_INICIAL,
			CAMPO_FECHA_FINAL, CAMPO_CALIFICADOR, CAMPO_VALOR, CAMPO_FORMATO,
			CAMPO_SEP, CAMPO_ORDEN, CAMPO_TIMESTAMP };

	/** Lista de nombres de columnas. */
	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(COL_DEFS);

	/** Hint para agilizar las búsquedas. */
	protected static final String HINT = "/*+INDEX(ADVCFECHADESCR ADVCFECHADESCR1)*/";

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
	public FechaDescrDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public FechaDescrDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene la lista de valores de tipo fecha de la ficha.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @return Lista de valores.
	 */
	public List getValues(String idDescr) {
		StringBuffer qual = new StringBuffer().append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_DESCR, idDescr))
				.append(" ORDER BY ").append(CAMPO_ID_CAMPO.getName())
				.append(",").append(CAMPO_ORDEN.getName());

		return getVOS(qual.toString(), TABLE_NAME, HINT, COL_DEFS,
				CampoFechaVO.class);
	}

	/**
	 * Obtiene la lista de valores de tipo fecha de la ficha.
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
				CampoFechaVO.class);
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
	public CampoFechaVO getValue(String idDescr, String idCampo, int orden) {
		StringBuffer qual = new StringBuffer().append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_DESCR, idDescr))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_CAMPO, idCampo))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ORDEN, orden));

		return (CampoFechaVO) getVO(qual.toString(), TABLE_NAME, HINT,
				COL_DEFS, CampoFechaVO.class);
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
			DbInsertFns.insert(conn, TABLE_NAME, DbUtil
					.getColumnNames(COL_DEFS), new SigiaDbInputRecord(COL_DEFS,
					value));
			return value;
		} catch (Exception e) {
			logger.error(
					"Error insertando campo de tipo fecha para el descriptor "
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
					"Error modificando campo de tipo fecha para el descriptor "
							+ value.getIdObjeto(), e);
			throw new DBException("modificando campo de tipo fecha", e);
		}
	}

	/**
	 * Elimina un valor de tipo fecha.
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
					"Error eliminando campo de tipo fecha para el descriptor "
							+ idDescr, e);
			throw new DBException("eliminando campo de tipo fecha", e);
		}
	}

	/**
	 * Elementos todos los valores de fecha que pertenezcan al descriptor pasado
	 * como parametro.
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