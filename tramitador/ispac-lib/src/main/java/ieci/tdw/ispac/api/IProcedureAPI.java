package ieci.tdw.ispac.api;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.graph.GInfo;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.context.StateContext;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * IProcedureAPI
 *
 */
public interface IProcedureAPI
{
    int CLONEPCD_PARENT_ROOT 		= 1;
    int CLONEPCD_PARENT_CHILDREN 	= 2;
    int CLONEPCD_PARENT_SIBLING 	= 3;

    public int createProcedure(String newname, List ctstages,
            Map ctstagestask) throws ISPACException;

    public int cloneProcedure(int procedureid, String newname, int parent)
            throws ISPACException;

    public int cloneProcedure(int procedureid, String newname,
            List ctstages, Map ctstagestask,int parent) throws ISPACException;

    public int modifyProcedure(int procedureid, String newname,
            List ctstages, Map ctstagestask) throws ISPACException;

    public int createDraft(int procedureid, String newname)
		throws ISPACException;

    public int createDraft(int procedureid, String newname,
        List ctstages, Map ctstagestask) throws ISPACException;

    /**
     * Crea un borrador del subproceso indicado.
     * @param subPcdId Identificador del subproceso.
     * @param subPcdName Nombre del subproceso.
     * @param activitiesNames Nombres de las actividades.
     * @return Identificador del subproceso creado.
     * @throws ISPACException si ocurre algún error.
     */
    public int createSubProcedureDraft(int subPcdId, String subPcdName,
            List activitiesNames) throws ISPACException;

    /**
     * Clona el subproceso indicado.
     * @param subPcdId Identificador del subproceso.
     * @param subPcdName Nombre del subproceso.
     * @param activitiesNames Nombres de las actividades.
     * @return Identificador del subproceso creado.
     * @throws ISPACException si ocurre algún error.
     */
    public int cloneSubProcedure(int subPcdId, String subPcdName,
            List activitiesNames, int parent) throws ISPACException;

    /**
     * Modifica el subproceso indicado.
     * @param subPcdId Identificador del subproceso.
     * @param subPcdName Nombre del subproceso.
     * @param activitiesNames Nombres de las actividades.
     * @return Identificador del subproceso.
     * @throws ISPACException si ocurre algún error.
     */
    public int modifySubProcedure(int subPcdId, String subPcdName,
            List activitiesNames) throws ISPACException;

    /**
     * Crea un subproceso.
     * @param subPcdName Nombre del subproceso.
     * @param activitiesNames Nombres de las actividades.
     * @return Identificador del subproceso creado.
     * @throws ISPACException si ocurre algún error.
     */
    public int createSubProcedure(String subPcdName, List activitiesNames)
    	throws ISPACException;

    public void deleteProcedure(int procedureid) throws ISPACException;

    /**
     * Elimina una entidad del procedimiento,
     * eliminando también las posibles asociaciones de formularios de la entidad a nivel de fase y trámite
     * y los eventos asociados a la entidad en el procedimiento.
     *
     * @param pcdEntityId Identificador de la entidad en el procedimiento.
     * @throws ISPACException si ocurre algún error.
     */
    public void deletePcdEntity(int pcdEntityId) throws ISPACException;

    public void markDraftAsCurrent(int procedureid) throws ISPACException;

    public Map getCTStageTaskIds(int procedureid) throws ISPACException;

    public int addStage(int pcdid, int ctstageid, String ctstagename)
    	throws ISPACException;

    public int addStage(int pcdid, int ctstageid, String ctstagename, GInfo ginfo)
		throws ISPACException;

    public void removeStage(int pcdid, int pstageid) throws ISPACException;

    public int addSyncNode(int pcdid, int type) throws ISPACException;

    public int addSyncNode(int pcdid, int type, GInfo ginfo) throws ISPACException;

	public void removeSyncNode(int pcdid, int psincnodeid)
		throws ISPACException;

    public int createFlow(int pcdid, int origId, int destId)
		throws ISPACException;

