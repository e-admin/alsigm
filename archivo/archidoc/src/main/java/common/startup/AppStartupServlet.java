/*
 * @(#)AppStartupServlet.java 1.0 07-nov-2006
 *
 * Copyright (c) 2006 Informática El Corte Inglés, S. A.
 * Travesía de Costa Brava, 4 (Mirasierra). 28034 MADRID. España.
 * All Rights Reserved.
 *
 */

package common.startup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import common.Globals;

import es.archigest.framework.core.initialization.FrameworkLauncher;
import es.archigest.framework.web.startup.ArchigestStartupServlet;

/**
 * Servlet de inicio de la aplicación.
 * 
 * @version 1.0 07-nov-2006
 */
public class AppStartupServlet extends ArchigestStartupServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public AppStartupServlet() {
		super();
	}

	/**
	 * Inicia el servlet.
	 * 
	 * @param config
	 *            Configuración del servlet.
	 * @throws ServletException
	 *             si ocurre algún error.
	 */
	public void init(ServletConfig config) throws ServletException {

		logger.info("Inicializando framework...");

		String scriptFile = config.getInitParameter(Globals.INIT_SCRIPT_FILE);
		if (scriptFile == null) {
			logger.warn("No se ha indicado ningun script de arranque. Para especificar un script utilice el parametro INIT.SCRIPT.FILE del startup servlet. Se realizara una carga de componentes minimos.");
		}

		InputStream script = config.getServletContext().getResourceAsStream(
				scriptFile);
		if (script == null) {
			try {
				script = new FileInputStream(scriptFile);
			} catch (FileNotFoundException e) {
				logger.warn("Error al leer el script de arranque. "
						+ e.toString());
			}
		}

		FrameworkLauncher.getLauncher().setInitScript(script);
		FrameworkLauncher.getLauncher().start("",
				new ConfigurationParametersServletArchivo(config));

	}

}
