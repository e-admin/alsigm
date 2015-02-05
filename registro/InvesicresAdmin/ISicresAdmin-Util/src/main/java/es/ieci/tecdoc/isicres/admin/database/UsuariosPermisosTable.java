package es.ieci.tecdoc.isicres.admin.database;


public class UsuariosPermisosTable {

	private static final String TN="iuserusertype";
	private static final String TN_IUSERUSERTYPE = "iuserusertype";
	private static final String TN_IUSERUSERHDR = "iuseruserhdr";
	private static final String TN_SCR_USRIDENT = "scr_usrident";

	protected static final String CN_USERID= "userid";
	protected static final String CN_ID_USUARIO= "id_usuario";
	protected static final String CN_NAME = "name";
	protected static final String CN_SURNAME = "surname";
	protected static final String CN_FIRST_NAME = "first_name";
	protected static final String CN_SECOND_NAME = "second_name";
	protected static final String CN_PRODID= "prodid";
	protected static final String CN_TYPE= "type";
	protected static final String CN_ID= "id";

	public UsuariosPermisosTable() {
	}

	/**
	 * Devuelve el nombre completo de la tabla mapeada sin cualificar.
	 * @return El nombre de la tabla.
	 */
	public String getTableName() {
		return TN;
	}

	  /**
	   * Devuelve el nombre completo de la columna identificador
	   * @return El nombre de dicha columna.
	   */
	  public String getIdUsuarioColumnName() {
	     return CN_USERID;
	  }

	  /**
	   * Devuelve el nombre completo de la columna id_aplicaciones
	   * @return El nombre de dicha columna.
	   */
	  public String getIdAplicacionesUsuarioColumnName() {
	     return CN_PRODID;
	  }

	  /**
	   * Devuelve los nombres completos de todas las columnas mapeadas de la tabla,
	   * separados por comas.
	   * @return El nombre de las columnas.
	   */
	  public String getAllColumnNames() {
	     String val = TN_IUSERUSERTYPE + "." +CN_USERID  + ", "
	     			+ TN_IUSERUSERHDR+"."+ CN_NAME + ", "
	     			+ TN_SCR_USRIDENT+"."+CN_SURNAME + ", "
	     			+ TN_SCR_USRIDENT+"."+CN_FIRST_NAME + ", "
	     			+ TN_SCR_USRIDENT+"."+CN_SECOND_NAME + ", "
	     			+ TN_IUSERUSERTYPE + "." + CN_PRODID+ ", "
	     			+ TN_IUSERUSERTYPE + "." + CN_TYPE;
	     return val;
	  }

	  public String getInsertColumnNames(){
		  String val= CN_USERID + " , " + CN_PRODID + " , " + CN_TYPE;
		  return val;
	  }


	  public String getUpdateColumnNames(){
		  return CN_TYPE;
	  }


	  /**
	   * Devuelve la cláusula de consulta con el valor del identificador que se
	   * pasa como parámetro.
	   * @param id Identificador del usuario
	   * @return La cláusula.
	   */
	  public String getByIdUsQual(int id) {
		  String condicion ="left join " + TN_IUSERUSERHDR +" on "+TN_IUSERUSERTYPE+"."+CN_USERID+"="+TN_IUSERUSERHDR+"."+ CN_ID +
		  					" left join "+TN_SCR_USRIDENT+" on "+TN_IUSERUSERTYPE+"."+CN_USERID+"="+TN_SCR_USRIDENT+"."+CN_ID_USUARIO+
		  					" WHERE " + CN_USERID + " = '" + id + "'"+
		  					" order by "+CN_PRODID;
	     return condicion;
	  }

	  /**
	   * Devuelve la cláusula de consulta con el valor del identificador del usuario y de la aplicacion
	   * @param idUsuario Identificador del usuario
	   * @param idAplicacion Identificador dxe la aplicacion
	   * @return La cláusula.
	   */
	  public String getByIdUsApOrderQual(int idUsuario, int idAplicacion) {
		  String condicion ="left join " + TN_IUSERUSERHDR +" on "+TN_IUSERUSERTYPE+"."+CN_USERID+"="+TN_IUSERUSERHDR+"."+ CN_ID +
		  					" left join "+TN_SCR_USRIDENT+" on "+TN_IUSERUSERTYPE+"."+CN_USERID+"="+TN_SCR_USRIDENT+"."+CN_USERID+
		  					" WHERE " + TN_IUSERUSERTYPE + "." + CN_USERID + " = '" + idUsuario + "' and " + TN_IUSERUSERTYPE + "." + CN_PRODID +" = '" + idAplicacion + "'" +
		  					" order by "+CN_PRODID;
	     return condicion;
	  }

