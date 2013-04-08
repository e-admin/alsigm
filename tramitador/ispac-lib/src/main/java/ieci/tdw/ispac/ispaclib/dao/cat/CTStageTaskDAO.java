package ieci.tdw.ispac.ispaclib.dao.cat;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

public class CTStageTaskDAO extends ObjectDAO
{

	static final String TABLENAME 	= "SPAC_CT_FSTR";
	static final String IDSEQUENCE 	= "SPAC_SQ_ID_CTFSTR";
	static final String IDKEY 		= "ID";
	
	/**
	 * @throws ISPACException
	 */
	public CTStageTaskDAO() throws ISPACException {
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public CTStageTaskDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
	
	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public CTStageTaskDAO(DbCnt cnt, int id) throws ISPACException {
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
	
	public static boolean exist(DbCnt cnt, int ctStageId, int ctTaskId) throws ISPACException {
		
		String sql = "WHERE ID_FASE = " + ctStageId + " AND ID_TRAMITE = " + ctTaskId;
		
		CollectionDAO collection = new CollectionDAO(CTStageTaskDAO.class);
		int count = collection.count(cnt,sql);
		if (count > 0) {
			return true;
		}
			
		return false;
	}

}