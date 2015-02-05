package common.tags.actions;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import common.BaseConfiguration;

/**
 * Visualiza el action del formulario del login.<br/>
 * Si el valor del atributo <code>action</code> es nulo, devolverá el valor
 * almacenado en el parámetro de sesión: SecurityGlobals.REQUESTED_URL. <br/>
 * Modo de uso:<br/>
 * <br/>
 * <ul>
 * <li><code><login:getLoginActionURL action="/action/login" /></code></li>
 * <li><code><login:getLoginActionURL /></code></li>
 * </ul>
 */
public class LoginActionTag extends TagSupport {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Logger de la clase. */
	private static final Logger logger = Logger.getLogger(LoginActionTag.class);

	/** Action al que se va a redirigir tras el login. */
	private String action = null;

	/**
	 * Establece el action al que se va a redirigir tras el login.
	 * 
	 * @param Action
	 *            al que se va a redirigir tras el login.
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * Se encarga de escribir la URL del action en función del valor almacenado
	 * en el parámetro de sesión: SecurityGlobals.REQUESTED_URL.
	 * 
	 * @return un código que indica si el método concluyó con éxito [EVAL_PAGE].
	 * @throws JspException
	 *             en caso de que se produzca algún error.
	 */
	public int doEndTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			out.print(getURL());
		} catch (IOException e) {
			logger.error(
					"No se puede escribir el action en la p\u00E1gina JSP", e);
			throw new JspException(e);
		}

		return EVAL_PAGE;
	}

	/**
	 * Obtiene la URL del action.
	 * 
	 * @return URL del action.
	 * @throws IOException
	 *             si ocurre algún error.
	 */
	protected String getURL() throws IOException {
		final StringBuffer url = new StringBuffer();

		String port = null;

		try {
			BaseConfiguration config = BaseConfiguration.getInstance();

			port = (String) config
					.getConfigurationProperty(BaseConfiguration.HTTPS_PORT);
		} catch (Exception e) {
			logger.debug(
					"No se puede ha encontrado la definición de puerto HTTPS en la configuracion",
					e);
		}

		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();

		url.append("https://");
		url.append(request.getServerName());

		if (!"443".equals(port)) {
			url.append(":");
			url.append(port);
		}

		// url.append("/");
		url.append(request.getContextPath());

		if (action != null)
			url.append(action);

		if (logger.isDebugEnabled())
			logger.debug("URL: " + url.toString());

		return url.toString();
	}
}
