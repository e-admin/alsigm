package es.ieci.tecdoc.isicres.admin.core.database;

public class SicresOficinaInformesTabla  implements ISicresTabla {

	public static final String GET_TABLE_NAME_WITH_OFICINA_METHOD = "getTableNameWithOficina";	
	public static final String GET_ALL_COLUMN_NAMES_WITH_OFICINA = "getAllColumnNamesWithOficina";

	public static final String TABLE_NAME 			= "scr_reportofic";
	private static final String CN_ID           	= "id";
	private static final String CN_ID_REPORT      	= "id_report";
	private static final String CN_ID_OFIC	       	= "id_ofic";
	
	private static final String CN_ID_WITH_OFICINA =  SicresOficinaTabla.TABLE_NAME + ".name";	
	private static final String CN_ID_WITH_CODE =  SicresOficinaTabla.TABLE_NAME + ".code";
	
	private static final String ALL_COLUMN_NAMES = 	CN_ID + SEPARATOR + CN_ID_REPORT + SEPARATOR + CN_ID_OFIC;

	private static final String UPDATE_COLUMN_NAMES = CN_ID_REPORT + SEPARATOR + CN_ID_OFIC;
	
	private static final String ALL_COLUMN_NAMES_WITH_OFICINA = 
		TABLE_NAME + "." + CN_ID + SEPARATOR + 
		TABLE_NAME + "." + CN_ID_REPORT + SEPARATOR + 
		TABLE_NAME + "." + CN_ID_OFIC + SEPARATOR + 
		CN_ID_WITH_OFICINA + SEPARATOR +  
			CN_ID_WITH_CODE;
	
	public String getAllColumnNames() {
		return ALL_COLUMN_NAMES;
	}
	
	public String getTableNameWithOficina() {
		   return TABLE_NAME + ", " + SicresOficinaTabla.TABLE_NAME;
	}	

	public String getAllColumnNamesWithOficina() {
		   return ALL_COLUMN_NAMES_WITH_OFICINA;
	}
	
	public String getOficinasByIdReport(int idReport) {
		StringBuffer sbAux = new StringBuffer("WHERE id_ofic=scr_ofic.id AND ").append(CN_ID_REPORT).append(" = ").append(idReport).append(" ");
		return sbAux.toString();
	}	
	
	public String getById(int id) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_ID).append(" = ").append(id).append(" ");
		return sbAux.toString();
	}

	public String getByIdReport(int id) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_ID_REPORT).append(" = ").append(id).append(" ");
		return sbAux.toString();
	}	
		
	public String getOrderByDesc() {
		return "";
	}

	public String getTableName() {
		return TABLE_NAME;
	}

	public String getUpdateColumnNames() {
		return UPDATE_COLUMN_NAMES;
	}
}
