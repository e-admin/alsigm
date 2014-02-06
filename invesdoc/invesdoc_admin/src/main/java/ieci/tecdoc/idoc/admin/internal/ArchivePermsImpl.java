package ieci.tecdoc.idoc.admin.internal;



import org.apache.log4j.Logger;

import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbDeleteFns;
import ieci.tecdoc.core.db.DbInputStatement;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.idoc.admin.api.archive.ArchivePerms;
import ieci.tecdoc.idoc.admin.api.exception.AdminException;
import ieci.tecdoc.idoc.admin.api.exception.ArchiveErrorCodes;
import ieci.tecdoc.idoc.admin.database.LdapUsersTable;
import ieci.tecdoc.idoc.core.database.DynamicFns;
import ieci.tecdoc.idoc.core.database.DynamicRow;
import ieci.tecdoc.idoc.core.database.DynamicRows;
import ieci.tecdoc.idoc.core.database.DynamicTable;
import ieci.tecdoc.sbo.acs.std.AcsMdoGenPerms;
import ieci.tecdoc.sbo.config.CfgMdoConfig;


public class ArchivePermsImpl implements ArchivePerms 
{
   public ArchivePermsImpl()
   {      
   }
   
   /**
    * Añade los permisos especificados 
    * 
    * @param destType - destinatario del permiso (departamento, usuario, grupo)
    * @param destId - identificador del destinatario
    * @param acsId - identificador de objeto sobre el que se añade el permiso (ej:archivador)
    * @param perms - permisos
    * @throws Exception - errores
    */
   public void addPerms(int destType, int destId, int acsId, int perms ) 
   				throws Exception
   {
      int     dstPerms = 0;   
      boolean commit = false;
      boolean inTrans = false;
      
      if (_logger.isDebugEnabled())
         _logger.debug("addPerms");
                  
      try
      {
         DbConnection.open(CfgMdoConfig.getDbConfig());
         
         switch(destType)
         {
         	case Defs.DESTINATION_USER:
         	{
         	   dstPerms =AcsMdoGenPerms.getUseriDocGenPerms(destId);
         	   break;
         	}
         	case Defs.DESTINATION_DEPT:
         	{
         	   dstPerms = AcsMdoGenPerms.getDeptiDocGenPerms(destId);      	   
         	   break;
         	}
         	case Defs.DESTINATION_GROUP:
         	{
         	   dstPerms = AcsMdoGenPerms.getGroupiDocGenPerms(destId);
         	   break;
         	}     	      
         }
         
	      if ((dstPerms & perms) != 0)
	      {  
	         DbConnection.beginTransaction();
	         inTrans = true;
	         setPerms(destType,destId,acsId,perms);
	         commit = true;
	      }
	      else
	      {
	         AdminException.throwException(ArchiveErrorCodes.EC_ARCH_CANNT_PERM);
	      }
	      
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
   
   /**
    * Elimina los permisos especificador
    * 
    * @param destType - destinatario del permiso (departamento, usuario, grupo)
    * @param destId - Identificador del destinatario    
    * @param archid - identificador del archivador
    * @param perms - permisos
    * @throws Exception - errores
    */
   public void deletePerms(int destType,int destId, int archId, int perms)
    				throws Exception
   {
      boolean commit = false;
      boolean inTrans = false;
      
      _destType = destType;
      _destId   = destId;
      _id       = archId;
      _typeObj  = Defs.OBJECT_OWNER_TYPE_ARCHIVE;
      
      if (_logger.isDebugEnabled())
         _logger.debug("deletePerms");
      
      try
      {
	      DbConnection.open(CfgMdoConfig.getDbConfig());
	      DbConnection.beginTransaction();
	      inTrans = true;
	      
	      if ( (perms & Defs.OBJ_PERM_QUERY) != 0 )
	      {
	         _perm = Defs.OBJ_PERM_QUERY;
	         deletePerm();
	      }
	      if ( (perms & Defs.OBJ_PERM_UPDATE) != 0 )
	      {
	         _perm = Defs.OBJ_PERM_UPDATE;
	         deletePerm();
	      }
	      if ( (perms & Defs.OBJ_PERM_CREATION) != 0 )
	      {
	         _perm = Defs.OBJ_PERM_CREATION;
	         deletePerm();
	      }
	      if ( (perms & Defs.OBJ_PERM_DELETION) != 0 )
	      {
	         _perm = Defs.OBJ_PERM_DELETION;
	         deletePerm();
	      }
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
      
   
   /**
    * Obtiene los permisos 
    * 
    * @param destType - destinatario del permiso (departamento, usuario, grupo)
    * @param destId - Identificador del destinatario    
    * @param archid - identificador del archivador
    * @param perms - permisos
    * @throws Exception - errores
    */
   public int loadPerms(int destType, int destId, int archId)
   				throws Exception
	{
      LdapUsersTable             table = new LdapUsersTable(); 
      int                        perms = Defs.OBJ_PERM_NONE;
      String                     qual;      
      IeciTdLongIntegerArrayList vals;
      int                        objType = Defs.OBJECT_OWNER_TYPE_ARCHIVE;
      
      if (_logger.isDebugEnabled())
         _logger.debug("loadPerm");
      
      try
      {
	      vals = new IeciTdLongIntegerArrayList();
	      qual = table.getLoadObjPermsQual(destType,destId,objType,archId);
	      
	      DbConnection.open(CfgMdoConfig.getDbConfig());
	      
	      DbSelectFns.select(table.getObjPermsTableName(), table.getLoadObjPermColumnName(),
	             qual,false,vals);
	      
	      for (int i= 0; i < vals.count(); i++)
	      {
	         perms = perms | vals.get(i);
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
      
      return perms;
	}
   public Integer insertPermValues(DbInputStatement statement, Integer idx) 
   				throws Exception
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("insertPermValues");

      statement.setLongInteger(index++, _destType);
      statement.setLongInteger(index++, _destId);
      statement.setLongInteger(index++, _acsId);
      statement.setLongInteger(index++, _perm);
      
      return new Integer(index);
   }
 
   
   ////////////////////////////////////////////////////////////////////////////
   //                    private
   ///////////////////////////////////////////////////////////////////////////
   
   
   private void setPerms(int destType,int destId, int acsId, int perms) 
   				throws Exception
   {
      
      _destType = destType;
      _destId = destId;
      _acsId = acsId;      
      
      
      if ( (perms & Defs.OBJ_PERM_QUERY) != 0 )
      {
         _perm = Defs.OBJ_PERM_QUERY;
         insertPerm();
      }
      if ( (perms & Defs.OBJ_PERM_UPDATE) != 0 )
      {
         _perm = Defs.OBJ_PERM_UPDATE;
         insertPerm();
      }      
      if ( (perms & Defs.OBJ_PERM_CREATION) != 0)
      {
         _perm = Defs.OBJ_PERM_CREATION;
         insertPerm();
      }
      if ( (perms & Defs.OBJ_PERM_DELETION) != 0)
      {
         _perm = Defs.OBJ_PERM_DELETION;
         insertPerm();
      }    
   }
   
   private void insertPerm() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      LdapUsersTable table = new LdapUsersTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("insertPerm");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(LdapUsersTable.class.getName());
         tableInfo.setTablesMethod("getObjPermsTableName");
         tableInfo.setColumnsMethod("getInsertObjPermsColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(ArchivePermsImpl.class.getName());
         rowInfo.setValuesMethod("insertPermValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.insert(tableInfo, rowsInfo);
         
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
   }
   
   private void deletePerm() throws Exception
   {
      int typeObj = _typeObj; //archivador
      
      LdapUsersTable table = new LdapUsersTable();

	   DbDeleteFns.delete(table.getObjPermsTableName(), 
	         table.getDeleteObjPermQual(_destType, _destId, _typeObj, _id, _perm));
	   
	   typeObj = Defs.OBJECT_OWNER_TYPE_FOLDER;
	   DbDeleteFns.delete(table.getObjPermsTableName(),
	         table.getDeleteObjPermQual(_destType, _destId, typeObj, _id, _perm));
	   
	   if (_perm == Defs.OBJ_PERM_QUERY)
	   {
	      typeObj = Defs.OBJECT_OWNER_TYPE_FORMAT;
	      DbDeleteFns.delete(table.getObjPermsTableName(),
	            table.getDeleteObjPermQual(_destType, _destId, typeObj, _id, _perm));
	      
	      typeObj = Defs.OBJECT_OWNER_TYPE_FORMAT;
	      DbDeleteFns.delete(table.getObjPermsTableName(),
	            table.getDeleteObjPermQual(_destType, _destId, typeObj, _id, _perm));
	   }
	   
   }
   
   private int _destType;
   private int _destId;
   private int _acsId;
   private int _perm;
   private int _id;
   private int _typeObj;
   
   
   private static final Logger _logger = Logger.getLogger(ArchivePermsImpl.class);
}
