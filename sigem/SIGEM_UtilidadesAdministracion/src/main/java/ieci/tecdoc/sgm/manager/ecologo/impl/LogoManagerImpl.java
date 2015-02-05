/**
 * 
 */
package ieci.tecdoc.sgm.manager.ecologo.impl;

import ieci.tecdoc.sgm.config.ecologo.EcoLogoConfigLoader;
import ieci.tecdoc.sgm.manager.ecologo.LogoManager;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author IECISA
 * @version $Revision$
 * 
 */
public class LogoManagerImpl implements LogoManager {
	/**
	 * 
	 */
	private static final String DEFAULT_IMG_MINETUR_URL = "img/Minetur.jpg";

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LogoManagerImpl.class);

	EcoLogoConfigLoader config = null;
	private String HOST;
	private String PORT;
	private String SERVLET;
	private String VERSION;
	private String IFRAME_HEIGHT;
	private String IFRAME_WIDTH;
	private String LOGO_HEIGHT;
	private String LOGO_WIDTH;

	public LogoManagerImpl() {
		config = EcoLogoConfigLoader.getInstance();

		HOST = config.getHost();
		PORT = config.getPort();
		SERVLET = config.getServlet();
		VERSION = config.getVersion();
		IFRAME_HEIGHT = config.getIframeHeight();
		IFRAME_WIDTH = config.getIFrameWidth();
		LOGO_HEIGHT = config.getLogoHeight();
		LOGO_WIDTH = config.getLogoWidth();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see ieci.tecdoc.sgm.ecologo.manager.LogoManager#render(java.lang.String)
	 */
	public String render(String idApp, String serverName) {
		if (logger.isDebugEnabled()) {
			logger.debug("render(String, String) - start");
		}

		StringBuffer htmlCode = new StringBuffer();

		String codigoAplicacion = config.getCodigoAplicacion(idApp);

		if (StringUtils.isNotBlank(codigoAplicacion)) {
			String dns = config.getDNS();
			if (StringUtils.isBlank(dns)) {
				dns = serverName;
			}

			StringBuffer url = new StringBuffer();
			url.append("http://");
			url.append(HOST);
			url.append(":" + PORT);
			url.append("/" + SERVLET);
			url.append("?dns=" + dns);
			url.append("&version=" + VERSION);
			url.append("&application=" + codigoAplicacion);
			url.append("&height=" + LOGO_HEIGHT + "&width=" + LOGO_WIDTH);
			htmlCode.append("<iframe style=\"POSITION: absolute; TOP: 5px; LEFT: 10px\" width="
					+ IFRAME_WIDTH
					+ "\"px\" height="
					+ IFRAME_HEIGHT
					+ "\"px\" marginheight=\"0\" marginwidth=\"0\" frameborder=\"0\" scrolling=\"no\" src=\""
					+ url + "\"></iframe>");
			
			if (logger.isDebugEnabled()) {
				logger.debug("Url construida para el logo: " + url);
			}
		} else {
			logger.info("Error al obtener el logo. No está registrada la aplicación con la clave: "
					+ codigoAplicacion);
			String url=DEFAULT_IMG_MINETUR_URL;
			htmlCode.append("<iframe style=\"POSITION: absolute; TOP: 5px; LEFT: 10px\" width="
					+ IFRAME_WIDTH
					+ "\"px\" height="
					+ IFRAME_HEIGHT
					+ "\"px\" marginheight=\"0\" marginwidth=\"0\" frameborder=\"0\" scrolling=\"no\" src=\""
					+ url + "\"></iframe>");
		}

		String returnString = htmlCode.toString();
		if (logger.isDebugEnabled()) {

			logger.debug("render(String, String) - end");
		}
		return returnString;
	}
}
