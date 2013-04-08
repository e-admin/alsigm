package common.db;

import ieci.core.db.DbOutputRecordSet;

public abstract class AbstractDbOutputRecordSet implements DbOutputRecordSet {

	protected int count = 0;

	public int getMaxNumItems() {
		return Integer.MAX_VALUE;
	}

	public boolean hasMoreItems() {
		return false;
	}
}
