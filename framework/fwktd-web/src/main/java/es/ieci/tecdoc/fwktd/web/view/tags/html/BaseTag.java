package es.ieci.tecdoc.fwktd.web.view.tags.html;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Renderiza un elemento HTML <base> con un atributo href apuntando a la ruta
 * absoluta en la que se encuentra la página JSP. Esta etiqueta solo es válida
 * dentro del cuerpo de la etiqueta head. La presencia de esta etiqueta permite
 * al navegador resolver URLs relativas a imágenes, hojas de estilo CSS y otros
 * recursos de forma independiente a la URL que se invocó para obtener la página
 * JSP.
 * 
 */
public class BaseTag extends TagSupport { 

	/**
	 * El nombre del servidor a usar en lugar de request.getServerName().
	 */
	protected String server = null;

	/**
	 * Atributo target de la etiqueta.
	 */
	protected String target = null;

	/**
	 * @return target
	 */
	public String getTarget() {
		return (this.target);
	}

	/**
	 * Set
	 * 
	 * @param target
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * Process the start of this tag.
	 * 
	 * @exception JspException
	 *                if a JSP exception has occurred
	 */
	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		String serverName = (this.server == null) ? request.getServerName()
				: this.server;

		String baseTag = renderBaseElement(request.getScheme(), serverName,
				request.getServerPort(), request.getContextPath());

		JspWriter out = pageContext.getOut();
		try {
			out.write(baseTag);
		} catch (IOException e) {
			throw new JspException(e);
		}

		return EVAL_BODY_INCLUDE;
	}

	/**
	 * Render a fully formed HTML &lt;base&gt; element and return it as a
	 * String.
	 * 
	 * @param scheme
	 *            The scheme used in the url (ie. http or https).
	 * @param serverName
	 * @param port
	 * @param uri
	 *            The portion of the url from the protocol name up to the query
	 *            string.
	 * @return String un elemento HTML &lt;base&gt;.
	 */
	protected String renderBaseElement(String scheme, String serverName,
			int port, String uri) {

		StringBuffer tag = new StringBuffer("<base href=\"");
		tag.append(createServerUriStringBuffer(scheme, serverName, port, uri)
				.toString());

		tag.append("/\"");

		if (this.target != null) {
			tag.append(" target=\"");
			tag.append(this.target);
			tag.append("\"");
		}

		tag.append(" />");

		return tag.toString();
	}

	/**
	 * <p>
	 * Return <code>StringBuffer</code> representing the scheme, server, and
	 * port number of the current request.
	 * </p>
	 * 
	 * @param scheme
	 *            The scheme name to use
	 * @param server
	 *            The server name to use
	 * @param port
	 *            The port value to use
	 * @param uri
	 *            The uri value to use
	 * 
	 * @return StringBuffer in the form scheme: server: port
	 * @since Struts 1.2.0
	 */
	protected StringBuffer createServerUriStringBuffer(String scheme,
			String server, int port, String uri) {

		StringBuffer serverUri = createServerStringBuffer(scheme, server, port);
		serverUri.append(uri);
		return serverUri;

	}

	/**
	 * <p>
	 * Return <code>StringBuffer</code> representing the scheme, server, and
	 * port number of the current request. Server-relative URLs can be created
	 * by simply appending the server-relative path (starting with '/') to this.
	 * </p>
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * 
	 * @return URL representing the scheme, server, and port number of the
	 *         current request
	 * @since Struts 1.2.0
	 */
	public static StringBuffer requestToServerStringBuffer(
			HttpServletRequest request) {

		return createServerStringBuffer(request.getScheme(), request
				.getServerName(), request.getServerPort());

	}

	/**
	 * <p>
	 * Return <code>StringBuffer</code> representing the scheme, server, and
	 * port number of the current request.
	 * </p>
	 * 
	 * @param scheme
	 *            The scheme name to use
	 * @param server
	 *            The server name to use
	 * @param port
	 *            The port value to use
	 * 
	 * @return StringBuffer in the form scheme: server: port
	 * @since Struts 1.2.0
	 */
	public static StringBuffer createServerStringBuffer(String scheme,
			String server, int port) {

		StringBuffer url = new StringBuffer();
		if (port < 0) {
			port = 80; // Work around java.net.URL bug
		}
		url.append(scheme);
		url.append("://");
		url.append(server);
		if ((scheme.equals("http") && (port != 80))
				|| (scheme.equals("https") && (port != 443))) {
			url.append(':');
			url.append(port);
		}
		return url;

	}

	/**
	 * Returns the server.
	 * 
	 * @return String
	 */
	public String getServer() {
		return this.server;
	}

	/**
	 * Sets the server.
	 * 
	 * @param server
	 *            The server to set
	 */
	public void setServer(String server) {
		this.server = server;
	}

	private static final long serialVersionUID = -7534483790325055472L;

}
