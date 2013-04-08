package ieci.tdw.ispac.ispacweb.webdav.servlets;

import ieci.tdw.ispac.api.ITemplateAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.impl.TemplateAPI;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.cat.CTTemplate;
import ieci.tdw.ispac.ispacweb.webdav.util.DOMWriter;
import ieci.tdw.ispac.ispacweb.webdav.util.XMLWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class TemplateServlet extends BaseServlet {
  
	private static final long serialVersionUID = -1861267665917885558L;

	private static Logger logger = Logger.getLogger(TemplateServlet.class);
  
  /**
   * PROPFIND Method.
   */
  protected void doPropfind(
  HttpServletRequest request, 
  HttpServletResponse response)
  throws ServletException, IOException {
    
    SessionAPI session = getSession( request);
    if (session == null) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return;
    }
    String path = getRelativePath(request);
    ResourceInfo resourceInfo = new ResourceInfo( path, session);
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
      Document document = 
      documentBuilder.parse(new InputSource(request.getInputStream()));
      
      // Get the root element of the document
      Element rootElement = document.getDocumentElement();
      NodeList childList = rootElement.getChildNodes();
      
      for (int i=0; i < childList.getLength(); i++) {
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
    } 
    catch(Exception e) 
	{}
    
    if (type == FIND_BY_PROPERTY) {
      properties = new Vector();
      NodeList childList = propNode.getChildNodes();
      
      for (int i=0; i < childList.getLength(); i++) {
        Node currentNode = childList.item(i);
        switch (currentNode.getNodeType()) {
          case Node.TEXT_NODE:
            break;
          case Node.ELEMENT_NODE:
            String nodeName = currentNode.getNodeName();
            String propertyName = null;
            if (nodeName.indexOf(':') != -1) {
              propertyName = nodeName.substring
              (nodeName.indexOf(':') + 1);
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
    
    generatedXML.writeElement(null, "multistatus" + generateNamespaceDeclarations(), XMLWriter.OPENING);
    
    parseProperties( request, 
    		         resourceInfo.creationDate,
    		         resourceInfo.updateDate,
    		         resourceInfo.length,
    		         resourceInfo.contentType,
					 generatedXML, 
					 path, 
					 type, 
					 properties, 
					 getETag(resourceInfo));
    
    generatedXML.writeElement(null, "multistatus", XMLWriter.CLOSING);
    
    generatedXML.sendData();
  }
  
  /**
   * LOCK Method.
   */
  protected void doLock(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {
    
    // Obtiene la conexión
    SessionAPI session = getSession( request);
    if (session == null) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return;
    }
    String path = getRelativePath(request);
    ResourceInfo resourceInfo = new ResourceInfo( path, session);

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
        lockDuration =
        (new Integer(lockDurationStr.substring(7))).intValue();
      } else {
        if (lockDurationStr.equalsIgnoreCase("infinity")) {
          lockDuration = MAX_TIMEOUT;
        } else {
          try {
            lockDuration = (new Integer(lockDurationStr)).intValue();
          } 
          catch (NumberFormatException e) {
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
      Document document = 
      documentBuilder.parse(new InputSource(request.getInputStream()));
      
      // Get the root element of the document
      Element rootElement = document.getDocumentElement();
      lockInfoNode = rootElement;
    } 
    catch(Exception e) 
    { lockRequestType = LOCK_REFRESH;}
    
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
                lock.scope = tempScope.substring
                (tempScope.indexOf(':') + 1);
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
        for (int i=0; i < childList.getLength(); i++) {
          Node currentNode = childList.item(i);
          switch (currentNode.getNodeType()) {
            case Node.TEXT_NODE:
              break;
            case Node.ELEMENT_NODE:
              String tempType = currentNode.getNodeName();
              if (tempType.indexOf(':') != -1) {
                lock.type =
                tempType.substring(tempType.indexOf(':') + 1);
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
    String lockTokenStr = request.getServletPath() 
                        + "-" 
                        + lock.type 
                        + "-"
                        + lock.scope 
                        + "-"
                        + lock.depth 
                        + "-" 
                        + lock.owner 
                        + "-" 
                        + lock.expiresAt 
                        + "-" 
                        + System.currentTimeMillis() 
                        + "-"
                        + secret;
    String lockToken =
    md5Encoder.encode(md5Helper.digest(lockTokenStr.getBytes()));
    lock.token = lockToken;

    if (lockRequestType == LOCK_CREATION) 
    {
      if (lock.isExclusive()) 
      {
      	try
		{
      		session.lockTemplate(resourceInfo.template);
		}
      	catch(ISPACException e)
		{
	        response.sendError(WebdavStatus.SC_LOCKED);
	        return;
		}
      	/*
	    if (resourceLocks.containsKey(lock.path)) 
	    {
	      LockInfo lck = (LockInfo) resourceLocks.get(lock.path);
	      long time = System.currentTimeMillis();
	      if (lck.expiresAt > time) 
	      {
	        response.sendError(WebdavStatus.SC_LOCKED);
	        return;
	      }
	    }
	    resourceLocks.put(lock.path, lock);
	    */
      } 
    }
    if (lockRequestType == LOCK_REFRESH) 
    {
	  // resourceLocks.put(lock.path, lock);
    }
    
    // Set the status, then generate the XML response containing
    // the lock information
    XMLWriter generatedXML = new XMLWriter();
    generatedXML.writeXMLHeader();
    generatedXML.writeElement(null, "prop" + generateNamespaceDeclarations(), XMLWriter.OPENING);
    generatedXML.writeElement(null, "lockdiscovery", XMLWriter.OPENING);
    lock.toXML(generatedXML, true);
    generatedXML.writeElement(null, "lockdiscovery",  XMLWriter.CLOSING);
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
  protected void doUnlock(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {
    
    // Obtiene la conexión
    SessionAPI session = getSession( request);
    if (session == null) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return;
    }
    String path = getRelativePath(request);
    ResourceInfo resourceInfo = new ResourceInfo( path, session);
    
    String lockTokenHeader = request.getHeader("Lock-Token");
    if (lockTokenHeader == null)
      lockTokenHeader = "";
    
    /*
    if (resourceLocks.containsKey(path))
    {
      resourceLocks.remove(path);
    }
    */
  	try
	{
  		session.unlockTemplate(resourceInfo.template);
	}
  	catch(ISPACException e)
	{}
   
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
  HttpServletResponse response)
  throws IOException, ServletException {
    
    SessionAPI session = getSession( request);
    if (session == null) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return;
    }
    String path = getRelativePath(request);
    ResourceInfo resourceInfo = new ResourceInfo( path, session);
    if (!resourceInfo.exists) {
      response.sendError(HttpServletResponse.SC_NOT_FOUND, path);
      return;
    }
    // ETag header
    response.setHeader("ETag", getETag(resourceInfo));
    // Last-Modified header
    response.setHeader("Last-Modified", getUpdateDate(resourceInfo.updateDate));
    // Set the appropriate content type
    if (resourceInfo.contentType != null) 
    { response.setContentType( resourceInfo.contentType);}
    // Obtiene el documento
    if (resourceInfo.length >= 0) {
      try 
      { response.setBufferSize(output);}
      catch (IllegalStateException e) 
      {}
      try {
        response.setContentLength(resourceInfo.length);
        ServletOutputStream out = response.getOutputStream();
        resourceInfo.getTemplate(out);
        response.setStatus(HttpServletResponse.SC_OK);
      } 
      catch(ISPACException e) 
      {
      	logger.error( e.getMessage());
      	response.sendError(HttpServletResponse.SC_CONFLICT);
      }
    }
  }

	/**
	 * Process a HEAD request for the specified resource.
	 * 
	 * @param request
	 *            The servlet request we are processing
	 * @param response
	 *            The servlet response we are creating
	 * 
	 * @exception IOException
	 *                if an input/output error occurs
	 * @exception ServletException
	 *                if a servlet-specified error occurs
	 */
	protected void doHead(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		SessionAPI session = getSession(request);
		if (session == null) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		String path = getRelativePath(request);
		ResourceInfo resourceInfo = new ResourceInfo(path, session);
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
	 * @param request
	 *            The servlet request we are processing
	 * @param response
	 *            The servlet response we are creating
	 * 
	 * @exception IOException
	 *                if an input/output error occurs
	 * @exception ServletException
	 *                if a servlet-specified error occurs
	 */
  protected void doPut(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {
    
    // Obtiene la conexión
    SessionAPI session = getSession( request);
    if (session == null) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return;
    }
    String path = getRelativePath(request);
    ResourceInfo resourceInfo = new ResourceInfo( path, session);

    try {
      // Salva el documento
      ServletInputStream in = request.getInputStream();
      int length = request.getContentLength();
      resourceInfo.setTemplate(in, length);
      response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    } 
    catch(ISPACException e) 
    {
      logger.error( e.getMessage());
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
  protected String getETag(ResourceInfo resourceInfo) 
  { return "W/\"" + resourceInfo.length + "-" + resourceInfo.template + "\"";}
  
  protected class ResourceInfo {
    
    /**
     * Constructor.
     *
     * @param pathname Path name of the file
     */
    public ResourceInfo( String path, SessionAPI session)
    { set( path, session);}
    
    public SessionAPI session;
    public CTTemplate ctTemplate;     // Plantilla
    
    public String path;
    public String contentType;
    public long creationDate;
    public long updateDate;
    public int length;
    public int template;
    public boolean exists;
    
    public void recycle() {
      session = null;
      ctTemplate = null;
      path = null;
      contentType = null;
      creationDate = 0;
      updateDate = 0;
      length = -1;
      template = -1;
      exists = false;
    }
    
    public void set( String path, SessionAPI session) {
      
      recycle();
      try {
      
        this.path = path;
        this.session = session;
        
        if ("/".equals(path)) {
        	// Directorio raíz
        	exists = true;
        } else {
	        String substring = path.substring( 1);
	        StringTokenizer tokens = new StringTokenizer( substring, "/.");
	        if (tokens.hasMoreTokens())
	        {
	          template = Integer.parseInt(tokens.nextToken());
	          ClientContext context = session.getClientContext(); 
	          		
	          ITemplateAPI templateAPI = new TemplateAPI(context);
	          ctTemplate = templateAPI.getTemplate(template);
	
	          contentType = ctTemplate.getMimetype();
	          updateDate = ctTemplate.getDate().getTime();
	          length = ctTemplate.getSize();
	          exists = true;
	        }
        }
      }
      catch( ISPACException e)
      {
      	logger.error( e.getMessage());
      }
    }

    public void getTemplate( OutputStream out)
    throws ISPACException
    {
        ITemplateAPI templateAPI = new TemplateAPI(session.getClientContext());
        templateAPI.getTemplate( ctTemplate, out);
    }

    public void setTemplate(InputStream in, int length)
    throws ISPACException
    {
        ITemplateAPI templateAPI = new TemplateAPI(session.getClientContext());

        templateAPI.setTemplate( ctTemplate, in,length, ctTemplate.getMimetype());
    }
  }
}
