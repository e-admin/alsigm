package es.ieci.tecdoc.isicres.admin.core.database;

public class SicresPerfilInformesTabla   implements ISicresTabla {

	public static final String GET_TABLE_NAME_WITH_PERFILES_METHOD = "getTableNameWithPerfil";	
	public static final String GET_ALL_COLUMN_NAMES_WITH_PERFILES = "getAllColumnNamesWithPerfil";

	public static final String TABLE_NAME 			= "scr_reportperf";
	private static final String CN_ID           	= "id";
	private static final String CN_ID_REPORT      	= "id_report";
	private static final String CN_ID_PERF	       	= "id_perf";
		
	private static final String ALL_COLUMN_NAMES = 	CN_ID + SEPARATOR + CN_ID_REPORT + SEPARATOR + CN_ID_PERF;

	private static final String UPDATE_COLUMN_NAMES = CN_ID_REPORT + SEPARATOR + CN_ID_PERF;
	
	private static final String ALL_COLUMN_NAMES_WITH_OFICINA = 
		TABLE_NAME + "." + CN_ID + SEPARATOR + 
		TABLE_NAME + "." + CN_ID_REPORT + SEPARATOR + 
		TABLE_NAME + "." + CN_ID_PERF; 
		//CN_ID_WITH_OFICINA + SEPARATOR +  
		//	CN_ID_WITH_CODE;
	
	public String getAllColumnNames() {
		return ALL_COLUMN_NAMES;
	}
	
	public String getTableNameWithPerfil() {
		   return TABLE_NAME;
	}	

	public String getAllColumnNamesWithPerfil() {
		   return ALL_COLUMN_NAMES_WITH_OFICINA;
	}
	
	public String getPerfilesByIdReport(int idReport) {
		//StringBuffer sbAux = new StringBuffer("WHERE id_ofic=scr_ofic.id AND ").append(CN_ID_REPORT).append(" = ").append(idReport).append(" ");
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_ID_REPORT).append(" = ").append(idReport).append(" ");
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