    public void removeFlow(int pcdid, int pflowid) throws ISPACException;

    public int addTask(int pstageid,int cttaskid) throws ISPACException;

    public void removeTask(int ptaskid) throws ISPACException;

    public void moveTaskUp(int ptaskid) throws ISPACException;

    public void moveTaskDown(int ptaskid) throws ISPACException;

    public void reorderTasks(int[] ptaskids) throws ISPACException;

    public String getProcedureXml( int procedureId)
    	throws ISPACException;

    public File exportProcedure(int procedureId, String path)
    	throws ISPACException;

    public String importProcedure(int parentPcdId, File file, boolean test)
		throws ISPACException;

    public String importProcedure(int parentPcdId, boolean asVersion, File file, boolean test)
	throws ISPACException;

    /**
     * Obtiene el uso de entidades, aplicaciones, reglas, fases, tareas,
     * documentos y eventos como una colección de items.
     * El resultado se obtiene como un join entre las tablas de
     * procedimientos implicadas.
     */
    public boolean isInUse( int entityId, int keyId) throws ISPACException;

    // SPAC_CT_PROCEDIMIENTOS -> CTPCD
    // SPAC_P_PROCEDIMIENTOS -> PROCD
    // SPAC_P_ENTIDADES -> FORM
    public IItemCollection getEntityProceduresUse(int entityId) throws ISPACException;

    // SPAC_CT_PROCEDIMIENTOS -> CTPCD
    // SPAC_P_PROCEDIMIENTOS -> PROCD
    // SPAC_P_FASES -> STAGE
    // SPAC_P_FRMFASES -> FORM
    public IItemCollection getEntityStagesUse(int entityId) throws ISPACException;

    // SPAC_CT_PROCEDIMIENTOS -> CTPCD
    // SPAC_P_PROCEDIMIENTOS -> PROCD
    // SPAC_P_FASES -> STAGE
    // SPAC_P_TRAMITES -> TASK
    // SPAC_P_FRMFASES -> FORM
    public IItemCollection getEntityTasksUse(int entityId) throws ISPACException;

    // SPAC_CT_ENTIDADES
    public IItemCollection getApplicationEntitiesUse(int applicationId) throws ISPACException;

    // SPAC_CT_PROCEDIMIENTOS -> CTPCD
    // SPAC_P_PROCEDIMIENTOS -> PROCD
    // SPAC_P_ENTIDADES -> FORM
    public IItemCollection getApplicationProceduresUse(int applicationId) throws ISPACException;

    // SPAC_CT_PROCEDIMIENTOS -> CTPCD
    // SPAC_P_PROCEDIMIENTOS -> PROCD
    // SPAC_P_FASES -> STAGE
    // SPAC_P_FRMFASES -> FORM
    public IItemCollection getApplicationStagesUse(int applicationId) throws ISPACException;

    // SPAC_CT_PROCEDIMIENTOS -> CTPCD
    // SPAC_P_PROCEDIMIENTOS -> PROCD
    // SPAC_P_FASES -> STAGE
    // SPAC_P_TRAMITES -> TASK
    // SPAC_P_FRMTRAMITES -> FORM
    public IItemCollection getApplicationTasksUse(int applicationId) throws ISPACException;

    public IItemCollection getRuleEventsUse(int ruleId) throws ISPACException;

    // SPAC_CT_PROCEDIMIENTOS -> CTPCD
    // SPAC_P_PROCEDIMIENTOS -> PROCD
    // SPAC_P_EVENTOS -> EVENT
    public IItemCollection getRuleProceduresUse(int ruleId) throws ISPACException;

    // SPAC_CT_PROCEDIMIENTOS -> CTPCD
    // SPAC_P_PROCEDIMIENTOS -> PROCD
    // SPAC_P_ENTIDADES -> PENTITY
    // SPAC_P_EVENTOS -> EVENT
    // SPAC_CT_ENTIDADES -> CTENTITY
    public IItemCollection getRuleProcedureEntitiesUse(int ruleId) throws ISPACException;

