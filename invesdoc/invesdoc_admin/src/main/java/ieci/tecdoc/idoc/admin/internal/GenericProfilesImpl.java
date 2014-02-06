
package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.core.db.DbDeleteFns;
import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.idoc.admin.api.user.UserProfile;
import ieci.tecdoc.idoc.admin.api.exception.UserErrorCodes;
import ieci.tecdoc.idoc.admin.api.exception.AdminException;
import ieci.tecdoc.idoc.admin.database.LdapUsersTable;

import ieci.tecdoc.idoc.core.database.DynamicRow;
import ieci.tecdoc.idoc.core.database.DynamicRows;
import ieci.tecdoc.idoc.core.database.DynamicTable;
import ieci.tecdoc.idoc.core.database.DynamicFns;
import ieci.tecdoc.idoc.admin.api.user.UserProfiles;
import ieci.tecdoc.idoc.admin.internal.UserProfileImpl;

import org.apache.log4j.Logger;

/**
 * Representa los perfiles de un elemento de invesDoc.
 */

public class GenericProfilesImpl 
{

   protected GenericProfilesImpl()
   {
      _profiles = new UserProfilesImpl();
   }
   
   protected UserProfiles getProfiles()
   {
      return _profiles;
   }

   /**
    * Inserta la parte de perfiles de las tablas de elemento.
    *  
    * @throws Exception Si se produce algún error en la inserción del elemento.
    */
    
