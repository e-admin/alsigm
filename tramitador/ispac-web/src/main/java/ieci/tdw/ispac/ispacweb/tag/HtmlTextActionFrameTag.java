package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.XMLUtil.XMLDocUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XPathUtil;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;
import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.MessageResources;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class HtmlTextActionFrameTag extends DefaultTextTag {
		
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
	
	protected String imageTabIndex = "false";
	
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
	
	/**
	 * @return Returns the imageTabIndex.
	 */
	public String getImageTabIndex() {
		return imageTabIndex;
	}
	/**
	 * @param focus The imageTabIndex to set.
	 */
	public void setImageTabIndex(String imageTabIndex) {
		this.imageTabIndex = imageTabIndex;
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
		    		client.setSsVariable(getId(), toXml(parameters));
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
	
	protected String generateDeleteButtonJS(TagUtils tagUtils, String base, Map parameters) throws JspException {
 		StringBuffer html = new StringBuffer();
 		
 		// Generar la imagen que ejecuta el borrado
 		html.append("<span style=\"margin: 0px\" id=\"imgDelete_" + getId() + "\">&nbsp;");
 		
 		if (getImageTabIndex().equals("true")) {
 			
 			// Por la imagen pasa el tabulador -> enlace que ejecuta el javascript (a con href y onclick)
 			html.append("<a href=\"javascript: ispac_needToConfirm = true; ");
 		
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
	 		
	 		if (getTabindex() != null) {
	 		
	 			html.append(" tabindex=\"")
	 				.append(Integer.parseInt(getTabindex()) + 2)
	 				.append("\"");
	 		}
	 		
			html.append(" /></a>");
 		}
 		else {
 			// Por la imagen no pasa el tabulador -> imagen que ejecuta el javascript (img con onclick)
 			html.append("<img src=\"")
	 			.append(base + getImageDelete())
	 			.append("\" border=\"0\" style=\"cursor:pointer\"");
 	
			html.append(" onclick=\"javascript: ispac_needToConfirm = false; ");
 			
	 		if (StringUtils.isEmpty(getJsDelete())) {
	 			
	 			html.append("delete_" + getId());
	 		}
	 		else {
	 			html.append(getJsDelete());
	 		}
	 		html.append("(); ispac_needToConfirm = true;\"");
 	
	 		if (!StringUtils.isEmpty(getTitleKeyImageDelete())) {
	 			
				html.append(" title=\"")
					.append(tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getTitleKeyImageDelete()))
					.append("\"");
	 		}
 	
	 		if (!StringUtils.isEmpty(getStyleClassDeleteLink())) {
		
	 			html.append(" class='");
	 			html.append(getStyleClassDeleteLink());
	 			html.append("'");
	 		}
	 		
	 		html.append("/>");
 		}
 		
		html.append("</span>");
		
 		// Generar el script Javascript con la función que borra
		if (StringUtils.isEmpty(getJsDelete())) {	
 			
			html.append("<script>")
 				.append("function delete_")
 				.append(getId())
 				.append("() {");
 		
			if (!StringUtils.isEmpty(getConfirmDeleteKey())) {
				
				html.append("jConfirm('"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getConfirmDeleteKey())+"', '"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.confirm")+"', '"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.message.ok")+"', '"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.message.cancel")+"', function(r) {if(r){");
	
				/*html.append("if(confirm(\"")
				.append(tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getConfirmDeleteKey()))
				.append("\")) {");*/
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
	 		
	 		html.append("}")
	 			.append("</script>");
		}

		return html.toString();
	}
	
	protected String generateMultivalueDeleteButtonJS(TagUtils tagUtils, String base, Map parameters, int contador) throws JspException {
 		StringBuffer html = new StringBuffer();
 		
 		String id = getId();
		id += "_" + contador;
 		
 		// Generar la imagen que ejecuta el borrado
 		html.append("<span style=\"margin: 0px\" id=\"imgDelete_" + id + "\">&nbsp;");
 		
 		if (getImageTabIndex().equals("true")) {
 		
 			// Por la imagen pasa el tabulador -> enlace que ejecuta el javascript (a con href y onclick)
 			html.append("<a href=\"javascript: ispac_needToConfirm = true; ");
 		
 		if (StringUtils.isEmpty(getJsDelete())) {
 			
 			html.append("delete_" + getId() + "("+contador+")");
 		}
 		else {
 			html.append(getJsDelete()+"()");
 		}
 		
 		html.append(";\" class=\"")
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
		
	 		if (getTabindex() != null) {
		 		
	 			html.append(" tabindex=\"")
	 				.append(Integer.parseInt(getTabindex()) + 4 + 3*contador)
	 				.append("\"");
	 		}
	 		
			html.append(" /></a>");
 		}
 		else {
 			// Por la imagen no pasa el tabulador -> imagen que ejecuta el javascript (img con onclick)
 			html.append("<img src=\"")
	 			.append(base + getImageDelete())
	 			.append("\" border=\"0\" style=\"cursor:pointer\"");
 			
 			html.append(" onclick=\"javascript: ispac_needToConfirm = false; ");
 			
 			if (StringUtils.isEmpty(getJsDelete())) {
 	 			
 				html.append("delete_" + getId() + "("+contador+")");
 			}
 			else {
 				html.append(getJsDelete()+"()");
 			}
	 		html.append("; ispac_needToConfirm = true;\"");
 	
	 		if (!StringUtils.isEmpty(getTitleKeyImageDelete())) {
	 			
				html.append(" title=\"")
					.append(tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getTitleKeyImageDelete()))
					.append("\"");
	 		}
 	
	 		if (!StringUtils.isEmpty(getStyleClassDeleteLink())) {
		
	 			html.append(" class='");
	 			html.append(getStyleClassDeleteLink());
	 			html.append("'");
	 		}
	 		
	 		html.append("/>");
 		}
		
 		html.append("</span>");

		return html.toString();		
	}		
	
	protected String generateMultivalueDeleteFunctionJS(TagUtils tagUtils, Map parameters) throws JspException {
		//Generar el script Javascript con la función que borra
		StringBuffer html = new StringBuffer();
		if (StringUtils.isEmpty(getJsDelete())) {	
			html.append("<script>")
 				.append("function delete_")
 				.append(getId())
				.append("(id) {");
 					
 		
			if (!StringUtils.isEmpty(getConfirmDeleteKey())) {
				
				/*html.append("if(confirm(\"")
					.append(tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getConfirmDeleteKey()))
					.append("\")) {");*/
					html.append("jConfirm('"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getConfirmDeleteKey())+"', '"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.confirm")+"', '"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.message.ok")+"', '"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.message.cancel")+"', function(r) {if(r){");
				
				
			}
	 		
			Iterator it = parameters.entrySet().iterator();
			List listById = (List)parameters.get(ParameterMultivalueTag.PARAMETER_PROPERTIES_BYID);
			while (it.hasNext()) {
				
				Entry e = (Entry) it.next();
				String key = (String) e.getKey();
                if (key.equals(ParameterMultivalueTag.PARAMETER_PROPERTIES_BYID))
                	continue;
				
				if (!key.equals(ParameterTag.PARAMETER_ID_JAVASCRIPT)) {
					if (listById != null && listById.contains(key)){
						html.append("key = '"+ key+ "';");
						html.append("key = key.replace(')', '_'+id+')');");
						html.append("document.getElementById(key).value = '';");
					}else{
						html.append("document.forms[0].elements[ '")
							.append(key)
						    .append("' ].value = '';");
					}
				}
			}
			
			if (!StringUtils.isEmpty(getConfirmDeleteKey())) {
				//html.append("}");
				html.append("}});");
			}
	 		
	 		html.append("}")
	 			.append("</script>");
		}
		
		return html.toString();
	}

	public void release() {
		
		super.release();
		
		readonlyTag = false;
	}


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
	
	/*
	public String getBasePath(String base, String ispac, String skin) {
		 
		 String sPath = base;

	     if (ispac != null) {
	      	sPath += "/" + ispac;
	     }

	     sPath += "/" + skin + "/";

	     return sPath;
	}
	*/

}