    // SPAC_CT_PROCEDIMIENTOS -> CTPCD
    // SPAC_P_PROCEDIMIENTOS -> PROCD
    // SPAC_P_ENTIDADES -> PENTITY
    // SPAC_CT_ENTIDADES -> CTENTITY
    public IItemCollection getRuleProcedureEntityFormsUse(int ruleId) throws ISPACException;

    // SPAC_CT_PROCEDIMIENTOS -> CTPCD
    // SPAC_P_PROCEDIMIENTOS -> PROCD
    // SPAC_P_FASES -> STAGE
    // SPAC_P_FRMFASES -> FORM
    // SPAC_CT_ENTIDADES -> CTENTITY
    public IItemCollection getRuleStageEntityFormsUse(int ruleId) throws ISPACException;

    // SPAC_CT_PROCEDIMIENTOS -> CTPCD
    // SPAC_P_PROCEDIMIENTOS -> PROCD
    // SPAC_P_FASES -> STAGE
    // SPAC_P_TRAMITES -> TASK
    // SPAC_P_FRMTRAMITES -> FORM
    // SPAC_CT_ENTIDADES -> CTENTITY
    public IItemCollection getRuleTaskEntityFormsUse(int ruleId) throws ISPACException;

    // SPAC_CT_PROCEDIMIENTOS -> CTPCD
    // SPAC_P_PROCEDIMIENTOS -> PROCD
    // SPAC_P_ENTIDADES -> PENTITY
    // SPAC_CT_ENTIDADES -> CTENTITY
    public IItemCollection getRuleProcedureEntityVisibleUse(int ruleId) throws ISPACException;

    // SPAC_CT_PROCEDIMIENTOS -> CTPCD
    // SPAC_P_PROCEDIMIENTOS -> PROCD
    // SPAC_P_FASES -> STAGE
    // SPAC_P_FRMFASES -> FORM
    // SPAC_CT_ENTIDADES -> CTENTITY
    public IItemCollection getRuleStageEntityVisibleUse(int ruleId) throws ISPACException;

    // SPAC_CT_PROCEDIMIENTOS -> CTPCD
    // SPAC_P_PROCEDIMIENTOS -> PROCD
    // SPAC_P_FASES -> STAGE
    // SPAC_P_TRAMITES -> TASK
    // SPAC_P_FRMTRAMITES -> FORM
    // SPAC_CT_ENTIDADES -> CTENTITY
    public IItemCollection getRuleTaskEntityVisibleUse(int ruleId) throws ISPACException;

    // SPAC_CT_PROCEDIMIENTOS -> CTPCD
    // SPAC_P_PROCEDIMIENTOS -> PROCD
    // SPAC_P_FASES -> STAGE
    // SPAC_P_EVENTOS -> EVENT
    public IItemCollection getRuleStagesUse(int ruleId) throws ISPACException;

    // SPAC_CT_PROCEDIMIENTOS -> CTPCD
    // SPAC_P_PROCEDIMIENTOS -> PROCD
    // SPAC_P_FASES -> STAGE
    // SPAC_P_TRAMITES -> TASK
    // SPAC_P_EVENTOS -> EVENT
    public IItemCollection getRuleTasksUse(int ruleId) throws ISPACException;

    // SPAC_CT_PROCEDIMIENTOS -> CTPCD
    // SPAC_P_PROCEDIMIENTOS
    // SPAC_P_FLUJOS
    public IItemCollection getRuleFlowsUse(int ruleId)  throws ISPACException;

    // SPAC_CT_PROCEDIMIENTOS -> CTPCD
    // SPAC_P_PROCEDIMIENTOS
    public IItemCollection getProcedureUse(int procedureId) throws ISPACException;

    // SPAC_CT_PROCEDIMIENTOS -> CTPCD
    // SPAC_P_PROCEDIMIENTOS -> PROCD
    // SPAC_P_FASES -> STAGE
    public IItemCollection getStageUse(int stageId) throws ISPACException;

