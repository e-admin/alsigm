package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.core.datetime.DatePattern;
import ieci.tecdoc.core.datetime.DateTimeUtil;
import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.core.xml.lite.XmlTextBuilder;
import ieci.tecdoc.idoc.admin.api.exception.AdminException;
import ieci.tecdoc.idoc.admin.api.exception.GroupErrorCodes;
import ieci.tecdoc.idoc.admin.api.user.BasicUsers;
import ieci.tecdoc.idoc.admin.api.user.Group;
import ieci.tecdoc.idoc.admin.api.user.GroupUserRel;
import ieci.tecdoc.idoc.admin.api.user.Permission;
import ieci.tecdoc.idoc.admin.api.user.Permissions;
import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.idoc.admin.api.user.Users;
import ieci.tecdoc.idoc.admin.database.GroupsTable;
import ieci.tecdoc.idoc.admin.database.UsersTable;
import ieci.tecdoc.sbo.util.nextid.NextId;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDataType;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DbSelectFns;
import ieci.tecdoc.sgm.base.dbex.DbUtil;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;

import java.util.Date;

import org.apache.log4j.Logger;

public class GroupImpl implements Group {
	public GroupImpl(int userConnected) {
		init(userConnected);
	}

	public GroupImpl() {
		init(Defs.NULL_ID);
	}

	public void load(int id, String entidad) throws Exception {
		load(id, true, entidad);
	}

	public void load(String name, String entidad) throws Exception {
		loadByName(name, true, entidad);
	}

	public void load(int id, boolean full, String entidad) throws Exception {
		if (_logger.isDebugEnabled())
			_logger.debug("load: Id = " + Integer.toString(id));

		try {
			_id = id;

			loadBase(entidad);
			if (full) {
				_permsImpl.loadPerms(_id, Defs.DESTINATION_GROUP, entidad);
				loadAdminUsersId(entidad);
			}
		} catch (Exception e) {
			_logger.error(e);
			throw e;
		}
	}

	public void loadByName(String name, boolean full, String entidad)
			throws Exception {
		if (_logger.isDebugEnabled())
			_logger.debug("loadByName: Name = " + name);

		try {
			_name = name;

			loadBaseName(entidad);
			if (full) {
				_permsImpl.loadPerms(_id, Defs.DESTINATION_GROUP, entidad);
				loadAdminUsersId(entidad);
			}
		} catch (Exception e) {
			_logger.error(e);
			throw e;
		}
	}

	public void store(String entidad) throws Exception {
		if (_logger.isDebugEnabled())
			_logger.debug("store");

		try {

			checkGroupExists(entidad);
			checkDescription();

			if (_id == Defs.NULL_ID) {
				_id = NextId.generateNextId(UsersTable.TN_NEXTID,
						Defs.DESTINATION_GROUP, entidad);
				_permsImpl.setDefaultPerms(_id, Defs.DESTINATION_GROUP);
				insert(entidad);
			} else {
				update(entidad);
			}

		} catch (Exception e) {
			_logger.error(e);
			throw e;
		}
	}

	public void delete(String entidad) throws Exception {
		boolean commit = false;
		boolean inTrans = false;
		UsersTable table = new UsersTable();
		GroupsTable tableGrp = new GroupsTable();

		if (_logger.isDebugEnabled())
			_logger.debug("delete");

		DbConnection dbConn = new DbConnection();
		try {
			dbConn.open(DBSessionManager.getSession(entidad));

			dbConn.beginTransaction();
			inTrans = true;
			DbDeleteFns.delete(dbConn, tableGrp.getBaseTableName(),
					tableGrp.getDeleteBaseQual(_id));
			_permsImpl.deletePerms(_id, Defs.DESTINATION_GROUP, entidad);
			DbDeleteFns.delete(dbConn, table.getObjPermsTableName(), table
					.getDeleteObjPermsQual(_id, Defs.OBJECT_OWNER_TYPE_GROUP));
			updateAllOwnership(entidad);

			/*
			Al eliminar el grupo hay que eliminar tambien las 
			asociaciones entre usuarios-grupo
			*/
			GroupUserRel grpUsrRel = new GrpUsrRelImpl();
			grpUsrRel.deleteGroup(_id, entidad);
			commit = true;
		} catch (Exception e) {
			_logger.error(e);
			throw e;
		} finally {
			if (inTrans)
				dbConn.endTransaction(commit);

			dbConn.close();
		}

	}

