package ieci.tdw.ispac.ispacweb.tag.context;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;

import java.lang.reflect.Constructor;

import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Creación de la clase que establece el contexto para los elementos estáticos de la presentación en un entorno distribuido.
 */
public class StaticContext implements IStaticContext {
	
	/**
	 *  Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(StaticContext.class);
	
	/** 
	 * Instancia de la clase. 
	 */
    private static StaticContext mInstance = null;
    
    /** 
     * Instancia de la clase que establece el contexto estático. 
     */
    private static IStaticContext staticContext = null;
    
	/**
	 * Constructor.
	 * 
	 * @throws ISPACException si ocurre algún error.
	 */
    private StaticContext() throws ISPACException {
    	
		ISPACConfiguration ispacConfiguration = ISPACConfiguration.getInstance();
		String className = ispacConfiguration.get(ISPACConfiguration.STATIC_CONTEXT_CLASS);
		
		// Cuando exista el nombre de la clase es que el entorno es distribuido 
		if (!StringUtils.isBlank(className)) {
			
			try {
				// Cargar la clase
				Class clazz = Class.forName(className);
				if (!IStaticContext.class.isAssignableFrom(clazz)) {
					throw new ISPACException(className + " no extiende la clase IStaticContext");
				}
			
				// Invocar al constructor
				Constructor object = clazz.getConstructor(new Class [] {});
				staticContext = (IStaticContext) object.newInstance(new Object [] {});

				if (logger.isDebugEnabled()) {
					logger.debug("IStaticContext creado [" + className + "]");
				}		
			} 
			catch (Exception e) {
				throw new ISPACException(e);
			}
		}
   	}
    
	/**
	 * Obtiene una instancia de la clase.
	 * 
	 * @return Instancia de la clase.
	 * @throws ISPACException si ocurre algún error.
	 */
	public static synchronized StaticContext getInstance() throws ISPACException {
		
		if (mInstance == null) {
			mInstance = new StaticContext();
		}
		return mInstance;
	}
	
	/**
	 * Obtiene la url base para los elementos estáticos de la presentación
	 * a partir del contexto de la aplicación.
	 * 
	 * @param context Contexto de la aplicación.
	 * @return Url base para los elementos estáticos de la presentación.
	 */
	public String getBaseUrl(String context) {
		
		if (staticContext != null) {
			return staticContext.getBaseUrl(context);
		}
		
		return context;
	}
	
    public static String rewritePage(PageContext pageContext, String context, String page) {
    	
        String ispacbase = (String) pageContext.getServletContext().getAttribute("ispacbase");
        
        return rewritePage(context, ispacbase, page);        
    }
    
    public static String rewritePage(String context, String ispacbase, String page) {
        
    	if (StringUtils.isEmpty(ispacbase)) {
    		
    		return (context + "/" + page);
    	}

        return (context + "/" + ispacbase + "/" + page);
    }
    
    public static String rewritePage(String ispacbase, String page) {
        
    	if (StringUtils.isEmpty(ispacbase)) {
            	
    		return ("/" + page);
    	}

        return ("/" + ispacbase + "/" + page);
    }
	
    public static String rewriteHref(PageContext pageContext, String baseurl, String href) {
    	
        String ispacbase = (String) pageContext.getServletContext().getAttribute("ispacbase");
        
        // Skin para el organismo de conexión
        String skin = (String) pageContext.getSession().getAttribute("skin");
        if (StringUtils.isEmpty(skin)) {
        	
        	// Skin por defecto
        	skin = (String) pageContext.getServletContext().getAttribute("skin");
        }
        
        return rewriteHref(baseurl, ispacbase, skin, href);        
    }
	
    public static String rewriteHref(String baseurl, String ispacbase, String skin, String href) {
        
    	if (StringUtils.isEmpty(ispacbase)) {
    		
    		return (baseurl + "/" + skin + "/" + href);
    	}

        return (baseurl + "/" + ispacbase + "/" + skin + "/" + href);
    }
    
    public static String rewriteAction(String url, String context, String action) {
    	
    	int lastIndexContext = url.lastIndexOf(context) + context.length();
        String urlContext = url.substring(0, lastIndexContext);
        
        if (action.indexOf("/") != 0) {
        		
        	return (urlContext + "/" + action);
        }
        else {
        	
        	// Comprobar si en el action ya está el contexto
        	int indexContext = action.indexOf(context);
        	if (indexContext != -1) {
        		
        		int actionIndex = indexContext + context.length();
        		return (urlContext + action.substring(actionIndex, action.length()));
        	}
        }
    	
        return (urlContext + action);
    }

}