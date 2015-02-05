package common.session.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.List;

import common.db.ConstraintType;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.session.vos.SessionVO;

/**
 * Clase que encapsula todas las definiciones de los datos de las trazas de
 * auditoría, así como de las operaciones que se pueden realizar sobre ellos.
 */
public class SessionDBEntity extends DBEntity implements ISessionDBEntity {

	/** Nombre de la tabla */
	public static final String TABLE_NAME = "AASESION";

	/** Nombre de los campos de la tabla */
	private static final String ID_COLUMN_NAME = "ID";
	private static final String IDUSUARIO_COLUMN_NAME = "IDUSUARIO";
	private static final String KEEPALIVE_COLUMN_NAME = "KEEPALIVE";

	/** Definicion de las columnas */
	public static final DbColumnDef CAMPO_ID = new DbColumnDef(ID_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_IDUSUARIO = new DbColumnDef(
			IDUSUARIO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_KEEPALIVE = new DbColumnDef(
			KEEPALIVE_COLUMN_NAME, DbDataType.DATE_TIME, false);

	/** Listado con las definiciones de las columnas */
	public static final DbColumnDef[] COLS_DEFS = { CAMPO_ID, CAMPO_IDUSUARIO,
			CAMPO_KEEPALIVE };

	public static final String COLUMN_NAMES = DbUtil.getColumnNames(COLS_DEFS);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Constructor por defecto de la clase.
	 * 
	 * @param dataSource
	 *            Gestor de transacciones
	 */
	public SessionDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public SessionDBEntity(DbConnection conn) {
		super(conn);
	}

	/**
	 * Inserta una nueva linea en la base de datos
	 * 
	 * @param sesion
	 *            Informacion de la sesion
	 * @return Información de la sesion almacenada
	 */
	public SessionVO insertSession(final SessionVO sesion) {

		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLS_DEFS, sesion);
				DbInsertFns.insert(conn, TABLE_NAME, COLUMN_NAMES, inputRecord);
			}
		};

		command.execute();

		return sesion;
	}

	/**
	 * Realiza la actualización de la sesion dada en la base de datos
	 * 
	 * @param sesion
	 *            Sesion que deseamos actualizar
	 */
	public void updateSession(SessionVO sesion) {
		String qual = new StringBuffer(" WHERE ")
				.append(DBUtils
						.generateTokenFieldQualified(CAMPO_IDUSUARIO,
								sesion.getIdUsuario(), TABLE_NAME,
								ConstraintType.EQUAL)).toString();

		updateVO(qual, TABLE_NAME, COLS_DEFS, sesion);
	}

	/**
	 * Realiza la actualización del keep-alive de una sesion dada en la base de
	 * datos
	 * 
	 * @param sesion
	 *            Sesion que deseamos actualizar
	 */
	public void updateKeepAlive(SessionVO sesion) {
		String qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateTokenFieldQualified(CAMPO_ID, sesion.getId(),
						TABLE_NAME, ConstraintType.EQUAL)).toString();

		DbColumnDef[] columns = { CAMPO_KEEPALIVE };
		updateVO(qual, TABLE_NAME, columns, sesion);
	}

	/**
	 * Realiza el borrado de una sesion dada por su identificador.
	 * 
	 * @param sesion
	 *            Identificador de la sesion.
	 */
	public void deleteSession(String id) {
		final String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id)).toString();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual);

			}
		};

		command.execute();
	}

	/**
	 * Obtiene una sesion a partir de su id
	 * 
	 * @param idSesion
	 *            Sesion que deseamos obtener
	 */
	public SessionVO getSession(String idSesion) {
		String qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateTokenFieldQualified(CAMPO_ID, idSesion,
						TABLE_NAME, ConstraintType.EQUAL)).toString();

		return (SessionVO) getVO(qual, TABLE_NAME, COLS_DEFS, SessionVO.class);
	}

	/**
	 * Obtiene una sesion a partir del id de usuario
	 * 
	 * @param idUser
	 *            id del usuario del que deseamos obtener la sesion
	 */
	public SessionVO getSessionUser(String idUser) {
		String qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateTokenFieldQualified(CAMPO_IDUSUARIO, idUser,
						TABLE_NAME, ConstraintType.EQUAL)).toString();

		return (SessionVO) getVO(qual, TABLE_NAME, COLS_DEFS, SessionVO.class);
	}

	public List getSessionesActivasActuales() {
		return getVOS(null, TABLE_NAME, COLS_DEFS, SessionVO.class);
	}
}
