package ieci.tdw.ispac.api;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.rule.IRuleContextParams;

import java.util.Date;
import java.util.Map;

public interface ITXTransaction {
	
	/**
	 * Crea un nuevo proceso a partir del procedimiento indicado
	 * @param nProcedure  identificador del procedimiento que regirá el proceso.
	 * @return identificador del proceso creado
	 * @throws ISPACException
	 */
	public int createProcess(int nProcedure) throws ISPACException;

	
	/**
	 * Crea un nuevo proceso a partir del procedimiento indicado
	 * @param nProcedure identificador del procedimiento que regirá el proceso (SPAC_P_PROCEDIMIENTOS.
	 * @param params mapa con nombres de parámetro como claves y sus correspespondientes valores. 
	 * 	Posibles valores:
	 * 		COD_PCD:codigo procedimiento
	 * 		
	 * @return identificador del proceso creado
	 * @throws ISPACException
	 */
	public int createProcess(int nProcedure,Map params) throws ISPACException;


	/**
	 * Crea un nuevo proceso a partir del procedimiento indicado pero reutilizando
	 *
	 * @param nProcedure identificador del procedimiento que regirá el proceso.
	 * @param numexp Número de expediente específico.
	 * @param params mapa con nombres de parámetro como claves y sus correspespondientes valores. 
	 * 	Posibles valores:
	 * 		COD_PCD:codigo procedimiento
	 * @param type Tipo proceso a crear. 
	 * 		Sus valores pueden ser @IProcess.PROCESS_TYPE o @IProcess.SUBPROCESS_TYPE
	 * @return identificador del proceso creado
	 * @throws ISPACException
	 */
	public int createProcess(int nProcedure,String numexp,Map params, int type) throws ISPACException;

	/**
	 * @param nProcedure identificador del procedimiento que regirá el proceso.
	 * @param numexp Número de expediente específico.
	 * @param subProcessUID UID del subproceso retornado por el BPM
	 * @param activityUID UID de la actividad retornado por el BPM
	 * @param subProcessRespId Responsable a asignar en el subproceso
	 * @param activityRespId Responsabla a asignar en la actividad del subproceso 
	 * @return array con los identificadores del subproceso iniciado y de la actividad dentro del subproceso
	 * @throws ISPACException
	 */
	public int[] createSubProcess(int nProcedure, String numexp, String subProcessUID,
			String activityUID, String subProcessRespId, String activityRespId)
			throws ISPACException;

	/**
	 * @param nProcedure identificador del procedimiento que regirá el proceso.
	 * @param numexp Número de expediente específico.
	 * @param subProcessUID UID del subproceso retornado por el BPM
	 * @param activityUID UID de la actividad retornado por el BPM
	 * @param subProcessRespId Responsable a asignar en el subproceso
	 * @param activityRespId Responsabla a asignar en la actividad del subproceso 
	 * @return array con los identificadores del subproceso iniciado y de la actividad dentro del subproceso
	 * @param params mapa con nombres de parámetro como claves y sus correspespondientes valores.
	 * @throws ISPACException
	 */
	public int[] createSubProcess(int nProcedure, String numexp, String subProcessUID,
			String activityUID, String subProcessRespId, String activityRespId, Map params)
			throws ISPACException;

	/**
	 * Impide la tramitación del proceso marcando todas sus trámites y fases como suspendidas.
	 * @param nIdProc identificador de proceso a cancelar/suspender
	 * @throws ISPACException
	 */
	public void cancelProcess(int nIdProc) throws ISPACException;

	/**
	 * Impide la tramitación del proceso marcando todas sus trámites y fases como suspendidas.
	 * @param nIdProc identificador de proceso a cancelar/suspender
	 * @param params Parámetros para las reglas.
	 * @throws ISPACException
	 */
	public void cancelProcess(int nIdProc, Map params) throws ISPACException;

	/**
	 * Reactiva el proceso volviendose éste a tramitar de forma normal.
	 * @param nIdProc identificador de proceso suspendido
	 * @throws ISPACException
	 */
	public void resumeProcess(int nIdProc) throws ISPACException;

	/**
	 * Reactiva el proceso volviendose éste a tramitar de forma normal.
	 * @param nIdProc identificador de proceso suspendido
	 * @param params Parámetros para las reglas.
	 * @throws ISPACException
	 */
	public void resumeProcess(int nIdProc, Map params) throws ISPACException;

