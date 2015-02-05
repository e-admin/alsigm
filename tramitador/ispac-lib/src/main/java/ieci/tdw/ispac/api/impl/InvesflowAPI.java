package ieci.tdw.ispac.api.impl;

import ieci.tdw.ispac.api.IBPMAPI;
import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.ICustomAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInboxAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.IPublisherAPI;
import ieci.tdw.ispac.api.IRegisterAPI;
import ieci.tdw.ispac.api.IReportsAPI;
import ieci.tdw.ispac.api.IRespManagerAPI;
import ieci.tdw.ispac.api.ISchedulerAPI;
import ieci.tdw.ispac.api.ISearchAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.ISignAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.ITemplateAPI;
import ieci.tdw.ispac.api.IThirdPartyAPI;
import ieci.tdw.ispac.api.IWorklistAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.api.item.ITask;
import ieci.tdw.ispac.ispaclib.bpm.BPMAPIFactory;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PNodoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PProcedimientoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PTramiteDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXHitoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXTramiteDAO;
import ieci.tdw.ispac.ispaclib.dao.wl.WLBatchTaskDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.thirdparty.ThirdPartyConnectorFactory;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispactx.TXProcedureMgr;
import ieci.tdw.ispac.ispactx.TXTransaction;

/**
 * API para la gestión del workflow.
 *
 */
public class InvesflowAPI implements IInvesflowAPI {
	
	private final ClientContext mcontext;
	private final WorklistAPI mworklist;

	private TXTransaction mtxtransaction;

	/**
	 * Constructor.
	 * @param context Contexto del cliente.
	 */
	public InvesflowAPI(ClientContext context) {
		this.mcontext = context;
		this.mworklist = new WorklistAPI(mcontext);
		this.mtxtransaction = null;
	}

	public IProcedure getProcedure(int nIdProcedure) throws ISPACException {
		return TXProcedureMgr.getInstance().getProcedure(mcontext,nIdProcedure);
	}

