package ieci.tecdoc.sgm.usuarios_backoffice.database;

import ieci.tecdoc.sbo.acs.std.AcsDaoGenPermTbl;
import ieci.tecdoc.sbo.acs.std.AcsDaoObjHdrTbl;
import ieci.tecdoc.sbo.acs.std.AcsDaoObjPermTbl;
import ieci.tecdoc.sbo.acs.std.AcsDaoUserTypeTbl;
import ieci.tecdoc.sbo.uas.ldap.UasDaoLdapUserTbl;

import org.apache.log4j.Logger;

public class LdapUsersTable {

    public static String TN_NEXTID = "IUSERNEXTID";
	private static final Logger _logger = Logger
			.getLogger(LdapUsersTable.class);

	public LdapUsersTable() {
	}

	public String getLoadBaseAllQual() {
		String qual = "";
		return qual;
	}

	public String getLoadBaseQual(int id) {
		String qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoLdapUserTbl.CD_ID.getName() + "="
				+ Integer.toString(id);
		if (_logger.isDebugEnabled())
			_logger.debug("getLoadBaseQual: " + qual);
		return qual;
	}

	public String getLoadBaseQual(String guid) {
		String qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoLdapUserTbl.CD_LDAPGUID.getName() + "='" + guid + "'";
		if (_logger.isDebugEnabled())
			_logger.debug("getLoadBaseQual: " + qual);
		return qual;
	}

	public String getLoadProfilesQual(int id) {
		String qual = "WHERE " + getProfilesTableName() + "."
				+ AcsDaoUserTypeTbl.CD_USERID.getName() + "="
				+ Integer.toString(id);
		if (_logger.isDebugEnabled())
			_logger.debug("getLoadProfilesQual: " + qual);
		return qual;
	}

	public String getUpdateProfilesQual(int id, int productId) {
		String qual = "WHERE " + getProfilesTableName() + "."
				+ AcsDaoUserTypeTbl.CD_USERID.getName() + "="
				+ Integer.toString(id) + " AND " + getProfilesTableName() + "."
				+ AcsDaoUserTypeTbl.CD_PRODID.getName() + "="
				+ Integer.toString(productId);
		if (_logger.isDebugEnabled())
			_logger.debug("getUpdateProfilesQual: " + qual);
		return qual;
	}

	public String getLoadPermsQual(int id, int dest) {
		String qual = "WHERE " + getPermsTableName() + "."
				+ AcsDaoGenPermTbl.CD_DSTID.getName() + "="
				+ Integer.toString(id) + " AND " + getPermsTableName() + "."
				+ AcsDaoGenPermTbl.CD_DSTTYPE.getName() + "="
				+ Integer.toString(dest);
		if (_logger.isDebugEnabled())
			_logger.debug("getLoadPermsQual: " + qual);
		return qual;
	}

