package ieci.tdw.ispac.ispacweb.webdav.servlets;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.impl.SessionAPIFactory;
import ieci.tdw.ispac.ispaclib.common.constants.ScopeConstants;
import ieci.tdw.ispac.ispaclib.session.OrganizationUser;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.webdav.util.MD5Encoder;
import ieci.tdw.ispac.ispacweb.webdav.util.URLEncoder;
import ieci.tdw.ispac.ispacweb.webdav.util.XMLWriter;

import java.io.IOException;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;

/**
 * The default resource-serving servlet for most web applications,
 * used to serve static resources such as HTML pages and images.
 */

public abstract class BaseServlet extends HttpServlet {
  
	private static final long serialVersionUID = -3324869175071035547L;

	/** Logger de la clase. */
	protected static Logger logger = Logger.getLogger(BaseServlet.class);
  
	/**
	 * Repository of the locks.
	 */
	protected static HashMap resourceLocks = new HashMap();

	protected static final String METHOD_OPTIONS = "OPTIONS";
	protected static final String METHOD_PROPFIND = "PROPFIND";
	protected static final String METHOD_LOCK = "LOCK";
	protected static final String METHOD_UNLOCK = "UNLOCK";
	protected static final String METHOD_GET = "GET";
	protected static final String METHOD_PUT = "PUT";
	protected static final String METHOD_HEAD = "HEAD";
	
	protected static final String ALLOWED_METHODS =  
		"OPTIONS, PROPFIND, GET, PUT, LOCK, UNLOCK";
	
	/**
	 * Create a new lock.
	 */
	protected static final int LOCK_CREATION = 0;

	/**
	 * Refresh lock.
	 */
	protected static final int LOCK_REFRESH = 1;

	/**
	 * 
	 * Default lock timeout value.
	 */
	protected static final int DEFAULT_TIMEOUT = 3600;

	/**
	 * Maximum lock timeout.
	 */
	protected static final int MAX_TIMEOUT = 604800;

	/**
	 * Information used to generate reasonably secure lock ids.
	 */
	protected String secret = "ispac";

	/**
	 * Default depth is infite.
	 */
	protected static final int INFINITY = 3;

	/**
	 * PROPFIND - Specify a property mask.
	 */
	protected static final int FIND_BY_PROPERTY = 0;

	/**
	 * PROPFIND - Display all properties.
	 */
	protected static final int FIND_ALL_PROP = 1;

	/**
	 * PROPFIND - Return property names.
	 */
	protected static final int FIND_PROPERTY_NAMES = 2;

	/**
	 * MD5 message digest provider.
	 */
	protected static MessageDigest md5Helper;

	/**
	 * The MD5 helper object for this class.
	 */
	protected static final MD5Encoder md5Encoder = new MD5Encoder();

	/**
	 * Array containing the safe characters set.
	 */
	protected static URLEncoder urlEncoder;

	static {
		urlEncoder = new URLEncoder();
		urlEncoder.addSafeCharacter('-');
		urlEncoder.addSafeCharacter('_');
		urlEncoder.addSafeCharacter('.');
		urlEncoder.addSafeCharacter('*');
		urlEncoder.addSafeCharacter('/');
	}
  
	/**
	 * Simple date format for the creation date ISO representation (partial).
	 */
	protected static final SimpleDateFormat creationDateFormat = 
		new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

	static {
		creationDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	protected static final SimpleDateFormat expireDateFormat = 
		new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);

