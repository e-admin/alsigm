package ieci.tecdoc.sgm.usuarios_backoffice.datatype;

import ieci.tecdoc.idoc.admin.api.exception.AdminException;
import ieci.tecdoc.idoc.admin.api.user.Permission;
import ieci.tecdoc.idoc.admin.api.user.Permissions;
import ieci.tecdoc.idoc.admin.api.user.UserProfile;
import ieci.tecdoc.idoc.admin.api.user.UserProfiles;
import ieci.tecdoc.sbo.uas.std.UasMisc;
import ieci.tecdoc.sbo.util.nextid.NextId;
import ieci.tecdoc.sgm.base.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;
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
import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;
import ieci.tecdoc.sgm.usuarios_backoffice.database.DBSessionManager;
import ieci.tecdoc.sgm.usuarios_backoffice.database.DepartmentsTable;
import ieci.tecdoc.sgm.usuarios_backoffice.database.GroupsTable;
import ieci.tecdoc.sgm.usuarios_backoffice.database.UsersTable;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;


public class UserImpl {

	private int _userConnected = 0;
	private int _id = DbDataType.NULL_LONG_INTEGER;
	private String _name = null;
	private String _password = null;
	private String _oldPassword = null;
	private boolean _isChange = false;
	private String _pwdmbc = "N";
	private String _pwdvpcheck = "N";
	private String _description = null;
	private int _deptId = 0;
	private int _state = 0;
//	private GenericProfilesImpl _profilesImpl = new GenericProfilesImpl();
//	private GenericPermsImpl _permsImpl = new GenericPermsImpl();
	private boolean _wasAdmin = false;
	private int _creatorId = 0;
	private Date _creationDate = DbDataType.NULL_DATE_TIME;
	private int _updaterId = 0;
	private Date _updateDate = DbDataType.NULL_DATE_TIME;
	private long _pwdLastUpdTs = 0L;
	private int _pwdminlen = 0;
	private static final Logger _logger = Logger.getLogger(UserImpl.class);

	public UserImpl() {
	}

	public void load(String entidad, int id) throws Exception {
		if (_logger.isDebugEnabled())
			_logger.debug("load: Id = " + Integer.toString(id));
		
		DbConnection dbConn = new DbConnection();
		
		try {
			_id = id;
			dbConn.open(DBSessionManager.getSession(entidad));
			loadBase(dbConn);
//			_profilesImpl.loadProfiles(_id);
//			_permsImpl.loadPerms(_id, 1);
		} catch (Exception e) {
			_logger.error(e);
			throw e;
		} finally {
			dbConn.close();
		}
		return;
	}

	public void store(String entidad) throws Exception {
		if (_logger.isDebugEnabled())
			_logger.debug("store");
		
		DbConnection dbConn = new DbConnection();
		
		try {
			dbConn.open(DBSessionManager.getSession(entidad));
			checkUserExists(dbConn);
			checkDescription();
			if (_id == DbDataType.NULL_LONG_INTEGER) {
				checkPassword(dbConn);
				_id = NextId.generateNextId(UsersTable.TN_NEXTID, 1, entidad);
//				_profilesImpl.setDefaultProfiles(_id);
//				_permsImpl.setDefaultPerms(_id, 1);
				insert(dbConn);
			} else {
				_isChange = changePassword(dbConn);
				update(dbConn);
			}
		} catch (Exception e) {
			_logger.error(e);
			throw e;
		} finally {
			dbConn.close();
		}
		return;
	}

