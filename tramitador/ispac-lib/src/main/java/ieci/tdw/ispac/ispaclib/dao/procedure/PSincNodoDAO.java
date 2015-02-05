package ieci.tdw.ispac.ispaclib.dao.procedure;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.dao.tx.TXSincNodoDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;


public class PSincNodoDAO extends ObjectDAO
{
	static final String TABLENAME 	= "SPAC_P_SINCNODO";
	static final String IDSEQUENCE 	= "SPAC_SQ_ID_PNODOS";
	static final String IDKEY 		= "ID";
	
	/**
	 * @throws ISPACException
	 */
	public PSincNodoDAO() throws ISPACException {
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public PSincNodoDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
	
	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public PSincNodoDAO(DbCnt cnt, int id) throws ISPACException {
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

	public static boolean checkSyncNodeInstances(DbCnt cnt, int psincNodeId)
			throws ISPACException {

		String sql = "WHERE ID_NODO = " + psincNodeId;

		CollectionDAO collection = new CollectionDAO(TXSincNodoDAO.class);
		return (collection.count(cnt, sql) > 0);
	}
	
}
