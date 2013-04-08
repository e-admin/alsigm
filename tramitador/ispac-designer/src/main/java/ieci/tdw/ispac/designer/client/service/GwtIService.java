package ieci.tdw.ispac.designer.client.service;

import ieci.tdw.ispac.designer.client.exceptions.DesignerException;
import ieci.tdw.ispac.designer.client.objetos.Condition;
import ieci.tdw.ispac.designer.client.objetos.DrawObject;
import ieci.tdw.ispac.designer.client.objetos.Procedimiento;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;

public interface GwtIService extends RemoteService {
	
	
	
	/**
	 * 
	 * @param nIdProc, Cuando un expediente ha sido finalizado la unica manera de obtener informacion es a través de numExp o nIdProc , 
	 * utilizamos el segundo por se un entero
	 * @param callback
	 * @throws DesignerException si ocurre algún error.
	 * @return Procedimiento informado
	 * @throws DesignerException
	 */
	public Procedimiento getProcedureBynIdProc(int nIdProc)throws DesignerException;
	
	/**
	 * 
	 * @param pcdId , Identificador del subproceso
	 * @return Procedimiento informado
	 * @throws DesignerException
	 */
	public Procedimiento getSubProcByPcdId(int pcdId, int idInstance)throws DesignerException;

	/**
	 * Obtiene la información del procedimiento.
	 * @param pcdId Identificador del procedimiento.
	 * @return Información del procedimiento.
	 * @throws DesignerException si ocurre algún error.
	 */
	public Procedimiento getProcedure(int pcdId) throws DesignerException;

	/**
	 * Obtiene la lista de fases del catálogo que no pertenecen al procedimiento.
	 * @param pcdId Identificador del procedimiento.
	 * @return Lista de fases del catálogo.
	 * @throws DesignerException si ocurre algún error.
	 */
	public List getCtStages(int pcdId) throws DesignerException;

	/**
	 * Añade una fase del catálogo al procedimiento.
	 * @param pcdId Identificador de la fase.
	 * @param ctStageId Identificador de la fase en el catálogo.
	 * @param x Coordenada x de la fase.
	 * @param y Coordenada y de la fase.
	 * @return Identificador de la fase creada.
	 * @throws DesignerException si ocurre algún error.
	 */
	public int addStage(int pcdId, int ctStageId, int x, int y) throws DesignerException;

	/**
	 * Añade una fase específica al procedimiento.
	 * @param pcdId Identificador de la fase.
	 * @param stageName Nombre de la fase.
	 * @param x Coordenada x de la fase.
	 * @param y Coordenada y de la fase.
	 * @return Identificador de la fase creada.
	 * @throws DesignerException si ocurre algún error.
	 */
	public int addStage(int pcdId, String stageName, int x, int y) throws DesignerException;

	/**
	 * Elimina una fase del procedimiento.
	 * @param pcdId Identificador del procedimiento.
	 * @param stageId Identificador de la fase en el procedimiento.
	 * @return Información del procedimiento
	 * @throws DesignerException si ocurre algún error.
	 */
	public Procedimiento removeStage(int pcdId, int stageId) throws DesignerException;

	/**
	 * Añade un nodo de sincronización al procedimiento.
	 * @param pcdId Identificador del procedimiento.
	 * @param type Tipo de nodo de sincronización ({@link }}).
	 * @param x Coordenada x del nodo de sincronización.
	 * @param y Coordenada y del nodo de sincronización.
	 * @return Identificador del nodo de sincronización creado.
	 * @throws DesignerException si ocurre algún error.
	 */
	public int addSyncNode(int pcdId, int type, int x, int y) throws DesignerException;

	/**
	 * Elimina el nodo de sincronización del procedimiento.
	 * @param pcdId Identificador del procedimiento.
	 * @param syncNodeId Identificador del nodo de sincronización en el procedimiento.
	 * @return Información del procedimiento 
	 * @throws DesignerException si ocurre algún error.
	 */
	public Procedimiento removeSyncNode(int pcdId, int syncNodeId) throws DesignerException;

	/**
	 * Añade una transición al procedimiento.
	 * @param pcdId Identificador del procedimiento.
	 * @param startNodeId Identificador del nodo origen de la transición.
	 * @param endNodeId Identificador del nodo destino de la transición.
	 * @return Identificador de la transición creada.
	 * @throws DesignerException si ocurre algún error.
	 */
	public int addFlow(int pcdId, int startNodeId, int endNodeId) 
		throws DesignerException;

	/**
	 * Elimina la transición del procedimiento.
	 * @param pcdId Identificador del procedimiento.
	 * @param flowId Identificador de la transición en el procedimiento.
	 * @throws DesignerException si ocurre algún error.
	 */
	public void removeFlow(int pcdId, int flowId) throws DesignerException;

	/**
	 * Obtiene los trámites asociados a la fase.
	 * @param stageId Identificador de la fase en el procedimiento.
	 * @return Lista de trámites.
	 */
	public List getTasks(int stageId) throws DesignerException;
	
	/**
	 * Añade un trámite a la fase del procedimiento.
	 * @param stageId Identificador de la fase en el procedimiento.
	 * @param ctTaskId Identificador del trámite en el catálogo de trámites.
	 * @return Identificador del trámite del procedimiento creado.
	 * @throws DesignerException si ocurre algún error.
	 */
	public int addTask(int stageId, int ctTaskId) throws DesignerException;

