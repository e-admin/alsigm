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

public class GetDocumentTag extends TagSupport {

	// Atributos del tag
	protected String document = null;
	protected String image = null;
	protected String message = null;
	protected String styleClass = null;
	protected String target = "workframe_document";
	protected String readonly = "false";

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

	public String getTarget() {
	  return this.target;
	}
	public void setTarget( String target) {
	  this.target = target;
	}

	public String getReadonly()
	{	return (this.readonly);}
	public void setReadonly(String readonly)
	{	this.readonly = readonly;}

	public int doStartTag() throws JspException
	{
		TagUtils tagUtils = TagUtils.getInstance();
		
        String sValue = null;
        if (StringUtils.isNotEmpty(getMessage())) {
        	sValue = tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getMessage());
        }

    	HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
    	String context = request.getContextPath();
    	
        String getDocumentUrl = context
				   		 	  + "/getDocument.do?document="
				   		 	  + getDocument()
				   		 	  + "&readonly="
				   		 	  + getReadonly();

		StringBuffer javascript = new StringBuffer();
		
		/*
 		// [Ver Documento] Modificacion para encontrar el frame sobre el que se abre el documento
		// [Codigo Original]
		//javascript.append("  document.all." + target + ".src=\"" + request.getContextPath() + "/getDocument.do?document=" + document + "\";\n");
		// [Codigo Añadido]
		String url = "\"" + request.getContextPath() + "/getDocument.do?document=" + document + "\"";
		javascript.append("  if (document.all) {\n");
		javascript.append("    document.all." + target + ".src=" + url + ";\n");
		javascript.append("  } else if (document.getElementById) {\n");
		javascript.append("    document.getElementById(\"" + target + "\").src=" + url + ";\n");
		javascript.append("  } else if (document.layers) {\n");
		javascript.append("    document.layers(\"" + target + "\").src=" + url + ";\n");
		javascript.append("  }\n");
		//------------------------------------
		*/
		
		if (StringUtils.isNotEmpty(getImage())) {
			
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
			javascript.append("<a target=\"" + target + "\" href=\"" + getDocumentUrl + "\" class=\"" + styleClass + "\">\n")
					  .append("<img src=\"" + imageRealPath + "\" border=\"0\" style=\"cursor:pointer\"/>")
					  .append("</a>\n");
			//javascript.append("</td>");
		}
		
		if (StringUtils.isNotEmpty(sValue)) {
			
			//javascript.append("<td valign=\"middle\">");
			javascript.append("<a target=\"" + target + "\" href=\"" + getDocumentUrl + "\" class=\"" + styleClass + "\">\n")
					  .append("<nobr>")
					  .append(sValue)
					  .append("</nobr>")
					  .append("</a>\n");
			//javascript.append("</td>");
		}

		JspWriter writer = pageContext.getOut();
		try {
			writer.print(javascript.toString());
		}
		catch (IOException ioe) {
			tagUtils.saveException(pageContext, ioe);
			throw new JspException(ioe.toString());
		}

		return (EVAL_BODY_INCLUDE);
	}
	
}