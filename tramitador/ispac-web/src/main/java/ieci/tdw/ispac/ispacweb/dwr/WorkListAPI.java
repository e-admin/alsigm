package ieci.tdw.ispac.ispacweb.dwr;

import ieci.tdw.ispac.api.ISessionAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;
import ieci.tdw.ispac.ispacweb.components.IWebComponent;
import ieci.tdw.ispac.ispacweb.components.WebComponentHelper;
import ieci.tdw.ispac.ispacweb.dwr.vo.WebComponent;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Clase de acceso a los métodos del API de la bandeja de entrada
 *
 */
public class WorkListAPI extends BaseDWR {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(WorkListAPI.class);

	
	/**
	 * Obtiene los componentes web de la bandeja de entrada.
	 * @return Componentes web de la bandeja de entrada.
	 * @throws ISPACException si ocurre algún error.
	 */
	public WebComponent[] getWebComponents() throws ISPACException {
		
		List components = new ArrayList(); 
		             
		ISessionAPI sessionAPI = null;
		
		try {
			
			sessionAPI = createSessionAPI();
			
			// Obtener la lista de componentes web
			List webComponents = getWebComponents(sessionAPI);

			// Renderizar los componentes
			for (int i = 0; i < webComponents.size(); i++) {
				IWebComponent webComponent = (IWebComponent) webComponents.get(i); 
				if (webComponent != null) {
					webComponent.render(getServletContext(), getHttpServletRequest(), sessionAPI);
					components.add(new WebComponent(webComponent.getId(), webComponent.getTitle(),
							webComponent.getContent()));
				}
			}

		} catch (ISPACException e) {
			logger.error("Error al obtener los componentes de la bandeja de entrada", e);
			throw e;
		} catch (Throwable t) {
			logger.error("Error al obtener los componentes de la bandeja de entrada", t);
			throw new ISPACException("Error al obtener los componentes de la bandeja de entrada", t);
		} finally {
			releaseSessionAPI(sessionAPI);
		}
		
		return (WebComponent[]) components.toArray(new WebComponent[components.size()]);
	}

	/**
	 * Obtiene la información de un componente web.
	 * @param id Identificador del componente.
	 * @return Información del componente web.
	 * @throws ISPACException si ocurre algún error.
	 */
	public WebComponent getWebComponent(String id) throws ISPACException {
		
		WebComponent component = null;
		
		ISessionAPI sessionAPI = null;
		
		try {
			
			sessionAPI = createSessionAPI();
			
			// Obtener la lista de componentes web
			List webComponents = getWebComponents(sessionAPI);

			// Renderizar los componentes
			for (int i = 0; (component == null) && (i < webComponents.size()); i++) {
				IWebComponent webComponent = (IWebComponent) webComponents.get(i); 
				if ((webComponent != null) && webComponent.getId().equals(id)) {
					webComponent.render(getServletContext(), getHttpServletRequest(), sessionAPI);
					component = new WebComponent(webComponent.getId(), webComponent.getTitle(),
							webComponent.getContent());
				}
			}

		} catch (ISPACException e) {
			logger.error("Error al obtener el componente de la bandeja de entrada", e);
			throw e;
		} catch (Throwable t) {
			logger.error("Error al obtener el componente de la bandeja de entrada", t);
			throw new ISPACException("Error al obtener el componente de la bandeja de entrada", t);
		} finally {
			releaseSessionAPI(sessionAPI);
		}
		
		return component;
	}
	
	protected List getWebComponents(ISessionAPI sessionAPI) throws ISPACException {
		
		// Obtener el XML con los componentes web
		String webComponentsXML = ConfigurationMgr.getVarGlobal(sessionAPI.getClientContext(), 
				ConfigurationMgr.INBOX_WEB_COMPONENTS);

		// Obtener la lista de componentes web
		List webComponents = WebComponentHelper.parseWebComponents(webComponentsXML);
		if (CollectionUtils.isEmpty(webComponents)) {
			
			// Añadir los componentes por defecto
			webComponents = new ArrayList();
			
			WebComponentHelper.addWebComponent(webComponents, 
					"ieci.tdw.ispac.ispacmgr.components.worklist.NoticesComponent");
			WebComponentHelper.addWebComponent(webComponents, 
					"ieci.tdw.ispac.ispacmgr.components.worklist.StagesComponent");
			WebComponentHelper.addWebComponent(webComponents, 
					"ieci.tdw.ispac.ispacmgr.components.worklist.TasksComponent");
			WebComponentHelper.addWebComponent(webComponents, 
					"ieci.tdw.ispac.ispacmgr.components.worklist.ClosedTasksComponent");
		}
		
		return webComponents;
	}
	
}
