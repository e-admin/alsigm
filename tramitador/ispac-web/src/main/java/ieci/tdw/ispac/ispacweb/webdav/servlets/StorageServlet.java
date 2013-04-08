package ieci.tdw.ispac.ispacweb.webdav.servlets;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IWorklistAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.api.item.ITask;
import ieci.tdw.ispac.audit.business.vo.AuditContext;
import ieci.tdw.ispac.audit.context.AuditContextHolder;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.webdav.util.DOMWriter;
import ieci.tdw.ispac.ispacweb.webdav.util.XMLWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class StorageServlet extends BaseServlet {
  
	/** serialVersionUID */
	private static final long serialVersionUID = 8065720387410213915L;

	/**
	 * PROPFIND Method.
	 */
	protected void doPropfind(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		SessionAPI session = getSession(request);
		if (session == null) {
			logger.error("No se ha podido obtener la sesión");
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		ResourceInfo resourceInfo = new ResourceInfo(request, session);
		if (!resourceInfo.authorized) {
			logger.error("No se tiene autorización para editar el documento: " + resourceInfo.path);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, resourceInfo.path);
			return;
		}
		if (!resourceInfo.exists) {
			logger.error("No se ha encontrado el documento: " + resourceInfo.path);
			response.sendError(HttpServletResponse.SC_NOT_FOUND, resourceInfo.path);
			return;
		}

		int type = FIND_ALL_PROP;

		//String depthStr = request.getHeader("Depth");

		// Properties which are to be displayed.
		Vector<String> properties = null;

		Node propNode = null;

		DocumentBuilder documentBuilder = getDocumentBuilder();

		try {

			Document document = documentBuilder.parse(new InputSource(
				request.getInputStream()));
			
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
		}

		if (type == FIND_BY_PROPERTY) {
			properties = new Vector<String>();
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
				resourceInfo.contentType, generatedXML, resourceInfo.path, type, properties,
				getETag(resourceInfo));

		generatedXML.writeElement(null, "multistatus", XMLWriter.CLOSING);

		generatedXML.sendData();
	}

	/**
	 * LOCK Method.
	 */
	protected void doLock(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Obtiene la conexión
		SessionAPI session = getSession(request);
		if (session == null) {
			logger.error("No se ha podido obtener la sesión");
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		ResourceInfo resourceInfo = new ResourceInfo(request, session);
		if (!resourceInfo.authorized) {
			logger.error("No se tiene autorización para editar el documento: " + resourceInfo.path);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, resourceInfo.path);
			return;
		}

		LockInfo lock = new LockInfo();

		lock.path = resourceInfo.path;

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
			Document document = documentBuilder.parse(new InputSource(
					request.getInputStream()));

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
							lock.scope = tempScope.substring(
									tempScope.indexOf(':') + 1);
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
							lock.type = tempType.substring(
									tempType.indexOf(':') + 1);
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
				try {
					session.lockDocument(resourceInfo.document);
				} catch (ISPACException e) {
					logger.error("Error al bloquear el documento", e);
					response.sendError(WebdavStatus.SC_LOCKED);
					return;
				}
				/*
				 if (resourceLocks.containsKey(lock.path)) 
				 {
				 LockInfo lck = (LockInfo) resourceLocks.get(lock.path);
				 if ( lck.expiresAt > System.currentTimeMillis())
				 {
				 response.sendError(WebdavStatus.SC_LOCKED);
				 return;
				 }
				 }
				 
				 resourceLocks.put(lock.path, lock);
				 */
			}
		}

		if (lockRequestType == LOCK_REFRESH) {
			//resourceLocks.put(lock.path, lock);
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

		// Obtiene la conexión
		SessionAPI session = getSession(request);
		if (session == null) {
			logger.error("No se ha podido obtener la sesión");
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		ResourceInfo resourceInfo = new ResourceInfo(request, session);
		if (!resourceInfo.authorized) {
			logger.error("No se tiene autorización para editar el documento: " + resourceInfo.path);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, resourceInfo.path);
			return;
		}

		String lockTokenHeader = request.getHeader("Lock-Token");
		if (lockTokenHeader == null)
			lockTokenHeader = "";
		/*
		 if (resourceLocks.containsKey(path))
		 {
		 resourceLocks.remove(path);
		 }
		 */
		try {
			session.unlockDocument(resourceInfo.document);
		} catch (ISPACException e) {
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

		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "No-cache");

		SessionAPI session = getSession(request);
		if (session == null) {
			logger.error("No se ha podido obtener la sesión");
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		//Auditoría: Añadir en el ThreadLocal el objeto AuditContext.
		auditContext(request,session);
				
		ResourceInfo resourceInfo = new ResourceInfo(request, session);
		if (!resourceInfo.authorized) {
			logger.error("No se tiene autorización para editar el documento: " + resourceInfo.path);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, resourceInfo.path);
			return;
		}
		if (!resourceInfo.exists) {
			logger.error("No se ha encontrado el documento: " + resourceInfo.path);
			response.sendError(HttpServletResponse.SC_NOT_FOUND, resourceInfo.path);
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
				response.setContentLength(resourceInfo.length);
				ServletOutputStream out = response.getOutputStream();
				IGenDocAPI genDocAPI = session.getAPI().getGenDocAPI();
				Object connectorSession = null;
				try {
					connectorSession = genDocAPI.createConnectorSession();
					genDocAPI.getDocument(connectorSession, resourceInfo.documentReference, out);
				}finally {
					if (connectorSession != null) {
						genDocAPI.closeConnectorSession(connectorSession);
					}
		    	}		
				
				response.setStatus(HttpServletResponse.SC_OK);
			} catch (ISPACException e) {
				logger.error(e.getMessage());
				response.sendError(HttpServletResponse.SC_CONFLICT);
			}
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

		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "No-cache");

		SessionAPI session = getSession(request);
		if (session == null) {
			logger.error("No se ha podido obtener la sesión");
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		ResourceInfo resourceInfo = new ResourceInfo(request, session);
		if (!resourceInfo.authorized) {
			logger.error("No se tiene autorización para editar el documento: " + resourceInfo.path);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, resourceInfo.path);
			return;
		}
		if (!resourceInfo.exists) {
			logger.error("No se ha encontrado el documento: " + resourceInfo.path);
			response.sendError(HttpServletResponse.SC_NOT_FOUND, resourceInfo.path);
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

		// Obtiene la conexión
		SessionAPI session = getSession(request);
		if (session == null) {
			logger.error("No se ha podido obtener la sesión");
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		ResourceInfo resourceInfo = new ResourceInfo(request, session);
		if (!resourceInfo.authorized) {
			logger.error("No se tiene autorización para editar el documento: " + resourceInfo.path);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, resourceInfo.path);
			return;
		}

		try {
			// Salva el documento
			ServletInputStream in = request.getInputStream();
			IGenDocAPI document = session.getAPI().getGenDocAPI();
			int length = request.getContentLength();
			//      document.setDocument( resourceInfo.documentReference, in, length, "application/msword");

			//***************MODIFICACION PARA EXCEL****************//

			Object connectorSession = null;
			try {
				auditContext(request, session);
				connectorSession = document.createConnectorSession();
				String mime = document.getMimeType(connectorSession, resourceInfo.documentReference);
				document.setDocument(connectorSession, resourceInfo.document,resourceInfo.documentReference, in, length,
						mime);
				// Auditoría: Añadir en el ThreadLocal el objeto AuditContext.
				auditContext(request, session);
				
				//      int count = request.getContentLength();
			}finally {
				if (connectorSession != null) {
					document.closeConnectorSession(connectorSession);
				}
	    	}		
			

			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		} catch (ISPACException e) {
			logger.error(e.getMessage());
			response.sendError(HttpServletResponse.SC_CONFLICT);
		}
	}

	/**
	 * Get the ETag associated with a document.
	 *
	 * @param resourceInfo object
	 * @param strong True if we want a strong ETag, in which case a checksum
	 * of the file has to be calculated
	 */
	protected String getETag(ResourceInfo resourceInfo) {
		return new StringBuffer("W/\"").append(resourceInfo.length)
			.append("-").append(resourceInfo.documentReference)
			.append("\"").toString();
	}

	protected class ResourceInfo {

		/**
		 * Constructor.
		 *
		 * @param pathname Path name of the file
		 */
		public ResourceInfo(HttpServletRequest request, SessionAPI session) {
			set(request, session);
		}

		public SessionAPI session;

		public String path;

		public String contentType;

		public long creationDate;

		public long updateDate;

		public int length;

		public int document;

		public String documentReference;

		public boolean exists;
		
		public boolean authorized;

		public void recycle() {
			session = null;
			path = null;
			contentType = null;
			creationDate = 0;
			updateDate = 0;
			length = -1;
			document = -1;
			exists = false;
			authorized = false;
			documentReference = new String();
		}

		public void set(HttpServletRequest request, SessionAPI session) {
			recycle();
			try {

				this.path = getRelativePath(request);
				this.session = session;
				String substring = path.substring(1);
				
				if (StringUtils.isNotBlank(substring) && (substring.indexOf(".") > 0)) {
					
					StringTokenizer tokens = new StringTokenizer(substring, "/.");
					if (tokens.hasMoreTokens()) {
						IEntitiesAPI entityAPI = session.getAPI().getEntitiesAPI();
	
						int documentId = Integer.parseInt(tokens.nextToken()); 
						IItem entity = entityAPI.getDocument(documentId);
	
						if (entity == null) {
							logger.warn("No se ha encontrado el documento con id [" + documentId + "]");
							return;
						}

						checkAuthorization(session, entity);
						
						document = entity.getKeyInt();
						documentReference = entity.getString("INFOPAG");
	
						IGenDocAPI document = session.getAPI().getGenDocAPI();
						Object connectorSession = null;
						try {
							connectorSession = document.createConnectorSession();
							// Obtiene la descripción del documento
							if (document.existsDocument(connectorSession, documentReference)) {
								exists = true;
								contentType = document.getMimeType(connectorSession, documentReference);
								length = document.getDocumentSize(connectorSession, documentReference);
								//creationDate = document.getCreateDate().getTime();
								//updateDate = FastHttpDateFormat.getDate(document.getUpdateDate());
							}

						}finally {
							if (connectorSession != null) {
								document.closeConnectorSession(connectorSession);
							}
				    	}		
	
					}
				} else {
					// Directorio raíz
					exists = true;
					authorized = true;
				}
			} catch (ISPACException e) {
				logger.error(e.getMessage());
			}
		}
		
		public void checkAuthorization(SessionAPI session, IItem document)
				throws ISPACException {

			IInvesflowAPI invesFlowAPI = session.getAPI();
			IWorklistAPI workListAPI = invesFlowAPI.getWorkListAPI();

			int stageId = document.getInt("ID_FASE");
			IStage stage = invesFlowAPI.getStage(stageId);
			
			if (stage.isActivity()) {

				// Comprobar los permisos de la actividad
				authorized = workListAPI.isInResponsibleList(
						stage.getString("ID_RESP"), stage);
				
			} else if (stage.isStage()) {

				int taskId = document.getInt("ID_TRAMITE");
				if (taskId > 0) {

					ITask task = invesFlowAPI.getTask(taskId);
					
					// Comprobar los permisos del trámite
					authorized = workListAPI.isInResponsibleList(
							stage.getString("ID_RESP"), task);

				} else {
					
					// Comprobar los permisos de la fase
					authorized = workListAPI.isInResponsibleList(
							stage.getString("ID_RESP"), stage);
				}
			}
		}
	}
	
	/**
	 * Establece en el AuditContextHolder el contexto de la sesión actual (ip,
	 * host, usuario, etc...) para la auditoría.
	 * 
	 * @param request
	 * @param session
	 */
	private void auditContext(HttpServletRequest request, SessionAPI session) {
		AuditContext auditContext = new AuditContext();
		auditContext.setUserHost(request.getRemoteHost());
		auditContext.setUserIP(request.getRemoteAddr());
		auditContext.setUser(session.getUserName());
		auditContext.setUserId(session.getClientContext().getUser().getUID());
		AuditContextHolder.setAuditContext(auditContext);
	}
}
