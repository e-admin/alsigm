
package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.core.db.DbDeleteFns;
import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.idoc.admin.api.exception.UserErrorCodes;
import ieci.tecdoc.idoc.admin.api.exception.AdminException;
import ieci.tecdoc.idoc.admin.database.LdapUsersTable;

import ieci.tecdoc.idoc.core.database.DynamicRow;
import ieci.tecdoc.idoc.core.database.DynamicRows;
import ieci.tecdoc.idoc.core.database.DynamicTable;
import ieci.tecdoc.idoc.core.database.DynamicFns;
import ieci.tecdoc.idoc.admin.api.user.Permissions;
import ieci.tecdoc.idoc.admin.internal.PermImpl;

import org.apache.log4j.Logger;

/**
 * Representa los permisos de un elemento de invesDoc.
 */

public class GenericPermsImpl 
{

   /**
    * Construye un objeto de la clase. 
    */
    
   protected GenericPermsImpl()
   {
      _perms = new PermsImpl();
   }
   
   /**
    * Elimina los permisos de un elemento.
    *  
    * @param id Identificador del elemento.
    * @param dest Destino del permiso.
    * @throws Exception Si se produce algún error en el borrao de los permisos 
    * del elemento.
    */
    
   protected void deletePerms(int id, int dest) throws Exception 
   {
      LdapUsersTable table = new LdapUsersTable();

      if (_logger.isDebugEnabled())
         _logger.debug("deletePerms");

      DbDeleteFns.delete(table.getPermsTableName(), table.getDeletePermsQual(id,
                         dest));
   }

   protected Permissions getPermissions()
   {
      return _perms;
   }

   /**
    * Inserta la parte de permisos de las tablas de elemento.
    *  
    * @throws Exception Si se produce algún error en la inserción del elemento.
    */
    
   protected void insertPerms() throws Exception
   {
      int counter;
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo;
      DynamicRow rowInfo;
      LdapUsersTable table = new LdapUsersTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("insertPerms");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(LdapUsersTable.class.getName());
         tableInfo.setTablesMethod("getPermsTableName");
         tableInfo.setColumnsMethod("getInsertPermsColumnNames");
         
         for (counter = 0; counter < _perms.count(); counter++)
         {
            rowInfo = new DynamicRow();
            rowsInfo = new DynamicRows();
            
            rowInfo.addRow(_perms.get(counter));
            rowInfo.setClassName(PermImpl.class.getName());
            rowInfo.setValuesMethod("insertValues");
            rowsInfo.add(rowInfo);

            DynamicFns.insert(tableInfo, rowsInfo);
         }
         
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
      
   }

   /**
    * Actualiza un permiso.
    * 
    * @param id Identificador del elemento.
    * @param productId Identificador del producto.
    * @param dest Destino del permiso.
    * @throws Exception Si se produce algún error en la actualización.
    */
    
   protected void updatePerm(int id, int productId, int dest) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo;
      DynamicRow rowInfo;
      LdapUsersTable table = new LdapUsersTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("updatePerm");

      tableInfo.setTableObject(table);
      tableInfo.setClassName(LdapUsersTable.class.getName());
      tableInfo.setTablesMethod("getPermsTableName");
      tableInfo.setColumnsMethod("getUpdatePermsColumnNames");
         
      rowInfo = new DynamicRow();
      rowsInfo = new DynamicRows();
      
      rowInfo.addRow(_perms.getProductPermission(productId));
      rowInfo.setClassName(PermImpl.class.getName());
      rowInfo.setValuesMethod("updateValues");
      rowsInfo.add(rowInfo);

      DynamicFns.update(table.getUpdatePermsQual(id,dest, productId), tableInfo, 
                                                 rowsInfo);
      
   }

   /**
    * Actualiza la parte de permisos de las tablas de elemento.
    *  
    * @param id Identificador del elemento.
    * @param dest Destino del permiso.
    * @throws Exception Si se produce algún error en la actualización.
    */
    
   protected void updatePerms(int id, int dest) throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("updatePerms");

      updatePerm(id, UserDefs.PRODUCT_IDOC, dest);
      updatePerm(id, UserDefs.PRODUCT_IFLOW, dest);
      updatePerm(id, UserDefs.PRODUCT_ISICRES, dest);
   }

   /**
    * Lee los permisos del elemento invesDoc.
    *  
    * @param id Identificador del elemento.
    * @param dest Destinatario de los permisos.
    * @throws Exception Si se produce algún error en la lectura de los permisos
    * del elemento.
    */

   protected void loadPerms(int id, int dest) throws Exception
   {
      int counter;
      PermImpl perm;
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      LdapUsersTable table = new LdapUsersTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("loadPerms");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(LdapUsersTable.class.getName());
         tableInfo.setTablesMethod("getPermsTableName");
         tableInfo.setColumnsMethod("getLoadPermsColumnNames");
         
         rowInfo.setClassName(PermImpl.class.getName());
         rowInfo.setValuesMethod("loadValues");
         rowsInfo.add(rowInfo);
         
         if (!DynamicFns.selectMultiple(table.getLoadPermsQual(id,
                              dest), false, tableInfo, rowsInfo))
         {
            AdminException.throwException(UserErrorCodes.EC_USER_NOT_PERMS);
         }
         
         // Solo hay filas de una tabla
         for (counter = 0; counter < rowInfo.getRowCount(); counter++)
         {
            perm = (PermImpl)rowInfo.getRow(counter);
            perm.setId(id);
           _perms.add(perm);
         }
         
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
   }
 
   /**
    * Establece los permisos del elemento invesDoc por defecto.
    *  
    * @param id Identificador del elemento.
    * @param dest Destino del elemento.
    * @throws Exception Si se produce algún error en el establecimiento de los 
    * permisos del elemento.
    */

   protected void setDefaultPerms(int id, int dest) throws Exception
   {
      PermImpl perm;
      
      perm = new PermImpl(id, dest, UserDefs.PRODUCT_IDOC, 
                         UserDefs.PERMISSION_QUERY);
      _perms.add(perm);

      perm = new PermImpl(id, dest, UserDefs.PRODUCT_ISICRES, 
                        UserDefs.PERMISSION_NONE);
      _perms.add(perm);

      perm = new PermImpl(id, dest, UserDefs.PRODUCT_IFLOW, 
                         UserDefs.PERMISSION_NONE);
      _perms.add(perm);

   }

   protected PermsImpl _perms;

   private static final Logger _logger = Logger.getLogger(GenericProfilesImpl.class);

}