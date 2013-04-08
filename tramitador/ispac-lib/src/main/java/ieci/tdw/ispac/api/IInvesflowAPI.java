package ieci.tdw.ispac.api;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.api.item.ITask;

import java.io.Serializable;

/**
 * Interface que proporciona la funcionalidad básica del framework.
 * A partir de éste se obtienen la mayoría de los interfaces
 * que proporcionan el resto de la funcionalidad e información del framework.
 *
 * @see ieci.tdw.ispac.api.item.IItem
 * @see ieci.tdw.ispac.api.item.IItemCollection
 */
public interface IInvesflowAPI extends Serializable
{
	/**
	 * Obtiene la definición de un procedimiento según su identificador.
	 *
	 * @param nIdProcedure identificador del procedimiento
	 * @return IProcedure IItem específico para el procedimiento
	 * @throws ISPACException
	 * @see IProcedure
	 */
	public IProcedure getProcedure(int nIdProcedure) throws ISPACException;

	/**
	 * Obtiene la definición de la fase en el procedimiento.
	 *
	 * @param nIdStagePCD identificador de la fase en el procedimiento.
	 * @return IItem
	 * @throws ISPACException
	 */
	public IItem getProcedureStage(int nIdStagePCD) throws ISPACException;

	/**
	 * Obtiene la información del nodo en el procedimiento.
	 *
	 * @param nIdNodePCD identificador del nodo en el procedimiento.
	 * @return IItem
	 * @throws ISPACException
	 */
	public IItem getProcedureNode(int nIdNodePCD) throws ISPACException;

	/**
	 * Obtiene la definición del trámite en el procedimiento.
	 *
	 * @param nIdTaskPCD identificador del trámite en el procedimiento
	 * @return IItem
	 * @throws ISPACException
	 */
	public IItem getProcedureTaskPCD(int nIdTaskPCD) throws ISPACException;

	/**
	 * Obtiene la definición del trámite en el procedimiento.
	 *
	 * @param nIdTaskCTL identificador del trámite en el catálogo
	 * @return IItem
	 * @throws ISPACException
	 */
	public IItem getProcedureTaskCTL(int nIdTaskPCD) throws ISPACException;
	/**
	 * Obtiene la colección de procedimientos definidos.
	 *
	 * @param query SQL para restringir el resultado
	 * @return IItem
	 * @throws ISPACException
	 */
	public IItemCollection getProcedures(String query) throws ISPACException;
	
	
	/**
	 * Obtiene la lista de procesos para un determinado procedimiento
	 * @param idProcedimiento Identificador del Procedimiento
	 * @return Colección de Procesos
	 * @throws ISPACException
	 */
	public IItemCollection getProcessesByProcedure(int idProcedimiento) throws ISPACException;

	/**
	 * Obtiene un proceso según su identificador.
	 * Un proceso es una instancia activa de un procedimiento.
	 *
	 * @param nIdProc identificador del proceso.
	 * @return IProcess IItem específico para el proceso
	 * @throws ISPACException
	 * @see IProcess
	 */
	public IProcess getProcess(int nIdProc) throws ISPACException;

	/**
	 * Busca un proceso dado su número de expediente. Si existe un
	 * más de proceso con el mismo número de expediente se devuelve el más reciente.
	 *
	 * @param numExp número de expediente.
	 * @return IProcess IItem específico para el proceso
	 * @throws ISPACException
	 * @see IProcess
	 */
	public IProcess getProcess(String numExp) throws ISPACException;

	
	/**
	 * Obtiene un subproceso instanciado segun su identificador.
	 *  
	 * @param nIdSubProc identificador del subproceso
	 * @return IItem específico para el subproceso
	 * @throws ISPACException
	 */
	public IProcess getSubProcess(int nIdSubProc)throws ISPACException;
	
	/**
	 * Obtiene todos los subprocesos asociados a un expediente
	 * @param numExp número de expediente
	 * @return Colección de subprocesos de un expediente
	 * @throws ISPACException
	 */
	public IItemCollection getSubProcess(String numExp) throws ISPACException;	
	