    // SPAC_CT_PROCEDIMIENTOS -> CTPCD
    // SPAC_P_PROCEDIMIENTOS -> PROCD
    // SPAC_P_CTOSFIRMA -> CTOSFIRMA
    public IItemCollection getSignProcessUse(int signProcessId) throws ISPACException;

	/**
	 * Para sacar los procedimientos que están utilizando el trámite que se le pasa por parámetro
	 * @param taskId
	 * @return
	 * @throws ISPACException
	 */
    // SPAC_CT_PROCEDIMIENTOS -> CTPCD
    // SPAC_P_PROCEDIMIENTOS -> PROCD
    // SPAC_P_FASES -> STAGE
    // SPAC_P_TRAMITES -> TASK
    public IItemCollection getTaskUse(int taskId) throws ISPACException;

	/**
	 * Para sacar las fases que utilizan el trámite que le pasamos como parámetro
	 * @param taskId
	 * @return
	 * @throws ISPACException
	 */
	public IItemCollection getStagesTaskUse(int taskId) throws ISPACException;

    // SPAC_CT_PROCEDIMIENTOS -> CTPCD
    // SPAC_P_PROCEDIMIENTOS -> PROCD
    // SPAC_P_FASES -> STAGE
    // SPAC_P_FSTD -> DOCUMENT
    public IItemCollection getDocumentStagesUse(int documentId) throws ISPACException;

    // SPAC_P_PROCEDIMIENTOS -> SUBPCD
    // SPAC_P_FASES -> ACTIVITY
    // SPAC_P_FSTD -> DOCUMENT
    public IItemCollection getDocumentActivitiesUse(int documentId) throws ISPACException;

    // SPAC_CT_TRAMITES -> TASK
    // SPAC_CT_TRTD -> DOCUMENT
    public IItemCollection getDocumentTasksUse(int documentId) throws ISPACException;

    /**
	 * Obtiene la lista de procedimientos relacionados con el informe.
	 *
	 * @param reportId
	 *            Identificador del informe.
	 * @return Lista de procedimientos.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItemCollection getReportProceduresUse(int reportId)
			throws ISPACException;

	/**
	 * Obtiene la lista de fases relacionadas con el informe.
	 *
	 * @param reportId
	 *            Identificador del informe.
	 * @return Lista de fases.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItemCollection getReportStagesUse(int reportId)
			throws ISPACException;

	/**
	 * Obtiene la lista de trámites relacionados con el informe.
	 *
	 * @param reportId
	 *            Identificador del informe.
	 * @return Lista de trámites.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItemCollection getReportTasksUse(int reportId)
			throws ISPACException;

	/**
	 * Obtiene la lista de actividades relacionadas con el informe.
	 *
	 * @param reportId
	 *            Identificador del informe.
	 * @return Lista de actividades.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public IItemCollection getReportActivitiesUse(int reportId)
			throws ISPACException;

	/**
	 * Obtiene el listado de formularios de busqueda relacionados con el informe.
	 * @param reportId
	 * 				Identificador del informe
	 * @return Lista de informes
	 * @throws ISPACException
	 * 						Si ocurre algún error
	 */
	public IItemCollection getReportSearchForm(int reportId) throws ISPACException;

    public IItemCollection getTaskTpDoc(int taskId) throws ISPACException;
    
    /**
     * Obtiene las dependencias de un trámite.
     * @param ptaskId Identificador del trámite en el procedimiento.
     * @return Lista de dependencias.
     * @throws ISPACException
     */
    public IItemCollection getTaskDependencies(int ptaskId) throws ISPACException;
    	
