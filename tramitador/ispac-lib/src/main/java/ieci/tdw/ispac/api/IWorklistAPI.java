package ieci.tdw.ispac.api;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

public interface IWorklistAPI extends Serializable
{
	public static final	int NORMAL = 0;
	public static final	int SUPERVISOR = 1;


	/**
	 * Cambia el modo de visibilidad de la lista de trabajo. En modo supervisor
	 * el resto de métodos devuelve todas las tareas de aquellos procedimientos en los
	 * cuales el usuario conectado es supervisor.
	 *
	 * @param nMode
	 * @throws ISPACException
	 */
	public void setMode(int nMode) throws ISPACException;

	/**
	 * Busca las fases del proceso que estén en la papelera.
	 * No se comprueba permisos,  ya que solo tiene acceso a los expedientes en la papelera
	 * el supervisor total o en modo consulta (se debe de comprobar antes de invocar a este método)
	 * @param nIdProcess
	 * @return
	 * @throws ISPACException
	 */
	public IItemCollection findActiveStagesInTrash(int nIdProcess) throws ISPACException;
	/**
	 * @param nIdProcess
	 * @return lista de fases
	 * @throws ISPACException
	 */
	public IItemCollection findActiveStages(int nIdProcess) throws ISPACException;

	/**
	 * @param nIdProcess
	 * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return lista de fases
	 * @throws ISPACException
	 */
	public IItemCollection findActiveStages(int nIdProcess, String resp) throws ISPACException;

	/**
	 * @param nIdProcess
	 * @return lista de trámites
	 * @throws ISPACException
	 */
	public IItemCollection findActiveTasks(int nIdProcess) throws ISPACException;

	/**
	 * @param nIdProcess
	 * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return lista de trámites
	 * @throws ISPACException
	 */
	public IItemCollection findActiveTasks(int nIdProcess, String resp) throws ISPACException;

	/**
	 * @param nIdProcess
	 * @param nIdStagePCD
	 * @return lista de trámites
	 * @throws ISPACException
	 */
	public IItemCollection findStageActiveTasks(int nIdProcess,int nIdStagePCD) throws ISPACException;

	// PROCEDIMIENTOS //
	/**
	 * Devuelve la lista de procedimientos en los cuales existen procesos
	 * que son responsabilidad del usuario conectado.
	 *
	 * @return lista de procedimientos
	 * @throws ISPACException
	 */
	public IItemCollection getProcedures() throws ISPACException;

	/**
	 * Devuelve la lista de procedimientos en los cuales existen procesos
	 * que son responsabilidad del usuario conectado.
	 *
	 * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return lista de procedimientos
	 * @throws ISPACException
	 */
	public IItemCollection getProcedures(String resp) throws ISPACException;

	/**
    * Devuelve la lista de procedimientos modelados como subprocesos
	 * en los cuales existen procesos instanciados
	 * que son responsabilidad del usuario conectado.
	 *
	 * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return lista de procedimientos
	 * @throws ISPACException
	 */
	public IItemCollection getSubProcedures(String resp) throws ISPACException;

	/**
	 * Devuelve la lista de procedimientos que puede iniciar el usuario conectado.
	 *
	 * @return lista de procedimientos
	 * @throws ISPACException
	 */
	public IItemCollection getCreateProcedures() throws ISPACException;


	// EXPEDIENTES //
	/**
	 * Devuelve una lista de items de expedientes de una fase responsabilidad
	 * del usuario conectado.
	 *
	 * @param idStagePCD identificador de la fase
	 * @return lista de expedientes
	 * @throws ISPACException
	 */
	public IItemCollection getProcesses(int idStagePCD)
			throws ISPACException;


