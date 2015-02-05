package es.ieci.tecdoc.fwktd.core.config.web;

import java.io.File;
import java.net.MalformedURLException;

import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContextUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ContextUtil.class);

	static public String getRealPath(ServletContext servletContext,
			String resourcePath) {
		String result = StringUtils.EMPTY;

		result = servletContext.getRealPath(resourcePath);

		if (result == null) {
			// resources en .war (JBoss, WebLogic)
			java.net.URL url;
			try {
				url = servletContext.getResource(resourcePath);
				if (url != null) {
					result = url.getPath();
				} else {

					result = getRealPathFromNotExitsResources(servletContext,
							resourcePath);

				}
			} catch (MalformedURLException e) {
				logger.warn("No se ha podido recuperar el path real de: "
						+ resourcePath);
			}

		}

		return result;

	}

	static private String getRealPathFromNotExitsResources(
			ServletContext servletContext, String resourcePath) {

		String result = null;
		result = getWebServletContextPath(servletContext);

		if (result != null) {
			result = result + File.separator + resourcePath;
			result = FilenameUtils.normalize(result);
		}

		return result;

	}

	static private String getWebServletContextPath(ServletContext servletContext) {
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
					result = StringUtils.substringBeforeLast(result, webInfDir);
				} else {
					result = null;
					logger
							.error("No se ha podido recuperar el path del contexto de la aplicacion - se esperaba que existiera el directorio:"
									+ webInfDir);
				}
			} catch (MalformedURLException e) {
				logger
						.warn("No se ha podido recuperar el getWebServletContextPath de: "
								+ webInfDir);
			}

		}

		return result;

	}

}
