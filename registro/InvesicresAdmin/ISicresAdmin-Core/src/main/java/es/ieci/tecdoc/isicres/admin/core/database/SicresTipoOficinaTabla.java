package es.ieci.tecdoc.isicres.admin.core.database;
/*$Id*/

import org.apache.log4j.Logger;

public class SicresTipoOficinaTabla {

	private static final Logger logger = Logger.getLogger(SicresTipoOficinaTabla.class);	

	private static final String SEPARATOR = ", ";
	public static final String TABLE_NAME = "scr_typeofic";
	private static final String CN_ID = "id";
	private static final String CN_DESCRIPTION = "description";
	private static final String ALL_COLUMN_NAMES = CN_ID + SEPARATOR + CN_DESCRIPTION;
	   
	  
	public SicresTipoOficinaTabla() {
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
	
	public String getOrderByDesc() {
		return "ORDER BY " + CN_DESCRIPTION;
	}

}
