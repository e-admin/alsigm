package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbUpdateFns;
import ieci.tecdoc.core.xml.lite.XmlTextBuilder;
import ieci.tecdoc.idoc.admin.api.user.User;
import ieci.tecdoc.idoc.admin.database.UsersTable;
import ieci.tecdoc.idoc.core.database.DynamicFns;
import ieci.tecdoc.idoc.core.database.DynamicRow;
import ieci.tecdoc.idoc.core.database.DynamicRows;
import ieci.tecdoc.idoc.core.database.DynamicTable;
import ieci.tecdoc.sbo.config.CfgMdoConfig;


import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * Implementación de la clase Users.  Maneja la lista de usuarios invesDoc.
 */
public class UsersImpl
{
	public UsersImpl()
   {
      _list = new ArrayList();
   }

   public int count() 
   {
      return _list.size();
   }

   public User get(int index) 
   {
      return (User)_list.get(index);
   }
   
   /**
    * Carga la lista de usuarios con su información básica, según un departamento.
    * 
    * @param deptId Identificador del departamento.
    * @throws Exception Si se produce algún error en la carga de los usuarios.
    */
   public void loadByDept(int deptId) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      UsersTable table = new UsersTable();
      int counter;
      UserImpl user;
   
      if (_logger.isDebugEnabled())
         _logger.debug("load");
      
		try
		{
		   DbConnection.open(CfgMdoConfig.getDbConfig());
		   
         tableInfo.setTableObject(table);
         tableInfo.setClassName(UsersTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getLoadBaseIdNameColumnNames");
         
         rowInfo.setClassName(UserImpl.class.getName());
         rowInfo.setValuesMethod("loadIdNameValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.selectMultiple(table.getLoadBaseByDeptQual(deptId), false, tableInfo, 
                                 rowsInfo);
         
         for (counter = 0; counter < rowInfo.getRowCount(); counter++)
         {
            user = (UserImpl)rowInfo.getRow(counter);
            user.load(user.getId(),false);            
            add(user);
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
   
   /**
    * Carga la lista de usuarios con su información básica, según un grupo.
    * 
    * @param groupId Identificador del grupo.
    * @throws Exception Si se produce algún error en la carga de los usuarios.
    */
   
   public void loadByGroup(int groupId) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      UsersTable table = new UsersTable();
      int counter;
      UserImpl user;
   
      if (_logger.isDebugEnabled())
         _logger.debug("load");
      
		try
		{			
		   DbConnection.open(CfgMdoConfig.getDbConfig());
		   
         tableInfo.setTableObject(table);
         tableInfo.setClassName(UsersTable.class.getName());
         tableInfo.setTablesMethod("getGURelTableName");
         tableInfo.setColumnsMethod("getLoadBaseIdColumnName");
         
         rowInfo.setClassName(UserImpl.class.getName());
         rowInfo.setValuesMethod("loadIdValue");
         rowsInfo.add(rowInfo);
         
         DynamicFns.selectMultiple(table.getLoadBaseByGroupQual(groupId), false, tableInfo, 
                                 rowsInfo);
         
         for (counter = 0; counter < rowInfo.getRowCount(); counter++)
         {
            user = (UserImpl)rowInfo.getRow(counter);
            user.load(user.getId(),false);
            add(user);
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

   /**
    * Carga la lista de usuarios con su información básica.
    * 
    * @param subName Partícula que tiene que contener en el nombre la lista de usuarios.
    * @throws Exception Si se produce algún error en la carga de los usuarios.
    */
   public void loadBySubName(String subName) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows  rowsInfo  = new DynamicRows();
      DynamicRow   rowInfo   = new DynamicRow();
      UsersTable   table     = new UsersTable();
      int          counter;
      UserImpl     user;
   
      if (_logger.isDebugEnabled())
         _logger.debug("load");
      
		try
		{
		   DbConnection.open(CfgMdoConfig.getDbConfig());
		   
         tableInfo.setTableObject(table);
         tableInfo.setClassName(UsersTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getLoadBaseIdNameColumnNames");
         
         rowInfo.setClassName(UserImpl.class.getName());
         rowInfo.setValuesMethod("loadIdNameValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.selectMultiple(table.getLoadBaseBySubNameQual(subName), false, tableInfo, 
                                 rowsInfo);
         
         for (counter = 0; counter < rowInfo.getRowCount(); counter++)
         {
            user = (UserImpl)rowInfo.getRow(counter);
            user.load(user.getId(),false);            
            add(user);
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
   
   /**
    * mueve un usuario de un departamento a otro.
    * @param userId Identificador de usuario.     
    * @param deptOrg Identificador del departamento al que pertenece. 
    * @throws Exception
    */
   public void moveUser(int userId, int deptDest) throws Exception
   {
      boolean    commit = false;
      boolean    inTrans = false;
      UsersTable tbl = new UsersTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("moveUser");
      
      try
      {
         DbConnection.open(CfgMdoConfig.getDbConfig());
         
         DbConnection.beginTransaction();
         inTrans = true;
      
         DbUpdateFns.updateLongInteger(tbl.getBaseTableName(),tbl.getDeptIdUserColumnName(),
            								deptDest,tbl.getLoadBaseQual(userId));
         
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
   
   public String toXML(boolean header) 
   {
      XmlTextBuilder bdr;
      String tagName = "Users";
      UserImpl user;
      
      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      for (int i = 0; i < count(); i++)
      {
         user = getImpl(i);
         bdr.addFragment(user.toXML(false));
      }

      bdr.addClosingTag(tagName);
      
      return bdr.getText();
   }

	public String toString()
	{
      return toXML(false);
   }

    /**
    * Devuelve un usuario de la lista.
    * 
    * @param index Indice del usuario que se desea recuperar.
    * 
    * @return El usuario mencionado.
    */
    
   private UserImpl getImpl(int index)
   {
      return (UserImpl)_list.get(index);  
   }
 
   /**
    * Añade un usuario a la lista.
    * 
    * @param user El usuario mencionado.
    *  
    */

   protected void add(UserImpl user) 
   {
      _list.add(user);
   }


   private ArrayList _list;
   private static final Logger _logger = Logger.getLogger(UsersImpl.class);
}
