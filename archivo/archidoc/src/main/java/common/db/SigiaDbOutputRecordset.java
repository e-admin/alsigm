package common.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbOutputRecord;
import ieci.core.db.DbOutputRecordSet;

import java.util.ArrayList;

import common.util.ArrayUtils;

public class SigiaDbOutputRecordset extends ArrayList implements
		DbOutputRecordSet {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private DbColumnDef[] defsQuery;
	private DbColumnDef[] defsFill;

	private Class classObject;

	public SigiaDbOutputRecordset(DbColumnDef[] defs, Class classObject) {
		this.defsQuery = defs;
		this.classObject = classObject;
	}

	public SigiaDbOutputRecordset(DbColumnDef[] defsQuery,
			DbColumnDef[] defsFill, Class classObject) {
		this.defsQuery = defsQuery;
		this.defsFill = defsFill;
		this.classObject = classObject;
	}

	public DbOutputRecord newRecord() throws Exception {
		Object objVO = classObject.newInstance();
		SigiaDbOutputRecord row = null;
		if (ArrayUtils.isNotEmpty(defsFill)) {
			row = new SigiaDbOutputRecord(objVO, null, defsFill);
		} else {
			row = new SigiaDbOutputRecord(objVO, defsQuery);
		}
		add(objVO);
		return row;
	}

	public int getMaxNumItems() {
		return Integer.MAX_VALUE;
	}

	public boolean hasMoreItems() {
		return false;
	}

}