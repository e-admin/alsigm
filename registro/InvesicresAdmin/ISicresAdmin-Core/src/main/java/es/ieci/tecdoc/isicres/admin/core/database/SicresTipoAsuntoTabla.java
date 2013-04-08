package es.ieci.tecdoc.isicres.admin.core.database;
/*$Id*/


public class SicresTipoAsuntoTabla {

//	private static final Logger logger = Logger.getLogger(SicresTransporteTabla.class);	

	private static final String SEPARATOR 			= ", ";
	public static final String TABLE_NAME 			= "scr_ca";
	private static final String CN_ID           	= "id";
	private static final String CN_CODE         	= "code";
	private static final String CN_MATTER       	= "matter";
	private static final String CN_FOR_EREG     	= "for_ereg";
	private static final String CN_FOR_SREG     	= "for_sreg";
	private static final String CN_ALL_OFICS    	= "all_ofics";
	private static final String CN_ID_ARCH      	= "id_arch";
	private static final String CN_CREATION_DATE	= "creation_date";
	private static final String CN_DISABLE_DATE 	= "disable_date";
	private static final String CN_ENABLED      	= "enabled";
	private static final String CN_ID_ORG       	= "id_org";
	
	private static final String ALL_COLUMN_NAMES = 	CN_ID + SEPARATOR +
													CN_CODE + SEPARATOR +
													CN_MATTER + SEPARATOR +
													CN_FOR_EREG + SEPARATOR +
													CN_FOR_SREG + SEPARATOR +
													CN_ALL_OFICS + SEPARATOR +
													CN_ID_ARCH + SEPARATOR +
													CN_CREATION_DATE + SEPARATOR +
													CN_DISABLE_DATE + SEPARATOR +
													CN_ENABLED + SEPARATOR +
													CN_ID_ORG; 
		
	private static final String UPDATE_COLUMN_NAMES =   CN_CODE + SEPARATOR +
														CN_MATTER + SEPARATOR +
														CN_FOR_EREG + SEPARATOR +
														CN_FOR_SREG + SEPARATOR +
														CN_ALL_OFICS + SEPARATOR +
														CN_ID_ARCH + SEPARATOR +
														CN_CREATION_DATE + SEPARATOR +
														CN_DISABLE_DATE + SEPARATOR +
														CN_ENABLED + SEPARATOR +
														CN_ID_ORG;
	
	public SicresTipoAsuntoTabla() {}

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
	
	public String getByCode(String code){
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_CODE).append(" = '").append(code).append("' ");
		return sbAux.toString();
	}

	public String getOrderByDesc() {
		StringBuffer sbAux = new StringBuffer("ORDER BY ").append(CN_MATTER);
		return sbAux.toString();
	}
}