	/**
	 * Devuelve una lista de expedientes de la fase indicada los cuales son responsabilidad
	 * del usuario conectado. Opcionalmente los relaciona con otras entidades seg&uacute;n el
	 * xml de definici&oacute;n de listas de procesos.
	 *
	 * <p> Ejemplo de definici&oacute;n de formato para la lista de trabajo:<p>
	 *<pre>
&lt;?xml version=&quot;1.0&quot; encoding=&quot;ISO-8859-1&quot;?&gt;
&lt;processlistformat&gt;

	&lt;!-- El tag worklist es obligatorio y s&oacute;lo puede aparecer una vez en la defici&oacute;n.
		 Atributos obligatorios

		 name - indica el prefijo que se quiere dar a las propiedades espec&iacute;ficas de
		 		cada item de la lista de trabajo seg&uacute;n
				estan definidos en la vista SPAC_WL_PROC.

	SPAC_WL_PROC:

	ID - Id del proceso
	NUMEXP - N&uacute;mero de expediente
	ID_PROC - Id del procedimiento
	ID_STAGEPCD - Nombre de la fase en el procedimiento
	ID_STAGE - Id de la fase actual
	NAME_STAGE - Nombre de la fase actual
	INITDATE - Fecha de inicio de la fase actual
	LIMITDATE - Fecha l&iacute;mite de la fase actual
	RESP - Responsable encargardo de realizar la fase actual

	--&gt;
	&lt;worklist name=&quot;WORKLIST&quot;/&gt;


	&lt;!-- Entidades a relacionar con los expedientes.Este tag puede aparecer m&uacute;ltiples veces.
 		 Atributos obligatorios:

		 name - indica el prefijo que se quiere utilizar para diferenciar
		 		las propiedades de esta entidad
		 entityid - id de la entidad.
	--&gt;
	&lt;entity name=&quot;EXPED&quot; entityid=&quot;1&quot;/&gt;


	&lt;!-- Tablas externas a relacionar con los expedientes.Este tag puede aparecer m&uacute;ltiples veces.
		 Atributos obligatorios:

		 name - indica el prefijo que se quiere utilizar para diferenciar
		 		las propiedades de esta tabla

		 tablename - el nombre de la tabla que se quiere unir a la lista de trabajo.

		 numexpfield - el campo que contiene el n&uacute;mero de expediente en dicha tabla.
	--&gt;
	&lt;table name=&quot;TEST&quot; tablename=&quot;TEST_TABLE&quot; numexpfield=&quot;NUMEXP&quot; /&gt;

	&lt;!-- Tablas con c&oacute;digos cuyo sustituto se quiere mostrar en la lista de trabajo en lugar
		del c&oacute;digo presente en alguna otra entidad o tabla.Este tag puede aparecer m&uacute;ltiples
		veces.

		Atributos obligatorios:

		name - nombre del prefijo de la tabla de c&oacute;digos.

		table - nombre de la tabla de c&oacute;digos
		code  - nombre del campo que contiene los c&oacute;digos
		value - nombre del campo que contiene el valor sustituto
	--&gt;

	&lt;codetable name=&quot;SUST&quot; table=&quot;SPAC_DT_CONCEJOS&quot; code=&quot;VALOR&quot; value=&quot;SUSTITUTO&quot;&gt;
		&lt;!-- Definici&oacute;n de todas las sustituciones de esta tabla de c&oacute;digos con
			el resto de entidades o tablas. Este tag puede aparecer m&uacute;ltiples veces.

			Atributos obligatorios:

			name - Nombre de la entidad o tabla que contiene el campo a sustituir
			property - Nombre de la propiedad que contiene el c&oacute;digo
		--&gt;
		&lt;substitute name=&quot;EXPED&quot; property=&quot;CONCEJO&quot;/&gt;
	&lt;/codetable&gt;

	&lt;!-- Consulta a aplicar al calculo de la lista de trabajo. Los nombres de tabla
		para la sentencia SQL son los mismos que los prefijos que se han especificado
		mediante el atributo name.
	--&gt;
	&lt;query&gt; WORKLIST.NUMEXP = 'EXP/386'&lt;/query&gt;

	&lt;!--
		 BeanFormater est&aacute;ndar. Notar el renombrado de los campos
		 con todos los prefijos suministrados, en especial los campos sustituidos.
	--&gt;
	&lt;showfields&gt;
		&lt;propertyfmt type=&quot;BeanPropertySimpleFmt&quot;
			property='EXPED:NUMEXP' readOnly='false'
			title='Numexp' fieldType='DATA' /&gt;

		&lt;propertyfmt type=&quot;BeanPropertySimpleFmt&quot;
			property='PROC:NOMBRE' readOnly='false'
			title='Procedimiento' fieldType='DATA' /&gt;

		&lt;propertyfmt type=&quot;BeanPropertySimpleFmt&quot;
			property='SUST:EXPED:CONCEJO' readOnly='false'
			title='Nombre Concejo' fieldType='DATA' /&gt;
	&lt;/showfields&gt;

&lt;/processlistformat&gt;
</pre>
	 *
	 * @param idStagePCD identificador de la fase
	 * @param processlistfmtpath path al xml de definici&oacute;n de la lista de procesos.
	 * @return lista de expedientes
	 * @throws ISPACException
	 */
	public IItemCollection getProcesses(int idStagePCD,String processlistfmtpath)
			throws ISPACException;

