package ieci.core.db;

public interface DbOutputRecordSet {

	DbOutputRecord newRecord() throws Exception;

	int getMaxNumItems();

	boolean hasMoreItems();
} // interface
