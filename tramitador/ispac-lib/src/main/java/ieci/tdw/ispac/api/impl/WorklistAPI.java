package ieci.tdw.ispac.api.impl;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.IWorklistAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.api.item.ITask;
import ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.DTTramiteDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PProcedimientoDAO;
import ieci.tdw.ispac.ispaclib.dao.wl.DeadLineDAO;
import ieci.tdw.ispac.ispaclib.dao.wl.WLActivityDAO;
import ieci.tdw.ispac.ispaclib.dao.wl.WLBatchTaskDAO;
import ieci.tdw.ispac.ispaclib.dao.wl.WLBatchTaskExpsDAO;
import ieci.tdw.ispac.ispaclib.dao.wl.WLClosedTaskDAO;
import ieci.tdw.ispac.ispaclib.dao.wl.WLProcedureDAO;
import ieci.tdw.ispac.ispaclib.dao.wl.WLProcessDAO;
import ieci.tdw.ispac.ispaclib.dao.wl.WLProcessInTrashDAO;
import ieci.tdw.ispac.ispaclib.dao.wl.WLStageDAO;
import ieci.tdw.ispac.ispaclib.dao.wl.WLTaskDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.directory.DirectoryConnectorFactory;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryConnector;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryEntry;
import ieci.tdw.ispac.ispaclib.resp.RespFactory;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.security.SecurityMgr;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.worklist.WLProcessListBuilder;
import ieci.tdw.ispac.ispaclib.worklist.WLWorklistFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.apache.log4j.Logger;

/**
 * @author juanin
 *
 */
public class WorklistAPI implements IWorklistAPI 
{
	private static final long serialVersionUID = 1L;
	
	public static final Logger logger = Logger.getLogger(WorklistAPI.class);
	private final ClientContext context;
	private int mWorkMode;
	private String resp;

	public WorklistAPI(ClientContext context)
	{
		this.context = context;
		//mWorkMode = IWorklistAPI.NORMAL;
		mWorkMode = IWorklistAPI.SUPERVISOR;
	}

	
	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IWorklistAPI#getRespString()
	 */
	public String getRespString() throws ISPACException
	{

		if (StringUtils.isEmpty(resp)) {

			TreeSet respList = new TreeSet();
			DbCnt cnt = context.getConnection();
	
			try
			{
				Responsible user = context.getUser();
				List respListUser = user.getRespList();
				respList.addAll(respListUser);
				if (mWorkMode == IWorklistAPI.SUPERVISOR)
				{
					SecurityMgr securityMgr = new SecurityMgr(cnt);
					
					// Comprobar si el usuario tiene asignada la función de Supervisor
					if (securityMgr.isSupervisor(user.getUID()))
					{	
						return Responsible.SUPERVISOR;
					}
					
					// Supervisar (supervisados del usuario, del departamento y de los grupos a los que pertenece el usuario)
					IItemCollection collection = securityMgr.getAllSuperviseds(user);
					addRespListFromEntryUID(respList, collection, "UID_SUPERVISADO");
					// Sustituir (sustituidos por el usuario, por el departamento y por los grupos a los que pertenece el usuario)
					collection = securityMgr.getAllSubstitutes(user);
					addRespListFromEntryUID(respList, collection, "UID_SUSTITUIDO");
			
				}
			}
			finally
			{
				context.releaseConnection( cnt);
			}
	
			resp="'"+StringUtils.join(respList, "','")+"'";
		}
		if(logger.isDebugEnabled()){
			logger.debug("getRespString:"+resp);
		}
		return resp;
	}

	private List getRespListFromEntryUID(String entryUID) throws ISPACException {
		
		IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();
		
		IDirectoryEntry entry = directory.getEntryFromUID(entryUID);
		Responsible resp = RespFactory.createResponsible(entry);
		
		return resp.getRespList();
	}
	
//	private String getRespStringFromEntryUID(String entryUID) throws ISPACException {
//		
//		IDirectoryConnector directory = DirectoryConnectorFactory.getConnector();
//		
//		IDirectoryEntry entry = directory.getEntryFromUID(entryUID);
//		Responsible resp= RespFactory.createResponsible(entry);
//		
//		return resp.getRespString();
//	}
	