	/**
	 * Devuelve una lista de expedientes de la fase indicada los cuales son responsabilidad
	 * del usuario conectado. Opcionalmente los relaciona con otras entidades seg&uacute;n el
	 * xml de definici&oacute;n de listas de procesos.
	 *
	 * La descripción del XML se encuentra en {@link getProcesses(int idStagePCD,String processlistfmtpath)}
	 *
	 * @param idStagePCD identificador de la fase
	 * @param processlistxml stream con la definici&oacute;n del formato para la lista de procesos.
	 * @return lista de expedientes
	 * @throws ISPACException
	 */
	public IItemCollection getProcesses(int idStagePCD,InputStream processlistxml)
			throws ISPACException;

	/**
	 * Devuelve una lista de subprocesos de la actividad indicada los cuales son responsabilidad
	 * del usuario conectado. Opcionalmente los relaciona con otras entidades seg&uacute;n el
	 * xml de definici&oacute;n de listas de procesos.
	 *
	 * La descripción del XML se encuentra en {@link getSubProcesses(int idActivityPCD,String processlistfmtpath)}
	 *
	 * @param idActivityPCD identificador de la actividad
	 * @param processlistxml stream con la definici&oacute;n del formato para la lista de subprocesos.
	 * @return lista de subprocesos
	 * @throws ISPACException
    */
	public IItemCollection getSubProcesses(int idActivityPCD, InputStream istream)
	throws ISPACException;

	/**
	 * Devuelve una lista de subprocesos de la actividad indicada los cuales son responsabilidad
	 * del usuario conectado. Opcionalmente los relaciona con otras entidades seg&uacute;n el
	 * xml de definici&oacute;n de listas de procesos.
	 *
	 * La descripción del XML se encuentra en {@link getSubProcesses(int idActivityPCD,String processlistfmtpath)}
	 *
	 * @param idPcd	identificador del procedimiento padre.
	 * @param idActivityPCD identificador de la actividad
	 * @param processlistxml stream con la definici&oacute;n del formato para la lista de subprocesos.
	 * @return lista de subprocesos
	 * @throws ISPACException
    */
	public IItemCollection getSubProcesses(int pcdId, int idActivityPCD,
			InputStream istream) throws ISPACException;

	/**
	 * Devuelve el expediente según su número de expediente.
	 *
	 * @param numexp número del expediente return un objeto expediente
	 * @throws ISPACException
	 */
	public IItem getProcess(String numexp) throws ISPACException;

	/**
	 * Obtiene el expediente con el identificador especificado.
	 *
	 * @param int id identificador del expediente
	 * @return datos del expediente especificado
	 * @throws ISPACException
	 */
	public IItem getProcess(int id) throws ISPACException;


