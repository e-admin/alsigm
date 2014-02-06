
package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.core.datetime.DatePattern;
import ieci.tecdoc.core.datetime.DateTimeUtil;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbDeleteFns;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.core.db.DbUtil;
import ieci.tecdoc.core.xml.lite.XmlTextBuilder;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveDefs;
import ieci.tecdoc.idoc.admin.api.archive.Directory;
import ieci.tecdoc.idoc.admin.api.exception.AdminException;
import ieci.tecdoc.idoc.admin.api.exception.DirErrorCodes;
import ieci.tecdoc.idoc.admin.api.user.BasicUsers;
import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.idoc.admin.database.ArchivesTable;
import ieci.tecdoc.idoc.admin.database.DirsTable;
import ieci.tecdoc.idoc.admin.database.LdapUsersTable;
import ieci.tecdoc.idoc.admin.database.UsersTable;
import ieci.tecdoc.idoc.core.database.DynamicFns;
import ieci.tecdoc.idoc.core.database.DynamicRow;
import ieci.tecdoc.idoc.core.database.DynamicRows;
import ieci.tecdoc.idoc.core.database.DynamicTable;
import ieci.tecdoc.sbo.util.nextid.NextId;
import ieci.tecdoc.core.db.DbInputStatement;
import ieci.tecdoc.core.db.DbOutputStatement;
import ieci.tecdoc.sbo.config.CfgMdoConfig;

import java.util.Date;
import org.apache.log4j.Logger;

public class DirectoryImpl implements Directory
{

   public DirectoryImpl(int userConnected, int parentId, boolean isLdap)
   {
      init(userConnected, parentId, isLdap);
   }

   public DirectoryImpl()
   {
      init(Defs.NULL_ID, Defs.NULL_ID, false);
   }

   public void load(int id) throws Exception
   {
      load(id, true);
   }

   public void load(int id, boolean full) throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("load: Id = " + Integer.toString(id));
      