	/**
	 * Envia a la papelera el proceso. 
	 * Enviar a la papelera supone cambiar el estado del proceso, de las fases activas y de los trámites a eliminados
	 * @param nIdProc Identificador del proceso a eliminar
	 * @param params Parámetros para las reglas
	 * @throws ISPACException
	 */
	public void sendProcessToTrash(int nIdProc, Map params)throws ISPACException;
	/**
	 * Envia a la papelera el proceso. 
	 * Enviar a la papelera supone cambiar el estado del proceso, de las fases activas y de los trámites a eliminados
	 * @param nIdProc Identificador del proceso a eliminar
	 * @throws ISPACException
	 */
	public void sendProcessToTrash(int nIdProc) throws ISPACException;
	
	/**
	 * Restaura un proceso enviado a la papelera
	 * @param nIdProc Identificador del proceso
	 * @throws ISPACException
	 */
	public void restoreProcess(int nIdProc)throws ISPACException;
	
	/**
	 * Restaura un proceso enviado a la papelera
	 * @param nIdProc Identificador del proceso a eliminar
	 * @param params Parámetros para las reglas
	 * @throws ISPACException
	 */
	public void restoreProcess(int nIdProc , Map params)throws ISPACException;
	
	/**
	 * Elimina definitivamente un proceso enviado a la papelera
	 * @param nIdProc Identificador del proceso
	 * @throws ISPACException
	 */
	public void deleteProcess(int nIdProc)throws ISPACException;
	
	/**
	 * Elimina un proceso y sus hitos.
	 * @param nIdProc Identificador del proceso
	 * @throws ISPACException
	 */
	public void cleanProcess(int nIdProc) throws ISPACException;
	
	/**
	 * Elimina un proceso y sus hitos.
	 * @param nIdProc Identificador del proceso
	 * @params params Parámetros para las reglas
	 * @throws ISPACException
	 */
	public void cleanProcess(int nIdProc , Map params) throws ISPACException;
	
	/**
	 * Elimina definitivamente un proceso enviado a la papelera
	 * @param nIdProc Identificador del proceso
	 * @param params Parámetros para las reglas
	 * @throws ISPACException
	 */
	public void deleteProcess(int nIdProc, Map params)throws ISPACException;
	
	/** NO UTILIZADA !!! NO PROBADA
	 * Reubica un proceso a la fase indicada.Para ello se eliminan todas las fases y tramites activas
	 * quedando como unica fase a realizar la especificada por nIdStagePCD. 
	 * @param nIdProc	identificador del proceso
	 * @param nIdStagePCD	identificador en el procedimiento de la fase en la cual se reubicará
	 * el proceso
	 * @throws ISPACException
	 */
	public void redeployProcess(int nIdProc, int nIdStagePCD)
			throws ISPACException;

	/**
	 * 
	 * Pone disponible para la tramitación la fase indicada. Al contrario que redeployProcess esta
	 * transacción no elimina el estado actual del proceso limitandose a poner pendiente una fase más.
	 * @param nIdProc	identificador del proceso
	 * @param nIdStagePCD identificador en el procedimiento de la fase a activar.
	 * @throws ISPACException
	 */
	public void activateStage(int nIdProc, int nIdStagePCD)
	throws ISPACException;
	
	/**
	 * Delega el encargado del proceso a la responsable indicado
	 * @param nIdProc identificador del proceso
	 * @param IdResp identificador del responsable a delegar
	 * @throws ISPACException
	 */
	public void delegateProc(int nIdProc,String IdResp) throws ISPACException;
	
	/**
	 * Delega el encargado del proceso a la responsable indicado
	 * @param nIdProc identificador del proceso
	 * @param IdResp identificador del responsable a delegar
	 * @param nameResp Nombre del responsable al delegar
	 * @throws ISPACException
	 */
	public void delegateProc(int nIdProc,String IdResp , String nameResp) throws ISPACException;

	/**
	 * Delega el encargado del proceso a la responsable indicado
	 * @param nIdProc identificador del proceso
	 * @param IdResp identificador del responsable a delegar
	 * @param params Parámetros para las reglas.
	 * @throws ISPACException
	 */
	public void delegateProc(int nIdProc,String IdResp, Map params) throws ISPACException;

