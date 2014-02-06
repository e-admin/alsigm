package ieci.tecdoc.idoc.admin.database;

import org.apache.log4j.Logger;

import ieci.tecdoc.sbo.uas.std.UasDaoDeptTbl;


public class DepartmentsTable
{
	/////////////////////////////////////////////////////////////////////////
   //                         Nombres de Tablas                           //
   /////////////////////////////////////////////////////////////////////////
	
	/**
    * Devuelve el nombre tabla base invesDoc de departamentos.
    * 
    * @return Las tabla mencionada. 
    */
    
   public String getBaseTableName()
   {
      return UasDaoDeptTbl.TN;
   }
	
	/////////////////////////////////////////////////////////////////////////
   //                         Nombres de Columnas                         //
   /////////////////////////////////////////////////////////////////////////
   
   /**
    * Devuelve todas las columnas de invesDoc de la tabla base de departamentos  
    * para su inserción.
    * 
    * @return Las columnas mencionadas. 
    */
    
   public String getInsertBaseColumnNames()
   {
      String val;
      
      val = getBaseTableName() + "." +  UasDaoDeptTbl.CD_ID.getName() + 
             "," + getBaseTableName() + "." +  UasDaoDeptTbl.CD_NAME.getName() +
             "," + getBaseTableName() + "." +  UasDaoDeptTbl.CD_PARENTID.getName() +
             "," + getBaseTableName() + "." +  UasDaoDeptTbl.CD_MGRID.getName() +
             "," + getBaseTableName() + "." +  UasDaoDeptTbl.CD_TYPE.getName() +
             "," + getBaseTableName() + "." +  UasDaoDeptTbl.CD_REMARKS.getName() +
             "," + getBaseTableName() + "." +  UasDaoDeptTbl.CD_CRTRID.getName() +
             "," + getBaseTableName() + "." +  UasDaoDeptTbl.CD_CRTNDATE.getName();
             
      return val;
   }
   
   /**
    * Devuelve todas las columnas de invesDoc de la tabla base de departamentos  
    * para su actualización.
    * 
    * @return Las columnas mencionadas. 
    */
   public String getUpdateBaseColumnNames()
	{
   	String val; 
   	
   	val = getBaseTableName() + "." +  UasDaoDeptTbl.CD_NAME.getName() +
		      "," + getBaseTableName() + "." +  UasDaoDeptTbl.CD_MGRID.getName() +
		      "," + getBaseTableName() + "." +  UasDaoDeptTbl.CD_PARENTID.getName() +
		      "," + getBaseTableName() + "." +  UasDaoDeptTbl.CD_REMARKS.getName() +
		      "," + getBaseTableName() + "." +  UasDaoDeptTbl.CD_UPDRID.getName() +
		      "," + getBaseTableName() + "." +  UasDaoDeptTbl.CD_UPDATE.getName();
   	
   	return val;
   }
   
   /**
    * Devuelve las columnas de invesDoc de la tabla base de departamentos  
    * para su actualización del administrador del departamento.
    * 
    * @return Las columnas mencionadas. 
    */
   public String getUpdateMgrColumnNames()
   {
      String val;
      
      val = getBaseTableName() + "." + UasDaoDeptTbl.CD_MGRID.getName() +
            "," + getBaseTableName() + "." + UasDaoDeptTbl.CD_UPDRID.getName() +
            "," + getBaseTableName() + "." + UasDaoDeptTbl.CD_UPDATE.getName();
      
      return val;
   }
   
   /**
    * Devuelve todas las columnas de invesDoc de la tabla base de departamentos
    * utilizadas en la carga de un departamento excepto el identificador.
    * 
    * @return Las columnas mencionadas. 
    */
    
   public String getLoadBaseColumnNames()
   {
      String val;
      
      val = getBaseTableName() + "." +  UasDaoDeptTbl.CD_ID.getName() +
            "," + getBaseTableName() + "." +  UasDaoDeptTbl.CD_NAME.getName() +
            "," + getBaseTableName() + "." +  UasDaoDeptTbl.CD_PARENTID.getName() +
		      "," + getBaseTableName() + "." +  UasDaoDeptTbl.CD_MGRID.getName() +
		      "," + getBaseTableName() + "." +  UasDaoDeptTbl.CD_TYPE.getName() +
		      "," + getBaseTableName() + "." +  UasDaoDeptTbl.CD_REMARKS.getName() +
		      "," + getBaseTableName() + "." +  UasDaoDeptTbl.CD_CRTRID.getName() +
		      "," + getBaseTableName() + "." +  UasDaoDeptTbl.CD_CRTNDATE.getName() +
		      "," + getBaseTableName() + "." +  UasDaoDeptTbl.CD_UPDRID.getName() +
		      "," + getBaseTableName() + "." +  UasDaoDeptTbl.CD_UPDATE.getName();
             
      return val;
   }
   
   /**
    * Devuelve las columnas identificador y nombre de la tabla de departamentos.
    * 
    * @return Las columnas mencionadas.
    */
   public String getLoadBaseIdNameColumnNames()
	{
   	String val;
   	val = getBaseTableName() + "." +  UasDaoDeptTbl.CD_ID.getName() +
   			"," + getBaseTableName() + "." +  UasDaoDeptTbl.CD_NAME.getName();
   	return val;
   }
   
