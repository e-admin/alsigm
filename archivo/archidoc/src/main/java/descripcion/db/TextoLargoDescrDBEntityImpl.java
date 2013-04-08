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

import descripcion.vos.CampoTextoVO;

/**
 * DBEntity para acceder a la tabla ADVCTEXTLDESCR.
 */
public class TextoLargoDescrDBEntityImpl extends DBEntity implements
		ITextoLargoDescrDBEntity {

	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = "ADVCTEXTLDESCR";

	/** Nombre de columnas */
	public static final String ID_OBJETO_COLUMN_NAME = "idobjeto";
	public static final String ID_DESCR_COLUMN_NAME = "iddescr";
	public static final String IDCAMPO_COLUMN_NAME = "idcampo";
	public static final String VALOR_COLUMN_NAME = "valor";
	public static final String ORDEN_COLUMN_NAME = "orden";
	public static final String TIMESTAMP_COLUMN_NAME = "timestamp";
	public static final String IDXVALOR_COLUMN_NAME = DBEntity.LONG_TEXT_POSTGRES_PREFFIX
			+ "valor";

	/** Definiciones de columna */
	public static final DbColumnDef CAMPO_ID_DESCR = new DbColumnDef(
			ID_OBJETO_COLUMN_NAME, TABLE_NAME, ID_DESCR_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_ID_CAMPO = new DbColumnDef(null,
			TABLE_NAME, IDCAMPO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_VALOR = new DbColumnDef(null,
			TABLE_NAME, VALOR_COLUMN_NAME, DbDataType.LONG_TEXT, 0, false);
	public static final DbColumnDef CAMPO_ORDEN = new DbColumnDef(null,
			TABLE_NAME, ORDEN_COLUMN_NAME, DbDataType.LONG_INTEGER, 0, false);
	public static final DbColumnDef CAMPO_TIMESTAMP = new DbColumnDef(null,
			TABLE_NAME, TIMESTAMP_COLUMN_NAME, DbDataType.DATE_TIME, 0, false);
	public static final DbColumnDef CAMPO_IDXVALOR = new DbColumnDef(null,
			TABLE_NAME, IDXVALOR_COLUMN_NAME, DbDataType.LONG_TEXT, 0, false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] {
			CAMPO_ID_DESCR, CAMPO_ID_CAMPO, CAMPO_VALOR, CAMPO_ORDEN,
			CAMPO_TIMESTAMP };

	/** Lista de nombres de columnas. */
	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(COL_DEFS);

	/** Hint para agilizar las búsquedas. */
	protected static final String HINT = "/*+INDEX(ADVCTEXTLDESCR ADVCTEXTLDESCR1)*/";

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
	public TextoLargoDescrDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public TextoLargoDescrDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene la lista de valores de tipo texto largo de la ficha.
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
				CampoTextoVO.class);
	}

	/**
	 * Obtiene la lista de valores de tipo texto largo de la ficha.
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
				CampoTextoVO.class);
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
	public CampoTextoVO getValue(String idDescr, String idCampo, int orden) {
		StringBuffer qual = new StringBuffer().append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_DESCR, idDescr))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_CAMPO, idCampo))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ORDEN, orden));

		return (CampoTextoVO) getVO(qual.toString(), TABLE_NAME, HINT,
				COL_DEFS, CampoTextoVO.class);
	}

	/**
	 * Inserta un valor de tipo texto.
	 * 
	 * @param value
	 *            Valor de tipo texto a insertar.
	 * @return Valor de tipo texto insertado.
	 */
	public CampoTextoVO insertValue(final CampoTextoVO value) {
		try {
			DbConnection conn = getConnection();
			if (!StringUtils.isEmpty(value.getValor())) {
				DbInsertFns.insert(conn, TABLE_NAME,
						DbUtil.getColumnNames(COL_DEFS),
						new SigiaDbInputRecord(COL_DEFS, value));
			}
			return value;
		} catch (Exception e) {
			logger.error(
					"Error insertando campo de tipo texto corto para el descriptor "
							+ value.getIdObjeto(), e);
			throw new DBException("insertando campo de tipo texto corto", e);
		}
	}

	/**
	 * Modifica un valor de tipo texto largo.
	 * 
	 * @param value
	 *            Información del campo de tipo texto largo.
	 */
	public void updateValue(final CampoTextoVO value) {
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
					"Error modificando campo de tipo texto largo para el descriptor "
							+ value.getIdObjeto(), e);
			throw new DBException("modificando campo de tipo texto largo", e);
		}
	}

	/**
	 * Elimina un valor de tipo texto largo.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @param idCampo
	 *            Identificador del campo.
	 * @param orden
	 *            Orden del valor.
	 */
	public void deleteValue(String idDescr, String idCampo, String orden) {
		DbConnection conn = getConnection();
		try {
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
					"Error eliminando campo de tipo texto largo para el descriptor "
							+ idDescr, e);
			throw new DBException("eliminando campo de tipo texto largo", e);
		}
	}

	/**
	 * Elementos todos los valores de texto largo que pertenezcan al descriptor
	 * pasado como parametro
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

	public String getEntityName() {
		return TABLE_NAME;
	}
}