package ieci.tdw.ispac.ispaclib.dao.cat;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

public class CTTaskTpDocDAO extends ObjectDAO {

	static final String TBNAME	= "SPAC_CT_TRTD";
	static final String SQNAME	= "SPAC_SQ_ID_CTTRTD";
	static final String PKNAME	= "ID";

	/**
	 * 
	 * @throws ISPACException
	 */
	public CTTaskTpDocDAO()	throws ISPACException {
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public CTTaskTpDocDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	/**
	 * 
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public CTTaskTpDocDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id, null);
	}

	public String getTableName() {
		return TBNAME;
	}

	protected String getDefaultSQL(int nActionDAO)
	throws ISPACException
	{
		return " WHERE " + PKNAME + " = " + getInt(PKNAME);
	}

	protected void newObject(DbCnt cnt)
	throws ISPACException
	{
		set(PKNAME, IdSequenceMgr.getIdSequence(cnt, SQNAME));
	}

	public String getKeyName()
	throws ISPACException
	{
		return PKNAME;
	}
	
	
	public static int countTaskTpDoc(DbCnt cnt , String idTask)throws ISPACException{
		
		String sql = "WHERE ID_TRAMITE = " + idTask;

		CollectionDAO collection = new CollectionDAO(CTTaskTpDocDAO.class);
		return collection.count(cnt, sql);
	}
	public static CollectionDAO getTaskTpDoc(DbCnt cnt,int taskId)
	throws ISPACException
	{
		String sql = "WHERE ID_TRAMITE = " + taskId;

		CollectionDAO collection = new CollectionDAO(CTTaskTpDocDAO.class);
		collection.query(cnt,sql);
		return collection;
	}
}