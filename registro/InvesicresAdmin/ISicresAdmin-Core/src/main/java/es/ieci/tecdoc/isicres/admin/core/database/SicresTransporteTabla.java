package es.ieci.tecdoc.isicres.admin.core.database;
/*$Id*/


public class SicresTransporteTabla {

//	private static final Logger logger = Logger.getLogger(SicresTransporteTabla.class);	

	private static final String SEPARATOR = ", ";
	public static final String TABLE_NAME = "scr_tt";
	private static final String CN_ID = "id";
	private static final String CN_TRANSPORT = "transport";
	
	private static final String ALL_COLUMN_NAMES = CN_ID + SEPARATOR + CN_TRANSPORT;
	private static final String UPDATE_COLUMN_NAMES = CN_TRANSPORT;
	
	public SicresTransporteTabla() {}

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

	public String getOrderByDesc() {
		StringBuffer sbAux = new StringBuffer("ORDER BY ").append(CN_TRANSPORT);
		return sbAux.toString();
	}
}
