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
import ieci.tdw.ispac.ispaclib.db.DbCnt;


/**
 * @author juanin
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PRelPlazoDAO extends ObjectDAO
{
    public static final int DEADLINE_OBJ_PROCEDURE=1;
    public static final int DEADLINE_OBJ_STAGE=2;
    public static final int DEADLINE_OBJ_TASK=3;

	static final String TABLENAME 	= "SPAC_P_RELPLAZOS";
	//static final String IDSEQUENCE 	= "SPAC_SQ_ID_PRELPLAZOS";
	static final String IDKEY 		= "ID";

	/**
	 * @throws ISPACException
	 */
	public PRelPlazoDAO() throws ISPACException	{
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public PRelPlazoDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
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
		//set(IDKEY,IdSequenceMgr.getIdSequence(cnt,IDSEQUENCE));
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispaclib.dao.ObjectDAO#getKeyName()
	 */
	public String getKeyName() throws ISPACException
	{
		return IDKEY;
	}

	public static CollectionDAO getRelDeadline(DbCnt cnt,int TypeObj,int nIdObj)
	throws ISPACException
	{
		String sql="WHERE TP_OBJ = "+TypeObj+" AND ID_OBJ = "+nIdObj;
		CollectionDAO objlist=new CollectionDAO(PRelPlazoDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}
}