		try
		{
         _id = id;
         
			DbConnection.open(CfgMdoConfig.getDbConfig());
         loadDirectory(full);
         loadParentId();
         loadAdminInfo();
		}
		catch(Exception e)
		{
         _logger.error(e);
         throw e;
		}
      finally
      {
         DbConnection.close();
      }
   }

   public void store() throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("store");
      
		try
		{
			DbConnection.open(CfgMdoConfig.getDbConfig());

         if (_id == Defs.NULL_ID)
         {
            insert();
         }
         else
         {
            update();
         }
         
		}
		catch(Exception e)
		{
         _logger.error(e);
         throw e;
		}
      finally
      {
         DbConnection.close();
      }
   }

   public void delete() throws Exception 
   {
      boolean commit = false;
      boolean inTrans = false;
      DirsTable table = new DirsTable();
      LdapUsersTable userTable = new LdapUsersTable();

      if (_logger.isDebugEnabled())
         _logger.debug("delete");

		try
		{
			DbConnection.open(CfgMdoConfig.getDbConfig());

         checkDirHasChildren();
         
         DbConnection.beginTransaction();
         inTrans = true;
         
         DbDeleteFns.delete(table.getDirTableName(), 
                            table.getLoadDirQual(_id));
         DbDeleteFns.delete(table.getNodeTableName(), 
                            table.getLoadNodeDirQual(_id));
         DbDeleteFns.delete(userTable.getOwnershipTableName(), 
                            userTable.getDeleteOwnershipById(_acsId));
         DbDeleteFns.delete(userTable.getObjPermsTableName(), 
                            userTable.getDeleteObjPermsByObjIdQual(_acsId));

         commit = true;         
		}
		catch(Exception e)
		{
         _logger.error(e);
         throw e;
		}
      finally
      {
         if (inTrans) 
            DbConnection.endTransaction(commit);
            
         DbConnection.close();
      }
   }

   
   public BasicUsers getAdminUsers() throws Exception
   {      
      if (_logger.isDebugEnabled())
         _logger.debug("getAdminUsers");

      if (_adminUsers.count() == 0)
      {
         try
         {
            DbConnection.open(CfgMdoConfig.getDbConfig());
            loadAdminUsers();
         }
         catch(Exception e)
         {
            _logger.error(e);
            throw e;
         }
         finally
         {
            DbConnection.close();
         }
      }
      return _adminUsers;
   }

   /**
    * Actualiza un usuario de invesDoc.
    *  
    * @throws Exception Si se produce algún error en la actualización del 
    * usuario.
    */
    
   private void update() throws Exception
   {
      boolean commit = false;
      boolean inTrans = false;
      int counter;
      
      if (_logger.isDebugEnabled())
         _logger.debug("update");

      try 
      {
         checkDirExists();
         
         DbConnection.beginTransaction();
         inTrans = true;

         updateDir();
         updateObjInfo();
                  
         commit = true;
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
      finally
      {
         if (inTrans) 
            DbConnection.endTransaction(commit);
      }
   }

   public void setLdap(boolean ldap)
   {
      _isLdap = ldap;
   }
   
   public String getName()
   {
      return _name;   
   }
   
   public void setName(String name)
   {
      _name = name;      
   }
   
   public String getDescription()
   {
      return _description;   
   }
   
   public void setDescription(String description)
   {
      _description = description;      
   }
   
   public int getFlags()
   {
      return _flags;   
   }
   
   public void setFlags(int flags)
   {
      _flags = flags;
   }
   
   public int getAccessType()
   {
      return _accessType;   
   }
   
   public void setAccessType(int accessType)
   {
      _accessType = accessType;   
   }
   
   public int getAcsId()
   {
      return _acsId;   
   }
   
   public int getCreatorId()
   {
      return _creatorId;   
   }
    
   public Date getCreationDate()
   {
      return _creationDate;   
   }
    
   public int getUpdaterId()
   {
      return _updaterId;   
   }
    
   public Date getUpdateDate()
   {
      return _updateDate;   
   }

   public int getAdminUserId()
   {
      return _adminUserId;   
   }
    
   public void setAdminUserId(int id)
   {
      _adminUserId = id;   
   }
    
   /**
    * Obtiene la información del directorio en formato XML.
    *
    * @param header Indica si hay que incluir la cabecera xml o no.
    * @return La información mencionada.
    */
    
   public String toXML(boolean header) 
   {
      XmlTextBuilder bdr;
      String tagName = "Directory";
      String text;
      
      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      bdr.addSimpleElement("Id", Integer.toString(_id));
      bdr.addSimpleElement("Name", _name);
      bdr.addSimpleElement("Description", _description);
      bdr.addSimpleElement("Type", Integer.toString(_type));
      bdr.addSimpleElement("AccessType", Integer.toString(_accessType));
      bdr.addSimpleElement("CreatorId", Integer.toString(_creatorId));
      bdr.addSimpleElement("CreationDate", DateTimeUtil.getDateTime(
                     _creationDate, DatePattern.XML_TIMESTAMP_PATTERN));
      if (DbDataType.isNullLongInteger(_updaterId))
      {
         text = "";
      }
      else
      {
         text = Integer.toString(_updaterId);
      }
      bdr.addSimpleElement("UpdaterId", text);
      if (DbDataType.isNullDateTime(_updateDate))
      {
         text = "";
      }
      else
      {
         text = DateTimeUtil.getDateTime(_updateDate, 
                                        DatePattern.XML_TIMESTAMP_PATTERN);
      }
      
      bdr.addSimpleElement("UpdateDate", text);
      bdr.addSimpleElement("ParentId", Integer.toString(_parentId));
      bdr.addSimpleElement("AdminUserId", Integer.toString(_adminUserId));
      bdr.addSimpleElement("Flags", Integer.toString(_flags));

      bdr.addClosingTag(tagName);
      
      return bdr.getText();
   }

   public String toXML() 
   {
      return toXML(true);
   }

	public String toString()
   {
      return toXML(false);
   }

   /**
    * Recupera de la base de datos información asociada al directorio. 
    * 
    * @param statement
    * @param idx
    * @return 
    * @throws java.lang.Exception
    */
    
   public Integer loadAllValues(DbOutputStatement statement, Integer idx) 
                  throws Exception 
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("loadAllValues");

      _id = statement.getLongInteger(index++);
      _name = statement.getShortText(index++);
      _type = statement.getLongInteger(index++);
      _flags = statement.getLongInteger(index++);
      _description = statement.getShortText(index++);
      _accessType = statement.getLongInteger(index++);
      _acsId = statement.getLongInteger(index++);
      _creatorId = statement.getLongInteger(index++);
      _creationDate = statement.getDateTime(index++);
      _updaterId = statement.getLongInteger(index++);
      _updateDate = statement.getDateTime(index++);

      return new Integer(index);
   }

   /**
    * Recupera de la base de datos información asociada al directorio. 
    * 
    * @param statement
    * @param idx
    * @return 
    * @throws java.lang.Exception
    */
    
   public Integer loadLiteValues(DbOutputStatement statement, Integer idx) 
                  throws Exception 
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("loadLiteValues");

      _id = statement.getLongInteger(index++);
      _name = statement.getShortText(index++);

      return new Integer(index);
   }

   /**
    * Recupera de la base de datos información asociada al directorio. 
    * 
    * @param statement
    * @param idx
    * @return 
    * @throws java.lang.Exception
    */
    
   public Integer loadParentIdValue(DbOutputStatement statement, Integer idx) 
                  throws Exception 
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("loadParentIdValue");

      _parentId = statement.getLongInteger(index++);

      return new Integer(index);
   }

   /**
    * Recupera de la base de datos información asociada al directorio. 
    * 
    * @param statement
    * @param idx
    * @return 
    * @throws java.lang.Exception
    */
    
   public Integer loadAdminUserIdValue(DbOutputStatement statement, Integer idx) 
                  throws Exception 
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("loadAdminUserIdValue");

      _adminUserId = statement.getLongInteger(index++);

      return new Integer(index);
   }

   /**
    * Inserta en base de datos información almacenada por esta clase. 
    * 
    * @param statement
    * @param idx
    * @return 
    * @throws java.lang.Exception
    */
    
   public Integer insertValues(DbInputStatement statement, Integer idx) 
                  throws Exception 
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("insertValues");

      statement.setLongInteger(index++, _id);
      statement.setShortText(index++, _name);
      statement.setLongInteger(index++, _type);
      statement.setLongInteger(index++, _flags);
      statement.setShortText(index++, _description);
      statement.setLongInteger(index++, _accessType);
      statement.setLongInteger(index++, _acsId);
      statement.setLongInteger(index++, _creatorId);
      statement.setDateTime(index++, _creationDate);
      statement.setLongInteger(index++, _updaterId);
      statement.setDateTime(index++, _updateDate);

      return new Integer(index);
   }
 
   /**
    * Inserta en base de datos información almacenada por esta clase. 
    * 
    * @param statement
    * @param idx
    * @return 
    * @throws java.lang.Exception
    */
    
   public Integer updateValues(DbInputStatement statement, Integer idx) 
                  throws Exception 
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("updateValues");

      statement.setShortText(index++, _name);
      statement.setLongInteger(index++, _flags);
      statement.setShortText(index++, _description);
      statement.setLongInteger(index++, _accessType);
      _updaterId = _userConnected;
      statement.setLongInteger(index++, _updaterId);
      _updateDate = DateTimeUtil.getCurrentDateTime();
      statement.setDateTime(index++, _updateDate);

      return new Integer(index);
   }
 
   /**
    * Inserta en base de datos información almacenada por esta clase. 
    * 
    * @param statement
    * @param idx
    * @return 
    * @throws java.lang.Exception
    */
    
   public Integer insertNodeValues(DbInputStatement statement, Integer idx) 
                  throws Exception 
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("insertNodeValues");

      statement.setLongInteger(index++, _id);
      statement.setLongInteger(index++, Defs.NODE_TYPE_DIRECTORY);
      statement.setLongInteger(index++, _parentId);
      
      return new Integer(index);
   }
 
   /**
    * Inserta en base de datos información almacenada por esta clase. 
    * 
    * @param statement
    * @param idx
    * @return 
    * @throws java.lang.Exception
    */
    
   public Integer insertObjValues(DbInputStatement statement, Integer idx) 
                  throws Exception 
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("insertObjValues");

      statement.setLongInteger(index++, _acsId);
      statement.setLongInteger(index++, UserDefs.PRODUCT_IDOC);
      statement.setLongInteger(index++, Defs.OBJECT_OWNER_TYPE_DIRECTORY);
      statement.setLongInteger(index++, _id);
      statement.setLongInteger(index++, 0);
      statement.setLongInteger(index++, 0);
      statement.setLongInteger(index++, Defs.OBJECT_OWNER_TYPE_USER);
      statement.setLongInteger(index++, _userConnected);
      statement.setLongInteger(index++, _userConnected);
      statement.setDateTime(index++, DbUtil.getCurrentDateTime());
      statement.setLongInteger(index++, DbDataType.NULL_LONG_INTEGER);
      statement.setDateTime(index++, DbDataType.NULL_DATE_TIME);
      
      return new Integer(index);
   }
 
   /**
    * Inserta en base de datos información almacenada por esta clase. 
    * 
    * @param statement
    * @param idx
    * @return 
    * @throws java.lang.Exception
    */
    
   public Integer updateObjValues(DbInputStatement statement, Integer idx) 
                  throws Exception 
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("updateObjValues");

      statement.setLongInteger(index++, _adminUserId);
      statement.setLongInteger(index++, _userConnected);
      statement.setDateTime(index++, DateTimeUtil.getCurrentDateTime());
      
      return new Integer(index);
   }
 
   /**
    * Lee la información básica de un directorio.
    *  
    * @param full Si hay que leer toda la información o únicamente el nombre y
    * el identificador.
    * @throws Exception Si se produce algún error en la lectura de la 
    * información mencionada.
    */

   private void loadDirectory(boolean full) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      DirsTable table = new DirsTable();
      String method;
      
      if (_logger.isDebugEnabled())
         _logger.debug("loadDirectory");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(DirsTable.class.getName());
         tableInfo.setTablesMethod("getDirTableName");
         if (full)
            method = "getAllDirColumnNames";
         else
            method = "getLiteDirColumnNames";
            
         tableInfo.setColumnsMethod(method);
         
         rowInfo.addRow(this);
         rowInfo.setClassName(DirectoryImpl.class.getName());
         if (full)
            method = "loadAllValues";
         else
            method = "loadLiteValues";
            
         rowInfo.setValuesMethod(method);
         rowsInfo.add(rowInfo);
         
         if (!DynamicFns.select(table.getLoadDirQual(_id), tableInfo, rowsInfo))
         {
            AdminException.throwException(DirErrorCodes.EC_DIR_NOT_EXITS);
         }
         
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
  }
   
   /**
    * Lee la información del identificador del padre.
    *  
    * @throws Exception Si se produce algún error en la lectura de la 
    * información mencionada.
    */

   private void loadParentId() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      DirsTable table = new DirsTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("loadParentId");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(DirsTable.class.getName());
         tableInfo.setTablesMethod("getNodeTableName");
         tableInfo.setColumnsMethod("getNodeParentIdColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(DirectoryImpl.class.getName());
         rowInfo.setValuesMethod("loadParentIdValue");
         rowsInfo.add(rowInfo);
         
         DynamicFns.select(table.getLoadNodeDirQual(_id), tableInfo, rowsInfo);
         
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
  }

   /**
    * Lee la información del usuario administrador.
    *  
    * @throws Exception Si se produce algún error en la lectura de la 
    * información mencionada.
    */

   private void loadAdminInfo() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      LdapUsersTable table = new LdapUsersTable();
      UsersTable     usrTbl = new UsersTable();
      String         qual;
      
      int counter;
      BasicUserImpl user;
      
      if (_logger.isDebugEnabled())
         _logger.debug("loadAdminInfo");

      try 
      {
         // Cargamos usuario administrador
         tableInfo.setTableObject(table);
         tableInfo.setClassName(LdapUsersTable.class.getName());
         tableInfo.setTablesMethod("getOwnershipTableName");
         tableInfo.setColumnsMethod("getOwnerIdColumnName");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(DirectoryImpl.class.getName());
         rowInfo.setValuesMethod("loadAdminUserIdValue");
         rowsInfo.add(rowInfo);
         
         DynamicFns.select(table.getLoadOwnerIdQual(_acsId), tableInfo, 
                                                    rowsInfo);
         
         // Cargamos posibles usuarios administradores
         
         if (_adminUsers.count() == 0)
         {
            if (_isLdap)
            {
               tableInfo.setTableObject(table);         
               tableInfo.setClassName(LdapUsersTable.class.getName());
               qual = table.getLoadAminUsersQual(UserDefs.PRODUCT_IDOC);
            }
            else
            {
               tableInfo.setTableObject(usrTbl);
               tableInfo.setClassName(UsersTable.class.getName());
               qual = usrTbl.getLoadAminUsersQual(UserDefs.PRODUCT_IDOC);
            }
            
            tableInfo.setTablesMethod("getUserAdminTableNames");
            tableInfo.setColumnsMethod("getAdminUserColumnNames");
         
            rowInfo = new DynamicRow();
            rowsInfo = new DynamicRows();
            rowInfo.setClassName(BasicUserImpl.class.getName());
            rowInfo.setValuesMethod("loadValues");
            rowsInfo.add(rowInfo);
                  
            DynamicFns.selectMultiple(qual, true, tableInfo, rowsInfo);
                                   
            for (counter = 0; counter < rowInfo.getRowCount(); counter++)
            {
               user = (BasicUserImpl)rowInfo.getRow(counter);
               _adminUsers.add(user);
            }
         }
         
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
  }
   
   private void loadAdminUsers() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      LdapUsersTable table = new LdapUsersTable();
      UsersTable     usrTbl = new UsersTable();
      String         qual;
      
      int counter;
      BasicUserImpl user;
      
      try
      {
         
         if (_isLdap)
         {
            tableInfo.setTableObject(table);         
            tableInfo.setClassName(LdapUsersTable.class.getName());
            qual = table.getLoadAminUsersQual(UserDefs.PRODUCT_IDOC);
         }
         else
         {
            tableInfo.setTableObject(usrTbl);
            tableInfo.setClassName(UsersTable.class.getName());
            qual = usrTbl.getLoadAminUsersQual(UserDefs.PRODUCT_IDOC);
         }
         tableInfo.setTablesMethod("getUserAdminTableNames");
         tableInfo.setColumnsMethod("getAdminUserColumnNames");
      
         rowInfo = new DynamicRow();
         rowsInfo = new DynamicRows();
         rowInfo.setClassName(BasicUserImpl.class.getName());
         rowInfo.setValuesMethod("loadValues");
         rowsInfo.add(rowInfo);
      
         DynamicFns.selectMultiple(qual, true, tableInfo, rowsInfo);
                                
         for (counter = 0; counter < rowInfo.getRowCount(); counter++)
         {
            user = (BasicUserImpl)rowInfo.getRow(counter);
            _adminUsers.add(user);
         }         
      
      }
      catch (Exception e)
      {
         _logger.error(e);
         throw e;
      }
   }

   /**
    * Actualiza la información de la tabla principal (header) del directorio.
    *  
    * @throws Exception Si se produce algún error en la inserción del 
    * directorio.
    */
    
   private void updateDir() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      DirsTable table = new DirsTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("updateDir");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(DirsTable.class.getName());
         tableInfo.setTablesMethod("getDirTableName");
         tableInfo.setColumnsMethod("getUpdateDirColumnNames");
         
         _creationDate = DbUtil.getCurrentDateTime();
         
         rowInfo.addRow(this);
         rowInfo.setClassName(DirectoryImpl.class.getName());
         rowInfo.setValuesMethod("updateValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.update(table.getLoadDirQual(_id), tableInfo, rowsInfo);
         
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
   }
  
   /**
    * Inserta la parte base de las tablas de directorio (header).
    *  
    * @throws Exception Si se produce algún error en la inserción del 
    * directorio.
    */
    
   private void insertBase() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      DirsTable table = new DirsTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("insertBase");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(DirsTable.class.getName());
         tableInfo.setTablesMethod("getDirTableName");
         tableInfo.setColumnsMethod("getAllDirColumnNames");
         
         _creationDate = DbUtil.getCurrentDateTime();
         
         rowInfo.addRow(this);
         rowInfo.setClassName(DirectoryImpl.class.getName());
         rowInfo.setValuesMethod("insertValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.insert(tableInfo, rowsInfo);
         
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
   }
  
   /**
    * Maneja la información de inserción del propietario del directorio.
    *  
    * @throws Exception Si se produce algún error en la inserción del 
    * nodo.
    */
    
   private void insertObjInfo() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      LdapUsersTable table = new LdapUsersTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("insertObjInfo");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(LdapUsersTable.class.getName());
         tableInfo.setTablesMethod("getOwnershipTableName");
         tableInfo.setColumnsMethod("getAllOwnershipColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(DirectoryImpl.class.getName());
         rowInfo.setValuesMethod("insertObjValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.insert(tableInfo, rowsInfo);
         
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
   }
  
   /**
    * Maneja la información de actualización del propietario del directorio.
    *  
    * @throws Exception Si se produce algún error en la inserción del 
    * nodo.
    */
    
   private void updateObjInfo() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      LdapUsersTable table = new LdapUsersTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("insertObjInfo");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(LdapUsersTable.class.getName());
         tableInfo.setTablesMethod("getOwnershipTableName");
         tableInfo.setColumnsMethod("getUpdateOwnershipColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(DirectoryImpl.class.getName());
         rowInfo.setValuesMethod("updateObjValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.update(table.getSelectOwnerQual(_acsId), tableInfo, rowsInfo);
         
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
   }
  
   /**
    * Inserta el directorio en el nodo correspondiente.
    *  
    * @throws Exception Si se produce algún error en la inserción del 
    * nodo.
    */
    
   private void insertNode() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      DirsTable table = new DirsTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("insertNode");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(DirsTable.class.getName());
         tableInfo.setTablesMethod("getNodeTableName");
         tableInfo.setColumnsMethod("getInsertNodeColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(DirectoryImpl.class.getName());
         rowInfo.setValuesMethod("insertNodeValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.insert(tableInfo, rowsInfo);
         
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
   }
  
   /**
    * Inserta un nuevo directorio en invesDoc.
    *  
    * @throws Exception Si se produce algún error en la inserción del directorio.
    */
   
   private void insert() throws Exception
   {
      boolean commit = false;
      boolean inTrans = false;
      
      if (_logger.isDebugEnabled())
         _logger.debug("insert");

      try 
      {
      /*
         count = DbSelectFns.selectCount(table.getDirTableName(), 
                                         table.getCountDirQual(_name));
         if (count > 0)
            AdminException.throwException(DirErrorCodes.EC_DIR_EXITS);
      */

         _id = NextId.generateNextId(ArchivesTable.TN_NEXTID, 
                                     Defs.NEXT_ID_TYPE_DIRECTORY);
         _acsId = NextId.generateNextId(LdapUsersTable.TN_NEXTID, 
                                       Defs.NEXT_ID_USER_TABLE_TYPE_OBJ);

         DbConnection.beginTransaction();
         inTrans = true;
   
         insertObjInfo();
         
         // Lo hacemos al revés de como está definido para no tener que 
         // actualizar a posteriori AcsId
         insertBase();
         checkDirExists();         
         insertNode();
         
         commit = true;
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
      finally
      {
         if (inTrans) 
            DbConnection.endTransaction(commit);
      }
   }
   
   /**
    * Verifica si existe un directorio con ese nombre y el mismo padre antes
    * de insertarlo.
    *  
    * @throws Exception Si el directorio existe.
    */
    
   private void checkDirExists() throws Exception
   {
      int count;
      DirsTable table = new DirsTable();
      
      count = DbSelectFns.selectCount(table.getDirTableName(), 
                                      table.getCountDirIdQual(_id, _name));
      if (count > 0)
      {
         count = DbSelectFns.selectCount(table.getNodeTableName(), 
                                 table.getCountDirNodeQual(_parentId));
         if (count > 0)
            AdminException.throwException(DirErrorCodes.EC_DIR_EXITS);
      }
      
   }
   
   /**
    * Verifica si un directorio tiene hijos.
    *  
    * @throws Exception Si el directorio tiene hijos.
    */
    
   private void checkDirHasChildren() throws Exception
   {
      int count;
      DirsTable table = new DirsTable();
      
      count = DbSelectFns.selectCount(table.getNodeTableName(), 
                                        table.getLoadAllChildrenDirQual(_id));
      if (count > 0)
      {
         AdminException.throwException(DirErrorCodes.EC_DIR_HAS_CHILDREN);
      }
      
   }
   
   private void init(int userConnected, int parentId, boolean isLdap)
   {
      _id = Defs.NULL_ID;
      _isLdap = isLdap;
      _parentId = parentId;
      _userConnected = userConnected;
      _adminUserId = userConnected;
      _name = "(Vacío)";
      _description = "(Vacío)";
      _flags = 0;
      _type = ArchiveDefs.DIR_TYPE_STANDARD;
      _accessType = ArchiveDefs.ACCESS_TYPE_PUBLIC;
      _creationDate = DbDataType.NULL_DATE_TIME;
      _creatorId = _userConnected;
      _updaterId = DbDataType.NULL_LONG_INTEGER;
      _updateDate = DbDataType.NULL_DATE_TIME;
      _acsId = Defs.NULL_ID;
      _adminUsers = new BasicUsersImpl();
   }
   
   
   private int _id;
   private boolean _isLdap;
   private int _userConnected;
   private int _parentId;
   private String _name;
   private String _description;
   private int _flags;
   private int _acsId;
   private int _type;
   private int _accessType;
   private Date _creationDate;
   private int _creatorId;
   private int _updaterId;
   private Date _updateDate;
   private int _adminUserId;
   private BasicUsersImpl _adminUsers;
   
   private static final Logger _logger = Logger.getLogger(DirectoryImpl.class);
   
}