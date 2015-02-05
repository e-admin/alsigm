package gcontrol.db;

import gcontrol.vos.ArchivoVO;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbUtil;

import java.util.List;

import common.Constants;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.TableDef;
import common.util.StringUtils;

/**
 * Implementación de los métodos de recuperación y actualización de la
 * información almacenada en la base de datos referente a los archivos
 * gestionados por el sistema
 */
public class ArchivoDbEntityImpl extends DBEntity implements IArchivoDbEntity {

	/**
	 * Nombre de la tabla en la que se almacena la lista de archivos gestionados
	 * por el sistema
	 */
	public static final String TABLE_NAME = "AGARCHIVO";

	public static final String ID_COLUMN_NAME = "ID";

	public static final String CODIGO_COLUMN_NAME = "CODIGO";

	public static final String NOMBRE_COLUMN_NAME = "NOMBRE";

	/**
	 * ID Nivel de Archivo
	 */
	public static final String IDNIVEL_COLUMN_NAME = "IDNIVEL";

	/**
	 * ID del Archivo por defecto.
	 */
	public static final String IDRECEPTORDEFECTO_COLUMN_NAME = "IDRECEPTORDEFECTO";

	/**
	 * Nombre Corto del Archivo (Para Cartelas)
	 */
	public static final String NOMBRECORTO_COLUMN_NAME = "NOMBRECORTO";

	/**
	 * Tipo de Signaturación
	 */
	public static final String TIPOSIGNATURACION_COLUMN_NAME = "TIPOSIGNATURACION";

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public static final DbColumnDef ID_FIELD = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CODIGO_FIELD = new DbColumnDef(null,
			TABLE_NAME, CODIGO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef NOMBRE_FIELD = new DbColumnDef(null,
			TABLE_NAME, NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 128, false);

	public static final DbColumnDef IDNIVEL_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDNIVEL_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef IDRECEPTORDEFECTO_FIELD = new DbColumnDef(
			null, TABLE_NAME, IDRECEPTORDEFECTO_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef NOMBRECORTO_FIELD = new DbColumnDef(null,
			TABLE_NAME, NOMBRECORTO_COLUMN_NAME, DbDataType.SHORT_TEXT, 25,
			false);

	public static final DbColumnDef TIPOSIGNATURACION_FIELD = new DbColumnDef(
			null, TABLE_NAME, TIPOSIGNATURACION_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, false);

	public static final DbColumnDef[] COLS_DEFS = { ID_FIELD, CODIGO_FIELD,
			NOMBRE_FIELD, IDNIVEL_FIELD, IDRECEPTORDEFECTO_FIELD,
			NOMBRECORTO_FIELD, TIPOSIGNATURACION_FIELD };

	public static final String COLUM_NAMES_ELEMCF_LIST = DbUtil
			.getColumnNames(COLS_DEFS);

	/**
	 * Constructor.
	 * 
	 * @param dataSource
	 *            Origen de datos que proporciona las conexiones a la base de
	 *            datos.
	 */
	public ArchivoDbEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public ArchivoDbEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtencion de lista de archivos a partir de lista de identificadores
	 * 
	 * @param idArchivo
	 *            Lista de identificadores de archivos
	 * @return Informacion {@link ArchivoVO} correspondiente a los archivos
	 *         solicitados
	 */
	public List getArchivosXId(Object[] idArchivo) {
		String qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateORTokens(ID_FIELD, idArchivo))
				.append(Constants.BLANK)
				.append(DBUtils.generateOrderBy(NOMBRE_FIELD)).toString();

		return getArchivosVOS(qual);
	}

	/**
	 * Recupera un archivo a partir de su identificador en el sistema
	 * 
	 * @param idArchivo
	 *            Identificador de archivo
	 * @return Datos de archivo
	 */
	public ArchivoVO getArchivoXId(String idArchivo) {
		String qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(ID_FIELD, idArchivo))
				.toString();

		return getArchivoVO(qual);
	}