	// FASES //
	/**
	 * Devuelve la lista de fases de un procedimiento responsabilidad
	 * del usuario conectado.
	 *
	 * @param idProcedure identificador del procedimiento
	 * @return lista de fases
	 * @throws ISPACException
	 */
	public IItemCollection getStages(int idProcedure)
			throws ISPACException;

	/**
	 * Devuelve la lista de fases de un procedimiento responsabilidad
	 * del usuario conectado.
	 *
	 * @param idProcedure identificador del procedimiento
    * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return lista de fases
	 * @throws ISPACException
	 */
	public IItemCollection getStages(int idProcedure, String resp)
			throws ISPACException;

	/**
	 * Devuelve la lista de actividades abiertas de un procedimiento bajo la
	 * responsabilidad del usuario conectado.
    *
	 * @param subProcedureId
	 *            Identificador del subprocedimiento.
	 * @return Lista de actividades abiertas.
	 * @throws ISPACException
	 */
	public IItemCollection getActivities(int subProcedureId)
			throws ISPACException;

	/**
	 * Devuelve la lista de actividades abiertas de un procedimiento bajo la
	 * responsabilidad indicada.
    *
	 * @param subProcedureId
	 *            Identificador del subprocedimiento.
	 * @param resp
	 *            Cadena de responsabilidad.
	 * @return Lista de actividades abiertas.
	 * @throws ISPACException
	 */
	public IItemCollection getActivities(int subProcedureId, String resp)
			throws ISPACException;

	/**
	 * Obtiene la fase de un expediente
	 * @param numExp
	 * @return
	 * @throws ISPACException
	 */
	public IItem getStage(String numExp)throws ISPACException;

	// TRÁMITES DE PROCEDIMIENTOS//
	/**
	 * Lista de trámites de todos los procedimientos que son responsabilidad del
	 * usuario conectado.
	 *
	 * @return lista de trámites
	 * @throws ISPACException
	 */
	public IItemCollection getProcedureTasks() throws ISPACException;

	/**
	 * Lista de trámites de todos los procedimientos que son responsabilidad del
	 * usuario conectado.
	 *
	 * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return lista de trámites
	 * @throws ISPACException
	 */
	public IItemCollection getProcedureTasks(String resp) throws ISPACException;


	/**
	 * Lista de trámites cerrados de todos los procedimientos que son responsabilidad del
	 * usuario conectado.
	 *
	 * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return lista de trámites
	 * @throws ISPACException
	 */
	public IItemCollection getProcedureClosedTasks(String resp) throws ISPACException;

	/**
	 * Lista de trámites agrupados por procedimiento que son responsabilidad del
	 * usuario conectado.
	 *
	 * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return lista de trámites
	 * @throws ISPACException
	 */
	public IItemCollection getProcedureTasksGroupByPcd(String resp) throws ISPACException;


	/**
	 * Lista de trámites cerrados agrupados por procedimiento que son responsabilidad del
	 * usuario conectado.
	 *
	 * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return lista de trámites
	 * @throws ISPACException
	 */
	public IItemCollection getProcedureClosedTasksGroupByPcd(String resp) throws ISPACException;


	/**
	 * Devuelve una lista de los trámites iniciados desde una fase.
	 *
	 * @param idStagePCD identificador de la fase
	 * @return lista de trámites
	 * @throws ISPACException
	 */
	public IItemCollection getProcedureStageTasks(int idStagePCD)
			throws ISPACException;

	/**
	 * Devuelve una lista de los trámites de una fase de procedimiento que se pueden iniciar.
	 *
	 * @param idStagePCD identificador de la fase
	 * @return lista de trámites
	 * @throws ISPACException
	 */
	public IItemCollection getProcedureStageTasksToStart(int idStagePCD)
			throws ISPACException;

	/**
    * Lista de trámites activos de un trámite del procedimiento.
	 *
    * @param taskPcdId identificador del trámite del procedimiento
	 * @return lista de trámites activos
	 * @throws ISPACException
	 */
	public IItemCollection getTasksPCD(int taskPcdId)
	throws ISPACException;

