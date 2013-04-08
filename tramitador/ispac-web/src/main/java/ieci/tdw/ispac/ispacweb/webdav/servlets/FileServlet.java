package ieci.tdw.ispac.ispacweb.webdav.servlets;

import ieci.tdw.ispac.ispaclib.util.FileDirContext;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.util.Resource;
import ieci.tdw.ispac.ispaclib.util.ResourceAttributes;
import ieci.tdw.ispac.ispacweb.webdav.util.DOMWriter;
import ieci.tdw.ispac.ispacweb.webdav.util.XMLWriter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.Vector;

import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class FileServlet extends BaseServlet {

	private static Logger logger = Logger.getLogger(FileServlet.class);

	/**
	 * Filesystem Directory Context
	 */
	private static FileDirContext mContext = null;

	/**
	 * Initialize this servlet.
	 */
	public void init(ServletConfig config) throws ServletException {

		super.init(config);

		try {
			ISPACConfiguration parameters = ISPACConfiguration.getInstance();

			String sPath = parameters.get(ISPACConfiguration.TEMPORARY_PATH);
			if (sPath != null) {
				if (sPath.endsWith("/")) {
					sPath = sPath.substring(0, sPath.length() - 1);
				}

				mContext = new FileDirContext();
				mContext.setDocBase(sPath);
			}
		} catch (Throwable e) {
		}
	}

	/**
	 * PROPFIND Method.
	 */
	protected void doPropfind(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		FileDirContext context = getContext(request);
		if (context == null) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		String path = getRelativePath(request);
		ResourceInfo resourceInfo = new ResourceInfo(path, context);
		if (!resourceInfo.exists) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, path);
			return;
		}

		int type = FIND_ALL_PROP;

		//String depthStr = request.getHeader("Depth");

		// Properties which are to be displayed.
		Vector properties = null;

		Node propNode = null;

		DocumentBuilder documentBuilder = getDocumentBuilder();

		try {
			Document document = documentBuilder.parse(new InputSource(request
					.getInputStream()));

			// Get the root element of the document
			Element rootElement = document.getDocumentElement();
			NodeList childList = rootElement.getChildNodes();

			for (int i = 0; i < childList.getLength(); i++) {
				Node currentNode = childList.item(i);
				switch (currentNode.getNodeType()) {
				case Node.TEXT_NODE:
					break;
				case Node.ELEMENT_NODE:
					if (currentNode.getNodeName().endsWith("prop")) {
						type = FIND_BY_PROPERTY;
						propNode = currentNode;
					}
					if (currentNode.getNodeName().endsWith("propname")) {
						type = FIND_PROPERTY_NAMES;
					}
					if (currentNode.getNodeName().endsWith("allprop")) {
						type = FIND_ALL_PROP;
					}
					break;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		if (type == FIND_BY_PROPERTY) {
			properties = new Vector();
			NodeList childList = propNode.getChildNodes();

			for (int i = 0; i < childList.getLength(); i++) {
				Node currentNode = childList.item(i);
				switch (currentNode.getNodeType()) {
				case Node.TEXT_NODE:
					break;
				case Node.ELEMENT_NODE:
					String nodeName = currentNode.getNodeName();
					String propertyName = null;
					if (nodeName.indexOf(':') != -1) {
						propertyName = nodeName
								.substring(nodeName.indexOf(':') + 1);
					} else {
						propertyName = nodeName;
					}
					// href is a live property which is handled differently
					properties.addElement(propertyName);
					break;
				}
			}
		}

		response.setStatus(WebdavStatus.SC_MULTI_STATUS);

		response.setContentType("text/xml; charset=UTF-8");

		// Create multistatus object
		XMLWriter generatedXML = new XMLWriter(response.getWriter());
		generatedXML.writeXMLHeader();

		generatedXML.writeElement(null, "multistatus"
				+ generateNamespaceDeclarations(), XMLWriter.OPENING);

		parseProperties(request, resourceInfo.creationDate,
				resourceInfo.updateDate, resourceInfo.length,
				resourceInfo.contentType, generatedXML, path, type, properties,
				getETag(resourceInfo));

		generatedXML.writeElement(null, "multistatus", XMLWriter.CLOSING);

		generatedXML.sendData();
	}

	/**
	 * LOCK Method.
	 */
	protected void doLock(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		FileDirContext context = getContext(request);
		if (context == null) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		String path = getRelativePath(request);
		//ResourceInfo resourceInfo = new ResourceInfo( path, context);

		LockInfo lock = new LockInfo();

		lock.path = path;

		// Parsing depth header

		//String depthStr = request.getHeader("Depth");

		lock.depth = 0;

		// Parsing timeout header

		int lockDuration = DEFAULT_TIMEOUT;
		String lockDurationStr = request.getHeader("Timeout");
		if (lockDurationStr == null) {
			lockDuration = DEFAULT_TIMEOUT;
		} else {
			if (lockDurationStr.startsWith("Second-")) {
				lockDuration = (new Integer(lockDurationStr.substring(7)))
						.intValue();
			} else {
				if (lockDurationStr.equalsIgnoreCase("infinity")) {
					lockDuration = MAX_TIMEOUT;
				} else {
					try {
						lockDuration = (new Integer(lockDurationStr))
								.intValue();
					} catch (NumberFormatException e) {
						lockDuration = MAX_TIMEOUT;
					}
				}
			}
			if (lockDuration == 0) {
				lockDuration = DEFAULT_TIMEOUT;
			}
			if (lockDuration > MAX_TIMEOUT) {
				lockDuration = MAX_TIMEOUT;
			}
		}

		lock.expiresAt = System.currentTimeMillis() + (lockDuration * 1000);

		int lockRequestType = LOCK_CREATION;

		Node lockInfoNode = null;

		DocumentBuilder documentBuilder = getDocumentBuilder();

		try {
			Document document = documentBuilder.parse(new InputSource(request
					.getInputStream()));

			// Get the root element of the document
			Element rootElement = document.getDocumentElement();
			lockInfoNode = rootElement;
		} catch (Exception e) {
			lockRequestType = LOCK_REFRESH;
		}

		if (lockInfoNode != null) {
			// Reading lock information
			NodeList childList = lockInfoNode.getChildNodes();
			StringWriter strWriter = null;
			DOMWriter domWriter = null;

			Node lockScopeNode = null;
			Node lockTypeNode = null;
			Node lockOwnerNode = null;

			for (int i = 0; i < childList.getLength(); i++) {
				Node currentNode = childList.item(i);
				switch (currentNode.getNodeType()) {
				case Node.TEXT_NODE:
					break;
				case Node.ELEMENT_NODE:
					String nodeName = currentNode.getNodeName();
					if (nodeName.endsWith("lockscope")) {
						lockScopeNode = currentNode;
					}
					if (nodeName.endsWith("locktype")) {
						lockTypeNode = currentNode;
					}
					if (nodeName.endsWith("owner")) {
						lockOwnerNode = currentNode;
					}
					break;
				}
			}
			if (lockScopeNode != null) {
				childList = lockScopeNode.getChildNodes();
				for (int i = 0; i < childList.getLength(); i++) {
					Node currentNode = childList.item(i);
					switch (currentNode.getNodeType()) {
					case Node.TEXT_NODE:
						break;
					case Node.ELEMENT_NODE:
						String tempScope = currentNode.getNodeName();
						if (tempScope.indexOf(':') != -1) {
							lock.scope = tempScope.substring(tempScope
									.indexOf(':') + 1);
						} else {
							lock.scope = tempScope;
						}
						break;
					}
				}
				if (lock.scope == null) {
					response.setStatus(WebdavStatus.SC_BAD_REQUEST);
				}
			} else {
				response.setStatus(WebdavStatus.SC_BAD_REQUEST);
			}
			if (lockTypeNode != null) {
				childList = lockTypeNode.getChildNodes();
				for (int i = 0; i < childList.getLength(); i++) {
					Node currentNode = childList.item(i);
					switch (currentNode.getNodeType()) {
					case Node.TEXT_NODE:
						break;
					case Node.ELEMENT_NODE:
						String tempType = currentNode.getNodeName();
						if (tempType.indexOf(':') != -1) {
							lock.type = tempType.substring(tempType
									.indexOf(':') + 1);
						} else {
							lock.type = tempType;
						}
						break;
					}
				}
				if (lock.type == null) {
					response.setStatus(WebdavStatus.SC_BAD_REQUEST);
				}
			} else {
				response.setStatus(WebdavStatus.SC_BAD_REQUEST);
			}
			if (lockOwnerNode != null) {
				childList = lockOwnerNode.getChildNodes();
				for (int i = 0; i < childList.getLength(); i++) {
					Node currentNode = childList.item(i);
					switch (currentNode.getNodeType()) {
					case Node.TEXT_NODE:
						lock.owner += currentNode.getNodeValue();
						break;
					case Node.ELEMENT_NODE:
						strWriter = new StringWriter();
						domWriter = new DOMWriter(strWriter, true);
						domWriter.print(currentNode);
						lock.owner += strWriter.toString();
						break;
					}
				}
				if (lock.owner == null) {
					response.setStatus(WebdavStatus.SC_BAD_REQUEST);
				}
			} else {
				lock.owner = new String();
			}
		}

		// Generating lock id
		String lockTokenStr = request.getServletPath() + "-" + lock.type + "-"
				+ lock.scope + "-" + lock.depth + "-" + lock.owner + "-"
				+ lock.expiresAt + "-" + System.currentTimeMillis() + "-"
				+ secret;
		String lockToken = md5Encoder.encode(md5Helper.digest(lockTokenStr
				.getBytes()));
		lock.token = lockToken;

		if (lockRequestType == LOCK_CREATION) {
			if (lock.isExclusive()) {
				if (resourceLocks.containsKey(lock.path)) {
					LockInfo lck = (LockInfo) resourceLocks.get(lock.path);
					if (lck.expiresAt > System.currentTimeMillis()) {
						response.sendError(WebdavStatus.SC_LOCKED);
						return;
					}
				}

				resourceLocks.put(lock.path, lock);
			}
		}

		if (lockRequestType == LOCK_REFRESH) {
			resourceLocks.put(lock.path, lock);
		}

		// Set the status, then generate the XML response containing
		// the lock information
		XMLWriter generatedXML = new XMLWriter();
		generatedXML.writeXMLHeader();
		generatedXML.writeElement(null, "prop"
				+ generateNamespaceDeclarations(), XMLWriter.OPENING);
		generatedXML.writeElement(null, "lockdiscovery", XMLWriter.OPENING);
		lock.toXML(generatedXML, true);
		generatedXML.writeElement(null, "lockdiscovery", XMLWriter.CLOSING);
		generatedXML.writeElement(null, "prop", XMLWriter.CLOSING);
		response.setStatus(WebdavStatus.SC_OK);
		response.setContentType("text/xml; charset=UTF-8");
		Writer writer = response.getWriter();
		writer.write(generatedXML.toString());
		writer.close();
	}

	/**
	 * UNLOCK Method.
	 */
	protected void doUnlock(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		FileDirContext context = getContext(request);
		if (context == null) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		String path = getRelativePath(request);
		//ResourceInfo resourceInfo = new ResourceInfo( path, context);

		String lockTokenHeader = request.getHeader("Lock-Token");
		if (lockTokenHeader == null)
			lockTokenHeader = "";

		if (resourceLocks.containsKey(path)) {
			resourceLocks.remove(path);
		}

		response.setStatus(WebdavStatus.SC_NO_CONTENT);
	}

	/**
	 * Process a GET request for the specified resource.
	 *
	 * @param request The servlet request we are processing
	 * @param response The servlet response we are creating
	 *
	 * @exception IOException if an input/output error occurs
	 * @exception ServletException if a servlet-specified error occurs
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		FileDirContext context = getContext(request);
		if (context == null) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		String path = getRelativePath(request);
		ResourceInfo resourceInfo = new ResourceInfo(path, context);
		if (!resourceInfo.exists) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, path);
			return;
		}
		// ETag header
		response.setHeader("ETag", getETag(resourceInfo));
		// Last-Modified header
		response.setHeader("Last-Modified",
				getUpdateDate(resourceInfo.updateDate));
		// Set the appropriate content type
		if (resourceInfo.contentType != null) {
			response.setContentType(resourceInfo.contentType);
		}
		// Obtiene el documento
		if (resourceInfo.length >= 0) {
			try {
				response.setBufferSize(output);
			} catch (IllegalStateException e) {
			}
			try {
				response.setContentLength((int) resourceInfo.length);
				ServletOutputStream out = response.getOutputStream();
				copy(resourceInfo, out);
				response.setStatus(HttpServletResponse.SC_OK);
			} catch (IOException e) {
				logger.error(e.getMessage());
				response.sendError(HttpServletResponse.SC_CONFLICT);
			}
			/*
			 finally {
			 
			 // Borrar el fichero temporal
			 try {
			 FileTemporaryManager fileTemporaryManager = FileTemporaryManager.getInstance(); 
			 fileTemporaryManager.delete(path);
			 }
			 catch (ISPACException e) {
			 }
			 }
			 */
		}
	}

	/**
	 * Process a HEAD request for the specified resource.
	 *
	 * @param request The servlet request we are processing
	 * @param response The servlet response we are creating
	 *
	 * @exception IOException if an input/output error occurs
	 * @exception ServletException if a servlet-specified error occurs
	 */
	protected void doHead(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		FileDirContext context = getContext(request);
		if (context == null) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		String path = getRelativePath(request);
		ResourceInfo resourceInfo = new ResourceInfo(path, context);
		if (!resourceInfo.exists) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, path);
			return;
		}
		
		// ETag header
		response.setHeader("ETag", getETag(resourceInfo));
		
		// Last-Modified header
		response.setHeader("Last-Modified", getUpdateDate(resourceInfo.updateDate));

		// Set the appropriate content type
		if (resourceInfo.contentType != null) {
			response.setContentType(resourceInfo.contentType);
		}

		response.setStatus(HttpServletResponse.SC_OK);
	}

	/**
	 * Process a PUT request for the specified resource.
	 *
	 * @param request The servlet request we are processing
	 * @param response The servlet response we are creating
	 *
	 * @exception IOException if an input/output error occurs
	 * @exception ServletException if a servlet-specified error occurs
	 */
	protected void doPut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		FileDirContext context = getContext(request);
		if (context == null) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		String path = getRelativePath(request);
		ResourceInfo resourceInfo = new ResourceInfo(path, context);

		try {
			// Salva el documento
			ServletInputStream in = request.getInputStream();

			Resource resource = new Resource(in);

			if (resourceInfo.exists) {
				context.rebind(path, resource);
			} else {
				context.bind(path, resource);
			}

			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		} catch (NamingException e) {
			logger.error(e.getMessage());
			response.sendError(HttpServletResponse.SC_CONFLICT);
		}
	}

	protected FileDirContext getContext(HttpServletRequest request) {

		// Inicializa la sesión con la aplicación
		getSession(request);

		return mContext;
	}

	/**
	 * Get the ETag associated with a file
	 *
	 * @param resourceInfo object
	 * @param strong True if we want a strong ETag, in which case a checksum
	 * of the file has to be calculated
	 */
	protected String getETag(ResourceInfo resourceInfo) {
		if (resourceInfo.strongETag != null) {
			return resourceInfo.strongETag;
		} else if (resourceInfo.weakETag != null) {
			return resourceInfo.weakETag;
		}
		return "W/\"" + resourceInfo.length + "-" + resourceInfo.updateDate
				+ "\"";
	}

	/**
	 * Copy the contents of the specified input stream to the specified
	 * output stream, and ensure that both streams are closed before returning
	 * (even in the face of an exception).
	 *
	 * @param istream The input stream to read from
	 * @param ostream The output stream to write to
	 *
	 * @exception IOException if an input/output error occurs
	 */
	private void copy(ResourceInfo resourceInfo, ServletOutputStream out)
			throws IOException {

		InputStream in = null;

		try {
			in = new BufferedInputStream(resourceInfo.getStream(), input);

			byte buffer[] = new byte[input];
			int len = buffer.length;
			while (true) {
				len = in.read(buffer);
				if (len == -1)
					break;
				out.write(buffer, 0, len);
			}
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException t) {
				logger.error(t.getMessage());
			}
		}
	}

	protected class ResourceInfo {

		/**
		 * Constructor.
		 *
		 * @param pathname Path name of the file
		 */
		public ResourceInfo(String path, FileDirContext context) {
			set(path, context);
		}

		public FileDirContext context;

		public Resource resource;

		public ResourceAttributes attributes;

		public String path;

		public String contentType;

		public long creationDate;

		public long updateDate;

		public long length;

		public String weakETag;

		public String strongETag;

		public boolean exists;

		public void recycle() {
			context = null;
			resource = null;
			attributes = null;
			path = null;
			contentType = null;
			creationDate = 0;
			updateDate = 0;
			length = -1;
			exists = false;
			weakETag = null;
			strongETag = null;
		}

		public void set(String path, FileDirContext context) {
			recycle();

			this.path = path;
			this.context = context;
			this.contentType = getServletContext().getMimeType(path);
			try {
				Object object = context.lookup(path);
				if (object instanceof Resource) {
					exists = true;
					resource = (Resource) object;
					attributes = (ResourceAttributes) context
							.getAttributes(path);
					Date date = attributes.getCreationDate();
					if (date != null) {
						creationDate = date.getTime();
					}
					date = attributes.getLastModifiedDate();
					if (date != null) {
						updateDate = date.getTime();
					}
					weakETag = attributes.getETag();
					strongETag = attributes.getETag(true);
					length = attributes.getContentLength();
				} else {
					// Directorio raíz
					exists = true;
				}
			} catch (NamingException e) {
				logger.error(e.getMessage());
				exists = false;
			}
		}

		public InputStream getStream() throws IOException {
			return resource.streamContent();
		}
	}

}