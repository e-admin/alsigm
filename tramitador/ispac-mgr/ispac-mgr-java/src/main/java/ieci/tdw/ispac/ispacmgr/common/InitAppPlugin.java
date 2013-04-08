package ieci.tdw.ispac.ispacmgr.common;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.util.FileTemplateManager;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;
import ieci.tdw.ispac.ispacmgr.common.constants.AppConstants;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

public class InitAppPlugin implements PlugIn {

	/** Logger de la clase. */
	private static final Logger logger = Logger.getLogger(InitAppPlugin.class);
	
	public void destroy() {
	}

	public void init(ActionServlet servlet, ModuleConfig config) 
			throws ServletException {
		
		ServletContext context = servlet.getServletContext();

		//inicializacion de los formateadores
		Formatters.init(context);

		//carga en contexto de aplicacion de la clase de constantes
		context.setAttribute("appConstants", getAppConstants());

		// Inicialización del manejador de plantillas
		try {
			FileTemplateManager.getInstance();
		} catch (ISPACException e) {
			logger.warn("Error al inicializar el manejador de plantillas", e);
		}
		
		// Inicialización del manejador de ficheros temporales
		try {
			FileTemporaryManager.getInstance();
		} catch (ISPACException e) {
			logger.warn("Error al inicializar el manejador de ficheros temporales", e);
		}
		
		// Inicialización del conector de almacenamiento
//		try {
//			DMConnectorFactory.getInstance().getConnector();
//		} catch (ISPACException e) {
//			logger.warn("Error al inicializar el conector de almacenamiento", e);
//		}
	}
	
	
	protected AppConstants getAppConstants(){
		return new AppConstants();
	}

}