	/**
	 * Añade una dependencia entre trámites.
	 * 
	 * @param ptaskId
	 *            Identificador del trámite en el procedimiento.
	 * @param parentPtaskId
	 *            Identificador del trámite en el procedimiento del que depende
	 *            el trámite.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public void addTaskDependency(int ptaskId, int parentPtaskId)
			throws ISPACException;

    /**
     * Elimina la dependencia entre trámites.
     * @param dependencyId Identificador de la dependencia.
     * @throws ISPACException
     */
    public void deleteTaskDependency(int dependencyId) throws ISPACException;
    
    /**
     * Devuelve los tipos de documentos de la fase del procedimiento
     * @param pcdStageId: Identificar de la fase del procedimiento. (Id en SPAC_P_FASES)
     * @return
     * @throws ISPACException
     */
    public IItemCollection getStageTpDoc(int pcdStageId) throws ISPACException;

    public IItemCollection getTpDocsTemplates(int tpDoc)
    throws ISPACException;

    public IItemCollection getProcTemplates(int tpDoc, int procId)
    throws ISPACException;

    public IItemCollection getProcedureByName(String name)
    throws ISPACException;

    public IItem getProcedureByCode(String code, int state)
    throws ISPACException;

    public IItem getProcedureById(int pcdId)
    throws ISPACException;

    public IItemCollection getSubprocedureByName(String name)
    throws ISPACException;

    /**
     * @param pcdId Identificador del procedimiento
     * @param stagePcdId Identificador de la fase en el procedimiento
     * @return true si es la primera fase del procedimiento, false en caso contrario
     * @throws ISPACException
     */
    public boolean isFirstStage(int pcdId, int stagePcdId)
    throws ISPACException;

    /**
     * @param pcdId Identificador del procedimiento
     * @param stagePcdId Identificador de la fase en el procedimiento
     * @return true si es la &uacute;ltima fase del procedimiento, false en caso contrario
     * @throws ISPACException
     */
    public boolean isLastStage(int pcdId, int stagePcdId)
    throws ISPACException;

    /**
     * Obtiene la lista de fases de un procedimiento.
     * @param pcdId Identificador del procedimiento.
     * @return Lista de fases.
     * @throws ISPACException si ocurre algún error.
     */
    public IItemCollection getStages(int pcdId) throws ISPACException;

    /**
     * Obtiene la lista de trámites de una fase en el procedimiento.
     * @param pcdStageId Identificador de la fase del procedimiento.
     * @return Lista de trámites.
     * @throws ISPACException si ocurre algún error.
     */
    public IItemCollection getStageTasks(int pcdStageId) throws ISPACException;

    /**
     * Obtiene la lista de nodos de sincronización del procedimiento.
     * @param pcdId Identificador del procedimiento.
     * @return Lista de nodos de sincronización.
     * @throws ISPACException si ocurre algún error.
     */
    public IItemCollection getSyncNodes(int pcdId) throws ISPACException;

    /**
     * Obtiene la lista de flujos de entrada del nodo.
     * @param nodeId Identificador del nodo.
     * @return Lista de flujos de entrada.
     * @throws ISPACException si ocurre algún error.
     */
    public IItemCollection getNodeInputFlows(int nodeId) throws ISPACException;

    /**
     * Obtiene la lista de flujos de salida de un nodo.
     * @param nodeId Identificador del nodo.
     * @return Lista de flujos de salida.
     * @throws ISPACException si ocurre algún error.
     */
    public IItemCollection getNodeOutputFlows(int nodeId) throws ISPACException;

    /**
     * Obtiene la lista de nodos origen que se pueden asociar a un flujo para
     * un nodo destino.
     * @param pcdId Identificador del procedimiento.
     * @param nodeId Identificador del nodo destino del flujo.
     * @return Lista de nodos.
     * @throws ISPACException si ocurre algún error.
     */
    public IItemCollection getAvailableInputFlowNodes(int pcdId, int nodeId)
    	throws ISPACException;

    /**
     * Obtiene la lista de nodos destino que se pueden asociar a un flujo para
     * un nodo origen.
     * @param pcdId Identificador del procedimiento.
     * @param nodeId Identificador del nodo destino del flujo.
     * @return Lista de nodos.
     * @throws ISPACException si ocurre algún error.
     */
    public IItemCollection getAvailableOutputFlowNodes(int pcdId, int nodeId)
    	throws ISPACException;