	public void delete(String entidad) throws Exception {
		boolean commit = false;
		boolean inTrans = false;
		UsersTable table = new UsersTable();
		if (_logger.isDebugEnabled())
			_logger.debug("delete");
		if (_id == _userConnected)
			AdminException.throwException(0x2dcaafL);
		
		DbConnection dbConn = new DbConnection();
		
		try {
			dbConn.open(DBSessionManager.getSession(entidad));
			dbConn.beginTransaction();
			inTrans = true;
			DbDeleteFns.delete(dbConn, table.getBaseTableName(), table.getDeleteBaseQual(_id));
//			_profilesImpl.deleteProfiles(_id);
//			_permsImpl.deletePerms(_id, 1);
			DbDeleteFns.delete(dbConn, table.getObjPermsTableName(), table.getDeleteObjPermsQual(_id, 1));
			deleteGroupUserRel(dbConn);
			updateDeptsMgr(dbConn);
			updateGroupsMgr(dbConn);
//			updateAllOwnership(dbConn);
			commit = true;
		} catch (Exception e) {
			_logger.error(e);
			throw e;
		} finally {
			if (inTrans)
				dbConn.endTransaction(commit);
			dbConn.close();
		}
		return;
	}

	public int getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setPassword(String password) {
		_password = password;
	}

	public boolean getPwdmbc() {
		return _pwdmbc.equals("Y");
	}

	public void setPwdmbc(boolean pwdmbc) {
		if (pwdmbc)
			_pwdmbc = "Y";
		else
			_pwdmbc = "N";
	}

	public boolean getPwdvpcheck() {
		return _pwdvpcheck.equals("Y");
	}

