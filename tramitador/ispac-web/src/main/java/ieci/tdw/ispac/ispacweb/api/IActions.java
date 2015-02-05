/*
 * Created on 16-feb-2005
 *
 */
package ieci.tdw.ispac.ispacweb.api;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.struts.util.MessageResources;

/**
 * @author juanin
 *
 */
public interface IActions
{
	/**
	 * Obtiene las acciones asociadas al contexto de
	 * la tramitaci&oacute;n como una lista de ActionBean.
	 *
	 * @param state  estado de tramitaci&oacute;n
	 * @param resources Resources de la aplicación.
	 * @return  Lista de ActionBean
	 * @throws ISPACException
	 */
	public List getStateActions(IState state, MessageResources resources) 
			throws ISPACException;

	/**
	 * Obtiene los tipos de documentos que se pueden generar en el
	 * contexto de la tramitación como una lista de ActionBean.
	 * @param state Estado de la tramitación
	 * @param resources Resources de la aplicación.
	 * @throws ISPACException
	 */
	public List getStateDocuments(IState state, MessageResources resources)	
			throws ISPACException;

	/**
	 * Obtiene los tipos de documentos que se pueden generar en el
	 * contexto de la tramitación como una lista de ActionBean.
	 * @param state Estado de la tramitación
	 * @param resources Resources de la aplicación.
	 * @param action Acción asociada al tipo de documento
	 * @throws ISPACException
	 */
	public List getStateDocuments(IState state, MessageResources resources, 
			String action) throws ISPACException;


	/**
	 * Obtiene las tareas que se pueden realizar en el contexto
	 * de la tramitación como una lista de ActionBean.
	 * @param state Estado de la tramitación
	 * @param resources Resources de la aplicación.
	 * @throws ISPACException
	 */
	public List getStateTasks(IState state, MessageResources resources) 
		throws ISPACException;

	/**
	 * Obtiene las tareas que se pueden realizar en el contexto
	 * de la tramitación como una lista de ActionBean.
	 * @param state Estado de la tramitación
	 * @param resources Resources de la aplicación.
	 * @param action Acción asociada al tipo de documento
	 * @throws ISPACException
	 */
	public List getStateTasks(IState state, MessageResources resources, 
			String action) throws ISPACException;

	/**
	 * Obtiene las plantillas que se pueden generar para un
	 * tipo de documento en el contexto de la tramitación
	 * como una lista de ActionBean.
	 * @param state Estado de la tramitación.
	 * @param resources Resources de la aplicación.
	 * @param documentType Tipo de documento.
	 * @param existDocument Indica si ya se ha generado un documento fisico.
	 * @throws ISPACException
	 */
	public List getStateTemplates(IState state, MessageResources resources, 
			int documentType, boolean existDocument) throws ISPACException;

	/**
	 * Obtiene las plantillas que se pueden generar para un
	 * tipo de documento en el contexto de la tramitación
	 * como una lista de ActionBean.
	 * @param state Estado de la tramitación.
	 * @param resources Resources de la aplicación.
	 * @param entdocId Id de la entidad de documentos.
	 * @param entdocregId Id del registro de la entidad de documentos.
	 * @throws ISPACException
	 */
	public List getStateTemplates(IState state, MessageResources resources, 
			int entdocId, int entdocregId) throws ISPACException;

	/**
	 * Obtiene las plantillas que se pueden generar para un
	 * tipo de documento en el contexto de la tramitación
	 * como una lista de ActionBean.
	 * @param state Estado de la tramitación.
	 * @param resources Resources de la aplicación.
	 * @param action Acción asociada a la plantilla
	 * @param entdocId Id de la entidad de documentos.
	 * @param entdocregId Id del registro de la entidad de documentos.
	 * @throws ISPACException
	 */
	public List getStateTemplates(IState state, MessageResources resources, 
			String action, int entdocId, int entdocregId) throws ISPACException;


	/**
	 * Obtiene las plantillas que se pueden generar para un
	 * tipo de documento en el contexto de la tramitación
	 * como una lista de ActionBean.
	 * @param state Estado de la tramitación
	 * @param resources Resources de la aplicación.
	 * @param action Acción asociada a la plantilla
	 * @param documentType Tipo de documento.
	 * @throws ISPACException
	 */
	public List getStateTemplates(IState state, MessageResources resources, 
			String action, int documentType) throws ISPACException;

	/**
	 * Obtiene la lista de acciones aplicables a la lista
	 * de procesos en una fase.
	 * @param state Estado de la tramitación
	 * @param resources Resources de la aplicación.
	 * @return lista de ActionBean
	 * @throws ISPACException
	 */
	public List getStageProcessesListActions(IState state, 
			MessageResources resources) throws ISPACException;

	/**
	 * Obtiene la lista de acciones aplicables a la lista
	 * de tareas de una fase.
	 * @param state Estado de la tramitación
	 * @param resources Resources de la aplicación.
	 * @return lista de ActionBean
	 * @throws ISPACException
	 */
	public List getTasksListActions(IState state, MessageResources resources) 
			throws ISPACException;

	/**
	 * Obtiene la lista de acciones aplicables a una fase.
	 * @param state Estado de la tramitación
	 * @param resources Resources de la aplicación.
	 * @return lista de ActionBean
	 * @throws ISPACException
	 */
	public List getStageActions(IState state, MessageResources resources) 
			throws ISPACException;
	
