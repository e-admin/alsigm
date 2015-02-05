package es.ieci.tecdoc.isicres.admin.core.database;

public class SicresLibroInformesTabla implements ISicresTabla{
	public static final String GET_TABLE_NAME_WITH_LIBRO_METHOD = "getTableNameWithLibro";	
	public static final String GET_ALL_COLUMN_NAMES_WITH_LIBRO = "getAllColumnNamesWithLibro";

	public static final String TABLE_NAME 			= "scr_reportarch";
	private static final String CN_ID           	= "id";
	private static final String CN_ID_REPORT      	= "id_report";	
	private static final String CN_ID_ARCH	       	= "id_arch";
	
	private static final String CN_ID_WITH_LIBRO =  IDocArchHDRTabla.TABLE_NAME + ".name";	
	private static final String CN_ID_WITH_CODE =   IDocArchHDRTabla.TABLE_NAME + ".id";
	
	private static final String ALL_COLUMN_NAMES = 	CN_ID + SEPARATOR + CN_ID_REPORT + SEPARATOR + CN_ID_ARCH;

	private static final String UPDATE_COLUMN_NAMES = CN_ID_REPORT + SEPARATOR + CN_ID_ARCH;
	
	private static final String ALL_COLUMN_NAMES_WITH_LIBRO = 
		TABLE_NAME + "." + CN_ID + SEPARATOR + 
		TABLE_NAME + "." + CN_ID_REPORT + SEPARATOR + 
		TABLE_NAME + "." + CN_ID_ARCH + SEPARATOR + 
		CN_ID_WITH_LIBRO + SEPARATOR +  
			CN_ID_WITH_CODE;
	
	public String getAllColumnNames() {
		return ALL_COLUMN_NAMES;
	}
	
	public String getTableNameWithLibro() {
		   return TABLE_NAME + ", " + IDocArchHDRTabla.TABLE_NAME;
	}	

	public String getAllColumnNamesWithLibro() {
		   return ALL_COLUMN_NAMES_WITH_LIBRO;
	}
	
	public String getLibrosByIdReport(int idReport) {
		StringBuffer sbAux = new StringBuffer("WHERE scr_reportarch.id_arch=idocarchhdr.id AND ").append(CN_ID_REPORT).append(" = ").append(idReport).append(" ");
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
	
	public String getByLibro(int libroId) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_ID_ARCH).append(" = ").append(libroId).append(" ");
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
