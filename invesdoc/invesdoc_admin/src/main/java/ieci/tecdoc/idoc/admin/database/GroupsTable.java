package ieci.tecdoc.idoc.admin.database;

import ieci.tecdoc.sbo.uas.std.UasDaoGroupTbl;

import org.apache.log4j.Logger;


public class GroupsTable
{
	public GroupsTable()
	{	
	}
	
	/////////////////////////////////////////////////////////////////////////
   //                         Nombres de Tablas                           //
   /////////////////////////////////////////////////////////////////////////
   
	/**
    * Devuelve el nombre de la tabla de grupos de usuarios de invesDoc.
    * 
    * @return Las tabla mencionada. 
    */
    
   public String getBaseTableName()
   {
      return UasDaoGroupTbl.TN;
   }
   

   /////////////////////////////////////////////////////////////////////////
   //                         Nombres de Columnas                         //
   /////////////////////////////////////////////////////////////////////////
   
   /**
    * Devuelve todas las columnas de invesDoc de la tabla base de grupos  
    * para su inserción.
    * 
    * @return Las columnas mencionadas. 
    */
    
   public String getInsertBaseColumnNames()
   {
      String val;
      
      val = getBaseTableName() + "." +  UasDaoGroupTbl.CD_ID.getName() + 
             "," + getBaseTableName() + "." +  UasDaoGroupTbl.CD_NAME.getName() +
             "," + getBaseTableName() + "." +  UasDaoGroupTbl.CD_MGRID.getName() +
             "," + getBaseTableName() + "." +  UasDaoGroupTbl.CD_TYPE.getName() +
             "," + getBaseTableName() + "." +  UasDaoGroupTbl.CD_REMARKS.getName() +
             "," + getBaseTableName() + "." +  UasDaoGroupTbl.CD_CRTRID.getName() +
             "," + getBaseTableName() + "." +  UasDaoGroupTbl.CD_CRTNDATE.getName();
             
      return val;
   }
   
   /**
    * Devuelve todas las columnas de invesDoc de la tabla base de grupos  
    * para su actualización.
    * 
    * @return Las columnas mencionadas. 
    */
   public String getUpdateBaseColumnNames()
	{
   	String val; 
   	
   	val = getBaseTableName() + "." +  UasDaoGroupTbl.CD_NAME.getName() +
		      "," + getBaseTableName() + "." +  UasDaoGroupTbl.CD_MGRID.getName() +
		      "," + getBaseTableName() + "." +  UasDaoGroupTbl.CD_REMARKS.getName() +
		      "," + getBaseTableName() + "." +  UasDaoGroupTbl.CD_UPDRID.getName() +
		      "," + getBaseTableName() + "." +  UasDaoGroupTbl.CD_UPDATE.getName();
   	
   	return val;
   }
   
   /**
    * Devuelve todas las columnas de invesDoc de la tabla base de grupos
    * utilizadas en la carga de un grupo excepto el identificador.
    * 
    * @return Las columnas mencionadas. 
    */
    
   public String getLoadBaseColumnNames()
   {
      String val;
      
      val = getBaseTableName() + "." +  UasDaoGroupTbl.CD_ID.getName() +
            "," + getBaseTableName() + "." +  UasDaoGroupTbl.CD_NAME.getName() +
		      "," + getBaseTableName() + "." +  UasDaoGroupTbl.CD_MGRID.getName() +
		      "," + getBaseTableName() + "." +  UasDaoGroupTbl.CD_REMARKS.getName() +
		      "," + getBaseTableName() + "." +  UasDaoGroupTbl.CD_CRTRID.getName() +
		      "," + getBaseTableName() + "." +  UasDaoGroupTbl.CD_CRTNDATE.getName() +
		      "," + getBaseTableName() + "." +  UasDaoGroupTbl.CD_UPDRID.getName() +
		      "," + getBaseTableName() + "." +  UasDaoGroupTbl.CD_UPDATE.getName();
             
      return val;
   }
   
   /**
    * Devuelve las columnas identificador y nombre de la tabla de grupos.
    * 
    * @return Las columnas mencionadas.
    */
   public String getLoadBaseIdNameColumnNames()
	{
   	String val;
   	val = getBaseTableName() + "." +  UasDaoGroupTbl.CD_ID.getName() +
   			"," + getBaseTableName() + "." +  UasDaoGroupTbl.CD_NAME.getName();
   	return val;
   }
   