	public void setPwdvpcheck(boolean pwdvpcheck) {
		if (pwdvpcheck)
			_pwdvpcheck = "Y";
		else
			_pwdvpcheck = "N";
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public int getDeptId() {
		return _deptId;
	}

	public void setDeptId(int deptId) {
		_deptId = deptId;
	}

	public int getState() {
		return _state;
	}

	public void setState(int state) {
		_state = state;
	}

//	public UserProfiles getProfiles() {
//		return _profilesImpl._profiles;
//	}
//
//	public Permissions getPermissions() {
//		return _permsImpl._perms;
//	}

	public int getCreatorId() {
		return _creatorId;
	}

	public Date getCreationDate() {
		return _creationDate;
	}

	public int getUpdaterId() {
		return _updaterId;
	}

	public Date getUpdateDate() {
		return _updateDate;
	}

	public double getPwdLastUpdTs() {
		return (double) _pwdLastUpdTs;
	}

	public String toXML(boolean header) {
		String tagName = "User";
		XmlTextBuilder bdr = new XmlTextBuilder();
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
		bdr.addSimpleElement("CreationDate", DateTimeUtil.getDateTime(_creationDate, "dd/MM/yy HH:mm:ss"));
		String text;
		if (DbDataType.isNullLongInteger(_updaterId))
			text = "";
		else
			text = Integer.toString(_updaterId);
		bdr.addSimpleElement("UpdaterId", text);
		if (DbDataType.isNullDateTime(_updateDate))
			text = "";
		else
			text = DateTimeUtil.getDateTime(_updateDate, "dd/MM/yy HH:mm:ss");
		bdr.addSimpleElement("UpdateDate", text);
		bdr.addSimpleElement("PwdLastUpdTs", Double.toString(_pwdLastUpdTs));
//		bdr.addFragment(_profilesImpl._profiles.toXML(false));
//		bdr.addFragment(_permsImpl._perms.toXML(false));
		bdr.addClosingTag(tagName);
		return bdr.getText();
	}

	public String toXML() {
		return toXML(true);
	}

	public String toString() {
		return toXML(false);
	}

	public Integer loadUserSysValue(DbOutputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("loadUserSysValue");
		_pwdminlen = statement.getLongInteger(index++);
		return new Integer(index);
	}

	public Integer loadPasswordValue(DbOutputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("loadPasswordValue");
		_oldPassword = UasMisc.decryptPassword(statement.getShortText(index++),
				_id);
		return new Integer(index);
	}

	public Integer insertValues(DbInputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("insertValues");
		statement.setLongInteger(index++, _id);
		statement.setShortText(index++, _name);
		statement.setShortText(index++, UasMisc.encryptPassword(_password, _id));
		statement.setLongInteger(index++, _deptId);
		statement.setLongInteger(index++, 0);
		statement.setLongInteger(index++, _state);
		statement.setLongInteger(index++, 0);
		statement.setShortText(index++, _description);
		statement.setLongInteger(index++, _creatorId);
		_creationDate = DbUtil.getCurrentDateTime();
		statement.setDateTime(index++, _creationDate);
		_pwdLastUpdTs = Math.round(DateTimeUtil.getCurrentTimeHours());
		statement.setLongDecimal(index++, _pwdLastUpdTs);
		statement.setShortText(index++, _pwdmbc);
		statement.setShortText(index++, _pwdvpcheck);
		return new Integer(index);
	}

	public Integer loadValues(DbOutputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("loadValues");
		_id = statement.getLongInteger(index++);
		_name = statement.getShortText(index++);
		_password = UasMisc.decryptPassword(statement.getShortText(index++),
				_id);
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

	public Integer updateValues(DbInputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("updateValues");
		statement.setShortText(index++, _name);
		statement
				.setShortText(index++, UasMisc.encryptPassword(_password, _id));
		statement.setLongInteger(index++, _deptId);
		statement.setLongInteger(index++, _state);
		statement.setShortText(index++, _description);
		_updaterId = _userConnected;
		statement.setLongInteger(index++, _updaterId);
		_updateDate = DbUtil.getCurrentDateTime();
		statement.setDateTime(index++, _updateDate);
		if (_isChange)
			_pwdLastUpdTs = Math.round(DateTimeUtil.getCurrentTimeHours());
		statement.setLongDecimal(index++, _pwdLastUpdTs);
		statement.setShortText(index++, _pwdmbc);
		statement.setShortText(index++, _pwdvpcheck);
		return new Integer(index);
	}

	public Integer updateOwnershipValues(DbInputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("updateOwnershipValues");
		statement.setLongInteger(index++, 0);
		statement.setLongInteger(index++, _userConnected);
		statement.setDateTime(index++, DbUtil.getCurrentDateTime());
		return new Integer(index);
	}

	public Integer updateDeptsMgrValues(DbInputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("updateDeptsMgrValues");
		statement.setLongInteger(index++, 0);
		statement.setLongInteger(index++, _userConnected);
		statement.setDateTime(index++, DbUtil.getCurrentDateTime());
		return new Integer(index);
	}

	public Integer updateGroupsMgrValues(DbInputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("updateGroupsMgrValues");
		statement.setLongInteger(index++, 0);
		statement.setLongInteger(index++, _userConnected);
		statement.setDateTime(index++, DbUtil.getCurrentDateTime());
		return new Integer(index);
	}

	public Integer loadIdNameValues(DbOutputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("loadIdNameValue");
		_id = statement.getLongInteger(index++);
		_name = statement.getShortText(index++);
		return new Integer(index);
	}

	public Integer loadIdValue(DbOutputStatement statement, Integer idx)
			throws Exception {
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("loadIdValue");
		_id = statement.getLongInteger(index++);
		return new Integer(index);
	}

	private void deleteGroupUserRel(DbConnection dbConn) throws Exception {
		UsersTable table = new UsersTable();
		DbDeleteFns.delete(dbConn, table.getGURelTableName(), table.getLoadGURelUserIdQual(_id));
	}

//	private void loadDeptsMgr(DbConnection dbConn, int usrId, ArrayList deptsId) throws Exception {
//		DepartmentsTable table = new DepartmentsTable();
//		if (_logger.isDebugEnabled())
//			_logger.debug("loadDeptsMgr");
//		IeciTdLongIntegerArrayList ids = new IeciTdLongIntegerArrayList();
//		String colName = table.getDeptIdColumnName();
//		String qual = table.getUpdateMgrQual(usrId);
//		DbSelectFns.select(dbConn, table.getBaseTableName(), colName, qual, true, ids);
//		for (int i = 0; i < ids.count(); i++) {
//			Integer val = new Integer(ids.get(i));
//			deptsId.add(val);
//		}
//
//	}

	private void updateDeptsMgr(DbConnection dbConn) throws Exception {
		DepartmentsTable table = new DepartmentsTable();
		DynamicTable tableInfo = new DynamicTable();
		if (_logger.isDebugEnabled())
			_logger.debug("updateDeptsMgr");
		tableInfo.setTableObject(table);
		tableInfo.setClassName(DepartmentsTable.class.getName());
		tableInfo.setTablesMethod("getBaseTableName");
		tableInfo.setColumnsMethod("getUpdateMgrColumnNames");
		DynamicRow rowInfo = new DynamicRow();
		DynamicRows rowsInfo = new DynamicRows();
		rowInfo.addRow(this);
		rowInfo.setClassName(UserImpl.class.getName());
		rowInfo.setValuesMethod("updateDeptsMgrValues");
		rowsInfo.add(rowInfo);
		DynamicFns.update(dbConn, table.getUpdateMgrQual(_id), tableInfo, rowsInfo);
	}

//	private void loadGroupsMgr(DbConnection dbConn, int usrId, ArrayList groupsId) throws Exception {
//		GroupsTable table = new GroupsTable();
//		if (_logger.isDebugEnabled())
//			_logger.debug("loadDeptsMgr");
//		IeciTdLongIntegerArrayList ids = new IeciTdLongIntegerArrayList();
//		String colName = table.getGroupIdColumnName();
//		String qual = table.getUpdateMgrQual(usrId);
//		DbSelectFns.select(dbConn, table.getBaseTableName(), colName, qual, true, ids);
//		for (int i = 0; i < ids.count(); i++) {
//			Integer val = new Integer(ids.get(i));
//			groupsId.add(val);
//		}
//
//	}

	private void updateGroupsMgr(DbConnection dbConn) throws Exception {
		GroupsTable table = new GroupsTable();
		DynamicTable tableInfo = new DynamicTable();
		if (_logger.isDebugEnabled())
			_logger.debug("updateGroupsMgr");
		tableInfo.setTableObject(table);
		tableInfo.setClassName(GroupsTable.class.getName());
		tableInfo.setTablesMethod("getBaseTableName");
		tableInfo.setColumnsMethod("getUpdateMgrColumnNames");
		DynamicRow rowInfo = new DynamicRow();
		DynamicRows rowsInfo = new DynamicRows();
		rowInfo.addRow(this);
		rowInfo.setClassName(UserImpl.class.getName());
		rowInfo.setValuesMethod("updateGroupsMgrValues");
		rowsInfo.add(rowInfo);
		DynamicFns.update(dbConn, table.getUpdateMgrQual(_id), tableInfo, rowsInfo);
	}

	private void checkUserExists(DbConnection dbConn) throws Exception {
		UsersTable table = new UsersTable();
		int count;
		if (_id == 0x7ffffffe)
			count = DbSelectFns.selectCount(dbConn, table.getBaseTableName(), table.getCountNameQual(_name));
		else
			count = DbSelectFns.selectCount(dbConn, table.getBaseTableName(), table.getCountNameIdQual(_id, _name));
		if (count > 0)
			AdminException.throwException(0x2dcab5L);
	}

//	private void deleteObjPerm(DbConnection dbConn, int perm, int productId) throws Exception {
//		UsersTable table = new UsersTable();
//		Permission permis = _permsImpl._perms.getProductPermission(productId);
//		if ((permis.getPermission() & perm) == 0)
//			DbDeleteFns.delete(dbConn, table.getObjPermsTableName(), table
//					.getDeleteObjPermQual(_id, 1, perm, productId));
//	}

//	private void deleteObjPerms(DbConnection dbConn) throws Exception {
//		if (_logger.isDebugEnabled())
//			_logger.debug("deleteObjPerms");
//		deleteObjPerm(dbConn, 16, 3);
//		deleteObjPerm(dbConn, 8, 3);
//		deleteObjPerm(dbConn, 4, 3);
//		deleteObjPerm(dbConn, 2, 3);
//		deleteObjPerm(dbConn, 1, 3);
//	}

	private void insert(DbConnection dbConn) throws Exception {
		boolean commit = false;
		boolean inTrans = false;
		if (_logger.isDebugEnabled())
			_logger.debug("insert");
		
		try {
			dbConn.beginTransaction();
			inTrans = true;
			insertBase(dbConn);
//			_profilesImpl.insertProfiles();
//			_permsImpl.insertPerms();
			commit = true;
		} catch (Exception e) {
			_logger.error(e);
			throw e;
		} finally {
			if (inTrans)
				dbConn.endTransaction(commit);
		}
		return;
	}

	private void update(DbConnection dbConn) throws Exception {
		boolean commit = false;
		boolean inTrans = false;
		if (_logger.isDebugEnabled())
			_logger.debug("update");
		
		try {
			dbConn.beginTransaction();
			inTrans = true;
			updateBase(dbConn);
//			_profilesImpl.updateProfiles(_id);
//			_permsImpl.updatePerms(_id, 1);
//			updateOwnership(dbConn);
//			deleteObjPerms(dbConn);
			commit = true;
		} catch (Exception e) {
			_logger.error(e);
			throw e;
		} finally {
			if (inTrans)
				dbConn.endTransaction(commit);
		}
		return;
	}

	private void insertBase(DbConnection dbConn) throws Exception {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		UsersTable table = new UsersTable();
		if (_logger.isDebugEnabled())
			_logger.debug("insertBase");
		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(UsersTable.class.getName());
			tableInfo.setTablesMethod("getBaseTableName");
			tableInfo.setColumnsMethod("getInsertBaseColumnNames");
			rowInfo.addRow(this);
			rowInfo.setClassName(UserImpl.class.getName());
			rowInfo.setValuesMethod("insertValues");
			rowsInfo.add(rowInfo);
			DynamicFns.insert(dbConn, tableInfo, rowsInfo);
		} catch (Exception e) {
			_logger.error(e);
			throw e;
		}
	}

	private void updateBase(DbConnection dbConn) throws Exception {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		UsersTable table = new UsersTable();
		if (_logger.isDebugEnabled())
			_logger.debug("update");
		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(UsersTable.class.getName());
			tableInfo.setTablesMethod("getBaseTableName");
			tableInfo.setColumnsMethod("getUpdateBaseColumnNames");
			rowInfo.addRow(this);
			rowInfo.setClassName(UserImpl.class.getName());
			rowInfo.setValuesMethod("updateValues");
			rowsInfo.add(rowInfo);
			DynamicFns.update(dbConn, table.getLoadBaseQual(_id), tableInfo, rowsInfo);
		} catch (Exception e) {
			_logger.error(e);
			throw e;
		}
	}

//	private void updateOwnership(DbConnection dbConn) throws Exception {
//		UsersTable table = new UsersTable();
//		DynamicTable tableInfo = new DynamicTable();
//		if (_logger.isDebugEnabled())
//			_logger.debug("updateOwnership");
//		if (_wasAdmin) {
//			UserProfile profile = _profilesImpl._profiles.getProductProfile(3);
//			int prof = profile.getProfile();
//			if (prof == 1 || prof == 0) {
//				tableInfo.setTableObject(table);
//				tableInfo.setClassName(UsersTable.class.getName());
//				tableInfo.setTablesMethod("getOwnershipTableName");
//				tableInfo.setColumnsMethod("getUpdateOwnershipColumnNames");
//				DynamicRow rowInfo = new DynamicRow();
//				DynamicRows rowsInfo = new DynamicRows();
//				rowInfo.addRow(this);
//				rowInfo.setClassName(UserImpl.class.getName());
//				rowInfo.setValuesMethod("updateOwnershipValues");
//				rowsInfo.add(rowInfo);
//				DynamicFns.update(dbConn, table.getUpdateOwnerDirArchQual(_id, 1, 3, 4, 5, 1), tableInfo, rowsInfo);
//			}
//		}
//	}

//	private void updateAllOwnership(DbConnection dbConn) throws Exception {
//		UsersTable table = new UsersTable();
//		DynamicTable tableInfo = new DynamicTable();
//		if (_logger.isDebugEnabled())
//			_logger.debug("updateAllOwnership");
//		tableInfo.setTableObject(table);
//		tableInfo.setClassName(UsersTable.class.getName());
//		tableInfo.setTablesMethod("getOwnershipTableName");
//		tableInfo.setColumnsMethod("getUpdateOwnershipColumnNames");
//		DynamicRow rowInfo = new DynamicRow();
//		DynamicRows rowsInfo = new DynamicRows();
//		rowInfo.addRow(this);
//		rowInfo.setClassName(UserImpl.class.getName());
//		rowInfo.setValuesMethod("updateOwnershipValues");
//		rowsInfo.add(rowInfo);
//		DynamicFns.update(dbConn, table.getUpdateOwnerQual(_id, 1), tableInfo, rowsInfo);
//	}

