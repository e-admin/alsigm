package ieci.tdw.ispac.ispacweb.api;

import java.util.Map;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.context.StateContext;

/**
 * @author juanin
 *
 */
public interface IState
{

	/**
	 * Obtiene el identificador del estado de tramitación que representa
	 * @return identificador del estado
	 */
	public String getTicket() throws ISPACException;

	/**
	 * Obtiene el identificador del procedimiento
	 * @return
	 */
	public int getPcdId();

	/**
	 * Obtiene el identificador de la fase del procedimiento
	 * @return
	 */
	public int getStagePcdId();

	/**
	 * Obtiene el identificador de la fase en el proceso
	 * @return
	 */
	public int getStageId();

	/**
	 * Obtiene el identificador del trámite en el catálogo
	 * @return
	 */
	public int getTaskCtlId();

	/**
	 * Obtiene el identificador del trámite en el procedimiento
	 * @return
	 */
	public int getTaskPcdId();

	/**
	 * Obtiene el identificador del trámite en el proceso
	 * @return
	 */
	public int getTaskId();

	/**
	 * Obtiene el identificador del proceso
	 * @return
	 */
	public int getProcessId();

	/**
	 * Obtiene el identificador del estado
	 * @return
	 */
	public int getState();

	/**
	 * Obtiene el número de expediente
	 * @return
	 */
	public String getNumexp();

	/**
	 * Obtiene el identificador de entidad
	 * @return
	 */
	public int getEntityId();

	/**
	 * Obtiene el identificador de un registro perteneciente a la entidad con id getEntityId()
	 * @return
	 */
	public int getEntityRegId();

	/**
	 * Se obtiene la responsabilidad de la actividad
	 * @return
	 */
	public boolean isResponsible();

	/**
	 * Se obtiene el estado de lectura
	 * @return
	 */
	public boolean getReadonly();
		
	/**
	 * Establece el estado de lectura
	 * @return
	 */
	public void setReadonly(boolean readonly);
	
	/**
	 * Se obtiene el estado de bloqueo de la sesión actual
	 * @return
	 */
	public boolean isLockByCurrentSession();
	
	/**
	 * Se obtiene el motivo del estado de s&oacute;lo lectura 
	 * @return 
	 */
	public int getReadonlyReason();
	
	/**
	 * Establece el motivo del estado de s&oacute;lo lectura 
	 * @return 
	 */
	public void setReadonlyReason(int readonlyReason);

	/**
	 * Se obtiene la actividad para el subproceso
	 * @return
	 */
	public int getActivityId();

	/**
	 * Se obtiene la actividad del subprocedimiento
	 * @return
	 */
	public int getActivityPcdId();

	/**
	 * Se otbiene el subprocedimiento
	 * @return
	 */
	public int getSubPcdId();

	/**
	 * Se obtiene el subproceso
	 * @return
	 */
	public int getSubProcessId();
	
	/**
	 * Almacena el identificador de entidad
	 * @param entityId
	 */
	public void setEntityId(int entityId);

	/**
	 * Almacena el identificador de un registro perteneciente a la entidad
	 * @param entityRegId
	 */
	public void setEntityRegId(int entityRegId);

	/**
	 * Libera los bloqueos según el contexto
	 * @param cctx el contexto del cliente actual
	 */
	public void exit(IClientContext cctx) throws ISPACException;
	
	/**
	 * Bloquea la entidad en el expediente
	 * @param cctx el contexto del cliente actual
	 * @throws ISPACException
	 */
	public void blockEntity(IClientContext cctx) throws ISPACException;
	
	/**
	 * Libera el bloqueo sobre la entidad en el expediente
	 * @param cctx el contexto del cliente actual
	 * @throws ISPACException
	 */
	public void unblockEntity(IClientContext cctx) throws ISPACException;

	/**
	 * Comprueba si la entidad del expediente a visualizar ha cambiado
	 * para desbloquear la entidad del estado anterior y 
	 * bloquear la entidad del estado actual
	 * @param iState estado anterior
	 * @param cctx el contexto del cliente actual
	 * @throws ISPACException
	 */
	public void checkNewEntity(IState iState, IClientContext cctx) throws ISPACException;

	/**
	 * Bloquea la entidad por defecto en el expediente
	 * @param cctx el contexto del cliente actual
	 * @throws ISPACException
	 */
	public void blockDefaultEntity(IClientContext cctx) throws ISPACException;
	
	/**
	 * Refresca el estado actual
	 */
	public void refresh();

	/**
	 * Decide que hacer cuando pasamos a otro estado
	 */
	public void enter(IClientContext cctx) throws ISPACException;
	
	/**
	 * Obtiene el parámetro principal que es necesario para definir el estado.
	 * 
	 * @return
	 */
	public Map getParameters();

	/**
	 * Obtiene el nombre que identifica al estado actual.
	 * Se puede utilizar creando una forward con el mismo nombre de etiqueta que situará
	 * la aplicación en el estado que representa
	 * @return
	 */
	public String getLabel();


	/**
	 * Obtiene los parámetros y sus valores necesarios para situarse en este estado de tramitación.
	 * La cadena resultante tiene formato de querystring.
	 * @return
	 */
	public String getQueryString();

	/**
	 * Compueba si dos estados son iguales
	 * @param iState estado a comparar
	 * @return <code>true</code> si son iguales
	 * 		   <code>false</code> si son distintos
	 */
	public boolean equals (IState iState);
	
	/**
	 * Se obtiene el identificador de la tarea agrupada
	 * @return
	 */
	public int getBatchTaskId();
	
	/**
	 * Se obtiene el contexto del estado
	 * @return
	 */
	public StateContext getStateContext();

}