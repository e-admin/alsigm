package ieci.tdw.ispac.ispacweb.dwr;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

/**
 * Clase de acceso a los métodos del API de Registro
 *
 */
public class ISPACDWRUtils extends BaseDWR {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(ISPACDWRUtils.class);
	
	
	public String getInclude(String pathJsp) throws ServletException,
			IOException {
		
		if (logger.isInfoEnabled()) {
			logger.info("Incluyendo la página [" + pathJsp + "]");
		}
		
		WebContext wctx = WebContextFactory.get();
		return wctx.forwardToString(pathJsp);
	}
}

