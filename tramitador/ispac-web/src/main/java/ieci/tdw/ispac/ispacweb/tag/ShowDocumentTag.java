package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;

/**
 * @author Lema
 */
public class ShowDocumentTag extends TagSupport {

	// Atributos del tag
	protected String document = null;
	protected String image = null;
	protected String message = null;
	protected String styleClass = null;
	
	protected String titleKeyLink = null;
	protected String titleLink = null;
	
	public String getDocument()
	{	return (this.document);}
	public void setDocument(String document)
	{	this.document = document;}

	public String getImage()
	{	return (this.image);}
	public void setImage(String image)
	{	this.image = image;}
	
	public String getMessage()
	{	return (this.message);}
	public void setMessage(String message)
	{	this.message = message;}

	public String getStyleClass()
	{	return (this.styleClass);}
	public void setStyleClass(String styleClass)
	{	this.styleClass = styleClass;}
	
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
	
	public int doStartTag() throws JspException	{
		
		TagUtils tagUtils = TagUtils.getInstance();
		
        String sValue = null;
        if (StringUtils.isNotEmpty(getMessage())) {
        	sValue = tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getMessage());
        }

    	HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
    	String context = request.getContextPath();
    	
        String showDocumentUrl = context
				   			   + "/showDocument.do?document="
				   			   + getDocument();
        
		StringBuffer htmlCode = new StringBuffer();
		
		/*
		javascript.append("<td valign='middle'>");
		javascript.append("<a href='"+ context + "/showDocument.do?document=" + document + "' class='" + styleClass + "'>\n");
		javascript.append("<img src='" + image + "' border='0' style='cursor:pointer'/>");
		javascript.append("</a>\n");
		javascript.append("</td>");
		javascript.append("<td valign='middle'>");
		javascript.append("<a href='"+ context + "/showDocument.do?document=" + document + "' class='" + styleClass + "'>\n");
		javascript.append("<nobr>");
		javascript.append(sValue);
		javascript.append("</nobr>");
		javascript.append("</a>\n");
		javascript.append("</td>");
		*/
		
		if (StringUtils.isNotEmpty(getImage())) {
			
			if (!StringUtils.isEmpty(getTitleKeyLink())) {
				setTitleLink(tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getTitleKeyLink()));
			}
			
	        String baseurl;
	        try {
	        	baseurl = StaticContext.getInstance().getBaseUrl(context);
	        }
	        catch (ISPACException ie) {
	            pageContext.setAttribute(Globals.EXCEPTION_KEY, ie, PageContext.REQUEST_SCOPE);
	            throw new JspException(ie.getMessage());
	        }
			
        	// Href que incluya el ispacbase junto con el skin
			String imageRealPath = StaticContext.rewriteHref(pageContext, baseurl, getImage());
			
			//javascript.append("<td valign=\"middle\">");
			htmlCode.append("<a target=\"_blank\" href=\"" + showDocumentUrl + "\" class=\"" + styleClass + "\">");
			htmlCode.append("<img src=\"" + imageRealPath + "\" border=\"0\" style=\"cursor:pointer\"");
		 	if (!StringUtils.isEmpty(getTitleKeyLink())) {
		 		htmlCode.append(" title=\"" + getTitleLink() + "\"");
		 	}
		 	htmlCode.append("/></a>\n");
			//javascript.append("</td>");
		}
		
		if (StringUtils.isNotEmpty(sValue)) {
			
			//javascript.append("<td valign=\"middle\">");
			htmlCode.append("<a target=\"_blank\" href=\"" + showDocumentUrl + "\" class=\"" + styleClass + "\">\n");
			htmlCode.append("<nobr>");
			htmlCode.append(sValue);
			htmlCode.append("</nobr>");
			//javascript.append("</td>");
		}
		
		JspWriter writer = pageContext.getOut();
		try {
			writer.print(htmlCode.toString());
		} 
		catch (IOException ioe) {
			tagUtils.saveException(pageContext, ioe);
			throw new JspException(ioe.toString());
		}

		return (EVAL_BODY_INCLUDE);
	}
	
}