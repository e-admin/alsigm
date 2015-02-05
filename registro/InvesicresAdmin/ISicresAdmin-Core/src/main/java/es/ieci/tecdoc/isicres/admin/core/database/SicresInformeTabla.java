package es.ieci.tecdoc.isicres.admin.core.database;

public class SicresInformeTabla {
	private static final String SEPARATOR 			= ", ";
	public static final String TABLE_NAME 			= "scr_reports";
	private static final String CN_ID           	= "id";
	private static final String CN_REPORT         	= "report";
	private static final String CN_TYPE_REPORT     	= "type_report";
	private static final String CN_TYPE_ARCH     	= "type_arch";
	private static final String CN_ALL_ARCH	     	= "all_arch";
	private static final String CN_ALL_OFICS    	= "all_ofics";
	private static final String CN_ALL_PERFS      	= "all_perfs";
	private static final String CN_DESCRIPTION		= "description";
	private static final String CN_DATA				= "data";
	
	
	private static final String COLUMN_NAMES 	= 	CN_ID + SEPARATOR +
													CN_REPORT + SEPARATOR +
													CN_TYPE_REPORT + SEPARATOR +
													CN_TYPE_ARCH + SEPARATOR +
													CN_ALL_ARCH + SEPARATOR +
													CN_ALL_OFICS + SEPARATOR +
													CN_ALL_PERFS + SEPARATOR +													
													CN_DESCRIPTION + SEPARATOR+
													CN_DATA;
	
	private static final String COLUMN_NAMES_LIST	 	= 	CN_ID + SEPARATOR +
															CN_REPORT + SEPARATOR +
															CN_TYPE_REPORT + SEPARATOR +
															CN_TYPE_ARCH + SEPARATOR +
															CN_ALL_ARCH + SEPARATOR +
															CN_ALL_OFICS + SEPARATOR +
															CN_ALL_PERFS + SEPARATOR +													
															CN_DESCRIPTION;
	
	private static final String ADD_COLUMN_NAMES 	= 	CN_ID + SEPARATOR +
														CN_REPORT + SEPARATOR +
														CN_TYPE_REPORT + SEPARATOR +
														CN_TYPE_ARCH + SEPARATOR +
														CN_ALL_ARCH + SEPARATOR +
														CN_ALL_OFICS + SEPARATOR +
														CN_ALL_PERFS + SEPARATOR +													
														CN_DESCRIPTION + SEPARATOR+
														CN_DATA;
	 
		
	private static final String UPDATE_COLUMN_NAMES =   CN_TYPE_REPORT + SEPARATOR +
														CN_TYPE_ARCH + SEPARATOR +
														CN_ALL_ARCH + SEPARATOR +
														CN_ALL_OFICS + SEPARATOR +
														CN_ALL_PERFS + SEPARATOR +
														CN_DESCRIPTION;
	
	private static final String ALL_UPDATE_COLUMN_NAMES =   CN_REPORT + SEPARATOR +
															CN_TYPE_REPORT + SEPARATOR +
															CN_TYPE_ARCH + SEPARATOR +
															CN_ALL_ARCH + SEPARATOR +
															CN_ALL_OFICS + SEPARATOR +
															CN_ALL_PERFS + SEPARATOR +
															CN_DESCRIPTION  + SEPARATOR+
															CN_DATA;
														
	public SicresInformeTabla() {
		// TODO Auto-generated constructor stub
	}

	public String getUpdateColumnNames() {
		return UPDATE_COLUMN_NAMES;
	}
	
	public String getALLUpdateColumnNames() {
		return ALL_UPDATE_COLUMN_NAMES;
	}
	
	public String getTableName() {
	   return TABLE_NAME;
	}
	
	public String getColumnNames() {
	   return COLUMN_NAMES;
	}
	
	public String getAddColumnNames(){
		return ADD_COLUMN_NAMES;
	}
	
	public String getColumnsNamesList(){
		return COLUMN_NAMES_LIST;
	}
	
	public String getById(int id) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_ID).append(" = ").append(id).append(" ");
		return sbAux.toString();
	}

	public String getOrderByDesc() {
		StringBuffer sbAux = new StringBuffer("ORDER BY ").append(CN_TYPE_REPORT);
		return sbAux.toString();
	}
}
