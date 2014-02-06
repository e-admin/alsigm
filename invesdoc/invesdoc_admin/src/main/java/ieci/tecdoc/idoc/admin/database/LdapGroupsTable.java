
package ieci.tecdoc.idoc.admin.database;

import ieci.tecdoc.sbo.uas.ldap.UasDaoLdapGroupTbl;

import org.apache.log4j.Logger;

public class LdapGroupsTable 
{

   public LdapGroupsTable()
   {
   }
   
   /////////////////////////////////////////////////////////////////////////
   //                         Nombres de Tablas                           //
   /////////////////////////////////////////////////////////////////////////
   
   
   /**
    * Devuelve el nombre tabla base invesDoc de grupo Ldap.
    * 
    * @return Las tabla mencionada. 
    */
    
   public String getBaseTableName()
   {
      return UasDaoLdapGroupTbl.TN;
   }

   /////////////////////////////////////////////////////////////////////////
   //                         Nombres de Columnas                         //
   /////////////////////////////////////////////////////////////////////////
   
   /**
    * Devuelve todas las columnas de invesDoc de la tabla base de grupos Ldap
    * utilizadas en la carga de un grupo excepto el identificador.
    * 
    * @return Las columnas mencionadas. 
    */
    
   public String getLoadBaseColumnNames()
   {
      String val;
      
      val = getBaseTableName() + "." +  
            UasDaoLdapGroupTbl.CD_LDAPGUID.getName() + "," + getBaseTableName() + 
            "." + UasDaoLdapGroupTbl.CD_LDAPFULLNAME.getName();
             
      return val;
   }

   /**
    * Devuelve todas las columnas de invesDoc de la tabla base de grupos Ldap 
    * para su inserción.
    * 
    * @return Las columnas mencionadas. 
    */
    
   public String getInsertBaseColumnNames()
   {
      String val;
      
      val = getBaseTableName() + "." +  UasDaoLdapGroupTbl.CD_ID.getName() + 
             "," + getBaseTableName() + "." +  
             UasDaoLdapGroupTbl.CD_LDAPGUID.getName() + "," + 
             getBaseTableName() + "." + UasDaoLdapGroupTbl.CD_LDAPFULLNAME.
             getName() + "," + getBaseTableName() + "." + 
             UasDaoLdapGroupTbl.CD_TYPE.getName();
             
      return val;
   }
   
   /**
    * Devuelve todas las columnas de invesDoc de la tabla base de grupos Ldap 
    * 
    * 
    * @return Las columnas mencionadas. 
    */
    
   public String getLoadBaseAllColumnNames()
   {
      String val;
      
      val = getBaseTableName() + "." +  UasDaoLdapGroupTbl.CD_ID.getName() + 
             "," + getBaseTableName() + "." +  
             UasDaoLdapGroupTbl.CD_LDAPGUID.getName() + "," + 
             getBaseTableName() + "." + UasDaoLdapGroupTbl.CD_LDAPFULLNAME.
             getName();
             
      return val;
   }
   
   /**
    * Devuelve la columna identificador de grupo.
    * 
    * @return La columna mencionada. 
    */
    
   public String getLoadBaseIdColumnName()
   {
      String val;
      
      val = getBaseTableName() + "." +  
            UasDaoLdapGroupTbl.CD_ID.getName();
             
      return val;
   }
   
   /////////////////////////////////////////////////////////////////////////
   //                            Quals                                    //
   /////////////////////////////////////////////////////////////////////////
   

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
             UasDaoLdapGroupTbl.CD_ID.getName() + "=" + Integer.toString(id); 
      
      if (_logger.isDebugEnabled())
         _logger.debug("getLoadBaseQual: " + qual);
            
      return qual;
   }
   
   /**
    * Construye una expresión de búsqueda para la tabla base de grupos a 
    * partir del identificador ldap.
    * 
    * @param id El identificador de grupo ldap.
    * @return La expresión mencionada. 
    */

   public String getLoadBaseQual(String guid)
   {
      String qual;
      
      qual = "WHERE " + getBaseTableName() + "." + 
             UasDaoLdapGroupTbl.CD_LDAPGUID.getName() + "='" + guid + "'"; 
      
      if (_logger.isDebugEnabled())
         _logger.debug("getLoadBaseQual: " + qual);
            
      return qual;
   }
  
   /**
    * Construye una expresión de búsqueda para extraer todos los usuarios.
    * 
    * @param id El identificador de usuario.
    * @return La expresión mencionada. 
    */

   public String getLoadBaseAllQual()
   {
      String qual;
      
      qual = "";
            
      return qual;
   }
  

   /**
    * Construye una expresión de búsqueda para eliminar el usuario de la tabla
    * principal.
    * 
    * @param groupId El identificador de usuario.
    * @return La expresión mencionada. 
    */

   public String getDeleteBaseQual(int groupId)
   {
      String qual;
      
      qual = "WHERE " + getBaseTableName() + "." + 
             UasDaoLdapGroupTbl.CD_ID.getName() + "=" + 
             Integer.toString(groupId); 
      
      if (_logger.isDebugEnabled())
         _logger.debug("getDeleteBaseQual: " + qual);
         
      return qual;
   }


   private static final Logger _logger = Logger.getLogger(LdapGroupsTable.class);
}