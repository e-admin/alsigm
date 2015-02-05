package ieci.tdw.ispac.ispaclib.dao.tx;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.ITask;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.dao.procedure.PPlazoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PRelPlazoDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class TXTramiteDAO extends ObjectDAO implements ITask
{

	private static final long serialVersionUID = -978580908540621476L;
	
	static final String TABLENAME 	= "SPAC_TRAMITES";
	static final String IDSEQUENCE 	= "SPAC_SQ_ID_TRAMITES";
	static final String IDKEY 		= "ID";

	/**
	 * @throws ISPACException
	 */
	public TXTramiteDAO() throws ISPACException	{
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public TXTramiteDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
	
	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public TXTramiteDAO(DbCnt cnt, int id) throws ISPACException {
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

	public static CollectionDAO getOutdatedTasks(DbCnt cnt)
	throws ISPACException
	{
		String sql = new StringBuffer("WHERE ESTADO=")
			.append(ITask.OPEN)
			.append(" AND FECHA_LIMITE <= ")
			.append(DBUtil.getToDateByBD(cnt, new Date()))
			.append(" ORDER BY ID ASC ")
			.toString();
		
	    CollectionDAO objlist=new CollectionDAO(TXTramiteDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	// TODO: Provisional, no sé muy bien donde ponerlo //
	public static CollectionDAO getTasks(DbCnt cnt, String tasks)
	throws ISPACException
	{
		String sql="WHERE ID IN ("+tasks+")" +
				   " ORDER BY ID asc ";
	    CollectionDAO objlist=new CollectionDAO(TXTramiteDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}
	
	public String getDeadlineXML(DbCnt cnt) throws ISPACException {
		//obtener el id del tramite
		CollectionDAO collDAO = PPlazoDAO.getDeadline(cnt, PRelPlazoDAO.DEADLINE_OBJ_TASK, getIdTask());
		//obtener el plazo
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
	
	public int getIdTask() throws ISPACException {
		return getInt("ID_TRAMITE");
	}
	public static TXTramiteDAO getTaskBySubProcess(DbCnt cnt, int subProcessId)
	throws ISPACException
	{
		String sql="WHERE ID_SUBPROCESO = " + subProcessId;
	    CollectionDAO objlist=new CollectionDAO(TXTramiteDAO.class);
		objlist.query(cnt,sql);
		
		if (!objlist.next()) {
			throw new ISPACNullObject("No se ha encontrado el trámite. SQL: " + sql);
		}
		
		return (TXTramiteDAO) objlist.value();
	}
	public boolean isSimple() throws ISPACException {
		return getInt("TIPO") == ITask.SIMPLE_TASK_TYPE;
	}

	public boolean isComplex() throws ISPACException {
		return getInt("TIPO") == ITask.COMPLEX_TASK_TYPE;
	}
}
