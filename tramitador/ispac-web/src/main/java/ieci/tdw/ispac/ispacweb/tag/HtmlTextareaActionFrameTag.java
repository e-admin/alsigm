package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.impl.SessionAPIFactory;
import ieci.tdw.ispac.ispaclib.XMLUtil.XMLDocUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XPathUtil;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;
import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.html.TextareaTag;
import org.apache.struts.util.MessageResources;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class HtmlTextareaActionFrameTag extends TextareaTag {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(HtmlTextareaActionFrameTag.class);
	
	
	/**
	 * The message resources for this package.
	 */
	protected static MessageResources messages =
	MessageResources.getMessageResources(Constants.Package + ".LocalStrings");

	/**
	 * Nombre del frame
	 */
	protected String target = "workframe";
	
	protected String action = null;
	protected String width = "640";
	protected String height = "480";
	protected String showFrame = "true";
	protected String inputField = null;
	protected String styleClassLink = null;
	protected String titleKeyLink = null;
	protected String titleLink = null;
	protected String captionKey = null;

	protected String msgConfirmKey = null;
	protected String needToConfirm = "false";
	
	protected boolean readonlyTag = false;
	protected String propertyReadonly = "readonly";
	protected String styleClassReadonly = "inputReadOnly";
	
	protected String showDelete = "true";
	protected String styleClassDeleteLink = "tdlink";
	protected String imageDelete = "img/borrar.gif";
	protected String confirmDeleteKey = "msg.delete.data.selection";
	protected String jsDelete = null;
	protected String titleKeyImageDelete = "title.delete.data.selection";
	
	/**
	 * @return Returns the msgConfirmKey.
	 */
	public String getMsgConfirmKey() {
		return msgConfirmKey;
	}
	/**
	 * @param msgConfirmKey The msgConfirmKey to set.
	 */
	public void setMsgConfirmKey(String msgConfirmKey) {
		this.msgConfirmKey = msgConfirmKey;
	}

	/**
	 * @return Returns the needToConfirm.
	 */
	public String getNeedToConfirm() {
		return needToConfirm;
	}
	/**
	 * @param needToConfirm The needToConfirm to set.
	 */
	public void setNeedToConfirm(String needToConfirm) {
		this.needToConfirm = needToConfirm;
	}
	
	/**
	 * @return Returns the action.
	 */
	public String getAction() {
		return action;
	}
	/**
	 * @param action The action to set.
	 */
	public void setAction(String action) {
		this.action = action;
	}
	
	/**
	 * @return Returns the height.
	 */
	public String getHeight() {
		return height;
	}
	/**
	 * @param height The height to set.
	 */
	public void setHeight(String height) {
		this.height = height;
	}
	
	/**
	 * @return Returns the target.
	 */
	public String getTarget() {
		return target;
	}
	/**
	 * @param target The target to set.
	 */
	public void setTarget(String target) {
		this.target = target;
	}
	
	/**
	 * @return Returns the width.
	 */
	public String getWidth() {
		return width;
	}
	/**
	 * @param width The width to set.
	 */
	public void setWidth(String width) {
		this.width = width;
	}
	
	/**
	 * @return Returns the captionKey.
	 */
	public String getCaptionKey() {
		return captionKey;
	}
	/**
	 * @param captionKey The captionKey to set.
	 */
	public void setCaptionKey(String captionKey) {
		this.captionKey = captionKey;
	}
	
	/**
	 * @return Returns the inputField.
	 */
	public String getInputField() {
		return inputField;
	}
	/**
	 * @param inputField The inputField to set.
	 */
	public void setInputField(String inputField) {
		this.inputField = inputField;
	}
	
	/**
	 * @return Returns the showFrame.
	 */
	public String getShowFrame() {
		return showFrame;
	}
	/**
	 * @param showFrame The showFrame to set.
	 */
	public void setShowFrame(String showFrame) {
		this.showFrame = showFrame;
	}
	
	/**
	 * @return Returns the readonlyTag.
	 */
	public boolean isReadonlyTag() {
		return readonlyTag;
	}
	/**
	 * @param readonlyTag The readonlyTag to set.
	 */
	public void setReadonlyTag(boolean readonlyTag) {
		this.readonlyTag = readonlyTag;
	}
	
	/**
	 * @return Returns the styleClassLink.
	 */
	public String getStyleClassLink() {
		return styleClassLink;
	}
	/**
	 * @param styleClassLink The styleClassLink to set.
	 */
	public void setStyleClassLink(String styleClassLink) {
		this.styleClassLink = styleClassLink;
	}
	
	/**
	 * @return Returns the titleKeyLink.
	 */
	public String getTitleKeyLink() {
		return titleKeyLink;
	}
	/**
	 * @param titleKeyLink The titleKeyLink to set.
	 */
	public void setTitleKeyLink(String titleKeyLink) {
		this.titleKeyLink = titleKeyLink;
	}
	
	/**
	 * @return Returns the propertyReadonly.
	 */
	public String getPropertyReadonly() {
		return propertyReadonly;
	}
	/**
	 * @param propertyReadonly The propertyReadonly to set.
	 */
	public void setPropertyReadonly(String propertyReadonly) {
		this.propertyReadonly = propertyReadonly;
	}

	/**
	 * @return Returns the styleClassReadonly.
	 */
	public String getStyleClassReadonly() {
		return styleClassReadonly;
	}
	/**
	 * @param styleClassReadonly The styleClassReadonly to set.
	 */
	public void setStyleClassReadonly(String styleClassReadonly) {
		this.styleClassReadonly = styleClassReadonly;
	}
	
	/**
	 * @return Returns the titleLink.
	 */
	public String getTitleLink() {
		return titleLink;
	}
	/**
	 * @param titleLink The titleLink to set.
	 */
	public void setTitleLink(String titleLink) {
		this.titleLink = titleLink;
	}

	/**
	 * @return Returns the showDelete.
	 */
	public String getShowDelete() {
		return showDelete;
	}
	/**
	 * @param showDelete The showDelete to set.
	 */
	public void setShowDelete(String showDelete) {
		this.showDelete = showDelete;
	}
	
	/**
	 * @return Returns the styleClassDeleteLink.
	 */
	public String getStyleClassDeleteLink() {
		return styleClassDeleteLink;
	}
	/**
	 * @param styleClassDeleteLink The styleClassDeleteLink to set.
	 */
	public void setStyleClassDeleteLink(String styleClassDeleteLink) {
		this.styleClassDeleteLink = styleClassDeleteLink;
	}
	
	/**
	 * @return Returns the imageDelete.
	 */
	public String getImageDelete() {
		return imageDelete;
	}
	/**
	 * @param imageDelete The imageDelete to set.
	 */
	public void setImageDelete(String imageDelete) {
		this.imageDelete = imageDelete;
	}
	
	/**
	 * @return Returns the confirmDeleteKey.
	 */
	public String getConfirmDeleteKey() {
		return confirmDeleteKey;
	}
	/**
	 * @param confirmDeleteKey The confirmDeleteKey to set.
	 */
	public void setConfirmDeleteKey(String confirmDeleteKey) {
		this.confirmDeleteKey = confirmDeleteKey;
	}
	
	/**
	 * @return Returns the jsDelete.
	 */
	public String getJsDelete() {
		return jsDelete;
	}
	/**
	 * @param jsDelete The jsDelete to set.
	 */
	public void setJsDelete(String jsDelete) {
		this.jsDelete = jsDelete;
	}

	/**
	 * @return Returns the titleKeyImageDelete.
	 */
	public String getTitleKeyImageDelete() {
		return titleKeyImageDelete;
	}
	/**
	 * @param titleKeyImageDelete The titleKeyImageDelete to set.
	 */
	public void setTitleKeyImageDelete(String titleKeyImageDelete) {
		this.titleKeyImageDelete = titleKeyImageDelete;
	}
	
	
	public int doStartTag() throws JspException {
		
    	// Sólo lectura en el campo de texto
    	if (getReadonly()) {
    		setStyleClass(getStyleClassReadonly());
    	}
		
		// Sólo lectura en el control
		if (!isReadonlyTag()) {

			// Sólo lectura en el formulario
			String isReadonly = null;
			try {
				isReadonly = (String) TagUtils.getInstance().lookup(pageContext, getName(), getPropertyReadonly(), null);
			}
			catch (JspException e) {
			}
			
	    	if ((isReadonly != null) &&
	    		(isReadonly.equals("true"))) {
	    		
	    		setReadonlyTag(true);
	    		
	    		// Comprobar si el control permite escribir en el campo de texto (no es de sólo lectura)
	    		if (!getReadonly()) {
	    			setReadonly(true);
	    			setStyleClass(getStyleClassReadonly());	
	    		}
	    	}
		}
		else if (!getReadonly()) {
			setReadonly(true);
			setStyleClass(getStyleClassReadonly());
		}
		
		super.doStartTag();
				
	    if (!isReadonlyTag()) {
	
		    // Crea el mapa de parámetros asociado al tag
			pageContext.setAttribute(getId(), new HashMap());
	
			// Incluye en la petición el nombre del frame
			if (getAction().indexOf("?" ) == -1)	{
				action += "?parameters=" + getId();
			}
			else {
				action += "&parameters=" + getId();
			}
			// Incluye en la petición el campo de entrada
			if (getInputField() != null) {
				action += "&field=" + getInputField();
			}
			if (getCaptionKey() != null) {
				action += "&caption=" + getCaptionKey();
			}
	    }
	    
	 	return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspException {
		
    	if (!isReadonlyTag()) {
		
			Map parameters = (HashMap) pageContext.findAttribute(getId());
	
		    if (parameters != null) {

		    	/*
		    	SessionAPI sessionAPI = getSession();
		    	ClientContext client = sessionAPI.getClientContext();
	
		    	try	{
		    		
		    		// Salva los parámetros del tag en una variable
		    		// de sesión.
		    		client.setSsVariable(getId(), toXml( parameters));
				}
		    	catch (Exception e) {
		    		TagUtils.getInstance().saveException(pageContext, e);
		    		throw new JspException(e.toString());
		    	}
		    	*/
	
				HttpSession session = pageContext.getSession();
				if (session != null) {
					session.setAttribute(getId(), parameters);
				}
		    }
		    
		    if (getShowDelete().equals("true")) {
		    	
		    	/*
			    String ispac = (String) pageContext.getServletContext().getAttribute("ispacbase");
			    String skin = (String) pageContext.getServletContext().getAttribute("skin");
			    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			    String base = getBasePath(request.getContextPath(), ispac, skin);
			    */
		    	
		        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		        String context = request.getContextPath();
		    	
		        String baseurl;
		        try {
		        	baseurl = StaticContext.getInstance().getBaseUrl(context);
		        }
		        catch (ISPACException ie) {
		            pageContext.setAttribute(Globals.EXCEPTION_KEY, ie, PageContext.REQUEST_SCOPE);
		            throw new JspException(ie.getMessage());
		        }
		     
		        // Href que incluya el ispacbase junto con el skin
		    	String base = StaticContext.rewriteHref(pageContext, baseurl, "");
		    
			    TagUtils tagUtils = TagUtils.getInstance();
			 	JspWriter writer = pageContext.getOut();
			 	
			 	try	{
			 		writer.print(generateDeleteButtonJS(tagUtils, base, parameters));
			 	}
			 	catch (IOException e) {
			 		tagUtils.saveException(pageContext, e);
					throw new JspException(messages.getMessage("common.io", e.toString()));
			 	}
		    }
    	}

	    return EVAL_PAGE;
	}
	
	private String generateDeleteButtonJS(TagUtils tagUtils,
										  String base, 
										  Map parameters) throws JspException {
		
 		StringBuffer html = new StringBuffer();
 		
 		// Generar el enlace con la imagen
 		html.append("<span style=\"margin: 0px\" id=\"imgDelete_" + getId() + "\">")
 			.append("&nbsp;<a href=\"javascript: ");
 		
 		if (StringUtils.isEmpty(getJsDelete())) {
 			
 			html.append("delete_" + getId());
 		}
 		else {
 			html.append(getJsDelete());
 		}
 		
 		html.append("();\" class=\"")
 			.append(getStyleClassDeleteLink())
 			.append("\" onclick=\"javascript: ispac_needToConfirm = false;\">")
 			.append("<img src=\"")
 			.append(base + getImageDelete())
 			.append("\" border=\"0\"");
 		
 		if (!StringUtils.isEmpty(getTitleKeyImageDelete())) {
 			
			html.append(" title=\"")
				.append(tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getTitleKeyImageDelete()))
				.append("\"");
 		}
		html.append(" /></a>")
			.append("</span>");
		
 		// Generar el script Javascript con la función que borrar
		if (StringUtils.isEmpty(getJsDelete())) {	
 			
			html.append("<script>")
 				.append("function delete_")
 				.append(getId())
 				.append("() {");
 		
			if (!StringUtils.isEmpty(getConfirmDeleteKey())) {
				
				/*html.append("if(confirm(\"")
					.append(tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getConfirmDeleteKey()))
					.append("\")) {");*/
				html.append("jConfirm('"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getConfirmDeleteKey())+"', '"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.confirm")+"', '"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.message.ok")+"', '"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.message.cancel")+"', function(r) {if(r){");
			
			
			}
	 		
			Iterator it = parameters.entrySet().iterator();
			while (it.hasNext()) {
				
				Entry e = (Entry) it.next();
				String key = (String) e.getKey();
				
				if (!key.equals(ParameterTag.PARAMETER_ID_JAVASCRIPT)) {
					
					html.append("document.forms[0].elements[ '")
					    .append(key)
					    .append("' ].value = '';");
				}
			}
			
			if (!StringUtils.isEmpty(getConfirmDeleteKey())) {
				//html.append("}");
				html.append("}});");
			}
	 		
	 		html.append("ispac_needToConfirm = true;")
	 			.append("}")
	 			.append("</script>");
		}

		return html.toString();
	}

	public void release() {
		
		super.release();
		
		readonlyTag = false;
	}

	protected SessionAPI getSession() {

	  	SessionAPI sessionAPI = null;

	  	try	{
	  		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
	  		HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
	  		sessionAPI = SessionAPIFactory.getSessionAPI(request, response);
	    } catch( ISPACException e) {
	    	logger.warn("Error al obtener el SessionAPI", e);
	    }

	    return sessionAPI;
	}
	
