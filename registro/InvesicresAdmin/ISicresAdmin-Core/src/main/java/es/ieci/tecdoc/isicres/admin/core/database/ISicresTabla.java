package es.ieci.tecdoc.isicres.admin.core.database;

public interface ISicresTabla {
	public static final String SEPARATOR 			= ", ";
	public static String GET_TABLES_NAME_METHOD = "getTableName";
	public static String GET_COLUMN_NAMES_METHOD = "getAllColumnNames";
	public static final String GET_UPDATE_COLUMN_NAMES = "getUpdateColumnNames";
	
	public abstract String getUpdateColumnNames();

	public abstract String getTableName();

	public abstract String getAllColumnNames();

	public abstract String getById(int id);

	public abstract String getOrderByDesc();
	
}