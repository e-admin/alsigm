package deposito.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;
import ieci.core.exception.IeciTdException;

import org.apache.commons.lang.mutable.MutableLong;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.lang.MutableInt;

public class NsecSigNumHuecoDBEntityImpl extends DBEntity implements
		INsecSigNumHuecoDBEntity {

	public static final String TABLE_NAME = "ASGDSIGNUMHUECO";
	public static final String ID_ARCHIVO = "IDARCHIVO";
	public static final String SIG_NUM_HUECO = "SIGNUMHUECO";

	public static final DbColumnDef CAMPO_SIGNUMHUECO = new DbColumnDef(
			SIG_NUM_HUECO, DbDataType.LONG, false);

	public static final DbColumnDef CAMPO_ID_ARCHIVO = new DbColumnDef(
			ID_ARCHIVO, DbDataType.SHORT_TEXT, true);

	public static final DbColumnDef[] COLUMN_DEFINITIONS = { CAMPO_SIGNUMHUECO,
			CAMPO_ID_ARCHIVO };

	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(COLUMN_DEFINITIONS);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public NsecSigNumHuecoDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public NsecSigNumHuecoDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	// TODO 1 CORREGIR PARA ACCESO CONCURRENTE!!!!!!!!!!!!
	/*
	 * (non-Javadoc)
	 * 
	 * @see deposito.db.INsecSigNumHueco#incrementarNumeroSec(int,
	 * java.lang.String)
	 */
	public int incrementarNumeroSec(final int incremento, final String idArchivo) {
		final MutableInt siguienteNumeroSecuencia = new MutableInt(1);

		final StringBuffer qual = new StringBuffer();
		qual.append(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(CAMPO_ID_ARCHIVO, idArchivo));

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				try {
					DbUpdateFns.incrementField(conn, TABLE_NAME, SIG_NUM_HUECO,
							incremento, qual.toString());
					siguienteNumeroSecuencia.setValue(DbSelectFns
							.selectLongInteger(conn, TABLE_NAME, SIG_NUM_HUECO,
									qual.toString())
							- incremento);
				} catch (IeciTdException ieciE) {
					NSecSigNumHuecoVO newNumSec = new NSecSigNumHuecoVO(
							siguienteNumeroSecuencia.getValue() + incremento,
							idArchivo);
					SigiaDbInputRecord input = new SigiaDbInputRecord(
							COLUMN_DEFINITIONS, newNumSec);
					DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
							input);
				}
			}
		};
		command.execute();
		return siguienteNumeroSecuencia.getValue();
	}

	/**
	 * Metodo que nos devuelve el numero de secuencia correspondiente sin
	 * actualizar la base de datos.
	 * 
	 * @param incremento
	 * @param idArchivo
	 * @return
	 */
	public int getNumeroSec(final int incremento, final String idArchivo) {
		final MutableInt siguienteNumeroSecuencia = new MutableInt(1);
		final StringBuffer qual = new StringBuffer();
		qual.append(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(CAMPO_ID_ARCHIVO, idArchivo));

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				try {
					// Si no devuelve ningun resultado se producira un error y
					// significa que es el primer elemento de le secuencia.
					siguienteNumeroSecuencia.setValue(DbSelectFns
							.selectLongInteger(getConnection(), TABLE_NAME,
									SIG_NUM_HUECO, qual.toString())
							+ incremento);
				} catch (IeciTdException e) {
					// En este caso devolverá el primer valor de la secuencia
					// (en este caso 1).
					siguienteNumeroSecuencia.setValue(siguienteNumeroSecuencia
							.getValue());
				}
			}
		};
		command.execute();
		return siguienteNumeroSecuencia.getValue();
	}

	public long getNumeroSecLong(final int incremento, final String idArchivo) {
		final MutableLong siguienteNumeroSecuencia = new MutableLong(1);
		final StringBuffer qual = new StringBuffer();
		qual.append(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(CAMPO_ID_ARCHIVO, idArchivo));

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				try {
					// Si no devuelve ningun resultado se producira un error y
					// significa que es el primer elemento de le secuencia.
					siguienteNumeroSecuencia.setValue(DbSelectFns.selectLong(
							getConnection(), TABLE_NAME, SIG_NUM_HUECO,
							qual.toString())
							+ incremento);
				} catch (IeciTdException e) {
					// En este caso devolverá el primer valor de la secuencia
					// (en este caso 1).
					siguienteNumeroSecuencia.setValue(siguienteNumeroSecuencia
							.getValue());
				}
			}
		};
		command.execute();
		return siguienteNumeroSecuencia.longValue();
	}

	/**
	 * Metodo que se encarga de establecer en base de datos el valor exacto de
	 * la secuencia para el archivo pasado como parametro.
	 * 
	 * @param valor
	 * @param idArchivo
	 */
	/*
	 * public void setNumeroSec(final int valor, final String idArchivo){ final
	 * StringBuffer qual = new StringBuffer();
	 * qual.append(DBUtils.WHERE).append(
	 * DBUtils.generateEQTokenField(CAMPO_ID_ARCHIVO, idArchivo));
	 * 
	 * DBCommand command = new DBCommand(this) { public void
	 * codeLogic(DbConnection conn) throws Exception { try { // Se actualiza el
	 * valor de la secuencia en la base de datos para el archivo especificado
	 * DbUpdateFns.incrementField(conn, TABLE_NAME, SIG_NUM_HUECO, valor,
	 * qual.toString()); // Si se produce algun error significa que no hay
	 * ningun registro para dicho archivo DbSelectFns.selectLongInteger(conn,
	 * TABLE_NAME, SIG_NUM_HUECO, qual.toString()); } catch (IeciTdException
	 * ieciE) { // En este caso se inserta dicho valor en la base de datos para
	 * el archivo especificado NSecSigNumHuecoVO newNumSec = new
	 * NSecSigNumHuecoVO(valor, idArchivo); SigiaDbInputRecord input = new
	 * SigiaDbInputRecord(COLUMN_DEFINITIONS, newNumSec);
	 * DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST, input); } } };
	 * command.execute(); }
	 */

	public void setNumeroSec(final long valor, final String idArchivo) {
		StringBuffer qual = new StringBuffer();
		qual.append(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(CAMPO_ID_ARCHIVO, idArchivo));

		try {
			if (!DbSelectFns.rowExists(getConnection(), TABLE_NAME,
					qual.toString())) {
				NSecSigNumHuecoVO newNumSec = new NSecSigNumHuecoVO(valor,
						idArchivo);
				SigiaDbInputRecord input = new SigiaDbInputRecord(
						COLUMN_DEFINITIONS, newNumSec);
				DbInsertFns.insert(getConnection(), TABLE_NAME,
						COLUM_NAMES_LIST, input);
			} else
				DbUpdateFns.updateLong(getConnection(), TABLE_NAME,
						SIG_NUM_HUECO, valor, qual.toString());
		} catch (Exception e) {
			logger.error(e);
			qual = new StringBuffer();
		}
	}

	/**
	 * Bloquea el registro si esta dentro de una transaccion
	 */
	public void bloquearNumeroSec(final String idArchivo) {
		StringBuffer sql = new StringBuffer("").append("UPDATE " + TABLE_NAME
				+ " SET " + SIG_NUM_HUECO + "=" + SIG_NUM_HUECO + " WHERE "
				+ ID_ARCHIVO + "='" + idArchivo + "'");

		try {
			DbUpdateFns.update(getConnection(), sql.toString());
		} catch (Exception e) {
			sql = new StringBuffer("");
		}
	}
}