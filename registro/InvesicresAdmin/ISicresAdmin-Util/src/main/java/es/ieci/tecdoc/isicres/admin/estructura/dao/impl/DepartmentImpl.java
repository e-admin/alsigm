package es.ieci.tecdoc.isicres.admin.estructura.dao.impl;

import java.util.Date;

import org.apache.log4j.Logger;

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
import es.ieci.tecdoc.isicres.admin.database.UsersTable;
import es.ieci.tecdoc.isicres.admin.estructura.dao.BasicUsers;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Department;
import es.ieci.tecdoc.isicres.admin.estructura.dao.GenericPerms;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Permission;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Permissions;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Users;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminDefsKeys;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminDepartamentoKeys;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminUserDefsKeys;
import es.ieci.tecdoc.isicres.admin.exception.ISicresAdminBasicException;
import es.ieci.tecdoc.isicres.admin.sbo.util.nextid.NextId;


public class DepartmentImpl implements Department
{
	public DepartmentImpl(int userConnected, int parentId)
   {
      init(userConnected, parentId);
   }

   public DepartmentImpl()
   {
      init(ISicresAdminDefsKeys.NULL_ID, ISicresAdminDefsKeys.NULL_ID);
   }

   public void load(String name, String entidad) throws Exception
   {
      loadByName(name,true, entidad);
   }

   public void load(int id, String entidad) throws Exception
   {
      load(id, true, entidad);
   }

   public void loadLite(int id, String entidad) throws Exception
   {
      load(id, false, entidad);
   }