    /**
     * Actualiza la posición de los nodos del procedimiento.
     * @param gInfoMap Mapa con la posición de los nodos del procedimiento.
     * @throws ISPACException si ocurre algún errror.

    public void updateGInfo(Map gInfoMap) throws ISPACException;*/

    /**
     *
     * @param info  Objeto con la informacion de la posicion del nodo
     * @param integer Identificador del nodo
     * @throws ISPACException
     */
	public void updateGInfoNode(GInfo info, Integer integer) throws ISPACException;


	/**
	 * @param stageCtId Identificador de fase en el catálogo
	 * @return true si el procedimiento tiene asociada la fase, false en caso contrario
	 * @throws ISPACException
	 */
	public boolean isStageAsociated(int pcdId, int stageCtId) throws ISPACException ;

	/**
	 * @param pcdId Identificador del procedimiento
	 * @param stageCtId Identificador de fase en el catálogo
	 * @return Fase asociada al procedimiento si es que está asociada
	 * @throws ISPACException
	 */
	public IItem getStagePcd(int pcdId, String archivoStageId) throws ISPACException ;

	/**
	 * Obtiene los informes relacionados con un objeto.
	 * @param objectId Identificador del objeto.
	 * @param objectType Tipo de objeto.
	 * @return IItemCollection Lista de informes.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItemCollection getPReports(int objectId , int objectType) throws ISPACException;


	/**
	 * Obtiene los informes relacionados con un objeto que tenga como responsable al usuario conectado o no tengan responsable
	 * @param objectId Identificador del objeto.
	 * @param objectType Tipo de objeto.
	 * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return IItemCollection Lista de informes.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItemCollection getPReports(int objectId , int objectType, String resp) throws ISPACException;

	/**
	  * Obtiene la lista de informes relacionados en el contexto del expediente.
	  * @param stateContext Contexto del expediente.
	  * @return Lista de informes disponibles para la fase o trámite actual ( generales + los del proc+ los propios de la fase o trámite)
	  * @throws ISPACException
	  */
	public IItemCollection getReports(StateContext stateContext) throws ISPACException;

	/**
	  * Obtiene la lista de informes relacionados en el contexto del expediente que tenga como responsable al usuario conectado
	  * o no tengan responsable
	  * @param stateContext Contexto del expediente.
	  * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	  * @return Lista de informes disponibles para la fase o trámite actual ( generales + los del proc+ los propios de la fase o trámite)
	  * @throws ISPACException
	  */
	public IItemCollection getReports(StateContext stateContext,String resp) throws ISPACException;

	/**
	 * Obtiene la lista de informes globales
	 * @return
	 * @throws ISPACException
	 */
	public IItemCollection getGlobalReports()throws ISPACException;


	/**
	 * Obtiene la lista de informes globales que tenga como responsable uno de sus resposnable a resp o no tengan responsable
	 * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return
	 * @throws ISPACException
	 */
	public IItemCollection getGlobalReports(String resp)throws ISPACException;

	/**
	 * Obtiene la lista de informes de tipo búsqueda que están asociados al formulario de búsqueda que recibe como parámetro
	 * @param idFrmBusqueda Identificador del fomulario de búsqueda
	 * @return Lista de informes disponibles para el formulario de búsqueda actual.
	 * @throws ISPACException
	 */
	public IItemCollection getSearchReports(int idFrmBusqueda)throws ISPACException;


	/**
	 * Obtiene la lista de informes de tipo búsqueda que están asociados al formulario de búsqueda que recibe como parámetro, y que
	 * o bien no tienen responsable o bien tienen al resp como uno de sus responsable
	 * @param idFrmBusqueda Identificador del fomulario de búsqueda
	 * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return Lista de informes disponibles para el formulario de búsqueda actual.
	 * @throws ISPACException
	 */
	public IItemCollection getSearchReports(int idFrmBusqueda, String resp)throws ISPACException;

