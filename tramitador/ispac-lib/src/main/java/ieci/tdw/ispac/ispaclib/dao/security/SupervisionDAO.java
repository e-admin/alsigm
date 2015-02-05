/*
 * Created on Aug 31, 2004 by IECISA
 *
 */
package ieci.tdw.ispac.ispaclib.dao.security;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import java.util.Iterator;
import java.util.Map;

public class SupervisionDAO extends ObjectDAO
{
	public static final String TABLENAME = "SPAC_SS_SUPERVISION";
	static final String IDSEQUENCE = "SPAC_SQ_ID_SSSUPERV";
	static final String IDKEY = "ID";

	/**
	 * 
	 * @throws ISPACException
	 */
	public SupervisionDAO() throws ISPACException {
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public SupervisionDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
	
	/**
	 * 
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public SupervisionDAO(DbCnt cnt, int id) throws ISPACException {
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

  	static public CollectionDAO getSuperviseds(DbCnt cnt, String supervisorUID, String type)
	throws ISPACException
	{
		String sql = "WHERE UID_SUPERVISOR = '" + DBUtil.replaceQuotes(supervisorUID) + "'";
		
		if (type != null) {
			sql += " AND TIPO = " + type;
		}
		
		CollectionDAO objlist=new CollectionDAO(SupervisionDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

  	static public CollectionDAO getSupervisors(DbCnt cnt, String supervisedUID, String type)
	throws ISPACException
	{
		String sql = "WHERE UID_SUPERVISADO = '" + DBUtil.replaceQuotes(supervisedUID) + "'";
		
		if (type != null) {
			sql += " AND TIPO = " + type;
		}
		
		CollectionDAO objlist=new CollectionDAO(SupervisionDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}
  
  	static public CollectionDAO getSuperviseds(DbCnt cnt, Map supervisorsUID, String type)
	throws ISPACException
	{
		String uids = getSupervisorsUID(supervisorsUID);
		
		String sql = "WHERE " + DBUtil.addInResponsibleCondition("UID_SUPERVISOR", uids);
		
		if (type != null) {
			sql += " AND TIPO = " + type;
		}
		
		CollectionDAO objlist=new CollectionDAO(SupervisionDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

  	static public boolean isSupervised(DbCnt cnt, String supervisorUID, String supervisedUID, String type)
  	throws ISPACException
  	{
		String sql = "WHERE UID_SUPERVISADO = '" + DBUtil.replaceQuotes(supervisedUID) 
				   + "' AND UID_SUPERVISOR = '" + DBUtil.replaceQuotes(supervisorUID) + "'";
		
		if (type != null) {
			sql += " AND TIPO = " + type;
		}
		
		CollectionDAO objlist=new CollectionDAO(SupervisionDAO.class);
		return objlist.count(cnt, sql)>0;
	}
  
  	static public boolean isSupervised(DbCnt cnt, Map supervisorsUID, String supervisedUID, String type)
	throws ISPACException
	{
	  	String uids = getSupervisorsUID(supervisorsUID);
	  	
		String sql = "WHERE UID_SUPERVISADO = '" + DBUtil.replaceQuotes(supervisedUID) 
				   + "' AND "
				   + DBUtil.addInResponsibleCondition("UID_SUPERVISOR", uids);
		
		if (type != null) {
			sql += " AND TIPO = " + type;
		}
		
		CollectionDAO objlist=new CollectionDAO(SupervisionDAO.class);
		return objlist.count(cnt, sql)>0;
	}
  
  	static public String getSupervisorsUID(Map supervisorsUID) {
	  
		String uids = "";
		Iterator it = supervisorsUID.keySet().iterator();
		while (it.hasNext()) {
			
			String uid = (String) it.next();
			uids += "'" + DBUtil.replaceQuotes(uid) + "', ";
		}
		
		return uids.substring(0, uids.length() - 2);
  	}

}