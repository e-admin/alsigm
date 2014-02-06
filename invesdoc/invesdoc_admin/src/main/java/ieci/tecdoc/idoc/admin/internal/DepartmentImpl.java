package ieci.tecdoc.idoc.admin.internal;

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
import ieci.tecdoc.idoc.admin.api.exception.DeptErrorCodes;
import ieci.tecdoc.idoc.admin.api.user.BasicUsers;
import ieci.tecdoc.idoc.admin.api.user.Department;
import ieci.tecdoc.idoc.admin.api.user.Permission;
import ieci.tecdoc.idoc.admin.api.user.Permissions;
import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.idoc.admin.api.user.Users;
import ieci.tecdoc.idoc.admin.database.DepartmentsTable;
import ieci.tecdoc.idoc.admin.database.UsersTable;
import ieci.tecdoc.idoc.core.database.DynamicFns;
import ieci.tecdoc.idoc.core.database.DynamicRow;
import ieci.tecdoc.idoc.core.database.DynamicRows;
import ieci.tecdoc.idoc.core.database.DynamicTable;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.util.nextid.NextId;

import java.util.Date;

import org.apache.log4j.Logger;


public class DepartmentImpl implements Department
{
	public DepartmentImpl(int userConnected, int parentId)
   {
      init(userConnected, parentId);
   }
   
   public DepartmentImpl()
   {
      init(Defs.NULL_ID, Defs.NULL_ID);
   }
   
   public void load(String name) throws Exception
   {
      loadByName(name,true);
   }
   
   public void load(int id) throws Exception
   {
      load(id, true);
   }
   
   public void loadByName(String name, boolean full) throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("loadByName: Name = " + name);
      
