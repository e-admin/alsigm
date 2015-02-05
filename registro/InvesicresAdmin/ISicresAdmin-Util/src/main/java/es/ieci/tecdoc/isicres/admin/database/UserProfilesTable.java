package es.ieci.tecdoc.isicres.admin.database;

public class UserProfilesTable {

	private static final String TN = "iuserusertype";
	
	protected static final String CN_ID_USUARIO = "userid";
	protected static final String CN_ID_APLICACION = "prodid";
	protected static final String CN_TYPE = "type";
	
	public UserProfilesTable() {}
	
	/**
	 * Devuelve el nombre completo de la tabla mapeada sin cualificar.
	 * @return El nombre de la tabla.
	 */
	public String getTableName() {
		return TN;
	}
	
	  /**
	   * Devuelve los nombres completos de todas las columnas mapeadas de la tabla,
	   * separados por comas.
	   * @return El nombre de las columnas.
	   */
	  public String getAllColumnNames() {
	     String val = CN_ID_USUARIO + ", " + CN_ID_APLICACION + ", " +  CN_TYPE;
	     return val;
	  }
	  
	  public String getInsertColumnNames(){
		  String val = CN_ID_USUARIO + ", " + CN_ID_APLICACION + ", " +  CN_TYPE;
		  return val;		  
	  }

	  public String getUpdateColumnNames(){
		  String val = CN_TYPE;
		  return val;		  
	  }

	  
	  public String getByIdUsuarioIdAplicacionQual(int idUsuario, int idAplicacion){
		  String condicion="where "+CN_ID_USUARIO+" = "+idUsuario+" and "+CN_ID_APLICACION+" = "+idAplicacion;
		  return condicion;
	  }
	
	
}
