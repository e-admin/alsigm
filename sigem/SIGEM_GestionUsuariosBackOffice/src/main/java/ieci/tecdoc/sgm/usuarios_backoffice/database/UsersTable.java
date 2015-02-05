package ieci.tecdoc.sgm.usuarios_backoffice.database;

import ieci.tecdoc.sbo.acs.std.AcsDaoObjHdrTbl;
import ieci.tecdoc.sbo.acs.std.AcsDaoObjPermTbl;
import ieci.tecdoc.sbo.acs.std.AcsDaoUserTypeTbl;
import ieci.tecdoc.sbo.uas.ldap.UasDaoLdapUserTbl;
import ieci.tecdoc.sbo.uas.std.UasDaoGURelTbl;
import ieci.tecdoc.sbo.uas.std.UasDaoSysTbl;
import ieci.tecdoc.sbo.uas.std.UasDaoUserTbl;

import org.apache.log4j.Logger;

public class UsersTable {

    public static String TN_NEXTID = "IUSERNEXTID";
    private static final Logger _logger = Logger.getLogger(UsersTable.class);

    public UsersTable() {
	}

	public String getBaseTableName() {
		return "IUSERUSERHDR";
	}

	public String getProfilesTableName() {
		return "IUSERUSERTYPE";
	}

	public String getObjPermsTableName() {
		return "IUSEROBJPERM";
	}

	public String getOwnershipTableName() {
		return "IUSEROBJHDR";
	}

	public String getGURelTableName() {
		return "IUSERGROUPUSER";
	}

	public String getUserSysTableName() {
		return "IUSERUSERSYS";
	}

	public String getUserAdminTableNames() {
		return getBaseTableName() + "," + getProfilesTableName();
	}

	public String getAdminUserColumnNames() {
		String val = getBaseTableName() + "." + UasDaoUserTbl.CD_ID.getName()
				+ "," + getBaseTableName() + "."
				+ UasDaoUserTbl.CD_NAME.getName();
		return val;
	}

	public String getDeptIdUserColumnName() {
		String val = getBaseTableName() + "."
				+ UasDaoUserTbl.CD_DEPTID.getName();
		return val;
	}

	public String getUpdateOwnershipColumnNames() {
		String val = AcsDaoObjHdrTbl.CD_OWNERID.getName() + ","
				+ AcsDaoObjHdrTbl.CD_UPDUSRID.getName() + ","
				+ AcsDaoObjHdrTbl.CD_UPDTS.getName();
		return val;
	}

	public String getInsertBaseColumnNames() {
		String val = UasDaoUserTbl.CD_ID.getName()
				+ "," + UasDaoUserTbl.CD_NAME.getName() 
				+ "," + UasDaoUserTbl.CD_PASSWORD.getName() 
				+ "," + UasDaoUserTbl.CD_DEPTID.getName()
				+ "," + UasDaoUserTbl.CD_FLAGS.getName() 
				+ "," + UasDaoUserTbl.CD_STAT.getName() 
				+ "," + UasDaoUserTbl.CD_NUMBADCNTS.getName() 
				+ "," + UasDaoUserTbl.CD_REMARKS.getName()
				+ "," + UasDaoUserTbl.CD_CRTRID.getName() 
				+ "," + UasDaoUserTbl.CD_CRTNDATE.getName() 
				+ "," + UasDaoUserTbl.CD_PWDLASTUPDTS.getName() 
				+ "," + UasDaoUserTbl.CD_PWDMBC.getName()
				+ "," + UasDaoUserTbl.CD_PWDVPCHECK.getName();
		return val;
	}

	public String getUpdateBaseColumnNames() {
		String val = UasDaoUserTbl.CD_NAME.getName()
				+ "," + UasDaoUserTbl.CD_PASSWORD.getName() 
				+ "," + UasDaoUserTbl.CD_DEPTID.getName()
				+ "," + UasDaoUserTbl.CD_STAT.getName() 
				+ "," + UasDaoUserTbl.CD_REMARKS.getName() 
				+ "," + UasDaoUserTbl.CD_UPDRID.getName()
				+ "," + UasDaoUserTbl.CD_UPDATE.getName() 
				+ "," + UasDaoUserTbl.CD_PWDLASTUPDTS.getName() 
				+ "," + UasDaoUserTbl.CD_PWDMBC.getName()
				+ "," + UasDaoUserTbl.CD_PWDVPCHECK.getName();
		return val;
	}