	/**
    * Lista de trámites activos de un trámite del procedimiento
    * los cuales son responsabilidad del usuario conectado.
    * Opcionalmente los relaciona con otras entidades seg&uacute;n el
    * xml de definici&oacute;n de listas de trámites.
    *
    * @param taskPcdId identificador del trámite del procedimiento.
    * @param tasklistxml stream con la definici&oacute;n del formato para la lista de trámites.
    * @return lista de trámites activos.
    * @throws ISPACException
    */
	public IItemCollection getTasksPCD(int taskPcdId, InputStream tasklistxml)
	throws ISPACException;

	/**
    * Lista de trámites activos de un trámite del catálogo.
	 *
	 * @param idTaskPCD identificador del trámite del catálogo
	 * @return lista de trámites activos
	 * @throws ISPACException
	 */
	public IItemCollection getTasksCTL(int taskCtlId)
	throws ISPACException;

	/**
    * Lista de trámites activos de un trámite del
    * catálogo para un determinado procedimiento.
	 *
	 * @param taskCtlId identificador del trámite del catálogo
	 * @param pcdId Identificador del procedimiento
	 * @return lista de trámites activos
	 * @throws ISPACException
	 */
	public IItemCollection getTasksCTL(int taskCtlId, int pcdId)
	throws ISPACException;

	/**
    * Devuelve una lista de trámites activos de un trámite del catálogo
    * los cuales son responsabilidad del usuario conectado.
    * Opcionalmente los relaciona con otras entidades seg&uacute;n el
    * xml de definici&oacute;n de listas de trámites.
    *
    * @param taskCtlId identificador del trámite del catálogo.
    * @param tasklistxml stream con la definici&oacute;n del formato para la lista de trámites.
    * @return lista de tramites activos.
    * @throws ISPACException
    */
	public IItemCollection getTasksCTL(int taskCtlId, InputStream tasklistxml)
	throws ISPACException;

	/**
    * Devuelve una lista de trámites activos de un trámite del catálogo para un determinado
    * procedimiento los cuales son responsabilidad del usuario conectado.
    * Opcionalmente los relaciona con otras entidades seg&uacute;n el
    * xml de definici&oacute;n de listas de trámites.
    *
    * @param taskCtlId identificador del trámite del catálogo.
    * @param tasklistxml stream con la definici&oacute;n del formato para la lista de trámites.
    * @param pcdId Identificador del procedimiento.
    * @return lista de tramites activos.
    * @throws ISPACException
    */
	public IItemCollection getTasksCTL(int taskCtlId, InputStream tasklistxml, int pcdId)
	throws ISPACException;

	/**
	 * Lista de trámites cerrados de un trámite
	 * procedimiento y un responsable.
	 *
	 * @param idTaskPCD identificador del trámite del procedimiento
	 * @return lista de trámites activos
	 * @throws ISPACException
	 */
	public IItemCollection getClosedTasksPCD(int taskPcdId)
	throws ISPACException;

	/**
    * Lista de trámites cerrados de un trámite del procedimiento
    * los cuales son responsabilidad del usuario conectado.
    * Opcionalmente los relaciona con otras entidades seg&uacute;n el
    * xml de definici&oacute;n de listas de trámites.
    *
    * @param taskPcdId identificador del trámite del procedimiento.
    * @param tasklistxml stream con la definici&oacute;n del formato para la lista de trámites.
    * @return lista de trámites cerrados.
    * @throws ISPACException
    */
	public IItemCollection getClosedTasksPCD(int taskPcdId, InputStream tasklistxml)
	throws ISPACException;

	/**
	 * Lista de trámites cerrados de un trámite
	 * catálogo y un responsable.
	 *
	 * @param idTaskPCD identificador del trámite del catálogo
    * @return lista de trámites cerrados
	 * @throws ISPACException
	 */
	public IItemCollection getClosedTasksCTL(int taskCtlId)
	throws ISPACException;