	/**
	 * Delega el encargado de la tramitación de la fase en  al responsable indicado
	 * @param nIdStage  identificador de la fase en el proceso
	 * @param IdResp identificador del responsable a delegar
	 * @throws ISPACException
	 */
	public void delegateStage(int nIdStage,String IdResp) throws ISPACException;

	/**
	 * Delega el encargado de la tramitación de la fase en  al responsable indicado
	 * @param nIdStage  identificador de la fase en el proceso
	 * @param IdResp identificador del responsable a delegar
	 * @param params Parámetros para las reglas.
	 * @throws ISPACException
	 */
	public void delegateStage(int nIdStage,String IdResp, Map params) throws ISPACException;
	/**
	 * Delega el encargado de la tramitación de la fase en  al responsable indicado
	 * @param nIdStage  identificador de la fase en el proceso
	 * @param IdResp identificador del responsable a delegar
	 * @param nameResp nombre del responsable a delegar
	 * @throws ISPACException
	 */
	public void delegateStage(int nIdStage,String IdResp, String nameResp) throws ISPACException;

	/**
	 * Delega el encargado de la tramitación del trámite al responsable indicado
	 * @param nIdTask identificador del trámite en el proceso
	 * @param IdResp identificador del responsable a delegar
	 * @throws ISPACException
	 */
	public void delegateTask(int nIdTask,String IdResp) throws ISPACException;

	/**
	 * Delega el encargado de la tramitación del trámite al responsable indicado
	 * @param nIdTask identificador del trámite en el proceso
	 * @param IdResp identificador del responsable a delegar
	 * @param params Parámetros para las reglas.
	 * @throws ISPACException
	 */
	public void delegateTask(int nIdTask,String IdResp, Map params) throws ISPACException;
	
	/**
	 * Delega el encargado de la tramitación del trámite al responsable indicado
	 * @param nIdTask identificador del trámite en el proceso
	 * @param IdResp identificador del responsable a delegar
	 * @param nameResp nombre del responsable a delegar
	 * @throws ISPACException
	 */
	public void delegateTask(int nIdTask,String IdResp, String nameResp) throws ISPACException;

	/**
	 * Da por concluida la fase
	 * @param nIdStage identificador de la fase en el proceso
	 * @throws ISPACException
	 */
	public void closeStage(int nIdStage) throws ISPACException;

	/**
	 * Da por concluida la fase
	 * @param nIdStage identificador de la fase en el proceso
	 * @param params Parámetros para las reglas.
	 * @throws ISPACException
	 */
	public void closeStage(int nIdStage, Map params) throws ISPACException;

	/**
	 * Da por concluida la fase
	 * @param nIdStage identificador de la fase en el proceso
	 * @param endTasks Indica si se quieren cerrar los trámites abiertos.
	 * @throws ISPACException
	 */
	public void closeStage(int nIdStage, boolean endTasks) throws ISPACException;

	/**
	 * Da por concluida la fase
	 * @param nIdStage identificador de la fase en el proceso
	 * @param endTasks Indica si se quieren cerrar los trámites abiertos.
	 * @param params Parámetros para las reglas.
	 * @throws ISPACException
	 */
	public void closeStage(int nIdStage, boolean endTasks, Map params) throws ISPACException;

	/**
	 * Activa el resto de fases/nodos según lo indicado en la definición
	 * del procedimiento, o cierra el expediente cuando no hay fases, nodos o trámites activos
	 * @param nIdPcdStage identificador de la fase en el procedimiento
	 * @param nIdProcess identificdaor del proceso
	 * @throws ISPACException
	 */
	public void openNextStages( int nIdProcess, int nIdPcdStage) throws ISPACException;
	
	/**
	 * Activa el resto de fases/nodos según lo indicado en la definición
	 * del procedimiento, o cierra el expediente cuando no hay fases, nodos o trámites activos
	 * @param nIdPcdStage identificador de la fase en el procedimiento
	 * @param nIdProcess identificdaor del proceso
	 * @param params Parámetros para las reglas.
	 * @throws ISPACException
	 */
	public void openNextStages( int nIdProcess, int nIdPcdStage, Map params) throws ISPACException;
	
