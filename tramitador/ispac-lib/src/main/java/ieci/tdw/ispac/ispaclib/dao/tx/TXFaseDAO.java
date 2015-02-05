package ieci.tdw.ispac.ispaclib.dao.tx;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.dao.procedure.DTTramiteDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PPlazoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PRelPlazoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PTramiteDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class TXFaseDAO extends ObjectDAO implements IStage
{

	private static final long serialVersionUID = -1863855288964318460L;
	
	static final String TABLENAME 	= "SPAC_FASES";
	static final String IDSEQUENCE 	= "SPAC_SQ_ID_FASES";
	static final String IDKEY 		= "ID";

	/**
	 * @throws ISPACException
	 */
	public TXFaseDAO() throws ISPACException {
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public TXFaseDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
	
	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public TXFaseDAO(DbCnt cnt, int id) throws ISPACException {
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

	public CollectionDAO getMilestones(DbCnt cnt)
	throws ISPACException
	{
		String sql="WHERE ID_EXP = "+getInt("ID_EXP")+
					" AND ID_FASE = "+getInt(IDKEY);
		CollectionDAO objlist=new CollectionDAO(TXHitoDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	public CollectionDAO getTasks(DbCnt cnt)
	throws ISPACException
	{
		String sql="WHERE ID_EXP = "+getInt("ID_EXP")+
					" AND ID_FASE_EXP = "+getInt(IDKEY);
		CollectionDAO objlist=new CollectionDAO(TXTramiteDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	public boolean existTask(DbCnt cnt)
	throws ISPACException
	{
		String sql="WHERE ID_EXP = "+getInt("ID_EXP")+
					" AND ID_FASE_EXP = "+getInt(IDKEY);
		CollectionDAO objlist=new CollectionDAO(TXTramiteDAO.class);
		return objlist.exist(cnt,sql);
	}

	public CollectionDAO getPCDTasks(DbCnt cnt)
	throws ISPACException
	{
		String sql="WHERE ID_PCD = "+getInt("ID_PCD")+
					" AND ID_FASE = "+getInt("ID_FASE");

		CollectionDAO objlist=new CollectionDAO(PTramiteDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}
	
	/**
	 * Obtiene todos aquellos trámites obligatorios de una fase para un determinado procedimiento
	 * @param cnt
	 * @return
	 * @throws ISPACException
	 */
	public CollectionDAO getPCDObligatoryTasks(DbCnt cnt)
	throws ISPACException
	{
		String sql="WHERE ID_PCD = "+getInt("ID_PCD")+
					" AND ID_FASE = "+getInt("ID_FASE")+
					" AND OBLIGATORIO = 1";

		CollectionDAO objlist=new CollectionDAO(PTramiteDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}
	
	
	public CollectionDAO getDTTasks(DbCnt cnt, String numexp, int stageId)
	throws ISPACException
	{
		String sql = "WHERE NUMEXP = '" + DBUtil.replaceQuotes(numexp)
				   + "' AND ID_FASE_PCD = " + stageId;

		CollectionDAO objlist=new CollectionDAO(DTTramiteDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	public static CollectionDAO getOutdatedStages(DbCnt cnt)
	throws ISPACException
	{
		String sql = new StringBuffer("WHERE ESTADO=")
			.append(IStage.OPEN)
			.append(" AND FECHA_LIMITE <= ")
			.append(DBUtil.getToDateByBD(cnt, new Date()))
			.append(" ORDER BY ID ASC ")
			.toString();
		
	    CollectionDAO objlist=new CollectionDAO(TXFaseDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	// TODO: Provisional, no sé muy bien donde ponerlo //
	public static CollectionDAO getStages(DbCnt cnt, String stages)
	throws ISPACException
	{
		String sql="WHERE ID IN ("+stages+")" +
				   " ORDER BY ID asc ";
	    CollectionDAO objlist=new CollectionDAO(TXFaseDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	public static CollectionDAO getStagesProcess(DbCnt cnt, int idProcess) 
	throws ISPACException
	{
		String sql="WHERE ID_EXP =  "+idProcess+
		   " AND TIPO = " + IStage.STAGE_TYPE+
		   " ORDER BY ID asc ";
		CollectionDAO objlist=new CollectionDAO(TXFaseDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}	
	
	
	
	public static CollectionDAO getStage(DbCnt cnt, String numExp)
	throws ISPACException
	{
		String sql="WHERE NUMEXP = '" + DBUtil.replaceQuotes(numExp) + "'";
		CollectionDAO objlist=new CollectionDAO(TXFaseDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	public String getDeadlineXML(DbCnt cnt) throws ISPACException {
		//obtener el id de la fase
		CollectionDAO collDAO = PPlazoDAO.getDeadline(cnt, PRelPlazoDAO.DEADLINE_OBJ_STAGE, getIdFase());
		// obtener el plazo
		if (collDAO!=null){
			IItemCollection coll = collDAO.disconnect();
			if (coll!=null){
				List collList = coll.toList();
				for (Iterator iter = collList.iterator(); iter.hasNext();) {
					IItem element = (IItem) iter.next();
					String plazo = element.getString("PLAZO");
					return plazo;
				}
			}
		}
		return null;
	}
	
	public int getIdFase() throws ISPACException {
		return getInt("ID_FASE");
	}

	public boolean isActivity() throws ISPACException{
		return getInt("TIPO") == ACTIVITY_TYPE;
	}

	public boolean isStage() throws ISPACException{
		return getInt("TIPO") == STAGE_TYPE;
	}

	
}
