package deposito.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbUtil;

import java.util.List;

import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;

import deposito.vos.FormatoHuecoVO;

/**
 * Implementación de los métodos de acceso a datos referidos a los formatos que
 * pueden tener los elementos asignables que integran el fondo físico
 */
public class FormatoDBEntity extends DBEntity implements IFormatoDbEntity {

	/** Logger de la clase */
	// static Logger logger = Logger.getLogger(FormatoDBEntity.class);

	public static final String TABLE_NAME = "AGFORMATO";

	public static final String ID_COLUMN_NAME = "ID";
	public static final String NOMBRE_COLUMN_NAME = "NOMBRE";
	public static final String MULTIDOC_COLUMN_NAME = "ESMULTIDOC";
	public static final String TIPO_COLUMN_NAME = "TIPO";
	public static final String REGULAR_COLUMN_NAME = "REGULAR";
	public static final String LONGITUD_COLUMN_NAME = "LONGITUD";
	public static final String XINFO_COLUMN_NAME = "XINFO";
	public static final String VIGENTE_COLUMN_NAME = "VIGENTE";

	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_NOMBRE = new DbColumnDef(null,
			TABLE_NAME, NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, false);

	public static final DbColumnDef CAMPO_ESMULTIDOC = new DbColumnDef(
			MULTIDOC_COLUMN_NAME, DbDataType.LONG_INTEGER, 1, false);

	public static final DbColumnDef CAMPO_TIPO = new DbColumnDef(
			TIPO_COLUMN_NAME, DbDataType.LONG_INTEGER, 1, false);

	public static final DbColumnDef CAMPO_REGULAR = new DbColumnDef(
			"esregular", TABLE_NAME, REGULAR_COLUMN_NAME,
			DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef CAMPO_LONGITUD = new DbColumnDef(
			LONGITUD_COLUMN_NAME, DbDataType.LONG_DECIMAL, true);

	public static final DbColumnDef CAMPO_XINFO = new DbColumnDef(
			XINFO_COLUMN_NAME, DbDataType.SHORT_TEXT, 1024, true);

	public static final DbColumnDef CAMPO_VIGENTE = new DbColumnDef(
			"esvigente", TABLE_NAME, VIGENTE_COLUMN_NAME,
			DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef[] ALL_COLUMNS = { CAMPO_ID, CAMPO_NOMBRE,
			CAMPO_ESMULTIDOC, CAMPO_TIPO, CAMPO_REGULAR, CAMPO_LONGITUD,
			CAMPO_XINFO, CAMPO_VIGENTE };

	protected static final String ALL_COLUMN_NAMES = DbUtil.getColumnNames(
			CAMPO_ID, CAMPO_NOMBRE, CAMPO_ESMULTIDOC, CAMPO_TIPO,
			CAMPO_REGULAR, CAMPO_LONGITUD, CAMPO_XINFO, CAMPO_VIGENTE);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public FormatoDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public FormatoDBEntity(DbConnection conn) {
		super(conn);
	}

	public List loadFormatos() {
		return getVOS(DBUtils.generateOrderBy(CAMPO_NOMBRE), TABLE_NAME,
				ALL_COLUMNS, FormatoHuecoVO.class);
	}

	public List loadFormatosVigentes() {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_VIGENTE, 1))
				.append(" ORDER BY ").append(CAMPO_NOMBRE.getQualifiedName());
		return getVOS(qual.toString(), TABLE_NAME, ALL_COLUMNS,
				FormatoHuecoVO.class);
	}

	public List loadFormatosRegulares() {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_REGULAR, 1))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_VIGENTE, 1))
				.append(" ORDER BY ").append(CAMPO_NOMBRE.getQualifiedName());
		return getVOS(qual.toString(), TABLE_NAME, ALL_COLUMNS,
				FormatoHuecoVO.class);
	}

	public List loadFormatosIrregulares() {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_REGULAR, 0))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_VIGENTE, 1))
				.append(" ORDER BY ").append(CAMPO_NOMBRE.getQualifiedName());
		return getVOS(qual.toString(), TABLE_NAME, ALL_COLUMNS,
				FormatoHuecoVO.class);
	}

	public FormatoHuecoVO loadFormato(String id) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_ID, id)).append(
				DBUtils.generateOrderBy(CAMPO_NOMBRE));
		return (FormatoHuecoVO) getVO(qual.toString(), TABLE_NAME, ALL_COLUMNS,
				FormatoHuecoVO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * deposito.db.IFormatoDbEntity#insertFormato(deposito.vos.FormatoHuecoVO)
	 */
	public FormatoHuecoVO insertFormato(FormatoHuecoVO formato) {
		formato.setId(getGuid(formato.getId()));
		insertVO(TABLE_NAME, ALL_COLUMNS, formato);
		return formato;
	}

	public FormatoHuecoVO getFormatoByName(String name) {
		StringBuffer qual = new StringBuffer("WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_NOMBRE, name)).append(
				DBUtils.generateOrderBy(CAMPO_NOMBRE));
		return (FormatoHuecoVO) getVO(qual.toString(), TABLE_NAME, ALL_COLUMNS,
				FormatoHuecoVO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * deposito.db.IFormatoDbEntity#updateFormato(deposito.vos.FormatoHuecoVO)
	 */
	public void updateFormato(FormatoHuecoVO formato) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, formato.getId()));

		updateVO(qual.toString(), TABLE_NAME, ALL_COLUMNS, formato);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see deposito.db.IFormatoDbEntity#deleteFormatos(java.lang.String[])
	 */
	public void deleteFormatos(String[] formatosSeleccionados) {
		StringBuffer qual = new StringBuffer().append(" WHERE ").append(
				DBUtils.generateORTokens(CAMPO_ID, formatosSeleccionados));

		deleteVO(qual.toString(), TABLE_NAME);

	}

	public FormatoHuecoVO getFormatoById(String idFormato) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, idFormato));
		return (FormatoHuecoVO) getVO(qual.toString(), TABLE_NAME, ALL_COLUMNS,
				FormatoHuecoVO.class);
	}

}