	/**
	 * NO UTILIZADA
	 * Activa la fase anterior del proceso según lo indicado en la definición del procesimiento sin cerrar la fase actual
	 * @param nIdProcess identificador del proceso
	 * @param nIdPcdStage identificador de la fase en el procedimiento
	 * @throws ISPACException
	 */
	public void openPreviousStage( int nIdProcess, int nIdPcdStage)	throws ISPACException;

	/**
	 * NO UTILIZADA
	 * Activa la fase anterior del proceso según lo indicado en la definición del procesimiento sin cerrar la fase actual
	 * @param nIdProcess identificador del proceso
	 * @param nIdPcdStage identificador de la fase en el procedimiento
	 * @param params Parámetros para las reglas.
	 * @throws ISPACException
	 */
	public void openPreviousStage( int nIdProcess, int nIdPcdStage, Map params) throws ISPACException;

	/**
	 * Da por concluida la fase activando el resto de fases/nodos según lo indicado en la definición
	 * del procedimiento, o cierra el expediente cuando no hay fases, nodos o trámites activos
	 * @param nIdStage identificador de la fase en el proceso
	 * @param nIdProcess identificador del proceso
	 * @param nIdPcdStage identificador de la fase en el procedimiento
	 * @throws ISPACException
	 */
	public void deployNextStage(int nIdProcess, int nIdPcdStage, int nIdStage)
			throws ISPACException;

	/**
	 * Situa el proceso en una fase cerrando la fase activa
	 * @param nIdProcess Identificador del proceso
	 * @param nIdPcdStage Identificador de la fase en el Procedimiento
	 * @param nIdStage Identificador de la fase instanciada
	 * @param endTasks indica si se deben cerrar los trámites
	 * @throws ISPACException
	 */
	public void gotoStage(int nIdProcess, int nIdPcdStage, int nIdStage, boolean endTasks)
			throws ISPACException;
	
	
	/**
	 * Situa el proceso en una fase cerrando la fase activa
	 * @param nIdProcess Identificador del proceso
	 * @param nIdPcdStage Identificador de la fase en el Procedimiento
	 * @param nIdStage Identificador de la fase instanciada
	 * @throws ISPACException
	 */
	public void gotoStage(int nIdProcess, int nIdPcdStage, int nIdStage)
			throws ISPACException;

	/**
	 * Situa el proceso en una fase cerrando todas las fases activas
	 * @param nIdProcess Identificador del proceso
	 * @param nIdPcdStage Identificador de la fase en el Procedimiento
	 * @param endTasks indica si se deben cerrar los trámites
	 * @throws ISPACException
	 */
	public void gotoStage(int nIdProcess, int nIdPcdStage, boolean endTasks)
		throws ISPACException;
	
	/**
	 * Situa el proceso en una fase cerrando todas las fases activas
	 * @param nIdProcess Identificador del proceso
	 * @param nIdPcdStage Identificador de la fase en el Procedimiento
	 * @throws ISPACException
	 */
	public void gotoStage(int nIdProcess, int nIdPcdStage)
		throws ISPACException;
	
	/**
	 * Da por concluido el trámite
	 * @param nIdTask identificador del trámite en el proceso
	 * @throws ISPACException
	 */
	public void closeTask(int nIdTask) throws ISPACException;

	/**
	 * Da por concluido el trámite
	 * @param nIdTask identificador del trámite en el proceso
	 * @param params Parámetros para las reglas
	 * @throws ISPACException
	 */
	public void closeTask(int nIdTask, Map params) throws ISPACException;

	/**
	 * Da por concluida la fase activando las fases anteriores según lo indicado en la definición
	 * del procedimiento
	 * @param nIdStage identificador de la fase en el proceso
	 * @param nIdProcess identificador del proceso
	 * @param nIdPcdStage identificador de la fase en el procedimiento
	 * @throws ISPACException
	 */

	public void redeployPreviousStage(int nIdProcess, int nIdPcdStage, int nIdStage)	
			throws ISPACException;
	
	/**
	 * Da por concluida la tramitación del proceso
	 * @param nIdProcess identificador del proceso
	 * @throws ISPACException
	 */
	public void closeProcess(int nIdProcess) throws ISPACException;

	/**
	 * Da por concluida la tramitación del proceso
	 * @param nIdProcess identificador del proceso
	 * @param params parámetros para las reglas.
	 * @throws ISPACException
	 */
	public void closeProcess(int nIdProcess, Map params) throws ISPACException;

