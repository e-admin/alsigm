package ieci.tdw.ispac.ispacweb.servlet;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.log.ISPACLogInitializer;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.util.PathUtils;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

/**
 * Servlet para inicializar la aplicación. Inicializa los siguientes 
 * componentes:
 * <ul>
 * 	<li>Configuración</li>
 *  <li>Logs</li>
 * </ul>
 *
 */
public class ISPACStartupServlet extends HttpServlet {
	
	/** UID de la versión. */
	private static final long serialVersionUID = 1L;

    /** Logger de la clase. */
    private final static Logger logger = 
    	Logger.getLogger(ISPACStartupServlet.class);

	
	/**
	 * Constructor.
	 *
	 */
    public ISPACStartupServlet() {
    	super();
    }

    /**
     * Inicia el servlet.
     * @param config Configuración del servlet.
     * @throws ServletException si ocurre algún error.
     */
    public void init(ServletConfig config) throws ServletException {
    	
		super.init(config);
		
		// Inicia las variables del sistema 
		initSystemVariables(config);

		// Inicia el módulo de logs
		initLoggingModule(config);

		// Inicia el módulo de configuración
		initConfigurationModule(config);
		
		// Inicia el contexto
		initContext(config);
    }

    /**
     * Inicia las variables del sistema.
     * @param config Configuración del servlet.
     */
    protected void initSystemVariables(ServletConfig config) {

    	// ContextPath
    	String contextPath = getContextPath();
    	if (StringUtils.isNotBlank(contextPath)) {
    		String realPath = PathUtils.getRealPath(getServletContext(), "/");
        	System.setProperty(contextPath + ".app.path", 
        			realPath.substring(0, realPath.length() - 1));
    	}
    }

    /**
     * Inicia el módulo de logs.
     * @param config Configuración del servlet.
     */
    protected void initLoggingModule(ServletConfig config) {
    	String subdir = config.getInitParameter("subdir");
    	String fileName = config.getInitParameter("logFileName");
    	
		ISPACLogInitializer.init(subdir, fileName);
    }

    /**
     * Inicia el módulo de configuración.
     * @param config Configuración del servlet.
     * @throws ISPACException 
     */
    protected void initConfigurationModule(ServletConfig config) {
    	try {
    		ISPACConfiguration.getInstance();
    	} catch (ISPACException e) {
    		logger.error("Error al leer el fichero de configuración", e);
    	}
    }

    /**
     * Inicia el contexto.
     * @param config Configuración del servlet.
     * @throws ServletException si ocurre algún error.
     */
    protected void initContext(ServletConfig config) throws ServletException {
    	
    	// Contexto de la aplicación
		ServletContext context = config.getServletContext();

		try {
			// Carga la configuración de la aplicación en el contexto
			context.setAttribute("ISPACConfiguration", ISPACConfiguration.getInstance());
		} catch (ISPACException e) {
			logger.warn("No se ha podido cargar la configuración en el contexto de la aplicación", e);
			throw new ServletException(e);
		}

    }
    
    private String getContextPath() {
    	
    	String contextPath = null;
    	
    	// Obtener la ruta completa de la aplicación
    	String realPath = PathUtils.getRealPath(getServletContext(), "/");
    	if (StringUtils.isNotBlank(realPath)) {
    		
    		// Eliminar el último separador
    		contextPath = realPath.substring(0, realPath.length() - 1);
    		
    		// Obtener solamente el contextPath
    		contextPath = contextPath.substring(
    				contextPath.lastIndexOf(File.separator) + 1);
    		
    		// Eliminar la cadena ".war" del nombre, si lo hay
			int dotWarIx = contextPath.lastIndexOf(".war");
			if (dotWarIx > 0) {
				contextPath = contextPath.substring(0, dotWarIx);
			}
    	}

    	return contextPath;
    }
}
