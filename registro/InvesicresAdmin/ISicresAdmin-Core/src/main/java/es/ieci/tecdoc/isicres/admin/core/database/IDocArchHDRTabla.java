package es.ieci.tecdoc.isicres.admin.core.database;
/*$Id*/

import org.apache.log4j.Logger;

public class IDocArchHDRTabla {

	private static final Logger logger = Logger.getLogger(IDocArchHDRTabla.class);	

	private static final String SEPARATOR = ", ";
	public static final String TABLE_NAME = "idocarchhdr";
	private static final String CN_ID = TABLE_NAME + ".id";
	private static final String CN_NAME = "name";
	private static final String CN_TYPE = "type";
	private static final String CN_ACSID = "acsid";
	private static final String CN_IDARCHREG = "idarchreg";
	private static final String CN_STATE = "state";
	
	private static final String ALL_COLUMN_NAMES = CN_ID + SEPARATOR + CN_NAME + SEPARATOR + CN_TYPE + SEPARATOR + CN_ACSID + SEPARATOR + CN_STATE;
	private static final String UPDATE_COLUMN_NAMES = CN_NAME;
	
	public IDocArchHDRTabla() {
	}

	public String getUpdateColumnNames() {
		return UPDATE_COLUMN_NAMES;
	}

	public String getTableName() {
		return TABLE_NAME;
	}

	public String getTableNameWithBook() {
		return TABLE_NAME + ", " + SicresLibroEstadoTabla.TABLE_NAME;
	}

	public String getAllColumnNames() {
	   return ALL_COLUMN_NAMES;
	}

	public String getById(int id) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_ID).append(" = ").append(id).append(" ");
		return sbAux.toString();
	}
	
	public String getByIdWithBook(int id) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_ID).append(" = ").append(id).append(" ").append(" AND ")
			.append(CN_ID).append(" = ").append("idarchreg ");
		return sbAux.toString();
	}
	 
	public String getByTypeWithBook(int tipo) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_TYPE).append(" = ").append(tipo).append(" ").append(" AND ")
			.append(CN_ID).append(" = ").append(CN_IDARCHREG).append(" ");
		return sbAux.toString();
	}
	
	public String getOrderByDesc() {
		StringBuffer sbAux = new StringBuffer("ORDER BY ").append(CN_NAME);
		return sbAux.toString();
	}
}
