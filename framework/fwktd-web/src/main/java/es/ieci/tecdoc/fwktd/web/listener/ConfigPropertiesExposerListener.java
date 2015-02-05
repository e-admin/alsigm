package es.ieci.tecdoc.fwktd.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import es.ieci.tecdoc.fwktd.core.spring.configuration.PlatformPropertyPlaceholderConfigurer;

/**
 * Listener para exponer las propiedades configuradas por el
 * PlatformPropertyPlaceholderConfigurer en el contexto de aplicación web.
 *
 * Por defecto, el nombre del bean que contiene en PlaceHolderConfigurer es
 * &quot;placeholderConfigurer&quot;. Si se desea obtener el bean con otro
 * nombre habrá que añadir un parámetro al contexto del tipo:
 *
 * <pre>
 * 	<context-param>
 * 	<param-name>placeholderConfigurerBeanName</param-name>
 * 	<param-value>myPlaceholderConfigurer</param-value>
 * </context-param>
 * </pre>
 *
 * Este listener almacena las propiedades en el contexto de la aplicación con el
 * atributo llamado &quot;configProperties&quot;. Si se desea modificar el nombre
 * de este atributo habrá que añadir un parámetro al contexto del tipo:
 *
 * <pre>
 * 	<context-param>
 * 	<param-name>placeholderConfigurerPropertiesContextProperty</param-name>
 * 	<param-value>myConfigProperties</param-value>
 * </context-param>
 * </pre>
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class ConfigPropertiesExposerListener implements ServletContextListener {

	public static final String DEFAULT_PROPERTIES_BEAN_NAME = "placeholderConfigurer";
	public static final String DEFAULT_CONTEXT_PROPERTY = "configProperties";

	public static final String PROPERTIES_BEAN_NAME_PARAMETER = "placeholderConfigurerBeanName";
	public static final String CONTEXT_PROPERTY_PARAMETER = "placeholderConfigurerPropertiesContextProperty";


	/**
	 * Notification that the web application initialization process is starting.
	 * All ServletContextListeners are notified of context initialization before
	 * any filter or servlet in the web application is initialized.
	 */
	public void contextDestroyed(ServletContextEvent sce) {
	}

	/**
	 * Notification that the servlet context is about to be shut down. All
	 * servlets and filters have been destroy()ed before any
	 * ServletContextListeners are notified of context destruction.
	 */
	public void contextInitialized(ServletContextEvent sce) {

		ServletContext servletContext = sce.getServletContext();

		// Nombre del bean que contiene el PlatformPropertyPlaceholderConfigurer
		String propertiesBeanName = servletContext.getInitParameter(PROPERTIES_BEAN_NAME_PARAMETER);
		if (StringUtils.isBlank(propertiesBeanName)) {
			propertiesBeanName = DEFAULT_PROPERTIES_BEAN_NAME;
		}

		// Nombre del atributo con las propiedades en el contexto de la aplicación web
		String contextProperty = servletContext.getInitParameter(CONTEXT_PROPERTY_PARAMETER);
		if (StringUtils.isBlank(contextProperty)) {
			contextProperty = DEFAULT_CONTEXT_PROPERTY;
		}

		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
		PlatformPropertyPlaceholderConfigurer configurer = (PlatformPropertyPlaceholderConfigurer) context
				.getBean(propertiesBeanName);
		sce.getServletContext().setAttribute(contextProperty,
				configurer.getResolvedProps());

	}
}
