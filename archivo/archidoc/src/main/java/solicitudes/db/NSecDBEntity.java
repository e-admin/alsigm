package solicitudes.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;
import ieci.core.exception.IeciTdException;
import solicitudes.consultas.vos.NSec;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.db.SigiaDbOutputRecord;

/**
 * Clase que se encarga de la generacion de las secuencias para prestamos y
 * consultas
 */
public abstract class NSecDBEntity extends DBEntity implements
		INSecSolicitudesDBEntity {
	public static final String TABLE_NAME = "ASGPSNSEC";

	public static final String ANO = "ano";

	public static final String NUMSEC = "numsec";

	public static final String TIPO = "tipo";

	public static final DbColumnDef CAMPO_ANO = new DbColumnDef(ANO,
			DbDataType.SHORT_TEXT, 4, false);

	public static final DbColumnDef CAMPO_NUMSEC = new DbColumnDef(NUMSEC,
			DbDataType.LONG_INTEGER, 10, false);

	public static final DbColumnDef CAMPO_TIPO = new DbColumnDef(TIPO,
			DbDataType.LONG_INTEGER, 32, false);

	public static final DbColumnDef[] COLUMN_DEFINITIONS = { CAMPO_ANO,
			CAMPO_NUMSEC, CAMPO_TIPO };

	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(COLUMN_DEFINITIONS);

	private static int TIPO_PRESTAMO = 1;
	private static int TIPO_CONSULTA = 2;
	/** Incremento en la secuencia */
	private static final int INCREMENTO_SECUENCIA = 1;

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public NSecDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public NSecDBEntity(DbConnection conn) {
		super(conn);
	}

	/**
	 * Incrementa el numero de secuencia para la tabla de prestamos.
	 * 
	 * @param ano
	 *            Año de creación del numero de secuencia
	 * @return Nuevo numero de la secuencia.
	 */
	public int incrementarNumeroSecConsulta(String ano) {
		return incrementarNumeroSec(ano, TIPO_CONSULTA, INCREMENTO_SECUENCIA);
	}

	/**
	 * Incrementa el numero de secuencia para la tabla de consultas.
	 * 
	 * @param ano
	 *            Año de creación del numero de secuencia
	 * @return Nuevo numero de la secuencia.
	 */
	public int incrementarNumeroSecPrestamo(String ano) {
		return incrementarNumeroSec(ano, TIPO_PRESTAMO, INCREMENTO_SECUENCIA);
	}

	/**
	 * Incrementa el numero de secuencia para la tabla de consultas.<b>NO CIERRA
	 * LA CONEXIÓN</b>
	 * 
	 * @param ano
	 *            Año de creación del numero de secuencia
	 * @param tipo
	 *            Tipo para el que se generara el numeo de secuencia.
	 * @return Nuevo numero de la secuencia.
	 */
	private int incrementarNumeroSec(final String ano, final int tipo,
			final int inc) {
		final String qual = new StringBuffer("WHERE")
				.append(DBUtils.generateEQTokenField(CAMPO_ANO, ano))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPO, tipo))
				.toString();

		final NSec numeroSecuencia = new NSec(ano, 1, tipo);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				try {
					DbSelectFns.selectLongInteger(conn, TABLE_NAME, NUMSEC,
							qual);
					DbUpdateFns.incrementField(conn, TABLE_NAME, NUMSEC, inc,
							qual);
					SigiaDbOutputRecord row = new SigiaDbOutputRecord(
							numeroSecuencia, COLUMN_DEFINITIONS);
					DbSelectFns.select(conn, TABLE_NAME, COLUM_NAMES_LIST,
							qual, row);
				} catch (IeciTdException ieciE) {
					SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
							COLUMN_DEFINITIONS, numeroSecuencia);
					DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
							inputRecord);
				}
			}
		};

		command.execute();
		return numeroSecuencia.getNumsec();
	}
}