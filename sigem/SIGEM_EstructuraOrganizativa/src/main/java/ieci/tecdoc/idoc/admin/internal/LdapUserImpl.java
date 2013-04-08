
package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.core.ldap.LdapBasicFns;
import ieci.tecdoc.core.ldap.LdapConnCfg;
import ieci.tecdoc.core.ldap.LdapConnection;
import ieci.tecdoc.core.ldap.LdapScope;
import ieci.tecdoc.core.ldap.LdapUasFns;
import ieci.tecdoc.core.xml.lite.XmlTextBuilder;
import ieci.tecdoc.idoc.admin.api.exception.AdminException;
import ieci.tecdoc.idoc.admin.api.exception.UserErrorCodes;
import ieci.tecdoc.idoc.admin.api.user.LdapUser;
import ieci.tecdoc.idoc.admin.api.user.Permission;
import ieci.tecdoc.idoc.admin.api.user.Permissions;
import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.idoc.admin.api.user.UserProfile;
import ieci.tecdoc.idoc.admin.api.user.UserProfiles;
import ieci.tecdoc.idoc.admin.database.DBSessionManager;
import ieci.tecdoc.idoc.admin.database.LdapUsersTable;
import ieci.tecdoc.sbo.uas.ldap.UasConfigUtil;
import ieci.tecdoc.sbo.uas.ldap.UasDaoLdapUserTbl;
import ieci.tecdoc.sbo.util.nextid.NextId;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DbUtil;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;

import org.apache.log4j.Logger;

/**
 * Representa un usuario Ldap de invesDoc
 */

public class LdapUserImpl implements LdapUser
{

   public LdapUserImpl(int userConnected)
   {
      init(userConnected);
   }

   public LdapUserImpl()
   {
      init(Defs.NULL_ID);
   }

   public void createUser(String guid, String fullName, String entidad) throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("createUser: Guid: " + guid + "fullName: " + fullName);

          _guid = guid;
         if (UasDaoLdapUserTbl.guidExists(_guid, entidad))
         {
            AdminException.throwException(UserErrorCodes.EC_USER_EXITS);
         }

         _fullName = fullName;