	/**
	 * Obtiene la lista de acciones aplicables a una actividad.
	 * @param state Estado de la tramitación
	 * @param resources Resources de la aplicación.
	 * @return lista de ActionBean
	 * @throws ISPACException
	 */
	public List getActivityActions(IState state, MessageResources resources)
			throws ISPACException;

	/**
	 * Obtiene la lista de acciones aplicables a una tarea.
	 * @param state Estado de la tramitación
	 * @param resources Resources de la aplicación.
	 * @return lista de ActionBean
	 * @throws ISPACException
	 */
	public List getTaskActions(IState state, MessageResources resources) 
			throws ISPACException;

	/**
	 * Obtiene los tipos de documentos que se pueden generar en
	 * el contexto de una fase como una lista de ActionBean.
	 * @param state Estado de la tramitación
	 * @param resources Resources de la aplicación.
	 * @param action Acción asociada al tipo de documento
	 * @return lista de ActionBean
	 * @throws ISPACException
	 */
	public List getStageDocuments(IState state, MessageResources resources, 
			String action) throws ISPACException;

	
	/**
	 * Obtiene los tipos de documentos que se pueden generar en
	 * el contexto de una actividad como una lista de ActionBean.
	 * @param state Estado de la tramitación
	 * @param resources Resources de la aplicación.
	 * @param action Acción asociada al tipo de documento
	 * @return lista de ActionBean
	 * @throws ISPACException
	 */
	public List getActivityDocuments(IState state, MessageResources resources, 
			String action) throws ISPACException;
	
	
	/**
	 * Obtiene los tipos de documentos que se pueden generar en
	 * el contexto de una tarea como una lista de ActionBean.
	 * @param state Estado de la tramitación
	 * @param resources Resources de la aplicación.
	 * @param action Acción asociada al tipo de documento
	 * @return lista de ActionBean
	 * @throws ISPACException
	 */
	public List getTaskDocuments(IState state, MessageResources resources, 
			String action) throws ISPACException;

	/**
	 * Obtiene las tareas que se pueden realizar en el contexto
	 * de una fase como una lista de ActionBean.
	 * @param state Estado de la tramitación
	 * @param resources Resources de la aplicación.
	 * @param action Acción asociada al tipo de documento
	 * @return lista de ActionBean
	 * @throws ISPACException
	 */
	public List getStageTasks(IState state, MessageResources resources, 
			String action) throws ISPACException;

	/**
	 * Obtiene las plantillas que se pueden generar para un
	 * tipo de documento en el contexto de una fase como
	 * una lista de ActionBean.
	 * @param state Estado de la tramitación
	 * @param resources Resources de la aplicación.
	 * @param action Acción asociada a la plantilla
	 * @param documentType Tipo de documento.
	 * @return lista de ActionBean
	 * @throws ISPACException
	 */
	public List getStageTemplates(IState state, MessageResources resources, 
			String action, int documentType) throws ISPACException;

	/**
	 * Obtiene las plantillas que se pueden generar para un
	 * tipo de documento en el contexto de una tarea como
	 * una lista de ActionBean.
	 * @param state Estado de la tramitación
	 * @param resources Resources de la aplicación.
	 * @param action Acción asociada a la plantilla
	 * @param documentType Tipo de documento.
	 * @return lista de ActionBean
	 * @throws ISPACException
	 */
	public List getTaskTemplates(IState state, MessageResources resources, 
			String action, int documentType) throws ISPACException;
	
    public List getStateDocsActions(IState state, MessageResources resources) 
    		throws ISPACException;
    /**
     * Devuelve si existen o no informes en el estado actual
     * @param state
     * @return
     * @throws ISPACException
     */
    public boolean hasReports(IState state) throws ISPACException ;
    
    
    /**
     * Devuelve si existen o no informes en el estado actual a los que el usuario tenga permisos
     * @param state
     * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
     * @return
     * @throws ISPACException
     */
    public boolean hasReports(IState state, String resp) throws ISPACException ;
    
    /**
     * Indica si existe o no informes globales
     * @return
     * @throws ISPACException
     */
    public boolean hasGlobalReports() throws ISPACException;
    
    
    /**
    * Indica si existe o no informes globales a los que el usuario tenga permisos
    * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
    * @return
    * @throws ISPACException
    */
   public boolean hasGlobalReports(String resp) throws ISPACException;
    
    
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
     * Indica si hay informes asociados al formulario de busqueda a los que el usuario tenga acceso
     * @param idForm
     * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
     * @return
     * @throws ISPACException
     */
    public boolean hasSearchReport(int idForm, String resp) throws ISPACException;
	
	public List getListas(IState state, MessageResources resources)
			throws ISPACException;

	/**
	 * @deprecated
	 * Obtiene las acciones asociadas al resultado de la búsqueda
	 * como una lista de ActionBean.
	 *
	 * @param searchActions Acciones para la búsqueda
	 * @param resources Resources de la aplicación.
	 * @return  Lista de ActionBean
	 * @throws ISPACException
	 */
	public List getSearchResultListActions(List searchActions, 
			   MessageResources resources, Map mapParams) throws ISPACException;
	
	
	/**
	 * Obtiene las acciones asociadas al resultado de la búsqueda
	 * como una lista de ActionBean.
	 *
	 * @param searchActions Acciones para la búsqueda
	 * @param resources Resources de la aplicación.
	 * @param Properties Mensajes para la internacionalización
	 * @return  Lista de ActionBean
	 * @throws ISPACException
	 */
	public List getSearchResultListActions(List searchActions, 
			   MessageResources resources, Properties properties, Map mapParams) throws ISPACException;

	
}