package transferencias.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;
import ieci.core.exception.IeciTdException;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.lang.MutableInt;

public class NSecUDocDBEntityImpl extends DBEntity implements INSecUDocDBEntity {

	/**
	 * Nombre de la tabla
	 */
	public static final String TABLE_NAME = "ASGTSNSECUDOC";

	/**
	 * Nombre del campo número secuencial
	 */
	public static final String NUMSEC = "numsec";

	/**
	 * Campo número secuencial
	 */
	public static final DbColumnDef CAMPO_NUMSEC = new DbColumnDef(NUMSEC,
			DbDataType.LONG_INTEGER, false);

	/**
	 * Definición de las columnas de la tabla
	 */
	public static final DbColumnDef[] COLUMN_DEFINITIONS = { CAMPO_NUMSEC };

	/**
	 * String con los nombres de las columnas
	 */
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

	/**
	 * Constructor
	 * 
	 * @param dataSource
	 *            Datasource necesario
	 */
	public NSecUDocDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public NSecUDocDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see transferencias.db.INSecUDocDBEntity#incrementarNumeroSec(int)
	 */
	public int incrementarNumeroSec(final int incremento) {

		// TODO 1 CORREGIR PARA ACCESO CONCURRENTE!!!!!!!!!!!!

		final MutableInt siguienteNumeroSecuencia = new MutableInt(1);

		final StringBuffer qual = new StringBuffer();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				try {
					DbUpdateFns.incrementField(conn, TABLE_NAME, NUMSEC,
							incremento, qual.toString());
					siguienteNumeroSecuencia.setValue(DbSelectFns
							.selectLongInteger(conn, TABLE_NAME, NUMSEC,
									qual.toString())
							- incremento);
				} catch (IeciTdException ieciE) {
					NSecUDocVO newNumSec = new NSecUDocVO(
							siguienteNumeroSecuencia.getValue() + incremento);
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
}