   public void loadByName(String name, boolean full, String entidad) throws Exception
   {
      if (_logger.isDebugEnabled())
         _logger.debug("loadByName: Name = " + name);

		try
		{
         _name = name;

         loadBaseName(entidad);
         if (full)
         {
         	_permsImpl.loadPerms(_id, ISicresAdminDefsKeys.DESTINATION_DEPT, entidad);
         	loadAdminUsersId(entidad);
         }
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
         	_permsImpl.loadPerms(_id, ISicresAdminDefsKeys.DESTINATION_DEPT, entidad);
         	loadAdminUsersId(entidad);
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

		try
		{

			checkDeptExists(entidad);
			checkDescription();

         if (_id == ISicresAdminDefsKeys.NULL_ID)
         {
            _id = NextId.generateNextId(UsersTable.TN_NEXTID,
                                 ISicresAdminDefsKeys.DESTINATION_DEPT, entidad);
            _permsImpl.setDefaultPerms(_id, ISicresAdminDefsKeys.DESTINATION_DEPT);
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
      UsersTable table = new UsersTable();
      DepartmentsTable tableDept = new DepartmentsTable();

      if (_logger.isDebugEnabled())
         _logger.debug("delete");

        DbConnection dbConn=new DbConnection();
		try
		{
			dbConn.open(DBSessionManager.getSession());

         dbConn.beginTransaction();
         inTrans = true;
         DbDeleteFns.delete(dbConn, tableDept.getBaseTableName(),
         		tableDept.getDeleteBaseQual(_id));
         _permsImpl.deletePerms(_id, ISicresAdminDefsKeys.DESTINATION_DEPT, entidad);
         DbDeleteFns.delete(dbConn, table.getObjPermsTableName(),
                            table.getDeleteObjPermsQual(_id,
                            ISicresAdminDefsKeys.OBJECT_OWNER_TYPE_DEPT));
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
   public BasicUsers getAdminUsers(String entidad) throws Exception
   {
      if (_adminUsers.count() == 0)
      {
         try
   		{
   			loadAdminUsersId(entidad);
   		}
         catch(Exception e)
         {
            _logger.error(e);
            throw e;
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
   public Users getUsersByDept(int id, String entidad) throws Exception
	{
   	if (_logger.isDebugEnabled())
         _logger.debug("getUsersByDept");

		try
		{
         _id = id;
         //la conexión a base de datos está en la función
			_users.loadByDept(_id, entidad);

		}
		catch(Exception e)
		{
         _logger.error(e);
         throw e;
		}


   	return _users;
   }




   public GenericPerms get_permsImpl() {
		return _permsImpl;
	}

	public void set_permsImpl(GenericPerms impl) {
		_permsImpl = (GenericPermsImpl)impl;
	}

	public int get_userConnected() {
		return _userConnected;
	}

	public void set_userConnected(int connected) {
		_userConnected = connected;
	}

	public Users get_users() {
		return _users;
	}

	public void set_users(Users _users) {
		this._users = _users;
	}



	public BasicUsers get_adminUsers() {
		return _adminUsers;
	}

	public void set_adminUsers(BasicUsers users) {
		_adminUsers = (BasicUsersImpl)users;
	}

	public void set_creationDate(Date date) {
		_creationDate = date;
	}

	public void set_creatorId(int id) {
		_creatorId = id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public void set_type(int _type) {
		this._type = _type;
	}

	public void set_updateDate(Date date) {
		_updateDate = date;
	}

	public void set_updaterId(int id) {
		_updaterId = id;
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

      statement.setLongInteger(index++, ISicresAdminDefsKeys.SYSSUPERUSER_ID);
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
		statement.setLongInteger(index++, _type);
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
      _type = statement.getLongInteger(index ++);

      return new Integer(index);
   }

   /**
	 * Comprueba que el departamento tiene distinto nombre a los que ya existen.
	 *
	 * @throws Exception Si existe ya el nombre del departamento.
	 */
	private void checkDeptExists(String entidad) throws Exception
	{
		int count;
		DepartmentsTable table = new DepartmentsTable();


	    DbConnection dbConn=new DbConnection();
	    try{
	      dbConn.open(DBSessionManager.getSession());
	    	//dbConn.open("org.postgresql.Driver", "jdbc:postgresql://128.90.111.90/registro_000", "postgres", "postgres");

	      if (_id == ISicresAdminDefsKeys.NULL_ID)
				count = DbSelectFns.selectCount(dbConn, table.getBaseTableName(), table
						.getCountNameQual(_name));
			else
				count = DbSelectFns.selectCount(dbConn, table.getBaseTableName(), table
						.getCountNameIdQual(_id, _name));
			if (count > 0)
				ISicresAdminBasicException.throwException(ISicresAdminDepartamentoKeys.EC_DEPT_EXIST_NAME);
	      }catch (Exception e) {
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
		if (_description != null) {
			index = _description.indexOf("\"");
			if (index > -1)
				ISicresAdminBasicException
				.throwException(ISicresAdminDepartamentoKeys.EC_DEPT_DESC_EXIST_QUOTES);
		}
	}

	/**
    * Actualiza todos los objetos de los que es propietario el departamento.
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

      DbConnection dbConn=new DbConnection();
      try{
      	  dbConn.open(DBSessionManager.getSession());
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

	      DynamicFns.update(dbConn, table.getUpdateOwnerQual(_id,
	                        ISicresAdminDefsKeys.OBJECT_OWNER_TYPE_DEPT), tableInfo, rowsInfo);
      }catch (Exception e){
    		_logger.error(e);
    		throw e;
    	}finally{
    		dbConn.close();
    	}

   }

   /**
    * Inserta un nuevo departamento en invesDoc.
    *
    * @throws Exception Si se produce algún error en la inserción del departamento.
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
    * Actualiza un departamento de invesDoc.
    *
    * @throws Exception Si se produce algún error en la actualización del departamento.
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
    	  //dbConn.open("org.postgresql.Driver", "jdbc:postgresql://128.90.111.90/registro_000", "postgres", "postgres");

         dbConn.beginTransaction();
         inTrans = true;

         updateBase(entidad);
         _permsImpl.updatePerms(_id, ISicresAdminDefsKeys.DESTINATION_DEPT, entidad);
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
         if (dbConn != null && dbConn.existConnection())
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
      DepartmentsTable table = new DepartmentsTable();

      if (_logger.isDebugEnabled())
         _logger.debug("loadBase");

      DbConnection dbConn=new DbConnection();
      try{
      	  dbConn.open(DBSessionManager.getSession());
    	  //dbConn.open("org.postgresql.Driver", "jdbc:postgresql://128.90.111.90/registro_000", "postgres", "postgres");

         tableInfo.setTableObject(table);
         tableInfo.setClassName(DepartmentsTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getLoadBaseColumnNames");

         rowInfo.addRow(this);
         rowInfo.setClassName(DepartmentImpl.class.getName());
         rowInfo.setValuesMethod("loadValues");
         rowsInfo.add(rowInfo);

         if (!DynamicFns.select(dbConn,table.getLoadBaseQual(_id), tableInfo, rowsInfo))
         {
            ISicresAdminBasicException.throwException(ISicresAdminDepartamentoKeys.EC_DEPT_NOT_EXITS);
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
      DepartmentsTable table = new DepartmentsTable();

      if (_logger.isDebugEnabled())
         _logger.debug("loadBaseName");

      DbConnection dbConn=new DbConnection();
      try {
    	  dbConn.open(DBSessionManager.getSession());
         tableInfo.setTableObject(table);
         tableInfo.setClassName(DepartmentsTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getLoadBaseColumnNames");

         rowInfo.addRow(this);
         rowInfo.setClassName(DepartmentImpl.class.getName());
         rowInfo.setValuesMethod("loadValues");
         rowsInfo.add(rowInfo);

         if (!DynamicFns.select(dbConn, table.getCountNameQual(_name), tableInfo, rowsInfo))
         {
            ISicresAdminBasicException.throwException(ISicresAdminDepartamentoKeys.EC_DEPT_NOT_EXITS);
         }

      }
      catch (Exception e){
         _logger.error(e);
         throw e;
      }finally{
    	  dbConn.close();
      }
   }

   /**
    * Inserta la parte base de las tablas de departamento (header).
    *
    * @throws Exception Si se produce algún error en la inserción del departamento.
    */

   private void insertBase(String entidad) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      DepartmentsTable table = new DepartmentsTable();

      if (_logger.isDebugEnabled())
         _logger.debug("insertBase");

      DbConnection dbConn=new DbConnection();
      try{
      	 dbConn.open(DBSessionManager.getSession());
         tableInfo.setTableObject(table);
         tableInfo.setClassName(DepartmentsTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getInsertBaseColumnNames");

         rowInfo.addRow(this);
         rowInfo.setClassName(DepartmentImpl.class.getName());
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
    * Actualiza la parte base de las tablas de grupos (header).
    *
    * @throws Exception Si se produce algún error en la actualización del grupo.
    */
   private void updateBase(String entidad) throws Exception
	{
   	DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      DepartmentsTable table = new DepartmentsTable();

      if (_logger.isDebugEnabled())
			_logger.debug("updateBase");

      DbConnection dbConn=new DbConnection();
      try{
      	 dbConn.open(DBSessionManager.getSession());
    	  //dbConn.open("org.postgresql.Driver", "jdbc:postgresql://128.90.111.90/registro_000", "postgres", "postgres");

         tableInfo.setTableObject(table);
         tableInfo.setClassName(DepartmentsTable.class.getName());
         tableInfo.setTablesMethod("getBaseTableName");
         tableInfo.setColumnsMethod("getUpdateBaseColumnNames");

         rowInfo.addRow(this);
         rowInfo.setClassName(DepartmentImpl.class.getName());
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
    * Elimina los permisos de un departamento sobre todos los objetos.
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
    	  //dbConn.open("org.postgresql.Driver", "jdbc:postgresql://128.90.111.90/registro_000", "postgres", "postgres");

	      // Comprobamos si para el productId se tiene el permiso o no.
	      permis = _permsImpl._perms.getProductPermission(productId);
	      if ((permis.getPermission() & perm) == 0)
	      {
	         DbDeleteFns.delete(dbConn, table.getObjPermsTableName(),
	                  table.getDeleteObjPermQual(_id, ISicresAdminDefsKeys.DESTINATION_DEPT, perm,
	                  productId));
	      }
      }catch (Exception e) {
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
    * Obtiene los identificadores de los usuarios suceptibles de ser
    * administradores del departamento.
    * @throws Exception Errores
    */
   private void loadAdminUsersId(String entidad) throws Exception
   {
      DynamicTable tableInfo = new DynamicTable();
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      UsersTable     usrTbl = new UsersTable();
      int            counter;
      BasicUserImpl  user;
      String         qual;

      //    Cargamos posibles usuarios administradores

      DbConnection dbConn=new DbConnection();
      try{
      	dbConn.open(DBSessionManager.getSession());
    	  //dbConn.open("org.postgresql.Driver", "jdbc:postgresql://128.90.111.90/registro_000", "postgres", "postgres");

         tableInfo.setTableObject(usrTbl);
         tableInfo.setClassName(UsersTable.class.getName());
         qual = usrTbl.getLoadAminUsersQual(ISicresAdminUserDefsKeys.PRODUCT_USER);

	      tableInfo.setTablesMethod("getUserAdminTableNames");
	      tableInfo.setColumnsMethod("getAdminUserColumnNames");

	      rowInfo = new DynamicRow();
	      rowsInfo = new DynamicRows();
	      rowInfo.setClassName(BasicUserImpl.class.getName());
	      rowInfo.setValuesMethod("loadValues");
	      rowsInfo.add(rowInfo);

	      DynamicFns.selectMultiple(dbConn, qual, true, tableInfo, rowsInfo);

	      for (counter = 0; counter < rowInfo.getRowCount(); counter++)
	      {
	         user = (BasicUserImpl)rowInfo.getRow(counter);
	         _adminUsers.add(user);
	      }

      }catch (Exception e){
    		_logger.error(e);
    		throw e;
    	}finally{
    		dbConn.close();
    	}
   }

   private void init(int userConnected, int parentId)
	{
   	_userConnected = userConnected;
   	_id = ISicresAdminDefsKeys.NULL_ID;
   	_name = null;
   	_parentId = parentId;
   	_managerId = userConnected;
   	_type = ISicresAdminDefsKeys.DEPT_STANDARD;
   	_description = null;
   	_creatorId = _userConnected;
      _creationDate = DbDataType.NULL_DATE_TIME;
      _updaterId = DbDataType.NULL_LONG_INTEGER;
      _updateDate = DbDataType.NULL_DATE_TIME;
      _permsImpl = new GenericPermsImpl();
      _users = new UsersImpl();
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
