package es.ieci.tecdoc.fwktd.sir.ws.servlet;

import java.io.File;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class Log4jInitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static String LOG4J_FILENAME = "log4j.xml";

	public void init() throws ServletException {
		String file = ServicioIntercambioRegistralConfigFilePathResolver
				.getInstance().resolveFullPath(LOG4J_FILENAME);
		if ((file != null) && new File(file).exists()) {
			DOMConfigurator.configure(file);
			Logger.getLogger(Log4jInitServlet.class).info(
					"Log4j cargado [" + file + "]");
		} else {
			Logger.getLogger(Log4jInitServlet.class).info(
					"Log4j no encontrado [" + file + "]");
		}
	}

}