	public IItem getProcedureTaskPCD(int nTaskPcdId) throws ISPACException {
		DbCnt cnt = null;
		try {
			cnt = mcontext.getConnection();
			return new PTramiteDAO(cnt,nTaskPcdId);
		} catch (ISPACException e) {
			throw new ISPACException(
					"Error en InvesflowAPI:getProcedureTaskPCD("+ nTaskPcdId 
					+ ")", e);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

	public IItem getProcedureTaskCTL(int nTaskCtlId) throws ISPACException {
		DbCnt cnt = null;
		try {
			cnt = mcontext.getConnection();
			return PTramiteDAO.getProcedureTaskCTL(cnt,nTaskCtlId);
		} catch (ISPACException e) {
			throw new ISPACException(
					"Error en InvesflowAPI:getProcedureTaskCTL("+ nTaskCtlId 
					+ ")", e);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

	public IItem getProcedureStage(int nIdStagePCD) throws ISPACException {
		DbCnt cnt = null;
		try {
			cnt = mcontext.getConnection();
			return new PFaseDAO(cnt,nIdStagePCD);
		} catch (ISPACException e) {
			throw new ISPACException("Error en InvesflowAPI:getProcedureStage("
					+ nIdStagePCD + ")", e);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

	/**
	 * Obtiene la información del nodo en el procedimiento.
	 *
	 * @param nIdNodePCD identificador del nodo en el procedimiento.
	 * @return IItem
	 * @throws ISPACException
	 */
	public IItem getProcedureNode(int nIdNodePCD) throws ISPACException {
		DbCnt cnt = null;
		try {
			cnt = mcontext.getConnection();
			return new PNodoDAO(cnt, nIdNodePCD);
		} catch (ISPACException e) {
			throw new ISPACException("Error en InvesflowAPI:getProcedureNode("
					+ nIdNodePCD + ")", e);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

	public IItemCollection getProcedures(String query) throws ISPACException {
		DbCnt cnt = null;
		try {
			cnt = mcontext.getConnection();
			return PProcedimientoDAO.getProcedures(cnt,query).disconnect();
		} catch (ISPACException ie) {
			throw new ISPACException("Error en invesflowAPI:getProcedures("
					+ query + ")", ie);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}


	public IItemCollection getProcessesByProcedure(int idProcedimiento) throws ISPACException{

		DbCnt cnt = null;
		try {
			cnt = mcontext.getConnection();
			return TXProcesoDAO.getProcesses(cnt, idProcedimiento, mcontext);
		} catch (ISPACException e) {
			throw new ISPACException("Error en InvesflowAPI:ProcessesByProcedure(" + idProcedimiento + ")", e);
		} finally {
			mcontext.releaseConnection(cnt);
		}
		
	}
	
	public IProcess getProcess(int nIdProc) throws ISPACException {
		DbCnt cnt = null;
		try {
			cnt = mcontext.getConnection();
			return new TXProcesoDAO(cnt,nIdProc,mcontext);

		} catch (ISPACException e) {
			throw new ISPACException("Error en InvesflowAPI:getProcess(" + nIdProc + ")", e);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

	public IProcess getProcess(String numExp) throws ISPACException {
		DbCnt cnt = null;
		try {
			cnt = mcontext.getConnection();
			IItemCollection itemcol = TXProcesoDAO.getProcess(cnt, numExp,
					mcontext);
			if (!itemcol.next())
//				throw new ISPACNullObject(
//						"No se ha encontrado el proceso con número de expediente ["
//								+ numExp + "]");

				throw new ISPACNullObject("exception.expedients.unknown", new Object[]{numExp});
				
				return (IProcess) itemcol.value();
		} catch (ISPACException e) {
			if (e instanceof ISPACNullObject){ 
				throw new ISPACInfo(e.getMessage(), e.getArgs(), false);
			}
			throw new ISPACException("Error en InvesflowAPI:getProcess("+ numExp + ")", e);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

	
	public IProcess getSubProcess(int nIdSubProc)throws ISPACException{
		return getProcess(nIdSubProc);
	}
	
	public IItemCollection getSubProcess(String numExp) throws ISPACException {
		DbCnt cnt = null;
		try {
			cnt = mcontext.getConnection();
			return TXProcesoDAO.getSubProcess(cnt,numExp,mcontext);
		} catch (ISPACException e) {
			throw new ISPACException("Error en InvesflowAPI:getSubProcess(" + numExp + ")", e);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}	
	
	
	public IStage getStage(int nIdStage) throws ISPACException {
		DbCnt cnt = null;
		try {
			cnt = mcontext.getConnection();
			return new TXFaseDAO(cnt, nIdStage);
		}
		catch (ISPACNullObject ino) {
			throw new ISPACNullObject("Error en InvesflowAPI:getStage("
					+ nIdStage + ")", ino);
		}
		catch (ISPACException ie) {
			throw new ISPACException("Error en InvesflowAPI:getStage("
					+ nIdStage + ")", ie);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}
	

	public IItemCollection getStages(String stages) throws ISPACException {
		DbCnt cnt = null;
		try {
			cnt = mcontext.getConnection();
			return TXFaseDAO.getStages(cnt, stages).disconnect();
		} catch (ISPACException ie) {
			throw new ISPACException("Error en invesflowAPI:getStages("
					+ stages + ")", ie);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

	public IItemCollection getStagesProcess(int idProcess) throws ISPACException {
		DbCnt cnt = null;
		try {
			cnt = mcontext.getConnection();
			return TXFaseDAO.getStagesProcess(cnt, idProcess).disconnect();
		} catch (ISPACException ie) {
			throw new ISPACException("Error en invesflowAPI:getStagesProcess("
					+ idProcess + ")", ie);
		} finally {
			mcontext.releaseConnection(cnt);
		}
		
	}
	
	
	public ITask getTask(int nIdTask) throws ISPACException {
		DbCnt cnt = null;
		try {
			cnt = mcontext.getConnection();
			return new TXTramiteDAO(cnt, nIdTask);
		} catch (ISPACNullObject e) {
			throw new ISPACNullObject("El trámite con id[" + nIdTask
					+ "] no existe o se ha cerrado", e);
		} catch (ISPACException e) {
			throw new ISPACException("Error en InvesflowAPI:getTask("
					+ nIdTask + ")", e);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}
	
	public ITask getTaskBySubProcess(int nIdSubProcess) throws ISPACException {
		DbCnt cnt = null;
		try {
			cnt = mcontext.getConnection();
			return TXTramiteDAO.getTaskBySubProcess(cnt, nIdSubProcess);
		} catch (ISPACNullObject e) {
			throw new ISPACNullObject("El trámite no existe o se ha cerrado", e);
		} catch (ISPACException e) {
			throw new ISPACException("Error en InvesflowAPI:getTaskByProcess("
					+ nIdSubProcess + ")", e);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

	public IItem getBatchTask(int nIdBatchTask) throws ISPACException {
		DbCnt cnt = null;
		try {
			cnt = mcontext.getConnection();
			IItem ret = WLBatchTaskDAO.getBatchTask(cnt, nIdBatchTask);
			return ret;

		} catch (Exception e) {
			// TODO: handle exception

		} finally {
			mcontext.releaseConnection(cnt);
		}

		// TODO Auto-generated method stub
		return null;
	}
	
	public IItemCollection getTasks(String tasks) throws ISPACException {
		DbCnt cnt = null;
		try {
			cnt = mcontext.getConnection();
			return TXTramiteDAO.getTasks(cnt, tasks).disconnect();
		} catch (ISPACException ie) {
			throw new ISPACException("Error en invesflowAPI:getTasks(" + tasks
					+ ")", ie);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}
	

	//	public IProcess getProcess(int nIdProcess)
	//	throws ISPACException
	//	{
	//
	//	}

	public IItemCollection getMilestones(int nIdProc) throws ISPACException {
		DbCnt cnt = null;

		try {
			cnt = mcontext.getConnection();
			return TXHitoDAO.getMilestones(cnt, nIdProc).disconnect();
		} catch (ISPACException ie) {
			throw new ISPACException("Error en invesflowAPI:getMilestones("
					+ nIdProc + ")", ie);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}

	public IItemCollection getMilestones(int nIdProc, int stagePcdId, int taskPcdId, int taskId, int milestone) throws ISPACException{
		DbCnt cnt = null;
		String query =  "ID_FASE = "+stagePcdId+
        				" AND ID_TRAMITE = "+ taskPcdId +
				        " AND HITO = "+milestone +
				        " AND INFO LIKE '%<id_tramite>" + taskId + "</id_tramite>%'";
		try {
			cnt = mcontext.getConnection();
			return TXHitoDAO.getMilestones(cnt, nIdProc, query).disconnect();
		} catch (ISPACException ie) {
			throw new ISPACException("Error en invesflowAPI:getMilestones("+ nIdProc + ")", ie);
		} finally {
			mcontext.releaseConnection(cnt);
		}	
	}
	public IItemCollection getMilestones(String numexp) throws ISPACException {
		DbCnt cnt = null;
		try {
			cnt = mcontext.getConnection();
			return TXHitoDAO.getMilestones(cnt, numexp).disconnect();
		} catch (ISPACException ie) {
			throw new ISPACException("Error en invesflowAPI:getMilestones("
					+ numexp + ")", ie);
		} finally {
			mcontext.releaseConnection(cnt);
		}
	}
	/**
	 * Cuenta el número de expedientes enviados a la papelera.
	 * Solamente el supervisor(consulta o total) podrá visualizar la lista de 
	 * procesos enviados a la papelera
	 * @return
	 * @throws ISPACException
	 */
	public int countExpedientsSentToTrash()throws ISPACException{
		DbCnt cnt = mcontext.getConnection();
		try {
			return TXProcesoDAO.countProcess(cnt, "", "ESTADO="+TXConstants.STATUS_DELETED +"AND TIPO="+IProcess.PROCESS_TYPE);
		}
		catch (ISPACException ie) {
			throw new ISPACException("Error en invesflowAPI:countExpedientsSentToTrash()", ie);
		}
		finally {
			mcontext.releaseConnection(cnt);
		}
		
	}
	
	/**
	 * Obtiene la lista de expedientes enviados a la papelera
	 * @fechaInicio Fecha de creación del expediente
	 * @fechaEliminacion Fecha de eliminación del expediente
	 * @procedimiento Procedimiento al que pertenece el expediente
	 * @return
	 * @throws ISPACException
	 */
	public IItemCollection getExpedientsSentToTrash(String fechaInicio, String fechaEliminacion , int procedimiento)throws ISPACException{
		DbCnt cnt = mcontext.getConnection();
		try {
			String sMaxResultados = ISPACConfiguration.getInstance().get(ISPACConfiguration.MAX_EXPS_SHOW_TRASH);
			int max=0;
			if(StringUtils.isNotBlank(sMaxResultados)){
				max=TypeConverter.parseInt(sMaxResultados.trim(),0);
				
			}
			return TXProcesoDAO.getExpedients(cnt, "PROCESOS.TIPO=" + IProcess.PROCESS_TYPE 
					+ " AND PROCESOS.ESTADO="+TXConstants.STATUS_DELETED, 
					max , fechaInicio, fechaEliminacion, procedimiento);
		}
		catch (ISPACException ie) {
			throw new ISPACException("Error en invesflowAPI:getExpedientsSentToTrash()", ie);
		}
		finally {
			mcontext.releaseConnection(cnt);
		}
	}
	/**
	 * Devuelve cierto si el expediente se encuentra en la papelera y falso en caso contrario
	 * @param numExp Número de expediente
	 * @return
	 * @throws ISPACException
	 */
	public boolean isExpedientSentToTrash(String numExp)throws ISPACException{
		
		IProcess exp =getProcess(numExp);
		return exp.getInt ("ESTADO") == TXConstants.STATUS_DELETED;
		
	}

	public IWorklistAPI getWorkListAPI() throws ISPACException {
		return mworklist;
	}

	public IEntitiesAPI getEntitiesAPI() throws ISPACException {
		return new EntitiesAPI(mcontext);
	}

	public ITXTransaction getTransactionAPI() throws ISPACException {
		if (mtxtransaction == null)
			mtxtransaction = new TXTransaction(mcontext);

		return mtxtransaction;
	}

	public IRespManagerAPI getRespManagerAPI() throws ISPACException {
		return new RespManagerAPI(mcontext);
	}

	public IGenDocAPI getGenDocAPI() throws ISPACException {
		return new GenDocAPI(mcontext);
	}

	public ISearchAPI getSearchAPI() throws ISPACException {
		return new SearchAPI(mcontext);
	}

	public IInboxAPI getInboxAPI() throws ISPACException {
		return new InboxAPI(mcontext);
	}

	public ISchedulerAPI getSchedulerAPI() throws ISPACException {
		return new SchedulerAPI(mcontext);
	}

	public IThirdPartyAPI getThirdPartyAPI() throws ISPACException {
		return ThirdPartyConnectorFactory.getInstance().getThirdPartyAPI();
	}

	public ICatalogAPI getCatalogAPI() throws ISPACException {
		return new CatalogAPI(mcontext);
	}

	public ITemplateAPI getTemplateAPI() throws ISPACException {
		return new TemplateAPI(mcontext);
	}

	public IProcedureAPI getProcedureAPI() {
		return new ProcedureAPI(mcontext);
	}

	public ISecurityAPI getSecurityAPI() throws ISPACException {
		return new SecurityAPI(mcontext);
	}
	/**
	 * Obtiene una instancia de la api de firmas
	 * utilizada en ispac.
	 * @since 24-04-2009
	 * @return ISignAPI
	 * @throws ISPACException si ocurre algún error.
	 */
	public ISignAPI getSignAPI() throws ISPACException {
		return new SignAPI(mcontext);
	}

	public ICustomAPI getCustomAPI() throws ISPACException {
		return new CustomAPI(mcontext);
	}

	/**
	 * Obtiene el interface {@link IBPMAPI}}
	 * @return interface IBPMAPI
	 * @throws ISPACException si ocurre algún error.
	 */
	public IBPMAPI getBPMAPI() throws ISPACException {
		return BPMAPIFactory.getBPMAPI(mcontext);
	}
	
	/**
	 * Obtiene el API de generación de informes.
	 * @return API de generación de informes.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IReportsAPI getReportsAPI() throws ISPACException {
		return new ReportsAPI(mcontext);
	}

	/**
	 * Obtiene el API de publicación.
	 * @return API de publicación.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IPublisherAPI getPublisherAPI() throws ISPACException {
		return new PublisherAPI(mcontext);
	}

	/**
	 * Obtiene el API de registro.
	 * @return API de registro.
	 * @throws ISPACException si ocurre algún error.
	 */	public IRegisterAPI getRegisterAPI() throws ISPACException {
		return new RegisterAPI(mcontext);
	}
}