	public String getLoadBaseColumnNames() {
		String val = getBaseTableName() + "." + UasDaoUserTbl.CD_ID.getName()
				+ "," + getBaseTableName() + "."
				+ UasDaoUserTbl.CD_NAME.getName() + "," + getBaseTableName()
				+ "." + UasDaoUserTbl.CD_PASSWORD.getName() + ","
				+ getBaseTableName() + "." + UasDaoUserTbl.CD_DEPTID.getName()
				+ "," + getBaseTableName() + "."
				+ UasDaoUserTbl.CD_STAT.getName() + "," + getBaseTableName()
				+ "." + UasDaoUserTbl.CD_REMARKS.getName() + ","
				+ getBaseTableName() + "." + UasDaoUserTbl.CD_CRTRID.getName()
				+ "," + getBaseTableName() + "."
				+ UasDaoUserTbl.CD_CRTNDATE.getName() + ","
				+ getBaseTableName() + "." + UasDaoUserTbl.CD_UPDRID.getName()
				+ "," + getBaseTableName() + "."
				+ UasDaoUserTbl.CD_UPDATE.getName() + "," + getBaseTableName()
				+ "." + UasDaoUserTbl.CD_PWDLASTUPDTS.getName() + ","
				+ getBaseTableName() + "." + UasDaoUserTbl.CD_PWDMBC.getName()
				+ "," + getBaseTableName() + "."
				+ UasDaoUserTbl.CD_PWDVPCHECK.getName();
		return val;
	}

	public String getUserSysColumnName() {
		String val = UasDaoSysTbl.CD_PWDMINLEN.getName();
		return val;
	}

	public String getPasswordColumnName() {
		String val = UasDaoUserTbl.CD_PASSWORD.getName();
		return val;
	}

	public String getLoadBaseIdNameColumnNames() {
		String val = getBaseTableName() + "." + UasDaoUserTbl.CD_ID.getName()
				+ "," + getBaseTableName() + "."
				+ UasDaoUserTbl.CD_NAME.getName();
		return val;
	}

	public String getLoadBaseIdColumnName() {
		String val = getGURelTableName() + "."
				+ UasDaoGURelTbl.CD_USERID.getName();
		return val;
	}

	public String getInsertGURelColumnNames() {
		String val = getGURelTableName() + "."
				+ UasDaoGURelTbl.CD_GROUPID.getName() + ","
				+ getGURelTableName() + "."
				+ UasDaoGURelTbl.CD_USERID.getName();
		return val;
	}

	public String getGURelGroupColumnName() {
		String val = getGURelTableName() + "."
				+ UasDaoGURelTbl.CD_GROUPID.getName();
		return val;
	}

	public String getCountNameQual(String name) {
		String qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoUserTbl.CD_NAME.getName() + "= '" + name + "'";
		if (_logger.isDebugEnabled())
			_logger.debug("getCountNameQual: " + qual);
		return qual;
	}

	public String getCountNameIdQual(int id, String name) {
		String qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoUserTbl.CD_NAME.getName() + "= '" + name + "' AND "
				+ getBaseTableName() + "." + UasDaoUserTbl.CD_ID.getName()
				+ "<>" + Integer.toString(id);
		if (_logger.isDebugEnabled())
			_logger.debug("getCountNameIdQual: " + qual);
		return qual;
	}

	public String getLoadAminUsersQual(int productId) {
		String qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoLdapUserTbl.CD_ID.getName() + " IN (SELECT "
				+ getProfilesTableName() + "."
				+ AcsDaoUserTypeTbl.CD_USERID.getName() + " FROM "
				+ getProfilesTableName() + " WHERE (" + getProfilesTableName()
				+ "." + AcsDaoUserTypeTbl.CD_TYPE.getName() + "=" + 2 + " OR "
				+ getProfilesTableName() + "."
				+ AcsDaoUserTypeTbl.CD_TYPE.getName() + "=" + 3 + ") AND ("
				+ getProfilesTableName() + "."
				+ AcsDaoUserTypeTbl.CD_PRODID.getName() + "="
				+ Integer.toString(productId) + " OR " + getProfilesTableName()
				+ "." + AcsDaoUserTypeTbl.CD_PRODID.getName() + "=" + 1 + "))";
		if (_logger.isDebugEnabled())
			_logger.debug("getLoadAminUsersQual: " + qual);
		return qual;
	}

	public String getLoadUserIdQual(int id) {
		String qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoUserTbl.CD_ID.getName() + "=" + Integer.toString(id);
		if (_logger.isDebugEnabled())
			_logger.debug("getLoadUserIdQual: " + qual);
		return qual;
	}

	public String getLoadGURelQual(int usrId, int grpId) {
		String qual = "WHERE " + getGURelTableName() + "."
				+ UasDaoGURelTbl.CD_USERID.getName() + "="
				+ Integer.toString(usrId) + " AND " + getGURelTableName() + "."
				+ UasDaoGURelTbl.CD_GROUPID.getName() + "="
				+ Integer.toString(grpId);
		if (_logger.isDebugEnabled())
			_logger.debug("getLoadGURelQual: " + qual);
		return qual;
	}

	public String getLoadGURelUserIdQual(int usrId) {
		String qual = "WHERE " + getGURelTableName() + "."
				+ UasDaoGURelTbl.CD_USERID.getName() + "="
				+ Integer.toString(usrId);
		if (_logger.isDebugEnabled())
			_logger.debug("getLoadGURelUserIdQual: " + qual);
		return qual;
	}

