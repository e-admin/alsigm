package common.lock.db;

import gcontrol.db.UsuarioDBEntityImpl;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbUtil;

import java.util.HashMap;

import common.db.ConstraintType;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.lock.vos.LockVO;

/**
 * Clase que encapsula todas las definiciones de los bloqueos, así como de las
 * operaciones que se pueden realizar sobre ellos.
 */
public abstract class LockDBEntityImplBase extends DBEntity implements
		ILockDBEntity {

	/** Nombre de la tabla */
	public static final String TABLE_NAME = "AGOBJBLOQUEO";

	/** Nombre de las columnas */
	private static final String IDOBJ_COLUMN_NAME = "IDOBJ";
	private static final String TIPOOBJ_COLUMN_NAME = "TIPOOBJ";
	private static final String MODULO_COLUMN_NAME = "MODULO";
	private static final String TS_COLUMN_NAME = "TS";
	private static final String IDUSUARIO_COLUMN_NAME = "IDUSUARIO";

	/** Definicion de las columnas */
	public static final DbColumnDef CAMPO_IDOBJ = new DbColumnDef(null,
			TABLE_NAME, IDOBJ_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_TIPOOBJ = new DbColumnDef(null,
			TABLE_NAME, TIPOOBJ_COLUMN_NAME, DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef CAMPO_MODULO = new DbColumnDef(null,
			TABLE_NAME, MODULO_COLUMN_NAME, DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef CAMPO_TS = new DbColumnDef(null,
			TABLE_NAME, TS_COLUMN_NAME, DbDataType.DATE_TIME, false);

	public static final DbColumnDef CAMPO_IDUSUARIO = new DbColumnDef(null,
			TABLE_NAME, IDUSUARIO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, true);

	/** Listado con las definiciones de las columnas */
	public static final DbColumnDef[] COLS_DEFS_BLOQUEO = { CAMPO_IDOBJ,
			CAMPO_TIPOOBJ, CAMPO_MODULO, CAMPO_TS, CAMPO_IDUSUARIO, };

	public static final DbColumnDef[] COLS_DEFS_BLOQUEO_TO_UPDATE = {
			CAMPO_TIPOOBJ, CAMPO_MODULO, CAMPO_TS, CAMPO_IDUSUARIO, };

	/** Nombre de las columnas. */
	public static final String COLUMN_NAMES_BLOQUEO = DbUtil
			.getColumnNames(COLS_DEFS_BLOQUEO);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Constructor por defecto de la clase
	 * 
	 * @param dataSource
	 *            TransactionManager de la entidad
	 */
	public LockDBEntityImplBase(DbDataSource dataSource) {
		super(dataSource);
	}

	public LockDBEntityImplBase(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene la información de un bloqueo.
	 * 
	 * @param idObj
	 *            Identificador del objeto.
	 * @param tipoObj
	 *            Tipo de objeto.
	 * @param modulo
	 *            Módulo donde se ha bloqueado.
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @return Información del bloqueo.
	 */
	public LockVO getBloqueo(String idObj, int tipoObj, int modulo,
			String idUsuario) {
		final String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPOOBJ, tipoObj))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDOBJ, idObj))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_MODULO, modulo))
				.append(" AND ")
				.append(DBUtils
						.generateEQTokenField(CAMPO_IDUSUARIO, idUsuario))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(CAMPO_IDUSUARIO,
						UsuarioDBEntityImpl.CAMPO_ID)).toString();

		// Mapeo de campos y tablas
		HashMap pairsTableNameColsDefs = new HashMap();
		pairsTableNameColsDefs.put(TABLE_NAME, COLS_DEFS_BLOQUEO);
		pairsTableNameColsDefs.put(UsuarioDBEntityImpl.TABLE_NAME,
				new DbColumnDef[] { UsuarioDBEntityImpl.CAMPO_NOMBRE,
						UsuarioDBEntityImpl.CAMPO_APELLIDOS });

		return (LockVO) getVO(qual, pairsTableNameColsDefs, LockVO.class);
	}

	/**
	 * Obtiene la información de un bloqueo.
	 * 
	 * @param idObj
	 *            Identificador del objeto.
	 * @param tipoObj
	 *            Tipo de objeto.
	 * @param modulo
	 *            Módulo donde se ha bloqueado.
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @return Información del bloqueo.
	 */
	public LockVO getBloqueo(String idObj, int tipoObj, int modulo) {
		final String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPOOBJ, tipoObj))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDOBJ, idObj))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_MODULO, modulo))

				.toString();

		// Mapeo de campos y tablas
		HashMap pairsTableNameColsDefs = new HashMap();
		pairsTableNameColsDefs.put(TABLE_NAME, COLS_DEFS_BLOQUEO);

		return (LockVO) getVO(qual, pairsTableNameColsDefs, LockVO.class);
	}

	/**
	 * Obtiene la información de un bloqueo y bloquea el registro en base de
	 * datos hasta cerrar la transacción.
	 * 
	 * @param idObj
	 *            Identificador del objeto.
	 * @param tipoObj
	 *            Tipo de objeto.
	 * @param modulo
	 *            Módulo donde se ha bloqueado.
	 * @return Información del bloqueo.
	 */
	public abstract LockVO getBloqueoForUpdate(String idObj, int tipoObj,
			int modulo);

	/**
	 * Inserta un nuevo bloqueo en la base de datos
	 * 
	 * @param bloqueo
	 *            Información del bloqueo.
	 */
	public void insertBloqueo(LockVO bloqueo) {
		insertVO(TABLE_NAME, COLS_DEFS_BLOQUEO, bloqueo);
	}

	/**
	 * Realiza la actualizacion de un bloqueo.
	 * 
	 * @param bloqueo
	 *            Información del bloqueo.
	 */
	public void updateBloqueo(LockVO bloqueo) {
		final String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateTokenFieldQualified(CAMPO_TIPOOBJ,
						bloqueo.getTipoObj(), TABLE_NAME, ConstraintType.EQUAL))
				.append(" AND ")
				.append(DBUtils.generateTokenFieldQualified(CAMPO_IDOBJ,
						bloqueo.getIdObj(), TABLE_NAME, ConstraintType.EQUAL))
				.append(" AND ")
				.append(DBUtils.generateTokenFieldQualified(CAMPO_MODULO,
						bloqueo.getModulo(), TABLE_NAME, ConstraintType.EQUAL))
				.toString();

		// TODO 1 revisar
		// deleteBloqueo(bloqueo.getIdObj(), bloqueo.getTipoObj(),
		// bloqueo.getModulo());
		// insertBloqueo(bloqueo);
		updateVO(qual, TABLE_NAME, COLS_DEFS_BLOQUEO_TO_UPDATE, bloqueo);
	}

	/**
	 * Elimina un bloqueo en la base de datos.
	 * 
	 * @param idObj
	 *            Identificador del objeto.
	 * @param tipoObj
	 *            Tipo de objeto.
	 * @param modulo
	 *            Módulo donde se ha bloqueado.
	 * @param idUsuario
	 *            Identificador del usuario que ha realizado el bloqueo.
	 */
	public void deleteBloqueo(String idObj, int tipoObj, int modulo,
			String idUsuario) {
		final String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPOOBJ, tipoObj))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDOBJ, idObj))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_MODULO, modulo))
				.append(" AND ")
				.append(DBUtils
						.generateEQTokenField(CAMPO_IDUSUARIO, idUsuario))
				.toString();

		deleteVO(qual, TABLE_NAME);
	}

	/**
	 * Elimina un bloqueo en la base de datos.
	 * 
	 * @param idObj
	 *            Identificador del objeto.
	 * @param tipoObj
	 *            Tipo de objeto.
	 * @param modulo
	 *            Módulo donde se ha bloqueado.
	 */
	public void deleteBloqueo(String idObj, int tipoObj, int modulo) {
		final String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPOOBJ, tipoObj))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDOBJ, idObj))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_MODULO, modulo))
				.toString();

		deleteVO(qual, TABLE_NAME);
	}
}
