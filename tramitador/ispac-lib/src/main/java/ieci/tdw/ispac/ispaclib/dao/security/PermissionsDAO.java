/*
 * Created on Oct 21, 2004 by IECISA
 *
 */
package ieci.tdw.ispac.ispaclib.dao.security;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

public class PermissionsDAO extends ObjectDAO
{
	static final String TABLENAME = "SPAC_SS_PERMISOS";
	static final String IDSEQUENCE = "SPAC_SQ_ID_SSPERMISOS";
	static final String IDKEY = "ID";

	/**
	 * 
	 * @throws ISPACException
	 */
	public PermissionsDAO() throws ISPACException {
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public PermissionsDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
	
	/**
	 * 
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public PermissionsDAO(DbCnt cnt, int id) throws ISPACException {
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


	static  public CollectionDAO getPermissionObjects(DbCnt cnt, String uid, int codePermission)
    throws ISPACException
	{
	    String sql="WHERE UID_USR = '"+ DBUtil.replaceQuotes(uid) + "' AND PERMISO = " + codePermission;
		CollectionDAO objlist=new CollectionDAO(PermissionsDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	static public CollectionDAO getPermissions(DbCnt cnt, String uid)
	    throws ISPACException
	{
		String sql="WHERE UID_USR = '"+ DBUtil.replaceQuotes(uid) + "'";
		CollectionDAO objlist=new CollectionDAO(PermissionsDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}


	static public CollectionDAO getPermissions(DbCnt cnt, int idpcd)
        throws ISPACException
    {
        String sql="WHERE ID_PCD = "+ idpcd+" ORDER BY UID_USR,PERMISO";
        CollectionDAO objlist=new CollectionDAO(PermissionsDAO.class);
        objlist.query(cnt,sql);
        return objlist;
    }

	static public CollectionDAO getPermissions(DbCnt cnt, int idpcd, String uid)
	    throws ISPACException
    {
        String sql="WHERE ID_PCD = " + idpcd + " AND UID_USR = '" + DBUtil.replaceQuotes(uid) + "'";
        CollectionDAO objlist=new CollectionDAO(PermissionsDAO.class);
        objlist.query(cnt,sql);
        return objlist;
    }
	
	static public CollectionDAO getPermissions(DbCnt cnt, int idpcd, int codePermission)
    	throws ISPACException
    {
	    String sql="WHERE ID_PCD = " + idpcd + " AND PERMISO = " + codePermission + " ORDER BY UID_USR";
	    CollectionDAO objlist=new CollectionDAO(PermissionsDAO.class);
	    objlist.query(cnt,sql);
	    return objlist;
    }
	
}