	/**
	 * Lista de trámites cerrados de un trámite
	 * catálogo y un responsable.
	 *
	 * @param taskCtlId identificador del trámite del catálogo
	 * @param pcdId Identificador del procedimiento
    * @return lista de trámites cerrados
	 * @throws ISPACException
	 */
	public IItemCollection getClosedTasksCTL(int taskCtlId, int pcdId)
	throws ISPACException;

	/**
    * Devuelve una lista de trámites cerrados de un trámite del catálogo
    * los cuales son responsabilidad del usuario conectado.
    * Opcionalmente los relaciona con otras entidades seg&uacute;n el
    * xml de definici&oacute;n de listas de trámites.
    *
    * @param taskCtlId identificador del trámite del catálogo.
    * @param tasklistxml stream con la definici&oacute;n del formato para la lista de trámites.
    * @return lista de tramites cerrados.
    * @throws ISPACException
    */
	public IItemCollection getClosedTasksCTL(int taskCtlId, InputStream tasklistxml)
	throws ISPACException;

	/**
    * Devuelve una lista de trámites activos de un trámite del catálogo para un determinado
    * procedimiento los cuales son responsabilidad del usuario conectado.
    * Opcionalmente los relaciona con otras entidades seg&uacute;n el
    * xml de definici&oacute;n de listas de trámites.
    *
    * @param taskCtlId identificador del trámite del catálogo.
    * @param tasklistxml stream con la definici&oacute;n del formato para la lista de trámites.
    * @param pcdId Identificador del procedimiento.
    * @return lista de tramites cerrados.
    * @throws ISPACException
    */
	public IItemCollection getClosedTasksCTL(int taskCtlId, InputStream tasklistxml, int pcdId)
	throws ISPACException;

	/**
	 * Lista de tr&aacute;mites activos de un trámite
	 * procedimiento filtrado por responsable.
	 *
	 * @param idTask identificador del trámite
	 * @param taskname nombre del trámite
	 * @return lista de trámites activos
	 * @throws ISPACException
	public IItemCollection getTasks(int idTaskCTL,String taskname)
			throws ISPACException;
	 */

	/**
	 * Devuelve una lista de tr&aacute;mites de un expediente.
	 *
	 * @param numexp número de expediente
	 * @return colección de trámites activos
	 * @throws ISPACException
	 */
	public IItemCollection getTasks(String numexp)
			throws ISPACException;

    /**
     * Obtiene la lista de tr&aacute;mites de la lista de trabajo
     * @param taskids
     * @return colección de trámites activos
     * @throws ISPACException
     */
    public IItemCollection getTasks(String taskids[])
        throws ISPACException;

	/**
	 * Informa si el UID pertenece a la lista de responsables del
	 * usuario conectado.
	 *
	 * @param sUID identificador de responsable
	 * @return true si el UID pertenece a la lista.
	 * @throws ISPACException
	 */
	public boolean isInResponsibleList( String sUID)
	throws ISPACException;
	/**
	 * Informa si el UID pertenece a la lista de responsables del
    * usuario conectado o tiene permisos sobre el item
	 *
	 * @param sUID identificador de responsable
	 * @param item IStage , ITask , IProcess
	 * @return true si el UID pertenece a la lista.
	 * @throws ISPACException
	 */
    public boolean isInResponsibleList (String sUID, IItem item)
    throws ISPACException ;

	/**
    *
	 * @param sUID
	 * @param supervisionType
	 * @return
	 * @throws ISPACException
	 */
	public boolean isInResponsibleList( String sUID, int supervisionType)
	throws ISPACException;

	/**
	 * Indica si el Uid pertenece a la lista de responbles del usuario conectado,
	 * tambien se tienen en cuenta los permisos de catálogo (a nivel de procedimiento, fase o trámite)
	 * @param sUID
	 * @param supervisionType
	 * @param item: ITask, IProcess, IStage
	 * @return
	 * @throws ISPACException
	 */
	public boolean isInResponsibleList (String sUID, int supervisionType, IItem item)
	throws ISPACException ;
	/**
	 * @return Cadena con todos los UIDs separados por comas
	 * @throws ISPACException
	 */
	public String getRespString()
	throws ISPACException;

