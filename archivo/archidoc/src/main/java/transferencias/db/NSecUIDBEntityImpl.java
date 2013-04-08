package transferencias.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;
import ieci.core.exception.IeciTdException;

import common.ConfigConstants;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.lang.MutableLong;

public class NSecUIDBEntityImpl extends DBEntity implements INSecUIDBEntity {

	// private final static Logger logger =
	// Logger.getLogger(NSecUIDBEntityImpl.class);

	public static final String TABLE_NAME = "ASGTSNSECUI";
	public static final String NUMSEC = "numsec";
	public static final String ID_ARCHIVO = "idarchivo";

	public static final DbColumnDef CAMPO_NUMSEC = new DbColumnDef(NUMSEC,
			DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef CAMPO_ID_ARCHIVO = new DbColumnDef(
			ID_ARCHIVO, DbDataType.SHORT_TEXT, true);

	public static final DbColumnDef[] COLUMN_DEFINITIONS = { CAMPO_NUMSEC };
	public static final DbColumnDef[] COLUMN_DEFINITIONS_MULTIARCHIVO = {
			CAMPO_NUMSEC, CAMPO_ID_ARCHIVO };

	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(COLUMN_DEFINITIONS);
	public static final String COLUM_NAMES_LIST_MULTIARCHIVO = DbUtil
			.getColumnNames(COLUMN_DEFINITIONS_MULTIARCHIVO);

	public NSecUIDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public NSecUIDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	// TODO 1 CORREGIR PARA ACCESO CONCURRENTE!!!!!!!!!!!!
	public long incrementarNumeroSec(final long incremento,
			final String idArchivoReceptor) {
		final MutableLong siguienteNumeroSecuencia = new MutableLong(1);

		final StringBuffer qual = new StringBuffer();
		if (ConfigConstants.getInstance().getSignaturacionPorArchivo()) {
			qual.append(DBUtils.WHERE).append(
					DBUtils.generateEQTokenField(CAMPO_ID_ARCHIVO,
							idArchivoReceptor));
		}

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				try {
					/*
					 * int numeroSecuencia = DbSelectFns.selectLongInteger(conn,
					 * TABLE_NAME, NUMSEC, qual.toString());
					 */
					/*
					 * qual = new StringBuffer("WHERE ")
					 * .append(DBUtils.generateEQTokenField(CAMPO_NUMSEC,
					 * numeroSecuencia));
					 */
					DbUpdateFns.incrementField(conn, TABLE_NAME, NUMSEC,
							incremento, qual.toString());
					siguienteNumeroSecuencia.setValue(DbSelectFns
							.selectLongInteger(conn, TABLE_NAME, NUMSEC,
									qual.toString())
							- incremento);

				} catch (IeciTdException ieciE) {
					NSecUIVO newNumSec = new NSecUIVO(
							siguienteNumeroSecuencia.getValue() + incremento,
							idArchivoReceptor);

					if (ConfigConstants.getInstance()
							.getSignaturacionPorArchivo()) {
						SigiaDbInputRecord input = new SigiaDbInputRecord(
								COLUMN_DEFINITIONS_MULTIARCHIVO, newNumSec);
						DbInsertFns.insert(conn, TABLE_NAME,
								COLUM_NAMES_LIST_MULTIARCHIVO, input);
					} else {
						SigiaDbInputRecord input = new SigiaDbInputRecord(
								COLUMN_DEFINITIONS, newNumSec);
						DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
								input);
					}
				}
			}
		};
		command.execute();
		return siguienteNumeroSecuencia.getValue();

		/*
		 * LUIS int siguienteNumeroSecuencia = 1; try { try { int
		 * numeroSecuencia = DbSelectFns.selectLongInteger(conn,
		 * NSecUITableDefinition.TABLE_NAME,
		 * NSecUITableDefinition.CAMPO_NUMSEC.getName(),"");
		 * 
		 * DbUpdateFns.incrementField(conn, NSecUITableDefinition.TABLE_NAME,
		 * NSecUITableDefinition.CAMPO_NUMSEC.getName(),incremento, " WHERE "+
		 * NSecUITableDefinition.CAMPO_NUMSEC.getName()+"="+numeroSecuencia);
		 * 
		 * siguienteNumeroSecuencia = DbSelectFns.selectLongInteger(conn,
		 * NSecUITableDefinition.TABLE_NAME,
		 * NSecUITableDefinition.CAMPO_NUMSEC.getName(), "")- incremento ;
		 * 
		 * } catch (IeciTdException ieciE) { DbInsertFns.insert(conn,
		 * NSecUITableDefinition.TABLE_NAME,
		 * NSecUITableDefinition.COLUM_NAMES_LIST,
		 * NSecUITableDefinition.createDbInputRecord(new
		 * NSecUIVO(siguienteNumeroSecuencia+incremento))); }
		 * 
		 * return siguienteNumeroSecuencia;
		 * 
		 * } catch (Exception e) { throw e; }
		 */
	}
}