   /**
    * Devuelve las columnas identificador, nombre y ParentId
    * de la tabla de departamentos.
    * 
    * @return Las columnas mencionadas.
    */
   public String getLoadBaseIdNameParentColumnNames()
	{
   	String val;
   	val = getBaseTableName() + "." +  UasDaoDeptTbl.CD_ID.getName() +
   			"," + getBaseTableName() + "." +  UasDaoDeptTbl.CD_NAME.getName() +
   			"," + getBaseTableName() + "." + UasDaoDeptTbl.CD_PARENTID.getName();
   	return val;
   }
   
   /**
    * Devuelve la columna parentId del departamento.
    * @return La columna mencionada.
    */
   public String getParentIdDeptColumnName()
   {
      String val;
      
      val = getBaseTableName() + "." + UasDaoDeptTbl.CD_PARENTID.getName();
      
      return val;
   }
   
   /**
    * Devuelve la columna del identificador del administrador del departamento
    * @return La columna mencionada
    */
   public String getMgrDeptColumnName()
   {
      String val;
      
      val = getBaseTableName() + "." + UasDaoDeptTbl.CD_MGRID.getName();
      
      return val;
   }
   
   /**
    * Devuelve la columna identificador del departamento
    * @return La columna mencionada
    */
   public String getDeptIdColumnName()
   {
      String val;
      
      val = getBaseTableName() + "." + UasDaoDeptTbl.CD_ID.getName();
      
      return val;
   }
   
	
	/////////////////////////////////////////////////////////////////////////
   //                            Quals                                    //
   /////////////////////////////////////////////////////////////////////////
   
   
   public String getUpdateMgrQual(int userId)
   {
      String qual;
      
      qual = "WHERE " + getBaseTableName() + "." +
             UasDaoDeptTbl.CD_MGRID.getName() + "= " + Integer.toString(userId);
      
      if (_logger.isDebugEnabled())
         _logger.debug("getUpdateMgrQual: " + qual);
      
      return qual;
   }
   /**
	 * Construye una expresión de búsqueda para calcular el número 
	 * de departamentos que hay con el mismo nombre.
	 * 
	 * @param name El nombre del departamento.
	 * @return La expresión mencionada.
	 */
	public String getCountNameQual(String name)
	{
		String qual;
		qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoDeptTbl.CD_NAME.getName() + "= '" + name + "'";
		if (_logger.isDebugEnabled())
			_logger.debug("getCountNameQual: " + qual);
		return qual;
	}
	
	/**
	 * Construye una expresión de búsqueda para calcular el número 
	 * de departamentos que hay con el mismo nombre y distinto 
	 * identificador. 
	 * 
	 * @param id Identificador del departamento.
	 * @param name El nombre del departamento.
	 * @return La expresión mencionada.
	 */
	public String getCountNameIdQual(int id, String name)
	{
		String qual;
		qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoDeptTbl.CD_NAME.getName() + "= '" + name + "' AND " 
				+ getBaseTableName() + "." + UasDaoDeptTbl.CD_ID.getName() 
				+ "<>" + Integer.toString(id);
		if (_logger.isDebugEnabled())
			_logger.debug("getCountNameIdQual: " + qual);
		return qual;
	}
	
	/**
    * Construye una expresión de búsqueda para la tabla base de departamentos a 
    * partir del identificador.
    * 
    * @param id El identificador de departamento.
    * @return La expresión mencionada. 
    */

   public String getLoadBaseQual(int id)
   {
      String qual;
      
      qual = "WHERE " + getBaseTableName() + "." + 
      UasDaoDeptTbl.CD_ID.getName() + "=" + Integer.toString(id); 
      
      if (_logger.isDebugEnabled())
         _logger.debug("getLoadBaseQual: " + qual);
            
      return qual;
   }
   
   /**
    * Construye una expresión de búsqueda para eliminar el departamento de la tabla
    * principal.
    * 
    * @param id El identificador de departamento.
    * @return La expresión mencionada. 
    */

   public String getDeleteBaseQual(int id)
   {
      String qual;
      
      qual = "WHERE " + getBaseTableName() + "." + 
      UasDaoDeptTbl.CD_ID.getName() + "=" +  Integer.toString(id); 
      
      if (_logger.isDebugEnabled())
         _logger.debug("getDeleteBaseQual: " + qual);
         
      return qual;
   }
	
   /**
    * Construye una expresión de búsqueda a partir del parenId del departamento
    * de la tabla principal.
    * 
    * @param parentId Identificador de departamento padre.
    * @return La expresión mencionada.
    */
   public String getParentIdQual(int parentId)
   {
      String qual;
      
      qual = "WHERE " + getBaseTableName() + "." +
      		UasDaoDeptTbl.CD_PARENTID.getName() + "=" + Integer.toString(parentId);
      
      if (_logger.isDebugEnabled())
         _logger.debug("getParentIdQual: " + qual);
      
      return qual;
   }
   
	private static final Logger _logger = Logger.getLogger(DepartmentsTable.class);
}