	/**
	 * Obtiene la definición de una fase activa o instanciada en el expediente,
	 * según su identificador.
	 *
	 * @param nIdStage identificador de la fase instanciada en el expediente
	 * @return IStage
	 * @throws ISPACException
	 * @see IStage
	 */
	public IStage getStage(int nIdStage) throws ISPACException;

	
	/**
	 * Obtiene una colección de definiciones de fases activas o instanciadas
	 * según una cadena de identificadores de fases activas.
	 *
	 * @param stages cadena de identificadores de fase separados por comas.
	 * @return IItemCollection, colección de objetos TXFaseDAO
	 * @throws ISPACException
	 * @see ieci.tdw.ispac.ispaclib.dao.tx.TXFaseDAO
	 */
	public IItemCollection getStages(String stages) throws ISPACException;

	/**
	 * Obtiene la definición de un trámite activo o instanciado en el expediente,
	 * según su identificador.
	 *
	 * @param nIdTask identificador del trámite instanciado en el expediente.
	 * @return ITask
	 * @throws ISPACException
	 * @see ITask
	 */
	public ITask getTask(int nIdTask) throws ISPACException;
	
	/**
	 * Obtiene la definición de un trámite activo o instanciado en el expediente,
	 * según su identificador de subproceso.
	 *
	 * @param nIdSubProcess identificador del subproceso en el trámite instanciado en el expediente.
	 * @return ITask
	 * @throws ISPACException
	 * @see ITask
	 */
	public ITask getTaskBySubProcess(int nIdSubProcess) throws ISPACException;

	/**
	 * Obtiene una colección de definiciones de trámites activos o instanciados
	 * según una cadena de identificadores de trámites activos. Los identificadores iran separados por comas.
	 *
	 * @param tasks cadena de identificadores de trámites separados por comas.
	 * @return IItemCollection, colección de objetos TXTramiteDAO
	 * @throws ISPACException
	 * @see ieci.tdw.ispac.ispaclib.dao.tx.TXTramiteDAO
	 */
	public IItemCollection getTasks(String tasks) throws ISPACException;

	/**
	 * Obtiene la colección de hitos alcanzados en el expediente indicado mediante
	 * el identificador del proceso.
	 *
	 * @param nIdProc identificador de un proceso
	 * @return IItemCollection, colección de objetos TXHitoDAO
	 * @throws ISPACException
	 * @see ieci.tdw.ispac.ispaclib.dao.tx.TXHitoDAO
	 */
	public IItemCollection getMilestones(int nIdProc) throws ISPACException;

	/**
	 * @param processId identificador de proceso
	 * @param stagePcdId identificador de fase en el procedmiento
	 * @param taskPcdId identificador de trámite en el procedimiento
	 * @param taskId identificador de trámite instanciado
	 * @param milestone hito
	 * @return Colección de hitos
	 * @throws ISPACException
	 */
	public IItemCollection getMilestones(int processId, int stagePcdId,int taskPcdId, int taskId, int milestone) throws ISPACException;
	/**
	 * Obtiene la colección de hitos alcanzadossegún
	 * el número de expediente de un proceso.
	 *
	 * @param numexp número de expediente.
	 * @return IItemCollection, colección de objetos TXHitoDAO
	 * @throws ISPACException
	 * @see ieci.tdw.ispac.ispaclib.dao.tx.TXHitoDAO
	 */
	public IItemCollection getMilestones(String numexp) throws ISPACException;

	/**
	 * Cuenta el número de expedientes enviados a la papelera.
	 * Solamente el supervisor(consulta o total) podrá visualizar la lista de 
	 * procesos enviados a la papelera
	 * @return
	 * @throws ISPACException
	 */
	public int countExpedientsSentToTrash()throws ISPACException;
	
	/**
	 * Obtiene la lista de expedientes enviados a la papelera
	 * @fechaInicio Fecha de creación del expediente
	 * @fechaEliminacion Fecha de eliminación del expediente
	 * @procedimiento Procedimiento al que pertenece el expediente
	 * @return
	 * @throws ISPACException
	 */
	public IItemCollection getExpedientsSentToTrash(String fechaInicio, String fechaEliminacion , int procedimiento)throws ISPACException;
	/**
	 * Devuelve cierto si el expediente se encuentra en la papelera y falso en caso contrario
	 * @param numexp Número de expediente
	 * @return
	 * @throws ISPACException
	 */
	public boolean isExpedientSentToTrash(String numexp)throws ISPACException;
	/**
	 * Obtiene el interface {@link IWorklistAPI}
	 * @return el interface IWorklistAPI
	 * @throws ISPACException
	 */
	public IWorklistAPI getWorkListAPI() throws ISPACException;