	/**
    *
	 * @param activityId: Identificador de la actividad
	 * @param idPcd: Identificador del procedimiento en el que se usa el subproceso
	 * @return Cadena con todos los UIDs separados por comas, incluyendo los del procedimiento padre
	 * @throws ISPACException
	 */
	public String getRespStringSubProceso(int activityId , int idPcd )
	throws ISPACException;
	/**
	 * Lista de trámites agrupados de todos los procedimientos que son responsabilidad del
	 * usuario conectado.
	 *
	 * @return lista de trámites
	 * @throws ISPACException
	 */
	public IItemCollection getBatchTasks() throws ISPACException;

	/**
	 * Cuenta el numero de tareas agrupadas que son responsabilidad del
	 * usuario conectado.
    *
	 * @return número de tareas
	 * @throws ISPACException
	 */
	public int countBatchTasks() throws ISPACException;

	/**
	 * Cuenta el numero de tareas agrupadas que son responsabilidad del
	 * usuario conectado.
    *
	 * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return número de tareas
	 * @throws ISPACException
	 */
	public int countBatchTasks(String resp) throws ISPACException;


	/**
    * Retorna los expedientes de una tramitación agrupada
	 * @param idBatchTask
	 * @return
	 * @throws ISPACException
	 */
	public IItemCollection getExpsBatchTask(int idBatchTask) throws ISPACException;

	/**
	 * Devuelve los expedientes por numero de expediente
	 * @param numexps
	 * @return
	 * @throws ISPACException
	 */
	public IItemCollection getProcesses(String[] numexps) throws ISPACException;

	/**
	 * Devuelve los tramites por numero de expediente e id tramite de procedimiento
	 * que son responsabilidad del usuario conectado.
    *
	 * @param numexp
	 * @param idPTask
	 * @return
	 * @throws ISPACException
	 */
	public IItemCollection getTasks(String numexp, int idPTask) throws ISPACException;

	/**
	 * Devuelve los tramites por numero de expediente e id tramite de procedimiento
	 * que son responsabilidad del usuario conectado.
    *
	 * @param numexp
	 * @param idPTask
	 * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return
	 * @throws ISPACException
	 */
	public IItemCollection getTasks(String numexp, int idPTask, String resp) throws ISPACException;

	/**
    * @param type tipo de plazo sobre el que se realiza la b&uacute;squeda
	 * @return colecci&oacute;n  de plazos vencidos hasta la fecha actual.
	 * @throws ISPACException
	 */
	public IItemCollection getExpiredTerms(int type) throws ISPACException;

	/**
	 * @param type tipo de plazo sobre el que se realiza la b&uacute;squeda
    * @param initDate fecha de inicio del per&iacute;odo sobre el que se acota la b&uacute;squeda
	 * @param endDate fecha de fin del per&iacute;odo sobre el que se acota la b&uacute;squeda
	 * @return colecci&oacute;n de plazos vencidos.
	 * @throws ISPACException
	 */
	public IItemCollection getExpiredTerms(int type, Date initDate, Date endDate) throws ISPACException;

	/**
	 * Cuenta el numerode plazos vencidos hasta la fecha actual que son responsabilidad del
	 * usuario conectado.
    *
	 * @param type
	 * @return n&uacute;mero de plazos vencidos hasta la fecha actual
	 * @throws ISPACException
	 */
	public int countExpiredTerms(int type) throws ISPACException;

	/**
	 * Cuenta el numerode plazos vencidos hasta la fecha actual que son responsabilidad del
	 * usuario conectado.
	 *
	 * @param type
	 * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return n&uacute;mero de plazos vencidos hasta la fecha actual
	 * @throws ISPACException
	 */
	public int countExpiredTerms(int type, String resp) throws ISPACException;



}