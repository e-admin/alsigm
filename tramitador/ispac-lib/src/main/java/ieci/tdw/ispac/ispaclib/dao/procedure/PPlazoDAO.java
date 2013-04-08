/*
 * Created on 22-jun-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.ispaclib.dao.procedure;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;


/**
 * @author juanin
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PPlazoDAO extends ObjectDAO
{

	static final String TABLENAME 	= "SPAC_P_PLAZOS";
	static final String IDSEQUENCE 	= "SPAC_SQ_ID_PPLAZOS";
	static final String IDKEY 		= "ID";

	/**
	 * 
	 * @throws ISPACException
	 */
	public PPlazoDAO() throws ISPACException {
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public PPlazoDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public PPlazoDAO(DbCnt cnt, int id)	throws ISPACException {
		super(cnt, id, null);
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispaclib.dao.ObjectDAO#getTableName()
	 */
	public String getTableName()
	{
		return TABLENAME;
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispaclib.dao.ObjectDAO#getDefaultSQL(int)
	 */
	protected String getDefaultSQL(int nActionDAO) throws ISPACException
	{
		return " WHERE " + IDKEY + " = " + getInt(IDKEY);
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispaclib.dao.ObjectDAO#newObject(ieci.tdw.ispac.ispaclib.dbx.DbCnt)
	 */
	protected void newObject(DbCnt cnt) throws ISPACException
	{
		set(IDKEY,IdSequenceMgr.getIdSequence(cnt,IDSEQUENCE));
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispaclib.dao.ObjectDAO#getKeyName()
	 */
	public String getKeyName() throws ISPACException
	{
		return IDKEY;
	}

	public static CollectionDAO getDeadline(DbCnt cnt,int TypeObj,int nIdObj)
	throws ISPACException
	{
		String sql="WHERE ID = (SELECT ID FROM SPAC_P_RELPLAZOS WHERE TP_OBJ = "+TypeObj+" AND ID_OBJ = "+nIdObj+") ";
		CollectionDAO objlist=new CollectionDAO(PPlazoDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}
}
