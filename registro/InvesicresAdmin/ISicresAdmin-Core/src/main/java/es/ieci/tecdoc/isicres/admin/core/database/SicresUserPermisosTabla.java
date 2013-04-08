package es.ieci.tecdoc.isicres.admin.core.database;
/*$Id*/

import org.apache.log4j.Logger;

public class SicresUserPermisosTabla {

	private static final Logger logger = Logger.getLogger(SicresUserPermisosTabla.class);	

	private static final String SEPARATOR = ", ";
	public static final String TABLE_NAME = "scr_usrperms";
	private static final String CN_ID = "id_usr";
	private static final String CN_TMSTAMP = "tmstamp";
	private static final String CN_PERMS = "perms";
	private static final String ALL_COLUMN_NAMES = CN_ID + SEPARATOR + CN_TMSTAMP + SEPARATOR + CN_PERMS;
	private static final String UPDATE_COLUMN_NAMES = CN_TMSTAMP + SEPARATOR + CN_PERMS;	   
	  
	public SicresUserPermisosTabla() {
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
