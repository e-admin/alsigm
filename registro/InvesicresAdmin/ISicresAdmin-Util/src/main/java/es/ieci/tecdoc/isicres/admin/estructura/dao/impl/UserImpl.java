package es.ieci.tecdoc.isicres.admin.estructura.dao.impl;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.collections.IeciTdLongIntegerArrayList;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDeleteFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbInputStatement;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbOutputStatement;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbSelectFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUtil;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicRow;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicRows;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicTable;
import es.ieci.tecdoc.isicres.admin.core.datetime.DatePattern;
import es.ieci.tecdoc.isicres.admin.core.datetime.DateTimeUtil;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.xml.lite.XmlTextBuilder;
import es.ieci.tecdoc.isicres.admin.database.DepartmentsTable;
import es.ieci.tecdoc.isicres.admin.database.GroupsTable;
import es.ieci.tecdoc.isicres.admin.database.UsersTable;
import es.ieci.tecdoc.isicres.admin.estructura.dao.GenericPerms;
import es.ieci.tecdoc.isicres.admin.estructura.dao.GenericProfiles;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Permission;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Permissions;
import es.ieci.tecdoc.isicres.admin.estructura.dao.User;
import es.ieci.tecdoc.isicres.admin.estructura.dao.UserProfile;
import es.ieci.tecdoc.isicres.admin.estructura.dao.UserProfiles;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminDefsKeys;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminUserDefsKeys;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminUserKeys;
import es.ieci.tecdoc.isicres.admin.exception.ISicresAdminBasicException;
import es.ieci.tecdoc.isicres.admin.sbo.uas.std.UasMisc;
import es.ieci.tecdoc.isicres.admin.sbo.util.nextid.NextId;

/**
 * Representa un usuario de invesDoc.
 */
public class UserImpl implements User
{
	public UserImpl(int userConnected, int deptId)
   {
      init(userConnected, deptId);
   }

   public UserImpl()
   {
      init(ISicresAdminDefsKeys.NULL_ID, ISicresAdminDefsKeys.NULL_ID);
   }

   public void load(int id, String entidad) throws Exception
   {
      load(id, true, entidad);
   }

