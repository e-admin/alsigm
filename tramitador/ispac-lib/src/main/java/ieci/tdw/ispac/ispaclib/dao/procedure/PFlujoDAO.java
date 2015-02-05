/*
 * Created on 03-may-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.ispaclib.dao.procedure;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;


/**
 * @author juanin
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PFlujoDAO extends ObjectDAO
{
	static final String TABLENAME 	= "SPAC_P_FLUJOS";
	static final String IDSEQUENCE 	= "SPAC_SQ_ID_PFLUJOS";
	static final String IDKEY 		= "ID";
	
	/**
	 * @param tableColumns
	 * @throws ISPACException
	 */
	public PFlujoDAO() throws ISPACException {
		super(null);
	}

	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public PFlujoDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
	
	/**
	 * @param cnt
	 * @param id
	 * @param tableColumns
	 * @throws ISPACException
	 */
	public PFlujoDAO(DbCnt cnt, int id, String[] tableColumns) throws ISPACException {
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
	
	public int getNodeStart() throws ISPACException
	{
		return getInt("ID_ORIGEN");
	}

	public int getNodeEnd() throws ISPACException
	{
		return getInt("ID_DESTINO");
	}
	
	public static boolean isFirstNode(DbCnt cnt, int procedureId, int nodeId) throws ISPACException{
	    String sql = "WHERE ID_PCD = " + procedureId + " AND ID_DESTINO = " + nodeId;
		CollectionDAO objlist=new CollectionDAO(PFlujoDAO.class);
		return objlist.count(cnt,sql) == 0;
	}
	
	public static boolean isLastNode(DbCnt cnt, int procedureId, int nodeId) throws ISPACException{
	    String sql = "WHERE ID_PCD = " + procedureId + " AND ID_ORIGEN = " + nodeId;
		CollectionDAO objlist=new CollectionDAO(PFlujoDAO.class);
		return objlist.count(cnt,sql) == 0;
	}

	public static boolean checkFlowInstance(DbCnt cnt, int procedureId, int origId, int destId) throws ISPACException{
	    String sql = "WHERE ID_PCD = " + procedureId + " AND ID_ORIGEN = " + origId + " AND ID_DESTINO = " + destId;
		CollectionDAO objlist=new CollectionDAO(PFlujoDAO.class);
		return objlist.count(cnt,sql) > 0;
	}
	
}
