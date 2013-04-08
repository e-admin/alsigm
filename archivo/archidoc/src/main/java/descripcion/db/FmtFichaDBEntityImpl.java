package descripcion.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;

import java.util.List;

import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.exceptions.DBException;
import common.util.StringUtils;

import descripcion.vos.FmtFichaVO;

/**
 * DBEntity para acceder a la tabla ADFMTFICHA.
 */
public class FmtFichaDBEntityImpl extends DBEntity implements IFmtFichaDBEntity {

	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = "ADFMTFICHA";

	/** Nombre de columnas */
	public static final String ID_COLUMN_NAME = "id";
	public static final String NOMBRE_COLUMN_NAME = "nombre";
	public static final String DEFINICION_COLUMN_NAME = "definicion";
	public static final String IDFICHA_COLUMN_NAME = "idficha";
	public static final String TIPO_COLUMN_NAME = "tipo";
	public static final String NIVEL_ACCESO_COLUMN_NAME = "nivelacceso";
	public static final String IDLCA_COLUMN_NAME = "idlca";
	public static final String DESCRIPCION_COLUMN_NAME = "descripcion";

	/** Definiciones de columna */
	public static final DbColumnDef CAMPO_ID = new DbColumnDef(ID_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_NOMBRE = new DbColumnDef(
			NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, false);
	public static final DbColumnDef CAMPO_DEFINICION = new DbColumnDef(
			DEFINICION_COLUMN_NAME, DbDataType.LONG_TEXT, 0, false);
	public static final DbColumnDef CAMPO_IDFICHA = new DbColumnDef(
			IDFICHA_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_TIPO = new DbColumnDef(
			TIPO_COLUMN_NAME, DbDataType.SHORT_INTEGER, 0, false);
	public static final DbColumnDef CAMPO_NIVEL_ACCESO = new DbColumnDef(
			NIVEL_ACCESO_COLUMN_NAME, DbDataType.SHORT_INTEGER, 0, false);
	public static final DbColumnDef CAMPO_IDLCA = new DbColumnDef(
			IDLCA_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_DESCRIPCION = new DbColumnDef(
			DESCRIPCION_COLUMN_NAME, DbDataType.SHORT_TEXT, 254, false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] { CAMPO_ID,
			CAMPO_NOMBRE, CAMPO_DEFINICION, CAMPO_IDFICHA, CAMPO_TIPO,
			CAMPO_NIVEL_ACCESO, CAMPO_IDLCA, CAMPO_DESCRIPCION };

	/** Lista reducida de columnas. */
	public static final DbColumnDef[] BASIC_COL_DEFS = new DbColumnDef[] {
			CAMPO_ID, CAMPO_NOMBRE,
			// CAMPO_DEFINICION,
			CAMPO_IDFICHA, CAMPO_TIPO, CAMPO_NIVEL_ACCESO, CAMPO_IDLCA,
			CAMPO_DESCRIPCION };

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
	public FmtFichaDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public FmtFichaDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene la definición del formato de una ficha.
	 *
	 * @param id
	 *            Identificador de la definición del formato de la ficha.
	 * @return Definición del formato de una ficha.
	 */
	public FmtFichaVO getFmtFicha(String id) {
		StringBuffer qual = new StringBuffer().append("WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_ID, id));

		return (FmtFichaVO) getVO(qual.toString(), TABLE_NAME, COL_DEFS,
				FmtFichaVO.class);
	}

	/**
	 * Obtiene la lista de definiciones de formatos de fichas.
	 *
	 * @return Lista de definiciones de formatos de fichas.
	 */
	public List getFmtFichas(String idFicha, int tipo) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDFICHA, idFicha))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPO, tipo))
				.append(" ORDER BY " + NIVEL_ACCESO_COLUMN_NAME + " DESC, "
						+ NOMBRE_COLUMN_NAME);