	public String getDeleteObjPermsQual(int userId, int dest) {
		String qual = "WHERE " + getObjPermsTableName() + "."
				+ AcsDaoObjPermTbl.CD_DSTTYPE.getName() + "="
				+ Integer.toString(dest) + " AND " + getObjPermsTableName()
				+ "." + AcsDaoObjPermTbl.CD_DSTID.getName() + "="
				+ Integer.toString(userId);
		if (_logger.isDebugEnabled())
			_logger.debug("getDeleteObjPermsQual: " + qual);
		return qual;
	}

	public String getUpdateOwnerQual(int id, int ownerType) {
		String qual = "WHERE " + getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_OWNERTYPE.getName() + "="
				+ Integer.toString(ownerType) + " AND "
				+ getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_OWNERID.getName() + "="
				+ Integer.toString(id);
		if (_logger.isDebugEnabled())
			_logger.debug("getUpdateOwnerQual: " + qual);
		return qual;
	}

	public String getDeleteObjPermQual(int id, int dest, int perm, int productId) {
		String qual = "WHERE " + getObjPermsTableName() + "."
				+ AcsDaoObjPermTbl.CD_DSTTYPE.getName() + "="
				+ Integer.toString(dest) + " AND " + getObjPermsTableName()
				+ "." + AcsDaoObjPermTbl.CD_DSTID.getName() + "="
				+ Integer.toString(id) + " AND " + getObjPermsTableName() + "."
				+ AcsDaoObjPermTbl.CD_PERM.getName() + "="
				+ Integer.toString(perm) + " AND EXISTS (SELECT "
				+ getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_ID.getName() + " FROM "
				+ getOwnershipTableName() + " WHERE " + getOwnershipTableName()
				+ "." + AcsDaoObjHdrTbl.CD_PRODID.getName() + "="
				+ Integer.toString(productId) + " AND "
				+ getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_ID.getName() + "="
				+ getObjPermsTableName() + "."
				+ AcsDaoObjPermTbl.CD_OBJID.getName() + ")";
		if (_logger.isDebugEnabled())
			_logger.debug("getDeleteObjPermQual: " + qual);
		return qual;
	}

	public String getDeleteBaseQual(int userId) {
		String qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoUserTbl.CD_ID.getName() + "="
				+ Integer.toString(userId);
		if (_logger.isDebugEnabled())
			_logger.debug("getDeleteBaseQual: " + qual);
		return qual;
	}

	public String getLoadBaseQual(int id) {
		String qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoUserTbl.CD_ID.getName() + "=" + Integer.toString(id);
		if (_logger.isDebugEnabled())
			_logger.debug("getLoadUserQual: " + qual);
		return qual;
	}

	public String getLoadNameBaseQual(String name) {
		String qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoUserTbl.CD_NAME.getName() + "= '" + name + "'";
		if (_logger.isDebugEnabled())
			_logger.debug("getLoadNameBaseQual: " + qual);
		return qual;
	}

	public String getLoadBaseBySubNameQual(String subName) {
		String qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoUserTbl.CD_NAME.getName() + " LIKE '%" + subName + "%'";
		if (_logger.isDebugEnabled())
			_logger.debug("getLoadSubNameBaseQual: " + qual);
		return qual;
	}

	public String getUpdateOwnerDirArchQual(int userId, int ownerType,
			int productId, int objTypeDir, int objTypeArch, int extId2) {
		String qual = "WHERE " + getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_OWNERTYPE.getName() + "="
				+ Integer.toString(ownerType) + " AND "
				+ getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_OWNERID.getName() + "="
				+ Integer.toString(userId) + " AND " + getOwnershipTableName()
				+ "." + AcsDaoObjHdrTbl.CD_PRODID.getName() + "="
				+ Integer.toString(productId) + " AND ("
				+ getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_TYPE.getName() + "="
				+ Integer.toString(objTypeDir) + " OR ("
				+ getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_TYPE.getName() + "="
				+ Integer.toString(objTypeArch) + " AND "
				+ getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_EXTID2.getName() + "="
				+ Integer.toString(extId2) + "))";
		if (_logger.isDebugEnabled())
			_logger.debug("getUpdateOwnerArchQual: " + qual);
		return qual;
	}

	public String getLoadBaseByDeptQual(int deptId) {
		String qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoUserTbl.CD_DEPTID.getName() + "="
				+ Integer.toString(deptId);
		if (_logger.isDebugEnabled())
			_logger.debug("getLoadBaseByDeptQual: " + qual);
		return qual;
	}

	public String getLoadBaseByGroupQual(int groupId) {
		String qual = "WHERE " + getGURelTableName() + "."
				+ UasDaoGURelTbl.CD_GROUPID.getName() + "="
				+ Integer.toString(groupId);
		if (_logger.isDebugEnabled())
			_logger.debug("getLoadBaseByGroupQual: " + qual);
		return qual;
	}

}