//	private String getTicket() {
//	  	
//		String ticket = null;
//		
//		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
//		Cookie[] cookies = request.getCookies();
//		for (int i = 0; i < cookies.length; i++) {
//			
//			if (cookies[i].getName().equals("user")) {
//				ticket = cookies[i].getValue();
//				break;
//			}
//		}
//
//		return ticket;
//	}

	/*
	 * Convierte los parámetros del tag en un documento XML
	 */
	protected String toXml(Map parameters) throws ISPACException {
		 
		String sXml = null;
		String sParameters = "";

		Iterator iterator = parameters.entrySet().iterator();
		while (iterator.hasNext()) {
			
			Entry entry = (Entry) iterator.next();
			String str = XmlTag.newTag("id", (String) entry.getKey());
			str += XmlTag.newTag("property", (String) entry.getValue());
			sParameters += XmlTag.newTag("parameter", str);
		}

		sXml = XmlTag.getXmlInstruction("ISO-8859-1") + XmlTag.newTag("parameters", sParameters);

		return sXml;
	}

	/*
	 * Obtiene el mapa de parámetros del tag analizando
	 * el documento XML de parámetros.
	 */
	public static Map toMap( String sParameters) throws ISPACException {
		  
	  	HashMap parameters = new HashMap();
		NodeList nodeList = null;
		Node parameter = null;

		Document document = XMLDocUtil.newDocument(sParameters);

		nodeList = XPathUtil.getNodeList(document, "//parameters/parameter");
		if (nodeList != null) {
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				parameter = nodeList.item(i);

				if ((XPathUtil.existNode( parameter, "id")) && 
					(XPathUtil.existNode( parameter, "property"))) {
					
					String sId = XPathUtil.get(parameter, "id/text()");
					String sProperty = XPathUtil.get(parameter, "property/text()");
					parameters.put(sId, sProperty);
				}
			}
		}

		return parameters;
	}
	
	
	public String getMessage(HttpServletRequest request, Locale locale, String key) {
		
		MessageResources resources = getResources(request);
		if ((resources != null) && resources.isPresent(locale, key)) {
			return (resources.getMessage(locale, key));
		}
		
		return key;
	}
		
	public String getMessage(HttpServletRequest request, Locale locale, String key, Object params[]) {
		
		MessageResources resources = getResources(request);
		if ((resources != null) && resources.isPresent(locale, key)) {
			return (resources.getMessage(locale, key, params));
		}
		
		return key;
	}
	
	protected MessageResources getResources(HttpServletRequest request) {

        return ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));

    }
	
	
//	public String getBasePath(String base, String ispac, String skin) {
//		 
//		 String sPath = base;
//
//	     if (ispac != null) {
//	      	sPath += "/" + ispac;
//	     }
//
//	     sPath += "/" + skin + "/";
//
//	     return sPath;
//	}

}