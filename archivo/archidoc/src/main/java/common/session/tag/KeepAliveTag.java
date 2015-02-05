package common.session.tag;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import se.usuarios.AppUser;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.exceptions.ConfigException;
import common.util.ArrayUtils;
import common.util.StringUtils;

/**
 * Tag para mantener la sesion del usuario
 */
public class KeepAliveTag extends TagSupport {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Logger de la clase. */
	private static final Logger logger = Logger.getLogger(KeepAliveTag.class);
	private static final String KEEP_ALIVE_PERIOD = "KEEP_ALIVE_PERIOD";

	// 1 minuto: 60000 ms. Este periodo es utilizado si no se encuentra
	// definido en el fichero de configuracion
	private String DEFAULT_PERIOD = "60000";

	public int doStartTag() throws JspException {
		String ticket = getTicket();
		String entity = getEntity();
		String url = getUrl(ticket, entity);
		String period = getPeriod();
		StringBuffer out = buildStartContents(period, url);

		try {
			JspWriter writer = pageContext.getOut();
			writer.print(out.toString());
		} catch (IOException ioe) {
			logger.error("Error generando la función js de KEEP-ALIVE", ioe);
			throw new JspException(ioe.toString());
		}

		return (EVAL_BODY_INCLUDE);
	}

	/**
	 * Obtiene el periodo de refresco del fichero de configuracion
	 * 
	 * @return Periodo de refresco
	 */
	private String getPeriod() {
		String period = null;

		HttpSession session = ((HttpServletRequest) pageContext.getRequest())
				.getSession();
		String sessionPeriod = (String) session.getAttribute(KEEP_ALIVE_PERIOD);

		if (sessionPeriod == null) {
			try {
				ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo();
				period = csa.getConfiguracionGeneral().getKeepAlive();

				Integer.parseInt(period);
			} catch (NumberFormatException e) {
				logger.error("El periodo establecido en el fichero de configuración no es válido. Estableciendo periodo por defecto");
				period = DEFAULT_PERIOD;
			} catch (ConfigException ce) {
				logger.error("Error leyendo  el fichero de configuración. Estableciendo periodo por defecto");
				period = DEFAULT_PERIOD;
			}

			// Lo metemos en la sesion
			session.setAttribute(KEEP_ALIVE_PERIOD, period);
			sessionPeriod = period;
		}

		return sessionPeriod;
	}

	/**
	 * Obtiene la entidad del usuario conectado
	 * 
	 * @return Entidad del usuario conectado
	 */
	private String getEntity() {
		String entity = null;

		HttpSession session = ((HttpServletRequest) pageContext.getRequest())
				.getSession();
		if (session != null) {

			AppUser user = (AppUser) session.getAttribute(Constants.USUARIOKEY);

			if (user != null) {
				entity = user.getEntity();
			}
		}

		return entity;
	}

	/**
	 * Obtiene la url donde se realiza el refresco de la sesion del usuaior
	 * 
	 * @param ticket
	 * @param entity
	 * @return
	 */
	public String getUrl(String ticket, String entity) {
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		String scheme = request.getScheme();
		String server = request.getServerName();
		int port = request.getServerPort();
		String context = request.getContextPath();

		String url = scheme + "://" + server + ":" + port + context
				+ "/keepalivesession?ticket=" + ticket;

		if (StringUtils.isNotEmpty(entity))
			url += "&entity=" + entity;

		return url;
	}

	private String getTicket() {
		String ticket = null;
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();

		Cookie[] cookies = request.getCookies();
		if (ArrayUtils.isNotEmpty(cookies)) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("user")) {
					ticket = cookies[i].getValue();
				}
			}
		}
		return ticket;
	}

	/**
	 * Genera el js que realiza la recarga de la pagiga cada cierto tiempo
	 * 
	 * @param period
	 *            Periodo de refresco de la pagina
	 * @param url
	 *            Direccion de refresco de la sesion del usuario
	 * @return Buffer con el js generado
	 */
	private StringBuffer buildStartContents(String period, String url) {
		StringBuffer out = new StringBuffer();
		out.append("<script language='javascript'>\n");
		out.append("//  FUNCIONES KEEP-ALIVE\n");
		out.append("var xmlhttp=false;\n");
		out.append("function xmlHttpResquestInit () {\n");
		out.append("if (window.XMLHttpRequest) {\n");
		out.append("xmlhttp = new XMLHttpRequest();\n");
		out.append("if (xmlhttp.overrideMimeType)\n");
		out.append("xmlhttp.overrideMimeType('text/xml');\n");
		out.append("}else if (window.ActiveXObject) {\n");
		out.append("try {\n");
		out.append("xmlhttp = new ActiveXObject ('Msxml2.XMLHTTP');\n");
		out.append("} catch (e) {\n");
		out.append("try {\n");
		out.append("xmlhttp = new ActiveXObject ('Microsoft.XMLHTTP');\n");
		out.append("} catch (E)	{\n");
		out.append("xmlhttp = false; \n} \n} \n} \n}\n");
		out.append("function keepAliveInit()\n{\n");
		out.append("xmlHttpResquestInit ();\n");
		out.append("window.setInterval ('keepAliveRefresh()',");
		out.append(period + "); \n}\n");
		out.append("function keepAliveRefresh() {\n");
		// out.append("window.status='KeepAlive:' + new Date();");
		out.append("xmlhttp.open ('GET', '" + url + "', true);\n");
		out.append("xmlhttp.onreadystatechange=function () {\n");
		out.append("if (xmlhttp.readyState==4) {}\n");
		out.append("\n}\n");
		out.append("xmlhttp.send (null); \n}\n");
		out.append("// FIN FUNCIONES KEEP-ALIVE\n");
		out.append("keepAliveInit();");
		out.append("</script>\n");
		return out;
	}

}