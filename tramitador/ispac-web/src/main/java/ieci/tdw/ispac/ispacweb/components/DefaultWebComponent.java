package ieci.tdw.ispac.ispacweb.components;

import ieci.tdw.ispac.api.ISessionAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPIFactory;
import ieci.tdw.ispac.ispaclib.messages.MessagesFormatter;
import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Implementación base para los componentes web.
 *
 */
public abstract class DefaultWebComponent implements IWebComponent {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(DefaultWebComponent.class);
	
	
	/**
	 * Identificador del componente.
	 */
	private String id = null;
	
	/**
	 * Título del componente.
	 */
	private String title = null;
	
	/**
	 * Contenido del componente.
	 */
	private String content = null;
	
	/**
	 * Parámetros del componente
	 */
	private Map parameters = new HashMap();
	
	
	/**
	 * Constructor.
	 */
	public DefaultWebComponent() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Map getParameters() {
		return parameters;
	}

	public Object getParameter(Object key) {
		return parameters.get(key);
	}

	public void putParameter(Object key, Object value) {
		parameters.put(key, value);
	}

	/**
	 * Renderiza el componente.
	 * @param context Contexto de servlets.
	 * @param request Petición del cliente.
	 * @param sessionAPI API de sesión.
	 * @param params Parámetros de configuración.
	 * @return Código HTML para mostrar en pantalla.
	 * @throws ISPACException si ocurre algún error.
	 */
    public abstract void render(ServletContext context, HttpServletRequest request,
    		ISessionAPI sessionAPI) throws ISPACException;

	public String getMessage(Locale locale, String key) {
		return getMessage(locale, key, null);
	}
		
	public String getMessage(Locale locale, String key, Object params[]) {

		try {
			ResourceBundle resBundle = ResourceBundle.getBundle(getMessagesFile(), locale);
	        String msg = resBundle.getString(key);
	        
        	return MessagesFormatter.format(msg, params);

		} catch (MissingResourceException e) {
			logger.warn("No se ha encontrado el recurso con clave [" + key + "]", e);
			return key;
		}
	}
    
    public String getMessagesFile() { 
    	return getClass().getName();
    }
    
	public String rewriteHref(ServletContext context, HttpServletRequest request, String href) 
			throws ISPACException {

		String contextPath = request.getContextPath();
        String ispacbase = (String) context.getAttribute("ispacbase");
        
        String skin = (String) request.getSession().getAttribute("skin");
        if (StringUtils.isEmpty(skin)) {
        	skin = (String) context.getAttribute("skin");
        }
        
        return StaticContext.rewriteHref(StaticContext.getInstance().getBaseUrl(contextPath),
        		ispacbase, skin, href);        
	}
    
	public String getStateTicket(HttpServletRequest request) {
		return (String)request.getAttribute(SessionAPIFactory.ATTR_STATETICKET);
	}

}
