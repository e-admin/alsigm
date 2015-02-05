/*
 * Created on 25-ene-2005
 *
 */
package test;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;

import common.ArchidocAppConstants;
import common.startup.AppConfig;

// TODO unificar con AppConfig
/**
 * Servlet que realiza tareas de configuracion en el arranque de la aplicacion
 * 
 */
public class InitServlet extends GenericServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		Logger rootLogger = Logger.getRootLogger();
		Enumeration appenders = rootLogger.getAllAppenders();
		Appender aAppender = null;
		while (appenders.hasMoreElements()) {
			aAppender = (Appender) appenders.nextElement();
			if (aAppender.getName() == null)
				rootLogger.removeAppender(aAppender);
		}

		getServletContext().setAttribute("appConstants",
				new ArchidocAppConstants());
		AppConfig.setBasePath(getServletContext().getRealPath("/"));
	}

	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
	}
}
