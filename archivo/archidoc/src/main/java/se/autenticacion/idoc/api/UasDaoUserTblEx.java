package se.autenticacion.idoc.api;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.core.db.DbUtil;

import java.util.List;

/**
 * Acceso a la información de usuarios.
 */
public class UasDaoUserTblEx {

	public static final String TN = "IUSERUSERHDR";
	public static final DbColumnDef CD_ID = new DbColumnDef("ID", 4, false);
	public static final DbColumnDef CD_NAME = new DbColumnDef("NAME", 1, 32,
			false);
	public static final DbColumnDef CD_PASSWORD = new DbColumnDef("PASSWORD",
			1, 68, false);
	public static final DbColumnDef CD_DEPTID = new DbColumnDef("DEPTID", 4,
			false);
	public static final DbColumnDef CD_FLAGS = new DbColumnDef("FLAGS", 4,
			false);
	public static final DbColumnDef CD_STAT = new DbColumnDef("STAT", 4, false);
	public static final DbColumnDef CD_NUMBADCNTS = new DbColumnDef(
			"NUMBADCNTS", 4, false);
	public static final DbColumnDef CD_REMARKS = new DbColumnDef("REMARKS", 1,
			254, true);
	public static final DbColumnDef CD_CRTRID = new DbColumnDef("CRTRID", 4,
			false);
	public static final DbColumnDef CD_CRTNDATE = new DbColumnDef("CRTNDATE",
			7, false);
	public static final DbColumnDef CD_UPDRID = new DbColumnDef("UPDRID", 4,
			true);
	public static final DbColumnDef CD_UPDATE = new DbColumnDef("UPDDATE", 7,
			true);

	private static final DbColumnDef ACD[] = (new DbColumnDef[] { CD_ID,
			CD_NAME, CD_PASSWORD, CD_DEPTID, CD_FLAGS, CD_STAT, CD_NUMBADCNTS,
			CD_REMARKS, CD_CRTRID, CD_CRTNDATE, CD_UPDRID, CD_UPDATE });
	protected static final String ACN = DbUtil.getColumnNames(ACD);
	private static final String OCN = DbUtil.getColumnNames(CD_ID, CD_NAME,
			CD_PASSWORD, CD_DEPTID, CD_STAT, CD_NUMBADCNTS, CD_REMARKS);
	protected static final String UaCN = DbUtil.getColumnNames(CD_STAT,
			CD_NUMBADCNTS);

	private static String getDefaultQual(int id) {
		return new StringBuffer().append("WHERE ").append(CD_ID.getName())
				.append("=").append(id).toString();
	}

	public static UasDaoUserRecO selectRecO(int id) throws Exception {
		UasDaoUserRecO rec = new UasDaoUserRecO();
		DbSelectFns.select("IUSERUSERHDR", OCN, getDefaultQual(id), rec);
		return rec;
	}

	public static List selectRecOList(String name) throws Exception {
		UasDaoUserRecOArrayList recordSet = new UasDaoUserRecOArrayList();

		String qual = new StringBuffer().append("WHERE UPPER(")
				.append(CD_NAME.getName()).append(") LIKE '%")
				.append(name.toUpperCase()).append("%' ORDER BY ")
				.append(CD_NAME.getName()).toString();

		DbSelectFns.select(TN, OCN, qual, true, recordSet);

		return recordSet;
	}

}