	/**
	 * Elimina el trámite del procedimiento.
	 * @param taskId Identificador del trámite en el procedimiento.
	 * @throws DesignerException si ocurre algún error.
	 */
	public void removeTask(int taskId) throws DesignerException;

	/**
	 * Actualiza las posiciones de las fases y nodos.
	 * @param drawObjects Lista de objetos ({@link DrawObject}).
	 * @throws DesignerException si ocurre algún error.
	 
	
	
	public void updatePositions(List drawObjects) throws DesignerException;*/
	
	/**
	 * 
	 * @param drawObject Objeto a actualizar la posicion
	 * @throws DesignerException 
	 */
	public void updatePosition(DrawObject drawObject) throws DesignerException;
	
	/**
	 * Devuelve la lista de fases en el procedimiento asociadas
	 * @param pcdId Procedimiento del que se quiere obtener las fases
	 * @return Lista de fases en el procedimiento asociadas
	 * @throws DesignerException
	 */
	public List getPStages(int pcdId)throws DesignerException;
	
	/**
	 * Devuelve en entorno con el que estamos trabajando, tramitador o catalogo
	 * @return 1 Si es el catalogo 2 si es el tramitador
	 * @throws DesignerException
	 */
	public int getEntorno()throws DesignerException;
	
	
	/**
	 * 
	 * @param stageId Identificador de la fase activa del expediente 
	 * @return Prodecidimiento con sus estados (fases y tramites) informados
	 * @throws DesignerException
	 */
	public Procedimiento getProcedureByStageId(int stageId)throws DesignerException;
	
	/**
	 * 
	 * @param orden de los tramites asociados a la fase
	 * @throws DesignerException
	 */
	public void reorderTasks(int [] orden) throws DesignerException;
	
	/**
	 * Obtiene la lista de entidades relacionadas con el procedimiento.
	 * @param pcdId Identificador del procedimiento
	 * @return Mapa de entidades con sus campos.
	 * @throws DesignerException si ocurre algún error.
	 */
	public Map getEntities(int pcdId) throws DesignerException;

	/**
	 * Obtiene la lista de reglas del catálogo.
	 * @return Lista de reglas del catálogo.
	 * @throws DesignerException si ocurre algún error.
	 */
	public Map getCTRules() throws DesignerException;

	/**
	 * Obtiene las reglas del catálogo que no estén asociadas al evento del objeto.
	 * @param objType Tipo de objeto.
	 * @param objId Identificador del objeto.
	 * @param eventCode Código de evento.
	 * @return Lista de reglas del catálogo.
	 * @throws DesignerException si ocurre algún error.
	 */
	public List getAvailableCTRules(int objType, int objId, int eventCode) 	
			throws DesignerException;

	/**
	 * Obtiene la lista de reglas de un objeto.
	 * @param objType Tipo de objeto.
	 * @param objId Identificador del objeto.
	 * @return Lista de reglas
	 * @throws DesignerException si ocurre algún error.
	 */
	public List getEventRules(int objType, int objId) throws DesignerException;

	/**
	 * Añade una regla asociada al evento de un objeto.
	 * @param objType Tipo de objeto.
	 * @param objId Identificador del objeto.
	 * @param eventCode Código de evento.
	 * @param rulesId Lista de identificadores de la regla.
	 * @param condition Información de la condición.
	 * @throws DesignerException si ocurre algún error.
	 */
	public void addEventRule(int objType, int objId, int eventCode, List rulesId, Condition condition) 
			throws DesignerException;
	
	/**
	 * Modifica la condición asociada al evento de un objeto.
	 * @param eventRuleId Identificador de la regla del evento.
	 * @param condition Condición.
	 * @throws DesignerException si ocurre algún error.
	 */
	public void updateEventRuleCondition(String eventRuleId, Condition condition) 
			throws DesignerException;

	/**
	 * Elimina un listado de reglas, cada una asociado a un evento
	 * @param eventRuleId Lista de identificador de la regla del evento.
	 * @throws DesignerException si ocurre algún error.
	 */
	public void removeEventsRules(List eventRuleIds) throws DesignerException;

	/**
	 * Incrementa el orden de las reglas asociadas a eventos de un objeto.
	 * @param eventRuleIds Identificadores de los eventos.
	 * @throws DesignerException si ocurre algún error.
	 */
	public void incOrderEventRules(String[] eventRuleIds) throws DesignerException;

	/**
	 * Decrementa el orden de las reglas asociadas a eventos de un objeto.
	 * @param eventRuleIds Identificadores de los eventos.
	 * @throws DesignerException si ocurre algún error.
	 */
	public void decOrderEventRules(String[] eventRuleIds) throws DesignerException;
	
	/**
	 * Obtiene los eventos asignables a un tipo de objeto.
	 * @param objType Tipo de objeto.
	 * @return Mapa de eventos.
	 * @throws DesignerException si ocurre algún error.
	 */
	public Map getEvents(int objType) throws DesignerException ;
	
	/**
	  * Elimina todas las condiciones de un flujo
	  * @param objType Tipo de objeto
	  * @param objId Identificador del objeto.
	  */
	 public void removeEventRules(int objType, int objId) throws DesignerException;
	


}
