package se.autenticacion.idoc.api;

import ieci.tecdoc.core.db.DbOutputRecord;
import ieci.tecdoc.core.db.DbOutputRecordSet;

import java.util.ArrayList;

public class UasDaoUserRecOArrayList extends ArrayList implements
		DbOutputRecordSet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public UasDaoUserRecOArrayList() {
	}

	public DbOutputRecord newRecord() throws Exception {
		UasDaoUserRecO row = new UasDaoUserRecO();
		add(row);
		return row;
	}
}