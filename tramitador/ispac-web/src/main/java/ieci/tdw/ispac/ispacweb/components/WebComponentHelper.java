package ieci.tdw.ispac.ispacweb.components;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

/**
 * Utilidad para manipular componentes web.
 *
 */
public class WebComponentHelper {
	
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(WebComponentHelper.class);

	
	/**
	 * Crea los componentes web a partir del XML con su definición.
	 * El XML deberá tener el siguiente formato:
	 * <pre>
	 * <components>
	 *   <component id="sucesos" class="ieci.tdw.ispac.ispacmgr.components.worklist.NoticesComponent">
	 *     <param name="param1" value="value1"/>
	 *   </component>
	 * </components>
	 * </pre>
	 * 
	 * @param xml XML con los componentes web.
	 * @return Lista de componentes web.
	 * @throws ISPACException si ocurre algún error.
	 */
	public static List parseWebComponents(String xml) throws ISPACException {
		
		List components = new ArrayList();
		
		try {
			
			if (StringUtils.isNotBlank(xml)) {
				XmlFacade xmlFacade = new XmlFacade(xml);
				
				NodeIterator componentsIt = xmlFacade.getNodeIterator("/components/component");
				
				Node componentNode = componentsIt.nextNode(); 
				while (componentNode != null) {
					
					String className = XmlFacade.getAttributeValue(componentNode, "class");
					IWebComponent webComponent = loadWebComponent(className);
					if (webComponent != null) {
					
						// Identificador del componente web
						String id = XmlFacade.getAttributeValue(componentNode, "id");
						if (StringUtils.isNotBlank(id)) {
							webComponent.setId(id);
						}
						
						// Parámetros del componente web
						NodeIterator paramIt = XmlFacade.getNodeIterator(componentNode, "param");
						Node paramNode = paramIt.nextNode();
						while (paramNode != null) {
							
							String key = XmlFacade.getAttributeValue(paramNode, "name");
							String value = XmlFacade.getAttributeValue(paramNode, "value");
							
							if (StringUtils.isNotBlank(key)) {
								webComponent.putParameter(key, value);
							}
							
							paramNode = paramIt.nextNode();
						}
						
						components.add(webComponent);
					}
					
					componentNode = componentsIt.nextNode();
				}
			}
			
		} catch (Throwable t) {
			logger.error("Error al parsear el XML de los componentes web", t);
			throw new ISPACException(t);
		}

		return components;
	}
	
	public static void addWebComponent(List componentList, String className) {
		IWebComponent component = loadWebComponent(className);
		if (component != null) {
			componentList.add(component);
		}
	}
	
	public static IWebComponent loadWebComponent(String className) {
		
		IWebComponent component = null;
		
		try {
			Class clazz = Class.forName(className);
			component = (IWebComponent)clazz.newInstance();
		} catch (Throwable t) {
			logger.error("No se ha podido instanciar el componente web: " + className, t);
		}
		
		return component;
		
	}
}