   protected void insertProfiles() throws Exception
   {
      int counter;
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo;
      DynamicRow rowInfo;
      LdapUsersTable table = new LdapUsersTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("insertProfiles");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(LdapUsersTable.class.getName());
         tableInfo.setTablesMethod("getProfilesTableName");
         tableInfo.setColumnsMethod("getInsertProfilesColumnNames");
         
         for (counter = 0; counter < _profiles.count(); counter++)
         {
            rowInfo = new DynamicRow();
            rowsInfo = new DynamicRows();
            
            rowInfo.addRow(_profiles.get(counter));
            rowInfo.setClassName(UserProfileImpl.class.getName());
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
    * Actualiza un perfil.
    *  
    * @param id Identificador del elemento.
    * @param productId Identificador del producto.
    * @throws Exception Si se produce algún error en la actualización.
    */
    
   private void updateProfile(int id, int productId) throws Exception
   {
      LdapUsersTable table = new LdapUsersTable();
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo;
      DynamicRow rowInfo;

      if (_logger.isDebugEnabled())
         _logger.debug("updateProfile");

      tableInfo.setTableObject(table);
      tableInfo.setClassName(LdapUsersTable.class.getName());
      tableInfo.setTablesMethod("getProfilesTableName");
      tableInfo.setColumnsMethod("getUpdateProfilesColumnNames");

      rowInfo = new DynamicRow();
      rowsInfo = new DynamicRows();

      rowInfo.addRow(_profiles.getProductProfile(productId));
      rowInfo.setClassName(UserProfileImpl.class.getName());
      rowInfo.setValuesMethod("updateValues");
      rowsInfo.add(rowInfo);

      DynamicFns.update(table.getUpdateProfilesQual(id, productId), tableInfo, 
                        rowsInfo);
   }

   /**
    * Actualiza la parte de perfiles de las tablas de elemento.
    *
    * @param id Identificador del elemento.
    * @throws Exception Si se produce algún error en la actualización.
    */
    
   protected void updateProfiles(int id) throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("updateProfiles");

      updateProfile(id, UserDefs.PRODUCT_IDOC);         
      updateProfile(id, UserDefs.PRODUCT_SYSTEM);         
      updateProfile(id, UserDefs.PRODUCT_USER);         
      updateProfile(id, UserDefs.PRODUCT_VOLUME);         
      updateProfile(id, UserDefs.PRODUCT_IFLOW);         
      updateProfile(id, UserDefs.PRODUCT_ISICRES);
   }

   /**
    * Lee los perfiles del elemento invesDoc.
    * 
    * @param id Identificador del elemento.
    * @return Si el elemento es administrador o no.
    * @throws Exception Si se produce algún error en la lectura de los perfiles
    * del elemento.
    */

   protected boolean loadProfiles(int id) throws Exception
   {
      int counter;
      UserProfile profile;
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      LdapUsersTable table = new LdapUsersTable();
      int prof;
      boolean wasAdmin = false;
      
      if (_logger.isDebugEnabled())
         _logger.debug("loadProfiles");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(LdapUsersTable.class.getName());
         tableInfo.setTablesMethod("getProfilesTableName");
         tableInfo.setColumnsMethod("getLoadProfilesColumnNames");
         
         rowInfo.setClassName(UserProfileImpl.class.getName());
         rowInfo.setValuesMethod("loadValues");
         rowsInfo.add(rowInfo);
         
         if (!DynamicFns.selectMultiple(
                    table.getLoadProfilesQual(id), false, tableInfo, rowsInfo))
         {
            AdminException.throwException(UserErrorCodes.EC_USER_NOT_PROFILES);
         }
         
         // Solo hay filas de una tabla
         for (counter = 0; counter < rowInfo.getRowCount(); counter++)
         {
            profile = (UserProfileImpl)rowInfo.getRow(counter);
            ((UserProfileImpl)profile).setId(id);
            _profiles.add(profile);
         }
         
         profile = _profiles.getProductProfile(UserDefs.PRODUCT_IDOC);
         prof = profile.getProfile();
         if ((prof == UserDefs.PROFILE_MANAGER) || 
             (prof == UserDefs.PROFILE_SUPERUSER))
         {
            wasAdmin = true;
         }
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
      
      return wasAdmin;
   }
   
   /**
    * Establece los perfiles del elemento invesDoc por defecto.
    *  
    * @param id Identificador del elemento.
    * @throws Exception Si se produce algún error en el establecimiento de los 
    * perfiles del elemento.
    */

   protected void setDefaultProfiles(int id) throws Exception
   {
      UserProfileImpl profile;
      
      profile = new UserProfileImpl(id, UserDefs.PRODUCT_SYSTEM, 
                                    UserDefs.PROFILE_NONE);
      _profiles.add(profile);

      profile = new UserProfileImpl(id, UserDefs.PRODUCT_USER, 
                                    UserDefs.PROFILE_NONE);
      _profiles.add(profile);

      profile = new UserProfileImpl(id, UserDefs.PRODUCT_IDOC, 
                                    UserDefs.PROFILE_STANDARD);
      _profiles.add(profile);

      profile = new UserProfileImpl(id, UserDefs.PRODUCT_IFLOW, 
                                    UserDefs.PROFILE_NONE);
      _profiles.add(profile);

      profile = new UserProfileImpl(id, UserDefs.PRODUCT_ISICRES, 
                                    UserDefs.PROFILE_NONE);
      _profiles.add(profile);

      profile = new UserProfileImpl(id, UserDefs.PRODUCT_VOLUME, 
                                    UserDefs.PROFILE_NONE);
      _profiles.add(profile);
   }

   /**
    * Elimina los permisos de un elemento.
    *  
    * @param id Identificador del elemento.
    * @throws Exception Si se produce algún error en el borrado de los 
    * perfiles del elemento.
    */

   protected void deleteProfiles(int id) throws Exception 
   {
      LdapUsersTable table = new LdapUsersTable();

      if (_logger.isDebugEnabled())
         _logger.debug("deleteProfiles");
         
      DbDeleteFns.delete(table.getProfilesTableName(), 
                         table.getDeleteProfilesQual(id));
      
   }
   
   protected UserProfilesImpl _profiles;

   private static final Logger _logger = Logger.getLogger(GenericProfilesImpl.class);

}