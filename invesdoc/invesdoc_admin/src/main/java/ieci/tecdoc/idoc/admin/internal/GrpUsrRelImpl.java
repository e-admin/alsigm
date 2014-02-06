package ieci.tecdoc.idoc.admin.internal;

import java.util.ArrayList;

import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbDeleteFns;
import ieci.tecdoc.core.db.DbInputStatement;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.idoc.admin.api.user.GroupUserRel;
import ieci.tecdoc.idoc.admin.database.UsersTable;
import ieci.tecdoc.idoc.core.database.DynamicFns;
import ieci.tecdoc.idoc.core.database.DynamicRow;
import ieci.tecdoc.idoc.core.database.DynamicRows;
import ieci.tecdoc.idoc.core.database.DynamicTable;
import ieci.tecdoc.sbo.config.CfgMdoConfig;

import org.apache.log4j.Logger;

public class GrpUsrRelImpl implements GroupUserRel
{
   public GrpUsrRelImpl()
   {
      
   }
   
   /**
    * Añade el usuario indicado al grupo indicado. No es necesario
    * realizar una carga del grupo.
    * @param groupId Identificador de grupo
    * @param userId Identificador de usuario
    * @throws Exception
    */
   public void  assignUserToGroup(int groupId, int userId) throws Exception
   { 
      boolean commit = false;
	   boolean inTrans = false;
      
      if (_logger.isDebugEnabled())
         _logger.debug("assignUserToGroup");
      
      try
      {
         _grpId = groupId;
         _usrId = userId;
         
         DbConnection.open(CfgMdoConfig.getDbConfig());
         DbConnection.beginTransaction();
         
         inTrans = true;
         insertBase();
         
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
    * Elimina un usuario de un grupo
    * @param groupId Identificador de grupo
    * @param userId Identificador de usuario
    * @throws Exception
    */
   public void  deleteGroupUser(int groupId, int userId) throws Exception
   {
      boolean commit = false;
	   boolean inTrans = false;
      
      if (_logger.isDebugEnabled())
         _logger.debug("deleteGroupUser");
      
      try
      {         
         
         DbConnection.open(CfgMdoConfig.getDbConfig());
         DbConnection.beginTransaction();
         
         inTrans = true;
         
         deleteBase(groupId,userId);
         
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
   
   public ArrayList loadUserGroupIds (int userId) throws Exception
   {
      ArrayList groupIds;	   
      
      if (_logger.isDebugEnabled())
         _logger.debug("loadUserGroupIds");
      
      try
      {         
         groupIds = new ArrayList();
         
         DbConnection.open(CfgMdoConfig.getDbConfig());
         
         loadGroupIds(userId,groupIds);
         
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
      
      return(groupIds);
   }
   
   private void loadGroupIds(int userId,ArrayList groupIds) throws Exception
   {
      IeciTdLongIntegerArrayList ids;
      String qual;
      String colName;
      UsersTable table = new UsersTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("loadGroupIds");
      
      ids = new IeciTdLongIntegerArrayList();
      colName = table.getGURelGroupColumnName();
      qual = table.getLoadGURelUserIdQual(userId);
      DbSelectFns.select(table.getGURelTableName(),colName,qual,true,ids);
      
      for (int i=0; i <ids.count(); i++)
      {
         Integer val = new Integer(ids.get(i));
         groupIds.add(val);
      }
      
      
   }
   
   private void deleteBase(int grpId,int usrId) throws Exception
   {
      UsersTable table = new UsersTable();
      
      DbDeleteFns.delete(table.getGURelTableName(), table.getLoadGURelQual(usrId,grpId));
      
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

      statement.setLongInteger(index++, _grpId);      
      statement.setLongInteger(index++, _usrId);
      
      return new Integer(index);
   }
   
   /**
    * Inserta la parte base de la tabla de relación grupos - usuarios.
    *  
    * @throws Exception Si se produce algún error en la inserción.
    */
    
   private void insertBase() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      UsersTable table = new UsersTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("insertBase");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(UsersTable.class.getName());
         tableInfo.setTablesMethod("getGURelTableName");
         tableInfo.setColumnsMethod("getInsertGURelColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(GrpUsrRelImpl.class.getName());
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
   
   private int _grpId;
   private int _usrId;
   
   private static final Logger _logger = Logger.getLogger(GrpUsrRelImpl.class);
}
