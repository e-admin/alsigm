package descripcion.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;

import java.util.List;

import common.db.DBEntityKeyValue;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.definitions.ArchivoTables;
import common.exceptions.DBException;
import common.vos.IKeyId;

import descripcion.vos.AreaVO;

public class AreaDBEntityImpl extends DBEntityKeyValue implements IAreaDBEntity {

	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = ArchivoTables.ADAREA_TABLE_NAME;

	/** Nombre de columnas */
	public static final String ID_COLUMN_NAME = "id";
	public static final String NOMBRE_COLUMN_NAME = "nombre";
	public static final String TIPONORMA_COLUMN_NAME = "tiponorma";
	public static final String DESCRIPCION_COLUMN_NAME = "descripcion";

	/** Definiciones de columna */
	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_NOMBRE = new DbColumnDef(null,
			TABLE_NAME, NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 128, false);
	public static final DbColumnDef CAMPO_TIPONORMA = new DbColumnDef(null,
			TABLE_NAME, TIPONORMA_COLUMN_NAME, DbDataType.SHORT_INTEGER, false);
	public static final DbColumnDef CAMPO_DESCRIPCION = new DbColumnDef(null,
			TABLE_NAME, DESCRIPCION_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			true);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] { CAMPO_ID,
			CAMPO_NOMBRE, CAMPO_TIPONORMA, CAMPO_DESCRIPCION };

	public String getTableName() {
		return TABLE_NAME;
	}

	/** Lista de nombres de columnas. */
	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(COL_DEFS);

	/**
	 * Constructor.
	 * 
	 * @param dataSource
	 *            Pool de conexiones de base de datos.
	 */
	public AreaDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public AreaDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene un AreaVO perteneciente al id pasado por parámetro
	 * 
	 * @param id
	 * @return un AreaVO
	 */
	public AreaVO getArea(String id) {
		return (AreaVO) getVO(getWhereClauseByKey(id), TABLE_NAME, COL_DEFS,
				AreaVO.class);
	}

	public IKeyId getById(String id) {
		return getArea(id);
	}

	/**
	 * Obtiene un AreaVO perteneciente al nombre pasado por parámetro
	 * 
	 * @param nombre
	 * @return un AreaVO
	 */
	public AreaVO getAreaPorNombre(String nombre) {
		StringBuffer qual = new StringBuffer().append("WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_NOMBRE, nombre));

		return (AreaVO) getVO(qual.toString(), TABLE_NAME, COL_DEFS,
				AreaVO.class);
	}

	/**
	 * Obtiene la lista de areas de un tipo de norma.
	 * 
	 * @param tipoNorma
	 *            Tipo de norma.
	 * @return Lista de areas.
	 */
	public List getAreasByTipoNorma(int tipoNorma) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils
						.generateEQTokenField(CAMPO_TIPONORMA, tipoNorma))
				.append(" ORDER BY ").append(CAMPO_NOMBRE.getQualifiedName());

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS, AreaVO.class);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see descripcion.db.IAreaDBEntity#getAreas()
	 */
	public List getAreas() {
		return getVOS("", TABLE_NAME, COL_DEFS, AreaVO.class);
	}

	/**
	 * Inserta un nuevo area.
	 * 
	 * @param AreaVO
	 * @return AreaVO
	 */
	public AreaVO createArea(AreaVO areaVO) {
		DbConnection conn = getConnection();

		try {
			areaVO.setId(getGuid(areaVO.getId()));
			DbInsertFns.insert(conn, TABLE_NAME, DbUtil
					.getColumnNames(COL_DEFS), new SigiaDbInputRecord(COL_DEFS,
					areaVO));
			return areaVO;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DBException(e.getMessage());
		}
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see descripcion.db.IAreaDBEntity#deleteAreas(java.lang.String[])
	 */
	public void deleteAreas(String[] idsAreas) {
		String qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateInTokenField(getKeyField(), idsAreas))
				.toString();

		if (logger.isDebugEnabled())
			logger.debug("Se van a eliminar las fichas con ids: " + idsAreas);

		deleteVO(qual, TABLE_NAME);
	}

	/**
	 * Actualiza un AreaVO
	 * 
	 * @param areaVO
	 */
	public AreaVO updateArea(AreaVO areaVO) {
		DbConnection conn = getConnection();

		try {
			DbUpdateFns.update(conn, TABLE_NAME, COLUM_NAMES_LIST,
					new SigiaDbInputRecord(COL_DEFS, areaVO),
					getWhereClauseByKey(areaVO.getId()));
			return areaVO;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DBException(e.getMessage());
		}
	}

	public DbColumnDef getKeyField() {
		return CAMPO_ID;
	}

	public DbColumnDef getValueField() {
		return CAMPO_NOMBRE;
	}

}
