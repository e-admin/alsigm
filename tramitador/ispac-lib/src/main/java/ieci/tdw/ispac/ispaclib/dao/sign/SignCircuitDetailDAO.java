package ieci.tdw.ispac.ispaclib.dao.sign;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

public class SignCircuitDetailDAO extends ObjectDAO
{
	static final String TABLENAME = "SPAC_CTOS_FIRMA_DETALLE";
	static final String IDSEQUENCE = "SPAC_SQ_ID_CTOS_FIRMA_DETALLE";
	static final String IDKEY = "ID";

	/**
	 * 
	 * @throws ISPACException
	 */
	public SignCircuitDetailDAO() throws ISPACException	{
		super(null);
	}

	/**
	 * 
	 * @param cnt
	 * @throws ISPACException
	 */
	public SignCircuitDetailDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	/**
	 * 
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public SignCircuitDetailDAO(DbCnt cnt, int id) throws ISPACException {
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

	static  public CollectionDAO getSteps(DbCnt cnt, int id)
    throws ISPACException
	{
	    String sql="WHERE ID_CIRCUITO = " + id + " ORDER BY ID_PASO ASC";
	    
		CollectionDAO objlist=new CollectionDAO(SignCircuitDetailDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	public static SignCircuitDetailDAO getStep(DbCnt cnt, int circuitId, int stepId) throws ISPACException {
	    String sql = "WHERE ID_CIRCUITO=" + circuitId + " AND ID_PASO=" + stepId;
	    
		CollectionDAO objlist = new CollectionDAO(SignCircuitDetailDAO.class);
		objlist.query(cnt,sql);
		if (objlist.next()) {
			return (SignCircuitDetailDAO) objlist.value();
		}
		return null;
	}
	
	public static int countSteps(DbCnt cnt, int id) throws ISPACException {
	    String sql="WHERE ID_CIRCUITO = " + id;
	    
		CollectionDAO objlist = new CollectionDAO(SignCircuitDetailDAO.class);
		return objlist.count(cnt, sql);
	}

}