   public void load(String name, String entidad) throws Exception
   {
      loadByName(name, true, entidad);
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
         	_profilesImpl.loadProfiles(_id, entidad);
            _permsImpl.loadPerms(_id, ISicresAdminDefsKeys.DESTINATION_USER, entidad);
         }
		}
		catch(Exception e)
		{
         _logger.error(e);
         throw e;
		}
   }

   public void loadByName(String name, boolean full, String entidad) throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("loadByName: Name = " + name);

      DbConnection dbConn=new DbConnection();
		try
		{
         _name = name;

         dbConn.open(DBSessionManager.getSession());

         loadBaseName(entidad);
         if (full)
         {
         	_profilesImpl.loadProfiles(_id, entidad);
            _permsImpl.loadPerms(_id, ISicresAdminDefsKeys.DESTINATION_USER, entidad);
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

   public void initSysSuperUser(String entidad) throws Exception
   {
      UserProfilesImpl profiles;
      UserProfileImpl  profile;

      if (_logger.isDebugEnabled())
         _logger.debug("initSysSuperUser");

      _userConnected = 0;
      _id = 0;
      _name = "SYSSUPERUSER";
      _password = "SYSPASSWORD";
      _pwdmbc ="N";
      _pwdvpcheck = "N";
      _deptId = 0;
      _state = 0;

      try
      {
			checkUserExists(entidad);
			profiles = (UserProfilesImpl)_profilesImpl.getProfiles();
			profile = new UserProfileImpl(0, ISicresAdminUserDefsKeys.PRODUCT_SYSTEM,
               								ISicresAdminUserDefsKeys.PROFILE_SUPERUSER);
			profiles.add((UserProfile)profile);

			insertBase(entidad);
			_profilesImpl.insertProfiles(entidad);
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
   		checkUserExists(entidad);
   		checkDescription();
      	checkPassword(entidad);

         if (_id == ISicresAdminDefsKeys.NULL_ID){
            _id = NextId.generateNextId(UsersTable.TN_NEXTID,
                                        ISicresAdminDefsKeys.NEXT_ID_USER_TABLE_TYPE_USER, entidad);
            _profilesImpl.setDefaultProfiles(_id);
            _permsImpl.setDefaultPerms(_id, ISicresAdminDefsKeys.DESTINATION_USER);
            insert(entidad);
         }
         else{
         	_isChange = changePassword(entidad);
            update(entidad);
         }

		}
		catch(Exception e){
         _logger.error(e);
         throw e;
		}

   }

   public void delete(String entidad) throws Exception
   {
      boolean commit = false;
      boolean inTrans = false;
      UsersTable table = new UsersTable();

      if (_logger.isDebugEnabled())
         _logger.debug("delete");

      // Con esto también se evita que se pueda eliminar SYSSUPERUSER
      if (_id == _userConnected)
      {
         ISicresAdminBasicException.throwException(ISicresAdminUserKeys.EC_USER_CONNECTED);
      }

      DbConnection dbConn=new DbConnection();
		try
		{
			dbConn.open(DBSessionManager.getSession());

         dbConn.beginTransaction();
         inTrans = true;

         DbDeleteFns.delete(dbConn, table.getBaseTableName(),
                            table.getDeleteBaseQual(_id));
         _profilesImpl.deleteProfiles(_id, entidad);
         _permsImpl.deletePerms(_id, ISicresAdminDefsKeys.DESTINATION_USER, entidad);

         DbDeleteFns.delete(dbConn, table.getObjPermsTableName(),
                            table.getDeleteObjPermsQual(_id,
                            ISicresAdminDefsKeys.DESTINATION_USER));
         deleteGroupUserRel(entidad);
         updateDeptsMgr(entidad);
         updateGroupsMgr(entidad);
         updateAllOwnership(entidad);

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

   public ArrayList getDeptsIdMgr(int userId, String entidad) throws Exception
   {
      ArrayList deptsId;

      if (_logger.isDebugEnabled())
         _logger.debug("getDeptsIdMgr");

      DbConnection dbConn=new DbConnection();
      try
      {
    		dbConn.open(DBSessionManager.getSession());
         deptsId = new ArrayList();

         dbConn.open(DBSessionManager.getSession());

         loadDeptsMgr(userId,deptsId, entidad);

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

      return (deptsId);
   }

   public ArrayList getGroupsIdMgr(int userId, String entidad) throws Exception
   {
      ArrayList grpsId;

      if (_logger.isDebugEnabled())
         _logger.debug("getGroupsIdMgr");

      DbConnection dbConn=new DbConnection();
      try
      {
         grpsId = new ArrayList();

         dbConn.open(DBSessionManager.getSession());

         loadGroupsMgr(userId,grpsId, entidad);

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

      return (grpsId);
   }

   /**
    * Obtiene el identificador del usuario.
    *
    * @return El identificador mencionado.
    */
   public int getId()
	{
   	return _id;
   }

   /**
    * Obtiene el nombre del usuario.
    *
    * @return El nombre mencionado.
    */
   public String getName()
	{
   	return _name;
   }

   /**
    * Establece el nombre del usuario.
    *
    * @param name El nombre del usuario.
    */
   public void setName(String name)
	{
   	_name = name;
   }

   /**
    * Establece la password del usuario.
    *
    * @param password La password del usuario.
    */
   public void setPassword(String password)
	{
   	_password = password;
   }

   /**
    * Obtiene la obligatoriedad sobre la inicialización de la contraseña.
    *
    * @return <code>true</code> si es obligatoria la inicialización de la contraseña.
    * 		  <code>false</code> si no es obligatoria la inicialización de la contraseña.
    */
   public boolean getPwdmbc()
	{
   	if (_pwdmbc.equals("Y"))
   		return true;
   	else
   		return false;
   }


   /**
    * Establece la obligatoriedad de la inicialización de la contraseña.
    *
    * @param pwdmbc <code>true</code> si es obligatoria la inicialización de la contraseña.
    * 		  		  <code>false</code> si no es obligatoria la inicialización de la contraseña.
    */
   public void setPwdmbc(boolean pwdmbc)
	{
   	if(pwdmbc)
   		_pwdmbc = "Y";
   	else
   		_pwdmbc = "N";
   }

   /**
    * Obtiene la información de comprobar la caducidad de la contraseña.
    *
    * @return <code>true</code> si se comprueba la caducidad de la contraseña.
    * 		  <code>false</code> si no se comprueba.
    */
   public boolean getPwdvpcheck()
	{
   	if (_pwdvpcheck.equals("Y"))
   		return true;
   	else
   		return false;
   }

   /**
    * Establece la información de comprobar la caducidad de la contraseña.
    *
    * @param pwdvpcheck <code>true</code> si se comprueba la caducidad de la contraseña.
    * 		  				<code>false</code> si no se comprueba.
    */
   public void setPwdvpcheck(boolean pwdvpcheck)
	{
   	if(pwdvpcheck)
   		_pwdvpcheck = "Y";
   	else
   		_pwdvpcheck = "N";
   }

   /**
    * Obtiene la descripción del usuario.
    *
    * @return El dato mencionado.
    */
   public String getDescription()
	{
   	return _description;
   }

   /**
    * Establece la descripción del usuario.
    *
    * @param description La descripción del usuario.
    */
   public void setDescription(String description)
	{
   	_description = description;
   }

   /**
    * Obtiene el identificador del departamento al que pertenece el usuario.
    *
    * @return El identificador mencionado.
    */
   public int getDeptId()
	{
   	return _deptId;
   }

   /**
    * Establece el identificador del departamento del usuario.
    *
    * @param deptId El identificador del departamento.
    */
   public void setDeptId(int deptId)
	{
   	_deptId = deptId;
   }

   /**
    * Obtiene el estado del usuario.
    *
    * @return El dato mencionado.
    */
   public int getState()
	{
   	return _state;
   }

   /**
    * Establece el estado del usuario.
    *
    * @param state El estado del usuario.
    */
   public void setState(int state)
	{
   	_state = state;
   }

   /**
    * Obtiene la lista de perfiles del usuario.
    *
    * @return La lista mencionada.
    */
   public UserProfiles getProfiles()
   {
      return _profilesImpl._profiles;
   }

   /**
    * Obtiene la lista de permisos del usuario.
    *
    * @return La lista mencionada.
    */
   public Permissions getPermissions()
   {
      return _permsImpl._perms;
   }

   /**
	 * Obtiene el identificador del usuario que ha creado el usuario.
	 *
	 * @return El identificador mencionado.
	 */
	public int getCreatorId()
   {
      return _creatorId;
   }

	/**
    * Obtiene la fecha de creación del usuario.
    *
    * @return La fecha mencionada.
    */
   public Date getCreationDate()
   {
      return _creationDate;
   }
   public int getUserConnected() {
	return _userConnected;
   }
   public String getPassword() {
	return _password;
   }
   public String getOldPassword() {
	return _oldPassword;
   }
   public boolean getIsChange() {
	return _isChange;
   }
   public String get_pwdmbc() {
	return _pwdmbc;
   }
   public String get_pwdvpcheck() {
	   return _pwdvpcheck;
   }

   public boolean get_wasAdmin(){
	   return _wasAdmin;
   }

   public int get_pwdminlen(){
	   return _pwdminlen;
   }

   public long get_pwdLastUpdTs(){
	   return _pwdLastUpdTs;
   }

   public void set_pwdminlen(int _pwdminlen){
	   this._pwdminlen=_pwdminlen;
   }

   public void set_pwdLastUpdTs(long _pwdLastUpdTs){
	   this._pwdLastUpdTs=_pwdLastUpdTs;
   }

   public GenericPerms get_permsImpl() {
	   return _permsImpl;
   }

   public GenericProfiles get_profilesImpl() {
	   return _profilesImpl;
   }

   /**
    * Obtiene el identificador del usuario que ha actualizado el usuario.
    *
    * @return El identificador mencionado.
    */
   public int getUpdaterId()
   {
      return _updaterId;
   }

   /**
    * Obtiene la fecha de actualización del usuario.
    *
    * @return La fecha mencionada.
    */
   public Date getUpdateDate()
   {
      return _updateDate;
   }

   /**
    * Obtiene la fecha de la última modificación de la contraseña.
    * Horas transcurridas desde 01-01-1971.
    *
    * @return La fecha mencionada.
    */
   public double getPwdLastUpdTs()
	{
   	return _pwdLastUpdTs;
   }

   /**
    * Obtiene la información del usuario en formato XML.
    *
    * @param header Indica si hay que incluir la cabecera xml o no.
    * @return La información mencionada.
    */
   public String toXML(boolean header)
   {
   	String text;
      XmlTextBuilder bdr;
      String tagName = "User";

      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      bdr.addSimpleElement("Id", Integer.toString(_id));
      bdr.addSimpleElement("Name", _name);
      bdr.addSimpleElement("Pwdmbc", _pwdmbc);
      bdr.addSimpleElement("Pwdvpcheck", _pwdvpcheck);
      bdr.addSimpleElement("Description", _description);
      bdr.addSimpleElement("DeptId", Integer.toString(_deptId));
      bdr.addSimpleElement("State", Integer.toString(_state));
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
      bdr.addSimpleElement("PwdLastUpdTs", Double.toString(_pwdLastUpdTs));

      bdr.addFragment(_profilesImpl._profiles.toXML(false));
      bdr.addFragment(_permsImpl._perms.toXML(false));

      bdr.addClosingTag(tagName);

      return bdr.getText();
   }

   /**
    * Obtiene la información del usuario en formato XML.
    */
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
    * Recupera de la base de datos información asociada al usuario.
    *
    * @param statement
    * @param idx
    * @return
    * @throws java.lang.Exception
    */

   public Integer loadUserSysValue(DbOutputStatement statement, Integer idx)
                  throws Exception
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("loadUserSysValue");

      _pwdminlen = statement.getLongInteger(index++);

      return new Integer(index);
   }

   /**
    * Recupera la contraseña de la base de datos asociada al usuario.
    *
    * @param statement
    * @param idx
    * @return
    * @throws java.lang.Exception
    */
   public Integer loadPasswordValue(DbOutputStatement statement, Integer idx)
   throws Exception
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("loadPasswordValue");

      _oldPassword = UasMisc.decryptPassword(statement.getShortText(index++), _id);

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
      statement.setShortText(index++,
      		UasMisc.encryptPassword(_password, _id));
      statement.setLongInteger(index++, _deptId);
      statement.setLongInteger(index++, ISicresAdminDefsKeys.USER_FLAG_NULL);
      statement.setLongInteger(index++, _state);
      statement.setLongInteger(index++, ISicresAdminDefsKeys.USER_NUMBADCNTS);
      statement.setShortText(index++, _description);
      statement.setLongInteger(index++, _creatorId);
		_creationDate = DbUtil.getCurrentDateTime();
		statement.setDateTime(index++, _creationDate);
		_pwdLastUpdTs = Math.round(DateTimeUtil.getCurrentTimeHours());
		statement.setLongDecimal(index++,_pwdLastUpdTs);
		statement.setShortText(index++, _pwdmbc);
		statement.setShortText(index++, _pwdvpcheck);

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

      _id = statement.getLongInteger(index++);
      _name = statement.getShortText(index++);
      _password = UasMisc.decryptPassword(statement.getShortText(index++), _id);
      _deptId = statement.getLongInteger(index++);
      _state = statement.getLongInteger(index++);
      _description = statement.getShortText(index++);
      _creatorId = statement.getLongInteger(index++);
      _creationDate = statement.getDateTime(index++);
      _updaterId = statement.getLongInteger(index++);
      _updateDate = statement.getDateTime(index++);
      _pwdLastUpdTs = Math.round(statement.getLongDecimal(index++));
      _pwdmbc = statement.getShortText(index++);
      _pwdvpcheck = statement.getShortText(index++);

      return new Integer(index);
   }

   /**
    * Actualiza en base de datos información almacenada por esta clase.
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
      statement.setShortText(index++,
      		UasMisc.encryptPassword(_password, _id));
      statement.setLongInteger(index++, _deptId);
      statement.setLongInteger(index++, _state);
      statement.setShortText(index++, _description);
      _updaterId = _userConnected;
      statement.setLongInteger(index++, _updaterId);
		_updateDate = DbUtil.getCurrentDateTime();
		statement.setDateTime(index++, _updateDate);

		if (_isChange)
		{
			_pwdLastUpdTs =
				Math.round(DateTimeUtil.getCurrentTimeHours());
		}

		statement.setLongDecimal(index++,_pwdLastUpdTs);
		statement.setShortText(index++, _pwdmbc);
		statement.setShortText(index++, _pwdvpcheck);

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

      statement.setLongInteger(index++, ISicresAdminDefsKeys.SYSSUPERUSER_ID);
      statement.setLongInteger(index++, _userConnected);
      statement.setDateTime(index++, DbUtil.getCurrentDateTime());

      return new Integer(index);
   }

   /**
    * Actualiza en base de datos la información relativa a departamentos propiedad
    * del usuario.
    *
    * @param statement
    * @param idx
    * @return
    * @throws java.lang.Exception
    */

   public Integer updateDeptsMgrValues(DbInputStatement statement, Integer idx)
                  throws Exception
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("updateDeptsMgrValues");

      statement.setLongInteger(index++, ISicresAdminDefsKeys.SYSSUPERUSER_ID);
      statement.setLongInteger(index++, _userConnected);
      statement.setDateTime(index++, DbUtil.getCurrentDateTime());

      return new Integer(index);
   }

   /**
    * Actualiza en base de datos la información relativa a grupos propiedad
    * del usuario.
    *
    * @param statement
    * @param idx
    * @return
    * @throws java.lang.Exception
    */
   public Integer updateGroupsMgrValues(DbInputStatement statement, Integer idx)
   					throws Exception
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("updateGroupsMgrValues");

      statement.setLongInteger(index++, ISicresAdminDefsKeys.SYSSUPERUSER_ID);
      statement.setLongInteger(index++, _userConnected);
      statement.setDateTime(index++, DbUtil.getCurrentDateTime());

      return new Integer(index);
   }

   /**
    * Recupera de la base de datos el identificador y el nombre del usuario.
    *
    * @param statement
    * @param idx
    * @return
    * @throws java.lang.Exception
    */

   public Integer loadIdNameValues(DbOutputStatement statement, Integer idx)
                  throws Exception
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("loadIdNameValue");

      _id = statement.getLongInteger(index++);
      _name = statement.getShortText(index++);

      return new Integer(index);
   }

   /**
    * Recupera de la base de datos el identificador del usuario.
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
         _logger.debug("loadIdValue");

      _id = statement.getLongInteger(index++);

      return new Integer(index);
   }


   private void deleteGroupUserRel(String entidad) throws Exception
   {
      UsersTable table = new UsersTable();

      DbConnection dbConn=new DbConnection();
      try{
      	dbConn.open(DBSessionManager.getSession());
	      DbDeleteFns.delete(dbConn, table.getGURelTableName(),
	            table.getLoadGURelUserIdQual(_id));
      }catch(Exception e){
    		_logger.error(e);
    		throw e;
    	}finally{
    		dbConn.close();
    	}

   }

   private void loadDeptsMgr(int usrId, ArrayList deptsId, String entidad) throws Exception
   {
      IeciTdLongIntegerArrayList ids;
      String qual;
      String colName;
      DepartmentsTable table = new DepartmentsTable();

      if (_logger.isDebugEnabled())
         _logger.debug("loadDeptsMgr");

      DbConnection dbConn=new DbConnection();
      try{
      	dbConn.open(DBSessionManager.getSession());
	      ids = new IeciTdLongIntegerArrayList();
	      colName = table.getDeptIdColumnName();
	      qual = table.getUpdateMgrQual(usrId);
	      DbSelectFns.select(dbConn, table.getBaseTableName(),colName,qual,true,ids);

	      for (int i=0; i <ids.count(); i++)
	      {
	         Integer val = new Integer(ids.get(i));
	         deptsId.add(val);
	      }
      }catch(Exception e){
    		_logger.error(e);
    		throw e;
    	}finally{
    		dbConn.close();
    	}

   }

   private void updateDeptsMgr(String entidad) throws Exception
   {
      DepartmentsTable table = new DepartmentsTable();
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo;
      DynamicRow rowInfo;

      if (_logger.isDebugEnabled())
         _logger.debug("updateDeptsMgr");

      DbConnection dbConn=new DbConnection();
      try{
      	dbConn.open(DBSessionManager.getSession());

	      tableInfo.setTableObject(table);
	      tableInfo.setClassName(DepartmentsTable.class.getName());
	      tableInfo.setTablesMethod("getBaseTableName");
	      tableInfo.setColumnsMethod("getUpdateMgrColumnNames");

	      // Propietario de departamento
	      rowInfo = new DynamicRow();
	      rowsInfo = new DynamicRows();

	      rowInfo.addRow(this);
	      rowInfo.setClassName(UserImpl.class.getName());
	      rowInfo.setValuesMethod("updateDeptsMgrValues");
	      rowsInfo.add(rowInfo);

	      DynamicFns.update(dbConn, table.getUpdateMgrQual(_id), tableInfo, rowsInfo);
      }catch (Exception e){
    		_logger.error(e);
    		throw e;
    	}finally{
    		dbConn.close();
    	}
   }

   private void loadGroupsMgr(int usrId, ArrayList groupsId, String entidad) throws Exception
   {
      IeciTdLongIntegerArrayList ids;
      String qual;
      String colName;
      GroupsTable table = new GroupsTable();

      if (_logger.isDebugEnabled())
         _logger.debug("loadDeptsMgr");

      DbConnection dbConn=new DbConnection();
      try{
      	dbConn.open(DBSessionManager.getSession());
	      ids = new IeciTdLongIntegerArrayList();
	      colName = table.getGroupIdColumnName();
	      qual = table.getUpdateMgrQual(usrId);
	      DbSelectFns.select(dbConn, table.getBaseTableName(),colName,qual,true,ids);

	      for (int i=0; i <ids.count(); i++)
	      {
	         Integer val = new Integer(ids.get(i));
	         groupsId.add(val);
	      }
      }catch(Exception e){
    		_logger.error(e);
    		throw e;
    	}finally{
    		dbConn.close();
    	}

   }

   private void updateGroupsMgr(String entidad) throws Exception
   {
      GroupsTable table = new GroupsTable();
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo;
      DynamicRow rowInfo;

      if (_logger.isDebugEnabled())
         _logger.debug("updateGroupsMgr");

      DbConnection dbConn=new DbConnection();
      try{
      	  dbConn.open(DBSessionManager.getSession());

	      tableInfo.setTableObject(table);
	      tableInfo.setClassName(GroupsTable.class.getName());
	      tableInfo.setTablesMethod("getBaseTableName");
	      tableInfo.setColumnsMethod("getUpdateMgrColumnNames");

	      // Propietario de departamento
	      rowInfo = new DynamicRow();
	      rowsInfo = new DynamicRows();

	      rowInfo.addRow(this);
	      rowInfo.setClassName(UserImpl.class.getName());
	      rowInfo.setValuesMethod("updateGroupsMgrValues");
	      rowsInfo.add(rowInfo);

	      DynamicFns.update(dbConn, table.getUpdateMgrQual(_id), tableInfo, rowsInfo);
      }catch (Exception e){
    		_logger.error(e);
    		throw e;
    	}finally{
    		dbConn.close();
    	}
   }

   /**
	 * Comprueba que el usuario tiene distinto nombre a los que ya existen.
	 *
	 * @throws Exception Si existe ya el nombre del usuario.
	 */
	private void checkUserExists(String entidad) throws Exception
	{
		int count;
		UsersTable table = new UsersTable();

		DbConnection dbConn=new DbConnection();
		try{
			dbConn.open(DBSessionManager.getSession());
			if (_id == ISicresAdminDefsKeys.NULL_ID)
				count = DbSelectFns.selectCount(dbConn, table.getBaseTableName(),
				                                table.getCountNameQual(_name));
			else
				count = DbSelectFns.selectCount(dbConn, table.getBaseTableName(),
				                                table.getCountNameIdQual(_id, _name));
			if (count > 0)
				ISicresAdminBasicException.throwException(ISicresAdminUserKeys.EC_USER_EXISTS_NAME);
		}catch(Exception e){
			_logger.error(e);
			throw e;
		}finally{
			dbConn.close();
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
      UsersTable table = new UsersTable();
      Permission permis;

      DbConnection dbConn=new DbConnection();
      try{
      	  dbConn.open(DBSessionManager.getSession());
	      // Comprobamos si para el productId se tiene el permiso o no.
	      permis = _permsImpl._perms.getProductPermission(productId);
	      if ((permis.getPermission() & perm) == 0)
	      {
	         DbDeleteFns.delete(dbConn, table.getObjPermsTableName(),
	                  table.getDeleteObjPermQual(_id, ISicresAdminDefsKeys.DESTINATION_USER, perm,
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
    * Elimina todos los permisos del usuario sobre los objetos invesDoc.
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
    * Inserta un nuevo usuario en invesDoc.
    *
    * @throws Exception Si se produce algún error en la inserción del usuario.
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
         insertBase(entidad);
         _profilesImpl.insertProfiles(entidad);
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
    * Actualiza un usuario de invesDoc.
    *
    * @throws Exception Si se produce algún error en la actualización del usuario.
    */

   private void update(String entidad) throws Exception
   {
      boolean commit = false;
      boolean inTrans = false;
      int counter;

      if (_logger.isDebugEnabled())
         _logger.debug("update");

      DbConnection dbConn=new DbConnection();
      try
      {
    	dbConn.open(DBSessionManager.getSession());
         dbConn.beginTransaction();
         inTrans = true;
         updateBase(entidad);
         _profilesImpl.updateProfiles(_id, entidad);
         _permsImpl.updatePerms(_id, ISicresAdminDefsKeys.DESTINATION_USER, entidad);
         updateOwnership(entidad);
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
    * Inserta la parte base de las tablas de usuario (header).
    *
    * @throws Exception Si se produce algún error en la inserción del usuario.
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
      try{
      	 dbConn.open(DBSessionManager.getSession());
         tableInfo.setTableObject(table);
         tableInfo.setClassName(UsersTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getInsertBaseColumnNames");

         rowInfo.addRow(this);
         rowInfo.setClassName(UserImpl.class.getName());
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

   /**
    * Actualiza la parte base de las tablas de usuario (header).
    *
    * @throws Exception Si se produce algún error en la actualización del usuario.
    */
   private void updateBase(String entidad) throws Exception
	{
   	DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      UsersTable table = new UsersTable();

      if (_logger.isDebugEnabled())
			_logger.debug("update");

      DbConnection dbConn=new DbConnection();
      try{
      	 dbConn.open(DBSessionManager.getSession());
         tableInfo.setTableObject(table);
         tableInfo.setClassName(UsersTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getUpdateBaseColumnNames");

         rowInfo.addRow(this);
         rowInfo.setClassName(UserImpl.class.getName());
         rowInfo.setValuesMethod("updateValues");
         rowsInfo.add(rowInfo);

         DynamicFns.update(dbConn, table.getLoadBaseQual(_id), tableInfo, rowsInfo);

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
    * Actualiza los objetos de los que es propietario el usuario.
    *
    * @throws Exception Si se produce algún error en la actualización.
    */

   private void updateOwnership(String entidad) throws Exception
   {
      UsersTable table = new UsersTable();
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
      	profile = _profilesImpl._profiles.getProductProfile(ISicresAdminUserDefsKeys.PRODUCT_IDOC);
         prof = profile.getProfile();
         if ((prof == ISicresAdminUserDefsKeys.PROFILE_STANDARD) ||
             (prof == ISicresAdminUserDefsKeys.PROFILE_NONE))
         {
            tableInfo.setTableObject(table);
            tableInfo.setClassName(UsersTable.class.getName());
            tableInfo.setTablesMethod("getOwnershipTableName");
            tableInfo.setColumnsMethod("getUpdateOwnershipColumnNames");

            // Propietario de directorio y archivador
            rowInfo = new DynamicRow();
            rowsInfo = new DynamicRows();

            rowInfo.addRow(this);
            rowInfo.setClassName(UserImpl.class.getName());
            rowInfo.setValuesMethod("updateOwnershipValues");
            rowsInfo.add(rowInfo);
            DbConnection dbConn=new DbConnection();
            try{
            	dbConn.open(DBSessionManager.getSession());
	            DynamicFns.update(dbConn, table.getUpdateOwnerDirArchQual(_id,
	                              ISicresAdminDefsKeys.OBJECT_OWNER_TYPE_USER, ISicresAdminUserDefsKeys.PRODUCT_IDOC,
	                              ISicresAdminDefsKeys.OBJECT_OWNER_TYPE_DIRECTORY,
	                              ISicresAdminDefsKeys.OBJECT_OWNER_TYPE_ARCHIVE,
	                              ISicresAdminDefsKeys.ARCH_TYPE_STANDARD), tableInfo, rowsInfo);
            }catch (Exception e){
            	_logger.error(e);
            	throw e;
            }finally{
            	dbConn.close();
            }
         }
      }
   }

   /**
    * Actualiza todos los objetos de los que es propietario el usuario.
    *
    * @throws Exception Si se produce algún error en la actualización.
    */

   private void updateAllOwnership(String entidad) throws Exception
   {
      UsersTable table = new UsersTable();
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo;
      DynamicRow rowInfo;

      if (_logger.isDebugEnabled())
         _logger.debug("updateAllOwnership");

      tableInfo.setTableObject(table);
      tableInfo.setClassName(UsersTable.class.getName());
      tableInfo.setTablesMethod("getOwnershipTableName");
      tableInfo.setColumnsMethod("getUpdateOwnershipColumnNames");

      // Propietario de directorio
      rowInfo = new DynamicRow();
      rowsInfo = new DynamicRows();

      rowInfo.addRow(this);
      rowInfo.setClassName(UserImpl.class.getName());
      rowInfo.setValuesMethod("updateOwnershipValues");
      rowsInfo.add(rowInfo);

      DbConnection dbConn=new DbConnection();
      try{
      	  dbConn.open(DBSessionManager.getSession());
	      DynamicFns.update(dbConn, table.getUpdateOwnerQual(_id,
	                        ISicresAdminDefsKeys.OBJECT_OWNER_TYPE_USER), tableInfo, rowsInfo);
      }catch (Exception e){
    		_logger.error(e);
    		throw e;
    	}finally{
    		dbConn.close();
    	}
   }

   /**
	 * Comprueba si el texto del campo Descripción tiene comillas dobles
	 *
	 * @throws Exception Si el campo Descripción tiene comillas dobles.
	 */
	private void checkDescription() throws Exception
	{
		int index ;
		if (_description != null)
		{
			index = _description.indexOf("\"");
			if (index > -1)
				ISicresAdminBasicException
				.throwException(ISicresAdminUserKeys.EC_USER_DESC_EXIST_QUOTES);
		}
	}

	/**
	 * Comprueba que la contraseña no tenga espacios en blancos y que supera
	 * el tamaño mínimo configurado.
	 *
	 * @throws Exception Si la contraseña está vacía, si tiene espacios en blancos
	 * y si no supera el tamaño mínino configurado.
	 */
	private void checkPassword(String entidad) throws Exception
	{
		int index;
		if (_password != null || !_password.equals(""))
		{
			index = _password.indexOf(" ");
			if (index > -1)
				ISicresAdminBasicException
				.throwException(ISicresAdminUserKeys.EC_USER_PASS_WHITESPACE);
		}
		else
		{
			ISicresAdminBasicException
			.throwException(ISicresAdminUserKeys.EC_USER_PASS_EMPTY);
		}

		loadUserConfig(entidad);

		if (_password.length() < _pwdminlen)
		{
			ISicresAdminBasicException
			.throwException(ISicresAdminUserKeys.EC_USER_PASS_LENGTH);
		}

	}
	/**
	 * Comprueba si la contraseña se ha cambiado.
	 *
	 * @return <code>true</code> Si se ha cambiado la contraseña.
	 * 		  <code>false</code> En otro caso.
	 * @throws Exception
	 */
	private boolean changePassword(String entidad) throws Exception
	{
		String passOld;
		boolean change = false;

		if (_logger.isDebugEnabled())
         _logger.debug("changePassword");

		loadPassword(entidad);
		if (!_password.equals(_oldPassword))
			change = true;

		return change;
	}

	/**
    * Lee la información de la configuración de usuarios de invesDoc.
    *
    * @throws Exception Si se produce algún error en la lectura de la
    * información mencionada.
    */

   private void loadUserConfig(String entidad) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      UsersTable table = new UsersTable();

      if (_logger.isDebugEnabled())
         _logger.debug("loadUserConfig");

      DbConnection dbConn=new DbConnection();
      try{
      	 dbConn.open(DBSessionManager.getSession());
         tableInfo.setTableObject(table);
         tableInfo.setClassName(UsersTable.class.getName());
         tableInfo.setTablesMethod("getUserSysTableName");
         tableInfo.setColumnsMethod("getUserSysColumnName");

         rowInfo.addRow(this);
         rowInfo.setClassName(UserImpl.class.getName());
         rowInfo.setValuesMethod("loadUserSysValue");
         rowsInfo.add(rowInfo);

         if (!DynamicFns.select(dbConn, "", tableInfo, rowsInfo))
         {
            ISicresAdminBasicException.throwException(ISicresAdminUserKeys.EC_USER_NOT_CONFIG);
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
    * Lee la contraseña del usuario de invesDoc.
    *
    * @throws Exception Si se produce algún error en la lectura de la
    * información mencionada.
    */

   private void loadPassword(String entidad) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      UsersTable table = new UsersTable();
      UserImpl user = new UserImpl();
      String password = null;

      if (_logger.isDebugEnabled())
         _logger.debug("loadPassword");

      DbConnection dbConn=new DbConnection();
      try{
      	 dbConn.open(DBSessionManager.getSession());
         tableInfo.setTableObject(table);
         tableInfo.setClassName(UsersTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getPasswordColumnName");

         rowInfo.addRow(this);
         rowInfo.setClassName(UserImpl.class.getName());
         rowInfo.setValuesMethod("loadPasswordValue");
         rowsInfo.add(rowInfo);

         DynamicFns.select(dbConn, table.getLoadBaseQual(_id), tableInfo, rowsInfo);
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
      UsersTable table = new UsersTable();

      if (_logger.isDebugEnabled())
         _logger.debug("loadBase");

      DbConnection dbConn = new DbConnection();
      try
      {
    	  dbConn.open(DBSessionManager.getSession());
    	 //dbConn.open("org.postgresql.Driver", "jdbc:postgresql://128.90.111.90/registro_001", "postgres", "postgres");
         tableInfo.setTableObject(table);
         tableInfo.setClassName(UsersTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getLoadBaseColumnNames");

         rowInfo.addRow(this);
         rowInfo.setClassName(UserImpl.class.getName());
         rowInfo.setValuesMethod("loadValues");
         rowsInfo.add(rowInfo);

         if (!DynamicFns.select(dbConn, table.getLoadBaseQual(_id), tableInfo, rowsInfo))
         {
            ISicresAdminBasicException.throwException(ISicresAdminUserKeys.EC_USER_NOT_EXITS);
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
    * Lee la información básica del usuario invesDoc.
    *
    * @throws Exception Si se produce algún error en la lectura de la
    * información mencionada.
    */

   private void loadBaseName(String entidad) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      UsersTable table = new UsersTable();

      if (_logger.isDebugEnabled())
         _logger.debug("loadBaseName");

      DbConnection dbConn=new DbConnection();
      try{
      	 dbConn.open(DBSessionManager.getSession());
         tableInfo.setTableObject(table);
         tableInfo.setClassName(UsersTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getLoadBaseColumnNames");

         rowInfo.addRow(this);
         rowInfo.setClassName(UserImpl.class.getName());
         rowInfo.setValuesMethod("loadValues");
         rowsInfo.add(rowInfo);

         if (!DynamicFns.select(dbConn, table.getLoadNameBaseQual(_name), tableInfo, rowsInfo)){
            ISicresAdminBasicException.throwException(ISicresAdminUserKeys.EC_USER_NOT_EXITS);
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

   public void resetProfiles(){
	   _profilesImpl.resetProfiles();
   }

	private void init(int userConnected, int deptId)
   {
		_userConnected = userConnected;
      _id = ISicresAdminDefsKeys.NULL_ID;
      _name = null;
      _password = null;
      _oldPassword = null;
      _isChange = false;
      _pwdmbc = "N";
      _pwdvpcheck = "N";
      _description = null;
      _deptId = deptId;
      _state = ISicresAdminUserDefsKeys.USER_STAT_DEF;
      _profilesImpl = new GenericProfilesImpl();
      _permsImpl = new GenericPermsImpl();
      _wasAdmin = false;
      _creatorId = _userConnected;
      _creationDate = DbDataType.NULL_DATE_TIME;
      _updaterId = DbDataType.NULL_LONG_INTEGER;
      _updateDate = DbDataType.NULL_DATE_TIME;
      _pwdLastUpdTs = 0;
      _pwdminlen = 0;

   }

	private int _userConnected;
   private int _id;
   private String _name;
   private String _password;
   private String _oldPassword;
   private boolean _isChange;
   private String _pwdmbc;
   private String _pwdvpcheck;
   private String _description;
   private int _deptId;
   private int _state;
   private GenericProfilesImpl _profilesImpl;
   private GenericPermsImpl _permsImpl;
   private boolean _wasAdmin;
   private int _creatorId;
   private Date _creationDate;
   private int _updaterId;
   private Date _updateDate;
   private long _pwdLastUpdTs;
   private int _pwdminlen;

   private static final Logger _logger = Logger.getLogger(UserImpl.class);

}