	/**
     * Indica si hay informes asociados al formulario de busqueda
     * @deprecated
     * @param idForm
     * @return
     * @throws ISPACException
     */
    public boolean hashSearchReport(int idForm) throws ISPACException;

    /**
     * Indica si hay informes asociados al formulario de busqueda
     * @param idForm
     * @return
     * @throws ISPACException
     */
    public boolean hasSearchReport(int idForm) throws ISPACException;

    /**
     * Indica si hay informes asociados al formulario de busqueda a los que tenga acceso el usuario conectado
     * @param idForm
     * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
     * @return
     * @throws ISPACException
     */
    public boolean hasSearchReport(int idForm, String resp) throws ISPACException;

	/**
	 * Indica si el hay informes relacionados en el contexto del expediente.
	 * @param stateContext Contexto del expediente.
	 * @return True si hay algun informe disponible, false en caso contrario.
	 * @throws ISPACException si ocurre algún error.
	 */
	public boolean hasReports(StateContext stateContext)throws ISPACException;

	/**
	 * Indica si el hay informes relacionados en el contexto del expediente a los que tenga acceso el usuario conectado
	 * @param stateContext Contexto del expediente.
	 * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return True si hay algun informe disponible, false en caso contrario.
	 * @throws ISPACException si ocurre algún error.
	 */
	public boolean hasReports(StateContext stateContext, String resp)throws ISPACException;

	 /**
     * Indica si existe o no informes globales
     * @return
     * @throws ISPACException
     */
	public boolean hasGlobalReports()throws ISPACException;

	 /**
     * Indica si existe o no informes globales a los que tenga acceso el usuario conectado
     * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
     * @return
     * @throws ISPACException
     */
	public boolean hasGlobalReports(String resp)throws ISPACException;

    /**
	 * Relaciona un informe con el objeto indicado.
	 *
	 * @param objectType
	 *            Tipo de objeto.
	 * @param objectId
	 *            Identificador de objeto.
	 * @param reportId
	 *            Identificador del informe.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public void addPReport(int objectType, int objectId, int reportId)
			throws ISPACException;

	/**
	 * Elimina la relación entre el informe y el objeto seleccionado.
	 *
	 * @param objectType
	 *            Tipo de objeto.
	 * @param objectId
	 *            Identificador de objeto.
	 * @param reportId
	 *            Identificador del informe.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public void deletePReport(int objectType, int objectId, int reportId)
			throws ISPACException;

	/**
	 * Elimina todas las relaciones del informe.
	 *
	 * @param reportId
	 *            Identificador del informe.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public void deletePReport(int reportId) throws ISPACException;

	/**
	 * Elimina la relación entre el informe y los usuarios
	 *
	 * @param reportId
	 *            Identificador del informe.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public void deleteUsersAssociatedToReport(int reportId)
			throws ISPACException ;

	  /**
     * Devuelve el listado de expedientes y documentos que tienen instanciado el circuito de firma
     * @param signprocessId
     * @return
     * @throws ISPACException
     */
    // SPAC_DT_DOCUMENTOS -> DOCUMENTOS
    // SPAC_P_CTOSFIRMA -> CTOSFIRMA
    public IItemCollection getSignProcessInUse(int signProcessId) throws ISPACException;

    /**
     * Devuelve cierto si el circuito de firma esta en uso
     * @param signProcessId Identificador del circuito
     * @return
     * @throws ISPACException
     */
    public boolean isSignProcessInUse(int signProcessId) throws ISPACException;
    
    /**
     * Devuelve el número de expedientes (independientemente de su estado) , 
     * existentes para un determinado procedimiento
     * @param idPcd Identificador del procedimiento
     * @return Número de expedientes del procedimiento.
     * @throws ISPACException
     */
    public int countExpedientesByPcd( int idPcd)throws ISPACException;


}