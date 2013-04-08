package ieci.tecdoc.idoc.admin.internal;

import java.util.ArrayList;



import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.idoc.admin.api.user.GroupUserRel;
import ieci.tecdoc.idoc.admin.database.UsersTable;
import ieci.tecdoc.sgm.base.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbSelectFns;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;

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
   public void  assignUserToGroup(int groupId, int userId, String entidad) throws Exception
   { 
      boolean commit = false;
	   boolean inTrans = false;
      
      if (_logger.isDebugEnabled())
         _logger.debug("assignUserToGroup");
      
      DbConnection dbConn=new DbConnection(); 
      try
      {
         _grpId = groupId;
         _usrId = userId;
         
         dbConn.open(DBSessionManager.getSession(entidad));
         dbConn.beginTransaction();
         
         inTrans = true;
         insertBase(entidad);
         
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
            dbConn.endTransaction(commit);
         
         
         dbConn.close();
      }
      
   }
   
   /**
    * Elimina un usuario de un grupo
    * @param groupId Identificador de grupo
    * @param userId Identificador de usuario
    * @throws Exception
    */
   public void  deleteGroupUser(int groupId, int userId, String entidad) throws Exception
   {
      boolean commit = false;
	   boolean inTrans = false;
      
      if (_logger.isDebugEnabled())
         _logger.debug("deleteGroupUser");
      
      DbConnection dbConn=new DbConnection(); 
      try
      {         
         
         dbConn.open(DBSessionManager.getSession(entidad));
         dbConn.beginTransaction();
         
         inTrans = true;
         
         deleteBase(groupId,userId, entidad);
         
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
            dbConn.endTransaction(commit);
         
         
         dbConn.close();
      }
   }
   
   public ArrayList loadUserGroupIds (int userId, String entidad) throws Exception
   {
      ArrayList groupIds;	   
      
      if (_logger.isDebugEnabled())
         _logger.debug("loadUserGroupIds");
      
      try
      {         
         groupIds = new ArrayList();
         
         loadGroupIds(userId,groupIds, entidad);
         
      }
      catch(Exception e)
      {
         _logger.error(e);
         throw e;
      }
      
      return(groupIds);
   }
   
   private void loadGroupIds(int userId,ArrayList groupIds, String entidad) throws Exception
   {
      IeciTdLongIntegerArrayList ids;
      String qual;
      String colName;
      UsersTable table = new UsersTable();
      
      
      DbConnection dbConn=new DbConnection();      
      try{
    	  dbConn.open(DBSessionManager.getSession(entidad));
	      if (_logger.isDebugEnabled())
	         _logger.debug("loadGroupIds");
	      
	      ids = new IeciTdLongIntegerArrayList();
	      colName = table.getGURelGroupColumnName();
	      qual = table.getLoadGURelUserIdQual(userId);
	      DbSelectFns.select(dbConn, table.getGURelTableName(),colName,qual,true,ids);
	      
	      for (int i=0; i <ids.count(); i++)
	      {
	         Integer val = new Integer(ids.get(i));
	         groupIds.add(val);
	      }
      }catch (Exception e) {
			_logger.error(e);
			throw e;
	}finally{
		  dbConn.close();
	}
      
   }
   
   private void deleteBase(int grpId,int usrId, String entidad) throws Exception
   {
      UsersTable table = new UsersTable();
      
      DbConnection dbConn=new DbConnection();      
      try{
    	  dbConn.open(DBSessionManager.getSession(entidad));
    	  DbDeleteFns.delete(dbConn, table.getGURelTableName(), table.getLoadGURelQual(usrId,grpId));
      }catch (Exception e) {
				_logger.error(e);
				throw e;
      }finally{
    	  dbConn.close();
      }
      
      	
      
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
    
   private void insertBase(String entidad) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      UsersTable table = new UsersTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("insertBase");

      DbConnection dbConn=new DbConnection();
      try {
    	  dbConn.open(DBSessionManager.getSession(entidad));
    	  
         tableInfo.setTableObject(table);
         tableInfo.setClassName(UsersTable.class.getName());
         tableInfo.setTablesMethod("getGURelTableName");
         tableInfo.setColumnsMethod("getInsertGURelColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(GrpUsrRelImpl.class.getName());
         rowInfo.setValuesMethod("insertValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.insert(dbConn, tableInfo, rowsInfo);
         
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}finally{
			dbConn.close();
		}
   }
   
   private int _grpId;
   private int _usrId;
   
   private static final Logger _logger = Logger.getLogger(GrpUsrRelImpl.class);

/**
 * {@inheritDoc}
 * @see ieci.tecdoc.idoc.admin.api.user.GroupUserRel#deleteGroup(int, java.lang.String)
 */
public void deleteGroup(int groupId, String entidad) throws Exception {
	UsersTable table = new UsersTable();
    
    DbConnection dbConn=new DbConnection();      
    try{
  	  dbConn.open(DBSessionManager.getSession(entidad));
  	  DbDeleteFns.delete(dbConn, table.getGURelTableName(), table.getLoadGURelGroupIdQual(groupId));
    }catch (Exception e) {
				_logger.error(e);
				throw e;
    }finally{
  	  dbConn.close();
    }
	
}
}
