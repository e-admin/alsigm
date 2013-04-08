package ieci.tecdoc.sgm.usuarios_backoffice.database;

import ieci.tecdoc.sbo.uas.std.UasDaoGroupTbl;

import org.apache.log4j.Logger;

public class GroupsTable {

    private static final Logger _logger = Logger.getLogger(GroupsTable.class);

	public GroupsTable() {
	}

	public String getBaseTableName() {
		return "IUSERGROUPHDR";
	}

	public String getInsertBaseColumnNames() {
		String val = UasDaoGroupTbl.CD_ID.getName()
				+ "," + UasDaoGroupTbl.CD_NAME.getName() 
				+ "," + UasDaoGroupTbl.CD_MGRID.getName() 
				+ "," + UasDaoGroupTbl.CD_TYPE.getName()
				+ "," + UasDaoGroupTbl.CD_REMARKS.getName() 
				+ "," + UasDaoGroupTbl.CD_CRTRID.getName()
				+ "," + UasDaoGroupTbl.CD_CRTNDATE.getName();
		return val;
	}

	public String getUpdateBaseColumnNames() {
		String val = UasDaoGroupTbl.CD_NAME.getName() 
				+ "," + UasDaoGroupTbl.CD_MGRID.getName() 
				+ "," + UasDaoGroupTbl.CD_REMARKS.getName() 
				+ "," + UasDaoGroupTbl.CD_UPDRID.getName()
				+ "," + UasDaoGroupTbl.CD_UPDATE.getName();
		return val;
	}

	public String getLoadBaseColumnNames() {
		String val = getBaseTableName() + "." + UasDaoGroupTbl.CD_ID.getName()
				+ "," + getBaseTableName() + "."
				+ UasDaoGroupTbl.CD_NAME.getName() + "," + getBaseTableName()
				+ "." + UasDaoGroupTbl.CD_MGRID.getName() + ","
				+ getBaseTableName() + "."
				+ UasDaoGroupTbl.CD_REMARKS.getName() + ","
				+ getBaseTableName() + "." + UasDaoGroupTbl.CD_CRTRID.getName()
				+ "," + getBaseTableName() + "."
				+ UasDaoGroupTbl.CD_CRTNDATE.getName() + ","
				+ getBaseTableName() + "." + UasDaoGroupTbl.CD_UPDRID.getName()
				+ "," + getBaseTableName() + "."
				+ UasDaoGroupTbl.CD_UPDATE.getName();
		return val;
	}

	public String getLoadBaseIdNameColumnNames() {
		String val = getBaseTableName() + "." + UasDaoGroupTbl.CD_ID.getName()
				+ "," + getBaseTableName() + "."
				+ UasDaoGroupTbl.CD_NAME.getName();
		return val;
	}

	public String getMgrGroupColumnName() {
		String val = getBaseTableName() + "."
				+ UasDaoGroupTbl.CD_MGRID.getName();
		return val;
	}

	public String getUpdateMgrColumnNames() {
		String val = UasDaoGroupTbl.CD_MGRID.getName() 
				+ "," + UasDaoGroupTbl.CD_UPDRID.getName() 
				+ "," + UasDaoGroupTbl.CD_UPDATE.getName();
		return val;
	}

	public String getGroupIdColumnName() {
		String val = getBaseTableName() + "." + UasDaoGroupTbl.CD_ID.getName();
		return val;
	}

	public String getCountNameQual(String name) {
		String qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoGroupTbl.CD_NAME.getName() + "= '" + name + "'";
		if (_logger.isDebugEnabled())
			_logger.debug("getCountNameQual: " + qual);
		return qual;
	}

	public String getCountNameIdQual(int id, String name) {
		String qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoGroupTbl.CD_NAME.getName() + "= '" + name + "' AND "
				+ getBaseTableName() + "." + UasDaoGroupTbl.CD_ID.getName()
				+ "<>" + Integer.toString(id);
		if (_logger.isDebugEnabled())
			_logger.debug("getCountNameIdQual: " + qual);
		return qual;
	}

	public String getLoadBaseQual(int id) {
		String qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoGroupTbl.CD_ID.getName() + "=" + Integer.toString(id);
		if (_logger.isDebugEnabled())
			_logger.debug("getLoadBaseQual: " + qual);
		return qual;
	}

	public String getDeleteBaseQual(int id) {
		String qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoGroupTbl.CD_ID.getName() + "=" + Integer.toString(id);
		if (_logger.isDebugEnabled())
			_logger.debug("getDeleteBaseQual: " + qual);
		return qual;
	}

	public String getUpdateMgrQual(int userId) {
		String qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoGroupTbl.CD_MGRID.getName() + "= "
				+ Integer.toString(userId);
		if (_logger.isDebugEnabled())
			_logger.debug("getUpdateMgrQual: " + qual);
		return qual;
	}

}