	/**
	 * Obtiene el identificador de grupo.
	 * 
	 * @return El identificador mencionado.
	 */
	public int getId() {
		return _id;
	}

	/**
	 * Obtiene el nombre del grupo.
	 * 
	 * @return El nombre mencionado.
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Establece el nombre del grupo.
	 * 
	 * @param name
	 *            Nombre del grupo.
	 */
	public void setName(String name) {
		_name = name;
	}

	/**
	 * Obtiene el identificador del administrador del grupo.
	 * 
	 * @return El identificador mencionado.
	 */
	public int getManagerId() {
		return _managerId;
	}

	/**
	 * Establece el identificador del administrador del grupo.
	 * 
	 * @param managerId
	 *            El identificador del administrador.
	 */
	public void setManagerId(int managerId) {
		_managerId = managerId;
	}

	/**
	 * Obtiene el identificador del tipo de grupo.
	 * 
	 * @return El identificador mencionado.
	 */
	public int getType() {
		return _type;
	}

	/**
	 * Obtiene la descripción del grupo.
	 * 
	 * @return El nombre mencionado.
	 */
	public String getDescription() {
		return _description;
	}

	/**
	 * Establece la descripción del grupo.
	 * 
	 * @param description
	 *            La descripción del grupo.
	 */
	public void setDescription(String description) {
		_description = description;
	}

	/**
	 * Obtiene el identificador del usuario que ha creado el grupo.
	 * 
	 * @return El identificador mencionado.
	 */
	public int getCreatorId() {
		return _creatorId;
	}

	/**
	 * Obtiene la fecha de creación del grupo.
	 * 
	 * @return La fecha mencionada.
	 */
	public Date getCreationDate() {
		return _creationDate;
	}

	/**
	 * Obtiene el identificador del usuario que ha actualizado el grupo.
	 * 
	 * @return El identificador mencionado.
	 */
	public int getUpdaterId() {
		return _updaterId;
	}

	/**
	 * Obtiene la fecha de actualización del grupo.
	 * 
	 * @return La fecha mencionada.
	 */
	public Date getUpdateDate() {
		return _updateDate;
	}

	public BasicUsersImpl get_adminUsers() {
		return _adminUsers;
	}

	public int get_userConnected() {
		return _userConnected;
	}

	public Users get_users() {
		return _users;
	}

	public GenericPermsImpl get_permsImpl() {
		return _permsImpl;
	}

	/**
	 * Devuelve lista de usuarios suceptibles de ser administradores
	 * 
	 * @return la lista
	 */
	public BasicUsers getAdminUsers(String entidad) throws Exception {
		if (_logger.isDebugEnabled())
			_logger.debug("getAdminUsers");

		if (_adminUsers.count() == 0) {
			try {
				loadAdminUsersId(entidad);
			} catch (Exception e) {
				_logger.error(e);
				throw e;
			}
		}

		return _adminUsers;
	}

	/**
	 * Devuelve la lista de permisos del grupo.
	 * 
	 * @return La lista mencionada.
	 */

	public Permissions getPermissions() {
		return _permsImpl.getPermissions();
	}

	/**
	 * Devuelve la lista de usuarios del grupo.
	 * 
	 * @param id
	 *            Identificador del grupo.
	 * @return Los usuarios del grupo.
	 */
	public Users getUsersByGroup(int id, String entidad) throws Exception {
		if (_logger.isDebugEnabled())
			_logger.debug("getUsersByGroup");

		try {
			_id = id;
			// la conexión a base de datos está en la función
			_users.loadByGroup(_id, entidad);

		} catch (Exception e) {
			_logger.error(e);
			throw e;
		}

		return _users;
	}

	/**
	 * Obtiene la información del grupo en formato XML.
	 * 
	 * @param header
	 *            Indica si hay que incluir la cabecera xml o no.
	 * @return La información mencionada.
	 */