	/**
	 * Cambia el proceso a estado archivado.
	 * @param nIdProcess identificador del proceso
	 * @throws ISPACException si ocurre algún error.
	 */
	public void archiveProcess(int nIdProcess) throws ISPACException; 

	/**
	 * Cambia el proceso a estado archivado.
	 * @param nIdProcess identificador del proceso
	 * @param params parámetros para las reglas.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void archiveProcess(int nIdProcess, Map params) throws ISPACException; 

	/**
	 * Crea un nuevo trámite el cual queda asignado a la fase especificada.
	 * @param nIdStage identificador de la fase en el proceso
	 * @param nIdTaskPCD identificador del trámite en el procedimiento
	 * @return identificador del trámite creado
	 * @throws ISPACException
	 */
	public int createTask(int nIdStage,int nIdTaskPCD)
			throws ISPACException;

	/**
	 * Crea un nuevo trámite el cual queda asignado a la fase especificada.
	 * @param nIdStage identificador de la fase en el proceso
	 * @param nIdTaskPCD identificador del trámite en el procedimiento
	 * @param params mapa con nombres de parámetro como claves y sus correspespondientes valores. 
	 * @return identificador del trámite creado
	 * @throws ISPACException
	 */
	public int createTask(int nIdStage,int nIdTaskPCD, Map params) throws ISPACException;

	/**
	 * Crea un tramite y asigna la fase que se pasa por parametro
	 * @param nIdPcd
	 * @param nIdStage
	 * @param nIdTaskPCD
	 * @param numExp
	 * @return
	 * @throws ISPACException
	 */
	public int createTask(int nIdPcd, int nIdStage, int nIdTaskPCD, String numExp) throws ISPACException;	

	/**
	 * Crea un tramite y asigna la fase que se pasa por parametro
	 * @param nIdPcd
	 * @param nIdStage
	 * @param nIdTaskPCD
	 * @param numExp
	 * @param params mapa con nombres de parámetro como claves y sus correspespondientes valores.
	 * @return
	 * @throws ISPACException
	 */
	public int createTask(int nIdPcd, int nIdStage, int nIdTaskPCD, String numExp, Map params)
			throws ISPACException;	
	
	/**
	 * Crea una nueva tramitación agrupada .
	 * @param idFase identificador de la fase en el proceso
	 * @param numExps números de expedientes agrupados
	 * @return identificador de la tramitación agrupada creada
	 * @throws ISPACException
	 */
	public int createBatchTask(int idFase, String [] numExps) 
		throws ISPACException;

	/**
	 * Elimina el trámite indicado
	 * @param nIdTask identificador del trámite en el proceso
	 * @throws ISPACException
	 */
	public void deleteTask(int nIdTask) throws ISPACException;
	
	/**
	 * Elimina el trámite indicado
	 * @param nIdTask identificador del trámite en el proceso
	 * @param params Parámetros para las reglas.
	 * @throws ISPACException
	 */
	public void deleteTask(int nIdTask, Map params) throws ISPACException;
	
	/**
	 * Elimina el subproceso indicado asociado a un trámite
	 * @param nIdSubProcess identificador del subproceso dentro la tabla SPAC_PROCESOS
	 * @param nIdTask identificador del trámite dentro de la tabla SPAC_TRAMITES
	 * @throws ISPACException
	 */
	public void deleteSubProcess(int nIdSubProcess, int nIdTask) throws ISPACException;

	/**
	 * Elimina el subproceso indicado asociado a un trámite
	 * @param nIdSubProcess identificador del subproceso dentro la tabla SPAC_PROCESOS
	 * @param nIdTask identificador del trámite dentro de la tabla SPAC_TRAMITES
	 * @param params Parámetros para las reglas.
	 * @throws ISPACException
	 */
	public void deleteSubProcess(int nIdSubProcess, int nIdTask, Map params) throws ISPACException;

