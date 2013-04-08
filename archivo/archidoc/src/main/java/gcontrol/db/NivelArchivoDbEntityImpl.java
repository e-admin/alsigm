package gcontrol.db;

import gcontrol.vos.NivelArchivoVO;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbUtil;

import java.util.List;

import common.Constants;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.util.StringUtils;

public class NivelArchivoDbEntityImpl extends DBEntity implements
		INivelArchivoDbEntity {

	/**
	 * Nombre de la tabla en la que se almacena la lista de archivos gestionados
	 * por el sistema
	 */
	public static final String TABLE_NAME = "AGNIVELARCHIVO";

	public static final String ID_COLUMN_NAME = "ID";

	public static final String NOMBRE_COLUMN_NAME = "NOMBRE";

	public static final String DESCRIPCION_COLUMN_NAME = "DESCRIPCION";

	public static final String ORDEN_COLUMN_NAME = "ORDEN";

	public static final DbColumnDef ID_FIELD = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef NOMBRE_FIELD = new DbColumnDef(null,
			TABLE_NAME, NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, false);

	public static final DbColumnDef DESCRIPCION_FIELD = new DbColumnDef(null,
			TABLE_NAME, DESCRIPCION_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			false);

	public static final DbColumnDef ORDEN_FIELD = new DbColumnDef(null,
			TABLE_NAME, ORDEN_COLUMN_NAME, DbDataType.SHORT_INTEGER, 4, false);

	public static final DbColumnDef[] COLS_DEFS = { ID_FIELD, NOMBRE_FIELD,
			DESCRIPCION_FIELD, ORDEN_FIELD };

	public static final String COLUM_NAMES_ELEMCF_LIST = DbUtil
			.getColumnNames(COLS_DEFS);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public NivelArchivoDbEntityImpl(DbDataSource datasource) {
		super(datasource);
	}

	public NivelArchivoDbEntityImpl(DbConnection conn) {
		super(conn);
	}

	public List getAll() {
		return getNivelesArchivoVOS(null);
	}

	public NivelArchivoVO getNivelArchivoXId(String idNivelArchivo) {
		String qual = new StringBuffer().append("WHERE")
				.append(DBUtils.generateEQTokenField(ID_FIELD, idNivelArchivo))
				.toString();

		return getNivelArchivoVO(qual);
	}

	public List getNivelesArchivoXId(String[] idNivelesArchivo) {
		String qual = new StringBuffer().append("WHERE")
				.append(DBUtils.generateORTokens(ID_FIELD, idNivelesArchivo))
				.toString();

		return getNivelesArchivoVOS(qual);
	}

	public NivelArchivoVO insert(NivelArchivoVO nivelArchivoVO) {
		nivelArchivoVO.setId(getGuid(nivelArchivoVO.getId()));
		insertVO(TABLE_NAME, COLS_DEFS, nivelArchivoVO);
		return nivelArchivoVO;
	}

	public void update(NivelArchivoVO nivelArchivoVO) {
		final String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(ID_FIELD,
						nivelArchivoVO.getId())).toString();

		updateVO(qual, TABLE_NAME, COLS_DEFS, nivelArchivoVO);

	}

	/**
	 * Obtencion de un objeto de tipo NivelArchivoVO de la base de datos
	 * 
	 * @param qual
	 * @return
	 */
	private NivelArchivoVO getNivelArchivoVO(final String qual) {
		return (NivelArchivoVO) getVO(qual, TABLE_NAME, COLS_DEFS,
				NivelArchivoVO.class);
	}

	/**
	 * Obtencion de lista de objetos NivelArchivoVO que cumplan el qual ordenado
	 * simpre por el Nivel
	 * 
	 * @param qual
	 * @return Lista de Niveles de archivo que cumplen el qual
	 */
	private List getNivelesArchivoVOS(String qual) {
		if (StringUtils.isBlank(qual)) {
			qual = Constants.BLANK;
		}

		qual += DBUtils.generateOrderBy(ORDEN_FIELD);
		return getVOS(qual, TABLE_NAME, COLS_DEFS, NivelArchivoVO.class);
	}

	public void delete(NivelArchivoVO nivelArchivoVO) {
		final String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(ID_FIELD,
						nivelArchivoVO.getId())).toString();

		deleteVO(qual, TABLE_NAME);
	}

	public NivelArchivoVO getNivelArchivoXNombre(String nombre) {

		String qual = new StringBuffer().append("WHERE")
				.append(DBUtils.generateEQTokenField(NOMBRE_FIELD, nombre))
				.toString();

		return getNivelArchivoVO(qual);
	}

}
