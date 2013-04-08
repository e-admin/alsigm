package ieci.tdw.ispac.api;

public interface ISPACEntities
{
	/**
	 * Identificador de la entidad de expediente
	 */
	public static final int DT_ID_EXPEDIENTES		= 1;
	
	/**
	 * Identificador de la entidad de documentos
	 */
	public static final int DT_ID_DOCUMENTOS		= 2;
	
	/**
	 * Identificador de la entidad de intervinientes
	 */
	public static final int DT_ID_INTERVINIENTES	= 3;

	/**
	 * Identificador de la entidad de trámites
	 */
	public static final int DT_ID_TRAMITES			= 7;

	
	/**
	 * Identificador de la entidad de Registros de Entrada/Salida
	 */
	public static final int DT_ID_REGISTROS_ES		= 8;
	
	
	/**
	 * Identificador del estado del trámite nulo
	 */
	public static final int TASKSTATUS_NULL 		= 0;

	/**
	 * Identificador del estado del trámite abierto
	 */
	public static final int TASKSTATUS_OPEN 		= 1;

	/**
	 * Identificador del estado del trámite delegado
	 */
	public static final int TASKSTATUS_DELEGATE 	= 2;

	/**
	 *  Identificador del estado del trámite cerrado
	 */
	public static final int TASKSTATUS_CLOSE 		= 3;

	/**
	 * Identificador de registro de entidad nulo
	 */
	public static final int ENTITY_NULLREGKEYID		= -1;
	
	/**
	 * Identificador de formulario de entidad no visible
	 */
	public static final int ENTITY_FORM_NO_VISIBLE	= 0;
	
	/**
	 * Identificador de formulario editable
	 */
	public static final int ENTITY_FORM_EDITABLE	= 0;
	
	/**
	 * Identificador de formulario de sólo lectura
	 */
	public static final int ENTITY_FORM_READONLY	= 1;
	
	/**
	 * Identificador de trámite obligatorio en el procedimiento
	 */
	public static final int PCD_TASK_OBLIGATORY		= 1;
    
}