	/**
	 * Crea un nuevo hito particular en el proceso.
	 * Se proporciona además un campo identificador <code>nIdInfo</code> y un campo texto <code>info</code>
	 * para permitir añadir información extra específica del llamante.
	 * Es obligatorio suministrar el identificador de proceso nIdProc y opcionales los identificadores
	 * de fase y trámite en el procedimiento (pasar valores a 0 si no se desea asignar el hito a una fase
	 * específica)
	 * @param nIdProc	identificador del proceso
	 * @param nIdStagePCD	identificador en el procedimiento de la fase
	 * @param nIdTaskPCD identificador del trámite en el procedimiento
	 * @param nIdInfo identificador descriptivo del hito El id viene de la clase 
	 * @InformationMilestonesConstants
	 * @param info  texto descriptivo del hito
	 * @param desc Descripción general del hito
	 * @throws ISPACException
	 */
	public void newMilestone(int nIdProc, int nIdStagePCD, int nIdTaskPCD,int nIdInfo,
			String info, String desc) throws ISPACException;

	/** NO SE UTILIZA NO EXPLICAR QUIZAS SE BORRE
	 * Anota un proceso pudiendo indicar además una fase y/o trámite con información de utilidad
	 * para la tramitación.
	 * @param nIdProc	identificador del proceso
	 * @param nIdStagePCD	identificador en el procedimiento de la fase
	 * @param nIdTaskPCD identificador del trámite en el procedimiento
	 * @param Text mensaje a anotación
	 * @throws ISPACException
	 */
	public void remark(int nIdProc, int nIdStagePCD, int nIdTaskPCD, String Text)
			throws ISPACException;

	/**
	 * Ejecuta la regla indicada y devuelve el objeto genérico resultado de su ejecución.
	 * @param nIdRule indentificador de la regla en el catálogo.
	 * @return el valor de retorno producto de la ejecución de la regla indicada
	 * @throws ISPACException
	 */
	public Object executeRule(int nIdRule)
		throws ISPACException;

	/**
	 * Ejecuta la regla indicada y devuelve el objeto genérico resultado de su ejecución.
	 * @param nIdRule indentificador de la regla en el catálogo.
	 * @param parameters mapa con los pares (nombre de parametro, valor) que se suministraran al
	 * contexto de ejución de la regla.
	 * @return el valor de retorno producto de la ejecución de la regla indicada
	 * @throws ISPACException
	 */
	public Object executeRule(int nIdRule,Map parameters)
		throws ISPACException;

	/**
	 * Ejecuta la regla indicada y devuelve el objeto genérico resultado de su ejecución.
	 * nIdProc, nIdStage y nIdTask indican el contexto en el cual se debería situar la ejecución
	 * de la regla.
	 * @param nIdRule identificador de la regla en el catálogo.
	 * @param nIdProc
	 * @param nIdStage
	 * @param nIdTask
	 * @return el valor de retorno producto de la ejecución de la regla indicada
	 * @throws ISPACException
	 */
	public Object executeContextRule(int nIdRule,int nIdProc, int nIdStage, int nIdTask,Map parameters)
		throws ISPACException;

	/**
	 * Ejecuta la regla indicada y devuelve el objeto genérico resultado de su ejecución.
	 * IRuleContextParams indica el contexto en el cual se debería situar la ejecución
	 * de la regla.
	 * @param nIdRule identificador de la regla en el catálogo.
	 * @param rctxobj contexto de la regla
	 * @return el valor de retorno producto de la ejecución de la regla indicada
	 * @throws ISPACException
	 */

	public Object executeContextRule(int nIdRule,IRuleContextParams rctxobj)
		throws ISPACException;

	/**
	 * Busca y ejecuta las reglas asociadas al evento indicado y devuelve el objeto genérico
	 * resultado de su ejecución.
	 * IRuleContextParams indica el contexto en el cual se debería situar la ejecución
	 * de los eventos.
	 * @param nIdRule identificador de la regla en el catálogo.
	 * @param TypeObj Los tipos de objetos se definen en @EventsDefines
	 * @param nIdObj
	 * @param EventCode Valor de la clase @EventsDefines
	 * @param rctxobj contexto de la regla
	 * @return el valor de retorno producto de la ejecución de la regla indicada
	 * @throws ISPACException
	 */
	public Object executeEvents(int TypeObj,int nIdObj,int EventCode,IRuleContextParams rctxobj)
		throws ISPACException;
	
	/**
	 * Actualiza una tramitación agrupada con los valores pasados por parametro
	 * @param batchTask
	 */
	public int updateBatchTask(IItem batchTask) throws ISPACException;
	
