package descripcion.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;

import java.util.HashMap;

import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.exceptions.DBException;

import descripcion.vos.FmtPrefFichaVO;

/**
 * DBEntity para acceder a la tabla ADFMTPREF.
 */
public class FmtPrefDBEntityImpl extends DBEntity implements IFmtPrefDBEntity {

	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = "ADFMTPREF";

	/** Nombre de columnas */
	public static final String ID_FICHA_COLUMN_NAME = "idficha";
	public static final String ID_USUARIO_COLUMN_NAME = "idusuario";
	public static final String TIPO_FMT_COLUMN_NAME = "tipofmt";
	public static final String ID_FMT_COLUMN_NAME = "idfmt";

	/** Definiciones de columna */
	public static final DbColumnDef CAMPO_ID_FICHA = new DbColumnDef(null,
			TABLE_NAME, ID_FICHA_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_ID_USUARIO = new DbColumnDef(null,
			TABLE_NAME, ID_USUARIO_COLUMN_NAME, DbDataType.SHORT_TEXT, 64,
			false);
	public static final DbColumnDef CAMPO_TIPO_FMT = new DbColumnDef(null,
			TABLE_NAME, TIPO_FMT_COLUMN_NAME, DbDataType.SHORT_INTEGER, 0,
			false);
	public static final DbColumnDef CAMPO_ID_FMT = new DbColumnDef(null,
			TABLE_NAME, ID_FMT_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] {
			CAMPO_ID_FICHA, CAMPO_ID_USUARIO, CAMPO_TIPO_FMT, CAMPO_ID_FMT };

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
	public FmtPrefDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public FmtPrefDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene la definición del formato de ficha preferente del usuario.
	 * 
	 * @param idFicha
	 *            Identificador de la definición de la ficha.
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @param tipo
	 *            Tipo de formato.
	 * @return Definición del formato de una ficha preferente.
	 */
	public FmtPrefFichaVO getFmtPrefFicha(String idFicha, String idUsuario,
			int tipo) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_FICHA, idFicha))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPO_FMT, tipo))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_USUARIO,
						idUsuario))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(TABLE_NAME, CAMPO_ID_FMT,
						FmtFichaDBEntityImpl.TABLE_NAME,
						FmtFichaDBEntityImpl.CAMPO_ID));

		HashMap pairsTableNameColsDefs = new HashMap();
		pairsTableNameColsDefs.put(TABLE_NAME, COL_DEFS);
		pairsTableNameColsDefs.put(FmtFichaDBEntityImpl.TABLE_NAME,
				new DbColumnDef[] { FmtFichaDBEntityImpl.CAMPO_NIVEL_ACCESO,
						FmtFichaDBEntityImpl.CAMPO_IDLCA });

		return (FmtPrefFichaVO) getVO(qual.toString(), pairsTableNameColsDefs,
				FmtPrefFichaVO.class);
	}

	/**
	 * Inserta un formato de ficha preferente para un usuario.
	 * 
	 * @param fmtPrefFicha
	 *            Formato de ficha preferente.
	 */
	public void insertFmtPrefFicha(FmtPrefFichaVO fmtPrefFicha) {
		try {
			DbConnection conn = getConnection();
			DbInsertFns.insert(conn, TABLE_NAME, DbUtil
					.getColumnNames(COL_DEFS), new SigiaDbInputRecord(COL_DEFS,
					fmtPrefFicha));
		} catch (Exception e) {
			logger.error("Error insertando formato de ficha preferente: "
					+ fmtPrefFicha, e);
			throw new DBException("insertando formato de ficha preferente", e);
		}
	}

	/**
	 * Modifica un formato de ficha preferente para un usuario.
	 * 
	 * @param fmtPrefFicha
	 *            Formato de ficha preferente.
	 */
	public void updateFmtPrefFicha(FmtPrefFichaVO fmtPrefFicha) {
		try {
			DbConnection conn = getConnection();
			final String qual = new StringBuffer()
					.append(" WHERE ")
					.append(DBUtils.generateEQTokenField(CAMPO_ID_FICHA,
							fmtPrefFicha.getIdFicha()))
					.append(" AND ")
					.append(DBUtils.generateEQTokenField(CAMPO_ID_USUARIO,
							fmtPrefFicha.getIdUsuario()))
					.append(" AND ")
					.append(DBUtils.generateEQTokenField(CAMPO_TIPO_FMT,
							fmtPrefFicha.getTipoFmt())).toString();

			DbUpdateFns.update(conn, TABLE_NAME, COLUM_NAMES_LIST,
					new SigiaDbInputRecord(COL_DEFS, fmtPrefFicha),
					qual.toString());
		} catch (Exception e) {
			logger.error("Error modificando formato de ficha preferente: "
					+ fmtPrefFicha, e);
			throw new DBException("modificando formato de ficha preferente", e);
		}
	}

	/**
	 * Elimina los formatos preferentes de ficha que tengan como idFicha algunos
	 * de los pasados por parámetro
	 * 
	 * @param String
	 *            [] idsFichas
	 */
	public void deleteFmtPrefFichas(String[] idsFichas) {
		String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateInTokenField(CAMPO_ID_FICHA, idsFichas))
				.toString();

		if (logger.isDebugEnabled())
			logger.debug("Se van a eliminar los formatos preferentes de las fichas con ids: "
					+ idsFichas);

		deleteVO(qual, TABLE_NAME);
	}
}