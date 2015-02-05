package ieci.tdw.ispac.ispacweb.servlet;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.impl.SessionAPIFactory;
import ieci.tdw.ispac.ispaclib.common.constants.ScopeConstants;
import ieci.tdw.ispac.ispaclib.session.OrganizationUser;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet base de ISPAC.
 */
public abstract class ISPACBaseServlet extends HttpServlet {
  
	/** Logger de la clase. */
	protected static Logger logger = Logger.getLogger(ISPACBaseServlet.class);
  
  
	/**
	 * Initialize this servlet.
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
    
	/**
	 * Handles the special WebDAV methods.
	 */
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if (logger.isDebugEnabled()) {
			showRequestInfo(request);
		}

		// Establecer el identificador de la entidad organizativa
		String organizationId = getOrganizationId(request);
		if (organizationId != null){
			OrganizationUserInfo info = new OrganizationUserInfo();
			info.setOrganizationId(organizationId);
			OrganizationUser.setOrganizationUserInfo(info);
			request.getSession().setAttribute(ScopeConstants.ORGANIZATION_USER_INFO, info);
		}
		
		super.service(request, response);
	}
  
  
	/**
	 * 
	 * Obtiene el identificador de la entidad organizativa.
	 * @param request Información de petición. 
	 * @return Identificador de la entidad organizativa.
	 */
	protected String getOrganizationId(HttpServletRequest request) {

		String organizationId = null;
		String path = normalize(request.getPathInfo());
		String substring = path.substring(1);
		String[] tokens = StringUtils.split(substring, '/');
		
		if (tokens.length >= 3) {
			organizationId = tokens[0];
		}
		
		return organizationId;
	}

	/**
	 * Obtiene la sesión de tramitación.
	 * @param request Información de petición.
	 * @return Sesión de tramitación.
	 */
	protected SessionAPI getSession(HttpServletRequest request) {

		SessionAPI session = null;

		String path = normalize(request.getPathInfo());
		String substring = path.substring(1);
		
		String[] tokens = StringUtils.split(substring, '/');
		if (tokens.length >0){
			try {
				String sUUID = null;

				if (StringUtils.countMatches(path, "/") >= 3) {
					sUUID = tokens[1];
				} else {
					sUUID = tokens[0];
				}
				
				session = SessionAPIFactory.getSessionAPI(request, sUUID);
				
			} catch (ISPACException e) {
				logger.error(e.getMessage());
				return null;
			}
		}
		
		return session;
	}

	/**
	 * Show HTTP header information.
	 */
	protected void showRequestInfo(HttpServletRequest request) {

		if (logger.isDebugEnabled()) {
			logger.debug("Request Info");
	
			// Show generic info
			logger.debug("Encoding : " + request.getCharacterEncoding());
			logger.debug("Length : " + request.getContentLength());
			logger.debug("Type : " + request.getContentType());
	
			logger.debug("Parameters");
	
			StringBuffer paramString;
			Enumeration parameters = request.getParameterNames();
			while (parameters.hasMoreElements()) {
				String paramName = (String) parameters.nextElement();
				String[] values = request.getParameterValues(paramName);
				paramString = new StringBuffer(paramName).append(" : ");
				for (int i = 0; i < values.length; i++) {
					paramString.append(values[i]).append(", ");
				}
				logger.debug(paramString.toString());
			}
	
			logger.debug("Protocol: " + request.getProtocol());
			logger.debug("Address: " + request.getRemoteAddr());
			logger.debug("Host: " + request.getRemoteHost());
			logger.debug("Scheme: " + request.getScheme());
			logger.debug("Server Name: " + request.getServerName());
			logger.debug("Server Port: " + request.getServerPort());
	
			logger.debug("Attributes");
	
			Enumeration attributes = request.getAttributeNames();
			while (attributes.hasMoreElements()) {
				String attributeName = (String) attributes.nextElement();
				logger.debug(attributeName + " : " 
						+ request.getAttribute(attributeName).toString());
			}
	
			// Show HTTP info
			logger.debug("HTTP Header Info");
	
			logger.debug("Authentication Type: " + request.getAuthType());
			logger.debug("HTTP Method: " + request.getMethod());
			logger.debug("Path Info: " + request.getPathInfo());
			logger.debug("Path translated: " + request.getPathTranslated());
			logger.debug("Query string: " + request.getQueryString());
			logger.debug("Remote user: " + request.getRemoteUser());
			logger.debug("Requested session id: "+ request.getRequestedSessionId());
			logger.debug("Request URI: " + request.getRequestURI());
			logger.debug("Context path: " + request.getContextPath());
			logger.debug("Servlet path: " + request.getServletPath());
			logger.debug("User principal: " + request.getUserPrincipal());
	
			logger.debug("Headers: ");
	
			Enumeration headers = request.getHeaderNames();
			while (headers.hasMoreElements()) {
				String headerName = (String) headers.nextElement();
				logger.debug(headerName + " : " + request.getHeader(headerName));
			}
		}
	}

	/**
	 * Obtiene el path relativo asociado al servlet.
	 * Se ha eliminado el string de conexión.
	 * @param request The servlet request we are processing
	 */
	protected String getRelativePath(HttpServletRequest request) {

		String path = request.getPathInfo();
		if (path == null || path.equals("")) {
			path = "/";
		} else {
			path = normalize(path);
			path = path.substring(path.lastIndexOf('/'));
		}
		return path;
	}

	/**
	 * Return a context-relative path, beginning with a "/", that represents
	 * the canonical version of the specified path after ".." and "." elements
	 * are resolved out.  If the specified path attempts to go outside the
	 * boundaries of the current context (i.e. too many ".." path elements
	 * are present), return <code>null</code> instead.
	 *
	 * @param path Path to be normalized
	 */
	protected String normalize(String path) {

		if (path == null) {
			return null;
		}

		// Create a place for the normalized path
		String normalized = path;

		if (normalized == null) {
			return (null);
		}

		// Normalize the slashes and add leading slash if necessary
		if (normalized.indexOf('\\') >= 0) {
			normalized = normalized.replace('\\', '/');
		}
		if (!normalized.startsWith("/")) {
			normalized = "/" + normalized;
		}

		// Resolve occurrences of "//" in the normalized path
		while (true) {
			int index = normalized.indexOf("//");
			if (index < 0)
				break;
			normalized = normalized.substring(0, index)
					+ normalized.substring(index + 1);
		}

		return (normalized);
	}

}
