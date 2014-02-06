package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.core.datetime.DatePattern;
import ieci.tecdoc.core.datetime.DateTimeUtil;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbDeleteFns;
import ieci.tecdoc.core.db.DbInputStatement;
import ieci.tecdoc.core.db.DbOutputStatement;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.core.db.DbUtil;
import ieci.tecdoc.core.xml.lite.XmlTextBuilder;
import ieci.tecdoc.idoc.admin.api.exception.AdminException;
import ieci.tecdoc.idoc.admin.api.exception.UserErrorCodes;
import ieci.tecdoc.idoc.admin.api.user.Permission;
import ieci.tecdoc.idoc.admin.api.user.Permissions;
import ieci.tecdoc.idoc.admin.api.user.User;
import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.idoc.admin.api.user.UserProfile;
import ieci.tecdoc.idoc.admin.api.user.UserProfiles;
import ieci.tecdoc.idoc.admin.database.DepartmentsTable;
import ieci.tecdoc.idoc.admin.database.GroupsTable;
import ieci.tecdoc.idoc.admin.database.UsersTable;
import ieci.tecdoc.idoc.core.database.DynamicFns;
import ieci.tecdoc.idoc.core.database.DynamicRow;
import ieci.tecdoc.idoc.core.database.DynamicRows;
import ieci.tecdoc.idoc.core.database.DynamicTable;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.uas.std.UasMisc;
import ieci.tecdoc.sbo.util.nextid.NextId;
import ieci.tecdoc.core.db.DbConnectionConfig;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

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
      init(Defs.NULL_ID, Defs.NULL_ID);
   }
	
	/**
    * Establece la configuración de la conexión de base de datos
    * 
    * @param dbConnConfig Configuración de la conexión de base de datos
    * @throws Exception
    */
   public void setConnectionConfig(DbConnectionConfig dbConnConfig)
               throws Exception
   {
      _dbCntConfig = dbConnConfig;
   }
   
   public void load(int id) throws Exception
   {
      load(id, true);
   }
   
   public void load(String name) throws Exception
   {
      loadByName(name, true);
   }   
	
	public void load(int id, boolean full) throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("load: Id = " + Integer.toString(id));
      
		try
		{
         _id = id;
         
			DbConnection.open(getDbConfig());

         loadBase();
         if (full)
         {
         	_profilesImpl.loadProfiles(_id);
            _permsImpl.loadPerms(_id, Defs.DESTINATION_USER);           
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
   
   public void loadByName(String name, boolean full) throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("loadByName: Name = " + name);
      
		try
		{
         _name = name;
         
			DbConnection.open(getDbConfig());

         loadBaseName();
         if (full)
         {
         	_profilesImpl.loadProfiles(_id);
            _permsImpl.loadPerms(_id, Defs.DESTINATION_USER);
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
   
   public void initSysSuperUser() throws Exception
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
			checkUserExists();
			profiles = (UserProfilesImpl)_profilesImpl.getProfiles();
			profile = new UserProfileImpl(0, UserDefs.PRODUCT_SYSTEM, 
               								UserDefs.PROFILE_SUPERUSER);
			profiles.add(profile);
			
			insertBase();
			_profilesImpl.insertProfiles();
      }
      catch(Exception e)
      {
         _logger.error(e);
         throw e;
      }
      
   }   
	
	public void store() throws Exception 
   {
   	if (_logger.isDebugEnabled())
         _logger.debug("store");
      
		try
		{
			
			DbConnection.open(getDbConfig());
			
			checkUserExists();
			checkDescription();
      	checkPassword();
      	
         if (_id == Defs.NULL_ID)
         {
         	
            _id = NextId.generateNextId(UsersTable.TN_NEXTID, 
                                        Defs.NEXT_ID_USER_TABLE_TYPE_USER);
            
            _profilesImpl.setDefaultProfiles(_id);
            _permsImpl.setDefaultPerms(_id, Defs.DESTINATION_USER);
            insert();
         }
         else
         {
         	_isChange = changePassword();
            update();
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
   
	
	public void delete() throws Exception 
   {
      boolean commit = false;
      boolean inTrans = false;
      UsersTable table = new UsersTable(); 

      if (_logger.isDebugEnabled())
         _logger.debug("delete");

      // Con esto también se evita que se pueda eliminar SYSSUPERUSER
      if (_id == _userConnected)
      {
         AdminException.throwException(UserErrorCodes.EC_USER_CONNECTED);
      }
      
		try
		{
			DbConnection.open(getDbConfig());
         
         DbConnection.beginTransaction();
         inTrans = true;
         
         DbDeleteFns.delete(table.getBaseTableName(), 
                            table.getDeleteBaseQual(_id));
         _profilesImpl.deleteProfiles(_id);
         _permsImpl.deletePerms(_id, Defs.DESTINATION_USER);
         
         DbDeleteFns.delete(table.getObjPermsTableName(), 
                            table.getDeleteObjPermsQual(_id,
                            Defs.DESTINATION_USER));
         deleteGroupUserRel();
         updateDeptsMgr();
         updateGroupsMgr();
         updateAllOwnership();
         
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
   
   public ArrayList getDeptsIdMgr(int userId) throws Exception
   {
      ArrayList deptsId;
      
      if (_logger.isDebugEnabled())
         _logger.debug("getDeptsIdMgr");
      
      try
      {         
         deptsId = new ArrayList();
         
         DbConnection.open(getDbConfig());
         
         loadDeptsMgr(userId,deptsId);
         
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
      
      return (deptsId);
   }
   
   public ArrayList getGroupsIdMgr(int userId) throws Exception
   {
      ArrayList grpsId;
      
      if (_logger.isDebugEnabled())
         _logger.debug("getGroupsIdMgr");
      
      try
      {         
         grpsId = new ArrayList();
         
         DbConnection.open(getDbConfig());
         
         loadGroupsMgr(userId,grpsId);
         
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
      statement.setLongInteger(index++, Defs.USER_FLAG_NULL);
      statement.setLongInteger(index++, _state);
      statement.setLongInteger(index++, Defs.USER_NUMBADCNTS);
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

      statement.setLongInteger(index++, Defs.SYSSUPERUSER_ID);
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
      
      statement.setLongInteger(index++, Defs.SYSSUPERUSER_ID);
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
      
      statement.setLongInteger(index++, Defs.SYSSUPERUSER_ID);
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
     
   
   private void deleteGroupUserRel() throws Exception
   {
      UsersTable table = new UsersTable(); 
      
      DbDeleteFns.delete(table.getGURelTableName(), 
            table.getLoadGURelUserIdQual(_id));
      
   }
   
   private void loadDeptsMgr(int usrId, ArrayList deptsId) throws Exception
   {
      IeciTdLongIntegerArrayList ids;
      String qual;
      String colName;
      DepartmentsTable table = new DepartmentsTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("loadDeptsMgr");
      
      ids = new IeciTdLongIntegerArrayList();
      colName = table.getDeptIdColumnName();
      qual = table.getUpdateMgrQual(usrId);
      DbSelectFns.select(table.getBaseTableName(),colName,qual,true,ids);
      
      for (int i=0; i <ids.count(); i++)
      {
         Integer val = new Integer(ids.get(i));
         deptsId.add(val);
      }
   }
   
   private void updateDeptsMgr() throws Exception
   {
      DepartmentsTable table = new DepartmentsTable();
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo;
      DynamicRow rowInfo;
      
      if (_logger.isDebugEnabled())
         _logger.debug("updateDeptsMgr");

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

      DynamicFns.update(table.getUpdateMgrQual(_id), tableInfo, rowsInfo);
   }
   
   private void loadGroupsMgr(int usrId, ArrayList groupsId) throws Exception
   {
      IeciTdLongIntegerArrayList ids;
      String qual;
      String colName;
      GroupsTable table = new GroupsTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("loadDeptsMgr");
      
      ids = new IeciTdLongIntegerArrayList();
      colName = table.getGroupIdColumnName();
      qual = table.getUpdateMgrQual(usrId);
      DbSelectFns.select(table.getBaseTableName(),colName,qual,true,ids);
      
      for (int i=0; i <ids.count(); i++)
      {
         Integer val = new Integer(ids.get(i));
         groupsId.add(val);
      }
   }
   
   private void updateGroupsMgr() throws Exception
   {
      GroupsTable table = new GroupsTable();
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo;
      DynamicRow rowInfo;
      
      if (_logger.isDebugEnabled())
         _logger.debug("updateGroupsMgr");

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

      DynamicFns.update(table.getUpdateMgrQual(_id), tableInfo, rowsInfo);
   }
   
   /**
	 * Comprueba que el usuario tiene distinto nombre a los que ya existen.
	 * 
	 * @throws Exception Si existe ya el nombre del usuario.
	 */
	private void checkUserExists() throws Exception
	{
		int count;
		UsersTable table = new UsersTable();
		
		if (_id == Defs.NULL_ID)
			count = DbSelectFns.selectCount(table.getBaseTableName(), 
			                                table.getCountNameQual(_name));
		else 
			count = DbSelectFns.selectCount(table.getBaseTableName(), 
			                                table.getCountNameIdQual(_id, _name));
		if (count > 0)
			AdminException.throwException(UserErrorCodes.EC_USER_EXISTS_NAME);
	}
	
	
   /**
    * Elimina los permisos de un usuario sobre todos los objetos.
    * 
    * @param perm Tipo de permiso a eliminar.
    * @param productId Identificador del producto.
    * @throws Exception Si se produce algún error en la actualización.
    */
    
   private void deleteObjPerm(int perm, int productId) throws Exception
   {
      UsersTable table = new UsersTable();
      Permission permis;
      
      // Comprobamos si para el productId se tiene el permiso o no.
      permis = _permsImpl._perms.getProductPermission(productId);
      if ((permis.getPermission() & perm) == 0)
      {
         DbDeleteFns.delete(table.getObjPermsTableName(),
                  table.getDeleteObjPermQual(_id, Defs.DESTINATION_USER, perm,
                  productId));
      }
   }

   /**
    * Elimina todos los permisos del usuario sobre los objetos invesDoc.
    *  
    * @throws Exception Si se produce algún error en la eliminación.
    */
    
   private void deleteObjPerms() throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("deleteObjPerms");

      deleteObjPerm(UserDefs.PERMISSION_PRINTING, UserDefs.PRODUCT_IDOC);
      deleteObjPerm(UserDefs.PERMISSION_DELETION, UserDefs.PRODUCT_IDOC);
      deleteObjPerm(UserDefs.PERMISSION_CREATION, UserDefs.PRODUCT_IDOC);
      deleteObjPerm(UserDefs.PERMISSION_UPDATE, UserDefs.PRODUCT_IDOC);
      deleteObjPerm(UserDefs.PERMISSION_QUERY, UserDefs.PRODUCT_IDOC);
   }
   
	/**
    * Inserta un nuevo usuario en invesDoc.
    *  
    * @throws Exception Si se produce algún error en la inserción del usuario.
    */
    
   private void insert() throws Exception
   {
      boolean commit = false;
      boolean inTrans = false;
      int counter;
      
      if (_logger.isDebugEnabled())
         _logger.debug("insert");

      try 
      {
         DbConnection.beginTransaction();
         inTrans = true;
         insertBase();
         _profilesImpl.insertProfiles();
         _permsImpl.insertPerms();
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
            DbConnection.endTransaction(commit);
      }
      
   }
   
   /**
    * Actualiza un usuario de invesDoc.
    *  
    * @throws Exception Si se produce algún error en la actualización del usuario.
    */
    
   private void update() throws Exception
   {
      boolean commit = false;
      boolean inTrans = false;
      int counter;
      
      if (_logger.isDebugEnabled())
         _logger.debug("update");

      try 
      {
         DbConnection.beginTransaction();
         inTrans = true;
         updateBase();
         _profilesImpl.updateProfiles(_id);
         _permsImpl.updatePerms(_id, Defs.DESTINATION_USER);
         updateOwnership();
         deleteObjPerms();
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
            DbConnection.endTransaction(commit);
      }
   }
   
   /**
    * Inserta la parte base de las tablas de usuario (header).
    *  
    * @throws Exception Si se produce algún error en la inserción del usuario.
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
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getInsertBaseColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(UserImpl.class.getName());
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
   
   /**
    * Actualiza la parte base de las tablas de usuario (header).
    *  
    * @throws Exception Si se produce algún error en la actualización del usuario.
    */
   private void updateBase() throws Exception
	{
   	DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      UsersTable table = new UsersTable();
      
      if (_logger.isDebugEnabled())
			_logger.debug("update");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(UsersTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getUpdateBaseColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(UserImpl.class.getName());
         rowInfo.setValuesMethod("updateValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.update(table.getLoadBaseQual(_id), tableInfo, rowsInfo);
         
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
   }

   /**
    * Actualiza los objetos de los que es propietario el usuario.
    *  
    * @throws Exception Si se produce algún error en la actualización.
    */
    
   private void updateOwnership() throws Exception
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
      	profile = _profilesImpl._profiles.getProductProfile(UserDefs.PRODUCT_IDOC);
         prof = profile.getProfile();
         if ((prof == UserDefs.PROFILE_STANDARD) || 
             (prof == UserDefs.PROFILE_NONE))
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
      
            DynamicFns.update(table.getUpdateOwnerDirArchQual(_id, 
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
    * @throws Exception Si se produce algún error en la actualización.
    */
    
   private void updateAllOwnership() throws Exception
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

      DynamicFns.update(table.getUpdateOwnerQual(_id, 
                        Defs.OBJECT_OWNER_TYPE_USER), tableInfo, rowsInfo);

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
				AdminException
				.throwException(UserErrorCodes.EC_USER_DESC_EXIST_QUOTES);
		}
	}
	
	/**
	 * Comprueba que la contraseña no tenga espacios en blancos y que supera 
	 * el tamaño mínimo configurado.
	 * 
	 * @throws Exception Si la contraseña está vacía, si tiene espacios en blancos 
	 * y si no supera el tamaño mínino configurado.
	 */
	private void checkPassword() throws Exception
	{
		int index;
		if (_password != null || !_password.equals("")) 
		{
			index = _password.indexOf(" ");
			if (index > -1)
				AdminException
				.throwException(UserErrorCodes.EC_USER_PASS_WHITESPACE);
		}
		else 
		{
			AdminException
			.throwException(UserErrorCodes.EC_USER_PASS_EMPTY);
		}
		
		loadUserConfig();
		
		if (_password.length() < _pwdminlen) 
		{
			AdminException
			.throwException(UserErrorCodes.EC_USER_PASS_LENGTH);
		}
				
	}
	/**
	 * Comprueba si la contraseña se ha cambiado.
	 * 
	 * @return <code>true</code> Si se ha cambiado la contraseña.
	 * 		  <code>false</code> En otro caso.
	 * @throws Exception
	 */
	private boolean changePassword() throws Exception
	{
		String passOld;
		boolean change = false;
		
		if (_logger.isDebugEnabled())
         _logger.debug("changePassword");
			
		loadPassword();
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

   private void loadUserConfig() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      UsersTable table = new UsersTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("loadUserConfig");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(UsersTable.class.getName());
         tableInfo.setTablesMethod("getUserSysTableName");
         tableInfo.setColumnsMethod("getUserSysColumnName");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(UserImpl.class.getName());
         rowInfo.setValuesMethod("loadUserSysValue");
         rowsInfo.add(rowInfo);
         
         if (!DynamicFns.select("", tableInfo, rowsInfo))
         {
            AdminException.throwException(UserErrorCodes.EC_USER_NOT_CONFIG);
         }
         
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
   }
   
   /**
    * Lee la contraseña del usuario de invesDoc.
    *  
    * @throws Exception Si se produce algún error en la lectura de la 
    * información mencionada.
    */

   private void loadPassword() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      UsersTable table = new UsersTable();
      UserImpl user = new UserImpl();
      String password = null;
      
      if (_logger.isDebugEnabled())
         _logger.debug("loadPassword");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(UsersTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getPasswordColumnName");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(UserImpl.class.getName());
         rowInfo.setValuesMethod("loadPasswordValue");
         rowsInfo.add(rowInfo);
         
         DynamicFns.select(table.getLoadBaseQual(_id), tableInfo, rowsInfo);
         
         
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
   }
   
   /**
    * Lee la información básica del usuario invesDoc.
    *  
    * @throws Exception Si se produce algún error en la lectura de la 
    * información mencionada.
    */

   private void loadBase() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      UsersTable table = new UsersTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("loadBase");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(UsersTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getLoadBaseColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(UserImpl.class.getName());
         rowInfo.setValuesMethod("loadValues");
         rowsInfo.add(rowInfo);
         
         if (!DynamicFns.select(
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
  }
   
   /**
    * Lee la información básica del usuario invesDoc.
    *  
    * @throws Exception Si se produce algún error en la lectura de la 
    * información mencionada.
    */

   private void loadBaseName() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      UsersTable table = new UsersTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("loadBaseName");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(UsersTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getLoadBaseColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(UserImpl.class.getName());
         rowInfo.setValuesMethod("loadValues");
         rowsInfo.add(rowInfo);
         
         if (!DynamicFns.select(
                        table.getLoadNameBaseQual(_name), tableInfo, rowsInfo))
         {
            AdminException.throwException(UserErrorCodes.EC_USER_NOT_EXITS);
         }
         
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
   }
   	
	private void init(int userConnected, int deptId)
   {
		_userConnected = userConnected;
      _id = Defs.NULL_ID;
      _name = null;
      _password = null;
      _oldPassword = null;
      _isChange = false;
      _pwdmbc = "N";
      _pwdvpcheck = "N";
      _description = null;
      _deptId = deptId;
      _state = UserDefs.USER_STAT_DEF;
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
	
	 /**
    * Devuelve la conexión a base de datos. Si la conexión es nula la crea 
    * a través del fichero de configuración de base de datos. 
    * 
    * @return Conexión con la base de datos
    * @throws Exception
    */
   private DbConnectionConfig getDbConfig() throws Exception
   {
      if (_dbCntConfig == null)
      {
         _dbCntConfig = CfgMdoConfig.getDbConfig();  
      }
      
      return  _dbCntConfig;
   }

   private DbConnectionConfig _dbCntConfig;
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
