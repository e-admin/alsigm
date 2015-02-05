
package es.ieci.tecdoc.isicres.admin.estructura.dao.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicRow;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicRows;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicTable;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.xml.lite.XmlTextBuilder;
import es.ieci.tecdoc.isicres.admin.database.LdapUsersTable;
import es.ieci.tecdoc.isicres.admin.estructura.dao.LdapUser;

/**
 * Implementación de la clase LdapUsers.  Maneja la lista de usuarios invesDoc 
 * relacionados con un servicio de directorio Ldap.
 */

public class LdapUsersImpl 
{

   public LdapUsersImpl()
   {
      _list = new ArrayList();
   }

   public int count() 
   {
      return _list.size();
   }

   public LdapUser get(int index) 
   {
      return (LdapUser)_list.get(index);
   }
   
   public void load(boolean full, String entidad) throws Exception 
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      LdapUsersTable table = new LdapUsersTable();
      int counter;
      LdapUserImpl user;
   
      if (_logger.isDebugEnabled())
         _logger.debug("loadLite");
      
      DbConnection dbConn=new DbConnection();
      try {
      	 dbConn.open(DBSessionManager.getSession());
      
      	 dbConn.beginTransaction();
      
         tableInfo.setTableObject(table);
         tableInfo.setClassName(LdapUsersTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getLoadBaseAllColumnNames");
         
         rowInfo.setClassName(LdapUserImpl.class.getName());
         rowInfo.setValuesMethod("loadAllValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.selectMultiple(dbConn, table.getLoadBaseAllQual(), false, tableInfo, rowsInfo);
         
         for (counter = 0; counter < rowInfo.getRowCount(); counter++)
         {
            user = (LdapUserImpl)rowInfo.getRow(counter);
            user.load(user.getId(), full, entidad);
            add(user);
         }
         dbConn.endTransaction(true);
      } catch(Exception e) {
    	  _logger.error(e);
          throw e;
      } finally {    	  
          dbConn.close();
      }
   }
   
   public void loadUsersLdapAssociated(int []idsUser, int idOfic, String entidad) throws Exception {
	   DynamicTable tableInfo = new DynamicTable(); 
	   DynamicRows rowsInfo = new DynamicRows();
	   DynamicRow rowInfo = new DynamicRow();
	   LdapUsersTable table = new LdapUsersTable();
	   int counter;
	   LdapUserImpl user;
      
	   if (_logger.isDebugEnabled())
	         _logger.debug("loadLite");
	   
      DbConnection dbConn=new DbConnection();
      try {
      	 dbConn.open(DBSessionManager.getSession());
      
      	 dbConn.beginTransaction();
      
         tableInfo.setTableObject(table);
         tableInfo.setClassName(LdapUsersTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getLoadBaseAllColumnNames");
         
         rowInfo.setClassName(LdapUserImpl.class.getName());
         rowInfo.setValuesMethod("loadAllValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.selectMultiple(dbConn, table.getLoadUsersAssociated(idsUser, idOfic), false, tableInfo, rowsInfo);
         
         for (counter = 0; counter < rowInfo.getRowCount(); counter++)
         {
            user = (LdapUserImpl)rowInfo.getRow(counter);
            user.load(user.getId(), true, entidad);
            add(user);
         }
         dbConn.endTransaction(true);
      } catch(Exception e) {
    	  _logger.error(e);
          throw e;
      } finally {    	  
          dbConn.close();
      }
   }
   
   public void loadByAplicacion(int aplicacion, int deptId[],
		   boolean sinPermisos, boolean usuarios, boolean superusuarios,
		   String entidad) throws Exception {
		
	   DynamicTable tableInfo = new DynamicTable();	  
	   DynamicRows rowsInfo = new DynamicRows();
	   DynamicRow rowInfo = new DynamicRow();
	   LdapUsersTable table = new LdapUsersTable();
	   int counter;
	   LdapUserImpl user;

	   DbConnection dbConn = new DbConnection();

	   if (_logger.isDebugEnabled())
		   _logger.debug("load");
		
	   try {
		   dbConn.open(DBSessionManager.getSession());
		   
		   tableInfo.setTableObject(table);
		   tableInfo.setClassName(LdapUsersTable.class.getName());
		   tableInfo.setTablesMethod("getUserByAplicationTableNames");
		   tableInfo.setColumnsMethod("getLoadBaseAllColumnNames");
		   
		   rowInfo.setClassName(LdapUserImpl.class.getName());
		   rowInfo.setValuesMethod("loadAllValues");
		   rowsInfo.add(rowInfo);
		   
		   DynamicFns.selectMultiple(dbConn, table.getLoadBaseByAplicacion(
				   aplicacion, deptId, sinPermisos, usuarios, superusuarios),
				   true, tableInfo, rowsInfo);
		
		   for (counter = 0; counter < rowInfo.getRowCount(); counter++) {
			   user = (LdapUserImpl) rowInfo.getRow(counter);
			   user.load(user.getId(), false, entidad);
			   add(user);
		   }
	   } catch (Exception e) {
		   _logger.error(e);
		   throw e;
	   } finally {
		   dbConn.close();
	   }
   }
   
   
   /**
	 * Carga la lista de usuarios con su información básica, según sus ids.
	 * 
	 * @param idsUser
	 *            Identificador de los usuarios.
	 * @param superusuarios
	 *            Para indicar si consultar los superusuarios o no.
	 * @throws Exception
	 *             Si se produce algún error en la carga de los usuarios.
	 */
	public void loadByIdsUser(int aplicacion, int idsUser[],
			boolean superusers, String entidad) throws Exception {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		LdapUsersTable table = new LdapUsersTable();
		int counter;
		LdapUserImpl user;

		DbConnection dbConn = new DbConnection();

		if (_logger.isDebugEnabled())
			_logger.debug("load");

		try {
			dbConn.open(DBSessionManager.getSession());

			tableInfo.setTableObject(table);
			tableInfo.setClassName(LdapUsersTable.class.getName());
			tableInfo.setTablesMethod("getBaseTableNameWithPerfil");
			tableInfo.setColumnsMethod("getLoadBaseAllColumnNames");

			rowInfo.setClassName(UserImpl.class.getName());
			rowInfo.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfo);

			DynamicFns.selectMultiple(dbConn, table.getLoadBaseByIdsUser(
					aplicacion, idsUser, superusers), false, tableInfo,
					rowsInfo);

			for (counter = 0; counter < rowInfo.getRowCount(); counter++) {
				user = (LdapUserImpl) rowInfo.getRow(counter);
				user.load(user.getId(), false, entidad);
				add(user);
			}
		} catch (Exception e) {
			_logger.error(e);
			throw e;
		} finally {
			dbConn.close();
		}
	}
   
   public String toXML(boolean header) 
   {
      XmlTextBuilder bdr;
      String tagName = "LdapUsers";
      LdapUserImpl user;
      
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
    
   private LdapUserImpl getImpl(int index)
   {
      return (LdapUserImpl)_list.get(index);  
   }
 
   /**
    * Añade un usuario a la lista.
    * 
    * @param user El usuario mencionado.
    *  
    */

   protected void add(LdapUserImpl user) 
   {
      _list.add(user);
   }


   private ArrayList _list;
   private static final Logger _logger = Logger.getLogger(LdapUsersImpl.class);
}