	/**
	 * Obtiene el interface {@link IEntitiesAPI}
	 * @returnel interface IEntitiesAPI
	 * @throws ISPACException
	 */
	public IEntitiesAPI getEntitiesAPI() throws ISPACException;

	/**
	 * Obtiene el interface {@link ITXTransaction}
	 * @return el interface ITXTransaction
	 * @throws ISPACException
	 */
	public ITXTransaction getTransactionAPI() throws ISPACException;

	/**
	 * Obtiene el interface {@link IRespManagerAPI}
	 * @return el interface IRespManagerAPI
	 * @throws ISPACException
	 */
	public IRespManagerAPI getRespManagerAPI() throws ISPACException;

	/**
	 * Obtiene el interface {@link IGenDocAPI}
	 * @return el interface IGenDocAPI
	 * @throws ISPACException
	 */
	public IGenDocAPI getGenDocAPI() throws ISPACException;

	/**
	 * Obtiene el interface {@link ISearchAPI}
	 * @return el interface ISearchAPI
	 * @throws ISPACException
	 */
	public ISearchAPI getSearchAPI() throws ISPACException;

	/**
	 * Obtiene el interface {@link IInboxAPI}
	 * @return el interface IInboxAPI
	 * @throws ISPACException
	 */
	public IInboxAPI getInboxAPI() throws ISPACException;

	/**
	 * Obtiene el interface {@link ISchedulerAPI}
	 * @return el interface ISchedulerAPI
	 * @throws ISPACException
	 */
	public ISchedulerAPI getSchedulerAPI() throws ISPACException;

	/**
	 * Obtiene el interface {@link IThirdPartyAPI}
	 * @return el interface IThirdPartyAPI
	 * @throws ISPACException
	 */
	public IThirdPartyAPI getThirdPartyAPI() throws ISPACException;

	/**
	 * Obtiene el interface {@link ICatalogAPI}
	 * @return el interface ICatalogAPI
	 * @throws ISPACException
	 */
	public ICatalogAPI getCatalogAPI() throws ISPACException;

	/**
	 * Obtiene el interface {@link ITemplateAPI}
	 * @return el interface ITemplateAPI
	 * @throws ISPACException
	 */
	public ITemplateAPI getTemplateAPI() throws ISPACException;

	/**
	 * Obtiene el interface {@link getProcedureAPI}
	 * @return el interface getProcedureAPI
	 * @throws ISPACException
	 */
    public IProcedureAPI getProcedureAPI();

	/**
	 * Obtiene el interface {@link ISecurityAPI}
	 * @return el interface ISecurityAPI
	 * @throws ISPACException
	 */
	public ISecurityAPI getSecurityAPI() throws ISPACException;
	
	/**
	 * Obtiene el interface {@link ISignAPI}
	 * @return el interface ISignAPI
	 * @throws ISPACException
	 */
	public ISignAPI getSignAPI() throws ISPACException;
	
	/**
	 * Obtiene una tarea agrupada
	 * @param idBatchTask
	 * @return
	 * @throws ISPACException
	 */
	public IItem getBatchTask(int nIdBatchTask) throws ISPACException;
	
	/**
	 * Obtiene el interface {@link ICustomAPI}
	 * @returnel interface ICustomAPI
	 * @throws ISPACException
	 */
	public ICustomAPI getCustomAPI() throws ISPACException;

	/**
	 * Obtiene el interface {@link IBPMAPI}}
	 * @return interface IBPMAPI
	 * @throws ISPACException si ocurre algún error.
	 */
	public IBPMAPI getBPMAPI() throws ISPACException;

	/**
	 * @param idProcess Identificador del proceso
	 * @return Fases instanciadas para el proceso
	 * @throws ISPACException si ocurre algún error
	 */
	public IItemCollection getStagesProcess(int idProcess) throws ISPACException;
	
	/**
	 * Obtiene el API de generación de informes.
	 * @return API de generación de informes.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IReportsAPI getReportsAPI() throws ISPACException;
	
	/**
	 * Obtiene el interface {@link IPublisherAPI}
	 * @return el interface IPublisherAPI
	 * @throws ISPACException si ocurre algún error.
	 */
	public IPublisherAPI getPublisherAPI() throws ISPACException;

	
	/**
	 * Obtiene el interface {@link IRegisterAPI}
	 * @return el interface IRegisterAPI
	 * @throws ISPACException
	 */
	public IRegisterAPI getRegisterAPI()  throws ISPACException;
	
}