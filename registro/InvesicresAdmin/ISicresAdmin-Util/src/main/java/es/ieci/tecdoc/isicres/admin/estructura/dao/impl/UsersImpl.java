package es.ieci.tecdoc.isicres.admin.estructura.dao.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUpdateFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicRow;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicRows;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicTable;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.xml.lite.XmlTextBuilder;
import es.ieci.tecdoc.isicres.admin.database.UsersTable;
import es.ieci.tecdoc.isicres.admin.estructura.dao.User;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Users;

/**
 * Implementación de la clase Users.  Maneja la lista de usuarios invesDoc.
 */
public class UsersImpl implements Users
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
    * Carga la lista de usuarios con su información básica, según sus ids.
    *
    * @param idsUser Identificador de los usuarios.
    * @param superusuarios Para indicar si consultar los superusuarios o no.
    * @throws Exception Si se produce algún error en la carga de los usuarios.
    */
   public void loadByIdsUser(int aplicacion, int idsUser[], boolean superusers, String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      UsersTable table = new UsersTable();
      int counter;
      UserImpl user;

      DbConnection dbConn=new DbConnection();

      if (_logger.isDebugEnabled())
         _logger.debug("load");

		try	{
			 dbConn.open(DBSessionManager.getSession());

	         tableInfo.setTableObject(table);
	         tableInfo.setClassName(UsersTable.class.getName());
	         tableInfo.setTablesMethod("getBaseTableNameWithPerfil");
	         tableInfo.setColumnsMethod("getLoadBaseIdNameColumnNames");

	         rowInfo.setClassName(UserImpl.class.getName());
	         rowInfo.setValuesMethod("loadIdNameValues");
	         rowsInfo.add(rowInfo);

	         DynamicFns.selectMultiple(dbConn, table.getLoadBaseByIdsUser(aplicacion, idsUser, superusers),
	        		 false, tableInfo, rowsInfo);

	         for (counter = 0; counter < rowInfo.getRowCount(); counter++) {
	            user = (UserImpl)rowInfo.getRow(counter);
	            user.load(user.getId(),false, entidad);
	            add(user);
	         }
		}catch(Exception e){
			_logger.error(e);
			throw e;
		}finally {
			dbConn.close();
      }
   }

   /**
    * Carga la lista de usuarios con su información básica, según sus ids.
    *
    * @param aplicacion Identificador de la aplicacion que se esta utilizando
    * @param deptId Identificador del departamento.
    * @param superusers para obtener los superusuarios o los de tipo usuario.
    * @param entidad con la que se esta trabajando
    * @throws Exception Si se produce algún error en la carga de los usuarios.
    */
   public void loadUsersAsociation(int aplicacion, int deptId, boolean superusers, String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      UsersTable table = new UsersTable();
      int counter;
      UserImpl user;

      DbConnection dbConn=new DbConnection();

      if (_logger.isDebugEnabled())
         _logger.debug("load");

		try	{
			 dbConn.open(DBSessionManager.getSession());

	         tableInfo.setTableObject(table);
	         tableInfo.setClassName(UsersTable.class.getName());
	         tableInfo.setTablesMethod("getBaseTableNameWithPerfil");
	         tableInfo.setColumnsMethod("getLoadBaseIdNameColumnNames");

	         rowInfo.setClassName(UserImpl.class.getName());
	         rowInfo.setValuesMethod("loadIdNameValues");
	         rowsInfo.add(rowInfo);

	         DynamicFns.selectMultiple(dbConn, table.getLoadUsersAsociation(aplicacion, deptId, superusers), false, tableInfo, rowsInfo);

	         for (counter = 0; counter < rowInfo.getRowCount(); counter++) {
	            user = (UserImpl)rowInfo.getRow(counter);
	            user.load(user.getId(),false, entidad);
	            add(user);
	         }
		}catch(Exception e){
			_logger.error(e);
			throw e;
		}finally {
			dbConn.close();
      }
   }

   /**
    * Carga la lista de usuarios ya asociados a una oficina.
    *
    * @throws Exception Si se produce algún error en la carga de los usuarios.
    */
   public void loadUsersAssociated(int []idsUser, int idOfic, String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      UsersTable table = new UsersTable();
      int counter;
      UserImpl user;

      DbConnection dbConn=new DbConnection();

      if (_logger.isDebugEnabled())
         _logger.debug("load");

		try	{
			 dbConn.open(DBSessionManager.getSession());

	         tableInfo.setTableObject(table);
	         tableInfo.setClassName(UsersTable.class.getName());
	         tableInfo.setTablesMethod("getBaseTableName");
	         tableInfo.setColumnsMethod("getLoadBaseIdNameColumnNames");

	         rowInfo.setClassName(UserImpl.class.getName());
	         rowInfo.setValuesMethod("loadIdNameValues");
	         rowsInfo.add(rowInfo);

	         DynamicFns.selectMultiple(dbConn, table.getLoadUsersAssociated(idsUser, idOfic), false, tableInfo, rowsInfo);

	         for (counter = 0; counter < rowInfo.getRowCount(); counter++) {
	            user = (UserImpl)rowInfo.getRow(counter);
	            user.load(user.getId(),false, entidad);
	            add(user);
	         }
		}catch(Exception e){
			_logger.error(e);
			throw e;
		}finally {
			dbConn.close();
      }
   }

   /**
    * Carga la lista de usuarios con su información básica, según un departamento.
    *
    * @param deptId Identificador del departamento.
    * @throws Exception Si se produce algún error en la carga de los usuarios.
    */
   public void loadByDept(int deptId, String entidad) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      UsersTable table = new UsersTable();
      int counter;
      UserImpl user;

      DbConnection dbConn=new DbConnection();

      if (_logger.isDebugEnabled())
         _logger.debug("load");

		try
		{
			dbConn.open(DBSessionManager.getSession());
			//dbConn.open("org.postgresql.Driver", "jdbc:postgresql://128.90.111.90/registro_001", "postgres", "postgres");

         tableInfo.setTableObject(table);
         tableInfo.setClassName(UsersTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getLoadBaseIdNameColumnNames");

         rowInfo.setClassName(UserImpl.class.getName());
         rowInfo.setValuesMethod("loadIdNameValues");
         rowsInfo.add(rowInfo);

         DynamicFns.selectMultiple(dbConn, table.getLoadBaseByDeptQual(deptId), false, tableInfo,
                                 rowsInfo);

         for (counter = 0; counter < rowInfo.getRowCount(); counter++)
         {
            user = (UserImpl)rowInfo.getRow(counter);
            user.load(user.getId(),false, entidad);
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
         dbConn.close();
      }


   }

   /**
    * Carga la lista de usuarios con su información básica, según un grupo.
    *
    * @param groupId Identificador del grupo.
    * @throws Exception Si se produce algún error en la carga de los usuarios.
    */

   public void loadByGroup(int groupId, String entidad) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      UsersTable table = new UsersTable();
      int counter;
      UserImpl user;

      DbConnection dbConn=new DbConnection();

      if (_logger.isDebugEnabled())
         _logger.debug("load");

		try
		{
			dbConn.open(DBSessionManager.getSession());

         tableInfo.setTableObject(table);
         tableInfo.setClassName(UsersTable.class.getName());
         tableInfo.setTablesMethod("getGURelTableName");
         tableInfo.setColumnsMethod("getLoadBaseIdColumnName");

         rowInfo.setClassName(UserImpl.class.getName());
         rowInfo.setValuesMethod("loadIdValue");
         rowsInfo.add(rowInfo);

         DynamicFns.selectMultiple(dbConn, table.getLoadBaseByGroupQual(groupId), false, tableInfo,
                                 rowsInfo);

         for (counter = 0; counter < rowInfo.getRowCount(); counter++)
         {
            user = (UserImpl)rowInfo.getRow(counter);
            user.load(user.getId(),false, entidad);
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
         dbConn.close();
      }


   }

   /**
    * Carga la lista de usuarios con su información básica.
    *
    * @param subName Partícula que tiene que contener en el nombre la lista de usuarios.
    * @throws Exception Si se produce algún error en la carga de los usuarios.
    */
   public void loadBySubName(String subName, String entidad) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows  rowsInfo  = new DynamicRows();
      DynamicRow   rowInfo   = new DynamicRow();
      UsersTable   table     = new UsersTable();
      int          counter;
      UserImpl     user;

      DbConnection dbConn=new DbConnection();

      if (_logger.isDebugEnabled())
         _logger.debug("load");

		try
		{
			dbConn.open(DBSessionManager.getSession());

         tableInfo.setTableObject(table);
         tableInfo.setClassName(UsersTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getLoadBaseIdNameColumnNames");

         rowInfo.setClassName(UserImpl.class.getName());
         rowInfo.setValuesMethod("loadIdNameValues");
         rowsInfo.add(rowInfo);

         DynamicFns.selectMultiple(dbConn, table.getLoadBaseBySubNameQual(subName), false, tableInfo,
                                 rowsInfo);

         for (counter = 0; counter < rowInfo.getRowCount(); counter++)
         {
            user = (UserImpl)rowInfo.getRow(counter);
            user.load(user.getId(),false, entidad);
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
         dbConn.close();
      }

   }

   public void loadByAplicacion(int aplicacion, int deptId[], boolean sinPermisos, boolean usuarios, boolean superusuarios, String entidad) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows  rowsInfo  = new DynamicRows();
      DynamicRow   rowInfo   = new DynamicRow();
      UsersTable   table     = new UsersTable();
      int          counter;
      UserImpl     user;

      DbConnection dbConn=new DbConnection();

      if (_logger.isDebugEnabled())
         _logger.debug("load");

		try
		{
			dbConn.open(DBSessionManager.getSession());

         tableInfo.setTableObject(table);
         tableInfo.setClassName(UsersTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableNameWithPerfil");
         tableInfo.setColumnsMethod("getLoadBaseIdNameColumnNames");

         rowInfo.setClassName(UserImpl.class.getName());
         rowInfo.setValuesMethod("loadIdNameValues");
         rowsInfo.add(rowInfo);

         DynamicFns.selectMultiple(dbConn, table.getLoadBaseByAplicacion(aplicacion, deptId, sinPermisos, usuarios, superusuarios), false, tableInfo,
                                 rowsInfo);

         for (counter = 0; counter < rowInfo.getRowCount(); counter++)
         {
            user = (UserImpl)rowInfo.getRow(counter);
            user.load(user.getId(),false, entidad);
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
         dbConn.close();
      }

   }

   /**
    * mueve un usuario de un departamento a otro.
    * @param userId Identificador de usuario.
    * @param deptOrg Identificador del departamento al que pertenece.
    * @throws Exception
    */
   public void moveUser(int userId, int deptDest, String entidad) throws Exception
   {
      boolean    commit = false;
      boolean    inTrans = false;
      UsersTable tbl = new UsersTable();

      DbConnection dbConn=new DbConnection();

      if (_logger.isDebugEnabled())
         _logger.debug("moveUser");

      try
      {
    	 dbConn.open(DBSessionManager.getSession());
         dbConn.beginTransaction();
         inTrans = true;

         DbUpdateFns.updateLongInteger(dbConn, tbl.getBaseTableName(),tbl.getDeptIdUserColumnName(),
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
            dbConn.endTransaction(commit);
         dbConn.close();
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

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.admin.estructura.dao.Users#getUser(int)
	 */
	public User getUser(int index) {
		// TODO Plantilla de método auto-generado
		return (User)_list.get(index);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.admin.estructura.dao.Users#toXML()
	 */
	public String toXML() {
		// TODO Plantilla de método auto-generado
		return null;
	}



private ArrayList _list;
   private static final Logger _logger = Logger.getLogger(UsersImpl.class);
}
