package ieci.tdw.ispac.ispacweb.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;

public class ISPACActionServlet extends ActionServlet {
	
	/** UID de la versión. */
	private static final long serialVersionUID = 1L;
    
	
	/**
	 * Constructor.
	 *
	 */
    public ISPACActionServlet() {
    	super();
    }

    /**
     * Inicia el servlet.
     * @param config Configuración del servlet.
     * @throws ServletException si ocurre algún error.
     */
    public void init(ServletConfig config) throws ServletException {
		super.init(config);
    }
    
}
