package ieci.core.guid.impl;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbUtil;
import ieci.core.guid.GuidGenVO;
import ieci.core.guid.IGuidGenDBEntity;

import common.db.DBEntity;
import common.db.DbDataSource;

public class GuidGenDBEntityImpl extends DBEntity implements IGuidGenDBEntity {

	private static final String TABLE_NAME = "ITDGUIDGEN";

	// MAC Address
	private static final DbColumnDef CD_NODE = new DbColumnDef("CNODE",
			DbDataType.SHORT_TEXT, 12, false);

	// Last Proccess Id
	private static final DbColumnDef CD_LPID = new DbColumnDef("CLPID",
			DbDataType.LONG_INTEGER, false);

	private static final DbColumnDef[] COLS_DEFS = { CD_NODE, CD_LPID };

	private static final String COLUMN_NAMES = DbUtil.getColumnNames(COLS_DEFS);

	public GuidGenDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public GuidGenDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	public String getTableName() {
		return TABLE_NAME;
	}

	public String getGUID() {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(GuidGenVO guidGenVO) {

	}

	public void insert(GuidGenVO guidGenVO) {
		insertVO(TABLE_NAME, COLS_DEFS, guidGenVO);
	}

	public void resetLpid() {

	}

}
