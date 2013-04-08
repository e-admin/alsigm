package es.ieci.tecdoc.isicres.admin.core.database;
/*$Id*/


public class SicresUsuarioConfigTabla {

	//private static final Logger logger = Logger.getLogger(SicresUsuarioConfigTabla.class);	
	private static final String SEPARATOR = ", ";
	public static final String TABLE_NAME = "scr_userconfig";
	public static final String CN_USERID = "userid";
	public static final String CN_DATA = "data";
	public static final String CN_IDOFICPREF = "idoficpref";
	
	private static final String ALL_COLUMN_NAMES = CN_USERID + SEPARATOR + CN_DATA + SEPARATOR + CN_IDOFICPREF;
	private static final String LOAD_BY_ID_COLUM_NAMES = CN_USERID + SEPARATOR + CN_IDOFICPREF; 
	
	private static final String UPDATE_COLUMN_NAMES = CN_IDOFICPREF;
	
	public SicresUsuarioConfigTabla() {
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
	
	public String getLoadByIdColumnNames() {
		return LOAD_BY_ID_COLUM_NAMES;
	}  

	public String getById(int userid) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_USERID).append(" = ").append(userid);
		return sbAux.toString();
	}
	
	public String getByIdOficPref(int idOficPref) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_IDOFICPREF).append(" = ").append(idOficPref);
		return sbAux.toString();
	}	

	public String getOrderByDesc() {
		return "";
	}	
	
}
