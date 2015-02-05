package descripcion.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.List;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;

import descripcion.vos.TablaValidacionVO;

/**
 * DBEntity para acceder a la tabla ADCTLGTBLVLD.
 */
public class TablaValidacionDBEntityImpl extends DBEntity implements
		ITablaValidacionDBEntity {

	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = "ADCTLGTBLVLD";

	/** Nombre de columnas */
	public static final String ID_COLUMN_NAME = "id";
	public static final String NOMBRE_COLUMN_NAME = "nombre";
	public static final String DESCRIPCION_COLUMN_NAME = "descripcion";
	public static final String TABLA_SISTEMA_COLUMN_NAME = "usointerno";

	/** Definiciones de columna */
	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_NOMBRE = new DbColumnDef(null,
			TABLE_NAME, NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, false);
	public static final DbColumnDef CAMPO_DESCRIPCION = new DbColumnDef(null,
			TABLE_NAME, DESCRIPCION_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			false);
	public static final DbColumnDef CAMPO_TABLA_SISTEMA = new DbColumnDef(null,
			TABLE_NAME, TABLA_SISTEMA_COLUMN_NAME, DbDataType.SHORT_TEXT, 1,
			false);

	// Campo para la join con los valores de validación
	public static final DbColumnDef CAMPO_NOMBRE_TBL_VLD = new DbColumnDef(
			"nombreTblVld", TABLE_NAME, NOMBRE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 64, false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] { CAMPO_ID,
			CAMPO_NOMBRE, CAMPO_DESCRIPCION, CAMPO_TABLA_SISTEMA };

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
	public TablaValidacionDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public TablaValidacionDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene la lista de tablas de validación.
	 * 
	 * @return Lista de tablas de validación.
	 */
	public List getTablasValidacion() {
		String qual = new StringBuffer().append(" ORDER BY ")
				.append(CAMPO_NOMBRE.getQualifiedName()).toString();

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS,
				TablaValidacionVO.class);
	}

	/**
	 * Obtiene la tabla de validación.
	 * 
	 * @param id
	 *            Identificador de la tabla de validación.
	 * @return Tabla de validación.
	 */
	public TablaValidacionVO getTablaValidacion(String id) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id)).toString();

		return (TablaValidacionVO) getVO(qual.toString(), TABLE_NAME, COL_DEFS,
				TablaValidacionVO.class);
	}

	public TablaValidacionVO getTablaValidacionByNombre(String nombre) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_NOMBRE, nombre))
				.toString();

		return (TablaValidacionVO) getVO(qual.toString(), TABLE_NAME, COL_DEFS,
				TablaValidacionVO.class);
	}

	/**
	 * Crea una tabla de validación.
	 * 
	 * @param tablaValidacion
	 *            Tabla de validación.
	 * @return Tabla de validación insertada.
	 */
	public TablaValidacionVO insert(final TablaValidacionVO tablaValidacion) {
		if (logger.isDebugEnabled())
			logger.debug("Se va a crear la tabla de validaci\u00F3n: "
					+ tablaValidacion);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				tablaValidacion.setId(getGuid(tablaValidacion.getId()));
				SigiaDbInputRecord row = new SigiaDbInputRecord(COL_DEFS,
						tablaValidacion);
				DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST, row);

			}
		};
		command.execute();

		return tablaValidacion;
	}

	/**
	 * Modifica la tabla de validación.
	 * 
	 * @param tablaValidacion
	 *            Tabla de validación.
	 */
	public void update(TablaValidacionVO tablaValidacion) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID,
						tablaValidacion.getId())).toString();

		if (logger.isDebugEnabled())
			logger.debug("Se va a modificar la tabla de validaci\u00F3n: "
					+ tablaValidacion);

		updateVO(qual, TABLE_NAME, COL_DEFS, tablaValidacion);
	}

	/**
	 * Elimina las tablas de validación.
	 * 
	 * @param listaIds
	 *            Lista de identificadores de tablas de validación.
	 */
	public void delete(String[] listaIds) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateInTokenField(CAMPO_ID, listaIds))
				.toString();

		if (logger.isDebugEnabled())
			logger.debug("Se van a eliminar las tablas de validaci\u00F3n con ids: "
					+ listaIds);

		deleteVO(qual, TABLE_NAME);
	}

	/**
	 * Elimina la tabla de validación.
	 * 
	 * @param id
	 *            Identificador de la tabla de validación.
	 */
	public void delete(String id) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id)).toString();

		if (logger.isDebugEnabled())
			logger.debug("Se va a eliminar la tabla de validaci\u00F3n con id: "
					+ id);

		deleteVO(qual, TABLE_NAME);
	}

}