	static {
		expireDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	/**
	 * The input buffer size to use when serving documents.
	 */
	protected int input = 4096;

	/**
	 * The output buffer size to use when serving resources.
	 */
	protected int output = 4096;
  
	/**
	 * Initialize this servlet.
	 */
	public void init(ServletConfig config) throws ServletException {

		super.init(config);

		// Load the MD5 helper used to calculate signatures.
		try {
			md5Helper = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
			throw new IllegalStateException();
		}
	}
    

	protected String getOrganizationId(HttpServletRequest request) {

		String organizationId = null;
		String path = normalize(request.getPathInfo());
		String substring = path.substring(1);
		String[] tokens = StringUtils.split(substring, '/');
		
		if (tokens.length >= 3) {
			organizationId = tokens[0];
			
			if (logger.isInfoEnabled()) {
				logger.info("organizationId: " + organizationId);
			}
		}
		
		return organizationId;
	}

	/**
	 * Get session or null.
	 */
	protected SessionAPI getSession(HttpServletRequest request) {

		SessionAPI session = null;

		String path = normalize(request.getPathInfo());
		String substring = path.substring(1);
		
		String[] tokens = StringUtils.split(substring, '/');
		if (tokens.length >0){
			try {
				String sUUID = null;
				//if (tokens.length >= 3) 
				if (StringUtils.countMatches(path, "/") >= 3)
					sUUID = tokens[1];
				else
					sUUID = tokens[0];
				
				if (logger.isInfoEnabled()) {
					logger.info("ticket: " + sUUID);
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
	 * Handles the special WebDAV methods.
	 */
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String organizationId = getOrganizationId(request);
		if (organizationId != null){
			OrganizationUserInfo info = new OrganizationUserInfo();
			info.setOrganizationId(organizationId);
			OrganizationUser.setOrganizationUserInfo(info);
			request.getSession().setAttribute(ScopeConstants.ORGANIZATION_USER_INFO, info);
		}
			
		String method = request.getMethod();

		if (logger.isDebugEnabled()) {
			showRequestInfo(request);
		}
		
		if (logger.isInfoEnabled()) {
			logger.info("[" + method + "] " + getRelativePath(request));
			logger.info("ContextPath: " + request.getContextPath());
			logger.info("PathInfo: " + request.getPathInfo());
			logger.info("PathTranslated: " + request.getPathTranslated());
			logger.info("QueryString: " + request.getQueryString());
			logger.info("RemoteAddr: " + request.getRemoteAddr());
			logger.info("RequestURI: " + request.getRequestURI());
			logger.info("RequestURL: " + request.getRequestURL().toString());
			logger.info("Method: " + request.getMethod());
			Enumeration headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String headerName = (String) headerNames.nextElement();
				logger.info(headerName + ": " + request.getHeader(headerName));
			}
		}

		if (method.equals(METHOD_OPTIONS)) {
			doOptions(request, response);
		} else if (method.equals(METHOD_PROPFIND)) {
			doPropfind(request, response);
		} else if (method.equals(METHOD_LOCK)) {
			doLock(request, response);
		} else if (method.equals(METHOD_UNLOCK)) {
			doUnlock(request, response);
		} else if (method.equals(METHOD_GET)) {
			doGet(request, response);
		} else if (method.equals(METHOD_PUT)) {
			doPut(request, response);
		} else if (method.equals(METHOD_HEAD)) {
			doHead(request, response);
		} else {
			super.service(request, response);
		}
	}
  
	/**
	 * OPTIONS Method.
	 */
	protected void doOptions(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.addHeader("DAV", "1,2");
		response.addHeader("Allow", ALLOWED_METHODS);
		response.addHeader("MS-Author-Via", "DAV");
	}

	protected abstract void doPropfind(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException;

	protected abstract void doLock(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException;

	protected abstract void doUnlock(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException;

	protected abstract void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException;

	protected abstract void doPut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException;

	protected abstract void doHead(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException;

  
	/**
	 * Show HTTP header information.
	 */
	protected void showRequestInfo(HttpServletRequest request) {

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
			//path = path.substring(path.indexOf('/', 1));
			path = path.substring(path.lastIndexOf('/'));
		}
		
		if (logger.isInfoEnabled()) {
			logger.info("Path relativo: " + request.getPathInfo() + " => " + path);
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

		if (logger.isInfoEnabled()) {
			logger.info("Path normalizado: " + path + " => " + normalized);
		}

		return (normalized);
	}

	/**
	 * Get creation date in ISO format.
	 */
	protected String getISOCreationDate(long date) {
		return creationDateFormat.format(new Date(date));
	}

	protected String getUpdateDate(long date) {
		return expireDateFormat.format(new Date(date));
	}

	/**
	 * URL rewriter.
	 *
	 * @param path Path which has to be rewiten
	 */
	protected String rewriteUrl(String path) {
		return urlEncoder.encode(path);
	}

	/**
	 * Generate the namespace declarations.
	 */
	protected String generateNamespaceDeclarations() {
		return " xmlns=\"DAV:\"";
	}

	/**
	 * Send a multistatus element containing a complete error report to the
	 * client.
	 *
	 * @param req Servlet request
	 * @param resp Servlet response
	 * @param errorList List of error to be displayed
	 */
	protected void sendReport(HttpServletRequest request,
			HttpServletResponse response, Hashtable errorList)
			throws ServletException, IOException {

		response.setStatus(WebdavStatus.SC_MULTI_STATUS);

		String absoluteUri = request.getRequestURI();
		String relativePath = getRelativePath(request);

		XMLWriter generatedXML = new XMLWriter();
		generatedXML.writeXMLHeader();

		generatedXML.writeElement(null, "multistatus"
				+ generateNamespaceDeclarations(), XMLWriter.OPENING);

		Enumeration pathList = errorList.keys();
		while (pathList.hasMoreElements()) {

			String errorPath = (String) pathList.nextElement();
			int errorCode = ((Integer) errorList.get(errorPath)).intValue();

			generatedXML.writeElement(null, "response", XMLWriter.OPENING);

			generatedXML.writeElement(null, "href", XMLWriter.OPENING);
			String toAppend = errorPath.substring(relativePath.length());
			if (!toAppend.startsWith("/")) {
				toAppend = "/" + toAppend;
			}
			generatedXML.writeText(absoluteUri + toAppend);
			generatedXML.writeElement(null, "href", XMLWriter.CLOSING);
			generatedXML.writeElement(null, "status", XMLWriter.OPENING);
			generatedXML.writeText("HTTP/1.1 " + errorCode + " "
					+ WebdavStatus.getStatusText(errorCode));
			generatedXML.writeElement(null, "status", XMLWriter.CLOSING);

			generatedXML.writeElement(null, "response", XMLWriter.CLOSING);
		}

		generatedXML.writeElement(null, "multistatus", XMLWriter.CLOSING);

		Writer writer = response.getWriter();
		writer.write(generatedXML.toString());
		writer.close();
	}

	/**
	 * Return JAXP document builder instance.
	 */
	protected DocumentBuilder getDocumentBuilder() throws ServletException {
		DocumentBuilder documentBuilder = null;
		try {
			documentBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			logger.error(e.getMessage());
			throw new ServletException(e.getMessage());
		}
		return documentBuilder;
	}

	/**
	 * Propfind helper method.
	 *
	 * @param resources Resources object associated with this context
	 * @param generatedXML XML response to the Propfind request
	 * @param path Path of the current resource
	 * @param type Propfind type
	 * @param propertiesVector If the propfind type is find properties by
	 * name, then this Vector contains those properties
	 */
	protected void parseProperties(HttpServletRequest request,
			long creationDate, long updateDate, long length,
			String contentType, XMLWriter generatedXML, String path, int type,
			Vector propertiesVector, String sETag) {

		generatedXML.writeElement(null, "response", XMLWriter.OPENING);
		String status = new String("HTTP/1.1 " + WebdavStatus.SC_OK + " "
				+ WebdavStatus.getStatusText(WebdavStatus.SC_OK));

		// Generating href element
		generatedXML.writeElement(null, "href", XMLWriter.OPENING);

		String href = request.getContextPath();
		if ((href.endsWith("/")) && (path.startsWith("/"))) {
			href += path.substring(1);
		} else {
			href += path;
		}

		generatedXML.writeText(rewriteUrl(href));

		generatedXML.writeElement(null, "href", XMLWriter.CLOSING);

		String resourceName = path;
		int lastSlash = path.lastIndexOf('/');
		if (lastSlash != -1) {
			resourceName = resourceName.substring(lastSlash + 1);
		}

		switch (type) {

		case FIND_ALL_PROP:

			generatedXML.writeElement(null, "propstat", XMLWriter.OPENING);
			generatedXML.writeElement(null, "prop", XMLWriter.OPENING);

			generatedXML.writeProperty(null, "creationdate",
					getISOCreationDate(creationDate));
			generatedXML.writeElement(null, "displayname", XMLWriter.OPENING);
			generatedXML.writeData(resourceName);
			generatedXML.writeElement(null, "displayname", XMLWriter.CLOSING);
			generatedXML.writeProperty(null, "getcontentlanguage", Locale
					.getDefault().toString());
			generatedXML.writeProperty(null, "getlastmodified",
					getUpdateDate(updateDate));
			generatedXML.writeProperty(null, "getcontentlength", String
					.valueOf(length));
			generatedXML.writeProperty(null, "getcontenttype", contentType);
			generatedXML.writeProperty(null, "getetag", sETag);
			generatedXML.writeElement(null, "resourcetype",
					XMLWriter.NO_CONTENT);

			generatedXML.writeProperty(null, "source", "");

			String supportedLocks = "<lockentry>"
					+ "<lockscope><exclusive/></lockscope>"
					+ "<locktype><write/></locktype>" + "</lockentry>"
					+ "<lockentry>" + "<lockscope><shared/></lockscope>"
					+ "<locktype><write/></locktype>" + "</lockentry>";
			generatedXML.writeElement(null, "supportedlock", XMLWriter.OPENING);
			generatedXML.writeText(supportedLocks);
			generatedXML.writeElement(null, "supportedlock", XMLWriter.CLOSING);
			generatedXML.writeElement(null, "prop", XMLWriter.CLOSING);
			generatedXML.writeElement(null, "status", XMLWriter.OPENING);
			generatedXML.writeText(status);
			generatedXML.writeElement(null, "status", XMLWriter.CLOSING);
			generatedXML.writeElement(null, "propstat", XMLWriter.CLOSING);

			break;

		case FIND_PROPERTY_NAMES:

			generatedXML.writeElement(null, "propstat", XMLWriter.OPENING);
			generatedXML.writeElement(null, "prop", XMLWriter.OPENING);

			generatedXML.writeElement(null, "creationdate",
					XMLWriter.NO_CONTENT);
			generatedXML
					.writeElement(null, "displayname", XMLWriter.NO_CONTENT);
			generatedXML.writeElement(null, "getcontentlanguage",
					XMLWriter.NO_CONTENT);
			generatedXML.writeElement(null, "getcontentlength",
					XMLWriter.NO_CONTENT);
			generatedXML.writeElement(null, "getcontenttype",
					XMLWriter.NO_CONTENT);
			generatedXML.writeElement(null, "getetag", XMLWriter.NO_CONTENT);
			generatedXML.writeElement(null, "getlastmodified",
					XMLWriter.NO_CONTENT);
//			generatedXML.writeElement(null, "resourcetype",
//					XMLWriter.NO_CONTENT);
			if ("/".equals(path)) {
				generatedXML.writeElement(null, "resourcetype",
						XMLWriter.OPENING);
				generatedXML.writeElement(null, "collection",
						XMLWriter.NO_CONTENT);
				generatedXML.writeElement(null, "resourcetype",
						XMLWriter.CLOSING);
			} else {
				generatedXML.writeElement(null, "resourcetype",
						XMLWriter.NO_CONTENT);
			}
			generatedXML.writeElement(null, "source", XMLWriter.NO_CONTENT);
			generatedXML.writeElement(null, "lockdiscovery",
					XMLWriter.NO_CONTENT);

			generatedXML.writeElement(null, "prop", XMLWriter.CLOSING);
			generatedXML.writeElement(null, "status", XMLWriter.OPENING);
			generatedXML.writeText(status);
			generatedXML.writeElement(null, "status", XMLWriter.CLOSING);
			generatedXML.writeElement(null, "propstat", XMLWriter.CLOSING);

			break;

		case FIND_BY_PROPERTY:

			Vector propertiesNotFound = new Vector();

			// Parse the list of properties

			generatedXML.writeElement(null, "propstat", XMLWriter.OPENING);
			generatedXML.writeElement(null, "prop", XMLWriter.OPENING);

			Enumeration properties = propertiesVector.elements();

			while (properties.hasMoreElements()) {

				String property = (String) properties.nextElement();

				if (property.equals("creationdate")) {
					generatedXML.writeProperty(null, "creationdate",
							getISOCreationDate(creationDate));
				} else if (property.equals("displayname")) {
					generatedXML.writeElement(null, "displayname",
							XMLWriter.OPENING);
					generatedXML.writeData(resourceName);
					generatedXML.writeElement(null, "displayname",
							XMLWriter.CLOSING);
				} else if (property.equals("getcontentlanguage")) {
					generatedXML.writeProperty(null, "getcontentlanguage",
							Locale.getDefault().toString());
				} else if (property.equals("getcontentlength")) {
					generatedXML.writeProperty(null, "getcontentlength",
							(String.valueOf(length)));
				} else if (property.equals("getcontenttype")) {
					generatedXML.writeProperty(null, "getcontenttype",
							contentType);
				} else if (property.equals("getetag")) {
					generatedXML.writeProperty(null, "getetag", sETag);
				} else if (property.equals("getlastmodified")) {
					generatedXML.writeProperty(null, "getlastmodified",
							getUpdateDate(updateDate));
				} else if (property.equals("resourcetype")) {
//					generatedXML.writeElement(null, "resourcetype",
//							XMLWriter.NO_CONTENT);
					if ("/".equals(path)) {
						generatedXML.writeElement(null, "resourcetype",
								XMLWriter.OPENING);
						generatedXML.writeElement(null, "collection",
								XMLWriter.NO_CONTENT);
						generatedXML.writeElement(null, "resourcetype",
								XMLWriter.CLOSING);
					} else {
						generatedXML.writeElement(null, "resourcetype",
								XMLWriter.NO_CONTENT);
					}
				} else if (property.equals("source")) {
					generatedXML.writeProperty(null, "source", "");
				} else if (property.equals("supportedlock")) {
					supportedLocks = "<lockentry>"
							+ "<lockscope><exclusive/></lockscope>"
							+ "<locktype><write/></locktype>" + "</lockentry>"
							+ "<lockentry>"
							+ "<lockscope><shared/></lockscope>"
							+ "<locktype><write/></locktype>" + "</lockentry>";
					generatedXML.writeElement(null, "supportedlock",
							XMLWriter.OPENING);
					generatedXML.writeText(supportedLocks);
					generatedXML.writeElement(null, "supportedlock",
							XMLWriter.CLOSING);
				} else if (property.equals("lockdiscovery")) {
					propertiesNotFound.addElement(property);
				} else {
					propertiesNotFound.addElement(property);
				}
			}

			generatedXML.writeElement(null, "prop", XMLWriter.CLOSING);
			generatedXML.writeElement(null, "status", XMLWriter.OPENING);
			generatedXML.writeText(status);
			generatedXML.writeElement(null, "status", XMLWriter.CLOSING);
			generatedXML.writeElement(null, "propstat", XMLWriter.CLOSING);

			Enumeration propertiesNotFoundList = propertiesNotFound.elements();

			if (propertiesNotFoundList.hasMoreElements()) {

				status = new String("HTTP/1.1 " + WebdavStatus.SC_NOT_FOUND
						+ " "
						+ WebdavStatus.getStatusText(WebdavStatus.SC_NOT_FOUND));

				generatedXML.writeElement(null, "propstat", XMLWriter.OPENING);
				generatedXML.writeElement(null, "prop", XMLWriter.OPENING);

				while (propertiesNotFoundList.hasMoreElements())
					generatedXML.writeElement(null,
							(String) propertiesNotFoundList.nextElement(),
							XMLWriter.NO_CONTENT);

				generatedXML.writeElement(null, "prop", XMLWriter.CLOSING);
				generatedXML.writeElement(null, "status", XMLWriter.OPENING);
				generatedXML.writeText(status);
				generatedXML.writeElement(null, "status", XMLWriter.CLOSING);
				generatedXML.writeElement(null, "propstat", XMLWriter.CLOSING);

			}
			break;
		}
		generatedXML.writeElement(null, "response", XMLWriter.CLOSING);
	}

	/**
	 * Holds a lock information.
	 */
	protected class LockInfo {

		/**
		 * Constructor.
		 *
		 * @param pathname Path name of the file
		 */
		public LockInfo() {
		}

		String path = "/";

		String type = "write";

		String scope = "exclusive";

		int depth = 0;

		String owner = "";

		String token = "";

		long expiresAt = 0;

		Date creationDate = new Date();

		/**
		 * Get a String representation of this lock token.
		 */
		public String toString() {

			String result = "Type:" + type + "\n";
			result += "Scope:" + scope + "\n";
			result += "Depth:" + depth + "\n";
			result += "Owner:" + owner + "\n";
			result += "Expiration:"
					+ expireDateFormat.format(new Date(expiresAt)) + "\n";
			result += "Token:" + token + "\n";
			return result;
		}

		/**
		 * Return true if the lock has expired.
		 */
		public boolean hasExpired() {
			return (System.currentTimeMillis() > expiresAt);
		}

		/**
		 * Return true if the lock is exclusive.
		 */
		public boolean isExclusive() {
			return (scope.equals("exclusive"));
		}

		/**
		 * Get an XML representation of this lock token. This method will
		 * append an XML fragment to the given XML writer.
		 */
		public void toXML(XMLWriter generatedXML) {
			toXML(generatedXML, false);
		}

		/**
		 * Get an XML representation of this lock token. This method will
		 * append an XML fragment to the given XML writer.
		 */
		public void toXML(XMLWriter generatedXML, boolean showToken) {

			generatedXML.writeElement(null, "activelock", XMLWriter.OPENING);

			generatedXML.writeElement(null, "locktype", XMLWriter.OPENING);
			generatedXML.writeElement(null, type, XMLWriter.NO_CONTENT);
			generatedXML.writeElement(null, "locktype", XMLWriter.CLOSING);

			generatedXML.writeElement(null, "lockscope", XMLWriter.OPENING);
			generatedXML.writeElement(null, scope, XMLWriter.NO_CONTENT);
			generatedXML.writeElement(null, "lockscope", XMLWriter.CLOSING);

			generatedXML.writeElement(null, "depth", XMLWriter.OPENING);
			if (depth == INFINITY)
				generatedXML.writeText("Infinity");
			else
				generatedXML.writeText("0");
			generatedXML.writeElement(null, "depth", XMLWriter.CLOSING);

			generatedXML.writeElement(null, "owner", XMLWriter.OPENING);
			generatedXML.writeText(owner);
			generatedXML.writeElement(null, "owner", XMLWriter.CLOSING);

			generatedXML.writeElement(null, "timeout", XMLWriter.OPENING);
			long timeout = (expiresAt - System.currentTimeMillis()) / 1000;
			generatedXML.writeText("Second-" + timeout);
			generatedXML.writeElement(null, "timeout", XMLWriter.CLOSING);

			generatedXML.writeElement(null, "locktoken", XMLWriter.OPENING);
			if (showToken) {
				generatedXML.writeElement(null, "href", XMLWriter.OPENING);
				generatedXML.writeText("opaquelocktoken:" + token);
				generatedXML.writeElement(null, "href", XMLWriter.CLOSING);
			} else {
				generatedXML.writeElement(null, "href", XMLWriter.OPENING);
				generatedXML.writeText("opaquelocktoken:dummytoken");
				generatedXML.writeElement(null, "href", XMLWriter.CLOSING);
			}
			generatedXML.writeElement(null, "locktoken", XMLWriter.CLOSING);

			generatedXML.writeElement(null, "activelock", XMLWriter.CLOSING);
		}
	}
}