		return getVOS(qual.toString(), TABLE_NAME, BASIC_COL_DEFS,
				FmtFichaVO.class);
	}

	/**
	 * Obtiene la lista de definiciones de formatos de fichas.
	 *
	 * @return Lista de definiciones de formatos de fichas.
	 */
	public List getFmtFichas() {
		StringBuffer qual = new StringBuffer().append(" ORDER BY "
				+ NOMBRE_COLUMN_NAME);
		return getVOS(qual.toString(), TABLE_NAME, BASIC_COL_DEFS,
				FmtFichaVO.class);
	}

	/**
	 * Inserta un formato de ficha ficha.
	 *
	 * @param FmtFichaVO
	 * @return FmtFichaVO
	 */
	public FmtFichaVO createFmtFicha(FmtFichaVO fmtFichaVO) {
		DbConnection conn = getConnection();

		try {
			fmtFichaVO.setId(getGuid(fmtFichaVO.getId()));
			DbInsertFns.insert(conn, TABLE_NAME, DbUtil
					.getColumnNames(COL_DEFS), new SigiaDbInputRecord(COL_DEFS,
					fmtFichaVO));
			return fmtFichaVO;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DBException(e.getMessage());
		}
	}

	/**
	 * Elimina los formatos de ficha que tengan como idFicha algunos de los
	 * pasados por parámetro
	 *
	 * @param String
	 *            [] idsFichas
	 */
	public void deleteFmtFichas(String[] idsFichas) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateInTokenField(CAMPO_IDFICHA, idsFichas))
				.toString();

		if (logger.isDebugEnabled())
			logger.debug("Se van a eliminar las fichas con ids: " + idsFichas);

		deleteVO(qual, TABLE_NAME);
	}

	/**
	 * Actualiza un formato de la ficha
	 *
	 * @param FmtFichaVO
	 * @return FmtFichaVO
	 */
	public FmtFichaVO updateFmtFicha(FmtFichaVO fmtFichaVO) {
		try {
			DbConnection conn = getConnection();
			final String qual = new StringBuffer()
					.append(" WHERE ")
					.append(DBUtils.generateEQTokenField(CAMPO_ID,
							fmtFichaVO.getId())).toString();

			DbUpdateFns.update(conn, TABLE_NAME, COLUM_NAMES_LIST,
					new SigiaDbInputRecord(COL_DEFS, fmtFichaVO),
					qual.toString());
			return fmtFichaVO;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DBException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * descripcion.db.IFmtFichaDBEntity#getFmtsFichasXListaAcceso(java.lang.
	 * String)
	 */
	public List getFmtsFichasXListaAcceso(String idListaAcceso) {
		StringBuffer qual = new StringBuffer().append("WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_IDLCA, idListaAcceso));

		return (List) getVOS(qual.toString(), TABLE_NAME, COL_DEFS,
				FmtFichaVO.class);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * descripcion.db.IFmtFichaDBEntity#deleteFmtFichasByIds(java.lang.String[])
	 */
	public void deleteFmtFichasByIds(String[] idsFmtFichas) {
		String qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateInTokenField(CAMPO_ID, idsFmtFichas))
				.toString();

		if (logger.isDebugEnabled())
			logger.debug("Se van a eliminar los formatos fichas con ids: "
					+ idsFmtFichas);

		deleteVO(qual, TABLE_NAME);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see descripcion.db.IFmtFichaDBEntity#findFmtFichas(java.lang.String,
	 * java.lang.String)
	 */
	public List findFmtFichas(String nombre, String idFicha) {
		StringBuffer qual = new StringBuffer();

		if (!StringUtils.isEmpty(nombre) && (!StringUtils.isEmpty(idFicha))) {
			qual.append(DBUtils.WHERE);
			qual.append(DBUtils.generateLikeTokenField(CAMPO_NOMBRE, nombre,
					true));
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(CAMPO_IDFICHA, idFicha));
		} else {
			if (!StringUtils.isEmpty(nombre)) {
				qual.append(DBUtils.WHERE).append(
						DBUtils.generateLikeTokenField(CAMPO_NOMBRE, nombre,
								true));
			}

			if (!StringUtils.isEmpty(idFicha)) {
				qual.append(DBUtils.WHERE).append(
						DBUtils.generateEQTokenField(CAMPO_IDFICHA, idFicha));
			}
		}
		qual.append(DBUtils.generateOrderBy(CAMPO_NOMBRE));
		return getVOS(qual.toString(), TABLE_NAME, BASIC_COL_DEFS,
				FmtFichaVO.class);

	}

	public List getFmtsFicha(String idFicha) {
		StringBuffer qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDFICHA, idFicha));

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS,
				FmtFichaVO.class);
	}

}