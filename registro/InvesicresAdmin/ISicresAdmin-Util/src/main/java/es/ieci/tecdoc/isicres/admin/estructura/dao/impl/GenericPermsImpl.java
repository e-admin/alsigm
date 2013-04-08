
package es.ieci.tecdoc.isicres.admin.estructura.dao.impl;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDeleteFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicRow;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicRows;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicTable;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.database.LdapUsersTable;
import es.ieci.tecdoc.isicres.admin.estructura.dao.GenericPerms;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Permissions;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminUserDefsKeys;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminUserKeys;
import es.ieci.tecdoc.isicres.admin.exception.ISicresAdminBasicException;

/**
 * Representa los permisos de un elemento de invesDoc.
 */

public class GenericPermsImpl implements GenericPerms
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

   public void deletePerms(int id, int dest, String entidad) throws Exception
   {
      LdapUsersTable table = new LdapUsersTable();

      if (_logger.isDebugEnabled())
         _logger.debug("deletePerms");

      DbConnection dbConn=new DbConnection();
      try{
      	dbConn.open(DBSessionManager.getSession());
	      DbDeleteFns.delete(dbConn, table.getPermsTableName(), table.getDeletePermsQual(id,
	                         dest));
      }catch (Exception e){
    		_logger.error(e);
    		throw e;
    	}finally{
    		dbConn.close();
    	}
   }

   public Permissions getPermissions()
   {
      return _perms;
   }

   /**
    * Inserta la parte de permisos de las tablas de elemento.
    *
    * @throws Exception Si se produce algún error en la inserción del elemento.
    */

   public void insertPerms(String entidad) throws Exception
   {
      int counter;
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo;
      DynamicRow rowInfo;
      LdapUsersTable table = new LdapUsersTable();

      if (_logger.isDebugEnabled())
         _logger.debug("insertPerms");

      DbConnection dbConn=new DbConnection();
      try{
      	 dbConn.open(DBSessionManager.getSession());
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

            DynamicFns.insert(dbConn, tableInfo, rowsInfo);
         }

      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}finally{
			dbConn.close();
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

   public void updatePerm(int id, int productId, int dest, String entidad) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo;
      DynamicRow rowInfo;
      LdapUsersTable table = new LdapUsersTable();

      if (_logger.isDebugEnabled())
         _logger.debug("updatePerm");

      DbConnection dbConn=new DbConnection();
      try{
      	  dbConn.open(DBSessionManager.getSession());
    	  //dbConn.open("org.postgresql.Driver", "jdbc:postgresql://128.90.111.90/registro_000", "postgres", "postgres");

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

	      DynamicFns.update(dbConn, table.getUpdatePermsQual(id,dest, productId), tableInfo,
	                                                 rowsInfo);
      }catch (Exception e){
    		_logger.error(e);
    		throw e;
    	}finally{
    		dbConn.close();
    	}
   }

   /**
    * Actualiza la parte de permisos de las tablas de elemento.
    *
    * @param id Identificador del elemento.
    * @param dest Destino del permiso.
    * @throws Exception Si se produce algún error en la actualización.
    */

   public void updatePerms(int id, int dest, String entidad) throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("updatePerms");

      updatePerm(id, ISicresAdminUserDefsKeys.PRODUCT_IDOC, dest, entidad);
      updatePerm(id, ISicresAdminUserDefsKeys.PRODUCT_IFLOW, dest, entidad);
      updatePerm(id, ISicresAdminUserDefsKeys.PRODUCT_ISICRES, dest, entidad);
   }

   /**
    * Lee los permisos del elemento invesDoc.
    *
    * @param id Identificador del elemento.
    * @param dest Destinatario de los permisos.
    * @throws Exception Si se produce algún error en la lectura de los permisos
    * del elemento.
    */

   public void loadPerms(int id, int dest, String entidad) throws Exception
   {
      int counter;
      PermImpl perm;
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      LdapUsersTable table = new LdapUsersTable();

      if (_logger.isDebugEnabled())
         _logger.debug("loadPerms");

      DbConnection dbConn=new DbConnection();
      try {
    	  dbConn.open(DBSessionManager.getSession());
    	  //dbConn.open("org.postgresql.Driver", "jdbc:postgresql://128.90.111.90/registro_000", "postgres", "postgres");

         tableInfo.setTableObject(table);
         tableInfo.setClassName(LdapUsersTable.class.getName());
         tableInfo.setTablesMethod("getPermsTableName");
         tableInfo.setColumnsMethod("getLoadPermsColumnNames");

         rowInfo.setClassName(PermImpl.class.getName());
         rowInfo.setValuesMethod("loadValues");
         rowsInfo.add(rowInfo);

         if (!DynamicFns.selectMultiple(dbConn, table.getLoadPermsQual(id,dest), false, tableInfo, rowsInfo))
         {
            ISicresAdminBasicException.throwException(ISicresAdminUserKeys.EC_USER_NOT_PERMS);
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
		}finally{
			dbConn.close();
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

   public void setDefaultPerms(int id, int dest) throws Exception
   {
      PermImpl perm;

      perm = new PermImpl(id, dest, ISicresAdminUserDefsKeys.PRODUCT_IDOC,
                         ISicresAdminUserDefsKeys.PERMISSION_QUERY);
      _perms.add(perm);

      perm = new PermImpl(id, dest, ISicresAdminUserDefsKeys.PRODUCT_ISICRES,
                        ISicresAdminUserDefsKeys.PERMISSION_NONE);
      _perms.add(perm);

      perm = new PermImpl(id, dest, ISicresAdminUserDefsKeys.PRODUCT_IFLOW,
                         ISicresAdminUserDefsKeys.PERMISSION_NONE);
      _perms.add(perm);

   }

   public Permissions get_perms() {
	   return _perms;
   }

   protected PermsImpl _perms;

   private static final Logger _logger = Logger.getLogger(GenericProfilesImpl.class);

}