   /**
    * Obtiene el nombre de la columna, identificador del administrador del grupo.
    * @return El nombre mencionado.
    */
   public String getMgrGroupColumnName()
   {
      String val;
      
      val = getBaseTableName() + "." + UasDaoGroupTbl.CD_MGRID.getName();
      
      return val;
   }
   
   public String getUpdateMgrColumnNames()
   {
      String val;
      
      val = getBaseTableName() + "." + UasDaoGroupTbl.CD_MGRID.getName() +
            "," + getBaseTableName() + "." + UasDaoGroupTbl.CD_UPDRID.getName() +
            "," + getBaseTableName() + "." + UasDaoGroupTbl.CD_UPDATE.getName();
      
      return val;
   }
   
   /**
    * Obtiene el nombre de la columna identificador de grupo
    * @return La columna mencionada
    */
   public String getGroupIdColumnName()
   {
      String val;
      
      val = getBaseTableName() + "." + UasDaoGroupTbl.CD_ID.getName();
      
      return val;
   }
   
   /////////////////////////////////////////////////////////////////////////
   //                            Quals                                    //
   /////////////////////////////////////////////////////////////////////////
   
   /**
	 * Construye una expresión de búsqueda para calcular el número 
	 * de grupos que hay con el mismo nombre.
	 * 
	 * @param name El nombre del grupo.
	 * @return La expresión mencionada.
	 */
	public String getCountNameQual(String name)
	{
		String qual;
		qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoGroupTbl.CD_NAME.getName() + "= '" + name + "'";
		if (_logger.isDebugEnabled())
			_logger.debug("getCountNameQual: " + qual);
		return qual;
	}
	
	/**
	 * Construye una expresión de búsqueda para calcular el número 
	 * de grupos que hay con el mismo nombre y distinto 
	 * identificador de grupo. 
	 * 
	 * @param id Identificador del grupo.
	 * @param name El nombre del grupo.
	 * @return La expresión mencionada.
	 */
	public String getCountNameIdQual(int id, String name)
	{
		String qual;
		qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoGroupTbl.CD_NAME.getName() + "= '" + name + "' AND " 
				+ getBaseTableName() + "." + UasDaoGroupTbl.CD_ID.getName() 
				+ "<>" + Integer.toString(id);
		if (_logger.isDebugEnabled())
			_logger.debug("getCountNameIdQual: " + qual);
		return qual;
	}
	
	/**
    * Construye una expresión de búsqueda para la tabla base de grupos a 
    * partir del identificador.
    * 
    * @param id El identificador de grupo.
    * @return La expresión mencionada. 
    */

   public String getLoadBaseQual(int id)
   {
      String qual;
      
      qual = "WHERE " + getBaseTableName() + "." + 
      UasDaoGroupTbl.CD_ID.getName() + "=" + Integer.toString(id); 
      
      if (_logger.isDebugEnabled())
         _logger.debug("getLoadBaseQual: " + qual);
            
      return qual;
   }
   
   /**
    * Construye una expresión de búsqueda para eliminar el grupo de la tabla
    * principal.
    * 
    * @param id El identificador de grupo.
    * @return La expresión mencionada. 
    */

   public String getDeleteBaseQual(int id)
   {
      String qual;
      
      qual = "WHERE " + getBaseTableName() + "." + 
      UasDaoGroupTbl.CD_ID.getName() + "=" +  Integer.toString(id); 
      
      if (_logger.isDebugEnabled())
         _logger.debug("getDeleteBaseQual: " + qual);
         
      return qual;
   }
   
   public String getUpdateMgrQual(int userId)
   {
      String qual;
      
      qual = "WHERE " + getBaseTableName() + "." +
             UasDaoGroupTbl.CD_MGRID.getName() + "= " + Integer.toString(userId);
      
      if (_logger.isDebugEnabled())
         _logger.debug("getUpdateMgrQual: " + qual);
      
      return qual;
   }
	
	private static final Logger _logger = Logger.getLogger(GroupsTable.class);

}