	  public String getByIdUsApQual(int idUsuario, int idAplicacion) {
		  String condicion ="left join " + TN_IUSERUSERHDR +" on "+TN_IUSERUSERTYPE+"."+CN_USERID+"="+TN_IUSERUSERHDR+"."+ CN_ID +
		  					" left join "+TN_SCR_USRIDENT+" on "+TN_IUSERUSERTYPE+"."+CN_USERID+"="+TN_SCR_USRIDENT+"."+CN_USERID+
		  					" WHERE " + TN_IUSERUSERTYPE + "." + CN_USERID + " = '" + idUsuario + "' and " + TN_IUSERUSERTYPE + "." + CN_PRODID +" = '" + idAplicacion + "'";
	     return condicion;
	  }

	  public String getByIdQual(int idUsuario) {
		  String condicion ="left join " + TN_IUSERUSERHDR +" on "+TN_IUSERUSERTYPE+"."+CN_USERID+"="+TN_IUSERUSERHDR+"."+ CN_ID +
		  					" left join "+TN_SCR_USRIDENT+" on "+TN_IUSERUSERTYPE+"."+CN_USERID+"="+TN_SCR_USRIDENT+"."+CN_USERID+
		  					" WHERE " + TN_IUSERUSERTYPE + "." + CN_USERID + " = '" + idUsuario + "' AND " + CN_TYPE + " = 3";
	     return condicion;
	  }

	  public String getByIdQual(int idUsuario, int idAplicacion) {
		  String condicion ="left join " + TN_IUSERUSERHDR +" on "+TN_IUSERUSERTYPE+"."+CN_USERID+"="+TN_IUSERUSERHDR+"."+ CN_ID +
		  					" left join "+TN_SCR_USRIDENT+" on "+TN_IUSERUSERTYPE+"."+CN_USERID+"="+TN_SCR_USRIDENT+"."+CN_USERID+
		  					" WHERE " + TN_IUSERUSERTYPE + "." + CN_USERID + " = '" + idUsuario + "' and " + TN_IUSERUSERTYPE + "." + CN_PRODID +" = '" + idAplicacion + "'";
	     return condicion;
	  }

	  public String getLeftJoin(){
		  String condicion ="left join " + TN_IUSERUSERHDR +" on "+TN_IUSERUSERTYPE+"."+CN_USERID+"="+TN_IUSERUSERHDR+"."+ CN_ID +
			" left join "+TN_SCR_USRIDENT+" on "+TN_IUSERUSERTYPE+"."+CN_USERID+"="+TN_SCR_USRIDENT+"."+CN_USERID +
			" WHERE " + CN_TYPE + " = 3";
		  return condicion;
	  }


	  public String getColumnNamesSearch(){
		  return "id, name, id_aplicaciones, surname, first_name, second_name";
	  }

	  public String getTableNameSearch(){
		  return "scr_permisos";
	  }


//	  public String getByCriterioQual(CriterioBusqueda criterio){
//		  String condicion="";
//		  if(criterio.getNombreUsuario()!=null && criterio.getNombreUsuario().length()>0)
//			  condicion+="name like'%"+criterio.getNombreUsuario()+"%'";
//		  if(criterio.getNombreReal()!=null && criterio.getNombreReal().length() > 0){
//			  if(condicion.length() > 0)
//				  condicion+=" or ";
//			  condicion+="surname like'%"+criterio.getNombreReal()+"%'";
//		  }
//		  if(criterio.getApellidos()!=null && criterio.getApellidos().length() > 0){
//			  if(condicion.length() > 0)
//				  condicion+=" or ";
//			  condicion+="first_name like'%"+criterio.getApellidos()+"%' or second_name like'%"+criterio.getApellidos()+"%'";
//		  }
//
//		  String resul="left join iuseruserhdr on scr_permisos.id_usuario=iuseruserhdr.id " +
//		  		 "left join scr_usrident on scr_permisos.id_usuario=scr_usrident.userid ";
//
//		  String res ="left join " + TN_IUSERUSERHDR +" on "+TN_IUSERUSERTYPE+"."+CN_USERID+"="+TN_IUSERUSERHDR+"."+ CN_ID +
//			" left join "+TN_SCR_USRIDENT+" on "+TN_IUSERUSERTYPE+"."+CN_USERID+"="+TN_SCR_USRIDENT+"."+CN_USERID;
//
//
//		  if(condicion.length()<=0)
//			  return res;
//		  else
//			  return res+" where ( "+condicion+" and "+ CN_TYPE + " = 3 )";
//	  }


}
