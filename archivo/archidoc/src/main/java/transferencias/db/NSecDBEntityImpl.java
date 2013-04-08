package transferencias.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;
import ieci.core.exception.IeciTdException;
import transferencias.vos.NSec;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.db.SigiaDbOutputRecord;

/**
 * Clase con los metodos encargados de recuperar y almacenar en la base de datos
 * la informacion referente a las secuencias empleadas para la numeracion de
 * relaciones y previsiones
 * 
 */
public class NSecDBEntityImpl extends DBEntity implements
		INSecTransferenciasDBEntity {

	/** Logger de la clase */
	// private static final Logger logger =
	// Logger.getLogger(NSecDBEntityImpl.class);

	/**
	 * Nombre de la tabla de la que se recupera y donde se almacena la
	 * información
	 */
	public static final String TABLE_NAME = "ASGTSNSEC";

	public static final String ANO_COLUMN_NAME = "ano";
	public static final String NUMSEC_COLUMN_NAME = "numsec";
	public static final String TIPO_COLUMN_NAME = "tipo";
	public static final String IDARCHIVO_COLUMN_NAME = "idarchivo";

	public static final DbColumnDef CAMPO_ANO = new DbColumnDef(
			ANO_COLUMN_NAME, DbDataType.SHORT_TEXT, 4, false);

	public static final DbColumnDef CAMPO_NUMSEC = new DbColumnDef(
			NUMSEC_COLUMN_NAME, DbDataType.LONG_INTEGER, 10, false);

	public static final DbColumnDef CAMPO_TIPO = new DbColumnDef(
			TIPO_COLUMN_NAME, DbDataType.LONG_INTEGER, 32, false);

	public static final DbColumnDef CAMPO_IDARCHIVO = new DbColumnDef(
			IDARCHIVO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef[] COLUMN_DEFINITIONS = { CAMPO_ANO,
			CAMPO_NUMSEC, CAMPO_TIPO, CAMPO_IDARCHIVO };

	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(COLUMN_DEFINITIONS);

	private static int TIPO_PREVISION = 1;
	private static int TIPO_RELACION = 2;
	private static int TIPO_REGISTRO = 3;
	private static int TIPO_INGRESO_DIRECTO = 4;

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public NSecDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public NSecDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Incrementa la secuencia con la que se contabilizan las previsiones
	 * 
	 * @param ano
	 *            La secuencia se reinicializa cada año
	 * @return Siguiente número en la secuencia
	 */
	public int incrementarNumeroSecPrevision(String ano, String idarchivo) {
		return incrementarNumeroSec(ano, TIPO_PREVISION, 1, idarchivo);
	}

	public int incrementarNumeroSecRelacion(String ano, String idarchivo) {
		return incrementarNumeroSec(ano, TIPO_RELACION, 1, idarchivo);
	}

	public int incrementarNumeroSecIngresoDirecto(String ano, String idarchivo) {
		return incrementarNumeroSec(ano, TIPO_INGRESO_DIRECTO, 1, idarchivo);
	}

	public int incrementarNumeroSecRegistro(String ano, String idarchivo) {
		return incrementarNumeroSec(ano, TIPO_REGISTRO, 1, idarchivo);
	}

	public int incrementarNumeroSecRegistro(String ano, int inc,
			String idarchivo) {
		return incrementarNumeroSec(ano, TIPO_REGISTRO, inc, idarchivo);
	}

	// TODO 1 CORREGIR PARA ACCESO CONCURRENTE!!!!!!!!!!!!
	private int incrementarNumeroSec(final String ano, final int tipo,
			final int inc, String idarchivo) {
		final String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_ANO, ano))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(CAMPO_IDARCHIVO, idarchivo))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_TIPO, tipo))
				.toString();

		final NSec numeroSecuencia = new NSec(ano, 1, tipo, idarchivo);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				try {
					DbSelectFns.selectLongInteger(conn, TABLE_NAME,
							NUMSEC_COLUMN_NAME, qual);
					DbUpdateFns.incrementField(conn, TABLE_NAME,
							NUMSEC_COLUMN_NAME, inc, qual);
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