         _profilesImpl.setDefaultProfiles(_id);
         _permsImpl.setDefaultPerms(_id, Defs.DESTINATION_USER);
   }

   public void createUserWithProfiles(String guid, String fullName, String entidad, UserProfilesImpl _profiles) throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("createUser: Guid: " + guid + "fullName: " + fullName);

          _guid = guid;
         if (UasDaoLdapUserTbl.guidExists(_guid, entidad))
         {
            AdminException.throwException(UserErrorCodes.EC_USER_EXITS);
         }

         _fullName = fullName;
         _profilesImpl.setProfiles(_profiles);
         _permsImpl.setDefaultPerms(_id, Defs.DESTINATION_USER);
   }


   public void createFromLdapUser(String root, String attr, String value, String entidad)
               throws Exception

   {
	  LdapConnection ldapConn = new LdapConnection();
      LdapConnCfg ldapCfg = null;
      String dn, fullName;
      int index;

      if (_logger.isDebugEnabled())
         _logger.debug("createFromLdapUser: Root" + root + " Attribute: " +
                       attr + " Value: " + value);

		try {

         ldapCfg = UasConfigUtil.createLdapConnConfig(entidad);
		 ldapConn.open(ldapCfg);

         dn = LdapUasFns.findUserDn(ldapConn, root, LdapScope.SUBTREE, attr,
                                    value, entidad);
         _guid = LdapUasFns.findUserGuidByDn(ldapConn, dn);
         if (UasDaoLdapUserTbl.guidExists(_guid, entidad))
         {
            AdminException.throwException(UserErrorCodes.EC_USER_EXITS);
         }

         fullName = LdapBasicFns.getEntryRdn(dn);
         index = fullName.indexOf("=");
         fullName = fullName.substring(index+1, fullName.length());
         _fullName = fullName;

         _profilesImpl.setDefaultProfiles(_id);
         _permsImpl.setDefaultPerms(_id, Defs.DESTINATION_USER);

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

   public void loadBasic(int id, String entidad) throws Exception
   {
      load(id, false, entidad);
   }

   public void loadFromGuid(String guid, String entidad) throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("loadFromGuid: guid = " + guid);

         _guid = guid;

         try {
			 loadBaseGuid(entidad);
			 _profilesImpl.loadProfiles(_id, entidad);
	         _permsImpl.loadPerms(_id, Defs.DESTINATION_USER, entidad);
		} catch (Exception e) {
			_logger.error(e);
	         throw e;
		}
   }

   public void loadFromFullName(String fullName, String entidad) throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("loadFromFullName: fullName = " + fullName);

         _fullName = fullName;

         try {
			 loadBaseFullName(entidad);
			 _profilesImpl.loadProfiles(_id, entidad);
	         _permsImpl.loadPerms(_id, Defs.DESTINATION_USER, entidad);
		} catch (Exception e) {
			_logger.error(e);
	         throw e;
		}
   }

   public void load(int id, boolean full, String entidad) throws Exception {
		if (_logger.isDebugEnabled())
			_logger.debug("load: Id = " + Integer.toString(id));

		try {
			_id = id;

			loadBase(entidad);
			if (full) {
				try {
					_profilesImpl.loadProfiles(_id, entidad);
				} catch (Exception e) {
					_logger.error(e);
				}

				try {
					_permsImpl.loadPerms(_id, Defs.DESTINATION_USER, entidad);
				} catch (Exception e) {
					_logger.error(e);
				}
				_userDataImpl.load(_id, entidad);
			}
		} catch (Exception e) {
			_logger.error(e);
			throw e;
		}
	}

   /**
    *
    * {@inheritDoc}
    * @see ieci.tecdoc.idoc.admin.api.user.LdapUser#loadFromIdCert(java.lang.String, java.lang.String)
    */
	public void loadFromIdCert(String idCert, String entidad) throws Exception{
		try {
			if (_logger.isDebugEnabled()) {
				_logger.debug("loadFromIdCert: IdCert = " + idCert);
			}
			_userDataImpl.loadFromIdCert(idCert, entidad);
			_id = _userDataImpl.getId();
			load(_id, entidad);
		} catch (Exception e) {
			_logger.error(e);
			throw e;
		}
	}

   public void store(String entidad) throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("store");

		try
		{

         if (_id == Defs.NULL_ID)
         {
            _id = NextId.generateNextId(LdapUsersTable.TN_NEXTID,
                                        Defs.NEXT_ID_USER_TABLE_TYPE_USER, entidad);
            _profilesImpl._profiles.setId(_id);
            _permsImpl._perms.setId(_id);
            insert(entidad);
         }
         else
         {
            update(entidad);
         }

		}
		catch(Exception e)
		{
         _logger.error(e);
         throw e;
		}
   }

   public void delete(String entidad) throws Exception
   {
      boolean commit = false;
      boolean inTrans = false;
      LdapUsersTable table = new LdapUsersTable();

      if (_logger.isDebugEnabled())
         _logger.debug("delete");

      // Con esto también se evita que se pueda eliminar SYSSUPERUSER
      if (_id == _userConnected)
      {
         AdminException.throwException(UserErrorCodes.EC_USER_CONNECTED);
      }

      DbConnection dbConn=new DbConnection();
		try
		{
		 dbConn.open(DBSessionManager.getSession(entidad));

         dbConn.beginTransaction();
         inTrans = true;

         DbDeleteFns.delete(dbConn, table.getBaseTableName(),
                            table.getDeleteBaseQual(_id));
         _profilesImpl.deleteProfiles(_id, entidad);
         _permsImpl.deletePerms(_id, Defs.DESTINATION_USER, entidad);
         _userDataImpl.delete(entidad);
         DbDeleteFns.delete(dbConn, table.getObjPermsTableName(),
                            table.getDeleteObjPermsQual(_id,
                            Defs.DESTINATION_USER));

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

   public int getId()
   {
      return _id;
   }

   public UserProfiles getProfiles()
   {
      return _profilesImpl._profiles;
   }

   public Permissions getPermissions()
   {
      return _permsImpl._perms;
   }

   public GenericProfilesImpl getProfilesImpl()
   {
      return _profilesImpl;
   }

   public GenericPermsImpl getPermissionsImpl()
   {
      return _permsImpl;
   }

   public UserDataImpl getUserDataImpl() {
		return _userDataImpl;
	}

	public void setUserDataImpl(UserDataImpl _userDataImpl) {
		this._userDataImpl = _userDataImpl;
	}

   /**
    * Obtiene la información del usuario Ldap en formato XML.
    *
    * @param header Indica si hay que incluir la cabecera xml o no.
    * @return La información mencionada.
    */

   public String toXML(boolean header)
   {
      XmlTextBuilder bdr;
      String tagName = "User";

      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      bdr.addSimpleElement("Id", Integer.toString(_id));
      bdr.addSimpleElement("Guid", _guid);
      bdr.addSimpleElement("FullName", _fullName);

      bdr.addFragment(_profilesImpl._profiles.toXML(false));
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

      return new Integer(index);
   }

   /**
    * Recupera de la base de datos informacón asociada al usuario.
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

      _id = statement.getShortInteger(index++);
      _guid = statement.getShortText(index++);
      _fullName = statement.getShortText(index++);

      return new Integer(index);
   }

   /**
    * Recupera de la base de datos información asociada al usuario.
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
    * Recupera de la base de datos información asociada al usuario.
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

      statement.setLongInteger(index++, Defs.SYSSUPERUSER_ID);
      statement.setLongInteger(index++, _userConnected);
      statement.setDateTime(index++, DbUtil.getCurrentDateTime());

      return new Integer(index);
   }

   /**
    * Actualiza los objetos de los que es propietario el usuario.
    *
    * @throws Exception Si se produce algún error en la actualizaciï¿½n.
    */

   private void updateOwnership(DbConnection dbConn) throws Exception
   {
      LdapUsersTable table = new LdapUsersTable();
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo;
      DynamicRow rowInfo;
      UserProfile profile;
      int prof;

      if (_logger.isDebugEnabled())
         _logger.debug("updateOwnership");

      // Si el usuario era administrador y ha bajado de permisos, hay que
      // cambiar quién es el propietario
      if (_wasAdmin)
      {
         profile = _profilesImpl._profiles.getProductProfile(UserDefs.PRODUCT_IDOC);
         prof = profile.getProfile();
         if ((prof == UserDefs.PROFILE_STANDARD) ||
             (prof == UserDefs.PROFILE_NONE))
         {
            tableInfo.setTableObject(table);
            tableInfo.setClassName(LdapUsersTable.class.getName());
            tableInfo.setTablesMethod("getOwnershipTableName");
            tableInfo.setColumnsMethod("getUpdateOwnershipColumnNames");

            // Propietario de directorio y archivador
            rowInfo = new DynamicRow();
            rowsInfo = new DynamicRows();

            rowInfo.addRow(this);
            rowInfo.setClassName(LdapUserImpl.class.getName());
            rowInfo.setValuesMethod("updateOwnershipValues");
            rowsInfo.add(rowInfo);

            DynamicFns.update(dbConn, table.getUpdateOwnerDirArchQual(_id,
                              Defs.OBJECT_OWNER_TYPE_USER, UserDefs.PRODUCT_IDOC,
                              Defs.OBJECT_OWNER_TYPE_DIRECTORY,
                              Defs.OBJECT_OWNER_TYPE_ARCHIVE,
                              Defs.ARCH_TYPE_STANDARD), tableInfo, rowsInfo);

         }
      }
   }

   /**
    * Actualiza todos los objetos de los que es propietario el usuario.
    *
    * @throws Exception Si se produce algún error en la actualización
    */

   private void updateAllOwnership(DbConnection dbConn) throws Exception
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
      rowInfo.setClassName(LdapUserImpl.class.getName());
      rowInfo.setValuesMethod("updateOwnershipValues");
      rowsInfo.add(rowInfo);

      DynamicFns.update(dbConn, table.getUpdateOwnerQual(_id,
                        Defs.OBJECT_OWNER_TYPE_USER), tableInfo, rowsInfo);
   }

   /**
    * Inserta un nuevo usuario en invesDoc.
    *
    * @throws Exception Si se produce algún error en la inserciï¿½n del usuario.
    */

   private void insert(String entidad) throws Exception
   {
      boolean commit = false;
      boolean inTrans = false;

      if (_logger.isDebugEnabled())
         _logger.debug("insert");

      DbConnection dbConn=new DbConnection();
      try
      {
    	dbConn.open(DBSessionManager.getSession(entidad));
         dbConn.beginTransaction();
         inTrans = true;

         insertBase(dbConn);
         _profilesImpl.insertProfiles(entidad);
         _permsImpl.insertPerms(entidad);

         _userDataImpl.setId(_id);
         _userDataImpl.insert(entidad);

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

		try{
			if(dbConn.existConnection()){
				dbConn.close();
			}
		}catch(Exception e){

		}
      }
   }

   /**
    * Inserta la parte base de las tablas de usuario (header).
    *
    * @throws Exception Si se produce algún error en la inserciï¿½n del usuario.
    */

   private void insertBase(DbConnection dbConn) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      LdapUsersTable table = new LdapUsersTable();

      if (_logger.isDebugEnabled())
         _logger.debug("insertBase");

      try
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(LdapUsersTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getInsertBaseColumnNames");

         rowInfo.addRow(this);
         rowInfo.setClassName(LdapUserImpl.class.getName());
         rowInfo.setValuesMethod("insertValues");
         rowsInfo.add(rowInfo);

         DynamicFns.insert(dbConn, tableInfo, rowsInfo);

      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
   }

   /**
    * Elimina los permisos de un usuario sobre todos los objetos.
    *
    * @param perm Tipo de permiso a eliminar.
    * @param productId Identificador del producto.
    * @throws Exception Si se produce algún error en la actualización.
    */

   private void deleteObjPerm(int perm, int productId, String entidad) throws Exception
   {
      LdapUsersTable table = new LdapUsersTable();
      Permission permis;

      DbConnection dbConn=new DbConnection();
      try{
      	dbConn.open(DBSessionManager.getSession(entidad));
	      // Comprobamos si para el productId se tiene el permiso o no.
	      permis = _permsImpl._perms.getProductPermission(productId);
	      if ((permis.getPermission() & perm) == 0)
	      {
	         DbDeleteFns.delete(dbConn, table.getObjPermsTableName(),
	                  table.getDeleteObjPermQual(_id, Defs.DESTINATION_USER, perm,
	                  productId));
	      }
      }catch(Exception e){
    		_logger.error(e);
    		throw e;
      }
      finally{
  		try{
			if(dbConn.existConnection()){
				dbConn.close();
			}
		}catch(Exception e){

		}
      }
   }

   /**
    * Elimina todos los permisos del usuario sobre los objetos invesDoc.
    *
    * @throws Exception Si se produce algún error en la eliminación.
    */

   private void deleteObjPerms(String entidad) throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("deleteObjPerms");

      deleteObjPerm(UserDefs.PERMISSION_PRINTING, UserDefs.PRODUCT_IDOC, entidad);
      deleteObjPerm(UserDefs.PERMISSION_DELETION, UserDefs.PRODUCT_IDOC, entidad);
      deleteObjPerm(UserDefs.PERMISSION_CREATION, UserDefs.PRODUCT_IDOC, entidad);
      deleteObjPerm(UserDefs.PERMISSION_UPDATE, UserDefs.PRODUCT_IDOC, entidad);
      deleteObjPerm(UserDefs.PERMISSION_QUERY, UserDefs.PRODUCT_IDOC, entidad);
   }

   /**
    * Actualiza un usuario de invesDoc.
    *
    * @throws Exception Si se produce algún error en la actualización del usuario.
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
    	 dbConn.open(DBSessionManager.getSession(entidad));
         dbConn.beginTransaction();
         inTrans = true;

         _profilesImpl.updateProfiles(_id, entidad);
         _permsImpl.updatePerms(_id, Defs.DESTINATION_USER, entidad);
         _userDataImpl.update(entidad);

         updateOwnership(dbConn);
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

		try{
			if(dbConn.existConnection()){
				dbConn.close();
			}
		}catch(Exception e){

		}
      }
   }

   /**
    * Lee la información básica del usuario invesDoc a partir del Guid.
    *
    * @throws Exception Si se produce algún error en la lectura de la
    * información mencionada.
    */
   private void loadBaseGuid(String entidad) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      LdapUsersTable table = new LdapUsersTable();

      if (_logger.isDebugEnabled())
         _logger.debug("loadBaseGuid");

      DbConnection dbConn=new DbConnection();
      try
      {
    	 dbConn.open(DBSessionManager.getSession(entidad));
         tableInfo.setTableObject(table);
         tableInfo.setClassName(LdapUsersTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getLoadBaseAllColumnNames");

         rowInfo.addRow(this);
         rowInfo.setClassName(LdapUserImpl.class.getName());
         rowInfo.setValuesMethod("loadAllValues");
         rowsInfo.add(rowInfo);

         if (!DynamicFns.select(dbConn,
                        table.getLoadBaseQual(_guid), tableInfo, rowsInfo))
         {
            AdminException.throwException(UserErrorCodes.EC_USER_NOT_EXITS);
         }

      }
      catch (Exception e)
      {
    	  _logger.error(e);
    	  throw e;
      }
      finally{
		try{
			if(dbConn.existConnection()){
				dbConn.close();
			}
		}catch(Exception e){

		}
      }
  }

   /**
    * Lee la información básica del usuario invesDoc a partir del FullName.
    *
    * @throws Exception Si se produce algún error en la lectura de la
    * información mencionada.
    */
   private void loadBaseFullName(String entidad) throws Exception {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      LdapUsersTable table = new LdapUsersTable();

      if (_logger.isDebugEnabled()){
         _logger.debug("loadBaseFullName");
      }

      DbConnection dbConn=new DbConnection();
      try{
    	 dbConn.open(DBSessionManager.getSession(entidad));
         tableInfo.setTableObject(table);
         tableInfo.setClassName(LdapUsersTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getLoadBaseAllColumnNames");

         rowInfo.addRow(this);
         rowInfo.setClassName(LdapUserImpl.class.getName());
         rowInfo.setValuesMethod("loadAllValues");
         rowsInfo.add(rowInfo);

         if (!DynamicFns.select(dbConn, table.getLoadBaseByFullName(_fullName), tableInfo, rowsInfo)){
            AdminException.throwException(UserErrorCodes.EC_USER_NOT_EXITS);
         }
      } catch (Exception e){
    	  _logger.error(e);
    	  throw e;
      }finally{
		try{
			if(dbConn.existConnection()){
				dbConn.close();
			}
		}catch(Exception e){
		}
      }
  }

   /**
    * Lee la información básica del usuario invesDoc.
    *
    * @throws Exception Si se produce algún error en la lectura de la
    * información mencionada.
    */

   private void loadBase(String entidad) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      LdapUsersTable table = new LdapUsersTable();

      if (_logger.isDebugEnabled())
         _logger.debug("loadBase");

      DbConnection dbConn=new DbConnection();
      try
      {
    	 dbConn.open(DBSessionManager.getSession(entidad));
         tableInfo.setTableObject(table);
         tableInfo.setClassName(LdapUsersTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getLoadBaseColumnNames");

         rowInfo.addRow(this);
         rowInfo.setClassName(LdapUserImpl.class.getName());
         rowInfo.setValuesMethod("loadValues");
         rowsInfo.add(rowInfo);

         if (!DynamicFns.select(dbConn,
                        table.getLoadBaseQual(_id), tableInfo, rowsInfo))
         {
            AdminException.throwException(UserErrorCodes.EC_USER_NOT_EXITS);
         }

      }
      catch (Exception e)
      {
    	  _logger.error(e);
    	  throw e;
      }
      finally{
		try{
			if(dbConn.existConnection()){
				dbConn.close();
			}
		}catch(Exception e){

		}
      }
  }

   private void init(int userConnected)
   {
      _guid = null;
      _fullName = null;
      _id = Defs.NULL_ID;
      _permsImpl = new GenericPermsImpl();
      _profilesImpl = new GenericProfilesImpl();
      _wasAdmin = false;
      _userConnected = userConnected;
      _userDataImpl = new UserDataImpl();
   }

   private UserDataImpl _userDataImpl;
   private int _id;
   private String _guid;
   private String _fullName;
   private boolean _wasAdmin;
   private int _userConnected;
   private GenericProfilesImpl _profilesImpl;
   private GenericPermsImpl _permsImpl;

   private static final Logger _logger = Logger.getLogger(LdapUserImpl.class);

   public void resetProfiles(){
	   _profilesImpl.resetProfiles();
   }

}