package com.ieci.tecdoc.isicres.init;

import java.io.File;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.ieci.tecdoc.common.isicres.ConfigFilePathResolverIsicres;

/*
 * @author LMVICENTE @creationDate 28-oct-2004 16:31:13
 *
 * @version
 * @since
 */
public class Log4jInitServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		
		
		
		
		
		ConfigFilePathResolverIsicres configFilePathResolverIsicres = ConfigFilePathResolverIsicres.getInstance();
		
		String subDir= getServletConfig().getInitParameter("subDir");
		String file =null;
		
		if (StringUtils.isEmpty(subDir)){
			file = configFilePathResolverIsicres.getISicresLog4jPath();
		}else{
			file = configFilePathResolverIsicres.getISicresLog4jPath(subDir);
		}

		if (file != null) {
			// file = getServletContext().getRealPath(file);
			if (new File(file).exists()) {
				DOMConfigurator.configure(file);
				Logger.getLogger(Log4jInitServlet.class).info(
						"Log4j cargado [" + file + "]");
			} else {
				Logger.getLogger(Log4jInitServlet.class).info(
						"Log4j no encontrado [" + file + "]");
			}
		}
		Logger
				.getLogger(Log4jInitServlet.class)
				.info(
						"-------------- ESTABLECIDAS PROPIEDADES DEL PARSER XML -------------------");
	}

}