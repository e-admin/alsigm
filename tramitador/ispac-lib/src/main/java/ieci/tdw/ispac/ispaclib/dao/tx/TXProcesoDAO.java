package ieci.tdw.ispac.ispaclib.dao.tx;

import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PPlazoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PRelPlazoDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TXProcesoDAO extends ObjectDAO implements IProcess
{

	private static final long serialVersionUID = 8860935640714120044L;
	
	public static final String TABLENAME 	= "SPAC_PROCESOS";
	static final String IDSEQUENCE 	= "SPAC_SQ_ID_PROCESOS";
	static final String IDKEY 		= "ID";
	final ClientContext mctx;

	/**
	 * @throws ISPACException
	 */
	public TXProcesoDAO() throws ISPACException {
		super(null);
		mctx=null;
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public TXProcesoDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
		mctx=null;
	}
	
	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public TXProcesoDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id, null);
		mctx=null;
	}

	/**
	 * Constructor necesario para utilizar el interface IProcess
	 *
	 * @param ctx
	 * @throws ISPACException
	 */
	public TXProcesoDAO(ClientContext ctx) throws ISPACException {
		super(ctx.getConnection(), null);
		mctx=ctx;
	}
	
	/**
	 * Constructor necesario para utilizar el interface IProcess
	 * 
	 * @param cnt
	 * @param ctx
	 * @throws ISPACException
	 */
	public TXProcesoDAO(DbCnt cnt, ClientContext ctx) throws ISPACException {
		super(cnt, null);
		mctx=ctx;
	}

	/**
	 * Constructor necesario para utilizar el interface IProcess
	 * 
	 * @param cnt
	 * @param id
	 * @param ctx
	 * @throws ISPACException
	 */
	public TXProcesoDAO(DbCnt cnt, int id,ClientContext ctx) throws ISPACException {
		super(cnt, id, null);
		mctx=ctx;
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

	public int getIdProcedure()
	throws ISPACException
	{
		return getInt("ID_PCD");
	}

	public CollectionDAO getStages(DbCnt cnt)
	throws ISPACException
	{
		String sql="WHERE ID_EXP = "+getInt(IDKEY);
		CollectionDAO objlist=new CollectionDAO(TXFaseDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	public CollectionDAO getOpenedStages(DbCnt cnt)
	throws ISPACException
	{
		String sql = "WHERE ID_EXP = "
			       + getInt(IDKEY)
				   + " AND ESTADO = 1";
		CollectionDAO objlist=new CollectionDAO(TXFaseDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	public CollectionDAO getMilestones(DbCnt cnt)
	throws ISPACException
	{
		String sql="WHERE ID_EXP = "+getInt(IDKEY);
		CollectionDAO objlist=new CollectionDAO(TXHitoDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	public CollectionDAO getSyncnodes(DbCnt cnt)
	throws ISPACException
	{
		String sql="WHERE ID_EXP = "+getInt(IDKEY);
		CollectionDAO objlist=new CollectionDAO(TXSincNodoDAO.class);

		objlist.query(cnt,sql);
		return objlist;
	}

	public CollectionDAO getTasks(DbCnt cnt)
	throws ISPACException
	{
		String sql="WHERE ID_EXP = "+getInt(IDKEY);
		CollectionDAO objlist=new CollectionDAO(TXTramiteDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	public CollectionDAO getTasks(DbCnt cnt,int nIdStage)
	throws ISPACException
	{
		String sql="WHERE ID_EXP = "+getInt(IDKEY)+" AND ID_FASE_EXP = "+ nIdStage;
		CollectionDAO objlist=new CollectionDAO(TXTramiteDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	
	public boolean testClosed(DbCnt cnt)
	throws ISPACException
	{
		String sql="WHERE ID_EXP = "+getInt(IDKEY);
		CollectionDAO objlist=new CollectionDAO(TXTramiteDAO.class);
		if (objlist.exist(cnt,sql))
			return false;

		sql="WHERE ID_EXP = "+getInt(IDKEY);
		objlist=new CollectionDAO(TXFaseDAO.class);
		if (objlist.exist(cnt,sql))
			return false;

		sql="WHERE ID_EXP = "+getInt(IDKEY);
		objlist=new CollectionDAO(TXSincNodoDAO.class);
		if (objlist.exist(cnt,sql))
			return false;

		return true;
	}

	public CollectionDAO getSubprocess(DbCnt cnt ,String numexp) throws ISPACException{
		try{
			
			String sql="WHERE NUMEXP = '"+ DBUtil.replaceQuotes(numexp)+"' AND TIPO="+IProcess.SUBPROCESS_TYPE;;
			CollectionDAO objlist=new CollectionDAO(TXProcesoDAO.class);
			objlist.query(cnt, sql);
			return objlist;
		}catch (ISPACException e)
		{
			throw new ISPACException("Error en TXProcesoDAO:getSubprocess()", e);
		}
		
	}
	public void deleteTasks(DbCnt cnt)
	throws ISPACException
	{
		String sql="WHERE ID_EXP = "+getInt(IDKEY);
		CollectionDAO objlist=new CollectionDAO(TXTramiteDAO.class);
		objlist.delete(cnt,sql);
	}

	public void deleteStages(DbCnt cnt)
	throws ISPACException
	{
		String sql="WHERE ID_EXP = "+getInt(IDKEY);
		CollectionDAO objlist=new CollectionDAO(TXFaseDAO.class);
		objlist.delete(cnt,sql);
	}

	public void deleteSyncNodes(DbCnt cnt)
	throws ISPACException
	{
		String sql="WHERE ID_EXP = "+getInt(IDKEY);
		CollectionDAO objlist=new CollectionDAO(TXSincNodoDAO.class);
		objlist.delete(cnt,sql);
	}

	// Implementación IProcess

	public IItemCollection getStages()
	throws ISPACException
	{
		if (isSubProcess())
			throw new ISPACException("exception.subprocess.nostages");

		return getStagesActivities();
	}

	public IStage getStage(DbCnt cnt, int stagePcdId) throws ISPACException {
		String sql= "WHERE ID_EXP = "+getInt(IDKEY)+" AND ID_FASE = "+stagePcdId;
		CollectionDAO objlist=new CollectionDAO(TXFaseDAO.class);
		objlist.query(cnt,sql);
		if (objlist.next())
			return (IStage)objlist.value();
		return null;		
	}
	public IItemCollection getOpenedStages()
	throws ISPACException
	{
		if ( isSubProcess())
			throw new ISPACException("exception.subprocess.nostages");
		DbCnt cnt = null;
		if (mctx==null)
		    throw new ISPACException("Error en IProcess:getOpenedStages() - ClientContext nulo");

		try
		{
			cnt = mctx.getConnection();
			return getOpenedStages(cnt).disconnect();
		}
		catch (ISPACException e)
		{
			throw new ISPACException("Error en IProcess:getOpenedStages()", e);
		}
		finally
		{
			mctx.releaseConnection(cnt);
		}
	}

	public IItemCollection getMilestones()
	throws ISPACException
	{
		DbCnt cnt = null;
		if (mctx==null)
		    throw new ISPACException("Error en IProcess:getMilestones() - ClientContext nulo");

		try
		{
			cnt = mctx.getConnection();
			return getMilestones(cnt).disconnect();
		}
		catch (ISPACException e)
		{
			throw new ISPACException("Error en IProcess:getMilestones()", e);
		}
		finally
		{
			mctx.releaseConnection(cnt);
		}
	}

	public IItemCollection getSyncnodes()
	throws ISPACException
	{
		DbCnt cnt = null;
		if (mctx==null)
		    throw new ISPACException("Error en IProcess:getSyncnodes() - ClientContext nulo");

		try
		{
			cnt = mctx.getConnection();
			return getSyncnodes(cnt).disconnect();
		}
		catch (ISPACException e)
		{
			throw new ISPACException("Error en IProcess:getSyncnodes()", e);
		}
		finally
		{
			mctx.releaseConnection(cnt);
		}
	}

	public IItemCollection getTasks()
	throws ISPACException
	{
		DbCnt cnt = null;
		if (mctx==null)
		    throw new ISPACException("Error en IProcess:getTasks() - ClientContext nulo");

		try
		{
			cnt = mctx.getConnection();
			return getTasks(cnt).disconnect();
		}
		catch (ISPACException e)
		{
			throw new ISPACException("Error en IProcess:getTasks()", e);
		}
		finally
		{
			mctx.releaseConnection(cnt);
		}
	}

	public IItemCollection getTasks(int nIdStage)
	throws ISPACException
	{
		DbCnt cnt = null;
		if (mctx==null)
		    throw new ISPACException("Error en IProcess:getTasks() - ClientContext nulo");

		try
		{
			cnt = mctx.getConnection();
			return getTasks(cnt,nIdStage).disconnect();
		}
		catch (ISPACException e)
		{
			throw new ISPACException("Error en IProcess:getTasks()", e);
		}
		finally
		{
			mctx.releaseConnection(cnt);
		}
	}


	public static CollectionDAO getOutdatedProcess(DbCnt cnt)
	throws ISPACException
	{
		String sql = new StringBuffer("WHERE ESTADO=")
			.append(IProcess.OPEN)
			.append(" AND FECHA_LIMITE <= ")
			.append(DBUtil.getToDateByBD(cnt, new Date()))
			.append(" ORDER BY ID ASC ")
			.toString();
		
	    CollectionDAO objlist=new CollectionDAO(TXProcesoDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

//	public static IItemCollection getProcess(DbCnt cnt,String numexp,ClientContext ctx)
//	throws ISPACException
//	{
//	    Object[] argsarr=new Object[1];
//	    argsarr[0]=ctx;
//
//		String sql="WHERE NUMEXP = '" + DBUtil.replaceQuotes(numexp) + "'";
//
//		CollectionDAO objlist=new CollectionDAO(TXProcesoDAO.class,argsarr);
//		objlist.query(cnt,sql);
//		return objlist.disconnect();
//	}

	
	public static IItemCollection getProcess(DbCnt cnt,String numexp,ClientContext ctx)
	throws ISPACException
	{
		return getProcess(cnt, numexp, ctx, PROCESS_TYPE);	
	}

	public static IItemCollection getSubProcess(DbCnt cnt,String numexp,ClientContext ctx)
	throws ISPACException
	{
		return getProcess(cnt, numexp, ctx, SUBPROCESS_TYPE);
	}
	
	public static IItemCollection getProcesses(DbCnt cnt,int idProcedimiento,ClientContext ctx)
	throws ISPACException
	{
		return getProcesses(cnt, idProcedimiento, ctx, PROCESS_TYPE);
	}
	
	private static IItemCollection getProcess(DbCnt cnt,String numexp,ClientContext ctx, int type) throws ISPACException{
	    Object[] argsarr=new Object[1];
	    argsarr[0]=ctx;

		String sql="WHERE NUMEXP = '" + DBUtil.replaceQuotes(numexp) + "' AND TIPO = " + type;

		CollectionDAO objlist = new CollectionDAO(TXProcesoDAO.class,argsarr);
		objlist.query(cnt,sql);
		
		return objlist.disconnect();	
	}
	
	
	private static IItemCollection getProcesses(DbCnt cnt,int idProcedimiento,ClientContext ctx, int type) throws ISPACException{
	    Object[] argsarr=new Object[1];
	    argsarr[0]=ctx;

		String sql="WHERE ID_PCD = "+ idProcedimiento+" AND TIPO = " + type;

		CollectionDAO objlist = new CollectionDAO(TXProcesoDAO.class,argsarr);
		objlist.query(cnt,sql);
		
		return objlist.disconnect();	
	}
	
	
	public static int countProcess(DbCnt cnt,String numexp,String query)
	throws ISPACException
	{
		String sql="WHERE ";
		if(StringUtils.isNotBlank(numexp)){
			sql+=" NUMEXP = '" + DBUtil.replaceQuotes(numexp) + "'";
		}
		if (sql!=null && sql.length()>0)
		    sql+=query;

		CollectionDAO objlist=new CollectionDAO(TXProcesoDAO.class);
		return objlist.count(cnt,sql);
	}
	

	public static IItemCollection getExpedients (DbCnt cnt, String query , int limit , String fechaInicio , String  fechaEliminacion , int idProcedimiento) throws ISPACException{
		
		TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
		
		factory.addTable(TABLENAME, "PROCESOS");
		factory.addTable("SPAC_P_PROCEDIMIENTOS", "PPROCEDIMIENTOS");

	    String sql = " WHERE PPROCEDIMIENTOS.ID = PROCESOS.ID_PCD"
	    			 +" AND "+query;
	    
	 // Fecha de inicio
		if (StringUtils.isNotBlank(fechaInicio)) {
		
			sql+=" AND PROCESOS.FECHA_INICIO >= "+DBUtil.getToTimestampByBD(cnt, fechaInicio) ;
		}
		
		// Fecha de eliminacion
		if (StringUtils.isNotBlank(fechaEliminacion)) {
		   sql+=" AND  PROCESOS.FECHA_ELIMINACION <=  " +DBUtil.getToTimestampByBD(cnt,fechaEliminacion) ;
		}
		
		if(idProcedimiento!=-1){
			sql+=" AND PROCESOS.ID_PCD="+idProcedimiento;
		}
		
	    sql+=" ORDER BY PPROCEDIMIENTOS.NOMBRE";
	    
	   return factory.queryTableJoin(cnt, sql , limit ).disconnect();
		
		
	}
	
	public String getDeadlineXML(DbCnt cnt) throws ISPACException{
		//obtener el id del proceso
		int idProcedure = getIdProcedure();
		CollectionDAO collDAO = PPlazoDAO.getDeadline(cnt, PRelPlazoDAO.DEADLINE_OBJ_PROCEDURE, idProcedure);
		//con el id del proceso obtener el plazo
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

	public static IItemCollection getProcessBPM(DbCnt cnt, String idProcessBPM, ClientContext ctx) throws ISPACException {
	    Object[] argsarr=new Object[1];
	    argsarr[0]=ctx;

		String sql="WHERE ID_PROCESO_BPM = '" + idProcessBPM + "'";

		CollectionDAO objlist=new CollectionDAO(TXProcesoDAO.class,argsarr);
		objlist.query(cnt,sql);
		return objlist.disconnect();
	}

	public IItemCollection getActivities() throws ISPACException {
		if (isProcess())
			throw new ISPACException("exception.process.noactivities");
		return getStagesActivities();
	}

	public IItemCollection getOpenActivities() throws ISPACException {
		if (isProcess())
			throw new ISPACException("exception.process.noactivities");
		return getOpenedStages();
	}

	public boolean isProcess() throws ISPACException {
		return getInt("TIPO") == PROCESS_TYPE;
	}

	public boolean isSubProcess() throws ISPACException {
		return getInt("TIPO") == SUBPROCESS_TYPE;
	}
	
	private IItemCollection getStagesActivities() throws ISPACException{
		DbCnt cnt = null;
		if (mctx==null)
		    throw new ISPACException("Error en IProcess:getStagesActivities() - ClientContext nulo");
		try
		{
			cnt = mctx.getConnection();
			return getStages(cnt).disconnect();
		}
		catch (ISPACException e)
		{
			throw new ISPACException("Error en IProcess:getStagesActivities()", e);
		}
		finally
		{
			mctx.releaseConnection(cnt);
		}
	}

public static String addInResponsibleOrExistPermissionCondition(String inResponsibleCondition, String inPermResponsibleCondition)
	throws ISPACException
	{
		StringBuffer sql = new StringBuffer();

		if (StringUtils.isNotBlank(inResponsibleCondition)) {

			// Añadir la responsabilidad
			// y consultar los permisos asignados
			sql.append(getSqlInResponsibleOrExistPermissionCondition(inResponsibleCondition, inPermResponsibleCondition));
		}

		return sql.toString();
	}

	public static String addInResponsibleOrExistPermissionCondition(String resp)
	throws ISPACException
	{
		String sql = " ";

		// Obtener la condición SQL de responsabilidad y de permisos
		// siempre que la responsabilidad no sea de Supervisor
		if (StringUtils.isNotBlank(resp) && !Responsible.SUPERVISOR.equalsIgnoreCase(resp)) {

			String sqlResponsibles = DBUtil.addInResponsibleCondition("ID_RESP", resp);

			// Añadir la responsabilidad y consultar los permisos asignados
			sql = getSqlInResponsibleOrExistPermissionCondition(sqlResponsibles, DBUtil.addInResponsibleCondition("SPC_PERMS.ID_RESP", resp));
		}

		return sql;
	}

	protected static String getSqlInResponsibleOrExistPermissionCondition(String inResponsibleCondition, String inPermResponsibleCondition)
	throws ISPACException
	{
		StringBuffer sql = new StringBuffer();

		sql.append(" ( ")
		   .append(inResponsibleCondition)
		   .append(" OR ( ")
		   .append("SELECT COUNT(*) FROM SPAC_PERMISOS SPC_PERMS WHERE ( ")
		   // Procedimiento
		   .append("(SPC_PERMS.TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_PROCEDURE)
		   .append(" AND SPC_PERMS.ID_OBJ = ID_PCD)  ")
		   // Proceso
		   //.append(" OR (SPC_PERMS.TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_PROCESS)
		   //.append(" AND SPC_PERMS.ID_OBJ = ID) ")
		   .append(") AND ")
		   .append(inPermResponsibleCondition)
		   .append(") > 0 ) ");

		return sql.toString();
	}

	public static String addAndInResponsibleOrExistPermissionCondition(String resp)
	throws ISPACException
	{
		String sql = addInResponsibleOrExistPermissionCondition(resp);

		if (StringUtils.isNotBlank(sql)) {
			sql = " AND " + sql;
		}

		return sql;
	}
	
	

	
}

