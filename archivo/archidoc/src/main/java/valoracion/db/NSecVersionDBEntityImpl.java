package valoracion.db;

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
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.lang.MutableInt;

/**
 * Lógica de acceso a datos referentes al numero de version para las
 * valoraciones de series documentales
 */
public class NSecVersionDBEntityImpl extends DBEntity implements
		INSecVersionDBEntity {

	public static final String TABLE_NAME = "ASGFNUMSECVAL";

	public static final String ID_SERIE_COLUMN_NAME = "idserie";
	public static final String VERSION_COLUMN_NAME = "numsec";

	public static final DbColumnDef CAMPO_ID_SERIE = new DbColumnDef(
			ID_SERIE_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_VERSION = new DbColumnDef(
			VERSION_COLUMN_NAME, DbDataType.SHORT_INTEGER, 10, false);

	public static final DbColumnDef[] COLUMN_DEFINITIONS = { CAMPO_ID_SERIE,
			CAMPO_VERSION };

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
	 * Constructor por defecto.
	 * 
	 * @param dataSource
	 */
	public NSecVersionDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public NSecVersionDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	public class VersionValoracionVO {
		String idSerie = null;
		int numsec;

		public VersionValoracionVO(String idSerie, int numsec) {
			this.idSerie = idSerie;
			this.numsec = numsec;
		}

		public String getIdSerie() {
			return idSerie;
		}

		public void setIdSerie(String idSerie) {
			this.idSerie = idSerie;
		}

		public int getNumsec() {
			return numsec;
		}

		public void setNumsec(int numsec) {
			this.numsec = numsec;
		}
	}

	/**
	 * Incrementa el número de versión de las valoraciones de una serie.
	 * 
	 * @return Nuevo número de versión.
	 */
	public int incrementarNumeroVersion(final String idSerie) {
		final MutableInt numeroSecuencia = new MutableInt(1);
		final StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID_SERIE, idSerie));
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				try {
					String where = qual.toString();
					DbSelectFns.selectLongInteger(conn, TABLE_NAME,
							VERSION_COLUMN_NAME, where);
					DbUpdateFns.incrementField(conn, TABLE_NAME,
							VERSION_COLUMN_NAME, 1, where);
					numeroSecuencia.setValue(DbSelectFns.selectLongInteger(
							conn, TABLE_NAME, VERSION_COLUMN_NAME, where));
				} catch (IeciTdException ieciE) {
					SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
							COLUMN_DEFINITIONS, new VersionValoracionVO(
									idSerie, 1));

					DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
							inputRecord);
				}
			}
		};

		command.execute();
		return numeroSecuencia.getValue();
	}

	/**
	 * Decrementa el número de versión de las valoraciones de una serie.
	 * 
	 * @return Nuevo número de versión.
	 */
	public int decrementarNumeroVersion(final String idSerie) {
		final MutableInt numeroSecuencia = new MutableInt(1);
		final StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID_SERIE, idSerie));
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				try {
					String where = qual.toString();
					DbSelectFns.selectLongInteger(conn, TABLE_NAME,
							VERSION_COLUMN_NAME, where);
					DbUpdateFns.incrementField(conn, TABLE_NAME,
							VERSION_COLUMN_NAME, -1, where);
					numeroSecuencia.setValue(DbSelectFns.selectLongInteger(
							conn, TABLE_NAME, VERSION_COLUMN_NAME, where));
				} catch (IeciTdException ieciE) {

					// TODO: Mirar más a fondo: ¿Esto está bien???
					SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
							COLUMN_DEFINITIONS, new VersionValoracionVO(
									idSerie, 1));

					DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
							inputRecord);
				}
			}
		};

		command.execute();
		return numeroSecuencia.getValue();
	}

	/**
	 * Obtiene el número de versión de la última valoracion realizada sobre la
	 * serie
	 * 
	 * @param idSerie
	 *            Identificador de serie
	 * @return Número de versión de la última valoración realizada sobre la
	 *         serie
	 */
	public int getCurrentVersion(String idSerie) {
		final MutableInt numeroSecuencia = new MutableInt(0);
		final StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID_SERIE, idSerie));
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				try {
					numeroSecuencia.setValue(DbSelectFns.selectLongInteger(
							conn, TABLE_NAME, VERSION_COLUMN_NAME,
							qual.toString()));
				} catch (IeciTdException ieciE) {
				}
			}
		};
		command.execute();
		return numeroSecuencia.getValue();
	}
}
