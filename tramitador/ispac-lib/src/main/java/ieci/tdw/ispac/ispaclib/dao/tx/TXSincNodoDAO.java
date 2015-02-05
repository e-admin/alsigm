/*
 * Created on 12-may-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.ispaclib.dao.tx;

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
public class TXSincNodoDAO extends ObjectDAO
{
	static final String TABLENAME 	= "SPAC_SINCNODO";
	static final String IDSEQUENCE 	= "SPAC_SQ_ID_SINCNODOS";
	static final String IDKEY 		= "ID";
	

	/**
	 * @throws ISPACException
	 */
	public TXSincNodoDAO() throws ISPACException {
		super(null);
	}

	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public TXSincNodoDAO(DbCnt cnt)	throws ISPACException {
		super(cnt, null);
	}
	
	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public TXSincNodoDAO(DbCnt cnt, int id)	throws ISPACException {
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
	 * @see ieci.tdw.ispac.ispactx.dao.ObjectDAO#getDefaultSQL(i	nt)
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