	/**
	 * Recupera un archivo a partir de su código de archivo
	 * 
	 * @param codArchivo
	 *            Código de archivo
	 * @return Datos de archivo
	 */
	public ArchivoVO getArchivoXCodArchivo(String codArchivo) {
		String qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CODIGO_FIELD, codArchivo))
				.toString();

		return getArchivoVO(qual);

	}

	/**
	 * Obtencion de un objeto de tipo ArchivoVO de la base de datos
	 * 
	 * @param qual
	 * @return
	 */
	private ArchivoVO getArchivoVO(final String qual) {
		return (ArchivoVO) getVO(qual, TABLE_NAME, COLS_DEFS, ArchivoVO.class);
	}

	/**
	 * Obtencion de todos los archivos existentes en la base de datos
	 * 
	 * @return
	 */
	public List getAll() {

		StringBuffer qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(IDNIVEL_FIELD.getQualifiedName())
				.append(" = ")
				.append(NivelArchivoDbEntityImpl.ID_FIELD.getQualifiedName())
				.append(DBUtils
						.generateOrderBy(NivelArchivoDbEntityImpl.NOMBRE_FIELD));

		String[] tablas = { TABLE_NAME, NivelArchivoDbEntityImpl.TABLE_NAME };
		String tablasFrom = DBUtils.generateTableSet(tablas);

		return getVOS(qual.toString(), tablasFrom, COLS_DEFS, ArchivoVO.class);
	}

	/**
	 * Obtiene los archivos cuyo orden de nivel es mayor que el orden que se le
	 * pasa
	 * 
	 * @param orden
	 *            Orden seleccionado.
	 * @return Lista de Archivos que cumplen las condiciones
	 */
	public List getArchivosConOrdenNivelMayor(String orden) {
		StringBuffer qual = new StringBuffer().append(DBUtils.WHERE)
				.append(IDNIVEL_FIELD.getQualifiedName()).append(" = ")
				.append(NivelArchivoDbEntityImpl.ID_FIELD.getQualifiedName());

		if (!StringUtils.isBlank(orden))
			qual.append(" AND ")
					.append(NivelArchivoDbEntityImpl.ORDEN_FIELD
							.getQualifiedName())
					.append(" > ")
					.append(orden)
					.append(DBUtils
							.generateOrderBy(NivelArchivoDbEntityImpl.ORDEN_FIELD));
		;

		String[] tablas = { TABLE_NAME, NivelArchivoDbEntityImpl.TABLE_NAME };
		String tablasFrom = DBUtils.generateTableSet(tablas);

		return getVOS(qual.toString(), tablasFrom, COLS_DEFS, ArchivoVO.class);

	}

	/**
	 * Obtiene los archivos receptores posibles para un archivo remitente
	 * 
	 * @param orden
	 *            Orden seleccionado.
	 * @return Lista de Archivos que cumplen las condiciones
	 */
	public List getArchivosReceptores(String idArchivoRemitente) {

		TableDef tablaNivelArchivo2 = new TableDef(
				NivelArchivoDbEntityImpl.TABLE_NAME, "N2");
		final DbColumnDef ORDEN_FIELD_N2 = new DbColumnDef(tablaNivelArchivo2,
				NivelArchivoDbEntityImpl.ORDEN_FIELD);

		TableDef tablaArchivo2 = new TableDef(TABLE_NAME, "A2");

		DbColumnDef[] COLS_DEFS_A2 = {
				new DbColumnDef(tablaArchivo2, ID_FIELD),
				new DbColumnDef(tablaArchivo2, CODIGO_FIELD),
				new DbColumnDef(tablaArchivo2, NOMBRE_FIELD),
				new DbColumnDef(tablaArchivo2, IDNIVEL_FIELD),
				new DbColumnDef(tablaArchivo2, IDRECEPTORDEFECTO_FIELD) };

		StringBuffer qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(ID_FIELD.getQualifiedName())
				.append("='")
				.append(idArchivoRemitente)
				.append("' AND ")
				.append(DBUtils.generateJoinCondition(IDNIVEL_FIELD,
						NivelArchivoDbEntityImpl.ID_FIELD))
				.append(" AND ")
				.append(ORDEN_FIELD_N2.getQualifiedName())
				.append(">")
				.append(NivelArchivoDbEntityImpl.ORDEN_FIELD.getQualifiedName())
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(tablaArchivo2.getAlias(),
						IDNIVEL_FIELD, tablaNivelArchivo2.getAlias(),
						NivelArchivoDbEntityImpl.ID_FIELD));

		String[] tablas = { TABLE_NAME, tablaArchivo2.getDeclaration(),
				NivelArchivoDbEntityImpl.TABLE_NAME,
				tablaNivelArchivo2.getDeclaration() };
		String tablasFrom = DBUtils.generateTableSet(tablas);
		return getVOS(qual.toString(), tablasFrom, COLS_DEFS_A2,
				ArchivoVO.class);

	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see gcontrol.db.IArchivoDbEntity#insert(gcontrol.vos.ArchivoVO)
	 */
	public ArchivoVO insert(ArchivoVO archivoVO) {
		archivoVO.setId(getGuid(archivoVO.getId()));
		insertVO(TABLE_NAME, COLS_DEFS, archivoVO);
		return archivoVO;
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see gcontrol.db.IArchivoDbEntity#update(gcontrol.vos.ArchivoVO)
	 */
	public void update(ArchivoVO archivoVO) {
		final String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(ID_FIELD,
						archivoVO.getId())).toString();

		updateVO(qual, TABLE_NAME, COLS_DEFS, archivoVO);
	}

	/**
	 * Obtencion de lista de objetos ArchivoVO que cumplan el qual
	 * 
	 * @param qual
	 * @return
	 */
	private List getArchivosVOS(String qual) {
		return getVOS(qual, TABLE_NAME, COLS_DEFS, ArchivoVO.class);
	}

	/**
	 * Elimina el Archivo seleccionado.
	 */
	public void delete(ArchivoVO archivoVO) {
		final String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(ID_FIELD,
						archivoVO.getId())).toString();

		deleteVO(qual, TABLE_NAME);
	}

	/**
	 * Obtiene los Archivos de un nivel
	 * 
	 * @param idNivel
	 * @return
	 */
	public List getArchivosXNivel(String idNivel) {
		if (StringUtils.isBlank(idNivel))
			return null;

		final String qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDNIVEL_FIELD, idNivel))
				.toString();

		return getVOS(qual, TABLE_NAME, COLS_DEFS, ArchivoVO.class);
	}

	public List getArchivosXIdReceptorDefecto(String idArchivo) {
		if (StringUtils.isBlank(idArchivo))
			return null;

		final String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDRECEPTORDEFECTO_FIELD,
						idArchivo)).toString();

		return getVOS(qual, TABLE_NAME, COLS_DEFS, ArchivoVO.class);

	}

	/**
	 * Devuelve todos los archivos que tiene el tipo de signaturacion y el id
	 * pasados como parametros siempre y cuando este id sea distinto de null.
	 * 
	 * @param tipoSignaturacion
	 * @param id
	 * @return {List ArchivoVO}
	 */
	public List getArchivosXTipoSignaturacion(String tipoSignaturacion,
			String idArchivo) {

		final StringBuffer qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateNEQTokenField(TIPOSIGNATURACION_FIELD,
						tipoSignaturacion));

		if (StringUtils.isNotEmpty(idArchivo)) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateNEQTokenField(ID_FIELD, idArchivo));
		}

		return getVOS(qual.toString(), TABLE_NAME, COLS_DEFS, ArchivoVO.class);
	}
}
