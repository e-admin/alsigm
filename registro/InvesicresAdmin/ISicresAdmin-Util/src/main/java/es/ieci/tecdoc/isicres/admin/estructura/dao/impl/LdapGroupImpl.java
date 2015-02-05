
package es.ieci.tecdoc.isicres.admin.estructura.dao.impl;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDeleteFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbInputStatement;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbOutputStatement;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUtil;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicRow;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicRows;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicTable;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnCfg;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnection;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapScope;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapUasFns;
import es.ieci.tecdoc.isicres.admin.core.xml.lite.XmlTextBuilder;
import es.ieci.tecdoc.isicres.admin.database.LdapGroupsTable;
import es.ieci.tecdoc.isicres.admin.database.LdapUsersTable;
import es.ieci.tecdoc.isicres.admin.estructura.dao.LdapGroup;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Permission;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Permissions;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminDefsKeys;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminGroupKeys;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminUserDefsKeys;
import es.ieci.tecdoc.isicres.admin.exception.ISicresAdminBasicException;
import es.ieci.tecdoc.isicres.admin.sbo.uas.ldap.UasConfigUtil;
import es.ieci.tecdoc.isicres.admin.sbo.uas.ldap.UasDaoLdapGroupTbl;
import es.ieci.tecdoc.isicres.admin.sbo.util.nextid.NextId;

/**
 * Representa un grupo de invesDoc
 */

public class LdapGroupImpl implements LdapGroup
{

   public LdapGroupImpl(int userConnected)
   {
      init(userConnected);
   }

   public LdapGroupImpl()
   {
      init(ISicresAdminDefsKeys.NULL_ID);
   }

   public void createGroup(String guid, String dn, String entidad) throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("createGroup: Guid: " + guid + "Dn: " + dn);

         _fullName = dn;
         _guid     = guid;

         if (UasDaoLdapGroupTbl.guidExists(_guid, entidad))
         {
            ISicresAdminBasicException.throwException(ISicresAdminGroupKeys.EC_GROUP_EXITS);
         }