		try
		{
         _name = name;
         
			DbConnection.open(CfgMdoConfig.getDbConfig());

         loadBaseName();
         if (full)
         {
         	_permsImpl.loadPerms(_id, Defs.DESTINATION_DEPT);
         	loadAdminUsersId();
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
   public void load(int id, boolean full) throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("load: Id = " + Integer.toString(id));
      
		try
		{
         _id = id;
         
			DbConnection.open(CfgMdoConfig.getDbConfig());

         loadBase();
         if (full)
         {
         	_permsImpl.loadPerms(_id, Defs.DESTINATION_DEPT);
         	loadAdminUsersId();
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

   public void store() throws Exception 
   {
      if (_logger.isDebugEnabled())
         _logger.debug("store");
      
		try
		{
			DbConnection.open(CfgMdoConfig.getDbConfig());
			
			checkDeptExists();
			checkDescription();

         if (_id == Defs.NULL_ID)
         {
            _id = NextId.generateNextId(UsersTable.TN_NEXTID, 
                                 Defs.DESTINATION_DEPT);
            _permsImpl.setDefaultPerms(_id, Defs.DESTINATION_DEPT);
            insert();
         }
         else
         {
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
      DepartmentsTable tableDept = new DepartmentsTable();

      if (_logger.isDebugEnabled())
         _logger.debug("delete");

		try
		{
			DbConnection.open(CfgMdoConfig.getDbConfig());
         
         DbConnection.beginTransaction();
         inTrans = true;
         DbDeleteFns.delete(tableDept.getBaseTableName(), 
         		tableDept.getDeleteBaseQual(_id));
         _permsImpl.deletePerms(_id, Defs.DESTINATION_DEPT);
         DbDeleteFns.delete(table.getObjPermsTableName(), 
                            table.getDeleteObjPermsQual(_id,
                            Defs.OBJECT_OWNER_TYPE_DEPT));
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
   
   /**
    * Obtiene el identificador de departamento.
    * 
    * @return El identificador mencionado.
    */
   public int getId()
	{
   	return _id;
   }
   
   /**
    * Obtiene el nombre del departamento.
    *  
    * @return El nombre mencionado.
    */
   public String getName()
	{
   	return _name;
   }
   
   /**
    * Establece el nombre del departamento.
    * 
    * @param name Nombre del departamento.
    */
   public void setName(String name)
	{
   	_name = name;
   }
   
   /**
    * Obtiene el identificador del padre del departamento.
    *  
    * @return El nombre mencionado.
    */
   public int getParentId()
	{
   	return _parentId;
   }
   
   /**
    * Devuelve lista de usuarios suceptibles de ser administradores
    * 
    * @return la lista
    */
   public BasicUsers getAdminUsers() throws Exception
   {
      if (_adminUsers.count() == 0)
      {
         try
   		{
   			DbConnection.open(CfgMdoConfig.getDbConfig());
   			loadAdminUsersId();
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
         
      return _adminUsers;
   }
   
   /**
    * Establece el identificador del padre del departamento.
    * 
    * @param parentId El identificador del padre del departamento.
    */
   public void setParentId(int parentId)
	{
   	_parentId = parentId;
   }
   
   /**
    * Obtiene el identificador del administrador del departamento.
    * 
    * @return El identificador mencionado.
    */
   public int getManagerId()
	{
   	return _managerId;
   }
   
   /**
    * Establece el identificador del administrador del departamento.
    * 
    * @param managerId El identificador del administrador.
    */
   public void setManagerId(int managerId)
	{
   	_managerId = managerId;
   }
   
   /**
    * Obtiene el identificador del tipo de departamento.
    * 
    * @return El identificador mencionado.
    */
   public int getType()
	{
   	return _type;
   }
   
   /**
    * Obtiene la descripción del departamento.
    * 
    * @return El nombre mencionado.
    */
   public String getDescription()
	{
   	return _description;
   }
   
   /**
    * Establece la descripción del departamento.
    * 
    * @param description La descripción del grupo.
    */
   public void setDescription(String description)
	{
   	_description = description;
   }
   
   /**
	 * Obtiene el identificador del usuario que ha creado el departamento. 
	 * 
	 * @return El identificador mencionado.
	 */
	public int getCreatorId()
   {
      return _creatorId;   
   }
	
	/**
    * Obtiene la fecha de creación del departamento.
    *
    * @return La fecha mencionada.
    */
   public Date getCreationDate()
   {
      return _creationDate;   
   }
   
   /**
    * Obtiene el identificador del usuario que ha actualizado el departamento.
    *
    * @return El identificador mencionado.
    */
   public int getUpdaterId()
   {
      return _updaterId;   
   }
   
   /**
    * Obtiene la fecha de actualización del departamento.
    *
    * @return La fecha mencionada.
    */
   public Date getUpdateDate()
   {
      return _updateDate;   
   }
   
   /**
    * Devuelve la lista de permisos del departamento.
    * 
    * @return La lista mencionada.
    */
   
   public Permissions getPermissions()
	{
   	return _permsImpl.getPermissions();
   }
   
   /**
    * Devuelve la lista de usuarios del departamento.
    * No es necesario cargar el objeto antes.
    * 
    * @param id Identificador del departamento.
    * @return Los usuarios del departamento.
    */
   public Users getUsersByDept(int id) throws Exception
	{
   	if (_logger.isDebugEnabled())
         _logger.debug("getUsersByDept");
      
		try
		{
         _id = id;
         //la conexión a base de datos está en la función
			_users.loadByDept(_id);
         
		}
		catch(Exception e)
		{
         _logger.error(e);
         throw e;
		}
      
   	
   	return _users;
   }
	
   /**
    * Obtiene la información del departamento en formato XML.
    *
    * @param header Indica si hay que incluir la cabecera xml o no.
    * @return La información mencionada.
    */
    
   public String toXML(boolean header) 
   {
   	String text;
      XmlTextBuilder bdr;
      String tagName = "Departament";
      
      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);
      
      bdr.addSimpleElement("Id", Integer.toString(_id));
      bdr.addSimpleElement("Name", _name);
      bdr.addSimpleElement("ParentId", Integer.toString(_parentId));
      bdr.addSimpleElement("ManagerId", Integer.toString(_managerId));
      bdr.addSimpleElement("Type", Integer.toString(_type));
      bdr.addSimpleElement("Description", _description);
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
      

      bdr.addFragment(_permsImpl._perms.toXML(false));

      bdr.addClosingTag(tagName);
      
      return bdr.getText();
   }
   
   /**
    * Obtiene la información del departamento en formato XML.
    *
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
    * Actualiza en base de datos la información relativa a objetos propiedad
    * del departamento. 
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
    * Recupera de la base de datos información asociada al departamento. 
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
      _parentId = statement.getLongInteger(index++);
      _managerId = statement.getLongInteger(index++);
      _type = statement.getLongInteger(index++);
      _description = statement.getShortText(index++);
      _creatorId = statement.getLongInteger(index++);
      _creationDate = statement.getDateTime(index++);
      _updaterId = statement.getLongInteger(index++);
      _updateDate = statement.getDateTime(index++);

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
      statement.setLongInteger(index++, _parentId);
      statement.setLongInteger(index++, _managerId);
      statement.setLongInteger(index++, _type);
      statement.setShortText(index++, _description);
      statement.setLongInteger(index++, _creatorId);
		_creationDate = DbUtil.getCurrentDateTime();
		statement.setDateTime(index++, _creationDate);
		
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
      statement.setLongInteger(index++, _managerId);
      statement.setLongInteger(index++, _parentId);
      statement.setShortText(index++, _description);
      _updaterId = _userConnected;
      statement.setLongInteger(index++, _updaterId);
		_updateDate = DbUtil.getCurrentDateTime();
		statement.setDateTime(index++, _updateDate);
		
      return new Integer(index);
   }
   
   /**
    * Recupera el identificador y el nombre del departamento. 
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
         _logger.debug("loadIdNameValues");

      _id = statement.getLongInteger(index ++);
      _name = statement.getShortText(index++);

      return new Integer(index);
   }
   
   /**
    * Recupera el identificador, el nombre del departamento y
    * el identificador del padre del departamento 
    * 
    * @param statement
    * @param idx
    * @return 
    * @throws java.lang.Exception
    */
   public Integer loadIdNameParentValues(DbOutputStatement statement, Integer idx) 
   throws Exception 
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("loadIdNameValues");

      _id = statement.getLongInteger(index ++);
      _name = statement.getShortText(index++);
      _parentId = statement.getLongInteger(index ++);

      return new Integer(index);
   }
   
   /**
	 * Comprueba que el departamento tiene distinto nombre a los que ya existen.
	 * 
	 * @throws Exception Si existe ya el nombre del departamento.
	 */
	private void checkDeptExists() throws Exception
	{
		int count;
		DepartmentsTable table = new DepartmentsTable();
		
		if (_id == Defs.NULL_ID)
			count = DbSelectFns.selectCount(table.getBaseTableName(), table
					.getCountNameQual(_name));
		else 
			count = DbSelectFns.selectCount(table.getBaseTableName(), table
					.getCountNameIdQual(_id, _name));
		if (count > 0)
			AdminException.throwException(DeptErrorCodes.EC_DEPT_EXIST_NAME);
	}
	
	/**
	 * Comprueba si el texto del campo Descripción tiene comillas dobles
	 * 
	 * @throws Exception Si el campo Descripción tiene comillas dobles.
	 */
	private void checkDescription() throws Exception
	{
		int index ;
		if (_description != null) {
			index = _description.indexOf("\"");
			if (index > -1)
				AdminException
				.throwException(DeptErrorCodes.EC_DEPT_DESC_EXIST_QUOTES);
		}
	}
   
	/**
    * Actualiza todos los objetos de los que es propietario el departamento.
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
      rowInfo.setClassName(DepartmentImpl.class.getName());
      rowInfo.setValuesMethod("updateOwnershipValues");
      rowsInfo.add(rowInfo);

      DynamicFns.update(table.getUpdateOwnerQual(_id, 
                        Defs.OBJECT_OWNER_TYPE_DEPT), tableInfo, rowsInfo);

   }
   
   /**
    * Inserta un nuevo departamento en invesDoc.
    *  
    * @throws Exception Si se produce algún error en la inserción del departamento.
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
    * Actualiza un departamento de invesDoc.
    *  
    * @throws Exception Si se produce algún error en la actualización del departamento.
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
         _permsImpl.updatePerms(_id, Defs.DESTINATION_DEPT);
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
      DepartmentsTable table = new DepartmentsTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("loadBase");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(DepartmentsTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getLoadBaseColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(DepartmentImpl.class.getName());
         rowInfo.setValuesMethod("loadValues");
         rowsInfo.add(rowInfo);
         
         if (!DynamicFns.select(
                        table.getLoadBaseQual(_id), tableInfo, rowsInfo))
         {
            AdminException.throwException(DeptErrorCodes.EC_DEPT_NOT_EXITS);
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
      DepartmentsTable table = new DepartmentsTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("loadBaseName");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(DepartmentsTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getLoadBaseColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(DepartmentImpl.class.getName());
         rowInfo.setValuesMethod("loadValues");
         rowsInfo.add(rowInfo);
         
         if (!DynamicFns.select(
                        table.getCountNameQual(_name), tableInfo, rowsInfo))
         {
            AdminException.throwException(DeptErrorCodes.EC_DEPT_NOT_EXITS);
         }
         
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}
   }
   
   /**
    * Inserta la parte base de las tablas de departamento (header).
    *  
    * @throws Exception Si se produce algún error en la inserción del departamento.
    */
    
   private void insertBase() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      DepartmentsTable table = new DepartmentsTable();
      
      if (_logger.isDebugEnabled())
         _logger.debug("insertBase");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(DepartmentsTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getInsertBaseColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(DepartmentImpl.class.getName());
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
    * Actualiza la parte base de las tablas de grupos (header).
    *  
    * @throws Exception Si se produce algún error en la actualización del grupo.
    */
   private void updateBase() throws Exception
	{
   	DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      DepartmentsTable table = new DepartmentsTable();
      
      if (_logger.isDebugEnabled())
			_logger.debug("updateBase");

      try 
      {
         tableInfo.setTableObject(table);
         tableInfo.setClassName(DepartmentsTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getUpdateBaseColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(DepartmentImpl.class.getName());
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
    * Elimina los permisos de un departamento sobre todos los objetos.
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
                  table.getDeleteObjPermQual(_id, Defs.DESTINATION_DEPT, perm,
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
    * Obtiene los identificadores de los usuarios suceptibles de ser
    * administradores del departamento.
    * @throws Exception Errores
    */
   private void loadAdminUsersId() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      UsersTable     usrTbl = new UsersTable();
      int            counter;
      BasicUserImpl  user;
      String         qual;
      
      //    Cargamos posibles usuarios administradores

      try
      {
	      
         tableInfo.setTableObject(usrTbl);
         tableInfo.setClassName(UsersTable.class.getName());
         qual = usrTbl.getLoadAminUsersQual(UserDefs.PRODUCT_USER);
	         
	      tableInfo.setTablesMethod("getUserAdminTableNames");
	      tableInfo.setColumnsMethod("getAdminUserColumnNames");
	      
	      rowInfo = new DynamicRow();
	      rowsInfo = new DynamicRows();
	      rowInfo.setClassName(BasicUserImpl.class.getName());
	      rowInfo.setValuesMethod("loadValues");
	      rowsInfo.add(rowInfo);
	      
	      DynamicFns.selectMultiple(qual, true, tableInfo, rowsInfo);
	                                
	      for (counter = 0; counter < rowInfo.getRowCount(); counter++)
	      {
	         user = (BasicUserImpl)rowInfo.getRow(counter);
	         _adminUsers.add(user);
	      }
	      
	   }
	   catch (Exception e)
		{
	      _logger.error(e);
	      throw e;
		}
   }
	
   private void init(int userConnected, int parentId)
	{
   	_userConnected = userConnected;
   	_id = Defs.NULL_ID;
   	_name = null;
   	_parentId = parentId;
   	_managerId = userConnected;
   	_type = Defs.DEPT_STANDARD;
   	_description = null;
   	_creatorId = _userConnected;
      _creationDate = DbDataType.NULL_DATE_TIME;
      _updaterId = DbDataType.NULL_LONG_INTEGER;
      _updateDate = DbDataType.NULL_DATE_TIME;
      _permsImpl = new GenericPermsImpl();
      _users = new Users();
      _adminUsers = new BasicUsersImpl();
   }
   
   private int _userConnected;
   private int _id;
   private String _name;
   private int _parentId;
   private int _managerId;
   private int _type;
   private String _description;
   private int _creatorId;
   private Date _creationDate;
   private int _updaterId;
   private Date _updateDate;
   private GenericPermsImpl _permsImpl;
   private Users _users;
   private BasicUsersImpl _adminUsers;
   
	private static final Logger _logger = Logger.getLogger(DepartmentImpl.class);
}
