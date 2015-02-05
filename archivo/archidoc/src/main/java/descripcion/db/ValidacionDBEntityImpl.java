package descripcion.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.HashMap;
import java.util.List;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;

import descripcion.vos.TextoTablaValidacionVO;

/**
 * DBEntity para acceder a la tabla ADTEXTTBLVLD.
 */
public class ValidacionDBEntityImpl extends DBEntity implements
		IValidacionDBEntity {

	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = "ADTEXTTBLVLD";

	/** Nombre de columnas */
	public static final String ID_COLUMN_NAME = "id";
	public static final String VALOR_COLUMN_NAME = "valor";
	public static final String IDTBLVLD_COLUMN_NAME = "idtblvld";

	/** Definiciones de columna */
	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_VALOR = new DbColumnDef(null,
			TABLE_NAME, VALOR_COLUMN_NAME, DbDataType.SHORT_TEXT, 254, false);
	public static final DbColumnDef CAMPO_IDTBLVLD = new DbColumnDef(null,
			TABLE_NAME, IDTBLVLD_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] { CAMPO_ID,
			CAMPO_VALOR, CAMPO_IDTBLVLD };

	/** Lista de nombres de columnas. */
	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(COL_DEFS);

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
	public ValidacionDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public ValidacionDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene la lista de valores de una tabla de validación.
	 * 
	 * @param idTblVld
	 *            Identificador de la tabla de validación.
	 * @return Lista de valores.
	 */
	public List getValoresTablaValidacion(String idTblVld) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDTBLVLD, idTblVld))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(CAMPO_IDTBLVLD,
						TablaValidacionDBEntityImpl.CAMPO_ID))
				.append(" ORDER BY ").append(CAMPO_VALOR.getName());

		// Mapeo de campos y tablas
		HashMap pairsTableNameColsDefs = new HashMap();
		pairsTableNameColsDefs.put(TABLE_NAME, COL_DEFS);
		pairsTableNameColsDefs
				.put(TablaValidacionDBEntityImpl.TABLE_NAME,
						new DbColumnDef[] { TablaValidacionDBEntityImpl.CAMPO_NOMBRE_TBL_VLD });

		return getVOS(qual.toString(), pairsTableNameColsDefs,
				TextoTablaValidacionVO.class);
	}

	/**
	 * Obtiene el valor de la tabla de validación.
	 * 
	 * @param id
	 *            Identificador del valor de la tabla de validación.
	 * @return Valor de la tabla de validación.
	 */
	public TextoTablaValidacionVO getValorTablaValidacion(String id) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(CAMPO_IDTBLVLD,
						TablaValidacionDBEntityImpl.CAMPO_ID)).toString();

		// Mapeo de campos y tablas
		HashMap pairsTableNameColsDefs = new HashMap();
		pairsTableNameColsDefs.put(TABLE_NAME, COL_DEFS);
		pairsTableNameColsDefs
				.put(TablaValidacionDBEntityImpl.TABLE_NAME,
						new DbColumnDef[] { TablaValidacionDBEntityImpl.CAMPO_NOMBRE_TBL_VLD });

		return (TextoTablaValidacionVO) getVO(qual.toString(),
				pairsTableNameColsDefs, TextoTablaValidacionVO.class);
	}

	public TextoTablaValidacionVO getValorTablaValidacionByValor(String valor,
			String idTblvld) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_VALOR, valor))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDTBLVLD, idTblvld))
				.toString();

		return (TextoTablaValidacionVO) getVO(qual.toString(), TABLE_NAME,
				COL_DEFS, TextoTablaValidacionVO.class);
	}

	/**
	 * Crea un valor de la tabla de validación.
	 * 
	 * @param valor
	 *            Valor de la tabla de validación.
	 * @return Valor insertado.
	 */
	public TextoTablaValidacionVO insert(final TextoTablaValidacionVO valor) {
		if (logger.isDebugEnabled())
			logger.debug("Se va a crear el valor de la tabla de validaci\u00F3n: "
					+ valor);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				valor.setId(getGuid(valor.getId()));
				SigiaDbInputRecord row = new SigiaDbInputRecord(COL_DEFS, valor);
				DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST, row);

			}
		};
		command.execute();

		return valor;
	}

	/**
	 * Modifica el valor de la tabla de validación.
	 * 
	 * @param valor
	 *            Valor de la tabla de validación.
	 */
	public void update(TextoTablaValidacionVO valor) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, valor.getId()))
				.toString();

		if (logger.isDebugEnabled())
			logger.debug("Se va a modificar el valor de la tabla de validaci\u00F3n: "
					+ valor);

		updateVO(qual, TABLE_NAME, COL_DEFS, valor);
	}

	/**
	 * Elimina un valor de una tabla de validación.
	 * 
	 * @param id
	 *            Identificador del valor de una tabla de validación.
	 */
	public void delete(String id) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id)).toString();

		if (logger.isDebugEnabled())
			logger.debug("Se va a eliminar el valor (de tabla de validaci\u00F3n) con id: "
					+ id);

		deleteVO(qual, TABLE_NAME);
	}

	/**
	 * Elimina los valores de una tabla de validación.
	 * 
	 * @param listaIds
	 *            Lista de identificadores de valores de una tabla de
	 *            validación.
	 */
	public void delete(String[] listaIds) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateInTokenField(CAMPO_ID, listaIds))
				.toString();

		if (logger.isDebugEnabled())
			logger.debug("Se van a eliminar los valores (de tabla de validaci\u00F3n) con ids: "
					+ listaIds);

		deleteVO(qual, TABLE_NAME);
	}

	/**
	 * Elimina los valores de una tabla de validación.
	 * 
	 * @param listaIds
	 *            Lista de identificadores de tablas de validación.
	 */
	public void deleteByVldTblIds(String[] listaIds) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateInTokenField(CAMPO_IDTBLVLD, listaIds))
				.toString();

		if (logger.isDebugEnabled())
			logger.debug("Se van a eliminar los valores de las tablas de validaci\u00F3n con ids: "
					+ listaIds);

		deleteVO(qual, TABLE_NAME);
	}

	/**
	 * Elimina los valores de una tabla de validación.
	 * 
	 * @param idTblVld
	 *            Identificador de la tabla de validación.
	 */
	public void deleteByVldTblId(String idTblVld) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDTBLVLD, idTblVld))
				.toString();

		if (logger.isDebugEnabled())
			logger.debug("Se van a eliminar los valores de la tabla de validaci\u00F3n con id: "
					+ idTblVld);

		deleteVO(qual, TABLE_NAME);
	}

}