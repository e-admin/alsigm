package ieci.tecdoc.sgm.usuarios_backoffice.database;

import ieci.tecdoc.sgm.base.dbex.DbOutputRecord;
import ieci.tecdoc.sgm.base.dbex.DbOutputRecordSet;

import java.util.ArrayList;


public class UasDaoUserRecOArrayList extends ArrayList implements
		DbOutputRecordSet {

	public UasDaoUserRecOArrayList() {
		super();
	}

	public DbOutputRecord newRecord() throws Exception {
		UasDaoUserRecO row = new UasDaoUserRecO();
		add(row);
		return row;
	}
}