	private String getSubstitutesRespString() throws ISPACException
	{
		if (StringUtils.isEmpty(resp)) {

			TreeSet respList=new TreeSet();
			DbCnt cnt = context.getConnection();

			try
			{
				Responsible user = context.getUser();
				respList.addAll(user.getRespList());

				if (mWorkMode == IWorklistAPI.SUPERVISOR) {

					SecurityMgr security = new SecurityMgr(cnt);

					// Sustituir (sustituidos por el usuario y por los grupos a los que pertenece el usuario)
					IItemCollection collection = security.getAllSubstitutes(user);
					addRespListFromEntryUID(respList, collection, "UID_SUSTITUIDO");

				}
			}
			finally
			{
				context.releaseConnection( cnt);
			}

			resp="'"+StringUtils.join(respList, "','")+"'";
		}
		if(logger.isDebugEnabled()){
			logger.debug("getSubstitutesRespString:"+resp);
		}
		return resp;
	}
	
	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IWorklistAPI#setMode(int)
	 */
	public void setMode(int nMode) throws ISPACException
	{
		mWorkMode=nMode;
	}

	public IItemCollection findActiveStagesInTrash(int nIdProcess)
			throws ISPACException {

		DbCnt cnt = context.getConnection();
		try
		{
			return WLProcessInTrashDAO.getWorkItems(cnt,resp,nIdProcess).disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:findActiveStagesInTrash(" + nIdProcess + ")", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	
		
	}
	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IWorklistAPI#findActiveStages(int)
	 */
	public IItemCollection findActiveStages(int nIdProcess) throws ISPACException
	{
		IInvesflowAPI invesFlowAPI = context.getAPI();
		IProcess iprocess = invesFlowAPI.getProcess(nIdProcess);
		String resp ="";
		
		if(iprocess.getInt("TIPO")==IPcdElement.TYPE_SUBPROCEDURE){
			resp=getRespStringSubProceso(0, context.getStateContext().getPcdId());
		}
		else{
			resp= getRespString();
		}
	
		return findActiveStages(nIdProcess, resp);
	}
	
	public IItemCollection findActiveStages(int nIdProcess, String resp) throws ISPACException {
		
		DbCnt cnt = context.getConnection();
		try
		{
			return WLProcessDAO.getWorkItems(cnt,resp,nIdProcess).disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:findActiveStages(" + nIdProcess + ")", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IWorklistAPI#findActiveTasks(int)
	 */
	public IItemCollection findActiveTasks(int nIdProcess) throws ISPACException
	{
		String resp = getRespString();
		return findActiveTasks(nIdProcess, resp);
	}
	
	public IItemCollection findActiveTasks(int nIdProcess, String resp) throws ISPACException
	{
		DbCnt cnt = context.getConnection();
		try
		{
			return WLTaskDAO.getWorkitems(cnt,resp,nIdProcess).disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:findActiveTasks(" + nIdProcess + ")", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IWorklistAPI#findStageActiveTasks(int, int)
	 */
	public IItemCollection findStageActiveTasks(int nIdProcess,int nIdStagePCD) throws ISPACException
	{
		DbCnt cnt = context.getConnection();
		try
		{
			return WLTaskDAO.getStageWorkitems(cnt, getRespString(), nIdProcess, nIdStagePCD).disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:findActiveTasks(" + nIdProcess + ")", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}

	// PROCEDIMIENTOS //

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IWorklistAPI#getProcedures()
	 */
	public IItemCollection getProcedures() throws ISPACException
	{
		String resp = getRespString();
		return getProcedures(resp);
	}
	
	public IItemCollection getProcedures(String resp) throws ISPACException
	{
		DbCnt cnt = context.getConnection();
		try
		{
			return WLProcedureDAO.getProcs(cnt, resp).disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:getProcs()", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}

	public IItemCollection getSubProcedures(String resp) throws ISPACException
	{
		DbCnt cnt = context.getConnection();
		try
		{
			return WLProcedureDAO.getSubProcs(cnt, resp).disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:getSubProcs()", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}
	// PROCEDIMIENTOS //

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IWorklistAPI#getCreateProcedures()
	 */
	public IItemCollection getCreateProcedures() throws ISPACException
	{
		//Si es supervisor podra crear todos, sino le corresponderan los suyos y y los que pueden crear los que sustituye
		String resp = null;
		
		DbCnt cnt = context.getConnection();
		try
		{
			Responsible user = context.getUser();
			SecurityMgr securityMgr = new SecurityMgr(cnt);
			// Comprobar si el usuario tiene asignada la función de Supervisor Total
			if (securityMgr.isSupervisorTotal(user.getUID())){	
				resp = Responsible.SUPERVISOR;
			}else{
				// los que sustituye
				resp = getSubstitutesRespString();
			}
			
			CollectionDAO pcdset=new CollectionDAO(PProcedimientoDAO.class);
			
			/* Procedimientos en vigor */
			String sqlquery = "WHERE ESTADO=" + IProcedure.PCD_STATE_CURRENT
				+ " AND TIPO=" + IProcedure.PROCEDURE_TYPE
				+ " AND ID IN" 
				+ " (SELECT ID_PCD FROM SPAC_SS_PERMISOS WHERE  PERMISO="
				+ ISecurityAPI.ISPAC_RIGHTS_CREATEEXP + DBUtil.addAndInResponsibleCondition("UID_USR", resp) + ") ORDER BY NOMBRE";
			
			pcdset.query(cnt,sqlquery);

			return pcdset.disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:getProcs()", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}

	// EXPEDIENTES //

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IWorklistAPI#getProcesses(int)
	 */
	public IItemCollection getProcesses(int idStagePCD) throws ISPACException
	{
		String resp = getRespString();
		DbCnt cnt = context.getConnection();
		try
		{
			return WLProcessDAO.getExps(cnt, resp, idStagePCD).disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:getProcesses("
					+ idStagePCD + ")", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IWorklistAPI#getProcesses(int)
	 */
	public IItemCollection getProcesses(int idStagePCD,String path) throws ISPACException
	{
		String resp = getRespString();
		DbCnt cnt = context.getConnection();
		try
		{
	        WLWorklistFactory wlfactory=new WLWorklistFactory();
	        WLProcessListBuilder wlbuilder=null;

    		wlbuilder=wlfactory.getProcessListBuilder(path);
            if (wlbuilder==null)
                throw new ISPACException("No se ha podido construir WLProcessListBuilder");

            CollectionDAO coldao = wlbuilder.getWorklist(cnt, idStagePCD, resp);
            return coldao.disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:getProcesses("
					+ idStagePCD + ")", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}
	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IWorklistAPI#getProcesses(int)
	 */
	public IItemCollection getProcesses(int idStagePCD,InputStream processlistxml) throws ISPACException
	{
		String resp = getRespString();
		return getProcesses(idStagePCD, processlistxml, resp);
		
	}
	
	private IItemCollection getProcesses(int idStagePCD,InputStream processlistxml , String resp ) throws ISPACException{
		
		DbCnt cnt=null;
		try
		{	cnt = context.getConnection();
	        WLWorklistFactory wlfactory=new WLWorklistFactory();
	        WLProcessListBuilder wlbuilder=null;

    		wlbuilder=wlfactory.getProcessListBuilder(processlistxml);
            if (wlbuilder==null)
                throw new ISPACException("No se ha podido construir WLProcessListBuilder");

            CollectionDAO coldao = wlbuilder.getWorklist(cnt, idStagePCD, resp);
            return coldao.disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:getProcesses("
					+ idStagePCD + ")", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}

	public String getRespStringSubProceso(int idStagePCD, int id_pcd) throws ISPACException{
		String responsibles="";
		try{
			IInvesflowAPI invesFlowApi = context.getAPI();
			int idPcd=id_pcd;
			ISecurityAPI securityAPI = invesFlowApi.getSecurityAPI();
			responsibles = getRespString();
			
			// Comprobar si el usuario es SUPERVISOR 
			if (!Responsible.SUPERVISOR.equalsIgnoreCase(responsibles)) {
				
				if(idStagePCD!=0){
					IItem stage = invesFlowApi.getProcedureStage(idStagePCD);
					idPcd=stage.getInt("ID_PCD");
				}
				
				IItemCollection itemcol=securityAPI.getPermission(ISecurityAPI.PERMISSION_TPOBJ_PROCEDURE, idPcd, null);
				while(itemcol.next()){
					responsibles+=" , '"+itemcol.value().getString("ID_RESP")+"'";
				}
			}
			
		} catch (ISPACException ie) {
			logger.error("Error en WLWorklist:getRespStringSubProceso("
					+ idStagePCD + ")", ie);
			throw new ISPACException("Error en WLWorklist:getRespStringSubProceso("
					+ idStagePCD + ")", ie);
		}
		return responsibles;
	}
	
	public IItemCollection getSubProcesses(int idActivityPCD, InputStream istream) throws ISPACException {
		return getProcesses(idActivityPCD, istream, getRespStringSubProceso(idActivityPCD, 0));
	}
	
	public IItemCollection getSubProcesses(int pcdId, int idActivityPCD, InputStream istream) throws ISPACException {
		
		DbCnt cnt = null;
		
		try {
			
			cnt = context.getConnection();
			WLWorklistFactory wlfactory = new WLWorklistFactory();
			WLProcessListBuilder wlbuilder = wlfactory.getProcessListBuilder(istream);
			if (wlbuilder == null) {
				throw new ISPACException("No se ha podido construir WLProcessListBuilder");
			}

			CollectionDAO coldao = wlbuilder.getWorklist(cnt, pcdId, idActivityPCD, getRespStringSubProceso(idActivityPCD, 0));
			return coldao.disconnect();
			
		} catch (ISPACException ie) {
			logger.error("Error al obtener los subprocesos", ie);
			throw new ISPACException("Error en WorklistAPI:getSubProcesses(" + idActivityPCD + ")", ie);
		} finally {
			context.releaseConnection(cnt);
		}
	}
	
	
	
	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IWorklistAPI#getProcess(java.lang.String)
	 */
	public IItem getProcess(String numexp) throws ISPACException
	{
		DbCnt cnt = context.getConnection();
		try
		{
			return new WLProcessDAO(cnt, numexp);
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:getProcessByNumexp("
					+ numexp + ")", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}

	public IItemCollection getProcesses(String[] numexps) throws ISPACException {
		
		DbCnt cnt = context.getConnection();
		try
		{
			WLProcessDAO process = new WLProcessDAO(cnt);
			CollectionDAO colDao = process.loadProcessByNumExps(cnt, numexps);
			return colDao.disconnect();
			
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:getProcesses("
					+ numexps.toString() + ")", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}

	}
	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IWorklistAPI#getProcess(int)
	 */
	public IItem getProcess(int id) throws ISPACException
	{
		DbCnt cnt = context.getConnection();
		try
		{
			return new WLProcessDAO(cnt, id);
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:getProcess(" + id
					+ ")", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}

	// FASES //

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IWorklistAPI#getStages(int)
	 */
	public IItemCollection getStages(int idProcedure) throws ISPACException
	{
		String resp = getRespString();
		return getStages(idProcedure, resp);
	}
	
	public IItemCollection getStages(int idProcedure, String resp) throws ISPACException
	{
		//Comprobamos si es un subprocesos
		
		DbCnt cnt = context.getConnection();
		List id_pcd_padres=null;
		try
		{
			IInvesflowAPI invesFlowAPI = context.getAPI();
			IProcedure iProcedure = invesFlowAPI.getProcedure(idProcedure);
			
			if(iProcedure.getInt("TIPO")==IPcdElement.TYPE_SUBPROCEDURE){
				IItemCollection itemcol=invesFlowAPI.getCatalogAPI().queryCTEntities(ICatalogAPI.ENTITY_P_TASK, "where id_cttramite in (select id from spac_ct_tramites where id_subproceso="+idProcedure+")");
		    	//Obtenemos la lista de padres
		    	id_pcd_padres= new ArrayList();
		    	while(itemcol.next()){
		    	
		    		id_pcd_padres.add(((IItem)itemcol.value()).get("ID_PCD"));
		    	}
			}
			return WLStageDAO.getStages(cnt, resp, idProcedure, id_pcd_padres).disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:getStages("
					+ idProcedure + ")", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}

	public IItemCollection getActivities(int idSubproceso) throws ISPACException {
		return getActivities(idSubproceso, getRespString());
	}
	
	public IItemCollection getActivities(int subProcedureId, String resp) throws ISPACException {
		
		DbCnt cnt = context.getConnection();
		List<Integer> id_pcd_padres = new ArrayList<Integer>();
		IItemCollection activities = null;
		
		try {
			
			IInvesflowAPI invesFlowAPI = context.getAPI();
			IProcedure iProcedure = invesFlowAPI.getProcedure(subProcedureId);

			if (iProcedure.getInt("TIPO") == IPcdElement.TYPE_SUBPROCEDURE) {
				
				// Obtenemos la lista de padres
				IItemCollection itemcol = invesFlowAPI.getCatalogAPI().queryCTEntities(
						ICatalogAPI.ENTITY_P_TASK,
						"where id_cttramite in (select id from spac_ct_tramites where id_subproceso=" + subProcedureId + ")");
				while (itemcol.next()) {
					id_pcd_padres.add(itemcol.value().getInt("ID_PCD"));
				}

				activities = WLActivityDAO.getActivities(cnt, resp, subProcedureId, id_pcd_padres).disconnect();
			}
			
			return activities;
			
		} catch (ISPACException ie) {
			logger.error("Error al obtener las actividades del subprocedimiento ["
					+ subProcedureId + "] y resp [" + resp + "]", ie);
			throw new ISPACException("Error en WorklistAPI:getActivities("
					+ subProcedureId + ", " + resp + ")", ie);
		} finally {
			context.releaseConnection(cnt);
		}
	}

	//TODO ESTE METODO MEJOR NO XQ TIENE QUE SER EL IDPCD PADRE DEL ACTUAL YA QUE EL SUBPROCESO
	//PUEDE PARTICIPAR EN VARIOS
	/*public IItemCollection getStages(int idProcedure, String resp, List id_pcd_padres) throws ISPACException
	{
		DbCnt cnt = context.getConnection();
		try
		{	
			return WLStageDAO.getStages(cnt, resp, idProcedure, id_pcd_padres).disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:getStages("
					+ idProcedure + ")", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}*/
	// TRÁMITES DE PROCEDIMIENTOS//

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IWorklistAPI#getProcedureTasks()
	 */
	public IItemCollection getProcedureTasks() throws ISPACException
	{
		String resp = getRespString();
		return getProcedureTasks(resp);
	}
	
	public IItemCollection getProcedureTasks(String resp) throws ISPACException
	{
		DbCnt cnt = context.getConnection();
		try
		{
			return WLTaskDAO.getTasks(cnt, resp).disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:getTasksPcd()", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}

	
	public IItemCollection getProcedureClosedTasks(String resp) throws ISPACException
	{
		DbCnt cnt = context.getConnection();
		try
		{
			return WLClosedTaskDAO.getTasks(cnt, resp).disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:getProcedureClosedTasks()", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}
	
	public IItemCollection getProcedureTasksGroupByPcd(String resp) throws ISPACException
	{
		DbCnt cnt = context.getConnection();
		try
		{
			return WLTaskDAO.getTasksGroupByPcd(cnt, resp).disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WorklistAPI:getProcedureTasksGroupByPcd()", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}

	
	public IItemCollection getProcedureClosedTasksGroupByPcd(String resp) throws ISPACException
	{
		DbCnt cnt = context.getConnection();
		try
		{
			return WLClosedTaskDAO.getTasksGroupByPcd(cnt, resp).disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WorklistAPI:getProcedureClosedTasksGroupByPcd()", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}	
	
	
	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IWorklistAPI#getProcedureStageTasks(int)
	 */
	public IItemCollection getProcedureStageTasks(int idStagePCD)
			throws ISPACException
	{
		DbCnt cnt = context.getConnection();
		try
		{
			return PFaseDAO.getTasks(cnt, idStagePCD).disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:getTasksByStage("
					+ idStagePCD + ")", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}

	public IItemCollection getProcedureStageTasksToStart(int idStagePCD)
			throws ISPACException {

		DbCnt cnt = context.getConnection();
		
		try {
			return PFaseDAO.getTasksToCreate(cnt, idStagePCD).disconnect();
		} catch (ISPACException ie) {
			logger.error("Error al obtener los trámites de la fase [" + idStagePCD + "] que se pueden iniciar", ie);
			throw new ISPACException("Error en WLWorklist:getProcedureStageTasksToStart(" + idStagePCD + ")", ie);
		} finally {
			context.releaseConnection(cnt);
		}

	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IWorklistAPI#getTasks(int)
	 */
	public IItemCollection getTasksPCD(int taskPcdId) throws ISPACException
	{
		String resp = getRespString();
		DbCnt cnt = context.getConnection();
		try
		{
			return WLTaskDAO.getTasksPCD(cnt, resp, taskPcdId).disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:getTasksPCD(" + taskPcdId
					+ ")", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}

	public IItemCollection getTasksCTL(int taskCtlId) throws ISPACException
	{
		String resp = getRespString();
		DbCnt cnt = context.getConnection();
		try
		{
			return WLTaskDAO.getTasksCTL(cnt, resp, taskCtlId).disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:getTasksCTL(" + taskCtlId
					+ ")", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}

	public IItemCollection getTasksCTL(int taskCtlId, int pcdId) throws ISPACException {
		
		String resp = getRespString();
		DbCnt cnt = context.getConnection();
		try
		{
			return WLTaskDAO.getTasksCTL(cnt, resp, taskCtlId, pcdId).disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:getTasksCTL(" + taskCtlId
					+ ")", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}

	public IItemCollection getClosedTasksPCD(int taskPcdId) throws ISPACException
	{
		String resp = getRespString();
		DbCnt cnt = context.getConnection();
		try
		{
			return WLClosedTaskDAO.getTasksPCD(cnt, resp, taskPcdId).disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:getClosedTasksPCD(" + taskPcdId
					+ ")", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}

	public IItemCollection getClosedTasksCTL(int taskCtlId) throws ISPACException
	{
		String resp = getRespString();
		DbCnt cnt = context.getConnection();
		try
		{
			return WLClosedTaskDAO.getTasksCTL(cnt, resp, taskCtlId).disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:getClosedTasksCTL(" + taskCtlId
					+ ")", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}

	public IItemCollection getClosedTasksCTL(int taskCtlId, int pcdId) throws ISPACException {
		
		String resp = getRespString();
		DbCnt cnt = context.getConnection();
		try
		{
			return WLClosedTaskDAO.getTasksCTL(cnt, resp, taskCtlId, pcdId).disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:getClosedTasksCTL(" + taskCtlId
					+ ")", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}	
	
	
	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IWorklistAPI#getTasks(int, java.lang.String)
	public IItemCollection getTasks(int idTaskPCD,String taskname) throws ISPACException
	{
		String resp = getRespString();
		DbCnt cnt = context.getConnection();
		try
		{
			return WLTaskDAO.getTasks(cnt, resp, idTaskPCD,taskname).disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:getTasks(" + idTaskPCD
					+ ")", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}
	*/

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.IWorklistAPI#getTasks(java.lang.String)
	 */
	public IItemCollection getTasks(String numexp) throws ISPACException
	{
		DbCnt cnt = context.getConnection();
		try
		{
			return WLTaskDAO.getTaskByNumexp(cnt, numexp).disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:getTaskByNumexp("
					+ numexp + ")", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}
	
	public IItemCollection getTasks(String numexp, int idPTask) throws ISPACException{
		
		String resp = getRespString();
		return getTasks(numexp, idPTask, resp);
	}
	
	public IItemCollection getTasks(String numexp, int idPTask, String resp) throws ISPACException{

        DbCnt cnt = context.getConnection();
        try
        {
            return WLTaskDAO.getTasksByNumExpsAndIdPTask(cnt, resp, numexp, idPTask).disconnect();
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en WLWorklist:getTasks("
                    + numexp + "," + idPTask + ")", ie);
        }
        finally
        {
            context.releaseConnection(cnt);
        }
	}

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.api.IWorklistAPI#getTasks(java.lang.String)
     */
    public IItemCollection getTasks(String taskids[]) throws ISPACException
    {
        DbCnt cnt = context.getConnection();
        try
        {
            return WLTaskDAO.getTasks(cnt, taskids).disconnect();
        }
        catch (ISPACException ie)
        {
            throw new ISPACException("Error en WLWorklist:getTasks("
                    + taskids + ")", ie);
        }
        finally
        {
            context.releaseConnection(cnt);
        }
    }

    public boolean isInResponsibleList (String sUID, int supervisionType) throws ISPACException {
    	return isInResponsibleList(sUID,supervisionType,null);
    }
    
    public boolean isInResponsibleList (String sUID, IItem item) throws ISPACException {
    	return isInResponsibleList(sUID,ISecurityAPI.SUPERV_TOTALMODE, item);
    }
    
    public boolean isInResponsibleList (String sUID, int supervisionType, IItem item) throws ISPACException {
    	
		DbCnt cnt = context.getConnection();
		List listSupervisados=new ArrayList();
		List listSustituidos=new ArrayList();
		int i=0;
		
		
		try {
			
			// Comprobar el UID en la cadena de responsabilidad directa del usuario
			Responsible user = context.getUser();
			if (user.isInResponsibleList(sUID)) {
				return true;
			}

			if (mWorkMode == IWorklistAPI.SUPERVISOR) {
				
				SecurityMgr securityMgr = new SecurityMgr(cnt);
				
				// Comprobar si el usuario es Supervisor en Modo Modificación
				if (securityMgr.isFunction(user.getUID(), ISecurityAPI.FUNC_TOTALSUPERVISOR)) {
					return true;
				}
				
				// Comprobar si el usuario es Supervisor en Modo Consulta, si aplica
				if ((supervisionType != ISecurityAPI.SUPERV_TOTALMODE) 
						&& securityMgr.isFunction(user.getUID(), ISecurityAPI.FUNC_MONITORINGSUPERVISOR)) {
					return true;
				}
				
				// Comprobar las responsabilidades referentes a las supervisiones
				IItemCollection collection = null;
				if (supervisionType == ISecurityAPI.SUPERV_TOTALMODE) {
					collection = securityMgr.getAllSuperviseds(user, ISecurityAPI.SUPERV_TOTALMODE);
				} else {
					collection = securityMgr.getAllSuperviseds(user);
				}
				
				while (collection.next()) {
					IItem supervisor = (IItem) collection.value();
					listSupervisados = getRespListFromEntryUID(supervisor.getString("UID_SUPERVISADO"));
					if (listSupervisados.contains(sUID)) {
						return true;
					}
				}

				// Comprobar las responsabilidades referentes a las sustituciones
				collection = securityMgr.getAllSubstitutes(user);
				while (collection.next()) {
					
					IItem substitute = (IItem) collection.value();
					listSustituidos = getRespListFromEntryUID(substitute.getString("UID_SUSTITUIDO"));
					if (listSustituidos.contains(sUID))
						return true;
				}
				
				//Comprobar si tenemos permisos a nivel de catálogo
				if(item!=null){
					if(logger.isDebugEnabled()){
						logger.debug("El responsable "+sUID +"No tiene permisos genéricos, " +
								"vamos a comprobar si tiene permisos a nivel" +
								"de catálogo (permisos sobre un procedimiento)");
					}
					ISecurityAPI securityAPI = context.getAPI().getSecurityAPI();
					int []permisos= new int[1];
					permisos[0]=ISecurityAPI.PERMISSION_TYPE_EDIT;
					
					//Componemos la cadena de responsabilidad separada por comas
					List resp= user.getRespList();
					String cadenaResp="";
					//Al menos esta el propio usuario
					cadenaResp="'"+DBUtil.replaceQuotes(resp.get(0).toString())+"'";
					for(i=1;i<resp.size();i++){
						cadenaResp+=" , '"+DBUtil.replaceQuotes(resp.get(i).toString())+"'";
					}
					
					for(i=0;i<listSupervisados.size();i++){
						cadenaResp+=" , '"+DBUtil.replaceQuotes(listSupervisados.get(i).toString())+"'";
					}
					for(i=0;i<listSustituidos.size();i++){
						cadenaResp+=" , '"+DBUtil.replaceQuotes(listSustituidos.get(i).toString())+"'";
					}
					//Item puede ser IProcess ,  IStage , ITask
					if(item instanceof ITask){
						return securityAPI.existPermissions((ITask)item, cadenaResp, permisos);
						
					}
					else if(item instanceof IStage){
						
						if(IPcdElement.TYPE_SUBPROCEDURE==item.getInt("TIPO")){
							IInvesflowAPI api = context.getAPI();
							IEntitiesAPI entitiesAPI = api.getEntitiesAPI();
							String sql = "WHERE TIPO="+IPcdElement.TYPE_PROCEDURE+" AND" ;
							sql+=" NUMEXP = '"+DBUtil.replaceQuotes(item.getString("NUMEXP"))+"'";
							IItemCollection col = entitiesAPI.queryEntities(SpacEntities.SPAC_FASES, sql);
							if(col.next()){
								IItem fasePadre= col.value();
								int id = fasePadre.getKeyInt();
								IStage stage = api.getStage(id);
								return securityAPI.existPermissions(stage, cadenaResp, permisos);
							}	
						}
						return securityAPI.existPermissions((IStage)item, cadenaResp, permisos);
					}
					else if(item instanceof IProcess){
						return securityAPI.existPermissions((IProcess)item, cadenaResp, permisos);
						
					}
				}
				
				
			}
			
		} finally {
			context.releaseConnection(cnt);
		}

		return false;
    }
    
	public boolean isInResponsibleList(String sUID) throws ISPACException {
		return isInResponsibleList(sUID, ISecurityAPI.SUPERV_TOTALMODE);
	}    

	public IItemCollection getBatchTasks()
	throws ISPACException {
		
		String resp = getRespString();
		DbCnt cnt = context.getConnection();
		try
		{
			return WLBatchTaskDAO.getBatchTasks(cnt, resp).disconnect();
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:getBatchTasks()", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
		
	}

	public int countBatchTasks() throws ISPACException {
		String resp = getRespString();
		return countBatchTasks(resp);
	}
	
	public int countBatchTasks(String resp) throws ISPACException {

		DbCnt cnt = context.getConnection();
		try
		{
			return WLBatchTaskDAO.countBatchTasks(cnt, resp);
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:getTasksPcd()", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}

//	public IItem getBatchTask(int idBatchTask) throws ISPACException {
	
//		DbCnt cnt = context.getConnection();
//		try {
//			IItem ret = WLBatchTaskDAO.getBatchTask(cnt, idBatchTask);
//			return ret;
//
//		} catch (Exception e) {
//
//		} finally {
//			context.releaseConnection(cnt);
//		}
//
//		return null;
//	}

	public IItemCollection getExpsBatchTask(int idBatchTask) throws ISPACException {
		
		DbCnt cnt = context.getConnection();
		try
		{
			IItemCollection expdsTrAgrupada = WLBatchTaskExpsDAO.getBatchTasksExps(cnt,idBatchTask).disconnect();
			return expdsTrAgrupada;
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:getExpsBatchTask("
					+ idBatchTask + ")", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}

	public IItem getStage(String numExp) throws ISPACException {
		
		String resp = getRespString();
		DbCnt cnt = context.getConnection();
		try
		{
			IItemCollection fasesDelExpediente = WLStageDAO.getStage(cnt, resp, numExp).disconnect();
			for (Iterator itFases = fasesDelExpediente.iterator(); itFases.hasNext();) {
				IItem fase = (IItem) itFases.next();
				return fase;
			}
			return null;
		}
		catch (ISPACException ie)
		{
			throw new ISPACException("Error en WLWorklist:getStage("
					+ numExp + ")", ie);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}


	public IItemCollection getExpiredTerms(int type) throws ISPACException {
		
		String resp = getRespString();
		DbCnt cnt = context.getConnection();
		try {
			return DeadLineDAO.getTerms(cnt, type, resp).disconnect();
		}
		catch (ISPACException ie) {
			throw new ISPACException("Error en invesflowAPI:getTerms("+ type + ")", ie);
		}
		finally {
			context.releaseConnection(cnt);
		}	
	}
	
	/**
	 * Cuenta el numerode plazos vencidos hasta la fecha actual que son responsabilidad del
	 * usuario conectado.
	 *
	 * @param type
	 * @return n&uacute;mero de plazos vencidos hasta la fecha actual
	 * @throws ISPACException
	 */
	public int countExpiredTerms(int type) throws ISPACException {
		
		String resp = getRespString();
		return countExpiredTerms(type, resp);
	}
	
	/**
	 * Cuenta el numerode plazos vencidos hasta la fecha actual que son responsabilidad del
	 * usuario conectado.
	 *
	 * @param type
	 * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return n&uacute;mero de plazos vencidos hasta la fecha actual
	 * @throws ISPACException
	 */
	public int countExpiredTerms(int type, String resp) throws ISPACException {
		
		DbCnt cnt = context.getConnection();
		try {
			return DeadLineDAO.countTerms(cnt, type, resp);
		}
		catch (ISPACException ie) {
			throw new ISPACException("Error en invesflowAPI:countTerms()", ie);
		}
		finally {
			context.releaseConnection(cnt);
		}
	}
	

	public IItemCollection getExpiredTerms(int type, Date initDate, Date endDate) throws ISPACException {
		
		String resp = getRespString();
		DbCnt cnt = context.getConnection();
		try {
			return DeadLineDAO.getTerms(cnt, type, initDate, endDate, resp).disconnect();
		}
		catch (ISPACException ie) {
			throw new ISPACException("Error en invesflowAPI:getTerms("+ type + ")", ie);
		}
		finally {
			context.releaseConnection(cnt);
		}	
	}

	private void addRespListFromEntryUID(Collection respList, IItemCollection collection, String campo ) throws ISPACException{
		Iterator iterator = collection.iterator();
		while (iterator.hasNext())
		{
			IItem item = (IItem) iterator.next();
			List respListUser=getRespListFromEntryUID(item.getString(campo));
			int i=0;
			for(i=0; i<respListUser.size(); i++){
				if(!respList.contains(respListUser.get(i))){
					respList.add(respListUser.get(i));
				}
			}						
		}
		
	}



	
	
}