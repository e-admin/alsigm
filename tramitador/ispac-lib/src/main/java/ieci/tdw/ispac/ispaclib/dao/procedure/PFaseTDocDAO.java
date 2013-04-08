/*
 * Created on 03-may-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.ispaclib.dao.procedure;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;


/**
 * @author juanin
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PFaseTDocDAO extends ObjectDAO
{
	static final String TABLENAME 	= "SPAC_P_FSTD";
	static final String IDSEQUENCE 	= "SPAC_SQ_ID_PFSTD";
	static final String IDKEY 		= "ID";
	
	/**
	 * @throws ISPACException
	 */
	public PFaseTDocDAO() throws ISPACException	{
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public PFaseTDocDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public PFaseTDocDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id, null);
	}

	public String getTableName()
	{
		return TABLENAME;
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispactx.dao.ObjectDAO#newObject(int)
	 */
	protected void newObject(DbCnt cnt)
		throws ISPACException
	{
		set(IDKEY,IdSequenceMgr.getIdSequence(cnt,IDSEQUENCE));
	}


	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispactx.dao.ObjectDAO#getDefaultSQL(int)
	 */
	protected String getDefaultSQL(int nActionDAO) throws ISPACException
	{
		return " WHERE " + IDKEY + " = " + getInt(IDKEY);
	}

	public String getKeyName() throws ISPACException
	{
		return IDKEY;
	}
}