	public String toXML(boolean header) {
		String text;
		XmlTextBuilder bdr;
		String tagName = "Group";

		bdr = new XmlTextBuilder();
		if (header)
			bdr.setStandardHeader();

		bdr.addOpeningTag(tagName);

		bdr.addSimpleElement("Id", Integer.toString(_id));
		bdr.addSimpleElement("Name", _name);
		bdr.addSimpleElement("ManagerId", Integer.toString(_managerId));
		bdr.addSimpleElement("Type", Integer.toString(_type));
		bdr.addSimpleElement("Description", _description);
		bdr.addSimpleElement("CreatorId", Integer.toString(_creatorId));
		bdr.addSimpleElement("CreationDate", DateTimeUtil.getDateTime(
				_creationDate, DatePattern.XML_TIMESTAMP_PATTERN));
		if (DbDataType.isNullLongInteger(_updaterId)) {
			text = "";
		} else {
			text = Integer.toString(_updaterId);
		}
		bdr.addSimpleElement("UpdaterId", text);
		if (DbDataType.isNullDateTime(_updateDate)) {
			text = "";
		} else {
			text = DateTimeUtil.getDateTime(_updateDate,
					DatePattern.XML_TIMESTAMP_PATTERN);
		}

		bdr.addSimpleElement("UpdateDate", text);

		bdr.addFragment(_permsImpl._perms.toXML(false));

		bdr.addClosingTag(tagName);

		return bdr.getText();
	}

	/**
	 * Obtiene la información del grupo en formato XML.
	 * 
	 */

	public String toXML() {
		return toXML(true);
	}

	/**
	 * Muestra una representación de la clase en formato texto.
	 * 
	 */

