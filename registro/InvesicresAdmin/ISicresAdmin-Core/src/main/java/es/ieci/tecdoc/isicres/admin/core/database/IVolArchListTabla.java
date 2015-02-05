package es.ieci.tecdoc.isicres.admin.core.database;
/*$Id*/

import org.apache.log4j.Logger;

public class IVolArchListTabla {

	private static final Logger logger = Logger.getLogger(IVolArchListTabla.class);	

	private static final String SEPARATOR = ", ";
	public static final String TABLE_NAME = "ivolarchlist";
	private static final String CN_ARCHID = "archid";
	private static final String CN_LISTID = "listid";
	private static final String ALL_COLUMN_NAMES = CN_ARCHID + SEPARATOR + CN_LISTID;
	private static final String UPDATE_COLUMN_NAMES = CN_LISTID;
	
	public IVolArchListTabla() {
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
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_ARCHID).append(" = ").append(id).append(" ");
		return sbAux.toString();
	}

}
