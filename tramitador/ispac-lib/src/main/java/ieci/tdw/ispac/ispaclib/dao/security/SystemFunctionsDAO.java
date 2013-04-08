/*
 * Created on Oct 21, 2004 by IECISA
 *
 */
package ieci.tdw.ispac.ispaclib.dao.security;

import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import java.util.List;

import org.springframework.util.CollectionUtils;

public class SystemFunctionsDAO extends ObjectDAO
{

	static final String TABLENAME = "SPAC_SS_FUNCIONES";
	static final String IDSEQUENCE = "SPAC_SQ_ID_SSFUNCIONES";
	static final String IDKEY = "ID";

	/**
	 * 
	 * @throws ISPACException
	 */
	public SystemFunctionsDAO() throws ISPACException {
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public SystemFunctionsDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	/**
	 * 
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public SystemFunctionsDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id, null);
	}

	public String getTableName()
	{
		return TABLENAME;
	}

  protected String getDefaultSQL(int nActionDAO) throws ISPACException
  {
    return " WHERE " + IDKEY + " = " + getInt(IDKEY);
  }

  public String getKeyName() throws ISPACException
  {
    return IDKEY;
  }

  public void newObject(DbCnt cnt) throws ISPACException
  {
    set(IDKEY,IdSequenceMgr.getIdSequence(cnt,IDSEQUENCE));
  }

  	public static CollectionDAO getFunctions(DbCnt cnt, String uid)
	throws ISPACException
	{
		String sql="WHERE UID_USR = '" + DBUtil.replaceQuotes(uid) + "'";
		CollectionDAO objlist=new CollectionDAO(SystemFunctionsDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	public static CollectionDAO getFunctions(DbCnt cnt, List<String> uidList)
			throws ISPACException {
		
		CollectionDAO objlist = new CollectionDAO(SystemFunctionsDAO.class);
		
		if (!CollectionUtils.isEmpty(uidList)) {
		
			StringBuffer sql = new StringBuffer("WHERE UID_USR='").append(
					DBUtil.replaceQuotes(uidList.get(0))).append("'");
			for (int i = 1; i < uidList.size(); i++) {
				sql.append(" OR UID_USR='")
						.append(DBUtil.replaceQuotes(uidList.get(i)))
						.append("'");
			}
			
			objlist.query(cnt, sql.toString());
		}
		
		return objlist;
	}

  	public static boolean isFunction(DbCnt cnt, String uid, int function)
	throws ISPACException
	{
		String sql="WHERE UID_USR = '" + DBUtil.replaceQuotes(uid) + "' AND FUNCION = " + function;
		CollectionDAO objlist=new CollectionDAO(SystemFunctionsDAO.class);
		return objlist.count(cnt, sql)>0;
	}
  
  	public static boolean isFunction(DbCnt cnt, List uids, int function)
	throws ISPACException
	{
	    String sUids = uids.toString();
	    sUids = sUids.substring(1, sUids.length() - 1);
		String sql="WHERE " + DBUtil.addInResponsibleCondition("UID_USR", sUids) 
			+ " AND FUNCION = " + function;
		CollectionDAO objlist=new CollectionDAO(SystemFunctionsDAO.class);
		return objlist.count(cnt, sql)>0;
	}
  	
  	public static boolean isSupervisor(DbCnt cnt, String uid)
	throws ISPACException
	{
		String sql = "WHERE UID_USR = '" + DBUtil.replaceQuotes(uid) 
				   + "' AND ( FUNCION = " + ISecurityAPI.FUNC_MONITORINGSUPERVISOR 
				   + " OR FUNCION = " + ISecurityAPI.FUNC_TOTALSUPERVISOR + " )";
		
		CollectionDAO objlist = new CollectionDAO(SystemFunctionsDAO.class);
		return objlist.count(cnt, sql) > 0;
	}

  	
  	private static boolean isSupervisor(DbCnt cnt, String uid, int type)
	throws ISPACException
	{
		String sql = "WHERE UID_USR = '" + DBUtil.replaceQuotes(uid) 
				   + "' AND FUNCION = " + type + " ";
		
		CollectionDAO objlist = new CollectionDAO(SystemFunctionsDAO.class);
		return objlist.count(cnt, sql) > 0;
	}  	

  	public static boolean isSupervisorTotal(DbCnt cnt, String uid)
	throws ISPACException
	{
		return isSupervisor(cnt, uid, ISecurityAPI.FUNC_TOTALSUPERVISOR);
	}  	
  	
  	public static boolean isSupervisorMonitoring(DbCnt cnt, String uid)
	throws ISPACException
	{
		return isSupervisor(cnt, uid, ISecurityAPI.FUNC_MONITORINGSUPERVISOR);
	}  	
  	
  	
}