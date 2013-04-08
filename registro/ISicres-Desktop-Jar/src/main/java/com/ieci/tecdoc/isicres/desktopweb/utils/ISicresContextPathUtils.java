package com.ieci.tecdoc.isicres.desktopweb.utils;

import java.io.File;
import java.net.MalformedURLException;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

/**
*
* @author IECISA
*
*/
public class ISicresContextPathUtils {

	 private static final Logger _log = Logger.getLogger(ISicresContextPathUtils.class);

	/**
	 * Metodo que obtiene el Path donde se encuentra el ServletContext pasado
	 * como parametro
	 * @param servletContext
	 * @return
	 */
	public static String getWebServletContextPath(ServletContext servletContext) {

		String result = null;
		String webInfDir = File.separator + "WEB-INF";

		result = servletContext.getRealPath(webInfDir);

		if (result == null) {
			// resource en .war (JBoss, WebLogic)
			java.net.URL url;
			try {
				url = servletContext.getResource(webInfDir);
				if (url != null) {
					result = url.getPath();
					result = result.substring(0, result.lastIndexOf(webInfDir));
				} else {
					result = null;
					_log.error("No se ha podido recuperar el path del contexto de la aplicacion - se esperaba que existiera el directorio:"
							+ webInfDir);
				}
			} catch (MalformedURLException e) {
				_log.warn("No se ha podido recuperar el getWebServletContextPath de: "
						+ webInfDir);
			}

		} else {
			result = result.substring(0, result.lastIndexOf(webInfDir));
		}

		return result;
	}

}