         _permsImpl.setDefaultPerms(_id, ISicresAdminDefsKeys.DESTINATION_GROUP);


   }

   public void createGroup(String guid, String dn, int type, String entidad) throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("createGroup: Guid: " + guid + "Dn: " + dn + "Type:" + type);

      _type = type;
      createGroup(guid, dn, entidad);

   }

   public void createFromLdapGroup(String root, String attr, String value, String entidad)
               throws Exception

   {
	  LdapConnection ldapConn = new LdapConnection();
      LdapConnCfg ldapCfg = null;
      String dn;

      if (_logger.isDebugEnabled())
         _logger.debug("createFromLdapGroup: Root" + root + " Attribute: " +
                       attr + " Value: " + value);

		try
		{
         ldapCfg = UasConfigUtil.createLdapConnConfig(entidad);
			ldapConn.open(ldapCfg);

         dn = LdapUasFns.findGroupDn(ldapConn, root, LdapScope.SUBTREE, attr,
                                    value, entidad);
         _guid = LdapUasFns.findGroupGuidByDn(ldapConn, dn);
         if (UasDaoLdapGroupTbl.guidExists(_guid, entidad))
         {
            ISicresAdminBasicException.throwException(ISicresAdminGroupKeys.EC_GROUP_EXITS);
         }

         _fullName = dn;


         _permsImpl.setDefaultPerms(_id, ISicresAdminDefsKeys.DESTINATION_GROUP);

		}
		catch(Exception e)
		{
         _logger.error(e);
         throw e;
		}
      finally
      {
         ldapConn.close();
      }

   }

   public void load(int id, String entidad) throws Exception
   {
      load(id, true, entidad);
   }

   public void loadFromGuid(String guid, String entidad) throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("loadFromGuid: guid = " + guid);

		try
		{
         _guid = guid;

         loadBaseGuid(entidad);

         _permsImpl.loadPerms(_id, ISicresAdminDefsKeys.DESTINATION_GROUP, entidad);

		}
		catch(Exception e)
		{
         _logger.error(e);
         throw e;
		}

   }

   public void loadFromDeptId(int deptId, String entidad) throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("loadFromGuid: deptId = " + deptId);

		try
		{

         loadBaseByDeptId(deptId, entidad);

         _permsImpl.loadPerms(_id, ISicresAdminDefsKeys.DESTINATION_GROUP, entidad);

		}
		catch(Exception e)
		{
         _logger.error(e);
         throw e;
		}

   }

   public void load(int id, boolean full, String entidad) throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("load: Id = " + Integer.toString(id));

		try
		{
         _id = id;

         loadBase(entidad);
         if (full)
         {
            _permsImpl.loadPerms(_id, ISicresAdminDefsKeys.DESTINATION_GROUP, entidad);
         }
		}
		catch(Exception e)
		{
         _logger.error(e);
         throw e;
		}
   }

   public void store(String entidad) throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("store");

	  try{

         if (_id == ISicresAdminDefsKeys.NULL_ID)
         {
            _id = NextId.generateNextId(LdapUsersTable.TN_NEXTID,
                                 ISicresAdminDefsKeys.DESTINATION_GROUP, entidad);
            _permsImpl._perms.setId(_id);
            insert(entidad);
         }
         else
         {
            update(entidad);
         }

	  }catch(Exception e){
         _logger.error(e);
         throw e;
	  }

   }

   public void delete(String entidad) throws Exception
   {
      boolean commit = false;
      boolean inTrans = false;
      LdapUsersTable table = new LdapUsersTable();
      LdapGroupsTable tableGrp = new LdapGroupsTable();

      if (_logger.isDebugEnabled())
         _logger.debug("delete");

      DbConnection dbConn=new DbConnection();
		try
		{
			dbConn.open(DBSessionManager.getSession());

         dbConn.beginTransaction();
         inTrans = true;

         DbDeleteFns.delete(dbConn, tableGrp.getBaseTableName(),
                            tableGrp.getDeleteBaseQual(_id));
         _permsImpl.deletePerms(_id, ISicresAdminDefsKeys.DESTINATION_GROUP, entidad);
         DbDeleteFns.delete(dbConn, table.getObjPermsTableName(),
                            table.getDeleteObjPermsQual(_id,
                            ISicresAdminDefsKeys.OBJECT_OWNER_TYPE_GROUP));

         updateAllOwnership(dbConn);

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

   public String getGuid()
   {
      return _guid;
   }

   public String getFullName()
   {
      return _fullName;
   }

   public void setFullName(String fullName)
   {
      _fullName = fullName;
   }

   public int getId()
   {
      return _id;
   }

   public Permissions getPermissions()
   {
      return _permsImpl._perms;
   }

   /**
    * Obtiene la información del grupo Ldap en formato XML.
    *
    * @param header Indica si hay que incluir la cabecera xml o no.
    * @return La información mencionada.
    */

   public String toXML(boolean header)
   {
      XmlTextBuilder bdr;
      String tagName = "Group";

      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      bdr.addSimpleElement("Id", Integer.toString(_id));
      bdr.addSimpleElement("Guid", _guid);
      bdr.addSimpleElement("FullName", _fullName);

      bdr.addFragment(_permsImpl._perms.toXML(false));

      bdr.addClosingTag(tagName);

      return bdr.getText();
   }

   public String toXML()
   {
      return toXML(true);
   }

   /**
    * Muestra una representación de la clase en formato texto.
    *
    */

	public String toString()
	{
      return toXML(false);
   }

   /**
    * Actualiza en base de datos la información relativa a objetos propiedad
    * del usuario.
    *
    * @param statement
    * @param idx
    * @return
    * @throws java.lang.Exception
    */

   public Integer updateOwnershipValues(DbInputStatement statement, Integer idx)
                  throws Exception
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("updateOwnershipValues");

      statement.setLongInteger(index++, ISicresAdminDefsKeys.SYSSUPERUSER_ID);
      statement.setLongInteger(index++, _userConnected);
      statement.setDateTime(index++, DbUtil.getCurrentDateTime());

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
      statement.setShortText(index++, _guid);
      statement.setShortText(index++, _fullName);
      statement.setLongInteger(index++, 0);

      return new Integer(index);
   }

   /**
    * Recupera de la base de datos información asociada al grupo.
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
         _logger.debug("loadValues");

      _id = statement.getShortInteger(index++);
      _guid = statement.getShortText(index++);
      _fullName = statement.getShortText(index++);


      return new Integer(index);
   }

   /**
    * Recupera de la base de datos información asociada al grupo.
    *
    * @param statement
    * @param idx
    * @return
    * @throws java.lang.Exception
    */

   public Integer loadIdValue(DbOutputStatement statement, Integer idx)
                  throws Exception
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("loadValues");

      _id = statement.getShortInteger(index++);

      return new Integer(index);
   }

   /**
    * Recupera de la base de datos información asociada al grupo.
    *
    * @param statement
    * @param idx
    * @return
    * @throws java.lang.Exception
    */

   public Integer loadValues(DbOutputStatement statement, Integer idx)
                  throws Exception
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("loadValues");

      _guid = statement.getShortText(index++);
      _fullName = statement.getShortText(index++);

      return new Integer(index);
   }

   /**
    * Inserta un nuevo grupo en invesDoc.
    *
    * @throws Exception Si se produce algún error en la inserción del grupo.
    */

   private void insert(String entidad) throws Exception
   {
      boolean commit = false;
      boolean inTrans = false;
      int counter;

      if (_logger.isDebugEnabled())
         _logger.debug("insert");

      DbConnection dbConn=new DbConnection();
      try
      {
    	dbConn.open(DBSessionManager.getSession());
         dbConn.beginTransaction();
         inTrans = true;

         insertBase(dbConn);
         _permsImpl.insertPerms(entidad);

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
            dbConn.endTransaction(commit);
         dbConn.close();
      }

   }

   /**
    * Inserta la parte base de las tablas de grupo (header).
    *
    * @throws Exception Si se produce algún error en la inserción del grupo.
    */

   private void insertBase(DbConnection dbconn) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      LdapGroupsTable table = new LdapGroupsTable();

      if (_logger.isDebugEnabled())
         _logger.debug("insertBase");

      try
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(LdapGroupsTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getInsertBaseColumnNames");

         rowInfo.addRow(this);
         rowInfo.setClassName(LdapGroupImpl.class.getName());
         rowInfo.setValuesMethod("insertValues");
         rowsInfo.add(rowInfo);

         DynamicFns.insert(dbconn, tableInfo, rowsInfo);

      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
   }

   /**
    * Actualiza todos los objetos de los que es propietario el grupo.
    *
    * @throws Exception Si se produce algún error en la actualización.
    */

   private void updateAllOwnership(DbConnection dbconn) throws Exception
   {
      LdapUsersTable table = new LdapUsersTable();
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo;
      DynamicRow rowInfo;

      if (_logger.isDebugEnabled())
         _logger.debug("updateAllOwnership");

      tableInfo.setTableObject(table);
      tableInfo.setClassName(LdapUsersTable.class.getName());
      tableInfo.setTablesMethod("getOwnershipTableName");
      tableInfo.setColumnsMethod("getUpdateOwnershipColumnNames");

      // Propietario de directorio
      rowInfo = new DynamicRow();
      rowsInfo = new DynamicRows();

      rowInfo.addRow(this);
      rowInfo.setClassName(LdapGroupImpl.class.getName());
      rowInfo.setValuesMethod("updateOwnershipValues");
      rowsInfo.add(rowInfo);

      DynamicFns.update(dbconn, table.getUpdateOwnerQual(_id,
                        ISicresAdminDefsKeys.OBJECT_OWNER_TYPE_GROUP), tableInfo, rowsInfo);

   }


   /**
    * Elimina los permisos de un grupo sobre todos los objetos.
    *
    * @param perm Tipo de permiso a eliminar.
    * @param productId Identificador del producto.
    * @throws Exception Si se produce algún error en la actualización.
    */

   private void deleteObjPerm(int perm, int productId, String entidad) throws Exception
   {
	   DbConnection dbConn=new DbConnection();
	   try{
	   	dbConn.open(DBSessionManager.getSession());

	      LdapUsersTable table = new LdapUsersTable();
	      Permission permis;

	      // Comprobamos si para el productId se tiene el permiso o no.
	      permis = _permsImpl._perms.getProductPermission(productId);
	      if ((permis.getPermission() & perm) == 0)
	      {
	         DbDeleteFns.delete(dbConn, table.getObjPermsTableName(),
	                  table.getDeleteObjPermQual(_id, ISicresAdminDefsKeys.DESTINATION_GROUP, perm,
	                  productId));
	      }
	   }catch(Exception e){
			_logger.error(e);
			throw e;
		}finally{
			dbConn.close();
		}

   }

   /**
    * Elimina todos los permisos del grupo sobre los objetos invesDoc.
    *
    * @throws Exception Si se produce algún error en la eliminación.
    */

   private void deleteObjPerms(String entidad) throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("deleteObjPerms");

      deleteObjPerm(ISicresAdminUserDefsKeys.PERMISSION_PRINTING, ISicresAdminUserDefsKeys.PRODUCT_IDOC, entidad);
      deleteObjPerm(ISicresAdminUserDefsKeys.PERMISSION_DELETION, ISicresAdminUserDefsKeys.PRODUCT_IDOC, entidad);
      deleteObjPerm(ISicresAdminUserDefsKeys.PERMISSION_CREATION, ISicresAdminUserDefsKeys.PRODUCT_IDOC, entidad);
      deleteObjPerm(ISicresAdminUserDefsKeys.PERMISSION_UPDATE, ISicresAdminUserDefsKeys.PRODUCT_IDOC, entidad);
      deleteObjPerm(ISicresAdminUserDefsKeys.PERMISSION_QUERY, ISicresAdminUserDefsKeys.PRODUCT_IDOC, entidad);
   }

   /**
    * Actualiza un grupo de invesDoc.
    *
    * @throws Exception Si se produce algún error en la actualización del grupo.
    */

   private void update(String entidad) throws Exception
   {
      boolean commit = false;
      boolean inTrans = false;

      if (_logger.isDebugEnabled())
         _logger.debug("update");

      DbConnection dbConn=new DbConnection();
      try
      {
    	  dbConn.open(DBSessionManager.getSession());

         dbConn.beginTransaction();
         inTrans = true;

         _permsImpl.updatePerms(_id, ISicresAdminDefsKeys.DESTINATION_GROUP, entidad);
         deleteObjPerms(entidad);

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
            dbConn.endTransaction(commit);
         dbConn.close();
      }
   }

   /**
    * Lee la información básica del grupo invesDoc.
    *
    * @throws Exception Si se produce algún error en la lectura de la
    * información mencionada.
    */

   private void loadBaseGuid(String entidad) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      LdapGroupsTable table = new LdapGroupsTable();

      if (_logger.isDebugEnabled())
         _logger.debug("loadBaseGuid");

      DbConnection dbConn=new DbConnection();
      try
      {
    	  dbConn.open(DBSessionManager.getSession());
         tableInfo.setTableObject(table);
         tableInfo.setClassName(LdapGroupsTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getLoadBaseAllColumnNames");

         rowInfo.addRow(this);
         rowInfo.setClassName(LdapGroupImpl.class.getName());
         rowInfo.setValuesMethod("loadAllValues");
         rowsInfo.add(rowInfo);

         if (!DynamicFns.select(dbConn,
                        table.getLoadBaseQual(_guid), tableInfo, rowsInfo))
         {
            ISicresAdminBasicException.throwException(ISicresAdminGroupKeys.EC_GROUP_NOT_EXITS);
         }

      }
      catch (Exception e)
      {
    	  _logger.error(e);
    	  throw e;
      }
      finally{
    	  dbConn.close();
      }
  }

   private void loadBaseByDeptId(int deptId, String entidad) throws Exception {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		LdapGroupsTable table = new LdapGroupsTable();

		if (_logger.isDebugEnabled())
			_logger.debug("loadBaseGuid");

		DbConnection dbConn = new DbConnection();
		try {
			dbConn.open(DBSessionManager.getSession());
			tableInfo.setTableObject(table);
			tableInfo.setClassName(LdapGroupsTable.class.getName());
			tableInfo.setTablesMethod("getGroupDeptTableName");
			tableInfo.setColumnsMethod("getLoadBaseAllColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(LdapGroupImpl.class.getName());
			rowInfo.setValuesMethod("loadAllValues");
			rowsInfo.add(rowInfo);

			if (!DynamicFns.select(dbConn, table.getLoadBaseFromDeptId(deptId),
					tableInfo, rowsInfo)) {
				ISicresAdminBasicException
						.throwException(ISicresAdminGroupKeys.EC_GROUP_NOT_EXITS);
			}

		} catch (Exception e) {
			_logger.error(e);
			throw e;
		} finally {
			dbConn.close();
		}
	}

   /**
	 * Lee la información básica del grupo invesDoc.
	 *
	 * @throws Exception
	 *             Si se produce algún error en la lectura de la información
	 *             mencionada.
	 */

   private void loadBase(String entidad) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      LdapGroupsTable table = new LdapGroupsTable();

      if (_logger.isDebugEnabled())
         _logger.debug("loadBase");

      DbConnection dbConn=new DbConnection();
      try
      {
    	  dbConn.open(DBSessionManager.getSession());
         tableInfo.setTableObject(table);
         tableInfo.setClassName(LdapGroupsTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getLoadBaseColumnNames");

         rowInfo.addRow(this);
         rowInfo.setClassName(LdapGroupImpl.class.getName());
         rowInfo.setValuesMethod("loadValues");
         rowsInfo.add(rowInfo);

         if (!DynamicFns.select(dbConn,
                        table.getLoadBaseQual(_id), tableInfo, rowsInfo))
         {
            ISicresAdminBasicException.throwException(ISicresAdminGroupKeys.EC_GROUP_NOT_EXITS);
         }

      }
      catch (Exception e)
      {
    	  _logger.error(e);
    	  throw e;
      }
      finally{
    	  dbConn.close();
      }
  }

   private void init(int userConnected)
   {
      _guid = null;
      _fullName = null;
      _id = ISicresAdminDefsKeys.NULL_ID;
      _permsImpl = new GenericPermsImpl();
      _userConnected = userConnected;

   }

   private int _id;
   private String _guid;
   private String _fullName;
   private int _type;
   private GenericPermsImpl _permsImpl;
   private int _userConnected;

   private static final Logger _logger = Logger.getLogger(LdapGroupImpl.class);

}