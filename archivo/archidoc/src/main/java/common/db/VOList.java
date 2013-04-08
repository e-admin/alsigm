/*
 * Created on 08-nov-2005
 *
 */
package common.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbOutputRecord;
import ieci.core.db.DbOutputRecordSet;

import java.util.ArrayList;

public class VOList extends ArrayList implements DbOutputRecordSet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private DbColumnDef[] defs;
	private Class classObject;
	private int nElements;
	private int maxCapacity;
	protected int blockSize;
	protected int currentBlock = 0;

	public VOList(DbColumnDef[] defs, Class classObject) {
		this.defs = defs;
		this.classObject = classObject;
	}

	public DbOutputRecord newRecord() throws Exception {
		Object objVO = classObject.newInstance();
		SigiaDbOutputRecord row = new SigiaDbOutputRecord(objVO, defs);
		add(objVO);
		return row;
	}

	public int size() {
		return nElements;
	}

	public int getMaxNumItems() {
		return maxCapacity;
	}

	public void setMaxNumItems(int maxNumItems) {
		this.maxCapacity = maxNumItems;
	}

	public void setNumItems(int count) {
		this.nElements = count;
	}

	public boolean hasMoreItems() {
		return super.size() == this.nElements;
	}

	public DbColumnDef[] getSelectedColumns() {
		return this.defs;
	}
}