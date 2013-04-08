package ieci.tdw.ispac.ispaclib.dao.publisher;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;


/**
 * @author david
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PubReglaDAO extends ObjectDAO
{
	static final String TABLENAME 	= "PUB_REGLAS";
	static final String IDSEQUENCE 	= "PUB_SQ_ID_REGLAS";
	static final String IDKEY 		= "ID";

	/**
	 * @throws ISPACException
	 */
	public PubReglaDAO() throws ISPACException {
		super(null);
	}

	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public PubReglaDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public PubReglaDAO(DbCnt cnt, int id) throws ISPACException	{
		super(cnt, id, null);
	}


	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispactx.dao.ObjectDAO#getTableName()
	 */
	public String getTableName()
	{
		return TABLENAME;
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispactx.dao.ObjectDAO#getDefaultSQL(int)
	 */
	protected String getDefaultSQL(int nActionDAO)
	throws ISPACException
	{
		return " WHERE " + IDKEY + " = " + getInt(IDKEY);
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispactx.dao.ObjectDAO#newObject(ieci.tdw.ispac.ispactx.dbx.DbCnt)
	 */
	protected void newObject(DbCnt cnt) throws ISPACException
	{
		set(IDKEY,IdSequenceMgr.getIdSequence(cnt,IDSEQUENCE));
	}

	public String getKeyName() throws ISPACException
	{
		return IDKEY;
	}

	public static PubReglaDAO getEvent(DbCnt cnt, int id)throws ISPACException
	{
	    String sql="WHERE ID = " + id;
	    CollectionDAO objlist=new CollectionDAO(PubReglaDAO.class);
	    objlist.query(cnt,sql);
	    if (objlist.next())
	        return (PubReglaDAO)objlist.value();
        return null;
	}

	public static PubReglaDAO getEventRulePred(DbCnt cnt, int pcdId, int stageId, int taskId, int typeDoc, int eventId, int infoId, int order)
	throws ISPACException
	{
	    String sql = "WHERE ID_PCD = " + pcdId
	    		   + " AND ID_FASE = " + stageId
	    		   + " AND ID_TRAMITE = " + taskId
	    		   + " AND TIPO_DOC = " + typeDoc
	    		   + " AND ID_EVENTO = " + eventId
	    		   + " AND ID_INFO = " + infoId
	    		   + " AND ORDEN >= " + (order + 1) + " ORDER BY ORDEN";
	    CollectionDAO objlist=new CollectionDAO(PubReglaDAO.class);
	    objlist.query(cnt,sql);
	    if (objlist.next())
	        return (PubReglaDAO)objlist.value();
        return null;
	}

	public static PubReglaDAO getEventRuleAnt(DbCnt cnt, int pcdId, int stageId, int taskId, int typeDoc, int eventId, int infoId, int order)
	throws ISPACException
	{
	    String sql = "WHERE ID_PCD = " + pcdId
				   + " AND ID_FASE = " + stageId
				   + " AND ID_TRAMITE = " + taskId
				   + " AND TIPO_DOC = " + typeDoc
				   + " AND ID_EVENTO = " + eventId
				   + " AND ID_INFO = " + infoId
				   + " AND ORDEN <= " + (order - 1) + " ORDER BY ORDEN DESC";
	    CollectionDAO objlist=new CollectionDAO(PubReglaDAO.class);
	    objlist.query(cnt,sql);
	    if (objlist.next())
	        return (PubReglaDAO)objlist.value();
	    return null;
	}

	public static CollectionDAO getRulesAsignedOnlyProcedure(DbCnt cnt, int procedureId)
	throws ISPACException
	{
	    String sql = "WHERE ID_PCD = " + procedureId
	    		   + " AND ID_FASE <= 0";
	    CollectionDAO objlist=new CollectionDAO(PubReglaDAO.class);
	    objlist.query(cnt,sql);
	    return objlist;
	}

	public static CollectionDAO getRulesAsignedOnlyStage(DbCnt cnt, int stageId)
	throws ISPACException
	{
	    String sql = "WHERE ID_FASE = " + stageId
	    		   + " AND ID_TRAMITE <= 0";
	    CollectionDAO objlist=new CollectionDAO(PubReglaDAO.class);
	    objlist.query(cnt,sql);
	    return objlist;
	}

	public static CollectionDAO getRulesByTask(DbCnt cnt, int taskId)
	throws ISPACException
	{
	    String sql = "WHERE ID_TRAMITE = " + taskId;
	    CollectionDAO objlist=new CollectionDAO(PubReglaDAO.class);
	    objlist.query(cnt,sql);
	    return objlist;
	}
}