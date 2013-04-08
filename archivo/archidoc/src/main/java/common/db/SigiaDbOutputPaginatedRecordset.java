package common.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbOutputPaginatedRecordSet;
import ieci.core.db.DbOutputRecord;

import common.pagination.PageInfo;
import common.pagination.PaginatedDataList;

public class SigiaDbOutputPaginatedRecordset extends PaginatedDataList
		implements DbOutputPaginatedRecordSet {
	private DbColumnDef[] defs;

	private Class classObject;

	public SigiaDbOutputPaginatedRecordset(DbColumnDef[] defs,
			Class classObject, PageInfo pageInfo) {
		super(pageInfo);
		this.defs = defs;
		this.classObject = classObject;
	}

	public DbOutputRecord newRecord() throws Exception {
		Object objVO = classObject.newInstance();
		SigiaDbOutputRecord row = new SigiaDbOutputRecord(objVO, defs);
		add(objVO);
		return row;
	}

	public int getMaxNumItems() {
		return getPageInfo().getObjectsPerPage();
	}

	public boolean hasMoreItems() {
		return false;
	}
}