package es.ieci.tecdoc.isicres.admin.core.database;

public class SicresDocumentoTipoAsuntoTabla implements ISicresTabla {
	
	public static final String TABLE_NAME 			= "scr_cadocs";
	private static final String CN_ID           	= "id";
	private static final String CN_ID_MATTER       	= "id_matter";
	private static final String CN_DESCRIPTION	   	= "description";
	private static final String CN_MANDATORY		= "mandatory";
	
	private static final String ALL_COLUMN_NAMES = 	CN_ID + SEPARATOR + CN_ID_MATTER + SEPARATOR + CN_DESCRIPTION + SEPARATOR + CN_MANDATORY;
	
	private static final String UPDATE_COLUMN_NAMES = CN_DESCRIPTION;
	
	public String getAllColumnNames() {
		return ALL_COLUMN_NAMES;
	}

	public String getById(int id) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_ID).append(" = ").append(id).append(" ");
		return sbAux.toString();
	}
	
	public String getByIdMatter(int id) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_ID_MATTER).append(" = ").append(id).append(" ");
		return sbAux.toString();
	}

	public String getOrderByDesc() {
		StringBuffer sbAux = new StringBuffer("ORDER BY ").append(CN_DESCRIPTION);
		return sbAux.toString();

	}

	public String getTableName() {
		return TABLE_NAME;
	}

	public String getUpdateColumnNames() {
		return UPDATE_COLUMN_NAMES;
	}

}
