package ieci.tecdoc.sgm.usuarios_backoffice.database;

import ieci.tecdoc.sbo.uas.std.UasDaoDeptTbl;

import org.apache.log4j.Logger;

public class DepartmentsTable {

	private static final Logger _logger = Logger.getLogger(DepartmentsTable.class);

    public DepartmentsTable() {
	}

	public String getBaseTableName() {
		return "IUSERDEPTHDR";
	}

	public String getInsertBaseColumnNames() {
		String val = UasDaoDeptTbl.CD_ID.getName()
				+ "," + UasDaoDeptTbl.CD_NAME.getName() 
				+ "," + UasDaoDeptTbl.CD_PARENTID.getName() 
				+ "," + UasDaoDeptTbl.CD_MGRID.getName()
				+ "," + UasDaoDeptTbl.CD_TYPE.getName() 
				+ "," + UasDaoDeptTbl.CD_REMARKS.getName() 
				+ "," + UasDaoDeptTbl.CD_CRTRID.getName()
				+ "," + UasDaoDeptTbl.CD_CRTNDATE.getName();
		return val;
	}

	public String getUpdateBaseColumnNames() {
		String val = UasDaoDeptTbl.CD_NAME.getName()
				+ "," + UasDaoDeptTbl.CD_MGRID.getName() 
				+ "," + UasDaoDeptTbl.CD_PARENTID.getName() 
				+ "," + UasDaoDeptTbl.CD_REMARKS.getName()
				+ "," + UasDaoDeptTbl.CD_UPDRID.getName() 
				+ "," + UasDaoDeptTbl.CD_UPDATE.getName();
		return val;
	}

	public String getUpdateMgrColumnNames() {
		String val = UasDaoDeptTbl.CD_MGRID.getName() 
				+ "," + UasDaoDeptTbl.CD_UPDRID.getName() 
				+ "," + UasDaoDeptTbl.CD_UPDATE.getName();
		return val;
	}

	public String getLoadBaseColumnNames() {
		String val = getBaseTableName() + "." + UasDaoDeptTbl.CD_ID.getName()
				+ "," + getBaseTableName() + "."
				+ UasDaoDeptTbl.CD_NAME.getName() + "," + getBaseTableName()
				+ "." + UasDaoDeptTbl.CD_PARENTID.getName() + ","
				+ getBaseTableName() + "." + UasDaoDeptTbl.CD_MGRID.getName()
				+ "," + getBaseTableName() + "."
				+ UasDaoDeptTbl.CD_TYPE.getName() + "," + getBaseTableName()
				+ "." + UasDaoDeptTbl.CD_REMARKS.getName() + ","
				+ getBaseTableName() + "." + UasDaoDeptTbl.CD_CRTRID.getName()
				+ "," + getBaseTableName() + "."
				+ UasDaoDeptTbl.CD_CRTNDATE.getName() + ","
				+ getBaseTableName() + "." + UasDaoDeptTbl.CD_UPDRID.getName()
				+ "," + getBaseTableName() + "."
				+ UasDaoDeptTbl.CD_UPDATE.getName();
		return val;
	}

	public String getLoadBaseIdNameColumnNames() {
		String val = getBaseTableName() + "." + UasDaoDeptTbl.CD_ID.getName()
				+ "," + getBaseTableName() + "."
				+ UasDaoDeptTbl.CD_NAME.getName();
		return val;
	}

	public String getLoadBaseIdNameParentColumnNames() {
		String val = getBaseTableName() + "." + UasDaoDeptTbl.CD_ID.getName()
				+ "," + getBaseTableName() + "."
				+ UasDaoDeptTbl.CD_NAME.getName() + "," + getBaseTableName()
				+ "." + UasDaoDeptTbl.CD_PARENTID.getName();
		return val;
	}

	public String getParentIdDeptColumnName() {
		String val = getBaseTableName() + "."
				+ UasDaoDeptTbl.CD_PARENTID.getName();
		return val;
	}

	public String getMgrDeptColumnName() {
		String val = getBaseTableName() + "."
				+ UasDaoDeptTbl.CD_MGRID.getName();
		return val;
	}

	public String getDeptIdColumnName() {
		String val = getBaseTableName() + "." + UasDaoDeptTbl.CD_ID.getName();
		return val;
	}

	public String getUpdateMgrQual(int userId) {
		String qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoDeptTbl.CD_MGRID.getName() + "= "
				+ Integer.toString(userId);
		if (_logger.isDebugEnabled())
			_logger.debug("getUpdateMgrQual: " + qual);
		return qual;
	}

	public String getCountNameQual(String name) {
		String qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoDeptTbl.CD_NAME.getName() + "= '" + name + "'";
		if (_logger.isDebugEnabled())
			_logger.debug("getCountNameQual: " + qual);
		return qual;
	}

	public String getCountNameIdQual(int id, String name) {
		String qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoDeptTbl.CD_NAME.getName() + "= '" + name + "' AND "
				+ getBaseTableName() + "." + UasDaoDeptTbl.CD_ID.getName()
				+ "<>" + Integer.toString(id);
		if (_logger.isDebugEnabled())
			_logger.debug("getCountNameIdQual: " + qual);
		return qual;
	}

	public String getLoadBaseQual(int id) {
		String qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoDeptTbl.CD_ID.getName() + "=" + Integer.toString(id);
		if (_logger.isDebugEnabled())
			_logger.debug("getLoadBaseQual: " + qual);
		return qual;
	}

	public String getDeleteBaseQual(int id) {
		String qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoDeptTbl.CD_ID.getName() + "=" + Integer.toString(id);
		if (_logger.isDebugEnabled())
			_logger.debug("getDeleteBaseQual: " + qual);
		return qual;
	}

	public String getParentIdQual(int parentId) {
		String qual = "WHERE " + getBaseTableName() + "."
				+ UasDaoDeptTbl.CD_PARENTID.getName() + "="
				+ Integer.toString(parentId);
		if (_logger.isDebugEnabled())
			_logger.debug("getParentIdQual: " + qual);
		return qual;
	}

}
