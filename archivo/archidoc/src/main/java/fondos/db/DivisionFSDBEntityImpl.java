package fondos.db;

import fondos.vos.DivisionFraccionSerieVO;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;

/**
 * Clase que trabaja con trabaja contra la base de datos sobre la tabla de
 * divisiones de fracciones de serie
 * 
 */
public class DivisionFSDBEntityImpl extends DBEntity implements
		IDivisionFSDbEntity {
	/** Logger de la clase */
	static Logger logger = Logger.getLogger(DivisionFSDBEntityImpl.class);

	public static final String TABLE_NAME_DIVISIONFS = "ASGFDIVISIONFS";

	private static final String IDFRACCIONSERIE_COLUMN_NAME = "IDFS";
	private static final String IDFICHA_COLUMN_NAME = "IDFICHA";
	private static final String IDNIVELDOCUMENTAL_COLUMN_NAME = "IDNIVELDOCUMENTAL";
	private static final String ESTADO_COLUMN_NAME = "ESTADO";
	private static final String ID_USRGESTOR_COLUMN_NAME = "IDUSRGESTOR";
	private static final String TIMESTAMP_COLUMN_NAME = "FECHAESTADO";
	private static final String INFO_COLUMN_NAME = "INFO";

	public static final DbColumnDef CAMPO_IDFS = new DbColumnDef(null,
			TABLE_NAME_DIVISIONFS, IDFRACCIONSERIE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_ID_FICHA = new DbColumnDef(null,
			TABLE_NAME_DIVISIONFS, IDFICHA_COLUMN_NAME, DbDataType.SHORT_TEXT,
			32, false);

	public static final DbColumnDef CAMPO_ID_NIVEL_DOCUMENTAL = new DbColumnDef(
			null, TABLE_NAME_DIVISIONFS, IDNIVELDOCUMENTAL_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_ESTADO = new DbColumnDef(null,
			TABLE_NAME_DIVISIONFS, ESTADO_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, 1, false);

	public static final DbColumnDef CAMPO_ID_USRGESTOR = new DbColumnDef(null,
			TABLE_NAME_DIVISIONFS, ID_USRGESTOR_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_TIMESTAMP = new DbColumnDef(null,
			TABLE_NAME_DIVISIONFS, TIMESTAMP_COLUMN_NAME, DbDataType.DATE_TIME,
			false);

	public static final DbColumnDef CAMPO_INFO = new DbColumnDef(null,
			TABLE_NAME_DIVISIONFS, INFO_COLUMN_NAME, DbDataType.SHORT_TEXT,
			true);

	public static final DbColumnDef[] COLS_DEFS_DIVISIONFS = { CAMPO_IDFS,
			CAMPO_ID_FICHA, CAMPO_ID_NIVEL_DOCUMENTAL, CAMPO_ESTADO,
			CAMPO_ID_USRGESTOR, CAMPO_INFO, CAMPO_TIMESTAMP };

	public static final String COLUMN_NAMES_DIVISIONFS = DbUtil
			.getColumnNames(COLS_DEFS_DIVISIONFS);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME_DIVISIONFS;
	}

	public DivisionFSDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public DivisionFSDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Recupera la división de fracción de serie que devuelve la ejecución de la
	 * consulta sobre divisiones de fracciones de serie con la clausula
	 * <i>WHERE</i> que se proporciona
	 * 
	 * @param qual
	 *            Clausula <i>WHERE</i> de la consulta a ejecutar
	 * @return DivisionFraccionSerie
	 */
	protected DivisionFraccionSerieVO getDivisionFSVO(String qual) {
		HashMap pairsTableNameColsDefs = new HashMap();
		pairsTableNameColsDefs.put(TABLE_NAME_DIVISIONFS, COLS_DEFS_DIVISIONFS);

		return (DivisionFraccionSerieVO) getVO(qual.toString(),
				pairsTableNameColsDefs, DivisionFraccionSerieVO.class);
	}

	/**
	 * Recupera la división de fracción de serie cuya fracción de serie tiene el
	 * identificador que se suministra
	 * 
	 * @param idFS
	 *            Identificador de la fracción de serie
	 * @return Datos de la división de fracción de serie
	 */
	public DivisionFraccionSerieVO getDivisionFSXId(String idFS) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_IDFS, idFS));

		return getDivisionFSVO(qual.toString());
	}

	/**
	 * Inserta una nueva división de fracción de serie en la base de datos
	 * 
	 * @param DivisionFraccionSerieVO
	 *            Datos de la división de fracción de serie a guardar
	 * @return Datos de la fracción de serie insertada
	 */
	public DivisionFraccionSerieVO insertDivisionFS(
			final DivisionFraccionSerieVO divisionFSVO) {
		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLS_DEFS_DIVISIONFS, divisionFSVO);
				DbInsertFns.insert(conn, TABLE_NAME_DIVISIONFS,
						COLUMN_NAMES_DIVISIONFS, inputRecord);
			}
		};

		command.execute();

		return divisionFSVO;
	}

	/**
	 * Actualiza los datos de la división de fracciones de serie que se indica
	 * por parámetro
	 * 
	 * @param divisionFSVO
	 *            Datos de la división de fracción de serie a actualizar
	 */
	public void updateDivisionFS(final DivisionFraccionSerieVO divisionFSVO) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_IDFS, divisionFSVO.getIdFS()));

		updateVO(qual.toString(), TABLE_NAME_DIVISIONFS, COLS_DEFS_DIVISIONFS,
				divisionFSVO);
	}

	/**
	 * Elimina la división de fracción de serie indicada de la tabla de
	 * divisiones de fracción de serie
	 * 
	 * @param idFS
	 *            : identificador de la fracción de serie
	 */
	public void deleteDivisionFS(String idFS) {
		final String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDFS, idFS))
				.toString();

		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME_DIVISIONFS, qual);
			}
		};

		command.execute();
	}

	/**
	 * Cuenta el número de divisiones de fracción de serie del gestor indicado y
	 * en los estados indicados. Si no se indican estados, se presupone que se
	 * buscan en cualquier estado
	 * 
	 * @param idUsrGestor
	 * @param estados
	 * @return número de divisiones de fracción de serie en los estados
	 *         indicados
	 */
	public int getCountDivisionFSXUsr(String idUsrGestor, int[] estados) {

		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID_USRGESTOR, idUsrGestor));

		if (estados != null && estados.length > 0)
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_ESTADO, estados));

		return getVOCount(qual.toString(), TABLE_NAME_DIVISIONFS);
	}

	/**
	 * Devuelve las divisiones de fracción de serie del gestor indicado y en los
	 * estados indicados. Si no se indican estados, se presupone que se buscan
	 * en cualquier estado
	 * 
	 * @param idUsrGestor
	 * @param estados
	 * @return divisiones de fracción de serie en los estados indicados y cuyo
	 *         gestor es el indicado
	 */
	public List getDivisionesFSXUsr(String idUsrGestor, int[] estados) {

		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID_USRGESTOR, idUsrGestor));

		if (estados != null && estados.length > 0)
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_ESTADO, estados));

		return getVOS(qual.toString(), TABLE_NAME_DIVISIONFS,
				COLS_DEFS_DIVISIONFS, DivisionFraccionSerieVO.class);
	}

	/**
	 * Recupera las divisiones de fracción de serie cuyos identificadores se
	 * pasan como parámetro
	 * 
	 * @param idsFS
	 *            Identificadores de las fracciones de serie
	 * @return Lista de la divisiones de fracción de serie
	 */
	public List getDivisionesFSXId(String[] idsFS) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateInTokenField(CAMPO_IDFS, idsFS));

		return getVOS(qual.toString(), TABLE_NAME_DIVISIONFS,
				COLS_DEFS_DIVISIONFS, DivisionFraccionSerieVO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fondos.db.IDivisionFSDbEntity#getUdocsByInfoDescriptor(java.lang.String
	 * [])
	 */
	public List getUdocsByInfoDescriptor(String[] idsDescriptores) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE);

		for (int i = 0; i < idsDescriptores.length; i++) {
			if (i > 0) {
				qual.append(DBUtils.OR);
			}
			qual.append(DBUtils.generateLikeTokenField(CAMPO_INFO,
					idsDescriptores[i], false));
		}

		return getVOS(qual.toString(), TABLE_NAME_DIVISIONFS,
				COLS_DEFS_DIVISIONFS, DivisionFraccionSerieVO.class);
	}

}