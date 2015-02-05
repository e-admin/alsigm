package valoracion.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInputRecord;
import ieci.core.db.DbInputStatement;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;
import ieci.core.exception.IeciTdException;
import valoracion.vos.NSec;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.db.SigiaDbOutputRecord;

/**
 * Clase que envuelve el acceso a bd de la tabla que genera los numeros de
 * secuencia de las valoraciones.
 */
public class NSecValoracionDBEntityImpl extends DBEntity implements
		INSecValoracionDBEntity {
	public static final String TABLE_NAME = "ASGFSNSEC";

	public static final String NUMSEC = "numsec";

	public static final DbColumnDef CAMPO_NUMSEC = new DbColumnDef(NUMSEC,
			DbDataType.LONG_INTEGER, 10, false);

	public static final DbColumnDef[] COLUMN_DEFINITIONS = { CAMPO_NUMSEC };

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

	public static DbInputRecord createDbInputRecord(final NSec vo) {
		return new DbInputRecord() {
			/** Establece al statement los valores del objeto */
			public void setStatementValues(DbInputStatement stmt)
					throws Exception {
				int i = 1;

				stmt.setLongInteger(i++, vo.getNumsec());
			}
		};
	}

	/**
	 * Constructor por defecto.
	 * 
	 * @param dataSource
	 */
	public NSecValoracionDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public NSecValoracionDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Incrementa el numero de secuencia para la tabla de registros de
	 * valoracion. <b>NO CIERRA LA CONEXIÓN, ya que debe ser llamado dentro de
	 * una transacción</b>
	 * 
	 * @return Nuevo numero de la secuencia.
	 */
	public int incrementarNumeroSec() {
		final int inc = 1;
		final NSec numeroSecuencia = new NSec(1);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				try {
					DbSelectFns.selectLongInteger(conn, TABLE_NAME, NUMSEC,
							null);
					DbUpdateFns.incrementField(conn, TABLE_NAME, NUMSEC, inc,
							null);
					SigiaDbOutputRecord row = new SigiaDbOutputRecord(
							numeroSecuencia, COLUMN_DEFINITIONS);
					DbSelectFns.select(conn, TABLE_NAME, COLUM_NAMES_LIST,
							null, row);
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
