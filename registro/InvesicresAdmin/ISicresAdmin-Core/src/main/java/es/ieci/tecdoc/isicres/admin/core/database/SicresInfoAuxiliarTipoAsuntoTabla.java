package es.ieci.tecdoc.isicres.admin.core.database;

public class SicresInfoAuxiliarTipoAsuntoTabla implements ISicresTabla {

	public static final String TABLE_NAME 			= "scr_caaux";
	private static final String CN_ID           	= "id";
	private static final String CN_ID_MATTER       	= "id_matter";
	private static final String CN_DATOS_AUX	   	= "datos_aux";
	
	private static final String ALL_COLUMN_NAMES = 	CN_ID + SEPARATOR + CN_ID_MATTER + SEPARATOR + CN_DATOS_AUX;
	
	private static final String UPDATE_COLUMN_NAMES = 	CN_ID_MATTER + SEPARATOR + CN_DATOS_AUX;
	
	public String getAllColumnNames() {
		return ALL_COLUMN_NAMES;
	}

	public String getById(int id) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_ID).append(" = ").append(id).append(" ");
		return sbAux.toString();
	}

	public String getByIdMatter(int idMatter) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_ID_MATTER).append(" = ").append(idMatter).append(" ");
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
