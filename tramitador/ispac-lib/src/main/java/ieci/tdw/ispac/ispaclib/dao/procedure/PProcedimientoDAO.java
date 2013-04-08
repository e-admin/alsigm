package ieci.tdw.ispac.ispaclib.dao.procedure;

import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTTaskDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.dao.security.PermissionDAO;
import ieci.tdw.ispac.ispaclib.dao.security.PermissionsDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXTramiteDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

/**
 * DAO de acceso a la tabla SPAC_P_PROCEDIMIENTOS
 *
 */
public class PProcedimientoDAO extends ObjectDAO
{

	public static final String TABLENAME 	= "SPAC_P_PROCEDIMIENTOS";
	static final String IDSEQUENCE 	= "SPAC_SQ_ID_PPROCEDIMIENTOS";
	static final String IDKEY 		= "ID";
	
	/**
	 * 
	 * @throws ISPACException
	 */
	public PProcedimientoDAO() throws ISPACException {
		super(null);
	}

	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public PProcedimientoDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	/**
	 * 
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public PProcedimientoDAO(DbCnt cnt, int id)	throws ISPACException {
		super(cnt,id,null);
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

	public CollectionDAO getStages(DbCnt cnt)
	throws ISPACException
	{
		String sql="WHERE ID_PCD = "+getInt(IDKEY) + " ORDER BY ID";
		CollectionDAO objlist=new CollectionDAO(PFaseDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	public CollectionDAO getNodes(DbCnt cnt)
	throws ISPACException
	{
		String sql="WHERE ID_PCD = "+getInt(IDKEY);
		CollectionDAO objlist=new CollectionDAO(PNodoDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	public PNodoDAO getNode(DbCnt cnt, int nodeId)
	throws ISPACException
	{
		String sql="WHERE ID = "+nodeId;
		CollectionDAO objlist=new CollectionDAO(PNodoDAO.class);
		objlist.query(cnt,sql);
		if (!objlist.next())
		    return null;

		return (PNodoDAO)objlist.value();		
	}
	
	public PNodoDAO getNode(DbCnt cnt, String bpmNodeId)
	throws ISPACException
	{
		String sql="WHERE ID_PCD = " + getInt(IDKEY) + " AND ID_NODO_BPM = '" + bpmNodeId + "'";
		CollectionDAO objlist=new CollectionDAO(PNodoDAO.class);
		objlist.query(cnt,sql);
		if (!objlist.next())
		    return null;

		return (PNodoDAO)objlist.value();		
	}
	
	public CollectionDAO getNodesStage(DbCnt cnt)
	throws ISPACException
	{
		String sql="WHERE ID_PCD = " + getInt(IDKEY) + " AND TIPO = " + PNodoDAO.NODE_OBJ_STAGE;
		CollectionDAO objlist=new CollectionDAO(PNodoDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	public CollectionDAO getNodesSyncNode(DbCnt cnt)
	throws ISPACException
	{
		String sql="WHERE ID_PCD = "+getInt(IDKEY)+" AND TIPO = "+PNodoDAO.NODE_OBJ_SYNCNODE;
		CollectionDAO objlist=new CollectionDAO(PNodoDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	public CollectionDAO getEntities(DbCnt cnt)
	throws ISPACException
	{
		String sql="WHERE ID_PCD = "+getInt(IDKEY) + " ORDER BY ORDEN";
		CollectionDAO objlist=new CollectionDAO(PEntidadDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	public CollectionDAO getSyncNodes(DbCnt cnt)
	throws ISPACException
	{
		String sql="WHERE ID_PCD = "+getInt(IDKEY);
		CollectionDAO objlist=new CollectionDAO(PSincNodoDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	public CollectionDAO getFlows(DbCnt cnt)
	throws ISPACException
	{
		String sql="WHERE ID_PCD = "+getInt(IDKEY);
		CollectionDAO objlist=new CollectionDAO(PFlujoDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	public CollectionDAO getTasks(DbCnt cnt,int nIdStage)
	throws ISPACException
	{
		String sql="WHERE ID_PCD = "+getInt(IDKEY)+" AND ID_FASE = "+nIdStage+" ORDER BY ORDEN";
		CollectionDAO objlist=new CollectionDAO(PTramiteDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	public CollectionDAO getTasks(DbCnt cnt)
	throws ISPACException
	{
		String sql="WHERE ID_PCD = "+getInt(IDKEY) + " ORDER BY ID_FASE, ORDEN";
		CollectionDAO objlist=new CollectionDAO(PTramiteDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	public CollectionDAO getPermissions(DbCnt cnt)
	throws ISPACException
	{
		String sql="WHERE ID_PCD = " + getInt(IDKEY);
        CollectionDAO objlist=new CollectionDAO(PermissionsDAO.class);
        objlist.query(cnt,sql);
        return objlist;
	}
	
	public CollectionDAO getOtherPermissions(DbCnt cnt)
			throws ISPACException
			{
				String sql="WHERE ID_OBJ = " + getInt(IDKEY) +" AND TP_OBJ="+ISecurityAPI.PERMISSION_TPOBJ_PROCEDURE;
		        CollectionDAO objlist=new CollectionDAO(PermissionDAO.class);
		        objlist.query(cnt,sql);
		        return objlist;
			}


	static public CollectionDAO getProcedures(DbCnt cnt,String sql)
	throws ISPACException
	{
		CollectionDAO objlist=new CollectionDAO(PProcedimientoDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	static public PProcedimientoDAO getProcedureMaxVersion(DbCnt cnt,int procedureGroupId)
	throws ISPACException
	{
	    String sql="WHERE ID_GROUP = "+procedureGroupId+" ORDER BY NVERSION DESC";
		CollectionDAO objlist=new CollectionDAO(PProcedimientoDAO.class);
		objlist.query(cnt,sql);
		if (!objlist.next())
		    return null;

		return (PProcedimientoDAO)objlist.value();
	}

	static public PProcedimientoDAO getProcedureCurrentVersion(DbCnt cnt,int procedureGroupId)
	throws ISPACException
	{
	    String sql="WHERE ID_GROUP = "+procedureGroupId+" AND ESTADO ="+
	    	IProcedure.PCD_STATE_CURRENT+"  ORDER BY NVERSION DESC";

		CollectionDAO objlist=new CollectionDAO(PProcedimientoDAO.class);
		objlist.query(cnt,sql);
		if (!objlist.next())
		    return null;

		return (PProcedimientoDAO)objlist.value();
	}


	static public CollectionDAO getInstancedProcess(DbCnt cnt,int procedureId) throws ISPACException
	{
	    String sql="WHERE ID_PCD = "+procedureId;
		CollectionDAO objlist=new CollectionDAO(TXProcesoDAO.class);
		objlist.query(cnt,sql);

		return objlist;
	}
	static public int countInstancedProcess(DbCnt cnt,int procedureId) throws ISPACException
	{
	    String sql="WHERE ID_PCD = "+procedureId;
		CollectionDAO objlist=new CollectionDAO(TXProcesoDAO.class);
		return objlist.count(cnt,sql);
	}


	public CollectionDAO getInstancedProcess(DbCnt cnt) throws ISPACException
	{
	    String sql="WHERE ID_PCD = "+getKeyInt();
		CollectionDAO objlist=new CollectionDAO(TXProcesoDAO.class);
		objlist.query(cnt,sql);

		return objlist;
	}
	
	public int countInstancedProcess(DbCnt cnt) throws ISPACException
	{
	    String sql="WHERE ID_PCD = "+getKeyInt();
		CollectionDAO objlist=new CollectionDAO(TXProcesoDAO.class);
		return objlist.count(cnt,sql);
	}

	public int countAsociatedCTTasks(DbCnt cnt) throws ISPACException
	{
	    String sql="WHERE ID_SUBPROCESO = "+getKeyInt();
		CollectionDAO objlist=new CollectionDAO(CTTaskDAO.class);
		return objlist.count(cnt,sql);
	}

	public int countAsociatedPTasks(DbCnt cnt) throws ISPACException
	{
	    String sql="WHERE ID_PCD_SUB = "+getKeyInt();
		CollectionDAO objlist=new CollectionDAO(PTramiteDAO.class);
		return objlist.count(cnt,sql);
	}

	public int countAsociatedTasks(DbCnt cnt) throws ISPACException
	{
	    String sql="WHERE ID_SUBPROCESO = "+getKeyInt();
		CollectionDAO objlist=new CollectionDAO(TXTramiteDAO.class);
		return objlist.count(cnt,sql);
	}
}
