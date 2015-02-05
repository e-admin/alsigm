/*
 * SsVariableDAO.java
 *
 * Created on June 14, 2004, 12:47 PM
 */

package ieci.tdw.ispac.ispaclib.dao.session;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

public class SsVariableDAO extends ObjectDAO
{
  
	static final String TABLENAME 	= "SPAC_S_VARS";
	static final String IDSEQUENCE 	= "SPAC_SQ_ID_SVARS";
	static final String IDKEY 		= "ID";
  
	/**
	 * 
	 * @throws ISPACException
	 */
	public SsVariableDAO() throws ISPACException {
		super(null);
	}
	
	/**
	 * 
	 * @param cnt
	 * @throws ISPACException
	 */
	public SsVariableDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
  
	/**
	 * 
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public SsVariableDAO(DbCnt cnt, int id)	throws ISPACException {		
		super(cnt,id,null);
	}
  
  protected String getDefaultSQL(int nActionDAO) throws ISPACException
  {
    return " WHERE " + IDKEY + " = " + getInt(IDKEY);
  }
  
  public String getKeyName() throws ISPACException
  {
    return IDKEY;
  }
  
  public String getTableName()
  {
    return TABLENAME;
  }
  
  public void newObject(DbCnt cnt) throws ISPACException
  {
    set(IDKEY,IdSequenceMgr.getIdSequence(cnt,IDSEQUENCE));
  }
  
}