	public String getUpdatePermsQual(int id, int dest, int productId) {
		String qual = "WHERE " + getPermsTableName() + "."
				+ AcsDaoGenPermTbl.CD_DSTID.getName() + "="
				+ Integer.toString(id) + " AND " + getPermsTableName() + "."
				+ AcsDaoGenPermTbl.CD_DSTTYPE.getName() + "="
				+ Integer.toString(dest) + " AND " + getPermsTableName() + "."
				+ AcsDaoGenPermTbl.CD_PRODID.getName() + "="
				+ Integer.toString(productId);
		if (_logger.isDebugEnabled())
			_logger.debug("getUpdatePermsQual: " + qual);
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

	public String getSelectOwnerQual(int id) {
		String qual = "WHERE " + getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_ID.getName() + "=" + Integer.toString(id);
		if (_logger.isDebugEnabled())
			_logger.debug("getSelectOwnerQual: " + qual);
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
				+ UasDaoLdapUserTbl.CD_ID.getName() + "="
				+ Integer.toString(userId);
		if (_logger.isDebugEnabled())
			_logger.debug("getDeleteBaseQual: " + qual);
		return qual;
	}

	public String getDeleteOwnershipById(int id) {
		String qual = "WHERE " + getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_ID.getName() + "=" + Integer.toString(id);
		if (_logger.isDebugEnabled())
			_logger.debug("getDeleteOwnershipById: " + qual);
		return qual;
	}

	public String getDeleteProfilesQual(int userId) {
		String qual = "WHERE " + getProfilesTableName() + "."
				+ AcsDaoUserTypeTbl.CD_USERID.getName() + "="
				+ Integer.toString(userId);
		if (_logger.isDebugEnabled())
			_logger.debug("getDeleteProfilesQual: " + qual);
		return qual;
	}

	public String getDeletePermsQual(int id, int dest) {
		String qual = "WHERE " + getPermsTableName() + "."
				+ AcsDaoGenPermTbl.CD_DSTTYPE.getName() + "="
				+ Integer.toString(dest) + " AND " + getPermsTableName() + "."
				+ AcsDaoGenPermTbl.CD_DSTID.getName() + "="
				+ Integer.toString(id);
		if (_logger.isDebugEnabled())
			_logger.debug("getDeletePermsQual: " + qual);
		return qual;
	}

	public String getDeleteObjPermsQual(int id, int dest) {
		String qual = "WHERE " + getObjPermsTableName() + "."
				+ AcsDaoObjPermTbl.CD_DSTTYPE.getName() + "="
				+ Integer.toString(dest) + " AND " + getObjPermsTableName()
				+ "." + AcsDaoObjPermTbl.CD_DSTID.getName() + "="
				+ Integer.toString(id);
		if (_logger.isDebugEnabled())
			_logger.debug("getDeleteObjPermsQual: " + qual);
		return qual;
	}

	public String getDeleteObjPermsByObjIdQual(int id) {
		String qual = "WHERE " + getObjPermsTableName() + "."
				+ AcsDaoObjPermTbl.CD_OBJID.getName() + "="
				+ Integer.toString(id);
		if (_logger.isDebugEnabled())
			_logger.debug("getDeleteObjPermsByObjIdQual: " + qual);
		return qual;
	}

	public String getDeleteObjPermQual(int dstType, int dstId, int typeObj,
			int id, int perm) {
		String qual = "WHERE " + getObjPermsTableName() + "."
				+ AcsDaoObjPermTbl.CD_DSTTYPE.getName() + "="
				+ Integer.toString(dstType) + " AND " + getObjPermsTableName()
				+ "." + AcsDaoObjPermTbl.CD_DSTID.getName() + "="
				+ Integer.toString(dstId) + " AND " + getObjPermsTableName()
				+ "." + AcsDaoObjPermTbl.CD_PERM.getName() + "="
				+ Integer.toString(perm) + " AND EXISTS(SELECT "
				+ getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_ID.getName() + " FROM "
				+ getOwnershipTableName() + " WHERE " + getOwnershipTableName()
				+ "." + AcsDaoObjHdrTbl.CD_PRODID.getName() + "="
				+ Integer.toString(3) + " AND " + getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_TYPE.getName() + "="
				+ Integer.toString(typeObj) + " AND " + getOwnershipTableName()
				+ "." + AcsDaoObjHdrTbl.CD_EXTID1.getName() + "="
				+ Integer.toString(id) + " AND " + getOwnershipTableName()
				+ "." + AcsDaoObjHdrTbl.CD_ID.getName() + "="
				+ getObjPermsTableName() + "."
				+ AcsDaoObjPermTbl.CD_OBJID.getName() + ")";
		return qual;
	}

	public String getLoadObjPermsQual(int dstType, int dstId, int objType,
			int id) {
		String qual = "WHERE " + getObjPermsTableName() + "."
				+ AcsDaoObjPermTbl.CD_DSTTYPE.getName() + "="
				+ Integer.toString(dstType) + " AND " + getObjPermsTableName()
				+ "." + AcsDaoObjPermTbl.CD_DSTID.getName() + "="
				+ Integer.toString(dstId) + " AND " + getObjPermsTableName()
				+ "." + AcsDaoObjPermTbl.CD_OBJID.getName() + " IN (SELECT "
				+ getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_ID.getName() + " FROM "
				+ getOwnershipTableName() + " WHERE " + getOwnershipTableName()
				+ "." + AcsDaoObjHdrTbl.CD_TYPE.getName() + "="
				+ Integer.toString(objType) + " AND " + getOwnershipTableName()
				+ "." + AcsDaoObjHdrTbl.CD_EXTID1.getName() + "="
				+ Integer.toString(id) + ")";
		return qual;
	}

	public String getLoadOwnerIdQual(int id) {
		String qual = "WHERE " + getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_ID.getName() + "=" + Integer.toString(id);
		if (_logger.isDebugEnabled())
			_logger.debug("getLoadOwnerIdQual: " + qual);
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

	public String getAllTableNames() {
		return getBaseTableName() + "," + getProfilesTableName() + ","
				+ getPermsTableName();
	}

	public String getBaseTableName() {
		return "IUSERLDAPUSERHDR";
	}

	public String getProfilesTableName() {
		return "IUSERUSERTYPE";
	}

	public String getPermsTableName() {
		return "IUSERGENPERMS";
	}

	public String getOwnershipTableName() {
		return "IUSEROBJHDR";
	}

	public String getObjPermsTableName() {
		return "IUSEROBJPERM";
	}

	public String getDeleteObjPermsTableNames() {
		return getOwnershipTableName() + "," + getObjPermsTableName();
	}

	public String getUserAdminTableNames() {
		return getBaseTableName() + "," + getProfilesTableName();
	}

	public String getAllColumnNames() {
		String val = getBaseTableName() + "."
				+ UasDaoLdapUserTbl.CD_ID.getName() + "," + getBaseTableName()
				+ "." + UasDaoLdapUserTbl.CD_LDAPGUID.getName() + ","
				+ getBaseTableName() + "."
				+ UasDaoLdapUserTbl.CD_LDAPFULLNAME.getName() + ","
				+ getProfilesTableName() + "."
				+ AcsDaoUserTypeTbl.CD_USERID.getName() + ","
				+ getProfilesTableName() + "."
				+ AcsDaoUserTypeTbl.CD_PRODID.getName() + ","
				+ getProfilesTableName() + "."
				+ AcsDaoUserTypeTbl.CD_TYPE.getName() + ","
				+ getPermsTableName() + "."
				+ AcsDaoGenPermTbl.CD_DSTTYPE.getName() + ","
				+ getPermsTableName() + "."
				+ AcsDaoGenPermTbl.CD_DSTID.getName() + ","
				+ getPermsTableName() + "."
				+ AcsDaoGenPermTbl.CD_PRODID.getName() + ","
				+ getPermsTableName() + "."
				+ AcsDaoGenPermTbl.CD_PERMS.getName();
		return val;
	}

	public String getAdminUserColumnNames() {
		String val = getBaseTableName() + "."
				+ UasDaoLdapUserTbl.CD_ID.getName() + "," + getBaseTableName()
				+ "." + UasDaoLdapUserTbl.CD_LDAPFULLNAME.getName();
		return val;
	}

	public String getInsertBaseColumnNames() {
		String val = getBaseTableName() + "."
				+ UasDaoLdapUserTbl.CD_ID.getName() + "," + getBaseTableName()
				+ "." + UasDaoLdapUserTbl.CD_LDAPGUID.getName() + ","
				+ getBaseTableName() + "."
				+ UasDaoLdapUserTbl.CD_LDAPFULLNAME.getName();
		return val;
	}

	public String getLoadBaseIdColumnName() {
		String val = getBaseTableName() + "."
				+ UasDaoLdapUserTbl.CD_ID.getName();
		return val;
	}

	public String getLoadBaseColumnNames() {
		String val = getBaseTableName() + "."
				+ UasDaoLdapUserTbl.CD_LDAPGUID.getName() + ","
				+ getBaseTableName() + "."
				+ UasDaoLdapUserTbl.CD_LDAPFULLNAME.getName();
		return val;
	}

	public String getLoadBaseAllColumnNames() {
		String val = getBaseTableName() + "."
				+ UasDaoLdapUserTbl.CD_ID.getName() + "," + getBaseTableName()
				+ "." + UasDaoLdapUserTbl.CD_LDAPGUID.getName() + ","
				+ getBaseTableName() + "."
				+ UasDaoLdapUserTbl.CD_LDAPFULLNAME.getName();
		return val;
	}

	public String getInsertProfilesColumnNames() {
		String val = AcsDaoUserTypeTbl.CD_USERID.getName() + ","
				+ AcsDaoUserTypeTbl.CD_PRODID.getName() + ","
				+ AcsDaoUserTypeTbl.CD_TYPE.getName();
		return val;
	}

	public String getLoadProfilesColumnNames() {
		String val = getProfilesTableName() + "."
				+ AcsDaoUserTypeTbl.CD_PRODID.getName() + ","
				+ getProfilesTableName() + "."
				+ AcsDaoUserTypeTbl.CD_TYPE.getName();
		return val;
	}

	public String getLoadObjPermColumnName() {
		String val = getObjPermsTableName() + "."
				+ AcsDaoObjPermTbl.CD_PERM.getName();
		return val;
	}

	public String getUpdateProfilesColumnNames() {
		String val = AcsDaoUserTypeTbl.CD_TYPE.getName();
		return val;
	}

	public String getInsertPermsColumnNames() {
		String val = AcsDaoGenPermTbl.CD_DSTTYPE.getName() + ","
				+ AcsDaoGenPermTbl.CD_DSTID.getName() + ","
				+ AcsDaoGenPermTbl.CD_PRODID.getName() + ","
				+ AcsDaoGenPermTbl.CD_PERMS.getName();
		return val;
	}

	public String getLoadPermsColumnNames() {
		String val = getPermsTableName() + "."
				+ AcsDaoGenPermTbl.CD_DSTTYPE.getName() + ","
				+ getPermsTableName() + "."
				+ AcsDaoGenPermTbl.CD_PRODID.getName() + ","
				+ getPermsTableName() + "."
				+ AcsDaoGenPermTbl.CD_PERMS.getName();
		return val;
	}

	public String getUpdatePermsColumnNames() {
		String val = AcsDaoGenPermTbl.CD_PERMS.getName();
		return val;
	}

	public String getInsertObjPermsColumnNames() {
		String val = getObjPermsTableName() + "."
				+ AcsDaoObjPermTbl.CD_DSTTYPE.getName() + ","
				+ getObjPermsTableName() + "."
				+ AcsDaoObjPermTbl.CD_DSTID.getName() + ","
				+ getObjPermsTableName() + "."
				+ AcsDaoObjPermTbl.CD_OBJID.getName() + ","
				+ getObjPermsTableName() + "."
				+ AcsDaoObjPermTbl.CD_PERM.getName();
		return val;
	}

	public String getAllOwnershipColumnNames() {
		String val = getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_ID.getName() + ","
				+ getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_PRODID.getName() + ","
				+ getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_TYPE.getName() + ","
				+ getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_EXTID1.getName() + ","
				+ getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_EXTID2.getName() + ","
				+ getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_EXTID3.getName() + ","
				+ getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_OWNERTYPE.getName() + ","
				+ getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_OWNERID.getName() + ","
				+ getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_CRTUSRID.getName() + ","
				+ getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_CRTTS.getName() + ","
				+ getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_UPDUSRID.getName() + ","
				+ getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_UPDTS.getName();
		return val;
	}

	public String getUpdateOwnershipColumnNames() {
		String val = getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_OWNERID.getName() + ","
				+ getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_UPDUSRID.getName() + ","
				+ getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_UPDTS.getName();
		return val;
	}

	public String getOwnerIdColumnName() {
		String val = getOwnershipTableName() + "."
				+ AcsDaoObjHdrTbl.CD_OWNERID.getName();
		return val;
	}

}
