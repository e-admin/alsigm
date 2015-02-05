package es.ieci.tecdoc.isicres.admin.core.database;
/*$Id*/

import org.apache.log4j.Logger;

public class SicresUserIdentificacionTabla {

	private static final Logger logger = Logger.getLogger(SicresUserIdentificacionTabla.class);	

	private static final String SEPARATOR = ", ";
	public static final String TABLE_NAME = "scr_usrident";
	private static final String CN_ID = "userid";
	private static final String CN_TMSTAMP = "tmstamp";
	private static final String CN_FIRST_NAME = "first_name";
	private static final String CN_SECOND_NAME = "second_name";
	private static final String CN_SURNAME = "surname";
	private static final String ALL_COLUMN_NAMES = CN_ID + SEPARATOR + CN_TMSTAMP + SEPARATOR + CN_FIRST_NAME + SEPARATOR + CN_SECOND_NAME + SEPARATOR + CN_SURNAME;
	private static final String UPDATE_COLUMN_NAMES = CN_TMSTAMP + SEPARATOR + CN_FIRST_NAME + SEPARATOR + CN_SECOND_NAME + SEPARATOR + CN_SURNAME;
	
	public SicresUserIdentificacionTabla() {
	}

	public String getUpdateColumnNames() {
		return UPDATE_COLUMN_NAMES;
	}
	   
	public String getTableName() {
	   return TABLE_NAME;
	}

	public String getAllColumnNames() {
	   return ALL_COLUMN_NAMES;
	}

	
	public String getById(int id) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_ID).append(" = ").append(id).append(" ");
		return sbAux.toString();
	}

}
