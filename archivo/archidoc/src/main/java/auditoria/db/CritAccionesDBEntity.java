package auditoria.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.Collection;
import java.util.Iterator;

import auditoria.vos.ArchivoAction;
import auditoria.vos.CritAccionVO;

import common.db.ConstraintType;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;

/**
 * Clase que encapsula todas las definiciones de los datos de la criticidad de
 * las acciones, así como de las operaciones que se pueden realizar sobre ellos.
 */
public abstract class CritAccionesDBEntity extends DBEntity implements
		ICritAccionesDBEntity {

	/** Nombre de la tabla */
	public static final String TABLE_NAME = "AANAUDITACCION";

	/** Nombre de los campos de la tabla */
	private static final String IDMODULO_COLUMN_NAME = "MODULO";
	private static final String IDACCION_COLUMN_NAME = "ACCION";
	private static final String IDNIVEL_COLUMN_NAME = "NIVEL";

	/** Definicion de las columnas */
	public static final DbColumnDef CAMPO_IDMODULO = new DbColumnDef(null,
			TABLE_NAME, IDMODULO_COLUMN_NAME, DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef CAMPO_IDACCION = new DbColumnDef(null,
			TABLE_NAME, IDACCION_COLUMN_NAME, DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef CAMPO_IDNIVEL = new DbColumnDef(null,
			TABLE_NAME, IDNIVEL_COLUMN_NAME, DbDataType.LONG_INTEGER, false);

	/** Listado con las definiciones de las columnas */
	public static final DbColumnDef[] COLS_DEFS_CRITACCIONES = {
			CAMPO_IDMODULO, CAMPO_IDACCION, CAMPO_IDNIVEL, };

	public static final String COLUMN_NAMES_CRITACCIONES = DbUtil
			.getColumnNames(COLS_DEFS_CRITACCIONES);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public CritAccionesDBEntity(DbDataSource db) {
		super(db);
	}

	public CritAccionesDBEntity(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene el nivel de criticidad para una acción de un módulo
	 * 
	 * @param module
	 *            Modulo al que pertenece la acción
	 * @param action
	 *            Acción
	 * @return
	 */
	public int getActionLogLevel(int module, int action) {
		int actionLogLevel = Integer.MIN_VALUE;

		StringBuffer finalQual = new StringBuffer().append(" WHERE ").append(
				DBUtils.generateTokenFieldQualified(CAMPO_IDACCION, action,
						TABLE_NAME, ConstraintType.EQUAL));

		if (module >= 0)
			finalQual.append(" AND ").append(
					DBUtils.generateTokenFieldQualified(CAMPO_IDMODULO, module,
							TABLE_NAME, ConstraintType.EQUAL));

		// Obtenemos la criticidad
		CritAccionVO critAccionVO = (CritAccionVO) getVO(finalQual.toString(),
				TABLE_NAME, COLS_DEFS_CRITACCIONES, CritAccionVO.class);
		// Si está definida la criticidad la devolvemos
		if (critAccionVO != null)
			actionLogLevel = critAccionVO.getNivel();

		return actionLogLevel;
	}

	/**
	 * Obtiene el nivel de criticidad para una acción
	 * 
	 * @param module
	 *            identificador del modulo o menor de 1 si no se quierre
	 *            restringir por modulo
	 * @param action
	 *            identificador de la Acción
	 * @return Accion seleccionada
	 */
	public CritAccionVO getAction(int module, int action) {
		StringBuffer finalQual = new StringBuffer().append(" WHERE ").append(
				DBUtils.generateTokenFieldQualified(CAMPO_IDACCION, action,
						TABLE_NAME, ConstraintType.EQUAL));

		if (module >= 0)
			finalQual.append(" AND ").append(
					DBUtils.generateTokenFieldQualified(CAMPO_IDMODULO, module,
							TABLE_NAME, ConstraintType.EQUAL));

		// Obtenemos la criticidad
		CritAccionVO critAccionVO = (CritAccionVO) getVO(finalQual.toString(),
				TABLE_NAME, COLS_DEFS_CRITACCIONES, CritAccionVO.class);

		return critAccionVO;
	}

	/**
	 * Obtiene un listado de las acciones solicitadas con su nivel de log
	 * asociado.
	 * 
	 * @param actions
	 *            Acciones de las que deseamos obtener su nivel de log en la
	 *            aplicacion.
	 * @return Listado de las acciones solicitadas con su nivel
	 */
	public Collection getActionsWithLevels(Collection actions) {
		Iterator it = actions.iterator();

		while (it.hasNext()) {
			ArchivoAction action = (ArchivoAction) it.next();

			action.setLogLevel(this.getActionLogLevel(action.getIdModule(),
					action.getId()));
		}

		return actions;
	}

	/**
	 * Establece el nivel de log de una accion.
	 * 
	 * @param moduel
	 *            modulo al que pertenece la accion
	 * @param action
	 *            Accion de la que deseamos establecer el nivel de log.
	 * @param logLevel
	 *            Nivel de log de la accion
	 */
	public void setActionLogLevel(int module, int action, int logLevel) {
		final String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDACCION, action))
				.toString();

		CritAccionVO ca = new CritAccionVO();
		ca.setModulo(module);
		ca.setAccion(action);
		ca.setNivel(logLevel);

		updateVO(qual, TABLE_NAME, COLS_DEFS_CRITACCIONES, ca);
	}

	/**
	 * Crear el nivel de log de una accion.
	 * 
	 * @param module
	 *            Modulo al que pertenece la accion
	 * @param action
	 *            Accion de la que deseamos establecer el nivel de log.
	 * @param logLevel
	 *            Nivel de log de la accion
	 */
	public CritAccionVO insertActionLogLevel(int module, int action,
			int logLevel) {
		final CritAccionVO ca = new CritAccionVO();
		ca.setAccion(action);
		ca.setModulo(module);
		ca.setNivel(logLevel);

		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLS_DEFS_CRITACCIONES, ca);
				DbInsertFns.insert(conn, TABLE_NAME, COLUMN_NAMES_CRITACCIONES,
						inputRecord);
			}
		};

		command.execute();

		return ca;
	}

	/**
	 * Realiza el borrado de una accion con su criticidad asocidad de la tabla
	 * de la base de datos
	 * 
	 * @param module
	 *            Module al que pertenece la accion o -1 si se desconoce el
	 *            módulo
	 * @param action
	 *            Accion que se desea borrar
	 */
	public void deleteActionLogLevel(int module, int action) {
		final StringBuffer qual = new StringBuffer().append(" WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_IDACCION, action));

		if (module >= 0)
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CAMPO_IDMODULO, module));

		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual.toString());
			}
		};

		command.execute();
	}
}
