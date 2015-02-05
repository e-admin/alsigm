package es.ieci.tecdoc.isicres.admin.core.database;
/*$Id*/

import org.apache.log4j.Logger;

public class SicresTipoOrganizacionTabla {

	private static final Logger logger = Logger.getLogger(SicresTipoOrganizacionTabla.class);	

	private static final String SEPARATOR = ", ";
	public static final String TABLE_NAME = "scr_typeadm";
	private static final String CN_ID = "id";
	private static final String CN_CODE = "code";	
	private static final String CN_DESCRIPTION = "description";
	private static final String ALL_COLUMN_NAMES = CN_ID + SEPARATOR + CN_CODE + SEPARATOR + CN_DESCRIPTION;
	private static final String UPDATE_COLUMN_NAMES = CN_DESCRIPTION;
	   
	  
	public SicresTipoOrganizacionTabla() {
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

	public String getByCode(String code) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_CODE).append(" = '").append(code).append("' ");
		return sbAux.toString();
	}
	
	public String getWithoutER() {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_ID).append(" <> ").append(0).append(" ");
		return sbAux.toString();
	}
	
	public String getOrderByDesc() {
		return "ORDER BY " + CN_DESCRIPTION;
	}

}
