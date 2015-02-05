package ieci.tecdoc.sgm.usuarios_backoffice.database;

import ieci.tecdoc.sgm.base.dbex.DbColumnDef;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbSelectFns;
import ieci.tecdoc.sgm.base.dbex.DbUtil;
import ieci.tecdoc.sgm.core.db.DataSourceManagerMultientidad;

import java.util.List;

/**
 * Acceso a la información de usuarios. 
 */
public class UasDaoUserTblEx {

	public static final String TN = "IUSERUSERHDR";
	
	public static final DbColumnDef CD_ID = new DbColumnDef("ID", 4, false);
	public static final DbColumnDef CD_NAME = new DbColumnDef("NAME", 1, 32, false);
	public static final DbColumnDef CD_PASSWORD = new DbColumnDef("PASSWORD", 1, 68, false);
	public static final DbColumnDef CD_DEPTID = new DbColumnDef("DEPTID", 4, false);
	public static final DbColumnDef CD_FLAGS = new DbColumnDef("FLAGS", 4, false);
	public static final DbColumnDef CD_STAT = new DbColumnDef("STAT", 4, false);
	public static final DbColumnDef CD_NUMBADCNTS = new DbColumnDef("NUMBADCNTS", 4, false);
	public static final DbColumnDef CD_REMARKS = new DbColumnDef("REMARKS", 1, 254, true);
	public static final DbColumnDef CD_CRTRID = new DbColumnDef("CRTRID", 4, false);
	public static final DbColumnDef CD_CRTNDATE = new DbColumnDef("CRTNDATE", 7, false);
	public static final DbColumnDef CD_UPDRID = new DbColumnDef("UPDRID", 4, true);
	public static final DbColumnDef CD_UPDATE = new DbColumnDef("UPDDATE", 7, true);
	public static final DbColumnDef CD_PWDLASTUPDTS = new DbColumnDef("PWDLASTUPDTS", 6, false);
	public static final DbColumnDef CD_PWDMBC = new DbColumnDef("PWDMBC", 1, 1, false);
	public static final DbColumnDef CD_PWDVPCHECK = new DbColumnDef("PWDVPCHECK", 1, 1, false);

	private static final DbColumnDef ACD[] = (new DbColumnDef[] { CD_ID,
			CD_NAME, CD_PASSWORD, CD_DEPTID, CD_FLAGS, CD_STAT, CD_NUMBADCNTS,
			CD_REMARKS, CD_CRTRID, CD_CRTNDATE, CD_UPDRID, CD_UPDATE,
			CD_PWDLASTUPDTS, CD_PWDMBC, CD_PWDVPCHECK });
	private static final String ACN = DbUtil.getColumnNames(ACD);
	private static final String OCN = DbUtil.getColumnNames(CD_ID, CD_NAME,
			CD_PASSWORD, CD_DEPTID, CD_STAT, CD_NUMBADCNTS, CD_REMARKS);
	private static final String UaCN = DbUtil.getColumnNames(CD_STAT,
			CD_NUMBADCNTS);

	private static String getDefaultQual(int id) {
		return new StringBuffer("WHERE ")
			.append(CD_ID.getName()).append("=").append(id)
			.toString();
	}

	public static UasDaoUserRecO selectRecO(int id, String entidad) throws Exception {
		DbConnection dbConn = new DbConnection();
		try {
			dbConn.open(DBSessionManager.getSession(DataSourceManagerMultientidad.BACKOFFICE_DATASOURCE_NAME, entidad));
			UasDaoUserRecO rec = new UasDaoUserRecO();
			DbSelectFns.select(dbConn, "IUSERUSERHDR", OCN, getDefaultQual(id), rec);
			return rec;
		} finally {
			dbConn.close();
		}
	}

	public static List selectRecOList(String name, String entidad) throws Exception {
		UasDaoUserRecOArrayList recordSet = new UasDaoUserRecOArrayList();

		StringBuffer qual = new StringBuffer();
		
		if ((name != null) && (name.trim().length() > 0)) {
			qual.append("WHERE ")
				.append(CD_NAME.getName())
				.append(" LIKE '%").append(name).append("%'");
		}			
		
		qual.append("ORDER BY ").append(CD_NAME.getName());
		
		DbConnection dbConn = new DbConnection();
		try {
			dbConn.open(DBSessionManager.getSession(DataSourceManagerMultientidad.BACKOFFICE_DATASOURCE_NAME, entidad));
			DbSelectFns.select(dbConn, TN, OCN, qual.toString(), true, recordSet);
			return recordSet;
		} finally {
			dbConn.close();
		}
	}

}