	/**
	 * Elimina la tramitación agrupada.
	 * @param batchTaskId Identificador de la tramitación agrupada.
	 * @return Número de tramitaciones agrupadas eliminadas.
	 * @throws ISPACException si ocurre algún error.
	 */
	public int deleteBatchTask(int batchTaskId) throws ISPACException;
	
	/**
	 * Establece un calendario para un objeto
	 * @param tipoObjeto. Los valores que puede tomar son las constantes de la clase @PRelPlazoDAO
	 * PRelPlazoDAO.DEADLINE_OBJ_PROCEDURE 	(1)
  	 * PRelPlazoDAO.DEADLINE_OBJ_STAGE 		(2)
  	 * PRelPlazoDAO.DEADLINE_OBJ_TASK 		(3) 
	 * @param idObjeto
	 * @param idCalendario
	 * @throws ISPACException
	 */
	public void setCalendar(int tipoObjeto, int idObjeto, int idCalendario) throws ISPACException;
	
	/**
	 * Establece la fecha de inicio del plazo del objeto
	* @param tipoObjeto. Los valores que puede tomar son las constantes de la clase @PRelPlazoDAO
	 * PRelPlazoDAO.DEADLINE_OBJ_PROCEDURE 	(1)
  	 * PRelPlazoDAO.DEADLINE_OBJ_STAGE 		(2)
  	 * PRelPlazoDAO.DEADLINE_OBJ_TASK 		(3) 
	 * @param idObjeto
	 * @param baseDate
	 * @throws ISPACException
	 */
	public void setBaseDate(int tipoObjeto, int idObjeto, Date baseDate) throws ISPACException;
	
	/**
	 * Reactiva el plazo de un proceso, tramite o fase
	 * @param tipoObjeto. Los valores que puede tomar son las constantes de la clase @PRelPlazoDAO
	 * PRelPlazoDAO.DEADLINE_OBJ_PROCEDURE 	(1)
  	 * PRelPlazoDAO.DEADLINE_OBJ_STAGE 		(2)
  	 * PRelPlazoDAO.DEADLINE_OBJ_TASK 		(3) 
	 * @param idProcess
	 * @throws ISPACException
	 */
	public void resumeDeadline(int tipoObjeto, int idObjeto) throws ISPACException;
	
	/**
	 * Congela el plazo de un proceso, tramite o fase 
	 * 
	 * @param tipoObjeto. Los valores que puede tomar son las constantes de la clase @PRelPlazoDAO
	 * PRelPlazoDAO.DEADLINE_OBJ_PROCEDURE 	(1)
  	 * PRelPlazoDAO.DEADLINE_OBJ_STAGE 		(2)
  	 * PRelPlazoDAO.DEADLINE_OBJ_TASK 		(3) 
  	 * 
	 * @param idObjeto
	 * @throws ISPACException
	 */
	public void pauseDeadline(int tipoObjeto, int idObjeto) throws ISPACException;
	
	/**
	 * Recalcula la fecha limite de un proceso, fase o tramite.
	 * 
	 * @param tipoObjeto. Los valores que puede tomar son las constantes de la clase @PRelPlazoDAO
	 * PRelPlazoDAO.DEADLINE_OBJ_PROCEDURE 	(1)
  	 * PRelPlazoDAO.DEADLINE_OBJ_STAGE 		(2)
  	 * PRelPlazoDAO.DEADLINE_OBJ_TASK 		(3) 
	 * @param idObjeto
	 * @throws ISPACException
	 */
	public void recalculateDeadline(int tipoObjeto, int idObjeto) throws ISPACException;

	/**
	 * Recalcula la fecha limite de un proceso, fase o tramite.
	 * 
	 * @param tipoObjeto. Los valores que puede tomar son las constantes de la clase @PRelPlazoDAO
	 * PRelPlazoDAO.DEADLINE_OBJ_PROCEDURE 	(1)
  	 * PRelPlazoDAO.DEADLINE_OBJ_STAGE 		(2)
  	 * PRelPlazoDAO.DEADLINE_OBJ_TASK 		(3) 
	 * @param idObjeto
	 * @param params Parámetros para las reglas.
	 * @throws ISPACException
	 */
	public void recalculateDeadline(int tipoObjeto, int idObjeto, Map params) throws ISPACException;

}