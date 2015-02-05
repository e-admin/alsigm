package descripcion.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.exceptions.DBException;
import common.util.StringUtils;

import descripcion.vos.CampoReferenciaVO;

/**
 * DBEntity para acceder a la tabla ADVCREFDESCR.
 */
public class ReferenciaDescrDBEntityImpl extends DBEntity implements
		IReferenciaDescrDBEntity {

	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = "ADVCREFDESCR";

	/** Nombre de columnas */
	public static final String ID_OBJETO_COLUMN_NAME = "idobjeto";
	public static final String REFERENCE_ID_DESCR_COLUMN_NAME = "iddescr";
	public static final String REFERENCE_IDCAMPO_COLUMN_NAME = "idcampo";
	public static final String REFERENCE_TIPOOBJREF_COLUMN_NAME = "tipoobjref";
	public static final String REFERENCE_IDOBJREF_COLUMN_NAME = "idobjref";
	public static final String REFERENCE_ORDEN_COLUMN_NAME = "orden";
	public static final String REFERENCE_TIMESTAMP_COLUMN_NAME = "timestamp";

	/** Definiciones de columna */
	public static final DbColumnDef REFERENCE_CAMPO_ID_DESCR = new DbColumnDef(
			ID_OBJETO_COLUMN_NAME, TABLE_NAME, REFERENCE_ID_DESCR_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef REFERENCE_CAMPO_ID_CAMPO = new DbColumnDef(
			null, TABLE_NAME, REFERENCE_IDCAMPO_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef REFERENCE_CAMPO_TIPOOBJREF = new DbColumnDef(
			null, TABLE_NAME, REFERENCE_TIPOOBJREF_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, 0, false);
	public static final DbColumnDef REFERENCE_CAMPO_IDOBJREF = new DbColumnDef(
			null, TABLE_NAME, REFERENCE_IDOBJREF_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef REFERENCE_CAMPO_ORDEN = new DbColumnDef(
			null, TABLE_NAME, REFERENCE_ORDEN_COLUMN_NAME,
			DbDataType.LONG_INTEGER, 0, false);
	public static final DbColumnDef REFERENCE_CAMPO_TIMESTAMP = new DbColumnDef(
			null, TABLE_NAME, REFERENCE_TIMESTAMP_COLUMN_NAME,
			DbDataType.DATE_TIME, 0, false);

	/** Lista de columnas. */
	public static final DbColumnDef[] REFERENCE_COL_DEFS = new DbColumnDef[] {
			REFERENCE_CAMPO_ID_DESCR, REFERENCE_CAMPO_ID_CAMPO,
			REFERENCE_CAMPO_TIPOOBJREF, REFERENCE_CAMPO_IDOBJREF,
			REFERENCE_CAMPO_ORDEN, REFERENCE_CAMPO_TIMESTAMP };

	/** Lista de columnas para el contenido de la referencia. */
	private static final DbColumnDef[] CUSTOM_COL_DEFS = new DbColumnDef[] {
			REFERENCE_CAMPO_ID_DESCR,
			REFERENCE_CAMPO_ID_CAMPO,
			REFERENCE_CAMPO_TIPOOBJREF,
			REFERENCE_CAMPO_IDOBJREF,
			REFERENCE_CAMPO_ORDEN,
			REFERENCE_CAMPO_TIMESTAMP,
			new DbColumnDef(
					"nombre",
					new StringBuffer()
							.append("CASE WHEN ADVCREFDESCR.TIPOOBJREF = 1 THEN ")
							.append("(SELECT ADDESCRIPTOR.NOMBRE FROM ADDESCRIPTOR WHERE ADDESCRIPTOR.ID=ADVCREFDESCR.IDOBJREF)")
							.append("WHEN ADVCREFDESCR.TIPOOBJREF = 2 THEN ")
							.append("(SELECT ASGFELEMENTOCF.CODREFERENCIA FROM ASGFELEMENTOCF WHERE ASGFELEMENTOCF.ID=ADVCREFDESCR.IDOBJREF)")
							.append("END").toString(), DbDataType.SHORT_TEXT,
					1024, true),
			new DbColumnDef(
					"descripcion",
					new StringBuffer()
							.append("CASE WHEN ADVCREFDESCR.TIPOOBJREF = 2 THEN ")
							.append("(SELECT ASGFELEMENTOCF.CODREFERENCIA FROM ASGFELEMENTOCF WHERE ASGFELEMENTOCF.ID=ADVCREFDESCR.IDOBJREF)")
							.append("END").toString(), DbDataType.SHORT_TEXT,
					1024, true) };

	/** Lista de nombres de columnas. */
	public static final String REFERENCE_COLUM_NAMES_LIST = DbUtil
			.getColumnNames(REFERENCE_COL_DEFS);

	/** Hint para agilizar las búsquedas. */
	protected static final String HINT = "/*+INDEX(ADVCREFDESCR ADVCREFDESCR1)*/";

	/** Hint para agilizar las búsquedas. */
	protected static final String HINT_TIPOOBJ_IDOBJ = "/*+INDEX(ADVCREFDESCR ADVCREFDESCR3)*/";

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
	public ReferenciaDescrDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public ReferenciaDescrDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene la lista de valores de tipo referencia de la ficha.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @return Lista de valores.
	 */
	public List getValues(String idDescr) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(REFERENCE_CAMPO_ID_DESCR,
						idDescr)).append(" ORDER BY ")
				.append(REFERENCE_CAMPO_ID_CAMPO.getName()).append(",")
				.append(REFERENCE_CAMPO_ORDEN.getName());

		return getVOS(qual.toString(), TABLE_NAME, HINT, CUSTOM_COL_DEFS,
				CampoReferenciaVO.class);
	}

	/**
	 * Obtiene la lista de valores de tipo referencia de la ficha.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @param idCampo
	 *            Identificador del campo.
	 * @return Lista de valores.
	 */
	public List getValues(String idDescr, String idCampo) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(REFERENCE_CAMPO_ID_DESCR,
						idDescr))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(REFERENCE_CAMPO_ID_CAMPO,
						idCampo)).append(" ORDER BY ")
				.append(REFERENCE_CAMPO_ORDEN.getName());

		return getVOS(qual.toString(), TABLE_NAME, HINT, CUSTOM_COL_DEFS,
				CampoReferenciaVO.class);
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
	public CampoReferenciaVO getValue(String idDescr, String idCampo, int orden) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(REFERENCE_CAMPO_ID_DESCR,
						idDescr))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(REFERENCE_CAMPO_ID_CAMPO,
						idCampo))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(REFERENCE_CAMPO_ORDEN,
						orden));

		return (CampoReferenciaVO) getVO(qual.toString(), TABLE_NAME, HINT,
				CUSTOM_COL_DEFS, CampoReferenciaVO.class);
	}

	/**
	 * Inserta un valor de tipo referencia.
	 * 
	 * @param value
	 *            Valor de tipo referencia a insertar.
	 * @return Valor de tipo referencia insertado.
	 */
	public CampoReferenciaVO insertValue(final CampoReferenciaVO value) {
		try {
			DbConnection conn = getConnection();
			DbInsertFns.insert(conn, TABLE_NAME,
					DbUtil.getColumnNames(REFERENCE_COL_DEFS),
					new SigiaDbInputRecord(REFERENCE_COL_DEFS, value));
			return value;
		} catch (Exception e) {
			logger.error(
					"Error insertando campo de tipo referencia para el descriptor "
							+ value.getIdObjeto(), e);
			throw new DBException("insertando campo de tipo referencia", e);
		}
	}

	/**
	 * Modifica un valor de tipo referencia.
	 * 
	 * @param value
	 *            Información del campo de tipo referencia.
	 */
	public void updateValue(final CampoReferenciaVO value) {
		try {
			DbConnection conn = getConnection();
			final String qual = new StringBuffer()
					.append(" WHERE ")
					.append(DBUtils.generateEQTokenField(
							REFERENCE_CAMPO_ID_DESCR, value.getIdObjeto()))
					.append(" AND ")
					.append(DBUtils.generateEQTokenField(
							REFERENCE_CAMPO_ID_CAMPO, value.getIdCampo()))
					.append(" AND ")
					.append(DBUtils.generateEQTokenField(REFERENCE_CAMPO_ORDEN,
							value.getOrden())).toString();

			DbUpdateFns.update(conn, TABLE_NAME, REFERENCE_COLUM_NAMES_LIST,
					new SigiaDbInputRecord(REFERENCE_COL_DEFS, value),
					qual.toString());
		} catch (Exception e) {
			logger.error(
					"Error modificando campo de tipo referencia para el descriptor "
							+ value.getIdObjeto(), e);
			throw new DBException("modificando campo de tipo referencia", e);
		}
	}

	/**
	 * Elimina un valor de tipo referencia.
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
					.append(DBUtils.generateEQTokenField(
							REFERENCE_CAMPO_ID_DESCR, idDescr))
					.append(" AND ")
					.append(DBUtils.generateEQTokenField(
							REFERENCE_CAMPO_ID_CAMPO, idCampo));

			if (StringUtils.isNotBlank(orden))
				qual.append(" AND ").append(
						DBUtils.generateEQTokenField(REFERENCE_CAMPO_ORDEN,
								orden));

			DbDeleteFns.delete(conn, TABLE_NAME, qual.toString());
		} catch (Exception e) {
			logger.error(
					"Error eliminando campo de tipo referencia para el descriptor "
							+ idDescr, e);
			throw new DBException("eliminando campo de tipo referencia", e);
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
		final String qual = new StringBuffer()
				.append("WHERE")
				.append(DBUtils.generateEQTokenField(REFERENCE_CAMPO_ID_DESCR,
						idDescr)).toString();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual);
			}
		};

		command.execute();

	}

	/**
	 * Obtiene el número de referencias a un objeto dado.
	 * 
	 * @param tipoObjRef
	 *            Tipo de objeto referenciado.
	 * @param idObjRef
	 *            Identificador del objeto referenciado.
	 * @return Número de referencias.
	 */
	public int countReferences(int tipoObjRef, String idObjRef) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE")
				.append(DBUtils.generateEQTokenField(
						REFERENCE_CAMPO_TIPOOBJREF, tipoObjRef))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(REFERENCE_CAMPO_IDOBJREF,
						idObjRef));

		return getVOCount(qual.toString(), TABLE_NAME, HINT_TIPOOBJ_IDOBJ);
	}

	/**
	 * Obtiene el número de referencias a los descriptores de una lista dada.
	 * 
	 * @param idListaDescriptora
	 *            Identificador de la lista descriptora.
	 * @return Número de referencias.
	 */
	public int countReferencesInList(String idListaDescriptora) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE")
				.append(DBUtils.generateEQTokenField(
						REFERENCE_CAMPO_TIPOOBJREF, 1))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(REFERENCE_CAMPO_IDOBJREF,
						DescriptorDBEntityImpl.CAMPO_ID))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						DescriptorDBEntityImpl.CAMPO_IDLISTA,
						idListaDescriptora));

		return getVOCount(qual.toString(), TABLE_NAME + ","
				+ DescriptorDBEntityImpl.TABLE_NAME, HINT_TIPOOBJ_IDOBJ);
	}

	public void unificarDescriptor(String idDescriptor, String[] idsAReemplazar) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateInTokenField(REFERENCE_CAMPO_IDOBJREF,
						idsAReemplazar)).toString();

		Map cols = Collections.singletonMap(REFERENCE_CAMPO_IDOBJREF,
				idDescriptor);

		updateFields(qual, cols, TABLE_NAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * descripcion.db.IReferenciaDescrDBEntity#deleteDescriptores(java.lang.
	 * String[])
	 */
	public void deleteDescriptores(String[] idsDescriptores) {
		final String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateInTokenField(REFERENCE_CAMPO_ID_DESCR,
						idsDescriptores)).toString();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual);
			}
		};

		command.execute();
	}

}