	private void checkDescription() throws Exception {
		if (_description != null) {
			int index = _description.indexOf("\"");
			if (index > -1)
				AdminException.throwException(0x2dcab0L);
		}
	}

	private void checkPassword(DbConnection dbConn) throws Exception {
		if (_password != null && !_password.equals("")) {
			int index = _password.indexOf(" ");
			if (index > -1)
				AdminException.throwException(0x2dcab2L);
		} else {
			AdminException.throwException(0x2dcab1L);
		}
		loadUserConfig(dbConn);
		if (_password.length() < _pwdminlen)
			AdminException.throwException(0x2dcab4L);
	}

	private boolean changePassword(DbConnection dbConn) throws Exception {
		boolean change = false;
		if (_logger.isDebugEnabled())
			_logger.debug("changePassword");
		loadPassword(dbConn);
		if (_password == null) {
			_password = _oldPassword;
		}
		if (!_password.equals(_oldPassword))
			change = true;
		return change;
	}

	private void loadUserConfig(DbConnection dbConn) throws Exception {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		UsersTable table = new UsersTable();
		if (_logger.isDebugEnabled())
			_logger.debug("loadUserConfig");
		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(UsersTable.class.getName());
			tableInfo.setTablesMethod("getUserSysTableName");
			tableInfo.setColumnsMethod("getUserSysColumnName");
			rowInfo.addRow(this);
			rowInfo.setClassName(UserImpl.class.getName());
			rowInfo.setValuesMethod("loadUserSysValue");
			rowsInfo.add(rowInfo);
			if (!DynamicFns.select(dbConn, "", tableInfo, rowsInfo))
				AdminException.throwException(0x2dcab3L);
		} catch (Exception e) {
			_logger.error(e);
			throw e;
		}
	}

	private void loadPassword(DbConnection dbConn) throws Exception {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		UsersTable table = new UsersTable();
//		UserImpl user = new UserImpl();
//		String password = null;
		if (_logger.isDebugEnabled())
			_logger.debug("loadPassword");
		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(UsersTable.class.getName());
			tableInfo.setTablesMethod("getBaseTableName");
			tableInfo.setColumnsMethod("getPasswordColumnName");
			rowInfo.addRow(this);
			rowInfo.setClassName(UserImpl.class.getName());
			rowInfo.setValuesMethod("loadPasswordValue");
			rowsInfo.add(rowInfo);
			DynamicFns.select(dbConn, table.getLoadBaseQual(_id), tableInfo, rowsInfo);
		} catch (Exception e) {
			_logger.error(e);
			throw e;
		}
	}

	private void loadBase(DbConnection dbConn) throws Exception {
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		UsersTable table = new UsersTable();
		if (_logger.isDebugEnabled())
			_logger.debug("loadBase");
		try {
			tableInfo.setTableObject(table);
			tableInfo.setClassName(UsersTable.class.getName());
			tableInfo.setTablesMethod("getBaseTableName");
			tableInfo.setColumnsMethod("getLoadBaseColumnNames");
			rowInfo.addRow(this);
			rowInfo.setClassName(UserImpl.class.getName());
			rowInfo.setValuesMethod("loadValues");
			rowsInfo.add(rowInfo);
			if (!DynamicFns.select(dbConn, table.getLoadBaseQual(_id), tableInfo, rowsInfo))
				AdminException.throwException(0x2dcaaaL);
		} catch (Exception e) {
			_logger.error(e);
			throw e;
		}
	}

}