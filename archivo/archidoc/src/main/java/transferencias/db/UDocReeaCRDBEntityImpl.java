/**
 *
 */
package transferencias.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;
import transferencias.vos.UDocReeaCRVO;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class UDocReeaCRDBEntityImpl extends DBEntity implements
		IUDocReeaCRDBEntity {

	public static String TABLE_NAME = "ASGTUDOCREEACR";

	public static String ID_COLUMN_NAME = "ID";
	public static String IDRELENTREGA_COLUMN_NAME = "IDRELENTREGA";
	public static String IDUNIDADDOC_COLUMN_NAME = "IDUNIDADDOC";
	public static String SIGNATURAUDOC_COLUMN_NAME = "SIGNATURAUDOC";
	public static String IDUIDEPOSITO_COLUMN_NAME = "IDUIDEPOSITO";
	public static String SIGNATURAUI_COLUMN_NAME = "SIGNATURAUI";

	public static final DbColumnDef ID_FIELD = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef IDRELENTREGA_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDRELENTREGA_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef IDUNIDADDOC_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDUNIDADDOC_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			true);

	public static final DbColumnDef SIGNATURAUDOC_FIELD = new DbColumnDef(null,
			TABLE_NAME, SIGNATURAUDOC_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			true);

	public static final DbColumnDef IDUIDEPOSITO_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDUIDEPOSITO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef SIGNATURAUI_FIELD = new DbColumnDef(null,
			TABLE_NAME, SIGNATURAUI_COLUMN_NAME, DbDataType.SHORT_TEXT, 16,
			true);

	public static final DbColumnDef[] COLS_DEFS = new DbColumnDef[] { ID_FIELD,
			IDRELENTREGA_FIELD, IDUNIDADDOC_FIELD, SIGNATURAUDOC_FIELD,
			IDUIDEPOSITO_FIELD, SIGNATURAUI_FIELD };

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * @param dataSource
	 */
	public UDocReeaCRDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public UDocReeaCRDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocReeaCRDBEntity#deleteByIdRelacion(java.lang.String)
	 */
	public void deleteByIdRelacion(String idRelEntrega) {
		deleteVO(ROW_QUAL_IDRELACION(idRelEntrega), TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocReeaCRDBEntity#deleteByIdUIDeposito(java.lang.String,
	 *      java.lang.String)
	 */
	public void deleteByIdUIDeposito(String idRelEntrega, String idUIDeposito) {
		deleteVO(
				ROW_QUAL_IDUIDEPOSITO_IDRELENTREGA(idRelEntrega, idUIDeposito),
				TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocReeaCRDBEntity#deleteByIdsUIDeposito(java.lang.String,
	 *      java.lang.String[])
	 */
	public void deleteByIdsUIDeposito(String idRelEntrega,
			String[] idsUIDeposito) {
		deleteVO(
				ROW_QUAL_IDSUIDEPOSITO_IDRELENTREGA(idRelEntrega, idsUIDeposito),
				TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocReeaCRDBEntity#fetchRow(java.lang.String)
	 */
	public UDocReeaCRVO fetchRow(String id) {
		return (UDocReeaCRVO) getVO(ROW_QUAL(id), TABLE_NAME, COLS_DEFS,
				UDocReeaCRVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocReeaCRDBEntity#insert(transferencias.vos.UDocReeaCRVO)
	 */
	public void insert(final UDocReeaCRVO udocReeaCRVO) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				udocReeaCRVO.setId(getGuid(udocReeaCRVO.getId()));
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLS_DEFS, udocReeaCRVO);
				DbInsertFns.insert(conn, TABLE_NAME,
						DbUtil.getColumnNames(COLS_DEFS), inputRecord);
			}
		};
		command.execute();
	}

	public static String ROW_QUAL_IDRELACION(String idRelEntrega) {
		return new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(IDRELENTREGA_FIELD, idRelEntrega))
				.toString();
	}

	public static String ROW_QUAL_IDUIDEPOSITO_IDRELENTREGA(
			String idRelEntrega, String idUIDeposito) {
		return new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDRELENTREGA_FIELD,
						idRelEntrega))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(IDUIDEPOSITO_FIELD,
						idUIDeposito)).toString();
	}

	public static String ROW_QUAL_IDSUIDEPOSITO_IDRELENTREGA(
			String idRelEntrega, String[] idsUIDeposito) {
		return new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDRELENTREGA_FIELD,
						idRelEntrega))
				.append(DBUtils.AND)
				.append(DBUtils.generateInTokenField(IDUIDEPOSITO_FIELD,
						idsUIDeposito)).toString();
	}

	public static String ROW_QUAL(String id) {
		return new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(ID_FIELD, id)).toString();
	}

}
