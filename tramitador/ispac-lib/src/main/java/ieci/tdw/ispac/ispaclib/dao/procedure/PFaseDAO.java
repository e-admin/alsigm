package ieci.tdw.ispac.ispaclib.dao.procedure;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.dao.tx.TXFaseDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;


public class PFaseDAO extends ObjectDAO
{
	static final String TABLENAME 	= "SPAC_P_FASES";
	static final String IDSEQUENCE 	= "SPAC_SQ_ID_PNODOS";
	static final String IDKEY 		= "ID";
	
	/**
	 * @throws ISPACException
	 */
	public PFaseDAO() throws ISPACException {
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public PFaseDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
	
	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public PFaseDAO(DbCnt cnt, int id) throws ISPACException {
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


	public CollectionDAO getTasks(DbCnt cnt)
	throws ISPACException
	{
		String sql="WHERE ID_PCD = "+getInt("ID_PCD")+
					" AND ID_FASE = "+getInt(IDKEY)+
					" ORDER BY ORDEN";

		CollectionDAO objlist=new CollectionDAO(PTramiteDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	public static CollectionDAO getTasks(DbCnt cnt,int nIdFasePCD)
	throws ISPACException
	{
		String sql="WHERE ID_FASE = "+nIdFasePCD + " ORDER BY ORDEN";

		CollectionDAO objlist=new CollectionDAO(PTramiteDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	public static CollectionDAO getTasksToCreate(DbCnt cnt,int nIdFasePCD)
	throws ISPACException
	{
		String sql="WHERE ID_FASE = "+nIdFasePCD + " ORDER BY ORDEN";

		CollectionDAO objlist=new CollectionDAO(PTramiteDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	public CollectionDAO getStageTpDocs(DbCnt cnt)
	throws ISPACException
	{
		String sql="WHERE ID_FASE = "+getInt(IDKEY);
		CollectionDAO objlist=new CollectionDAO(PFaseTDocDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	public CollectionDAO getStageApps(DbCnt cnt)
	throws ISPACException
	{
		String sql="WHERE ID_FASE = "+getInt(IDKEY);
		CollectionDAO objlist=new CollectionDAO(PFrmFaseDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	public CollectionDAO getStageTasksApps(DbCnt cnt)
	throws ISPACException
	{
		String sql="WHERE ID_TRAMITE IN "+
			"(SELECT ID FROM SPAC_P_TRAMITES WHERE ID_FASE = "+getInt(IDKEY)+")";
		CollectionDAO objlist=new CollectionDAO(PFrmFaseDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	public static boolean checkStageInstances(DbCnt cnt, int pstageId)
			throws ISPACException {
		
		String sql = "WHERE ID_FASE = " + pstageId;

		CollectionDAO collection = new CollectionDAO(TXFaseDAO.class);
		return (collection.count(cnt,sql)>0);
	}

	public static boolean checkStageInstances(DbCnt cnt, int pcdId, String stageName)
			throws ISPACException {

		String sql = "WHERE ID_PCD=" + pcdId 
				   + " AND NOMBRE='" + DBUtil.replaceQuotes(stageName) + "'";

		CollectionDAO collection = new CollectionDAO(PFaseDAO.class);
		return (collection.count(cnt, sql) > 0);
	}
}