	public String toString() {
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
			throws Exception {
		int index = idx.intValue();

		if (_logger.isDebugEnabled())
			_logger.debug("insertValues");

		statement.setLongInteger(index++, _id);
		statement.setShortText(index++, _name);
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
			throws Exception {
		int index = idx.intValue();

		if (_logger.isDebugEnabled())
			_logger.debug("updateValues");

		statement.setShortText(index++, _name);
		statement.setLongInteger(index++, _managerId);
		statement.setShortText(index++, _description);
		_updaterId = _userConnected;
		statement.setLongInteger(index++, _updaterId);
		_updateDate = DbUtil.getCurrentDateTime();
		statement.setDateTime(index++, _updateDate);

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
			throws Exception {
		int index = idx.intValue();

		if (_logger.isDebugEnabled())
			_logger.debug("loadValues");

		_id = statement.getLongInteger(index++);
		_name = statement.getShortText(index++);
		_managerId = statement.getLongInteger(index++);
		_description = statement.getShortText(index++);
		_creatorId = statement.getLongInteger(index++);
		_creationDate = statement.getDateTime(index++);
		_updaterId = statement.getLongInteger(index++);
		_updateDate = statement.getDateTime(index++);

		return new Integer(index);
	}

	/**
	 * Recupera el identificador y el nombre del grupo.
	 * 
	 * @param statement
	 * @param idx
	 * @return
	 * @throws java.lang.Exception
	 */
	public Integer loadIdNameValues(DbOutputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();

		if (_logger.isDebugEnabled())
			_logger.debug("loadIdNameValues");

		_id = statement.getLongInteger(index++);
		_name = statement.getShortText(index++);

		return new Integer(index);
	}

	/**
	 * Actualiza en base de datos la información relativa a objetos propiedad
	 * del grupo.
	 * 
	 * @param statement
	 * @param idx
	 * @return
	 * @throws java.lang.Exception
	 */

	public Integer updateOwnershipValues(DbInputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();

		if (_logger.isDebugEnabled())
			_logger.debug("updateOwnershipValues");

		statement.setLongInteger(index++, Defs.SYSSUPERUSER_ID);
		statement.setLongInteger(index++, _userConnected);
		statement.setDateTime(index++, DbUtil.getCurrentDateTime());

		return new Integer(index);
	}

	/**
	 * Comprueba que el grupo tiene distinto nombre a los que ya existen.
	 * 
	 * @throws Exception
	 *             Si existe ya el nombre del grupo.
	 */
	private void checkGroupExists(String entidad) throws Exception {
		int count;
		GroupsTable table = new GroupsTable();

		DbConnection dbConn = new DbConnection();
		try {
			dbConn.open(DBSessionManager.getSession(entidad));
			if (_id == Defs.NULL_ID)
				count = DbSelectFns
						.selectCount(dbConn, table.getBaseTableName(),
								table.getCountNameQual(_name));
			else
				count = DbSelectFns.selectCount(dbConn,
						table.getBaseTableName(),
						table.getCountNameIdQual(_id, _name));
			if (count > 0)
				AdminException
						.throwException(GroupErrorCodes.EC_GROUP_EXIST_NAME);
		} catch (Exception e) {
			_logger.error(e);
			throw e;
		} finally {
			dbConn.close();
		}
	}

	/**
	 * Comprueba si el texto del campo Descripción tiene comillas dobles
	 * 
	 * @throws Exception
	 *             Si el campo Descripción tiene comillas dobles.
	 */
	private void checkDescription() throws Exception {
		int index;
		if (_description != null) {
			index = _description.indexOf("\"");
			if (index > -1)
				AdminException
						.throwException(GroupErrorCodes.EC_GROUP_DESC_EXIST_QUOTES);
		}
	}

	/**
	 * Lee la información básica del usuario invesDoc.
	 * 
	 * @throws Exception
	 *             Si se produce algún error en la lectura de la información
	 *             mencionada.
	 */

	private void loadBase(String entidad) throws Exception {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		GroupsTable table = new GroupsTable();

		if (_logger.isDebugEnabled())
			_logger.debug("loadBase");

		DbConnection dbConn = new DbConnection();
		try {
			dbConn.open(DBSessionManager.getSession(entidad));
			tableInfo.setTableObject(table);
			tableInfo.setClassName(GroupsTable.class.getName());
			tableInfo.setTablesMethod("getBaseTableName");
			tableInfo.setColumnsMethod("getLoadBaseColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(GroupImpl.class.getName());
			rowInfo.setValuesMethod("loadValues");
			rowsInfo.add(rowInfo);

			if (!DynamicFns.select(dbConn, table.getLoadBaseQual(_id),
					tableInfo, rowsInfo)) {
				AdminException
						.throwException(GroupErrorCodes.EC_GROUP_NOT_EXITS);
			}

		} catch (Exception e) {
			_logger.error(e);
			throw e;
		} finally {
			dbConn.close();
		}
	}

	/**
	 * Lee la información básica del usuario invesDoc.
	 * 
	 * @throws Exception
	 *             Si se produce algún error en la lectura de la información
	 *             mencionada.
	 */

	private void loadBaseName(String entidad) throws Exception {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		GroupsTable table = new GroupsTable();

		if (_logger.isDebugEnabled())
			_logger.debug("loadBaseName");

		DbConnection dbConn = new DbConnection();
		try {
			dbConn.open(DBSessionManager.getSession(entidad));
			tableInfo.setTableObject(table);
			tableInfo.setClassName(GroupsTable.class.getName());
			tableInfo.setTablesMethod("getBaseTableName");
			tableInfo.setColumnsMethod("getLoadBaseColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(GroupImpl.class.getName());
			rowInfo.setValuesMethod("loadValues");
			rowsInfo.add(rowInfo);

			if (!DynamicFns.select(dbConn, table.getCountNameQual(_name),
					tableInfo, rowsInfo)) {
				AdminException
						.throwException(GroupErrorCodes.EC_GROUP_NOT_EXITS);
			}

		} catch (Exception e) {
			_logger.error(e);
			throw e;
		} finally {
			dbConn.close();
		}
	}

	/**
	 * Inserta un nuevo grupo en invesDoc.
	 * 
	 * @throws Exception
	 *             Si se produce algún error en la inserción del grupo.
	 */

	private void insert(String entidad) throws Exception {
		boolean commit = false;
		boolean inTrans = false;
		int counter;

		if (_logger.isDebugEnabled())
			_logger.debug("insert");

		DbConnection dbConn = new DbConnection();
		try {
			dbConn.open(DBSessionManager.getSession(entidad));
			dbConn.beginTransaction();
			inTrans = true;

			insertBase(entidad);
			_permsImpl.insertPerms(entidad);

			commit = true;
		} catch (Exception e) {
			_logger.error(e);
			throw e;
		} finally {
			if (inTrans)
				dbConn.endTransaction(commit);
			dbConn.close();
		}

	}

	/**
	 * Actualiza un grupo de invesDoc.
	 * 
	 * @throws Exception
	 *             Si se produce algún error en la actualización del grupo.
	 */

	private void update(String entidad) throws Exception {
		boolean commit = false;
		boolean inTrans = false;
		int counter;

		if (_logger.isDebugEnabled())
			_logger.debug("update");

		DbConnection dbConn = new DbConnection();
		try {
			dbConn.open(DBSessionManager.getSession(entidad));
			dbConn.beginTransaction();
			inTrans = true;

			updateBase(entidad);
			_permsImpl.updatePerms(_id, Defs.DESTINATION_GROUP, entidad);
			deleteObjPerms(entidad);

			commit = true;
		} catch (Exception e) {
			_logger.error(e);
			throw e;
		} finally {
			if (inTrans)
				dbConn.endTransaction(commit);
			dbConn.close();
		}
	}

	/**
	 * Inserta la parte base de las tablas de grupo (header).
	 * 
	 * @throws Exception
	 *             Si se produce algún error en la inserción del grupo.
	 */

	private void insertBase(String entidad) throws Exception {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		GroupsTable table = new GroupsTable();

		if (_logger.isDebugEnabled())
			_logger.debug("insertBase");

		DbConnection dbConn = new DbConnection();
		try {
			dbConn.open(DBSessionManager.getSession(entidad));
			tableInfo.setTableObject(table);
			tableInfo.setClassName(GroupsTable.class.getName());
			tableInfo.setTablesMethod("getBaseTableName");
			tableInfo.setColumnsMethod("getInsertBaseColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(GroupImpl.class.getName());
			rowInfo.setValuesMethod("insertValues");
			rowsInfo.add(rowInfo);

			DynamicFns.insert(dbConn, tableInfo, rowsInfo);

		} catch (Exception e) {
			_logger.error(e);
			throw e;
		} finally {
			dbConn.close();
		}
	}

	/**
	 * Actualiza la parte base de las tablas de grupos (header).
	 * 
	 * @throws Exception
	 *             Si se produce algún error en la actualización del grupo.
	 */
	private void updateBase(String entidad) throws Exception {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		GroupsTable table = new GroupsTable();

		if (_logger.isDebugEnabled())
			_logger.debug("update");

		DbConnection dbConn = new DbConnection();
		try {
			dbConn.open(DBSessionManager.getSession(entidad));
			tableInfo.setTableObject(table);
			tableInfo.setClassName(GroupsTable.class.getName());
			tableInfo.setTablesMethod("getBaseTableName");
			tableInfo.setColumnsMethod("getUpdateBaseColumnNames");

			rowInfo.addRow(this);
			rowInfo.setClassName(GroupImpl.class.getName());
			rowInfo.setValuesMethod("updateValues");
			rowsInfo.add(rowInfo);

			DynamicFns.update(dbConn, table.getLoadBaseQual(_id), tableInfo,
					rowsInfo);

		} catch (Exception e) {
			_logger.error(e);
			throw e;
		} finally {
			dbConn.close();
		}
	}

	/**
	 * Actualiza todos los objetos de los que es propietario el grupo.
	 * 
	 * @throws Exception
	 *             Si se produce algún error en la actualización.
	 */

	private void updateAllOwnership(String entidad) throws Exception {
		UsersTable table = new UsersTable();
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo;
		DynamicRow rowInfo;

		if (_logger.isDebugEnabled())
			_logger.debug("updateAllOwnership");

		DbConnection dbConn = new DbConnection();
		try {
			dbConn.open(DBSessionManager.getSession(entidad));
			tableInfo.setTableObject(table);
			tableInfo.setClassName(UsersTable.class.getName());
			tableInfo.setTablesMethod("getOwnershipTableName");
			tableInfo.setColumnsMethod("getUpdateOwnershipColumnNames");

			// Propietario de directorio
			rowInfo = new DynamicRow();
			rowsInfo = new DynamicRows();

			rowInfo.addRow(this);
			rowInfo.setClassName(GroupImpl.class.getName());
			rowInfo.setValuesMethod("updateOwnershipValues");
			rowsInfo.add(rowInfo);

			DynamicFns
					.update(dbConn, table.getUpdateOwnerQual(_id,
							Defs.OBJECT_OWNER_TYPE_GROUP), tableInfo, rowsInfo);
		} catch (Exception e) {
			_logger.error(e);
			throw e;
		} finally {
			dbConn.close();
		}

	}

	/**
	 * Elimina los permisos de un grupo sobre todos los objetos.
	 * 
	 * @param perm
	 *            Tipo de permiso a eliminar.
	 * @param productId
	 *            Identificador del producto.
	 * @throws Exception
	 *             Si se produce algún error en la actualización.
	 */

	private void deleteObjPerm(int perm, int productId, String entidad)
			throws Exception {
		UsersTable table = new UsersTable();
		Permission permis;

		DbConnection dbConn = new DbConnection();
		try {
			dbConn.open(DBSessionManager.getSession(entidad));
			// Comprobamos si para el productId se tiene el permiso o no.
			permis = _permsImpl._perms.getProductPermission(productId);
			if ((permis.getPermission() & perm) == 0) {
				DbDeleteFns.delete(dbConn, table.getObjPermsTableName(), table
						.getDeleteObjPermQual(_id, Defs.DESTINATION_GROUP,
								perm, productId));
			}

		} catch (Exception e) {
			_logger.error(e);
			throw e;
		} finally {
			dbConn.close();
		}
	}

	/**
	 * Elimina todos los permisos del usuario sobre los objetos invesDoc.
	 * 
	 * @throws Exception
	 *             Si se produce algún error en la eliminación.
	 */

	private void deleteObjPerms(String entidad) throws Exception {
		if (_logger.isDebugEnabled())
			_logger.debug("deleteObjPerms");

		deleteObjPerm(UserDefs.PERMISSION_PRINTING, UserDefs.PRODUCT_IDOC,
				entidad);
		deleteObjPerm(UserDefs.PERMISSION_DELETION, UserDefs.PRODUCT_IDOC,
				entidad);
		deleteObjPerm(UserDefs.PERMISSION_CREATION, UserDefs.PRODUCT_IDOC,
				entidad);
		deleteObjPerm(UserDefs.PERMISSION_UPDATE, UserDefs.PRODUCT_IDOC,
				entidad);
		deleteObjPerm(UserDefs.PERMISSION_QUERY, UserDefs.PRODUCT_IDOC, entidad);
	}

	/**
	 * Obtiene los identificadores de los usuarios suceptibles de ser
	 * administradores del grupo.
	 * 
	 * @throws Exception
	 *             Errores
	 */
	private void loadAdminUsersId(String entidad) throws Exception {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		UsersTable usrTbl = new UsersTable();
		int counter;
		BasicUserImpl user;
		String qual;

		// Cargamos posibles usuarios administradores

		DbConnection dbConn = new DbConnection();
		try {
			dbConn.open(DBSessionManager.getSession(entidad));

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

			DynamicFns.selectMultiple(dbConn, qual, true, tableInfo, rowsInfo);

			for (counter = 0; counter < rowInfo.getRowCount(); counter++) {
				user = (BasicUserImpl) rowInfo.getRow(counter);
				_adminUsers.add(user);
			}

		} catch (Exception e) {
			_logger.error(e);
			throw e;
		} finally {
			dbConn.close();
		}
	}

	private void init(int userConnected) {
		_userConnected = userConnected;
		_id = Defs.NULL_ID;
		_name = null;
		_managerId = userConnected;
		_type = Defs.GROUP_STANDARD;
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

	private static final Logger _logger = Logger.getLogger(GroupImpl.class);
}
